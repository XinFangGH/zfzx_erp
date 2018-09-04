package com.webServices.finance;

/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */

import javax.annotation.Resource;

import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;

/**
 * 
 * @author
 * 
 */
public class FundQlideFromWebServiceAction extends BaseAction {
	
	@Resource
	private FundQlideFromWebService fundQlideFromWebService;
	private String startT;
	private String endT;
	

	public String getStartT() {
		return startT;
	}


	public void setStartT(String startT) {
		this.startT = startT;
	}


	public String getEndT() {
		return endT;
	}


	public void setEndT(String endT) {
		this.endT = endT;
	}


	public String importBank() {
		String[] str=new String[2];
		StringBuffer sb = new StringBuffer();
		Long companyId=ContextUtil.getLoginCompanyId();
		try {
			if(startT!=null&&!startT.equals("")&&endT!=null&&!endT.equals("")){
				str=fundQlideFromWebService.getqlideManual(startT, endT, companyId);
				if(str[0].equals("Y")){
					sb.append("{success:true,falg:true,result:");
					sb.append("'"+str[1]+"'");
					sb.append("}");
					
				}else{
					sb.append("{success:true,falg:false,result:");
					sb.append("'"+str[1]+"'");
					sb.append("}");
					
				}
			}
			
		} catch (Exception e) {
			sb.append("{success:false,falg:false,result:'erro'");
			sb.append("}");
		}
		
		
		setJsonString(sb.toString());
		return SUCCESS;
	}


}
