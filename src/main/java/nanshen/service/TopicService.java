package nanshen.service;

import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.Topic.Topic;
import nanshen.data.User.UserInfo;

import java.util.List;

/**
 * Topic related services
 *
 * @author WANG Minghao
 */
public interface TopicService {

    /**
     * get hot topic
     *
     * @return list of hot topic
     */
    List<Topic> getHotTopic();

    /**
     * subscribe hot topic
     *
     * @return ExecInfo
     */
    ExecInfo subTopic(long tid, UserInfo userInfo);

}
