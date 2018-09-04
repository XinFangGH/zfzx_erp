package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.flow.FormDefMappingDao;
import com.zhiwei.credit.model.flow.FormDefMapping;

@SuppressWarnings("unchecked")
public class FormDefMappingDaoImpl extends BaseDaoImpl<FormDefMapping> implements FormDefMappingDao{

	public FormDefMappingDaoImpl() {
		super(FormDefMapping.class);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.dao.flow.FormDefMappingDao#getByDeployId(java.lang.String)
	 */
	public FormDefMapping getByDeployId(String deployId){
		String hql="from FormDefMapping fdm where fdm.deployId=?";
		return (FormDefMapping)findUnique(hql, new Object[]{deployId});
	}
	
	/**
	 * 根据defId判断是否已经设置表单数据
	 */
	@Override
	public FormDefMapping findByDefId(Long defId) {
		String hql = "select m from FormDefMapping m where m.proDefinition.defId = ? ";
		Object[] paramList = { defId };
		List<FormDefMapping> list = findByHql(hql, paramList);
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}
	@Override
	public List<FormDefMapping> getByFormDef(Long formDefId) {
		String hql="from FormDefMapping vo where vo.formDef.formDefId=?";
		return findByHql(hql,new Object[]{formDefId});
	}
	
	/**
	 * 根据流程定义id(defId)查询所有版本的deployId,从而更新所有版本的xml文件。
	 * @param defId
	 * @return
	 * add by lu 2012.09.25
	 */
	public List<FormDefMapping> findListByDefId(Long defId){
		String hql = "from FormDefMapping fdm where fdm.proDefinition.defId=?";
		return findByHql(hql,new Object[]{defId});
	}

}