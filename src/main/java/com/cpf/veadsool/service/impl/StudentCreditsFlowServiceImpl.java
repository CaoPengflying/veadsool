package com.cpf.veadsool.service.impl;

import com.cpf.veadsool.annotation.NeedExchangeName;
import com.cpf.veadsool.base.BusinessException;
import com.cpf.veadsool.constants.RulesEnum;
import com.cpf.veadsool.constants.StudentCreditsFlowEnum;
import com.cpf.veadsool.dto.StudentCreditsFlowDto;
import com.cpf.veadsool.entity.Rules;
import com.cpf.veadsool.entity.Student;
import com.cpf.veadsool.entity.StudentCreditsFlow;
import com.cpf.veadsool.mapper.StudentCreditsFlowMapper;
import com.cpf.veadsool.service.IRulesService;
import com.cpf.veadsool.service.IStudentCreditsFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpf.veadsool.service.IStudentService;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 学分变动流水 服务实现类
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
@Service
public class StudentCreditsFlowServiceImpl extends ServiceImpl<StudentCreditsFlowMapper, StudentCreditsFlow> implements IStudentCreditsFlowService {
    @Resource
    private IStudentService iStudentService;
    @Resource
    private IRulesService iRuleService;

    @Override
    public boolean update(StudentCreditsFlow studentCreditsFlow) {
        if (studentCreditsFlow.getId() == null) {
            throw new BusinessException("数据一刷新请重试");
        }
        return this.updateById(studentCreditsFlow);
    }

    @Override
    @NeedExchangeName
    public List<StudentCreditsFlowDto> listFlow() {
        List<StudentCreditsFlow> list = this.list();
        List<StudentCreditsFlowDto> resultList = Lists.newArrayList();
        List<Integer> ruleIdList = Lists.newArrayList();
        List<Integer> studentIdList = Lists.newArrayList();
        for (StudentCreditsFlow studentCreditsFlow : list) {
            ruleIdList.add(studentCreditsFlow.getRuleId());
            studentIdList.add(studentCreditsFlow.getStudentId());
        }
        Map<Integer, Rules> ruleMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(ruleIdList)) {
            List<Rules> rules = iRuleService.listByIds(ruleIdList);
            ruleMap = rules.parallelStream().collect(Collectors.toMap(Rules::getId, Function.identity()));
        }
        Map<Integer, String> studentMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(studentIdList)) {
            List<Student> students = iStudentService.listByIds(studentIdList);
            studentMap = students.parallelStream().collect(Collectors.toMap(Student::getId, Student::getStudentName));
        }
        for (StudentCreditsFlow studentCreditsFlow : list) {
            StudentCreditsFlowDto studentCreditsFlowDto = new StudentCreditsFlowDto();
            BeanUtils.copyProperties(studentCreditsFlow,studentCreditsFlowDto);
            if (studentMap.containsKey(studentCreditsFlow.getStudentId())){
                studentCreditsFlowDto.setStudentName(studentMap.get(studentCreditsFlow.getStudentId()));
            }
            if (ruleMap.containsKey(studentCreditsFlow.getRuleId())){
                studentCreditsFlowDto.setRuleName(ruleMap.get(studentCreditsFlow.getRuleId()).getRuleName());
                studentCreditsFlowDto.setRuleTypeName(RulesEnum.getRuleTypeName(ruleMap.get(studentCreditsFlow.getRuleId()).getRuleType()));
                studentCreditsFlowDto.setRuleType(ruleMap.get(studentCreditsFlow.getRuleId()).getRuleType());
                if (ruleMap.get(studentCreditsFlow.getRuleId()).getRuleFlag()){
                    studentCreditsFlowDto.setChangePoints(ruleMap.get(studentCreditsFlow.getRuleId()).getChangePoints());
                }else {
                    studentCreditsFlowDto.setChangePoints(ruleMap.get(studentCreditsFlow.getRuleId()).getChangePoints().negate());
                }
            }
            studentCreditsFlowDto.setStatusName(StudentCreditsFlowEnum.getStatusName(studentCreditsFlow.getStatus()));
            resultList.add(studentCreditsFlowDto);
        }
        return resultList;
    }
}
