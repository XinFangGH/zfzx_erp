package com.zhiwei.credit.action.creditFlow.pawn.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnContinuedManagment;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PawnContinuedManagmentService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PlPawnProjectService;
import com.zhiwei.credit.service.system.DictionaryService;
/**
 * 
 * @author 
 *
 */
public class PawnContinuedManagmentAction extends BaseAction{
	@Resource
	private PawnContinuedManagmentService pawnContinuedManagmentService;
	@Resource
	private PlPawnProjectService plPawnProjectService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private SlFundIntentService slFundIntentService;
	private PawnContinuedManagment pawnContinuedManagment;
	
	private Long continueId;

	public Long getContinueId() {
		return continueId;
	}

	public void setContinueId(Long continueId) {
		this.continueId = continueId;
	}

	public PawnContinuedManagment getPawnContinuedManagment() {
		return pawnContinuedManagment;
	}

	public void setPawnContinuedManagment(PawnContinuedManagment pawnContinuedManagment) {
		this.pawnContinuedManagment = pawnContinuedManagment;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		String projectId=this.getRequest().getParameter("projectId");
		String businessType=this.getRequest().getParameter("businessType");
		List<PawnContinuedManagment> list= pawnContinuedManagmentService.getListByProjectId(Long.valueOf(projectId), businessType, continueId);
		
		StringBuffer buff = new StringBuffer("{success:true,result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
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
				pawnContinuedManagmentService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		PawnContinuedManagment pawnContinuedManagment=pawnContinuedManagmentService.get(continueId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(pawnContinuedManagment));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		try{
			if(pawnContinuedManagment.getContinueId()==null){
				pawnContinuedManagment.setCreateDate(new Date());
				pawnContinuedManagmentService.save(pawnContinuedManagment);
			}else{
				PawnContinuedManagment orgPawnContinuedManagment=pawnContinuedManagmentService.get(pawnContinuedManagment.getContinueId());
				
					BeanUtil.copyNotNullProperties(orgPawnContinuedManagment, pawnContinuedManagment);
					pawnContinuedManagmentService.save(orgPawnContinuedManagment);
				
			}
			PlPawnProject project=plPawnProjectService.get(pawnContinuedManagment.getProjectId());
			List<SlFundIntent> list=slFundIntentService.getlistbyslSuperviseRecordId(pawnContinuedManagment.getContinueId(), pawnContinuedManagment.getBusinessType(), pawnContinuedManagment.getProjectId());
			for(SlFundIntent s:list){
				if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
					slFundIntentService.remove(s);
				}
			}
			List<SlFundIntent> slist=slFundIntentService.getListByFundType(project.getProjectId(), project.getBusinessType(), "pawnPrincipalRepayment");
			String fundIntentJsonData=this.getRequest().getParameter("fundIntentJsonData");
			if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
				String[] fundIntentJsonArr = fundIntentJsonData.split("@");
				for (int i = 0; i < fundIntentJsonArr.length; i++) {
					String str = fundIntentJsonArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
						SlFundIntent1.setProjectId(project.getProjectId());
						SlFundIntent1.setProjectName(project.getProjectName());
						SlFundIntent1.setProjectNumber(project.getProjectNumber());
						SlFundIntent1.setBusinessType(project.getBusinessType());
						SlFundIntent1.setCompanyId(project.getCompanyId());
						SlFundIntent1.setSlSuperviseRecordId(pawnContinuedManagment.getContinueId());
						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						}
						SlFundIntent1.setAfterMoney(new BigDecimal(0));
						SlFundIntent1.setAccrualMoney(new BigDecimal(0));
						SlFundIntent1.setFlatMoney(new BigDecimal(0));
						Short isvalid = 0;
						SlFundIntent1.setIsValid(isvalid);
						SlFundIntent1.setIsCheck(Short.valueOf("0"));
						
						if (null == SlFundIntent1.getFundIntentId()) {
							slFundIntentService.save(SlFundIntent1);
						} else {
							SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
							if (slFundIntent2.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
								BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);
								slFundIntentService.merge(slFundIntent2);
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if(null!=slist && slist.size()>0){
				SlFundIntent s=slist.get(slist.size()-1);
				s.setIsValid(Short.valueOf("1"));
				s.setIsCheck(Short.valueOf("1"));
				slFundIntentService.save(s);
			}
			project.setProjectStatus(Short.valueOf("4"));
			plPawnProjectService.save(project);
			setJsonString("{success:true}");
		}catch(Exception ex){
			setJsonString("{success:false}");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		return SUCCESS;
		
	}
	
	public String getPawnInfo(){
		String projectId=this.getRequest().getParameter("projectId");
		String businessType=this.getRequest().getParameter("businessType");
		if(null!=projectId && !"".equals(projectId) && null!=businessType && !businessType.equals("")){
			PlPawnProject project=plPawnProjectService.get(Long.valueOf(projectId));
			if(null!=project){
				StringBuffer buff=new StringBuffer("{success:true,phnumber : '"+project.getPhnumber()+"',payintentPeriod:"+project.getPayintentPeriod());
				if(project.getOppositeType().equals("company_customer")){
					Enterprise enterprise=enterpriseService.getById(project.getOppositeID().intValue());
					if(null!=enterprise){
						buff.append(",cardType:'组织机构代码',cardNumber:'"+enterprise.getOrganizecode()+"',customerName:'"+enterprise.getEnterprisename()+"'");
					}
				}else{
					Person person=personService.getById(project.getOppositeID().intValue());
					if(null!=person){
						Dictionary dic=dictionaryService.get(person.getCardtype().longValue());
						if(null!=dic){
							buff.append(",cardType:'"+dic.getItemValue()+"',cardNumber:'"+person.getCardnumber()+"',customerName:'"+person.getName()+"'");
						}
					}
				}
				if(null!=continueId){
					PawnContinuedManagment p=pawnContinuedManagmentService.get(continueId);
					if(null!=p){
						buff.append(",data:");
						Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
						buff.append(gson.toJson(p));
					}
				}
				buff.append("}");
				jsonString=buff.toString();
			}
		}
		return SUCCESS;
	}
}
