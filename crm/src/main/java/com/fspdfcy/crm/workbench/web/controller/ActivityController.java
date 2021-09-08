package com.fspdfcy.crm.workbench.web.controller;

import com.fspdfcy.crm.settings.domain.User;
import com.fspdfcy.crm.settings.service.UserService;
import com.fspdfcy.crm.settings.service.impl.UserServiceImpl;
import com.fspdfcy.crm.utils.*;
import com.fspdfcy.crm.vo.PageVo;
import com.fspdfcy.crm.workbench.domain.Activity;
import com.fspdfcy.crm.workbench.service.ActivityService;
import com.fspdfcy.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/6
 */
public class ActivityController extends HttpServlet {


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/workbench/activity/getUserList.do".equals(path)) {
            getUserList(request, response);
        } else if ("/workbench/activity/save.do".equals(path)) {
            save(request,response);
        } else if ("/workbench/activity/pageList.do".equals(path)) {
            pageList(request, response);
        }
    }


    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        //调用service获取数据
        List<User> userList =  service.getUserList();
        System.out.println(userList);
        PrintJson.printJsonObj(response, userList);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {

        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity activity = new Activity();
        WebUtil.makeRequestToObject(request, activity);

        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateUtil.format(Const.DATA_FORMAT_PATTERN_ALL));
        activity.setCreateBy(((User)request.getSession().getAttribute("user")).getName());

        //调用service获取数据
        boolean flag =  service.save(activity);
        PrintJson.printJsonFlag(response, flag);
    }


    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<>();
        WebUtil.makeRequestToMap(request, map);
        int pageNo = Integer.parseInt((String) map.get("pageNo")) - 1;
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        map.put("skipCount",pageNo * pageSize);
        map.replace("pageSize",pageSize);


        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PageVo<Activity> pageVo = service.pageList(map);

        PrintJson.printJsonObj(response,pageVo);


    }
}
