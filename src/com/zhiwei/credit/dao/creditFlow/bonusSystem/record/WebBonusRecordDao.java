package com.zhiwei.credit.dao.creditFlow.bonusSystem.record;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.bonusSystem.record.WebBonusRecord;
import com.zhiwei.credit.model.creditFlow.bonusSystem.setting.WebBonusSetting;

/**
 * 
 * @author 
 *
 */
public interface WebBonusRecordDao extends BaseDao<WebBonusRecord>{
	
	/**
	 * 通过积分规则配置的时间找出积分记录
	 * @param webBonusSetting
	 * @return
	 */
	public List<WebBonusRecord> findBySettingEngine(Long userId,WebBonusSetting webBonusSetting);

	/**
	 * 查询全部
	 * @param request
	 * @return
	 */
	public  List<WebBonusRecord> findList(HttpServletRequest request,Integer start,Integer limit);
	
	/**
	 * 查询全部的总记录数
	 * @param request
	 * @return
	 */
	public Long findListCount(HttpServletRequest request);
	public List<WebBonusRecord> getActivityNumber(String activityNumber,String bpCustMemberId,String remark);
}