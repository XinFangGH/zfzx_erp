package com.zhiwei.credit.action.pay.YeePay;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.Constants;
import com.zhiwei.credit.model.thirdInterface.YeePayReponse;
import com.zhiwei.credit.service.thirdInterface.OpraterBussinessDataService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;
import com.zhiwei.core.web.action.BaseAction;





public class YeePayFrontAction extends BaseAction {
	@Resource
	private YeePayService yeePayService;
	@Resource
	private OpraterBussinessDataService opraterBussinessDataService;
	
	/**
	 * 页面回调函数处理方法
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String webfrontBackValue() throws IOException {
		//验证充值返回值
		System.out.println("测试地址开始");
		Object[]  ret =yeePayService.webfrontBackValue(this.getRequest());
		System.out.println("开始执行页面回调");
		try{
			if(ret[0].toString().equals(Constants.CODE_SUCCESS)){
				YeePayReponse response=(YeePayReponse) ret[1];
				if(response.getCode().equals("1")){//返回交易成功的记录
					String mesg="用户注册";
					if(response.getService().equals("REGISTER")){//注册
						String customerId=response.getRequestNo().split("-")[1];
						Map<String ,String> map=new HashMap<String,String>();
						map.put("custermemberId", customerId);
						map.put("custermemberType", "0");
						map.put("platformUserNo", response.getRequestNo());
						map.put("platFormUserName",response.getRequestNo());
						String[]  bussinessResult=opraterBussinessDataService.regedit(map);
					}else if(response.getService().equals("RECHARGE")){//充值
						mesg="用户充值"+response.getDescription();
						Map<String ,String> map=new HashMap<String,String>();
						map.put("custermemberId", "");
						map.put("requestNo",response.getRequestNo());
						map.put("custmerType", "0");
						map.put("dealRecordStatus","2");
						//String[]  bussinessResult=opraterBussinessDataService.recharge(map);
					}else if(response.getService().equals("WITHDRAW")){//取现
						mesg="用户取现"+response.getDescription();
						Map<String ,String> map=new HashMap<String,String>();
						map.put("custermemberId", "");
						map.put("requestNo",response.getRequestNo());
						map.put("custmerType", "0");
						map.put("dealRecordStatus","2");
						//String[]  bussinessResult=opraterBussinessDataService.withDraw(map);
					}else if(response.getService().equals("BIND_BANK_CARD")){//绑银行卡
						mesg="用户绑定银行卡"+response.getDescription()+"受理";
						Map<String ,String> map=new HashMap<String,String>();
						map.put("custermemberId", "");
						map.put("requestNo",response.getRequestNo());
						map.put("custmerType", "0");
						map.put("bankCardNo","");
						map.put("bankCode","");
						map.put("bankName","");
						map.put("bankstatus","bindCard_status_repare");
						//String[]  bussinessResult=opraterBussinessDataService.bandCard(map);
					}else if(response.getService().equals("TRANSFER")){
						mesg="投资资金冻结成功，等待订单确认";
						/*Map<String ,String> map=new HashMap<String,String>();
						map.put("custermemberId", "");
						map.put("custmerType", "0");
						map.put("requestNo",response.getRequestNo());
						map.put("dealRecordStatus","2");
						String JZWWmethod=this.getRequest().getParameter("JZWWmethod");
						if(JZWWmethod!=null){
							String []  rrett=opraterBussinessDataService.bidMoneyPlan(map);
						}else{
							String[]  bussinessResult=opraterBussinessDataService.biding(map);
							if(bussinessResult[0].contains("successOther")){
								ret[0]=Constants.CODE_FAILED;
								ret[1]=bussinessResult[1];
							}
						}*/
						
					}else if(response.getService().equals("REPAYMENT")){
						mesg="借款人自助还款"+response.getDescription();
						Map<String ,String> map=new HashMap<String,String>();
						map.put("orderNo", "");
						map.put("requestNo",response.getRequestNo());
					//	String[]  bussinessResult=opraterBussinessDataService.repayment(map);
					}else if(response.getService().equals("AUTHORIZE_AUTO_TRANSFER")){
						mesg="自动投标授权"+response.getDescription();
						Map<String ,String> map=new HashMap<String,String>();
						map.put("custermemberId", "");
						map.put("custmerType", "0");
						map.put("requestNo",response.getRequestNo());
						String[]  bussinessResult=opraterBussinessDataService.bidingAuthorization(map);
					}else if(response.getService().equals("AUTHORIZE_AUTO_REPAYMENT")){
					//	mesg="自动还款授权"+response.getDescription();
						mesg="自动还款授权成功！";
						Map<String ,String> map=new HashMap<String,String>();
						map.put("orderNo", "");
						map.put("requestNo",response.getRequestNo());
						String JZWWmethod=this.getRequest().getParameter("JZWWmethod");
						if(JZWWmethod!=null){
							String []  rrett=opraterBussinessDataService.repaymentAuthorizationMoneyPlan(map);
						}else{
							String[]  bussinessResult=opraterBussinessDataService.repaymentAuthorization(map);
						}
						
					}else if(response.getService().equals("UNBIND_BANK_CARD")){
						mesg="取消绑卡"+response.getDescription();
						Map<String ,String> map=new HashMap<String,String>();
						map.put("custermemberId", "");
						map.put("custmerType", "0");
						map.put("orderNo", "");
						map.put("requestNo",response.getRequestNo().split("-")[0]);
						String[]  bussinessResult=opraterBussinessDataService.cancelBindBank(map);
					}else if(response.getService().equals("TRANSFER_CLAIMS")){
						mesg="债权交易"+response.getDescription();
						Map<String ,String> map=new HashMap<String,String>();
						map.put("requestNo",response.getRequestNo());
						map.put("dealstatus", "2");
						//String[]  bussinessResult=opraterBussinessDataService.doObligationDeal(map);
					} else if(response.getService().equals("TRANSACTION")){//通用转账授权接口
						mesg="通用转账授权"+response.getDescription();
						Map<String ,String> map=new HashMap<String,String>();
						map.put("requestNo",response.getRequestNo());
						map.put("dealstatus", "2");
						//String[]  bussinessResult=opraterBussinessDataService.doObligationPublish(map);
					}else{
						
					}
					ret[0]=Constants.CODE_SUCCESS;
					ret[1]=mesg;
				}else{
					String mesg="用户注册";
					if(response.getService().equals("REGISTER")){//注册
						
					}else if(response.getService().equals("RECHARGE")){//充值
						mesg="用户充值"+response.getDescription();
						Map<String ,String> map=new HashMap<String,String>();
						map.put("custermemberId", "");
						map.put("requestNo",response.getRequestNo());
						map.put("custmerType", "0");
						map.put("dealRecordStatus","3");
					//	String[]  bussinessResult=opraterBussinessDataService.recharge(map);
					}else if(response.getService().equals("WITHDRAW")){//取现
						mesg="用户取现"+response.getDescription();
						Map<String ,String> map=new HashMap<String,String>();
						map.put("custermemberId", "");
						map.put("requestNo",response.getRequestNo());
						map.put("custmerType", "0");
						map.put("dealRecordStatus","3");
					//	String[]  bussinessResult=opraterBussinessDataService.recharge(map);
					}else if(response.getService().equals("BIND_BANK_CARD")){//绑银行卡
						mesg="用户绑定银行卡"+response.getDescription();
						Map<String ,String> map=new HashMap<String,String>();
						map.put("custermemberId", "");
						map.put("requestNo",response.getRequestNo());
						map.put("custmerType", "0");
						map.put("bankCardNo","");
						map.put("bankCode","");
						map.put("bankName","");
						map.put("bankstatus","bindCard_status_faild");
						String[]  bussinessResult=opraterBussinessDataService.bandCard(map);
					}else if(response.getService().equals("TRANSFER")){
						mesg="投资资金冻结失败，等待订单取消，失败原因："+response.getDescription();
						/*Map<String ,String> map=new HashMap<String,String>();
						map.put("custermemberId", "");
						map.put("custmerType", "0");
						map.put("requestNo",response.getRequestNo());
						map.put("dealRecordStatus","3");*/
						//String[]  bussinessResult=opraterBussinessDataService.biding(map);
					}else if(response.getService().equals("REPAYMENT")){
						mesg="借款人自助还款"+response.getDescription();
					}else if(response.getService().equals("AUTHORIZE_AUTO_TRANSFER")){
						mesg="自动投标授权"+response.getDescription();
					}else if(response.getService().equals("AUTHORIZE_AUTO_REPAYMENT")){
						mesg="自动还款授权"+response.getDescription();
					}else if(response.getService().equals("TRANSFER_CLAIMS")){
						mesg="债权交易"+response.getDescription();
						Map<String ,String> map=new HashMap<String,String>();
						map.put("requestNo",response.getRequestNo());
						map.put("dealstatus", "0");
						//String[]  bussinessResult=opraterBussinessDataService.doObligationDeal(map);
					} else if(response.getService().equals("TRANSACTION")){//通用转账授权接口
						mesg="通用转账授权"+response.getDescription();
						Map<String ,String> map=new HashMap<String,String>();
						map.put("requestNo",response.getRequestNo());
						map.put("dealstatus", "0");
						//String[]  bussinessResult=opraterBussinessDataService.doObligationPublish(map);
					}else{
						
					}
					ret[0]=Constants.CODE_FAILED;
					ret[1]=mesg+"失败：易宝支付报错信息-"+response.getDescription();
				}
				
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="易宝验签失败";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			ret[0]=Constants.CODE_FAILED;
			ret[1]="出错了，请联系管理员";
			
		}		
		setJsonString("{success:true,message:'"+ret[1]+"'}");
		return SUCCESS;
	}
}