package com.zhiwei.credit.service.creditFlow.customer.bankRelationPerson.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.customer.bankRelationPerson.CustomerBankRelationPersonDao;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.CustomerBankRelationPerson;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.VBankBankcontactperson;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.service.creditFlow.customer.bankRelationPerson.CustomerBankRelationPersonService;

public class CustomerBankRelationPersonServiceImpl extends BaseServiceImpl<CustomerBankRelationPerson> implements CustomerBankRelationPersonService{
	@SuppressWarnings("unused")
	private CustomerBankRelationPersonDao dao;
	@Resource
	private CreditBaseDao creditBaseDao;
	public CustomerBankRelationPersonServiceImpl(CustomerBankRelationPersonDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public void QueryList(String companyId,int start, int limit , String userIds,String hql ,String[] str,
			Object[] obj,String sort,String dir) throws Exception {
		dao.QueryList(companyId, start, limit, userIds, hql, str, obj, sort, dir);
		
	}

	@Override
	public List queryPersonWindow(int id, String bankName, String bankDuty,
			int start, int limit, String userIds) {
		return dao.queryPersonWindow(id, bankName, bankDuty, start, limit, userIds);
	}

	@Override
	public int queryPersonWindow(int id) {
		// TODO Auto-generated method stub
		return dao.queryPersonWindow(id);
	}

	@Override
	public void addPerson(CustomerBankRelationPerson person) {
		try{
			if(null==person.getId()){
				dao.save(person);
			}else{
				CustomerBankRelationPerson cp=dao.getById(person.getId());
				BeanUtil.copyNotNullProperties(cp, person);
				dao.merge(cp);
			}
			JsonUtil.responseJsonSuccess();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public CustomerBankRelationPerson getById(Integer id) {
		
		return dao.getById(id);
	}

	@Override
	public VBankBankcontactperson queryPersonName(Integer id) {
		
		return dao.queryPersonName(id);
	}

	@Override
	public List<CustomerBankRelationPerson> getListByFenbankid(Integer fenbankid) {
		// TODO Auto-generated method stub
		return dao.getListByFenbankid(fenbankid);
	}
	@Override
	public void isExist(int id) throws Exception {
		
		List list =dao.getListByFenbankid(id);
		if(null == list){
			JsonUtil.responseJsonString("{success:true,exsit:false,msg :'没有银行联系人'}");
		}else
			JsonUtil.responseJsonString("{success:true,exsit:true,msg :'存在银行联系人'}");
	}
	@Override
	public List findBank(int bankId) {
		AreaDic dic = null ;
		List li = new ArrayList();
		if(bankId == 0){
			return null ;
		} else
			try {
				dic = (AreaDic)creditBaseDao.getById(AreaDic.class, bankId);
				li.add(dic);
				while(dic.getParentId()!= 0){
					dic = (AreaDic)creditBaseDao.getById(AreaDic.class, dic.getParentId()); 
					if(dic.getParentId() == 89){
						li.add(dic);
					}
				}
				/*do{
					dic = (AreaDic)creditBaseDao.getById(AreaDic.class, dic.getParentId()); 
				}while(dic.getParentId()!= 0);
				dic = (AreaDic)creditBaseDao.getById(AreaDic.class, dic.getId()); */
				//li.add(dic.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return li;
	}
}