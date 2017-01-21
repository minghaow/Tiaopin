package nanshen.dao.Question.impl;

import nanshen.dao.Question.AnswerDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Question.Answer;
import nanshen.data.Question.AnswerStatus;
import nanshen.data.SystemUtil.PageInfo;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
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

    @Override
    public boolean up(long aid) {
        Sql sql = Sqls.create("UPDATE Answer " +
                "SET upCnt = upCnt + 1 " +
                "WHERE id = @aid");
        sql.params().set("aid", aid);
        dao.execute(sql);
        return 1 == sql.getUpdateCount();
    }

    @Override
    public boolean upCancel(long aid) {
        Sql sql = Sqls.create("UPDATE Answer " +
                "SET upCnt = upCnt - 1 " +
                "WHERE id = @aid");
        sql.params().set("aid", aid);
        dao.execute(sql);
        return 1 == sql.getUpdateCount();
    }

    @Override
    public Answer getByQuestionIdAndUid(long qid, long uid) {
        Condition cnd = Cnd.where("questionId", "=", qid)
                .and("userId", "=", uid);
        return dao.fetch(Answer.class, cnd);
    }

    @Override
    public List<Answer> getByUid(long uid, PageInfo pageInfo) {
        Condition cnd = Cnd.where("userId", "=", uid).desc("updateTime");
        return dao.query(Answer.class, cnd, genaratePager(pageInfo));
    }

    @Override
    public long getCountByUid(long uid) {
        Condition cnd = Cnd.where("userId", "=", uid);
        return dao.count(Answer.class, cnd);
    }

}
