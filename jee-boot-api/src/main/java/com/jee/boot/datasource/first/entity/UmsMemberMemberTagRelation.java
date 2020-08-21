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
    * 用户和标签关系表
    */
@Data
@TableName(value = "ums_member_member_tag_relation")
public class UmsMemberMemberTagRelation {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "member_id")
    private Long memberId;

    @TableField(value = "tag_id")
    private Long tagId;

    public static final String COL_ID = "id";

    public static final String COL_MEMBER_ID = "member_id";

    public static final String COL_TAG_ID = "tag_id";
}