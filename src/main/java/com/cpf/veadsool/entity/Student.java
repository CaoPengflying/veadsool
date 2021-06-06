package com.cpf.veadsool.entity;

import com.cpf.veadsool.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学生
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Student extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生学号
     */
    private String studentNo;

    /**
     * 所属班级
     */
    private Integer gradeId;


}
