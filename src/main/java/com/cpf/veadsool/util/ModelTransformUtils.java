package com.cpf.veadsool.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author CaoPengfei
 * @time 2018年11月17日
 * @descript 数据类型的转换工具 ExtModel  和 Model之间的相互转换
 */

public class ModelTransformUtils {
    /**
     * 将子类中所有的属性设置到父类中
     *
     * @param sup  父类
     * @param base 子类
     * @return
     * @throws IllegalAccessException
     */
    public static Object setValueToSuper(Object sup, Object base) {
        //获得子类中的所有属性和属性值并设置好到父类中
        Class<?> baseClass = base.getClass();
        Field[] declaredFields = baseClass.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String filedName = field.getName();
            Class<?> type = field.getType();
            String methodName = "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
            Method method = null;
            try {
                method = baseClass.getDeclaredMethod(methodName, type);
                method.invoke(sup, field.get(base));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("扩展类型转化失败");
            }
        }
        return sup;
    }

    /**
     * 将基础模型集合转化为扩展类型模型集合
     *
     * @param extModels 存储转化后的结果
     * @param extModel  扩展模型
     * @param models    带转化的模型集合
     */
    public static List modelsToExtModels(List extModels, Object extModel, List models) {
        Class<?> extModelClass = extModel.getClass();
        models.forEach(m -> {
            try {
                Object o = extModelClass.newInstance();
                setValueToSuper(o, m);
                extModels.add(o);
            } catch (Exception e) {
                throw new RuntimeException("类型转换");
            }
        });
        return extModels;
    }

    /**
     * 将扩展模型集合转化为基础类型模型集合
     *
     * @param models    存储转化后的基础模型集合
     * @param extModels 带转化的扩展模型集合
     * @param model     模型
     */
    public static List extModelsToModels(List models, Object model, List extModels) {
        extModels.forEach(extModel -> {
            try {
                Object o = extModel;
                models.add(o);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("数据模型转换失败");
            }
        });
        return models;
    }

    /**
     * 单个对象转换
     *
     * @param sourceModel 入参对象
     * @param clazz       出参类型
     * @return
     */
    public static <T> T exchangeClass(Object sourceModel, Class clazz) {
        return (T) exchangeClassList(Lists.newArrayList(sourceModel), clazz).get(0);
    }

    /**
     * list对象转换
     *
     * @param sourceModelList 入参对象
     * @param clazz           出参类型
     * @return
     */
    public static List exchangeClassList(List sourceModelList, Class clazz) {
        return JSONArray.parseArray(JSON.toJSONString(sourceModelList)).toJavaList(clazz);
    }


}
