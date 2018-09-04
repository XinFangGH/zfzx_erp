package com.zhiwei.credit.action.creditFlow.pawn.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnContinuedManagment;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnRedeemManagement;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.pawn.pawnItems.PawnItemsListService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PawnRedeemManagementService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PlPawnProjectService;
import com.zhiwei.credit.service.system.DictionaryService;
/**
 * 
 * @author 
 *
 */
public class PawnRedeemManagementAction extends BaseAction{
	@Resource
	private PawnRedeemManagementService pawnRedeemManagementService;
	@Resource
	private PawnItemsListService pawnItemsListService;
	@Resource
	private PlPawnProjectService plPawnProjectService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;
	@Resource
	private DictionaryService dictionaryService;
	private PawnRedeemManagement pawnRedeemManagement;
	
	private Long redeemId;

	public Long getRedeemId() {
		return redeemId;
	}

	public void setRedeemId(Long redeemId) {
		this.redeemId = redeemId;
	}

	public PawnRedeemManagement getPawnRedeemManagement() {
		return pawnRedeemManagement;
	}

	public void setPawnRedeemManagement(PawnRedeemManagement pawnRedeemManagement) {
		this.pawnRedeemManagement = pawnRedeemManagement;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		String projectId=this.getRequest().getParameter("projectId");
		String businessType=this.getRequest().getParameter("businessType");
		List<PawnRedeemManagement> list= pawnRedeemManagementService.getListByProjectId(Long.valueOf(projectId), businessType);
		for(PawnRedeemManagement redeem :list){
			if(null!=redeem.getRedeemWay()){
				Dictionary dic=dictionaryService.get(redeem.getRedeemWay());
				if(null!=dic){
					redeem.setRedeemWayValue(dic.getItemValue());
				}
			}
		}
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
				pawnRedeemManagementService.remove(new Long(id));
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
		PawnRedeemManagement pawnRedeemManagement=pawnRedeemManagementService.get(redeemId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(pawnRedeemManagement));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		try{
			if(pawnRedeemManagement.getRedeemId()==null){
				pawnRedeemManagementService.save(pawnRedeemManagement);
			}else{
				PawnRedeemManagement orgPawnRedeemManagement=pawnRedeemManagementService.get(pawnRedeemManagement.getRedeemId());
				
					BeanUtil.copyNotNullProperties(orgPawnRedeemManagement, pawnRedeemManagement);
					pawnRedeemManagementService.save(orgPawnRedeemManagement);
				
			}
			List<PawnItemsList> list=pawnItemsListService.getListByProjectId(pawnRedeemManagement.getProjectId(), pawnRedeemManagement.getBusinessType());
			for(PawnItemsList p:list){
				p.setPawnItemStatus("finish");
				pawnItemsListService.save(p);
			}
			PlPawnProject project=plPawnProjectService.get(pawnRedeemManagement.getProjectId());
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
			
			project.setProjectStatus(Short.valueOf("5"));
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
				if(null!=redeemId){
					PawnRedeemManagement p=pawnRedeemManagementService.get(redeemId);
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
