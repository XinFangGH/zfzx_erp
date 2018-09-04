package com.zhiwei.credit.model.creditFlow.contract;
/**
 * <p>鏂囨。鎶借薄瀵硅薄</p>
 * @author tianhuiguo 
 * */
public class CreditDocument implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id ;
	private String documentname ;
	private boolean istempletrelated ;
	private int businesstype ;
	private int documenttype ;
	private int templetid ;
	private String describe ;
	
	public CreditDocument() {
	}
	public CreditDocument(int id, String documentname, int businesstype,
			int documenttype, int templetid, String describe,
			boolean istempletrelated) {
		this.id = id;
		this.documentname = documentname;
		this.businesstype = businesstype;
		this.documenttype = documenttype;
		this.templetid = templetid;
		this.describe = describe;
		this.istempletrelated = istempletrelated ;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocumentname() {
		return documentname;
	}
	public void setDocumentname(String documentname) {
		this.documentname = documentname;
	}
	public int getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(int businesstype) {
		this.businesstype = businesstype;
	}
	public int getDocumenttype() {
		return documenttype;
	}
	public void setDocumenttype(int documenttype) {
		this.documenttype = documenttype;
	}
	public int getTempletid() {
		return templetid;
	}
	public void setTempletid(int templetid) {
		this.templetid = templetid;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public boolean isIstempletrelated() {
		return istempletrelated;
	}
	public void setIstempletrelated(boolean istempletrelated) {
		this.istempletrelated = istempletrelated;
	}

}
