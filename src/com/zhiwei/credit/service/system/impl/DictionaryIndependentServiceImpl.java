package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.DictionaryIndependentDao;
import com.zhiwei.credit.model.system.DictionaryIndependent;
import com.zhiwei.credit.service.system.DictionaryIndependentService;

/**
 * 
 * @author 
 *
 */
public class DictionaryIndependentServiceImpl extends BaseServiceImpl<DictionaryIndependent> implements DictionaryIndependentService{
	@SuppressWarnings("unused")
	private DictionaryIndependentDao dao;
	
	public DictionaryIndependentServiceImpl(DictionaryIndependentDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<DictionaryIndependent> getListByProTypeId(long proTypeId) {
		
		return dao.getListByProTypeId(proTypeId);
	}

	@Override
	public List<String> getAllByItemName(String itemName) {
		
		return dao.getAllByItemName(itemName);
	}

	@Override
	public List<String> getAllItems() {
		
		return dao.getAllItems();
	}

	@Override
	public List<DictionaryIndependent> getByDicKey(String dicKey) {
		
		return dao.getByDicKey(dicKey);
	}

	@Override
	public List<DictionaryIndependent> getByItemName(String itemName) {
		
		return dao.getByItemName(itemName);
	}

	@Override
	public String getDicKeyByDicId(long dicId) {
		// TODO Auto-generated method stub
		return null;
	}

}