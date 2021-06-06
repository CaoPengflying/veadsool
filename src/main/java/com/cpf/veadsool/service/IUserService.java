package com.cpf.veadsool.service;

import com.cpf.veadsool.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户基本信息表 服务类
 * </p>
 *
 * @author caopengflying
 * @since 2020-03-15
 */
public interface IUserService extends IService<User> {
    /**
     * 登陆
     * @param user
     * @return
     */
    User login(User user);
}
