package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.SlLoanAccountErrorLogDao;
import com.zhiwei.credit.model.creditFlow.finance.SlLoanAccountErrorLog;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlLoanAccountErrorLogDaoImpl extends BaseDaoImpl<SlLoanAccountErrorLog> implements SlLoanAccountErrorLogDao{

	public SlLoanAccountErrorLogDaoImpl() {
		super(SlLoanAccountErrorLog.class);
	}

	@Override
	public List<SlLoanAccountErrorLog> getList(String customerName,
			String projectNum,String companyId, PagingBean pb) {
		String hql="from SlLoanAccountErrorLog as s where s.custname like ? or s.duebillno like ?";
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		if(null!=strs && !"".equals(strs)){
			hql=hql+" and s.corpno='"+strs+"'";
		}
		return this.findByHql(hql, new Object[]{"%"+customerName+"%","%"+projectNum+"%"}, pb);
		
	}

}