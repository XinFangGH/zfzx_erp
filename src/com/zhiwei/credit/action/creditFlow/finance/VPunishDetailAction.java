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

import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.finance.VPunishDetail;
import com.zhiwei.credit.service.creditFlow.finance.VPunishDetailService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class VPunishDetailAction extends BaseAction{
	@Resource
	private VPunishDetailService vPunishDetailService;
	
	private VPunishDetail vPunishDetail;
	
	private Long fundDetailId;
	
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
		List<VPunishDetail> list=new ArrayList<VPunishDetail>();
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
		
		list= vPunishDetailService.search(map);
		size= vPunishDetailService.searchsize(map);
		
		for(VPunishDetail l:list){
			if(null!=l.getMyAccount()){
				if(!l.getMyAccount().equals("1111")){
					l.setTransactionType(l.getTransactionType());
					if(l.getMyAccount().equals("cahsqlideAccount")){
						l.setMyAccount("现金账户");
					}
					if(l.getMyAccount().equals("pingqlideAccount")){
						l.setMyAccount("平账");
						l.setQlideincomeMoney(null);
						l.setFactDate(l.getOperTime());
					}
				}
			}else{
				l.setMyAccount("此流水被删除");
			}
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate","operTime");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate","factDate"});
		json.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"),new String[]{"operTime"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	public VPunishDetail getvPunishDetail() {
		return vPunishDetail;
	}
	public void setvPunishDetail(VPunishDetail vPunishDetail) {
		this.vPunishDetail = vPunishDetail;
	}
}