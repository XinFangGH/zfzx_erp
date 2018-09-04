package com.zhiwei.core.web.filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.crypto.BadPaddingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crypto.encrypt.UnEncryptData;
import com.crypto.encrypt.Util;
import com.zhiwei.credit.core.creditUtils.DateUtil;
import com.zhiwei.credit.util.DesUtils;
import com.zhiwei.credit.util.GetMACUtil;

public class AuthorizationFilter  implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		  
		  if(1==2)
		  {
			  HttpServletRequest request = (HttpServletRequest) req;
			  HttpServletResponse response = (HttpServletResponse) resp;
			  String path = request.getSession().getServletContext().getRealPath("");
			  File file=new File(path+"\\attachFiles\\authorizationFile\\authorization.txt");
			  if(!file.exists()){ //表示授权文件不存在
				  request.setAttribute("errorMessage", "该软件尚未授权,请联系软件供应商!");
				  request.getRequestDispatcher("/authorization.jsp").forward(request, response);
			  }
			  else {
				  
				   BufferedReader br=new BufferedReader(new FileReader(file));
			       String str="";
			       String desContent="";
				   String r=br.readLine();
			       while(r!=null){
				     str+=r;
				     r=br.readLine();
				   }
			      if(str!=""){
			    	  String tempV=Util.getValue("keypath");
			    	  UnEncryptData ue=new UnEncryptData(path+"\\"+tempV);
			    	  byte[] data = Util.readFile(path+"\\attachFiles\\authorizationFile\\authorization.txt");
			    	  try
			    	  {
			    	     byte decryptedData[] =ue.createUnEncryptData(data);
			    	     str=new String(decryptedData);
			    	  }
			    	  catch (Exception e) {
						e.printStackTrace();
					  }
			      String[] temp=str.split(",");
			      String keys=temp[0];
			      String password=temp[1];
			      br.close();
			      System.gc();
			      String macSt=GetMACUtil.getMacStr();
			      DesUtils des = new DesUtils(password.trim());//自定义密钥   
				      try{
					       desContent=des.decrypt(keys);
					       String [] strtemp=desContent.split(",");
			        	   String desCode=strtemp[0]; //机器码
			        	   String date=strtemp[1];  //授权日期
			        	   Integer days=com.zhiwei.core.util.DateUtil.getDaysBetweenDate(DateUtil.getNowDateTime("yyyy-MM-dd"), date);
			        	   if(!desCode.equals(macSt))
			        	   {
			        		   request.setAttribute("errorMessage", "该软件授权密匙不正确,请联系软件供应商!");
			        		   request.getRequestDispatcher("/authorization.jsp").forward(request, response);
			        	   }
			        	   else{
			        		   
			        		    if(days<0){
			        		    	
			        		    	request.getRequestDispatcher("/authorization.jsp").forward(request, response);
			        		        request.setAttribute("errorMessage", "该软件授权密匙已过期,请联系软件供应商!");
			        		    }
			        		    else{
			        		    	chain.doFilter(req, resp);
			        		    	
			        		    }
			        	   }
				      }
				      catch (BadPaddingException e) {
				    	   request.setAttribute("errorMessage", "该软件授权密匙不正确,请联系软件供应商!");
				    	   request.getRequestDispatcher("/authorization.jsp").forward(request, response);
					  }
			      }
			      else{
			    	  request.setAttribute("errorMessage", "该软件尚未授权,请联系软件供应商!");
			    	  request.getRequestDispatcher("/authorization.jsp").forward(request, response);
			      }
			  }
		  }
		  else{
		   chain.doFilter(req, resp);
		  }
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
