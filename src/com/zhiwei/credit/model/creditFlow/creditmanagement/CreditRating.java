package com.zhiwei.credit.model.creditFlow.creditmanagement;

import java.util.Date;

import com.zhiwei.core.model.BaseModel;

public class CreditRating extends BaseModel {

	/**
	 * 主键
	 */
	private int id;
	/**
	 * 项目Id
	 */
	private String projectId;
	/**
	 * 客户类型
	 */
	private String customerType;
	/**
	 * 客户Id
	 */
	private int customerId;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 评估模板Id
	 */
	private int creditTemplateId;
	/**
	 * 评估模板名称
	 */
	private String creditTemplate;
	/**
	 * 财务报表文件
	 */
	private String financeFile;
	/**
	 * 评估人
	 */
	private String ratingMan;
	/**
	 * 评估时间
	 */
	private Date ratingTime;
	/**
	 * 得分
	 */
	private float ratingScore;
	/**
	 * 模板总分值
	 */
	private float templateScore;
	/**
	 * 资信等级
	 */
	private String creditRegister;
	/**
	 * 建议内容
	 */
	private String advise_sb;
	/**
	 * 所有  选择的选项ID（字符串）
	 */
	private String arr_id;
	/**
	 * 所有 选择的选项对应得分(字符串)
	 */
	private String arr_score;
	/**
	 * 创建者ID
	 */
	private Long userId;
	/**
	 * 评估耗时
	 */
	private String pgtime;
	/**
	 *定量中的参考值 
	 */
	private String ans; 
	/**
	 * 资信评估的客户的所属公司id
	 */
	private Long companyId;
	
	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public CreditRating() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getCreditTemplateId() {
		return creditTemplateId;
	}

	public void setCreditTemplateId(int creditTemplateId) {
		this.creditTemplateId = creditTemplateId;
	}

	public String getCreditTemplate() {
		return creditTemplate;
	}

	public void setCreditTemplate(String creditTemplate) {
		this.creditTemplate = creditTemplate;
	}

	public String getFinanceFile() {
		return financeFile;
	}

	public void setFinanceFile(String financeFile) {
		this.financeFile = financeFile;
	}

	public String getRatingMan() {
		return ratingMan;
	}

	public void setRatingMan(String ratingMan) {
		this.ratingMan = ratingMan;
	}

	public Date getRatingTime() {
		return ratingTime;
	}

	public void setRatingTime(Date ratingTime) {
		this.ratingTime = ratingTime;
	}

	public float getRatingScore() {
		return ratingScore;
	}

	public void setRatingScore(float ratingScore) {
		this.ratingScore = ratingScore;
	}

	public String getCreditRegister() {
		return creditRegister;
	}

	public void setCreditRegister(String creditRegister) {
		this.creditRegister = creditRegister;
	}

	
	public String getAdvise_sb() {
		return advise_sb;
	}

	public void setAdvise_sb(String adviseSb) {
		advise_sb = adviseSb;
	}

	public String getArr_id() {
		return arr_id;
	}

	public void setArr_id(String arrId) {
		arr_id = arrId;
	}

	public String getArr_score() {
		return arr_score;
	}

	public void setArr_score(String arrScore) {
		arr_score = arrScore;
	}

	public float getTemplateScore() {
		return templateScore;
	}

	public void setTemplateScore(float templateScore) {
		this.templateScore = templateScore;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPgtime() {
		return pgtime;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setPgtime(String pgtime) {
		this.pgtime = pgtime;
	}

}
