package com.fspdfcy.crm.workbench.dao;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/7
 */
public interface ActivityRemarkDao {
    int selectCountByAIds(String[] ids);

    int deleteByIds(String[] ids);
}
