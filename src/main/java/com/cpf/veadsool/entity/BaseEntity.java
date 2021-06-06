package com.cpf.veadsool.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author caopengflying
 * @time 2020/3/3
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 创建人
     */
    @TableField("create_user")
    private Integer createUser;
    /**
     * 创建人姓名
     */
    @TableField(exist = false)
    private String createUserName;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @TableField("update_user")
    private Integer updateUser;
    /**
     * 修改人
     */
    @TableField(exist = false)
    private String updateUserName;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                '}';
    }

}
