package com.zhiwei.credit.dao.creditFlow.customer.salesRecord;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.customer.salesRecord.CsSalesRecord;

/**
 * 
 * @author 
 *
 */
public interface CsSalesRecordDao extends BaseDao<CsSalesRecord>{

	public List<CsSalesRecord> getListByRequest(HttpServletRequest request,
			Integer start, Integer limit, String[] userIds);
	
}