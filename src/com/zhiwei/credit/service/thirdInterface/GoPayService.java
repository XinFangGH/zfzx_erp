package com.zhiwei.credit.service.thirdInterface;

import javax.servlet.http.HttpServletResponse;

import com.zhiwei.credit.model.thirdInterface.GoZhiFuVO;

public interface GoPayService {
	/**
	 * 充值 
	 * 
	 * @param respose
	 * @param bankCode
	 *            银行代码
	 * @param buyerContact
	 *            买方联系方式
	 * @param buyerName
	 *            买方姓名
	 * @param goodsName
	 *            商品名称
	 * @param goodsDetail
	 *            商品简介
	 * @param remark1 //这里 填写 平台用户id 用于 返回信息操作
	 * @param remark2
	 * @param tranAmt
	 *            交易金额
	 * @param ip
	 *            用户IP
	 * @param userType
	 *            用户类型
	 * @param charSet
	 *            编码格式
	 * @param timeOut
	 *            超时
	 * @param  basePath 访问路劲
	 * orderNum 订单号
	 * @return
	 */
	
	public String[] recharge(HttpServletResponse respose, String bankCode,
			String buyerContact, String buyerName, String goodsName,
			String goodsDetail, String remark1, String remark2, String tranAmt,
			String ip, String userType, String orderNum,String charSet, String timeOut,String basePath);

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
	
	/**
	 * xml 转换为实体
	 * @param xml
	 * @return
	 */
	public GoZhiFuVO strToDocument(String xml);

}
