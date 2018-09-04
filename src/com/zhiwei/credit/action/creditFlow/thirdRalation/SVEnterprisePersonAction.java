package com.zhiwei.credit.action.creditFlow.thirdRalation;

/*
 北京互融时代软件有限公司————协同办公管理系统   
 */
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.thirdRalation.SVEnterprisePerson;
import com.zhiwei.credit.service.creditFlow.common.EnterpriseShareequityService;
import com.zhiwei.credit.service.creditFlow.contract.VProcreditContractService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.creditFlow.thirdRalation.SVEnterprisePersonService;

import flexjson.JSONSerializer;

/**
 * 
 * @author
 * 
 */
public class SVEnterprisePersonAction extends BaseAction {
	@Resource
	private SVEnterprisePersonService sVEnterprisePersonService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;

	@Resource
	private EnterpriseShareequityService enterpriseShareequityService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private VProcreditContractService vProcreditContractService;
	@Resource
	private GLGuaranteeloanProjectService glGuaranteeloanProjectService;
	@Resource
	private FlFinancingProjectService flFinancingProjectService;
	private SVEnterprisePerson sVEnterprisePerson;

	private Long id;

	private String type;
	private Long projectId;
	private Enterprise enterprise;
	private Person person;
	private String gudong;
	private String string;
	private String businessType;

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getGudong() {
		return gudong;
	}

	public void setGudong(String gudong) {
		this.gudong = gudong;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SVEnterprisePerson getSVEnterprisePerson() {
		return sVEnterprisePerson;
	}

	public void setSVEnterprisePerson(SVEnterprisePerson sVEnterprisePerson) {
		this.sVEnterprisePerson = sVEnterprisePerson;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		try {
			List<SVEnterprisePerson> list = new ArrayList<SVEnterprisePerson>();
			String projectId = this.getRequest().getParameter("projectId");
			Type type = new TypeToken<List<SVEnterprisePerson>>() {}.getType();
			StringBuffer buff = new StringBuffer("{success:true,result:");
			Gson gson = new Gson();
			if (null != list && list.size() > 0) {
				String json = gson.toJson(list, type);
				buff.append(json);
			} else {
				buff.append("[]");
			}
			buff.append("}");
			jsonString = buff.toString();
		} catch (Exception e) {
			logger.error("SVEnterprisePersonAction:" + e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {
		try {
			String arr[] = string.split("@");
			for (int i = 0; i < arr.length; i++) {
				String str = arr[i];
				String id = str.substring(1, str.lastIndexOf(","));
				String type = str.substring(str.lastIndexOf(",") + 1, str
						.length() - 1);
			}
			jsonString = "{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SVEnterprisePersonAction:" + e.getMessage());
			jsonString = "{success:false}";
		}
		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {

		Map<String, Object> map = new HashMap<String, Object>();
		if ("company_third".equals(type)) {

			try {
				Enterprise enterprise = enterpriseService
						.getById(id.intValue());
				Person p = this.personService.getById(enterprise
						.getLegalpersonid());
				map.put("enterprise", enterprise);
				map.put("person", p);
			} catch (Exception e) {
				logger.error("SVEnterprisePersonAction:" + e.getMessage());
				e.printStackTrace();
			}

		} else {

			Person person = personService.getById(id.intValue());
			map.put("person", person);
		}
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil
				.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		if (sVEnterprisePerson.getId() == null) {
			sVEnterprisePersonService.save(sVEnterprisePerson);
		} else {
			SVEnterprisePerson orgSVEnterprisePerson1 = sVEnterprisePersonService
					.get(sVEnterprisePerson.getId());
			try {
				BeanUtil.copyNotNullProperties(orgSVEnterprisePerson1,
						sVEnterprisePerson);
				sVEnterprisePersonService.save(orgSVEnterprisePerson1);
			} catch (Exception ex) {
				logger.error("SVEnterprisePersonAction:" + ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;

	}

	/*
	 * 更新企业第三方保证
	 */

	public String updateComapnyInfo() {

		try {
			int enterpriseId = 0;
			String oppositeType = "";
			if ("Guarantee".equals(businessType)) {
				GLGuaranteeloanProject project = this.glGuaranteeloanProjectService
						.get(projectId);
				enterpriseId = project.getOppositeID().intValue();
				oppositeType = project.getOppositeType();
			} else if ("SmallLoan".equals(businessType)) {
				SlSmallloanProject project = this.slSmallloanProjectService
						.get(projectId);
				enterpriseId = project.getOppositeID().intValue();
				oppositeType = project.getOppositeType();
			} else if ("Financing".equals(businessType)) {
				FlFinancingProject project = this.flFinancingProjectService
						.get(projectId);
				enterpriseId = project.getOppositeID().intValue();
				oppositeType = project.getOppositeType();
			}

			if (enterpriseId == (enterprise.getId())
					&& (oppositeType.equals("company_customer"))) {
				setJsonString("{success:true,msg:null}");
			} else {
				if ((id.intValue() == enterprise.getId())) {
					// 更新企业信息
					Enterprise orgenterprise = enterpriseService
							.getById(enterprise.getId());
					BeanUtil.copyNotNullProperties(orgenterprise, enterprise);
					enterpriseService.save(orgenterprise);
					// 更新法人信息
					Person p = this.personService.getById(orgenterprise
							.getLegalpersonid());
					BeanUtil.copyNotNullProperties(p, person);
					personService.save(p);
					// 更新股东信息
					if (null != gudong && !"".equals(gudong)) {

						String[] shareequityArr = gudong.split("@");

						for (int i = 0; i < shareequityArr.length; i++) {
							String str = shareequityArr[i];
							JSONParser parser = new JSONParser(
									new StringReader(str));

							EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper
									.toJava(parser.nextValue(),
											EnterpriseShareequity.class);

							enterpriseShareequity.setEnterpriseid(enterprise
									.getId());

							if (null == enterpriseShareequity.getId()
									|| "".equals(enterpriseShareequity.getId())) {
								enterpriseShareequityService
										.save(enterpriseShareequity);
							} else {
								EnterpriseShareequity orgEnterpriseShareequity = enterpriseShareequityService
										.load(enterpriseShareequity.getId());
								BeanUtil.copyNotNullProperties(
										orgEnterpriseShareequity,
										enterpriseShareequity);
								enterpriseShareequityService
										.save(orgEnterpriseShareequity);
							}
						}
					}
					setJsonString("{success:true}");
				} else {
					setJsonString("{success:true,msg:false}");
				}
			}
		} catch (Exception e1) {
			setJsonString("{success:false}");
			logger.error("SVEnterprisePersonAction:" + e1.getMessage());
			e1.printStackTrace();
		}

		return SUCCESS;
	}

	/*
	 * 更新个人第三方保证
	 */
	public String updatePersonInfo() {

		try {
			int personId = 0;
			String oppositeType = "";
			if ("Guarantee".equals(businessType)) {
				GLGuaranteeloanProject project = this.glGuaranteeloanProjectService
						.get(projectId);
				personId = project.getOppositeID().intValue();
				oppositeType = project.getOppositeType();
			} else if ("SmallLoan".equals(businessType)) {
				SlSmallloanProject project = this.slSmallloanProjectService
						.get(projectId);
				personId = project.getOppositeID().intValue();
				oppositeType = project.getOppositeType();
			} else if ("Financing".equals(businessType)) {
				FlFinancingProject project = this.flFinancingProjectService
						.get(projectId);
				personId = project.getOppositeID().intValue();
				oppositeType = project.getOppositeType();
			}

			if (personId == (person.getId())
					&& (oppositeType.equals("person_customer"))) {
				setJsonString("{success:true,msg:null}");
			} else {
				if ((id.intValue() == person.getId())) {
					Person p = personService.getById(person.getId());
					BeanUtil.copyNotNullProperties(p, person);
					personService.save(p);
					setJsonString("{success:true}");
				} else {
					setJsonString("{success:true,msg:false}");
				}
			}
		} catch (Exception e) {
			setJsonString("{success:false}");
			logger.error("SVEnterprisePersonAction:" + e.getMessage());
			e.printStackTrace();
		}

		return SUCCESS;
	}
}
