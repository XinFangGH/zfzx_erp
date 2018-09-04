package com.zhiwei.credit.action.creditFlow.log;
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


import com.zhiwei.credit.model.creditFlow.log.BatchRunRecord;
import com.zhiwei.credit.service.creditFlow.log.BatchRunRecordService;
/**
 * 
 * @author 
 *
 */
public class BatchRunRecordAction extends BaseAction{
	@Resource
	private BatchRunRecordService batchRunRecordService;
	private BatchRunRecord batchRunRecord;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BatchRunRecord getBatchRunRecord() {
		return batchRunRecord;
	}

	public void setBatchRunRecord(BatchRunRecord batchRunRecord) {
		this.batchRunRecord = batchRunRecord;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BatchRunRecord> list= batchRunRecordService.getAll(filter);
		
		Type type=new TypeToken<List<BatchRunRecord>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new  GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
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
				batchRunRecordService.remove(new Long(id));
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
		BatchRunRecord batchRunRecord=batchRunRecordService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(batchRunRecord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(batchRunRecord.getId()==null){
			batchRunRecordService.save(batchRunRecord);
		}else{
			BatchRunRecord orgBatchRunRecord=batchRunRecordService.get(batchRunRecord.getId());
			try{
				BeanUtil.copyNotNullProperties(orgBatchRunRecord, batchRunRecord);
				batchRunRecordService.save(orgBatchRunRecord);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
