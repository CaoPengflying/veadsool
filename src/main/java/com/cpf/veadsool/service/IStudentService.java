package com.cpf.veadsool.service;

import com.cpf.veadsool.dto.StudentDto;
import com.cpf.veadsool.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学生 服务类
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
public interface IStudentService extends IService<Student> {
    /**
     * 修改学生
     * @param student
     * @return
     */
    boolean update(Student student);

    List<StudentDto> listStudent();

    /**
     * 根据ids获取学生列表
     * @param studentIdList
     * @return
     */
    List<StudentDto> listStudentByIds(List<Integer> studentIdList);
}
