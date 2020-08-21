package com.jee.boot.datasource.second.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
   @author yaomengke
   @create 2020- 08 - 19 - 15:53 
    
 */
@Data
@TableName(value = "t_dict")
public class Dict {
    @TableField(value = "sid")
    private Integer sid;

    @TableField(value = "name")
    private String name;

    @TableField(value = "value")
    private String value;

    @TableField(value = "type")
    private String type;

    @TableField(value = "sort")
    private Integer sort;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "inserttime")
    private Date inserttime;

    @TableField(value = "updatetime")
    private Date updatetime;

    public static final String COL_SID = "sid";

    public static final String COL_NAME = "name";

    public static final String COL_VALUE = "value";

    public static final String COL_TYPE = "type";

    public static final String COL_SORT = "sort";

    public static final String COL_REMARK = "remark";

    public static final String COL_INSERTTIME = "inserttime";

    public static final String COL_UPDATETIME = "updatetime";
}