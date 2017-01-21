package nanshen.dao.User;

import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserMessage;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface UserMessageDao {

    UserMessage insert(UserMessage msg);

    UserMessage get(long id);

    List<UserMessage> getByUserId(long uid, PageInfo pageInfo);

    long count(long uid);
}
