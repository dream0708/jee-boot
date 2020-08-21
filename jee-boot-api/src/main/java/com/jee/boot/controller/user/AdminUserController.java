package com.jee.boot.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jee.boot.controller.AbstractBaseController;
import com.jee.boot.datasource.first.entity.UmsAdmin;
import com.jee.boot.session.annotation.CurrentSession;
import com.jee.boot.session.user.CurrentUser;
import com.jee.boot.utils.Constants;
import com.jee.rest.base.annotation.rbac.Permissions;
import com.jee.rest.base.annotation.rbac.Roles;
import com.jee.rest.base.exception.BusinessException;
import com.jee.rest.base.response.biz.BizResponse;
import com.jee.rest.base.response.code.ResponseCode;
import com.jee.rest.base.util.MD5Util;
import com.jee.rest.base.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Date;


/**
 @author yaomengke
 @create 2020- 04 - 14 - 14:40

 */
@RestController
@Slf4j
public class AdminUserController extends AbstractBaseController{



	 /**
     * 
    	 *
    	 * @api {post} /admin/login  1登录
    	 * @apiVersion 1.0.0
    	 * @apiName login
    	 * @apiDescription 用户管理-登录
    	 * @apiGroup 用户管理
    	 * @apiParam {String} uuid 序列号
    	 * @apiParam {String} username 用户名
    	 * @apiParam {String} password 密码
    	 * @apiParam {String} yzm  验证码
    	 * @apiSuccess {Number} code 0 请求成功<br> 
    	 * @apiSuccess {String} msg  返回信息
    	 * @apiSuccess {String} hsession  header会话
    	 * @apiSuccess {String} hash  防重发token
    	 * @apiSuccessExample Success-Response:
    	 *     HTTP/1.1 200 OK
    	 *     {
    	 *       "code": 0,
    	 *       "msg": "请求成功",
    	 *       "hash": "abcd1233333" , 
    	 *       "hsession": "abcd1233333"
    	 *     }
     */
    
    @RequestMapping("/admin/login")
    public BizResponse login(
            @NotBlank(message = "用户名不为空") String username ,
            @NotBlank(message = "密码不为空")  String password ,
            @NotBlank(message = "验证码不为空") String yzm ,
            @NotBlank(message = "登录序列号") String uuid

    ){

        String sessionYzm =  sessionTemplate.get("JEE:ADMIN:YZM:" + uuid) ;
        if(StringUtils.isBlank(sessionYzm)){
            throw new BusinessException(ResponseCode.FAILUER_10 , "验证码过期") ;
        }
        if(!yzm.equalsIgnoreCase(sessionYzm)){
            throw new BusinessException(ResponseCode.FAILUER_10 , "验证码不正确") ;
        }
        sessionTemplate.delete("JEE:ADMIN:YZM:" + uuid);
        UmsAdmin userAdmin =  umsAdminService.getOne(
        		new LambdaQueryWrapper<UmsAdmin>().eq(UmsAdmin::getUsername , username)
                .eq(UmsAdmin::getPassword , MD5Util.MD5(password)));
        if(userAdmin == null){
           throw new BusinessException(ResponseCode.UNAUTHORIZED_401 , "用户名密码错误") ;
        }
        
        umsAdminService.update(new LambdaUpdateWrapper<UmsAdmin>()
        		.set(UmsAdmin::getLoginTime, new Date())
        		.eq(UmsAdmin::getUsername , username)) ; 
        
        
        userAdmin.setPassword("");
        CurrentUser user = new CurrentUser() ;
        user.setUserid(String.valueOf(userAdmin.getId()));
        user.setAccount(username);
        user.setUmsAdmin(userAdmin);
        user.setUserType("admin");
        user.setHash(RandomUtil.randomLower(4) + System.currentTimeMillis()) ;
        user.setSessionid(RandomUtil.random(100));

        String oldSessionid = sessionTemplate.get(com.jee.boot.utils.Constants.USERID_PREFIX + user.getUserid()) ;
        if(StringUtils.isNotBlank(oldSessionid)){
            sessionTemplate.delete(com.jee.boot.utils.Constants.SESSION_PREFIX  + oldSessionid); //清除历史
        }
        sessionTemplate.set(com.jee.boot.utils.Constants.USERID_PREFIX + user.getUserid() ,
                user.getSessionid() ,
                com.jee.boot.utils.Constants.SESSION_EXPIRE_SECONDS + 5
        );

        sessionTemplate.setAttribute(com.jee.boot.utils.Constants.SESSION_PREFIX + user.getSessionid() , Constants.USER_ATTR_STRING , user);
        sessionTemplate.setAttribute(com.jee.boot.utils.Constants.SESSION_PREFIX + user.getSessionid() , Constants.HASH , user.getHash());
        sessionTemplate.expire(com.jee.boot.utils.Constants.SESSION_PREFIX + user.getSessionid() , com.jee.boot.utils.Constants.SESSION_EXPIRE_SECONDS);


        return BizResponse.ok().hash(user.getHash()).append("hsession", user.getSessionid()) ;

    }
    
    /**
     * 
    	 *
    	 * @api {post} /admin/check_pwd  2校验密码
    	 * @apiVersion 1.0.0
    	 * @apiName checkPwd
    	 * @apiDescription 用户管理-校验密码
    	 * @apiGroup 用户管理
    	 * 
    	 * @apiUse req_hsession_hash
    	 * @apiParam {String} password 密码
    	 
    	 * @apiSuccess {Number} code 0 请求成功<br> 
    	 * @apiUse resp_hash
    	 * @apiSuccessExample Success-Response:
    	 *     HTTP/1.1 200 OK
    	 *     {
    	 *       "code": 0,
    	 *       "msg": "请求成功",
    	 *       "hash": "abcd1233333"
    	 *     }
     */
    
    @RequestMapping("/admin/check_pwd")
    public BizResponse checkPwd(
           @CurrentSession(hash = true) CurrentUser user ,
           @NotBlank String password

    ) {
    	
        UmsAdmin userAdmin =  umsAdminService.getOne(
                new LambdaQueryWrapper<UmsAdmin>().eq(UmsAdmin::getUsername , user.getAccount())
                .eq(UmsAdmin::getPassword , MD5Util.MD5(password)));
        if(userAdmin == null){
           throw new BusinessException(ResponseCode.UNAUTHORIZED_401 , "密码错误") ;
        }
    	
        user.setAttribute("checkPwdTimeout", LocalDateTime.now().plusMinutes(30));
    	//前后分离 会话保留 是不是很简单  详见 setAttribute方法 有很多重载
        return BizResponse.ok() ;
    }
    
    
    
    /**
     * 
    	 *
    	 * @api {post} /admin/reset_pwd  3重置密码
    	 * @apiVersion 1.0.0
    	 * @apiName resetPwd
    	 * @apiDescription 用户管理-重置密码
    	 * @apiGroup 用户管理
    	 * 
    	 * @apiUse req_hsession_hash
    	 * @apiParam {String} password 密码
    	 
    	 * @apiSuccess {Number} code 0 请求成功<br> 
    	 * @apiUse resp_hash
    	 * @apiSuccessExample Success-Response:
    	 *     HTTP/1.1 200 OK
    	 *     {
    	 *       "code": 0,
    	 *       "msg": "请求成功",
    	 *       "hash": "abcd1233333"
    	 *     }
     */
    
    
    @RequestMapping("/admin/reset_pwd")
    public BizResponse resetPwd(
           @CurrentSession(hash = true) CurrentUser user ,
           @NotBlank String password

    ) {
    	
    	LocalDateTime checkPwdTimeout = user.getAttribute("checkPwdTimeout", LocalDateTime.class) ;
    	if(checkPwdTimeout == null || checkPwdTimeout.compareTo(LocalDateTime.now()) < 0) {
    		throw new BusinessException(ResponseCode.UNAUTHORIZED_401 , "重置时间过期") ;
    	}
    	user.removeAttribute("checkPwdTimeout"); //清掉缓存
        boolean affects = umsAdminService.update(new LambdaUpdateWrapper<UmsAdmin>()
        		.set(UmsAdmin::getPassword, MD5Util.MD5(password))
        		.set(UmsAdmin::getLoginTime, new Date())
        		.eq(UmsAdmin::getUsername , user.getAccount())) ;
        return BizResponse.ok() ;
    }

    /**
     * 
    	RABC注解控制
    	完美抛弃调shiro spring security (这些权限框架太复杂，配置很麻烦）
     */

    
    
    /**
     * 
    	 *
    	 * @api {post} /admin/info  4获取管理员信息
    	 * @apiVersion 1.0.0
    	 * @apiName getUserInfo
    	 * @apiDescription 用户管理-获取管理员信息
    	 * @apiGroup 用户管理
    	 * 
    	 * @apiUse req_hsession
    	 * @apiSuccess {Number} code 0 请求成功<br> 
    	 * @apiUse resp_hash
    	 * 
    	 * @apiSuccess {Object} result 信息
    	 * @apiSuccess {String} result.id id
    	 * @apiSuccess {String} result.icon 头像
    	 * @apiSuccess {String} result.email 邮箱
    	 * @apiSuccess {String} result.nickName 昵称
    *
     */
    
    
    @RequestMapping("/admin/info")
    public BizResponse getUserInfo(@CurrentSession CurrentUser user){
    	log.info(">>>login user enter>>>>>");
    	
        return BizResponse.ok().result(user.getUmsAdmin()) ;
    }
    
    @RequestMapping("/admin/roles")
    public BizResponse checkUserRoles(@CurrentSession @Roles({"admin"}) CurrentUser user){
    	log.info(">>>roles contains [admin]>>>>>");
    	
        return BizResponse.ok().result(user) ;
    }

    @RequestMapping("/admin/permissions")
    public BizResponse checkUserPermissions(@CurrentSession @Permissions({"add"}) CurrentUser user){
    	log.info(">>>permissions contains [add]>>>>>");
    	
    	return BizResponse.ok().result(user) ;
    }
    
    
    /**
     * 
    	 *
    	 * @api {post} /admin/info  5角色权限验证
    	 * @apiVersion 1.0.0
    	 * @apiName checkUserRights
    	 * @apiDescription 用户管理-角色权限验证
    	 * @apiGroup 用户管理
    	 * @apiPermission roles[admin&guest]  &  permissions[query]
    	 * 
    	 * @apiUse req_hsession
    	 * @apiSuccess {Number} code 0 请求成功<br> 
    	 * @apiUse resp_hash
    	 * 
    	 * @apiSuccess {Object} result 信息
    	 * @apiSuccess {String} result.id id
    	 * @apiSuccess {String} result.icon 头像
    	 * @apiSuccess {String} result.email 邮箱
    	 * @apiSuccess {String} result.nickName 昵称
    *
     */
    

    @RequestMapping("/admin/rights")
    public BizResponse checkUserRights(@CurrentSession @Roles({"admin" , "guest"}) @Permissions({"query"}) CurrentUser user){
    	log.info(">>>roles contains [admin] and permissions contains [add]>>>>>");
    	
    	return BizResponse.ok().result(user) ;
    }
    
    
  



}