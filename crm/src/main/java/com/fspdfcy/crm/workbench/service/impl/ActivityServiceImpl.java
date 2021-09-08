package com.fspdfcy.crm.workbench.service.impl;

import com.fspdfcy.crm.settings.dao.UserDao;
import com.fspdfcy.crm.settings.domain.User;
import com.fspdfcy.crm.utils.SqlSessionUtil;
import com.fspdfcy.crm.vo.PageVo;
import com.fspdfcy.crm.workbench.dao.ActivityDao;
import com.fspdfcy.crm.workbench.dao.ActivityRemarkDao;
import com.fspdfcy.crm.workbench.domain.Activity;
import com.fspdfcy.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/7
 */
public class ActivityServiceImpl implements ActivityService {

    private final ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private final UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    private final ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    @Override
    public List<User> getUserList() {
        UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        List<User> userList =  userDao.selectAll();
        return userList;
    }

    @Override
    public boolean save(Activity activity) {
        int i = activityDao.insert(activity);
        return i == 1;
    }

    @Override
    public PageVo<Activity> pageList(Map<String, Object> map) {
        PageVo<Activity> pageVo = new PageVo<>();
        List<Activity> activityList = activityDao.selectByCondition(map);
        int totalSize = activityDao.selectCountByCondition(map);

        pageVo.setDataList(activityList);
        pageVo.setTotalSize(totalSize);

        return pageVo;
    }

    @Override
    public boolean update(Activity activity) {
        int i = activityDao.update(activity);
        return i == 1;
    }

    @Override
    public boolean delete(String[] ids) {

        int count1 = activityRemarkDao.selectCountByAIds(ids);
        int count2 = activityRemarkDao.deleteByIds(ids);

        if (count1 == count2) {
            int count3 = activityDao.deleteByIds(ids);
            if (count3 == ids.length) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        List<User> userList = userDao.selectAll();
        Activity activity = activityDao.selectById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("userList",userList);
        map.put("activity",activity);
        return map;
    }
}
