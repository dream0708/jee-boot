package com.jee.boot.datasource.first.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jee.boot.datasource.first.entity.UmsAdminRoleRelation;
import com.jee.boot.datasource.first.entity.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
   @author yaomengke
   @create 2020- 08 - 18 - 17:03 
    
 */
public interface UmsAdminRoleRelationMapper extends BaseMapper<UmsAdminRoleRelation> {


    public List<UmsRole> queryUmsRoleByAdminId(@Param("adminId") String adminId) ;
}