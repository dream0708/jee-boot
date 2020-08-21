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
    * 会员等级表
    */
@Data
@TableName(value = "ums_member_level")
public class UmsMemberLevel {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "growth_point")
    private Integer growthPoint;

    /**
     * 是否为默认等级：0->不是；1->是
     */
    @TableField(value = "default_status")
    private Integer defaultStatus;

    /**
     * 免运费标准
     */
    @TableField(value = "free_freight_point")
    private BigDecimal freeFreightPoint;

    /**
     * 每次评价获取的成长值
     */
    @TableField(value = "comment_growth_point")
    private Integer commentGrowthPoint;

    /**
     * 是否有免邮特权
     */
    @TableField(value = "priviledge_free_freight")
    private Integer priviledgeFreeFreight;

    /**
     * 是否有签到特权
     */
    @TableField(value = "priviledge_sign_in")
    private Integer priviledgeSignIn;

    /**
     * 是否有评论获奖励特权
     */
    @TableField(value = "priviledge_comment")
    private Integer priviledgeComment;

    /**
     * 是否有专享活动特权
     */
    @TableField(value = "priviledge_promotion")
    private Integer priviledgePromotion;

    /**
     * 是否有会员价格特权
     */
    @TableField(value = "priviledge_member_price")
    private Integer priviledgeMemberPrice;

    /**
     * 是否有生日特权
     */
    @TableField(value = "priviledge_birthday")
    private Integer priviledgeBirthday;

    @TableField(value = "note")
    private String note;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_GROWTH_POINT = "growth_point";

    public static final String COL_DEFAULT_STATUS = "default_status";

    public static final String COL_FREE_FREIGHT_POINT = "free_freight_point";

    public static final String COL_COMMENT_GROWTH_POINT = "comment_growth_point";

    public static final String COL_PRIVILEDGE_FREE_FREIGHT = "priviledge_free_freight";

    public static final String COL_PRIVILEDGE_SIGN_IN = "priviledge_sign_in";

    public static final String COL_PRIVILEDGE_COMMENT = "priviledge_comment";

    public static final String COL_PRIVILEDGE_PROMOTION = "priviledge_promotion";

    public static final String COL_PRIVILEDGE_MEMBER_PRICE = "priviledge_member_price";

    public static final String COL_PRIVILEDGE_BIRTHDAY = "priviledge_birthday";

    public static final String COL_NOTE = "note";
}