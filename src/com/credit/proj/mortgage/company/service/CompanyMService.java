package com.credit.proj.mortgage.company.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageEnterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseView;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;

public interface CompanyMService {

	//异步添加记录
	public void addCompany(ProcreditMortgageEnterprise procreditMortgageEnterprise,ProcreditMortgage procreditMortgage,Enterprise enterprise,Person person,int customerEnterpriseName,int legalpersonid) throws Exception;
	
	//从视图查询数据显示在详情页面
	@SuppressWarnings("unchecked")
	public List seeCompanyForUpdate(int id) throws Exception;
	
	//查看详情从责任公司表
	@SuppressWarnings("unchecked")
	public List seeCompany(int mortgageid) throws Exception;
	
	//根据反担保表中企业id查询企业实体获取法人代表人id等信息
	public EnterpriseView getEnterpriseObj(int id);
	
	//异步更新记录
	public void updateCompany(int mortgageid,ProcreditMortgageEnterprise procreditMortgageEnterprise,ProcreditMortgage procreditMortgage,Enterprise enterprise,Person person,int legalpersonid) throws Exception;
	
	
}
