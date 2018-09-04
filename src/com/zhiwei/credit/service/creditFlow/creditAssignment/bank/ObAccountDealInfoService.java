package com.zhiwei.credit.service.creditFlow.creditAssignment.bank;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;

import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.zhiwei.core.Constants;
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObObligationInvestInfo;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateFormStatisticsDetail;

/**
 * 
 * @author 
 *
 */
@WebService
public interface ObAccountDealInfoService extends BaseService<ObAccountDealInfo>{
	//查询已有的取现审核单
	public List<ObAccountDealInfo> getDealList(String investPersonName,
			String transferDate,String flag);
//确认充值查询列表
	public List<ObAccountDealInfo> getRechargeDealList(String investPersonName,
			String seniorValidationRechargeStatus,
			String rechargeConfirmStatus, String string,String rechargeLevel);
	public String getCreateNameByCreateId(Long createId);
	//根据投资人id  投资人债权id  以及账户交易类型
	public ObAccountDealInfo getDealInfo(Long investMentPersonId, Long id,String flag);
	//(投资人添加债权和撤销债权以及债权产品匹配投资人时用的方法)主要作用是：删除对应的一条账户交易信息以及更改账户总额
	public void changeAccountDealInfo(ObObligationInvestInfo ob, int i);
	
	/**
	 * 交易 记录  充值   提现  投标  还款
	 * @param accountId 虚拟账户id
	 * @param money  交易金额
	 * @param transferType 交易类型    1表示充值，2表示取现,3收益，4投资，
	 * @param dealType  交易类型。1现金交易。2银行卡交易
	 * @param rechargeLevel  充值金额确认级别  1表示只需要充值确认，2表示需要高级确认
	 * @param investPersonName 投资人姓名
	 * @param investPersonId 投资人id
	 * @param rechargeConfirmStatus  充值确认 ,true 表示确认  false  表示没有确认
	 * @param seniorValidationRechargeStatus  充值高级确认 ,true 表示确认  false  表示没有确认
	 * @return
	 */
	@WebMethod
	public String saveRechargeDealInfo(String accountId, String money ,String transferType,String dealType,String rechargeLevel,String investPersonName,String investPersonId,String rechargeConfirmStatus,String seniorValidationRechargeStatus);
	
/*	*/
	
	/**
	 * 操作系统账户调用方法
	 * Map<String,Object> map =new HashMap<String,Object>();
	 * map.put("investPersonId",Long);//投资人Id
	 * map.put("investPersonType",Short);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
	 * map.put("transferType",Short);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
	 * map.put("money",BigDecimal);//交易金额
	 * map.put("dealDirection",short);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
	 * map.put("dealType",Short);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）
	 * map.put("recordNumber",String);//交易流水号
	 * map.put("fianceStatus",short);//资金明细状态：0资金冻结，1交易生效，null 表示为 交易记录
	 * map.put("dealStatus",short);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
	 * @return String[0]:操作代码  Constants.CODE_SUCCESS =8888账户操作成功，Constants.CODE_FAILED=0000  账户操作失败
	 *         String[1]:操作信息   操作成功 ，操作失败
	 *  add by liny 2014-5-6   
	 *  update by linyan  2014-10-10
	 */
	
	public String[] operateAcountInfo(Map<String,Object> map);
	
	/**
	 * 将账户交易记录更新，并且跟新系统账户金额
	 * Map<String,Object> map =new HashMap<String,Object>();
	 * map.put("investPersonId",Long);//投资人Id
	 * map.put("investPersonType",Short);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
	 * map.put("transferType",Short);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
	 * map.put("money",BigDecimal);//交易金额
	 * map.put("dealDirection",short);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
	 * map.put("recordNumber",String);//交易流水号
	 * map.put("DealInfoId",Long);//交易记录Id  没有值默认传null
	 * map.put("dealStatus",short);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
	 * @return String[0]:操作代码  Constants.CODE_SUCCESS =8888账户操作成功，Constants.CODE_FAILED=0000  账户操作失败
	 *         String[1]:操作信息   操作成功 ，操作失败
	 *  add by liny 2014-5-6   
	 *  update by linyan  2014-10-10
	 * @return
	 */
	
	public String[] updateAcountInfo(Map<String,Object> map);
	/**
	 * 查询系统账户交易信息调用方法
	 * @param accountId  系统账户id
	 * @param transferType  交易类型  1表示充值，2表示取现,3收益，4投资，5还本
	 * @param dealRecordStatus  系统账户交易明细记录状态：1审批2成功3失败4复核5办理
	 * @param rechargeConfirmStatus 系统账户交易明细记录是否生效，或者冻结
	 * @param request
	 * @return
	 */
	@WebMethod
	public List<ObAccountDealInfo> queyAccountInfoRecord(Long accountId,String transferType,Short dealRecordStatus, Short rechargeConfirmStatus,HttpServletRequest request,Integer start, Integer limit);
	/**
	 * 线下客户充值操作接口
	 * @param obAccountDealInfo
	 * @return
	 */
	
	public String[] saveAcountInfo(ObAccountDealInfo obAccountDealInfo);
	/**
	 * 根据系统账户信息以及查询条件来查询系统账户成功和没有冻结的交易信息
	 * @param accountId
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ObAccountDealInfo> getaccountListQuery(String accountId,HttpServletRequest request, Integer start, Integer limit);
	/**
	 * 查询线上提现审核
	 * @return
	 */
	public List<ObAccountDealInfo> getWithdrawCheck(HttpServletRequest request, Integer start, Integer limit);
	
	public String[] newOpreaterDealInfo(String investPersonId,String transferType, String money,String dealType,String resource,String operateType,String accountType,String recordNumber,String bankId);
	
	/**
	 * 还款后自动更新 交易记录  和款项台帐
	 * @param OutCustId 付款人
	 * @param InCustId 收款人
	 * @param TransAmt 金额
	 * @param OrdId 订单号
	 * @param Fee 服务费  
	 * @param Remark1 备注 主要用于获取 款项台帐id 字符串 例如   "fid:460:1.61_fid:461:1.61_fid:459:9.67_bfid:247:0.00";
	 * @param  feeType 手续费收取方  I 收入方 O 支出方
	 * @return
	 */
	public String[] updateAccountByRepayment(String OutCustId,String InCustId,String TransAmt,String OrdId,String Fee,String Remark1,String feeType);

	/**
	 * 根据交易编号和交易客户类型  以及交易金额查询到交易记录
	 * @param orderNo
	 * @return
	 */
	public ObAccountDealInfo getByOrderNumber(String orderNo,String totalFee, String transferType,String type0);
	/**
	 * 根据第三方流水号查询记录
	 * @param thirdNumber
	 * @return
	 */
	public ObAccountDealInfo getBythirdNumber(String thirdNumber);
	/**
	 * 查出来平台每个账户的自己明细
	 */
	public List<PlateFormStatisticsDetail> getOneTypePlateFormStaticsDatail(HttpServletRequest request, Integer start, Integer limit);
	
	
	/**
	 * 合作机构账户明细查询
	 * @param map
	 * @return
	 */
	public List<ObAccountDealInfo> cooperationAccountInfoList(Map<String, String> map);
	/**
	 * 合作机构账户明细查询--总记录数
	 * @param map
	 * @return
	 */
	public Long cooperationAccountInfoListCount(Map<String, String> map);
	public BigDecimal sumPersonMoney(String transferType,Long investPersonId,String startTime,String endTime);
	public BigDecimal getAccountIdRechargeMoney(Long accoutId);
	/**
	 * 查询系统所有发生交易的用户
	 * @param day
	 */
	public List<ObAccountDealInfo> getAllThirdDealInfo(String day);
	
	public BigDecimal sumMoney(String transferType,Long[] investPersonIds,String startTime,String endTime);
	
	/**
	 * 根据交易类型，交易状态查询记录
	 * @param transferType
	 * @param dealRecordStatus
	 * @return
	 */
	public List<ObAccountDealInfo> getListByTransFerTypeAndState(String transferType,String dealRecordStatus,String searchHours);

	/**
	 * 定时器自动处理募集期利润没有发放完毕的客户
	 * @auther: XinFang
	 * @date: 2018/6/13 10:27
	 */
	public void  raiseinterestNotMoney();
	/**
	 *查询三方请求明细
	 *
	 * @auther: XinFang
	 * @date: 2018/7/23 14:12
	 */
    List<ThirdPayRecord> getThirdPayRecord(String bpName, String startDate,String transferType);
}


