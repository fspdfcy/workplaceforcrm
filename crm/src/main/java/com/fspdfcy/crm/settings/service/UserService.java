package com.fspdfcy.crm.settings.service;

import com.fspdfcy.crm.settings.domain.User;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/6
 */
public interface UserService {
    User login(String loginAct, String loginPwd, String ip);
}
