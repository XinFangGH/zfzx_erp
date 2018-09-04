package com.thirdPayInteface;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.thirdPayInteface.SinaPay.SinaPayIntefaceUtil;
import com.thirdPayInteface.UMPay.UMPayInterfaceUtil;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.model.customer.InvestPersonInfo;

public class ThirdPayInterfaceUtil {
	//得到config.properties读取的系统配置文件，来判断使用哪家第三方以及正式还是测试环境
	private static Map configMap = AppUtil.getConfigMap();
	/**
	 * 第三方支付名称
	 */
	private static String  thirdPayConfig=configMap.containsKey("thirdPayConfig")?configMap.get("thirdPayConfig").toString().trim():ThirdPayConstants.YEEPAY;
	/**
	 * 第三方支付类型（资金池模式和资金托管模式）
	 */
	private static String  thirdPayType=configMap.containsKey("thirdPayType")?configMap.get("thirdPayType").toString().trim():ThirdPayConstants.THIRDPAYTYPE0;

	//D理财计划购买
	
	/**
	 * 开通第三方账户方法（带参数1，参数2....方式）
	 * @return
	 */
	public static Object[] register(String custMemberId,String custMemberType,String custMemberName,String certifyCardType,String certifyCardNumber,String email ,String telephone,String loginName){
		/**
		 * 将参数组织成实体对象存放在实体对象中，然后将实体对象放置在任意第三方接口工具类中
		 * 由工具类来完成接口参数的配置
		 */
		
		CommonRequst commonRequst=new CommonRequst();
		commonRequst.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_REGISTER);
		commonRequst.setCustMemberType(custMemberType);
		commonRequst.setCustMemberId(custMemberId);
		commonRequst.setTrueName(custMemberName);
		commonRequst.setCardType(certifyCardType);
		commonRequst.setCardNumber(certifyCardNumber);
		commonRequst.setEmail(email);
		commonRequst.setTelephone(telephone);
		return ThirdPayMethodCall(commonRequst);
		
		
	}
	

	/**
	 * 
	 * @param thirdPayFlagId  
	 * @param useType
	 * @param amount
	 * @param orderNum
	 * @param bankCode
	 * @return
	 */
	public static Object[] recharge(String thirdPayFlagId,String useType, String amount,String orderNum, String bankCode) {
		// TODO Auto-generated method stub
		/**
		 * 将参数组织成实体对象存放在实体对象中，然后将实体对象放置在任意第三方接口工具类中
		 * 由工具类来完成接口参数的配置
		 */
		CommonRequst commonRequst=new CommonRequst();
		commonRequst.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_RECHAGE);
		commonRequst.setCustMemberType(useType);
		commonRequst.setThirdPayConfigId(thirdPayFlagId);
		commonRequst.setAmount(new BigDecimal(amount));
		commonRequst.setRequsetNo(orderNum);
		commonRequst.setBankCode(bankCode);
		return ThirdPayMethodCall(commonRequst);
	}
	
	/**
	 * 投标接口
	 * @param thirdPayFlagId
	 * @param orderNum
	 * @param amount
	 * @param bidId
	 * @return
	 */
	public static Object[] transferInterface(String thirdPayFlagId,String orderNum,String amount,String bidId){
		Date date = new Date();
		CommonRequst common = new CommonRequst();
		common.setRequsetNo(orderNum);
		common.setTransactionTime(date);
		common.setBidId(bidId);
		common.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_BID);
		common.setCustMemberType("01");
		common.setThirdPayConfigId(thirdPayFlagId);
		common.setAmount(new BigDecimal(amount));
		return ThirdPayMethodCall(common);
		
	}
	/**
	 * p2p立即还款接口
	 * @param thirdPayFlagId
	 * @param orderNum
	 * @param amount
	 * @param bidId
	 * @return
	 */
	public static Object[] thirdPayRepayMentByLoaner(String thirdPayFlagId,String orderNum,String amount,String bidId){
		Date date = new Date();
		CommonRequst common = new CommonRequst();
		common.setRequsetNo(orderNum);
		common.setTransactionTime(date);
		common.setBidId(bidId);
		common.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_LOANERREPAYMENT);
		common.setCustMemberType("02");
		common.setThirdPayConfigId(thirdPayFlagId);
		common.setAmount(new BigDecimal(amount));
		return ThirdPayMethodCall(common);
		
	}
	/**
	 * 流标接口
	 * @param thirdPayFlagId
	 * @param orderNum
	 * @param amount
	 * @param bidId
	 * @return
	 */
	public static Object[] bidFailed(String thirdPayFlagId,String orderNum,String amount,String bidId){
		Date date = new Date();
		CommonRequst common = new CommonRequst();
		common.setRequsetNo(orderNum);
		common.setTransactionTime(date);
		common.setBidId(bidId);
		common.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_BIDFAILD);
		common.setCustMemberType("02");
		common.setThirdPayConfigId(thirdPayFlagId);
		common.setAmount(new BigDecimal(amount));
		return ThirdPayMethodCall(common);
		
	}
	/**
	 * 放款接口
	 * @param thirdPayFlagId
	 * @param orderNum
	 * @param amount
	 * @param bidId
	 * @return
	 */
	public static Object[] bidLoan(String thirdPayFlagId,String orderNum,BigDecimal amount,String bidId,List<InvestPersonInfo> investPersionList ){
		Date date = new Date();
		CommonRequst common = new CommonRequst();
		common.setRequsetNo(orderNum);
		common.setTransactionTime(date);
		common.setBidId(bidId);
		common.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_LOAN);
		common.setCustMemberType("02");
		common.setThirdPayConfigId(thirdPayFlagId);
		common.setAmount(amount);
		BigDecimal amountt =new BigDecimal (0);
		if(investPersionList!=null&&!"".equals(investPersionList)){
			List<CommonRequestInvestRecord> list=new ArrayList<CommonRequestInvestRecord>();
			for(InvestPersonInfo temp :investPersionList){
				CommonRequestInvestRecord investRecord=new CommonRequestInvestRecord();
				amountt.add(temp.getInvestMoney());
				list.add(investRecord);
			}
		}
		common.setAmount(amountt);
		return ThirdPayMethodCall(common);
		
	}
	/**
	 * 发标接口
	 * @param bussinessType
	 * @param bidId
	 * @param bidTyp
	 * @param amount
	 * @param thirdPayFlagId
	 * @return
	 */
	public static Object[] createBidAccount(String bussinessType,String bidId,String bidType,String bidName,String amount,String thirdPayFlagId){
		CommonRequst common = new CommonRequst();
		common.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_CREATEBID);
		common.setBidId(bidId);
		common.setBidName(bidName);
		common.setBidType(bidType);
		common.setAmount(new BigDecimal(amount));
		common.setLoaner_thirdPayflagId(thirdPayFlagId);
		return ThirdPayMethodCall(common);
	}
	/**
	 * 更新标
	 * @param bussinessType
	 * @param bidId
	 * @param bidTyp
	 * @param amount
	 * @param thirdPayFlagId
	 * @return
	 */
	public static Object[] UpdateBidAccount(String bussinessType,String bidId,String bidType,String bidIdStatus){
		CommonRequst common = new CommonRequst();
		common.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_UPDATEBID);
		common.setBidId(bidId);
		common.setBidType(bidType);
		common.setBidIdStatus(bidIdStatus);
		return ThirdPayMethodCall(common);
	}
	/**
	 * 充值接口
	 * 绑定银行卡接口
	 * @param thirdPayFlagId
	 * @param amount
	 * @param orderNum
	 * @param bankCode
	 * @return
	 */
	public static Object[] bindBank(CommonRequst commonRequst) {
		// TODO Auto-generated method stub
		/**
		 * 将参数组织成实体对象存放在实体对象中，然后将实体对象放置在任意第三方接口工具类中
		 * 由工具类来完成接口参数的配置
		 */
		commonRequst.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_BINDBANKCAR);
		return ThirdPayMethodCall(commonRequst);
	}
	
	/**
	 * 取现丶提现接口
	 * @param thirdPayFlagId
	 * @param amount
	 * @param orderNum
	 * @param bankCode
	 * @return
	 */
	public static Object[] withdrawMoney(CommonRequst commonRequst) {
		// TODO Auto-generated method stub
		/**
		 * 将参数组织成实体对象存放在实体对象中，然后将实体对象放置在任意第三方接口工具类中
		 * 由工具类来完成接口参数的配置
		 */
		commonRequst.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_WITHDRAW);
		return ThirdPayMethodCall(commonRequst);
	}
	
	
	/**
	 *调用第三方接口方法
	 * 
	 * @return
	 */
	private static Object[] ThirdPayMethodCall(CommonRequst commonRequst){
		Object[] ret=new Object[3];
		if(thirdPayType.equals(ThirdPayConstants.THIRDPAYTYPE0)){//资金托管模式
			if(thirdPayConfig.equals(ThirdPayConstants.SINAPAY)){//新浪支付
				if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_REGISTER)){//注册
					Object[] sinaPay=null;
					ret[0]=sinaPay[0];
					ret[1]=sinaPay[1];
					ret[2]=sinaPay[2];
				}else {
					ret[0]=ThirdPayConstants.RECOD_INVAILID;
					ret[1]="尚未对接此业务";
					ret[2]=commonRequst;
				}
			}else if(thirdPayConfig.equals(ThirdPayConstants.UMPAY)){//联动优势支付
				if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_REGISTER)){//注册
					Object[] umpay=UMPayInterfaceUtil.regiest(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_RECHAGE)){//充值
					if(commonRequst.getBankCode()==null){
						commonRequst.setBankCode("CMB");//由于页面没有传银行编号，所以默认选择招商银行
						commonRequst.setAmount(new BigDecimal(10));
					}
					commonRequst.setReturnType(ThirdPayConstants.RETURNTYPE_WAITOPRATE);
					Object[] umpay=UMPayInterfaceUtil.recharge(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_AUTOBID)){//自动投标授权
					commonRequst.setAutoAuthorizationType("invest");
					commonRequst.setReturnType(ThirdPayConstants.RETURNTYPE_WAITOPRATE);
					Object[] umpay=UMPayInterfaceUtil.autoAuthorization(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_BID)){//投标
					Object[] umpay=UMPayInterfaceUtil.transferInterface(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_BINDBANKCAR)){//绑定银行卡
					Object[] umpay = UMPayInterfaceUtil.toBindBankCard(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_CREATEBID)){//发标
					Object[] umpay = UMPayInterfaceUtil.createBidAccount(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_UPDATEBID)){//更新标
					Object[] umpay = UMPayInterfaceUtil.UpdateBidAccount(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_LOANERREPAYMENT)){//p2p立即还款
					Object[] umpay = UMPayInterfaceUtil.transferInterface(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_WITHDRAW)){//取现
					Object[] umpay = UMPayInterfaceUtil.toWithdraw(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_CLOSEAUTOBID)){//关闭自动投标授权
					commonRequst.setAutoAuthorizationType("invest");
					commonRequst.setReturnType(ThirdPayConstants.RETURNTYPE_WAITOPRATE);
					Object[] umpay=UMPayInterfaceUtil.cancelAuthorization(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_NOPWREPAYMENT)){//无密还款授权
					commonRequst.setAutoAuthorizationType("repayment");
					commonRequst.setReturnType(ThirdPayConstants.RETURNTYPE_WAITOPRATE);
					Object[] umpay=UMPayInterfaceUtil.autoAuthorization(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_RELIEVENOPWREPAYMENT)){//解除无密还款授权
					commonRequst.setAutoAuthorizationType("repayment");
					commonRequst.setReturnType(ThirdPayConstants.RETURNTYPE_WAITOPRATE);
					Object[] umpay=UMPayInterfaceUtil.cancelAuthorization(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_BIDFAILD)){//流标
					//commonRequst.setBidType(bidType);
					Object[] umpay = UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_LOAN)){//放款
					commonRequst.setReturnType(ThirdPayConstants.RETURNTYPE_DOOPRATE);
					Object[] umpay = UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
					ret[0]=umpay[0];
					ret[1]=umpay[1];
					ret[2]=umpay[2];
				}else {
					ret[0]=ThirdPayConstants.RECOD_INVAILID;
					ret[1]="尚未对接此业务";
					ret[2]=commonRequst;
				}
			}else if(thirdPayConfig.equals(ThirdPayConstants.YEEPAY)){//易宝支付
				
			}else{
				ret[0]=ThirdPayConstants.RECOD_INVAILID;
				ret[1]="请检查第三方支付类型是否为系统提供的第三方类型";
				ret[2]=commonRequst;
			}
		}else{//资金池模式
			ret[0]=ThirdPayConstants.RECOD_INVAILID;
			ret[1]="资金池模式不需要进行第三方支付账户开通";
			ret[2]=commonRequst;
		}
		return ret;
	}
	/**
	 * 页面回调
	 * @param request
	 * @return
	 */
	public static CommonResponse pageOprate(HttpServletRequest request) {
		// TODO Auto-generated method stub
		CommonResponse commonResponse =new CommonResponse();
		if(thirdPayType.equals(ThirdPayConstants.THIRDPAYTYPE0)){//资金托管模式
			if(thirdPayConfig.equals(ThirdPayConstants.SINAPAY)){//新浪支付
				commonResponse=SinaPayIntefaceUtil.pageCallBackOprate(request);
			}else if(thirdPayConfig.equals(ThirdPayConstants.UMPAY)){//联动优势支付
				commonResponse=UMPayInterfaceUtil.pageCallBackOprate(request);
			}else if(thirdPayConfig.equals(ThirdPayConstants.YEEPAY)){//易宝支付
				
			}else{
				
			}
			
		}else{//资金池模式
			
		}
		return commonResponse;
	}
	/**
	 * 服务器端回调函数通知
	 * @param request
	 * @return
	 */
	public static CommonResponse notifyCallBackOprate(HttpServletRequest request) {
		CommonResponse commonResponse =new CommonResponse();
		if(thirdPayType.equals(ThirdPayConstants.THIRDPAYTYPE0)){//资金托管模式
			if(thirdPayConfig.equals(ThirdPayConstants.SINAPAY)){//新浪支付
				commonResponse=SinaPayIntefaceUtil.notifyCallBackOprate(request);
			}else if(thirdPayConfig.equals(ThirdPayConstants.UMPAY)){//联动优势支付
				commonResponse=UMPayInterfaceUtil.notifyCallBackOprate(request);
			}else if(thirdPayConfig.equals(ThirdPayConstants.YEEPAY)){//易宝支付
				
			}else{
				
			}
			
		}else{//资金池模式
			
		}
		return commonResponse;
	}

	/**
	 * 自动投标授权接口
	 * @param orderNo
	 * @param thirdPayFlagId
	 * @param string
	 * @param string2
	 * @return
	 */
	public static Object[] autoTransferAuthorization(CommonRequst commonRequst) {
		commonRequst.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_AUTOBID);
		return ThirdPayMethodCall(commonRequst);
	}


	/**
	 * 关闭自动投标授权接口
	 * @param thirdPayFlagId
	 * @param string
	 * @param string2
	 * @return
	 */
	public static Object[] closeAutoTransferAuthorization(CommonRequst commonRequst) {
		commonRequst.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_CLOSEAUTOBID);
		return ThirdPayMethodCall(commonRequst);
	}
	
	/**
	 * 无密还款授权接口
	 * @param thirdPayFlagId
	 * @param string
	 * @param string2
	 * @return
	 */
	public static Object[] noPasswordRepayment(CommonRequst commonRequst) {
		commonRequst.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_NOPWREPAYMENT);
		return ThirdPayMethodCall(commonRequst);
	}
	
	/**
	 * 解除无密还款授权接口
	 * @param thirdPayFlagId
	 * @param string
	 * @param string2
	 * @return
	 */
	public static Object[] relieveNoPasswordRepayment(CommonRequst commonRequst) {
		commonRequst.setBussinessType(ThirdPayConstants.BUSSINESSTYPE_RELIEVENOPWREPAYMENT);
		return ThirdPayMethodCall(commonRequst);
	}
	
	/**
	 * 业务层调用第三方公用方法（接口实例化的时候，实例化类名配置在config.properties中，例如：thirdPayURL = com.thirdPayInteface.YeePay.YeepayConfigImpl）
	 * 调用对应第三方时，config.properties中必须配置对应第三方接口实现类的全路径
	 * @param commonRequst  
	 * @return commonResponse
	 */

	public  static CommonResponse thirdCommon(CommonRequst commonRequst){
		CommonResponse commonResponse=new CommonResponse();
		try {
			ThirdPayTypeInterface s = (ThirdPayTypeInterface) Class.forName(configMap.get("thirdPayURL").toString().trim()).newInstance();
			//判断是托管还是资金池
			if(configMap.get("thirdPayType").toString().equals(ThirdPayConstants.THIRDPAYTYPE0)){//托管
				commonResponse=s.businessHandle(commonRequst);
			}else{//资金池
				/*ret[0]=ThirdPayConstants.RECOD_INVAILID;
			ret[1]="资金池模式不需要进行第三方支付账户开通";
			ret[2]=commonRequst;*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return commonResponse;
	}
	
	public static void main(String[] args) {
		ThirdPayInterfaceUtil t=new ThirdPayInterfaceUtil();
		t.thirdCommon(new CommonRequst());
	}
	
}
