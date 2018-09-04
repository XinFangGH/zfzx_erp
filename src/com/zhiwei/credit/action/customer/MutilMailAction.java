package com.zhiwei.credit.action.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.google.gson.Gson;
import com.zhiwei.core.engine.MailEngine;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.customer.Customer;
import com.zhiwei.credit.model.customer.CustomerMail;
import com.zhiwei.credit.model.customer.Provider;
import com.zhiwei.credit.model.system.Company;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.customer.CustomerService;
import com.zhiwei.credit.service.customer.ProviderService;
import com.zhiwei.credit.service.system.CompanyService;
import com.zhiwei.credit.service.system.FileAttachService;

public class MutilMailAction extends BaseAction {

	@Resource
	private MailEngine mailEngine;
	@Resource
	private ProviderService providerService;
	@Resource
	private FileAttachService fileAttachService;
	@Resource
	private CustomerService customerService;
	@Resource
	private CompanyService companyService;
	private CustomerMail customerMail;
	
	public CustomerMail getCustomerMail() {
		return customerMail;
	}

	public void setCustomerMail(CustomerMail customerMail) {
		this.customerMail = customerMail;
	}

	public String send(){
		Short type=customerMail.getType();
		String ids=customerMail.getIds();	
		String files=customerMail.getFiles();
		List<File> atFiles=new ArrayList<File>();
		List<String> fileName=new ArrayList<String>();
		if(StringUtils.isNotEmpty(files)){
			String[]fIds=files.split(",");
			for(int i=0;i<fIds.length;i++){
				FileAttach fileAttach=fileAttachService.get(new Long(fIds[i]));
				File file=new File(getSession().getServletContext().getRealPath("/attachFiles/")+"/"+fileAttach.getFilePath());
				fileName.add(fileAttach.getFileName());
				atFiles.add(file);
			}
		}
		String[] id=ids.split(",");
		List<String> toss=new ArrayList<String>();
		if(type==0){
			for(int i=0;i<id.length;i++){
				Customer customer=customerService.get(new Long(id[i]));
				toss.add(customer.getEmail());
			}
		}
		if(type==1){
			for(int i=0;i<id.length;i++){
				Provider provider=providerService.get(new Long(id[i]));
				toss.add(provider.getEmail());
			}
		}
		String from=null;
		String cc=null;
		String htmlMsgContent=customerMail.getMailContent();
		String subject=customerMail.getSubject();
		String[] st={};
		String []attachedFileNames=fileName.toArray(st);
		File[] f={};
		File[]attachedFiles=atFiles.toArray(f);
		String replyTo=null;
		String[]tos=toss.toArray(st);
		if(tos.length>0){
			Map configs=AppUtil.getSysConfig();
			if(StringUtils.isNotEmpty((String)configs.get("host"))&&StringUtils.isNotEmpty((String)configs.get("username"))&&StringUtils.isNotEmpty((String)configs.get("password"))&&StringUtils.isNotEmpty((String)configs.get("from"))){
				mailEngine.setFrom((String)configs.get("from"));
				mailEngine.sendMimeMessage(from, tos, cc, replyTo, subject, htmlMsgContent, attachedFileNames, attachedFiles, false);
				setJsonString("{success:true}");
			}else{
				setJsonString("{success:false,message:'未配置好邮箱配置!'}");
			}
		}else{
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}
	
	public String loadVm(){
		VelocityEngine velocityEngine=(VelocityEngine)AppUtil.getBean("velocityEngine");
		String templateLocation="mail/sendMsg.vm";
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("appUser",ContextUtil.getCurrentUser());
		List<Company> list = companyService.findCompany();
		if(list.size()>0){
			Company company=list.get(0);
			if(company!=null){
				model.put("company",company);
			}		 
			String pageHtml=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, model); 
			Gson gson=new Gson();
			setJsonString("{success:true,data:"+gson.toJson(pageHtml)+"}");
		}else{
			setJsonString("{success:false,message:'你的公司信息还不完整！请填写好公司信息!'}");
		}
		return SUCCESS;
	}
}
