package com.jee.boot.datasource.first.service;

import com.jee.boot.datasource.first.entity.UmsAdminPermissionRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jee.boot.datasource.first.entity.UmsPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
@author yaomengke
@create 2020- 08 - 18 - 17:03

*/
public interface UmsAdminPermissionRelationService extends IService<UmsAdminPermissionRelation>{



    public List<UmsPermission> queryUmsPermissionByAdminId(String adminId) ;

    public List<UmsPermission> queryUmsRolePermissionByAdminId( String adminId) ;

    public List<String>  getDistinctUmsPermissionsByAdminId(String adminId) ;

}
