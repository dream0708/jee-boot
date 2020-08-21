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
    * 积分消费设置
    */
@Data
@TableName(value = "ums_integration_consume_setting")
public class UmsIntegrationConsumeSetting {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 每一元需要抵扣的积分数量
     */
    @TableField(value = "deduction_per_amount")
    private Integer deductionPerAmount;

    /**
     * 每笔订单最高抵用百分比
     */
    @TableField(value = "max_percent_per_order")
    private Integer maxPercentPerOrder;

    /**
     * 每次使用积分最小单位100
     */
    @TableField(value = "use_unit")
    private Integer useUnit;

    /**
     * 是否可以和优惠券同用；0->不可以；1->可以
     */
    @TableField(value = "coupon_status")
    private Integer couponStatus;

    public static final String COL_ID = "id";

    public static final String COL_DEDUCTION_PER_AMOUNT = "deduction_per_amount";

    public static final String COL_MAX_PERCENT_PER_ORDER = "max_percent_per_order";

    public static final String COL_USE_UNIT = "use_unit";

    public static final String COL_COUPON_STATUS = "coupon_status";
}