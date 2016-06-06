package nanshen.web.controller.user;

import nanshen.data.ExecResult;
import nanshen.data.Order;
import nanshen.data.PageType;
import nanshen.data.UserInfo;
import nanshen.service.CartService;
import nanshen.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/cart")
public class CartCtrl extends BaseCtrl {

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView cart(ModelMap model) {
		prepareHeaderModel(model, PageType.ITEM_DETAIL);
		return new ModelAndView("user/cart");
	}

	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	public void addToCart(HttpServletResponse response, ModelMap model,
						  @RequestParam(defaultValue = "1", required = true) long skuId,
						  @RequestParam(defaultValue = "1", required = true) long count)
			throws IOException {
		UserInfo userInfo = getLoginedUser();
		ExecResult<Long> execResult = ExecResult.fail("请登陆后再加入购物车，谢谢！");
		if (userInfo != null) {
			execResult= cartService.addSku(userInfo.getId(), skuId, count);
		}
		model.put("success", execResult.isSucc());
		model.put("message", execResult.getMsg());
		model.put("cnt", execResult.getValue());
		prepareHeaderModel(model, PageType.ITEM_DETAIL);
		responseJson(response, model);
	}

	@RequestMapping(value = "/goods/minus", method = RequestMethod.POST)
	public void minusCount(HttpServletResponse response, ModelMap model,
						  @RequestParam(defaultValue = "1", required = true) long goodsId)
			throws IOException {
		UserInfo userInfo = getLoginedUser();
		ExecResult<Long> execResult = ExecResult.fail("请登陆后再修改商品数量，谢谢！");
		if (userInfo != null) {
			execResult= cartService.minusSkuCount(userInfo.getId(), goodsId);
		}
		outputCountChangeResponse(response, model, execResult);
	}

	@RequestMapping(value = "/goods/add", method = RequestMethod.POST)
	public void addCount(HttpServletResponse response, ModelMap model,
					  @RequestParam(defaultValue = "1", required = true) long goodsId)
			throws IOException {
		UserInfo userInfo = getLoginedUser();
		ExecResult<Long> execResult = ExecResult.fail("请登陆后再修改商品数量，谢谢！");
		if (userInfo != null) {
			execResult= cartService.addSkuCount(userInfo.getId(), goodsId);
		}
		outputCountChangeResponse(response, model, execResult);
	}

	@RequestMapping(value = "/goods/setCount", method = RequestMethod.POST)
	public void setCount(HttpServletResponse response, ModelMap model,
					@RequestParam(defaultValue = "1", required = true) long skuId,
					@RequestParam(defaultValue = "1", required = true) long count)
			throws IOException {
		UserInfo userInfo = getLoginedUser();
		ExecResult<Long> execResult = ExecResult.fail("请登陆后再修改商品数量，谢谢！");
		if (userInfo != null) {
			execResult= cartService.setSkuCount(userInfo.getId(), skuId, count);
		}
		outputCountChangeResponse(response, model, execResult);
	}

	@RequestMapping(value = "/goods/del", method = RequestMethod.POST)
	public void delete(HttpServletResponse response, ModelMap model,
						 @RequestParam(defaultValue = "1", required = true) long skuId)
			throws IOException {
		UserInfo userInfo = getLoginedUser();
		ExecResult<Long> execResult = ExecResult.fail("请登陆后再修改商品数量，谢谢！");
		if (userInfo != null) {
			execResult= cartService.deleteGoods(userInfo.getId(), skuId);
		}
		outputCountChangeResponse(response, model, execResult);
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public void submit(HttpServletResponse response, ModelMap model,
						  @RequestParam(defaultValue = "", required = true) String goodsIdList)
			throws IOException {
		UserInfo userInfo = getLoginedUser();
		ExecResult<Long> execResult = ExecResult.fail("请登陆，谢谢！");
		if (userInfo != null) {
			Order order = orderService.createOrder(userInfo.getId(), goodsIdList);
			if (order != null) {
				execResult = ExecResult.succ(order.getOrderId());
				model.put("orderId", order.getOrderId());
			} else {
				execResult = ExecResult.fail("添加购物车失败");
			}
		}
		model.put("success", execResult.isSucc());
		model.put("message", execResult.getMsg());
		prepareHeaderModel(model, PageType.ITEM_DETAIL);
		responseJson(response, model);
	}

	private void outputCountChangeResponse(HttpServletResponse response, ModelMap model, ExecResult<Long> execResult) throws IOException {
		model.put("success", execResult.isSucc());
		model.put("message", execResult.getMsg());
		model.put("cnt", execResult.getValue());
		responseJson(response, model);
	}

}