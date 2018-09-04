package com.zhiwei.credit.service.creditFlow.customer.person.impl;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonThereunderDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonThereunder;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonThereunderService;

public class PersonThereunderServiceImpl extends BaseServiceImpl<PersonThereunder> implements PersonThereunderService{
	@SuppressWarnings("unused")
	private PersonThereunderDao dao;
	@Resource
	private EnterpriseDao enterpriseDao;
	@Resource
	private PersonDao personDao;
	public PersonThereunderServiceImpl(PersonThereunderDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PersonThereunder> getListByPersonId(Integer personId,
			PagingBean pb) {
		
		return dao.getListByPersonId(personId, pb);
	}

	@Override
	public PersonThereunder getById(Integer id) {
		
		return dao.getById(id);
	}
	@Override
	public void addPerson(PersonThereunder personThereunder)throws Exception {
		
			if(personThereunder.getCompanyname()!=null){
				Enterprise e=enterpriseDao.getById(personThereunder.getCompanyname());
				e.setCciaa(personThereunder.getLicensenum());
				e.setRegistermoney(personThereunder.getRegistercapital());
				e.setFactaddress(personThereunder.getAddress());
				enterpriseDao.merge(e);
			}
			if(personThereunder.getLnpid()!=null){
				Person p=personDao.getById(personThereunder.getLnpid());
				p.setCellphone(personThereunder.getPhone());
				personDao.merge(p);
			}
			dao.save(personThereunder);
			JsonUtil.jsonFromObject(null, true);
		
	}
}