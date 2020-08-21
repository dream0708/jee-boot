package com.jee.boot.datasource.first.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
   @author yaomengke
   @create 2020- 08 - 18 - 17:03 
    
 */

/**
    * 后台用户和角色关系表
    */
@Data
@TableName(value = "ums_admin_role_relation")
public class UmsAdminRoleRelation {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "admin_id")
    private Long adminId;

    @TableField(value = "role_id")
    private Long roleId;

    public static final String COL_ID = "id";

    public static final String COL_ADMIN_ID = "admin_id";

    public static final String COL_ROLE_ID = "role_id";
}