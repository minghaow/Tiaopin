package nanshen.data.Sku.SkuAttri;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * SkuCategoryOneType
 *
 * @author WANG Minghao
 */
public enum SkuCategoryOneType {

    ALL("都可以"),

    EAT("吃的"),

    CLOTH("穿的"),

    ITEM("用的"),

    UNKNOWN("未知种类");

    private String desc;

    SkuCategoryOneType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static SkuCategoryOneType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToCategoryOneType extends Castor<String, SkuCategoryOneType> {
        @Override
        public SkuCategoryOneType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return SkuCategoryOneType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToCategoryOneType.class);
    }
}
