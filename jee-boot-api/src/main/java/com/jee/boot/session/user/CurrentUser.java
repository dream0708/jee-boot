package com.jee.boot.session.user;

import com.jee.boot.datasource.first.entity.UmsAdmin;
import com.jee.rest.base.model.user.BaseUser;

import lombok.Data;

/**
 * @author yaomengke
 * @create 2020- 04 - 15 - 9:03
 */
@Data
public class CurrentUser extends BaseUser {
    private String account ; //绑定的客户号 有可能为空
    private String openid ;
    private String unionid ;
    private String deviceid ;
    private String channel ;
    private String nickName ;
    //根据需要自定义

    private String userType ;
    private UmsAdmin umsAdmin ;

}
