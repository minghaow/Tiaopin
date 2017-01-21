package nanshen.web.controller.user;

import nanshen.data.Banner.Banner;
import nanshen.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/banner")
public class BannerCtrl extends BaseCtrl {

	@Autowired
	private BannerService bannerService;

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public void peoplePage(HttpServletRequest request, ModelMap model, HttpServletResponse response) throws IOException {
		List<Banner> bannerList = bannerService.getOnlineBannerList();
		model.addAttribute("bannerList", bannerList);
		responseJson(response, model);
	}

}