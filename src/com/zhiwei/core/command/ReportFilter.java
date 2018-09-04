package com.zhiwei.core.command;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.util.ParamUtil;

public class ReportFilter {
	Map<String, Object> variables=new HashMap<String, Object>();
	public ReportFilter() {
		// TODO Auto-generated constructor stub
	}
	
	public ReportFilter(HttpServletRequest request){
		Enumeration paramEnu= request.getParameterNames();
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    		
    		if(paramName.startsWith("Q_")){
    			String paramValue=(String)request.getParameter(paramName);
    			addFilter(paramName,paramValue);
    		}
    	}
	}
	
	public void addFilter(String paramName,String value){
		String []fieldInfo=paramName.split("[_]");
		if(fieldInfo.length==3){
			variables.put(fieldInfo[1], ParamUtil.convertObject(fieldInfo[2], value));
		}
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
	

}
