package com.credit.proj.mortgage.dutyperson.service;

import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgagePerson;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
@SuppressWarnings("all")
public interface DutypersonService {

	//异步添加记录
	public void addDutyperson(ProcreditMortgagePerson procreditMortgageperson,ProcreditMortgage procreditMortgage,Person person,int customerPersonName,int cardtype) throws Exception;
	
	//从视图查询数据显示在详情页面
	public List seeDutypersonForUpdate(int id) throws Exception;
	
	//查看详情从责任个人表
	public List seeDutyperson(int mortgageid) throws Exception;
	
	//异步更新记录
	public void updateDutyperson(int mortgageid,ProcreditMortgagePerson procreditMortgageperson,ProcreditMortgage procreditMortgage,Person person,int customerPersonName,int cardtype) throws Exception;
	
	
}
