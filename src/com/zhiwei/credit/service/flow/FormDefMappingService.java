package com.zhiwei.credit.service.flow;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.flow.FormDefMapping;

public interface FormDefMappingService extends BaseService<FormDefMapping> {
	/**
	 * 按jbpm流程发布id取得表单映射
	 * 
	 * @param deployId
	 * @return
	 */
	public FormDefMapping getByDeployId(String deployId);

	/**
	 * @description 根据defId查询是否已经设置表单数据,存在：FormDefMapping,否则：null
	 * @param defId
	 *            流程定义Id
	 * @return FormDefMapping,null
	 */
	FormDefMapping findByDefId(Long defId);
	/**
	 * 检查表单是否已经被映射
	 * @param formDefId
	 * @return
	 */
	public boolean formDefHadMapping(Long formDefId);
	
	public void copyNewConfig(String oldDeployId,String newDeployId,Integer versionNo,Long defId);
	
	/**
	 * 根据流程定义id(defId)查询所有版本的deployId,从而更新所有版本的xml文件。
	 * @param defId
	 * @return
	 * add by lu 2012.09.25
	 */
	public List<FormDefMapping> findListByDefId(Long defId);
}
