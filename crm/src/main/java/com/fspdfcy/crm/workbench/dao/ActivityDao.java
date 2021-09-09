package com.fspdfcy.crm.workbench.dao;

import com.fspdfcy.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/7
 */
public interface ActivityDao {
    int insert(Activity activity);

    List<Activity> selectByCondition(Map<String, Object> map);

    int selectCountByCondition(Map<String, Object> map);

    int update(Activity activity);

    Activity selectById(String id);

    int deleteByIds(String[] ids);

    Activity selectById1(String id);
}
