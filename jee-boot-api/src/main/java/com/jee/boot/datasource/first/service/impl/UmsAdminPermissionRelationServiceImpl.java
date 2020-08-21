package com.jee.boot.datasource.first.service.impl;

import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jee.boot.datasource.first.dao.UmsAdminPermissionRelationMapper;
import com.jee.boot.datasource.first.entity.UmsAdminPermissionRelation;
import com.jee.boot.datasource.first.entity.UmsPermission;
import com.jee.boot.datasource.first.service.UmsAdminPermissionRelationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
   @author yaomengke
   @create 2020- 08 - 18 - 17:03 
    
 */
@Service
public class UmsAdminPermissionRelationServiceImpl extends ServiceImpl<UmsAdminPermissionRelationMapper, UmsAdminPermissionRelation> implements UmsAdminPermissionRelationService{


    @Override
    public List<UmsPermission> queryUmsPermissionByAdminId(String adminId) {
        return  this.baseMapper.queryUmsPermissionByAdminId(adminId) ;
    }

    @Override
    public List<UmsPermission> queryUmsRolePermissionByAdminId(String adminId) {
        return this.baseMapper.queryUmsRolePermissionByAdminId(adminId) ;
    }

    @Override
    @Cached(name = "cache:getDistinctUmsPermissionsByAdminId:" , key = "#adminId" , expire =  3600)
    public List<String>  getDistinctUmsPermissionsByAdminId(String adminId) {

       List<UmsPermission> list = queryUmsPermissionByAdminId(adminId) ;

       list.addAll(queryUmsRolePermissionByAdminId(adminId)) ;

       return list.stream().map(l -> l.getName()).distinct().collect(Collectors.toList()) ;

    }
}
