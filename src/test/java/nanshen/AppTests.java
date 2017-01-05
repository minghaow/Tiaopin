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

        String cleanContent = "当你结束一天辛勤劳累的工作回到家中，是想要面对一个干净整洁温馨的房间，还是布满灰尘堆满了脏衣服臭袜子的一片狼藉呢？\n" +
                "面对这些，你需要的仅仅是一台扫地机器人，从此你不用再面对每周两次每次半小时的扫地时间，从此她将解放你的双手，还你一片清净，和一个步入家门后净洁美好的心情。\n" +
                "这里，送你一份扫地机器人选购指南，你想知道的都在这里。\n" +
                "<img src=\"http://image-cdn.zaitaoyuan.com/images/tiaopin/answer/30/0.jpg\"/>\n" +
                "\n" +
                "本文主要包含了：\n" +
                "1. 扫地机器人的选购标准\n" +
                "2. 各价格范围内挑品精选推荐\n" +
                "选购标准\n" +
                "扫地机器人在进入中国市场之初，价格高昂，接受程度低，但经过短短几年发展，国内市场逐步打开，成为智能家电中渗透率最高的电器之一，也成为我们提高生活品质必不可少的电器之一。\n" +
                "面对越来越多的品牌和型号，我们认为在选购扫地机器人时，我们需要注意的选购标准主要有以下几点：\n" +
                "-智能程度：目前扫地机器人的行程规划主要有两种形式：碰撞式和规划式。碰撞式即扫地机器人遇到障碍物后会进行无规则的随机碰撞，向其他方向继续行进。规划式目前主流为激光测距导航系统，扫地机器人会先进行激光测距，在绘制好房间地图，规划好最优清洁路线后再进行打扫。规划式的智能程度要比碰撞式高。\n" +
                "-是否能打扫多个房间：与一个房间不同，多个房间就会有门。碰撞式的扫地机器人只有在随机行走到门的时候才会进入到下一个房间，并且无法完成自动回充，因为她依然无法准确找到门。因此，只有配备了灯塔的碰撞型扫地机器人才能打扫多个房间，灯塔能够准确告知扫地机器人门的位置。相比之下，规划式的扫地机器人可在绘制好房间地图后完成多房间有规划的打扫。\n" +
                "-清洁力：目前市场上常见的扫地机器人都能完成基本的清扫工作，如有特殊需求可在选购时着重注意。\n" +
                "-其他可参考：\n" +
                "·虚拟墙：可阻隔不想让扫地机器人打扫的区域；\n" +
                "·防跌落：不用担心摔下楼梯；\n" +
                "·续航：扫地机器人每次打扫的最长时间；\n" +
                "·静音度：扫地机器人产生的噪音。\n" +
                "·预约定时：可在无人时段进行清扫，完全消除噪音困扰。\n" +
                "\n" +
                "各价格范围内精品推荐：\n" +
                "2000元以下\n" +
                "<img src=\"http://image-cdn.zaitaoyuan.com/images/tiaopin/answer/30/1\"/>\n" +
                "\n" +
                "小米的产品向来以超高的性价比著称。这款扫地机器人整体设计简洁，使用了规划式的清扫路径，大风压电机，大容量锂电池，支持虚拟墙系统，适合注重性价比或想体验规划式扫地机的入门人群。\n" +
                "2000-3000元\n" +
                "<img src=\"http://image-cdn.zaitaoyuan.com/images/tiaopin/answer/30/2\"/>\n" +
                "\n" +
                "作为军工起家的品牌，iRobot的品质一直所被人称赞。iRobot作为中端机的入门机型，清洁能力非常强，拆洗也非常方便，并支持虚拟墙系统。但由于碰撞式的工作原理且不支持灯塔系统，因此对多房间的清理效果并不是太好，比较适合单房间使用。\n" +
                "3000元以上\n" +
                "<img src=\"http://image-cdn.zaitaoyuan.com/images/tiaopin/answer/30/3\"/>\n" +
                "Roomba 880非常有设计感，碰撞式工作原理可搭配灯塔系统，配置了空气动力学清洁系统，清洁效果一流。\n" +
                "\n" +
                "<img src=\"http://image-cdn.zaitaoyuan.com/images/tiaopin/answer/30/4\"/>\n" +
                "规划式扫地机中高端入门款，使用谷歌无人驾驶汽车专利，路线规划效率非常高，清扫效果页非常好 。支持虚拟墙系统。\n" +
                "\n" +
                "注：部分价格不同渠道差别较大，本文中商品推荐及价格仅供参考。\n" +
                "本文首发于公众号「挑品精选」\n" +
                "挑品，品质购物问答社区。\n" +
                "\n";
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
