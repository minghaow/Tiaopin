package nanshen.data.User;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * UserMessage data
 *
 * @author WANG Minghao
 */
@Table("UserMessage")
public class UserMessage {

    @Id
    private long id;

    @Column
    private long userId;

    @Column
    private UserMessageType type;

    @Column
    private long answerUpUid = 0;

    @Column
    private long aid;

    @Column
    private String content;

    /** create time */
    @Column
    private Date createTime = new Date();

    public UserMessage(long aid, long answerUpUid, UserMessageType type, long userId, String content) {
        this.aid = aid;
        this.answerUpUid = answerUpUid;
        this.type = type;
        this.userId = userId;
        this.content = content;
    }

    public UserMessage() {
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

    public long getAnswerUpUid() {
        return answerUpUid;
    }

    public void setAnswerUpUid(long answerUpUid) {
        this.answerUpUid = answerUpUid;
    }

    public UserMessageType getType() {
        return type;
    }

    public void setType(UserMessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
