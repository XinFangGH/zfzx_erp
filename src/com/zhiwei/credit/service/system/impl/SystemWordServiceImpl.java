package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.SystemWordDao;
import com.zhiwei.credit.model.system.SystemWord;
import com.zhiwei.credit.service.system.SystemWordService;

/**
 * 
 * @author 
 *
 */
public class SystemWordServiceImpl extends BaseServiceImpl<SystemWord> implements SystemWordService{
	@SuppressWarnings("unused")
	private SystemWordDao dao;
	
	public SystemWordServiceImpl(SystemWordDao dao) {
		super(dao);
		this.dao=dao;
	}
	public SystemWord getSystemWordByKey(String key) {
		return dao.getSystemWordByKey(key);
	}
	
	public List<SystemWord> getByParentId(Long parentId, String wordName, Boolean isWordType) {
		return dao.getByParentId(parentId, wordName, isWordType);
	}
	
	public List<SystemWord> getByWordName(String wordName) {
		return dao.getByWordName(wordName);
	}
}