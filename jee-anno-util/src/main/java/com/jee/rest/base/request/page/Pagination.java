package com.jee.rest.base.request.page;

import lombok.Data;

@Data
public class Pagination {
	
	private int pageNo = 1 ;
	private int pageSize = 15 ;
	private String order ;
	private String orderBy  ;
	private String sort ;
	private String sortBy ;
	private String extra ;

}
