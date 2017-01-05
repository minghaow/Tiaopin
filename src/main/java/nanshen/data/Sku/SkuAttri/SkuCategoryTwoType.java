package nanshen.data.Sku.SkuAttri;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * SkuCategoryTwoType
 *
 * @author WANG Minghao
 */
public enum SkuCategoryTwoType {

    ALL("都可以"),

    HEALTH("保健品"),

    DECO("装饰品"),

    ACCS("配饰"),

    CUP("杯子"),

    UNKNOWN("未知种类");

    private String desc;

    SkuCategoryTwoType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static SkuCategoryTwoType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToSkuCategoryTwoType extends Castor<String, SkuCategoryOneType> {
        @Override
        public SkuCategoryOneType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return SkuCategoryOneType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToSkuCategoryTwoType.class);
    }
}
