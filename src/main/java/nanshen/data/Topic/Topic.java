package nanshen.data.Topic;

import nanshen.constant.SystemConstants;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * Topic
 *
 * @Author WANG Minghao
 */
@Table("Topic")
public class Topic {

    /** ID */
    @Id
    private long id;

    /** showId */
    @Column
    private long showId;

    @Column
    private boolean isHot;

    private boolean isSubed = false;

    @Column
    private String title = "";

    @Column
    private String imgUrl = "";

    @Column
    private String bannerImgUrl = "";

    public Topic() {
    }

    public Topic(String bannerImgUrl, String imgUrl, long showId, String title) {
        this.bannerImgUrl = bannerImgUrl;
        this.imgUrl = imgUrl;
        this.showId = showId;
        this.title = title;
    }

    public String getBannerImgUrl() {
        return bannerImgUrl;
    }

    public void setBannerImgUrl(String bannerImgUrl) {
        this.bannerImgUrl = bannerImgUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getShowId() {
        return showId;
    }

    public void setShowId(long showId) {
        this.showId = showId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setIsHot(boolean isHot) {
        this.isHot = isHot;
    }

    public boolean isSubed() {
        return isSubed;
    }

    public void setIsSubed(boolean isSubed) {
        this.isSubed = isSubed;
    }

    public long generateShowId(long userId) {
        long timeMillis = System.currentTimeMillis();
        if(userId<=0 || timeMillis<=0) {
            return 0;
        }
        long showId = timeMillis + userId * 1000001;
        char[] c = String.valueOf(showId).toCharArray();
        for(int i = 0; i < c.length; i++) {
            int number = c[i] - '0';
            number = (number + SystemConstants.SHOWID_TOPIC) % 10;
            c[i] = (char) ('0' + number);
        }
        String showIdStr = new String(c);
        return Long.parseLong(showIdStr);
    }
}
