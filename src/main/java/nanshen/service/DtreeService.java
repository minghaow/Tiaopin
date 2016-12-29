package nanshen.service;

import nanshen.data.Dtree.DtreeQuestion;

/**
 * Question related services
 *
 * @author WANG Minghao
 */
public interface DtreeService {

    DtreeQuestion getNextDtreeQuestion(long topicId, long qid);

}
