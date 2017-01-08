package nanshen.data.Sku;

import nanshen.data.CustomerReview.CustomerReview;
import nanshen.data.PublicationStatus;
import nanshen.data.Sku.SkuAttri.*;
import nanshen.utils.ViewUtils;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * Tiaopin
 *
 * @Author WANG Minghao
 */
@Table("Sku")
public class Sku {

    /** ID */
    @Id
    private long id;

    /** showId */
    @Column
    private long showId;

    /** the one who upload this sku, updater will not update this value */
    @Column
    private long uploadUserId = 0;

    /** title, attractive information */
    @Column
    private String title = "";

    /** sub title for the sku, explaining the title normally */
    @Column
    private String subTitle = "";

    /** order notice for the sku */
    @Column
    private String notice = "";

    @Column
    private SkuCategoryOneType categoryOneType = SkuCategoryOneType.ALL;

    @Column
    private SkuCategoryTwoType categoryTwoType = SkuCategoryTwoType.ALL;

    @Column
    private SkuColorType colorType = SkuColorType.ALL;

    @Column
    private SkuMaterialType materialType = SkuMaterialType.ALL;

    @Column
    private SkuSituationType situationType = SkuSituationType.ALL;

    @Column
    private SkuSpecialType specialType = SkuSpecialType.ALL;

    @Column
    private SkuStyleType styleType = SkuStyleType.ALL;

    @Column
    private SkuUserType userType = SkuUserType.ALL;

    /** warning for the sku */
    @Column
    private String warning = "";

    /** sku link for outside domains */
    @Column
    private String url = "";

    /** description, to be designed */
    @Column
    private String description = "";

    /** tags */
    @Column
    private String tags;

    /** price, price unit: RMB */
    @Column
    private long price;

    /** image count, for displaying the image(the first one will be 0 and then 1, etc.) */
    @Column
    private long imgCount = 0L;

    /** description image count, for displaying the image(the first one will be 0 and then 1, etc.) */
    @Column
    private long contentImgCount = 0L;

    /** online status {@code nanshen.data.PublicationStatus} */
    @Column
    private PublicationStatus status = PublicationStatus.NEW;

    /** total sales info for sku items */
    private long totalSalesInfo = 0;

    /** monthly sales info for sku items */
    private long monthlySalesInfo = 0;

    private long likeCnt = 0;

    /** customer review */
    private List<CustomerReview> customerReviewList;

    /** sku item describe info */
    private SkuItemDescription skuItemDescription;

    /** create time for this look, will fill when create */
    @Column
    private Date createTime = new Date();

    /** update time for this look, all operator will update this value */
    @Column
    private Date updateTime = new Date();

    public Sku() {
    }

    public Sku(String title, String subTitle, long price, String tags, String url) {
        this.title = title;
        this.showId = generateShowId(1);
        this.price = price;
        this.subTitle = subTitle;
        this.tags = tags;
        this.url = url;
    }

    public long getContentImgCount() {
        return contentImgCount;
    }

    public void setContentImgCount(long contentImgCount) {
        this.contentImgCount = contentImgCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<CustomerReview> getCustomerReviewList() {
        return customerReviewList;
    }

    public void setCustomerReviewList(List<CustomerReview> customerReviewList) {
        this.customerReviewList = customerReviewList;
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

    public long getImgCount() {
        return imgCount;
    }

    public void setImgCount(long imgCount) {
        this.imgCount = imgCount;
    }

    public long getMonthlySalesInfo() {
        return monthlySalesInfo;
    }

    public void setMonthlySalesInfo(long monthlySalesInfo) {
        this.monthlySalesInfo = monthlySalesInfo;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getConvertedPrice() {
        return ViewUtils.priceConverter(price);
    }

    public long getShowId() {
        return showId;
    }

    public void setShowId(long showId) {
        this.showId = showId;
    }

    public SkuItemDescription getSkuItemDescription() {
        return skuItemDescription;
    }

    public void setSkuItemDescription(SkuItemDescription skuItemDescription) {
        this.skuItemDescription = skuItemDescription;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public void setStatus(PublicationStatus status) {
        this.status = status;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTotalSalesInfo() {
        return totalSalesInfo;
    }

    public void setTotalSalesInfo(long totalSalesInfo) {
        this.totalSalesInfo = totalSalesInfo;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public SkuCategoryOneType getCategoryOneType() {
        return categoryOneType;
    }

    public void setCategoryOneType(SkuCategoryOneType categoryOneType) {
        this.categoryOneType = categoryOneType;
    }

    public SkuCategoryTwoType getCategoryTwoType() {
        return categoryTwoType;
    }

    public void setCategoryTwoType(SkuCategoryTwoType categoryTwoType) {
        this.categoryTwoType = categoryTwoType;
    }

    public SkuColorType getColorType() {
        return colorType;
    }

    public void setColorType(SkuColorType colorType) {
        this.colorType = colorType;
    }

    public SkuMaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(SkuMaterialType materialType) {
        this.materialType = materialType;
    }

    public SkuSituationType getSituationType() {
        return situationType;
    }

    public void setSituationType(SkuSituationType situationType) {
        this.situationType = situationType;
    }

    public SkuSpecialType getSpecialType() {
        return specialType;
    }

    public void setSpecialType(SkuSpecialType specialType) {
        this.specialType = specialType;
    }

    public SkuStyleType getStyleType() {
        return styleType;
    }

    public void setStyleType(SkuStyleType styleType) {
        this.styleType = styleType;
    }

    public SkuUserType getUserType() {
        return userType;
    }

    public void setUserType(SkuUserType userType) {
        this.userType = userType;
    }

    public long getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(long likeCnt) {
        this.likeCnt = likeCnt;
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
            number = (number + 4) % 10;
            c[i] = (char) ('0' + number);
        }
        String showIdStr = new String(c);
        return Long.parseLong(showIdStr);
    }
}
