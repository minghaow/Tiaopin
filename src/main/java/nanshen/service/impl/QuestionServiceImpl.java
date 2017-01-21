package nanshen.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import nanshen.constant.SystemConstants;
import nanshen.constant.TimeConstants;
import nanshen.dao.Question.AnswerDao;
import nanshen.dao.Question.QuestionDao;
import nanshen.dao.Topic.TopicDao;
import nanshen.dao.User.UserAnswerUpDao;
import nanshen.dao.User.UserMessageDao;
import nanshen.dao.User.UserQuestionSubDao;
import nanshen.data.Question.*;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.Topic.Topic;
import nanshen.data.User.*;
import nanshen.service.AccountService;
import nanshen.service.QuestionService;
import nanshen.service.api.oss.OssFormalApi;
import nanshen.utils.LogUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tiaopin
 *
 * @Author WANG Minghao
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserQuestionSubDao userQuestionSubDao;

    @Autowired
    private UserAnswerUpDao userAnswerUpDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private UserMessageDao userMessageDao;

    @Autowired
    private OssFormalApi ossFormalApi;

    @Autowired
    private AccountService accountService;

    /** 回答的缓存 */
    private final LoadingCache<Long, Answer> answerCache = CacheBuilder.newBuilder()
            .softValues()
            .maximumSize(200)
            .expireAfterWrite(TimeConstants.HALF_HOUR_IN_SECONDS, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Long, Answer>() {
                        @Override
                        public Answer load(Long id) throws Exception {
                            return answerDao.get(id);
                        }
                    });

    @Override
    public List<ComplexQuestion> getHotQuestions(List<QuestionType> typeList, PageInfo pageInfo) {
        List<Question> questionList = questionDao.getHot(typeList, pageInfo);
        List<ComplexQuestion> complexQuestionList = new ArrayList<ComplexQuestion>();
        for (Question question : questionList) {
            List<Answer> answerList = answerDao.getByQuestionId(question.getId());
            UserInfo userInfo = accountService.getUserInfo(question.getUserId());
            complexQuestionList.add(new ComplexQuestion(question.getId(), question.getShowId(), question, userInfo, answerList));
        }
        return complexQuestionList;
    }

    @Override
    public List<Question> getNewQuestions(List<QuestionType> typeList, PageInfo pageInfo) {
        return null;
    }

    @Override
    public Question getQuestion(long questionId) {
        return questionDao.get(questionId);
    }

    @Override
    public ComplexQuestion getComplexQuestionByShowId(UserInfo userInfo, long qShowId) {
        Question question = questionDao.getByShowId(qShowId);
        if (question == null) {
            return null;
        }
        if (userInfo != null) {
            UserQuestionSub sub = userQuestionSubDao.get(userInfo.getId(), question.getId());
            question.setSubed(sub != null);
        }
        fillQuestionTopicList(question);
        List<Answer> answerList = answerDao.getByQuestionId(question.getId());
        for (Answer answer : answerList) {
            UserInfo userInfoT = accountService.getUserInfo(answer.getUserId());
            if (userInfoT != null) {
                answer.setUserName(userInfoT.getUsername());
                answer.setUserDesc(userInfoT.getUserDesc());
                answer.setUserImgUrl(userInfoT.getImgUrl());
            }
        }
        UserInfo userInfoT = accountService.getUserInfo(question.getUserId());
        return new ComplexQuestion(question.getId(), question.getShowId(), question, userInfoT, answerList);
    }

    private void fillQuestionTopicList(Question question) {
        if (StringUtils.isNotBlank(question.getTopicIdList())) {
            List<Topic> topicList = new ArrayList<Topic>();
            for (String topicIdString : question.getTopicIdList().split(",")) {
                Topic topic = topicDao.get(Long.parseLong(topicIdString));
                topicList.add(topic);
            }
            question.setTopicList(topicList);
        }
    }

    @Override
    public ComplexQuestion getQuestionAndAnswerByShowId(long qShowId, long aShowId) {
        Question question = questionDao.getByShowId(qShowId);
        if (question == null) {
            return null;
        }
        Answer answer = answerDao.getByShowId(aShowId);
        UserInfo userInfo = accountService.getUserInfo(question.getUserId());
        return new ComplexQuestion(question.getId(), question.getShowId(), question, userInfo, Collections.singletonList(answer));
    }

    @Override
    public ComplexAnswer getComplexAnswerByShowId(UserInfo userInfo, long aShowId) {
        Answer answer = answerDao.getByShowId(aShowId);
        if (answer == null) {
            return null;
        }
        if (userInfo != null) {
            UserAnswerUp up = userAnswerUpDao.get(userInfo.getId(), answer.getId());
            answer.setUped(up != null);
        }
        fillCleanContentList(answer);
        Question question = questionDao.get(answer.getQuestionId());
        if (question == null) {
            return null;
        }
        fillQuestionTopicList(question);
        UserInfo userInfoT = accountService.getUserInfo(answer.getUserId());
        if (userInfoT != null) {
            answer.setUserName(userInfoT.getUsername());
            answer.setUserDesc(userInfoT.getUserDesc());
            answer.setUserImgUrl(userInfoT.getImgUrl());
        }
        return new ComplexAnswer(answer.getId(), answer, answer.getShowId(), question.getId(), question.getShowId(),
                question, userInfoT, "");
    }

    @Override
    public List<ComplexAnswer> getHotAnswers(PageInfo pageInfo) {
        List<Answer> answerList = answerDao.getHot(pageInfo);
        List<ComplexAnswer> complexAnswerList = new ArrayList<ComplexAnswer>();
        for (Answer answer : answerList) {
            answer.setContent("");
            Pattern p = Pattern.compile("<img src=\"(.+)\"/>");
            Matcher m = p.matcher(answer.getCleanContent());
            String imgUrl = m.find() ? m.group(1) : "";
            Question question = questionDao.get(answer.getQuestionId());
            UserInfo userInfo = accountService.getUserInfo(answer.getUserId());
            complexAnswerList.add(new ComplexAnswer(answer.getId(), answer, answer.getShowId(), question.getId(),
                    question.getShowId(), question, userInfo, imgUrl));
        }
        return complexAnswerList;
    }

    @Override
    public List<ComplexQuestion> getComplexQuestionByIdList(List<Long> questionIdList) {
        List<Question> questionList = questionDao.get(questionIdList);
        List<ComplexQuestion>  complexQuestionList = new ArrayList<ComplexQuestion>();
        for (Question question : questionList) {
            UserInfo userInfo = accountService.getUserInfo(question.getUserId());
            fillQuestionTopicList(question);
            complexQuestionList.add(new ComplexQuestion(question.getId(), question.getShowId(), question, userInfo,
                    new ArrayList<Answer>()));
        }
        return complexQuestionList;
    }

    @Override
    public ComplexAnswer getComplexAnswerByAidAndQid(long aid, long qid) {
        Answer answer = answerDao.get(aid);
        Question question = questionDao.get(qid);
        UserInfo userInfo = accountService.getUserInfo(answer.getUserId());
        return new ComplexAnswer(aid, answer, answer.getShowId(), qid, question.getShowId(), question, userInfo, "");
    }

    @Override
    public ExecInfo subByQid(long qid, UserInfo userInfo) {
        if (userInfo == null) {
            return ExecInfo.fail("还未登陆或已失效，请重新登陆");
        }
        Question question = questionDao.getByShowId(qid);
        UserQuestionSub sub = userQuestionSubDao.insert(new UserQuestionSub(question.getId(), userInfo.getId()));
        if (sub == null) {
            return ExecInfo.fail("关注失败，请稍后再关注一遍");
        }
        return ExecInfo.succ();
    }

    @Override
    public ExecInfo subCancelByQid(long qid, UserInfo userInfo) {
        if (userInfo == null) {
            return ExecInfo.fail("还未登陆或已失效，请重新登陆");
        }
        Question question = questionDao.getByShowId(qid);
        if (!userQuestionSubDao.remove(question.getId(), userInfo.getId())) {
            return ExecInfo.fail("关注取消失败，请稍后再取消");
        }
        return ExecInfo.succ();
    }

    @Override
    public List<ComplexQuestion> getSubList(UserInfo userInfo, PageInfo pageInfo) {
        if (userInfo == null) {
            return new ArrayList<ComplexQuestion>();
        }
        List<UserQuestionSub> subList = userQuestionSubDao.getByUserId(userInfo.getId(), pageInfo);
        List<Long> qidList = new ArrayList<Long>();
        for (UserQuestionSub sub : subList) {
            qidList.add(sub.getQid());
        }
        return getComplexQuestionByIdList(qidList);
    }

    @Override
    public ExecInfo upAnswer(long aid, UserInfo userInfo) {
        Answer answer = getAnswer(aid);
        if (answer != null) {
            UserAnswerUp userAnswerUp = userAnswerUpDao.insert(new UserAnswerUp(aid, userInfo.getId(), answer.getId()));
            if (userAnswerUp != null) {
                if (answerDao.up(aid)) {
                    userMessageDao.insert(new UserMessage(aid, userInfo.getId(), UserMessageType.ANSWER_UP, answer.getUserId(), "", 0));
                    return ExecInfo.succ();
                }
            }
        }
        return ExecInfo.fail("UP失败");
    }

    @Override
    public ExecInfo upCancelAnswer(long aid, UserInfo userInfo) {
        if (userAnswerUpDao.remove(aid, userInfo.getId()).isSucc()) {
            if (answerDao.upCancel(aid)) {
                return ExecInfo.succ();
            }
        }
        return ExecInfo.fail("UP取消失败");
    }

    @Override
    public ExecResult<Answer> createAnswer(long qShowId, UserInfo userInfo) {
        if (userInfo == null) {
            return ExecResult.fail("登录后才可以写答案哦");
        }
        Question question = getQuestionByShowId(qShowId);
        Answer answer = answerDao.getByQuestionIdAndUid(question.getId(), userInfo.getId());
        if (answer == null) {
            answer = answerDao.insert(new Answer(question.getId(), "", "", AnswerStatus.DRAFT, userInfo.getId()));
        } else {
            if (StringUtils.isNotBlank(answer.getCleanContent())) {
                Pattern p = Pattern.compile("<img src=\"(.+)\"/>");
                Matcher m = p.matcher(answer.getCleanContent());
                long imgCnt = 0;
                while(m.find()){
                    imgCnt ++;
                }
                answer.setImgCnt(imgCnt);
            }
        }
        return ExecResult.succ(answer);
    }

    private Question getQuestionByShowId(long qShowId) {
        return questionDao.getByShowId(qShowId);
    }

    @Override
    public ExecInfo uploadImage(long aShowId, String name, MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        Answer answer = answerDao.getByShowId(aShowId);
        if (answer == null) {
            return ExecInfo.fail("上传错误，不存在的答案ID");
        }
        return uploadImageToOss(file, is, aShowId, name);
    }

    @Override
    public ExecInfo submitAnswer(long aShowId, String content, UserInfo userInfo) {
        Answer answer = answerDao.getByShowId(aShowId);
        if (answer == null || userInfo == null || answer.getUserId() != userInfo.getId()) {
            return ExecInfo.fail("错误的身份信息");
        }
        answer.setContent(content);
        answer.setStatus(AnswerStatus.ONLINE);
        answer.setUpdateTime(new Date());
        answer.setCleanContent(content);
        setDescription(content, answer);
        if (!answerDao.update(answer)) {
            return ExecInfo.fail("更新失败");
        }
        return ExecInfo.succ();
    }

    @Override
    public List<ComplexAnswer> getAnswersByUid(long uid, PageInfo pageInfo) {
        List<Answer> answerList = answerDao.getByUid(uid, pageInfo);
        List<ComplexAnswer> complexAnswerList = new ArrayList<ComplexAnswer>();
        for (Answer answer : answerList) {
            answer.setContent("");
            Pattern p = Pattern.compile("<img src=\"(.+)\"/>");
            Matcher m = p.matcher(answer.getCleanContent());
            String imgUrl = m.find() ? m.group(1) : "";
            Question question = questionDao.get(answer.getQuestionId());
            complexAnswerList.add(new ComplexAnswer(answer.getId(), answer, answer.getShowId(), question.getId(),
                    question.getShowId(), question, null, imgUrl));
        }
        return complexAnswerList;
    }

    private void setDescription(String content, Answer answer) {
        Pattern p = Pattern.compile("<img src=\"(.+)\"/>");
        Matcher m = p.matcher(content);
        content = m.replaceAll("");
        content = content.replaceAll("\t", "");
        content = content.replaceAll("\n", "");
        content = content.replaceAll("&quot;", "");
        answer.setDescription(content.substring(0, content.length() < 100 ? content.length() : 100));
    }

    private ExecInfo uploadImageToOss(MultipartFile file, InputStream is, long aShowId, String name) {
        String imgKey = getImgKey(file, aShowId, name);
        ExecInfo execInfo = ExecInfo.fail("上传云服务失败");
        try {
            execInfo = ossFormalApi.putObject(SystemConstants.BUCKET_NAME, imgKey, is, file.getSize());
        } catch (FileNotFoundException e) {
            System.out.println("上传图片文件未找到");
        }
        return execInfo;
    }

    private String getImgKey(MultipartFile file, long aShowId, String name) {
        String imgKey = "images/answer/" + aShowId + "/" + name;
        LogUtils.info("imgKey : " + imgKey);
        return imgKey;
    }

    private void fillCleanContentList(Answer answer) {
        if (StringUtils.isNotBlank(answer.getCleanContent())) {
            String cleanContent = answer.getCleanContent();
            cleanContent = " " + cleanContent + " ";
            List<AnswerCleanContent> answerCleanContents = new ArrayList<AnswerCleanContent>();
            Pattern p = Pattern.compile("<img src=\"(.+)\"/>");
            Matcher m = p.matcher(cleanContent);
            while (m.find()) {
                answerCleanContents.add(new AnswerCleanContent(m.group(1), ""));
            }
            cleanContent = m.replaceAll("<img>");
            String[] cleanContentArray = cleanContent.split("<img>");
            for (int i = 0; i < cleanContentArray.length - 1; i++) {
                AnswerCleanContent answerCleanContent = answerCleanContents.get(i);
                answerCleanContent.setText(cleanContentArray[i]);
            }
            answerCleanContents.add(new AnswerCleanContent("", cleanContentArray[cleanContentArray.length - 1]));
            answer.setCleanContentList(answerCleanContents);
        }
    }

    private Answer getAnswer(long aid) {
        try {
            return answerCache.get(aid);
        } catch (ExecutionException e) {
            LogUtils.info("[QuesitonSergviceImpl] getAnswer cannot find the answer by aid.");
        }
        return null;
    }

}
