package com.cpf.veadsool.service;

import com.cpf.veadsool.entity.Grade;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 班级 服务类
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
public interface IGradeService extends IService<Grade> {
    /**
     * 根据主键修改
     * @param grade
     * @return
     */
    boolean update(Grade grade);
}
