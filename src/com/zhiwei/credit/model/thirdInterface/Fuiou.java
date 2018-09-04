package com.zhiwei.credit.model.thirdInterface;

public class Fuiou{

	/**
	 * 字符编码
	 */
	public static final String CHARSETUTF8 = "UTF-8";
	/**
	 * 富有金账户测试注册url
	 */
	public static final String FUIOUGOLDREG="reg.action";
	/**
	 * 富有金账户测试登陆url
	 */
	public static final String FUIOULOGIN="webLogin.action";
	/**
	 * 富有金账户余额查询url
	 */
	public static final String FUIOUCURRENTMONEY="BalanceAction.action";

	/**
	 * 富有预授权url
	 */
	public static final String FUIOUPREAUTO="preAuth.action";
	/**
	 * 富有预授权撤销url
	 */
	public static final String FUIOUPREAUTOCANC="preAuthCancel.action";
	/**
	 * 富有转账url
	 */
	public static final String FUIOUTRAACC="transferBmu.action";
	
	/**
	 * 富有金账户交易明细查询url
	 */
	public static final String FUIOUDEALINFOQUERY="query.action";


	/**
	 * 富有账户信息查询url
	 * svn:songwj
	 */
	public static final String FUIOUMEMBERMESSAGE="queryUserInfs.action";

	
	/**
	 * 富有更新账户信息url
	 * svn:songwj
	 */
	public static final String FUIOUUPDATEMEMBERMESSAGE="modifyUserInf.action";
	
	/**
	 * 划拨（个人和个人）url
	 * svn:songwj
	 */
	public static final String FUIOUTRANSFERPERSONTOPERSON="transferBu.action";
	
	
	/**
	 * 商户代码
	 */
	private String mchnt_cd;
	/**
	 * 流水号1：合作机构唯一标识
	 */
	private String mchnt_txn_ssn;
	
	
	/**
	 * 注册用户：客户姓名
	 */
	private String cust_nm;
	
	/**
	 * 身份证号码
	 */
	private String certif_id;
	/**
	 * 注册手机号
	 */
	private String mobile_no;
	/**
	 * 注册用户邮箱(非必填项)
	 */
	private String email;
	/**
	 * 城市码（见富有的城市编码）
	 */
	private String city_id;
	/**
	 * 开户行编码
	 */
	private String parent_bank_id;
	/**
	 * 开户网点（手动输入）（非必填）
	 */
	private String bank_nm;
	/**
	 * 开户名
	 */
	private String capAcntNm;
	/**
	 * 开户账号
	 */
	private String capAcntNo;
	/**
	 * 取现密码（非必填）（默认手机号后六位）
	 */
	private String password;
	/**
	 * 登陆密码（非必填）（默认手机号后六位）
	 */
	private String lpassword;
	/**
	 * 备注字段（非必填）
	 */
	private String rem ;
	
	/**
	 * fuiou签名
	 */
	private String signature;
	
	/**
	 * 返回相应代码
	 */
	private String resp_code;
	/**
	 * 富有账户登陆账号
	 */
	private String cust_no;
	/**
	 * 登陆富有账户跳转页面
	 */
	private String location;
	
	
	/**
	 * 富有查询日期
	 */
	private String mchnt_txn_dt;
	/**
	 * 富有交易明细查询登陆id（最多查询10个账户，已"|"分割）
	 */
	private String user_ids;
	/**
	 *  富有交易明细查询起始日期
	 */
	private String start_day;
	/**
	 * 富有交易明细查询结束日期
	 */
	private String end_day;
	

	/**
	 * 付款登录账户
	 * svn：songwj
	 */
	private String out_cust_no;
	
	
	/**
	 * 收款登录账户
	 * svn：songwj
	 */
	private String in_cust_no;
	
	/**
	 * 划拨金额
	 * svn：songwj
	 */
	private String amt;

	/**
	 * 预授权合同号
	 */
	private String contract_no;
	public String getMchnt_cd() {
		return mchnt_cd;
	}
	public void setMchnt_cd(String mchntCd) {
		mchnt_cd = mchntCd;
	}
	public String getMchnt_txn_ssn() {
		return mchnt_txn_ssn;
	}
	public void setMchnt_txn_ssn(String mchntTxnSsn) {
		mchnt_txn_ssn = mchntTxnSsn;
	}
	public String getCust_nm() {
		return cust_nm;
	}
	public void setCust_nm(String custNm) {
		cust_nm = custNm;
	}
	public String getCertif_id() {
		return certif_id;
	}
	public void setCertif_id(String certifId) {
		certif_id = certifId;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobileNo) {
		mobile_no = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String cityId) {
		city_id = cityId;
	}
	public String getParent_bank_id() {
		return parent_bank_id;
	}
	public void setParent_bank_id(String parentBankId) {
		parent_bank_id = parentBankId;
	}
	public String getBank_nm() {
		return bank_nm;
	}
	public void setBank_nm(String bankNm) {
		bank_nm = bankNm;
	}
	public String getCapAcntNo() {
		return capAcntNo;
	}
	public void setCapAcntNo(String capAcntNo) {
		this.capAcntNo = capAcntNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLpassword() {
		return lpassword;
	}
	public void setLpassword(String lpassword) {
		this.lpassword = lpassword;
	}
	public String getRem() {
		return rem;
	}
	public void setRem(String rem) {
		this.rem = rem;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}
	public String getResp_code() {
		return resp_code;
	}
	public void setCapAcntNm(String capAcntNm) {
		this.capAcntNm = capAcntNm;
	}
	public String getCapAcntNm() {
		return capAcntNm;
	}
	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}
	public String getCust_no() {
		return cust_no;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setMchnt_txn_dt(String mchnt_txn_dt) {
		this.mchnt_txn_dt = mchnt_txn_dt;
	}
	public String getMchnt_txn_dt() {
		return mchnt_txn_dt;
	}
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contractNo) {
		contract_no = contractNo;
	}
	public void setUser_ids(String user_ids) {
		this.user_ids = user_ids;
	}
	public String getUser_ids() {
		return user_ids;
	}
	public void setStart_day(String start_day) {
		this.start_day = start_day;
	}
	public String getStart_day() {
		return start_day;
	}
	public void setEnd_day(String end_day) {
		this.end_day = end_day;
	}
	public String getEnd_day() {
		return end_day;
	}
	

	public String getOut_cust_no() {
		return out_cust_no;
	}
	public void setOut_cust_no(String outCustNo) {
		out_cust_no = outCustNo;
	}
	public String getIn_cust_no() {
		return in_cust_no;
	}
	public void setIn_cust_no(String inCustNo) {
		in_cust_no = inCustNo;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}

}