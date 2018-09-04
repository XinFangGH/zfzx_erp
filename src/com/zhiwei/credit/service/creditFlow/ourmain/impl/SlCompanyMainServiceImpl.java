package com.zhiwei.credit.service.creditFlow.ourmain.impl;
/*
 *  北京互融时代软件有限公司 
*/

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.ourmain.SlCompanyMainDao;
import com.zhiwei.credit.model.creditFlow.ourmain.SlCompanyMain;
import com.zhiwei.credit.service.creditFlow.ourmain.SlCompanyMainService;


public class SlCompanyMainServiceImpl extends BaseServiceImpl<SlCompanyMain> implements SlCompanyMainService{

	private SlCompanyMainDao dao;
	
	public SlCompanyMainServiceImpl(SlCompanyMainDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlCompanyMain> findCompanyList(String corName) {
		
		return dao.findCompanyList(corName);
	}

	/**
	 * 获取我方企业主体
	 * @param corName
	 * @param lawName
	 * @param organizeCode
	 * @param PagingBean
	 * @return
	 */
	public List<SlCompanyMain> findCompanyListByIsPledge(String corName,String lawName,String organizeCode,PagingBean pb,String companyId){
		return dao.findCompanyListByIsPledge(corName, lawName, organizeCode, pb,companyId);
	}
	
	/**
	 * 获取我方企业主体-参照
	 * @param corName
	 * @param PagingBean
	 * @return
	 */
	public List<SlCompanyMain> findCompanyListReference(String corName,PagingBean pb){
		return dao.findCompanyListReference(corName, pb);
	}
	
	/**
	 * 获取我方企业主体-添加抵质押物索引匹配信息
	 * @param query
	 * @return
	 */
	public List<SlCompanyMain> queryListForCombo(String query){
		return dao.queryListForCombo(query);
	}
	/**
	 * 获取我方企业主体-包含pb
	 * @param query
	 * @author gaoqingrui
	 * @return
	 */
	public List<SlCompanyMain> queryListWithPB(PagingBean pb){
		return dao.getAll(pb);
	}
}