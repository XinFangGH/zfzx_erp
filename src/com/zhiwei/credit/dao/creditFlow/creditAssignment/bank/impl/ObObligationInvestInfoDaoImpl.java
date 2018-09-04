package com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObObligationInvestInfoDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObObligationInvestInfo;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class ObObligationInvestInfoDaoImpl extends BaseDaoImpl<ObObligationInvestInfo> implements ObObligationInvestInfoDao{

	public ObObligationInvestInfoDaoImpl() {
		super(ObObligationInvestInfo.class);
	}
	@Override
	public List<ObObligationInvestInfo> getListInvestPeonId(
			Long investmentPersonId, String flag) {
		// TODO Auto-generated method stub
		String  hql ="from ObObligationInvestInfo as info where info.investMentPersonId =? ";
		if("2".equals(flag)){//表示查询客户累计投资金额
			hql=hql+"  and info.fundIntentStatus  in (1,2) ";
		}else if("1".equals(flag)){//表示当前客户当前的投资金额
			hql=hql+"  and info.fundIntentStatus=1 ";
		}else if("0".equals(flag)){//表示当前客户的已经投资记录  但是没有对账的投资记录
			hql=hql+"  and info.fundIntentStatus=0 ";
		}
		hql=hql+"   order by info.investStartDate asc ";
		return this.getSession().createQuery(hql).setParameter(0, investmentPersonId).list();
	}
//查询债权产品下面的债权人
	@Override
	public List<ObObligationInvestInfo> getInfoByobObligationProjectId(Long id,
			String flag) {
		String  hql ="from ObObligationInvestInfo as info where info.obligationId =? ";
		if(flag!=null&&!"".equals(flag)&&!"null".equals(flag)){
			hql=hql+"  and info.systemInvest= "+Short.valueOf(flag);
		}
		hql=hql+"   order by info.investStartDate asc ";
		return this.getSession().createQuery(hql).setParameter(0, id).list();
	}

}