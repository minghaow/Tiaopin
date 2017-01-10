package nanshen;

import nanshen.data.Question.AnswerCleanContent;
import nanshen.utils.LogUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Assert;
import org.junit.Test;
import org.nutz.json.Json;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppTests {

    @Test
    public void simple() throws Exception {

        String cleanContent = "ad&#39;s&#39;fad&#39;s&#39;fa&#39;d&#39;s&#39;f大师法第三方adfa&#39;d&#39;fa&#39;d&#39;f爱的发的发adfad&#39;fa&#39;d&#39;s&#39;f打发第三方wang&#39;ming汪明浩汪mingming&#39;ha明浩a&#39;da&#39;d&#39;fa&#39;d&#39;fadfadfad&#39;s&#39;fddfa\n" +
                "\n" +
                " &lt;img src=&#39;wxfile://tmp_812682210o6zAJs4DV4A8mxI3Uudbw7cDOk9k1484066911093.png&#39;/&gt;\n" +
                "\n" +
                "a&#39;d爱的发的是adfa&#39;d&#39;fa&#39;d&#39;faa\n" +
                "\n" +
                " &lt;img src=&#39;wxfile://tmp_812682210o6zAJs4DV4A8mxI3Uudbw7cDOk9k1484066926359.jpg&#39;/&gt;\n" +
                "\n" +
                "a&#39;s阿萨德发的";
        cleanContent = " " + cleanContent + " ";
        cleanContent = URLDecoder.decode(cleanContent, "UTF-8");
        cleanContent = StringEscapeUtils.unescapeHtml(cleanContent);
        LogUtils.info("++++++++" + cleanContent.substring(0, cleanContent.length() < 100 ? cleanContent.length() : 100));
        List<AnswerCleanContent> answerCleanContents = new ArrayList<AnswerCleanContent>();
        Pattern p = Pattern.compile("<img src=\"(.+)\"/>");
        Matcher m = p.matcher(cleanContent);
        while (m.find()) {
            answerCleanContents.add(new AnswerCleanContent(m.group(1), ""));
        }
        cleanContent = m.replaceAll("");
        cleanContent = cleanContent.replaceAll("\t", "");
        cleanContent = cleanContent.replaceAll("\n", "");
        LogUtils.info("++++++++" + cleanContent.substring(0, cleanContent.length() < 100 ? cleanContent.length() : 100));
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
