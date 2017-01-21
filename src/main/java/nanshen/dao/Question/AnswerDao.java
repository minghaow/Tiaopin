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

    boolean up(long aid);

    boolean upCancel(long aid);

    Answer getByQuestionIdAndUid(long qid, long uid);

    List<Answer> getByUid(long uid, PageInfo pageInfo);
}
