package nanshen.dao.Question;

import nanshen.dao.common.BaseDao;
import nanshen.data.Question.Answer;
import nanshen.data.Question.AnswerStatus;
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
public class AnswerDaoImpl extends BaseDao implements AnswerDao {

    @Override
    public Answer insert(Answer answer) {
        return dao.insert(answer);
    }

    @Override
    public Answer get(long answerId) {
        return dao.fetch(Answer.class, answerId);
    }

    @Override
    public Answer getByShowId(long aShowId) {
        Condition cnd = Cnd.where("showId", "=", aShowId);
        return dao.fetch(Answer.class, cnd);
    }

    @Override
    public boolean update(Answer answer) {
        return dao.update(answer) == 1;
    }

    @Override
    public boolean remove(long answerId) {
        return dao.delete(Answer.class, answerId) == 1;
    }

    @Override
    public List<Answer> getAll(PageInfo pageInfo) {
        Condition cnd = Cnd.where("status", "=", AnswerStatus.ONLINE)
                .desc("id");
        return dao.query(Answer.class, cnd, genaratePager(pageInfo));
    }

    @Override
    public List<Answer> getHot(PageInfo pageInfo) {
        Condition cnd = Cnd.where("status", "=", AnswerStatus.ONLINE)
                .desc("upCnt");
        return dao.query(Answer.class, cnd, genaratePager(pageInfo));
    }

    @Override
    public List<Answer> getByQuestionId(long questionId) {
        Condition cnd = Cnd.where("questionId", "=", questionId)
                .and("status", "=", AnswerStatus.ONLINE)
                .desc("upCnt");
        return dao.query(Answer.class, cnd);
    }

}
