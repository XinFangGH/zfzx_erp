package com.zhiwei.core.web.filter;


import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.StringEscapeUtils;

import com.google.gson.JsonParser;

public class XssHttpServletRequestWrapper  extends HttpServletRequestWrapper{
	static HttpServletRequest orgRequest = null;
	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		 orgRequest = request;
	}
   /**
    * 转义	
    * @param name
    * @return
    */
   private String format(String name) {
	 //过滤json字符串
  	// boolean checkJson = isGoodJson(name);
  	 //if(!checkJson){
  		return replaceSting(name);//过滤代码
  	// }else{
  		// return name;
  	 //}
   }
   /**
	 *  默认的过滤方法
	 * 	过滤sql语句关键字，字符串特殊符号
	 */
	public static String replaceSting(String parameterName){
		parameterName = parameterName
						.replace("sleep", "").replace("SLEEP", "")
						.replace("select", "").replace("SELECT", "")
						.replace("delete", "").replace("DELETE", "")
						.replace("update", "").replace("UPDATE", "")
						.replace("insert", "").replace("INSERT", "")
			  			.replace("union", "").replace("UNION", "")
			  			.replace("query", "").replace("QUERY", "")
			  			.replace("having", "").replace("HAVING", "")
			  			.replace("drop", "").replace("DROP", "")
			  			.replace("or 1", "").replace("alert", "")
			  			.replace("alter", "").replace("create", "")
			  			.replace("exec", "").replace("execute", "")
			  			.replace("xp_cmdshell", "").replace("exists", "")
			  			.replace("master", "").replace("restore", "")
			  			.replace(".jsp", "").replace(".html", "")
			  			.replace(".js", "").replace(".ext", "")
			  			.replace(".rar", "").replace("%7B", "")
			  			.replace("<", "").replace("%3C", "")
						.replace(">", "").replace("%3E", "")
						.replace("%7D", "").replace("xss", "")
		 ;
		//空格单独处理,如果不是日期侧替换
		/*if(!isDate(parameterName)){
			parameterName = parameterName.replace(" ", "");
		}*/
		return parameterName;
	}
	/*
	 * 判断是不是日期
	 * 是  返回true
	 * 不是 返回false
	 */
	public static boolean isDate(String str){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date parse2 = format.parse(str);
			return true;
		} catch (ParseException e) {
			return false;
		}
	
	}
	public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
        if (value instanceof String) {
           // value = format(String.valueOf(value));
        }
        return value;
    }
	/**
	 * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
	 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	 */
	 @Override
	 public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value == null)
            return null;
        //过滤路径
		boolean reqUrl = checkUrl();
		if(reqUrl){
			return value;
		}else{
			return format(value);
		}
    }
   public String[] getParameterValues(String name) {
	    String[] values = super.getParameterValues(name);
	    //过滤路径
		boolean reqUrl = checkUrl();
		if(reqUrl){
			if (values != null) {
	            for (int i = 0; i < values.length; i++) {
	                values[i] = format(values[i]);
	            }
	        }
		}        
	    return values;
   }
   /**
    * 得到请求的参数
    */
   public Map getParameterMap() {
	   
	   HashMap paramMap = (HashMap) super.getParameterMap();
        paramMap = (HashMap) paramMap.clone();
        //过滤路径
		boolean reqUrl = checkUrl();
		
	    if(reqUrl&&paramMap!=null&&!paramMap.equals("")&&!paramMap.equals("{}")){
	    	 for (Iterator iterator = paramMap.entrySet().iterator(); iterator.hasNext(); ) {
	             Map.Entry entry = (Map.Entry) iterator.next();
	             String [] values = (String[]) entry.getValue();
	             for (int i = 0; i < values.length; i++) {
	                 if(values[i] instanceof String){
	                	values[i] = format(values[i]);
	                 }
	             }
	             entry.setValue(values);
	         }
	    }
        return paramMap;
    }
	 /**
	 * 过滤路径
	 */
	 private static boolean checkUrl() { 
		 boolean requrl = true;
		 try{
		 	//转为HttpServletRequest
			HttpServletRequest req = orgRequest;
			//项目名称
			//String contextPath = req.getContextPath();
			//带项目名称的相对路径
			String requestURI = req.getRequestURI();
			if(requestURI!=null&&!requestURI.equals("")){
				//得到不过滤的路径
				String src = XssHttpServletRequestWrapper.class.getClassLoader().getResource("urlfilter.properties").getPath();
				Properties props=new Properties();
	    		FileInputStream fis=new FileInputStream(src);
	    		Reader r = new InputStreamReader(fis, "UTF-8"); 
	    		props.load(r);
	    		Iterator it= props.keySet().iterator();
	    		while(it.hasNext()){
	    			String filterUrl=(String)it.next();
	    			//最一个"/"符号位置
	    			int index = requestURI.lastIndexOf("/");
	    			//最一个"/"符号之后
	    			String urlEnd = requestURI.substring(index+1);
	    			if(requestURI.equals(filterUrl)){//直接全部匹配查找
	    				requrl = false;
	    			}
	    			//最一个"*"符号之后
	    			String nextEnd = filterUrl.substring(filterUrl.lastIndexOf("*")+1);
	    			if(urlEnd.contains(nextEnd)){//*号通配符号查找
	    				requrl = false;
	    			}
	    		}
			}
			//判断是否是编辑器提交的内容
			/*String ueditorCheck =(String) req.getSession().getAttribute("hryUeditor");
			if(ueditorCheck!=null&&!ueditorCheck.equals("")){
				requrl = false;
			}*/
		 }catch (Exception e) {
			e.printStackTrace();
		}
		return requrl;
	 }
	 /**
		 * 判断是不是json串
		 * 是返回true
		 * 不是返回false
		 */
		public static boolean isGoodJson(String json) {  
	        if (json==null||"".equals(json)) {  
	            return false;  
	        }  
	        try {  
	            new JsonParser().parse(json);  
	            return true;  
	        } catch (Exception e) {  
	            return false;  
	        }  
		}
	 /**
	 * 获取最原始的request
	 * 
	 * @return
	 */
	 public HttpServletRequest getOrgRequest() {
	 return orgRequest;
	 }

	 /**
	 * 获取最原始的request的静态方法
	 * 
	 * @return
	 */
	 public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
	 if (req instanceof XssHttpServletRequestWrapper) {
	 return ((XssHttpServletRequestWrapper) req).getOrgRequest();
	 }

	 return req;
	 }
}
