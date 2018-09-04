package com.thirdPayInterface;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.thirdPayInterface.ThirdPayLog.service.ThirdPayRecordService;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.thirdInterface.WebBankcard;
import com.zhiwei.credit.service.thirdInterface.OpraterBussinessDataService;

/**
 * 
 * @author linyan
 * 2015-5-4
 *
 */
public class ThirdPayInterfaceAction extends BaseAction {
	
	@Resource
	private OpraterBussinessDataService opraterBussinessDataService;
	@Resource
	private ThirdPayRecordService thirdPayRecordService;
	/**
	 * 页面回调通知
	 * @return
	 */
	public String pageUrl(){
		Map maps = ThirdPayUtil.createResponseMap(this.getRequest());
		try {
				Thread.sleep(1000);
				System.out.println("maps-erp="+maps);
				//解析第三方返回值
				CommonResponse commonResponse=ThirdPayInterfaceUtil.returnPageOprate(maps,this.getRequest());
				
				//更新第三方日志
				//commonResponse.setBussinessType(thirdPayRecord.getInterfaceType());
				thirdPayRecordService.insertOrUpdateLog(null,commonResponse);
				
				//根据  commonResponse.getRequestNo()流水号 查询第三方记录判断实际业务类型 
				ThirdPayRecord thirdPayRecord = thirdPayRecordService.getByOrderNo(commonResponse.getRequestNo());
				
				//获取响应编码，选择消息展示模式（是成功标识，还是失败标识）
				if(commonResponse!=null&&commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
					if(ThirdPayConstants.BUSSINESSTYPE_REGISTER.equals(thirdPayRecord.getInterfaceType())){//开通第三方
						Map<String ,String> map=new HashMap<String,String>();
						String[] num=commonResponse.getRequestNo().split("-");
						String customerId=num[num.length-2];
						
						map.put("custermemberId", customerId);
						map.put("custermemberType", "0");
						map.put("platformUserNo", commonResponse.getRequestNo());
						map.put("platFormUserName",commonResponse.getRequestNo());
						opraterBussinessDataService.regedit(map);
					}else if(ThirdPayConstants.BUSSINESSTYPE_BINDBANKCAR.equals(thirdPayRecord.getInterfaceType())){//绑定银行卡
						Map<String ,String> map=new HashMap<String,String>();
						map.put("custermemberId", "");
						map.put("requestNo",commonResponse.getRequestNo());
						map.put("custmerType", "0");
						map.put("bankCardNo","");
						map.put("bankCode","");
						map.put("bankName","");
						map.put("bankstatus","bindCard_status_repare");
						opraterBussinessDataService.bandCard(map);
					}else if(ThirdPayConstants. BUSSINESSTYPE_CANCELBINDBANKCARD.equals(thirdPayRecord.getInterfaceType())){//取消绑定银行卡
						Map<String,String> map = new HashMap<String, String>();
						map.put("requestNo",commonResponse.getRequestNo());
						map.put("bankstatus","bindCard_status_cancel"); 
						opraterBussinessDataService.bandCard(map);
					}else if(ThirdPayConstants.BUSSINESSTYPE_AUTOBID.equals(commonResponse.getBussinessType())){//自动投标授权
						Map<String,String> map = new HashMap<String, String>();
						map.put("ThirdPayConfigId", thirdPayRecord.getThirdPayFlagId());
						map.put("custermemberId", "");
						opraterBussinessDataService.bidingAuthorization(map);
					}else if(ThirdPayConstants.BUSSINESSTYPE_RECHAGE.equals(commonResponse.getBussinessType())){//充值成功
						Map<String,String> map = new HashMap<String, String>();
						map.put("requestNo", commonResponse.getRequestNo());
						map.put("dealRecordStatus", ObAccountDealInfo.DEAL_STATUS_2.toString());
						opraterBussinessDataService.recharge(map);
					}else if(ThirdPayConstants.BUSSINESSTYPE_BID.equals(commonResponse.getBussinessType())){//投标成功
						Map<String,String> map = new HashMap<String, String>();
						map.put("custmerType", "0");
						map.put("requestNo", commonResponse.getRequestNo());
						map.put("dealRecordStatus", ObAccountDealInfo.DEAL_STATUS_2.toString());
						if(ThirdPayConstants.BUSSINESSNAME_MMPLAN.equals(thirdPayRecord.getInterfaceName())){
							opraterBussinessDataService.bidMoneyPlan(map);//D计划
						}else{
							opraterBussinessDataService.biding(map);//散标投资
						}
					}else if(ThirdPayConstants.BUSSINESSTYPE_LOANERREPAYMENT.equals(commonResponse.getBussinessType())){//p2p立即返款
						Map<String,String> map = new HashMap<String, String>();
						map.put("bidId",thirdPayRecord.getBidId().toString());//标id
						map.put("intentDate",thirdPayRecord.getIntentDate().toString());//计划还款日期
						map.put("requestTime", thirdPayRecord.getRequestTime().toString());//请求接口时间
						map.put("requestNum", thirdPayRecord.getRequestNum().toString());//请求接口次数
						map.put("requestNo", commonResponse.getRequestNo());
						opraterBussinessDataService.repayment(map);
					}else if(ThirdPayConstants.BUSSINESSTYPE_LOANERDIRECTLYREPAYMENT.equals(commonResponse.getBussinessType())){//erp返款
						Map<String,String> map = new HashMap<String, String>();
						map.put("bidId",thirdPayRecord.getBidId().toString());//标id
						map.put("intentDate",thirdPayRecord.getIntentDate().toString());//计划还款日期
						map.put("requestTime", thirdPayRecord.getRequestTime().toString());//请求接口时间
						map.put("requestNum", thirdPayRecord.getRequestNum().toString());//请求接口次数
						map.put("requestNo", commonResponse.getRequestNo());
						opraterBussinessDataService.repayment(map);
					}else if(ThirdPayConstants.BT_WITHDRAW.equals(commonResponse.getBussinessType())){//取现成功
						Map<String,String> map = new HashMap<String, String>();
						if(commonResponse.getRequestNo()!=null&&!commonResponse.getRequestNo().equals("")){
							map.put("requestNo", commonResponse.getRequestNo());
						}
						map.put("dealRecordStatus", ObAccountDealInfo.DEAL_STATUS_2.toString());
						if(commonResponse.getLoanNo()!=null&&!commonResponse.getLoanNo().equals("")){
							map.put("loanNo", commonResponse.getLoanNo());
						}
						map.put("custmerType", "");
						opraterBussinessDataService.withDraw(map);
					}else if(ThirdPayConstants.BUSSINESSTYPE_CLOSEAUTOBID.equals(commonResponse.getBussinessType())){//关闭自动投标授权
						Map<String,String> map = new HashMap<String, String>();
						map.put("ThirdPayConfigId", commonResponse.getThirdPayConfigId());
						map.put("custermemberId", "");
						opraterBussinessDataService.closeBidingAuthorization(map);
					}else if(ThirdPayConstants.BUSSINESSTYPE_NOPWREPAYMENT.equals(commonResponse.getBussinessType())){//无密还款授权
						Map<String ,String> map=new HashMap<String,String>();
						map.put("orderNo", "");
						map.put("requestNo",commonResponse.getRequestNo());
						if(ThirdPayConstants.BUSSINESSNAME_NOPWREPAYMENTMMPLAN.equals(thirdPayRecord.getInterfaceName())){//理财计划派息
							opraterBussinessDataService.repaymentAuthorizationMoneyPlan(map);
						}else{
							opraterBussinessDataService.repaymentAuthorization(map);//散标还款
						}
					}else if(ThirdPayConstants.BUSSINESSTYPE_RELIEVENOPWREPAYMENT.equals(commonResponse.getBussinessType())){//解除无密还款授权
						Map<String,String> map = new HashMap<String, String>();
						map.put("ThirdPayConfigId", commonResponse.getThirdPayConfigId());
						map.put("oprateType", "cancel");
						opraterBussinessDataService.rAuthorization(map);
					}else if(ThirdPayConstants.BT_LOAN.equals(commonResponse.getBussinessType())){//放款
						Map<String,String> map = new HashMap<String, String>();
						map.put("requestNo", commonResponse.getRequestNo());
						map.put("LoanNoList", thirdPayRecord.getRemark1());
						opraterBussinessDataService.loan(map);
					}else if(ThirdPayConstants.BUSSINESSTYPE_REVOCATIONTRANSFER.equals(commonResponse.getBussinessType())){//流标
						Map<String,String> map = new HashMap<String, String>();
						map.put("requestNo", commonResponse.getRequestNo());
						map.put("LoanNoList", maps.get("LoanNoList").toString());
						opraterBussinessDataService.loanFailed(map);
					}else if(ThirdPayConstants.BUSSINESSTYPE_TOTRANSFERCLAIMS.equals(thirdPayRecord.getInterfaceType())){//债权转让
						Map<String,String> map = new HashMap<String, String>();
						map.put("requestNo", commonResponse.getRequestNo());
						map.put("dealRecordStatus", ObAccountDealInfo.DEAL_STATUS_2.toString());
						opraterBussinessDataService.doObligationDeal(map);
					}else if(ThirdPayConstants.BUSSINESSTYPE_TORESETMOBILE.equals(thirdPayRecord.getInterfaceType())){//修改手机号
						Map<String ,String> map=new HashMap<String,String>();
						map.put("requestNo",commonResponse.getRequestNo());
						map.put("dealstatus", "2");//表示修改成功
						opraterBussinessDataService.chageMobile(map);
					}else if(ThirdPayConstants.BUSSINESSTYPE_TOCPTRANSACTION.equals(thirdPayRecord.getInterfaceType())){//通用转账授权
						Map<String,String> map = new HashMap<String, String>();
						map.put("requestNo", commonResponse.getRequestNo());
						map.put("dealRecordStatus", ObAccountDealInfo.DEAL_STATUS_2.toString());
						if(ThirdPayConstants.BUSSINESSNAME_TOCPTRANSACTION.equals(thirdPayRecord.getInterfaceName())){//易宝第三方支付通用转账挂牌业务处理方法
							opraterBussinessDataService.doObligationPublish(map);
						}else if(ThirdPayConstants.BUSSINESSNAME_MMPLAN.equals(thirdPayRecord.getInterfaceName())){//D计划收款户为平台账户
							map.put("custmerType", "0");
							opraterBussinessDataService.bidMoneyPlan(map);
						}else if(ThirdPayConstants.BUSSINESSNAME_DCHK.equals(thirdPayRecord.getInterfaceName())){//代偿还款
							opraterBussinessDataService.doCompensatory(commonResponse.getRequestNo(),Short.valueOf("7"));
						}else if(ThirdPayConstants.BUSSINESSNAME_PFUI.equals(thirdPayRecord.getInterfaceName())){//站岗资金的购买
							opraterBussinessDataService.doFianceProductBuy(commonResponse.getRequestNo(),Short.valueOf("7"));
						}
					}else if(ThirdPayConstants.BT_MMAUTH.equals(thirdPayRecord.getInterfaceType())){//理财计划授权平台派息
						Map<String ,String> map=new HashMap<String,String>();
						map.put("orderNo", "");
						map.put("requestNo",commonResponse.getRequestNo());
						if(thirdPayRecord.getThirdPayConfig().equals("moneyMoreMoreConfig")){//双乾
							opraterBussinessDataService.moneyMoneyAuthorizationMoneyPlan(map);
						}else if(thirdPayRecord.getThirdPayConfig().equals("umpayConfig")){//联动
							map.put("open", maps.get("user_bind_agreement_list").toString());//开启类型
							map.put("close", "");//关闭类型
							opraterBussinessDataService.umpayLoanAuthorize(map);
						}else{//易宝
							opraterBussinessDataService.repaymentAuthorizationMoneyPlan(map);
						}
					}else if(ThirdPayConstants.BT_HELPPAY.equals(commonResponse.getBussinessType())){//BT_HELPPAY
						Map<String,String> map = new HashMap<String, String>();
						map.put("bidId",thirdPayRecord.getBidId().toString());//标id
						map.put("intentDate",thirdPayRecord.getIntentDate().toString());//计划还款日期
						map.put("requestTime", thirdPayRecord.getRequestTime().toString());//请求接口时间
						map.put("requestNum", thirdPayRecord.getRequestNum().toString());//请求接口次数
						map.put("requestNo", commonResponse.getRequestNo());
						map.put("thirdPayConfig", commonResponse.getThirdPayConfig());
						if(thirdPayRecord.getThirdPayConfig().equals("umpayConfig")){
							opraterBussinessDataService.umpayRepayment(map);
						}else{
							opraterBussinessDataService.repayment(map);
						}
					}
					setJsonString(thirdPayRecord.getInterfaceName()+"申请成功");
				}else if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_FAILD)){
					setJsonString(thirdPayRecord.getInterfaceName()+"失败");
				}else{
					setJsonString(thirdPayRecord.getInterfaceName()+"失败");
				}
		} catch (Exception e) {
			setJsonString("第三方返回参数="+maps);
			e.printStackTrace();
		}
		return SUCCESS;
	}



	/**
	 * 服务器端回调通知
	 * @return   由于每家第三方return对象不同    return  对象来自每家第三方接口返回对象
	 */
	public String notifyUrl(){
		CommonResponse commonResponse=ThirdPayInterfaceUtil.notifyCallBackOprate(this.getRequest());
		return null;
	}
}
