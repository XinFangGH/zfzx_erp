package com.zhiwei.credit.service.creditFlow.customer.person.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.person.BpCustPersonNegativeSurveyDao;
import com.zhiwei.credit.model.creditFlow.customer.person.BpCustPersonNegativeSurvey;
import com.zhiwei.credit.service.creditFlow.customer.person.BpCustPersonNegativeSurveyService;

/**
 * 
 * @author 
 *
 */
public class BpCustPersonNegativeSurveyServiceImpl extends BaseServiceImpl<BpCustPersonNegativeSurvey> implements BpCustPersonNegativeSurveyService{
	@SuppressWarnings("unused")
	private BpCustPersonNegativeSurveyDao dao;
	
	public BpCustPersonNegativeSurveyServiceImpl(BpCustPersonNegativeSurveyDao dao) {
		super(dao);
		this.dao=dao;
	}
	public List<BpCustPersonNegativeSurvey> getNegativeInfoByPid(int personId){
		return dao.getNegativeInfoByPid(personId);
	}

}