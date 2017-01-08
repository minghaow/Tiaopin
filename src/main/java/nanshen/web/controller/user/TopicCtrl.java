package nanshen.web.controller.user;

import nanshen.data.Question.ComplexQuestion;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.SystemUtil.PageType;
import nanshen.data.Topic.Topic;
import nanshen.data.User.UserInfo;
import nanshen.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/t")
public class TopicCtrl extends BaseCtrl {

    @Autowired
    private TopicService topicService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView topicList(ModelMap model, @RequestParam(defaultValue = "1", required = true) int lookId) {
		prepareHeaderModel(model, PageType.TOPIC);
		return new ModelAndView("user/topicList");
	}

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    public void skuJson(HttpServletResponse response) throws IOException {
        Map<String, Object> json = new HashMap<String, Object>();
        List<Topic> topicList = topicService.getHotTopic();
        if (topicList == null || topicList.size() == 0) {
            json.put("success", false);
            json.put("msg", "错误的商品ID");
        } else {
            json.put("success", true);
        }
        json.put("hotTopicList", topicList);
        responseJson(response, json);
    }

    @RequestMapping(value = "/l/json", method = RequestMethod.GET)
    public void topicContentListJson(HttpServletResponse response, @RequestParam(defaultValue = "1", required = true) long topicId,
            @RequestParam(defaultValue = "1", required = true) int page) throws IOException {
        Map<String, Object> json = new HashMap<String, Object>();
        List<ComplexQuestion> questionList = topicService.getTopicQuestionList(topicId, new PageInfo(page));
        if (questionList == null || questionList.size() == 0) {
            json.put("success", false);
            json.put("msg", "错误的商品ID");
        } else {
            json.put("success", true);
        }
        json.put("questionList", questionList);
        responseJson(response, json);
    }

    @RequestMapping(value = "/sub", method = RequestMethod.GET)
    public void skuJson(HttpServletResponse response, @RequestParam(defaultValue = "1", required = true) long topicId) throws IOException {
        Map<String, Object> json = new HashMap<String, Object>();
        UserInfo userInfo = getLoginedUser();
        ExecInfo execInfo = topicService.subTopic(topicId, userInfo);
        json.put("success", execInfo.isSucc());
        json.put("msg", execInfo.getMsg());
        responseJson(response, json);
    }

}