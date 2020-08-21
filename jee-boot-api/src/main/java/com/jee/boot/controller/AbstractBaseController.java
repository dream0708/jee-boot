package com.jee.boot.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.jee.boot.datasource.first.dao.UmsAdminPermissionRelationMapper;
import com.jee.boot.datasource.first.dao.UmsAdminRoleRelationMapper;
import com.jee.boot.datasource.first.service.*;
import com.jee.rest.base.session.template.SessionTemplate;
import com.jee.rest.base.util.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Validated
public abstract class AbstractBaseController {
	
	
	@Autowired
	protected RedisTemplate<String, String> redisTemplate2 ;
	
	@Resource
	protected SessionTemplate sessionTemplate ;

	@Resource
	protected UmsAdminService umsAdminService ;


	@Autowired
	protected DefaultKaptcha defaultKaptcha;

	@Autowired
	protected UmsPermissionService umsPermissionService ;

	@Autowired
	protected UmsRoleService umsRoleService ;

	@Autowired
	protected UmsAdminPermissionRelationService umsAdminPermissionRelationService;

	@Autowired
	protected UmsAdminRoleRelationService umsAdminRoleRelationService;



	protected static final HttpHeaders HTTP_HEADERS   ;
	
	static {
		HTTP_HEADERS = new HttpHeaders();
		HTTP_HEADERS.set("Pragma", "No-cache");
		HTTP_HEADERS.set("Cache-Control", "No-cache");
		HTTP_HEADERS.setDate("Expires", 0);
		HTTP_HEADERS.setContentType(MediaType.IMAGE_JPEG);
	}
	
	
	
	
	
	protected  HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	protected  String getRemoteIp() {
		HttpServletRequest request = getHttpServletRequest() ;
		if(request != null) {
			return IpUtils.getRealIp(request) ;
		}
		return null ;
	}


}
