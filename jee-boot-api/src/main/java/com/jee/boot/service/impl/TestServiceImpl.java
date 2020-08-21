package com.jee.boot.service.impl;

import org.springframework.stereotype.Service;

import com.jee.boot.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	
	
	public int testBoot(String id) {
		return Integer.valueOf(id) ;
	}
}
