package com.cpf.veadsool.entity;

import java.math.BigDecimal;
import com.cpf.veadsool.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 基础规则
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Rules extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 1:加分  0 减分
     */
    private Boolean ruleFlag;

    /**
     * 1:考勤规则  2:奖惩规则
     */
    private Integer ruleType;

    /**
     * 分值
     */
    private BigDecimal changePoints;


}
