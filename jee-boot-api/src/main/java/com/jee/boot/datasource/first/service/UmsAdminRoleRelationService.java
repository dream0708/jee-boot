package com.jee.boot.datasource.first.service;

import com.jee.boot.datasource.first.entity.UmsAdminRoleRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jee.boot.datasource.first.entity.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
@author yaomengke
@create 2020- 08 - 18 - 17:03

*/
public interface UmsAdminRoleRelationService extends IService<UmsAdminRoleRelation>{

    public List<UmsRole> queryUmsRoleByAdminId(String adminId) ;

    public List<String>  getDistinctRolesByAdminId(String adminId) ;
}
