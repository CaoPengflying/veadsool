package com.cpf.veadsool.entity;

import com.cpf.veadsool.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学分变动流水
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class StudentCreditsFlow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 规则
     */
    private Integer ruleId;

    /**
     * 学生标识
     */
    private Integer studentId;
    /**
     * 状态 0：未统计  1：已统计
     */
    private Integer status;


}
