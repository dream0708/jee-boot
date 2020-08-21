package com.jee.boot.datasource.first.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jee.boot.datasource.first.entity.UmsAdminPermissionRelation;
import com.jee.boot.datasource.first.entity.UmsPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
   @author yaomengke
   @create 2020- 08 - 18 - 17:03 
    
 */
public interface UmsAdminPermissionRelationMapper extends BaseMapper<UmsAdminPermissionRelation> {

    @Select("select b.*\n" +
            "        from ums_admin_permission_relation a\n" +
            "        inner join ums_permission b on a.permission_id = b.id\n" +
            "        where b.status = 1\n" +
            "        and a.admin_id = #{adminId,jdbcType=VARCHAR}")
    public List<UmsPermission> queryUmsPermissionByAdminId(@Param("adminId") String adminId) ;

    public List<UmsPermission> queryUmsRolePermissionByAdminId(@Param("adminId") String adminId) ;
}