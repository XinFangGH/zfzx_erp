package com.zhiwei.credit.action.coupon;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.messageAlert.service.SmsDetailsService;
import com.messageAlert.service.SmsSendService;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.jms.MailMessageProducer;
import com.zhiwei.core.model.MailModel;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.model.coupon.BpCouponSetting;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.service.coupon.BpCouponSettingService;
import com.zhiwei.credit.service.coupon.BpCouponsService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.OaNewsMessageService;
import com.zhiwei.credit.service.sms.util.SmsSendUtil;
/**
 * 
 * @author 
 *
 */
public class BpCouponsAction extends BaseAction{
	@Resource
	private BpCouponsService bpCouponsService;
	@Resource
	private BpCustMemberService bpCustmemberService;
	@Resource
	private BpCouponSettingService bpCouponSettingService;
	@Resource
	private PlBidInfoService plBidInfoService;
	@Resource
	private PlBidPlanService plBidPlanService;
	@Resource
	private OaNewsMessageService oaNewsMessageService;
	@Resource
	private SmsDetailsService smsDetailsService;
	@Resource
	private SmsSendService smsSendService;
	private BpCoupons bpCoupons;
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	private Long couponId;

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public BpCoupons getBpCoupons() {
		return bpCoupons;
	}

	public void setBpCoupons(BpCoupons bpCoupons) {
		this.bpCoupons = bpCoupons;
	}

	/**
	 * 查询出来没有派发给P2P用户的优惠券显示列表
	 */
	public String list(){
		List<BpCoupons> list= bpCouponsService.listForNotDistributeCoupon(this.getRequest(),start,limit);
		for(BpCoupons bp:list){
			Date d = new Date();
			if(bp.getCouponEndDate().getTime()<d.getTime()){
				BpCoupons bpList = bpCouponsService.get(bp.getCouponId());
				bpList.setCouponStatus(Short.valueOf("4"));
				bpCouponsService.save(bpList);
			}
		}
		List<BpCoupons> listcount= bpCouponsService.listForNotDistributeCoupon(this.getRequest(),null,null);
		Type type=new TypeToken<List<BpCoupons>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(listcount!=null?listcount.size():0).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		System.out.println(buff.toString());
		return SUCCESS;
	}
	/**
	 * 查询出来已经派发给P2P用户的优惠券列表
	 * @return
	 */
	public String bouponBelongList(){
		List<BpCoupons> list= bpCouponsService.bouponBelongList(this.getRequest(),start,limit);
		List<BpCoupons> listcount= bpCouponsService.bouponBelongList(this.getRequest(),null,null);
		String couponstatus = this.getRequest().getParameter("couponstatus");
		if(couponstatus!=null&&!couponstatus.equals("")&&couponstatus.equals("10")){
			if(list!=null){
				
				for(BpCoupons bp :list){
					QueryFilter filter = new QueryFilter(this.getRequest());
					filter.addFilter("Q_couponId_L_EQ", bp.getCouponId());
					List<PlBidInfo> bidinfo = plBidInfoService.getAll(filter);
					if(bidinfo.size()>0){
						bp.setInfoMoney(bidinfo.get(0).getUserMoney());//投资金额
						QueryFilter filter1 = new QueryFilter(this.getRequest());
						filter1.addFilter("Q_bidId_L_EQ", bidinfo.get(0).getBidId());
						List<PlBidPlan> plan = plBidPlanService.getAll(filter1);
						if(plan.size()>0){
							bp.setReturnRatio(plan.get(0).getReturnRatio());//返现比例
						}
					}
				}
			}
		}
		Type type=new TypeToken<List<BpCoupons>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(listcount!=null?listcount.size():0).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		if(list!=null){
			buff.append(gson.toJson(list, type));
		}else{
			buff.append("[]");
		}
		buff.append("}");
		
		jsonString=buff.toString();
		System.out.println(buff.toString());
		return SUCCESS;
	}
	/**
	 * 批量对优惠券进行禁用和开启操作
	 * @return
	 */
	public String forbidOrEnable(){
		String msg="";
		String[]ids=getRequest().getParameterValues("ids");
		String couponStatus=this.getRequest().getParameter("couponStatus");
		if(couponStatus!=null&&!"".equals(couponStatus)){
			if(ids!=null){
				for(String id:ids){
					BpCoupons bps =bpCouponsService.get(new Long(id));
					bps.setCouponStatus(Short.valueOf(couponStatus));
					bpCouponsService.merge(bps);
				}
				msg="操作成功";
			}else{
				msg="没有选择操作记录";
			}
		}else{
			msg="没有选择操作类型";
		}
		
		jsonString="{success:true,msg:\""+msg+"\"}";
		return SUCCESS;
	}
	/**
	 * 给投资人派发优惠券方法
	 * @return
	 */
	public String couponsDistribute(){
		String msg="";
		String p2pAccount=this.getRequest().getParameter("p2pAccount");
		if(couponId!=null&&!"".equals(couponId)){
			BpCoupons bps=	bpCouponsService.get(couponId);
			if(bps!=null){
				if(p2pAccount!=null&&!"".equals(p2pAccount)){
					BpCustMember member=bpCustmemberService.getMemberUserName(p2pAccount);
					if(member!=null){
						Boolean flag=bpCouponsService.couponDistribute(member.getId(),member.getTruename(),ContextUtil.getCurrentUserId(),ContextUtil.getCurrentUser().getFullname(),bps);
						if(flag){
							msg="成功给用户【"+p2pAccount+"】派发了优惠券";
							//发送站内信
							sms_oaMessage(bps.getCouponValue(),bps.getCouponEndDate(),bps.getCouponType().toString(),member.getId(),"1",null);
							//发送短信
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String dw="";
							if(bps.getCouponType().toString().equals("1")||bps.getCouponType().toString().equals("2")){
								dw="元";
							}else if(bps.getCouponType().toString().equals("3")){
								dw="%";
							}
							Map<String, String> map = new HashMap<String, String>();
							map.put("telephone", member.getTelphone());
							map.put("${parValue}", bps.getCouponValue().setScale(2)+dw);
							map.put("${endTime}", sdf.format(bps.getCouponEndDate()));
							map.put("key", "sms_oaMessage");
							smsSendService.smsSending(map);
							
							//SmsSendUtil send = new SmsSendUtil();
							//send.sms_oaMessage(member.getTelphone(),bps.getCouponValue(),sdf.format(bps.getCouponEndDate()),bps.getCouponType().toString());
						}else{
							msg="优惠券派发出错,请稍后再试";
						}
					}else{
						msg="派发用户信息没有找到";
					}
				}else{
					msg="没有填写派发用户的P2P登陆账号";
				}
			}else{
				msg="没有优惠券信息，无法派发";
			}
		}else{
			msg="没有选中派发的优惠券";
		}
		jsonString="{success:true,msg:\""+msg+"\"}";
		return SUCCESS;
	}
	/**
	 * 批量派发优惠券方法
	 * @return
	 */
	public String couponsAllDistribute(){
		String msg="";
		String categoryId=this.getRequest().getParameter("categoryId");
		String reddatas=this.getRequest().getParameter("reddatas");
		String setPattern=this.getRequest().getParameter("setPattern");//派发方式
		String couponsNum=this.getRequest().getParameter("couponsNum");//派发个数
		BpCouponSetting couset = bpCouponSettingService.get(Long.valueOf(categoryId));
		if(setPattern!=null&&!setPattern.equals("")&&setPattern.equals("4")){//制卡派发
			if(couset.getCounponCount()>=Integer.valueOf(couponsNum)){
				for(int i=0;i<Integer.valueOf(couponsNum);i++){
					Long couponNumber=System.nanoTime();//激活码
					Boolean flag=bpCouponsService.couponAllDistribute(null,null,ContextUtil.getCurrentUserId(),ContextUtil.getCurrentUser().getFullname(),Long.valueOf(categoryId),setPattern, couponNumber.toString());
				}
				if(couset.getCounponCount()-Integer.valueOf(couponsNum)<=0){
					couset.setCouponState(Short.valueOf("2"));
				}
				couset.setTotalCouponValue(couset.getCouponValue().multiply(new BigDecimal(couset.getCounponCount()-Integer.valueOf(couponsNum))));
				couset.setCounponCount(couset.getCounponCount()-Integer.valueOf(couponsNum));
				bpCouponSettingService.save(couset);
				msg="发送成功";
			}else{
				System.out.println("派发人数不能大于剩余张数");
				msg="1";
			}
		}else{
			if(!reddatas.equals("null")&&categoryId!=null&&!"".equals(categoryId)&&reddatas!=null&&!reddatas.equals("")){
				if(couset!=null){
					String str [] = reddatas.split(",");
					BigDecimal useMoney=new BigDecimal("0");
					int num = 0;
					if(couset.getCounponCount()>=str.length){
						for(int i=0;i<str.length;i++){
							Long couponNumber=System.nanoTime();//激活码
								if(str[i]!=null&&!str[i].equals("")&&!str[i].equals("null")){
									BpCustMember member = bpCustmemberService.get(Long.valueOf(str[i]));
									if(member!=null){
										if(setPattern!=null&&!setPattern.equals("")&&setPattern.equals("1")){//系统派发
											Boolean flag=bpCouponsService.couponAllDistribute(member.getId(),member.getTruename(),ContextUtil.getCurrentUserId(),ContextUtil.getCurrentUser().getFullname(),Long.valueOf(categoryId),setPattern, couponNumber.toString());
											//发送站内信
											sms_oaMessage(couset.getCouponValue(),couset.getCouponEndDate(),couset.getCouponType(),member.getId(),"1",null);
										}else if(setPattern!=null&&!setPattern.equals("")&&setPattern.equals("2")){//短信派发
											//调用短信发送接口
											if(member.getTelphone()!=null){
												Boolean flag=bpCouponsService.couponAllDistribute(member.getId(),member.getTruename(),ContextUtil.getCurrentUserId(),ContextUtil.getCurrentUser().getFullname(),Long.valueOf(categoryId),setPattern, couponNumber.toString());
												//发送站内信
												sms_oaMessage(couset.getCouponValue(),couset.getCouponEndDate(),couset.getCouponType(),member.getId(),"2",couponNumber.toString());
												//发送短信
												sendTelphone(member.getTelphone(),couset.getCouponValue(),couset.getCouponEndDate(),couset.getCouponType(),couponNumber.toString());
												
											}
										}else if(setPattern!=null&&!setPattern.equals("")&&setPattern.equals("3")){//邮件派发
											if(member.getEmail()!=null){
												Boolean flag=bpCouponsService.couponAllDistribute(member.getId(),member.getTruename(),ContextUtil.getCurrentUserId(),ContextUtil.getCurrentUser().getFullname(),Long.valueOf(categoryId),setPattern, couponNumber.toString());
												if(flag){
													//发送站内信
													sms_oaMessage(couset.getCouponValue(),couset.getCouponEndDate(),couset.getCouponType(),member.getId(),"3",couponNumber.toString());
													//发送邮件
													sendEmail(member.getEmail(),couset.getCouponValue(),couset.getCouponEndDate(),couset.getCouponType(),couponNumber.toString());
													
												}
											}
										}
									}else{
										msg="用户选择错误";
									}

								}else{
									num=num+1;
								}
							}
						if(couset.getCounponCount()-str.length<=0){
							couset.setCouponState(Short.valueOf("2"));

						}
						couset.setTotalCouponValue(couset.getCouponValue().multiply(new BigDecimal(couset.getCounponCount()-str.length)));
						couset.setCounponCount(couset.getCounponCount()-str.length+num);
						bpCouponSettingService.save(couset);
						msg="发送成功";
					}else{
						System.out.println("派发人数不能大于剩余张数");
						msg="1";
					}
					/*if(p2pAccount!=null&&!"".equals(p2pAccount)){
					BpCustMember member=bpCustmemberService.getMemberUserName(p2pAccount);
					if(member!=null){
						Boolean flag=bpCouponsService.couponDistribute(member.getId(),member.getTruename(),ContextUtil.getCurrentUserId(),ContextUtil.getCurrentUser().getFullname(),bps);
						if(flag){
							msg="成功给用户【"+p2pAccount+"】派发了优惠券";
						}else{
							msg="优惠券派发出错,请稍后再试";
						}
					}else{
						msg="派发用户信息没有找到";
					}
				}else{
					msg="没有填写派发用户的P2P登陆账号";
				}*/
				}else{
					msg="没有优惠券信息，无法派发";
				}
			}else{
				msg="没有选中派发的优惠券或用户";
			}
		}
		jsonString="{success:true,msg:\""+msg+"\"}";
		return SUCCESS;
	}
	/**
	 * 发送邮件
	 * @param email
	 */
	public void sendEmail(String email,BigDecimal couponValue,Date endTime,String couponType,String couponNumber){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String project=configMap.get("subject").toString();
		String telphone=configMap.get("phone").toString();
		String dw="";
		if(couponType.equals("1")||couponType.equals("2")){
			dw="元";
		}else if(couponType.equals("3")){
			dw="%";
		}
		
		String temp = configMap.get("sms_bpCoupons").toString();
		String content = temp.replace("${parValue}", couponValue.setScale(2)+dw).replace("${couponNumber}", couponNumber).replace("${endTime}", 
				sdf.format(endTime)).replace("${phone}", telphone).replace("${subject}", project);
		
		
		String tempPath="mail/bpCoupons.vm";
		Map model=new HashMap();
		MailMessageProducer mailMessageProducerThread = (MailMessageProducer) AppUtil.getBean("mailMessageProducer");
		MailModel mailModel=new MailModel();
		model.put("content", content);
		model.put("project", project);
		mailModel.setMailTemplate(tempPath);
		mailModel.setTo(email);
		mailModel.setMailData(model);
		//把邮件加至发送列队中去
		mailMessageProducerThread.send(mailModel);
	}
	/**
	 * 发送优惠券短信
	 * @param email
	 * @param couponNumber
	 * @param endTime
	 */
	public void sendTelphone(String userTelephone,BigDecimal couponValue,Date endTime,String couponType,String couponNumber){
		String project=configMap.get("subject").toString();
		String telphone=configMap.get("phone").toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SmsSendUtil send = new SmsSendUtil();
		send.sendCode(userTelephone,couponValue, couponNumber,sdf.format(endTime),telphone,project,couponType);
	}
	/**
	 * 发送站内信
	 * @param couponValue优惠券面值
	 * @param endTime过期时间
	 * @param couponType优惠券类型
	 * @param memberId用户id
	 * @param setPatten发送类型
	 * @param couponNumber优惠券编码
	 */
	public void sms_oaMessage(BigDecimal couponValue,Date endTime,String couponType,Long memberId,String setPatten,String couponNumber){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String project=configMap.get("subject").toString();
		String telphone=configMap.get("phone").toString();
		String dw="";
		if(couponType.equals("1")||couponType.equals("2")){
			dw="元";
		}else if(couponType.equals("3")){
			dw="%";
		}
		String content="";
		if(setPatten.equals("1")){//系统派发
			Map<String, String> map = new HashMap<String, String>();
			map.put("${parValue}", couponValue.setScale(2)+dw);
			map.put("${endTime}", sdf.format(endTime));
			map.put("key", "sms_oaMessage");
			content = smsDetailsService.getContent(map);
			
			//String temp = configMap.get("sms_oaMessage").toString();
			//content = temp.replace("${parValue}", couponValue.setScale(2)+dw).replace("${endTime}", 
			//		sdf.format(endTime)).replace("${phone}", telphone).replace("${subject}", project);
		}else if(setPatten.equals("2")||setPatten.equals("3")){//短信派发，邮件派发
			Map<String, String> map = new HashMap<String, String>();
			map.put("${parValue}", couponValue.setScale(2)+dw);
			map.put("${couponNumber}", couponNumber);
			map.put("${endTime}", sdf.format(endTime));
			map.put("key", "sms_bpCoupons");
			content = smsDetailsService.getContent(map);
			
			//String temp = configMap.get("sms_bpCoupons").toString();
			//content = temp.replace("${parValue}", couponValue.setScale(2)+dw).replace("${couponNumber}", couponNumber).replace("${endTime}", 
			//		sdf.format(endTime)).replace("${phone}", telphone).replace("${subject}", project);
		}
		String title="优惠券派发提醒";
		oaNewsMessageService.sedBpcouponsMessage(title,content,memberId,"");
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				bpCouponsService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		BpCoupons bpCoupons=bpCouponsService.get(couponId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCoupons));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpCoupons.getCouponId()==null){
			bpCouponsService.save(bpCoupons);
		}else{
			BpCoupons orgBpCoupons=bpCouponsService.get(bpCoupons.getCouponId());
			try{
				BeanUtil.copyNotNullProperties(orgBpCoupons, bpCoupons);
				bpCouponsService.save(orgBpCoupons);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	public void excelBpcoupons(){
		List<BpCoupons> allList= bpCouponsService.bouponBelongList(this.getRequest(),null,null);
		for(BpCoupons cou:allList){
			if(cou.getCouponResourceType().equals("couponResourceType_active")){
				cou.setCouponResourceText("活动优惠券");
			}else{
				cou.setCouponResourceText("普通派发优惠券");
			}
			if(cou.getCouponType()==1){
				cou.setCouponText("返现券");
				cou.setValueText(cou.getCouponValue().setScale(2)+"元");
			}else if(cou.getCouponType()==2){
				cou.setCouponText("体验券");
				cou.setValueText(cou.getCouponValue().setScale(2)+"元");
			}else if(cou.getCouponType()==3){
				cou.setCouponText("加息券");
				cou.setValueText(cou.getCouponValue().setScale(2)+"%");
			}
			if(cou.getCouponStatus().compareTo(Short.valueOf("5"))==0){
				cou.setStatusText("未使用");
			}else if(cou.getCouponStatus().compareTo(Short.valueOf("3"))==0){
				cou.setStatusText("已禁用");
			}else if(cou.getCouponStatus().compareTo(Short.valueOf("4"))==0){
				cou.setStatusText("已过期");
			}else if(cou.getCouponStatus().compareTo(Short.valueOf("10"))==0){
				cou.setStatusText("已使用");
			}else if(cou.getCouponStatus().compareTo(Short.valueOf("1"))==0){
				cou.setStatusText("占用中");
			}else if(cou.getCouponStatus().compareTo(Short.valueOf("0"))==0){
				cou.setStatusText("未激活");
			}
		}
		String[] tableHeader = {"序号", "用户名", "优惠券来源", "优惠券类型","优惠券编号","面值" ,"有效开始时间",
				"过期时间","状态","活动编号","绑定时间","使用时间","说明"};
		String[] fields = {"logginName","couponResourceText","couponText","couponNumber","valueText","couponStartDate",
				"couponEndDate","statusText","activityNumber","bindOpraterDate","useTime","resourceName"};
		try {
			ExportExcel.export(tableHeader, fields, allList,"优惠券派发记录", BpCoupons.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void excelUseBpcoupons(){
		List<BpCoupons> allList= bpCouponsService.bouponBelongList(this.getRequest(),null,00);
			for(BpCoupons bp :allList){
				QueryFilter filter = new QueryFilter(this.getRequest());
				filter.addFilter("Q_couponId_L_EQ", bp.getCouponId());
				List<PlBidInfo> bidinfo = plBidInfoService.getAll(filter);
				if(bidinfo.size()>0){
					bp.setInfoMoney(bidinfo.get(0).getUserMoney());//投资金额
					QueryFilter filter1 = new QueryFilter(this.getRequest());
					filter1.addFilter("Q_bidId_L_EQ", bidinfo.get(0).getBidId());
					List<PlBidPlan> plan = plBidPlanService.getAll(filter1);
					if(plan.size()>0){
						bp.setReturnRatio(plan.get(0).getReturnRatio());//返现比例
					}
				}
				if(bp.getCouponResourceType().equals("couponResourceType_active")){
					bp.setCouponResourceText("活动优惠券");
				}else{
					bp.setCouponResourceText("普通派发优惠券");
				}
				if(bp.getCouponType()==1){
					bp.setCouponText("返现券");
					bp.setValueText(bp.getCouponValue().setScale(2)+"元");
				}else if(bp.getCouponType()==2){
					bp.setCouponText("体验券");
					bp.setValueText(bp.getCouponValue().setScale(2)+"元");
				}else if(bp.getCouponType()==3){
					bp.setCouponText("加息券");
					bp.setValueText(bp.getCouponValue().setScale(2)+"%");
				}
				if(null!=bp.getUseProjectType() && bp.getUseProjectType().equals("mmplan")){

					bp.setProjectType("理财计划");
				}else{
					bp.setProjectType("散标");
				}
			}
		
		String[] tableHeader = { "序号","用户名", "优惠券来源", "优惠券类型","优惠券编号","面值","活动编号","绑定时间","使用时间","使用类型","投标名称","投标编号","投资金额(元)","折现比例(%)","奖励金额(元)","说明"};
		String[] fields = {"logginName","couponResourceText","couponText","couponNumber","valueText","activityNumber",
				"bindOpraterDate","useTime","projectType","useProjectName","useProjectNumber","infoMoney","returnRatio","couponMoney","resourceName"};
		try {
			ExportExcel.export(tableHeader, fields, allList,"优惠券使用明细记录", BpCoupons.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
