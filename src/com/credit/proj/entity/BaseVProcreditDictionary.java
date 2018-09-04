package com.credit.proj.entity;

import java.math.BigDecimal;

import com.google.gson.annotations.Expose;

/*
 * add by gao
 * 所有VProcreditDictionary* 的父类
 * */
public class BaseVProcreditDictionary {
	//add by gao
	protected boolean havingTransactFile; //是否有办理文件可以预览    非视图关联字段
	protected boolean havingUnchainFile; //是否有接触文件可以预览      非视图关联字段
	//end
	public boolean isHavingTransactFile() {
		return havingTransactFile;
	}
	public void setHavingTransactFile(boolean havingTransactFile) {
		this.havingTransactFile = havingTransactFile;
	}
	public boolean isHavingUnchainFile() {
		return havingUnchainFile;
	}
	public void setHavingUnchainFile(boolean havingUnchainFile) {
		this.havingUnchainFile = havingUnchainFile;
	}
	
	
	
}
