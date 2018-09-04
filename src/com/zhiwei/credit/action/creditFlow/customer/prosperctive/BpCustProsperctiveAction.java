package com.zhiwei.credit.action.creditFlow.customer.prosperctive;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseRelationPerson;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonRelation;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProspectiveFollowup;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProspectiveRelation;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Department;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.RelativeUser;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseRelationPersonService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonRelationService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.customer.prosperctive.BpCustProspectiveFollowupService;
import com.zhiwei.credit.service.creditFlow.customer.prosperctive.BpCustProspectiveRelationService;
import com.zhiwei.credit.service.creditFlow.customer.prosperctive.BpCustProsperctiveService;
import com.zhiwei.credit.service.customer.InvestIntentionService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.system.RelativeUserService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustProsperctiveAction extends BaseAction{
	@Resource
	private BpCustProsperctiveService bpCustProsperctiveService;
	@Resource
	private BpCustProspectiveRelationService bpCustProspectiveRelationService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private EnterpriseRelationPersonService enterpriseRelationPersonService;
	@Resource
	private PersonService personService;
	@Resource
	private PersonRelationService personRelationService;
	@Resource
	private BpCustProspectiveFollowupService bpCustProspectiveFollowupService;
	private BpCustProsperctive bpCustProsperctive;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private InvestIntentionService investIntentionService;
	@Resource
	private RelativeUserService relativeUserService;
	@Resource
	private CsInvestmentpersonService csInvestmentpersonService;
	
	private Boolean isAll;
	
	private Long perId;

	public Long getPerId() {
		return perId;
	}

	public void setPerId(Long perId) {
		this.perId = perId;
	}

	public BpCustProsperctive getBpCustProsperctive() {
		return bpCustProsperctive;
	}

	public void setBpCustProsperctive(BpCustProsperctive bpCustProsperctive) {
		this.bpCustProsperctive = bpCustProsperctive;
	}
	
	public void verification(){
		try{
			String telephoneNumber=this.getRequest().getParameter("telephoneNumber");
			String companyId=this.getRequest().getParameter("companyId");
			String perId=this.getRequest().getParameter("perId");
			if(null==perId || "".equals(perId) || "0".equals(perId)){
				BpCustProsperctive p1=bpCustProsperctiveService.queryTelNumber(telephoneNumber,companyId);
				if(null!=p1){
					JsonUtil.responseJsonString("{success:true,msg:false}");
				}else{
					JsonUtil.responseJsonString("{success:true,msg:true}");
				}
			}else{
				BpCustProsperctive p=bpCustProsperctiveService.get(Long.valueOf(perId));
				if(!p.getTelephoneNumber().equals(telephoneNumber)){
					BpCustProsperctive p2=bpCustProsperctiveService.queryTelNumber(telephoneNumber,companyId);
					if(null!=p2){
						JsonUtil.responseJsonString("{success:true,msg:false}");
					}else{
						JsonUtil.responseJsonString("{success:true,msg:true}");
					}
				}else{
					JsonUtil.responseJsonString("{success:true,msg:true}");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 显示列表
	 */
	public String list(){
		String []userIds = null;
		String currentUserId = ContextUtil.getCurrentUserId().toString();
		StringBuffer userIdsStr = new StringBuffer();
		isAll = super.getRequest().getParameter("isAll").equals("true")?true:false;
		Boolean  isShop = super.getRequest().getParameter("isShop").equals("true")?true:false;
		String departmentId=null;
		//授权查询全部客户的代码begin 在这里为companyId赋值
/*		List<AppUser> userList = appUserService.findRelativeUsersByUserId();
		if(!isAll) {//如果用户不拥有查看所有项目信息的权限
			if(userList.size() > 0) {
				for(AppUser appUser : userList) {
					userIdsStr.append(appUser.getUserId());
					userIdsStr.append(",");
				}
				userIds = (userIdsStr.toString() + currentUserId).split(",");//当前登录用户以及其所有下属用户
			}else {
				userIds = new String[]{currentUserId};
			}
		}*/
		List<RelativeUser> findSubordinate = relativeUserService.findSubordinate(ContextUtil.getCurrentUserId());
		ArrayList<AppUser> appUserList = new ArrayList<AppUser>();
		List<AppUser> diguiList = new ArrayList<AppUser>();
		diguiList = appUserService.diguiRelativeUser(findSubordinate, appUserList);
		if(!isAll) {//如果用户不拥有查看所有项目信息的权限，没有授权查看所有
			if(!isShop){//判断是否按门店分离，如果没有授权按门店分离，默认按上下级分离
				if(diguiList.size() > 0) {
					for(AppUser appUser : diguiList) {
						userIdsStr.append(appUser.getUserId());
						userIdsStr.append(",");
					}
					userIds = (userIdsStr.toString() + currentUserId).split(",");//当前登录用户以及其所有下属用户
				}else {
					userIds = new String[]{currentUserId};
				}
			}else{
				//如果授权按门店分离，则按门店分离数据
				AppUser  appUser=appUserService.get(Long.valueOf(currentUserId));
				Organization organization=organizationService.getByUserIdToStoreNameAndStoreNameId(appUser.getDepartment().getDepId());
				if(null !=organization && !"".equals(organization)){
					departmentId = organization.getOrgId().toString();
				}
			}
		}
		
		
		List<BpCustProsperctive> list= bpCustProsperctiveService.getList(this.getRequest(),start,limit,userIds,departmentId);
		if(list!=null &&list.size()>0){
			for(BpCustProsperctive temp :list){
				System.out.println(temp.getNextFollowDate());
				if(temp.getCreatorId()!=null){
					Set<BpCustProspectiveFollowup> bpCustProspectiveFollowups =temp.getBpCustProspectiveFollowups();
					temp.setFollowUpcount(bpCustProspectiveFollowups.size()+"");
				/*	if(bpCustProspectiveFollowups.size()>0){
						List<BpCustProspectiveFollowup> listf= bpCustProspectiveFollowupService.getListByPerId(temp.getPerId().toString(),this.getRequest(),null,null);
						temp.setLastFollowUpDate(listf.get(0).getFollowDate());
					}*/
					String appuers = "";
					String[] appstr = temp.getCreatorId().split(",");
					Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
					for (AppUser s : userSet) {
						appuers = appuers + s.getFullname() + ",";
					}
					if (appuers.length() > 0) {
						appuers = appuers.substring(0, appuers.length() - 1);
					}
					if(!"".equals(appuers)){
						temp.setCreatorName(appuers);
					}
					
					String belongName = "";
					String[] belongNametr = temp.getBelongId().split(",");
					Set<AppUser> userSets = this.appUserService.getAppUserByStr(belongNametr);
					for (AppUser s : userSets) {
						belongName = belongName + s.getFullname() + ",";
					}
					if (belongName.length() > 0) {
						belongName = belongName.substring(0, belongName.length() - 1);
					}
					if(!"".equals(belongName)){
						temp.setBelongName(belongName);
					}
					//add by zcb 2014.8.20
					if(null!=temp.getCompanyId()){
						Organization org=organizationService.get(temp.getCompanyId());
						if(null!=org){
							temp.setOrgName(org.getOrgName());
						}
					}
				}
			}
		}
		List<BpCustProsperctive> listcount= bpCustProsperctiveService.getList(this.getRequest(),null,null,userIds,departmentId);
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:");
		buff.append(listcount!=null?listcount.size():0);
		buff.append(",result:");
		JSONSerializer serializer=new JSONSerializer();
		buff.append(serializer.transform(new DateTransformer("yyyy-MM-dd"), "loanDate", "createDate").transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), "nextFollowDate","lastFollowUpDate").exclude(new String[]{"class"}).serialize(list));
		buff.append("}");
		jsonString=buff.toString();
		System.out.println(jsonString);
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
				bpCustProsperctiveService.remove(new Long(id));
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
		BpCustProsperctive bpCustProsperctive=bpCustProsperctiveService.get(perId);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("bpCustProsperctive", bpCustProsperctive);
		map.put("object", "");
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer=new JSONSerializer();
		sb.append(serializer.transform(new DateTransformer("yyyy-MM-dd"), "bpCustProsperctive.loanDate", "bpCustProsperctive.createDate", "bpCustProsperctive.nextFollowDate","bpCustProsperctive.validEnddate").serialize(map));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		try{
			String personType=this.getRequest().getParameter("bpCustProsperctive.personType");
			String  relationData =this.getRequest().getParameter("relationPerson");
			BpCustProsperctive customer =null;
			if(bpCustProsperctive.getPerId()==null){
				bpCustProsperctive.setBelongId(ContextUtil.getCurrentUserId().toString());
				bpCustProsperctive.setCreatorId(ContextUtil.getCurrentUserId().toString());
				bpCustProsperctive.setCreateDate(new Date());
				bpCustProsperctive.setCompanyId(ContextUtil.getLoginCompanyId());
				bpCustProsperctive.setProsperctiveType(Short.valueOf("2"));
				bpCustProsperctive.setPersonType(Integer.valueOf(personType));
				customer=bpCustProsperctiveService.save(bpCustProsperctive);
			}else{
				BpCustProsperctive orgBpCustProsperctive=bpCustProsperctiveService.get(bpCustProsperctive.getPerId());
				BeanUtil.copyNotNullProperties(orgBpCustProsperctive, bpCustProsperctive);
				customer=bpCustProsperctiveService.save(orgBpCustProsperctive);
				
			}
			if(customer!=null){
				bpCustProspectiveRelationService.saveRelationData(relationData,customer);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 根据潜在客户的id  来查看潜在客户的信息
	 * @return
	 */
	public String queryCustomerById(){
		BpCustProsperctive bpCustProsperctive=bpCustProsperctiveService.get(perId);
		
		return SUCCESS;
	}
	
	public String changePersonType(){
		BpCustProsperctive Prosperctive=bpCustProsperctiveService.get(bpCustProsperctive.getPerId());
		Prosperctive.setProsperctiveType(bpCustProsperctive.getProsperctiveType());
		Prosperctive.setConversionReason(bpCustProsperctive.getConversionReason());
		bpCustProsperctiveService.merge(Prosperctive);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	public String setNextFollowUpDate(){
		System.out.println(bpCustProsperctive.getNextFollowDate());
		BpCustProsperctive Prosperctive=bpCustProsperctiveService.get(bpCustProsperctive.getPerId());
		Prosperctive.setNextFollowDate(bpCustProsperctive.getNextFollowDate());
		Prosperctive.setFollowUpRemark(bpCustProsperctive.getFollowUpRemark());
		bpCustProsperctiveService.merge(Prosperctive);
		return SUCCESS;
	}
	
	/**
	 * 给意向客户授权保存方法
	 * 
	 * @return
	 */
	public String getGrant(){
		String  userIdStr =this.getRequest().getParameter("userIdStr");
		if(perId!=null){
			BpCustProsperctive Prosperctive=bpCustProsperctiveService.get(perId);
			if(userIdStr!=null && !"".equals(userIdStr) && !"null".equals(userIdStr)){
				Prosperctive.setBelongId(userIdStr);
				bpCustProsperctiveService.merge(Prosperctive);
			}
		}
		return SUCCESS;
	}
	/**
	 * 对意向客户进行高级分配，分别为按创建人分配，按共享人分配，按地区分配
	 * @return
	 */
	public String getSeniorGrantCreatorId(){
		try{
			//按创建人的字段
			String creatorId =this.getRequest().getParameter("creatorId");
			String grantCreatorId =this.getRequest().getParameter("grantCreatorId");
			List<BpCustProsperctive> list =bpCustProsperctiveService.getByCreatorId(creatorId);
			if(list!=null && list.size()>0){
				for(BpCustProsperctive  temp: list){
					String belongId =temp.getBelongId();
					String[] belongIdtr = temp.getBelongId().split(",");
					String newBelong =replaceBelongId(belongIdtr,creatorId);
					String repBelong =CheckBelongId(newBelong,grantCreatorId);
					temp.setBelongId(repBelong);
					temp.setCreatorId(grantCreatorId);
					bpCustProsperctiveService.merge(temp);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 用来查看被分配的这个人是否在共享人中
	 * @param belongIdtr
	 * @param creatorId
	 * @return
	 */
	private String  CheckBelongId (String belongIdtr,String grantCreatorId){
		if(belongIdtr!=null){
			String[] belongId = belongIdtr.split(",");
			String belong ="";
			int flag =0;
			for(int i=0;i<belongId.length;i++){
				if(belongId[i].equals(grantCreatorId)){
					flag= flag+1;
				}
			}
			if(flag==0){
				belongIdtr=belongIdtr+","+grantCreatorId;
			}
				return belongIdtr;
	
		}else {
				return null;
		}
	}
		
		
	
	/**
	 * 用来剔除当前被替换的共享人，创建人的id
	 * @param belongIdtr
	 * @param creatorId
	 * @return
	 */
	private String  replaceBelongId (String[] belongIdtr,String creatorId){
		if(belongIdtr!=null){
			String belong ="";
			for(int i=0;i<belongIdtr.length;i++){
				   if(belongIdtr[i].equals(creatorId)){
					   
				   }else{
					   belong=belong+belongIdtr[i]+",";
				   }
				  
			}
			if (belong.length() > 0) {
				belong = belong.substring(0, belong.length() - 1);
			}
			return belong;
		}else {
			return null;
		}
		
	}
	
	/**
	 * 对意向客户进行高级分配，按共享人分配，按地区分配
	 * @return
	 */
	public String getSeniorGrantBelongId(){
		try{
			//按共享人的字段
			String belongId =this.getRequest().getParameter("belongId");
			String grantBelongId =this.getRequest().getParameter("grantBelongId");
			List<BpCustProsperctive> list =bpCustProsperctiveService.getByBelongId(belongId);
			if(list!=null && list.size()>0){
				for(BpCustProsperctive  temp: list){
					String oldBelongId =temp.getBelongId();
					String[] belongIdtr = temp.getBelongId().split(",");
					String newBelong =replaceBelongId(belongIdtr,belongId);
					String repBelong =CheckBelongId(newBelong,grantBelongId);
					temp.setBelongId(repBelong);
					bpCustProsperctiveService.merge(temp);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 对意向客户进行高级分配，按地区分配
	 * @return
	 */
	public String getSeniorGrantAreaId(){
		try{
			//按共享人的字段
			String areaId =this.getRequest().getParameter("areaId");
			String grantAreaId =this.getRequest().getParameter("grantAreaId");
			List<BpCustProsperctive> list =bpCustProsperctiveService.getByAreaId(areaId);
			if(list!=null && list.size()>0){
				for(BpCustProsperctive  temp: list){
					String oldBelongId =temp.getBelongId();
					String repBelong =CheckBelongId(oldBelongId,grantAreaId);
					temp.setBelongId(repBelong);
					bpCustProsperctiveService.merge(temp);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public void getOnlineUserDepartment(){
		PrintWriter writer = null;
		try {
			writer = this.getResponse().getWriter();
			
			AppUser appUser = ContextUtil.getCurrentUser();
			Department department = appUser.getDepartment();
			Organization endorganization = null;
			Organization organization = organizationService.get(department.getDepId());
			if(organization.getOrgType()==1||organization.getOrgType()==0){
				endorganization = organization;
			}else {
				endorganization = organizationService.get(organization.getOrgSupId());
			}
			
			StringBuffer sb = new StringBuffer("{");
			sb.append("\"orgId\":\"").append(endorganization.getOrgId().toString()).append("\", ");
			sb.append("\"orgName\":\"").append(endorganization.getOrgName()).append("\" ");
			sb.append("}");
			writer.print(sb.toString());
			writer.close();
			
		} catch (IOException e) {
			
		}finally{
			writer.close();
		}
		
	}
	

	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}
	
}
