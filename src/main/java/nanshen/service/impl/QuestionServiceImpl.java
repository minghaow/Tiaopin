package nanshen.service.impl;

import nanshen.dao.Question.AnswerDao;
import nanshen.dao.Question.QuestionDao;
import nanshen.dao.TopicDao;
import nanshen.dao.UserInfoDao;
import nanshen.dao.UserQuestionSubDao;
import nanshen.data.Question.*;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.Topic.Topic;
import nanshen.data.User.UserInfo;
import nanshen.data.User.UserQuestionSub;
import nanshen.service.AccountService;
import nanshen.service.QuestionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private UserInfoDao userInfoDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserQuestionSubDao userQuestionSubDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private AccountService accountService;

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
    public ComplexQuestion getComplexQuestionByShowId(long qShowId) {
        Question question = questionDao.getByShowId(qShowId);
        if (question == null) {
            return null;
        }
        fillQuestionTopicList(question);
        List<Answer> answerList = answerDao.getByQuestionId(question.getId());
        for (Answer answer : answerList) {
            UserInfo userInfo = accountService.getUserInfo(answer.getUserId());
            if (userInfo != null) {
                answer.setUserName(userInfo.getUsername());
                answer.setUserDesc(userInfo.getUserDesc());
            }
        }
        UserInfo userInfo = accountService.getUserInfo(question.getUserId());
        return new ComplexQuestion(question.getId(), question.getShowId(), question, userInfo, answerList);
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
    public ComplexAnswer getComplexAnswerByShowId(long aShowId) {
        Answer answer = answerDao.getByShowId(aShowId);
        if (answer == null) {
            return null;
        }
        fillCleanContentList(answer);
        Question question = questionDao.get(answer.getQuestionId());
        if (question == null) {
            return null;
        }
        fillQuestionTopicList(question);
        UserInfo userInfo = accountService.getUserInfo(answer.getUserId());
        if (userInfo != null) {
            answer.setUserName(userInfo.getUsername());
            answer.setUserDesc(userInfo.getUserDesc());
        }
        return new ComplexAnswer(answer.getId(), answer, answer.getShowId(), question.getId(), question.getShowId(),
                question, userInfo, "");
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
        UserQuestionSub sub = userQuestionSubDao.insert(new UserQuestionSub(qid, userInfo.getId()));
        if (sub == null) {
            return ExecInfo.fail("关注失败，请稍后再关注一遍");
        }
        return ExecInfo.succ();
    }

    @Override
    public List<ComplexQuestion> getSubList(UserInfo userInfo, PageInfo pageInfo) {
        List<UserQuestionSub> subList = userQuestionSubDao.getByUserId(userInfo.getId(), pageInfo);
        List<Long> qidList = new ArrayList<Long>();
        for (UserQuestionSub sub : subList) {
            qidList.add(sub.getQid());
        }
        return getComplexQuestionByIdList(qidList);
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

}
