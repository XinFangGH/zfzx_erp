package com.webServices.services.factory.urlFactory;

import com.webServices.services.factory.urlFactory.BaseFactory.WebUrl;
import com.zhiwei.core.util.AppUtil;

public class WebServicesUrl implements WebUrl {
	public static WebServicesUrl wbsUrl = new WebServicesUrl();

	public static WebServicesUrl getInstances() {
		if (wbsUrl == null) {
			wbsUrl = new WebServicesUrl();
		} else {
			return wbsUrl;
		}
		return wbsUrl;
	}

	@Override
	public String createUrl(){
		String ret=null;
		ret=AppUtil.getWebServicesUrl();
		if(ret!=null&&!ret.equals(""))
		{
			return ret;
		}else{
			ret="webServices Url Erro……";
		}
		return ret;
	}

	@Override
	public boolean isOpen() {
		boolean ret=false;
		ret=AppUtil.getWebServicesIsOpen();
		return ret;
	}

	@Override
	public boolean customerIsOpen() {
		boolean ret=false;
		ret=AppUtil.getWebServicesCustomerIsOpen();
		return ret;
	}

}
