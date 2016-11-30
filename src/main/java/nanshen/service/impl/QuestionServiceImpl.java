package nanshen.service.impl;

import nanshen.dao.Question.AnswerDao;
import nanshen.dao.Question.QuestionDao;
import nanshen.dao.UserInfoDao;
import nanshen.data.Question.Answer;
import nanshen.data.Question.ComplexQuestion;
import nanshen.data.Question.Question;
import nanshen.data.Question.QuestionType;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserInfo;
import nanshen.service.AccountService;
import nanshen.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private AnswerDao answerDao;

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

}
