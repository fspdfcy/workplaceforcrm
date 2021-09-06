package com.fspdfcy.crm.settings.dao;

import com.fspdfcy.crm.settings.domain.User;

import java.util.Map;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/6
 */
public interface UserDao {
    User selectByActAndPwd(Map<String, Object> map);
}
