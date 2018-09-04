package com.zhiwei.credit.service.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.flow.FormTable;
import com.zhiwei.credit.model.system.AppUser;

public interface FormTableService extends BaseService<FormTable>{
	public List<FormTable> getListFromPro(String typeId,String tableName,AppUser curUser,PagingBean pb); 
	/**
	 * 返回所有表定义及其表的字段
	 * @return
	 */
	public List<FormTable> getAllAndFields();
	/**
	 * 根据key来查找表
	 * @return
	 */
	public List<FormTable> findByTableKey(String tableKey);
}


