/*
 * @(#)SpringAuthenticationSuccessHandler.java, 2015-7-17.
 *
 * Copyright 2015 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.service.impl;

import nanshen.dao.UserInfoDao;
import nanshen.service.AccountService;
import nanshen.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author WANG Minghao
 */
@Service
public class SpringAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String PARAM_USERNAME = "email";

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private AccountService accountService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        long tempLoginId = recordLogin(authentication.getName());
        accountService.clearUserInfoCache(Long.parseLong(authentication.getName()));
        setDefaultTargetUrl("/auth/success?uid=" + authentication.getName() + "&tpuid=" + tempLoginId);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private long recordLogin(String uid) {
        return userInfoDao.login(uid, RequestUtils.getRequestIp(), new Date());
    }

}
