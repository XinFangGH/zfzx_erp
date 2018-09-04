package com.zhiwei.credit.dao.creditFlow.customer.person.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.customer.person.SpouseDao;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;

@SuppressWarnings("unchecked")
public class SpouseDaoImpl extends BaseDaoImpl<Spouse> implements SpouseDao{

	public SpouseDaoImpl() {
		super(Spouse.class);
	}
	public Spouse getByPersonId(Integer personId) {
		String hql="from Spouse as s where s.personId=?";
		Spouse spouse=null;
		try {
			List list=this.findByHql(hql, new Object[]{personId});
			if(null!=list && list.size()>0){
				spouse=(Spouse) list.get(0);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return spouse;
	}
}