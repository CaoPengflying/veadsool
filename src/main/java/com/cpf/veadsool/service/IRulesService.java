package com.cpf.veadsool.service;

import com.cpf.veadsool.dto.RulesDto;
import com.cpf.veadsool.entity.Rules;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 基础规则 服务类
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
public interface IRulesService extends IService<Rules> {
    /**
     * 获取列表
     * @param rules
     * @return
     */
    List<RulesDto> list(Rules rules);

    /**
     * 修改
     * @param rules
     * @return
     */
    boolean update(Rules rules);
}
