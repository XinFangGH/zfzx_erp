package com.zhiwei.credit.service.system.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.DictionaryDao;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.system.DictionaryService;

public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary> implements DictionaryService{
	private DictionaryDao dao;
	
	public DictionaryServiceImpl(DictionaryDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<String> getAllItems() {
		return dao.getAllItems();
	}

	@Override
	public List<String> getAllByItemName(String itemName) {
		return dao.getAllByItemName(itemName);
	}
	
	@Override
	public List<Dictionary> getByItemName(String itemName) {
		return dao.getByItemName(itemName);
	}

	@Override
	public List<Dictionary> getByProTypeId(long proTypeId) {
		
		return dao.getByProTypeId(proTypeId);
	}

	@Override
	public List<Dictionary> getByDicKey(String dicKey) {
		
		return dao.getByDicKey(dicKey);
	}

	@Override
	public String getDicKeyByDicId(long dicId) {
		
		return dao.getDicKeyByDicId(dicId);
	}

	@Override
	public List<Dictionary> getByParentId(long parentId,int start,int limit) {
		
		return dao.getByParentId(parentId,start,limit);
	}

	@Override
	public long getDicNumByParentId(long parentId) {
		
		return dao.getDicNumByParentId(parentId);
	}

	@Override
	public long getDicNumByProTypeId(long proTypeId) {
		
		return dao.getDicNumByProTypeId(proTypeId);
	}

	@Override
	public List<Dictionary> getListByProTypeId(long proTypeId, int start,
			int limit) {
		
		return dao.getListByProTypeId(proTypeId, start, limit);
	}

	@Override
	public String queryDic(String dicId) {
		return dao.queryDic(dicId);
	}

	@Override
	public Dictionary queryLoanUser(Long dicId) {
		return dao.queryLoanUser(dicId);
	}
}