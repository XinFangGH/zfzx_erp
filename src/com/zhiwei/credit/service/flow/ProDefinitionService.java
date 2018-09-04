package com.zhiwei.credit.service.flow;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.util.List;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.system.AppUser;

public interface ProDefinitionService extends BaseService<ProDefinition> {
	public ProDefinition getByDeployId(String deployId);

	public ProDefinition getByName(String name);

	public List<ProDefinition> getByRights(AppUser curUser,
			ProDefinition proDefinition, QueryFilter filter);

	/**
	 * 返回true则通过,false则重名
	 * 
	 * @param proDefinition
	 * @return
	 */
	public boolean checkNameByVo(ProDefinition proDefinition);

	/**
	 * 返回true则通过,false则重名
	 * 
	 * @param proDefinition
	 * @return
	 */
	public boolean checkProcessNameByVo(ProDefinition proDefinition);

	// 根据流程实现运行状态来查找流程
	public List<ProDefinition> findRunningPro(ProDefinition proDefinition,
			Short runstate, PagingBean pb);
	
	public  ProDefinition getProdefinitionByProcessName(String processName);
	/**
	 * @description 保存流程信息,包含流程发布
	 * @param proDefinition ProDefinition
	 * @param deploy true:保存，并发布;false:只保存
	 * @return 数据操作信息
	 */
	String defSave(ProDefinition proDefinition,Boolean deploy);
	
	public List<ProDefinition> getByProTypeId(Long proTypeId,boolean isGroupVersion,Long branchCompanyId);
	
	public  ProDefinition getByKey(String key);
	
	/**
	 * @description 根据分公司id查询对应流程信息列表
	 * @param branchCompanyId
	 * @param PagingBean
	 * add by lu 2012.09.13
	 */
	public List<ProDefinition> getByBranchCompanyId(Long branchCompanyId,PagingBean pb,String flowName,String flowDesc);
	
	/**
	 * @description 复制标准流程的节点文件(vm)到新创建的分公司目录版本下。
	 * @param ProDefinition
	 * add by lu 2012.09.13
	 */
	public String copyNormalVMToBranchCompany(ProDefinition proDefinition,String oldDeployId);
	
	/**
	 * @description 根据流程分类id查询对应标准流程信息列表
	 * @param proTypeId
	 * add by lu 2012.11.13
	 */
	public List<ProDefinition> getNormalFlowByProTypeId(Long proTypeId);
	
	/**
	 * @description 根据流程的key查询已经分配了该流程的分公司
	 * @param processName
	 * add by lu 2013.07.04
	 */
	public List<ProDefinition> listByProcessName(String processName);
	
	/**
	 * 根据companyId和processName查询对应的流程定义
	 * @param companyId
	 * @param processName
	 * add by lu 2012.12.27
	 */
	public ProDefinition getByCompanyIdProcessName(Long companyId,String processName);
}
