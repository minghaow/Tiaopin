package nanshen.dao.Question;

import nanshen.data.Question.Question;
import nanshen.data.Question.QuestionType;
import nanshen.data.SystemUtil.PageInfo;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface QuestionDao {

    Question insert(Question question);

    Question get(long questionId);
    Question getByShowId(long qShowId);

    boolean update(Question question);

    boolean remove(long questionId);

    List<Question> getAll(PageInfo pageInfo);

    List<Question> getHot(List<QuestionType> typeList, PageInfo pageInfo);

    List<Question> get(List<Long> questionIdList);

}
