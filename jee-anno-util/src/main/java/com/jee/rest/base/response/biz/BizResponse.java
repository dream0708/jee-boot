package com.jee.rest.base.response.biz;

import java.util.Collection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jee.rest.base.exception.BusinessException;
import com.jee.rest.base.response.code.ResponseCode;
import com.jee.rest.base.response.facade.IBusinessResponse;
import com.jee.rest.base.response.page.PageInfo;

public class BizResponse extends JSONObject implements java.io.Serializable{

	private static final long serialVersionUID = 5832251539685437762L;

	public static final String CODE = "code" ;
	public static final String MSG = "msg" ;
	public static final String HASH = "hash" ;
	public static final String DATA = "data" ;
	public static final String SESSIONID = "sessionid" ;
	public static final String INFO = "info" ;
	public static final String RESULT = "result" ;
	
	public static final String PAGE_NO = "pageNo" ;
	public static final String PAGE_SIZE = "pageSize" ;
	public static final String TOTAL_COUNT = "totalCount" ;
	public static final String TOTAL_PAGE = "totalPage" ;
	public static final String PAGE = "page" ;
	public static final String LIST = "list" ;
	
	
	
	public  BizResponse success(){
		this.put(CODE, ResponseCode.SUCCESS_0.getCode()) ;
		this.put(MSG , ResponseCode.SUCCESS_0.getMsg()) ;
		this.putIfAbsent(HASH, "") ;
    	return this ;
    }
    
	public  BizResponse success(String msg){
		this.put(CODE, ResponseCode.SUCCESS_0.getCode()) ;
		this.put(MSG, msg) ;
		this.putIfAbsent(HASH, "") ;
    	return this ;
    }
	public  BizResponse success(int code , String msg){
		this.put(CODE, code) ;
		this.put(MSG, msg) ;
		this.putIfAbsent(HASH, "") ;
    	return this ;
    }
	public static BizResponse ok() {
		return new BizResponse().success() ;
	}
	
	public static BizResponse ok(String msg) {
		return new BizResponse().success(msg) ;
	}
	
	public static BizResponse ok(int code , String msg) {
		return new BizResponse().success(code , msg) ;
	}
	
    /**
     * 失败
     * @return
     */
	public BizResponse failure(IBusinessResponse code){
		this.put(CODE, code.getCode()) ;
		this.put(MSG, code.getMsg()) ;
		this.putIfAbsent(HASH, "") ;
    	return this ;
    }
	public BizResponse failure(IBusinessResponse code , String msg){
		this.put(CODE, code.getCode()) ;
		this.put(MSG, msg) ;
		this.putIfAbsent(HASH, "") ;
    	return this ;
    }
	public BizResponse failure(){
    	return failure(ResponseCode.FAILUER_1) ;
    }
	public  BizResponse failure(String msg){
		this.put(CODE, ResponseCode.FAILUER_1.getCode()) ;
		this.put(MSG , msg) ;
		this.putIfAbsent(HASH, "") ;
    	return this ;
    }
   	public  BizResponse failure(int code , String msg){
   		this.put(CODE, code) ;
   		this.put(MSG , msg) ;
   		this.putIfAbsent(HASH, "") ;
       	return this ;
    }

   	public  BizResponse failure(BusinessException ex){
   		this.put(CODE , ex.getErrorCode().getCode());
   		this.put(MSG , ex.getMessage());
   		this.putIfAbsent(HASH, "") ;
       	return this ;
    }
   	
   	
   	public static BizResponse fail() {
		return new BizResponse().failure() ;
	}
   	public static BizResponse fail(IBusinessResponse code) {
		return new BizResponse().failure(code) ;
	}
   	public static BizResponse fail(IBusinessResponse code , String msg) {
		return new BizResponse().failure(code , msg) ;
	}
   	public static BizResponse fail(String   msg) {
		return new BizResponse().failure(msg) ;
	}
   	public static BizResponse fail(int code , String msg) {
		return new BizResponse().failure(code , msg) ;
	}
   	public static BizResponse fail(BusinessException ex) {
		return new BizResponse().failure(ex) ;
	}
   	
	public  static BizResponse status(int code , String msg , String hash){
		return new BizResponse().failure(code , msg).hash(hash) ;
    }	
	public static BizResponse status(int code , String msg){
   		return status(code , msg , "") ;
    }
	
	public  static BizResponse of(int code , String msg , String hash){
		return new BizResponse().failure(code , msg).hash(hash) ;
    }	
	public static BizResponse of(int code , String msg){
   		return status(code , msg , "") ;
    }
	
   	/**
   	 * 
   	 * @param hash
   	 * @return
   	 */
 	public  BizResponse hash(String hash){
 		this.put(HASH , hash);
       	return this ;
    }
 	
 	public  BizResponse sessionid(String sessionid){
 		this.put(SESSIONID , sessionid);
       	return this ;
    }
   	
 	public  BizResponse result(Object result){
 		this.put(RESULT , result);
       	return this ;
    }
 	
 	public  BizResponse info(Object info){
 		this.put(INFO , info);
       	return this ;
    }
 	
   	/**
   	 * data
   	 * @param t
   	 * @return
   	 */

 	
	public BizResponse data(Collection<?> t){
		if(t == null) {
			return puts(DATA , new JSONArray()) ;
		}
 		return puts(DATA , t) ;
	}
	public BizResponse list(Collection<?> t){
		if(t == null) {
			return puts(LIST , new JSONArray()) ;
		}
 		return puts(LIST , t) ;
	}
 	
	
	public BizResponse listArray(Object... t){
		if(t == null) {
			return puts(LIST , new Object[] {}) ;
		}
		if(t.length == 1) {
 			if(t[0] == null) {
 				return puts(LIST , new Object[] {}) ;
 			}
 		}
 		return puts(LIST , t) ;
	}
	
	public BizResponse listObjects(Object... t){
		if(t == null) {
			return puts(LIST , new Object[] {}) ;
		}
		if(t.length == 1) {
 			if(t[0] == null) {
 				return puts(LIST , new Object[] {}) ;
 			}
 		}
 		return puts(LIST , t) ;
	}
	
	public BizResponse dataArray(Object... t){
		if(t == null) {
			return puts(DATA , new Object[] {}) ;
		}
 		return puts(DATA , t) ;
	}
	public BizResponse dataObjects(Object... t){
		if(t == null) {
			return puts(DATA , new Object[] {}) ;
		}
		if(t.length == 1) {
 			if(t[0] == null) {
 				return puts(DATA , new Object[] {}) ;
 			}
 		}
 		return puts(DATA , t) ;
	}
 	
	public BizResponse listNullable(String key , Object... t){
 		if( t == null ) {
 			return puts(key , new Object[] {} ) ;
 		}
 		if(t.length == 1) {
 			if(t[0] == null) {
 				return puts(key , new Object[] {}) ;
 			}
 		}
 		return puts(key , t) ;
	}

	public BizResponse listNullable(String key , Collection<?> t){
 		if(t == null ) {
 			return puts(key , new JSONArray()) ;
 		}
 		return puts(key , t) ;
	}
 	
	public BizResponse array(String key , Object ... t){
 		return puts(key , t) ;
	}
	public BizResponse array(String key , Collection<?> t){
 		return puts(key , t) ;
	}
	public BizResponse arrayNullable(String key , Object... t){
 		if(t == null) {
 			return puts(key , new Object[] {} ) ;
 		}
 		return puts(key , t) ;
	}
	public BizResponse arrayNullable(String key , Collection<?> t){
 		if(t == null) {
 			return puts(key , new JSONArray()) ;
 		}
 		return puts(key , t) ;
	}
	public BizResponse dataNullable(String key , Object... t){
 		if(t == null) {
 			return puts(key , new Object[] {} ) ;
 		}
 		return puts(key , t) ;
	}
	public BizResponse dataNullable(String key , Collection<?> t){
 		if(t == null) {
 			return puts(key , new JSONArray()) ;
 		}
 		return puts(key , t) ;
	}
	
 	
 	
   	public BizResponse entity(String key , Object value){
   		this.put(key, value) ;
   		return this ;
   	}
	public BizResponse additional(String key , Object value){
		this.put(key, value) ;
   		return this ;
   	}
	public BizResponse append(String key , Object value){
		this.put(key, value) ;
   		return this ;
   	}
	public BizResponse puts(String key , Object value){
		this.put(key, value) ;
   		return this ;
   	}
    /**
     * 
     * @return
     */
   	public void setCode(int code){
   		this.put(CODE , code) ;
   	}
	public void setMsg(String msg){
		this.put(MSG , msg) ;
   	}
	public void setHash(String hash){
		this.put(HASH, hash) ;
   	}
	
	public int getCode(){
		return (int) this.getOrDefault(CODE , -1) ;
	}
	public String getMsg(){
		return (String) this.getOrDefault(MSG , "") ;
	}
	public String getHash(){
		return (String) this.getOrDefault(HASH, "") ;
	}
	
	public BizResponse page(int pageNo , int pageSize , int totalCount){
		JSONObject page = new JSONObject() ;
		if(pageNo <= 0){
			pageNo = 1 ; //从第一页开始
		}
		page.put(PAGE_NO, pageNo) ;
		page.put(PAGE_SIZE, pageSize) ;
		page.put(TOTAL_COUNT, totalCount) ;
		int totalPage = (int) Math.ceil(totalCount * 1.0 / pageSize) ;
		page.put(TOTAL_PAGE, totalPage) ;
		this.put(PAGE, page) ;
		return this ;
	}
	
	public BizResponse page(int pageNo , int pageSize , long totalCount){
		JSONObject page = new JSONObject() ;
		if(pageNo <= 0){
			pageNo = 1 ; //从第一页开始
		}
		page.put(PAGE_NO, pageNo) ;
		page.put(PAGE_SIZE, pageSize) ;
		page.put(TOTAL_COUNT, totalCount) ;
		long totalPage = (long) Math.ceil(totalCount * 1.0 / pageSize) ;
		page.put(TOTAL_PAGE, totalPage) ;
		this.put(PAGE, page) ;
		return this ;
	}
	
	public BizResponse page(String key , int pageNo , int pageSize , int totalCount){
		JSONObject page = new JSONObject() ;
		if(pageNo <= 0){
			pageNo = 1 ; //从第一页开始
		}
		page.put(PAGE_NO, pageNo) ;
		page.put(PAGE_SIZE, pageSize) ;
		page.put(TOTAL_COUNT, totalCount) ;
		int totalPage = (int) Math.ceil(totalCount * 1.0 / pageSize) ;
		page.put(TOTAL_PAGE, totalPage) ;
		this.put(key, page) ;
		return this ;
	}
	
	public BizResponse page(String key , int pageNo , int pageSize , long totalCount){
		JSONObject page = new JSONObject() ;
		if(pageNo <= 0){
			pageNo = 1 ; //从第一页开始
		}
		page.put(PAGE_NO, pageNo) ;
		page.put(PAGE_SIZE, pageSize) ;
		page.put(TOTAL_COUNT, totalCount) ;
		long totalPage = (long) Math.ceil(totalCount * 1.0 / pageSize) ;
		page.put(TOTAL_PAGE, totalPage) ;
		this.put(key, page) ;
		return this ;
	}
	
	
	
	public <T> BizResponse pageInfo(PageInfo<T> page){
		this.put(PAGE, page.getList()) ;
		this.page(page.getPageNum() , page.getPageSize() , page.getTotal()) ;
		return this ;
	}
	
	public <T> BizResponse pageInfo(String key , PageInfo<T> page){
		this.put(key, page.getList()) ;
		this.page(page.getPageNum() , page.getPageSize() , page.getTotal()) ;
		return this ;
	}
	
	
	public <T> BizResponse page(String key , Object page){
		this.put(key, page) ;
		return this ;
	}
	
	
	
	public int getPageNo(){
		 JSONObject page = this.getJSONObject(PAGE) ;
	     if(page == null){
	    	 return 0 ;
	     }
	     return page.getIntValue(PAGE_NO) ;
	}
	public void setPageNo(int pageNo){
		 JSONObject page = this.getJSONObject(PAGE) ;
	     if(page == null){
	    	 page = new JSONObject() ;
	     }
	     page.put(PAGE_NO, pageNo) ;
	     this.put(PAGE, page) ;
	}
	public int getPageSize(){
		 JSONObject page = this.getJSONObject(PAGE) ;
	     if(page == null){
	    	 return 0 ;
	     }
	     return page.getIntValue(PAGE_SIZE) ;
	}
	public void setPageSize(int pageSize){
		  JSONObject page = this.getJSONObject(PAGE) ;
		  if(page == null){
		      page = new JSONObject() ;
		  }
		  page.put(PAGE_SIZE, pageSize) ;
		  this.put(PAGE, page) ;
	}
	public int getTotalCount(){
		 JSONObject page = this.getJSONObject(PAGE) ;
	     if(page == null){
	    	 return -1 ;
	     }
	     return page.getIntValue(TOTAL_COUNT) ;
	}
	public void setTotalCount(int totalCount){
		 JSONObject page = this.getJSONObject(PAGE) ;
		 if(page == null){
			 page = new JSONObject() ;
		 }
		 page.put(TOTAL_COUNT, totalCount) ;
		 this.put(PAGE, page) ;
	}
	
	
	public static void main(String args[]) {
		JSONArray array = null ;
		BizResponse result = BizResponse.ok().list( array) ;
		
		System.out.println(JSON.toJSONString(result)) ;
		
		
		String array1 = null ;
		BizResponse result1 = BizResponse.ok().listNullable("array" ,array1) ;
		
		System.out.println(JSON.toJSONString(result1)) ;
		
		
		JSONArray array2 = null ;
		BizResponse result2 = BizResponse.ok().listNullable("array2") ;
		
		
		System.out.println(JSON.toJSONString(result2)) ;
	}
	
}
