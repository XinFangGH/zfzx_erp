package com.zhiwei.credit.service.coupon.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.messageAlert.service.SmsDetailsService;
import com.messageAlert.service.SmsSendService;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.dao.coupon.BpCouponsDao;
import com.zhiwei.credit.dao.p2p.BpCustMemberDao;
import com.zhiwei.credit.model.activity.BpActivityManage;
import com.zhiwei.credit.model.coupon.BpCouponSetting;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.service.coupon.BpCouponSettingService;
import com.zhiwei.credit.service.coupon.BpCouponsService;
import com.zhiwei.credit.service.p2p.OaNewsMessageService;
import com.zhiwei.credit.service.sms.util.SmsSendUtil;

/**
 * 
 * @author 
 *
 */
public class BpCouponsServiceImpl extends BaseServiceImpl<BpCoupons> implements BpCouponsService{
	@SuppressWarnings("unused")
	private BpCouponsDao dao;
	
	@Resource
	private BpCustMemberDao bpCustMemberDao;
	@Resource
	private BpCouponSettingService bpCouponSettingService;
	@Resource
	private OaNewsMessageService oaNewsMessageService;
	@Resource
	private SmsSendService smsSendService;
	@Resource
	private SmsDetailsService smsDetailsService;
	
	private static Map configMap = AppUtil.getConfigMap();
	public BpCouponsServiceImpl(BpCouponsDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Boolean createBpCoupons(BpCouponSetting bps,
			String couponresourceNormal) {
		// TODO Auto-generated method stub
		try {
			List<BpCoupons> list=new ArrayList<BpCoupons>();
			if(bps!=null){
				for(int i=0;i<bps.getCounponCount();i++){
					BpCoupons bp=new BpCoupons();
					bp.setCouponType(Long.valueOf(bps.getCouponType()));
					bp.setCouponResourceType(couponresourceNormal);
					bp.setResourceId(bps.getCategoryId());
					bp.setCompanyId(bps.getCompanyId());
					bp.setCreateDate(new Date());
					bp.setCreateName(bps.getCouponCheckUserName());
					bp.setCreateUserId(bps.getCouponCheckUserId());
					bp.setCouponValue(bps.getCouponValue());
					bp.setCouponStartDate(bps.getCouponStartDate());
					bp.setCouponEndDate(bps.getCouponEndDate());
					bp.setCouponTypeValue(bps.getCouponTypeValue());
					bp.setCouponStatus(Short.valueOf("0"));
					bp.setCouponsDescribe(bps.getCouponDescribe());
					bp.setCouponNumber(this.createCouponNumber(bps.getCouponType()));
					dao.save(bp);
				}
				//批量保存生成的代金券方法
				//dao.saveList(list);
				return true;
			}else{//没有对象时返回空
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	@Override
	public String createCouponNumber(String couponType) {

		Long time =System.nanoTime();
		if(couponType.equals("")){
			
		}else{
			
		}
		System.out.println("time=="+time);
		
		return time.toString();
	
	}

	/**
	 * 优惠券派发方法
	 */
	@Override
	public Boolean couponDistribute(Long id, String truename,Long opraterId,String oprateName,BpCoupons bps) {
		// TODO Auto-generated method stub
		try {
			bps.setBelongUserId(id);
			bps.setCouponStatus(BpCoupons.COUPONSTATUS5);
			bps.setBelongUserName(truename);
			bps.setBindOpratorId(opraterId);
			bps.setBindOpratorName(oprateName);
			bps.setBindOpraterDate(new Date());
			dao.save(bps);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 批量派发优惠券
	 */
	@Override
	public Boolean couponAllDistribute(Long id, String truename,
			Long opraterId, String oprateName, Long categoryId,String setPattern,String couponNumber) {
		System.out.println("setPattern="+setPattern);
		BpCouponSetting bps=bpCouponSettingService.get(categoryId);
		try {
			BpCoupons bp=new BpCoupons();
			bp.setCouponType(Long.valueOf(bps.getCouponType()));
			bp.setCouponResourceType(BpCoupons.COUPONRESOURCE_NORMAL);
			bp.setResourceId(bps.getCategoryId());
			bp.setCompanyId(bps.getCompanyId());
			bp.setCreateDate(new Date());
			bp.setCreateName(bps.getCouponCheckUserName());
			bp.setCreateUserId(bps.getCouponCheckUserId());
			bp.setCouponValue(bps.getCouponValue());
			bp.setCouponStartDate(bps.getCouponStartDate());
			bp.setCouponEndDate(bps.getCouponEndDate());
			bp.setCouponTypeValue(bps.getCouponTypeValue());
			bp.setCouponNumber(couponNumber);
			bp.setCouponsDescribe(bps.getCouponDescribe());
			bp.setBindOpratorId(opraterId);
			bp.setBindOpratorName(oprateName);
			bp.setCategoryId(categoryId);
			bp.setBindOpraterDate(new Date());
			if(setPattern!=null&&!setPattern.equals("")&&setPattern.equals("1")){//系统派发
				bp.setBelongUserId(id);
				bp.setBelongUserName(truename);
				bp.setCouponStatus(BpCoupons.COUPONSTATUS5);
			}else if(setPattern!=null&&!setPattern.equals("")&&setPattern.equals("2")){//短信派发
				bp.setBelongUserId(id);
				bp.setBelongUserName(truename);
				bp.setCouponStatus(BpCoupons.COUPONSTATUS0);
			}else if(setPattern!=null&&!setPattern.equals("")&&setPattern.equals("3")){//邮件派发
				bp.setBelongUserId(id);
				bp.setBelongUserName(truename);
				bp.setCouponStatus(BpCoupons.COUPONSTATUS0);
			}else if(setPattern!=null&&!setPattern.equals("")&&setPattern.equals("4")){//制卡派发
//				bp.setBelongUserId(id);
//				bp.setBelongUserName(truename);
				bp.setCouponStatus(BpCoupons.COUPONSTATUS0);
			}
			dao.save(bp);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public List<BpCoupons> getActivityType(String Type, Long activityId,
			String bpCustMemberId) {
		// TODO Auto-generated method stub
		return dao.getActivityType(Type, activityId, bpCustMemberId);
	}
	/**
	 * 查询出来所有没有派发的优惠券
	 */
	@Override
	public List<BpCoupons> listForNotDistributeCoupon(
			HttpServletRequest request, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		return dao.listForNotDistributeCoupon(request,start,limit);
	}
	/**
	 * 查询出来所有派发过的优惠券
	 */
	@Override
	public List<BpCoupons> bouponBelongList(HttpServletRequest request,Integer start, Integer limit) {
		 List<BpCoupons> list = dao.bouponBelongList(request,start,limit);
		 if(list!=null&&list.size()>0){
			 for(BpCoupons bp : list){
				 dao.evict(bp);
				 if(bp.getBelongUserId()!=null&&!bp.getBelongUserId().equals("")){
					 BpCustMember bpCustMember = bpCustMemberDao.get(bp.getBelongUserId());
					 if(bpCustMember!=null){
						 bp.setLogginName(bpCustMember.getLoginname());
					 }
				 }
			 }
			 return list;
		 }
		 return null;
	}

	@Override
	public BpCoupons saveBpCoupons(String bpCustMemberId,
			BpActivityManage bpActivityManage) {
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat sdf2 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BpCustMember bpCustMember = bpCustMemberDao.get(Long.valueOf(bpCustMemberId));
		BpCoupons bp=new BpCoupons();
		bp.setCouponResourceType(BpCoupons.COUPONRESOURCE_ACTIVE);//来源
		bp.setCompanyId(Long.valueOf(1));
		bp.setResourceId(bpActivityManage.getActivityId());
		bp.setCreateName("系统");
		bp.setCreateUserId(Long.valueOf(0));
		bp.setCreateDate(new Date());
		bp.setCouponType(bpActivityManage.getCouponType());
		
		bp.setCouponStatus(Short.valueOf("5"));
		bp.setCouponValue(new BigDecimal(bpActivityManage.getParValue().longValue()));
		
		Date startDate=new Date();
		Date couponEndDate=DateUtil.addDaysToDate(new Date(), bpActivityManage.getValidNumber());
		try {
			startDate = sdf2.parse(sdf1.format(startDate));
			couponEndDate = sdf2.parse(sdf.format(couponEndDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		bp.setCouponStartDate(startDate);
		bp.setCouponEndDate(couponEndDate);
		bp.setCouponNumber(this.createCouponNumber(BpCoupons.COUPONRESOURCE_ACTIVE));
		bp.setBindOpratorName("系统");
		bp.setBindOpraterDate(new Date());
		
		bp.setBelongUserId(Long.valueOf(bpCustMemberId));
		bp.setBelongUserName(bpCustMember.getTruename());
		bp.setIntention(bpActivityManage.findSendType());//操作点名称
		bp.setCouponsDescribe(bpActivityManage.getActivityExplain());//描述
		BpCoupons save = dao.save(bp);
		//发送站内信
		sms_oaMessage(bp.getCouponValue(),bp.getCouponEndDate(),bp.getCouponType().toString(),Long.valueOf(bpCustMemberId),bp.getCouponNumber(),bp.getCouponsDescribe());
		return save;
	}
	/**
	 * 发送站内信
	 * @param bpCustMemberId
	 * @param bpActivityManage
	 */
	public void sms_oaMessage(BigDecimal couponValue,Date endTime,String couponType,Long memberId,String couponNumber,String couponsDescribe){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//String project=configMap.get("project").toString();
		//String telphone=configMap.get("telphone").toString();
		String dw="";
		if(couponType.equals("1")||couponType.equals("2")){
			dw="元";
		}else if(couponType.equals("3")){
			dw="%";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("${parValue}", couponValue.setScale(2)+dw);
		map.put("${endTime}", sdf.format(endTime));
		map.put("${activity}", couponsDescribe);
		map.put("key", "sms_activityMessage");
		String content=smsDetailsService.getContent(map);
		
		//String temp = configMap.get("sms_activityMessage").toString();
		//content = temp.replace("${parValue}", couponValue.setScale(2)+dw).replace("${endTime}", 
		//		sdf.format(endTime)).replace("${activity}", couponsDescribe).replace("${telphone}", telphone).replace("${project}", project);
		String title="优惠券派发提醒";
		oaNewsMessageService.sedBpcouponsMessage(title,content,memberId,"");
		
	}
	/**
	 * 定时器 ，优惠券过期提醒,发送站内信
	 * @return
	 */
	@Override
	public void sendExpirationRemind() {
		try {
			String project=configMap.get("project").toString();
			String telphone=configMap.get("telphone").toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date endDate=DateUtil.addDaysToDate(new Date(),2);
			//提前3天提醒
			List<BpCoupons> bpCouponsThreeList= dao.getCouponEndDate(sdf.format(endDate));
			sendExpirationBpcoupons(bpCouponsThreeList,telphone,project);
			//当天提醒
			List<BpCoupons> bpCouponsNowList= dao.getCouponEndDate(sdf.format(new Date()));
			sendExpirationBpcoupons(bpCouponsNowList,telphone,project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 开始发送
	 * @param bpCouponsList
	 * @param telphone
	 * @param project
	 */
	public void sendExpirationBpcoupons(List<BpCoupons> bpCouponsList,String telphone,String project){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(BpCoupons  bpCoupons : bpCouponsList){
				//String temp = configMap.get("sms_endTimeCoupons").toString();
				//String content="";
				String dw="";
				if(bpCoupons.getCouponType().equals("1")||bpCoupons.getCouponType().equals("2")){
					dw="元";
				}else if(bpCoupons.getCouponType().equals("3")){
					dw="%";
				}
				//发送站内信
				//content = temp.replace("${parValue}", bpCoupons.getCouponValue().setScale(2)+dw).replace("${endTime}", 
				//		sdf.format(bpCoupons.getCouponEndDate())).replace("${phone}", telphone).replace("${subject}", project);
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("${parValue}", bpCoupons.getCouponValue().setScale(2)+dw);
				map.put("${endTime}", sdf.format(bpCoupons.getCouponEndDate()));
				map.put("key", "sms_endTimeCoupons");
				String content = smsDetailsService.getContent(map);
				String title="优惠券过期提醒";
				oaNewsMessageService.sedBpcouponsMessage(title,content,bpCoupons.getBelongUserId(),"");
				//发送短信
				SmsSendUtil send = new SmsSendUtil();
				BpCustMember member = bpCustMemberDao.get(bpCoupons.getBelongUserId());
				map.put("telephone", member.getTelphone());
				smsSendService.smsSending(map);
				//send.sendBpcoupons(member.getTelphone(),bpCoupons.getCouponValue().setScale(2)+dw,sdf.format(bpCoupons.getCouponEndDate()),telphone,project);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}