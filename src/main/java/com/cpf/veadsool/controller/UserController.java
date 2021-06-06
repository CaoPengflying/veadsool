package com.cpf.veadsool.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cpf.veadsool.annotation.NeedExchangeName;
import com.cpf.veadsool.base.ErrorConstant;
import com.cpf.veadsool.base.Result;
import com.cpf.veadsool.constants.ApplicationConstants;
import com.cpf.veadsool.dto.GradeDto;
import com.cpf.veadsool.dto.UserDto;
import com.cpf.veadsool.entity.Grade;
import com.cpf.veadsool.entity.StudentFiles;
import com.cpf.veadsool.entity.User;
import com.cpf.veadsool.service.IUserService;
import com.cpf.veadsool.util.ModelTransformUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户基本信息表 前端控制器
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService iUserService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User userSelect = iUserService.login(user);
        return ErrorConstant.getSuccessResult(userSelect, "登陆成功");
    }


    @GetMapping("/list")
    @NeedExchangeName
    public Result<List<UserDto>> list() {
        List<User> gradeList = iUserService.list();
        List<UserDto> result = ModelTransformUtils.exchangeClassList(gradeList, UserDto.class);
        return ErrorConstant.getSuccessResult(result);
    }

    @PostMapping("/create")
    public Result create(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getPassword())){
            user.setPassword(ApplicationConstants.DEFAULT_PASSWORD);
        }
        boolean save = iUserService.save(user);
        if (save) {
            return ErrorConstant.getSuccessResult("新增成功");
        }
        return ErrorConstant.getErrorResult(ErrorConstant.FAIL, "新增失败");
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        boolean update = iUserService.updateById(user);
        if (update) {
            return ErrorConstant.getSuccessResult("修改成功");
        }
        return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL, "修改失败");
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody User user) {
        if (null != user.getId()) {
            User byId = iUserService.getById(user.getId());
            if (null == byId){
                return ErrorConstant.getErrorResult(ErrorConstant.FAIL, "数据已刷新");
            }
            boolean delete = iUserService.removeById(user.getId());
            if (delete) {
                return ErrorConstant.getSuccessResult("删除成功");
            }
            return ErrorConstant.getErrorResult(ErrorConstant.FAIL, "删除失败");
        }
        return ErrorConstant.getErrorResult(ErrorConstant.PARAM_IS_NULL, "删除失败");
    }
}
