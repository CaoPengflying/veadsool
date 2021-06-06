package com.cpf.veadsool.controller;


import com.cpf.veadsool.base.ErrorConstant;
import com.cpf.veadsool.base.Result;
import com.cpf.veadsool.dto.RulesDto;
import com.cpf.veadsool.dto.StudentCreditsFlowDto;
import com.cpf.veadsool.dto.StudentDto;
import com.cpf.veadsool.entity.Rules;
import com.cpf.veadsool.entity.Student;
import com.cpf.veadsool.entity.StudentCreditsFlow;
import com.cpf.veadsool.service.IRulesService;
import com.cpf.veadsool.service.IStudentCreditsFlowService;
import com.cpf.veadsool.util.ModelTransformUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 学分变动流水 前端控制器
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-03
 */
@RestController
@RequestMapping("/studentCreditsFlow")
public class StudentCreditsFlowController {
    @Resource
    private IStudentCreditsFlowService iStudentCreditsFlowService;

    @GetMapping("/list")
    public Result<List<StudentCreditsFlowDto>> list() {
        return ErrorConstant.getSuccessResult(iStudentCreditsFlowService.listFlow());
    }

    @PostMapping("/create")
    public Result create(@RequestBody StudentCreditsFlow studentCreditsFlow) {
        boolean save = iStudentCreditsFlowService.save(studentCreditsFlow);
        if (save) {
            return ErrorConstant.getSuccessResult("新增成功");
        }
        return ErrorConstant.getErrorResult(ErrorConstant.FAIL, "新增失败");
    }

    @PostMapping("/update")
    public Result<List<RulesDto>> update(@RequestBody StudentCreditsFlow studentCreditsFlow) {
        boolean update = iStudentCreditsFlowService.update(studentCreditsFlow);
        if (update) {
            return ErrorConstant.getSuccessResult("修改成功");
        }
        return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL, "修改失败");
    }

    @PostMapping("/delete")
    public Result<List<RulesDto>> delete(@RequestBody StudentCreditsFlow studentCreditsFlow) {
        if (null != studentCreditsFlow.getId()) {
            StudentCreditsFlow byId = iStudentCreditsFlowService.getById(studentCreditsFlow.getId());
            if (null == byId){
                return ErrorConstant.getErrorResult(ErrorConstant.FAIL, "数据已刷新");
            }
            boolean delete = iStudentCreditsFlowService.removeById(studentCreditsFlow.getId());
            if (delete) {
                return ErrorConstant.getSuccessResult("删除成功");
            }
            return ErrorConstant.getErrorResult(ErrorConstant.FAIL, "删除失败");
        }
        return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL, "删除失败");
    }

}
