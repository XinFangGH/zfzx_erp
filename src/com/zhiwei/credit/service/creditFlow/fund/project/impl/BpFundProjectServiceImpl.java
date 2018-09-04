package com.zhiwei.credit.service.creditFlow.fund.project.impl;

/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.fund.project.BpFundProjectDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;

/**
 * 
 * @author
 * 
 */
public class BpFundProjectServiceImpl extends BaseServiceImpl<BpFundProject> implements BpFundProjectService {
	private BpFundProjectDao dao;
	@Resource
    private PlBidPlanService plBidPlanService;
	public BpFundProjectServiceImpl(BpFundProjectDao dao) {
		super(dao);
		this.dao = dao;
	}

	@Override
	public BpFundProject getByProjectId(Long projectId, Short type) {
		return dao.getByProjectId(projectId,type);
	}
	
	@Override
	public void updateState(Long bidid,Short s) {
		PlBidPlan plan=plBidPlanService.get(bidid);
		BpFundProject proj=null;
        Long plantId=null;
        try{
		if (plan.getProType().equals("B_Dir")) {
			plantId=plan.getBpBusinessDirPro().getMoneyPlanId();
		} else if (plan.getProType().equals("B_Or")) {
			plantId=plan.getBpBusinessOrPro().getMoneyPlanId();
			
		} else if (plan.getProType().equals("P_Dir")) {
			plantId=plan.getBpPersionDirPro().getMoneyPlanId();
			
		} else if (plan.getProType().equals("P_Or")) {
			plantId=plan.getBpPersionOrPro().getMoneyPlanId();
			
		}
		proj=this.get(plantId);
		proj.setProjectStatus(s);
		this.save(proj);
	}catch(Exception e){
		e.printStackTrace();
	}
	}

	@Override
	public BpFundProject getByBidid(Long bidid) {
		BpFundProject proj=null;
        Long plantId=null;
		try{
		PlBidPlan plan=plBidPlanService.get(bidid);
		
		if (plan.getProType().equals("B_Dir")) {
			plantId=plan.getBpBusinessDirPro().getMoneyPlanId();
		} else if (plan.getProType().equals("B_Or")) {
			plantId=plan.getBpBusinessOrPro().getMoneyPlanId();
			
		} else if (plan.getProType().equals("P_Dir")) {
			plantId=plan.getBpPersionDirPro().getMoneyPlanId();
			
		} else if (plan.getProType().equals("P_Or")) {
			plantId=plan.getBpPersionOrPro().getMoneyPlanId();
			
		}
		proj=this.get(plantId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return proj;
	}
	@Override
	public Long projectCount(String userIdsStr, Short[] projectStatus,
			HttpServletRequest request) {
		
		return dao.projectCount(userIdsStr, projectStatus, request);
	}

	@Override
	public void projectList(PageBean<BpFundProject> pageBean,Map<String,String> map) {
		dao.projectList(pageBean,map);
	}

	@Override
	public Long fundProjectCount(String userIdsStr, HttpServletRequest request) {
		
		return dao.fundProjectCount(userIdsStr, request);
	}

	@Override
	public List<BpFundProject> fundProjectList(String userIdsStr, int start,
			int limit, HttpServletRequest request) {
		
		return dao.fundProjectList(userIdsStr, start, limit, request);
	}

	@Override
	public List<BpFundProject> getProject(Long projectId, String businessType) {
		
		return dao.getProject(projectId, businessType);
	}
	
	@Override
	public List<BpFundProject> projectLoanList(String userIdsStr,
			Short[] projectStatus, PagingBean pb, HttpServletRequest request) {
		
		return dao.projectLoanList(userIdsStr, projectStatus, pb, request);
	}
	
	@Override
	public Long projectLoanCount(String userIdsStr, Short[] projectStatus,
			HttpServletRequest request) {
		
		return dao.projectLoanCount(userIdsStr, projectStatus, request);
	}
	@Override
	public List<BpFundProject> bpProjectList(Short[] projectStatus, PagingBean pb, HttpServletRequest request) {
		
		return dao.bpProjectList(projectStatus, pb, request);
	}
	@Override
	public Long bpProjectCount(Short[] projectStatus,HttpServletRequest request) {
		
		return dao.bpProjectCount(projectStatus, request);
	}
	
	@Override
	public List<BpFundProject> overdueProjectList(Short[] projectStatus, PagingBean pb, HttpServletRequest request) {
		
		return dao.overdueProjectList(projectStatus, pb, request);
	}
	@Override
	public Long overdueProjectCount(Short[] projectStatus,HttpServletRequest request) {
		
		return dao.overdueProjectCount(projectStatus, request);
	}
	@Override
	public List<BpFundProject> IndustryProjectList(Short[] projectStatus, PagingBean pb, HttpServletRequest request) {
		
		return dao.IndustryProjectList(projectStatus, pb, request);
	}
	@Override
	public Long IndustryProjectCount(Short[] projectStatus,HttpServletRequest request) {
		
		return dao.IndustryProjectCount(projectStatus, request);
	}

	@Override
	public Long personBrowerProjectCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.personBrowerProjectCount(request);
	}

	@Override
	public List<BpFundProject> personBrowerProjectList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.personBrowerProjectList(pb, request);
	}

	@Override
	public Long personBrowerOnlineProjectCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.personBrowerOnlineProjectCount(request);
	}

	@Override
	public List<BpFundProject> personBrowerOnlineProjectList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.personBrowerOnlineProjectList(pb, request);
	}

	@Override
	public Long personAssureOnlineProjectCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.personBrowerOnlineProjectCount(request);
	}

	@Override
	public List<BpFundProject> personAssureOnlineProjectList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.personAssureOnlineProjectList(pb, request);
	}

	@Override
	public Long personAssureProjectCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.personAssureProjectCount(request);
	}

	@Override
	public List<BpFundProject> personAssureProjectList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.personAssureProjectList(pb, request);
	}

	@Override
	public Long personLegalOnlineProjectCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.personLegalOnlineProjectCount(request);
	}

	@Override
	public List<BpFundProject> personLegalOnlineProjectList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.personLegalOnlineProjectList(pb, request);
	}

	@Override
	public Long personLegalProjectCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.personLegalProjectCount(request);
	}

	@Override
	public List<BpFundProject> personLegalProjectList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.personLegalProjectList(pb, request);
	}
}
