package com.zhiwei.credit.dao.pay.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.pay.BankCodeDao;
import com.zhiwei.credit.model.pay.BankCode;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BankCodeDaoImpl extends BaseDaoImpl<BankCode> implements BankCodeDao{

	public BankCodeDaoImpl() {
		super(BankCode.class);
	}
	/**
	 * 获取省市列表
	 * @param ParentCode
	 * @return
	 */
	@Override
	public List<BankCode> getAreaList(String ParentCode) {
		List<BankCode> list =null;
		StringBuffer hql = new StringBuffer("from BankCode where parentCode = ").append(ParentCode);
		list =  findByHql(hql.toString());
		
		return list;
	}
	@Override
	public BankCode getBycityName(String cityname, String thirdPayConfig) {
		// TODO Auto-generated method stub
		String sql ="from BankCode as b where b.name=? and b.thirdPayConfig=? and b.parentCode !=0";
		return (BankCode) this.getSession().createQuery(sql).setParameter(0, cityname).setParameter(1, thirdPayConfig).uniqueResult();
	}

}