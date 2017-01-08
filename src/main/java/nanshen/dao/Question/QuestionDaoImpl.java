package nanshen.dao.Question;

import nanshen.dao.common.BaseDao;
import nanshen.data.Question.Question;
import nanshen.data.Question.QuestionStatus;
import nanshen.data.Question.QuestionType;
import nanshen.data.SystemUtil.PageInfo;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
@Repository
public class QuestionDaoImpl extends BaseDao implements QuestionDao {

    @Override
    public Question insert(Question question) {
        return dao.insert(question);
    }

    @Override
    public Question get(long questionId) {
        return dao.fetch(Question.class, questionId);
    }

    @Override
    public Question getByShowId(long qShowId) {
        Condition cnd = Cnd.where("showId", "=", qShowId);
        return dao.fetch(Question.class, cnd);
    }

    @Override
    public boolean update(Question question) {
        return dao.update(question) == 1;
    }

    @Override
    public boolean remove(long questionId) {
        return dao.delete(Question.class, questionId) == 1;
    }

    @Override
    public List<Question> getAll(PageInfo pageInfo) {
        Condition cnd = Cnd.where("status", "=", QuestionStatus.ONLINE)
                .desc("id");
        return dao.query(Question.class, cnd, genaratePager(pageInfo));
    }

    @Override
    public List<Question> getHot(List<QuestionType> typeList, PageInfo pageInfo) {
        Condition cnd = Cnd.where("status", "=", QuestionStatus.ONLINE)
                .and("type", "in", typeList)
                .desc("viewCnt");
        return dao.query(Question.class, cnd, genaratePager(pageInfo));
    }

    @Override
    public List<Question> get(List<Long> questionIdList) {
        Condition cnd = Cnd.where("id", "in", questionIdList);
        return dao.query(Question.class, cnd);
    }

}
