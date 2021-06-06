package com.cpf.veadsool.service;

import com.cpf.veadsool.dto.StudentCreditsFlowDto;
import com.cpf.veadsool.entity.StudentCreditsFlow;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学分变动流水 服务类
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
public interface IStudentCreditsFlowService extends IService<StudentCreditsFlow> {
    /**
     * 修改
     * @param studentCreditsFlow
     * @return
     */
    boolean update(StudentCreditsFlow studentCreditsFlow);

    /**
     * 获取列表
     * @return
     */
    List<StudentCreditsFlowDto> listFlow();

}
