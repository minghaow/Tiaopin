package nanshen.data.User;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * UserPeopleSub data
 *
 * @author WANG Minghao
 */
@Table("UserPeopleSub")
public class UserPeopleSub {

    /** ID */
    @Id
    private long id;

    /** phone, need to be verified */
    @Column
    private long userId;

    /** user id */
    @Column
    private long toUserId;

    /** create time */
    @Column
    private Date createTime = new Date();

    public UserPeopleSub(long toUserId, long userId) {
        this.toUserId = toUserId;
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

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
