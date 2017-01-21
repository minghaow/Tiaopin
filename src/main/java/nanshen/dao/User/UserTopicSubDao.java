package nanshen.dao.User;

import nanshen.data.User.UserTopicSub;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface UserTopicSubDao {

    UserTopicSub insert(UserTopicSub sub);

    UserTopicSub get(long id);

    List<UserTopicSub> getByUserId(long uid);

    boolean remove(long tid, long uid);
}
