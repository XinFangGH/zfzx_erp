package com.zhiwei.credit.service.system;
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.system.Dictionary;

public interface DictionaryService extends BaseService<Dictionary>{

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
	/**
	 * 查询借款用途
	 * @param dicId
	 * @return
	 */
	public Dictionary queryLoanUser(Long dicId);
}


