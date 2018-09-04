package com.zhiwei.credit.dao.flow;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.flow.ProcessRun;

/**
 * @description 运行中的流程管理
 * @class ProcessRunDao
 * @author 智维软件
 * @updater YHZ
 * @company www.credit-software.com
 * @data 2010-12-28PM
 * 
 */
public interface ProcessRunDao extends BaseDao<ProcessRun> {

	/**
	 * 
	 * @param piId
	 * @return
	 */
	public ProcessRun getByPiId(String piId);

	/**
	 * 
	 * @param defId
	 * @param pb
	 * @return
	 */
	public List<ProcessRun> getByDefId(Long defId, PagingBean pb);

	/**
	 * 按标题模糊查询某个用户所参与的流程列表
	 * 
	 * @param userId
	 * @param subject
	 * @param pb
	 * @return
	 */
	public List<ProcessRun> getByUserIdSubject(Long userId, String subject,
			PagingBean pb);

	/**
	 * @description 根据流程定义Id,查询对应的数据，如果存在:true,否则:false
	 * @param defId
	 *            流程定义Id
	 * @return 存在:true
	 */
	boolean checkRun(Long defId);
	/**
	 * 查找某个流程正在进行的实例
	 * @param defId
	 * @return
	 */
	public List<ProcessRun> getProcessRunning(Long defId);
	
	/**
	 * 项目事项追回-根据用户id和业务流程key查询相应任务
	 * @param userId
	 * @param processName
	 * @param PagingBean
	 * @return
	 * add by lu 2012.03.08
	 */
	public List<ProcessRun> getProcessRunsByUserIdProcessName(Long userId,String processName,PagingBean pb);
	
	/**
	 * 我参与的项目-根据用户id和业务流程key查询相应任务
	 * @param userId
	 * @param processName
	 * @param PagingBean
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param runStatus
	 * @param subject
	 * @return
	 * add by lu 2012.03.09
	 */
	public List<ProcessRun> getMyProjectsByUserIdProcessName(Long userId,String processName,PagingBean pb,String createTimeStart,String createTimeEnd,String runStatus,String subject);
	
	/**
	 * 我发起的项目-根据用户id和业务流程key查询相应任务
	* @param userId
	 * @param processName
	 * @param PagingBean
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param runStatus
	 * @param subject
	 * @return
	 * add by lu 2012.03.09
	 */
	public List<ProcessRun> getMyCreateProjectsByUserIdProcessName(Long userId,String processName,PagingBean pb,String createTimeStart,String createTimeEnd,String runStatus,String subject);
	
	/**
	 * 查询小贷常规流程、快速流程是否存在展期流程。add by lu 2012.04.24
	 * @param projectId
	 * @param businessType
	 * @param processName
	 * @return
	 * add by lu 2012.04.24
	 */
	public List<ProcessRun> getByProcessNameProjectId(Long projectId,String businessType,String processName);
	
	public ProcessRun getByBusinessTypeProjectId(Long projectId,String businessType);
}