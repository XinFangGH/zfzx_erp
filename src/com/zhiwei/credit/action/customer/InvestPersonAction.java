package com.zhiwei.credit.action.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.model.customer.InvestPerson;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.contract.ElementHandleService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.customer.InvestPersonService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DictionaryService;
import com.zhiwei.credit.service.system.OrganizationService;
/**
 * 
 * @author 
 *
 */
public class InvestPersonAction extends BaseAction{
	@Resource
	private InvestPersonService investPersonService;
	@Resource
	private ElementHandleService elementHandleService;
	@Resource
	private FileFormService fileFormService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private AreaDicService areaDicService;
	
	private InvestPerson investPerson;
	private Boolean isAll;
	private Long perId;

	private String userIds = "";
	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}

	public Long getPerId() {
		return perId;
	}

	public void setPerId(Long perId) {
		this.perId = perId;
	}

	public InvestPerson getInvestPerson() {
		return investPerson;
	}

	public void setInvestPerson(InvestPerson investPerson) {
		this.investPerson = investPerson;
	}

	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				investPersonService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示列表
	 */
	public String list(){
		String userIdsStr = "";
		String currentUserId = ContextUtil.getCurrentUserId().toString();
	/*	String roleTypeStr = ContextUtil.getRoleTypeSession();
		if (!isAll && !roleTypeStr.equals("control")) {// 如果用户不拥有查看所有项目信息的权限
			userIdsStr = appUserService.getUsersStr();// 当前登录用户以及其所有下属用户
		}
		isAll = super.getRequest().getParameter("isAll").equals("true")?true:false;
		//授权查询全部客户的代码begin 在这里为companyId赋值
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());//判断当前是否为集团版本 
	    String  roleType=ContextUtil.getRoleTypeSession();
		String companyId=null;
		if(flag){//表示为集团版本
			if(roleType.equals("control")){//具有管控角色
				if(!isAll){//具有业务角色
					companyId=ContextUtil.getBranchIdsStr();
				}	
			}else {//不具有管控角色
				if(!currentUserId.equals("1")){//不具有查看所有客户的权限
					companyId=String.valueOf(ContextUtil.getLoginCompanyId());
				}
			}
		}else {//表示不为集团版本
			if(!isAll){
				companyId=String.valueOf(ContextUtil.getLoginCompanyId());
			}
		}*/
		//授权查询全部客户的代码end
		/*if(null!=this.getRequest().getParameter("companyId") && !"".equals(this.getRequest().getParameter("companyId")))
		{
			companyId=(String)this.getRequest().getParameter("companyId");
		}*/
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		String area = this.getRequest().getParameter("areaId");
		if(null !=area && !area.equals("")){
			map.put("areaId", area);			
		}
		List<InvestPerson> list= investPersonService.getList(null,userIdsStr, pb, getRequest(),map);
		for (InvestPerson p : list) {
			String areaId = p.getAreaId();//获取地区信息
			if(areaId==null){
				
			}else{
				String[] array = areaId.split(",");
				if(array!=null){
					StringBuffer sbff = new StringBuffer();
					AreaDic areaDic = null;
					for(int i = 0; i<array.length;i++){
						if(array[i].equals("")||array[i].equals(null)){
							continue;
						}
						areaDic = areaDicService.getById(Integer.valueOf(array[i]));
						sbff.append(areaDic.getText()).append("-");
					}
					if(array.length>0 ){
						if(!array[0].equals("") && sbff.length()!=0){
							sbff.deleteCharAt(sbff.length()-1);
						}
					}
					p.setAreaText(sbff.toString());
				}
			}
			//获取共享人信息
			String  belongedId = p.getBelongedId();
			StringBuffer depName = new StringBuffer();
//			StringBuffer belongedIds = new StringBuffer();
			StringBuffer belonger = new StringBuffer();
			if(null==belongedId){
				
			}else{	
				List<AppUser> listApp = appUserService.findByUserId(belongedId);
				for(int i = 0 ; i<listApp.size();i++){
					AppUser au = listApp.get(i);
					depName.append(au.getDepartment().getDepName()).append(",");
					belonger.append(au.getFullname()).append(",");
				}
				if(depName.length()!=0)
					p.setDepName(depName.toString().substring(0,depName.length()-1 ).toString());
				if(belonger.length()!=0)
					p.setBelonger(belonger.toString().substring(0,belonger.length()-1 ).toString());
			}
			if (null != p.getCompanyId()) {
				Organization organization = organizationService.get(p.getCompanyId());
				if (null != organization) {
					p.setOrgName(organization.getOrgName());
				}
			}
			if(p.getPerBirthday()!=null){
				p.setPerBirthdayStr(new SimpleDateFormat("yyyy-MM-dd").format(p.getPerBirthday()));
			}
			FileForm fileForm5 = fileFormService.getFileByMark("cs_invest_person_sfzz."+p.getPerId());
			if(null!=fileForm5 && null!=fileForm5.getFileid()){
				p.setPersonSFZZId(fileForm5.getFileid());
				p.setPersonSFZZUrl(fileForm5.getWebPath());
				p.setPersonSFZZExtendName(fileForm5.getExtendname());
			}
			FileForm fileForm6 = fileFormService.getFileByMark("cs_invest_person_sfzf."+p.getPerId());
			if(null!=fileForm6 && null!=fileForm6.getFileid()){
				p.setPersonSFZFId(fileForm6.getFileid());
				p.setPersonSFZFUrl(fileForm6.getWebPath());
				p.setPersonSFZFExtendName(fileForm6.getExtendname());
			}
		}
		int counts = investPersonService.getList(null,userIdsStr, null, getRequest(),map).size();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(counts).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		InvestPerson investPerson=investPersonService.get(perId);
		String areaId = investPerson.getAreaId();
		if(null!=areaId && !areaId.equals("")){
			StringBuffer sb = new StringBuffer();
			String[] adids = areaId.split(",");
			if(adids!=null){
				AreaDic areaDic = null;
				for(String id: adids){
					if(id!=null){
						areaDic = areaDicService.getById(Integer.valueOf(id));
						sb.append(areaDic.getText()).append("-");
					}
				}
				if(sb.length()!=0){
					investPerson.setAreaText(sb.toString().substring(0,sb.length()-1 ).toString());
				}
			}
		}
		FileForm fileForm2 = fileFormService.getFileByMark("cs_invest_person_sfzz."+perId);
		if(fileForm2 != null){
			investPerson.setPersonSFZZId(fileForm2.getFileid());
			investPerson.setPersonSFZZUrl(fileForm2.getWebPath());
			investPerson.setPersonSFZZExtendName(fileForm2.getExtendname());
		}
		FileForm fileForm3 = fileFormService.getFileByMark("cs_invest_person_sfzf."+perId);
		if(fileForm3 != null){
			investPerson.setPersonSFZFId(fileForm3.getFileid());
			investPerson.setPersonSFZFUrl(fileForm3.getWebPath());
			investPerson.setPersonSFZFExtendName(fileForm3.getExtendname());
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(investPerson));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 添加及保存操作
	 */
	public String save(){
		Long currentUserId = ContextUtil.getCurrentUserId();
		Organization organization = null;
    	if(null!=this.getSession().getAttribute("company")&&!"null".equals(this.getSession().getAttribute("company"))&&!"".equals(this.getSession().getAttribute("company")))
    	{
    		String companySession = (String)this.getSession().getAttribute("company");
    		organization = organizationService.getBranchCompanyByKey(companySession);
    	}else{
    		 organization = organizationService.getGroupCompany();
    	}
    	if(null!=organization){
    		investPerson.setCompanyId(organization.getOrgId());
    	}
    	investPerson.setCreater(ContextUtil.getCurrentUser().getFullname());
    	investPerson.setCreaterId(currentUserId);
    	investPerson.setBelongedId(currentUserId.toString());
    	investPerson.setCreatedate(new Date());
    	InvestPerson ipSaved = new InvestPerson();
		if(investPerson.getPerId()==null){
			ipSaved = investPersonService.save(investPerson);
		}else{
			InvestPerson orgInvestPerson=investPersonService.get(investPerson.getPerId());
			try{
				BeanUtil.copyNotNullProperties(orgInvestPerson, investPerson);
				ipSaved = investPersonService.save(orgInvestPerson);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		try {
			String personSFZZId = super.getRequest().getParameter("personSFZZId");
			if(personSFZZId != ""&&!personSFZZId.equals("")){
				Object [] obj = {"cs_invest_person_sfzz."+ipSaved.getPerId(),Integer.parseInt(personSFZZId)};
				elementHandleService.updateCon("update FileForm set mark = ? where fileid = ?", obj);
			}
			String personSFZFId = super.getRequest().getParameter("personSFZFId");
			if(personSFZFId != ""&&!personSFZFId.equals("")){
				Object [] obj = {"cs_invest_person_sfzf."+ipSaved.getPerId(),Integer.parseInt(personSFZFId)};
				elementHandleService.updateCon("update FileForm set mark = ? where fileid = ?", obj);
			}
		}catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	/**
	 * 导出Excel
	 */
	public void exportExcel() {
		getRequest().getParameter("ids");
		List<InvestPerson> list = null;
		if(userIds.equals(null)||"".equals(userIds)){
			userIds = "";
		}
		
		String [] tableHeader = {"序号","姓名","性别","证件类型/号码","客户级别","手机号码","出生日期","通讯地址","联系人姓名","联系人电话","关系","邮箱"};
		try {
			String userIdsStr = "";
			String currentUserId = ContextUtil.getCurrentUserId().toString();
			String roleTypeStr = ContextUtil.getRoleTypeSession();
			if (!isAll && !roleTypeStr.equals("control")) {// 如果用户不拥有查看所有项目信息的权限
				userIdsStr = appUserService.getUsersStr();// 当前登录用户以及其所有下属用户
			}
			isAll = super.getRequest().getParameter("isAll").equals("true")?true:false;
			//授权查询全部客户的代码begin 在这里为companyId赋值
			Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());//判断当前是否为集团版本 
		    String  roleType=ContextUtil.getRoleTypeSession();
			String companyId=null;
			if(flag){//表示为集团版本
				if(roleType.equals("control")){//具有管控角色
					if(!isAll){//具有业务角色
						companyId=ContextUtil.getBranchIdsStr();
					}	
				}else {//不具有管控角色
					if(!currentUserId.equals("1")){//不具有查看所有客户的权限
						companyId=String.valueOf(ContextUtil.getLoginCompanyId());
					}
				}
			}else {//表示不为集团版本
				if(!isAll){
					companyId=String.valueOf(ContextUtil.getLoginCompanyId());
				}
			}
			Map<String, String> map = new HashMap<String, String>();
			String area = this.getRequest().getParameter("areaId");
			if(null !=area && !area.equals("")){
				map.put("areaId", area);			
			}
		/*	String Q_perName_S_LK = java.net.URLDecoder.decode(this.getRequest().getParameter("Q_perName_S_LK"),"UTF-8");// 项目名称
			String Q_cardNumber_S_LK = java.net.URLDecoder.decode(this.getRequest().getParameter("Q_cardNumber_S_LK"),"UTF-8");// 客户名称
			this.getRequest().setAttribute(name, o)*/
			list = investPersonService.getList(companyId,userIdsStr, null, getRequest(),map);
			for(InvestPerson ip : list) {
				ip.setCardTypeValue(dictionaryService.get(Long.valueOf(ip.getCardType())).getItemValue());
				
			}
			ExcelHelper.export(list,tableHeader,"个人");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void verificationPerson(){
		try{
		String cardNum=this.getRequest().getParameter("cardNum");
		String personId=this.getRequest().getParameter("personId");
		if(null==personId || "".equals(personId) || "0".equals(personId)){
			InvestPerson p= investPersonService.getByCardNumber(cardNum);
			if(null!=p){
				JsonUtil.responseJsonString("{success:true,msg:false}");
			}else{
				JsonUtil.responseJsonString("{success:true,msg:true}");
			}
		}else{
			InvestPerson person=investPersonService.get(Long.valueOf(personId));
			if(!person.getCardNumber().equals(cardNum)){
				InvestPerson p= investPersonService.getByCardNumber(cardNum);
				if(null!=p){
					JsonUtil.responseJsonString("{success:true,msg:false}");
				}else{
					JsonUtil.responseJsonString("{success:true,msg:true}");
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
