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
    * 会员表
    */
@Data
@TableName(value = "ums_member")
public class UmsMember {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "member_level_id")
    private Long memberLevelId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 手机号码
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 帐号启用状态:0->禁用；1->启用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 注册时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 头像
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 性别：0->未知；1->男；2->女
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 所做城市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 职业
     */
    @TableField(value = "job")
    private String job;

    /**
     * 个性签名
     */
    @TableField(value = "personalized_signature")
    private String personalizedSignature;

    /**
     * 用户来源
     */
    @TableField(value = "source_type")
    private Integer sourceType;

    /**
     * 积分
     */
    @TableField(value = "integration")
    private Integer integration;

    /**
     * 成长值
     */
    @TableField(value = "growth")
    private Integer growth;

    /**
     * 剩余抽奖次数
     */
    @TableField(value = "luckey_count")
    private Integer luckeyCount;

    /**
     * 历史积分数量
     */
    @TableField(value = "history_integration")
    private Integer historyIntegration;

    public static final String COL_ID = "id";

    public static final String COL_MEMBER_LEVEL_ID = "member_level_id";

    public static final String COL_USERNAME = "username";

    public static final String COL_PASSWORD = "password";

    public static final String COL_NICKNAME = "nickname";

    public static final String COL_PHONE = "phone";

    public static final String COL_STATUS = "status";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_ICON = "icon";

    public static final String COL_GENDER = "gender";

    public static final String COL_BIRTHDAY = "birthday";

    public static final String COL_CITY = "city";

    public static final String COL_JOB = "job";

    public static final String COL_PERSONALIZED_SIGNATURE = "personalized_signature";

    public static final String COL_SOURCE_TYPE = "source_type";

    public static final String COL_INTEGRATION = "integration";

    public static final String COL_GROWTH = "growth";

    public static final String COL_LUCKEY_COUNT = "luckey_count";

    public static final String COL_HISTORY_INTEGRATION = "history_integration";
}