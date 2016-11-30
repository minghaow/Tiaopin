package nanshen.web.controller.user;

import nanshen.dao.Question.AnswerDao;
import nanshen.data.Question.ComplexQuestion;
import nanshen.data.Sku.Sku;
import nanshen.data.SystemUtil.PageType;
import nanshen.service.QuestionService;
import nanshen.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
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
	public ModelAndView questionPage(ModelMap model,
									 @RequestParam(defaultValue = "0", required = true) long qid,
									 @RequestParam(defaultValue = "0", required = false) long aid) throws UnsupportedEncodingException {
		if (qid <= 0) {
			return new ModelAndView("500");
		}
		ComplexQuestion complexQuestion;
		if (aid <= 0 ) {
			complexQuestion = questionService.getComplexQuestionByShowId(qid);
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

}