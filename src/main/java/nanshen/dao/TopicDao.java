package nanshen.dao;

import nanshen.data.Topic.Topic;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface TopicDao {

    Topic insert(Topic topic);

    Topic get(long tid);

    Topic getByShowId(long stid);

    List<Topic> getHotTopicList();

}
