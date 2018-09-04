package com.zhiwei.credit.model.creditFlow.smallLoan.finance;

import java.io.Serializable;

public class SlUrgeRecord implements Serializable {

	protected java.lang.Long recordId;
	protected java.util.Date insertDate;
	protected java.lang.Long creatorId;
	protected java.lang.Long findIntentId;
	protected String recordText;
	
	protected String creatorName;
	public java.lang.Long getRecordId() {
		return recordId;
	}
	public void setRecordId(java.lang.Long recordId) {
		this.recordId = recordId;
	}
	public java.util.Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(java.util.Date insertDate) {
		this.insertDate = insertDate;
	}
	public java.lang.Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(java.lang.Long creatorId) {
		this.creatorId = creatorId;
	}
	public java.lang.Long getFindIntentId() {
		return findIntentId;
	}
	public void setFindIntentId(java.lang.Long findIntentId) {
		this.findIntentId = findIntentId;
	}
	public String getRecordText() {
		return recordText;
	}
	public void setRecordText(String recordText) {
		this.recordText = recordText;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
}