package com.zhiwei.credit.dao.creditFlow.bonusSystem.setting;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.bonusSystem.setting.WebBonusSetting;

/**
 * 
 * @author 
 *
 */
public interface WebBonusSettingDao extends BaseDao<WebBonusSetting>{


	/**
	 * 按条件查出是否有相同的积分规则
	 * @param queryMap
	 * 		  queryMap 中的内容全是判断不相等的值
	 * 			例：map.put("a","a")
	 * 			   map.put("b","b")    
	 * 			   转换为sql 为    and  a !='a'  and b != 'b'
	 * 	
	 * @return  list  
	 * 			
	 */
	public List<WebBonusSetting> excludeALike(Map<String, String> queryMap);
	
	
}