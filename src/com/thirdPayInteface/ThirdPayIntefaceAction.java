package com.thirdPayInteface;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.thirdPayInterface.ThirdPayConstants;
import com.zhiwei.core.Constants;
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
public class ThirdPayIntefaceAction extends BaseAction {
	
	@Resource
	private OpraterBussinessDataService opraterBussinessDataService;
	
	/**
	 * 页面回调通知
	 * @return
	 */
	public String pageUrl(){
		CommonResponse commonResponse=ThirdPayInterfaceUtil.pageOprate(this.getRequest());
		//获取响应编码，选择消息展示模式（是成功标识，还是失败标识）
		if(commonResponse!=null&&commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
			if(ThirdPayConstants.BUSSINESSTYPE_BINDBANKCAR.equals(commonResponse.getBussinessType())){
				Map<String,String> map = new HashMap<String, String>();
				map.put("requestNo", commonResponse.getRequestNo());
				map.put("bankstatus", "");
				opraterBussinessDataService.bandCard(map);
			}else if(ThirdPayConstants.BUSSINESSTYPE_AUTOBID.equals(commonResponse.getBussinessType())){//自动投标授权
				Map<String,String> map = new HashMap<String, String>();
				map.put("ThirdPayConfigId", commonResponse.getThirdPayConfigId());
				map.put("custermemberId", "");
				opraterBussinessDataService.bidingAuthorization(map);
			}else if(ThirdPayConstants.BUSSINESSTYPE_RECHAGE.equals(commonResponse.getBussinessType())){//充值成功
				Map<String,String> map = new HashMap<String, String>();
				map.put("requestNo", commonResponse.getRequestNo());
				map.put("dealRecordStatus", ObAccountDealInfo.DEAL_STATUS_2.toString());
				opraterBussinessDataService.recharge(map);
			}else if(ThirdPayConstants.BUSSINESSTYPE_BID.equals(commonResponse.getBussinessType())){//投标成功
				Map<String,String> map = new HashMap<String, String>();
				map.put("requestNo", commonResponse.getRequestNo());
				map.put("dealRecordStatus", ObAccountDealInfo.DEAL_STATUS_7.toString());
				opraterBussinessDataService.biding(map);
			}else if(ThirdPayConstants.BUSSINESSTYPE_LOANERREPAYMENT.equals(commonResponse.getBussinessType())){//p2p立即返款
				Map<String,String> map = new HashMap<String, String>();
				map.put("requestNo", commonResponse.getRequestNo());
				opraterBussinessDataService.repayment(map);
			}else if(ThirdPayConstants.BUSSINESSTYPE_WITHDRAW.equals(commonResponse.getBussinessType())){//取现成功
				Map<String,String> map = new HashMap<String, String>();
				map.put("requestNo", commonResponse.getRequestNo());
				map.put("dealRecordStatus", ObAccountDealInfo.DEAL_STATUS_2.toString());
				opraterBussinessDataService.withDraw(map);
			}else if(ThirdPayConstants.BUSSINESSTYPE_CLOSEAUTOBID.equals(commonResponse.getBussinessType())){//关闭自动投标授权
				Map<String,String> map = new HashMap<String, String>();
				map.put("ThirdPayConfigId", commonResponse.getThirdPayConfigId());
				map.put("custermemberId", "");
//				opraterBussinessDataService.closeBidingAuthorization(map);
			}else if(ThirdPayConstants.BUSSINESSTYPE_NOPWREPAYMENT.equals(commonResponse.getBussinessType())){//无密还款授权
				Map<String,String> map = new HashMap<String, String>();
				map.put("ThirdPayConfigId", commonResponse.getThirdPayConfigId());
				map.put("oprateType", "");
//				opraterBussinessDataService.rAuthorization(map);
			}else if(ThirdPayConstants.BUSSINESSTYPE_RELIEVENOPWREPAYMENT.equals(commonResponse.getBussinessType())){//解除无密还款授权
				Map<String,String> map = new HashMap<String, String>();
				map.put("ThirdPayConfigId", commonResponse.getThirdPayConfigId());
				map.put("oprateType", "cancel");
//				opraterBussinessDataService.rAuthorization(map);
			}else if(ThirdPayConstants.BT_CANCELBID.equals(commonResponse.getBussinessType())){//流标
				Map<String,String> map = new HashMap<String, String>();
				map.put("requestNo", commonResponse.getRequestNo());
				map.put("dealRecordStatus", ObAccountDealInfo.DEAL_STATUS_2.toString());
				opraterBussinessDataService.cancelbiding(map);
			}else if(ThirdPayConstants.BUSSINESSTYPE_LOAN.equals(commonResponse.getBussinessType())){//放款
				Map<String,String> map = new HashMap<String, String>();
				map.put("requestNo", commonResponse.getRequestNo());
				map.put("dealRecordStatus", ObAccountDealInfo.DEAL_STATUS_2.toString());
				opraterBussinessDataService.loan(map);
			}
//			webMsgInstance("0", Constants.CODE_SUCCESS, commonResponse.getResponseMsg(),  "", "", "", "", "");
		}else if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_FAILD)){
			if(ThirdPayConstants.BUSSINESSTYPE_BINDBANKCAR.equals(commonResponse.getBussinessType())){
				Map<String,String> map = new HashMap<String, String>();
				map.put("requestNo", commonResponse.getRequestNo());
				map.put("bankstatus", WebBankcard.BINDCARD_STATUS_FAILD);
				opraterBussinessDataService.bandCard(map);
			}else if(ThirdPayConstants.BUSSINESSTYPE_RECHAGE.equals(commonResponse.getBussinessType())){//充值成功
				Map<String,String> map = new HashMap<String, String>();
				map.put("requestNo", commonResponse.getRequestNo());
				map.put("dealRecordStatus", ObAccountDealInfo.DEAL_STATUS_3.toString());
				opraterBussinessDataService.recharge(map);
			}
//			webMsgInstance("0", Constants.CODE_FAILED, commonResponse.getResponseMsg(),  "", "", "", "", "");
		}else{
			//设置 返回提示消息
//			webMsgInstance("0", Constants.CODE_FAILED, commonResponse.getResponseMsg(),  "", "", "", "", "");
		}
//		this.setSuccessResultValue(TemplateConfigUtil.getDynamicConfig(DynamicConfig.MESSAGE).getTemplateFilePath());
		return "freemarker";
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
