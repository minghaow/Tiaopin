package nanshen.web.controller.user;

import nanshen.dao.Question.AnswerDao;
import nanshen.data.Question.ComplexQuestion;
import nanshen.data.Sku.Sku;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.PageInfo;
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
@RequestMapping("/q")
public class QuestionCtrl extends BaseCtrl {

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private SkuService skuService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView questionPage(HttpServletRequest request, ModelMap model,
									 @RequestParam(defaultValue = "0", required = true) long qid) throws UnsupportedEncodingException {
		if (qid <= 0) {
			return new ModelAndView("500");
		}
		ComplexQuestion complexQuestion;
		UserInfo userInfo = getLoginedUser(request);
		complexQuestion = questionService.getComplexQuestionByShowId(userInfo, qid);
		Map<Long, List<Sku>> aidSkuListMap = skuService.getMapByQuestionId(complexQuestion.getId());
		model.addAttribute("answerPage", false);
		model.addAttribute("complexQuestion", complexQuestion);
		model.addAttribute("aidSkuListMap", aidSkuListMap);
		return new ModelAndView("user/question");
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public void questionJson(HttpServletRequest request, ModelMap model, HttpServletResponse response,
									 @RequestParam(defaultValue = "0", required = true) long qid) throws IOException {
		if (qid <= 0) {
			model.addAttribute("success", false);
			model.addAttribute("msg", "错误的qid");
		}
		ComplexQuestion complexQuestion;
		UserInfo userInfo = getLoginedUser(request);
		complexQuestion = questionService.getComplexQuestionByShowId(userInfo, qid);
		Map<Long, List<Sku>> aidSkuListMap = skuService.getMapByQuestionId(complexQuestion.getId());
		model.addAttribute("answerPage", false);
		model.addAttribute("complexQuestion", complexQuestion);
		model.addAttribute("aidSkuListMap", aidSkuListMap);
		responseJson(response, model);
	}

	@RequestMapping(value = "/sub/l", method = RequestMethod.GET)
	public void questionJson(HttpServletRequest request, ModelMap model, HttpServletResponse response,
							 @RequestParam(defaultValue = "1", required = true) int page) throws IOException {
		UserInfo userInfo = getLoginedUser(request);
		List<ComplexQuestion> complexQuestionList = questionService.getSubList(userInfo, new PageInfo(page));
		model.addAttribute("complexQuestionList", complexQuestionList);
		responseJson(response, model);
	}

	@RequestMapping(value = "/sub", method = RequestMethod.GET)
	public void skuJson(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "1", required = true) long qid) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();
		UserInfo userInfo = getLoginedUser(request);
		ExecInfo execInfo = questionService.subByQid(qid, userInfo);
		json.put("success", execInfo.isSucc());
		json.put("msg", execInfo.getMsg());
		responseJson(response, json);
	}

	@RequestMapping(value = "/sub/cancel", method = RequestMethod.GET)
	public void subCancel(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "1", required = true) long qid) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();
		UserInfo userInfo = getLoginedUser(request);
		ExecInfo execInfo = questionService.subCancelByQid(qid, userInfo);
		json.put("success", execInfo.isSucc());
		json.put("msg", execInfo.getMsg());
		responseJson(response, json);
	}

}