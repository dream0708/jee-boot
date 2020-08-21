package com.jee.rest.base.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import io.jsonwebtoken.Jwts;

public class RandomUtil {
	
	public static String digital = "0123456789" ;
	public static String lower = "abcdefghijklmnopqrstuvwxyz" ;
	public static String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" ;
	public static String all = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" ;

	

	public static String random(int n){
		Random random = new Random() ;
		int counts = all.length() ;
		String str = "" ;
		for(int i = 0 ;  i  < n  ; i ++){
			str += all.charAt(random.nextInt(counts)) ;
		}
		return str ;
	}
	
	public static String randomDigital(int n){
		String str = "" ;
		Random random = new Random() ;
		int counts = digital.length() ;
		for(int i = 0 ;  i  < n  ; i ++){
			str += digital.charAt(random.nextInt(counts)) ;
		}
		return str ;
	}
	
	public static String randomLower(int n){
		String str = "" ;
		Random random = new Random() ;
		int counts = lower.length() ;
		for(int i = 0 ;  i  < n  ; i ++){
			str += lower.charAt(random.nextInt(counts)) ;
		}
		
		return str ;
	}
	
	
	public static String randomUpper(int n){
		String str = "" ;
		Random random = new Random() ;
		int counts = upper.length() ;
		for(int i = 0 ;  i  < n  ; i ++){
			str += upper.charAt(random.nextInt(counts)) ;
		}
		return str ;
	}
	
	public static String jstSessionid(String userid , String secret , String subject ){
		return  Jwts.builder()
                .setId(userid)                                      // JWT_ID
                .setAudience(userid)                                // 接受者
                // .setClaims(null)                                    // 自定义属性
                .setSubject(subject)                                // 主题
                .setIssuer(userid)                                  // 签发者
                .setIssuedAt(new Date())                            // 签发时间
                .setNotBefore(new Date())                           // 失效时间
                                                                    // 过期时间
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret) // 签名算法以及密匙
                .compact() 
                ;           
		
		
	}
	public static String simpleSessionid( ){
		return UUID.randomUUID().toString().replace("-", random(12) ).toUpperCase() ;
	}



	public static int nextInt(Random random) {
		return random.nextInt();
	}

	
	public static int nextInt(Random random, int n) {
		return random.nextInt(n);
	}

	

	public static long nextLong(Random random) {
		return random.nextLong();
	}


	public static boolean nextBoolean(Random random) {
		return random.nextBoolean();
	}



	public static float nextFloat(Random random) {
		return random.nextFloat();
	}



	public static double nextDouble(Random random) {
		return random.nextDouble();
	}
	
	public static void main(String args[]){
		System.out.println(jstSessionid("123" , "iiii" , "2w2"));
		System.out.println(simpleSessionid());
	}

}
