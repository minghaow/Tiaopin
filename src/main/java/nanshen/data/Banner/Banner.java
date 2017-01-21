package nanshen.data.Banner;

import nanshen.data.PublicationStatus;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * Banner data
 *
 * @author WANG Minghao
 */
@Table("Banner")
public class Banner {

    @Id
    private long id;

    @Column
    private long userId = 0;

    @Column
    private BannerType type;

    @Column
    private PublicationStatus status = PublicationStatus.NEW;

    @Column
    private long firstId = 0;

    @Column
    private long secondId = 0;

    @Column
    private String text = "";

    @Column
    private String imgUrl = "";

    /** create time */
    @Column
    private Date createTime = new Date();

    public Banner(Date createTime, long firstId, long secondId, String text, BannerType type, long userId) {
        this.createTime = createTime;
        this.firstId = firstId;
        this.secondId = secondId;
        this.text = text;
        this.type = type;
        this.userId = userId;
    }

    public Banner() {
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getFirstId() {
        return firstId;
    }

    public void setFirstId(long firstId) {
        this.firstId = firstId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSecondId() {
        return secondId;
    }

    public void setSecondId(long secondId) {
        this.secondId = secondId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BannerType getType() {
        return type;
    }

    public void setType(BannerType type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public void setStatus(PublicationStatus status) {
        this.status = status;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
