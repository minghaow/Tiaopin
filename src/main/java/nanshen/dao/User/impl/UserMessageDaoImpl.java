package nanshen.dao.User.impl;

import nanshen.dao.User.UserMessageDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserMessage;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserMessageDaoImpl
 *
 * @Author WANG Minghao
 */
@Repository
public class UserMessageDaoImpl extends BaseDao implements UserMessageDao {

    @Override
    public UserMessage insert(UserMessage msg) {
        return dao.insert(msg);
    }

    @Override
    public UserMessage get(long id) {
        return dao.fetch(UserMessage.class, id);
    }

    @Override
    public List<UserMessage> getByUserId(long uid, PageInfo pageInfo) {
        Condition condition = Cnd.where("userId", "=", uid).desc("id");
        return dao.query(UserMessage.class, condition, genaratePager(pageInfo));
    }

    @Override
    public long count(long uid) {
        Condition condition = Cnd.where("userId", "=", uid);
        return dao.count(UserMessage.class, condition);
    }
}
