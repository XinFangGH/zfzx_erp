package com.zhiwei.credit.core.commons;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @function 得到http 对话
 * 
 * @author Jiang Wanyu
 *
 */
public class CreditBaseAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	public static final String JSON_SUCCESS="{success:true}";
	
	protected final Log log = LogFactory.getLog(getClass());
	
	protected ServletActionContext servletActionContext;
	
	protected String jsonString=JSON_SUCCESS;

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getJsonString() {
		return jsonString;
	}
	
	//获得request
	protected HttpServletRequest getRequest(){
		return servletActionContext.getRequest();
	}
	
	//获得 response
	protected HttpServletResponse getResponse(){
		return servletActionContext.getResponse();
	}
	
	//获得 session
	protected HttpSession getSession() {
		return getRequest().getSession();
	}
	
	//获得session  true时总是新建session  false时会先查找 然后根据相近的session使用
	protected HttpSession getSession(boolean create){
		return getRequest().getSession(create);
	}
	
	//得到servletContext
	public ServletContext getServletContext(){
		return servletActionContext.getServletContext();
	}
	
	//rootPath
	protected String getRootPath(){
		return getServletContext().getRealPath("/");
	}
}
