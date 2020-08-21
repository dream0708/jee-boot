package com.jee.common.jdbc.extend;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Page<T> {
	
	private int pageNo ;
	private int pageSize ;
	private int totalCount ;
	private int totalPage ;
	
	private List<T> list ;
	
	public Page (int pageNo , int pageSize , int totalCount , List<T> list){
		this.pageNo = pageNo ;
		this.pageSize = pageSize ;
		this.totalCount = totalCount ;
		this.list = list ;
		this.totalPage = (int) Math.ceil(totalCount * 1.0 / pageSize) ;
	}
	
	public Page (int pageNo , int pageSize , int totalCount ){
		this.pageNo = pageNo ;
		this.pageSize = pageSize ;
		this.totalCount = totalCount ;
		this.list = new ArrayList<T>() ;
		this.totalPage = (int) Math.ceil(totalCount * 1.0 / pageSize) ;
	}

}
