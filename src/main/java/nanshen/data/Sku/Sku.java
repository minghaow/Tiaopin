package nanshen.data.Sku;

import nanshen.data.CustomerReview.CustomerReview;
import nanshen.data.PublicationStatus;
import nanshen.data.Sku.SkuAttri.*;
import org.apache.commons.lang.StringUtils;
import org.nutz.castor.FailToCastObjectException;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.*;

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
    private String categoryOneType = "ALL";

    @Column
    private String categoryTwoType = "ALL";

    @Column
    private String colorType = "ALL";

    @Column
    private String materialType = "ALL";

    @Column
    private String situationType = "ALL";

    @Column
    private String specialType = "ALL";

    @Column
    private String styleType = "ALL";

    @Column
    private String userType = "ALL";

    private List<SkuCategoryOneType> categoryOneTypeList = Collections.singletonList(SkuCategoryOneType.ALL);
    private List<SkuCategoryTwoType> categoryTwoTypeList = Collections.singletonList(SkuCategoryTwoType.ALL);
    private List<SkuColorType> colorTypeList = Collections.singletonList(SkuColorType.ALL);
    private List<SkuMaterialType> materialTypeList = Collections.singletonList(SkuMaterialType.ALL);
    private List<SkuSituationType> situationTypeList = Collections.singletonList(SkuSituationType.ALL);
    private List<SkuSpecialType> specialTypeList = Collections.singletonList(SkuSpecialType.ALL);
    private List<SkuStyleType> styleTypeList = Collections.singletonList(SkuStyleType.ALL);
    private List<SkuUserType> userTypeList = Collections.singletonList(SkuUserType.ALL);

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

    public String getCategoryOneType() {
        return categoryOneType;
    }

    public void setCategoryOneType(String categoryOneType) {
        this.categoryOneType = categoryOneType;
    }

    public List<SkuCategoryOneType> getCategoryOneTypeList() {
        List<String> typeString = getListFromString(categoryOneType);
        if (typeString.size() == 0) {
            return Collections.singletonList(SkuCategoryOneType.ALL);
        }
        List<SkuCategoryOneType> categoryOneTypeList = new ArrayList<SkuCategoryOneType>();
        for (String type : typeString) {
            categoryOneTypeList.add(SkuCategoryOneType.get(type));
        }
        return categoryOneTypeList;
    }

    public void setCategoryOneTypeList(List<SkuCategoryOneType> categoryOneTypeList) {
        this.categoryOneTypeList = categoryOneTypeList;
    }

    public String getCategoryTwoType() {
        return categoryTwoType;
    }

    public void setCategoryTwoType(String categoryTwoType) {
        this.categoryTwoType = categoryTwoType;
    }

    public List<SkuCategoryTwoType> getCategoryTwoTypeList() {
        List<String> typeString = getListFromString(categoryTwoType);
        if (typeString.size() == 0) {
            return Collections.singletonList(SkuCategoryTwoType.ALL);
        }
        List<SkuCategoryTwoType> categoryTwoTypeList = new ArrayList<SkuCategoryTwoType>();
        for (String type : typeString) {
            categoryTwoTypeList.add(SkuCategoryTwoType.get(type));
        }
        return categoryTwoTypeList;
    }

    public void setCategoryTwoTypeList(List<SkuCategoryTwoType> categoryTwoTypeList) {
        this.categoryTwoTypeList = categoryTwoTypeList;
    }

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public List<SkuColorType> getColorTypeList() {
        List<String> typeString = getListFromString(colorType);
        if (typeString.size() == 0) {
            return Collections.singletonList(SkuColorType.ALL);
        }
        List<SkuColorType> colorTypeList = new ArrayList<SkuColorType>();
        for (String type : typeString) {
            colorTypeList.add(SkuColorType.get(type));
        }
        return colorTypeList;
    }

    public void setColorTypeList(List<SkuColorType> colorTypeList) {
        this.colorTypeList = colorTypeList;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public List<SkuMaterialType> getMaterialTypeList() {
        List<String> typeString = getListFromString(materialType);
        if (typeString.size() == 0) {
            return Collections.singletonList(SkuMaterialType.ALL);
        }
        List<SkuMaterialType> materialTypeList = new ArrayList<SkuMaterialType>();
        for (String type : typeString) {
            materialTypeList.add(SkuMaterialType.get(type));
        }
        return materialTypeList;
    }

    public void setMaterialTypeList(List<SkuMaterialType> materialTypeList) {
        this.materialTypeList = materialTypeList;
    }

    public String getSituationType() {
        return situationType;
    }

    public void setSituationType(String situationType) {
        this.situationType = situationType;
    }

    public List<SkuSituationType> getSituationTypeList() {
        List<String> typeString = getListFromString(situationType);
        if (typeString.size() == 0) {
            return Collections.singletonList(SkuSituationType.ALL);
        }
        List<SkuSituationType> situationTypeList = new ArrayList<SkuSituationType>();
        for (String type : typeString) {
            situationTypeList.add(SkuSituationType.get(type));
        }
        return situationTypeList;
    }

    public void setSituationTypeList(List<SkuSituationType> situationTypeList) {
        this.situationTypeList = situationTypeList;
    }

    public String getSpecialType() {
        return specialType;
    }

    public void setSpecialType(String specialType) {
        this.specialType = specialType;
    }

    public List<SkuSpecialType> getSpecialTypeList() {
        List<String> typeString = getListFromString(specialType);
        if (typeString.size() == 0) {
            return Collections.singletonList(SkuSpecialType.ALL);
        }
        List<SkuSpecialType> specialTypeList = new ArrayList<SkuSpecialType>();
        for (String type : typeString) {
            specialTypeList.add(SkuSpecialType.get(type));
        }
        return specialTypeList;
    }

    public void setSpecialTypeList(List<SkuSpecialType> specialTypeList) {
        this.specialTypeList = specialTypeList;
    }

    public String getStyleType() {
        return styleType;
    }

    public void setStyleType(String styleType) {
        this.styleType = styleType;
    }

    public List<SkuStyleType> getStyleTypeList() {
        List<String> typeString = getListFromString(styleType);
        if (typeString.size() == 0) {
            return Collections.singletonList(SkuStyleType.ALL);
        }
        List<SkuStyleType> styleTypeList = new ArrayList<SkuStyleType>();
        for (String type : typeString) {
            styleTypeList.add(SkuStyleType.get(type));
        }
        return styleTypeList;
    }

    public void setStyleTypeList(List<SkuStyleType> styleTypeList) {
        this.styleTypeList = styleTypeList;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<SkuUserType> getUserTypeList() {
        List<String> userTypeString = getListFromString(userType);
        if (userTypeString.size() == 0) {
            return Collections.singletonList(SkuUserType.ALL);
        }
        List<SkuUserType> userTypeList = new ArrayList<SkuUserType>();
        for (String skuUserType : userTypeString) {
            userTypeList.add(SkuUserType.get(skuUserType));
        }
        return userTypeList;
    }

    public void setUserTypeList(List<SkuUserType> userTypeList) {
        this.userTypeList = userTypeList;
    }

    public List<String> getListFromString(String src) throws FailToCastObjectException {
        if (StringUtils.isNotBlank(src)) {
            String[] stringArr = src.split(",");
            if (stringArr.length > 0) {
                return Arrays.asList(stringArr);
            }
        }
        return new ArrayList<String>();
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
