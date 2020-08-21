package com.jee.boot.test ;


import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jee.boot.App;
import com.jee.boot.datasource.first.dao.UmsAdminMapper;
import com.jee.boot.datasource.first.dao.UmsAdminPermissionRelationMapper;
import com.jee.boot.datasource.first.entity.UmsAdmin;
import com.jee.boot.datasource.first.entity.UmsAdminPermissionRelation;
import com.jee.boot.datasource.first.entity.UmsPermission;
import com.jee.boot.datasource.first.service.UmsAdminPermissionRelationService;
import com.jee.rest.base.collections.util.HashMaps;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class SpringBootTestExample  {
	
	

	@Autowired
	private ApplicationContext  act ;

	@Autowired
	private UmsAdminPermissionRelationMapper umsAdminPermissionRelationMapper ;
	@Autowired
	private UmsAdminMapper umsAdminMapper ;
	@Autowired
	private UmsAdminPermissionRelationService umsAdminPermissionRelationService ;
	
    @Test
	public void testmsAdminPermissionRelationMapper() {

    	try{
    		
    		
    		
    		Map<String , Object> parms = new HashMaps<>() ;
			System.out.println(umsAdminMapper.selectList(new QueryWrapper<UmsAdmin>().lambda()
			.eq(UmsAdmin::getId , 1L))) ;
			System.out.println(umsAdminPermissionRelationMapper.selectList(
					new QueryWrapper<UmsAdminPermissionRelation>()
					.lambda().eq(UmsAdminPermissionRelation::getId , 1L ) ));
			List<UmsPermission> list = umsAdminPermissionRelationMapper.queryUmsRolePermissionByAdminId("1") ;
			System.out.println(JSON.toJSONString(list)) ;
		}catch (Exception e){
    		e.printStackTrace();
		}
	}
	
}
