package com.zqsign.method.dependency;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	//判断字符串是否为�?
	public static String isStrNull(String str){
		return (str == null ? "" : str.toString().trim());
	}
	
	public static String isStrNull(Object str){
		return (str == null ? "" : str.toString().trim());
	}
	
	public static String subStr(String str , int length){
		String stri = null;
		if(!StringUtil.isStrNull(str).equals("")){
			if(str.length() > length){
				stri = str.substring(0, length)+"...";
			}else{
				stri = str;
			}
		}else{
			stri = "";
		}
		return stri;
	}
	public static String replace(String source,String target,int start,int end){
		if(source==null||source.length()<1) return source;
		if(start==end) return source;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			if(i==start&&start!=end) {
				buffer.append(target);
				start++;
			}else{
				buffer.append(c);
			}
		}
		return buffer.toString();
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
}
