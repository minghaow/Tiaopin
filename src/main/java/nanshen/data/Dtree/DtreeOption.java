package nanshen.data.Dtree;

import nanshen.data.Sku.SkuAttri.*;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * DtreeQuestion
 *
 * @Author WANG Minghao
 */
@Table("DtreeOption")
public class DtreeOption {

    /** ID */
    @Id
    private long id;

    /** topicId */
    @Column
    private long topicId = 0;

    /** qid */
    @Column
    private long qid = 0;

    /** question level */
    @Column
    private long level = 0;

    /** nextQid */
    @Column
    private long nextQid = 0;

    /** content */
    @Column
    private String content;

    /** skuType */
    @Column
    private String skuType;

    /** isImg */
    @Column
    private DtreeOptionType type = DtreeOptionType.UNKNOWN;

    @Column
    private SkuCategoryOneType categoryOneType = null;

    @Column
    private SkuCategoryTwoType categoryTwoType = null;

    @Column
    private SkuColorType colorType = null;

    @Column
    private SkuMaterialType materialType = null;

    @Column
    private SkuSituationType situationType = null;

    @Column
    private SkuSpecialType specialType = null;

    @Column
    private SkuStyleType styleType = null;

    @Column
    private SkuUserType userType = null;

    @Column
    private Long lowerPriceRange = null;

    @Column
    private Long higherPriceRange = null;

    public DtreeOption() {
    }

    public DtreeOption(String content, long qid, DtreeOptionType type, long topicId, long nextQid) {
        this.content = content;
        this.qid = qid;
        this.topicId = topicId;
        this.type = type;
        this.nextQid = nextQid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQid() {
        return qid;
    }

    public void setQid(long qid) {
        this.qid = qid;
    }

    public DtreeOptionType getType() {
        return type;
    }

    public void setType(DtreeOptionType type) {
        this.type = type;
    }

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public long getNextQid() {
        return nextQid;
    }

    public void setNextQid(long nextQid) {
        this.nextQid = nextQid;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
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

    public Long getHigherPriceRange() {
        return higherPriceRange;
    }

    public void setHigherPriceRange(Long higherPriceRange) {
        this.higherPriceRange = higherPriceRange;
    }

    public Long getLowerPriceRange() {
        return lowerPriceRange;
    }

    public void setLowerPriceRange(Long lowerPriceRange) {
        this.lowerPriceRange = lowerPriceRange;
    }
}
