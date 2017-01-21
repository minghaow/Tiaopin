package nanshen.web.controller.user;

import nanshen.dao.Question.AnswerDao;
import nanshen.data.Question.ComplexQuestion;
import nanshen.data.Sku.Sku;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.SystemUtil.PageType;
import nanshen.data.User.ComplexUserInfo;
import nanshen.data.User.UserInfo;
import nanshen.data.User.UserMessage;
import nanshen.service.AccountService;
import nanshen.service.PeopleService;
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
@RequestMapping("/p")
public class PeopleCtrl extends BaseCtrl {

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PeopleService peopleService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private SkuService skuService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView peoplePage(ModelMap model,
									 @RequestParam(defaultValue = "0", required = true) long qid,
									 @RequestParam(defaultValue = "0", required = false) long aid) throws UnsupportedEncodingException {
		if (qid <= 0) {
			return new ModelAndView("500");
		}
		ComplexQuestion complexQuestion;
		if (aid <= 0 ) {
			complexQuestion = questionService.getComplexQuestionByShowId(null, qid);
			Map<Long, List<Sku>> aidSkuListMap = skuService.getMapByQuestionId(complexQuestion.getId());
			model.addAttribute("answerPage", false);
			prepareHeaderModel(model, PageType.QUESTION);
			model.addAttribute("complexQuestion", complexQuestion);
			model.addAttribute("aidSkuListMap", aidSkuListMap);
			return new ModelAndView("user/question");
		} else {
			complexQuestion = questionService.getQuestionAndAnswerByShowId(qid, aid);
			model.addAttribute("answerPage", true);
			prepareHeaderModel(model, PageType.ANSWER);
			model.addAttribute("complexQuestion", complexQuestion);
			return new ModelAndView("user/answer");
		}
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public void peoplePage(HttpServletRequest request, ModelMap model, HttpServletResponse response,
						   	@RequestParam(defaultValue = "0", required = true) long userId,
						   	@RequestParam(defaultValue = "0", required = true) int page) throws IOException {
		UserInfo userInfo = getLoginedUser(request);
		ComplexUserInfo complexUserInfo = peopleService.getUserInfo(userInfo, userId, new PageInfo(page));
		model.addAttribute("complexUserInfo", complexUserInfo);
		responseJson(response, model);
	}

	@RequestMapping(value = "/l/json", method = RequestMethod.GET)
	public void subListPage(HttpServletRequest request, ModelMap model, HttpServletResponse response,
									 @RequestParam(defaultValue = "0", required = true) int page) throws IOException {
		UserInfo userInfo = getLoginedUser(request);
		List<UserInfo> userInfoList = peopleService.getSubList(userInfo, new PageInfo(page));
		model.addAttribute("userInfoList", userInfoList);
		responseJson(response, model);
	}

	@RequestMapping(value = "/sub", method = RequestMethod.GET)
	public void peopleSub(HttpServletRequest request, HttpServletResponse response,
						  @RequestParam(defaultValue = "1", required = true) long userId) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();
		UserInfo userInfo = getLoginedUser(request);
		ExecInfo execInfo = peopleService.subPeople(userId, userInfo);
		json.put("success", execInfo.isSucc());
		json.put("msg", execInfo.getMsg());
		responseJson(response, json);
	}

	@RequestMapping(value = "/sub/cancel", method = RequestMethod.GET)
	public void peopleSubCancel(HttpServletRequest request, HttpServletResponse response,
								@RequestParam(defaultValue = "1", required = true) long userId) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();
		UserInfo userInfo = getLoginedUser(request);
		ExecInfo execInfo = peopleService.subCancelPeople(userId, userInfo);
		json.put("success", execInfo.isSucc());
		json.put("msg", execInfo.getMsg());
		responseJson(response, json);
	}

	@RequestMapping(value = "/msg", method = RequestMethod.GET)
	public void msgPage(HttpServletRequest request, ModelMap model, HttpServletResponse response,
							@RequestParam(defaultValue = "0", required = true) int page) throws IOException {
		UserInfo userInfo = getLoginedUser(request);
		List<UserMessage> userMessageList = peopleService.getMsgList(userInfo, new PageInfo(page));
		model.addAttribute("userMessageList", userMessageList);
		responseJson(response, model);
	}

}