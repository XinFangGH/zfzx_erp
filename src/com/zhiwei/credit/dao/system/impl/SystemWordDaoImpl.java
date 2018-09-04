package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import org.springframework.security.providers.dao.DaoAuthenticationProvider;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.SystemWordDao;
import com.zhiwei.credit.model.system.SystemWord;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SystemWordDaoImpl extends BaseDaoImpl<SystemWord> implements SystemWordDao{

	public SystemWordDaoImpl() {
		super(SystemWord.class);
	}
	public SystemWord getSystemWordByKey(String key) {
		String hql = "from SystemWord s where s.wordKey = '"+key+"'";
		List<SystemWord> list = super.findByHql(hql);
		return list.size()>0?list.get(0):null;
	}
	
	public List<SystemWord> getByParentId(Long parentId, String wordName, Boolean isWordType) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" from SystemWord sw where sw.parentId =" + parentId + "and sw.isWordType = "+isWordType);
		if(wordName != null && "".equals(wordName)) {
			buffer.append(" and sw.wordName = " + wordName.trim());
		}
		return findByHql(buffer.toString());
	}
	
	public List<SystemWord> getByWordName(String wordName) {
		String hql=" from SystemWord sw where sw.wordName like " + "%"+wordName+"%";
		return findByHql(hql);
	}
}