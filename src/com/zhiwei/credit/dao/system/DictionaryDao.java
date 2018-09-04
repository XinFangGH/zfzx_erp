package com.zhiwei.credit.dao.system;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.system.Dictionary;

/**
 * 
 * @author 
 *
 */
public interface DictionaryDao extends BaseDao<Dictionary>{

	public List<String> getAllItems();

	public List<String> getAllByItemName(String itemName);
	
	public List<Dictionary> getByItemName(final String itemName);
	
	public List<Dictionary> getByProTypeId(long proTypeId);
	
	public List<Dictionary> getByDicKey(String dicKey);
	
	public String getDicKeyByDicId(long dicId);
	
	public List<Dictionary> getByParentId(long parentId,int start,int limit);
	
	public List<Dictionary> getListByProTypeId(long proTypeId,int start,int limit);
	
	public long getDicNumByParentId(long parentId);
	
	public long getDicNumByProTypeId(long proTypeId);
	
	public String queryDic(String dicId);
	
	public Dictionary queryLoanUser(Long dicId);
}