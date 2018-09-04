package com.zhiwei.credit.model.creditFlow.contract;


public class LeaseFinanceElementCode extends BaseElementCode implements java.io.Serializable{
	//---------------------------add by gao --------------------------------------
	public static final String RZZLHTBH_D = "融资租赁合同编号"; 
	public String generateContractNum$_v = "" ; //A,B 无意义
	public static final String RZZLHTBH_C = "[#融资租赁合同编号#]" ;
	
	public static final String CZF_D = "出租方"; 
	public String organization$orgName$_v = "" ; 
	public static final String CZF_C = "[#出租方#]" ;
	
	
	public static final String CZFZCDZ_D = "出租方注册地址"; 
	public String organization$address$_v = "" ; 
	public static final String CZFZCDZ_C = "[#出租方注册地址#]" ;
	
	
	public static final String CZFYZBH_D = "出租方邮政编号"; 
	public String organization$postCode$_v = "" ; 
	public static final String CZFYZBH_C = "[#出租方邮政编号#]" ;
	
	
	public static final String CZFDH_D = "出租方电话"; 
	public String organization$linktel$_v = "" ; 
	public static final String CZFDH_C = "[#出租方电话#]" ;
	
	
/*	public static final String CZFFRDB_D = "出租方法人代表"; 
	public String czffrdb_v = "" ; 
	public static final String CZFFRDB_C = "[#出租方法人代表#]" ;
	
	
	public static final String CZFFRDBZW_D = "出租方法人代表职务"; 
	public String czffrdbzw_v = "" ; 
	public static final String CZFFRDBZW_C = "[#出租方法人代表职务#]" ;*/
	
	
	public static final String CZRF_D = "承租人方"; 
	public String vCustomer$customerName$_v = "" ; 
	public static final String CZRF_C = "[#承租人方#]" ;
	
	
	public static final String CZRZCDZ_D = "承租人注册地址"; 
	public String vCustomer$address$_v = "" ; 
	public static final String CZRZCDZ_C = "[#承租人注册地址#]" ;
	
	
	public static final String CZRYZBH_D = "承租人邮政编号"; 
	public String vCustomer$postcoding$_v = "" ; 
	public static final String CZRYZBH_C = "[#承租人邮政编号#]" ;
	
	public static final String CZRDH_D = "承租人电话"; 
	public String vCustomer$telephone$_v = "" ; 
	public static final String CZRDH_C = "[#承租人电话#]" ;
	
	public static final String CZRDZ_D = "承租人地址"; 
	public String vCustomer$area$_v = "" ; 
	public static final String CZRDZ_C = "[#承租人地址#]" ;
	
	public static final String CZRCZ_D = "承租人传真"; 
	public String vCustomer$fax$_v = "" ; 
	public static final String CZRCZ_C = "[#承租人传真#]" ;
	
	public static final String RZRFRDB_D = "承租人法人代表"; 
	public String vCustomer$id$legalpersonName$_v = "" ; 
	public static final String RZRFRDB_C = "[#承租人法人代表#]" ;
	
	/*public static final String CZRFRDBZW_D = "承租人法人代表职务"; 
	public String czrfrdbzw_v = "" ; 
	public static final String CZRFRDBZW_C = "[#承租人法人代表职务#]" ;*/
	
	//没有这个字段，要算
  	public static final String ZLQX_D = "租赁期限"; 
	public String getPayIntentInfo$_v = "" ; 
	public static final String ZLQX_C = "[#租赁期限#]" ;
	
	public static final String ZLCBDX_D = "租赁成本大写"; 
	public String project$projectMoney$decimalBigFormat$_v = "" ; 
	public static final String ZLCBDX_C = "[#租赁成本大写#]" ;
	
	public static final String ZLCB_D = "租赁成本小写"; 
	public String project$projectMoney$_v = "" ; 
	public static final String ZLCB_C = "[#租赁成本小写#]" ;
	
	public static final String ZLQS_D = "租赁期数"; 
	public String project$payintentPeriod$_v = "" ; 
	public static final String ZLQS_C = "[#租赁期数#]" ;
	
	public static final String ZLZQ_D = "租赁周期"; 
	public String project$payaccrualType$_v = "" ; 
	public static final String ZLZQ_C = "[#租赁周期#]" ;
	
	public static final String ZLBZJDX_D = "租赁保证金大写"; 
	public String project$leaseDepositMoney$decimalBigFormat$_v = "" ; 
	public static final String ZLBZJDX_C = "[#租赁保证金大写#]" ;
	
	public static final String ZLBZJXX_D = "租赁保证金小写"; 
	public String project$leaseDepositMoney$_v = "" ; 
	public static final String ZLBZJXX_C = "[#租赁保证金小写#]" ;
	
	public static final String ZLBZJ_D = "租赁保证金"; 
	public String project$leaseDepositMoney$_v2 = "" ; 
	public static final String ZLBZJ_C = "[#租赁保证金#]";
	
	public static final String MQHKRQ_D = "每期还款日期"; 
	public String project$isStartDatePay$_v = "" ; 
	public static final String MQHKRQ_C = "[#每期还款日期#]";
	
	public static final String ZLQSRQ_D = "租赁起始日期"; 
	public String project$startDate$_v = "" ; 
	public static final String ZLQSRQ_C = "[#租赁起始日期#]";
	
	public static final String ZLDQRQ_D = "租赁到期日期"; 
	public String project$intentDate$_v = "" ; 
	public static final String ZLDQRQ_C = "[#租赁到期日期#]";
	
	public static final String ZJHJ_D = "租金合计"; 
	public String project$allMoney$_v = "" ; 
	public static final String ZJHJ_C = "[#租金合计#]";
	
	public static final String ZLBZJFL_D = "租赁保证金费率"; 
	public String project$leaseDepositRate$_v = "" ; 
	public static final String ZLBZJFL_C = "[#租赁保证金费率#]";
	
	public static final String ZLSXFDX_D = "租赁手续费大写"; 
	public String project$rentalFeeMoney$decimalBigFormat$_v = "" ; 
	public static final String ZLSXFDX_C = "[#租赁手续费大写#]";
	
	public static final String ZLSXFXX_D = "租赁手续费小写"; 
	public String project$rentalFeeMoney$_v = "" ; 
	public static final String ZLSXFXX_C = "[#租赁手续费小写#]";
	
	public static final String ZLSXFL_D = "租赁手续费率"; 
	public String project$rentalFeeRate$_v = "" ; 
	public static final String ZLSXFL_C = "[#租赁手续费率#]";
	
	public static final String ZLWSZD_D = "租赁物所在地"; 
	public String project$leasedPropertyPlace$_v = "" ; 
	public static final String ZLWSZD_C = "[#租赁物所在地#]";
	
	public static final String ZLWLGFDX_D = "租赁物留购费大写"; 
	public String project$leaseRetentionFeeMoney$decimalBigFormat$_v = "" ; 
	public static final String ZLWLGFDX_C = "[#租赁物留购费大写#]";
	
	
	public static final String ZLWLGFXX_D = "租赁物留购费小写"; 
	public String project$leaseRetentionFeeMoney$_v = "" ; 
	public static final String ZLWLGFXX_C = "[#租赁物留购费小写#]";
	
	public static final String ZLWMMHTBH_D = "租赁物买卖合同编号"; 
	public String generateLeaseContractNum$_v = "" ; 
	public static final String ZLWMMHTBH_C = "[#租赁物买卖合同编号#]";
	
	public static final String GHF_D = "供货方"; 
	public String supplior$Name$_v = "" ; 
	public static final String GHF_C = "[#供货方#]";
	
	public static final String GHFZCDZ_D = "供货方注册地址"; 
	public String supplior$companyAddress$_v = "" ; 
	public static final String GHFZCDZ_C = "[#供货方注册地址#]";
	
/*	public static final String GHFZCDZ_D = "供货方邮政编码"; 
	public String supplior$$_v = "" ; 
	public static final String GHFZCDZ_C = "[#供货方邮政编码#]";*/
	
	public static final String GHFDH_D = "供货方电话"; 
	public String supplior$connectorPhoneNum$_v = "" ; 
	public static final String GHFDH_C = "[#供货方电话#]";
	
	public static final String GHFCZ_D = "供货方传真"; 
	public String supplior$companyFax$_v = "" ; 
	public static final String GHFCZ_C = "[#供货方传真#]";
	
	public static final String GHFFRDB_D = "供货方法人代表"; 
	public String supplior$legalPersonName$_v = "" ; 
	public static final String GHFFRDB_C = "[#供货方法人代表#]";
	
	public static final String ZLWGMJGDX_D = "租赁物购买价格大写"; 
	public String vObjectInfo$buyPrice$decimalBigFormat$_v = "" ; 
	public static final String ZLWGMJGDX_C = "[#租赁物购买价格大写#]";
	public String getGenerateContractNum$_v() {
		return generateContractNum$_v;
	}
	public void setGenerateContractNum$_v(String generateContractNum$V) {
		generateContractNum$_v = generateContractNum$V;
	}
	public String getOrganization$orgName$_v() {
		return organization$orgName$_v;
	}
	public void setOrganization$orgName$_v(String organization$orgName$V) {
		organization$orgName$_v = organization$orgName$V;
	}
	public String getOrganization$address$_v() {
		return organization$address$_v;
	}
	public void setOrganization$address$_v(String organization$address$V) {
		organization$address$_v = organization$address$V;
	}
	public String getOrganization$postCode$_v() {
		return organization$postCode$_v;
	}
	public void setOrganization$postCode$_v(String organization$postCode$V) {
		organization$postCode$_v = organization$postCode$V;
	}
	public String getOrganization$linktel$_v() {
		return organization$linktel$_v;
	}
	public void setOrganization$linktel$_v(String organization$linktel$V) {
		organization$linktel$_v = organization$linktel$V;
	}
	public String getvCustomer$customerName$_v() {
		return vCustomer$customerName$_v;
	}
	public void setvCustomer$customerName$_v(String vCustomer$customerName$V) {
		vCustomer$customerName$_v = vCustomer$customerName$V;
	}
	public String getvCustomer$address$_v() {
		return vCustomer$address$_v;
	}
	public void setvCustomer$address$_v(String vCustomer$address$V) {
		vCustomer$address$_v = vCustomer$address$V;
	}
	public String getvCustomer$postcoding$_v() {
		return vCustomer$postcoding$_v;
	}
	public void setvCustomer$postcoding$_v(String vCustomer$postcoding$V) {
		vCustomer$postcoding$_v = vCustomer$postcoding$V;
	}
	public String getvCustomer$telephone$_v() {
		return vCustomer$telephone$_v;
	}
	public void setvCustomer$telephone$_v(String vCustomer$telephone$V) {
		vCustomer$telephone$_v = vCustomer$telephone$V;
	}
	public String getvCustomer$area$_v() {
		return vCustomer$area$_v;
	}
	public void setvCustomer$area$_v(String vCustomer$area$V) {
		vCustomer$area$_v = vCustomer$area$V;
	}
	public String getProject$projectMoney$decimalBigFormat$_v() {
		return project$projectMoney$decimalBigFormat$_v;
	}
	public void setProject$projectMoney$decimalBigFormat$_v(
			String project$projectMoney$decimalBigFormat$V) {
		project$projectMoney$decimalBigFormat$_v = project$projectMoney$decimalBigFormat$V;
	}
	public String getProject$projectMoney$_v() {
		return project$projectMoney$_v;
	}
	public void setProject$projectMoney$_v(String project$projectMoney$V) {
		project$projectMoney$_v = project$projectMoney$V;
	}
	public String getProject$payintentPeriod$_v() {
		return project$payintentPeriod$_v;
	}
	public void setProject$payintentPeriod$_v(String project$payintentPeriod$V) {
		project$payintentPeriod$_v = project$payintentPeriod$V;
	}
	public String getProject$payaccrualType$_v() {
		return project$payaccrualType$_v;
	}
	public void setProject$payaccrualType$_v(String project$payaccrualType$V) {
		project$payaccrualType$_v = project$payaccrualType$V;
	}
	public String getProject$leaseDepositMoney$decimalBigFormat$_v() {
		return project$leaseDepositMoney$decimalBigFormat$_v;
	}
	public void setProject$leaseDepositMoney$decimalBigFormat$_v(
			String project$leaseDepositMoney$decimalBigFormat$V) {
		project$leaseDepositMoney$decimalBigFormat$_v = project$leaseDepositMoney$decimalBigFormat$V;
	}
	public String getProject$leaseDepositMoney$_v() {
		return project$leaseDepositMoney$_v;
	}
	public void setProject$leaseDepositMoney$_v(String project$leaseDepositMoney$V) {
		project$leaseDepositMoney$_v = project$leaseDepositMoney$V;
	}
	public String getProject$leaseDepositMoney$_v2() {
		return project$leaseDepositMoney$_v2;
	}
	public void setProject$leaseDepositMoney$_v2(String project$leaseDepositMoney$V2) {
		project$leaseDepositMoney$_v2 = project$leaseDepositMoney$V2;
	}
	public String getProject$isStartDatePay$_v() {
		return project$isStartDatePay$_v;
	}
	public void setProject$isStartDatePay$_v(String project$isStartDatePay$V) {
		project$isStartDatePay$_v = project$isStartDatePay$V;
	}
	public String getProject$startDate$_v() {
		return project$startDate$_v;
	}
	public void setProject$startDate$_v(String project$startDate$V) {
		project$startDate$_v = project$startDate$V;
	}
	public String getProject$intentDate$_v() {
		return project$intentDate$_v;
	}
	public void setProject$intentDate$_v(String project$intentDate$V) {
		project$intentDate$_v = project$intentDate$V;
	}
	public String getProject$allMoney$_v() {
		return project$allMoney$_v;
	}
	public void setProject$allMoney$_v(String project$allMoney$V) {
		project$allMoney$_v = project$allMoney$V;
	}
	public String getProject$leaseDepositRate$_v() {
		return project$leaseDepositRate$_v;
	}
	public void setProject$leaseDepositRate$_v(String project$leaseDepositRate$V) {
		project$leaseDepositRate$_v = project$leaseDepositRate$V;
	}
	public String getProject$rentalFeeMoney$decimalBigFormat$_v() {
		return project$rentalFeeMoney$decimalBigFormat$_v;
	}
	public void setProject$rentalFeeMoney$decimalBigFormat$_v(
			String project$rentalFeeMoney$decimalBigFormat$V) {
		project$rentalFeeMoney$decimalBigFormat$_v = project$rentalFeeMoney$decimalBigFormat$V;
	}
	public String getProject$rentalFeeMoney$_v() {
		return project$rentalFeeMoney$_v;
	}
	public void setProject$rentalFeeMoney$_v(String project$rentalFeeMoney$V) {
		project$rentalFeeMoney$_v = project$rentalFeeMoney$V;
	}
	public String getProject$rentalFeeRate$_v() {
		return project$rentalFeeRate$_v;
	}
	public void setProject$rentalFeeRate$_v(String project$rentalFeeRate$V) {
		project$rentalFeeRate$_v = project$rentalFeeRate$V;
	}
	public String getProject$leasedPropertyPlace$_v() {
		return project$leasedPropertyPlace$_v;
	}
	public void setProject$leasedPropertyPlace$_v(
			String project$leasedPropertyPlace$V) {
		project$leasedPropertyPlace$_v = project$leasedPropertyPlace$V;
	}
	public String getProject$leaseRetentionFeeMoney$decimalBigFormat$_v() {
		return project$leaseRetentionFeeMoney$decimalBigFormat$_v;
	}
	public void setProject$leaseRetentionFeeMoney$decimalBigFormat$_v(
			String project$leaseRetentionFeeMoney$decimalBigFormat$V) {
		project$leaseRetentionFeeMoney$decimalBigFormat$_v = project$leaseRetentionFeeMoney$decimalBigFormat$V;
	}
	public String getProject$leaseRetentionFeeMoney$_v() {
		return project$leaseRetentionFeeMoney$_v;
	}
	public void setProject$leaseRetentionFeeMoney$_v(
			String project$leaseRetentionFeeMoney$V) {
		project$leaseRetentionFeeMoney$_v = project$leaseRetentionFeeMoney$V;
	}
	public String getGenerateLeaseContractNum$_v() {
		return generateLeaseContractNum$_v;
	}
	public void setGenerateLeaseContractNum$_v(String generateLeaseContractNum$V) {
		generateLeaseContractNum$_v = generateLeaseContractNum$V;
	}
	public String getSupplior$Name$_v() {
		return supplior$Name$_v;
	}
	public void setSupplior$Name$_v(String supplior$Name$V) {
		supplior$Name$_v = supplior$Name$V;
	}
	public String getSupplior$companyAddress$_v() {
		return supplior$companyAddress$_v;
	}
	public void setSupplior$companyAddress$_v(String supplior$companyAddress$V) {
		supplior$companyAddress$_v = supplior$companyAddress$V;
	}
	public String getSupplior$connectorPhoneNum$_v() {
		return supplior$connectorPhoneNum$_v;
	}
	public void setSupplior$connectorPhoneNum$_v(String supplior$connectorPhoneNum$V) {
		supplior$connectorPhoneNum$_v = supplior$connectorPhoneNum$V;
	}
	public String getSupplior$companyFax$_v() {
		return supplior$companyFax$_v;
	}
	public void setSupplior$companyFax$_v(String supplior$companyFax$V) {
		supplior$companyFax$_v = supplior$companyFax$V;
	}
	public String getSupplior$legalPersonName$_v() {
		return supplior$legalPersonName$_v;
	}
	public void setSupplior$legalPersonName$_v(String supplior$legalPersonName$V) {
		supplior$legalPersonName$_v = supplior$legalPersonName$V;
	}
	public String getvObjectInfo$buyPrice$decimalBigFormat$_v() {
		return vObjectInfo$buyPrice$decimalBigFormat$_v;
	}
	public void setvObjectInfo$buyPrice$decimalBigFormat$_v(
			String vObjectInfo$buyPrice$decimalBigFormat$V) {
		vObjectInfo$buyPrice$decimalBigFormat$_v = vObjectInfo$buyPrice$decimalBigFormat$V;
	}
	public static String getRzzlhtbhD() {
		return RZZLHTBH_D;
	}
	public static String getRzzlhtbhC() {
		return RZZLHTBH_C;
	}
	public static String getCzfD() {
		return CZF_D;
	}
	public static String getCzfC() {
		return CZF_C;
	}
	public static String getCzfzcdzD() {
		return CZFZCDZ_D;
	}
	public static String getCzfzcdzC() {
		return CZFZCDZ_C;
	}
	public static String getCzfyzbhD() {
		return CZFYZBH_D;
	}
	public static String getCzfyzbhC() {
		return CZFYZBH_C;
	}
	public static String getCzfdhD() {
		return CZFDH_D;
	}
	public static String getCzfdhC() {
		return CZFDH_C;
	}
	public static String getCzrfD() {
		return CZRF_D;
	}
	public static String getCzrfC() {
		return CZRF_C;
	}
	public static String getCzrzcdzD() {
		return CZRZCDZ_D;
	}
	public static String getCzrzcdzC() {
		return CZRZCDZ_C;
	}
	public static String getCzryzbhD() {
		return CZRYZBH_D;
	}
	public static String getCzryzbhC() {
		return CZRYZBH_C;
	}
	public static String getCzrdhD() {
		return CZRDH_D;
	}
	public static String getCzrdhC() {
		return CZRDH_C;
	}
	public static String getCzrdzD() {
		return CZRDZ_D;
	}
	public static String getCzrdzC() {
		return CZRDZ_C;
	}
	public static String getZlcbdxD() {
		return ZLCBDX_D;
	}
	public static String getZlcbdxC() {
		return ZLCBDX_C;
	}
	public static String getZlcbD() {
		return ZLCB_D;
	}
	public static String getZlcbC() {
		return ZLCB_C;
	}
	public static String getZlqsD() {
		return ZLQS_D;
	}
	public static String getZlqsC() {
		return ZLQS_C;
	}
	public static String getZlzqD() {
		return ZLZQ_D;
	}
	public static String getZlzqC() {
		return ZLZQ_C;
	}
	public static String getZlbzjdxD() {
		return ZLBZJDX_D;
	}
	public static String getZlbzjdxC() {
		return ZLBZJDX_C;
	}
	public static String getZlbzjxxD() {
		return ZLBZJXX_D;
	}
	public static String getZlbzjxxC() {
		return ZLBZJXX_C;
	}
	public static String getZlbzjD() {
		return ZLBZJ_D;
	}
	public static String getZlbzjC() {
		return ZLBZJ_C;
	}
	public static String getMqhkrqD() {
		return MQHKRQ_D;
	}
	public static String getMqhkrqC() {
		return MQHKRQ_C;
	}
	public static String getZlqsrqD() {
		return ZLQSRQ_D;
	}
	public static String getZlqsrqC() {
		return ZLQSRQ_C;
	}
	public static String getZldqrqD() {
		return ZLDQRQ_D;
	}
	public static String getZldqrqC() {
		return ZLDQRQ_C;
	}
	public static String getZjhjD() {
		return ZJHJ_D;
	}
	public static String getZjhjC() {
		return ZJHJ_C;
	}
	public static String getZlbzjflD() {
		return ZLBZJFL_D;
	}
	public static String getZlbzjflC() {
		return ZLBZJFL_C;
	}
	public static String getZlsxfdxD() {
		return ZLSXFDX_D;
	}
	public static String getZlsxfdxC() {
		return ZLSXFDX_C;
	}
	public static String getZlsxfxxD() {
		return ZLSXFXX_D;
	}
	public static String getZlsxfxxC() {
		return ZLSXFXX_C;
	}
	public static String getZlsxflD() {
		return ZLSXFL_D;
	}
	public static String getZlsxflC() {
		return ZLSXFL_C;
	}
	public static String getZlwszdD() {
		return ZLWSZD_D;
	}
	public static String getZlwszdC() {
		return ZLWSZD_C;
	}
	public static String getZlwlgfdxD() {
		return ZLWLGFDX_D;
	}
	public static String getZlwlgfdxC() {
		return ZLWLGFDX_C;
	}
	public static String getZlwlgfxxD() {
		return ZLWLGFXX_D;
	}
	public static String getZlwlgfxxC() {
		return ZLWLGFXX_C;
	}
	public static String getZlwmmhtbhD() {
		return ZLWMMHTBH_D;
	}
	public static String getZlwmmhtbhC() {
		return ZLWMMHTBH_C;
	}
	public static String getGhfD() {
		return GHF_D;
	}
	public static String getGhfC() {
		return GHF_C;
	}
	public static String getGhfzcdzD() {
		return GHFZCDZ_D;
	}
	public static String getGhfzcdzC() {
		return GHFZCDZ_C;
	}
	public static String getGhfdhD() {
		return GHFDH_D;
	}
	public static String getGhfdhC() {
		return GHFDH_C;
	}
	public static String getGhfczD() {
		return GHFCZ_D;
	}
	public static String getGhfczC() {
		return GHFCZ_C;
	}
	public static String getGhffrdbD() {
		return GHFFRDB_D;
	}
	public static String getGhffrdbC() {
		return GHFFRDB_C;
	}
	public static String getZlwgmjgdxD() {
		return ZLWGMJGDX_D;
	}
	public static String getZlwgmjgdxC() {
		return ZLWGMJGDX_C;
	}
	public String getGetPayIntentInfo$_v() {
		return getPayIntentInfo$_v;
	}
	public void setGetPayIntentInfo$_v(String getPayIntentInfo$V) {
		getPayIntentInfo$_v = getPayIntentInfo$V;
	}
	public static String getZlqxD() {
		return ZLQX_D;
	}
	public static String getZlqxC() {
		return ZLQX_C;
	}
	public String getvCustomer$fax$_v() {
		return vCustomer$fax$_v;
	}
	public void setvCustomer$fax$_v(String vCustomer$fax$V) {
		vCustomer$fax$_v = vCustomer$fax$V;
	}
	public String getvCustomer$id$legalpersonName$_v() {
		return vCustomer$id$legalpersonName$_v;
	}
	public void setvCustomer$id$legalpersonName$_v(
			String vCustomer$id$legalpersonName$V) {
		vCustomer$id$legalpersonName$_v = vCustomer$id$legalpersonName$V;
	}
	public static String getCzrczD() {
		return CZRCZ_D;
	}
	public static String getCzrczC() {
		return CZRCZ_C;
	}
	public static String getRzrfrdbD() {
		return RZRFRDB_D;
	}
	public static String getRzrfrdbC() {
		return RZRFRDB_C;
	}
	
	
}