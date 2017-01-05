package nanshen.dao.impl;

import nanshen.dao.TopicDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Topic.Topic;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Tiaopin
 *
 * @Author WANG Minghao
 */
@Repository
public class TopicDaoImpl extends BaseDao implements TopicDao {

    @Override
    public Topic insert(Topic topic) {
        return dao.insert(topic);
    }

    @Override
    public Topic get(long tid) {
        return dao.fetch(Topic.class, tid);
    }

    @Override
    public Topic getByShowId(long stid) {
        Condition cnd = Cnd.where("showId", "=", stid);
        return dao.fetch(Topic.class, cnd);
    }

    @Override
    public List<Topic> getHotTopicList() {
        Condition cnd = Cnd.where("isHot", "=", true);
        return dao.query(Topic.class, cnd);
    }
}
