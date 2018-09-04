package com.zhiwei.credit.dao.creditFlow.financeProject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financeProject.VFinancingProject;
/**
 * 
 * @author 
 *
 */
public interface VFinancingProjectDao extends BaseDao<VFinancingProject>{
	
	/**
	 * 得到融资项目信息列表
	 * @param userIdsStr 
	 * @param pb
	 * @param request
	 * @return
	 * @author lisl
	 */
	public List<VFinancingProject> getProjectList(String userIdsStr,PagingBean pb,HttpServletRequest request,Map map);
	
	/**
	 * 通过ProjectId获取VFinancingProject
	 * @param projectId
	 * @return
	 */
	public VFinancingProject getByProjectId(Long projectId);
	
	public List<VFinancingProject> getFinancingList(String projectNum,String projectName,int start,int limit,String companyId);
	
	public long getFinancingNum(String projectNum,String projectName,String companyId);
}