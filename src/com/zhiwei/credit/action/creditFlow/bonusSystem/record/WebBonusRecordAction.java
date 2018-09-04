package com.zhiwei.credit.action.creditFlow.bonusSystem.record;
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


import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.model.creditFlow.bonusSystem.record.WebBonusRecord;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedMember;
import com.zhiwei.credit.service.creditFlow.bonusSystem.record.WebBonusRecordService;
/**
 * 
 * @author 
 *
 */
public class WebBonusRecordAction extends BaseAction{
	@Resource
	private WebBonusRecordService webBonusRecordService;
	private WebBonusRecord webBonusRecord;
	
	private Long recordId;

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public WebBonusRecord getWebBonusRecord() {
		return webBonusRecord;
	}

	public void setWebBonusRecord(WebBonusRecord webBonusRecord) {
		this.webBonusRecord = webBonusRecord;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		List<WebBonusRecord> list = webBonusRecordService.findList(getRequest(),this.start,this.limit);
		Long count = webBonusRecordService.findListCount(getRequest());
		
		
		Type type=new TypeToken<List<WebBonusRecord>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(count).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		return SUCCESS;
	}
	public void exportExcel(){
		List<WebBonusRecord> list = webBonusRecordService.findList(getRequest(),this.start,this.limit);
		for(WebBonusRecord bonus:list){
			if(bonus.getOperationType().equals("1")){
				bonus.setOperationTypeText("普通积分");
			}else if(bonus.getOperationType().equals("2")){
				bonus.setOperationTypeText("活动积分");
			}else if(bonus.getOperationType().equals("3")){
				bonus.setOperationTypeText("论坛积分");
			}
			if(bonus.getRecordDirector().equals("1")){
				bonus.setRecordDirectorText("奖励");
			}else if(bonus.getRecordDirector().equals("2")){
				bonus.setRecordDirectorText("扣除");
			}
		}
		String[] tableHeader = { "序号","用户名", "积分类型", "积分","奖励时间","增减方向","操作类型","用户当前积分","活动编号","说明","积分日志"};
		String[] fields = {"customerName","operationTypeText","recordNumber","createTime","recordDirectorText","bonusIntention","totalNumber","activityNumber","bonusDescription","recordMsg"};
		try {
				ExportExcel.export(tableHeader, fields, list,"积分记录", WebBonusRecord.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				webBonusRecordService.remove(new Long(id));
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
		WebBonusRecord webBonusRecord=webBonusRecordService.get(recordId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(webBonusRecord));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(webBonusRecord.getRecordId()==null){
			webBonusRecordService.save(webBonusRecord);
		}else{
			WebBonusRecord orgWebBonusRecord=webBonusRecordService.get(webBonusRecord.getRecordId());
			try{
				BeanUtil.copyNotNullProperties(orgWebBonusRecord, webBonusRecord);
				webBonusRecordService.save(orgWebBonusRecord);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	
}
