package nanshen.dao.User.impl;

import nanshen.dao.User.UserAnswerUpDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserAnswerUp;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserAnswerUpDaoImpl
 *
 * @Author WANG Minghao
 */
@Repository
public class UserAnswerUpDaoImpl extends BaseDao implements UserAnswerUpDao {

    @Override
    public UserAnswerUp insert(UserAnswerUp up) {
        return dao.insert(up);
    }

    @Override
    public UserAnswerUp get(long id) {
        return dao.fetch(UserAnswerUp.class, id);
    }

    @Override
    public List<UserAnswerUp> getByUserId(long uid, PageInfo pageInfo) {
        Condition cnd = Cnd.where("userId", "=", uid).desc("id");
        return dao.query(UserAnswerUp.class, cnd, genaratePager(pageInfo));
    }

    @Override
    public UserAnswerUp get(long uid, long aid) {
        Condition cnd = Cnd.where("userId", "=", uid).and("aid", "=", aid);
        return dao.fetch(UserAnswerUp.class, cnd);
    }

    @Override
    public long count(long aid) {
        Condition cnd = Cnd.where("aid", "=", aid);
        return dao.count(UserAnswerUp.class, cnd);
    }

    @Override
    public ExecInfo remove(long aid, long uid) {
        Condition cnd = Cnd.where("aid", "=", aid).and("userId", "=", uid);
        if (dao.clear(UserAnswerUp.class, cnd) > 0) {
            return ExecInfo.succ();
        }
        return ExecInfo.fail("UP取消失败");
    }

}
