package com.zhiwei.credit.action.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.jms.MailMessageProducer;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.customer.InvestEnterprise;
import com.zhiwei.credit.model.customer.InvestPerson;
import com.zhiwei.credit.model.customer.InvestPersonCare;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.customer.InvestEnterpriseService;
import com.zhiwei.credit.service.customer.InvestPersonCareService;
import com.zhiwei.credit.service.customer.InvestPersonService;
import com.zhiwei.credit.service.system.AppUserService;
/**
 * 
 * @author 
 *
 */
public class InvestPersonCareAction extends BaseAction{
	@Resource
	private InvestPersonCareService investPersonCareService;
	@Resource
	private InvestPersonService investPersonService;
	@Resource
	private  AppUserService  appUserService;
	@Resource
	private InvestEnterpriseService investEnterpriseService;
	@Resource
	private CsInvestmentpersonService csInvestmentpersonService;
	private InvestPersonCare investPersonCare;
	
	private Long id;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InvestPersonCare getInvestPersonCare() {
		return investPersonCare;
	}

	public void setInvestPersonCare(InvestPersonCare investPersonCare) {
		this.investPersonCare = investPersonCare;
	}
 
	
	/**
	 * 显示列表
	 */
	public String list(){
		Long perId = Long.valueOf(this.getRequest().getParameter("perId"));
		List<InvestPersonCare> list= investPersonCareService.getByperId(perId);
		int counts = investPersonCareService.getByperId(perId).size();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(counts).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//		Gson gson=new Gson();
		for(InvestPersonCare a :list){
			AppUser app = appUserService.get(Long.valueOf(a.getCareMan()));
			a.setCareManValue(app.getFullname());
		}
		
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
				investPersonCareService.remove(new Long(id));
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
		Map<String,Object> map=new HashMap<String,Object>();
		if(null!=id){
		 investPersonCare=investPersonCareService.get(id);
		 if(null!=investPersonCare && null!=investPersonCare.getCareMan()){
			 String careMan=investPersonCare.getCareMan();
			 String[] careManArr=careMan.split(",");
			 String careManName="";
			 for(int i=0;i<careManArr.length;i++){
				 String appUserId=careManArr[i];
				 if(!"".equals(appUserId)){
					 AppUser appUser=appUserService.get(Long.valueOf(appUserId));
					 careManName=careManName+appUser.getFullname()+",";
				 }
			 }
			 if(!careManName.equals("")){
				 careManName=careManName.substring(0,careManName.length()-1);
			 }
			 map.put("careManName", careManName);
		 }
		 map.put("investPersonCare", investPersonCare);
		}
		AppUser user=ContextUtil.getCurrentUser();
		map.put("appUserName", user.getFullname());
		map.put("appUserId", user.getUserId());
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(map));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		Long perId = Long.valueOf(this.getRequest().getParameter("investPersonId"));
		CsInvestmentperson investp = csInvestmentpersonService.get(perId);
		String careWay =investPersonCare.getCareWay();
		MailMessageProducer mailMessageProducerThreadZM = (MailMessageProducer) AppUtil.getBean("mailMessageProducer");
		
		//保存关怀人信息
		if(investPersonCare.getId()==null){
			investPersonCare.setPerId(perId);
			investPersonCareService.save(investPersonCare);
		}else{
			InvestPersonCare orgInvestPersonCare=investPersonCareService.get(investPersonCare.getId());
			try{
//				orgInvestPersonCare.setPerId(perId);
				BeanUtil.copyNotNullProperties(orgInvestPersonCare, investPersonCare);
				investPersonCareService.save(orgInvestPersonCare);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		/*if(perId !=null && !perId.equals("")){
			Date lostCareDate = investPersonCare.getCareDate();//保存时获得关怀时间
//			Date lostCareDate = new Date();
			investp.setLostCareDate(lostCareDate);
			csInvestmentpersonService.save(investp);
		}*/
		/*if(null !=careWay && careWay.equals("mailCare")){//邮件
			String tempPath = "mail/CustomerCare.vm";//"mail/InvestPersonCare.vm";
			MailModel mailModel=new MailModel();
			Map model = new HashMap();
			model.put("perName",investp.getPerName());
			model.put("content",investPersonCare.getCareContent());
			model.put("careMan",investPersonCare.getCareMan());
			model.put("careTitle",investPersonCare.getCareTitle());
			
			mailModel.setMailTemplate(tempPath);
			mailModel.setTo(investp.getPostAddress());
			mailModel.setSubject(investPersonCare.getCareTitle());
			mailModel.setMailData(model);
			mailMessageProducerThreadZM.send(mailModel);
		}
		//发送短信
		if(null !=careWay && careWay.equals("shortMessageCare")){
		   String[] phone=new String[]{investp.getPhoneNumber()};
		   int flg=SmsSDKClientManager.SendSMS(phone, investPersonCare.getCareContent());//电话号；内容
		   Date s = new Date();
		   if(flg ==0){
			  investPersonCare.setFlag(0);//发送短信状态
			  System.out.println(s+"发送短信成功");
		   }else{
			   investPersonCare.setFlag(1);
			   System.out.println(s+"发送短信失败");
		   }
		   investPersonCareService.merge(investPersonCare);
		}*/
		setJsonString("{success:true}");
		return SUCCESS;
	}
	public String saveEnterpriseCare(){
		Long enterpriseId = Long.valueOf(this.getRequest().getParameter("enterpriseId"));
		InvestEnterprise investp = investEnterpriseService.get(enterpriseId);
		String careWay =investPersonCare.getCareWay();
		MailMessageProducer mailMessageProducerThreadZM = (MailMessageProducer) AppUtil.getBean("mailMessageProducer");
		
		//保存关怀人信息
		if(investPersonCare.getId()==null){
			investPersonCare.setPerId(enterpriseId);
			investPersonCareService.save(investPersonCare);
		}else{
			InvestPersonCare orgInvestPersonCare=investPersonCareService.get(investPersonCare.getId());
			try{
//				orgInvestPersonCare.setPerId(perId);
				BeanUtil.copyNotNullProperties(orgInvestPersonCare, investPersonCare);
				investPersonCareService.save(orgInvestPersonCare);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		if(enterpriseId !=null && !enterpriseId.equals("")){
			Date lostCareDate = investPersonCare.getCareDate();//保存时获得关怀时间
//			Date lostCareDate = new Date();
			investp.setLastCareDate(lostCareDate);
			investEnterpriseService.save(investp);
		}
		/*if(null !=careWay && careWay.equals("mailCare")){//邮件
			String tempPath = "mail/CustomerCare.vm";//"mail/InvestPersonCare.vm";
			MailModel mailModel=new MailModel();
			Map model = new HashMap();
			model.put("perName",investp.getPerName());
			model.put("content",investPersonCare.getCareContent());
			model.put("careMan",investPersonCare.getCareMan());
			model.put("careTitle",investPersonCare.getCareTitle());
			
			mailModel.setMailTemplate(tempPath);
			mailModel.setTo(investp.getPostAddress());
			mailModel.setSubject(investPersonCare.getCareTitle());
			mailModel.setMailData(model);
			mailMessageProducerThreadZM.send(mailModel);
		}
		//发送短信
		if(null !=careWay && careWay.equals("shortMessageCare")){
		   String[] phone=new String[]{investp.getPhoneNumber()};
		   int flg=SmsSDKClientManager.SendSMS(phone, investPersonCare.getCareContent());//电话号；内容
		   Date s = new Date();
		   if(flg ==0){
			  investPersonCare.setFlag(0);//发送短信状态
			  System.out.println(s+"发送短信成功");
		   }else{
			   investPersonCare.setFlag(1);
			   System.out.println(s+"发送短信失败");
		   }
		   investPersonCareService.merge(investPersonCare);
		}*/
		setJsonString("{success:true}");
		return SUCCESS;
	}
	public String getList(){
		Long id = Long.valueOf(this.getRequest().getParameter("id"));
		String isEnterprise=this.getRequest().getParameter("isEnterprise");
		List<InvestPersonCare> list= investPersonCareService.getList(id, Integer.valueOf(isEnterprise));
		StringBuffer buff = new StringBuffer("{success:true,result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		for(InvestPersonCare a :list){
			String careMan=a.getCareMan();
			String careManValue="";
			if(null!=careMan && !careMan.equals("")){
				String[] careManArr=careMan.split(",");
				for(int i=0;i<careManArr.length;i++){
					String appUserId=careManArr[i];
					if(!"".equals(appUserId)){
						AppUser app = appUserService.get(Long.valueOf(appUserId));
						if(null!=app){
							careManValue=careManValue+app.getFullname()+",";
						}
					}
				}
			}
			if(!"".equals(careManValue)){
				careManValue=careManValue.substring(0,careManValue.length()-1);
			}
			a.setCareManValue(careManValue);
		}
		
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	public String getInvestPerson(){
		Map<String,Object> map=new HashMap<String,Object>();
		String investPersonId=this.getRequest().getParameter("investPersonId");
		if(null!=investPersonId&&!"".equals(investPersonId)){
			CsInvestmentperson cs =csInvestmentpersonService.get(Long.valueOf(investPersonId));
			map.put("csInvestmentperson", cs);
		}
		AppUser user=ContextUtil.getCurrentUser();
		map.put("appUserName", user.getFullname());
		map.put("appUserId", user.getUserId());
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(map));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
}
