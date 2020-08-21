package com.jee.boot.datasource.first.service.impl;

import com.alicp.jetcache.anno.Cached;
import com.jee.boot.datasource.first.entity.UmsRole;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jee.boot.datasource.first.entity.UmsAdminRoleRelation;
import com.jee.boot.datasource.first.dao.UmsAdminRoleRelationMapper;
import com.jee.boot.datasource.first.service.UmsAdminRoleRelationService;

/**
   @author yaomengke
   @create 2020- 08 - 18 - 17:03 
    
 */
@Service
public class UmsAdminRoleRelationServiceImpl extends ServiceImpl<UmsAdminRoleRelationMapper, UmsAdminRoleRelation> implements UmsAdminRoleRelationService{


    @Override
    public List<UmsRole> queryUmsRoleByAdminId(String adminId) {
        return this.baseMapper.queryUmsRoleByAdminId(adminId) ;
    }
    @Override
    @Cached(name = "cache:getDistinctRolesByAdminId:" , key = "#adminId" , expire = 3600)
    public List<String>  getDistinctRolesByAdminId(String adminId) {
        return queryUmsRoleByAdminId(adminId).stream().map(l ->l.getName()).distinct().collect(Collectors.toList());
    }
}
