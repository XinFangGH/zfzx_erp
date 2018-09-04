package com.zhiwei.credit.service.pay;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletResponse;

import com.zhiwei.credit.model.pay.ResultBean;
import com.zhiwei.credit.model.pay.MoneyMoreMore;

public interface IPayService {
	/**
	 * 转账接口
	 * @param moneyMoreMore 钱多多vo
	 */
	public String transfer(MoneyMoreMore moneyMoreMore,String basePath);
	/**
	 * 查询接口
	 * moneymoremoreId 查询账户的乾多多标识
	 * type 1.托管账户 2.自有账户

	 * @param moneyMoreMore 钱多多vo
	 */
	public String[] balanceQuery(String moneymoremoreId,String type);
	
	public String balanceQueryMoneys(String moneymoremoreId,String type);
	
	
	/**
	 * 绑定
	 * @param moneyMoreMore
	 * @param basePath
	 * @return
	 */
	public String bind(MoneyMoreMore moneyMoreMore, String basePath);
		/**
	 * 注册并绑定 半自动 返回页面 
	 */
	public void registerAndBind(MoneyMoreMore moneyMoreMore, String basePath,HttpServletResponse response);
	/**
	 * 提现接口
	 * @param moneyMoreMore
	 * @param basePath
	 * @return
	 */
	public String withdraws(MoneyMoreMore moneyMoreMore, String basePath);
	/**
	 * 充值接口
	 * @param moneyMoreMore
	 * @param basePath
	 * @return
	 */
	public String recharge(MoneyMoreMore moneyMoreMore, String basePath);
	/**
	 * 转账list
	 * @param moneyMoreMore
	 * @return
	 */
	public MoneyMoreMore loanJsonList(MoneyMoreMore moneyMoreMore);
	/**
	 * 二次转账list
	 * money  收费基数  =投资金额 
	 * reePercent  收费比例
	 * @return
	 */
	public String secondaryJsonList(BigDecimal money,Double reePercent);
	/**
	 * 资金释放接口
	 * @return
	 */
	public void moneyReaease(MoneyMoreMore moneyMoreMore,String basePath,HttpServletResponse response);
	/**
	 * 放款审核接口 
	 * @param moneyMoreMore
	 * @param basePath
	 * @return
	 */
	public ResultBean transferaudit(MoneyMoreMore moneyMoreMore,String basePath,HttpServletResponse response);
}
