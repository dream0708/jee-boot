package com.jee.rest.base.anno.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

public class NumberFormatUtils {
	
	private final static Map<String, DecimalFormat> decimalFormatMaps = new ConcurrentHashMap<String, DecimalFormat>();
	

	public static final String CONTACT_STRING = "&&&";
	
	
	
	public static String formatNumber(
			final BigDecimal str, 
			String pattern ,
			RoundingMode mode , 
			int   groupSize , 
			int   scales ) {
		
		DecimalFormat format = decimalFormatMaps.get(pattern + CONTACT_STRING +  mode.ordinal() + 
				CONTACT_STRING + groupSize + CONTACT_STRING +  scales) ;
		if(format == null) {
			if(StringUtils.isNotBlank(pattern)){
				format = new DecimalFormat(pattern) ;
			}else {
				format = new DecimalFormat() ;
			}
			format.setRoundingMode(mode);
			if(groupSize > 0) {
				format.setGroupingSize(groupSize);
			}
			if(scales > 0) {
				format.setMaximumFractionDigits(scales);
			}
			decimalFormatMaps.put(pattern + CONTACT_STRING +  mode.ordinal() + 
					CONTACT_STRING + groupSize + CONTACT_STRING +  scales , format) ;
		}
		return format.format(str);
	}
	
	public static BigDecimal toBigDecimal(final String str) {
		return toBigDecimal(str, "0.00");
	}
	
	public static String formatNumber(final String str, String pattern ,
			RoundingMode mode , int groupSize , int  scales ) {
		return formatNumber(toBigDecimal(str) , pattern , mode , groupSize ,  scales ) ;
	}
	
	public static String formatNumber(final Number str, String pattern ,
			RoundingMode mode , int groupSize , int  scales ) {
		return formatNumber(toBigDecimal(String.valueOf(str)) , pattern , mode , groupSize ,  scales ) ;
	}
	

	public static String formatNumber(final String number, String pattern) {
		return formatNumber(number, pattern , RoundingMode.HALF_UP , -1 , -1 ) ;
	}
	
	public static String formatNumber(final Number number, String pattern) {
		return formatNumber(String.valueOf(number) , pattern , RoundingMode.HALF_UP , -1 , -1 ) ;
	}
	
	public static String formatNumber(final BigDecimal number , String pattern) {
		return formatNumber(number , pattern , RoundingMode.HALF_UP , -1 , -1 ) ;
	}
	
	
	public static String formatNumber(final String number, String pattern , RoundingMode mode) {
		return formatNumber(number, pattern , mode , -1 , -1 ) ;
	}
	
	public static String formatNumber(final Number number, String pattern , RoundingMode mode) {
		return formatNumber(String.valueOf(number) , pattern , mode , -1 , -1 ) ;
	}
	
	public static String formatNumber(final BigDecimal number , String pattern , RoundingMode mode) {
		return formatNumber(number , pattern , mode, -1 , -1 ) ;
	}
	
	
	public static BigDecimal toBigDecimal(final String str, String defaultValue) {
		if (str == null) {
			return new BigDecimal(defaultValue) ;
		}
		try {
			return new BigDecimal(str) ;
		}catch (NumberFormatException e) {
			return new BigDecimal(defaultValue) ;
		}
	}
	

	
	public static double toDouble(final String str) {
		return toDouble(str, 0.0d);
	}
	
	public static double toDouble(final String str, int digits, RoundingMode roundingMode) {
		BigDecimal b = new BigDecimal(str) ;
		return b.setScale(digits, roundingMode).doubleValue();
	}
	
	public static double toDouble(final String str, final double defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		try {
			return Double.parseDouble(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}
	
	
	
	public static void main(String args[]) {
		
	
	    double db1 = 12.00005;
	    double db2 = 12.00006;
	    double db3 = 12.000055;
	    double db4 = 12.000056;
	    DecimalFormat df = new DecimalFormat("#.000") ;
	    System.out.println(df.format(db1)); // 输出结果是12.0001
	    System.out.println(df.format(db2)); // 输出结果是12.0001
	    System.out.println(df.format(db3)); // 输出结果是12.0001
	    System.out.println(df.format(db4)); // 输出结果是12.0001
	    
	    System.out.println("-------------------------------------------") ;
		
	    Number x = null ;
		System.out.println(formatNumber(x, "0.00")) ;
		System.out.println(formatNumber(-0.35216, "+#.##%")) ;
		System.out.println(formatNumber(19.799999, "00.00")) ;
		System.out.println(formatNumber(19.7, "0.00")) ;
		System.out.println(formatNumber(1000.645, "#")) ;
		System.out.println(formatNumber(1000.645, "#.00")) ;
		System.out.println(formatNumber("1000.645" , "#.00")) ;
		System.out.println(formatNumber("10445854400.6455", "000,0.00")) ;
		
		System.out.println(toDouble("1000.645" , 2 , RoundingMode.HALF_UP)) ;
		
		System.out.println(formatNumber(new BigDecimal(758.645D), "0.00")) ;
		System.out.println(formatNumber(new BigDecimal("758.645"), "0.00")) ;
		
		
		System.out.println("-------------------------------------------") ;
		System.out.println(formatNumber("0.4984", "0.00%")) ;  
		   
		System.out.println("-------------------------------------------") ;
		   
		DecimalFormat formater = new DecimalFormat("0.00%");
		      formater.setMaximumFractionDigits(2);
		      formater.setGroupingSize(3);
		      formater.setRoundingMode(RoundingMode.FLOOR);
		   //   formater.applyLocalizedPattern(pattern);
		      System.out.println( formater.format(12538285825.455));
	
		  		
	}

}
