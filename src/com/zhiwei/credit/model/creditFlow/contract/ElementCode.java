package com.zhiwei.credit.model.creditFlow.contract;

public class ElementCode implements java.io.Serializable{

	public static final String ZMG_D = "中铭担保"; 
	public String zmg_v = "" ; 
	public static final String ZMG_C = "[#zmg#]" ;
	
	public static final String ZMGA_D = "中铭担保地址";
	public String zmga_v = "" ;
	public static final String ZMGD_C = "[#zmga#]" ;
	
	public static final String ZMGLP_D = "中铭担保法定代表人";
	public String zmglp_v = "" ;
	public static final String ZMGLP_C = "[#zmglp#]" ;
	
	public static final String ZMGP_D = "中铭担保联系电话";
	public String zmgp_v = "" ;
	public static final String ZMGP_C = "[#zmgp#]" ;
	
	public static final String ZMGCA_D = "中铭担保通讯地址";
	public String zmgca_v = "" ;
	public static final String ZMGCA_C = "[#zmgca#]" ;
	                 
	public static final String ZMGF_D = "中铭担保传真号";
	public String zmgf_v = "" ;
	public static final String ZMGF_C = "[#zmgf#]" ;
	
	public static final String ZMGRLN_D = "中铭担保收件人";
	public String zmgrln_v = "" ;
	public static final String ZMGRLN_C = "[#zmgrln#]" ;
	
	public static final String CACN_D = "委托担保合同编号";
	public String cacn_v = "" ;
	public static final String CACN_C = "[#cacn#]";
	
	public static final String CAACN_D = "委托反担保合同编号";
	public String caacn_v = "" ;
	public static final String CAACN_C = "[#caacn#]";
	
	public static final String PLN_D = "承诺函编号";
	public String pln_v = "" ;
	public static final String PLN_C = "[#pln#]";
	
	public static final String LBN_D = "借款银行名称";  // ok
	public String lbn_v = "" ;
	public static final String LBN_C = "[#lbn#]";
	
	public static final String LBNB_D = "借款银行支行名称"; 
	public String lbnb_v = "" ;
	public static final String LBNB_C = "[#lbnb#]";
	
	public static final String C_D = "币种"; //ok
	public String c_v = "" ;
	public static final String C_C = "[#c#]";
	
	public static final String LMC_D = "担保金额(万元)"; // ok
	public String lmc_v = "" ;
	public static final String LMC_C = "[#lmc#]";
	
	public static final String LMCB_D = "担保金额（大写）";  //ok
	public String lmcb_v = "" ;
	public static final String LMCB_C = "[#lmcb#]";
	
	public static final String LT_D = "担保期限（月）";   //ok
	public String lt_v = "" ;
	public static final String LT_C = "[#lt#]";
	
	public static final String FP_D = "资金用途";  //ok
	public String fp_v = "" ;
	public static final String FP_C = "[#fp#]";
	
	public static final String LIR_D = "借款利率（%）";   
	public String lir_v = "" ;
	public static final String LIR_C = "[#lir#]";
	
	public static final String CT_D = "信贷种类";
	public String ct_v = "" ;
	public static final String CT_C = "[#ct#]";
	
	public static final String AMCL_D = "担保金额（万元）";//ok
	public String amcl_v = "" ;
	public static final String AMCL_C = "[#amcl#]";
	
	public static final String AMCB_D = "担保金额（大写）";//ok
	public String amcb_v = "" ;
	public static final String AMCB_C = "[#amcb#]";
	
	public static final String PRL_D = "保费费率(小写：___%/年)"; //ok
	public String prl_v = "" ;
	public static final String PRL_C = "[#prl#]";
	
	public static final String PRB_D = "保费费率（大写：百分之___/年）"; //ok
	public String prb_v = "" ;
	public static final String PRB_C = "[#prb#]";
	
	public static final String PCL_D = "保费金额（元）";  // ok
	public String pcl_v = "" ;      //贷款金额*保费费率
	public static final String PCL_C = "[#pcl#]";
	
	public static final String PCB_D = "保费金额（大写）";  //ok
	public String pcb_v = "" ;
	public static final String PCB_C = "[#pcb#]";
	
	public static final String DPR_D = "展期保费费率(___%/月)";   
	public String dpr_v = "" ;
	public static final String DPR_C = "[#dpr#]";
	
	public static final String OPR_D = "逾期保费费率(___%/月)";  
	public String opr_v = "" ;
	public static final String OPR_C = "[#opr#]";
	
	public static final String CMR_D = "保证金比率(%)"; // ok
	public String cmr_v = "" ;
	public static final String CMR_C = "[#cmr#]";
	
	public static final String CMCL_D = "保证金金额（万元）";  // ok
	public String cmcl_v = "" ;     //贷款金额*保证金比率
	public static final String CMCL_C = "[#cmcl#]";
	
	public static final String CMCB_D = "保证金金额（大写）"; // ok
	public String cmcb_v = "" ;     //贷款金额*保证金比率
	public static final String CMCB_C = "[#cmcb#]";
	
	public static final String CC_D = "法人委托人";
	public String cc_v = "" ;
	public static final String CC_C = "[#cc#]";
	
	public static final String CCA_D = "法人委托人地址";
	public String cca_v = "" ;
	public static final String CCA_C = "[#cca#]";
	
	public static final String CCLM_D = "法人委托人的法定代表人";
	public String cclm_v = "" ;
	public static final String CCLM_C = "[#cclm#]";
	
	public static final String CCP_D = "法人委托人的联系电话";
	public String ccp_v = "" ;
	public static final String CCP_C = "[#ccp#]";
	
	public static final String CCCA_D = "法人委托人的通信地址";
	public String ccca_v = "" ;
	public static final String CCCA_C = "[#ccca#]";
	
	public static final String CCF_D = "法人委托人的传真号码";
	public String ccf_v = "" ;
	public static final String CCF_C = "[#ccf#]";
	
	public static final String CCRLM_D = "法人委托人的收件人";
	public String ccrlm_v = "" ;
	public static final String CCRLM_C = "[#ccrlm#]";
	
	public static final String NPC_D = "自然人委托人";
	public String npc_v = "" ;
	public static final String NPC_C = "[#npc#]";
	
	public static final String NPCCN_D = "自然人委托人的身份证号码";
	public String npccn_v = "" ;
	public static final String NPCCN_C = "[#npccn#]";
	
	public static final String NPCA_D = "自然人委托人的住址";
	public String npca_v = "" ;
	public static final String NPCA_C = "[#npca#]";
	
	public static final String NPCP_D = "自然人委托人的联系电话";
	public String npcp_v = "" ;
	public static final String NPCP_C = "[#npcp#]";
	
	public static final String NPCCA_D = "自然人委托人通讯地址";
	public String npcca_v = "" ;
	public static final String NPCCA_C = "[#npcca#]";
	
	public static final String NPCF_D = "自然人委托人传真号码";
	public String npcf_v = "" ;
	public static final String NPCF_C = "[#npcf#]";
	
	public static final String NPCRLM_D = "自然人委托人收件人";
	public String npcrlm_v = "" ;
	public static final String NPCRLM_C = "[#npcrlm#]";
	
	public static final String CA_D = "法人保证人";
	public String ca_v = "" ;
	public static final String CA_C = "[#ca#]";
	
	public static final String CAA_D = "法人保证人住所";
	public String caa_v = "" ;
	public static final String CAA_C = "[#caa#]";
	
	public static final String CALM_D = "法人保证人的法定代表人";
	public String calm_v = "" ;
	public static final String CALM_C = "[#calm#]";
	
	public static final String CAP_D = "法人保证人的联系电话";
	public String cap_v = "" ;
	public static final String CAP_C = "[#cap#]";
	
	public static final String RACN_D = "反担保合同编号";
	public String racn_v = "" ;
	public static final String RACN_C = "[#racn#]";
	
	public static final String NP_D = "自然人保证人";
	public String np_v = "" ;
	public static final String NP_C = "[#np#]";
	
	public static final String NPCN_D = "自然人保证人的身份证号码";
	public String npcn_v = "" ;
	public static final String NPCN_C = "[#npcn#]";
	
	public static final String NPA_D = "自然人保证人的住址";
	public String npa_v = "" ;
	public static final String NPA_C = "[#npa#]";
	
	public static final String NPP_D = "自然人保证人的联系电话";
	public String npp_v = "" ;
	public static final String NPP_C = "[#npp#]";

	public static final String CM_D = "法人抵押人";
	public String cm_v = "" ;
	public static final String CM_C = "[#cm#]";
	
	public static final String CMA_D = "法人抵押人住所";
	public String cma_v = "" ;
	public static final String CMA_C = "[#cma#]";
	
	public static final String CMLM_D = "法人抵押人的法定代表人";
	public String cmlm_v = "" ;
	public static final String CMLM_C = "[#cmlm#]";
	
	public static final String CMP_D = "法人抵押人的联系电话";
	public String cmp_v = "" ;
	public static final String CMP_C = "[#cmp#]";
	
	public static final String NPM_D = "自然人抵押人";
	public String npm_v = "" ;
	public static final String NPM_C = "[#npm#]";
	
	public static final String NPMA_D = "自然抵押人住所";
	public String npma_v = "" ;
	public static final String NPMA_C = "[#npma#]";
	
	public static final String NPMCN_D = "自然抵押人的身份证号码";
	public String npmcn_v = "" ;
	public static final String NPMCN_C = "[#npmcn#]";
	
	public static final String NPMP_D = "自然抵押人的联系电话";
	public String npmp_v = "" ;
	public static final String NPMP_C = "[#npmp#]";
	
	public static final String G_D = "抵押物";
	public String g_v = "" ;
	public static final String G_C = "[#g#]";
	
	public static final String LMP_D = "法人质押人";
	public String lmp_v = "" ;
	public static final String LMP_C = "[#lmp#]";
	
	public static final String LMPA_D = "法人质押人住所";
	public String lmpa_v = "" ;
	public static final String LMPA_C = "[#lmpa#]";
	
	public static final String LMPLM_D = "法人质押人的法定代表人";
	public String lmplm_v = "" ;
	public static final String LMPLM_C = "[#lmplm#]";
	
	public static final String LMPP_D = "法人质押人的联系电话 ";
	public String lmpp_v = "" ;
	public static final String LMPP_C = "[#lmpp#]";
	
	public static final String LMPCA_D = "法人质押人公司通信地址 ";
	public String lmpca_v = "" ;
	public static final String LMPCA_C = "[#lmpca#]";
	
	public static final String LMPF_D = "法人质押人公司传真号码 ";
	public String lmpf_v = "" ;
	public static final String LMPF_C = "[#lmpf#]";
	
	public static final String LMPRLM_D = "法人质押人公司收件人";
	public String lmprlm_v = "" ;
	public static final String LMPRLM_C = "[#lmprlm#]";
	
	public static final String NPPL_D = "自然人质押人 ";
	public String nppl_v = "" ;
	public static final String NPPL_C = "[#nppl#]";
	
	public static final String NPPLA_D = "自然人质押人住所 ";
	public String nppla_v = "" ;
	public static final String NPPLA_C = "[#nppla#]";
	
	public static final String NPPLCN_D = "自然质押人的身份证号码 ";
	public String npplcn_v = "" ;  
	public static final String NPPLCN_C = "[#npplcn#]";
	
	public static final String NPPLP_D = "自然质押人的联系电话 ";
	public String npplp_v = "" ;  
	public static final String NPPLP_C = "[#npplp#]";
	
	public static final String RPCO_D = "权利质押出质标的 ";
	public String rpco_v = "" ;  
	public static final String RPCO_C = "[#rpco#]";
	
	public static final String PSR_D = "质押股权 ";
	public String psr_v = "" ;  
	public static final String PSR_C = "[#psr#]";
	
	public static final String PRAM_D = "质押反担保金额（该金额为总债权金额*所占股权比例） ";
	public String pram_v = "" ;  
	public static final String PRAM_C = "[#pram#]";
	
	public static final String TCRS_D = "占总债权总额的比例（_%,与持有的股权比例一致） ";
	public String tcrs_v = "" ;  
	public static final String TCRS_C = "[#tcrs#]";
	
	public static final String BRPRCT_D = "应收帐款质押人登记证件种类 ";
	public String brprct_v = "" ;  
	public static final String BRPRCT_C = "[#brprct#]";
	
	public static final String BRPRCN_D = "应收帐款质押人登记证件号码 ";
	public String brprcn_v = "" ;  
	public static final String BRPRCN_C = "[#brprcn#]";
	
	public static final String BRPPC_D = "应收账款质押人邮政编码 ";
	public String brppc_v = "" ; 
	public static final String BRPPC_C = "[#brppc#]";
	
	public static final String BRPF_D = "应收账款质押人传真号码";
	public String brpf_v = "" ; 
	public static final String BRPF_C = "[#brpf#]";
	
	public static final String  ZMBRPRCT_D = "中铭担保应收账款质押登记证件种类";
	public String zmbrprct_v = "" ; 
	public static final String ZMBRPRCT_C = "[#zmbrprct#]";
	
	public static final String  ZMBRPRCN_D = "中铭担保应收账款质押登记证件号码";
	public String zmbrprcn_v = "" ; 
	public static final String ZMBRPRCN_C = "[#zmbrprcn#]";
	
	public static final String BRPCY_D = "《应收账款质押合同》日期，年";
	public String brpcy_v = "" ; 
	public static final String BRPCY_C = "[#brpcy#]";
	
	public static final String BRPCM_D = "《应收账款质押合同》日期，月";
	public String brpcm_v = "" ; 
	public static final String BRPCM_C = "[#brpcm#]";
	
	public static final String BRPCD_D = "《应收账款质押合同》日期，日";
	public String brpcd_v = "" ;
	public static final String BRPCD_C = "[#brpcd#]";
	
/*	public static final String CLAUSE_D = "补充条款";
	public String clause_v = "" ;
	public static final String CLAUSE_C = "[#clause#]";*/
	
	//3.11 最高额贷款
	public static final String MHCM_D = "最高额合同金额(万元) 小写";  // 授信金额
	public String mhcm_v ="";
	public static final String MHCM_C = "[#mhcm#]";
	
	public static final String MHCMB_D = "最高额合同金额(万元) 大写";
	public String mhcmb_v ="";
	public static final String MHCMB_C = "[#mhcmb#]";
	
	public static final String MHCT_D = "最高额合同期限（月）"; //   授信期限
	public String mhct_v ="";
	public static final String MHCT_C = "[#mhct#]";
	
	public static final String FIRSTBM_D = "第一次借款金额(万元) 小写";
	public String firstbm_v ="";
	public static final String FIRSTBM_C = "[#firstbm#]";
	
	public static final String FIRSTBMB_D = "第一次借款金额(万元) 大写";
	public String firstbmb_v ="";
	public static final String FIRSTBMB_C = "[#firstbmb#]";
	
	public static final String FIRSTBT_D = "第一次借款期限";
	public String firstbt_v ="";
	public static final String FIRSTBT_C = "[#firstbt#]";
	
	public static final String FIRSTPR_D = "首期保费费率"; 
	public String firstpr_v ="";
	public static final String FIRSTPR_C = "[#firstpr#]";
	
	public static final String FIRSTPRB_D = "首期保费费率 （大写）"; 
	public String firstprb_v ="";
	public static final String FIRSTPRB_C = "[#firstprb#]";
	
	public static final String AGAINPR_D = "再次保费费率";
	public String againpr_v ="";
	public static final String AGAINPR_C = "[#againpr#]";
	
	public static final String AGAINPRB_D = "再次保费费率（大写）";
	public String againprb_v ="";
	public static final String AGAINPRB_C = "[#againprb#]";
	
	public static final String FIRSTLPT_D = "第一次借款保费总额（元）";  //第一次借款金额 * 第一次借款期限 * 首期保费费率
	public String firstlpt_v ="";
	public static final String FIRSTLPT_C = "[#firstlpt#]";
	
	public static final String PRET_D = "保费总额";  //授信金额 * 授信期限 * 首期保费费率
	public String pret_v ="";
	public static final String PRET_C = "[#pret#]";
	
	public static final String PRETB_D = "保费总额（大写）";  
	public String pretb_v ="";
	public static final String PRETB_C = "[#pretb#]";
	
	public static final String TFYPM_D = "第一年度保费金额";   //授信金额 * 1 * 首期保费费率
	public String tfypm_v ="";
	public static final String TFYPM_C = "[#tfypm#]";
	
	public static final String TFYPMB_D = "第一年度保费金额（大写）";  
	public String tfypmb_v ="";
	public static final String TFYPMB_C = "[#tfypmb#]";
	
	public static final String SYPM_D = "剩余年度保费金额";   //授信金额 * （授信期限-12）换算年，四舍五入 * 首期保费费率
	public String sypm_v ="";
	public static final String SYPM_C = "[#sypm#]";
	
	public static final String SYPMB_D = "剩余年度保费金额（大写）"; 
	public String sypmb_v ="";
	public static final String SYPMB_C = "[#sypmb#]";
	
	public static final String STARTDATE_D = "承诺函起始日期"; 
	public String startdate_v ="";
	public static final String STARTDATE_C = "[#startdate#]";
	
	public static final String ENDDATE_D = "承诺函截止日期"; 
	public String enddate_v ="";
	public static final String ENDDATE_C = "[#enddate#]";
	
	public static final String NDBYXSBH_D = "拟担保意向书编号"; 
	public String ndbyxsbh_v ="";
	public static final String NDBYXSBH_C = "[#ndbyxsbh#]";

	public static final String JKRXM_D = "借款人姓名"; 
	public String jkrxm_v ="";
	public static final String JKRXM_C = "[#jkrxm#]";
	
	public static final String JKRZJLX_D = "借款人证件类型"; 
	public String jkrzjlx_v ="";
	public static final String JKRZJLX_C = "[#jkrzjlx#]";
	
	public static final String JKRZJHM_D = "借款人证件号码"; 
	public String jkrzjhm_v ="";
	public static final String JKRZJHM_C = "[#jkrzjhm#]";
	
	public static final String JKRJTZZ_D = "借款人家庭住址"; 
	public String jkrjtzz_v ="";
	public static final String JKRJTZZ_C = "[#jkrjtzz#]";
	
	public static final String JKRJTDH_D = "借款人家庭电话"; 
	public String jkrjtdh_v ="";
	public static final String JKRJTDH_C = "[#jkrjtdh#]";
	
	public static final String JKRSJ_D = "借款人手机"; 
	public String jkrsj_v ="";
	public static final String JKRSJ_C = "[#jkrsj#]";
	
	public static final String FRTXDZ_D = "法人通讯地址"; 
	public String frtxdz_v ="";
	public static final String FRTXDZ_C = "[#frtxdz#]";
	
	public static final String FRCZHM_D = "法人传真号码"; 
	public String frczhm_v ="";
	public static final String FRCZHM_C = "[#frczhm#]";
	
	public static final String FRSJR_D = "法人收件人"; 
	public String frsjr_v ="";
	public static final String FRSJR_C = "[#frsjr#]";
	
	public static final String ZRRTXDZ_D = "自然人通讯地址"; 
	public String zrrtxdz_v ="";
	public static final String ZRRTXDZ_C = "[#zrrtxdz#]";
	
	public static final String ZRRCZHM_D = "自然人传真号码"; 
	public String zrrczhm_v ="";
	public static final String ZRRCZHM_C = "[#zrrczhm#]";
	
	public static final String ZRRSJR_D = "自然人收件人"; 
	public String zrrsjr_v ="";
	public static final String ZRRSJR_C = "[#zrrsjr#]";
		
	public String getZmg_v() {
		return zmg_v;
	}
	public void setZmg_v(String zmgV) {
		zmg_v = zmgV;
	}
	public String getZmga_v() {
		return zmga_v;
	}
	public void setZmga_v(String zmgaV) {
		zmga_v = zmgaV;
	}
	public String getZmglp_v() {
		return zmglp_v;
	}
	public void setZmglp_v(String zmglpV) {
		zmglp_v = zmglpV;
	}
	public String getZmgp_v() {
		return zmgp_v;
	}
	public void setZmgp_v(String zmgpV) {
		zmgp_v = zmgpV;
	}
	public String getZmgca_v() {
		return zmgca_v;
	}
	public void setZmgca_v(String zmgcaV) {
		zmgca_v = zmgcaV;
	}
	public String getZmgf_v() {
		return zmgf_v;
	}
	public void setZmgf_v(String zmgfV) {
		zmgf_v = zmgfV;
	}
	public String getZmgrln_v() {
		return zmgrln_v;
	}
	public void setZmgrln_v(String zmgrlnV) {
		zmgrln_v = zmgrlnV;
	}
	public String getCacn_v() {
		return cacn_v;
	}
	public void setCacn_v(String cacnV) {
		cacn_v = cacnV;
	}
	public String getLbn_v() {
		return lbn_v;
	}
	public void setLbn_v(String lbnV) {
		lbn_v = lbnV;
	}
	public String getLmc_v() {
		return lmc_v;
	}
	public void setLmc_v(String lmcV) {
		lmc_v = lmcV;
	}
	public String getLt_v() {
		return lt_v;
	}
	public void setLt_v(String ltV) {
		lt_v = ltV;
	}
	public String getFp_v() {
		return fp_v;
	}
	public void setFp_v(String fpV) {
		fp_v = fpV;
	}
	public String getLir_v() {
		return lir_v;
	}
	public void setLir_v(String lirV) {
		lir_v = lirV;
	}
	public String getAmcl_v() {
		return amcl_v;
	}
	public void setAmcl_v(String amclV) {
		amcl_v = amclV;
	}
	public String getAmcb_v() {
		return amcb_v;
	}
	public void setAmcb_v(String amcbV) {
		amcb_v = amcbV;
	}
	public String getPrl_v() {
		return prl_v;
	}
	public void setPrl_v(String prlV) {
		prl_v = prlV;
	}
	public String getPrb_v() {
		return prb_v;
	}
	public void setPrb_v(String prbV) {
		prb_v = prbV;
	}
	public String getPcl_v() {
		return pcl_v;
	}
	public void setPcl_v(String pclV) {
		pcl_v = pclV;
	}
	public String getPcb_v() {
		return pcb_v;
	}
	public void setPcb_v(String pcbV) {
		pcb_v = pcbV;
	}
	public String getDpr_v() {
		return dpr_v;
	}
	public void setDpr_v(String dprV) {
		dpr_v = dprV;
	}
	public String getOpr_v() {
		return opr_v;
	}
	public void setOpr_v(String oprV) {
		opr_v = oprV;
	}
	public String getCmr_v() {
		return cmr_v;
	}
	public void setCmr_v(String cmrV) {
		cmr_v = cmrV;
	}
	public String getCmcl_v() {
		return cmcl_v;
	}
	public void setCmcl_v(String cmclV) {
		cmcl_v = cmclV;
	}
	public String getCmcb_v() {
		return cmcb_v;
	}
	public void setCmcb_v(String cmcbV) {
		cmcb_v = cmcbV;
	}
	public String getCc_v() {
		return cc_v;
	}
	public void setCc_v(String ccV) {
		cc_v = ccV;
	}
	public String getCca_v() {
		return cca_v;
	}
	public void setCca_v(String ccaV) {
		cca_v = ccaV;
	}
	public String getCclm_v() {
		return cclm_v;
	}
	public void setCclm_v(String cclmV) {
		cclm_v = cclmV;
	}
	public String getCcp_v() {
		return ccp_v;
	}
	public void setCcp_v(String ccpV) {
		ccp_v = ccpV;
	}
	public String getCcca_v() {
		return ccca_v;
	}
	public void setCcca_v(String cccaV) {
		ccca_v = cccaV;
	}
	public String getCcf_v() {
		return ccf_v;
	}
	public void setCcf_v(String ccfV) {
		ccf_v = ccfV;
	}
	public String getCcrlm_v() {
		return ccrlm_v;
	}
	public void setCcrlm_v(String ccrlmV) {
		ccrlm_v = ccrlmV;
	}
	public String getNpc_v() {
		return npc_v;
	}
	public void setNpc_v(String npcV) {
		npc_v = npcV;
	}
	public String getNpccn_v() {
		return npccn_v;
	}
	public void setNpccn_v(String npccnV) {
		npccn_v = npccnV;
	}
	public String getNpca_v() {
		return npca_v;
	}
	public void setNpca_v(String npcaV) {
		npca_v = npcaV;
	}
	public String getNpcp_v() {
		return npcp_v;
	}
	public void setNpcp_v(String npcpV) {
		npcp_v = npcpV;
	}
	public String getCa_v() {
		return ca_v;
	}
	public void setCa_v(String caV) {
		ca_v = caV;
	}
	public String getCaa_v() {
		return caa_v;
	}
	public void setCaa_v(String caaV) {
		caa_v = caaV;
	}
	public String getCalm_v() {
		return calm_v;
	}
	public void setCalm_v(String calmV) {
		calm_v = calmV;
	}
	public String getCap_v() {
		return cap_v;
	}
	public void setCap_v(String capV) {
		cap_v = capV;
	}
	public String getRacn_v() {
		return racn_v;
	}
	public void setRacn_v(String racnV) {
		racn_v = racnV;
	}
	public String getNp_v() {
		return np_v;
	}
	public void setNp_v(String npV) {
		np_v = npV;
	}
	public String getNpcn_v() {
		return npcn_v;
	}
	public void setNpcn_v(String npcnV) {
		npcn_v = npcnV;
	}
	public String getNpa_v() {
		return npa_v;
	}
	public void setNpa_v(String npaV) {
		npa_v = npaV;
	}
	public String getNpp_v() {
		return npp_v;
	}
	public void setNpp_v(String nppV) {
		npp_v = nppV;
	}
	public String getCm_v() {
		return cm_v;
	}
	public void setCm_v(String cmV) {
		cm_v = cmV;
	}
	public String getCma_v() {
		return cma_v;
	}
	public void setCma_v(String cmaV) {
		cma_v = cmaV;
	}
	public String getCmlm_v() {
		return cmlm_v;
	}
	public void setCmlm_v(String cmlmV) {
		cmlm_v = cmlmV;
	}
	public String getCmp_v() {
		return cmp_v;
	}
	public void setCmp_v(String cmpV) {
		cmp_v = cmpV;
	}
	public String getNpm_v() {
		return npm_v;
	}
	public void setNpm_v(String npmV) {
		npm_v = npmV;
	}
	public String getNpma_v() {
		return npma_v;
	}
	public void setNpma_v(String npmaV) {
		npma_v = npmaV;
	}
	public String getNpmcn_v() {
		return npmcn_v;
	}
	public void setNpmcn_v(String npmcnV) {
		npmcn_v = npmcnV;
	}
	public String getNpmp_v() {
		return npmp_v;
	}
	public void setNpmp_v(String npmpV) {
		npmp_v = npmpV;
	}
	public String getG_v() {
		return g_v;
	}
	public void setG_v(String gV) {
		g_v = gV;
	}
	public String getLmp_v() {
		return lmp_v;
	}
	public void setLmp_v(String lmpV) {
		lmp_v = lmpV;
	}
	public String getLmpa_v() {
		return lmpa_v;
	}
	public void setLmpa_v(String lmpaV) {
		lmpa_v = lmpaV;
	}
	public String getLmplm_v() {
		return lmplm_v;
	}
	public void setLmplm_v(String lmplmV) {
		lmplm_v = lmplmV;
	}
	public String getLmpp_v() {
		return lmpp_v;
	}
	public void setLmpp_v(String lmppV) {
		lmpp_v = lmppV;
	}
	public String getNppl_v() {
		return nppl_v;
	}
	public void setNppl_v(String npplV) {
		nppl_v = npplV;
	}
	public String getNppla_v() {
		return nppla_v;
	}
	public void setNppla_v(String npplaV) {
		nppla_v = npplaV;
	}
	public String getNpplcn_v() {
		return npplcn_v;
	}
	public void setNpplcn_v(String npplcnV) {
		npplcn_v = npplcnV;
	}
	public String getNpplp_v() {
		return npplp_v;
	}
	public void setNpplp_v(String npplpV) {
		npplp_v = npplpV;
	}
	public String getRpco_v() {
		return rpco_v;
	}
	public void setRpco_v(String rpcoV) {
		rpco_v = rpcoV;
	}
	public String getPsr_v() {
		return psr_v;
	}
	public void setPsr_v(String psrV) {
		psr_v = psrV;
	}
	public String getPram_v() {
		return pram_v;
	}
	public void setPram_v(String pramV) {
		pram_v = pramV;
	}
	public String getTcrs_v() {
		return tcrs_v;
	}
	public void setTcrs_v(String tcrsV) {
		tcrs_v = tcrsV;
	}
	public String getBrprct_v() {
		return brprct_v;
	}
	public void setBrprct_v(String brprctV) {
		brprct_v = brprctV;
	}
	public String getBrprcn_v() {
		return brprcn_v;
	}
	public void setBrprcn_v(String brprcnV) {
		brprcn_v = brprcnV;
	}
	public String getBrppc_v() {
		return brppc_v;
	}
	public void setBrppc_v(String brppcV) {
		brppc_v = brppcV;
	}
	public String getBrpf_v() {
		return brpf_v;
	}
	public void setBrpf_v(String brpfV) {
		brpf_v = brpfV;
	}
	public String getZmbrprct_v() {
		return zmbrprct_v;
	}
	public void setZmbrprct_v(String zmbrprctV) {
		zmbrprct_v = zmbrprctV;
	}
	public String getZmbrprcn_v() {
		return zmbrprcn_v;
	}
	public void setZmbrprcn_v(String zmbrprcnV) {
		zmbrprcn_v = zmbrprcnV;
	}
	public String getBrpcy_v() {
		return brpcy_v;
	}
	public void setBrpcy_v(String brpcyV) {
		brpcy_v = brpcyV;
	}
	public String getBrpcm_v() {
		return brpcm_v;
	}
	public void setBrpcm_v(String brpcmV) {
		brpcm_v = brpcmV;
	}
	public String getBrpcd_v() {
		return brpcd_v;
	}
	public void setBrpcd_v(String brpcdV) {
		brpcd_v = brpcdV;
	}
	public String getCaacn_v() {
		return caacn_v;
	}
	public void setCaacn_v(String caacnV) {
		caacn_v = caacnV;
	}
	public String getPln_v() {
		return pln_v;
	}
	public void setPln_v(String plnV) {
		pln_v = plnV;
	}
	public String getLbnb_v() {
		return lbnb_v;
	}
	public void setLbnb_v(String lbnbV) {
		lbnb_v = lbnbV;
	}
	public String getC_v() {
		return c_v;
	}
	public void setC_v(String cV) {
		c_v = cV;
	}
	public String getLmcb_v() {
		return lmcb_v;
	}
	public void setLmcb_v(String lmcbV) {
		lmcb_v = lmcbV;
	}
	public String getCt_v() {
		return ct_v;
	}
	public void setCt_v(String ctV) {
		ct_v = ctV;
	}
	public String getNpcca_v() {
		return npcca_v;
	}
	public void setNpcca_v(String npccaV) {
		npcca_v = npccaV;
	}
	public String getNpcf_v() {
		return npcf_v;
	}
	public void setNpcf_v(String npcfV) {
		npcf_v = npcfV;
	}
	public String getNpcrlm_v() {
		return npcrlm_v;
	}
	public void setNpcrlm_v(String npcrlmV) {
		npcrlm_v = npcrlmV;
	}
	public String getLmpca_v() {
		return lmpca_v;
	}
	public void setLmpca_v(String lmpcaV) {
		lmpca_v = lmpcaV;
	}
	public String getLmpf_v() {
		return lmpf_v;
	}
	public void setLmpf_v(String lmpfV) {
		lmpf_v = lmpfV;
	}
	public String getLmprlm_v() {
		return lmprlm_v;
	}
	public void setLmprlm_v(String lmprlmV) {
		lmprlm_v = lmprlmV;
	}
	
	//最高额贷款
	public String getMhcm_v() {
		return mhcm_v;
	}
	public void setMhcm_v(String mhcmV) {
		mhcm_v = mhcmV;
	}
	public String getMhcmb_v() {
		return mhcmb_v;
	}
	public void setMhcmb_v(String mhcmbV) {
		mhcmb_v = mhcmbV;
	}
	public String getMhct_v() {
		return mhct_v;
	}
	public void setMhct_v(String mhctV) {
		mhct_v = mhctV;
	}
	public String getFirstbm_v() {
		return firstbm_v;
	}
	public void setFirstbm_v(String firstbmV) {
		firstbm_v = firstbmV;
	}
	public String getFirstbmb_v() {
		return firstbmb_v;
	}
	public void setFirstbmb_v(String firstbmbV) {
		firstbmb_v = firstbmbV;
	}
	public String getFirstbt_v() {
		return firstbt_v;
	}
	public void setFirstbt_v(String firstbtV) {
		firstbt_v = firstbtV;
	}
	public String getFirstpr_v() {
		return firstpr_v;
	}
	public void setFirstpr_v(String firstprV) {
		firstpr_v = firstprV;
	}
	public String getFirstprb_v() {
		return firstprb_v;
	}
	public void setFirstprb_v(String firstprbV) {
		firstprb_v = firstprbV;
	}
	public String getAgainpr_v() {
		return againpr_v;
	}
	public void setAgainpr_v(String againprV) {
		againpr_v = againprV;
	}
	public String getAgainprb_v() {
		return againprb_v;
	}
	public void setAgainprb_v(String againprbV) {
		againprb_v = againprbV;
	}
	public String getFirstlpt_v() {
		return firstlpt_v;
	}
	public void setFirstlpt_v(String firstlptV) {
		firstlpt_v = firstlptV;
	}
	public String getPret_v() {
		return pret_v;
	}
	public void setPret_v(String pretV) {
		pret_v = pretV;
	}
	public String getPretb_v() {
		return pretb_v;
	}
	public void setPretb_v(String pretbV) {
		pretb_v = pretbV;
	}
	public String getTfypm_v() {
		return tfypm_v;
	}
	public void setTfypm_v(String tfypmV) {
		tfypm_v = tfypmV;
	}
	public String getTfypmb_v() {
		return tfypmb_v;
	}
	public void setTfypmb_v(String tfypmbV) {
		tfypmb_v = tfypmbV;
	}
	public String getSypm_v() {
		return sypm_v;
	}
	public void setSypm_v(String sypmV) {
		sypm_v = sypmV;
	}
	public String getSypmb_v() {
		return sypmb_v;
	}
	public void setSypmb_v(String sypmbV) {
		sypmb_v = sypmbV;
	}
	public String getStartdate_v() {
		return startdate_v;
	}
	public void setStartdate_v(String startdateV) {
		startdate_v = startdateV;
	}
	public String getEnddate_v() {
		return enddate_v;
	}
	public void setEnddate_v(String enddateV) {
		enddate_v = enddateV;
	}
	public String getNdbyxsbh_v() {
		return ndbyxsbh_v;
	}
	public void setNdbyxsbh_v(String ndbyxsbhV) {
		ndbyxsbh_v = ndbyxsbhV;
	}
	public String getJkrxm_v() {
		return jkrxm_v;
	}
	public void setJkrxm_v(String jkrxmV) {
		jkrxm_v = jkrxmV;
	}
	public String getJkrzjlx_v() {
		return jkrzjlx_v;
	}
	public void setJkrzjlx_v(String jkrzjlxV) {
		jkrzjlx_v = jkrzjlxV;
	}
	public String getJkrzjhm_v() {
		return jkrzjhm_v;
	}
	public void setJkrzjhm_v(String jkrzjhmV) {
		jkrzjhm_v = jkrzjhmV;
	}
	public String getJkrjtzz_v() {
		return jkrjtzz_v;
	}
	public void setJkrjtzz_v(String jkrjtzzV) {
		jkrjtzz_v = jkrjtzzV;
	}
	public String getJkrjtdh_v() {
		return jkrjtdh_v;
	}
	public void setJkrjtdh_v(String jkrjtdhV) {
		jkrjtdh_v = jkrjtdhV;
	}
	public String getJkrsj_v() {
		return jkrsj_v;
	}
	public void setJkrsj_v(String jkrsjV) {
		jkrsj_v = jkrsjV;
	}
	public String getFrtxdz_v() {
		return frtxdz_v;
	}
	public void setFrtxdz_v(String frtxdzV) {
		frtxdz_v = frtxdzV;
	}
	public String getFrczhm_v() {
		return frczhm_v;
	}
	public void setFrczhm_v(String frczhmV) {
		frczhm_v = frczhmV;
	}
	public String getFrsjr_v() {
		return frsjr_v;
	}
	public void setFrsjr_v(String frsjrV) {
		frsjr_v = frsjrV;
	}
	public String getZrrtxdz_v() {
		return zrrtxdz_v;
	}
	public void setZrrtxdz_v(String zrrtxdzV) {
		zrrtxdz_v = zrrtxdzV;
	}
	public String getZrrczhm_v() {
		return zrrczhm_v;
	}
	public void setZrrczhm_v(String zrrczhmV) {
		zrrczhm_v = zrrczhmV;
	}
	public String getZrrsjr_v() {
		return zrrsjr_v;
	}
	public void setZrrsjr_v(String zrrsjrV) {
		zrrsjr_v = zrrsjrV;
	}
	
}
