package nanshen.service.impl;

import nanshen.constant.TimeConstants;
import nanshen.dao.TopicDao;
import nanshen.dao.UserTopicSubDao;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.Topic.Topic;
import nanshen.data.User.UserInfo;
import nanshen.data.User.UserTopicSub;
import nanshen.service.TopicService;
import nanshen.service.common.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Topic related service
 *
 * @Author WANG Minghao
 */
@Service
public class TopicServiceImpl extends ScheduledService implements TopicService {

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private UserTopicSubDao userTopicSubDao;

    private List<Topic> hotTopicList = new ArrayList<Topic>();

    @Override
    public void update() {
        long startTime = System.currentTimeMillis();

        hotTopicList = topicDao.getHotTopicList();

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("[TopicService] Update in " + totalTime + "ms");
    }

    @Override
    public int updatePeriod() {
        return TimeConstants.TEN_MINUTE_IN_SECONDS;
    }

    @Override
    public boolean runPreUpdate() {
        return true;
    }

    @Override
    public List<Topic> getHotTopic() {
        return hotTopicList;
    }

    @Override
    public ExecInfo subTopic(long tid, UserInfo userInfo) {
        UserTopicSub topicSub = userTopicSubDao.insert(new UserTopicSub(tid, userInfo.getId()));
        if (topicSub == null) {
            return ExecInfo.fail("关注话题失败，请稍后再试");
        }
        return ExecInfo.succ();
    }
}
