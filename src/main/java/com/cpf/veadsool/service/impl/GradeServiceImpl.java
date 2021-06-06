package com.cpf.veadsool.service.impl;

import com.cpf.veadsool.base.BusinessException;
import com.cpf.veadsool.entity.Grade;
import com.cpf.veadsool.mapper.GradeMapper;
import com.cpf.veadsool.service.IGradeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 班级 服务实现类
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements IGradeService {

    @Override
    public boolean update(Grade grade) {
        if (grade.getId() == null) {
            throw new BusinessException("数据一刷新请重试");
        }
        return this.updateById(grade);
    }
}
