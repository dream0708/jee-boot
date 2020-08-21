package com.jee.boot.datasource.first.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
   @author yaomengke
   @create 2020- 08 - 18 - 17:03 
    
 */

/**
    * 会员统计信息
    */
@Data
@TableName(value = "ums_member_statistics_info")
public class UmsMemberStatisticsInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "member_id")
    private Long memberId;

    /**
     * 累计消费金额
     */
    @TableField(value = "consume_amount")
    private BigDecimal consumeAmount;

    /**
     * 订单数量
     */
    @TableField(value = "order_count")
    private Integer orderCount;

    /**
     * 优惠券数量
     */
    @TableField(value = "coupon_count")
    private Integer couponCount;

    /**
     * 评价数
     */
    @TableField(value = "comment_count")
    private Integer commentCount;

    /**
     * 退货数量
     */
    @TableField(value = "return_order_count")
    private Integer returnOrderCount;

    /**
     * 登录次数
     */
    @TableField(value = "login_count")
    private Integer loginCount;

    /**
     * 关注数量
     */
    @TableField(value = "attend_count")
    private Integer attendCount;

    /**
     * 粉丝数量
     */
    @TableField(value = "fans_count")
    private Integer fansCount;

    @TableField(value = "collect_product_count")
    private Integer collectProductCount;

    @TableField(value = "collect_subject_count")
    private Integer collectSubjectCount;

    @TableField(value = "collect_topic_count")
    private Integer collectTopicCount;

    @TableField(value = "collect_comment_count")
    private Integer collectCommentCount;

    @TableField(value = "invite_friend_count")
    private Integer inviteFriendCount;

    /**
     * 最后一次下订单时间
     */
    @TableField(value = "recent_order_time")
    private Date recentOrderTime;

    public static final String COL_ID = "id";

    public static final String COL_MEMBER_ID = "member_id";

    public static final String COL_CONSUME_AMOUNT = "consume_amount";

    public static final String COL_ORDER_COUNT = "order_count";

    public static final String COL_COUPON_COUNT = "coupon_count";

    public static final String COL_COMMENT_COUNT = "comment_count";

    public static final String COL_RETURN_ORDER_COUNT = "return_order_count";

    public static final String COL_LOGIN_COUNT = "login_count";

    public static final String COL_ATTEND_COUNT = "attend_count";

    public static final String COL_FANS_COUNT = "fans_count";

    public static final String COL_COLLECT_PRODUCT_COUNT = "collect_product_count";

    public static final String COL_COLLECT_SUBJECT_COUNT = "collect_subject_count";

    public static final String COL_COLLECT_TOPIC_COUNT = "collect_topic_count";

    public static final String COL_COLLECT_COMMENT_COUNT = "collect_comment_count";

    public static final String COL_INVITE_FRIEND_COUNT = "invite_friend_count";

    public static final String COL_RECENT_ORDER_TIME = "recent_order_time";
}