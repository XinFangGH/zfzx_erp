package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.flow.FormFieldDao;
import com.zhiwei.credit.model.flow.FormField;

@SuppressWarnings("unchecked")
public class FormFieldDaoImpl extends BaseDaoImpl<FormField> implements FormFieldDao{

	public FormFieldDaoImpl() {
		super(FormField.class);
	}
	/**
	 * 取某个表的标题字段
	 * @param tableId
	 * @param isFlowTitle
	 * @return
	 */
	public FormField find(Long tableId,Short isFlowTitle){
		String hql="from FormField ff where ff.formTable.tableId=? and ff.isFlowTitle=? ";
		return (FormField)findUnique(hql, new Object[]{
				tableId,isFlowTitle
		});
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.FormFieldDao#getByForeignTableAndKey(java.lang.String, java.lang.String)
	 */
	public List<FormField> getByForeignTableAndKey(String foreignTable,String foreignKey){
		String hql = "select formField from FormField formField where formField.foreignTable=? and formField.foreignKey=?";
		Object[] objs = { foreignTable,foreignKey };
		List<FormField> setList = findByHql(hql, objs);
		return setList;
	}

}