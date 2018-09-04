package com.zhiwei.credit.action.creditFlow.smallLoan.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.BorrowerInfo;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.BorrowerInfoService;
/**
 * 
 * @author 
 *
 */
public class BorrowerInfoAction extends BaseAction{
	@Resource
	private BorrowerInfoService borrowerInfoService;
	private BorrowerInfo borrowerInfo;
	
	private Long borrowerInfoId;
    private Long projectId;
    @Resource
    private CreditBaseDao creditBaseDao;
    
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getBorrowerInfoId() {
		return borrowerInfoId;
	}

	public void setBorrowerInfoId(Long borrowerInfoId) {
		this.borrowerInfoId = borrowerInfoId;
	}

	public BorrowerInfo getBorrowerInfo() {
		return borrowerInfo;
	}

	public void setBorrowerInfo(BorrowerInfo borrowerInfo) {
		this.borrowerInfo = borrowerInfo;
	}

	/**
	 * 显示列表
	 */
	public String list(){

		List<BorrowerInfo> list= borrowerInfoService.getBorrowerList(projectId);
		if(list!=null&&list.size()>0){//不判空取值时会报错，报null错误
			for(BorrowerInfo bo:list){
				if(null!=bo.getType() && bo.getType()==0){
					if(null!=bo.getCustomerId()){
						try {
							Enterprise e=(Enterprise) creditBaseDao.getById(Enterprise.class, bo.getCustomerId());
							bo.setCustomerName(e.getEnterprisename());
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					}
				}else if(null!=bo.getType() && bo.getType()==1){
					if(null!=bo.getCustomerId()){
						try {
							Person p=(Person) creditBaseDao.getById(Person.class, bo.getCustomerId());
							bo.setCustomerName(p.getName());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		Type type=new TypeToken<List<BorrowerInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				borrowerInfoService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	public String delete(){
		String borrowerInfoId=this.getRequest().getParameter("borrowerInfoId");
		if(null!=borrowerInfoId && !"".equals(borrowerInfoId)){
			borrowerInfoService.remove(new Long(borrowerInfoId));
		}
		jsonString="{success:true}";
		return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		BorrowerInfo borrowerInfo=borrowerInfoService.get(borrowerInfoId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(borrowerInfo));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(borrowerInfo.getBorrowerInfoId()==null){
			borrowerInfoService.save(borrowerInfo);
		}else{
			BorrowerInfo orgBorrowerInfo=borrowerInfoService.get(borrowerInfo.getBorrowerInfoId());
			try{
				BeanUtil.copyNotNullProperties(orgBorrowerInfo, borrowerInfo);
				borrowerInfoService.save(orgBorrowerInfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
