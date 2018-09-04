package com.zhiwei.credit.dao.creditFlow.smallLoan.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.finance.ProYearRate;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;

/**
 * 
 * @author 
 *
 */
public interface SlSmallloanProjectDao extends BaseDao<SlSmallloanProject>{
	public List<SlSmallloanProject> getProjectById(Long projectId);
	public List<SlSmallloanProject> getProjectByCompanyId(Long companyId);
	public List<ProYearRate> getProYearRate();
	public List<SlSmallloanProject> getProDetail(Map<String,String> map);
	public List<SlSmallloanProject> getList(short operationType,Date startTime,Date endTime);
	public List<SlSmallloanProject> getListByProjectStatus(short operationType,Date startTime,Date endTime,short projectStatus);
	public List<SlSmallloanProject> getTodayProjectList(Date date);
	public String getList(String customerType,Long customerId);
	public SlSmallloanProject getListByoperationType(Long projectId,String operationType);
	public SlSmallloanProject findByprojectNumber(String projectName);
	public List<SlSmallloanProject> getListOfCustomer(String oppositeType,Long oppositeID);
	
	public List getprojectList(Long companyId,String startDate,String endDate,String status);
	public SlSmallloanProject getProjectNumber(String projectNumberKey);
	public List<SlSmallloanProject> findList(String projectName,
			String projectNumber, String minMoneyStr, String maxMoneyStr,
			String projectStatus, PagingBean pb);
	public void approveProjectList(PageBean<SlSmallloanProject> pageBean,Short projectStatus, String userIdsStr);
	public void getprojectByCustomerId(Integer personId, PageBean pageBean);
}