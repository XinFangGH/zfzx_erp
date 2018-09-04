package com.zhiwei.credit.service.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.flow.FormTemplateDao;
import com.zhiwei.credit.model.flow.FormDefMapping;
import com.zhiwei.credit.model.flow.FormTemplate;
import com.zhiwei.credit.service.flow.FormTemplateService;

public class FormTemplateServiceImpl extends BaseServiceImpl<FormTemplate> implements FormTemplateService{
	@SuppressWarnings("unused")
	private FormTemplateDao dao;
	
	public FormTemplateServiceImpl(FormTemplateDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 按映射取到所有的流程表单定义
	 * @param mappingId
	 * @return
	 */
	public List<FormTemplate> getByMappingId(Long mappingId){
		return dao.getByMappingId(mappingId);
	}

	@Override
	public void batchAddDefault(List<String> nodeNames, FormDefMapping fdm) {
		for(String nodeName:nodeNames){
			FormTemplate formTemplate=new FormTemplate();
			formTemplate.setFormDefMapping(fdm);
			formTemplate.setNodeName(nodeName);
			save(formTemplate);
		}
		
	}
	
	/**
	 * 取得映射
	 * @param mappingId
	 * @param nodeName
	 * @return
	 */
	public FormTemplate getByMappingIdNodeName(Long mappingId,String nodeName){
		return dao.getByMappingIdNodeName(mappingId, nodeName);
	}
}