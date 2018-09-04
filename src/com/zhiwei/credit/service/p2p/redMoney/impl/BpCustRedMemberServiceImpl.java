package com.zhiwei.credit.service.p2p.redMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.zhiwei.core.Constants;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.util.Common;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.dao.p2p.redMoney.BpCustRedMemberDao;
import com.zhiwei.credit.model.activity.BpActivityManage;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedEnvelope;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedMember;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.redMoney.BpCustRedEnvelopeService;
import com.zhiwei.credit.service.p2p.redMoney.BpCustRedMemberService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;

/**
 * 
 * @author 
 *
 */
public class BpCustRedMemberServiceImpl extends BaseServiceImpl<BpCustRedMember> implements BpCustRedMemberService{
	@SuppressWarnings("unused")
	private BpCustRedMemberDao dao;
	@Resource
	private BpCustRedEnvelopeService bpCustRedEnvelopeService;
	
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private YeePayService yeePayService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	
	public BpCustRedMemberServiceImpl(BpCustRedMemberDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<BpCustRedMember> getActivityNumber(String activityNumber,
			String bpCustMemberId, String remark) {
		// TODO Auto-generated method stub
		return dao.getActivityNumber(activityNumber, bpCustMemberId, remark);
	}

	@Override
	public String[] saveredmembers(String reddatas, Long redId) {
		String[] ret=new String[2];
		BigDecimal sumredMoney=new BigDecimal(0);
		Integer count=0;
		
			if (null != reddatas && !"".equals(reddatas)) {
				;
				QueryFilter filter=new QueryFilter( ServletActionContext.getRequest());
				filter.addFilter("Q_redId_L_EQ", redId.toString());
				List<BpCustRedMember> list= dao.getAll(filter);
				for(BpCustRedMember a:list){
					dao.remove(a);
				}
				String[] reddatasArr = reddatas.split("@");
	
				for (int i = 0; i < reddatasArr.length; i++) {
					String str = reddatasArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
	
					try {
	
						BpCustRedMember bpCustRedMember = (BpCustRedMember) JSONMapper
								.toJava(parser.nextValue(), BpCustRedMember.class);
				
							
						if (null ==bpCustRedMember.getRedTopersonId()||bpCustRedMember.getRedTopersonId().compareTo(new Long(0))==0) {
							bpCustRedMember.setRedTopersonId(null);
							bpCustRedMember.setRedId(redId);
							bpCustRedMember.setEdredMoney(new BigDecimal(0));
							dao.save(bpCustRedMember);
							sumredMoney=sumredMoney.add(bpCustRedMember.getRedMoney());
							count++;
						} else {

							 bpCustRedMember.setEdredMoney(new BigDecimal(0));
							   BpCustRedMember bpCustRedMember2 = dao.get(bpCustRedMember.getRedTopersonId());
								BeanUtil.copyNotNullProperties(bpCustRedMember2,
										bpCustRedMember);
								dao.save(bpCustRedMember2);
								sumredMoney=sumredMoney.add(bpCustRedMember.getRedMoney());
								count++;
							}
						
	
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(" 保存出错:"+e.getMessage());
						
						return ret;
					}
	
					
				}
				
			}	
			ret[0]=sumredMoney.toString();
			ret[1]=count.toString();
			return ret;
			}

	@Override
	public List<BpCustRedMember> listbyredId(Long redId,String type) {
		// TODO Auto-generated method stub
		return dao.listbyredId(redId,type);
	}

	@Override
	public BpCustRedMember bymemberId(Long memberId) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer(" from BpCustRedMember a where a.bpCustMemberId=? ");
		List<BpCustRedMember> list= dao.findByHql(hql.toString(), new Object[]{memberId});
		if(null !=list&&list.size()>0){
			
			return list.get(0);
		}
		return null;
	}

	@Override
	public BpCustRedMember saveByBpActivityManage(Long userId,BigDecimal redMoney, BpActivityManage bpActivityManage) {
		String activityTypeValue = "";
		if(bpActivityManage.getSendType()==1){activityTypeValue="注册";}
		if(bpActivityManage.getSendType()==2){activityTypeValue="邀请";}
		if(bpActivityManage.getSendType()==3){activityTypeValue="投标";}
		if(bpActivityManage.getSendType()==4){activityTypeValue="充值";}
		if(bpActivityManage.getSendType()==5){activityTypeValue="邀请好友第一次投标";}
		
		//红包表
		BpCustRedEnvelope bpCustRedEnvelope = new BpCustRedEnvelope();
		bpCustRedEnvelope.setName("红包_"+activityTypeValue);
		bpCustRedEnvelope.setDistributestatus(Short.valueOf("0"));
		bpCustRedEnvelope.setDistributemoney(redMoney);
		bpCustRedEnvelope.setDistributecount(1);
		bpCustRedEnvelope.setDistributeTime(new Date());
		bpCustRedEnvelope.setRemarks("红包_"+activityTypeValue);
		bpCustRedEnvelope.setCreateTime(new Date());//创建时间
		BpCustRedEnvelope _bpCustRedEnvelope = bpCustRedEnvelopeService.save(bpCustRedEnvelope);
		//红包明细表
		BpCustRedMember bpCustRedMember = new BpCustRedMember();
		bpCustRedMember.setActivityNumber(bpActivityManage.getActivityNumber());//活动编号
		bpCustRedMember.setBpCustMemberId(Long.valueOf(userId));
		bpCustRedMember.setRedMoney(redMoney);
		bpCustRedMember.setEdredMoney(new BigDecimal("0"));
		bpCustRedMember.setRedType("bpActivityManage");
		bpCustRedMember.setRedId(_bpCustRedEnvelope.getRedId());
		bpCustRedMember.setDescription(bpActivityManage.getActivityExplain());
		bpCustRedMember.setSendType(bpActivityManage.getSendType().toString());
		bpCustRedMember.setIntention(bpActivityManage.findSendType());
		
		BpCustRedMember save = dao.save(bpCustRedMember);
		
		try {
			System.out.println("红包活动,,,发送红包-");
			//其实不需要这个地址
			//HttpServletRequest request = ServletActionContext.getRequest();
			//String path= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
			BpCustMember mem = bpCustMemberService.get(userId);

			String	requestNo=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
			CommonRequst commonRequest = new CommonRequst();
			commonRequest.setThirdPayConfigId(mem.getThirdPayFlagId());//用户第三方标识
			commonRequest.setRequsetNo(requestNo);//请求流水号
			commonRequest.setAmount(redMoney);//交易金额
			if(mem.getCustomerType()!=null&&mem.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
				commonRequest.setAccountType(1);
			}else{//借款人是个人
				commonRequest.setAccountType(0);
			}
			commonRequest.setCustMemberType("0");
			commonRequest.setBidId(bpCustRedMember.getRedTopersonId().toString());
			commonRequest.setBussinessType(ThirdPayConstants.BT_SEDRED);//业务类型
			commonRequest.setTransferName(ThirdPayConstants.TN_SEDRED);//业务名称
			CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
			if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
				//添加资金明细
				Map<String, Object> mapO = new HashMap<String, Object>();
				mapO.put("investPersonId", mem.getId());// 投资人Id（必填）
				mapO.put("investPersonType", ObSystemAccount.type0);// 投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
				mapO.put("transferType",ObAccountDealInfo.T_REDENVELOPE);// 交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
				mapO.put("money", redMoney);// 交易金额
				mapO.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);// 交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
				mapO.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);// 交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
				mapO.put("recordNumber", requestNo);// 交易流水号 （必填）
				mapO.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);// 资金交易状态：1等待支付，2支付成功，3
				String[] rett = obAccountDealInfoService.operateAcountInfo(mapO);
				//更新红包记录
				bpCustRedMember.setEdredMoney(redMoney);
				bpCustRedMember.setDistributeTime(new Date());
				bpCustRedMember.setOrderNo(requestNo);
				BpCustRedMember save2 = dao.save(bpCustRedMember);
				
				bpCustRedEnvelope.setDistributestatus(Short.valueOf("2"));
				bpCustRedEnvelopeService.save(bpCustRedEnvelope);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return save;
		
	}
}