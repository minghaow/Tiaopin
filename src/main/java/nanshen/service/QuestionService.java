package nanshen.service;

import nanshen.data.Question.ComplexAnswer;
import nanshen.data.Question.ComplexQuestion;
import nanshen.data.Question.Question;
import nanshen.data.Question.QuestionType;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserInfo;

import java.util.List;

/**
 * Question related services
 *
 * @author WANG Minghao
 */
public interface QuestionService {

    List<ComplexQuestion> getHotQuestions(List<QuestionType> typeList, PageInfo pageInfo);

    List<Question> getNewQuestions(List<QuestionType> typeList, PageInfo pageInfo);

    Question getQuestion(long questionId);

    ComplexQuestion getComplexQuestionByShowId(long qShowId);

    ComplexQuestion getQuestionAndAnswerByShowId(long qShowId, long aShowId);

    ComplexAnswer getComplexAnswerByShowId(long aShowId);

    List<ComplexAnswer> getHotAnswers(PageInfo pageInfo);

    List<ComplexQuestion> getComplexQuestionByIdList(List<Long> questionIdList);

    ComplexAnswer getComplexAnswerByAidAndQid(long aid, long qid);

    ExecInfo subByQid(long qid, UserInfo userInfo);

    List<ComplexQuestion> getSubList(UserInfo userInfo, PageInfo pageInfo);
}
