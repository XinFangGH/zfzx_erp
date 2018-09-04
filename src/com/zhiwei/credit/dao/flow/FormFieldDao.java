package com.zhiwei.credit.dao.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.flow.FormField;

/**
 * 
 * @author 
 *
 */
public interface FormFieldDao extends BaseDao<FormField>{
	/**
	 * 取某个表的标题字段
	 * @param tableId
	 * @param isFlowTitle
	 * @return
	 */
	public FormField find(Long tableId,Short isFlowTitle);
	/**
	 * 按外键表名及外键取得字段列表
	 * @param foreignTable
	 * @param foreignKey
	 * @return
	 */
	public List<FormField> getByForeignTableAndKey(String foreignTable,String foreignKey);
}