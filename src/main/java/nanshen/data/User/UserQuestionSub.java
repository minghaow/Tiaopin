package nanshen.data.User;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * UserQuestionSub data
 *
 * @author WANG Minghao
 */
@Table("UserQuestionSub")
public class UserQuestionSub {

    /** ID */
    @Id
    private long id;

    /** phone, need to be verified */
    @Column
    private long userId;

    /** user nick name */
    @Column
    private long qid;

    /** create time */
    @Column
    private Date createTime = new Date();

    public UserQuestionSub(long qid, long userId) {
        this.qid = qid;
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

    public long getQid() {
        return qid;
    }

    public void setQid(long qid) {
        this.qid = qid;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
