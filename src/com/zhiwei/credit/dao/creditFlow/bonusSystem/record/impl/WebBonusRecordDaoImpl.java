package com.zhiwei.credit.dao.creditFlow.bonusSystem.record.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.bonusSystem.record.WebBonusRecordDao;
import com.zhiwei.credit.model.creditFlow.bonusSystem.WebBonusConstant;
import com.zhiwei.credit.model.creditFlow.bonusSystem.record.WebBonusRecord;
import com.zhiwei.credit.model.creditFlow.bonusSystem.setting.WebBonusSetting;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class WebBonusRecordDaoImpl extends BaseDaoImpl<WebBonusRecord> implements WebBonusRecordDao{

	public WebBonusRecordDaoImpl() {
		super(WebBonusRecord.class);
	}


	@Override
	public List<WebBonusRecord> findBySettingEngine(Long userId,WebBonusSetting webBonusSetting) {
		
		/**
		 * 查询积分规则内的有积分记录
		 */
		//webBonusSetting.getIsBonus();//积分增加还是减少
		//webBonusSetting.getBonusId();//积分ID
		//通过奖励周期，和奖励周期类型计算出createTime
		//奖励周期类型分为四类
			//分钟，查当前时间减去这个分钟之间的所有记录数
			//小时，查当前时间减去这个小时之间的所有记录数
			//天,  查当前时间减去这个天数之间的所有记录数
		//webBonusSetting.getBomusPeriod();//奖励周期
		//webBonusSetting.getBomusPeriodType();//奖励周期类型
		
		StringBuffer sb = new StringBuffer("from WebBonusRecord as w  where 1=1 ");
		sb.append(" and w.customerId = ").append("'").append(userId.toString()).append("'");
		sb.append(" and w.recordDirector =  ").append("'").append(webBonusSetting.getIsBonus()).append("'");
		sb.append(" and w.bonusId =  ").append("'").append(webBonusSetting.getBonusId().toString()).append("'");
		
		//String queryDate = DateUtil.integralNextDate(webBonusSetting.getBomusPeriod(), webBonusSetting.getBomusPeriodType());
		String queryDate = integralNextDate(webBonusSetting.getBomusPeriod(), webBonusSetting.getBomusPeriodType());
		sb.append(" and w.createTime >  ").append("'").append(queryDate).append("'");
		
		
		return findByHql(sb.toString());
		
	}
	
	
    public static String integralNextDate(String bomusPeriod ,String bomusPeriodType){
    	SimpleDateFormat formatMin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat formatHour = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    	
    	Date toDate = new Date();
    	Calendar c1 = Calendar.getInstance();
		c1.setTime(toDate);
 //   	System.out.println(formatMin.format(toDate));
    	
    	
		if(WebBonusConstant.BOMUSPERIOD_TYPE_MIN.equals(bomusPeriodType)){
			
    		//如果是类型为分钟，则当前时间减去bomusPeriod分钟
    		c1.add(Calendar.MINUTE,-Integer.parseInt(bomusPeriod));
    		return formatMin.format(c1.getTime());
    		
    	}else if(WebBonusConstant.BOMUSPERIOD_TYPE_HOUR.equals(bomusPeriodType)){
    		
    		//如果是类型为分钟，则当前时间减去bomusPeriod小时
    		c1.add(Calendar.HOUR_OF_DAY,-Integer.parseInt(bomusPeriod));
    		return formatHour.format(c1.getTime());
    		
    	}else if(WebBonusConstant.BOMUSPERIOD_TYPE_DATE.equals(bomusPeriodType)){
    		
    		//如果是类型为天数，则当前时间减去bomusPeriod天数
    		c1.add(Calendar.DAY_OF_MONTH, (-Integer.parseInt(bomusPeriod))  +1);
    		return formatDate.format(c1.getTime());
    		
    	}
		
		return null;
    	
    }


	@Override
	public List<WebBonusRecord> findList(HttpServletRequest request,Integer start,Integer limit) {
		StringBuffer sb = new StringBuffer(" from WebBonusRecord where 1=1 ");
		String customerName = request.getParameter("Q_customerName_S_LK");
		String recordDirector = request.getParameter("recordDirector");
		String createTime = request.getParameter("createTime");
		String bonusIntention = request.getParameter("bonusIntention");
		String activityNumber = request.getParameter("activityNumber");
		String operationType = request.getParameter("operationType");
		
		if(customerName!=null&&!"".equals(customerName)){
			sb.append(" and customerName like '%"+customerName+"%' ");
		}
		if(recordDirector!=null&&!"".equals(recordDirector)){
			sb.append(" and recordDirector = '"+recordDirector+"' ");
		}
		if(createTime!=null&&!"".equals(createTime)){
			sb.append(" and createTime like '%"+createTime+"%' ");
		}
		if(bonusIntention!=null&&!"".equals(bonusIntention)){
			sb.append(" and bonusIntention = '"+bonusIntention+"' ");
		}
		if(activityNumber!=null&&!"".equals(activityNumber)){
			sb.append(" and activityNumber like '%"+activityNumber+"%' ");
		}
		if(operationType!=null&&!"".equals(operationType)){
			sb.append(" and operationType = '"+operationType+"' ");
		}
		
		sb.append(" order by createTime desc ");
		return findByHql(sb.toString(), null, start, limit);
	}


	@Override
	public Long findListCount(HttpServletRequest request) {
		
		StringBuffer sb = new StringBuffer(" select count(*) from web_bonus_record where 1=1 ");
		String customerName = request.getParameter("Q_customerName_S_LK");
		if(customerName!=null&&!"".equals(customerName)){
			sb.append(" and customerName like '"+customerName+"' ");
		}
		
		BigInteger bigInteger =(BigInteger) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
		return bigInteger.longValue();
	}

	@Override
	public List<WebBonusRecord> getActivityNumber(String activityNumber,
			String bpCustMemberId, String remark) {
		String sql = "select * from web_bonus_record where customerId=? and activityNumber=?";
		return this.getSession().createSQLQuery(sql).setParameter(0, bpCustMemberId).setParameter(1, activityNumber).list();
	}

}