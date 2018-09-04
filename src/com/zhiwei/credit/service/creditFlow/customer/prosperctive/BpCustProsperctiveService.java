package com.zhiwei.credit.service.creditFlow.customer.prosperctive;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive;

/**
 * 
 * @author 
 *
 */
public interface BpCustProsperctiveService extends BaseService<BpCustProsperctive>{

	public List<BpCustProsperctive> getList(HttpServletRequest request, Integer start,
			Integer limit, String[] userIds,String departmentId);
	//查询创建人的意向客户
	public List<BpCustProsperctive> getByCreatorId(String creatorId);
	//查询共享人的意向客户
	public List<BpCustProsperctive> getByBelongId(String belongId);
	//按地区查询意向客户
	public List<BpCustProsperctive> getByAreaId(String areaId);
	/**
	 * 根据分公司Id和联系电话1判断用户是否重复
	 * @param telephoneNumber
	 * @param companyId
	 * @return
	 */
	public BpCustProsperctive queryTelNumber(String telephoneNumber,String companyId);
	
}


