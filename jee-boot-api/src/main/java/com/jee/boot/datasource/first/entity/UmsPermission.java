package com.jee.boot.datasource.first.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
   @author yaomengke
   @create 2020- 08 - 18 - 17:03 
    
 */

/**
    * 后台用户权限表
    */
@Data
@TableName(value = "ums_permission")
public class UmsPermission {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级权限id
     */
    @TableField(value = "pid")
    private Long pid;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 权限值
     */
    @TableField(value = "value")
    private String value;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 前端资源路径
     */
    @TableField(value = "uri")
    private String uri;

    /**
     * 启用状态；0->禁用；1->启用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    public static final String COL_ID = "id";

    public static final String COL_PID = "pid";

    public static final String COL_NAME = "name";

    public static final String COL_VALUE = "value";

    public static final String COL_ICON = "icon";

    public static final String COL_TYPE = "type";

    public static final String COL_URI = "uri";

    public static final String COL_STATUS = "status";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_SORT = "sort";
}