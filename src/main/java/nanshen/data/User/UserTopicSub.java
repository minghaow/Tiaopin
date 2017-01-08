package nanshen.data.User;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * UserTopicSub data
 *
 * @author WANG Minghao
 */
@Table("UserTopicSub")
public class UserTopicSub {

    /** ID */
    @Id
    private long id;

    /** phone, need to be verified */
    @Column
    private long userId;

    /** user nick name */
    @Column
    private long tid;

    /** create time */
    @Column
    private Date createTime = new Date();

    public UserTopicSub(long tid, long userId) {
        this.tid = tid;
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
