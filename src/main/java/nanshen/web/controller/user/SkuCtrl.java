package nanshen.web.controller.user;

import nanshen.dao.Question.AnswerDao;
import nanshen.data.Question.ComplexAnswer;
import nanshen.data.Sku.Sku;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.SystemUtil.PageType;
import nanshen.data.User.UserInfo;
import nanshen.service.QuestionService;
import nanshen.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/s")
public class SkuCtrl extends BaseCtrl {

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private SkuService skuService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView skuPage(ModelMap model,
								@RequestParam(defaultValue = "0", required = true) long sid) throws UnsupportedEncodingException {
		if (sid <= 0) {
			return new ModelAndView("500");
		}
		Sku sku = skuService.getByShowSid(sid);
		List<ComplexAnswer> answerList = skuService.getAnswersBySid(sku.getId());
		prepareHeaderModel(model, PageType.SKU);
		model.addAttribute("sku", sku);
		model.addAttribute("answerList", answerList);
		return new ModelAndView("user/sku");
	}

	@RequestMapping(value = "/like", method = RequestMethod.GET)
	public void skuLike(HttpServletRequest request, HttpServletResponse response,
						@RequestParam(defaultValue = "0", required = true) long sid) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();
		UserInfo userInfo = getLoginedUser(request);
		if (sid <= 0) {
			json.put("success", false);
			json.put("msg", "错误的商品ID");
		} else {
			json.put("success", skuService.likeBySid(sid, userInfo));
		}
		responseJson(response, json);
	}

	@RequestMapping(value = "/like/l", method = RequestMethod.GET)
	public void skuLike(HttpServletRequest request, HttpServletResponse response,
						@RequestParam(defaultValue = "0", required = true) int page) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();
		UserInfo userInfo = getLoginedUser(request);
		List<Sku> skuList = skuService.getLikeList(userInfo, new PageInfo(page));
		json.put("success", skuList != null);
		json.put("skuList", skuList);
		responseJson(response, json);
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public void skuJson(HttpServletResponse response,
							   @RequestParam(defaultValue = "0", required = true) long sid) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();
		if (sid <= 0) {
			json.put("sku", null);
			json.put("success", false);
			json.put("msg", "错误的商品ID");
		} else {
			Sku sku = skuService.getBySid(sid);
			json.put("sku", sku);
			if (sku != null) {
				List<ComplexAnswer> answerList = skuService.getAnswersBySid(sku.getId());
				json.put("answerList", answerList);
			}
		}
		responseJson(response, json);
	}
}