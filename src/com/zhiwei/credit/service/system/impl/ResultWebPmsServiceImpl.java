package com.zhiwei.credit.service.system.impl;

import java.net.SocketTimeoutException;

import javax.xml.ws.WebServiceException;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.hurong.credit.config.HtmlConfig;
import com.hurong.credit.service.system.ResultParamsService;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.service.system.ResultWebPmsService;

public class ResultWebPmsServiceImpl implements ResultWebPmsService {
   private  ResultParamsService resultParamsService;
	public ResultWebPmsServiceImpl(){
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();  
        factoryBean.setAddress(AppUtil.getWebServiceUrlWs());  
        factoryBean.setServiceClass(ResultParamsService.class);  
        Object obj = factoryBean.create();  
 
         resultParamsService = (ResultParamsService) obj; 
	}
	@Override
	public HtmlConfig findHtmlCon(String name) {
		HtmlConfig htmlConfig=null;
		 try {  
	             htmlConfig = resultParamsService.resultHtmlConfig(name);  
	        } catch(Exception e) {  
	            if (e instanceof WebServiceException   
	                    && e.getCause() instanceof SocketTimeoutException) {  
	                System.err.println("This is timeout exception.");  
	            } else {  
	                e.printStackTrace();  
	            }  
	        }  
		
		return htmlConfig;
	}
	
	@Override
	public HtmlConfig findSingleHtmlCon(String params) {
		
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();  
        factoryBean.setAddress(AppUtil.getWebServiceUrlWs());  
        factoryBean.setServiceClass(ResultParamsService.class);  
        Object obj = factoryBean.create();  
 
         resultParamsService = (ResultParamsService) obj; 
		HtmlConfig htmlConfig=null;
		 try {  
	             htmlConfig = resultParamsService.resultSingleHtmlConfig(params);  
	        } catch(Exception e) {  
	            if (e instanceof WebServiceException   
	                    && e.getCause() instanceof SocketTimeoutException) {  
	                System.err.println("This is timeout exception.");  
	            } else {  
	                e.printStackTrace();  
	            }  
	        }  
		
		return htmlConfig;
	}

}
