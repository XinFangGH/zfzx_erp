package com.zhiwei.credit.model.creditFlow.creditAssignment.bank;


/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * ObAccountDealInfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ObAccountDealInfo extends com.zhiwei.core.model.BaseModel {

	/**
	 * 交易类型:对应transferType字段
	 */
	public static final String T_RECHARGE="1";//充值
    public static final String T_ENCHASHMENT="2";//取现
    public static final String T_PROFIT="3";//投资收益
    public static final String T_INVEST="4";//投资
    public static final String T_PRINCIALBACK="5";//本金收回
    public static final String T_LOANINCOME="6";//取现手续费
    public static final String T_LOANPAY="7";//借款人还本付息
    public static final String T_INMONEY="8";//借款人借款入账
    public static final String T_N_REPAYMENT="9";//剩余未还本息
    public static final String T_REDENVELOPE="10";//系统红包
    public static final String T_TRANSFER="11";//账户转账
	public static final String T_TRANSFERPAY="12";//账户转账支出
    public static final String T_PLATEFORM_LOANFEE="13";//平台收取的随息管理费用
    public static final String T_PLATEFORM_ONETIMEFEE="14";//平台收取的一次性费用
    public static final String T_PLATEFORM_RISKGURANTEE="15";//风险保证金
    public static final String T_PLATEFORM_GURANTEEFEE="16";//担保费用
    public static final String T_PLATEFORM_MEMBERSHIPFEE="17";//会费
    public static final String T_PLATEFORM_VOUCHER="18";//代金券
    public static final String T_PLATEFORM_CHANGECORRECT="19";//调账
    public static final String T_PLATEFORM_OTHERFEE="20";//其他费用
    public static final String T_PLATEFORM_OBLIGATIONDEAL="21";//债权交易手续费
    public static final String T_BINDCARD_FEE="22";//绑卡手续费
    public static final String T_RECHARGE_FEE="23";//充值手续费
    public static final String T_BIDRETURN_MONEY="24";//流标返款
    public static final String T_BIDRETURN_RETURNRATIO="25";//返现奖励
    public static final String T_BIDRETURN_ADDRATE="26";//普通加息奖励
    public static final String T_BIDRETURN_RATE27="27";//返息奖励
    public static final String T_BIDRETURN_RATE28="28";//返息现本金奖励
    public static final String T_BIDRETURN_RATE29="29";//返息现利息奖励
    public static final String T_BIDRETURN_RATE30="30";//加息券奖励
    public static final String T_BIDRETURN_RATE31="31";//募集期奖励
    public static final String T_JOIN="32";//加入费用
    public static final String T_PLBID_COMPENSATORY="33";//代偿还款
    public static final String T_EXPERIENCE="40";//体验标收益
    public static final String T_PENALTYINTEREST = "41";//补偿息奖励
    public static final String T_BREACH = "42";//提前赎回违约金
    /**
     * 交易方式:对应dealType字段
     */
    public static final Short CASHDEAL=Short.valueOf("1");//1现金交易
    public static final Short BANKDEAL=Short.valueOf("2");//2银行卡交易
    public static final Short THIRDPAYDEAL=Short.valueOf("3");//3第三方支付交易

    /**
     * 交易方向：没有数据库表对应
     */
    public static  final Short DIRECTION_PAY =Short.valueOf("1");//1针对系统账户是支出
    public static  final Short DIRECTION_INCOME =Short.valueOf("2");//2针对系统账户是收入
    /**
     * 资金交易记录状态:对应字段dealRecordStatus
     */
    public static  final Short DEAL_STATUS_1 =Short.valueOf("1");//1：表示取现，充值一次审批状态
    public static  final Short DEAL_STATUS_2 =Short.valueOf("2");//2：表示取现审批通过状态，充值成功状态或者交易成功记录
    public static  final Short DEAL_STATUS_3 =Short.valueOf("3");//3：表示取现审批否决状态，充值失败状态或者交易失败记录
    public static  final Short DEAL_STATUS_4 =Short.valueOf("4");//4：表示取现审批复核状态
    public static  final Short DEAL_STATUS_5 =Short.valueOf("5");//5：表示取现办理阶段
    public static  final Short DEAL_STATUS_6 =Short.valueOf("6");//6：交易异常
    public static  final Short DEAL_STATUS_7 =Short.valueOf("7");//7：资金冻结
    public static  final Short DEAL_STATUS_8 =Short.valueOf("8");//8：资金解冻
    
    protected Long id;//系统账户交易明细表主键Id
    /**
     * 系统账户表主键id
     */
	protected Long accountId;
	/**
	 * 收入金额
	 */
	protected java.math.BigDecimal incomMoney=new BigDecimal(0);
	/**
	 * 支出金额
	 */
	protected java.math.BigDecimal payMoney=new BigDecimal(0);
	/**
	 * 发生该笔交易后账户的余额
	 */
	protected java.math.BigDecimal currentMoney=new BigDecimal(0);
	protected String transferType;//交易类型    参见以上常量字段
	/**
	 * 发生交易的线下门店Id
	 */
	protected Long shopId;
	/**
	 * 发生线下交易的门店名称
	 */
	protected String shopName;
	/**
	 * 创建交易记录的人员Id
	 */
	protected Long createId;
	/**
	 * //交易方式。参见以上常量字段
	 */
	protected Short dealType;
	/**
	 * 创建这条交易记录的时间（默认系统时间）
	 */
	protected java.util.Date createDate;
	/**
	 * 交易时间（实际发生转账时间）
	 */
	protected java.util.Date transferDate;
	/**
	 * 账户所属人（单位）的姓名
	 */
	protected String  investPersonName;
	protected Long  investPersonId;//账户所属人（单位）所在表的主键Id
	protected Short investPersonType;//账户所属人（单位）的类型（目前是线下投资人账户，p2p投资客户，平台账户类型）
	
	
	public static final Short UNFREEZY=Short.valueOf("0");//0金额冻结
    public static final Short ISAVAILABLE=Short.valueOf("1");//0金额生效
    
    public static  final Short ISREADTHIRDRECORD =Short.valueOf("1");//1需要读取第三方数据
    public static  final Short UNREADTHIRDRECORD =Short.valueOf("2");//2不需要读取第三方数据
	
    protected String loginname;
    protected String telphone;
    protected String cardcode;
    protected String transferTypeNmae;
    protected String dealRecordStatusName;
    
	/**
	 * 不与数据库字段相关联，交易类型名称
	 */
    protected String typeName;

	public String getCardcode() {
		return cardcode;
	}

	public void setCardcode(String cardcode) {
		this.cardcode = cardcode;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTransferTypeNmae() {
		return transferTypeNmae;
	}

	public void setTransferTypeNmae(String transferTypeNmae) {
		this.transferTypeNmae = transferTypeNmae;
	}

	public String getDealRecordStatusName() {
		return dealRecordStatusName;
	}

	public void setDealRecordStatusName(String dealRecordStatusName) {
		this.dealRecordStatusName = dealRecordStatusName;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	/**
     * 投资项目表的Id
     */
	protected Long investObligationInfoId;
	/**
	 * 投资项目名称
	 */
	protected String obligationInfoName;
	/**
	 * 投资项目编号
	 */
	protected String obligationNumber;
	/**
	 * rechargeLevel用来表示充值交易记录的来源
	 * 1：表示来源于第三方机构划扣
	 * 2：表示来源于系统账户自维护的状况  --手动充值
	 * 3: 表示系统自动充值
	 */
	protected Short rechargeLevel;

	/**
	 * 用来记录取现流程当前处于什么状态；充值状态
	 * 1：表示取现，充值一次审批状态
	 * 2：表示取现审批通过状态，充值成功状态或者交易成功记录
	 * 3：表示取现审批否决状态，充值失败状态或者交易失败记录
	 * 4：表示取现审批复核状态
	 * 5：表示取现办理阶段
	 */
	protected  Short dealRecordStatus;
	/**
	 * process_run  主键id
	 */
	protected Long runId;
	/**
	 * 交易编号 （交易流水号）
	 */
	protected String recordNumber;
	
	/**
	 * 取现银行卡账号
	 */
	private Long bankId;
	/**
	 * 备注字段：交易状态描述
	 */
	protected String  msg;
	/**
	 * 资金账户发生转账交易对方账户名
	 */
	protected String transferAccountName;
	/**
	 *  资金账户发生转账交易对方账号
	 */
	protected String transferAccountNumber;
	
	/**
	 * 第三方支付交易的流水号
	 */
	protected String thirdPayRecordNumber;
	
	/**
	 * 产生了费用需要平台普通资金账户支付和垫资的账户账号
	 */
	protected String  createFeeAccountNumber;
	
	/**
	 * 产生了费用需要平台普通资金账户支付和垫资的账户姓名
	 */
	protected String  createFeeAccountName;
	
	/**
	 * 线下投资客户取现的时候资金就应处于资金冻结状态(dealRecordStatus=7),但是原有的流程最后一个节点又将
	 * dealRecordStatus修改成了(4取现复审)或者5(取现办理)，而且只有取现办理功能处理了之后才真正的解冻，所以
	 * 之前资金应一直处于冻结状态，遂增加一个新的状态用于表示不同流程节点下的任务 
	 * 1取现审核
	 * 4取现复审
	 * 5取现办理
	 */
	protected  Short recordStatus;

	//不与数据库映射字段
	private String transferTypeName;//交易类型的名称
	private String accountName;//系统账户名称
	private String accountNumber;//系统账户账号
	private String accountType;//系统账户的类型
	private String truename;//查询bp_cust_member真是姓名作为账户名称
	
	 
	protected String   createName;//办理取现的操作人员姓名
    private String bankName;//银行名称
    private String bankNum;//贷款卡卡号
	private Integer enterpriseBankId;
	private Short openType;//开户类型
    private String khname;//开户名称
    private String areaId;//地区Id
    private String areaName;//地区名称
   
	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getCreateFeeAccountNumber() {
		return createFeeAccountNumber;
	}

	public void setCreateFeeAccountNumber(String createFeeAccountNumber) {
		this.createFeeAccountNumber = createFeeAccountNumber;
	}

	public String getCreateFeeAccountName() {
		return createFeeAccountName;
	}

	public void setCreateFeeAccountName(String createFeeAccountName) {
		this.createFeeAccountName = createFeeAccountName;
	}

	public String getThirdPayRecordNumber() {
		return thirdPayRecordNumber;
	}

	public void setThirdPayRecordNumber(String thirdPayRecordNumber) {
		this.thirdPayRecordNumber = thirdPayRecordNumber;
	}
    
	public String getTransferAccountName() {
		return transferAccountName;
	}

	public void setTransferAccountName(String transferAccountName) {
		this.transferAccountName = transferAccountName;
	}

	public String getTransferAccountNumber() {
		return transferAccountNumber;
	}

	public void setTransferAccountNumber(String transferAccountNumber) {
		this.transferAccountNumber = transferAccountNumber;
	}



    
	public Short getInvestPersonType() {
		return investPersonType;
	}

	public void setInvestPersonType(Short investPersonType) {
		this.investPersonType = investPersonType;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRecordNumber() {
		return recordNumber;
	}
	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}
	public Long getRunId() {
		return runId;
	}
	public void setRunId(Long runId) {
		this.runId = runId;
	}
	public Short getDealRecordStatus() {
		return dealRecordStatus;
	}

	public void setDealRecordStatus(Short dealRecordStatus) {
		this.dealRecordStatus = dealRecordStatus;
	}
    
	public Long getInvestObligationInfoId() {
		return investObligationInfoId;
	}

	public void setInvestObligationInfoId(Long investObligationInfoId) {
		this.investObligationInfoId = investObligationInfoId;
	}

	public Long getInvestPersonId() {
		return investPersonId;
	}

	public void setInvestPersonId(Long investPersonId) {
		this.investPersonId = investPersonId;
	}
    
	public String getInvestPersonName() {
		return investPersonName;
	}

	public void setInvestPersonName(String investPersonName) {
		this.investPersonName = investPersonName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Short getRechargeLevel() {
		return rechargeLevel;
	}

	public void setRechargeLevel(Short rechargeLevel) {
		this.rechargeLevel = rechargeLevel;
	}

	/**
	 * Default Empty Constructor for class ObAccountDealInfo
	 */
	public ObAccountDealInfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ObAccountDealInfo
	 */
	public ObAccountDealInfo (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="accountId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getAccountId() {
		return this.accountId;
	}
	
	/**
	 * Set the accountId
	 */	
	public void setAccountId(Long aValue) {
		this.accountId = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="incomMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getIncomMoney() {
		return this.incomMoney;
	}
	
	/**
	 * Set the incomMoney
	 */	
	public void setIncomMoney(java.math.BigDecimal aValue) {
		this.incomMoney = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="payMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPayMoney() {
		return this.payMoney;
	}
	
	/**
	 * Set the payMoney
	 */	
	public void setPayMoney(java.math.BigDecimal aValue) {
		this.payMoney = aValue;
	}	

	/**
	 * 转账类型 :1表示充值，2表示取现	 * @return Short
	 * @hibernate.property column="transferType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public String getTransferType() {
		return this.transferType;
	}
	
	/**
	 * Set the transferType
	 */	
	public void setTransferType(String aValue) {
		this.transferType = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="shopId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getShopId() {
		return this.shopId;
	}
	
	/**
	 * Set the shopId
	 */	
	public void setShopId(Long aValue) {
		this.shopId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="shopName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getShopName() {
		return this.shopName;
	}
	
	/**
	 * Set the shopName
	 */	
	public void setShopName(String aValue) {
		this.shopName = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="createId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCreateId() {
		return this.createId;
	}
	
	/**
	 * Set the createId
	 */	
	public void setCreateId(Long aValue) {
		this.createId = aValue;
	}	

	/**
	 * 交易类型，1表示现金交易，2表示银行卡交易	 * @return Short
	 * @hibernate.property column="dealType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getDealType() {
		return this.dealType;
	}
	
	/**
	 * Set the dealType
	 */	
	public void setDealType(Short aValue) {
		this.dealType = aValue;
	}	

	/**
	 * 本条记录登记时间	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 */	
	public void setCreateDate(java.util.Date aValue) {
		this.createDate = aValue;
	}	

	/**
	 * 交易额到账日	 * @return java.util.Date
	 * @hibernate.property column="transferDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getTransferDate() {
		return this.transferDate;
	}
	
	/**
	 * Set the transferDate
	 */	
	public void setTransferDate(java.util.Date aValue) {
		this.transferDate = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ObAccountDealInfo)) {
			return false;
		}
		ObAccountDealInfo rhs = (ObAccountDealInfo) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.accountId, rhs.accountId)
				.append(this.incomMoney, rhs.incomMoney)
				.append(this.payMoney, rhs.payMoney)
				.append(this.transferType, rhs.transferType)
				.append(this.shopId, rhs.shopId)
				.append(this.shopName, rhs.shopName)
				.append(this.createId, rhs.createId)
				.append(this.dealType, rhs.dealType)
				.append(this.createDate, rhs.createDate)
				.append(this.transferDate, rhs.transferDate)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.accountId) 
				.append(this.incomMoney) 
				.append(this.payMoney) 
				.append(this.transferType) 
				.append(this.shopId) 
				.append(this.shopName) 
				.append(this.createId) 
				.append(this.dealType) 
				.append(this.createDate) 
				.append(this.transferDate) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("accountId", this.accountId) 
				.append("incomMoney", this.incomMoney) 
				.append("payMoney", this.payMoney) 
				.append("transferType", this.transferType) 
				.append("shopId", this.shopId) 
				.append("shopName", this.shopName) 
				.append("createId", this.createId) 
				.append("dealType", this.dealType) 
				.append("createDate", this.createDate) 
				.append("transferDate", this.transferDate) 
				.toString();
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public String getBankNum() {
		return bankNum;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setEnterpriseBankId(Integer enterpriseBankId) {
		this.enterpriseBankId = enterpriseBankId;
	}

	public Integer getEnterpriseBankId() {
		return enterpriseBankId;
	}

	public void setOpenType(Short openType) {
		this.openType = openType;
	}

	public Short getOpenType() {
		return openType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setKhname(String khname) {
		this.khname = khname;
	}

	public String getKhname() {
		return khname;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaName() {
		return areaName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public java.math.BigDecimal getCurrentMoney() {
		return currentMoney;
	}

	public void setCurrentMoney(java.math.BigDecimal currentMoney) {
		this.currentMoney = currentMoney;
	}

    public String getObligationInfoName() {
		return obligationInfoName;
	}

	public void setObligationInfoName(String obligationInfoName) {
		this.obligationInfoName = obligationInfoName;
	}

	public String getObligationNumber() {
		return obligationNumber;
	}

	public void setObligationNumber(String obligationNumber) {
		this.obligationNumber = obligationNumber;
	}

	public void setTransferTypeName(String transferTypeName) {
		this.transferTypeName = transferTypeName;
	}

	public String getTransferTypeName() {
		return transferTypeName;
	}

	public Short getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(Short recordStatus) {
		this.recordStatus = recordStatus;
	}

}
