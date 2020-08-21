package com.jee.rest.base.model.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jee.rest.base.session.template.SessionTemplate ;

public class BaseUser {

	private String userid ;  //the key of user 用户唯一标示
	private String mobile ;
	private Long   uid ;
	private String loginIp ;
	private String loginTime ;
	private String sessionid ;
	private String hash ;
	@JSONField(serialize = false)
	private transient SessionTemplate session ;
	@JSONField(serialize = false)
	private transient String prefixSession ;
	@JSONField(serialize = false)
	private transient int exprieSession ;
	@JSONField(serialize = false)
	private transient Map<String , String > sessionMap ;


    public BaseUser(){
		
	}
	
	@JSONField(serialize = false)
	public  void setAttribute(String key , String value ){
		session.setAttribute(prefixSession + sessionid, key , value);
	}
	@JSONField(serialize = false)
	public  void setAttribute(Map<String , String> value ){
		session.setAttribute(prefixSession + sessionid, value);
	}
	@JSONField(serialize = false)
	public  boolean setAttribute(String[] keys , Object[] objs ){
		Map<String , String> values = new HashMap<String , String>() ;
		if(keys.length != objs.length){
			return false ;
		}
		for(int i = 0 ; i < keys.length  ; i ++){
			if(objs[i] == null){
				continue ;
			}
			if(objs[i] instanceof String){
				values.put(keys[i], (String)objs[i] ) ;
			}else{
				values.put(keys[i], JSON.toJSONString(objs[i] , SerializerFeature.WriteClassName) ) ;
			}
			
		}
		session.setAttribute(prefixSession + sessionid, values);
		return true ;
	}
	
	@JSONField(serialize = false)
	public  <T> void setAttribute(String key , T value){
		session.setAttribute(prefixSession + sessionid, key , value , SerializerFeature.WriteClassName);
	}
	@JSONField(serialize = false)
	public String getAttribute(String key){
		return session.getAttribute(prefixSession + sessionid, key) ;
	}
	@JSONField(serialize = false)
	public String getString(String key){
		return  getAttribute(key) ;
	}
	@JSONField(serialize = false)
	public <T> T getAttribute(String key , Class<T> clazz ){
		return session.getAttribute(prefixSession + sessionid , key , clazz) ;
	}
	@JSONField(serialize = false)
	public <T> T getAttribute(String key , TypeReference<T> clazz ){
		return session.getAttribute(prefixSession + sessionid , key , clazz) ;
	}
	@JSONField(serialize = false)
	public JSONObject getJSONObject(String key){
		String value = session.getAttribute(prefixSession + sessionid , key ) ;
		return JSONObject.parseObject(value) ;
	}
	@JSONField(serialize = false)
	public Integer getInt(String key ){
		return getAttribute( key , Integer.class) ;
	}
	@JSONField(serialize = false)
	public Double getDouble(String key ){
		return getAttribute( key , Double.class) ;
	}
	@JSONField(serialize = false)
	public Long getLong(String key ){
		return getAttribute( key , Long.class) ;
	}
	@JSONField(serialize = false)
	public Integer getInt(String key , int defaults){
		Integer ints = getInt(key) ;
		return  null == ints ? defaults : ints ;
	}
	@JSONField(serialize = false)
	public Double getDouble(String key , double defaults ){
		Double dous =  getDouble(key) ;
		return  null == dous ? defaults : dous ;
	}
	@JSONField(serialize = false)
	public Long getLong(String key , long defaults ){
		Long longs =  getLong(key) ;
		return  null == longs ? defaults : longs ;
	}
	@JSONField(serialize = false)
	public String getString(String key , String defaults ){
		String str =  getString(key) ;
		return  StringUtils.isBlank(str) ? defaults : str ;
	}
	@JSONField(serialize = false)
	public <T> List<T> getList(String key , Class<T> clazz ){
		String text =  getAttribute(key) ;
		return JSONArray.parseArray(text, clazz) ;
	}
	@JSONField(serialize = false)
	public Map<Object , Object> getAll(){
		return session.getAll(prefixSession + sessionid) ;
	}
	@JSONField(serialize = false)
	public void removeAttribute(String ... key){
		session.removeAttribute(prefixSession + sessionid, key);
	}
	@JSONField(serialize = false)
	public void removeSession(){
		session.delete(prefixSession + sessionid);
	}
	@JSONField(serialize = false)
	public void exprieSession(int seconds){
		session.expire(prefixSession + sessionid, seconds);
	}
	@JSONField(serialize = false)
	public long increment(String key , long value){
		return session.increment(prefixSession + sessionid, key , value );
	}
	public Map<String, String> getSessionMap() {
		return sessionMap;
	}
	public void setSessionMap(Map<String, String> sessionMap) {
		this.sessionMap = sessionMap;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public int getExprieSession() {
		return exprieSession;
	}
	public void setExprieSession(int exprieSession) {
		this.exprieSession = exprieSession;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getPrefixSession() {
		return prefixSession;
	}
	public void setPrefixSession(String prefixSession) {
		this.prefixSession = prefixSession;
	}
	public SessionTemplate getSession() {
		return session;
    }
	public void setSession(SessionTemplate session) {
		this.session = session;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}




}
