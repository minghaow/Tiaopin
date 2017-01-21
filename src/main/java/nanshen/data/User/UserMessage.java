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
    private long aid = 0;

    @Column
    private long subUid = 0;

    @Column
    private String content = "";

    /** create time */
    @Column
    private Date createTime = new Date();

    private long qShowId = 0;

    private String firstString = "";

    private String secondString = "";

    public UserMessage(long aid, long answerUpUid, UserMessageType type, long userId, String content, long subUid) {
        this.aid = aid;
        this.answerUpUid = answerUpUid;
        this.type = type;
        this.userId = userId;
        this.content = content;
        this.subUid = subUid;
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

    public long getSubUid() {
        return subUid;
    }

    public void setSubUid(long subUid) {
        this.subUid = subUid;
    }

    public String getFirstString() {
        return firstString;
    }

    public void setFirstString(String firstString) {
        this.firstString = firstString;
    }

    public String getSecondString() {
        return secondString;
    }

    public void setSecondString(String secondString) {
        this.secondString = secondString;
    }

    public long getqShowId() {
        return qShowId;
    }

    public void setqShowId(long qShowId) {
        this.qShowId = qShowId;
    }
}
