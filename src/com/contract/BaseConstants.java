package com.contract;

/**
 * 本类主要定义了合同要素属性文件个数以及用于区分要素来源的key
 * @author zhangcb
 *
 */
public class BaseConstants {
	//合同key-------------------------------------------------------------------开始
	/**
	 * 合同类别_借款合同
	 */
	public static final String LOANCONTRACT="loanContract";
	/**
	 * 合同类别_保证合同
	 */
	public static final String XEDBZDB="XEDBZDB";
	/**
	 * 合同类别_抵质押合同
	 */
	public static final String XEDBCS="XEDBCS";
	/**
	 * 合同类别_展期合同
	 */
	public static final String XEDZZQ="XEDZZQ";
	/**
	 * 合同类别_P2P投资人合同
	 */
	public static final String P2PTZRHT="P2PTZRHT";
	/**
	 * 合同类别_P2P借款人合同
	 */
	public static final String P2PJKRHT="P2PJKRHT";
	/**
	 * 合同类别_信用借款人合同
	 */
	public static final String CREDITJKRHT="CREDITJKRHT";
	/**
	 * 合同类别_信用投资人合同
	 */
	public static final String CREDITTZRHT="CREDITTZRHT";
	/**
	 * 合同类别_C2P投资人合同
	 */
	public static final String C2PTZRHT="C2PTZRHT";
	/**
	 * 合同类别_C2P借款人合同
	 */
	public static final String C2PJKRHT="C2PJKRHT";
	/**
	 * 合同类别_PA2P借款人债权合同
	 */
	public static final String PA2PJKRZQHT="PA2PJKRZQHT";
	/**
	 * 合同类别_PA2P投资人债权合同
	 */
	public static final String PA2PTZRZQHT="PA2PTZRZQHT";
	/**
	 * 合同类别_CA2P借款人债权合同
	 */
	public static final String CA2PJKRZQHT="CA2PJKRZQHT";
	/**
	 * 合同类别_CA2P投资人债权合同
	 */
	public static final String CA2PTZRZQHT="CA2PTZRZQHT";
	//合同key-------------------------------------------------------------------结束
	
	public static final String REGEX="\\$\\{[^{}]+\\}";
	
	public static final String SUFIXDOCX=".docx";
	
	public static final String SUFIXPDF=".pdf";

	public static final String SUFIXJPG=".jpg";

	//单位-------------------------------------------------------------------开始
	/**
	 * 金额大写单位
	 */
	public static String DWDX = "整";
	/**
	 * 金额小写单位
	 */
	public static String DWXX="元";
	/**
	 * 金额大写单位2
	 */
	public static String DWDX2="零元整";
	/**
	 * 利率单位
	 */
	public static String DWLL="%";
	/**
	 * 期限单位1
	 */
	public static String DWQX1="个月";
	/**
	 * 期限单位2
	 */
	public static String DWQX2="天";
	/**
	 * 还款方式1
	 */
	public static String HKFS1="等额本金";
	/**
	 * 还款方式2
	 */
	public static String HKFS2="等额本息";
	/**
	 * 还款方式3
	 */
	public static String HKFS3="按期收息,到期还本";
	/**
	 * 还款方式4
	 */
	public static String HKFS4="一次性还本付息";
	
	//单位-------------------------------------------------------------------结束
	
	//保证人类别id-----------------------------------------------------------开始
	/**
	 * 保证人——企业
	 */
	public static int BZID1=602;
	/**
	 * 保证人——企业
	 */
	public static int BZID2=603;
	/**
	 * 保证人——企业
	 */
	public static int BZID3=604;
	/**
	 * 保证人——企业
	 */
	public static int BZID4=605;
	
	//保证人类别id-----------------------------------------------------------结束
	
	/**
	 * 贷款端合同要素属性文件个数
	 */
	public static int LOANSIZE=11;
	/**
	 * 互联网金融端合同要素属性文件个数
	 */
	public static int P2PSIZE=7;
	
	/**
	 * 保证
	 */
	public static final String BZ="BZ_";
	/**
	 * 出质
	 */
	public static final String CZ="CZ_";
	/**
	 * 抵押
	 */
	public static final String DY="DY_";
	/**
	 * 服务费主体
	 */
	public static final String FWFZT="FWFZT_";
	/**
	 * 管理费主体
	 */
	public static final String GLFZT="GLFZT_";
	/**
	 * 出借(我方)主体
	 */
	public static final String CJZT="CJZT_";
	
	public static final String TZRKXJH="投资人款项计划";
	
	public static final String TZRLB="投资人列表";
	
	public static final String JKRHKJH="借款人还款计划";
	
	public static final String QTJSJE = "其他结算金额";
	
	public static final String TCMXXX = "提成明细信息";
	
	/**
	 * 根据type获得指定类别
	 * @param type
	 * @return
	 */
	public static String getType(String type){
		type=type+"_";
		if(type.equals(BZ)){
			return BZ;
		}else if(type.equals(CZ)){
			return CZ;
		}else if(type.equals(DY)){
			return DY;
		}else if(type.equals(FWFZT)){
			return FWFZT;
		}else if(type.equals(GLFZT)){
			return GLFZT;
		}else if(type.equals(CJZT)){
			return CJZT;
		}else{
			return "";
		}
	}
}