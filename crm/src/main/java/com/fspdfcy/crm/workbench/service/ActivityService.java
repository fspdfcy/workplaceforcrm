package com.fspdfcy.crm.workbench.service;

import com.fspdfcy.crm.settings.domain.User;
import com.fspdfcy.crm.vo.PageVo;
import com.fspdfcy.crm.workbench.domain.Activity;
import com.fspdfcy.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/7
 */
public interface ActivityService {
    List<User> getUserList();

    boolean save(Activity activity);

    PageVo<Activity> pageList(Map<String, Object> map);

    boolean update(Activity activity);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    Activity getActivityById(String id);

    List<ActivityRemark> getActivityRemarkList(String id);

    boolean deleteActivityRemark(String id);

    ActivityRemark getActivityRemark(String id);

    boolean updateActivityRemark(ActivityRemark activityRemark);

    boolean insertActivityRemark(ActivityRemark activityRemark);
}
