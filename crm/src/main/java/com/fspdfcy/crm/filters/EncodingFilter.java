package com.fspdfcy.crm.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/6
 */
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //解决post请求乱码
        request.setCharacterEncoding("utf-8");
        //解决响应乱码
        response.setContentType("text/html;charset=utf-8");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
