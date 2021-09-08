package com.fspdfcy.crm.workbench.service.impl;

import com.fspdfcy.crm.settings.dao.UserDao;
import com.fspdfcy.crm.settings.domain.User;
import com.fspdfcy.crm.utils.SqlSessionUtil;
import com.fspdfcy.crm.vo.PageVo;
import com.fspdfcy.crm.workbench.dao.ActivityDao;
import com.fspdfcy.crm.workbench.domain.Activity;
import com.fspdfcy.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/7
 */
public class ActivityServiceImpl implements ActivityService {

    private final ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
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
}
