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
    * 会员收货地址表
    */
@Data
@TableName(value = "ums_member_receive_address")
public class UmsMemberReceiveAddress {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "member_id")
    private Long memberId;

    /**
     * 收货人名称
     */
    @TableField(value = "name")
    private String name;

    @TableField(value = "phone_number")
    private String phoneNumber;

    /**
     * 是否为默认
     */
    @TableField(value = "default_status")
    private Integer defaultStatus;

    /**
     * 邮政编码
     */
    @TableField(value = "post_code")
    private String postCode;

    /**
     * 省份/直辖市
     */
    @TableField(value = "province")
    private String province;

    /**
     * 城市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 区
     */
    @TableField(value = "region")
    private String region;

    /**
     * 详细地址(街道)
     */
    @TableField(value = "detail_address")
    private String detailAddress;

    public static final String COL_ID = "id";

    public static final String COL_MEMBER_ID = "member_id";

    public static final String COL_NAME = "name";

    public static final String COL_PHONE_NUMBER = "phone_number";

    public static final String COL_DEFAULT_STATUS = "default_status";

    public static final String COL_POST_CODE = "post_code";

    public static final String COL_PROVINCE = "province";

    public static final String COL_CITY = "city";

    public static final String COL_REGION = "region";

    public static final String COL_DETAIL_ADDRESS = "detail_address";
}