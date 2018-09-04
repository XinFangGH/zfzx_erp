package com.zhiwei.core.util;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

import com.zhiwei.core.util.gopay.config.GopayConfig;
public class SendPostMessage {
	 private static HttpURLConnection conn = null;   
     
     public static  boolean send(String urlAddr, Map<String,String> map) throws Exception {   
        
         boolean isSuccess = false;   
          StringBuffer params = new StringBuffer();   

          Iterator it = map.entrySet().iterator();   
         while(it.hasNext()){   
              Entry element = (Entry)it.next();   
              params.append(element.getKey());   
              params.append("=");   
              params.append(element.getValue());   
              params.append("&");   
          }   
        
         if(params.length() > 0){   
              params.deleteCharAt(params.length()-1);   
          } 
         

 		HttpClient httpClient = new HttpClient();
 		httpClient.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
 		httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,10000); 
 		GetMethod getMethod = new GetMethod(GopayConfig.gopay_gateway+"?"+params);
 		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");
 		// 执行getMethod
 		int statusCode = 0;
 		try {
 			statusCode = httpClient.executeMethod(getMethod);			
 			if (statusCode == HttpStatus.SC_OK){
 				String respString = StringUtils.trim((new String(getMethod.getResponseBody(),"GBK")));
 				return isSuccess=true;
 			}			
 		} catch (IOException e) {
 			e.printStackTrace();
 		} finally {
 			getMethod.releaseConnection();
 		}
 	
        
         /*try{   
              URL url = new URL(urlAddr);   
              conn = (HttpURLConnection)url.openConnection();   

              conn.setDoOutput(true);   
              conn.setRequestMethod("POST");   
              conn.setUseCaches(false);   
              conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");   
              conn.setRequestProperty("Content-Length", String.valueOf(params.length()));   
              conn.setDoInput(true);   
              conn.connect();   

              OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");   
              out.write(params.toString());   
              out.flush();   
              out.close();   
            
             int code = conn.getResponseCode();   
             if (code != 200) {   
                  System.out.println("ERROR===" + code);   
              } else {   
                  isSuccess = true;   
                  System.out.println("Success!");   
              }   
          }catch(Exception ex){   
              ex.printStackTrace();   
          }finally{   
              conn.disconnect();   
          }  */ 
         return isSuccess;   
  }   

}
