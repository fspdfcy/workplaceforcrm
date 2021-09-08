package com.fspdfcy.crm.workbench.service;

import com.fspdfcy.crm.settings.domain.User;
import com.fspdfcy.crm.vo.PageVo;
import com.fspdfcy.crm.workbench.domain.Activity;

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
}
