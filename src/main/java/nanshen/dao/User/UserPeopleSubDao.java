package nanshen.dao.User;

import nanshen.data.User.UserPeopleSub;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface UserPeopleSubDao {

    UserPeopleSub insert(UserPeopleSub sub);

    UserPeopleSub get(long id);

    List<UserPeopleSub> getByUserId(long uid);

    UserPeopleSub getByUserIdAndToUserId(long uid, long toUid);

    boolean remove(long uid, long toUid);
}
