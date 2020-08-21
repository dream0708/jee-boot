package com.jee.rest.base.anno.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtils {

	 //http://blog.chinaunix.net/uid-20577907-id-1758770.html
	 public static final String allReg = "[\\w\\W]*" ;
	 
	 public static final String mobileReg = "^((13[0-9])|(15[0-9])|(18[0-9])|(14[0-9])|(17[0-9]))\\d{8}$" ;
	 public static final String complexMobileReg = "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$" ;
	 public static final String simpleMobileReg = "^1[3|4|5|7|8][0-9]{9}$" ;
	 public static final String emailReg = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	 public static final String complexPwdReg = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_]).{6,20})" ;
	 public static final String simplePwdReg = "[0-9a-zA-Z_]{6,16}" ;
	 public static final String webReg = "^http://([w-]+.)+[w-]+(/[w-./?%&=]*)?$" ;
	 public static final String cerReg = "(\\d{15})|(\\d{14}[xX])|(\\d{18})|(\\d{17}[xX])" ;
	 public static final String chReg = "[\u4e00-\u9fa5]{0,}" ;
	 public static final String ipReg = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
	 public static final String urlReg = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?" ;
	 public static final String doubleCodeReg = "[^\\x00-\\xff]" ; //双字节;
	 public static final String negetiveReg = "^-[1-9]\\d*$" ;
	 
	 /**YYYY-MM-DD**/
	 public static final String dateReg1 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[13579][26])00))-02-29)" ;
     /**YYYYMMDD**/
	 public static final String dateReg2 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)" ;
	 
	 /**
	 匹配特定数字：
	 ^[1-9]\\d*$ //匹配正整数
	 ^-[1-9]\\d*$ //匹配负整数
	 ^-?[1-9]\\d*$ //匹配整数
	 ^[1-9]\\d*|0$ //匹配非负整数（正整数 + 0）
	 ^-[1-9]\\d*|0$ //匹配非正整数（负整数 + 0）
	 ^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$ //匹配正浮点数
	 ^-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*)$ //匹配负浮点数
	 ^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$ //匹配浮点数
	 ^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$ //匹配非负浮点数（正浮点数 + 0）
	 ^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$ //匹配非正浮点数（负浮点数 + 0）
	 评注：处理大量数据时有用，具体应用时注意修正
	 匹配特定字符串：
	 ^[A-Za-z]+$ //匹配由26个英文字母组成的字符串
	 ^[A-Z]+$ //匹配由26个英文字母的大写组成的字符串
	 ^[a-z]+$ //匹配由26个英文字母的小写组成的字符串
	 ^[A-Za-z0-9]+$ //匹配由数字和26个英文字母组成的字符串
	 */
	 
	 /**
	  * 检查合法性
	  * @param subject
	  * @param reg
	  * @return
	  */
	 public static boolean checkLegal(String subject , String reg){
		 Pattern p =  Pattern.compile(reg);//复杂匹配  
		 Matcher m = p.matcher(subject);  
		 return m.matches();
	 }
	 
	 public static void main(String args[]){
		 System.out.println(checkLegal("139(6203390" , mobileReg));
	 }
}
