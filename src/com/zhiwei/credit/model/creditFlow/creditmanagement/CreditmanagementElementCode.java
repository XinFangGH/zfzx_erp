package com.zhiwei.credit.model.creditFlow.creditmanagement;

import com.zhiwei.core.model.BaseModel;

public class CreditmanagementElementCode extends BaseModel implements java.io.Serializable {
	/**
	 * 定量要素书写规则
	 * 5个为一组：
	 * _TD表示定量类型：企业和个人
	 * _T表示定量类型企业和个人的key：Enterprise，Person
	 * _D  企业对应企业财务信息中对应财务要素描述汉语或个人家庭经济情况信息财务要素描述
	 * _C  企业对应企业财务信息中对应财务要素描述key值（企业对应js页面是financeInfo.js，）或个人家庭经济情况信息财务要素描述（个人对应js页面是：需要自己找）
	 * 
	 */
	public static final String BBQJ_D = "报表期间"; 
	public String bbqj_v = "" ; 
	public static final String BBQJ_C = "id_xxxx_xxxx128" ;//默认企业和个人的财富表里面的id（第一季度）
	public static final String BBQJ_TD = "企业" ;//表示要素的类型汉语描述
	public static final String BBQJ_T = "Enterprise" ;//表示要素的类型key
	
	public static final String QYZZC_D = "企业:总资产（万元）"; 
	public String qyzzc_v = "" ; 
	public static final String QYZZC_C = "id_xxxx_xxxx124" ;
	public static final String QYZZC_TD = "企业" ;//表示要素的类型汉语描述
	public static final String QYZZC_T = "Enterprise" ;//表示要素的类型key
	
	public static final String QYJZC_D = "企业:净资产(万元)"; 
	public String qyjzc_v = "" ; 
	public static final String QYJZC_C = "id_xxxx_xxxx120" ;
	public static final String QYJZC_TD = "企业" ;//表示要素的类型汉语描述
	public static final String QYJZC_T = "Enterprise" ;//表示要素的类型key
	
	public static final String QYLDZC_D = "企业:流动资产(万元)"; 
	public String qyldzc_v = "" ; 
	public static final String QYLDZC_C = "id_xxxx_xxxx116" ;
	public static final String QYLDZC_TD = "企业" ;//表示要素的类型汉语描述
	public static final String QYLDZC_T = "Enterprise" ;//表示要素的类型key
	
	public static final String QYLDFZ_D = "企业:流动负债(万元)"; 
	public String qyldfz_v = "" ; 
	public static final String QYLDFZ_C = "id_xxxx_xxxx112" ;
	public static final String QYLDFZ_TD = "企业" ;//表示要素的类型汉语描述
	public static final String QYLDFZ_T = "Enterprise" ;//表示要素的类型key
	
/*	public static final String GDZCJZ_D = "企业:固定资产净值(万元)"; 
	public String gdzcjz_v = "" ; 
	public static final String GDZCJZ_C = "[#企业:固定资产净值#]" ;
	public static final String GDZCJZ_TD = "企业" ;//表示要素的类型汉语描述
	public static final String GDZCJZ_T = "Enterprise" ;//表示要素的类型key
*/
        public static final String YYZJ_D = "企业:营运资金(万元)"; 
	public String yyzj_v = "" ; 
	public static final String YYZJ_C = "id_xxxx_xxxx01" ;
	public static final String YYZJ_TD = "企业" ;//表示要素的类型汉语描述
	public static final String YYZJ_T = "Enterprise" ;//表示要素的类型key

        public static final String LDBL_D = "企业:流动比率"; 
	public String ldbl_v = "" ; 
	public static final String LDBL_C = "id_xxxx_xxxx05" ;
	public static final String LDBL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String LDBL_T = "Enterprise" ;//表示要素的类型key

        public static final String SDBL_D = "企业:速动比率"; 
	public String sdbl_v = "" ; 
	public static final String SDBL_C = "id_xxxx_xxxx09" ;
	public static final String SDBL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String SDBL_T = "Enterprise" ;//表示要素的类型key

        public static final String ZCYFZL_D = "企业:资产与负债率"; 
	public String zcyfzl_v = "" ; 
	public static final String ZCYFZL_C = "id_xxxx_xxxx09" ;
	public static final String ZCYFZL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String ZCYFZL_T = "Enterprise" ;//表示要素的类型key

        public static final String FZYSYZQYBL_D = "企业:负债与所有者权益比例"; 
	public String fzysyzqybl_v = "" ; 
	public static final String FZYSYZQYBL_C = "id_xxxx_xxxx17" ;
	public static final String FZYSYZQYBL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String FZYSYZQYBL_T = "Enterprise" ;//表示要素的类型key

        public static final String LXBZBS_D = "企业:利息保障倍数"; 
	public String lxbzbs_v = "" ; 
	public static final String LXBZBS_C = "id_xxxx_xxxx21" ;
	public static final String LXBZBS_TD = "企业" ;//表示要素的类型汉语描述
	public static final String LXBZBS_T = "Enterprise" ;//表示要素的类型key

        public static final String ZYYWSR_D = "企业:主营业务收入(万元)"; 
	public String zyywsr_v = "" ; 
	public static final String ZYYWSR_C = "id_xxxx_xxxx25" ;
	public static final String ZYYWSR_TD = "企业" ;//表示要素的类型汉语描述
	public static final String ZYYWSR_T = "Enterprise" ;//表示要素的类型key

        public static final String ZYYWCB_D = "企业:主营业务成本(万元)"; 
	public String zyywcb_v = "" ; 
	public static final String ZYYWCB_C = "id_xxxx_xxxx29" ;
	public static final String ZYYWCB_TD = "企业" ;//表示要素的类型汉语描述
	public static final String ZYYWCB_T = "Enterprise" ;//表示要素的类型key

        public static final String YYFY_D = "企业:营业费用(万元)"; 
	public String yyfy_v = "" ; 
	public static final String YYFY_C = "id_xxxx_xxxx33" ;
	public static final String YYFY_TD = "企业" ;//表示要素的类型汉语描述
	public static final String YYFY_T = "Enterprise" ;//表示要素的类型key

        public static final String GLFY_D = "企业:管理费用(万元)"; 
	public String glfy_v = "" ; 
	public static final String GLFY_C = "id_xxxx_xxxx37" ;
	public static final String GLFY_TD = "企业" ;//表示要素的类型汉语描述
	public static final String GLFY_T = "Enterprise" ;//表示要素的类型key

        public static final String CWFY_D = "企业:财务费用(万元)"; 
	public String cwfy_v = "" ; 
	public static final String CWFY_C = "id_xxxx_xxxx41" ;
	public static final String CWFY_TD = "企业" ;//表示要素的类型汉语描述
	public static final String CWFY_T = "Enterprise" ;//表示要素的类型key

        public static final String YYLR_D = "企业:营业利润(万元)"; 
	public String yylr_v = "" ; 
	public static final String YYLR_C = "id_xxxx_xxxx45" ;
	public static final String YYLR_TD = "企业" ;//表示要素的类型汉语描述
	public static final String YYLR_T = "Enterprise" ;//表示要素的类型key

        public static final String LRZE_D = "企业:利润总额(万元)"; 
	public String lrze_v = "" ; 
	public static final String LRZE_C = "id_xxxx_xxxx49" ;
	public static final String LRZE_TD = "企业" ;//表示要素的类型汉语描述
	public static final String LRZE_T = "Enterprise" ;//表示要素的类型key

        public static final String JLR_D = "企业:净利润(万元)"; 
	public String jlr_v = "" ; 
	public static final String JLR_C = "id_xxxx_xxxx53" ;
	public static final String JLR_TD = "企业" ;//表示要素的类型汉语描述
	public static final String JLR_T = "Enterprise" ;//表示要素的类型key

        public static final String MLL_D = "企业:毛利率"; 
	public String mll_v = "" ; 
	public static final String MLL_C = "id_xxxx_xxxx57" ;
	public static final String MLL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String MLL_T = "Enterprise" ;//表示要素的类型key

        public static final String YYLRL_D = "企业:营业利润率"; 
	public String yylrl_v = "" ; 
	public static final String YYLRL_C = "id_xxxx_xxxx61" ;
	public static final String YYLRL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String YYLRL_T = "Enterprise" ;//表示要素的类型key

        public static final String XSLRL_D = "企业:销售利润率"; 
	public String xslrl_v = "" ; 
	public static final String XSLRL_C = "id_xxxx_xxxx65" ;
	public static final String XSLRL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String XSLRL_T = "Enterprise" ;//表示要素的类型key

        public static final String JLRL_D = "企业:净利润率"; 
	public String jlrl_v = "" ; 
	public static final String JLRL_C = "id_xxxx_xxxx69" ;
	public static final String JLRL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String JLRL_T = "Enterprise" ;//表示要素的类型key

        public static final String JZCSYL_D = "企业:净资产收益率"; 
	public String jzcsyl_v = "" ; 
	public static final String JZCSYL_C = "id_xxxx_xxxx73" ;
	public static final String JZCSYL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String JZCSYL_T = "Enterprise" ;//表示要素的类型key

        public static final String CBFYLRL_D = "企业:成本费用利润率"; 
	public String cbfylrl_v = "" ; 
	public static final String CBFYLRL_C = "id_xxxx_xxxx77" ;
	public static final String CBFYLRL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String CBFYLRL_T = "Enterprise" ;//表示要素的类型key

        public static final String ZZCBCL_D = "企业:总资产报酬率"; 
	public String zzcbcl_v = "" ; 
	public static final String ZZCBCL_C = "id_xxxx_xxxx81" ;
	public static final String ZZCBCL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String ZZCBCL_T = "Enterprise" ;//表示要素的类型key
        
    /*    public static final String YYSRXJL_D = "企业:营业收入现金率"; 
	public String yysrxjl_v = "" ; 
	public static final String YYSRXJL_C = "[#企业:营业收入现金率#]" ;
	public static final String YYSRXJL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String YYSRXJL_T = "Enterprise" ;//表示要素的类型key
*/        
    /*    public static final String GDZCCXL_D = "企业:固定资产成新率"; 
	public String gdzccxl_v = "" ; 
	public static final String GDZCCXL_C = "[#企业:固定资产成新率#]" ;
	public static final String GDZCCXL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String GDZCCXL_T = "Enterprise" ;//表示要素的类型key
*/        
        /*public static final String XSYYZZL_D = "企业:销售(营业)增长率"; 
	public String xsyyzzl_v = "" ; 
	public static final String XSYYZZL_C = "[#企业:销售(营业)增长率#]" ;
	public static final String XSYYZZL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String XSYYZZL_T = "Enterprise" ;//表示要素的类型key
*/        
        public static final String CH_D = "企业:存货(万元)"; 
	public String ch_v = "" ; 
	public static final String CH_C = "id_xxxx_xxxx85" ;
	public static final String CH_TD = "企业" ;//表示要素的类型汉语描述
	public static final String CH_T = "Enterprise" ;//表示要素的类型key
        
        public static final String YSZK_D = "企业:应收账款(万元)"; 
	public String yszk_v = "" ; 
	public static final String YSZK_C = "id_xxxx_xxxx89" ;
	public static final String YSZK_TD = "企业" ;//表示要素的类型汉语描述
	public static final String YSZK_T = "Enterprise" ;//表示要素的类型key
        
        public static final String ZZCZZL_D = "企业:总资产周转率"; 
	public String zzczzl_v = "" ; 
	public static final String ZZCZZL_C = "id_xxxx_xxxx93" ;
	public static final String ZZCZZL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String ZZCZZL_T = "Enterprise" ;//表示要素的类型key
        
        public static final String CHZZL_D = "企业:存货周转率"; 
	public String chzzl_v = "" ; 
	public static final String CHZZL_C = "id_xxxx_xxxx97" ;
	public static final String CHZZL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String CHZZL_T = "Enterprise" ;//表示要素的类型key
        
        public static final String YSZKZZL_D = "企业:应收账款周转率"; 
	public String yszkzzl_v = "" ; 
	public static final String YSZKZZL_C = "id_xxxx_xxxx101" ;
	public static final String YSZKZZL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String YSZKZZL_T = "Enterprise" ;//表示要素的类型key
        
        public static final String JZCZZL_D = "企业:净资产周转率"; 
	public String jzczzl_v = "" ; 
	public static final String JZCZZL_C = "id_xxxx_xxxx105" ;
	public static final String JZCZZL_TD = "企业" ;//表示要素的类型汉语描述
	public static final String JZCZZL_T = "Enterprise" ;//表示要素的类型key
        
   /*     public static final String PJYSZKZZTS_D = "企业:平均应收账款周转天数"; 
	public String pjyszkzzts_v = "" ; 
	public static final String PJYSZKZZTS_C = "[#企业:平均应收账款周转天数#]" ;
	public static final String PJYSZKZZTS_TD = "企业" ;//表示要素的类型汉语描述
	public static final String PJYSZKZZTS_T = "Enterprise" ;//表示要素的类型key
        
        public static final String PJCHZZTS_D = "企业:平均存货周转天数"; 
	public String pjchzzts_v = "" ; 
	public static final String PJCHZZTS_C = "[#企业:平均存货周转天数#]" ;
	public static final String PJCHZZTS_TD = "企业" ;//表示要素的类型汉语描述
	public static final String PJCHZZTS_T = "Enterprise" ;//表示要素的类型key
        
        public static final String GDZCZZCS_D = "企业:固定资产周转次数"; 
	public String gdzczzcs_v = "" ; 
	public static final String GDZCZZCS_C = "[#企业:固定资产周转次数#]" ;
	public static final String GDZCZZCS_TD = "企业" ;//表示要素的类型汉语描述
	public static final String GDZCZZCS_T = "Enterprise" ;//表示要素的类型key
        
        public static final String JYHDCSDXJLR_D = "企业:经营活动产生的现金流入"; 
	public String jyhdcsdxjlr_v = "" ; 
	public static final String JYHDCSDXJLR_C = "[#企业:经营活动产生的现金流入#]" ;
	public static final String JYHDCSDXJLR_TD = "企业" ;//表示要素的类型汉语描述
	public static final String JYHDCSDXJLR_T = "Enterprise" ;//表示要素的类型key
        
        public static final String JYHDCSDXJLC_D = "企业:经营活动产生的现金流出"; 
	public String jyhdcsdxjlc_v = "" ; 
	public static final String JYHDCSDXJLC_C = "[#企业:经营活动产生的现金流出#]" ;
	public static final String JYHDCSDXJLC_TD = "企业" ;//表示要素的类型汉语描述
	public static final String JYHDCSDXJLC_T = "Enterprise" ;//表示要素的类型key
        
        public static final String BNJYXJLLJE_D = "企业:本年经营现金流量净额"; 
	public String bnjyxjllje_v = "" ; 
	public static final String BNJYXJLLJE_C = "[#企业:本年经营现金流量净额#]" ;
	public static final String BNJYXJLLJE_TD = "企业" ;//表示要素的类型汉语描述
	public static final String BNJYXJLLJE_T = "Enterprise" ;//表示要素的类型key
        
        public static final String BNJYXJLLJEQMLDFZ_D = "企业:本年经营现金流量净额/期末流动负债"; 
	public String bnjyxjlljeqmldfz_v = "" ; 
	public static final String BNJYXJLLJEQMLDFZ_C = "[#企业:本年经营现金流量净额/期末流动负债#]" ;
	public static final String BNJYXJLLJEQMLDFZ_TD = "企业" ;//表示要素的类型汉语描述
	public static final String BNJYXJLLJEQMLDFZ_T = "Enterprise" ;//表示要素的类型key
        
        public static final String BNJYXJLLJEQMCQFZ_D = "企业:本年经营现金流量净额/期末长期负债"; 
	public String bnjyxjlljeqmcqfz_v = "" ; 
	public static final String BNJYXJLLJEQMCQFZ_C = "[#企业:本年经营现金流量净额/期末长期负债#]" ;
	public static final String BNJYXJLLJEQMCQFZ_TD = "企业" ;//表示要素的类型汉语描述
	public static final String BNJYXJLLJEQMCQFZ_T = "Enterprise" ;//表示要素的类型key
*/	
}