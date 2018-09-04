package com.zhiwei.credit.model.creditFlow.contract;
public class PawnElementCode extends BaseElementCode implements java.io.Serializable{
	public static final String _D = "借款人"; 
	public String vCustomer$customerName$_v = "" ; 
	public static final String CZF_C = "[#借款人#]" ;
	
	public static final String JKRZZJGDM_D = "借款人组织机构代码"; 
	public String vCustomer$organizecode$_v = "" ; 
	public static final String JKRZZJGDM_C = "[#借款人组织机构代码#]" ;
	
	public static final String JKRYYZZHM_D = "借款人营业执照号码"; 
	public String vCustomer$cciaa$_v = "" ; 
	public static final String JKRYYZZHM_C = "[#借款人营业执照号码#]" ;
	
	public static final String JKRDZ_D = "借款人地址"; 
	public String vCustomer$address$_v = "" ; 
	public static final String JKRDZ_C = "[#借款人地址#]" ;
	
	public static final String JKRYZBH_D = "借款人邮政编码"; 
	public String vCustomer$postcoding$_v = "" ; 
	public static final String JKRYZBH_C = "[#借款人邮政编码#]" ;
	
	public static final String JKRLXDH_D = "借款人联系电话"; 
	public String vCustomer$telephone$_v = "" ; 
	public static final String JKRLXDH_C = "[#借款人联系电话#]" ;
	
	public static final String JKRCZ_D = "借款人传真 "; 
	public String vCustomer$fax$_v = "" ; 
	public static final String JKRCZ_C = "[#借款人传真 #]" ;
	
	public static final String JKRDZYX_D = "借款人电子邮箱 "; 
	public String vCustomer$email$_v = "" ; 
	public static final String JKRDZYX_C = "[#借款人电子邮箱 #]" ;
	
	public static final String JKRZJLX_D = "借款人证件类型"; 
	public String vCustomer$cardtypeValue$_v = "" ; 
	public static final String JKRZJLX_C = "[#借款人证件类型#]" ;
	
	public static final String JKRZJHM_D = "借款人证件号码"; 
	public String vCustomer$cardnumber$_v = "" ; 
	public static final String JKRZJHM_C = "[#借款人证件号码#]" ;
	
	
	//-----------------------------------------------------------
	
	public static final String WFZTMC = "债权人"; 
	public String project$mineName$_v = "" ; 
	public static final String WFZTMC_C = "[#我方主体名称#]" ;
	
	//------------------------项目表---------------------------------------
/*	public static final String WFZTDZ_D = "债权人地址"; 
	public String vCustomer$organizecode$_v = "" ; 
	public static final String WFZTDZ_C = "[#我方主体地址#]" ;
*/	
	public static final String DKJEDX_D = "贷款金额大写"; 
	public String project$projectMoney$decimalBigFormat$_v = "" ; 
	public static final String DKJEDX_C = "[#贷款金额大写#]" ;
	
	/*
	public static final String DKJEXX_D = "利息余额大写"; 
	public String project$projectMoney$_v = "" ; 
	public static final String DKJEXX_C = "[#利息余额大写#]" ;
	*/
	public static final String DDQSRQ_D = "典当起始日期"; 
	public String project$startDate$_v = "" ; 
	public static final String DDQSRQ_C = "[#典当起始日期#]" ;
	
	public static final String DDDQRQ_D = "典当到期日期"; 
	public String project$intentDate$_v = "" ; 
	public static final String DDDQRQ_C = "[#典当到期日期#]" ;
	
	public static final String DDQX_D = "典当期限"; 
	public String getPayIntentInfo$_v = "" ; 
	public static final String DDQX_C = "[#典当期限#]" ;
	
	public static final String DDHTBH_D = "典当合同编号"; 
	public String getPawnItemContract$_v = "" ; 
	public static final String DDHTBH_C = "[#典当合同编号#]" ;
	
}