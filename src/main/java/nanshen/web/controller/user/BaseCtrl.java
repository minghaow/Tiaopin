package nanshen.web.controller.user;

import nanshen.constant.SystemConstants;
import nanshen.data.Cart.Cart;
import nanshen.data.SystemUtil.PageType;
import nanshen.data.User.UserInfo;
import nanshen.service.AccountService;
import nanshen.service.CartService;
import nanshen.utils.JsonUtils;
import nanshen.utils.RequestUtils;
import nanshen.utils.ViewUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Controller的基类，定义了Controller使用的基本方法，所有Controller都应该继承该基类
 *
 * @author WANG Minghao
 */
public abstract class BaseCtrl {

	class StringEscapeEditor extends PropertyEditorSupport {

		@Override
		public void setAsText(String text) {
			if (text != null) {
				text = html(text);
			}
			setValue(text);
		}

		/**
		 * 该方法必须重载，否则在Java6环境下会出问题
		 *
		 * @return
		 */
		@Override
		public String getAsText() {
			return (String) getValue();
		}

	}

	@Autowired
	protected AccountService accountService;

	@Autowired
	protected CartService cartService;

	/**
	 * 默认对所有的输入字符串进行过滤，防止XSS攻击
	 * <p />
	 * 输入的字符串变量不需要使用{@link #html(String)}方法过滤了
	 *
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringEscapeEditor());
	}

	/**
	 * 将html文件中直接展示的文本进行转换，以预防XSS漏洞。
	 * <p>
	 * 若str变量要在html中直接展示，如使用&lt;span>$!str&lt;/span>的模板代码， 则应该对str使用此函数进行过滤。
	 * </p>
	 *
	 * @param display
	 *            待展示的字符串
	 * @return
	 */
	protected String html(String display) {
		return ViewUtils.getHTMLValidText(display);
	}

	/**
	 * 获取当前登录的用户的信息
	 *
	 * @return 用户信息，若未登录返回null
	 */
	protected UserInfo getLoginedUser() {
		if (RequestUtils.isLogined()) {
			long userId = RequestUtils.loginedUserInfo();
			return accountService.getUserInfo(userId);
		}
		return null;
	}

	/**
	 * 获取当前登录的用户的信息
	 *
	 * @return 用户信息，若未登录返回null
	 */
	protected UserInfo getLoginedUser(HttpServletRequest request) {
		Map<String,String> params = getRequestParams(request);
		String tpuidString = params.get("tpuid");
		String uidString = params.get("uid");
		if (StringUtils.isBlank(tpuidString) || StringUtils.isBlank(uidString)) {
			return null;
		}
		long tpuid = Long.parseLong(params.get("tpuid"));
		long uid = Long.parseLong(params.get("uid"));
		UserInfo userInfo = accountService.getUserInfo(uid);
//		if (userInfo != null && userInfo.getTempLoginId() == tpuid) {
//			return userInfo;
//		}
		if (userInfo != null) {
			return userInfo;
		}
		return null;
	}

	/**
	 * 获取当前登录的用户的购物车信息
	 *
	 * @return 用户信息，若未登录返回null
	 */
	protected Cart getCartInfo() {
		if (RequestUtils.isLogined()) {
			long userId = RequestUtils.loginedUserInfo();
			if (userId == 0) {
				return null;
			}
			return cartService.getByUserId(userId);
		}
		return null;
	}

	/**
	 * print request ip address
	 *
	 * @param request
	 * @return
	 */
	protected String getRequestIp(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	/**
	 * print json output
	 *
	 * @param response
	 * @param object
	 * @throws IOException
	 */
	protected void responseJson(HttpServletResponse response, Object object) throws IOException {
		response.setContentType("application/json;charset=" + SystemConstants.SYS_ENC);
		response.getWriter().print(JsonUtils.toJson(object));
	}

	/**
	 * print image output
	 *
	 * @param response
	 * @param imageData
	 * @throws IOException
	 */
	protected void responseImageData(HttpServletResponse response, byte[] imageData) throws IOException {
		response.setContentType("image/jpeg");
		response.getOutputStream().write(imageData);
	}

	/**
	 * print jsonp output
	 *
	 * @param response
	 * @param object
	 * @throws IOException
	 */
	protected void responseJsonp(HttpServletResponse response, String jsonp, Object object) throws IOException {
		response.setContentType("text/javascript;charset=" + SystemConstants.SYS_ENC);
		response.getWriter().print(JsonUtils.toJsonP(jsonp, object));
	}

	public Map<String,String> getRequestParams(HttpServletRequest request) {
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		return params;
	}

	protected void prepareHeaderModel(ModelMap model, PageType pageType) {
		for (PageType type : PageType.values()) {
			model.addAttribute(type.name(), pageType == type && type.isNeedShow() ? "selected" : "");
		}
		model.addAttribute("pageType", pageType);
		model.addAttribute("userInfo", getLoginedUser());
		model.addAttribute("cart", getCartInfo());
		model.addAttribute("imageUrlPrefix", "http://static.itiaopin.com");
//		model.addAttribute("imageUrlPrefix", "");
		model.addAttribute("cssUrlPrefix", "http://static.itiaopin.com");
//		model.addAttribute("cssUrlPrefix", "");
	}
}