package com.cpf.veadsool.entity;

import java.math.BigDecimal;
import com.cpf.veadsool.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 班级档案
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class GradeFiles extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 班级标识
     */
    private Integer gradeId;

    /**
     * 平均分
     */
    private BigDecimal aveScore;

    /**
     * 最高分
     */
    private BigDecimal maxScore;

    /**
     * 最低分
     */
    private BigDecimal minScore;

    /**
     * 优秀人数
     */
    private Integer greatCount;

    /**
     * 不及格人数
     */
    private Integer badCount;


}
