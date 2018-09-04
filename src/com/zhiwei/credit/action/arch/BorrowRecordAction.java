package com.zhiwei.credit.action.arch;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.arch.BorrowFileList;
import com.zhiwei.credit.model.arch.BorrowRecord;
import com.zhiwei.credit.service.arch.BorrowFileListService;
import com.zhiwei.credit.service.arch.BorrowRecordService;
/**
 * 
 * @author 
 *
 */
public class BorrowRecordAction extends BaseAction{
	@Resource
	private BorrowRecordService borrowRecordService;
	private BorrowRecord borrowRecord;
	@Resource
	private BorrowFileListService borrowFileListService;
	
	private Long recordId;

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public BorrowRecord getBorrowRecord() {
		return borrowRecord;
	}

	public void setBorrowRecord(BorrowRecord borrowRecord) {
		this.borrowRecord = borrowRecord;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BorrowRecord> list= borrowRecordService.getAll(filter);
		
		Type type=new TypeToken<List<BorrowRecord>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation(). setDateFormat("yyyy-MM-dd").create();
		
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
				borrowRecordService.remove(new Long(id));
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
		BorrowRecord borrowRecord=borrowRecordService.get(recordId);
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(borrowRecord));
		sb.append("}");
		setJsonString(sb.toString());
		logger.debug("sb:"+sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		
		if (borrowRecord.getRecordId()== null) {
			borrowRecordService.save(borrowRecord);
		} else {
			BorrowRecord orgBorrowRecord = borrowRecordService.get(borrowRecord.getRecordId());
			try {
				Set borrowFileLists = orgBorrowRecord.getBorrowFileLists();
				BeanUtil.copyNotNullProperties(orgBorrowRecord, borrowRecord);
				orgBorrowRecord.setBorrowFileLists(borrowFileLists);
				borrowRecordService.save(orgBorrowRecord);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		
		
		String params = getRequest().getParameter("params");
		if (StringUtils.isNotEmpty(params)) {
			Gson gson =new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			BorrowFileList[] bfl = gson.fromJson(params, BorrowFileList[].class);
			if (bfl != null && bfl.length > 0) {
				for (BorrowFileList l : bfl) {
					
					l.setRecordId(borrowRecord.getRecordId());
					
					borrowFileListService.save(l);

				}
			}

		}

		setJsonString("{success:true,recordId:" + borrowRecord.getRecordId()
				+ "}");
		
		
		
		return SUCCESS;
	}
	public String check(){
		
		BorrowRecord r=borrowRecordService.get(borrowRecord.getRecordId());
		r.setReturnStatus(borrowRecord.getReturnStatus());
		r.setCheckId(ContextUtil.getCurrentUserId());
		r.setCheckName(ContextUtil.getCurrentUser().getUsername());
		borrowRecordService.save(r);
		
		setJsonString("{success:true,recordId:" + r.getRecordId()
				+ "}");
		
		
		
		return SUCCESS;
	}
}
