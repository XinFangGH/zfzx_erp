package com.zhiwei.credit.action.creditFlow.lawsuitguarantee.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project.SgLawsuitguaranteeProject;
import com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project.VLawsuitguarantProject;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlPlansToChargeService;
import com.zhiwei.credit.service.creditFlow.lawsuitguarantee.project.SgLawsuitguaranteeProjectService;
import com.zhiwei.credit.service.creditFlow.lawsuitguarantee.project.VLawsuitguarantProjectService;
import com.zhiwei.credit.service.system.AppUserService;
/**
 * 
 * @author 
 *
 */
public class SgLawsuitguaranteeProjectAction extends BaseAction{
	@Resource
	private SgLawsuitguaranteeProjectService sgLawsuitguaranteeProjectService;
	@Resource
	private VLawsuitguarantProjectService vLawsuitguarantProjectService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private SlPlansToChargeService slPlansToChargeService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private AppUserService appUserService;
	
	private SgLawsuitguaranteeProject sgLawsuitguaranteeProject;
	
	private Long projectId;
	private Person person;
	private Enterprise enterprise;
	private short projectStatus;
	private Boolean isGrantedShowAllProjects;// 是否授权查看所有项目信息

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public SgLawsuitguaranteeProject getSgLawsuitguaranteeProject() {
		return sgLawsuitguaranteeProject;
	}

	public void setSgLawsuitguaranteeProject(SgLawsuitguaranteeProject sgLawsuitguaranteeProject) {
		this.sgLawsuitguaranteeProject = sgLawsuitguaranteeProject;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public short getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(short projectStatus) {
		this.projectStatus = projectStatus;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
	
	
    public Boolean getIsGrantedShowAllProjects() {
		return isGrantedShowAllProjects;
	}

	public void setIsGrantedShowAllProjects(Boolean isGrantedShowAllProjects) {
		this.isGrantedShowAllProjects = isGrantedShowAllProjects;
	}

	public String projectlist(){
	
    	String userIdsStr = "";
    	Short[] projectStatuses = null;
		if (!isGrantedShowAllProjects) {// 如果用户不拥有查看所有项目信息的权限
			userIdsStr = appUserService.getUsersStr();// 当前登录用户以及其所有下属用户
		}
    	projectStatuses = new Short[] { projectStatus };
		PagingBean pb = new PagingBean(start, limit);
	   List<VLawsuitguarantProject> list=vLawsuitguarantProjectService.getProjectList(userIdsStr, projectStatuses, pb, getRequest());
	   int counts=vLawsuitguarantProjectService.getProjectList(userIdsStr, projectStatuses, pb, getRequest()).size();

	   for (VLawsuitguarantProject vp : list) {
			String appuers = "";
			if (null != vp.getAppUserId()) {
				String[] appstr = vp.getAppUserId().split(",");
				Set<AppUser> userSet = this.appUserService
						.getAppUserByStr(appstr);
				for (AppUser s : userSet) {
					appuers = appuers + s.getFamilyName() + ",";
				}
			}
			if (appuers.length() > 0) {
				appuers = appuers.substring(0, appuers.length() - 1);
			}
			vp.setAppuserName(appuers);
		}
	   
		Type type = new TypeToken<List<VLawsuitguarantProject>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(counts).append(",result:");

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			//	.excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		buff.append("}");

		jsonString = buff.toString();
		return SUCCESS;
	
   } 
	public String update() {
		StringBuffer sb = new StringBuffer("{success:true,result:");
		try {
			String shareequity = this.getRequest().getParameter("gudongInfo");
			String slActualToChargeJsonData = this.getRequest().getParameter(
					"slActualToChargeJsonData");
			List<EnterpriseShareequity> listES = new ArrayList<EnterpriseShareequity>();
			if (null != shareequity && !"".equals(shareequity)) {
				String[] shareequityArr = shareequity.split("@");
				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper
								.toJava(parser.nextValue(),
										EnterpriseShareequity.class);
						listES.add(enterpriseShareequity);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		
			List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
			slActualToCharges = savejsoncharge(slActualToChargeJsonData,
					sgLawsuitguaranteeProject, Short.parseShort("0"));
			this.sgLawsuitguaranteeProjectService.updateInfo(
					sgLawsuitguaranteeProject, person, enterprise, listES,slActualToCharges);

			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
			
		
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("诉讼保全担保 -受理申请-出错:" + e.getMessage());
		}
		
		
	//	setJsonString(sb.toString());
		return SUCCESS;
	}
public List<SlActualToCharge> savejsoncharge(
		String slActualToChargeJsonData,
		SgLawsuitguaranteeProject slSmallloanProject, Short flag) {
	List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
	if (null != slActualToChargeJsonData
			&& !"".equals(slActualToChargeJsonData)) {

		String[] shareequityArr = slActualToChargeJsonData.split("@");

		for (int i = 0; i < shareequityArr.length; i++) {
			String str = shareequityArr[i];
			String[] strArr = str.split(",");
			String typestr = "";
			if (strArr.length == 7) {
				typestr = strArr[1];
			} else {
				typestr = strArr[0];
			}
			String typeId = "";
			String typename = "";
			if (typestr.endsWith("\"") == true) {
				typename = typestr.substring(typestr.indexOf(":") + 2,
						typestr.length() - 1);
			} else {
				typeId = typestr.substring(typestr.indexOf(":") + 1,
						typestr.length());
			}
			SlPlansToCharge slPlansToCharge = new SlPlansToCharge();

			if (!typename.equals("")) {

				List<SlPlansToCharge> list = slPlansToChargeService
						.getAll();
				int k = 0;
				for (SlPlansToCharge p : list) {
					if (p.getName().equals(typename)
							&& p.getBusinessType().equals("BeNotFinancing")) {
						k++;
					}
				}

				if (k == 0) {
					slPlansToCharge.setName(typename);
					slPlansToCharge.setIsType(1);
					slPlansToCharge.setIsValid(0);
					slPlansToCharge.setBusinessType("BeNotFinancing");
					slPlansToChargeService.save(slPlansToCharge);
					if (strArr.length == 9) {
						str = "{";
						for (int j = 0; j <= strArr.length - 2; j++) {
							if (j != 1) {
								str = strArr[j] + ",";
							}
						}
						str = str + strArr[strArr.length - 1];

					} else {
						str = "{";
						for (int j = 1; j <= strArr.length - 2; j++) {
							str = str + strArr[j] + ",";
						}
						str = str + strArr[strArr.length - 1];
					}
				}
			} else {
				long typeid = Long.parseLong(typeId);
				slPlansToCharge = slPlansToChargeService.get(typeid);

			}

			JSONParser parser = new JSONParser(new StringReader(str));

			try {

				SlActualToCharge slActualToCharge = (SlActualToCharge) JSONMapper
						.toJava(parser.nextValue(), SlActualToCharge.class);

				slActualToCharge.setProjectId(slSmallloanProject
						.getProjectId());
				slActualToCharge.setProjectName(slSmallloanProject
						.getProjectName());
				slActualToCharge.setProjectNumber(slSmallloanProject
						.getProjectNumber());

				slActualToCharge.setPlanChargeId(slPlansToCharge
						.getPlansTochargeId());
				if (null == slActualToCharge.getActualChargeId()) {

					slActualToCharge.setAfterMoney(new BigDecimal(0));
					slActualToCharge.setFlatMoney(new BigDecimal(0));
					if (slActualToCharge.getIncomeMoney().equals(
							new BigDecimal(0.00))) {
						slActualToCharge.setNotMoney(slActualToCharge
								.getPayMoney());
					} else {
						slActualToCharge.setNotMoney(slActualToCharge
								.getIncomeMoney());
					}
					slActualToCharge.setBusinessType("BeNotFinancing");
					slActualToCharge.setIsCheck(flag);
					slActualToCharges.add(slActualToCharge);
				} else {

					SlActualToCharge slActualToCharge1 = slActualToChargeService
							.get(slActualToCharge.getActualChargeId());
					if (slActualToCharge1.getAfterMoney().compareTo(
							new BigDecimal(0)) == 0) {
						BeanUtil.copyNotNullProperties(slActualToCharge1,
								slActualToCharge);
						if (slActualToCharge1.getIncomeMoney().equals(
								new BigDecimal(0.00))) {
							slActualToCharge1.setNotMoney(slActualToCharge
									.getPayMoney());
						} else {
							slActualToCharge1.setNotMoney(slActualToCharge
									.getIncomeMoney());
						}
						slActualToCharge1.setPlanChargeId(slPlansToCharge
								.getPlansTochargeId());
						slActualToCharge1.setBusinessType("BeNotFinancing");
						slActualToCharge1.setIsCheck(flag);
						slActualToCharges.add(slActualToCharge1);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

	return slActualToCharges;
}
/**
 * 在节点保存意见与说明 公共方法
 * 
 * @return
 * @author shendx
 */
public String saveComments() {

	String taskId = this.getRequest().getParameter("task_id");
	String comments = this.getRequest().getParameter("comments");
	if (null != taskId && !"".equals(taskId) && null != comments
			&& !"".equals(comments.trim())) {
		this.creditProjectService.saveComments(taskId, comments);
	}
	return SUCCESS;
}
public String intentcheck() {

	String slActualToChargeJsonData = this.getRequest().getParameter("slActualToChargeJsonData");
	List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
	slActualToCharges = savejsoncharge(slActualToChargeJsonData,sgLawsuitguaranteeProject, Short.parseShort("0"));
	for(SlActualToCharge a:slActualToCharges){
		this.slActualToChargeService.save(a);
		
	}
	String taskId = this.getRequest().getParameter("task_id");
	String comments = this.getRequest().getParameter("comments");
	if (null != taskId && !"".equals(taskId) && null != comments
			&& !"".equals(comments.trim())) {
		this.creditProjectService.saveComments(taskId, comments);
	}
	return SUCCESS;
}
}
