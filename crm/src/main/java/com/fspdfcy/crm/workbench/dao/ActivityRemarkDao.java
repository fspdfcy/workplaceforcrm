package com.fspdfcy.crm.workbench.dao;

import com.fspdfcy.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/7
 */
public interface ActivityRemarkDao {
    int selectCountByAIds(String[] ids);

    int deleteByIds(String[] ids);

    List<ActivityRemark> selectByAid(String id);

    int deleteById(String id);

    ActivityRemark selectById(String id);

    int update(ActivityRemark activityRemark);

    int insert(ActivityRemark activityRemark);
}
