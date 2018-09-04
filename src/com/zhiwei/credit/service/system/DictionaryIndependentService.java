package com.zhiwei.credit.service.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.system.DictionaryIndependent;

/**
 * 
 * @author 
 *
 */
public interface DictionaryIndependentService extends BaseService<DictionaryIndependent>{
	public List<DictionaryIndependent> getListByProTypeId(long proTypeId);
	public List<String> getAllItems();

	public List<String> getAllByItemName(String itemName);
	
	public List<DictionaryIndependent> getByItemName(final String itemName);
	
	
	public List<DictionaryIndependent> getByDicKey(String dicKey);
	
	public String getDicKeyByDicId(long dicId);
}


