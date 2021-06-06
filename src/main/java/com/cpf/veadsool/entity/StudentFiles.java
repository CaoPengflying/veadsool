package com.cpf.veadsool.entity;

import java.math.BigDecimal;
import com.cpf.veadsool.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 学生档案
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class StudentFiles extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 学生标识
     */
    @NotNull
    private Integer studentId;

    /**
     * 文化课成绩
     */
    @NotNull
    @DecimalMin("0")
    private Integer culturalSubjectScore;

    /**
     * 考勤成绩
     */
    private BigDecimal attendanceScore;

    /**
     * 其他成绩(奖惩)
     */
    private BigDecimal otherScore;

    /**
     * 最终得分
     */
    private BigDecimal realScore;
    /**
     * 学分规则
     */
    @NotNull
    private Integer calcRuleId;
    /**
     * 总得分
     */
    private BigDecimal sumScore;

    private String memo;


}
