package com.zhiwei.credit.service.creditFlow.bonusSystem.record.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.bonusSystem.record.WebBonusRecordDao;
import com.zhiwei.credit.model.creditFlow.bonusSystem.WebBonusConstant;
import com.zhiwei.credit.model.creditFlow.bonusSystem.record.WebBonusRecord;
import com.zhiwei.credit.model.creditFlow.bonusSystem.setting.WebBonusSetting;
import com.zhiwei.credit.service.creditFlow.bonusSystem.record.WebBonusRecordService;

/**
 * 
 * @author 
 *
 */
public class WebBonusRecordServiceImpl extends BaseServiceImpl<WebBonusRecord> implements WebBonusRecordService{
	@SuppressWarnings("unused")
	private WebBonusRecordDao dao;
	
	public WebBonusRecordServiceImpl(WebBonusRecordDao dao) {
		super(dao);
		this.dao=dao;
	}


	@Override
	public Long findBySetting(Long userId ,WebBonusSetting webBonusSetting) {
		
		/**
		 * 一丶如果webBonusSetting 规则的周期类型为一次性--直接返回奖励分值
		 * 二丶通过规则查找对应的积分记录，返回符合条件的相同记录
		 *   返回一个list
		 * 	   如果list不为空，并且list.size()大于等于积分规则中的周期内奖励次数则,返回0
		 * 	   否则返回奖励分值
		 */
		//一丶
		if(WebBonusConstant.BOMUSPERIOD_TYPE_ONCE.equals(webBonusSetting.getBomusPeriodType())){
			return Long.valueOf(webBonusSetting.getBonus());
		}
		
		//二丶
		List<WebBonusRecord> list = dao.findBySettingEngine(userId,webBonusSetting);
		if(list!=null&&list.size()>=Integer.parseInt(webBonusSetting.getPeriodBonusLimit())){
			return Long.valueOf(0);
		}
		return Long.valueOf(webBonusSetting.getBonus());
		
	}


	@Override
	public List<WebBonusRecord> listByUserId(Long userId, String sort) {
		StringBuffer sb = new StringBuffer(" from WebBonusRecord where customerId = ? ");
		if(sort!=null&&!"".equals(sort)){
			sb.append(" order by  createTime  " + sort + " ");
		}else{
			sb.append(" order by  createTime desc ");
		}
		return dao.findByHql(sb.toString(), new Object[]{userId.toString()});
	}


	@Override
	public List<WebBonusRecord> findList(HttpServletRequest request,Integer start,Integer limit) {
		return dao.findList(request,start,limit);
	}

	@Override
	public List<WebBonusRecord> getActivityNumber(String activityNumber,
			String bpCustMemberId, String remark) {
		// TODO Auto-generated method stub
		return dao.getActivityNumber(activityNumber, bpCustMemberId, remark);
	}
	@Override
	public Long findListCount(HttpServletRequest request) {
		return dao.findListCount(request);
	}

}