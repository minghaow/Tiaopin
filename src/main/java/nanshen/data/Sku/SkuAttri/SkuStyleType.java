package nanshen.data.Sku.SkuAttri;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * SkuStyleType
 *
 * @author WANG Minghao
 */
public enum SkuStyleType {

    ALL("都可以"),

    NORTH_EURO("北欧"),

    CHINA("中式"),

    JAPAN("日式"),

    DESEX("性冷淡"),

    INDUSTRY("工业"),

    CUTE("可爱"),

    TECHNOLOGY("现代科技"),

    UNKNOWN("未知种类");

    private String desc = "<text>难道不明白纸质书更贵啊！！！\n" +
            "\n" +
            "            若觉得kindle更贵，我觉得要么阅读量太少，那确实没有买kindle的必要。要么买的都是盗版的纸质书？我不清楚不加以评论。。。\n" +
            "\n" +
            "            另外，用kindle看小说的怎么真心不懂了。题主不看小说么？难道题主拿来看教科书还是技术文档？还是题主觉得小说就是小时代内样的？（对小时代没偏见，尊重多样性）\n" +
            "\n" +
            "            而且纸质书搬起来真心困难啊！当初毕业带不回来，忍痛卖了不少好桑心！\n" +
            "\n" +
            "            碎片时间阅读总不能天天背着一本书吧，那么占地方。\n" +
            "\n" +
            "            看到描述最后一段，感觉有骗答案的嫌疑\n" +
            "\n" +
            "        </text>\n" +
            "        <image src=\"../../images/1444983318907-_DSC1826.jpg\"></image>\n" +
            "        <text>\n" +
            "            难道不明白纸质书更贵啊！！！\n" +
            "\n" +
            "            若觉得kindle更贵，我觉得要么阅读量太少，那确实没有买kindle的必要。要么买的都是盗版的纸质书？我不清楚不加以评论。。。\n" +
            "\n" +
            "            另外，用kindle看小说的怎么真心不懂了。题主不看小说么？难道题主拿来看教科书还是技术文档？还是题主觉得小说就是小时代内样的？（对小时代没偏见，尊重多样性）\n" +
            "\n" +
            "            而且纸质书搬起来真心困难啊！当初毕业带不回来，忍痛卖了不少好桑心！\n" +
            "\n" +
            "            碎片时间阅读总不能天天背着一本书吧，那么占地方。\n" +
            "\n" +
            "            看到描述最后一段，感觉有骗答案的嫌疑\n" +
            "\n" +
            "        </text>\n" +
            "        <image src=\"../../images/1444983318907-_DSC1826.jpg\"></image>";

    SkuStyleType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static SkuStyleType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToSkuStyleType extends Castor<String, SkuCategoryOneType> {
        @Override
        public SkuCategoryOneType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return SkuCategoryOneType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToSkuStyleType.class);
    }
}
