package com.zhiwei.credit.service.creditFlow.lawsuitguarantee.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.lawsuitguarantee.project.VLawsuitguarantProjectDao;
import com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project.VLawsuitguarantProject;
import com.zhiwei.credit.service.creditFlow.lawsuitguarantee.project.VLawsuitguarantProjectService;

/**
 * 
 * @author 
 *
 */
public class VLawsuitguarantProjectServiceImpl extends BaseServiceImpl<VLawsuitguarantProject> implements VLawsuitguarantProjectService{
	@SuppressWarnings("unused")
	private VLawsuitguarantProjectDao dao;
	
	public VLawsuitguarantProjectServiceImpl(VLawsuitguarantProjectDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<VLawsuitguarantProject> getProjectList(String userIdsStr,
			Short[] projectStatus, PagingBean pb, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.getProjectList(userIdsStr, projectStatus, pb, request);
	}
	
	public VLawsuitguarantProject getByProjectId(Long projectId) {
		return dao.getByProjectId(projectId);
	}
	
}