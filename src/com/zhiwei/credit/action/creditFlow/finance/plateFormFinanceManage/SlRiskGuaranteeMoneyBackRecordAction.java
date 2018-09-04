package com.zhiwei.credit.action.creditFlow.finance.plateFormFinanceManage;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateFormBidIncomeDetail;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.SlRiskGuaranteeMoneyBackRecord;
import com.zhiwei.credit.service.creditFlow.finance.plateFormFinanceManage.SlRiskGuaranteeMoneyBackRecordService;
/**
 * 
 * @author 
 *
 */
public class SlRiskGuaranteeMoneyBackRecordAction extends BaseAction{
	@Resource
	private SlRiskGuaranteeMoneyBackRecordService slRiskGuaranteeMoneyBackRecordService;
	private SlRiskGuaranteeMoneyBackRecord slRiskGuaranteeMoneyBackRecord;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SlRiskGuaranteeMoneyBackRecord getSlRiskGuaranteeMoneyBackRecord() {
		return slRiskGuaranteeMoneyBackRecord;
	}

	public void setSlRiskGuaranteeMoneyBackRecord(SlRiskGuaranteeMoneyBackRecord slRiskGuaranteeMoneyBackRecord) {
		this.slRiskGuaranteeMoneyBackRecord = slRiskGuaranteeMoneyBackRecord;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SlRiskGuaranteeMoneyBackRecord> list= slRiskGuaranteeMoneyBackRecordService.getAll(filter);
		
		Type type=new TypeToken<List<SlRiskGuaranteeMoneyBackRecord>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				slRiskGuaranteeMoneyBackRecordService.remove(new Long(id));
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
		SlRiskGuaranteeMoneyBackRecord slRiskGuaranteeMoneyBackRecord=slRiskGuaranteeMoneyBackRecordService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slRiskGuaranteeMoneyBackRecord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slRiskGuaranteeMoneyBackRecord.getId()==null){
			slRiskGuaranteeMoneyBackRecordService.save(slRiskGuaranteeMoneyBackRecord);
		}else{
			SlRiskGuaranteeMoneyBackRecord orgSlRiskGuaranteeMoneyBackRecord=slRiskGuaranteeMoneyBackRecordService.get(slRiskGuaranteeMoneyBackRecord.getId());
			try{
				BeanUtil.copyNotNullProperties(orgSlRiskGuaranteeMoneyBackRecord, slRiskGuaranteeMoneyBackRecord);
				slRiskGuaranteeMoneyBackRecordService.save(orgSlRiskGuaranteeMoneyBackRecord);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 获取保证金代偿借款人偿还记录
	 * @return
	 */
	public String getrepaymentRecord (){
		List<SlRiskGuaranteeMoneyBackRecord> list=slRiskGuaranteeMoneyBackRecordService.getrepaymentRecord(this.getRequest(),start,limit);
		List<SlRiskGuaranteeMoneyBackRecord> listcount=slRiskGuaranteeMoneyBackRecordService.getrepaymentRecord(this.getRequest(),null,null);
		Type type = new TypeToken<List<SlRiskGuaranteeMoneyBackRecord>>() {}.getType();
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:").append(listcount == null ? 0 : listcount.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
}
