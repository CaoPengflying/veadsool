package com.cpf.veadsool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cpf.veadsool.annotation.NeedExchangeName;
import com.cpf.veadsool.base.BusinessException;
import com.cpf.veadsool.dto.RulesDto;
import com.cpf.veadsool.entity.Rules;
import com.cpf.veadsool.mapper.RulesMapper;
import com.cpf.veadsool.service.IRulesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.veadsool.util.ModelTransformUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 基础规则 服务实现类
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
@Service
public class RulesServiceImpl extends ServiceImpl<RulesMapper, Rules> implements IRulesService {

    @Override
    @NeedExchangeName
    public List<RulesDto> list(Rules rules) {
        QueryWrapper<Rules> queryWrapper = new QueryWrapper<>();
        if (null != rules.getRuleType()) {
            queryWrapper.lambda().eq(Rules::getRuleType, rules.getRuleType());
        }
        List<Rules> list = this.list(queryWrapper);
        return ModelTransformUtils.exchangeClassList(list, RulesDto.class);
    }

    @Override
    public boolean update(Rules rules) {
        if (rules.getId() == null) {
            throw new BusinessException("数据一刷新请重试");
        }
        return this.updateById(rules);
    }
}
