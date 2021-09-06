package com.fspdfcy;

import com.fspdfcy.crm.utils.DateUtil;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/9/6
 */
public class Test {
    public static void main(String[] args) {
        String currentDate = DateUtil.getCurrentDate();
        String oldDate = "2022-11-30 23:50:55";
        System.out.println(currentDate.compareTo(oldDate));
    }
}
