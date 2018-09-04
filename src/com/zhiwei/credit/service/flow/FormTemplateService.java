package com.zhiwei.credit.service.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.flow.FormDefMapping;
import com.zhiwei.credit.model.flow.FormTemplate;

public interface FormTemplateService extends BaseService<FormTemplate>{
	/**
	 * 按映射取到所有的流程表单定义
	 * @param mappingId
	 * @return
	 */
	public List<FormTemplate> getByMappingId(Long mappingId);
	
	/**
	 * 为某个流程定义映射添加缺省的流程表单定义
	 * @param nodeNames
	 * @param fdm
	 */
	public void batchAddDefault(List<String>nodeNames,FormDefMapping fdm);
	
	/**
	 * 取得映射
	 * @param mappingId
	 * @param nodeName
	 * @return
	 */
	public FormTemplate getByMappingIdNodeName(Long mappingId,String nodeName);
}
