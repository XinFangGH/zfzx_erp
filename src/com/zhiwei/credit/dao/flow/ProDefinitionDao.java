package com.zhiwei.credit.dao.flow;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.util.List;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.system.AppUser;

/**
 * 
 * @author
 * 
 */
public interface ProDefinitionDao extends BaseDao<ProDefinition> {
	/**
	 * 按发布ID取得XML
	 * 
	 * @param deployId
	 * @return
	 */
	public ProDefinition getByDeployId(String deployId);

	/**
	 * get by name
	 * 
	 * @param name
	 * @return
	 */
	public ProDefinition getByName(String name);

	public List<ProDefinition> getByRights(AppUser curUser,
			ProDefinition proDefinition, QueryFilter filter);

	public boolean checkNameByVo(ProDefinition proDefinition);

	public boolean checkProcessNameByVo(ProDefinition proDefinition);
	//根据流程实现运行状态来查找流程
	public List<ProDefinition> findRunningPro(ProDefinition proDefinition,Short runstate,PagingBean pb);
	
	public ProDefinition getByProcessName(String processName);
	
	public List<ProDefinition> getByProTypeId(Long proTypeId,boolean isGroupVersion,Long branchCompanyId);
	
	public  ProDefinition getByKey(String key);
	
	/**
	 * @description 根据分公司id查询对应流程信息列表
	 * @param branchCompanyId
	 * add by lu 2012.09.13
	 */
	public List<ProDefinition> getByBranchCompanyId(Long branchCompanyId,PagingBean pb,String flowName,String flowDesc);
	
	/**
	 * @description 根据流程分类id查询对应标准流程信息列表
	 * @param proTypeId
	 * add by lu 2012.11.13
	 */
	public List<ProDefinition> getNormalFlowByProTypeId(Long proTypeId);
	
	/**
	 * 根据companyId和processName查询对应的流程定义
	 * @param companyId
	 * @param processName
	 * add by lu 2012.12.27
	 */
	public ProDefinition getByCompanyIdProcessName(Long companyId,String processName);
	
	/**
	 * @description 根据流程的key查询已经分配了该流程的分公司
	 * @param processName
	 * add by lu 2013.07.04
	 */
	public List<ProDefinition> listByProcessName(String processName);
	
	public ProDefinition getDefByKey(String key);
	
}