package com.zhiwei.credit.dao.p2p.redMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.redMoney.BpCustRedMemberDao;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedMember;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCustRedMemberDaoImpl extends BaseDaoImpl<BpCustRedMember> implements BpCustRedMemberDao{

	public BpCustRedMemberDaoImpl() {
		super(BpCustRedMember.class);
	}

	@Override
	public List<BpCustRedMember> listbyredId(Long redId,String type) {
		
		StringBuffer hql=new StringBuffer(" from BpCustRedMember a where a.redId=? ");
		if(null!=type){
			if(type.equals("ed")){
				hql.append(" and a.edredMoney !=0");
				
			}else if(type.equals("not")){
				
				hql.append(" and a.edredMoney =0");
				
			}
		}
		return this.findByHql(hql.toString(), new Object[]{redId});
	}
	@Override
	public List<BpCustRedMember> getActivityNumber(String activityNumber,
			String bpCustMemberId, String remark) {
		String sql = " from BpCustRedMember where bpCustMemberId=? and activityNumber=?";
		return this.getSession().createQuery(sql).setParameter(0, Long.valueOf(bpCustMemberId)).setParameter(1, activityNumber).list();
	}
}