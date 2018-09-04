package com.thirdPayInterface.AllinPay.AllinpayUtil;

public class QUERY_TRANS {
	String MERCHANT_ID="";
	String QUERY_SN="";//2、要查询的交易流水,也就是原请求交易中的REQ_SN的值
	String STATUS="";//3、状态,交易状态条件, 0成功,1失败, 2全部,3退票
	String TYPE="";//4、查询类型,0.按完成日期1.按提交日期，默认为1（如果使用0查询，未完成交易将查不到）
	String START_DAY="";//5、开始日，格式YYYYMMDDHHmmss，若不填QUERY_SN则必填
	String END_DAY="";//6、结束日，格式YYYYMMDDHHmmss，填了开始时间必填
	
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANTID) {
		MERCHANT_ID = mERCHANTID;
	}
	public String getQUERY_SN(){
		return QUERY_SN;
	}
	public void setQUERY_SN(String query_sn){
		QUERY_SN=query_sn;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getSTART_DAY() {
		return START_DAY;
	}
	public void setSTART_DAY(String sTARTDAY) {
		START_DAY = sTARTDAY;
	}
	public String getEND_DAY() {
		return END_DAY;
	}
	public void setEND_DAY(String eNDDAY) {
		END_DAY = eNDDAY;
	}
	
}
