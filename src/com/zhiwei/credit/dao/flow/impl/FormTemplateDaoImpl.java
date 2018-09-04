package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.flow.FormTemplateDao;
import com.zhiwei.credit.model.flow.FormTemplate;

@SuppressWarnings("unchecked")
public class FormTemplateDaoImpl extends BaseDaoImpl<FormTemplate> implements FormTemplateDao{

	public FormTemplateDaoImpl() {
		super(FormTemplate.class);
	}
	/**
	 * 按映射取到所有的流程表单定义
	 * @param mappingId
	 * @return
	 */
	public List<FormTemplate> getByMappingId(Long mappingId){
		String hql="from FormTemplate ft where ft.formDefMapping.mappingId=?";
		return findByHql(hql, new Object[]{mappingId});
	}
	/**
	 * 取得映射
	 * @param mappingId
	 * @param nodeName
	 * @return
	 */
	public FormTemplate getByMappingIdNodeName(Long mappingId,String nodeName){
		String hql="from FormTemplate ft where ft.formDefMapping.mappingId=? and ft.nodeName=?";
		return (FormTemplate)findUnique(hql, new Object[]{mappingId,nodeName});
	}
	

}