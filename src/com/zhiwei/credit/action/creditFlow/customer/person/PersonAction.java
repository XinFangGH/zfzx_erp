package com.zhiwei.credit.action.creditFlow.customer.person;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.GroupUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonCar;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonHouse;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonRelation;
import com.zhiwei.credit.model.creditFlow.customer.person.VPersonDic;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.CsPersonCarService;
import com.zhiwei.credit.service.creditFlow.customer.person.CsPersonHouseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import org.apache.commons.lang.StringUtils;

public class PersonAction extends BaseAction{
	@Resource
	private AppUserService appUserService;
	@Resource
	private PersonService personService;
	@Resource
	private FileFormService fileFormService;
	@Resource
	private SlPersonMainService slPersonMainService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private CsPersonCarService csPersonCarService;
	@Resource
	private CsPersonHouseService csPersonHouseService;
	@Resource
	private AreaDicService areaDicService;
	
	@Resource
	private  EnterpriseService enterpriseService;
	
	private String businessType;
	private Boolean isAll;
	private String name;
	private String job;
	private String sex;
	private String cellphone;
	private String cardtype;
	private String cardnumber;
	private String sexvalue="";
	private String customerLevel;
	private Person person;
	private String id;
	private VPersonDic vPersonDic;
	private String listId;
	private String blackReason;
	private String query;
	

	private PersonRelation personRelation;
	
	@SuppressWarnings("all")
	public void getListBypersonId(){
		try {
			String personId=this.getRequest().getParameter("personId");
//			String relationPersonType=this.getRequest().getParameter("relationPersonType");
			String flag=this.getRequest().getParameter("flag");
			if(null!=personId && !personId.equals("")){
				personService.getListBypersonId(start, limit,Integer.valueOf(personId).intValue(),flag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//添加客户联系人
	public void add(){
		try {
			personService.addRelationPerson(personRelation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//删除客户联系人信息
	public void deletePersonRelationById(){
		String id=this.getRequest().getParameter("id");
		try {
			personService.deletePersonRelationById(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//查看客户联系人信息
	public void seeRelation(){
		String id=this.getRequest().getParameter("id");
		try {
			personService.seePersonRelation(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//更新客户联系人信息
	public String updateRelation(){
		try {
			personService.updateRelationPerson(personRelation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void queryList() {
		try {
			Object ids=this.getRequest().getSession().getAttribute("userIds");
			Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
			String companyId= map.get("companyId");
			String userIds= map.get("userId");
			if(sexvalue == ""){
				Object[] obj = {name,job,sex,cellphone,cardtype,cardnumber};
				String[] str = {"name","job","sex","cellphone","cardtype","cardnumber","orgUserId"};
				personService.ajaxQueryPerson(companyId,start, limit,userIds, "from VPersonDic AS p", str, obj,sort,dir,customerLevel);
			}else{
				Object[] obj = {name,sexvalue,sex,cellphone,cardtype,cardnumber};
				String[] str = {"name","sexvalue","sex","cellphone","cardtype","cardnumber","orgUserId"};
				personService.ajaxQueryPerson(companyId,start, limit,userIds, "from VPersonDic AS p", str, obj,sort,dir,customerLevel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String perQueryList(){
		try{
			Object ids=this.getRequest().getSession().getAttribute("userIds");
			Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
			PageBean<Person> pageBean = new PageBean<Person>(start,limit,getRequest());
			personService.perList(pageBean,map);
			JsonUtil.jsonFromList(pageBean.getResult(),pageBean.getTotalCounts());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	// 添加数据
	public void addInfo() {
		try {
			Long currentUserId = ContextUtil.getCurrentUserId();
			Long companyId = ContextUtil.getLoginCompanyId();
        	if(null == person.getCompanyId()){
        		 person.setCompanyId(companyId);
        	}
			person.setCreater(ContextUtil.getCurrentUser().getFullname());
			person.setCreaterId(currentUserId);
			if("".equals(person.getBelongedId())||person.getBelongedId() == null) {
				person.setBelongedId(currentUserId.toString());
			}
			person.setCreatedate(new Date());
			personService.addPerson(person);
			String personPhotoId = super.getRequest().getParameter("personPhotoId");
			if(personPhotoId != ""&&!personPhotoId.equals("")){
				fileFormService.updateFile("cs_person_tx."+person.getId(), Integer.parseInt(personPhotoId));
			}
			String personSFZZId = super.getRequest().getParameter("personSFZZId");
			if(personSFZZId != ""&&!personSFZZId.equals("")){
				fileFormService.updateFile("cs_person_sfzz."+person.getId(), Integer.parseInt(personSFZZId));
			}
			String personSFZFId = super.getRequest().getParameter("personSFZFId");
			if(personSFZFId != ""&&!personSFZFId.equals("")){
				fileFormService.updateFile("cs_person_sfzf."+person.getId(), Integer.parseInt(personSFZFId));
			}
		} catch (Exception e) {
			e.printStackTrace();
			//return "error";
		}
		//return "insert";
	}
	// 查看选中的数据
	public String seeInfo() {
		try {
			if("Financing".equals(businessType)){
				SlPersonMain perMain=slPersonMainService.get(new Long(id));
				if(perMain!=null){
					JsonUtil.jsonFromObject(perMain, true);
				}
			}else{
				Person p = personService.getById(Integer.valueOf(id));
				if(p.getMateid() != null){
					Person per = personService.getById(p.getMateid());
					p.setMateValue(per.getName());
				}
				if(p.getBelongedId()!= null && !"".equals(p.getBelongedId())){
					String belongedName = "";
					String []str = p.getBelongedId().split(",");
					Set<AppUser> userSet = appUserService.getAppUserByStr(str);
					int i = 0;
					for(AppUser s:userSet){
						belongedName += s.getFamilyName();
						i++;
						if(i != userSet.size()){
							belongedName= belongedName+",";
						}
					}
					p.setBelongedName(belongedName);
				}
				if(null!=p.getHomeland()){
					AreaDic areaDic=areaDicService.getById(p.getHomeland());
					p.setParentHomeland(areaDic.getParentId());
				}
				if(null!=p.getLiveCity()){
					AreaDic areaDic=areaDicService.getById(p.getLiveCity());
					p.setParentLiveCity(areaDic.getParentId());
				}
				if(null!=p.getHireCity()){
					AreaDic areaDic=areaDicService.getById(p.getHireCity());
					p.setParentHireCity(areaDic.getParentId());
				}
				FileForm fileForm = fileFormService.getFileByMark("cs_person_tx."+id);
				if(fileForm != null){
					p.setPersonPhotoId(fileForm.getFileid());
					p.setPersonPhotoUrl(fileForm.getWebPath());
					p.setPersonPhotoExtendName(fileForm.getExtendname());
				}
				FileForm fileForm2 = fileFormService.getFileByMark("cs_person_sfzz."+id);
				if(fileForm2 != null){
					p.setPersonSFZZId(fileForm2.getFileid());
					p.setPersonSFZZUrl(fileForm2.getWebPath());
					p.setPersonSFZZExtendName(fileForm2.getExtendname());
				}
				FileForm fileForm3 = fileFormService.getFileByMark("cs_person_sfzf."+id);
				if(fileForm3 != null){
					p.setPersonSFZFId(fileForm3.getFileid());
					p.setPersonSFZFUrl(fileForm3.getWebPath());
					p.setPersonSFZFExtendName(fileForm3.getExtendname());
				}
				if (StringUtils.isNotEmpty(p.getName())){
				BigDecimal availableMoney = personService.getAvailableMoney(p.getName());
				p.setAvailableMoney(availableMoney);
				}
				//将数据转成JSON格式
				StringBuffer buff = new StringBuffer("{success:true,data:");
				JSONSerializer serializer=new JSONSerializer();
				buff.append(serializer.transform(new DateTransformer("yyyy-MM-dd"), "birthday","validity","jobstarttime","residenceDate").exclude(new String[]{"class"}).serialize(p));
				buff.append("}");
				jsonString=buff.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 *查询当前法人名下的企业剩余额度信息
	 *
	 * @auther: XinFang
	 * @date: 2018/6/21 10:09
	 */
	public String queryEnterpriseMoney(){
        String name = this.getRequest().getParameter("name");
         List<Enterprise> enterprises =  personService.queryEnterpriseMoneyByName(name);
         if (enterprises != null && enterprises.size()>0){
            JsonUtil.jsonFromList(enterprises);
         }

        return SUCCESS;
    }


	// 更新选中的数据
	public void updateInfo() {
		try {
			Organization organization=null;
        	if(null!=this.getSession().getAttribute("company")){
        		String companySession=(String)this.getSession().getAttribute("company");
        		organization=organizationService.getBranchCompanyByKey(companySession);
        	}else{
        		 organization=organizationService.getGroupCompany();
        	}
        	if(null!=organization){
        		 person.setCompanyId(organization.getOrgId());
        	}
        	if(null!=person.getId()){
        		Person p = personService.getById(person.getId());
            	if(null!=p){
            		BeanUtil.copyNotNullProperties(p, person);
            		personService.updatePerson(p);
            	}
        	}else{
        		personService.updatePerson(person);
        	}
			
			String personPhotoId = super.getRequest().getParameter("personPhotoId");
			if(personPhotoId != ""&&!personPhotoId.equals("")){
				fileFormService.updateFile("cs_person_tx."+person.getId(), Integer.parseInt(personPhotoId));
			}
			String personSFZZId = super.getRequest().getParameter("personSFZZId");
			if(personSFZZId != ""&&!personSFZZId.equals("")){
				fileFormService.updateFile("cs_person_sfzz."+person.getId(), Integer.parseInt(personSFZZId));
			}
			String personSFZFId = super.getRequest().getParameter("personSFZFId");
			if(personSFZFId != ""&&!personSFZFId.equals("")){
				fileFormService.updateFile("cs_person_sfzf."+person.getId(), Integer.parseInt(personSFZFId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String deleteRelationById(){
		String id = getRequest().getParameter("id");
		if(id!=null&&!"".equals(id)){
			personService.deletePersonRelationById(Integer.parseInt(id));
		}
		return SUCCESS;
	}
	// 删除人员表的数据
	public String delete() {
		String[] strTable = {"cs_person-id"/*,"cs_person_attachfile-personid"*/,"cs_person_relation-personId","cs_person_thereunder-personid","cs_person_relation-personId"};
		String [] listArrayId =listId.split(",");
		String [] newListArrayId = null ;//重新定义数组代表  不是企业法人的集合(可以被删除)
		List<Person> listPerson = new ArrayList<Person>();
		List<String> list = new ArrayList<String>();
		try {
			boolean flag=true;
			for(int i=0;i<listArrayId.length;i++){
				if(null!=listArrayId[i]){
					List<SlSmallloanProject> slist=slSmallloanProjectService.getListOfCustomer("person_customer", Long.valueOf(listArrayId[i]));
					if(null!=slist && slist.size()>0){
						flag=false;
						Person p=personService.getById(Integer.valueOf(listArrayId[i]));
						JsonUtil.responseJsonString("{success:true,flag:'false',msg:'"+p.getName()+"在项目中已被使用，不能删除'}");
					}
				}
			}
			//判断是否满足第一层条件
			//进行判断是否是企业的法人  如果不是重新加入list
			if(listArrayId.length>0 && flag==true){
				for(String str : listArrayId){
					List<Enterprise> elist = enterpriseService.getListByLegalPersonId(Integer.valueOf(str));
					if(elist.size()>0){
						listPerson.add(personService.getById(Integer.valueOf(str)));
					}else{
						list.add(str);
					}
				}
				//如果list为空的话 返回false不进行删除
				if(list.size()==0){
					flag=false;
					JsonUtil.responseJsonString("{success:true,flag:'false',msg:'该客户为企业法人，不能删除'}");
				}else{
					//list转数组
					Object[] array=list.toArray();
					newListArrayId=new String[list.size()];
					for(int i=0;i<array.length;i++){
					  newListArrayId[i]=(String)array[i];
					  System.out.println(newListArrayId[i]);
					}
				}
			}
			if(flag==true){
				String  hurl = super.getRequest().getRealPath("/");
				personService.deletePerson(strTable,(newListArrayId!=null&&newListArrayId.length>0)?newListArrayId:listArrayId,hurl);
				if(newListArrayId!=null&&newListArrayId.length>0){
					for(int i= 0;i<newListArrayId.length;i++){
						String mark = "cs_person_tx."+newListArrayId[i];
						FileForm file = fileFormService.getFileByMark(mark);
						if(file != null){
							fileFormService.remove(file);
						}
						String mark2 = "cs_person_sfzz."+newListArrayId[i];
						FileForm file2 = fileFormService.getFileByMark(mark2);
						if(file2!= null){
							fileFormService.remove(file2);
						}
						String mark3 = "cs_person_sfzf."+newListArrayId[i];
						FileForm file3 = fileFormService.getFileByMark(mark3);
						if(file3 != null){
							fileFormService.remove(file3);
						}
					}
				}else{
					for(int i= 0;i<listArrayId.length;i++){
						String mark = "cs_person_tx."+listArrayId[i];
						FileForm file = fileFormService.getFileByMark(mark);
						if(file != null){
							fileFormService.remove(file);
						}
						String mark2 = "cs_person_sfzz."+listArrayId[i];
						FileForm file2 = fileFormService.getFileByMark(mark2);
						if(file2!= null){
							fileFormService.remove(file2);
						}
						String mark3 = "cs_person_sfzf."+listArrayId[i];
						FileForm file3 = fileFormService.getFileByMark(mark3);
						if(file3 != null){
							fileFormService.remove(file3);
						}
					}
				}
				
				if(listPerson.size()>0 && newListArrayId.length>0){
					JsonUtil.responseJsonString("{success:true,flag:'true',msg:'成功删除'"+newListArrayId.length+"人,失败"+listPerson.size()+"人,原因:删除个人客户为企业法人}");
				}else{
					JsonUtil.responseJsonString("{success:true,flag:'true',msg:'成功删除'}");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void outputExcel(){
		//String [] tableHeader = {"序号","姓名","性别","年龄","职务","婚姻状况","证件类型/号码","手机号码","家庭电话","出生日期"};
		String [] tableHeader = {"序号","所属门店","姓名","性别","证件类型/号码","手机号码","家庭电话"};
		try {
			Object ids=this.getRequest().getSession().getAttribute("userIds");
			Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
			PageBean<Person> pageBean = new PageBean<Person>(null,null,getRequest());
			personService.perList(pageBean,map);
			for(Person p:pageBean.getResult()){
				System.out.println(p.getShopName());
			}
			ExcelHelper.export(pageBean.getResult(),tableHeader,"个人客户列表");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void addBlack(){
		String msg="";
		try {
			Person p=personService.getById(Integer.valueOf(id));
			p.setIsBlack(true);
			p.setBlackReason(blackReason);
			personService.save(p);
			msg="{success:true}";
		} catch (Exception e) {
			msg="{success:false}";
			e.printStackTrace();
		}
	}
	public void getById(){
		String personId=this.getRequest().getParameter("personId");
		if(null!=personId || !"".equals(personId) || !"0".equals(personId)){
			Person person =personService.getById(Integer.parseInt(personId));
//			person.setActivityInfos(null);
//			person.setNegativeInfos(null);
//			person.setEducationInfos(null);
//			person.setWorkExperienceInfos(null);
//			person.setPersonCars(null);
//			person.setPersonHouses(null);
			if(null!=person.getHireCity()){
				AreaDic areaDic=areaDicService.getById(person.getHireCity());
				person.setParentHireCity(areaDic.getParentId());
				person.setHireCityName(areaDic.getText());
			}
			JSONSerializer json = com.zhiwei.core.util.JsonUtil.getJSONSerializer();
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"jobstarttime"});
//			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			//将数据转成JSON格式
			StringBuffer sb = new StringBuffer("{success:true,data:");
			sb.append(json.serialize(person));
//			sb.append(gson.toJson(person));
			sb.append("}");
			JsonUtil.responseJsonString(sb.toString());
		}
	}
	/**
	 * 保存家庭经济情况信息，包括车辆，房产，资产信息
	 * updated by luowenyan
	 */
	public void saveFinance(){
		try{
			String personId =this.getRequest().getParameter("personId");
			String personCarInfo =this.getRequest().getParameter("personCarInfo");
			String personHouseInfo =this.getRequest().getParameter("personHouseInfo");
			if(null!=personId || !"".equals(personId) || !"0".equals(personId)){
				//保存客户信息
				Person persons =personService.getById(Integer.parseInt(personId));
				//设置资产信息
				persons.setGrossasset(person.getGrossasset());
				persons.setHomeasset(person.getHomeasset());
				persons.setGrossdebt(person.getGrossdebt());
				persons.setYeargrossexpend(person.getYeargrossexpend());
				//设置工资卡信息
				persons.setWagebank(person.getWagebank());
				persons.setWageperson(person.getWageperson());
				persons.setWageaccount(person.getWageaccount());
				persons.setMatebank(person.getMatebank());
				persons.setMateperson(person.getMateperson());
				persons.setMateaccount(person.getMateaccount());
				personService.merge(persons);
				//保存客户车辆信息
				if(personCarInfo!=null&&!"".equals(personCarInfo)){
					String[] personCarInfoArr = personCarInfo.split("@");
					for (int i = 0; i < personCarInfoArr.length; i++) {
						String str = personCarInfoArr[i];
//						String str1="{\"negativeId\":null,\"personId\":\"33\"}";
//						String str2 = "{\"id\":null,\"personId\":\"33\",\"propertyOwner\":\"1\",\"carSystemType\":\"1\",\"carType\":\"1\",\"carLicenseNumber\":\"1\",\"isMortgage\":0,\"newCarValue\":1,\"loanMoney\":1,\"carFactValue\":1,\"yearOfCarUse\":\"1\",\"finalCertificationPrice\":1}";
						JSONParser parser = new JSONParser(new StringReader(str));
//						BpCustPersonNegativeSurvey negativeInfo = (BpCustPersonNegativeSurvey) JSONMapper.toJava(parser.nextValue(), BpCustPersonNegativeSurvey.class);
						CsPersonCar csPersonCar = (CsPersonCar) JSONMapper.toJava(parser.nextValue(), CsPersonCar.class);
						if(csPersonCar.getId()==null||"".equals(csPersonCar.getId())){
							csPersonCarService.save(csPersonCar);
						}else{
							CsPersonCar temp=csPersonCarService.get(csPersonCar.getId());
							BeanUtil.copyNotNullProperties(temp, csPersonCar);
							csPersonCarService.save(temp);
						}
					}
					
				}
				//保存客户房产信息
				if(personHouseInfo!=null&&!"".equals(personHouseInfo)){
					String[] personHouseInfoArr = personHouseInfo.split("@");
					for (int i = 0; i < personHouseInfoArr.length; i++) {
						String str = personHouseInfoArr[i];
						//反序列化
						JSONParser parser = new JSONParser(new StringReader(str));
						CsPersonHouse csPersonHouse = (CsPersonHouse) JSONMapper.toJava(parser.nextValue(), CsPersonHouse.class);
						if(null==csPersonHouse.getId()||"".equals(csPersonHouse.getId())){
							csPersonHouseService.save(csPersonHouse);
						}else{
							CsPersonHouse tempHouse=csPersonHouseService.get(csPersonHouse.getId());
							BeanUtil.copyNotNullProperties(tempHouse, csPersonHouse);
							csPersonHouseService.save(tempHouse);
						}
					}
					
				}
				StringBuffer sb = new StringBuffer("{success:true");
				sb.append("}");
				JsonUtil.responseJsonString(sb.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void findAddress(){
		try{
			this.areaDicService.selectPersonAddress(
					Integer.parseInt(getRequest().
							getParameter("parentId"))) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	public void ajaxQueryForCombo(){
		Object ids=this.getRequest().getSession().getAttribute("userIds");
		Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
		String userIds= map.get("userId");
		personService.ajaxQueryPersonForCombo(query,start,limit,userIds) ;
	}
	public void addFamily(){
		
		try {
			this.personService.addPersonFamily(person);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	public void verification(){
		try{
		String cardNum=this.getRequest().getParameter("cardNum");
		String personId=this.getRequest().getParameter("personId");
		if(null==personId || "".equals(personId) || "0".equals(personId)){
			Person p=personService.queryPersonCardnumber(cardNum);
			if(null!=p){
				JsonUtil.responseJsonString("{success:true,msg:false}");
			}else{
				JsonUtil.responseJsonString("{success:true,msg:true}");
			}
		}else{
			Person person=personService.getById(Integer.parseInt(personId));
			if(!person.getCardnumber().equals(cardNum)){
				Person p=personService.queryPersonCardnumber(cardNum);
				if(null!=p){
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
	/**个人客户
	 * 2014-08-01
	 * @return
	 */
	public String personList(){
		String userIdsStr = "";
		String roleTypeStr = ContextUtil.getRoleTypeSession();
		if (!isAll && !roleTypeStr.equals("control")) {// 如果用户不拥有查看所有项目信息的权限
			userIdsStr = appUserService.getUsersStr();// 当前登录用户以及其所有下属用户
		}
		List<Person> listPerson=personService.personList(start,limit,this.getRequest(),userIdsStr);
		List<Person> listPersonCount=personService.personList(null,null,this.getRequest(),userIdsStr);
		StringBuffer buff = new StringBuffer("{success:true,'totalProperty':")
		.append(listPersonCount!=null?listPersonCount.size():0).append(",topics:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
		/* .excludeFieldsWithoutExposeAnnotation() */.create();
		buff.append(gson.toJson(listPerson));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	//查询个人客户资金账户表
	public String fianceAccountList(){
		String userIdsStr = "";
		String roleTypeStr = ContextUtil.getRoleTypeSession();
		if (!isAll && !roleTypeStr.equals("control")) {// 如果用户不拥有查看所有项目信息的权限
			userIdsStr = appUserService.getUsersStr();// 当前登录用户以及其所有下属用户
		}
		PagingBean pb=new PagingBean(start,limit);
		Map map=ContextUtil.createResponseMap(this.getRequest());
		map.put("userIdsStr", userIdsStr);
		List<Person> list= personService.getAllAccountList(map,pb);
		Type type=new TypeToken<List<Person>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
		/* .excludeFieldsWithoutExposeAnnotation() */.create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	//保存个人客户的工作信息和家庭信息
	public String savePartInfo(){
		String msg="";
		try {
			Person p=personService.getById(Integer.valueOf(id));
			BeanUtil.copyNotNullProperties(p, person);
			personService.merge(p);
			msg="{success:true}";
		} catch (Exception e) {
			msg="{success:false}";
			e.printStackTrace();
		}
		jsonString = msg.toString();
		return SUCCESS;
	}
	public String getBlackReason() {
		return blackReason;
	}


	public String getQuery() {
		return query;
	}


	public void setQuery(String query) {
		this.query = query;
	}


	public void setBlackReason(String blackReason) {
		this.blackReason = blackReason;
	}


	public String getListId() {
		return listId;
	}


	public void setListId(String listId) {
		this.listId = listId;
	}


	public VPersonDic getvPersonDic() {
		return vPersonDic;
	}


	public void setvPersonDic(VPersonDic vPersonDic) {
		this.vPersonDic = vPersonDic;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getBusinessType() {
		return businessType;
	}


	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	public Boolean getIsAll() {
		return isAll;
	}


	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getJob() {
		return job;
	}


	public void setJob(String job) {
		this.job = job;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getCellphone() {
		return cellphone;
	}


	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}


	public String getCardtype() {
		return cardtype;
	}


	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}


	public String getCardnumber() {
		return cardnumber;
	}


	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}


	public String getSexvalue() {
		return sexvalue;
	}


	public void setSexvalue(String sexvalue) {
		this.sexvalue = sexvalue;
	}


	public String getCustomerLevel() {
		return customerLevel;
	}


	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public PersonRelation getPersonRelation() {
		return personRelation;
	}

	public void setPersonRelation(PersonRelation personRelation) {
		this.personRelation = personRelation;
	}
}