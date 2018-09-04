package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundQlideDao;
import com.zhiwei.credit.model.creditFlow.finance.SlFundQlide;
import com.zhiwei.credit.service.creditFlow.finance.SlFundQlideService;

/**
 * 
 * @author 
 *
 */
public class SlFundQlideServiceImpl extends BaseServiceImpl<SlFundQlide> implements SlFundQlideService{
	@SuppressWarnings("unused")
	private SlFundQlideDao dao;
	
	public SlFundQlideServiceImpl(SlFundQlideDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlFundQlide> search(Map<String,String> map) {
		// TODO Auto-generated method stub
		return dao.search(map);
	}

	@Override
	public int searchsize(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.searchsize(map);
	}

	@Override
	public List<SlFundQlide> getsortAll(Map<String,String> map) {
		// TODO Auto-generated method stub
		return dao.getsortAll(map);
	}

	@Override
	public int getsortcount(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.getsortcount(map);
	}

	@Override
	public List<SlFundQlide> getcashQlide(String iscash) {
		// TODO Auto-generated method stub
		return dao.getcashQlide(iscash);
	}

	@Override
	public List<SlFundQlide> searchcash(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.searchcash(map);
	}

	@Override
	public int searchcashsize(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.searchcashsize(map);
	}

	@Override
	public List<SlFundQlide> listbyaccount(Map<String,String> map) {
		// TODO Auto-generated method stub
		return dao.listbyaccount(map);
	}

	@Override
	public int listbyaccountsize(Map<String,String> map) {
		// TODO Auto-generated method stub
		return dao.listbyaccountsize(map);
	}

	@Override
	public List<SlFundQlide>  listbyaccountall(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.listbyaccountall(map);
	}

	@Override
	public List<SlFundQlide> listcashall(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.listcashall(map);
	}

	@Override
	public List<SlFundQlide> searchnotcheck(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.searchnotcheck(map);
	}

	@Override
	public int searchnotchecksize(Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.searchnotchecksize(map);
	}

	@Override
	public List<SlFundQlide> getallbycompanyId() {
		return dao.getallbycompanyId();
	}

	@Override
	public List<SlFundQlide> getLastByMaxDate(Long comId) {
		return dao.getLastByMaxDate(comId);
	}

	
	

}