package com.cpf.veadsool.constants;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
public interface ApplicationConstants {
    /**
     * 考勤初始成绩
     */
    BigDecimal INIT_ATTENDANCE_SCORE = new BigDecimal("100");
    /**
     * 奖惩初始成绩
     */
    BigDecimal INIT_OTHER_SCORE = new BigDecimal("100");

    String DEFAULT_PASSWORD = "123456";
    /**
     * 创建人key
     */
    String CREATE_USER_KEY = "createUser";
    /**
     * 修改人key
     */
    String UPDATE_USER_KEY = "updateUser";
    /**
     * 创建人名称key
     */
    String CREATE_USER_NAME_KEY = "createUserName";
    /**
     * 修改人名称key
     */
    String UPDATE_USER_NAME_KEY = "updateUserName";
}
