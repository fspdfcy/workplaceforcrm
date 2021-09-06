package com.fspdfcy.crm.settings.service.impl;

import com.fspdfcy.crm.settings.dao.UserDao;
import com.fspdfcy.crm.settings.domain.User;
import com.fspdfcy.crm.settings.exception.LoginException;
import com.fspdfcy.crm.settings.service.UserService;
import com.fspdfcy.crm.utils.DateUtil;
import com.fspdfcy.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/6
 */
public class UserServiceImpl implements UserService {
    UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) {

        Map<String,Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user = userDao.selectByActAndPwd(map);
        if (user == null) {
            throw new LoginException("用户名或密码错误");
        }
        //验证expireTime
        String currentDate = DateUtil.getCurrentDate();
        if (currentDate.compareTo(user.getExpireTime()) > 0) {
            throw new LoginException("用户已过期");
        }
        //验证lockState
        if ("0".equals(user.getLockState())) {
            throw new LoginException("账户已冻结");
        }
        //验证allowIps
        String allowIps = user.getAllowIps();
        String[] ips = allowIps.split(",");
        boolean flag = false;
        for (String s : ips) {
            if (ip.equals(s)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new LoginException("用户ip地址不正确");
        }
        return null;
    }
}
