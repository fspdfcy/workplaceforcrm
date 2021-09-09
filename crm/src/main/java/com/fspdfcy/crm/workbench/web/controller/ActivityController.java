package com.fspdfcy.crm.workbench.web.controller;

import com.fspdfcy.crm.settings.domain.User;
import com.fspdfcy.crm.utils.*;
import com.fspdfcy.crm.vo.PageVo;
import com.fspdfcy.crm.workbench.domain.Activity;
import com.fspdfcy.crm.workbench.domain.ActivityRemark;
import com.fspdfcy.crm.workbench.service.ActivityService;
import com.fspdfcy.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
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
            save(request, response);
        } else if ("/workbench/activity/pageList.do".equals(path)) {
            pageList(request, response);
        } else if ("/workbench/activity/getUserListAndActivity.do".equals(path)) {
            getUserListAndActivity(request, response);
        } else if ("/workbench/activity/delete.do".equals(path)) {
            delete(request, response);
        } else if ("/workbench/activity/update.do".equals(path)) {
            update(request, response);
        } else if ("/workbench/activity/getActivity.do".equals(path)) {
            getActivity(request, response);
        } else if ("/workbench/activity/getActivityRemarkList.do".equals(path)) {
            getActivityRemarkList(request, response);
        } else if ("/workbench/activity/deleteActivityRemark.do".equals(path)) {
            deleteActivityRemark(request, response);
        } else if ("/workbench/activity/getActivityRemark.do".equals(path)) {
            getActivityRemark(request, response);
        } else if ("/workbench/activity/updateActivityRemark.do".equals(path)) {
            updateActivityRemark(request, response);
        } else if ("/workbench/activity/insertActivityRemark.do".equals(path)) {
            insertActivityRemark(request, response);
        }
    }

    private void insertActivityRemark(HttpServletRequest request, HttpServletResponse response) {

        ActivityRemark activityRemark = new ActivityRemark();
        WebUtil.makeRequestToObject(request, activityRemark);
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        activityRemark.setCreateTime(DateUtil.format(Const.DATA_FORMAT_PATTERN_ALL));
        activityRemark.setEditFlag("0");

        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = service.insertActivityRemark(activityRemark);

        Map<String,Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar", activityRemark);
        PrintJson.printJsonObj(response, map);
    }

    private void updateActivityRemark(HttpServletRequest request, HttpServletResponse response) {

        ActivityRemark activityRemark = new ActivityRemark();
        WebUtil.makeRequestToObject(request, activityRemark);
        activityRemark.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        activityRemark.setEditTime(DateUtil.format(Const.DATA_FORMAT_PATTERN_ALL));
        activityRemark.setEditFlag("1");

        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = service.updateActivityRemark(activityRemark);

        Map<String,Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar", activityRemark);
        PrintJson.printJsonObj(response, map);
    }

    private void getActivityRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        ActivityRemark remark = service.getActivityRemark(id);
        PrintJson.printJsonObj(response, remark);
    }

    private void deleteActivityRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = service.deleteActivityRemark(id);
        PrintJson.printJsonFlag(response, flag);
    }

    private void getActivityRemarkList(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> activityRemarkList = service.getActivityRemarkList(id);

        PrintJson.printJsonObj(response, activityRemarkList);
    }

    private void getActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity activity = service.getActivityById(id);
        request.setAttribute("activity",activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        Activity activity = new Activity();
        WebUtil.makeRequestToObject(request, activity);
        activity.setEditBy(((User) request.getSession().getAttribute("user")).getName());
        activity.setEditTime(DateUtil.format(Const.DATA_FORMAT_PATTERN_ALL));


        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = service.update(activity);

        PrintJson.printJsonFlag(response, flag);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {

        String[] ids = request.getParameterValues("id");
        System.out.println(Arrays.toString(ids));

        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = service.delete(ids);

        PrintJson.printJsonFlag(response, flag);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");

        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String, Object> map = service.getUserListAndActivity(id);

        PrintJson.printJsonObj(response, map);
    }


    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        //调用service获取数据
        List<User> userList = service.getUserList();
        System.out.println(userList);
        PrintJson.printJsonObj(response, userList);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {

        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity activity = new Activity();
        WebUtil.makeRequestToObject(request, activity);

        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateUtil.format(Const.DATA_FORMAT_PATTERN_ALL));
        activity.setCreateBy(((User) request.getSession().getAttribute("user")).getName());

        //调用service获取数据
        boolean flag = service.save(activity);
        PrintJson.printJsonFlag(response, flag);
    }


    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        WebUtil.makeRequestToMap(request, map);
        int pageNo = Integer.parseInt((String) map.get("pageNo")) - 1;
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        map.put("skipCount", pageNo * pageSize);
        map.replace("pageSize", pageSize);


        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PageVo<Activity> pageVo = service.pageList(map);

        PrintJson.printJsonObj(response, pageVo);


    }
}
