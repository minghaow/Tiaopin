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
@Table("UserAnswerUp")
public class UserAnswerUp {

    /** ID */
    @Id
    private long id;

    /** phone, need to be verified */
    @Column
    private long userId;

    /** user nick name */
    @Column
    private long aid;

    /** create time */
    @Column
    private Date createTime = new Date();

    public UserAnswerUp(long aid, long userId) {
        this.aid = aid;
        this.userId = userId;
    }

    public UserAnswerUp() {
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

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
