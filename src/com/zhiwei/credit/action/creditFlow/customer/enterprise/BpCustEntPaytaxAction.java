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
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntLawsuit;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntPaytax;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntPaytaxService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustEntPaytaxAction extends BaseAction{
	@Resource
	private BpCustEntPaytaxService bpCustEntPaytaxService;
	@Resource
	private EnterpriseService enterpriseService;
	private BpCustEntPaytax bpCustEntPaytax;
	
	private Long paytaxsId;

	public Long getPaytaxsId() {
		return paytaxsId;
	}

	public void setPaytaxsId(Long paytaxsId) {
		this.paytaxsId = paytaxsId;
	}

	public BpCustEntPaytax getBpCustEntPaytax() {
		return bpCustEntPaytax;
	}

	public void setBpCustEntPaytax(BpCustEntPaytax bpCustEntPaytax) {
		this.bpCustEntPaytax = bpCustEntPaytax;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		

		
		try {
		       String enterpriseId=this.getRequest().getParameter("enterpriseId");
				List<BpCustEntPaytax> list=new ArrayList<BpCustEntPaytax>();
				list.addAll(enterpriseService.getById(Integer.valueOf(enterpriseId)).getBpCustEntPaytaxlist());
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
		

		
		
		String[]ids=getRequest().getParameterValues("paytaxsId");
		if(ids!=null){
			for(String id:ids){
				bpCustEntPaytax=bpCustEntPaytaxService.getbyId(Integer.valueOf(id));
				bpCustEntPaytaxService.remove(bpCustEntPaytax);
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
		BpCustEntPaytax bpCustEntPaytax=bpCustEntPaytaxService.get(paytaxsId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustEntPaytax));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		String enterpriseId=this.getRequest().getParameter("enterpriseId");
		String paytaxListData =this.getRequest().getParameter("bppaytaxListData");
	
		if(null!=enterpriseId && !"".equals(enterpriseId)){
			
			if(paytaxListData!=null&&!"".equals(paytaxListData)){
				String[] paytaxListDataoArr = paytaxListData.split("@");
				for (int i = 0; i < paytaxListDataoArr.length; i++) {
					String str = paytaxListDataoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					BpCustEntPaytax bpCustEntPaytax;
					try {
						bpCustEntPaytax = (BpCustEntPaytax) JSONMapper.toJava(
								parser.nextValue(), BpCustEntPaytax.class);
					
						bpCustEntPaytax.setEnterprise(enterpriseService.getById(Integer.valueOf(enterpriseId)));
					if(bpCustEntPaytax.getPaytaxsId()==null||"".equals(bpCustEntPaytax.getPaytaxsId())){
						bpCustEntPaytaxService.save(bpCustEntPaytax);
					}else{
						BpCustEntPaytax temp=bpCustEntPaytaxService.getbyId(bpCustEntPaytax.getPaytaxsId());
						BeanUtil.copyNotNullProperties(temp, bpCustEntPaytax);
						bpCustEntPaytaxService.save(temp);
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
