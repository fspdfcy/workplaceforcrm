package com.fspdfcy.crm.settings.web.controller;

import com.fspdfcy.crm.settings.domain.User;
import com.fspdfcy.crm.settings.service.UserService;
import com.fspdfcy.crm.settings.service.impl.UserServiceImpl;
import com.fspdfcy.crm.utils.MD5Util;
import com.fspdfcy.crm.utils.PrintJson;
import com.fspdfcy.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/6
 */
public class UserController extends HttpServlet {
    private UserService service = (UserService) ServiceFactory.getService(new UserServiceImpl());

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletPath();
        if ("/settings/user/login.do".equals(path)) {
            doLogin(request, response);
        } else if ("/settings/user/yy.do".equals(path)) {
            //doXxx(request,response);
        }

    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接收数据
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        //将密码的明文形式转换为密文形式
        loginPwd = MD5Util.getMD5(loginPwd);
        //接收浏览器的ip地址
        String ip = request.getRemoteAddr();

        //调用service执行login业务
        try {
            User user = service.login(loginAct, loginPwd, ip);
            request.getSession().setAttribute("user", user);
            PrintJson.printJsonFlag(response, true);
        } catch (Exception e) {
            String msg = e.getMessage();
            Map<String, Object> map = new HashMap<>();
            map.put("msg", msg);
            map.put("success", false);
            PrintJson.printJsonObj(response, map);
        }
    }
}
