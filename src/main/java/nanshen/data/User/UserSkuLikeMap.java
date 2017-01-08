package nanshen.data.User;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * UserSkuLikeMap data
 *
 * @author WANG Minghao
 */
@Table("UserSkuLikeMap")
public class UserSkuLikeMap {

    /** ID */
    @Id
    private long id;

    /** uid */
    @Column
    private long uid;

    /** tid */
    @Column
    private long sid;

    /** send time */
    @Column
    private Date createTime = new Date();

    public UserSkuLikeMap() {
    }

    public UserSkuLikeMap(long sid, long uid) {
        this.sid = sid;
        this.uid = uid;
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

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
