package com.zhiwei.credit.service.flow.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.dao.flow.FormDefMappingDao;
import com.zhiwei.credit.model.flow.FormDefMapping;
import com.zhiwei.credit.service.flow.FormDefMappingService;

public class FormDefMappingServiceImpl extends BaseServiceImpl<FormDefMapping>
		implements FormDefMappingService {

	private FormDefMappingDao dao;

	public FormDefMappingServiceImpl(FormDefMappingDao dao) {
		super(dao);
		this.dao = dao;
	}

	/**
	 * 按jbpm流程发布id取得表单映射
	 * 
	 * @param deployId
	 * @return
	 */
	public FormDefMapping getByDeployId(String deployId) {
		return dao.getByDeployId(deployId);
	}

	// 根据defId查询是否已经设置表单数据
	@Override
	public FormDefMapping findByDefId(Long defId) {
		return dao.findByDefId(defId);
	}

	@Override
	public boolean formDefHadMapping(Long formDefId) {
		List<FormDefMapping> mps=dao.getByFormDef(formDefId);
		if(mps.size()>0){
			return true;
		}
		return false;
	}
	
	public void copyNewConfig(String oldDeployId,String newDeployId,Integer versionNo,Long defId){
		FormDefMapping formDefMap=dao.getByDeployId(oldDeployId);
		if(formDefMap!=null){
			FormDefMapping newDefMapping=new FormDefMapping();
			try{
				BeanUtil.copyNotNullProperties(newDefMapping, formDefMap);
				if(defId!=null&&!"".equals(defId)){
					newDefMapping.setDefId(defId);
				}
				newDefMapping.setMappingId(null);
				newDefMapping.setDeployId(newDeployId);
				newDefMapping.setVersionNo(versionNo);
				newDefMapping.setFieldRightss(null);
	    		dao.save(newDefMapping);
			}catch(Exception ex){
				logger.error(ex);
			}
		}
	}
	
	/**
	 * 根据流程定义id(defId)查询所有版本的deployId,从而更新所有版本的xml文件。
	 * @param defId
	 * @return
	 * add by lu 2012.09.25
	 */
	public List<FormDefMapping> findListByDefId(Long defId){
		return dao.findListByDefId(defId);
	}
}