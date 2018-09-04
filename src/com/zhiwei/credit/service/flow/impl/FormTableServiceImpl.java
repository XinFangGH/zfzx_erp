package com.zhiwei.credit.service.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.flow.FormTableDao;
import com.zhiwei.credit.model.flow.FormTable;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.flow.FormTableService;

public class FormTableServiceImpl extends BaseServiceImpl<FormTable> implements FormTableService{
	@SuppressWarnings("unused")
	private FormTableDao dao;
	
	public FormTableServiceImpl(FormTableDao dao) {
		super(dao);
		this.dao=dao;
	}
	public List<FormTable> getListFromPro(String typeId,String tableName,AppUser curUser,PagingBean pb){
		
		return this.dao.getListFromPro( typeId, tableName, curUser, pb);
	}
	
	/**
	 * 返回所有表定义及其表的字段
	 * @return
	 */
	public List<FormTable> getAllAndFields(){
		return dao.getAllAndFields();
	}
	@Override
	public List<FormTable> findByTableKey(String tableKey) {
		return dao.findByTableKey(tableKey);
	}

}