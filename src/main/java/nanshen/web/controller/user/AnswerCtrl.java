package nanshen.web.controller.user;

import nanshen.dao.Question.AnswerDao;
import nanshen.data.Question.Answer;
import nanshen.data.Question.ComplexAnswer;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.User.UserInfo;
import nanshen.service.QuestionService;
import nanshen.service.SkuService;
import nanshen.utils.JsonUtils;
import nanshen.utils.LogUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/a")
public class AnswerCtrl extends BaseCtrl {

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private SkuService skuService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView answerPage(ModelMap model,
									 @RequestParam(defaultValue = "0", required = true) long qid,
									 @RequestParam(defaultValue = "0", required = false) long aid) throws UnsupportedEncodingException {
		if (aid <= 0) {
			model.addAttribute("success", false);
			model.addAttribute("msg", "错误的aid");
		}
		ComplexAnswer complexAnswer  = questionService.getComplexAnswerByShowId(null, aid);
		model.addAttribute("complexAnswer", complexAnswer);
		return new ModelAndView("user/answer");
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public void answerJson(HttpServletRequest request, ModelMap model, HttpServletResponse response,
									 @RequestParam(defaultValue = "0", required = false) long aid) throws IOException {
		if (aid <= 0) {
			model.addAttribute("success", false);
			model.addAttribute("msg", "错误的aid");
		}
		UserInfo userInfo = getLoginedUser(request);
		ComplexAnswer complexAnswer  = questionService.getComplexAnswerByShowId(userInfo, aid);
		complexAnswer.getAnswer().setContent("");
		model.addAttribute("complexAnswer", complexAnswer);
		responseJson(response, model);
	}

	@RequestMapping(value = "/up", method = RequestMethod.GET)
	public void skuJson(HttpServletRequest request, HttpServletResponse response,
						@RequestParam(defaultValue = "1", required = true) long aid) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();
		UserInfo userInfo = getLoginedUser(request);
		ExecInfo execInfo = questionService.upAnswer(aid, userInfo);
		json.put("success", execInfo.isSucc());
		json.put("msg", execInfo.getMsg());
		responseJson(response, json);
	}

	/**
	 * 上传单品配图功能
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public void uploadImage(HttpServletRequest request, HttpServletResponse response, ModelMap model,
							@RequestParam(defaultValue = "1", required = true) long qid)
			throws IOException {
		UserInfo userInfo = getLoginedUser(request);
		ExecResult<Answer> execResult = questionService.createAnswer(qid, userInfo);
        model.addAttribute("success", execResult.isSucc());
        model.addAttribute("message", execResult.getMsg());
		if (execResult.isSucc()) {
			model.addAttribute("aShowId", execResult.getValue().getShowId());
			model.addAttribute("content", execResult.getValue().getCleanContent());
		}
		responseJson(response, model);
	}

	/**
	 * 上传答案配图功能
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public void uploadImage(MultipartHttpServletRequest request, HttpServletResponse response, ModelMap model,
							@RequestParam(defaultValue = "1", required = true) long aid,
							@RequestParam(defaultValue = "0.jpg", required = true) String name)
			throws IOException {
		MultipartFile file = request.getFile(name);
		LogUtils.info(JsonUtils.toJson(request.getFileMap()));
		ExecInfo execInfo = questionService.uploadImage(aid, name, file);
        model.addAttribute("success", execInfo.isSucc());
        model.addAttribute("message", execInfo.getMsg());
		responseJson(response, model);
	}

	/**
	 * 上传单品配图功能
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public void uploadImage(HttpServletRequest request, HttpServletResponse response, ModelMap model,
							@RequestParam(defaultValue = "0", required = true) long aid,
							@RequestParam(defaultValue = "", required = true) String content)
			throws IOException {
		UserInfo userInfo = getLoginedUser(request);
		content = StringEscapeUtils.unescapeHtml(content);
		ExecInfo execInfo = questionService.submitAnswer(aid, content, userInfo);
		model.addAttribute("success", execInfo.isSucc());
		model.addAttribute("message", execInfo.getMsg());
		responseJson(response, model);
	}

}