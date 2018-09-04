package com.zhiwei.credit.dao.creditFlow.bonusSystem.setting.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.bonusSystem.setting.WebBonusSettingDao;
import com.zhiwei.credit.model.creditFlow.bonusSystem.setting.WebBonusSetting;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class WebBonusSettingDaoImpl extends BaseDaoImpl<WebBonusSetting> implements WebBonusSettingDao{

	public WebBonusSettingDaoImpl() {
		super(WebBonusSetting.class);
	}

	@Override
	public List<WebBonusSetting> excludeALike(Map<String, String> queryMap) {
		
		StringBuffer hql = new StringBuffer(" from WebBonusSetting as w   where 1 = 1 ");
		if(queryMap!=null){
			Set<Entry<String, String>> entrySet = queryMap.entrySet();
			Iterator<Entry<String, String>> iterator = entrySet.iterator();
			while (iterator.hasNext()) {
				Entry<String, String> next = iterator.next();
				hql.append(" and  ").append("w."+next.getKey()).append(" = ").append("'"+next.getValue()+"'");
			}
		}
		
		List<WebBonusSetting> list = findByHql(hql.toString());
		return list;
	}
	
	

}