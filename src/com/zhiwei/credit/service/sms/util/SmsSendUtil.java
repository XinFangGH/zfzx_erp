package com.zhiwei.credit.service.sms.util;

import com.hurong.extend.module.CommonSendUtil;
import com.hurong.extend.module.commonRequest.SendMsgCommonRequest;
import com.hurong.extend.module.commonResponse.SendMsgCommonResponse;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.service.sms.MessageStrategyService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 发送短信
 * @author songwenjie
 *
 */
@SuppressWarnings({ "unchecked"})
public class SmsSendUtil extends BaseAction {
	
	public Map configMap=AppUtil.getConfigMap();
	
	public String sms_Sign(String telephone, String codeStr) {
		String content = "";
		if (telephone != null && !"".equals(telephone)) {
			// 获得短信发送模板
			String temp = configMap.get("sms_Sign").toString();
			if (temp != null && !"".equals(temp)) {
				content = temp.replace("${code}", codeStr)
						.replace("${phone4}",configMap.get("phone") == null ? "" : configMap.get( "phone").toString())
						.replace( "${subject}", configMap.get("subject") == null ? "" : configMap.get( "subject").toString());
				System.out.println("*****短信验证码发送内容=" + content);
			}
		}
//		sendMsg(telephone, content);
		return "";
	}
	
	/**
	 * 发送短信方法
	 * 
	 * @param phone
	 *            接收短信的手机号
	 * @param content
	 *            短信内容
	 * @return
	 */
	public String sendMsg(String phone, String content) {
		//检查是否需要发送短信，如果配置if_test是否配值了  ，如果配置了  则不发送短信
		if(configMap.containsKey("if_test")&&configMap.get("if_test")!=null&&!configMap.get("if_test").equals("")){
			
		}else{//没有if_test属性或者没有配置值，则发送短信
			MessageStrategyService messageStrategy = (MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//			messageStrategy.sendMsg(phone, content);
		}
		return "";
	}
	
	/***************************** 会员成功注册后发送短信*******************************/
	//svn:songwj
	public String vIPRegistrationSend(String phone){
	   	String content = "";
	   	// 获得短信发送模板
		String temp = configMap.get("sms_VipSegistration").toString();
//		System.out.println("temp短信发送模板==="+temp);
		if(temp!=null && !"".equals(temp)){
			content = temp.replace("${phone}", configMap.get("phone")==null ? "":configMap.get("phone").toString())
			.replace("${subject}", configMap.get("subject")==null?"":configMap.get("subject").toString());
//			System.out.println("content==="+content);
		}
		MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//		messageStrategy.sendMsg(phone,content);
		return "";
   }
   
   /***************************** 银行卡绑定后发送短信*******************************/
   
    /**
    * 银行卡绑定后发送短信
    * BpCustMember:账号绑定人员信息
    * svn：songw
    */
	public String bankCardBindingSend(String cardNum,String phone){
	   	//1 取出银行卡的后四位
	   	//2 设置发送信息的内容
	   	String content  = "";
	   	if(cardNum != null && !"".equals(cardNum) && phone!=null && !"".equals(phone)){
	   		String lastFourNum = cardNum.substring(cardNum.length()-4, cardNum.length());
	    	// 获得短信发送模板
	 		String temp = configMap.get("sms_BankCardBinding").toString();
//			System.out.println("temp短信发送模板==="+temp);//打印模板
			if(temp!=null && !"".equals(temp)){
				content = temp.replace("${lastFourNum}", lastFourNum)//拼接发送的内容
				.replace("${phone}", configMap.get("phone")==null ? "":configMap.get("phone").toString())
				.replace("${subject}", configMap.get("subject")==null?"":configMap.get("subject").toString());
//				System.out.println("content==="+content);
			}
			MessageStrategyService messageStrategy= (MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//			messageStrategy.sendMsg(phone,content);
	   	}
		return "";
   }
   /*****************************主动投标后发送短信*******************************/
   
    /**
    * 主动投标后发送短信
    * BpCustMember:账号绑定人员信息
    * svn：songw
    */
	public String initiativeBid(String project,String money,String phone){
	   	//1 取出银行卡的后四位
	   	//2 设置发送信息的内容
	   	String content  = "";
	   	if(project != null && !"".equals(project) &&  money!=null && !"".equals(money) && phone!=null && !"".equals(phone)){
	    	// 获得短信发送模板
	 		String temp = configMap.get("sms_InitiativeBid").toString();
			if(temp!=null && !"".equals(temp)){
				content = temp.replace("${project}", project)
				.replace("${money}", money)
				.replace("${phone}", configMap.get("phone")==null ? "":configMap.get("phone").toString())
				.replace("${subject}", configMap.get("subject")==null?"":configMap.get("subject").toString());
				System.out.println("content==="+content);
			}
	   	}
	   	MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//	   	messageStrategy.sendMsg(phone,content);
		return "";
   }
   
    /*****************************发送短信验证码********************************/
    /**
    * 发送优惠券短信提醒
    */
    public String sendCode(String userTelephone,BigDecimal couponValue,String couponNumber,String endTime,String telphone,String project,String couponType){
	   	String dw="";
		if(couponType.equals("1")||couponType.equals("2")){
			dw="元";
		}else if(couponType.equals("3")){
			dw="%";
		}
	   	String content  = "";
	   	if(userTelephone != null && !"".equals(userTelephone)){
	    	// 获得短信发送模板
	 		String temp = configMap.get("sms_bpCoupons").toString();
			if(temp!=null && !"".equals(temp)){
				content = temp.replace("${parValue}", couponValue.setScale(2)+dw).replace("${couponNumber}", couponNumber).replace("${endTime}",endTime)
				.replace("${phone}", telphone).replace("${subject}", project);
			}
	   	}
	   	MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//	   	messageStrategy.sendMsg(userTelephone,content);
	   	return "";
    }
    /**
     * 单个派发优惠券提醒
     * @param userTelephone
     * @param couponValue
     * @param couponNumber
     * @param endTime
     * @param telphone
     * @param project
     * @param couponType
     * @return
     */
    public String sms_oaMessage(String userTelephone,BigDecimal couponValue,String endTime,String couponType){
	   	String dw="";
		if(couponType.equals("1")||couponType.equals("2")){
			dw="元";
		}else if(couponType.equals("3")){
			dw="%";
		}
	   	String content  = "";
	   	if(userTelephone != null && !"".equals(userTelephone)){
	    	// 获得短信发送模板
	 		String temp = configMap.get("sms_oaMessage").toString();
			if(temp!=null && !"".equals(temp)){
				content = temp.replace("${parValue}", couponValue.setScale(2)+dw).replace("${endTime}",endTime)
				.replace("${phone}", configMap.get("phone").toString()).replace("${subject}", configMap.get("subject").toString());
			}
	   	}
	   	System.out.println("userTelephone="+userTelephone);
	   	System.out.println("content="+content);
	   	MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//	   	messageStrategy.sendMsg(userTelephone,content);
	   	return "";
    }
    /**
    * 优惠券过期提醒
    * @param telephone
    * @param codeStr
    * @return
    */
    public String sendBpcoupons(String userphone,String couponValue,String endTime,String telphone,String project){
	   String content  = "";
	   if(userphone != null && !"".equals(userphone)){
		   // 获得短信发送模板
		   String temp = configMap.get("sms_endTimeCoupons").toString();
		   if(temp!=null && !"".equals(temp)){
				content = temp.replace("${parValue}", couponValue).replace("${endTime}",endTime).replace("${phone}", telphone).replace("${subject}", project);
		   }
	   }
	   System.out.println("content="+content);
	   MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//	   messageStrategy.sendMsg(userphone,content);
	   return "";
   }
   /***************************** 还款催收--发送短信*******************************/
   public String sms_huankuancuishou(String projName,String payintentPeriod,String name,String phone,String money,String  year,String month ,String day){
	   String content = "";
	   // 获得短信发送模板 ${projName}第${payintentPeriod}期
	   String temp = configMap.get("sms_huankuancuishou").toString();
	   if(temp!=null && !"".equals(temp)){
			content = temp
			.replace("${name}", name)
			.replace("${projName}", projName)
			.replace("${payintentPeriod}", payintentPeriod)
			.replace("${year}", year)
			.replace("${month}", month)
			.replace("${day}", day)
			.replace("${money}", money)
			.replace("${phone}", configMap.get("phone")==null ? "":configMap.get("phone").toString())
			.replace("${subject}", configMap.get("subject")==null?"":configMap.get("subject").toString());
	   }
	   System.out.println("content="+content);
	   MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//	   messageStrategy.sendMsg(phone,content);
	   return content;
    }
   /**
    * 项目逾期提醒
    * @param phone
    * @param money
    * @param year
    * @param month
    * @param day
    * @return
    */
   public String sms_overdueProj(String name,String phone,String projName,String payintentPeriod,String money){
	   String content = "";
	   // 获得短信发送模板
	   String temp = configMap.get("sms_overdueProj").toString();
	   if(temp!=null && !"".equals(temp)){
			content = temp
			.replace("${name}", name)
			.replace("${projName}", projName)
			.replace("${payintentPeriod}", payintentPeriod)
			.replace("${money}", money)
			.replace("${phone}", configMap.get("phone")==null ? "":configMap.get("phone").toString())
			.replace("${subject}", configMap.get("subject")==null?"":configMap.get("subject").toString());
	   }
	   System.out.println("content="+content);
	   MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//	   messageStrategy.sendMsg(phone,content);
	   return "";
    }
	/***************************** 项目通过、终止、结束--发送短信*******************************/
	/*审批通过短信：
	您好！你参与的【${projectName}】已通过了【${flowName}】,请知悉;

	项目终止短信：
	您好！你参与的【${projectName}】已终止【${flowName}】,请知悉;

	项目结束短信：
	您好！你参与的【${projectName}】已结束【${flowName}】,请知悉;*/
	public String sms_projectFinish(String phone,String projectName,String projectNumber,String  flowName,String flag){
	   	String content = "";
	   	// 获得短信发送模板
    	String temp = "";
    	if(null!=flag && "1".equals(flag)){
    		temp = configMap.get("sms_flowpass").toString();
    	}else if(null!=flag && "2".equals(flag)){
    		temp = configMap.get("sms_flowfinish").toString();
    	}else{
    		temp = configMap.get("sms_flowstop").toString();
    	}
		if(temp!=null && !"".equals(temp)){
			content = temp
			.replace("${projectName}", projectName)
			.replace("${projNumber}", projectNumber)
			.replace("${flowName}", flowName)
			.replace("${subject}", configMap.get("subject")==null?"":configMap.get("subject").toString());
			System.out.println("content==="+content);
		}
		MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//		messageStrategy.sendMsg(phone,content);
		return "";
    }
			   
    /***************************** 下一节点提醒短信--发送短信*******************************/
    //您好！【${projectName}】流转到【${signalName}】节点,请及时处理";
	public String sms_nextNodeRemind(String phone,String projectName,String  signalName){
	   	String content = "";
	   	// 获得短信发送模板
    	String 	temp = configMap.get("sms_nextnoderemind").toString();
		if(temp!=null && !"".equals(temp)){
			content = temp
			.replace("${projectName}", projectName)
			.replace("${signalName}", signalName)
			.replace("${subject}", configMap.get("subject")==null?"":configMap.get("subject").toString());
		}
		MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
		System.out.println("content="+content);
//		messageStrategy.sendMsg(phone,content);
		return "";
	}
				
    /**
    * 流标发送短信
    * @param telephone
    * @param codeStr
    * @return
    */
	public String sms_flowStandard(String telphone,String truename,String money,String projName){
		System.out.println("telphone="+telphone);
	   String content  = "";
	   if (telphone != null && !"".equals(telphone)) {
			// 获得短信发送模板
			String temp = configMap.get("sms_flowStandard").toString();
			System.out.println("模板内容==="+content);
			if (temp != null && !"".equals(temp)) {
				content = temp.replace("${name}", truename)
			              .replace("${code}", money)
			              .replace("${projName}",projName)
			              .replace("${phone}",configMap.get("phone") == null ? "" : configMap.get("phone").toString())
						  .replace("${subject}",configMap.get("subject") == null ? "" : configMap.get("subject").toString());
			}
	   }
	   System.out.println("发送的内容==="+content);
	   MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//	   messageStrategy.sendMsg(telphone,content);
	   return "";
	}	
   
    /**
    * 回款提醒
    * @param telephone
    * @param codeStr
    * @return
    */
	public String sms_paymentRemind(String telphone,String truename,String RepaymentMoney,String loanInterestMoney,String projName,String payintentPeriod){
	   String content  = "";
	   if (telphone != null && !"".equals(telphone)) {
			// 获得短信发送模板
			String temp = configMap.get("sms_paymentRemind").toString();
			if (temp != null && !"".equals(temp)) {
				content = temp.replace("${name}", truename)
		              .replace("${investInterest}", loanInterestMoney)
                        .replace("${investPrincipal}", RepaymentMoney)
                        .replace("${projName}",projName)
		              .replace("${payintentPeriod}",payintentPeriod)
		              .replace("${phone}",configMap.get("phone") == null ? "" : configMap.get("phone").toString())
					  .replace("${subject}",configMap.get("subject") == null ? "" : configMap.get("subject").toString());
				System.out.println("*****回款成功发送内容=" + content);
			}
	   }
	   MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//	   messageStrategy.sendMsg(telphone,content);
	   return "";
	}
	/**
	 * 提前退出审核提醒
	 * @param telphone
	 * @param name
	 * @param projName
	 * @param projNumber
	 * @param code
	 * @param smsType
	 * @return
	 */
	public String sms_plEarlyRedemption(String telphone,String name,String projName,String projNumber,String code,String smsType){
		// 获得短信发送模板
		String content = configMap.get(smsType).toString();
		System.out.println("telphone="+telphone);
		System.out.println("content="+content);
		if (content != null && !"".equals(content)) {
			if(code!=null&&!code.equals("")){
				content=content.replace("${code}", code);
			}		
			content=content.replace("${name}", name)
		              .replace("${projName}",projName)
		              .replace("${projNumber}",projNumber)
		              .replace("${phone}",configMap.get("phone") == null ? "" : configMap.get("phone").toString())
					  .replace("${subject}",configMap.get("subject") == null ? "" : configMap.get("subject").toString());
		}
		System.out.println("content="+content);
		MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//		messageStrategy.sendMsg(telphone,content);
		return "";
	}

	/**
	 * 放款后起息短信通知
	 * XF
	 *
	 * sms_loan=尊敬的客户${name}您好，您投资编号为“${projNumber}”的项目“${projName}”已起息。如有疑问请致电${phone}【${subject}】
	 */

	public String sms_loanSend(String telphone,String name,String projNumber,String projName ,String type){
		String content = "";
		System.out.println("telphone="+telphone);
		if (telphone != null && !"".equals(telphone)) {
			String temp = null;
			if("loan".equals(type)){
            temp = configMap.get("sms_loan").toString();
			}else {
				temp = configMap.get("sms_collect_period").toString();
			}
            if (temp != null && !"".equals(temp)){
                content= temp.replace("${name}",name)
                        .replace("${projNumber}",projNumber)
                        .replace("${projName}",projName)
                        .replace("${phone}",configMap.get("phone") == null ? "" : configMap.get("phone").toString())
                        .replace("${subject}",configMap.get("subject") == null ? "" : configMap.get("subject").toString());
            }
        }
//        MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//        messageStrategy.sendMsg(telphone,content);
        System.out.println("content="+content);
		sendMessage(telphone, content);
		return "";

	}

	/**
	 *后台增加手动发送线下短信功能
	 * sms_Manual=【展信资本】尊敬的${name}(先生/女士)您好!您于${year}年${mouth}月${day}日在我公司选择的${product}产品出借成功,出借金额为${money}元,合同编号:${id}.感谢您对展信资本的支持与信任!
	 * @auther: XinFang
	 * @date: 2018/6/6 11:35
	 */
	public  boolean   sms_Manual(String name,String year,String mouth,String day ,String product,String money,String id ,String telphone){
		String content = configMap.get("sms_Manual").toString();
		System.out.println("telphone="+telphone);
		if (StringUtils.isNotBlank(content)){
			content = content.replace("${name}",name)
							 .replace("${year}",year)
							 .replace("${mouth}",mouth)
							 .replace("${day}",day)
							 .replace("${product}",product)
							 .replace("${money}",money)
							 .replace("${id}",id);
		}
		System.out.println("content="+content);
//		MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
//		 messageStrategy.sendMsg(telphone, content);
		sendMessage(telphone, content);
		return  true;
	}


	public void sendMessage(String phone,String content) {
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(content)) {
			return;
		}
		if (configMap.containsKey("if_test") && configMap.get("if_test").toString().equals("111111")){
			return;
		}else {
			if (configMap.get("sms_benname").toString().equals("MDSmsManagerService")) {
				SendMsgCommonRequest sn = new SendMsgCommonRequest();
				sn.setThirdPayInterfaceType("ManDaoServiceImpl");
				sn.setThirdPayMethodType("sendMsg");
				sn.setPhone(phone);
				sn.setMsg(content);
				SendMsgCommonResponse response=(SendMsgCommonResponse) CommonSendUtil.sendMsg(sn);
				System.out.println(response.getCode());
				System.out.println(response.getResponseMessage());
				System.out.println(response.getResponseMessage());
			}else {
				MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(configMap.get("sms_benname").toString());
				if(phone.contains(",")) {
					String[] tels = phone.split(",");
					for (String tel : tels) {
						messageStrategy.sendMsg(tel, content);
					}
				}else {
					messageStrategy.sendMsg(phone, content);
				}
			}
		}
	}
}