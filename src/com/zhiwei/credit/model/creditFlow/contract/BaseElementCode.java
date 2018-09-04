package com.zhiwei.credit.model.creditFlow.contract;

/*
 * 保存要素公共字段   用以通过多态统一设置 各不同项目要素的公共字段 
 * 公共字段
 * 抵质押物 要素
 * 保证合同  要素
 * 
 * */
public class BaseElementCode {
	public static final String BZRMC_D = "保证人"; 
	public String bzrmc_v = "" ; 
	public static final String BZRMC_C = "[#保证人#]" ;
	
	public static final String BZRZJZL_D = "保证人证件种类"; 
	public String bzrzjzl_v = "" ; 
	public static final String BZRZJZL_C = "[#保证人证件种类#]" ;
	
	public static final String BZRZJHM_D = "保证人证件号码"; 
	public String bzrzjhm_v = "" ; 
	public static final String BZRZJHM_C = "[#保证人证件号码#]" ;

	public static final String BZRDZ_D = "保证人地址"; 
	public String bzrdz_v = "" ; 
	public static final String BZRDZ_C = "[#保证人地址#]" ;
	
	public static final String BZRYZBM_D = "保证人邮编"; 
	public String bzryzbm_v = "" ; 
	public static final String BZRYZBM_C = "[#保证人邮编#]" ;

	public static final String BZRYLXDH_D = "保证人电话"; 
	public String bzrlxdh_v = "" ; 
	public static final String BZRYLXDH_C = "[#保证人电话#]" ;

	public static final String BZRCZHM_D = "保证人传真"; 
	public String bzrczhm_v = "" ; 
	public static final String BZRCZHM_C = "[#保证人传真#]" ;
	
	public static final String BZFFDDBR_D = "保证方法定代表人"; 
	public String bzffddbr_v = "" ; 
	public static final String BZFFDDBR_C = "[#保证方法定代表人#]" ;
	
	public static final String BZFFDDBRZW_D = "保证方法定代表人职务"; 
	public String bzffddbrzw_v = "" ; 
	public static final String BZFFDDBRZW_C = "[#保证方法定代表人职务#]" ;
	
	public static final String BZFYYZZH_D = "保证方营业执照号"; 
	public String bzfyyzzh_v = "" ; 
	public static final String BZFYYZZH_C = "[#保证方营业执照号#]" ;
	
	public static final String BZRPOMC_D = "保证人配偶名称"; 
	public String bzrpomc_v = "" ; 
	public static final String BZRPOMC_C = "[#保证人配偶名称#]" ;
	
	public static final String BZRPOZJHM_D = "保证人配偶证件号码"; 
	public String bzrpozjhm_v = "" ; 
	public static final String BZRPOZJHM_C = "[#保证人配偶证件号码#]" ;
	
	public static final String DYR_D = "抵押人"; 
	public String dyr_v = "" ; 
	public static final String DYR_C = "[#抵押人#]" ;

	public static final String DYRZJZL_D = "抵押人证件种类"; 
	public String dyrzjzl = "" ; 
	public static final String DYRZJZL_C = "[#抵押人证件种类#]" ;
	
	public static final String DYRZJHM_D = "抵押人证件号码"; 
	public String dyrzjhm = "" ; 
	public static final String DYRZJHM_C = "[#抵押人证件号码#]" ;

	public static final String DYRDZ_D = "抵押人地址"; 
	public String dyrdz_v = "" ; 
	public static final String DYRDZ_C = "[#抵押人地址#]" ;

	public static final String DYRYZBM_D = "抵押人邮编"; 
	public String dyryzbm_v = "" ; 
	public static final String DYRYZBM_C = "[#抵押人邮编#]" ;

	public static final String DYRDH_D = "抵押人电话"; 
	public String dyrdh_v = "" ; 
	public static final String DYRDH_C = "[#抵押人电话#]" ;

	public static final String DYRCZ_D = "抵押人传真"; 
	public String dyrcz_v = "" ; 
	public static final String DYRCZ_C = "[#抵押人传真#]" ;
	
	public static final String DYFFDDBR_D = "抵押方法定代表人"; 
	public String dyffddbr_v = "" ; 
	public static final String DYFFDDBR_C = "[#抵押方法定代表人#]" ;

	public static final String DYFFDDBRZW_D = "抵押方法定代表人职务"; 
	public String dyffddbrzw_v = "" ; 
	public static final String DYFFDDBRZW_C = "[#抵押方法定代表人职务#]" ;
	
	public static final String DYFYYZZHM_D = "抵押方营业执照号码"; 
	public String dyfyyzzhm_v = "" ; 
	public static final String DYFYYZZHM_C = "[#抵押方营业执照号码#]" ;
	
	public static final String DYWGYR_D = "抵押物共有人"; 
	public String dywgyr_v = "" ; 
	public static final String DYWGYR_C = "[#抵押物共有人#]" ;
	
	public static final String DYWMC_D = "抵押物名称"; 
	public String dywmc_v = "" ; 
	public static final String DYWMC_C = "[#抵押物名称#]" ;
	
	public static final String DYWQZBH_D = "抵押物权证编号"; 
	public String dywqzbh_v = "" ; 
	public static final String DYWQZBH_C = "[#抵押物权证编号#]" ;
	
	public static final String DYWSZD_D = "抵押物所在地"; 
	public String dywszd_v = "" ; 
	public static final String DYWSZD_C = "[#抵押物所在地#]" ;
	
	public static final String DYWGYJZ_D = "抵押物认定价值"; 
	public String dywgyjz_v = "" ; 
	public static final String DYWGYJZ_C = "[#抵押物公允价值#]" ;
	
	public static final String CZR_D = "出质人"; 
	public String czr_v = "" ; 
	public static final String CZR_C = "[#出质人#]" ;
	
	public static final String CZRZJZL_D = "出质人证件种类"; 
	public String czrzjzl_v = "" ; 
	public static final String CZRZJZL_C = "[#出质人证件种类#]" ;

	public static final String CZRZJHM_D = "出质人证件号码"; 
	public String czrzjhm_v = "" ; 
	public static final String CZRZJHM_C = "[#出质人证件号码#]" ;

	public static final String CZRTXDZ_D = "出质人地址"; 
	public String czrtxdz_v = "" ; 
	public static final String CZRTXDZ_C = "[#出质人地址#]" ;

	public static final String CZRYZBM_D = "出质人邮编"; 
	public String czryzbm_v = "" ; 
	public static final String CZRYZBM_C = "[#出质人邮编#]" ;

	public static final String CZRLXDH_D = "出质人电话"; 
	public String czrlxdh_v = "" ; 
	public static final String CZRLXDH_C = "[#出质人电话#]" ;

	public static final String CZRCZHM_D = "出质人传真"; 
	public String czrczhm_v = "" ; 
	public static final String CZRCZHM_C = "[#出质人传真#]" ;
	
	public static final String CZFFDDBR_D = "出质方法定代表人"; 
	public String czffddbr_v = "" ; 
	public static final String CZFFDDBR_C = "[#出质方法定代表人#]" ;
	
	public static final String CZFFDDBRZW_D = "出质方法定代表人职务"; 
	public String czffddbrzw_v = "" ; 
	public static final String CZFFDDBRZW_C = "[#出质方法定代表人职务#]" ;
	
	public static final String CZFYYZZHM_D = "出质方营业执照号码"; 
	public String czfyyzzhm_v = "" ; 
	public static final String CZFYYZZHM_C = "[#出质方营业执照号码#]" ;
	
	public static final String ZYWGYR_D = "质押物共有人"; 
	public String zywgyr_v = "" ; 
	public static final String ZYWGYR_C = "[#质押物共有人#]" ;
	
	public static final String ZYWGYRZJHM_D = "质押物共有人证件号码"; 
	public String zywgyrzjhm_v = "" ; 
	public static final String ZYWGYRZJHM_C = "[#质押物共有人证件号码#]" ;
	
	public static final String ZYWMC_D = "质押物名称"; 
	public String zywmc_v = "" ; 
	public static final String ZYWMC_C = "[#质押物名称#]" ;
	
	public static final String ZYWQZBH_D = "质押物权证编号"; 
	public String zywqzbh_v = "" ; 
	public static final String ZYWQZBH_C = "[#质押物权证编号#]" ;
	
	public static final String ZYWSL_D = "质押物数量"; 
	public String zywsl_v = "" ; 
	public static final String ZYWSL_C = "[#质押物数量#]" ;
	
	public static final String ZYWCFDD_D = "质押物存放地点"; 
	public String zywcfdd_v = "" ; 
	public static final String ZYWCFDD_C = "[#质押物存放地点#]" ;
	
	public static final String DBRMC_D = "担保人"; 
	public String dbrmc_v = "" ; 
	public static final String DBRMC_C = "[#担保人#]" ;
	
	public static final String JJBH_D = "借据编号"; 
	public String jjbh_v = "" ; 
	public static final String JJBH_C = "[#借据编号#]" ;
	
	public static final String JKHTBH_D = "借款合同编号"; 
	public String jkhtbh_v = "" ; 
	public static final String JKHTBH_C = "[#借款合同编号#]" ;
	
	public static final String dbhtbh_D = "担保合同编号"; 
	public String dbhtbh_v = "" ; 
	public static final String dbhtbh_C = "[#担保合同编号#]" ;
	
	public static final String DYDBHTBH_D = "抵押担保合同编号"; 
	public String dydbhtbh_v = "" ; 
	public static final String DYDBHTBH_C = "[#抵押担保合同编号#]" ;
	
	public static final String ZYDBHTBH_D = "质押担保合同编号"; 
	public String zydbhtbh_v = "" ; 
	public static final String ZYDBHTBH_C = "[#质押担保合同编号#]" ;
	
	public static final String BZDBHTBH_D = "保证担保合同编号"; 
	public String bzdbhtbh_v = "" ; 
	public static final String BZDBHTBH_C = "[#保证担保合同编号#]" ;
	
	public static final String GYR_D = "共有人 "; 
	public String gyr_v = "" ; 
	public static final String GYR_C = "[#共有人#]" ;
	
	public static final String SYCQR_D = "所有产权人 "; 
	public String sycqr_v = "" ; 
	public static final String SYCQR_C = "[#产权人#]" ;
	
	
	public static final String ZQRMC_D = "债权人"; 
	public String zqrmc_v = "" ; 
	public static final String ZQRMC_C = "[#我方主体名称#]" ;
	
	public static final String ZQRDZ_D = "债权人地址"; 
	public String zqrdz_v = "" ; 
	public static final String ZQRDZ_C = "[#我方主体地址#]" ;
	
	public static final String ZQRYB_D = "债权人邮编"; 
	public String zqryb_v = "" ; 
	public static final String ZQRYB_C = "[#我方主体邮编#]" ;
	
	public static final String ZQRDH_D = "债权人电话"; 
	public String zqrdh_v = "" ; 
	public static final String ZQRDH_C = "[#我方主体电话#]" ;
	
	public static final String ZQRCZ_D = "债权人传真"; 
	public String zqrcz_v = "" ; 
	public static final String ZQRCZ_C = "[#我方主体传真#]" ;
	
	public static final String ZQFFDDBR_D = "债权方法定代表人"; 
	public String zqffddbr_v = "" ; 
	public static final String ZQFFDDBR_C = "[#我方主体法定代表人#]" ;
	
	
	
	public String getZqrmc_v() {
		return zqrmc_v;
	}
	public void setZqrmc_v(String zqrmcV) {
		zqrmc_v = zqrmcV;
	}
	public String getZqrdz_v() {
		return zqrdz_v;
	}
	public void setZqrdz_v(String zqrdzV) {
		zqrdz_v = zqrdzV;
	}
	public String getZqryb_v() {
		return zqryb_v;
	}
	public void setZqryb_v(String zqrybV) {
		zqryb_v = zqrybV;
	}
	public String getZqrdh_v() {
		return zqrdh_v;
	}
	public void setZqrdh_v(String zqrdhV) {
		zqrdh_v = zqrdhV;
	}
	public String getZqrcz_v() {
		return zqrcz_v;
	}
	public void setZqrcz_v(String zqrczV) {
		zqrcz_v = zqrczV;
	}
	public String getZqffddbr_v() {
		return zqffddbr_v;
	}
	public void setZqffddbr_v(String zqffddbrV) {
		zqffddbr_v = zqffddbrV;
	}
	public static String getZqrmcD() {
		return ZQRMC_D;
	}
	public static String getZqrmcC() {
		return ZQRMC_C;
	}
	public static String getZqrdzD() {
		return ZQRDZ_D;
	}
	public static String getZqrdzC() {
		return ZQRDZ_C;
	}
	public static String getZqrybD() {
		return ZQRYB_D;
	}
	public static String getZqrybC() {
		return ZQRYB_C;
	}
	public static String getZqrdhD() {
		return ZQRDH_D;
	}
	public static String getZqrdhC() {
		return ZQRDH_C;
	}
	public static String getZqrczD() {
		return ZQRCZ_D;
	}
	public static String getZqrczC() {
		return ZQRCZ_C;
	}
	public static String getZqffddbrD() {
		return ZQFFDDBR_D;
	}
	public static String getZqffddbrC() {
		return ZQFFDDBR_C;
	}
	public String getBzrmc_v() {
		return bzrmc_v;
	}
	public void setBzrmc_v(String bzrmcV) {
		bzrmc_v = bzrmcV;
	}
	public String getBzrzjzl_v() {
		return bzrzjzl_v;
	}
	public void setBzrzjzl_v(String bzrzjzlV) {
		bzrzjzl_v = bzrzjzlV;
	}
	public String getBzrzjhm_v() {
		return bzrzjhm_v;
	}
	public void setBzrzjhm_v(String bzrzjhmV) {
		bzrzjhm_v = bzrzjhmV;
	}
	public String getBzrdz_v() {
		return bzrdz_v;
	}
	public void setBzrdz_v(String bzrdzV) {
		bzrdz_v = bzrdzV;
	}
	public String getBzryzbm_v() {
		return bzryzbm_v;
	}
	public void setBzryzbm_v(String bzryzbmV) {
		bzryzbm_v = bzryzbmV;
	}
	public String getBzrlxdh_v() {
		return bzrlxdh_v;
	}
	public void setBzrlxdh_v(String bzrlxdhV) {
		bzrlxdh_v = bzrlxdhV;
	}
	public String getBzrczhm_v() {
		return bzrczhm_v;
	}
	public void setBzrczhm_v(String bzrczhmV) {
		bzrczhm_v = bzrczhmV;
	}
	public String getBzffddbr_v() {
		return bzffddbr_v;
	}
	public void setBzffddbr_v(String bzffddbrV) {
		bzffddbr_v = bzffddbrV;
	}
	public String getBzffddbrzw_v() {
		return bzffddbrzw_v;
	}
	public void setBzffddbrzw_v(String bzffddbrzwV) {
		bzffddbrzw_v = bzffddbrzwV;
	}
	public String getBzfyyzzh_v() {
		return bzfyyzzh_v;
	}
	public void setBzfyyzzh_v(String bzfyyzzhV) {
		bzfyyzzh_v = bzfyyzzhV;
	}
	public String getBzrpomc_v() {
		return bzrpomc_v;
	}
	public void setBzrpomc_v(String bzrpomcV) {
		bzrpomc_v = bzrpomcV;
	}
	public String getBzrpozjhm_v() {
		return bzrpozjhm_v;
	}
	public void setBzrpozjhm_v(String bzrpozjhmV) {
		bzrpozjhm_v = bzrpozjhmV;
	}
	public String getDyr_v() {
		return dyr_v;
	}
	public void setDyr_v(String dyrV) {
		dyr_v = dyrV;
	}
	public String getDyrzjzl() {
		return dyrzjzl;
	}
	public void setDyrzjzl(String dyrzjzl) {
		this.dyrzjzl = dyrzjzl;
	}
	public void setDyrzjhm(String dyrzjhm) {
		this.dyrzjhm = dyrzjhm;
	}
	public String getDyrdz_v() {
		return dyrdz_v;
	}
	public void setDyrdz_v(String dyrdzV) {
		dyrdz_v = dyrdzV;
	}
	public String getDyryzbm_v() {
		return dyryzbm_v;
	}
	public void setDyryzbm_v(String dyryzbmV) {
		dyryzbm_v = dyryzbmV;
	}
	public String getDyrdh_v() {
		return dyrdh_v;
	}
	public void setDyrdh_v(String dyrdhV) {
		dyrdh_v = dyrdhV;
	}
	public String getDyrcz_v() {
		return dyrcz_v;
	}
	public void setDyrcz_v(String dyrczV) {
		dyrcz_v = dyrczV;
	}
	public String getDyffddbr_v() {
		return dyffddbr_v;
	}
	public void setDyffddbr_v(String dyffddbrV) {
		dyffddbr_v = dyffddbrV;
	}
	public String getDyffddbrzw_v() {
		return dyffddbrzw_v;
	}
	public void setDyffddbrzw_v(String dyffddbrzwV) {
		dyffddbrzw_v = dyffddbrzwV;
	}
	public String getDyfyyzzhm_v() {
		return dyfyyzzhm_v;
	}
	public void setDyfyyzzhm_v(String dyfyyzzhmV) {
		dyfyyzzhm_v = dyfyyzzhmV;
	}
	public String getDywgyr_v() {
		return dywgyr_v;
	}
	public void setDywgyr_v(String dywgyrV) {
		dywgyr_v = dywgyrV;
	}
	public String getDywmc_v() {
		return dywmc_v;
	}
	public void setDywmc_v(String dywmcV) {
		dywmc_v = dywmcV;
	}
	public String getDywqzbh_v() {
		return dywqzbh_v;
	}
	public void setDywqzbh_v(String dywqzbhV) {
		dywqzbh_v = dywqzbhV;
	}
	public String getDywszd_v() {
		return dywszd_v;
	}
	public void setDywszd_v(String dywszdV) {
		dywszd_v = dywszdV;
	}
	public String getDywgyjz_v() {
		return dywgyjz_v;
	}
	public void setDywgyjz_v(String dywgyjzV) {
		dywgyjz_v = dywgyjzV;
	}
	public String getCzr_v() {
		return czr_v;
	}
	public void setCzr_v(String czrV) {
		czr_v = czrV;
	}
	public String getCzrzjzl_v() {
		return czrzjzl_v;
	}
	public void setCzrzjzl_v(String czrzjzlV) {
		czrzjzl_v = czrzjzlV;
	}
	public String getCzrzjhm_v() {
		return czrzjhm_v;
	}
	public void setCzrzjhm_v(String czrzjhmV) {
		czrzjhm_v = czrzjhmV;
	}
	public String getCzrtxdz_v() {
		return czrtxdz_v;
	}
	public void setCzrtxdz_v(String czrtxdzV) {
		czrtxdz_v = czrtxdzV;
	}
	public String getCzryzbm_v() {
		return czryzbm_v;
	}
	public void setCzryzbm_v(String czryzbmV) {
		czryzbm_v = czryzbmV;
	}
	public String getCzrlxdh_v() {
		return czrlxdh_v;
	}
	public void setCzrlxdh_v(String czrlxdhV) {
		czrlxdh_v = czrlxdhV;
	}
	public String getCzrczhm_v() {
		return czrczhm_v;
	}
	public void setCzrczhm_v(String czrczhmV) {
		czrczhm_v = czrczhmV;
	}
	public String getCzffddbr_v() {
		return czffddbr_v;
	}
	public void setCzffddbr_v(String czffddbrV) {
		czffddbr_v = czffddbrV;
	}
	public String getCzffddbrzw_v() {
		return czffddbrzw_v;
	}
	public void setCzffddbrzw_v(String czffddbrzwV) {
		czffddbrzw_v = czffddbrzwV;
	}
	public String getCzfyyzzhm_v() {
		return czfyyzzhm_v;
	}
	public void setCzfyyzzhm_v(String czfyyzzhmV) {
		czfyyzzhm_v = czfyyzzhmV;
	}
	public String getZywgyr_v() {
		return zywgyr_v;
	}
	public void setZywgyr_v(String zywgyrV) {
		zywgyr_v = zywgyrV;
	}
	public String getZywgyrzjhm_v() {
		return zywgyrzjhm_v;
	}
	public void setZywgyrzjhm_v(String zywgyrzjhmV) {
		zywgyrzjhm_v = zywgyrzjhmV;
	}
	public String getZywmc_v() {
		return zywmc_v;
	}
	public void setZywmc_v(String zywmcV) {
		zywmc_v = zywmcV;
	}
	public String getZywqzbh_v() {
		return zywqzbh_v;
	}
	public void setZywqzbh_v(String zywqzbhV) {
		zywqzbh_v = zywqzbhV;
	}
	public String getZywsl_v() {
		return zywsl_v;
	}
	public void setZywsl_v(String zywslV) {
		zywsl_v = zywslV;
	}
	public String getZywcfdd_v() {
		return zywcfdd_v;
	}
	public void setZywcfdd_v(String zywcfddV) {
		zywcfdd_v = zywcfddV;
	}
	public String getDbrmc_v() {
		return dbrmc_v;
	}
	public void setDbrmc_v(String dbrmcV) {
		dbrmc_v = dbrmcV;
	}
	public String getJjbh_v() {
		return jjbh_v;
	}
	public void setJjbh_v(String jjbhV) {
		jjbh_v = jjbhV;
	}
	public String getJkhtbh_v() {
		return jkhtbh_v;
	}
	public void setJkhtbh_v(String jkhtbhV) {
		jkhtbh_v = jkhtbhV;
	}
	public String getDbhtbh_v() {
		return dbhtbh_v;
	}
	public void setDbhtbh_v(String dbhtbhV) {
		dbhtbh_v = dbhtbhV;
	}
	public String getDydbhtbh_v() {
		return dydbhtbh_v;
	}
	public void setDydbhtbh_v(String dydbhtbhV) {
		dydbhtbh_v = dydbhtbhV;
	}
	public String getZydbhtbh_v() {
		return zydbhtbh_v;
	}
	public void setZydbhtbh_v(String zydbhtbhV) {
		zydbhtbh_v = zydbhtbhV;
	}
	public String getBzdbhtbh_v() {
		return bzdbhtbh_v;
	}
	public void setBzdbhtbh_v(String bzdbhtbhV) {
		bzdbhtbh_v = bzdbhtbhV;
	}
	public static String getBzrmcD() {
		return BZRMC_D;
	}
	public static String getBzrmcC() {
		return BZRMC_C;
	}
	public static String getBzrzjzlD() {
		return BZRZJZL_D;
	}
	public static String getBzrzjzlC() {
		return BZRZJZL_C;
	}
	public static String getBzrzjhmD() {
		return BZRZJHM_D;
	}
	public static String getBzrzjhmC() {
		return BZRZJHM_C;
	}
	public static String getBzrdzD() {
		return BZRDZ_D;
	}
	public static String getBzrdzC() {
		return BZRDZ_C;
	}
	public static String getBzryzbmD() {
		return BZRYZBM_D;
	}
	public static String getBzryzbmC() {
		return BZRYZBM_C;
	}
	public static String getBzrylxdhD() {
		return BZRYLXDH_D;
	}
	public static String getBzrylxdhC() {
		return BZRYLXDH_C;
	}
	public static String getBzrczhmD() {
		return BZRCZHM_D;
	}
	public static String getBzrczhmC() {
		return BZRCZHM_C;
	}
	public static String getBzffddbrD() {
		return BZFFDDBR_D;
	}
	public static String getBzffddbrC() {
		return BZFFDDBR_C;
	}
	public static String getBzffddbrzwD() {
		return BZFFDDBRZW_D;
	}
	public static String getBzffddbrzwC() {
		return BZFFDDBRZW_C;
	}
	public static String getBzfyyzzhD() {
		return BZFYYZZH_D;
	}
	public static String getBzfyyzzhC() {
		return BZFYYZZH_C;
	}
	public static String getBzrpomcD() {
		return BZRPOMC_D;
	}
	public static String getBzrpomcC() {
		return BZRPOMC_C;
	}
	public static String getBzrpozjhmD() {
		return BZRPOZJHM_D;
	}
	public static String getBzrpozjhmC() {
		return BZRPOZJHM_C;
	}
	public static String getDyrD() {
		return DYR_D;
	}
	public static String getDyrC() {
		return DYR_C;
	}
	public static String getDyrzjzlD() {
		return DYRZJZL_D;
	}
	public static String getDyrzjzlC() {
		return DYRZJZL_C;
	}

	public static String getDyrzjhmD() {
		return DYRZJHM_D;
	}
	public String getDyrzjhm() {
		return dyrzjhm;
	}
	public static String getDyrzjhmC() {
		return DYRZJHM_C;
	}
	public static String getDyrdzD() {
		return DYRDZ_D;
	}
	public static String getDyrdzC() {
		return DYRDZ_C;
	}
	public static String getDyryzbmD() {
		return DYRYZBM_D;
	}
	public static String getDyryzbmC() {
		return DYRYZBM_C;
	}
	public static String getDyrdhD() {
		return DYRDH_D;
	}
	public static String getDyrdhC() {
		return DYRDH_C;
	}
	public static String getDyrczD() {
		return DYRCZ_D;
	}
	public static String getDyrczC() {
		return DYRCZ_C;
	}
	public static String getDyffddbrD() {
		return DYFFDDBR_D;
	}
	public static String getDyffddbrC() {
		return DYFFDDBR_C;
	}
	public static String getDyffddbrzwD() {
		return DYFFDDBRZW_D;
	}
	public static String getDyffddbrzwC() {
		return DYFFDDBRZW_C;
	}
	public static String getDyfyyzzhmD() {
		return DYFYYZZHM_D;
	}
	public static String getDyfyyzzhmC() {
		return DYFYYZZHM_C;
	}
	public static String getDywgyrD() {
		return DYWGYR_D;
	}
	public static String getDywgyrC() {
		return DYWGYR_C;
	}
	public static String getDywmcD() {
		return DYWMC_D;
	}
	public static String getDywmcC() {
		return DYWMC_C;
	}
	public static String getDywqzbhD() {
		return DYWQZBH_D;
	}
	public static String getDywqzbhC() {
		return DYWQZBH_C;
	}
	public static String getDywszdD() {
		return DYWSZD_D;
	}
	public static String getDywszdC() {
		return DYWSZD_C;
	}
	public static String getDywgyjzD() {
		return DYWGYJZ_D;
	}
	public static String getDywgyjzC() {
		return DYWGYJZ_C;
	}
	public static String getCzrD() {
		return CZR_D;
	}
	public static String getCzrC() {
		return CZR_C;
	}
	public static String getCzrzjzlD() {
		return CZRZJZL_D;
	}
	public static String getCzrzjzlC() {
		return CZRZJZL_C;
	}
	public static String getCzrzjhmD() {
		return CZRZJHM_D;
	}
	public static String getCzrzjhmC() {
		return CZRZJHM_C;
	}
	public static String getCzrtxdzD() {
		return CZRTXDZ_D;
	}
	public static String getCzrtxdzC() {
		return CZRTXDZ_C;
	}
	public static String getCzryzbmD() {
		return CZRYZBM_D;
	}
	public static String getCzryzbmC() {
		return CZRYZBM_C;
	}
	public static String getCzrlxdhD() {
		return CZRLXDH_D;
	}
	public static String getCzrlxdhC() {
		return CZRLXDH_C;
	}
	public static String getCzrczhmD() {
		return CZRCZHM_D;
	}
	public static String getCzrczhmC() {
		return CZRCZHM_C;
	}
	public static String getCzffddbrD() {
		return CZFFDDBR_D;
	}
	public static String getCzffddbrC() {
		return CZFFDDBR_C;
	}
	public static String getCzffddbrzwD() {
		return CZFFDDBRZW_D;
	}
	public static String getCzffddbrzwC() {
		return CZFFDDBRZW_C;
	}
	public static String getCzfyyzzhmD() {
		return CZFYYZZHM_D;
	}
	public static String getCzfyyzzhmC() {
		return CZFYYZZHM_C;
	}
	public static String getZywgyrD() {
		return ZYWGYR_D;
	}
	public static String getZywgyrC() {
		return ZYWGYR_C;
	}
	public static String getZywgyrzjhmD() {
		return ZYWGYRZJHM_D;
	}
	public static String getZywgyrzjhmC() {
		return ZYWGYRZJHM_C;
	}
	public static String getZywmcD() {
		return ZYWMC_D;
	}
	public static String getZywmcC() {
		return ZYWMC_C;
	}
	public static String getZywqzbhD() {
		return ZYWQZBH_D;
	}
	public static String getZywqzbhC() {
		return ZYWQZBH_C;
	}
	public static String getZywslD() {
		return ZYWSL_D;
	}
	public static String getZywslC() {
		return ZYWSL_C;
	}
	public static String getZywcfddD() {
		return ZYWCFDD_D;
	}
	public static String getZywcfddC() {
		return ZYWCFDD_C;
	}
	public static String getDbrmcD() {
		return DBRMC_D;
	}
	public static String getDbrmcC() {
		return DBRMC_C;
	}
	public static String getJjbhD() {
		return JJBH_D;
	}
	public static String getJjbhC() {
		return JJBH_C;
	}
	public static String getJkhtbhD() {
		return JKHTBH_D;
	}
	public static String getJkhtbhC() {
		return JKHTBH_C;
	}
	public static String getDbhtbhD() {
		return dbhtbh_D;
	}
	public static String getDbhtbhC() {
		return dbhtbh_C;
	}
	public static String getDydbhtbhD() {
		return DYDBHTBH_D;
	}
	public static String getDydbhtbhC() {
		return DYDBHTBH_C;
	}
	public static String getZydbhtbhD() {
		return ZYDBHTBH_D;
	}
	public static String getZydbhtbhC() {
		return ZYDBHTBH_C;
	}
	public static String getBzdbhtbhD() {
		return BZDBHTBH_D;
	}
	public static String getBzdbhtbhC() {
		return BZDBHTBH_C;
	}
	public String getGyr_v() {
		return gyr_v;
	}
	public void setGyr_v(String gyrV) {
		gyr_v = gyrV;
	}
	public static String getGyrD() {
		return GYR_D;
	}
	public static String getGyrC() {
		return GYR_C;
	}
	public String getSycqr_v() {
		return sycqr_v;
	}
	public void setSycqr_v(String sycqrV) {
		sycqr_v = sycqrV;
	}
	public static String getSycqrD() {
		return SYCQR_D;
	}
	public static String getSycqrC() {
		return SYCQR_C;
	}

	

}
