package com.zhiwei.credit.service.creditFlow.contract;

import com.zhiwei.credit.model.creditFlow.contract.AssignmentElementCode;
import com.zhiwei.credit.model.creditFlow.contract.AssureIntentBookElementCode;
import com.zhiwei.credit.model.creditFlow.contract.DocumentTemplet;
import com.zhiwei.credit.model.creditFlow.contract.FinancingElementCode;
import com.zhiwei.credit.model.creditFlow.contract.GuaranteeElementCode;
import com.zhiwei.credit.model.creditFlow.contract.LeaseFinanceElementCode;
import com.zhiwei.credit.model.creditFlow.contract.PawnElementCode;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.contract.SmallLoanElementCode;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.customer.InvestPersonInfo;

public interface ElementHandleService {

	SmallLoanElementCode getElementBySystem(String proId , Integer conId,Integer tempId,String contractType,String rnum,String comments)throws Exception ;
	GuaranteeElementCode getGuaranteeElementBySystem(String proId , int conId,int tempId,String contractType,String rnum)throws Exception ;
	FinancingElementCode getFinancingElementBySystem(String proId , int conId,int tempId,String contractType,String rnum)throws Exception ;
	AssureIntentBookElementCode getAssureBookElement(String projId,String startTime ,String endTime) throws Exception ;
	boolean updateCon(String hql ,Object[] obj)throws Exception ;
	
	
	boolean ajaxValida(int id)throws Exception ;
	boolean ajaxFileValida(String mark) throws Exception;
	boolean deleteFileContractById(int id) throws Exception;
	String getNumber(String projId,String temptId)throws Exception;
	
	//生成客户承诺函保存一条记录到合同表，如果存在则不添加; add by lu 2011.04.11
	public boolean saveAcceptanceLetter(String projectId,DocumentTemplet templet,int contractType,String templetName);
	
	//上传客户承诺函需要的合同id
	public void ajaxGetContractIdForUpLoadFile(String projId,DocumentTemplet templet);
	
	public void updateProcreditContractById(ProcreditContract procreditContract);
	
	
	SmallLoanElementCode getInterntElementBySystem(String proId ,String rnum)throws Exception;
	SmallLoanElementCode getExhibitionElementBySystem(String proId , int conId,int tempId,String contractType,String rnum);
	//给贷款审查审批表文档 要素里面填值的方法（add  by  liny 2013-2-21）
	public SmallLoanElementCode getElementBySystemReviewTable(String projectID,int conId, int tempId, String htType, String rnum);
	PawnElementCode getPawnElementBySystem(String proId , int conId,int tempId,String contractType,String rnum,Long dwId)throws Exception ;
	LeaseFinanceElementCode getLeaseFinanceElementBySystem(String proId , int conId,int tempId,String contractType,String rnum,Long leaseObjectInfoId)throws Exception ;
	PlPawnProject findByPawnProjectId (String projId)throws Exception;
	public AssignmentElementCode getAssignmentElementBySystem()throws Exception;
	SmallLoanElementCode getElementBySystem(String proId , int conId,int tempId,String contractType,String rnum,String comments,InvestPersonInfo investPerson)throws Exception ;
}
