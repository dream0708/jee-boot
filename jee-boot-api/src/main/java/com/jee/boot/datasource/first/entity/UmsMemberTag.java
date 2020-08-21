package com.jee.boot.datasource.first.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import lombok.Data;

/**
   @author yaomengke
   @create 2020- 08 - 18 - 17:03 
    
 */

/**
    * 用户标签表
    */
@Data
@TableName(value = "ums_member_tag")
public class UmsMemberTag {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    private String name;

    /**
     * 自动打标签完成订单数量
     */
    @TableField(value = "finish_order_count")
    private Integer finishOrderCount;

    /**
     * 自动打标签完成订单金额
     */
    @TableField(value = "finish_order_amount")
    private BigDecimal finishOrderAmount;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_FINISH_ORDER_COUNT = "finish_order_count";

    public static final String COL_FINISH_ORDER_AMOUNT = "finish_order_amount";
}