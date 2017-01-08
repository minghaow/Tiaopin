package nanshen.dao.impl;

import nanshen.dao.TopicQuestionMapDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Topic.TopicQuestionMap;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TopicQuestionMapImpl
 *
 * @Author WANG Minghao
 */
@Repository
public class TopicQuestionMapDaoImpl extends BaseDao implements TopicQuestionMapDao {

    @Override
    public TopicQuestionMap insert(TopicQuestionMap sub) {
        return dao.insert(sub);
    }

    @Override
    public TopicQuestionMap get(long id) {
        return dao.fetch(TopicQuestionMap.class, id);
    }

    @Override
    public List<TopicQuestionMap> getByTid(long tid) {
        Condition cnd = Cnd.where("tid", "=", tid).desc("id");
        return dao.query(TopicQuestionMap.class, cnd);
    }

}
