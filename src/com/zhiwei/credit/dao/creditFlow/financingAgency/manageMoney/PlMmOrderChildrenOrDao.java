package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOr;

/**
 * 
 * @author 
 *
 */
public interface PlMmOrderChildrenOrDao extends BaseDao<PlMmOrderChildrenOr>{
	public List<PlMmOrderChildrenOr> listupdate(String searchDate);
	List<PlMmOrderChildrenOr> listbysearch(PagingBean pb,Map<String, String> map);
	List<PlMmOrderChildrenOr> listbyorderid(Long orderId,String enddate);
	List<PlMmOrderChildrenOr> listbychildrenorId(Long childrenorId);
	List<PlMmOrderChildrenOr> listbyIds(String ids);
	List<PlMmOrderChildrenOr> listbysame(String matchStartDate,String endStartDate,Long orderId,Long childrenorId);
	List<PlMmOrderChildrenOr> listbyorderid(Long orderId);
	List<PlMmOrderChildrenOr> listbyChildrenorId(Long childrenorId);
}