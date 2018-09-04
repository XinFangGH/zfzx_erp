package com.zhiwei.credit.service.thirdInterface;

import javax.servlet.http.HttpServletResponse;

import com.zhiwei.credit.model.p2p.BpCustMember;



public interface HuiFuService {
	
	/**
	 * 充值
	 * @param respose
	 * * @param mem 投资客户
	 * regType 类型 NetSave
	 * @param ordId 订单号
	 * @param ordDate 订单日期
	 * @param dcFlag  借贷记标记  D：借记  C：贷记
	 * @param amt 金额
	 * bankCode 银行代码 如 ICBC
	 * @param basePath
	 * @return
	 */
	public String[] recharge(HttpServletResponse respose,BpCustMember mem,String regType,String ordId,String ordDate,String dcFlag,String amt,String bankCode, String basePath);
	/**
	 * 用户开户
	 * @param mem 投资客户
	 * @param regType 注册类型 后台 用户 开户  BgRegister  前台 用户 开户 UserRegister 目前使用前台用户开户
	 * @param  basePath 访问路径
	 * @return
	 */
	public String[] register(HttpServletResponse respose,BpCustMember mem, String regType,String basePath);
	/**
	 * 用户登录
	 * @param mem 投资客户
	 * @param regType 注册类型 后台 用户 开户  UserLogin
	 * @param  basePath 访问路径
	 * @return
	 */
	public String[] loginToHuiFu(HttpServletResponse respose,BpCustMember mem, String regType,String basePath);
	/**
	 * 绑定银行卡
	 * @param respose
	 * @param mem
	 * @param basePath
	 * @return
	 */
	public String[] bindBankCard(HttpServletResponse respose,BpCustMember mem,String regType,String basePath);
	
	/**
	 * 自动投标
	 * @param respose
	 * @param mem 投资客户
	  * @param regType InitiativeTender 手动投标  AutoTender  自动投标
	 * @param ordId 订单号
	 * @param ordDate 订单日期
	 * @param transAmt 投标金额
	 * @param borrowerDetails借款人信息
	 * @param isFreeze 是否 冻结 Y冻结 N不冻结
	 * @param freezeOrdId 冻结订单号
	 * @param reqExt 
	 * @param basePath
	 * @return
	 */
	public String[] tenderHuiFu(HttpServletResponse respose,BpCustMember mem, String regType,String ordId,String ordDate,String transAmt,String borrowerDetails,String isFreeze,String freezeOrdId,String reqExt,String basePath);

	/**
	 * 余额 查询接口
	 * @param mem
	 * @param regType QueryBalanceBg
	 * @return
	 *  ret[0]=huifu.getAvlBal(); //可用余额
	    ret[1]=huifu.getAcctBal(); //账户余额 账户资金余额
		ret[2]=huifu.getFrzBal(); //冻结金额
	 */
	public String[] balanceQuery(HttpServletResponse respose,BpCustMember mem,String regType);
	/**
	 * 解冻接口
	 * @param respose
	 * @param mem
	 * @param regType
	 * @param ordId
	 * @param ordDate
	 * @param trxid 解冻订单号 在 冻结时候 汇付返回的 唯一编号 保存在 日志表里 备注 1 字段里
	 * @return
	 */
	public String[] unFreeze(HttpServletResponse respose,BpCustMember mem,String regType,String ordId,String ordDate,String trxid,String basePath);
	
	/**
	 * 放款接口 2.0 version 传 20
	 * @param respose
	 * @param regType
	 * @param ordId 订单号
	 * @param ordDate 订单时间
	 * @param OutCustId 付款人账号 id
	 * @param TransAmt 付款金额
	 * @param Fee  手续费
	 * @param SubOrdId 参考 fonthuifuAction 实体
	 * @param SubOrdDate 参考 fonthuifuAction 实体
	 * @param InCustId 收款人账号
	 * @param DivDetails 分账信息
	 * @param FeeObjFlag 续 费 收 取 对象标志  I/O 
	 * @param IsDefault 是否默认 Y默认  N不默认
	 * @param IsUnFreeze 
	 * @param UnFreezeOrdId
	 * @param FreezeTrxId
	 * @param basePath
	 * @return
	 */
	public String[] Loans(HttpServletResponse respose,String regType,String ordId,String ordDate,String OutCustId,String TransAmt,String Fee,String SubOrdId ,String SubOrdDate ,String InCustId ,String DivDetails ,String FeeObjFlag ,String IsDefault ,String IsUnFreeze ,String 
UnFreezeOrdId ,String FreezeTrxId,String reqExt,String basePath);
	/**
	 * 数据解密签名验证
	 * @param msgData 被签名的数据
	 * @param chkValue 要验证的签名
	 * @return true 表示验证成 false 失败
	 */
	public boolean DecodSign(String msgData,String chkValue);
	/**
	 * 提现
	 * @param respose
	 * @param recvBankAcctName 开户名称
	 * @param recvBankAcctNum  账号
	 * @param recvBankBranchName 网点名称
	 * @param recvBankCity 收款方银行所在城市
	 * @param recvBankName 银行名称 
	 * @param recvBankProvince 所在省份
	 * @param tranAmt 金额
	 * @param description 备注
	 * @param charSet 编码格式
	 * @param timeOut 超时时间
	 * @return
	 */
	
	public String withdraw(HttpServletResponse respose,
			String recvBankAcctName, String recvBankAcctNum,
			String recvBankBranchName, String recvBankCity,
			String recvBankName, String recvBankProvince,
			String tranAmt, String description, String charSet, String timeOut);

}
