package com.zhiwei.credit.dao.creditFlow.customer.prosperctive;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProspectiveFollowup;

/**
 * 
 * @author 
 *
 */
public interface BpCustProspectiveFollowupDao extends BaseDao<BpCustProspectiveFollowup>{

	public List<BpCustProspectiveFollowup> getList(HttpServletRequest request,
			Integer start, Integer limit, String[] userIds,String departmentId);

	public List<BpCustProspectiveFollowup> getListByPerId(String perId,
			HttpServletRequest request, Integer start, Integer limit);
	
}