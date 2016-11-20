package nanshen.service;

import nanshen.data.Question.ComplexAnswer;
import nanshen.data.Question.QuestionType;
import nanshen.data.SystemUtil.PageInfo;

import java.util.List;

/**
 * Question related services
 *
 * @author WANG Minghao
 */
public interface AnswerService {

    List<ComplexAnswer> getHotAnswers(List<QuestionType> typeList, PageInfo pageInfo);

    List<ComplexAnswer> getNewAnswers(List<QuestionType> typeList, PageInfo pageInfo);

    ComplexAnswer getComplexAnswerByShowId(long questionId);

}
