package nanshen.data.Sku.SkuAttri;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * SkuColorType
 *
 * @author WANG Minghao
 */
public enum SkuColorType {

    ALL("都可以"),

    RED("红色"),

    BLUE("蓝色"),

    WHITE("白色"),

    GREY("灰色"),

    YELLOW("黄色"),

    GREEN("绿色"),

    PURPLE("紫色"),

    MULTI("彩色"),

    UNKNOWN("未知颜色");

    private String desc;

    SkuColorType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static SkuColorType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToSkuColorType extends Castor<String, SkuCategoryOneType> {
        @Override
        public SkuCategoryOneType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return SkuCategoryOneType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToSkuColorType.class);
    }
}
