package com.zhiwei.credit.dao.creditFlow.customer.bankRelationPerson;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.CustomerBankRelationPerson;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.VBankBankcontactperson;

public interface CustomerBankRelationPersonDao extends BaseDao<CustomerBankRelationPerson>{
	public void QueryList(String companyId,int start, int limit , String userIds,String hql ,String[] str,
			Object[] obj,String sort,String dir) throws Exception;
	public List queryPersonWindow(int id, String bankName ,String bankDuty,int start, int limit,String userIds);
	public int queryPersonWindow(int id);
	public CustomerBankRelationPerson getById(Integer id);
	public VBankBankcontactperson queryPersonName(Integer id);
	public List<CustomerBankRelationPerson> getListByFenbankid(Integer fenbankid);
}