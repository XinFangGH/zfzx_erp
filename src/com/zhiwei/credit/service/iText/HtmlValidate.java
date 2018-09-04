package com.zhiwei.credit.service.iText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description html内容非法标签的验证，不是基本的html标签过滤
 * @class HtmlValidate
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-7-1AM
 * 
 */
public class HtmlValidate {

	/**
	 * 验证非法标签的正则表达式
	 */
	private String regxp = "</?[a-zA-Z]+[:][a-zA-Z]+>";

	/**
	 * 基本功能：过滤指定标签，返回新的数据
	 * 
	 * @param str
	 *            转化前的数据
	 * @return String转化后的新数据
	 */
	public String fiterHtmlTag(String str) {
		Pattern pattern = Pattern.compile(regxp);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = matcher.find();
		while (result) {
			matcher.appendReplacement(sb, "");
			result = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

}
