package com.cpf.veadsool.service;

import com.cpf.veadsool.dto.StudentFilesDto;
import com.cpf.veadsool.entity.StudentFiles;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学生档案 服务类
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
public interface IStudentFilesService extends IService<StudentFiles> {
    /**
     * 修改
     * @param studentFiles
     * @return
     */
    boolean update(StudentFiles studentFiles);

    /**
     * 新增学生档案
     * @param studentFiles
     */
    void create(StudentFiles studentFiles);

    /**
     * 获取档案列表
     * @return
     */
    List<StudentFilesDto> listFiles();

}
