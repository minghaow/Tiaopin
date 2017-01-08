package nanshen.service.impl;

import nanshen.constant.TimeConstants;
import nanshen.dao.TopicDao;
import nanshen.dao.TopicQuestionMapDao;
import nanshen.dao.UserTopicSubDao;
import nanshen.data.Question.ComplexQuestion;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.Topic.Topic;
import nanshen.data.Topic.TopicQuestionMap;
import nanshen.data.User.UserInfo;
import nanshen.data.User.UserTopicSub;
import nanshen.service.QuestionService;
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

    @Autowired
    private TopicQuestionMapDao topicQuestionMapDao;

    @Autowired
    private QuestionService questionService;

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
    public List<Topic> getHotTopic(UserInfo userInfo) {
        List<UserTopicSub> topicSubList = userTopicSubDao.getByUserId(userInfo.getId());
        List<Long> subedTopicIdList = new ArrayList<Long>();
        for (UserTopicSub topicSub : topicSubList) {
            subedTopicIdList.add(topicSub.getId());
        }
        for (Topic topic : hotTopicList) {
            if (subedTopicIdList.contains(topic.getId())) {
                topic.setIsSubed(true);
            }
        }
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

    @Override
    public List<ComplexQuestion> getTopicQuestionList(long tid, PageInfo pageInfo) {
        List<TopicQuestionMap> topicQuestionMapList = topicQuestionMapDao.getByTid(tid, pageInfo);
        List<Long> questionIdList = new ArrayList<Long>();
        for (TopicQuestionMap topicQuestionMap : topicQuestionMapList) {
            questionIdList.add(topicQuestionMap.getQid());
        }
        return questionService.getComplexQuestionByIdList(questionIdList);
    }

}
