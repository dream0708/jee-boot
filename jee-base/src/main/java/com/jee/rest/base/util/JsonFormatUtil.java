package com.jee.rest.base.util;

import com.alibaba.fastjson.JSON;

public class JsonFormatUtil {
	 /**
     * 打印输入到控制台
     * @param jsonStr
     */
    public static void printJson(String jsonStr){
        System.out.println(formatJson(jsonStr));
    }
    
    public static void printJson(Object obj){
    	System.out.println(formatJson(JSON.toJSONString(obj))) ;
    }
    public static String formatJson(Object obj){
    	return formatJson(JSON.toJSONString(obj)) ;
    }
    
    /**
     * 格式化
     * @param jsonStr
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
 
        return sb.toString();
    }
 
    /**
     * 添加space
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}
