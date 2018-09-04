package com.zhiwei.credit.service.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.flow.FormFieldDao;
import com.zhiwei.credit.model.flow.FormField;
import com.zhiwei.credit.service.flow.FormFieldService;

public class FormFieldServiceImpl extends BaseServiceImpl<FormField> implements FormFieldService{
	@SuppressWarnings("unused")
	private FormFieldDao dao;
	
	public FormFieldServiceImpl(FormFieldDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 取某个表的标题字段
	 * @param tableId
	 * @param isFlowTitle
	 * @return
	 */
	public FormField find(Long tableId,Short isFlowTitle){
		return dao.find(tableId, isFlowTitle);
	}

}