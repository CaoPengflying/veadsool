package com.cpf.veadsool.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cpf.veadsool.annotation.NeedExchangeName;
import com.cpf.veadsool.base.ErrorConstant;
import com.cpf.veadsool.base.Result;
import com.cpf.veadsool.dto.GradeDto;
import com.cpf.veadsool.dto.RulesDto;
import com.cpf.veadsool.entity.Grade;
import com.cpf.veadsool.entity.Rules;
import com.cpf.veadsool.service.IGradeService;
import com.cpf.veadsool.service.IRulesService;
import com.cpf.veadsool.util.ModelTransformUtils;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 班级 前端控制器
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
@RestController
@RequestMapping("/grade")
public class GradeController {
    @Resource
    private IGradeService iGradeService;

    @GetMapping("/list")
    @NeedExchangeName
    public Result<List<GradeDto>> list(Integer current, Integer offset) {
        Page<Grade> page = new Page<>(current,offset);
        Page<Grade> pageResult = iGradeService.page(page);
        Map<String, Object> resultMap = Maps.newHashMap();
        List<GradeDto> result = ModelTransformUtils.exchangeClassList(pageResult.getRecords(), GradeDto.class);
        resultMap.put("list", result);
        resultMap.put("count", pageResult.getTotal());
        return ErrorConstant.getSuccessResult(resultMap);
    }

    @GetMapping("/listAll")
    @NeedExchangeName
    public Result<List<GradeDto>> listAll() {
        List<Grade> gradeList = iGradeService.list();
        List<GradeDto> result = ModelTransformUtils.exchangeClassList(gradeList, GradeDto.class);
        return ErrorConstant.getSuccessResult(result);
    }

    @PostMapping("/create")
    public Result create(@RequestBody Grade grade) {
        boolean save = iGradeService.save(grade);
        if (save) {
            return ErrorConstant.getSuccessResult("新增成功");
        }
        return ErrorConstant.getErrorResult(ErrorConstant.FAIL, "新增失败");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Grade grade) {
        boolean update = iGradeService.update(grade);
        if (update) {
            return ErrorConstant.getSuccessResult("修改成功");
        }
        return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL, "修改失败");
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Grade grade) {
        if (null != grade.getId()) {
            Grade byId = iGradeService.getById(grade.getId());
            if (null == byId){
                return ErrorConstant.getErrorResult(ErrorConstant.FAIL, "数据已刷新");
            }
            boolean delete = iGradeService.removeById(grade.getId());
            if (delete) {
                return ErrorConstant.getSuccessResult("删除成功");
            }
            return ErrorConstant.getErrorResult(ErrorConstant.FAIL, "删除失败");
        }
        return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL, "删除失败");
    }

}
