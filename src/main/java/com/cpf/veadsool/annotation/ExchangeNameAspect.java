package com.cpf.veadsool.annotation;

import com.alibaba.fastjson.JSON;
import com.cpf.veadsool.base.Result;
import com.cpf.veadsool.constants.ApplicationConstants;
import com.cpf.veadsool.entity.User;
import com.cpf.veadsool.service.IUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * date 2020/5/6
 *
 * @author caopengflying
 */
@Component
@Aspect
public class ExchangeNameAspect {
    @Resource
    private IUserService userService;

    /**
     * 以自定义 @NeedApiLog 注解为切点
     */
    @Pointcut("@annotation(com.cpf.veadsool.annotation.NeedExchangeName)")
    public void exchangeName() {
    }

    @Around("exchangeName()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object outParameter = joinPoint.proceed();
        List<Integer> userIdList = Lists.newArrayList();
        if (outParameter instanceof Result){
            Object t = ((Result) outParameter).getT();
            if (t instanceof Map){
                Object list = ((Map) t).get("list");
                setName(list);
            }else {
                setName(t);
            }
        }
        if (outParameter instanceof List){
            setName(outParameter);
        }
        return outParameter;
    }

    private void setName(Object outParameter) {
        List<Integer> userIdList = Lists.newArrayList();
        if (outParameter instanceof List) {
            ((List) outParameter).forEach(obj -> {
                Field createUserField = null;
                try {
                    createUserField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField(ApplicationConstants.CREATE_USER_KEY);
                    if (null != createUserField) {
                        Method m = obj.getClass().getMethod(
                                "get" + getMethodName(createUserField.getName()));
                        Integer createUser = (Integer) m.invoke(obj);
                        userIdList.add(createUser);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Field updateUserField = null;
                try {
                    updateUserField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField(ApplicationConstants.UPDATE_USER_KEY);
                    if (null != updateUserField) {
                        Method m = obj.getClass().getMethod(
                                "get" + getMethodName(updateUserField.getName()));
                        Integer updateUser = (Integer) m.invoke(obj);
                        userIdList.add(updateUser);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }

        if (CollectionUtils.isNotEmpty(userIdList)) {
            List<User> users = userService.listByIds(userIdList);
            Map<Integer, String> userMap = users.parallelStream().collect(Collectors.toMap(User::getId, User::getName));
            if (outParameter instanceof List) {
                ((List) outParameter).forEach(obj -> {
                    Field createUserField = null;
                    try {
                        createUserField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField(ApplicationConstants.CREATE_USER_KEY);
                        if (null != createUserField) {
                            Method m = obj.getClass().getMethod(
                                    "get" + getMethodName(createUserField.getName()));
                            Integer createUser = (Integer) m.invoke(obj);
                            if (userMap.containsKey(createUser)) {
                                String createUserName = userMap.get(createUser);
                                Field createUserNameField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField(ApplicationConstants.CREATE_USER_NAME_KEY);
                                Method setMethod = obj.getClass().getSuperclass().getSuperclass().getDeclaredMethod(
                                        "set" + getMethodName(createUserNameField.getName()),createUserNameField.getType());
                                setMethod.invoke(obj, createUserName);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Field updateUserField = null;
                    try {
                        updateUserField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField(ApplicationConstants.UPDATE_USER_KEY);
                        if (null != updateUserField) {
                            Method m = obj.getClass().getMethod(
                                    "get" + getMethodName(updateUserField.getName()));
                            Integer updateUser = (Integer) m.invoke(obj);
                            if (userMap.containsKey(updateUser)) {
                                String updateUserName = userMap.get(updateUser);
                                Field updateUserNameField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField(ApplicationConstants.UPDATE_USER_NAME_KEY);
                                Method setMethod = obj.getClass().getSuperclass().getSuperclass().getMethod(
                                        "set" + getMethodName(updateUserNameField.getName()),updateUserNameField.getType());
                                setMethod.invoke(obj, updateUserName);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            }
        }
    }

    /**
     * 首字母转大写
     * @param fildeName
     * @return
     */
    private static String getMethodName(String fildeName){
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
