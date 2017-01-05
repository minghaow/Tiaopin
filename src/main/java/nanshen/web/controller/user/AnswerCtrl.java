package nanshen.web.controller.user;

import nanshen.dao.Question.AnswerDao;
import nanshen.data.Question.ComplexAnswer;
import nanshen.service.QuestionService;
import nanshen.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
		ComplexAnswer complexAnswer  = questionService.getComplexAnswerByShowId(aid);
		model.addAttribute("complexAnswer", complexAnswer);
		return new ModelAndView("user/answer");
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public void answerJson(ModelMap model, HttpServletResponse response,
									 @RequestParam(defaultValue = "0", required = false) long aid) throws IOException {
		if (aid <= 0) {
			model.addAttribute("success", false);
			model.addAttribute("msg", "错误的aid");
		}
		ComplexAnswer complexAnswer  = questionService.getComplexAnswerByShowId(aid);
		complexAnswer.getAnswer().setContent("");
		model.addAttribute("complexAnswer", complexAnswer);
		responseJson(response, model);
	}

}