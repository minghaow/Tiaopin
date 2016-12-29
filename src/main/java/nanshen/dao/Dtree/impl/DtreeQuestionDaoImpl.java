package nanshen.dao.Dtree.impl;

import nanshen.dao.Dtree.DtreeQuestionDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Dtree.DtreeQuestion;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

/**
 * @author WANG Minghao
 */
@Repository
public class DtreeQuestionDaoImpl extends BaseDao implements DtreeQuestionDao {

    @Override
    public DtreeQuestion get(long topicId, long qid) {
        Condition cnd = Cnd.where("topicId", "=", topicId).and("id", "=", qid);
        return dao.fetch(DtreeQuestion.class, cnd);
    }

}
