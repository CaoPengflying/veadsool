package com.cpf.veadsool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cpf.veadsool.base.BusinessException;
import com.cpf.veadsool.entity.User;
import com.cpf.veadsool.mapper.UserMapper;
import com.cpf.veadsool.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息表 服务实现类
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User login(User user) {
        if (StringUtils.isEmpty(user.getPhoneNumber())){
            throw new BusinessException("手机号不能为空");
        }
        if (StringUtils.isEmpty(user.getPassword())){
            throw new BusinessException("密码不能为空");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getPhoneNumber, user.getPhoneNumber());
        User one = this.getOne(queryWrapper);
        if (one == null){
            throw new BusinessException("该用户不存在");
        }
        if (!one.getPassword().equals(user.getPassword())){
            throw new BusinessException("密码不正确，请重试!");
        }
        return one;
    }
}
