package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.Constants;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.system.Company;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.system.CompanyService;
import com.zhiwei.credit.service.system.FileAttachService;


public class CompanyAction extends BaseAction{

	private Company company;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Resource
	private CompanyService companyService;
	@Resource
	private FileAttachService fileAttachService;

	public String check(){
		List<Company> list = companyService.findCompany();
		if(list.size()>0){
			setJsonString("{success:true}");
		}else{
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}
	
	public String list(){
		List<Company> list = companyService.findCompany();
		if(list.size()>0){
			company = list.get(0);
			if(company.getLogo()!=null){
				FileAttach fileattach = fileAttachService.getByPath(company.getLogo());
				if(fileattach!=null){
					company.setLogoId(fileattach.getFileId().toString());
				}
			}
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			StringBuffer cf = new StringBuffer("{success:true,result:[");
			cf.append(gson.toJson(company));
			cf.append("]}");
			setJsonString(cf.toString());
		}else{
			setJsonString("{success:false,message:'还没有填写公司信息'}");
			return SUCCESS;			
		}		
		return SUCCESS;

	}

	public String add(){
		companyService.save(company);
		Map map=AppUtil.getSysConfig();
		map.remove(Constants.COMPANY_LOGO);
		map.remove(Constants.COMPANY_NAME);
		map.put(Constants.COMPANY_LOGO,company.getLogo());
		map.put(Constants.COMPANY_NAME,company.getCompanyName());
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	public String delphoto(){
		List<Company> list = companyService.findCompany();
		if(list.size()>0){
			company = list.get(0);
			company.setLogo("");
			companyService.save(company);
		}
		return SUCCESS;
	}

}
