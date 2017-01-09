package nanshen;

import nanshen.data.Question.AnswerCleanContent;
import nanshen.utils.LogUtils;
import org.junit.Assert;
import org.junit.Test;
import org.nutz.json.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppTests {

    @Test
    public void simple() throws Exception {

        String cleanContent = "预算300以内的进 土豪可直接忽略 \n" +
                "\n" +
                "————————我是可爱的分割线—————\n" +
                "\n" +
                "学生党一枚，没什么闲钱，买个钱包又不愿意买质量特别差的，或者特别没有设计感的……（求别打=_=）\n" +
                "\n" +
                "别和我说质量和品牌 1K+才能勉强聊牌子（我也知道C家B家好看啊！买不起！！） \n" +
                "\n" +
                "宗旨是—尽可能少的钱！！ 买不要太 low的货 就心满意足了 \n" +
                "\n" +
                "—————————以下正文————————\n" +
                "1.CHARLES&KEITH\n" +
                "小CK 材质PU\n" +
                "优点：价格亲民150-300软妹币/略微设计感\n" +
                "\n" +
                "<img src=\"http://static.itiaopin.com/images/answer/104/0.jpg\">\n" +
                "\n" +
                "价格200左右，我个人觉得无功无过，拿出来不至于寒酸，毕竟他们家就是作皮鞋皮包的，还有那么点设计感！\n" +
                "\n" +
                "2.Accessorize\n" +
                "价格200+为主 100-500不等\n" +
                "价格亲民，算是什么风格都有一点，有些也会比较花哨，但还是可以的\n" +
                "\n" +
                "<img src=\"http://static.itiaopin.com/images/answer/104/1.jpg\">\n" +
                "\n" +
                "这款 在某猫上面看到的 我自己比较喜欢的型啦\n" +
                "顺便放一张他们家的单肩包\n" +
                "\n" +
                "<img src=\"http://static.itiaopin.com/images/answer/104/2.jpg\">\n" +
                "\n" +
                "我自己很长草这种，200、300买一个Pu包 ，平时配配衣服，百搭。背三个季度不喜欢不要了也不心疼。。。。好像跑题了………\n" +
                "\n" +
                "3.top shop\n" +
                "快时尚,没办法，既要价格便宜又要有设计感，快时尚首选，并且top shop在快时尚里不算便宜的了\n" +
                "\n" +
                "<img src=\"http://static.itiaopin.com/images/answer/104/3.jpg\">\n" +
                "\n" +
                "无功无过 多找代购 \n" +
                "\n" +
                "4.newlook\n" +
                "让我在快时尚路上越走越远吧～～～～\n" +
                "没怎么关注过这个 上图吧\n" +
                "\n" +
                "<img src=\"http://static.itiaopin.com/images/answer/104/4.jpg\">\n" +
                "\n" +
                "官网货很少的样子……\n" +
                "\n" +
                "5.墙裂推river island\n" +
                "RI的看到都还不错，100-300+不等吧\n" +
                "\n" +
                "<img src=\"http://static.itiaopin.com/images/answer/104/5.jpg\">\n" +
                "<img src=\"http://static.itiaopin.com/images/answer/104/6.jpg\">\n" +
                "<img src=\"http://static.itiaopin.com/images/answer/104/7.jpg\">\n" +
                "\n" +
                "不小 可以当手拿包了 质量什么的不敢说 没买过，但是算平均分可以的了！\n" +
                "\n" +
                "—————————————————————\n" +
                "我觉得我算不上推荐 毕竟皮包这种东西 皮质很重要的，我大学第一个钱包是素人的（手工制造主打 真皮 价格普遍600-1000），真皮手感真的不一样，用到现在反倒越旧越好看。\n" +
                "但素，女生嘛，为了不同的衣服肯定需要不同的包包的，然后有追赶潮流，加上大部分喜新厌旧，在经济不允许的情况下，不太可能所有的包包都可以是牌子的，那就挑个你觉得性价比高的！\n" +
                "\n" +
                "有帮助 就点赞\n" +
                "\n" +
                "\n" +
                "__________________\n" +
                "知乎用户“无效假设”授权转载";
        cleanContent = " " + cleanContent + " ";
        List<AnswerCleanContent> answerCleanContents = new ArrayList<AnswerCleanContent>();
        Pattern p = Pattern.compile("<img src=\"(.+)\"/>");
        Matcher m = p.matcher(cleanContent);
        while (m.find()) {
            answerCleanContents.add(new AnswerCleanContent(m.group(1), ""));
        }
        cleanContent = m.replaceAll("<img>");
        String[] cleanContentArray = cleanContent.split("<img>");
        for (int i = 0; i < cleanContentArray.length - 1; i++) {
            AnswerCleanContent answerCleanContent = answerCleanContents.get(i);
            answerCleanContent.setText(cleanContentArray[i]);
        }
        answerCleanContents.add(new AnswerCleanContent("", cleanContentArray[cleanContentArray.length - 1]));

        LogUtils.info(Json.toJson(answerCleanContents));

        Assert.assertTrue(true);
    }
}
