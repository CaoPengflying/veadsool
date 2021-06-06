package com.cpf.veadsool.dto;


import com.cpf.veadsool.entity.Grade;
import com.cpf.veadsool.entity.StudentCreditsFlow;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


/**
 * @author caopengflying
 * @time 2020/3/4
 */
@Getter
@Setter
@ToString
public class StudentCreditsFlowDto extends StudentCreditsFlow {
    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 规则类型
     */
    private Integer ruleType;
    /**
     * 规则类型
     */
    private String ruleTypeName;
    /**
     * 分数变动
     */
    private BigDecimal changePoints;
    /**
     * 学生名称
     */
    private String studentName;
    /**
     * 状态名称
     */
    private String statusName;
}
