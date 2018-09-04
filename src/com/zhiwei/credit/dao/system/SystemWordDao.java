package com.zhiwei.credit.dao.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.system.SystemWord;

/**
 * 
 * @author 
 *
 */
public interface SystemWordDao extends BaseDao<SystemWord>{
	public SystemWord getSystemWordByKey(String key);
	public List<SystemWord> getByParentId(Long parentId, String wordName, Boolean isWordType);
	public List<SystemWord> getByWordName(String wordName);
}