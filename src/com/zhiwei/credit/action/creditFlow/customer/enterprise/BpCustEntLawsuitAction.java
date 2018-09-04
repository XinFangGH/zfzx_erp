package com.zhiwei.credit.action.creditFlow.customer.enterprise;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import java.io.StringReader;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntCashflowAndSaleIncome;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntLawsuit;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntLawsuitService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustEntLawsuitAction extends BaseAction{
	@Resource
	private BpCustEntLawsuitService bpCustEntLawsuitService;
	@Resource
	private EnterpriseService enterpriseService;
	private BpCustEntLawsuit bpCustEntLawsuit;
	
	private Long lawsuitId;

	public Long getLawsuitId() {
		return lawsuitId;
	}

	public void setLawsuitId(Long lawsuitId) {
		this.lawsuitId = lawsuitId;
	}

	public BpCustEntLawsuit getBpCustEntLawsuit() {
		return bpCustEntLawsuit;
	}

	public void setBpCustEntLawsuit(BpCustEntLawsuit bpCustEntLawsuit) {
		this.bpCustEntLawsuit = bpCustEntLawsuit;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		try {
		       String enterpriseId=this.getRequest().getParameter("enterpriseId");
				List<BpCustEntLawsuit> list=new ArrayList<BpCustEntLawsuit>();
				list.addAll(enterpriseService.getById(Integer.valueOf(enterpriseId)).getBpCustEntLawsuitlist());
				int size=0;
				if(null!=list&&list.size()!=0){
					
					size=list.size();
				}
				StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(size).append(",result:");
				JSONSerializer json = JsonUtil.getJSONSerializer("registerDate","recordTime"
						);
				json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
					"registerDate","recordTime"});
				buff.append(json.serialize(list));
				buff.append("}");
				jsonString = buff.toString();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			
			return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("lawsuitId");
		if(ids!=null){
			for(String id:ids){
				bpCustEntLawsuit=bpCustEntLawsuitService.getbyId(Integer.valueOf(id));
				bpCustEntLawsuitService.remove(bpCustEntLawsuit);
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		BpCustEntLawsuit bpCustEntLawsuit=bpCustEntLawsuitService.get(lawsuitId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustEntLawsuit));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		String enterpriseId=this.getRequest().getParameter("enterpriseId");
		String bplawsuitListData =this.getRequest().getParameter("bplawsuitListData");
	
		if(null!=enterpriseId && !"".equals(enterpriseId)){
			
			if(bplawsuitListData!=null&&!"".equals(bplawsuitListData)){
				String[] personCarInfoArr = bplawsuitListData.split("@");
				for (int i = 0; i < personCarInfoArr.length; i++) {
					String str = personCarInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					BpCustEntLawsuit bpCustEntLawsuit;
					try {
						bpCustEntLawsuit = (BpCustEntLawsuit) JSONMapper.toJava(
								parser.nextValue(), BpCustEntLawsuit.class);
						AppUser user = ContextUtil.getCurrentUser();
						bpCustEntLawsuit.setRecordUser(user.getFullname());
						
						bpCustEntLawsuit.setRecordTime(new Date());
						bpCustEntLawsuit.setEnterprise(enterpriseService.getById(Integer.valueOf(enterpriseId)));
					if(bpCustEntLawsuit.getLawsuitId()==null||"".equals(bpCustEntLawsuit.getLawsuitId())){
						bpCustEntLawsuitService.save(bpCustEntLawsuit);
					}else{
						BpCustEntLawsuit temp=bpCustEntLawsuitService.getbyId(bpCustEntLawsuit.getLawsuitId());
						BeanUtil.copyNotNullProperties(temp, bpCustEntLawsuit);
						bpCustEntLawsuitService.save(temp);
					}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
				
			}
		
		
	}
		return SUCCESS;
}
}
