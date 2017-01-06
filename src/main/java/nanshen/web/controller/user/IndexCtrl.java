package nanshen.web.controller.user;

import nanshen.dao.Question.AnswerDao;
import nanshen.dao.Question.QuestionDao;
import nanshen.dao.SkuDao;
import nanshen.dao.SkuSourceDao;
import nanshen.dao.UserInfoDao;
import nanshen.data.Question.ComplexAnswer;
import nanshen.data.Question.ComplexQuestion;
import nanshen.data.Question.QuestionType;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.SystemUtil.PageType;
import nanshen.service.AnswerService;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexCtrl extends BaseCtrl {

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private SkuDao skuDao;

	@Autowired
	private SkuSourceDao skuSourceDao;

	@Autowired
	private SkuService skuService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView homePage(ModelMap model,
								 @RequestParam(defaultValue = "", required = true) QuestionType type,
								 @RequestParam(defaultValue = "1", required = true) int page) {
		List<QuestionType> typeList = Arrays.asList(QuestionType.values());
//		questionDao.insert(new Question("如何选购扫地机器人？", "当你结束一天辛勤劳累的工作回到家中，是想要面对一个干净整洁温馨的房间，还是布满灰尘堆满了脏衣服臭袜子的一片狼藉呢？",
//				QuestionStatus.ONLINE, QuestionType.SMART_HOME, 1));
//		userInfoDao.addNewUser(new UserInfo("13811448892", EncryptUtils.encodePassword("1")));
//		answerDao.insert(new Answer(1, "", AnswerStatus.ONLINE, 1));
//		Sku sku = skuDao.insert(new Sku("小米扫地机器人", "米家智能家居新款上市", 1999, "智能家居", "https://detail.tmall.com/item.htm?spm=a230r.1.14.6.5fSC72&id=538932602256&cm_id=140105335569ed55e27b&abbucket=4&skuId=3220665053591"));
//		skuSourceDao.insert(new SkuSource(30, 1, sku.getId()));
		if (type != null) {
			typeList = Collections.singletonList(type);
		}
		List<ComplexQuestion> questionList = questionService.getHotQuestions(typeList, new PageInfo(page));
//		List<ComplexQuestion> questionList = answerService.getHotAnswers(typeList, new PageInfo(page));
		prepareHeaderModel(model, PageType.ITEM_LIST);
		model.addAttribute("questionList", questionList);
		return new ModelAndView("user/list");
	}

	@RequestMapping(value = "/l/json", method = RequestMethod.GET)
	public void questionJson(ModelMap model, HttpServletResponse response,
							 @RequestParam(defaultValue = "1", required = true) int page) throws IOException {
		List<ComplexAnswer> answerList = questionService.getHotAnswers(new PageInfo(page));
		model.addAttribute("alist", answerList);
		model.addAttribute("size", answerList.size());
		responseJson(response, model);
	}

}