package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementReviewerPay;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.UserOrg;
import com.zhiwei.credit.service.creditFlow.fund.project.SettlementReviewerPayService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DemensionService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.system.UserOrgService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class OrganizationAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private UserOrgService userOrgService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private DemensionService demensionService;
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private SettlementReviewerPayService settlementReviewerPayService;
	private Organization organization;
	
	private Long orgId;
	private Long parentId;//上级部门id
	private Short orgType;
	private String settlementType; //部门保有量类型

	public String getSettlementType() {
		return settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		
		String orgSupId=getRequest().getParameter("orgSupId");
		if(StringUtils.isNotEmpty(orgSupId)){
			Organization supOrg=organizationService.get(new Long(orgSupId));
			filter.addFilter("Q_path_S_LFK", supOrg==null?"0.":supOrg.getPath());
		}
		filter.addSorted("path", "asc");
		List<Organization> list= organizationService.getAll(filter);
		
		Type type=new TypeToken<List<Organization>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list,type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	/**
	 * 根据流程的key查询已经分配了该流程的分公司
	 * @return
	 * add by lu 2013.07.04
	 */
	public String listByProcessName(){
		/*QueryFilter filter = new QueryFilter(getRequest());
		int start = filter.getPagingBean().getStart();
		int limit = filter.getPagingBean().getPageSize();
		PagingBean pb = new PagingBean(start, limit);*/
		String processName = getRequest().getParameter("processName");
		if(processName!=null&&!"".equals(processName)&&!"null".equals(processName)){
			List<ProDefinition> list = proDefinitionService.listByProcessName(processName);
			if(list!=null&&list.size()!=0){
				Long[] branchCompanyIds = new Long[list.size()];
				for(int i=0;i<list.size();i++){
					branchCompanyIds[i] = list.get(i).getBranchCompanyId();
				}
				List<Organization> orgList = organizationService.listByOrgIds(branchCompanyIds);
				StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(orgList==null?0:orgList.size()).append(",result:");
				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
				buff.append(gson.toJson(orgList));
				buff.append("}");
				jsonString = buff.toString();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	@LogResource(description = "删除组织")
	public String multiDel(){
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null && !ids.equals("")){
			for(String id:ids){
				//删除某个组织及其下属组织
				//List<AppUser>users=appUserService.getDepUsers(organizationService.get(new Long(id)).getPath(), null, null);
				//organizationService.delCascade(new Long(id),users);
				Organization og=this.organizationService.get(Long.valueOf(id));
				if(null!=og){
					
					if(og.getOrgType()==2 || og.getOrgType()==3){
						List<AppUser>users=appUserService.getDepUsers(organizationService.get(new Long(id)).getPath(), null, null);
						organizationService.delCascade(new Long(id),users);
					}
					else if(og.getOrgType()==1){
						  og.setDelFlag(Short.valueOf("0"));
						  this.organizationService.merge(og);
					}
			
				}
			}
		}
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String get(){
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		Organization organization=organizationService.get(orgId);	
		List<AppUser> list = appUserService.getChargeOrgUsers(organization.getUserOrgs());
		String chargeIds = "";
		String chargeNames = "";
		if(list!=null&&list.size()>0){
			for(int index=0;index<list.size();index++){
				AppUser au = list.get(index);
				if(index==0){
					chargeIds += au.getUserId();
					chargeNames += au.getFullname();
				}else{
					chargeIds += ","+au.getUserId();
					chargeNames += ","+au.getFullname();
				}
			}
		}
		
		//将数据转成JSON格式
		Gson gson = new Gson();
		StringBuffer sb = new StringBuffer("{success:true,data:{");
		sb.append("\"orgId\":" + organization.getOrgId() + ",");			
		sb.append("\"demId\":" + organization.getDemId() + ",");
		sb.append("\"orgName\":" + gson.toJson(organization.getOrgName()) + ",");
		sb.append("\"orgDesc\":" + gson.toJson(organization.getOrgDesc()) + ",");
		sb.append("\"orgSupId\":" + organization.getOrgSupId() + ",");
		sb.append("\"orgType\":" + organization.getOrgType() + ",");
		sb.append("\"trusteeshipMode\":" + organization.getTrusteeshipMode() + ",");
		sb.append("\"equityRelationship\":" + organization.getEquityRelationship() + ",");
		sb.append("\"chargeIds\":\"" + chargeIds + "\",");
		sb.append("\"key\":\"" + organization.getKey() + "\",");
		sb.append("\"linkman\":\"" + organization.getLinkman()+ "\",");
		sb.append("\"linktel\":\"" + organization.getLinktel()+ "\",");
		sb.append("\"postCode\":\"" + organization.getPostCode()+ "\",");
		sb.append("\"fax\":\"" + organization.getFax()+ "\",");
		sb.append("\"address\":\"" + organization.getAddress()+ "\",");
		sb.append("\"acronym\":\"" + organization.getAcronym()+ "\",");
		sb.append("\"branchNO\":\"" + organization.getBranchNO()+ "\",");
		sb.append("\"delFlag\":\"" +organization.getDelFlag()+ "\",");
		sb.append("\"capital\":"+organization.getCapital()+",");
		sb.append("\"shopPhone\":\""+organization.getShopPhone()+"\",");
		sb.append("\"commissionRate\":\""+organization.getCommissionRate()+"\",");
		sb.append("\"recommendCode\":\""+organization.getRecommendCode()+"\",");
		if(null==organization.getSettlementType()){
			sb.append("\"settlementType\":\""+0+"\",");
		}else
		sb.append("\"settlementType\":\""+organization.getSettlementType()+"\",");
		sb.append("\"foundingTime\":\"" + (null!=organization.getFoundingTime()?sd.format(organization.getFoundingTime()):null)+ "\",");
		sb.append("\"chargeNames\":" + gson.toJson(chargeNames));
		sb.append("}}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

    public String getControlName(){
    	List<Organization> list=new ArrayList<Organization>();
    	String controlCompanyId=ContextUtil.getBranchIdsStr();
    	String [] tempA=controlCompanyId.split(",");
    	
    	if(tempA.length>0){
    		for(int i=0;i<tempA.length;i++){
    			if(null!=tempA[i] && !tempA[i].equals("")){
    			  Organization organization=this.organizationService.get(Long.valueOf(tempA[i]));
    			  list.add(organization);
    			}
    		}
    	}
    	StringBuffer buff = new StringBuffer("[");
    	for (Organization o:list){
    		buff.append("[").append(o.getOrgId()).append(",'")
			.append(o.getOrgName()).append("'],");
    	}
    	if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
    	return SUCCESS;
    }
    /**
     * 查询出所有门店
     * @return
     */
    public String getOrgUserName(){
    	List<Organization> list=new ArrayList<Organization>();
    	
    	list=this.organizationService.listAllBranch();
    	
    	StringBuffer buff = new StringBuffer("[");
    	for (Organization o:list){
    		buff.append("[").append(o.getOrgId()).append(",'")
			.append(o.getOrgName()).append("'],");
    	}
    	if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
    	return SUCCESS;
    }
    
	/**
	 * 添加及保存操作
	 */
    @LogResource(description = "添加或修改组织")
	public String save(){
		String chargeIds = getRequest().getParameter("chargeIds");
		String isAddCompany=getRequest().getParameter("addCompany");
		Organization org = new Organization();
		int count = 0;
		//如果是
		if(null!=organization.getSettlementType()&& Short.valueOf("1").equals(organization.getSettlementType())){  
			count = this.organizationService.checkSettlementType(organization.getOrgId());
		}		
		if(organization.getOrgId()==null){
			if(count != 0){
				   setJsonString("{success:false,msg:'已有部门选择为投资推广部，不能重复选择!'}");
				   return SUCCESS;
			   }
			if(null!=isAddCompany && !"".equals(isAddCompany))
			{	
				
				if(Boolean.valueOf(isAddCompany)){
					   boolean orgNameIsHave=this.organizationService.isExist("orgName", organization.getOrgName().trim());
					   if(orgNameIsHave){
						   setJsonString("{success:false,msg:'该分公司名称已经存在!'}");
						   return SUCCESS;
					   }
					   else if(this.organizationService.isExist("key", organization.getKey().trim()))
					   {
						   setJsonString("{success:false,msg:'访问地址后缀已经存在!'}");
						   return SUCCESS;
					   }else if(this.organizationService.isExist("branchNO", organization.getBranchNO().trim())){
						   setJsonString("{success:false,msg:'分公司编号已经存在!'}");
						   return SUCCESS;
					   }else if(this.organizationService.isExist("acronym", organization.getAcronym().trim())){
						   setJsonString("{success:false,msg:'该分公司缩写已经存在!'}");
						   return SUCCESS;
					   }
				}
			}
			organization.setCreatetime(new Date());
			organization.setUpdatetime(new Date());
			organization.setCreatorId(ContextUtil.getCurrentUserId());
			organization.setUpdateId(ContextUtil.getCurrentUserId());
			if(null!=organization.getOrgSupId() && organization.getOrgSupId().intValue()==0){
				organization.setOrgSupId(organizationService.getGroupCompany().getOrgId());
			}
			
			organizationService.save(organization);
			Long parentId=organization.getOrgSupId();
			if(parentId==0){
				organization.setPath("0."+organization.getOrgId()+".");
				organization.setDepth(1l);
			}else{
				Organization supOrg=organizationService.get(parentId);
				organization.setPath(supOrg.getPath()+organization.getOrgId()+".");
				organization.setDepth(supOrg.getDepth()==null?1:supOrg.getDepth()+1);
			}
			organization.setVmName(organization.getOrgName());//分公司vm文件的访问路径。新添后不可修改。
			org = organizationService.save(organization);
			if(chargeIds!=null&&chargeIds.length()>0){
				String[] pids = chargeIds.split(",");
				for(int index=0;index<pids.length;index++){
					UserOrg userOrg = new UserOrg();
					userOrg.setUserid(new Long(pids[index]));
					userOrg.setOrgId(organization.getOrgId());
					userOrg.setIsPrimary(new Short("1"));
					userOrg.setIsCharge(new Short("1"));
					userOrgService.save(userOrg);
				}
			}
		}else{
			Organization orgOrganization=organizationService.get(organization.getOrgId());
			if(null!=orgOrganization.getSettlementType()&&1!=orgOrganization.getSettlementType()&&count!=0){
 				  setJsonString("{success:false,msg:'已有部门选择为投资推广部，不能重复选择!'}");
				   return SUCCESS;
			}
			try{
				if(null!=isAddCompany && !"".equals(isAddCompany))
				{
					if(Boolean.valueOf(isAddCompany)){
						if(Boolean.valueOf(isAddCompany)){
							
							  boolean orgNameIsHave=this.organizationService.isUpdateExit("orgName", organization.getOrgName(), organization.getOrgId());
							  if(orgNameIsHave){
								   setJsonString("{success:false,msg:'该分公司名称已经存在!'}");
								   return SUCCESS;
							  }
							  else if(this.organizationService.isUpdateExit("key", organization.getKey(), organization.getOrgId())){
								   setJsonString("{success:false,msg:'访问地址后缀已经存在!'}");
								   return SUCCESS;
							  }
							  else if(this.organizationService.isUpdateExit("branchNO", String.valueOf(organization.getBranchNO()), organization.getOrgId())){
								   setJsonString("{success:false,msg:'分公司编号已经存在!'}");
								   return SUCCESS;
							  }
							  else if(this.organizationService.isUpdateExit("acronym", organization.getAcronym(), organization.getOrgId())){
								   setJsonString("{success:false,msg:'该分公司缩写已经存在!'}");
								   return SUCCESS;
							  }
						}
						
					}
				}
				Long parentId=orgOrganization.getOrgSupId();
				if(parentId==0){
					orgOrganization.setPath("0."+organization.getOrgId()+".");
					orgOrganization.setDepth(1l);
				}else{
					Organization supOrg=organizationService.get(parentId);
					orgOrganization.setPath(supOrg.getPath()+orgOrganization.getOrgId()+".");
					orgOrganization.setDepth(supOrg.getDepth()+1);
				}
				orgOrganization.setUpdatetime(new Date());
				orgOrganization.setUpdateId(ContextUtil.getCurrentUserId());
				Set<UserOrg> userOrgs=orgOrganization.getUserOrgs();
				BeanUtil.copyNotNullProperties(orgOrganization, organization);
				orgOrganization.setUserOrgs(userOrgs);
				org = organizationService.save(orgOrganization);

				/*userOrgService.delUserOrg(orgOrganization.getOrgId());
				if(chargeIds!=null&&chargeIds.length()>0){
					String[] pids = chargeIds.split(",");
					for(int index=0;index<pids.length;index++){
						UserOrg userOrg = new UserOrg();
						userOrg.setUserid(new Long(pids[index]));
						userOrg.setOrgId(organization.getOrgId());
						userOrg.setIsPrimary(new Short("1"));
						userOrg.setIsCharge(new Short("1"));
						userOrgService.save(userOrg);
					}
				}*/
				
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		//如果部门是属于计算提成部门
		if(org!=null&&org.getSettlementType()!=null&&Short.valueOf("0")!=org.getSettlementType()&&Short.valueOf("2").equals(org.getOrgType())){
			List<SettlementReviewerPay> sets = settlementReviewerPayService.getByOrgId(org.getOrgId().toString());
			if(sets!=null&&sets.size()>0){
				
			}else{
				SettlementReviewerPay pay = new SettlementReviewerPay();   //如果没有在里边先创建一个奖励记录
				pay.setCollectionDepartment(org.getOrgId().toString());
				pay.setFactMoney(BigDecimal.ZERO);
				settlementReviewerPayService.save(pay);
			}
			
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 取得树
	 * @return
	 */
	public String tree(){
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
		if(flag){
			String type=this.getRequest().getParameter("type");
			String pDemId=getRequest().getParameter("demId");
			Long demId=0l;
			if(StringUtils.isNotEmpty(pDemId)){
				demId=new Long(pDemId);
			}
			if((null!=type && type.equals("branch")) || type.equals("undefined")){
				Long companyId=ContextUtil.getLoginCompanyId();
				String roleType=ContextUtil.getRoleTypeSession();
				if(null!=companyId){
					Organization org=organizationService.get(companyId);
					if(null!=org){
						if(null!=roleType && "business".equals(roleType)){
							String iconClss=this.orgnazationIconClss(org.getOrgType());
							StringBuffer buff = new StringBuffer("[{id:'"+org.getOrgId()+"',text:'"+org.getOrgName()+"',orgType:'"+org.getOrgType()+"',iconCls:'"+iconClss+"',orgDem:'"+org.getDemId()+"',expanded:true,children:[");
							List<Organization> orgList=null;
							if(null!=org.getOrgType() && org.getOrgType()==0){
								 orgList=organizationService.getByParentOfZC(companyId, demId);
							}else{
								orgList=organizationService.getByParent(companyId,demId);
							}
							for(Organization orga:orgList){
								String iconCls=this.orgnazationIconClss(org.getOrgType());
								buff.append("{id:'"+orga.getOrgId()).append("',text:'"+orga.getOrgName().trim()).append("',")
									.append("orgType:'").append(orga.getOrgType()).append("',")
									.append("iconCls:'").append(iconCls).append("',")
								.append("orgDem:'").append(orga.getDemId()).append("',");
							 buff.append(getChildList(orga.getOrgId(),demId));
							}
							if (!orgList.isEmpty()) {
								buff.deleteCharAt(buff.length() - 1);
						    }
							buff.append("]}]");
							setJsonString(buff.toString());
						}else if(null!=roleType && "control".equals(roleType)){
							StringBuffer buff = new StringBuffer("["/*"[{id:'"+0+"',text:'全部',expanded:true,children:["*/);
							
							List<Organization> orgList=organizationService.getByParentOfZControl(companyId, demId);
							for(Organization orga:orgList){
								String iconCls=this.orgnazationIconClss(org.getOrgType());
								if(orga.getOrgType()==0){
									buff.append("{id:'"+orga.getOrgId()+"',text:'"+orga.getOrgName()+"',orgType:'"+orga.getOrgType()+"',iconCls:'"+iconCls+"',orgDem:'"+orga.getDemId()+"',expanded:true,children:[");
									List<Organization> list=organizationService.getByParentOfZC(orga.getOrgId(), null);
									for(Organization org1:list){
										String iconClss=this.orgnazationIconClss(org.getOrgType());
										buff.append("{id:'"+org1.getOrgId()).append("',text:'"+org1.getOrgName().trim()).append("',")
											.append("orgType:'").append(org1.getOrgType()).append("',")
											.append("iconClss:'").append(iconClss).append("',")
										.append("orgDem:'").append(org1.getDemId()).append("',");
									 buff.append(getChildList(org1.getOrgId(),demId));
									}
									if (!list.isEmpty()) {
										buff.deleteCharAt(buff.length() - 1);
								    }
									buff.append("]},");
								}else{
									buff.append("{id:'"+orga.getOrgId()).append("',text:'"+orga.getOrgName().trim()).append("',")
									.append("orgType:'").append(orga.getOrgType()).append("',")
									.append("iconCls:'").append(iconCls).append("',")
									.append("orgDem:'").append(orga.getDemId()).append("',");
									buff.append(getChildList(orga.getOrgId(),demId));
								}
							}
							if (!orgList.isEmpty()) {
								buff.deleteCharAt(buff.length() - 1);
						    }
							//buff.append("]}]");
							buff.append("]");
							setJsonString(buff.toString());
						}
						
						
						
					}
				}
			}else if(null!=type && type.equals("all")){
			
				StringBuffer buff = new StringBuffer("["/*"[{id:'0',text:'全部',expanded:true,children:["*/);
				Organization orga=organizationService.getHeadquarters();
				if(null!=orga && null!=orga.getOrgId()){
					String iconCls=this.orgnazationIconClss(orga.getOrgType());
					buff.append("{id:'"+orga.getOrgId()+"',text:'"+orga.getOrgName()+"',orgType:'"+orga.getOrgType()+"',iconCls:'"+iconCls+"',orgDem:'"+orga.getDemId()+"',expanded:true,children:[");
					List<Organization> list=organizationService.getByParentOfZC(orga.getOrgId(), null);
					for(Organization org:list){
						String iconClss=this.orgnazationIconClss(org.getOrgType());
						buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName().trim()).append("',")
							.append("orgType:'").append(org.getOrgType()).append("',")
							.append("iconCls:'").append(iconClss).append("',")
						.append("orgDem:'").append(org.getDemId()).append("',");
					 buff.append(getChildList(org.getOrgId(),demId));
					}
					if (!list.isEmpty()) {
						buff.deleteCharAt(buff.length() - 1);
				    }
					buff.append("]},");
					List<Organization> orgList=organizationService.getBranchCompany(null);
					for(Organization org:orgList){
						String iconClss=this.orgnazationIconClss(org.getOrgType());
						buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName().trim()).append("',")
							.append("orgType:'").append(org.getOrgType()).append("',")
							.append("iconCls:'").append(iconClss).append("',")
						.append("orgDem:'").append(org.getDemId()).append("',");
					 buff.append(getChildList(org.getOrgId(),demId));
					}
					if (!orgList.isEmpty()) {
						buff.deleteCharAt(buff.length() - 1);
				    }
					//buff.append("]}]");
					buff.append("]");
					setJsonString(buff.toString());
				}				
			}else if(null!=type && type.equals("flow")){
				String companyId=this.getRequest().getParameter("branchCompanyId");
				if(null==companyId || companyId.equals("0")|| companyId.equals("1")){
					Organization org=organizationService.getHeadquarters();
					
						StringBuffer buff = new StringBuffer("[{id:'"+org.getOrgId()+"',text:'"+org.getOrgName()+"',orgType:'"+org.getOrgType()+"',orgDem:'"+org.getDemId()+"',expanded:true,children:[");
						List<Organization >  orgList=organizationService.getByParentOfZC(org.getOrgId(), null);
						for(Organization orga:orgList){
							buff.append("{id:'"+orga.getOrgId()).append("',text:'"+orga.getOrgName().trim()).append("',")
							.append("orgType:'").append(orga.getOrgType()).append("',")
							.append("orgDem:'").append(orga.getDemId()).append("',");
							buff.append(getChildList(orga.getOrgId(),demId));
						}
						if (!orgList.isEmpty()) {
							buff.deleteCharAt(buff.length() - 1);
					    }
						buff.append("]}]");
						setJsonString(buff.toString());
				}else{
					StringBuffer buff = new StringBuffer("["/*"[{id:'0',text:'全部',expanded:true,children:["*/);
					Organization orga=organizationService.getHeadquarters();
					if(null!=orga && null!=orga.getOrgId()){
						buff.append("{id:'"+orga.getOrgId()+"',text:'"+orga.getOrgName()+"',orgType:'"+orga.getOrgType()+"',orgDem:'"+orga.getDemId()+"',expanded:true,children:[");
						List<Organization> list=organizationService.getByParentOfZC(orga.getOrgId(), null);
						for(Organization o:list){
							buff.append("{id:'"+o.getOrgId()).append("',text:'"+o.getOrgName().trim()).append("',")
								.append("orgType:'").append(o.getOrgType()).append("',")
							.append("orgDem:'").append(o.getDemId()).append("',");
						 buff.append(getChildList(o.getOrgId(),demId));
						}
						if (!list.isEmpty()) {
							buff.deleteCharAt(buff.length() - 1);
					    }
						buff.append("]},");
					Organization org=organizationService.get(Long.valueOf(companyId));
					List<Organization> orgList=organizationService.getByParent(org.getOrgId(),null);
					buff.append("{id:'"+org.getOrgId()+"',text:'"+org.getOrgName()+"',orgType:'"+org.getOrgType()+"',orgDem:'"+org.getDemId()+"',expanded:true,children:[");
					for(Organization r:orgList){
						buff.append("{id:'"+r.getOrgId()).append("',text:'"+r.getOrgName().trim()).append("',")
							.append("orgType:'").append(r.getOrgType()).append("',")
						.append("orgDem:'").append(r.getDemId()).append("',");
					 buff.append(getChildList(r.getOrgId(),demId));
					}
					if (!orgList.isEmpty()) {
						buff.deleteCharAt(buff.length() - 1);
				    }
					//buff.append("]}]}]");
					buff.append("]}]");
					setJsonString(buff.toString());
					
				}
				}
			}
		}else{
			
			String pDemId=getRequest().getParameter("demId");
			Long demId=0l;
			if(StringUtils.isNotEmpty(pDemId)){
				demId=new Long(pDemId);
			}
			Organization orga=organizationService.getHeadquarters();
			if(null!=orga && null!=orga.getOrgId()){
				StringBuffer buff = new StringBuffer("[{id:'"+orga.getOrgId()+"',text:'"+orga.getOrgName()+"',orgType:'"+orga.getOrgType()+"',orgDem:'"+orga.getDemId()+"',expanded:true,children:[");
				List<Organization> orgList=organizationService.getByParent(orga.getOrgId(),demId);
				for(Organization org:orgList){
					buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName().trim()).append("',")
						.append("orgType:'").append(org.getOrgType()).append("',")
					.append("orgDem:'").append(org.getDemId()).append("',");
				 buff.append(getChildList(org.getOrgId(),demId));
				}
				if (!orgList.isEmpty()) {
					buff.deleteCharAt(buff.length() - 1);
			    }
				buff.append("]}]");
				setJsonString(buff.toString());
			}
		/*	String pDemId=getRequest().getParameter("demId");
			Long demId=0l;
			if(StringUtils.isNotEmpty(pDemId)){
				demId=new Long(pDemId);
			}
			StringBuffer buff = new StringBuffer("[{id:'"+0+"',text:'全部',expanded:true,children:[");
			List<Organization> orgList=organizationService.getByParent(new Long(0l),demId);
			for(Organization org:orgList){
				buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName().trim()).append("',")
					.append("orgType:'").append(org.getOrgType()).append("',")
				.append("orgDem:'").append(org.getDemId()).append("',");
			 buff.append(getChildList(org.getOrgId(),demId));
			}
			if (!orgList.isEmpty()) {
				buff.deleteCharAt(buff.length() - 1);
		    }
			buff.append("]}]");
			setJsonString(buff.toString());*/
		}
		return SUCCESS;
	}
	public String treeCompany(){
		String companySession="";
		companySession="henan";
		Organization og=null;
		Object obj=this.getSession().getAttribute("company");
		String companyId=this.getRequest().getParameter("companyId");
		if(null!=companyId && !"".equals(companyId)){
			og=this.organizationService.get(Long.valueOf(companyId));
		}else{
			if(null!=obj && !"null".equals(obj.toString())){
				companySession=(String)obj;
				og=this.organizationService.getBranchCompanyByKey(companySession);
			}
			else{
				og=this.organizationService.getGroupCompany();
			}
		}
		
		StringBuffer buff=null;
		String iconCls=this.orgnazationIconClss(og.getOrgType());
		if(og.getOrgType()==0){
		  buff = new StringBuffer("[{id:'"+og.getOrgId()+"',orgType:'"+og.getOrgType()+"',iconCls:'"+iconCls+"',text:'"+og.getOrgName().trim()+"',expanded:true,children:[");
		}
		else{
		   buff = new StringBuffer("[{id:'"+og.getOrgId()+"',orgType:'"+og.getOrgType()+"',iconCls:'"+iconCls+"',text:'"+this.organizationService.getGroupCompany().getOrgName().trim()+"_"+og.getOrgName().trim()+"',expanded:true,children:[");
		}
		boolean isOnlyShowDepart=false;
		String isOnlyShowDepart_=this.getRequest().getParameter("onlyShowDepart");
		if(null!=isOnlyShowDepart_){
			isOnlyShowDepart=Boolean.valueOf(isOnlyShowDepart_);
		}
		List<Organization> orgList=organizationService.getByParent(og.getOrgId(),null);
		for(Organization org:orgList){
			String iconClss=this.orgnazationIconClss(org.getOrgType());
			 if(!"".equals(companyId) && og.getOrgType()==0){
				   if(org.getOrgType()==2||org.getOrgType()==3){
					    buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName().trim()).append("',")
						.append("orgType:'").append(org.getOrgType()).append("',")
						.append("iconCls:'").append(iconClss).append("',")
						.append("orgDem:'").append(org.getDemId()).append("',");
						 buff.append(getChildList(org.getOrgId(),null));
				   }
			 }else{
				     if(isOnlyShowDepart){
				    	 if(org.getOrgType()==2||org.getOrgType()==3){
				    	     	buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName().trim()).append("',")
								.append("orgType:'").append(org.getOrgType()).append("',")
								.append("iconCls:'").append(iconClss).append("',")
								.append("orgDem:'").append(org.getDemId()).append("',");
								 buff.append(getChildList(org.getOrgId(),null));
				    	 }
				     }else{
				             buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName().trim()).append("',")
							.append("orgType:'").append(org.getOrgType()).append("',")
							.append("iconCls:'").append(iconClss).append("',")
							.append("orgDem:'").append(org.getDemId()).append("',");
							 buff.append(getChildList(org.getOrgId(),null));
				     }
			 }
		}
		if (!orgList.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]}]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	public String treeGroup(){
		String pDemId=getRequest().getParameter("demId");
		String orgId=getRequest().getParameter("orgId");
		String isOnlyShowCompany=getRequest().getParameter("isOnlyShowCompany");
		Organization o=null;
		if(null!=orgId && !orgId.equals("")){
			o=this.organizationService.get(Long.valueOf(orgId));
		}
		else{
			o=organizationService.getGroupCompany();
		}
		String groupName="";
		Long id=null;
		
		if(null!=o){
			groupName=o.getOrgName();
			id=o.getOrgId();
		}
		Long demId=0l;
		if(StringUtils.isNotEmpty(pDemId)){
			demId=new Long(pDemId);
		}
		String iconCls=this.orgnazationIconClss(orgType);
		StringBuffer buff = new StringBuffer("[{id:'"+id+"',orgType:'"+o.getOrgType()+"',text:'"+o.getOrgName()+"',iconCls:'"+iconCls+"',expanded:true,children:[");
		List<Organization> orgList=organizationService.getByParent(new Long(o.getOrgId()),demId);
		for(Organization org:orgList){
			String iconClss=this.orgnazationIconClss(org.getOrgType());
			 if(null==isOnlyShowCompany){
				
				 buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName().trim()).append("',")
				 .append("orgType:'").append(org.getOrgType()).append("',")
				 .append("iconCls:'").append(iconClss).append("',")
				 .append("orgDem:'").append(org.getDemId()).append("',");
			     buff.append(getChildList(org.getOrgId(),demId));}
			 else{
				  if(org.getOrgType().intValue()==1){
					  buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName().trim()).append("',")
					  .append("orgType:'").append(org.getOrgType()).append("',")
					  .append("iconCls:'").append(iconClss).append("',")
					  .append("orgDem:'").append(org.getDemId()).append("',");
					   buff.append("leaf:true,expanded:true},");
				  }
			 }
		}
		if (!orgList.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]}]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	public String getBranchCompany(){
		AppUser users = (AppUser) getSession().getAttribute("users");
		parentId=users.getDepartment().getDepId();
		String companySession="";
		companySession="henan";
		Object obj=this.getSession().getAttribute("company");
		Organization og=null;
		if(parentId==null){
			if(null!=obj && !obj.toString().equals("null")){
				companySession=(String)obj;
				og=this.organizationService.getBranchCompanyByKey(companySession);
			}
			else{
				og=this.organizationService.getGroupCompany();
			}
		}else{
			og = this.organizationService.get(parentId);
		}
		Long id=null;
		String groupName=og.getOrgName();
		Short orgType=og.getOrgType();
		id=og.getOrgId();
		Long demId=0l;
		String iconCls=this.orgnazationIconClss(orgType);
		StringBuffer buff = new StringBuffer("[{id:'"+id+"',orgType:'"+orgType+"',iconCls:'"+iconCls+"',text:'"+groupName+"',expanded:true,children:[");
		List<Organization> orgList =organizationService.getByParent(new Long(og.getOrgId()),demId);
		for(Organization org:orgList){
			String iconClss=this.orgnazationIconClss(org.getOrgType());
			if(org.getOrgType()==2 || org.getOrgType()==3){
			
			buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName().trim()).
			append("',iconCls:'"+iconClss).append("',")
			.append("orgType:'").append(org.getOrgType()).append("',")
			.append("orgDem:'").append(org.getDemId()).append("',");
			buff.append(getChildList(org.getOrgId(),demId));}
		}
		if (!orgList.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]}]");
		setJsonString(buff.toString());
		System.out.println("json=="+buff.toString());
		return SUCCESS;
	}
	public String getChildList(Long parentId,Long demId){
		StringBuffer buff=new StringBuffer();
		List<Organization> orgList=organizationService.getByParent(parentId,demId);
		if(orgList.size()==0){
			buff.append("leaf:true,expanded:true},");
			return buff.toString(); 
		}else {
			buff.append("expanded:true,children:[");
			for(Organization org:orgList){
				String iconClss=this.orgnazationIconClss(org.getOrgType());
				buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName().trim()).append("',")
				.append("orgType:'").append(org.getOrgType()).append("',")
				.append("iconCls:'").append(iconClss).append("',")
				.append("orgDem:'").append(org.getDemId()).append("',");
				buff.append(getChildList(org.getOrgId(),demId));
			}
			buff.deleteCharAt(buff.length() - 1);
			buff.append("]},");
			return buff.toString();
		}
	}
	/**
	 * 明细
	 * @return
	 */
	public String detail(){
		String orgId=getRequest().getParameter("orgId");
		if(StringUtils.isNotEmpty(orgId) && !"0".equals(orgId)){
			Organization organization=organizationService.get(new Long(orgId));
			// 主要负责人
			String chargeUser = "";
			Iterator<UserOrg> auit = organization.getUserOrgs().iterator();
			while(auit.hasNext()){
				UserOrg uo = auit.next();
				if(uo.getIsCharge()!=null&&uo.getIsCharge()==1){
					chargeUser += uo.getAppUser().getFullname()+",";
				}
			}
			if(chargeUser.length()>0){chargeUser=chargeUser.substring(0, chargeUser.length()-1);}
			getRequest().setAttribute("chargeUser", chargeUser);
			
			// 维度
			getRequest().setAttribute("demName", demensionService.get(organization.getDemId()).getDemName());
			if(organization.getOrgSupId()!=null && organization.getOrgSupId()!=-1){
				Organization supOrg=organizationService.get(organization.getOrgSupId());
				getRequest().setAttribute("supOrg",supOrg);
			}
			
			getRequest().setAttribute("org", organization);
		}
		return "detail";
		
	}
	
	public String getAllCompany(){
		
		String orgName=this.getRequest().getParameter("orgName");
		Map<String, String> map =new HashMap<String, String>();
		if(null!=orgName && !"".equals(orgName)){
			map.put("orgName", orgName);
		}
		List<Organization> orgList=organizationService.getBranchCompany(map);
		List<Organization> returnList=new java.util.ArrayList<Organization>();
		for(Organization or:orgList){
			String chargeUser = "";
			or.setAppUsers(null);
			Iterator<UserOrg> auit = or.getUserOrgs().iterator();
			while(auit.hasNext()){
				UserOrg uo = auit.next();
				if(uo.getIsCharge()!=null&&uo.getIsCharge()==1){
					chargeUser += uo.getAppUser().getFullname()+",";
				}
			}
			if(!chargeUser.equals(""))
			chargeUser=chargeUser.substring(0, chargeUser.length()-1);
			or.setChargeUser(chargeUser);
			or.setUserOrgs(null);
			returnList.add(or);
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(returnList.size()).append(",result:");
		Gson gson=new Gson();
		buff.append(gson.toJson(returnList));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 取得树
	 * @return
	 */
	public String orgStructure(){
		String pDemId=getRequest().getParameter("demId");
		Long demId=0l;
		if(StringUtils.isNotEmpty(pDemId)){
			demId=new Long(pDemId);
		}
		StringBuffer buff = new StringBuffer("[{id:'"+0+"',text:'全部',expanded:true,children:[");
		List<Organization> orgList=organizationService.getByParent(new Long(0l),demId);
		for(Organization org:orgList){
			buff.append("{id:'"+org.getOrgId())
			.append("',text:'"+org.getOrgName()).append("',")
			.append("flag:'").append("org").append("',");
			 buff.append(getDeps(org.getOrgId(),demId));
		}
		if (!orgList.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]}]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	
	public String getDeps(Long parentId,Long demId){
		StringBuffer buff=new StringBuffer();
		List<Organization> orgList=organizationService.getByParent(parentId,demId);
		List<AppUser> users=appUserService.getDepUsers(organizationService.get(parentId).getPath(), null, null);
		if(orgList.size()==0&&users.size()==0){
			buff.append("leaf:true,expanded:true},");
			return buff.toString();
		} else if(orgList.size()==0&&users.size()!=0){
			buff.append("expanded:true,children:[");
			buff.append("{id:'org"+parentId+"',text:'员工',flag:'user',expanded:true,children:[");
			buff.append(getUsers(users,parentId));
			buff.deleteCharAt(buff.length() - 1);
			buff.append("]},");
			return buff.toString();
		} else {
			buff.append("expanded:true,children:[");
			for(Organization org:orgList){
				buff.append("{id:'org"+parentId+"',text:'员工',flag:'user',expanded:true,");
				buff.append("children:[");
				buff.append(getUsers(users,parentId));
				buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName()).append("',")
				.append("flag:'").append("org").append("',");
			    buff.append(getDeps(org.getOrgId(),demId));
			}
			buff.deleteCharAt(buff.length() - 1);
			buff.append("]},");
			return buff.toString();
		}
	}
	
	/**
	 * 
	 * 根据部门路径取得部门员工<br>
	 *	 
	 * @param 参数
	 * orgPath 组织路径
	 *
	 * @return 返回 员工json
	 */
	public String getUsers(List<AppUser> users,Long orgId){
		StringBuffer buff=new StringBuffer();
//		List<AppUser> users=appUserService.getDepUsers(orgPath, null, null);
		if(users.size()==0){
			buff.append("]},");
			return buff.toString(); 
		}else {
			for(AppUser au:users){
				buff.append("{id:'"+"org"+orgId+"_"+au.getUserId())
				.append("',text:'"+au.getFullname()).append("',")
				.append("flag:'").append("user").append("',")
				.append("leaf:true,expanded:true").append("},");
			}
			buff.deleteCharAt(buff.length() - 1);
			buff.append("]},");
			return buff.toString();
		}
	}
	
	public String getDepartmentByCompany(){
		String orgId=getRequest().getParameter("orgId");
		Organization o=null;
		if(null!=orgId && !orgId.equals("") && !orgId.equals("0")){
			o=this.organizationService.get(Long.valueOf(orgId));
		}
		else{
			o=this.organizationService.getGroupCompany();
			orgId=String.valueOf(o.getOrgId());
		}
		String groupName="";
		Long id=null;
		if(null!=o){
			groupName=o.getOrgName();
			id=o.getOrgId();
		}
		String iconCls=this.orgnazationIconClss(o.getOrgType());
		StringBuffer buff = new StringBuffer("[{id:'"+id+"',orgType:'"+o.getOrgType()+"',iconCls:'"+iconCls+"',text:'"+groupName+"',expanded:true,children:[");
		List<Organization> orgList=organizationService.getDepartmentByCompany(Long.valueOf(orgId));
		for(Organization org:orgList){
			String iconClss=this.orgnazationIconClss(o.getOrgType());
			 buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName().trim()).append("',")
			 .append("orgType:'").append(org.getOrgType()).append("',")
			 .append("iconCls:'").append(iconClss).append("',")
			 .append("orgDem:'").append(org.getDemId()).append("',");
			 buff.append(getChildList(org.getOrgId(),null));
		}
		if (!orgList.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]}]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	/**
	 * 获取当前登录的公司  by shendex
	 * @return
	 */
	public String getLoginCompanyinfo(){
		
		String companySession="";
		Object obj=this.getSession().getAttribute("company");
		Organization og=null;
		if(null!=obj&&!"".equals(obj)&&!"null".equals(obj)){
			companySession=(String)obj;
			og=this.organizationService.getBranchCompanyByKey(companySession);
		}
		else{
			og=this.organizationService.getGroupCompany();
		}
		Gson gson = new Gson();
		StringBuffer sb = new StringBuffer("{success:true,data:{");
		sb.append("\"orgId\":" + og.getOrgId() + ",");			
		sb.append("\"orgName\":" + gson.toJson(og.getOrgName().trim()));
		sb.append("}}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 检查是否已选投资推广部
	 */
	public String check(){
		int count = this.organizationService.checkSettlementType(null);
		if(count != 0){
			setJsonString("{success:false}");
		}else{
			setJsonString("{success:true}");
		}
		return SUCCESS;
	}
	public String getCompanys(){
		String roleType=ContextUtil.getRoleTypeSession();
		List<Organization> list=new ArrayList<Organization>();
		if(null!=roleType && roleType.equals("control")){		 	
	    	String controlCompanyId=ContextUtil.getBranchIdsStr();
	    	String [] tempA=controlCompanyId.split(",");
	    	if(tempA.length>0){
	    		for(int i=0;i<tempA.length;i++){
	    			  Organization organization=this.organizationService.get(Long.valueOf(tempA[i]));
	    			  list.add(organization);
	    		}
	    	}	    	
		}else{
			Long companyId=ContextUtil.getLoginCompanyId();
			if(null!=companyId){
				Organization o=organizationService.get(companyId);
				if(null!=o){
					if(o.getOrgType()==0){
						List<Organization> olist=organizationService.getAllByOrgType();
						list=olist;
					}else{
						list.add(o);
					}
				}
			}
		}
		StringBuffer buff = new StringBuffer("[");
    	for (Organization o:list){
    		buff.append("[").append(o.getOrgId()).append(",'")
			.append(o.getOrgName()).append("'],");
    	}
    	if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	/**查询所有门店,不包括部门*/
	public void getShopList(){
	organizationService.getShopList(start,limit);

	}
	public String getShopList1(){
		String flag=this.getRequest().getParameter("flag");
		String isMobile =this.getRequest().getParameter("isMobile");
		
		if(null!=isMobile&&isMobile.equals("1")){
			QueryFilter query = new QueryFilter(getRequest());
			StringBuffer sb=new StringBuffer("");
			List<Organization> list = new ArrayList<Organization>();
			if(null!=flag && "1".equals(flag)){
				list = organizationService.getShopList(null,sb.toString());
			}else{
				list = organizationService.getShopList(null,sb.toString());
			}
			if(null!=list && list.size()>0){
				StringBuffer buff = new StringBuffer("[");
				for (Organization rt : list) {
					buff.append("{\"text\":\"").append(rt.getOrgName()).append("\",\"value\":\"")
							.append(rt.getOrgId()).append("\"},");
				}
				if (list.size() > 0) {
					buff.deleteCharAt(buff.length() - 1);
				}
				buff.append("]");
				setJsonString(buff.toString());
				System.out.println(buff.toString());
			}
			return SUCCESS;
		}else{
		QueryFilter query = new QueryFilter(getRequest());
		List<UserOrg> olist=userOrgService.listByUserId(ContextUtil.getCurrentUserId());
		StringBuffer sb=new StringBuffer("");
		for(UserOrg uo:olist){
			Organization o=uo.getOrganization();
			String path=o.getPath();
			 
			String str=path.replace(".", ",");
			sb.append(str);
		}
		if(null!=olist && olist.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		PagingBean pb = query.getPagingBean();
		List<Organization> list = new ArrayList<Organization>();
		list = organizationService.getShopList(pb,sb.toString());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",topics:");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list));
		buff.append("}");
		
		jsonString = buff.toString();
		return SUCCESS;
		}
		
	}
	
	
	public String getChildList(){
		if(parentId==null){
			parentId = Long.valueOf("1");
		}
		List<Organization> orgList = organizationService.getByPIdAndType(parentId, orgType);
		StringBuffer buff = new StringBuffer();
		Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(orgList));
		setJsonString(buff.toString());
		return SUCCESS;
	}
	/**
	 * 给公司和门店以及部分添加iconClss
	 * @param orgType
	 * @return
	 */
	public String orgnazationIconClss(Short orgType){
		String iconClss="";
		if(orgType.equals(Short.valueOf("0"))||orgType.equals(Short.valueOf("1"))){
			iconClss="btn-company";
		}else if(orgType.equals(Short.valueOf("2"))){
			iconClss="btn-department";
		}else if(orgType.equals(Short.valueOf("3"))){
			iconClss="btn-stores";
		}
		return iconClss;
	}
	
	/**
	 * 根据当前登陆用户查出所在的门店
	 * @return
	 */
	public String getByUserIdToStoreNameAndStoreNameId(){
		AppUser appuser=ContextUtil.getCurrentUser();
		AppUser  appuser2=appUserService.get(appuser.getUserId());
		if(!"admin@jzww".equals(appuser2.getUsername())){
			Organization organization=organizationService.getByUserIdToStoreNameAndStoreNameId(appuser2.getDepartment().getDepId());
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{success:true,result:");
		sb.append(gson.toJson(organization));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Short getOrgType() {
		return orgType;
	}

	public void setOrgType(Short orgType) {
		this.orgType = orgType;
	}

}
