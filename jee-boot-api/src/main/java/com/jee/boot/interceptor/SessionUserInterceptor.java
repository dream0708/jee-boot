package com.jee.boot.interceptor ;

import com.jee.boot.controller.AbstractBaseController;
import com.jee.boot.session.annotation.CurrentSession;
import com.jee.boot.session.user.CurrentUser;
import com.jee.boot.utils.Constants;
import com.jee.rest.base.annotation.rbac.Logical;
import com.jee.rest.base.annotation.rbac.Permissions;
import com.jee.rest.base.annotation.rbac.Roles;
import com.jee.rest.base.exception.BusinessException;
import com.jee.rest.base.response.code.ResponseCode;
import com.jee.rest.base.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;
import java.util.List;

/**
/**
@author yaomengke
@create 2020- 04 - 14 - 14:59

 */
@Service("sessionUserInterceptor")
@Slf4j
public class SessionUserInterceptor extends AbstractBaseController implements HandlerMethodArgumentResolver{


    
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterType().isAssignableFrom(CurrentUser.class)
                && parameter.hasParameterAnnotation(CurrentSession.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // String sessionid = webRequest.getParameter(Constants.SESSION_NAME);
        String sessionid = webRequest.getHeader(Constants.HEADER_NAME);
        if (StringUtils.isBlank(sessionid)) {
            throw new BusinessException(ResponseCode.NO_LOGIN_600, "会话标示不为空");
        }

        CurrentUser user =  sessionTemplate.getAttribute(Constants.SESSION_PREFIX + sessionid , Constants.USER_ATTR_STRING ,  CurrentUser.class) ;
        if(user == null || StringUtils.isBlank(user.getUserid())){
            throw new BusinessException(ResponseCode.NO_LOGIN_600 , "登录超时,请重新登录") ;
        }
        ThreadContext.put(Constants.USERID, user.getUserid()); //埋点
        ThreadContext.put(Constants.MOBILE_NAME, user.getMobile());//埋点
        webRequest.setAttribute(Constants.USERID, user.getUserid(), NativeWebRequest.SCOPE_REQUEST);//埋点
        CurrentSession session = parameter.getParameterAnnotation(CurrentSession.class);
        if(session == null){
            throw new BusinessException(ResponseCode.SYSTEM_ERROR_504 , "拦截器配置错误") ;
        }
        if(!session.logger()){
            webRequest.setAttribute(Constants.LOGGER, Constants.LOGGER_FLAG , WebRequest.SCOPE_REQUEST );
        }
        
        /**防止重发统一控制 **/
        if(session.hash()){
            // String reqHash = webRequest.getParameter(Constants.HASH) ;
            String reqHash = webRequest.getHeader(Constants.HASH) ;
            if(StringUtils.isBlank(reqHash)){
                throw new BusinessException(ResponseCode.HASH_UNAUTHORIZED_632 , "需要hash数据") ;
            }
            String sessionHash = sessionTemplate.getAttribute(Constants.SESSION_PREFIX  + sessionid, Constants.HASH) ;
            if(StringUtils.isBlank(sessionHash)){
                throw new BusinessException(ResponseCode.HASH_UNAUTHORIZED_632 , "hash过期,请重新获取") ;
            }
            String newHash = RandomUtil.randomLower(4) + System.currentTimeMillis();
            sessionTemplate.setAttribute(Constants.SESSION_PREFIX  + sessionid , Constants.HASH , newHash);
            webRequest.setAttribute(Constants.HASH, newHash, WebRequest.SCOPE_REQUEST );
            user.setHash(newHash);
            if(!reqHash.equals(sessionHash)){
                log.error("request hash:{} , but session hash:{} , those hashes not equals " , reqHash , sessionHash);
                throw new BusinessException(ResponseCode.HASH_UNAUTHORIZED_632 , "请勿重复提交") ;
            }
        }
        
        Roles roles = parameter.getParameterAnnotation(Roles.class) ;
        if(roles != null && ArrayUtils.isNotEmpty(roles.value())){
            final List<String> userRoles = umsAdminRoleRelationService.getDistinctRolesByAdminId(user.getUserid()) ;
            Logical logical = roles.logical() ;
            if(logical == Logical.AND && !Arrays.stream(roles.value()).allMatch(userRoles::contains)){
                throw new BusinessException(ResponseCode.ROLES_UNAUTHORIZED_621 , "不满足角色要求，拒绝访问") ;
            }
            if(logical == Logical.OR && Arrays.stream(roles.value()).noneMatch(userRoles::contains)){
                throw new BusinessException(ResponseCode.ROLES_UNAUTHORIZED_621 , "不满足角色要求，拒绝访问") ;
            }
        }

        Permissions permissions = parameter.getParameterAnnotation(Permissions.class) ;
        if(permissions != null && ArrayUtils.isNotEmpty(permissions.value())){
            final List<String> userPermissions = umsAdminPermissionRelationService.getDistinctUmsPermissionsByAdminId(user.getUserid()) ;
            Logical logical = permissions.logical() ;
            if(logical == Logical.AND && !Arrays.stream(permissions.value()).allMatch(userPermissions::contains)){
                throw new BusinessException(ResponseCode.ROLES_UNAUTHORIZED_621 , "不满足权限要求，拒绝访问") ;
            }
            if(logical == Logical.OR && Arrays.stream(permissions.value()).noneMatch(userPermissions::contains)){
                throw new BusinessException(ResponseCode.ROLES_UNAUTHORIZED_621 , "不满足权限要求，拒绝访问") ;
            }
        }
        
      
        // 根据注解控制判断


        user.setPrefixSession(Constants.SESSION_PREFIX);
        user.setSessionid(sessionid);
        user.setSession(sessionTemplate);
        sessionTemplate.expire(Constants.SESSION_PREFIX  + sessionid , Constants.SESSION_EXPIRE_SECONDS);
        sessionTemplate.expire(Constants.USERID_PREFIX + user.getUserid() , Constants.SESSION_EXPIRE_SECONDS + 5) ; //多加5S


        return user  ;
    }
}