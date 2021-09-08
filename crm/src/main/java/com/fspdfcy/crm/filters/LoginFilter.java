package com.fspdfcy.crm.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/6
 */
@WebFilter({"*.do","*.jsp"})
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        System.out.println("进入到验证有没有登录的过滤器");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();

        //如果没有登录则跳转到登录页面
        if (request.getSession().getAttribute("user") == null) {
            if ("/settings/user/login.do".equals(path)) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
