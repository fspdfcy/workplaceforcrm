package com.fspdfcy.crm.settings.service.impl;

import com.fspdfcy.crm.settings.dao.UserDao;
import com.fspdfcy.crm.settings.service.UserService;
import com.fspdfcy.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/6
 */
public class UserServiceImpl implements UserService {
    UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
}
