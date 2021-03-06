package nanshen.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import nanshen.constant.TimeConstants;
import nanshen.dao.User.UserInfoDao;
import nanshen.dao.User.UserMessageDao;
import nanshen.dao.User.UserPeopleSubDao;
import nanshen.data.Question.ComplexAnswer;
import nanshen.data.Question.Question;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.*;
import nanshen.service.PeopleService;
import nanshen.service.QuestionService;
import nanshen.service.common.ScheduledService;
import nanshen.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * People Service Implementation
 * NOTE: Mainly provide id and account information map according to all kinds of list
 *
 * @Author WANG Minghao
 */
@Service
public class PeopleServiceImpl extends ScheduledService  implements PeopleService {

    @Autowired
    private UserPeopleSubDao userPeopleSubDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserMessageDao userMessageDao;

    @Autowired
    private QuestionService questionService;

    /** 买手ID到买手信息的缓存 */
    private final LoadingCache<Long, UserInfo> userCache = CacheBuilder.newBuilder()
            .softValues()
            .maximumSize(1000)
            .expireAfterWrite(TimeConstants.HALF_HOUR_IN_SECONDS, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Long, UserInfo>() {
                        @Override
                        public UserInfo load(Long id) throws Exception {
                            return userInfoDao.getUserInfo(id);
                        }
                    });

    public void update() {
    }

    public int updatePeriod() {
        return TimeConstants.HOUR_IN_SECONDS;
    }

    @Override
    public ExecInfo subPeople(long uid, UserInfo userInfo) {
        UserPeopleSub peopleSub = userPeopleSubDao.insert(new UserPeopleSub(uid, userInfo.getId()));
        if (peopleSub == null) {
            return ExecInfo.fail("关注达人失败，请稍后再试");
        }
        userMessageDao.insert(new UserMessage(0, 0, UserMessageType.USER_SUB, uid, "", userInfo.getId()));
        return ExecInfo.succ();
    }

    @Override
    public ExecInfo subCancelPeople(long uid, UserInfo userInfo) {
        if (!userPeopleSubDao.remove(userInfo.getId(), uid)) {
            return ExecInfo.fail("取消关注达人失败，请稍后再试");
        }
        return ExecInfo.succ();
    }

    @Override
    public List<UserInfo> getSubList(UserInfo userInfo, PageInfo pageInfo) {
        List<UserPeopleSub> userPeopleSubList = userPeopleSubDao.getByUserId(userInfo.getId());
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        for (UserPeopleSub userPeopleSub : userPeopleSubList) {
            try {
                UserInfo tempUserInfo = userCache.get(userPeopleSub.getToUserId());
                tempUserInfo.setPassword("");
                userInfoList.add(tempUserInfo);
            } catch (Exception e) {
                LogUtils.info("[PeopleServiceImpl] getSubList cannot find some of subed user");
            }
        }
        return userInfoList;
    }

    @Override
    public ComplexUserInfo getUserInfo(UserInfo userInfo, long uid, PageInfo pageInfo) {
        UserInfo toUserInfo = getUserInfo(uid);
        if (toUserInfo == null) {
            return null;
        }
        List<ComplexAnswer> complexAnswerList = questionService.getAnswersByUid(uid, pageInfo);
        long answerCnt = questionService.getAnswerCntByUid(uid);
        toUserInfo.setAnswerCnt(answerCnt);
        if (userInfo != null) {
            UserPeopleSub userPeopleSub = userPeopleSubDao.getByUserIdAndToUserId(userInfo.getId(), uid);
            long cnt = userPeopleSubDao.getSubCount(uid);
            toUserInfo.setIsSubed(userPeopleSub != null);
            toUserInfo.setSubCnt(cnt);
        }
        return new ComplexUserInfo(complexAnswerList, toUserInfo);
    }

    @Override
    public List<UserMessage> getMsgList(UserInfo userInfo, PageInfo pageInfo) {
        List<UserMessage> msgList = userMessageDao.getByUserId(userInfo.getId(), pageInfo);
        for (UserMessage msg : msgList) {
            if (msg.getType() == UserMessageType.ANSWER_UP) {
                Question question = questionService.getQuestionByAid(msg.getAid());
                msg.setFirstString(question.getTitle());
                msg.setqShowId(question.getShowId());
                UserInfo subUserInfo = getUserInfo(msg.getAnswerUpUid());
                if (subUserInfo != null) {
                    msg.setSecondString(subUserInfo.getUsername());
                } else {
                    msg.setSecondString("已删除的用户");
                }
            } else if (msg.getType() == UserMessageType.USER_SUB) {
                UserInfo subUserInfo = getUserInfo(msg.getSubUid());
                if (subUserInfo != null) {
                    msg.setFirstString(subUserInfo.getUsername());
                } else {
                    msg.setFirstString("已删除的用户");
                }
            } else if (msg.getType() == UserMessageType.ANSWER_REPORT) {
                Question question = questionService.getQuestionByAid(msg.getAid());
                msg.setFirstString(question.getTitle());
            }
        }
        return msgList;
    }

    @Override
    public boolean invalidateUserCache(long uid) {
        userCache.invalidate(uid);
        return true;
    }

    private UserInfo getUserInfo(long uid) {
        try {
            return userCache.get(uid);
        } catch (ExecutionException e) {
            LogUtils.info("[PeopleServiceImpl] getUserIfno cannot find some of subed user");
        }
        return null;
    }

}
