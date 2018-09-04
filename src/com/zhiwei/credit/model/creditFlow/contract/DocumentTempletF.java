package com.zhiwei.credit.model.creditFlow.contract;
/**
 * <p>文档模板抽象对象 , 为某一类型的文档添加模板 ,可以在没有文档的情况下添加模板，但是</b>必须要有明确的文档类型
 * 即文档类型对应模板， 但是为文档匹配模板，必须要匹配有模板的文档类型</p>
 * @author tianhuiguo 
 * */
public class DocumentTempletF implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id ;
	private String text ;
	private String onlymark ;
	private int parentid ;
	private boolean leaf ;
	private boolean isexist ;
	private int templettype ;
	private int fileid ;
	private String remarks ;
	private String textoo ;
	private int businessType;
	private String handleFun ;
	
	//private int order ;
	public DocumentTempletF() {
	}

	public DocumentTempletF(int id, String text, int parentid, boolean leaf,
			boolean isexist, int templettype, int fileid, String remarks,
			String onlymark,String textoo ,int businessType ,String handleFun
			) {
		this.id = id;
		this.text = text;
		this.parentid = parentid;
		this.leaf = leaf;
		this.isexist = isexist;
		this.templettype = templettype;
		this.fileid = fileid;
		this.remarks = remarks;
		this.onlymark = onlymark ;
		this.textoo = textoo ;
		this.businessType = businessType ;
		this.handleFun = handleFun ;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isIsexist() {
		return isexist;
	}

	public void setIsexist(boolean isexist) {
		this.isexist = isexist;
	}

	public int getTemplettype() {
		return templettype;
	}

	public void setTemplettype(int templettype) {
		this.templettype = templettype;
	}

	public int getFileid() {
		return fileid;
	}

	public void setFileid(int fileid) {
		this.fileid = fileid;
	}

	

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOnlymark() {
		return onlymark;
	}

	public void setOnlymark(String onlymark) {
		this.onlymark = onlymark;
	}

	public String getTextoo() {
		return textoo;
	}

	public void setTextoo(String textoo) {
		this.textoo = textoo;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public String getHandleFun() {
		return handleFun;
	}

	public void setHandleFun(String handleFun) {
		this.handleFun = handleFun;
	}
}
