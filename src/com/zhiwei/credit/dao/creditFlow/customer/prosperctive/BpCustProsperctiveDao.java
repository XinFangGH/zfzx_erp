package com.zhiwei.credit.dao.creditFlow.customer.prosperctive;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive;

/**
 * 
 * @author 
 *
 */
public interface BpCustProsperctiveDao extends BaseDao<BpCustProsperctive>{

	public List<BpCustProsperctive> getList(HttpServletRequest request, Integer start,
			Integer limit, String[] userIds,String departmentId);

	public List<BpCustProsperctive> getByCreatorId(String creatorId);

	public List<BpCustProsperctive> getByBelongId(String belongId);

	public List<BpCustProsperctive> getByAreaId(String areaId);

	public BpCustProsperctive queryTelNumber(String telephoneNumber,String companyId);
	
}