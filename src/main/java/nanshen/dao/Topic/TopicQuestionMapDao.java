package nanshen.dao.Topic;

import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.Topic.TopicQuestionMap;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface TopicQuestionMapDao {

    TopicQuestionMap insert(TopicQuestionMap map);

    TopicQuestionMap get(long id);

    List<TopicQuestionMap> getByTid(long tid, PageInfo pageInfo);

}
