package nanshen.service.impl;

import nanshen.dao.Question.AnswerDao;
import nanshen.dao.Question.QuestionDao;
import nanshen.dao.UserInfoDao;
import nanshen.data.Question.Answer;
import nanshen.data.Question.ComplexAnswer;
import nanshen.data.Question.Question;
import nanshen.data.Question.QuestionType;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserInfo;
import nanshen.service.AccountService;
import nanshen.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Tiaopin
 *
 * @Author WANG Minghao
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private AccountService accountService;

    @Override
    public List<ComplexAnswer> getHotAnswers(List<QuestionType> typeList, PageInfo pageInfo) {
        List<Answer> answerList = answerDao.getHot(pageInfo);
        List<ComplexAnswer> complexAnswerList = new ArrayList<ComplexAnswer>();
        for (Answer answer : answerList) {
            Question question = questionDao.get(answer.getQuestionId());
            UserInfo userInfo = accountService.getUserInfo(answer.getUserId());
            complexAnswerList.add(new ComplexAnswer(answer.getId(), answer, answer.getShowId(), question.getId(),
                    question.getShowId(), question, userInfo));
        }
        return complexAnswerList;
    }

    @Override
    public List<ComplexAnswer> getNewAnswers(List<QuestionType> typeList, PageInfo pageInfo) {
        return null;
    }

    @Override
    public ComplexAnswer getComplexAnswerByShowId(long aShowId) {
        Answer answer = answerDao.getByShowId(aShowId);
        if (answer == null) {
            return null;
        }
        Question question = questionDao.get(answer.getQuestionId());
        UserInfo userInfo = accountService.getUserInfo(answer.getUserId());
        return new ComplexAnswer(answer.getId(), answer, aShowId, question.getId(), question.getShowId(), question, userInfo);
    }
}
