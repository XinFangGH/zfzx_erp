package com.zhiwei.credit.service.creditFlow.bonusSystem.setting;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.bonusSystem.setting.WebBonusSetting;

/**
 * 
 * @author 
 *
 */
public interface WebBonusSettingService extends BaseService<WebBonusSetting>{

	/**
	 * 初始化积分规则信息
	 */
	public void initDate();
	
	/**
	 * 按条件查出是否有其它相同的积分规则
	 * @param queryMap
	 * @return 返回查出的对象
	 */
	public WebBonusSetting excludeALike(Map<String, String> queryMap);
	
	/**
	 * 通过类名，方法名，和会员等级查出一条积分规则
	 * @param userAction    类名
	 * @param userActionKey 方法名
	 * @param memberLevel   会员等级id
	 * @return
	 */
	public WebBonusSetting hasWebBonusSetting(String userAction,String userActionKey,String memberLevel);
	
	
	
	
}


