package com.zhiwei.credit.dao.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.finance.SlDataList;

/**
 * 
 * @author 
 *
 */
public interface SlDataListDao extends BaseDao<SlDataList>{
	public List<SlDataList> getListByType(String type,String companyId,String startDate,String endDate,String sendPersonId,PagingBean pb);
	public Date getMaxDate(String type);
}