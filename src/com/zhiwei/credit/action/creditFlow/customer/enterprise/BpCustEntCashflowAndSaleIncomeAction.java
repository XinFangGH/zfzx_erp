package com.zhiwei.credit.action.creditFlow.customer.enterprise;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import java.io.StringReader;
import java.lang.reflect.Type;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.log.Log;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntCashflowAndSaleIncome;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpstreamCustom;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntCashflowAndSaleIncomeService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustEntCashflowAndSaleIncomeAction extends BaseAction{
	@Resource
	private BpCustEntCashflowAndSaleIncomeService bpCustEntCashflowAndSaleIncomeService;
	@Resource
	private EnterpriseService enterpriseService;
	private BpCustEntCashflowAndSaleIncome bpCustEntCashflowAndSaleIncome;
	
	private Long cashflowAndSaleIncomeId;

	public Long getCashflowAndSaleIncomeId() {
		return cashflowAndSaleIncomeId;
	}

	public void setCashflowAndSaleIncomeId(Long cashflowAndSaleIncomeId) {
		this.cashflowAndSaleIncomeId = cashflowAndSaleIncomeId;
	}

	public BpCustEntCashflowAndSaleIncome getBpCustEntCashflowAndSaleIncome() {
		return bpCustEntCashflowAndSaleIncome;
	}

	public void setBpCustEntCashflowAndSaleIncome(BpCustEntCashflowAndSaleIncome bpCustEntCashflowAndSaleIncome) {
		this.bpCustEntCashflowAndSaleIncome = bpCustEntCashflowAndSaleIncome;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		try {
	       String enterpriseId=this.getRequest().getParameter("enterpriseId");
			List<BpCustEntCashflowAndSaleIncome> list=new ArrayList<BpCustEntCashflowAndSaleIncome>();
			list.addAll(enterpriseService.getById(Integer.valueOf(enterpriseId)).getBpCustEntCashflowAndSaleIncomelist());
			int size=0;
			if(null!=list&&list.size()!=0){
				
				size=list.size();
			}
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
			.append(size).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate"
					);
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
					"intentDate"});
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
		
		String[]ids=getRequest().getParameterValues("cashflowAndSaleIncomeId");
		if(ids!=null){
			for(String id:ids){
			 bpCustEntCashflowAndSaleIncome=bpCustEntCashflowAndSaleIncomeService.getbyId(Integer.valueOf(id));
				bpCustEntCashflowAndSaleIncomeService.remove(bpCustEntCashflowAndSaleIncome);
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
		BpCustEntCashflowAndSaleIncome bpCustEntCashflowAndSaleIncome=bpCustEntCashflowAndSaleIncomeService.get(cashflowAndSaleIncomeId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustEntCashflowAndSaleIncome));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		String enterpriseId=this.getRequest().getParameter("enterpriseId");
		String cashflowandSaleIncomeListData =this.getRequest().getParameter("cashflowandSaleIncomeListData");
	
		if(null!=enterpriseId && !"".equals(enterpriseId)){
			
			if(cashflowandSaleIncomeListData!=null&&!"".equals(cashflowandSaleIncomeListData)){
				String[] personCarInfoArr = cashflowandSaleIncomeListData.split("@");
				for (int i = 0; i < personCarInfoArr.length; i++) {
					String str = personCarInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					BpCustEntCashflowAndSaleIncome bpCustEntCashflowAndSaleIncome;
					try {
						bpCustEntCashflowAndSaleIncome = (BpCustEntCashflowAndSaleIncome) JSONMapper.toJava(
								parser.nextValue(), BpCustEntCashflowAndSaleIncome.class);
				
					bpCustEntCashflowAndSaleIncome.setEnterprise(enterpriseService.getById(Integer.valueOf(enterpriseId)));
					if(bpCustEntCashflowAndSaleIncome.getCashflowAndSaleIncomeId()==null||"".equals(bpCustEntCashflowAndSaleIncome.getCashflowAndSaleIncomeId())){
						bpCustEntCashflowAndSaleIncomeService.save(bpCustEntCashflowAndSaleIncome);
					}else{
						BpCustEntCashflowAndSaleIncome temp=bpCustEntCashflowAndSaleIncomeService.getbyId(bpCustEntCashflowAndSaleIncome.getCashflowAndSaleIncomeId());
						BeanUtil.copyNotNullProperties(temp, bpCustEntCashflowAndSaleIncome);
						bpCustEntCashflowAndSaleIncomeService.save(temp);
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
	
