package com.zhiwei.credit.service.creditFlow.contract;

public interface ConstantTemplateImpl {

	public final static int CONTRACT_TEMPLATE = 1 ;  /**合同模板类型*/
	public final static int OTHER_TEMPLATE= 2 ;  /**其他模板类型*/
	
	public final static  int ENTRUST_GUARANTEE = 1 ;  /**企业贷款委托担保合同*/ 
	public final static int REVERSE_GUARANTEE = 2 ;  /**企业贷款反担保合同*/
	public final static int ENTRUST_REVERSE_GUARANTEE = 3 ;  /**企业贷款委托反担保合同*/
	public final static int COMMITMENT = 4 ;  /**企业贷款承诺函*/
}
