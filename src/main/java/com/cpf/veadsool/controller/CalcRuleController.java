package com.cpf.veadsool.controller;


import com.cpf.veadsool.annotation.NeedExchangeName;
import com.cpf.veadsool.base.ErrorConstant;
import com.cpf.veadsool.base.Result;
import com.cpf.veadsool.dto.CalcRuleDto;
import com.cpf.veadsool.entity.CalcRule;
import com.cpf.veadsool.service.ICalcRuleService;
import com.cpf.veadsool.util.ModelTransformUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 计算规则 前端控制器
 * </p>
 *
 * @author caopengflying
 * @since 2020-05-10
 */
@RestController
@RequestMapping("/calcRule")
public class CalcRuleController {
    @Resource
    private ICalcRuleService calcRuleService;

    @GetMapping("/list")
    @NeedExchangeName
    public Result<List<CalcRuleDto>> list() {
        List<CalcRule> list = calcRuleService.list();
        return ErrorConstant.getSuccessResult(ModelTransformUtils.exchangeClassList(list, CalcRuleDto.class));
    }

    @PostMapping("/create")
    public Result create(@RequestBody CalcRule calcRule) {
        boolean save = calcRuleService.save(calcRule);
        if (save) {
            return ErrorConstant.getSuccessResult("新增成功");
        }
        return ErrorConstant.getErrorResult(ErrorConstant.FAIL, "新增失败");
    }

    @PostMapping("/update")
    public Result update(@RequestBody CalcRule calcRule) {
        boolean update = calcRuleService.updateById(calcRule);
        if (update) {
            return ErrorConstant.getSuccessResult("修改成功");
        }
        return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL, "修改失败");
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody CalcRule calcRule) {
        if (null != calcRule.getId()) {
            CalcRule byId = calcRuleService.getById(calcRule.getId());
            if (null == byId) {
                return ErrorConstant.getErrorResult(ErrorConstant.FAIL, "数据已刷新");
            }
            boolean delete = calcRuleService.removeById(calcRule.getId());
            if (delete) {
                return ErrorConstant.getSuccessResult("删除成功");
            }
            return ErrorConstant.getErrorResult(ErrorConstant.FAIL, "删除失败");
        }
        return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL, "删除失败");
    }
}
