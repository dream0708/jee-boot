package com.jee.rest.base.anno.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DateTimeFormatUtils {

	
	public static String  format(Date date , String pattern) {
		return DateFormatUtils.format(date, pattern) ;
	}
	public static String  format(Long date , String pattern) {
		return DateFormatUtils.format(date, pattern) ;
	}
	public static String  format(Calendar date , String pattern) {
		return DateFormatUtils.format(date, pattern) ;
	}
	
	public static  String  format(LocalDateTime dateTime , String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return 	dateTime.format(formatter) ;
	}
	
	public static  String  format(LocalDate dateTime , String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return 	dateTime.format(formatter) ;
	}
	
	
	public static  String  format(LocalTime dateTime , String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return 	dateTime.format(formatter) ;
	}
	
	public static  LocalDateTime  parseLocalDateTime(String dateTime , String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(dateTime, formatter) ;
	}
	
	
	public static  LocalTime  parseLocalTime(String dateTime , String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalTime.parse(dateTime, formatter) ;
	}
	
	
	public static  LocalDate  parseLocalDate(String dateTime , String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(dateTime, formatter) ;
	}
	
	
	public static void main(String args[]) {
		System.out.println(DateTimeFormatUtils.format(LocalDate.now(), "yyMMdd")) ;
		System.out.println(DateTimeFormatUtils.format(LocalTime.now(), "HHmmss")) ;
	}
}
