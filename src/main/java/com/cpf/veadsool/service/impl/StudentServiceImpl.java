package com.cpf.veadsool.service.impl;

import com.cpf.veadsool.annotation.NeedExchangeName;
import com.cpf.veadsool.base.BusinessException;
import com.cpf.veadsool.dto.StudentDto;
import com.cpf.veadsool.entity.Grade;
import com.cpf.veadsool.entity.Student;
import com.cpf.veadsool.mapper.StudentMapper;
import com.cpf.veadsool.service.IGradeService;
import com.cpf.veadsool.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 学生 服务实现类
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    @Resource
    private IGradeService iGradeService;
    @Override
    public boolean update(Student student) {
        if (student.getId() == null) {
            throw new BusinessException("数据一刷新请重试");
        }
        return this.updateById(student);
    }

    @Override
    @NeedExchangeName
    public List<StudentDto> listStudent() {
        List<Student> list = this.list();
        if (CollectionUtils.isNotEmpty(list)){
           return exchangeName(list);

        }
        return Lists.newArrayList();
    }

    @Override
    public List<StudentDto> listStudentByIds(List<Integer> studentIdList) {
        List<Student> students = this.listByIds(studentIdList);
        return exchangeName(students);
    }

    /**
     * 置换名称
     * @param list
     * @return
     */
    private List<StudentDto> exchangeName(List<Student> list) {
        List<Integer> gradeIdList = list.parallelStream().map(Student::getGradeId).collect(Collectors.toList());
        Map<Integer, String> collect = Maps.newHashBiMap();
        if (CollectionUtils.isNotEmpty(gradeIdList)){
            List<Grade> gradeList = iGradeService.listByIds(gradeIdList);
            collect = gradeList.parallelStream().collect(Collectors.toMap(Grade::getId, Grade::getGradeName));
        }
        Map<Integer, String> finalCollect = collect;
        return list.parallelStream().map(e -> {
            StudentDto studentDto = new StudentDto();
            BeanUtils.copyProperties(e, studentDto);
            studentDto.setGradeName(finalCollect.get(e.getGradeId()));
            return studentDto;
        }).collect(Collectors.toList());
    }
}
