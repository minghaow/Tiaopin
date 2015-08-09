package nanshen.data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * SkuInfo 商品信息数据
 *
 * @author WANG Minghao
 */
@Table("SkuInfo")
public class SkuInfo {

    /** ID */
    @Id
    private long id;

    /** 用户名 */
    @Column
    private long uploadUserId;

    /** 搭配名称 */
    @Column
    private String title;

    /** 搭配子名称 */
    @Column
    private String subTitle;

    /** sku link for outside domains */
    @Column
    private String url;

    /** 描述，html */
    @Column
    private String description;

    /** 标签 */
    @Column
    private String tags;

    /** 价格，单位：人民币分 */
    @Column
    private long price;

    /** 图片数量 */
    @Column
    private long imgCount = 0L;

    /** 图片列表 */
    private List<String> imgUrlList;

    /** 标签列表 */
    private List<SkuTag> skuTagList;

    /** 添加时间 */
    @Column
    private Date createTime = new Date();

    /** 更新时间 */
    @Column
    private Date updateTime = new Date();

    public SkuInfo(Date createTime, String description, long price, String subTitle, String title, String url, Date
            updateTime, long uploadUserId, String tags) {
        this.createTime = createTime;
        this.description = description;
        this.price = price;
        this.subTitle = subTitle;
        this.title = title;
        this.url = url;
        this.updateTime = updateTime;
        this.uploadUserId = uploadUserId;
        this.tags = tags;
    }

    public SkuInfo() {
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(long uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getImgCount() {
        return imgCount;
    }

    public void setImgCount(long imgCount) {
        this.imgCount = imgCount;
    }

    public List<SkuTag> getSkuTagList() {
        return skuTagList;
    }

    public void setSkuTagList(List<SkuTag> skuTagList) {
        this.skuTagList = skuTagList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
