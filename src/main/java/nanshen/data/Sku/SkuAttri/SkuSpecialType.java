package nanshen.data.Sku.SkuAttri;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * SkuSpecialType
 *
 * @author WANG Minghao
 */
public enum SkuSpecialType {

    ALL("都可以"),

    QPRATE("性价比"),

    SQ("超品质"),

    STYLISH("有格调"),

    NEW_YEAR("新年礼"),

    UNKNOWN("未知种类");

    private String desc;

    SkuSpecialType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static SkuSpecialType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToSkuSpecialType extends Castor<String, SkuCategoryOneType> {
        @Override
        public SkuCategoryOneType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return SkuCategoryOneType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToSkuSpecialType.class);
    }
}
