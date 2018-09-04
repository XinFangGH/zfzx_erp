package com.zhiwei.credit.dao.flow;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.flow.FormDefMapping;

/**
 * 
 * @author
 * 
 */
public interface FormDefMappingDao extends BaseDao<FormDefMapping> {
	/**
	 * 按jbpm流程发布id取得表单映射
	 * 
	 * @param deployId
	 * @return
	 */
	public FormDefMapping getByDeployId(String deployId);

	/**
	 * @description 根据defId判断表单数据是否存在,存在：formDefMapping对应的数据,否则:null
	 * @param defId
	 *            流程定义id
	 * @return FormDefMapping对象
	 */
	FormDefMapping findByDefId(Long defId);
	/**
	 * 根据表单定义去查找表单映射
	 * @param formDefId
	 * @return
	 */
	public List<FormDefMapping> getByFormDef(Long formDefId);
	
	/**
	 * 根据流程定义id(defId)查询所有版本的deployId,从而更新所有版本的xml文件。
	 * @param defId
	 * @return
	 * add by lu 2012.09.25
	 */
	public List<FormDefMapping> findListByDefId(Long defId);
}