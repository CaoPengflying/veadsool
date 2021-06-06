package com.cpf.veadsool.entity;

import com.cpf.veadsool.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 计算规则
 * </p>
 *
 * @author caopengflying
 * @since 2020-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CalcRule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 文化课占比
     */
    @NotNull
    @Min(0)
    @Max(100)
    private Integer cultureProp;

    /**
     * 考勤占比
     */
    @NotNull
    @Min(0)
    @Max(100)
    private Integer attendanceProp;

    /**
     * 其他占比
     */
    @NotNull
    @Min(0)
    @Max(100)
    private Integer otherProp;


}
