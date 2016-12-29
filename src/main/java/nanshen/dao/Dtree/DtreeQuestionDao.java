package nanshen.dao.Dtree;

import nanshen.data.Dtree.DtreeQuestion;

/**
 * @author WANG Minghao
 */
public interface DtreeQuestionDao {

    DtreeQuestion get(long topicId, long qid);

}
