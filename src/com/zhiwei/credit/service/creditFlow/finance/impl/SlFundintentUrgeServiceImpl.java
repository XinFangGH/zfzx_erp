package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundintentUrgeDao;
import com.zhiwei.credit.model.creditFlow.finance.SlFundintentUrge;
import com.zhiwei.credit.service.creditFlow.finance.SlFundintentUrgeService;

/**
 * 
 * @author 
 *
 */
public class SlFundintentUrgeServiceImpl extends BaseServiceImpl<SlFundintentUrge> implements SlFundintentUrgeService{
	@SuppressWarnings("unused")
	private SlFundintentUrgeDao dao;
	
	public SlFundintentUrgeServiceImpl(SlFundintentUrgeDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlFundintentUrge> getlistbyfundintentId(Long fundIntentId) {

		return dao.getlistbyfundintentId(fundIntentId);
	}

	@Override
	public List<SlFundintentUrge> getListByProjectId(Long projectId) {

		return dao.getListByProjectId(projectId);
	}

}