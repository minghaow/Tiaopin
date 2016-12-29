package nanshen.web.controller.user;

import nanshen.data.Dtree.DtreeQuestion;
import nanshen.data.Sku.Sku;
import nanshen.service.DtreeService;
import nanshen.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dtree")
public class DtreeCtrl extends BaseCtrl {

	@Autowired
	private SkuService skuService;

	@Autowired
	private DtreeService dtreeService;

//	@RequestMapping(value = "q", method = RequestMethod.GET)
//	public ModelAndView listPage(ModelMap model, @RequestParam(defaultValue = "1", required = true) long qid) {
//		prepareHeaderModel(model, PageType.DTREE);
//		return new ModelAndView("user/list");
//	}

	/**
	 * 2类问题交互
	 *
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/q", method = RequestMethod.GET)
	public void questionConversation(HttpServletResponse response,
									 @RequestParam(defaultValue = "1", required = true) long topicId,
									 @RequestParam(defaultValue = "1", required = true) long qid) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();
		DtreeQuestion dtreeQuestion = dtreeService.getNextDtreeQuestion(topicId, qid);
		json.put("q", dtreeQuestion);
		responseJson(response, json);
	}

	/**
	 * 2类问题交互
	 *
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/r", method = RequestMethod.GET)
	public void questionResult(HttpServletResponse response,
									 @RequestParam(defaultValue = "", required = true) String rs) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();
//		LogUtils.info(JsonUtils.toJson(rs));
		List<Sku> skuList = dtreeService.getResult(null);
		json.put("skuList", skuList);
		responseJson(response, json);
	}

}