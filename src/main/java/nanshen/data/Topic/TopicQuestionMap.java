package nanshen.data.Topic;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * TopicQuestionMap data
 *
 * @author WANG Minghao
 */
@Table("TopicQuestionMap")
public class TopicQuestionMap {

    /** ID */
    @Id
    private long id;

    /** qid */
    @Column
    private long qid;

    /** tid */
    @Column
    private long tid;

    public TopicQuestionMap(long qid, long tid) {
        this.qid = qid;
        this.tid = tid;
    }

    public TopicQuestionMap() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQid() {
        return qid;
    }

    public void setQid(long qid) {
        this.qid = qid;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }
}
