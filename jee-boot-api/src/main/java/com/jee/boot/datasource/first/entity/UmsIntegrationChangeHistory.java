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
    * 积分变化历史记录表
    */
@Data
@TableName(value = "ums_integration_change_history")
public class UmsIntegrationChangeHistory {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "member_id")
    private Long memberId;

    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 改变类型：0->增加；1->减少
     */
    @TableField(value = "change_type")
    private Integer changeType;

    /**
     * 积分改变数量
     */
    @TableField(value = "change_count")
    private Integer changeCount;

    /**
     * 操作人员
     */
    @TableField(value = "operate_man")
    private String operateMan;

    /**
     * 操作备注
     */
    @TableField(value = "operate_note")
    private String operateNote;

    /**
     * 积分来源：0->购物；1->管理员修改
     */
    @TableField(value = "source_type")
    private Integer sourceType;

    public static final String COL_ID = "id";

    public static final String COL_MEMBER_ID = "member_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_CHANGE_TYPE = "change_type";

    public static final String COL_CHANGE_COUNT = "change_count";

    public static final String COL_OPERATE_MAN = "operate_man";

    public static final String COL_OPERATE_NOTE = "operate_note";

    public static final String COL_SOURCE_TYPE = "source_type";
}