package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundDetailDao;
import com.zhiwei.credit.model.creditFlow.finance.SlFundDetail;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlFundDetailDaoImpl extends BaseDaoImpl<SlFundDetail> implements SlFundDetailDao{

	public SlFundDetailDaoImpl() {
		super(SlFundDetail.class);
	}

	@Override
	public List<SlFundDetail> getlistbySlFundIntentId(Long slFundIntentId, String type) {
		StringBuffer sql  = new StringBuffer();
		sql.append("(select" +
				" sq.myAccount as qlidemyAccount," +
				" sq.currency as qlidecurrency," +
				" sfd.factDate," +
				" sq.incomeMoney as qlideincomeMoney," +
				" sq.payMoney as qlidepayMoney," +
				" sfd.afterMoney," +
				" sfd.iscancel," +
				" sfd.cancelremark," +
				" IF(sfd.overdueAccrual,sfd.overdueAccrual,0) as overdueAccrual," +
				" sfd.overdueNum" +
				" from sl_fund_detail as sfd " +
				" LEFT JOIN sl_fund_qlide as sq on sq.fundQlideId=sfd.fundQlideId" +
				" where sfd.fundIntentId="+slFundIntentId +" order by sfd.factDate desc)");
		/*if("0".equals(type)){
			sql.append(" UNION ");
			sql.append("(select" +
					" sq.myAccount as qlidemyAccount," +
					" sq.currency as qlidecurrency," +
					" spd.factDate," +
					" sq.incomeMoney as qlideincomeMoney," +
					" sq.payMoney as qlidepayMoney," +
					" spd.afterMoney," +
					" spd.iscancel," +
					" spd.cancelremark," +
					" 0 as overdueAccrual," +
					" 0 as overdueNum" +
					" from sl_punish_interest as sp " +
					" LEFT JOIN sl_punish_detail as spd on spd.punishInterestId=sp.punishInterestId" +
					" LEFT JOIN sl_fund_qlide as sq on sq.fundQlideId=spd.fundQlideId" +
					" where sp.fundIntentId="+slFundIntentId+" order by spd.factDate desc)");
		}*/
		List list = this.getSession().createSQLQuery(sql.toString())
			.addScalar("qlidemyAccount", Hibernate.STRING)
			.addScalar("qlidecurrency", Hibernate.STRING)
			.addScalar("cancelremark", Hibernate.STRING)
			.addScalar("factDate", Hibernate.DATE)
			.addScalar("iscancel", Hibernate.SHORT)
			.addScalar("qlideincomeMoney", Hibernate.BIG_DECIMAL)
			.addScalar("qlidepayMoney", Hibernate.BIG_DECIMAL)
			.addScalar("afterMoney", Hibernate.BIG_DECIMAL)
			.addScalar("overdueAccrual", Hibernate.BIG_DECIMAL)
			.addScalar("overdueNum", Hibernate.LONG)
			.setResultTransformer(Transformers.aliasToBean(SlFundDetail.class)).list();
		return list;
	}

	@Override
	public List<SlFundDetail> getallbycompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SlFundDetail> getAllRecordList(String projectId, String period) {
		StringBuffer sql  = new StringBuffer();
		sql.append("( select" +
				" sq.myAccount as qlidemyAccount," +
				" sq.currency as qlidecurrency," +
				" sd.factDate," +
				" sq.incomeMoney as qlideincomeMoney," +
				" sq.payMoney as qlidepayMoney," +
				" sd.afterMoney," +
				" sd.iscancel," +
				" sd.cancelremark," +
				" 0 AS overdueAccrual," +
				" 0 AS overdueNum" +
				" from" +
				" (" +
				"  select" +
				"  sfd.fundQlideId,sfd.factDate,sfd.afterMoney,sfd.iscancel,sfd.cancelremark," +
				"  IF(sfd.overdueAccrual,sfd.overdueAccrual,0) as overdueAccrual," +
				"  sfd.overdueNum" +
				"  from sl_fund_detail as sfd " +
				"  LEFT JOIN sl_fund_intent as sf on sf.fundIntentId=sfd.fundIntentId " +
				"  where sf.projectId="+projectId+" and sf.payintentPeriod="+period+"" +
				" ) as sd" +
				" LEFT JOIN sl_fund_qlide as sq on sq.fundQlideId=sd.fundQlideId" +
				" order by sd.factDate desc) ");
			sql.append(" UNION ALL ");
			sql.append("(select" +
					" sq.myAccount as qlidemyAccount," +
					" sq.currency as qlidecurrency," +
					" spd.factDate," +
					" sq.incomeMoney as qlideincomeMoney," +
					" sq.payMoney as qlidepayMoney," +
					" spd.afterMoney," +
					" spd.iscancel," +
					" spd.cancelremark," +
					" 0 as overdueAccrual," +
					" 0 as overdueNum" +
					" from sl_punish_detail as spd" +
					" LEFT JOIN sl_fund_qlide as sq on sq.fundQlideId=spd.fundQlideId" +
					" where spd.punishInterestId IN" +
					"  (" +
					" 	 select " +
					"	 si.punishInterestId from sl_punish_interest as si where si.fundIntentId IN" +
					"    ( select fundIntentId from  sl_fund_intent  as  sf where  sf.projectId="+projectId+" and sf.payintentPeriod="+period+" )" +
					"  )" +
					" order by spd.factDate desc" +
					")");
			
		List list = this.getSession().createSQLQuery(sql.toString())
			.addScalar("qlidemyAccount", Hibernate.STRING)
			.addScalar("qlidecurrency", Hibernate.STRING)
			.addScalar("cancelremark", Hibernate.STRING)
			.addScalar("factDate", Hibernate.DATE)
			.addScalar("iscancel", Hibernate.SHORT)
			.addScalar("qlideincomeMoney", Hibernate.BIG_DECIMAL)
			.addScalar("qlidepayMoney", Hibernate.BIG_DECIMAL)
			.addScalar("afterMoney", Hibernate.BIG_DECIMAL)
			.addScalar("overdueAccrual", Hibernate.BIG_DECIMAL)
			.addScalar("overdueNum", Hibernate.LONG)
			.setResultTransformer(Transformers.aliasToBean(SlFundDetail.class)).list();
		return list;
	}


}