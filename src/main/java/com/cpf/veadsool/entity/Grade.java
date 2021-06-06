package com.cpf.veadsool.entity;

import com.cpf.veadsool.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 班级
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Grade extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 班级名称
     */
    private String gradeName;

    /**
     * 班级编号
     */
    private String gradeNo;


}
