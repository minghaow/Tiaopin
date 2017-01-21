package nanshen.service;

import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.ComplexUserInfo;
import nanshen.data.User.UserInfo;
import nanshen.data.User.UserMessage;

import java.util.List;

/**
 * 人的相关操作
 *
 * @author WANG Minghao
 */
public interface PeopleService {

    /**
     * subscribe hot people
     *
     * @return ExecInfo
     */
    ExecInfo subPeople(long uid, UserInfo userInfo);

    ExecInfo subCancelPeople(long uid, UserInfo userInfo);

    List<UserInfo> getSubList(UserInfo userInfo, PageInfo pageInfo);

    ComplexUserInfo getUserInfo(UserInfo userInfo, long uid, PageInfo pageInfo);

    List<UserMessage> getMsgList(UserInfo userInfo, PageInfo pageInfo);

    boolean invalidateUserCache(long uid);
}
