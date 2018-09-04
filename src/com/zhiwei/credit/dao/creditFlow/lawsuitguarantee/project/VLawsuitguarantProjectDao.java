package com.zhiwei.credit.dao.creditFlow.lawsuitguarantee.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project.SgLawsuitguaranteeProject;
import com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project.VLawsuitguarantProject;

/**
 * 
 * @author 
 *
 */
public interface VLawsuitguarantProjectDao extends BaseDao<VLawsuitguarantProject>{
	public List<VLawsuitguarantProject> getProjectList(String userIdsStr,Short []projectStatus,PagingBean pb,HttpServletRequest request) ;
		
	public VLawsuitguarantProject getByProjectId(Long projectId);
}