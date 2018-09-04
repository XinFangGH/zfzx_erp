package com.zhiwei.credit.dao.creditFlow.customer.bankRelationPerson.impl;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.customer.bankRelationPerson.CustomerBankRelationPersonDao;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.CustomerBankRelationPerson;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.VBankBankcontactperson;

@SuppressWarnings("unchecked")
public class CustomerBankRelationPersonDaoImpl extends BaseDaoImpl<CustomerBankRelationPerson> implements CustomerBankRelationPersonDao{
	@Resource
	private CreditBaseDao creditBaseDao;
	public CustomerBankRelationPersonDaoImpl() {
		super(CustomerBankRelationPerson.class);
	}
	public void QueryList(String companyId,int start, int limit , String userIds,String hql ,String[] str,
			Object[] obj,String sort,String dir) throws Exception{
		StringBuffer strBuffer = new StringBuffer(hql);
		
		if(null!=userIds && !"".equals(userIds)){
			strBuffer.append(" where b.belongedId in ("+userIds+") and ");
		}else{
			strBuffer.append(" where ");
		}
		
		if(companyId!=null&&!"".equals(companyId)&&!"null".equals(companyId)){
			strBuffer.append(" b.companyId in ("+companyId+") and");
		}
		
		int len = obj.length;
		List list = null;
		int total = 0;
		for(int j = 0 ; j < str.length ; j ++){
			for(int i = j ; i < obj.length ;){
				if(obj[i] == null){
					obj[i] = "";
				}
				strBuffer.append(" b."+str[j]+" like "+"'"+'%'+obj[i]+'%'+"'");
				if(len != j+1){
					strBuffer.append(" and ");
				}else{
					if(sort!=null && dir!=null){
						strBuffer.append(" order by   CONVERT(b."+sort+" , 'GBK') "+dir);
					}else{
						strBuffer.append(" order by b.name asc,b.bankname asc, b.blmtelephone asc");
					}
					
				}
			break;
			}
		}
		list = creditBaseDao.queryHql(strBuffer.toString());
		if(null != list && !"".equals(list)){
			total = list.size();
		}
		list = creditBaseDao.queryHql(strBuffer.toString(), start, limit);
		JsonUtil.jsonFromList(list, total);
	};
	public List queryPersonWindow(int id, String bankName ,String bankDuty,int start, int limit,String userIds) {
		String hql = "";
		List list = null ;
		Object params[] = null;
		
		try {
			if(id == 0){
				params = new Object[]{"%"+bankName+"%","%"+bankDuty+"%"} ;
				if(null!=userIds && !"".equals(userIds)){
					hql = "from  VBankBankcontactperson  p where p.belongedId in ("+userIds+") and p.name like ? and p.duty like ? order by name asc";
				}else{
					hql = "from  VBankBankcontactperson  p where p.name like ? and p.duty like ? order by name asc";
				}
				list = creditBaseDao.queryHql(hql, params ,start, limit) ;
			}else{
				params = new Object[]{"%"+bankName+"%","%"+bankDuty+"%",id,} ;
				if(null!=userIds && !"".equals(userIds)){
					hql = "from  VBankBankcontactperson  p where p.belongedId in ("+userIds+") and p.name like ? and p.duty like ? and p.fenbankid=? order by p.name asc";
				}else{
					hql = "from  VBankBankcontactperson  p where p.name like ? and p.duty like ? and p.fenbankid=? order by p.name asc";
				}
				
				list = creditBaseDao.queryHql(hql, params, start, limit) ;
			}
			return list ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int queryPersonWindow(int id) {
		String hql = null;
		List list = null ;
		try{
			if(id == 0){
				hql = "select count(*) from  VBankBankcontactperson  p";
				list = creditBaseDao.queryHql(hql) ;
			}else{
				hql = "select count(*) from  VBankBankcontactperson  p where p.fenbankid=?";
				list = creditBaseDao.queryHql(hql, id) ;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ((Long)(list.get(0))).intValue() ;
	}
	@Override
	public CustomerBankRelationPerson getById(Integer id) {
		String hql="from CustomerBankRelationPerson as c where c.id=?";
		return (CustomerBankRelationPerson) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}
	@Override
	public VBankBankcontactperson queryPersonName(Integer id) {
		String hql="from VBankBankcontactperson  p where p.id=?";
		return (VBankBankcontactperson) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}
	@Override
	public List<CustomerBankRelationPerson> getListByFenbankid(Integer fenbankid) {
		String hql="from CustomerBankRelationPerson AS cb where cb.fenbankid=?";
		return getSession().createQuery(hql).setParameter(0, fenbankid).list();
	}
}