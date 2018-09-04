package com.zhiwei.credit.model.creditFlow.contract;

import com.zhiwei.core.model.BaseModel;

/**
 * <p>文档模板抽象对象 , 为某一类型的文档添加模板 ,可以在没有文档的情况下添加模板，但是</b>必须要有明确的文档类型
 * 即文档类型对应模板， 但是为文档匹配模板，必须要匹配有模板的文档类型</p>
 * @author tianhuiguo 
 * */
public class DocumentTemplet extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id ;
	private String text ;
	private String onlymark ;
	private int parentid ;
	private int leaf ;
	private boolean isexist ;
	private int templettype ;
	private int fileid ;
	private String remarks ;
	private String textoo ;
	private String businessType;
	private String handleFun ;
	private Boolean checked = false;
	private int orderNum ;
	private Integer isChild;
	public DocumentTemplet() {
	}

	public DocumentTemplet(int id, String text, int parentid, int leaf,
			boolean isexist, int templettype, int fileid, String remarks,
			String onlymark,String textoo ,String businessType ,String handleFun
			,int orderNum,Integer isChild) {
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
		this.orderNum = orderNum ;
		this.isChild = isChild;
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

	public int isLeaf() {
		return leaf;
	}

	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}
	public int getLeaf(){
		return leaf;
	}
	public boolean isIsexist() {
		return isexist;
	}
	public boolean getIsexist(){
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

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getHandleFun() {
		return handleFun;
	}

	public void setHandleFun(String handleFun) {
		this.handleFun = handleFun;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getIsChild() {
		return isChild;
	}

	public void setIsChild(Integer isChild) {
		this.isChild = isChild;
	}



}
