package nanshen.web.controller.user;

import nanshen.constant.SystemConstants;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.LoginError;
import nanshen.data.SystemUtil.PageType;
import nanshen.data.User.UserInfo;
import nanshen.data.Weixin.WeixinSessionResponse;
import nanshen.service.AccountService;
import nanshen.utils.HttpUtils;
import nanshen.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证相关页面
 *
 * @author WANG Minghao
 */
@Controller
@RequestMapping("/auth")
public class AuthorityCtrl extends BaseCtrl {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/login_page")
    public String login(ModelMap model, LoginError error) {
        prepareHeaderModel(model, PageType.LOGIN_PAGE);
        model.put("error", error);
        return "user/login";
    }

    @RequestMapping("/logout")
    public String logout(ModelMap model, @RequestParam(defaultValue = "false") boolean error) {
        model.put("error", error);
        return "user/login";
    }

    @RequestMapping("/deny")
    public String deny() {
        return "user/deny";
    }

    /**
     * Login successfully
     *
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public void loginSuccess(HttpServletResponse response, ModelMap model,
                             @RequestParam(defaultValue = "") String uid,
                             @RequestParam(defaultValue = "") long tpuid)
            throws IOException {
        model.put("success", "true");
        model.put("uid", uid);
        model.put("tpuid", tpuid);
        responseJson(response, model);
    }

    /**
     * Login successfully
     *
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/success", method = RequestMethod.POST)
    public void loginSuccessPOST(HttpServletResponse response, ModelMap model,
                             @RequestParam(defaultValue = "") String uid,
                             @RequestParam(defaultValue = "") long tpuid)
            throws IOException {
        model.put("success", "true");
        model.put("uid", uid);
        model.put("tpuid", tpuid);
        responseJson(response, model);
    }

    /**
     * Login failed
     *
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/fail", method = RequestMethod.GET)
    public void loginFail(HttpServletResponse response, ModelMap model)
            throws IOException {
        model.put("success", "false");
        responseJson(response, model);
    }

    /**
     * Is phone not registered?
     *
     * @param response
     * @param model
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public void isNotRegister(HttpServletResponse response, ModelMap model,
                              @RequestParam(defaultValue = "") String openid,
                              @RequestParam(defaultValue = "") String imgUrl,
                              @RequestParam(defaultValue = "") String gender,
                              @RequestParam(defaultValue = "") String nickName,
                              @RequestParam(defaultValue = "") String province,
                              @RequestParam(defaultValue = "") String city,
                              @RequestParam(defaultValue = "") String country)
            throws IOException {
        ExecResult<UserInfo> userInfoExecResult = accountService.createNewUser(openid, "wx|" + openid, "12345678", imgUrl, country,
                province, city, gender, nickName);
        model.put("success", userInfoExecResult.isSucc());
        model.put("value", userInfoExecResult.getValue());
        responseJson(response, model);
    }

    /**
     * Is phone not registered?
     *
     * @param response
     * @param model
     * @throws IOException
     */
    @RequestMapping(value = "/wx/code", method = RequestMethod.GET)
    public void getWxOpenId(HttpServletResponse response, ModelMap model,
                              @RequestParam(defaultValue = "") String code)
            throws IOException {
        String result = HttpUtils.get(SystemConstants.ACCESS_TOKEN_URL.replace("JSCODE", code));
        WeixinSessionResponse tokenResponse = JsonUtils.fromJson(result, WeixinSessionResponse.class);
        if (tokenResponse != null) {
            ExecResult<UserInfo> execResult = accountService.checkIsNotRegisteredByWx(tokenResponse.getOpenid());
            model.put("openid", tokenResponse.getOpenid());
            model.put("success", execResult.isSucc());
        } else {
            model.put("success", false);
        }
        responseJson(response, model);
    }

    /**
     * Is phone not registered?
     *
     * @param response
     * @param model
     * @param phone
     * @throws IOException
     */
    @RequestMapping(value = "/isNotRegistered", method = RequestMethod.POST)
    public void isNotRegister(HttpServletResponse response, ModelMap model,
                         @RequestParam(defaultValue = "") String phone)
            throws IOException {
        ExecInfo execInfo = phoneInputValidCheck(phone);
        if (!execInfo.isSucc()) {
            model.put("msg", execInfo.getMsg());
            model.put("success", false);
        } else {
            ExecResult<UserInfo> execResult = accountService.checkIsNotRegistered(phone);
            model.put("success", execResult.isSucc());
            model.put("msg", execResult.getMsg());
        }
        responseJson(response, model);
    }

    /**
     * Is phone registered?
     *
     * @param response
     * @param model
     * @param phone
     * @throws IOException
     */
    @RequestMapping(value = "/isRegistered", method = RequestMethod.POST)
    public void isRegister(HttpServletResponse response, ModelMap model,
                         @RequestParam(defaultValue = "") String phone)
            throws IOException {
        ExecInfo execInfo = phoneInputValidCheck(phone);
        if (!execInfo.isSucc()) {
            model.put("msg", execInfo.getMsg());
            model.put("success", false);
        } else {
            ExecResult<UserInfo> execResult = accountService.checkIsRegistered(phone);
            model.put("success", execResult.isSucc());
            model.put("msg", execResult.getMsg());
        }
        responseJson(response, model);
    }

    /**
     * Register new user
     *
     * @param response
     * @param model
     * @param password
     * @param phone
     * @throws IOException
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(HttpServletResponse response, ModelMap model,
                         @RequestParam(defaultValue = "") String password,
                         @RequestParam(defaultValue = "") String phone)
            throws IOException {
        ExecInfo execInfo = registerInputValidCheck(phone, password, password);
        if (!execInfo.isSucc()) {
            model.put("msg", execInfo.getMsg());
            model.put("success", false);
        } else {
            ExecResult<UserInfo> execResult = accountService.createNewUser(phone, password);
            model.put("success", execResult.isSucc());
            model.put("msg", execResult.getMsg());
            if (execResult.isSucc()) {
                model.put("userId", execResult.getValue().getId());
            }
        }
        responseJson(response, model);
    }

    /**
     * Register new user
     *
     * @param response
     * @param model
     * @param username
     * @param userId
     * @throws IOException
     */
    @RequestMapping(value = "/setName", method = RequestMethod.POST)
    public void setUserName(HttpServletResponse response, ModelMap model,
                         @RequestParam(defaultValue = "") String username,
                         @RequestParam(defaultValue = "") long userId)
            throws IOException {
        ExecInfo execInfo = userNameInputValidCheck(username);
        if (!execInfo.isSucc()) {
            model.put("msg", execInfo.getMsg());
        } else {
            execInfo = accountService.setUsername(userId, username);
        }
        model.put("success", execInfo.isSucc());
        model.put("msg", execInfo.getMsg());
        responseJson(response, model);
    }

    private ExecInfo phoneInputValidCheck(String phone) {
        ExecInfo execInfo = ExecInfo.succ();
        if (StringUtils.isBlank(phone)) {
            execInfo = ExecInfo.fail("抱歉，手机号不能为空");
        } else if (phone.length() != 11 && StringUtils.isNumeric(phone)) {
            execInfo = ExecInfo.fail("抱歉，手机号码应为11位数字");
        }
        return execInfo;
    }

    private ExecInfo registerInputValidCheck(String phone, String password1, String password2) {
        ExecInfo execInfo = phoneInputValidCheck(phone);
        if (!execInfo.isSucc()) {
            return execInfo;
        }
        if (StringUtils.isBlank(password1)) {
            execInfo = ExecInfo.fail("抱歉，密码不能为空");
        } else if (!password1.equals(password2)) {
            execInfo = ExecInfo.fail("抱歉，两次密码输入不一致，请重新输入。");
        } else if (password1.equalsIgnoreCase(phone)) {
            execInfo = ExecInfo.fail("抱歉，密码不能等于手机号，请重新输入。");
        } else if (password1.equalsIgnoreCase("123456")) {
            execInfo = ExecInfo.fail("抱歉，密码过于简单，请重新输入。");
        } else if (password1.length() < 6) {
            execInfo = ExecInfo.fail("抱歉，请使用大于等于6位的密码，请重新输入。");
        }
        return execInfo;
    }

    private ExecInfo userNameInputValidCheck(String username) {
        ExecInfo execInfo = ExecInfo.succ();
        if (StringUtils.isBlank(username)) {
            execInfo = ExecInfo.fail("抱歉，用户名不能为空，请填写用户名。");
        } else if (username.length() > 20) {
            execInfo = ExecInfo.fail("抱歉，用户名过长，请重新选择。");
        }
        return execInfo;
    }

}
