package com.zhiwei.credit.dao.creditFlow.finance.plateFormFinanceManage.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.finance.plateFormFinanceManage.SlRiskGuaranteeMoneyBackRecordDao;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.SlRiskGuaranteeMoneyBackRecord;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlRiskGuaranteeMoneyBackRecordDaoImpl extends BaseDaoImpl<SlRiskGuaranteeMoneyBackRecord> implements SlRiskGuaranteeMoneyBackRecordDao{

	public SlRiskGuaranteeMoneyBackRecordDaoImpl() {
		super(SlRiskGuaranteeMoneyBackRecord.class);
	}

	@Override
	public List<SlRiskGuaranteeMoneyBackRecord> getrepaymentRecord(
			HttpServletRequest request, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		String hql="from SlRiskGuaranteeMoneyBackRecord as sl where 1=1";
		String punishmentId=request.getParameter("punishmentId");
		if(punishmentId!=null&&!"".equals("punishmentId")){
			hql=hql+" and sl.punishmentId="+Long.valueOf(punishmentId);
		}
		if(start!=null&&limit!=null){
			return this.getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
		}else{
			return this.getSession().createQuery(hql).list();
		}
	}

}