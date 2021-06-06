package com.cpf.veadsool.dto;


import com.cpf.veadsool.entity.Grade;
import com.cpf.veadsool.entity.Student;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author caopengflying
 * @time 2020/3/4
 */
@Getter
@Setter
@ToString
public class StudentDto extends Student {
    /**
     * 班级名称
     */
    private String gradeName;
}
