package com.zhiwei.credit.action.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.VChargeDetail;
import com.zhiwei.credit.model.creditFlow.finance.VFundDetail;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.finance.VFundDetailService;
import com.zhiwei.credit.service.system.OrganizationService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class VFundDetailAction extends BaseAction{
	@Resource
	private VFundDetailService vFundDetailService;
	private VFundDetail vFundDetail;
	@Resource
	private OrganizationService organizationService;
	
	private Long fundDetailId;


	public VFundDetail getvFundDetail() {
		return vFundDetail;
	}
	public void setvFundDetail(VFundDetail vFundDetail) {
		this.vFundDetail = vFundDetail;
	}
	public Long getFundDetailId() {
		return fundDetailId;
	}
	public void setFundDetailId(Long fundDetailId) {
		this.fundDetailId = fundDetailId;
	}
	/**
	 * 显示列表
	 */
	public String list(){
		Map<String,String> map = new HashMap<String,String>();
		int size=0;
		List<VFundDetail> list=new ArrayList<VFundDetail>();
		String searchaccount;
		Enumeration paramEnu= getRequest().getParameterNames();
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    			String paramValue=(String)getRequest().getParameter(paramName);
    			map.put(paramName, paramValue);
    		
    	}
    	String projectProperties=this.getRequest().getParameter("projectProperties");
		if(null!=projectProperties && !projectProperties.equals("")){
			String properties="";
			String[] propertiesArr=projectProperties.split(",");
			for(int i=0;i<propertiesArr.length;i++){
				properties=properties+"'"+propertiesArr[i]+"',";
			}
			if(!properties.equals("")){
				properties=properties.substring(0,properties.length()-1);
			}
			map.put("properties",properties);
		}
	//	List<VFundDetail> list= vFundDetailService.getAll();
		 list= vFundDetailService.search(map);
		size=vFundDetailService.searchsize(map);
		
		for(VFundDetail l:list){
//			vFundDetailService.evict(l);
//			if(null!=l.getCompanyId()){
//  				Organization organization=organizationService.get(l.getCompanyId());
//  				if(null!=organization){
//  					l.setOrgName(organization.getOrgName());
//  				}
//  			
//  			}
			
			if(null!=l.getMyAccount()){
			if(!l.getMyAccount().equals("1111")){
				l.setTransactionType(l.getQlidetransactionType());
				if(l.getMyAccount().equals("cahsqlideAccount")){
					l.setMyAccount("现金账户");
				}
				if(l.getMyAccount().equals("pingqlideAccount")){
					l.setMyAccount("平账");
					l.setQlideincomeMoney(null);
					l.setQlidepayMoney(null);
					l.setFactDate(l.getOperTime());
				}
			}
			
			}else{
				
				l.setMyAccount("此流水被删除");
				
			}
			
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate","operTime");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate"});
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"factDate"});
		json.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"),new String[]{"operTime"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		
		return SUCCESS;
	}

}
