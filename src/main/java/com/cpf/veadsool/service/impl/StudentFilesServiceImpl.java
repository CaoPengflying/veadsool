package com.cpf.veadsool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cpf.veadsool.annotation.NeedExchangeName;
import com.cpf.veadsool.base.BusinessException;
import com.cpf.veadsool.constants.ApplicationConstants;
import com.cpf.veadsool.constants.RulesEnum;
import com.cpf.veadsool.constants.StudentCreditsFlowEnum;
import com.cpf.veadsool.dto.StudentDto;
import com.cpf.veadsool.dto.StudentFilesDto;
import com.cpf.veadsool.entity.CalcRule;
import com.cpf.veadsool.entity.Rules;
import com.cpf.veadsool.entity.StudentCreditsFlow;
import com.cpf.veadsool.entity.StudentFiles;
import com.cpf.veadsool.mapper.StudentFilesMapper;
import com.cpf.veadsool.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 学生档案 服务实现类
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
@Service
public class StudentFilesServiceImpl extends ServiceImpl<StudentFilesMapper, StudentFiles> implements IStudentFilesService {
    @Resource
    private IStudentCreditsFlowService studentCreditsFlowService;
    @Resource
    private IRulesService rulesService;
    @Resource
    private IStudentService studentService;
    @Resource
    private ICalcRuleService calcRuleService;

    @Override
    public boolean update(StudentFiles studentFiles) {
        if (studentFiles.getId() == null) {
            throw new BusinessException("数据一刷新请重试");
        }
        return this.updateById(studentFiles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(StudentFiles studentFiles) {
        LambdaQueryWrapper<StudentCreditsFlow> qw = new LambdaQueryWrapper<>();
        qw.eq(StudentCreditsFlow::getStudentId, studentFiles.getStudentId())
                .eq(StudentCreditsFlow::getStatus, StudentCreditsFlowEnum.StatusEnum.NO.getCode());
        List<StudentCreditsFlow> list = studentCreditsFlowService.list(qw);
        List<Integer> roleIdList = list.parallelStream().map(StudentCreditsFlow::getRuleId).collect(Collectors.toList());
        List<Rules> rules = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(roleIdList)){
            rules = rulesService.listByIds(roleIdList);
        }
        BigDecimal attendanceScore = ApplicationConstants.INIT_ATTENDANCE_SCORE;
        BigDecimal otherScore = ApplicationConstants.INIT_OTHER_SCORE;
        StringBuilder memo = new StringBuilder();
        for (Rules rule : rules) {
            if (rule.getRuleType().equals(RulesEnum.RuleTypeEnum.attendance.getCode())) {
                if (rule.getRuleFlag()) {
                    attendanceScore = attendanceScore.add(rule.getChangePoints());
                } else {
                    attendanceScore = attendanceScore.subtract(rule.getChangePoints());
                }
            }
            if (rule.getRuleType().equals(RulesEnum.RuleTypeEnum.rule.getCode())) {
                if (rule.getRuleFlag()) {
                    otherScore = otherScore.add(rule.getChangePoints());
                } else {
                    otherScore = otherScore.subtract(rule.getChangePoints());
                }
            }
        }
        memo.append("该学生本学期");

        if (attendanceScore.compareTo(new BigDecimal("80")) >= 0) {
            memo.append("考勤成绩不错，平时比较遵守纪律！");
        } else if (attendanceScore.compareTo(new BigDecimal("60")) >= 0) {
            memo.append("考勤一般，有待提高自觉性！");
        } else {
            memo.append("考勤很差，严肃批评！");
        }

        if (otherScore.compareTo(new BigDecimal("80")) >= 0) {
            memo.append("经常参加其他活动，并获得奖项！");
        }
        if (otherScore.compareTo(new BigDecimal("60")) >= 0) {
            memo.append("比较内向！");
        } else {
            memo.append("有待提高综合能力！");
        }

        if (studentFiles.getCulturalSubjectScore() > 500) {
            memo.append("文化科目成绩很好！");
        }
        if (studentFiles.getCulturalSubjectScore() > 300) {
            memo.append("文化科目成绩一般，有待努力！");
        } else {
            memo.append("文化科目成绩很差，有必要一对一辅导！");
        }
        studentFiles.setMemo(memo.toString());
        studentFiles.setAttendanceScore(attendanceScore);
        studentFiles.setOtherScore(otherScore);
        studentFiles.setSumScore(attendanceScore.add(otherScore).add(new BigDecimal(studentFiles.getCulturalSubjectScore())));
        CalcRule calcRule = calcRuleService.getById(studentFiles.getCalcRuleId());
        BigDecimal attendanceRule = attendanceScore.multiply(new BigDecimal(calcRule.getAttendanceProp()).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP)).setScale(1, BigDecimal.ROUND_HALF_UP);
        BigDecimal otherRule = otherScore.multiply(new BigDecimal(calcRule.getOtherProp()).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP)).setScale(1, BigDecimal.ROUND_HALF_UP);
        BigDecimal cultureRule = new BigDecimal(studentFiles.getCulturalSubjectScore()).multiply(new BigDecimal(calcRule.getCultureProp()).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP)).setScale(1, BigDecimal.ROUND_HALF_UP);
        studentFiles.setRealScore(attendanceRule.add(otherRule).add(cultureRule));
        this.save(studentFiles);
        for (StudentCreditsFlow studentCreditsFlow : list) {
            studentCreditsFlow.setStatus(StudentCreditsFlowEnum.StatusEnum.YES.getCode());
        }
        studentCreditsFlowService.updateBatchById(list);
    }

    @Override
    @NeedExchangeName
    public List<StudentFilesDto> listFiles() {
        List<StudentFiles> list = this.list();
        List<Integer> studentIdList = list.parallelStream().map(StudentFiles::getStudentId).collect(Collectors.toList());
        List<Integer> calcRuleIdList = list.parallelStream().map(StudentFiles::getCalcRuleId).collect(Collectors.toList());
        Map<Integer, StudentDto> studentDtoMap = Maps.newHashMap();
        Map<Integer, String> ruleDtoMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(studentIdList)) {
            List<StudentDto> studentDtos = studentService.listStudentByIds(studentIdList);
            studentDtoMap = studentDtos.parallelStream().collect(Collectors.toMap(StudentDto::getId, Function.identity()));
        }
        if (CollectionUtils.isNotEmpty(calcRuleIdList)) {
            List<CalcRule> calcRuleList = calcRuleService.listByIds(calcRuleIdList);
            ruleDtoMap = calcRuleList.parallelStream().collect(Collectors.toMap(CalcRule::getId, CalcRule::getRuleName));

        }
        Map<Integer, StudentDto> finalStudentDtoMap = studentDtoMap;
        Map<Integer, String> finalRuleDtoMap = ruleDtoMap;
        return list.parallelStream().map(e -> {
            StudentDto studentDto = finalStudentDtoMap.get(e.getStudentId());
            StudentFilesDto studentFilesDto = new StudentFilesDto();
            if (null != studentDto) {
                BeanUtils.copyProperties(e, studentFilesDto);
                studentFilesDto.setGradeName(studentDto.getGradeName());
                studentFilesDto.setStudentNo(studentDto.getStudentNo());
                studentFilesDto.setStudentName(studentDto.getStudentName());
            }
            studentFilesDto.setCalcRuleName(finalRuleDtoMap.get(e.getCalcRuleId()));
            return studentFilesDto;
        }).collect(Collectors.toList());
    }


}
