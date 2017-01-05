package nanshen.dao.Question;

import nanshen.data.Question.Answer;
import nanshen.data.SystemUtil.PageInfo;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface AnswerDao {

    Answer insert(Answer answer);

    Answer get(long answerId);
    Answer getByShowId(long aShowId);

    boolean update(Answer answer);

    boolean remove(long answerId);

    List<Answer> getAll(PageInfo pageInfo);

    List<Answer> getHot(PageInfo pageInfo);

    List<Answer> getByQuestionId(long questionId);
}