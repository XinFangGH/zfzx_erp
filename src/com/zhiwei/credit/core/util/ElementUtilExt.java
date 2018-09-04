package com.zhiwei.credit.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.credit.proj.entity.BaseVProcreditDictionaryExceptFinancing;
import com.credit.proj.entity.ProcreditMortgageBusiness;
import com.credit.proj.entity.ProcreditMortgageBusinessandlive;
import com.credit.proj.entity.ProcreditMortgageEducation;
import com.credit.proj.entity.ProcreditMortgageHouse;
import com.credit.proj.entity.ProcreditMortgageHouseground;
import com.credit.proj.entity.ProcreditMortgageIndustry;
import com.credit.proj.entity.ProcreditMortgageOfficebuilding;
import com.credit.proj.entity.VProcreditMortgageGlobal;
import com.credit.proj.entity.VProcreditMortgageLeaseFinance;
import com.credit.proj.entity.VProjMortBusAndLive;
import com.credit.proj.entity.VProjMortBusiness;
import com.credit.proj.entity.VProjMortHouse;
import com.credit.proj.entity.VProjMortHouseGround;
import com.credit.proj.entity.VProjMortIndustry;
import com.credit.proj.entity.VProjMortOfficeBuilding;
import com.credit.proj.entity.VProjMortProduct;
import com.credit.proj.mortgage.business.service.BusinessServMort;
import com.credit.proj.mortgage.businessandlive.service.BusinessandliveService;
import com.credit.proj.mortgage.house.service.HouseService;
import com.credit.proj.mortgage.houseground.service.HousegroundService;
import com.credit.proj.mortgage.industry.service.IndustryService;
import com.credit.proj.mortgage.officebuilding.service.OfficebuildingService;
import com.zhiwei.core.model.BaseProject;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.contract.impl.VProcreditContractDaoImpl;
import com.zhiwei.credit.model.creditFlow.contract.BaseElementCode;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseView;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.model.creditFlow.customer.person.VPersonDic;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.VLeaseFinanceObjectInfo;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.ourmain.SlCompanyMain;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.customer.person.SpouseService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.VLeaseFinanceObjectInfoService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.supplior.FlObjectSuppliorService;
import com.zhiwei.credit.service.customer.VCustomerService;
import com.zhiwei.credit.service.system.OrganizationService;

/*
 * @author gaoqingrui
 * 除担保，抵质押物材料沿用过去的赋值方式以外
 * 其他信息采用反射机制赋值
 * 使用方法
 * new ElementUtilExt(FlLeaseFinanceProject project,Long leaseObjectInfoId,String rnum,VProcreditMortgageLeaseFinance mortgage).update(Object obj)obj为LeaseFinanceElementCode对象
 * 通过设置不同的构造函数对ElementUtilExt对象属性赋值
 * 或
 * new ElementUtilExt(PlPawnProject project,String rnum,VProcreditMortgagePawn mortgage).update(Object obj)obj为PawnElementCode对象
 * 
 * 
 * NOTE1:要使用该方法，则项目对象必须要继承自BaseProject,抵质押物视图信息继承自BaseVProcreditDictionaryExceptFinancing
 * NOTE2：该方法不适合Financing的改造因为VProcreditDictionaryFinance的id字段和其他所有的id字段含义不同，所以他并不继承自BaseVProcreditDictionaryExceptFinancing
 * 
 * */
public class ElementUtilExt {
	
	public Object project;
	/*供货方信息*/
	public Object supplior = null;
	/*租赁物信息*/
	public Object vObjectInfo = null;
	/*分公司信息*/
	public Object organization = null;
	/*客户信息*/
	public Object vCustomer = null;
	/*担保，抵质押物信息*/
	public Object mortgage = null;
	/*担保，抵质押物信息*/
	public Object pawnItem = null;
	public String rnum = "0";
	
//融资租赁 项目构造函数
	public ElementUtilExt(FlLeaseFinanceProject project,Long leaseObjectInfoId,String rnum,VProcreditMortgageLeaseFinance mortgage){
		this.project = project ;
		if(null != rnum){
			setRnum(rnum);
		}
		/*用到的service*/
		VLeaseFinanceObjectInfoService vLeaseFinanceObjectInfoService = (VLeaseFinanceObjectInfoService) AppUtil.getBean("vLeaseFinanceObjectInfoService");
		FlObjectSuppliorService flObjectSuppliorService = (FlObjectSuppliorService) AppUtil.getBean("flObjectSuppliorService");
		OrganizationService organizationService = (OrganizationService) AppUtil.getBean("organizationService");
		VCustomerService vCustomerService = (VCustomerService) AppUtil.getBean("vCustomerService");
		
		if(null!=leaseObjectInfoId){
			this.vObjectInfo = vLeaseFinanceObjectInfoService.get(leaseObjectInfoId);
		}
		if(null != this.vObjectInfo){
			this.supplior = flObjectSuppliorService.get(((VLeaseFinanceObjectInfo)(this.vObjectInfo)).getSuppliorId());
		}
		if(null != project.getCompanyId()){
			this.organization = organizationService.get(project.getCompanyId());
		}
		if(null != project.getOppositeID().toString()){
			this.vCustomer = vCustomerService.getByIdAndCustomerType(Integer.parseInt(project.getOppositeID().toString()) ,project.getOppositeType());
		}
		this.mortgage = mortgage;
	}
	
	public ElementUtilExt(PlPawnProject project,String rnum,VProcreditMortgageGlobal mortgage,PawnItemsList pawnItem){
		this.project = project ;
		if(null != rnum){
			setRnum(rnum);
		}
		/*用到的service*/
		OrganizationService organizationService = (OrganizationService) AppUtil.getBean("organizationService");
		VCustomerService vCustomerService = (VCustomerService) AppUtil.getBean("vCustomerService");
		
		if(null != project.getCompanyId()){
			this.organization = organizationService.get(project.getCompanyId());
		}
		if(null != project.getOppositeID().toString()){
			this.vCustomer = vCustomerService.getByIdAndCustomerType(Integer.parseInt(project.getOppositeID().toString()) ,project.getOppositeType());
		}
		this.mortgage = mortgage;
		this.pawnItem = pawnItem;
	}
	
	
	
	public String getPayIntentInfo(){
		String str = "";

		if(((BaseProject)getProject()).getPayaccrualType().equals("monthPay")){
			if(null!=((BaseProject)getProject()).getPayintentPeriod()){
				 str = ((BaseProject)getProject()).getPayintentPeriod()+"个月";
			}
		}else if(((BaseProject)getProject()).getPayaccrualType().equals("seasonPay")){
			if(null!=((BaseProject)getProject()).getPayintentPeriod()){
				str = ((BaseProject)getProject()).getPayintentPeriod()*3+"个月";
			}
		}else if(((BaseProject)getProject()).getPayaccrualType().equals("yearPay")){
			if(null!=((BaseProject)getProject()).getPayintentPeriod()){
				str = ((BaseProject)getProject()).getPayintentPeriod()*12+"个月";
			}
		}else if(((BaseProject)getProject()).getPayaccrualType().equals("dayPay")){
			if(null!=((BaseProject)getProject()).getPayintentPeriod()){
				 str = ((BaseProject)getProject()).getPayintentPeriod()+"天";
			}
		}else if(((BaseProject)getProject()).getPayaccrualType().equals("owerPay")){
			if(null!=((BaseProject)getProject()).getDayOfEveryPeriod() && null!=((BaseProject)getProject()).getPayintentPeriod()){
				str = ((BaseProject)getProject()).getPayintentPeriod()*((BaseProject)getProject()).getDayOfEveryPeriod()+"天";
			}
		}
		return str;
	}
	
	
	@SuppressWarnings("unchecked")
	public  Object updateElement(Object obj){
		Class temp = obj.getClass(); 
//		Field[] fields = temp.getDeclaredFields(); //只获取该类中声明的属性值
		Field[] fields = temp.getFields(); 
		Field[] fields1 = this.getClass().getDeclaredFields(); //获取  当前util内部对象   用以赋值
		List list = new ArrayList();
		String text = "" ;
		int n = 0 ;
		try{
			for(int j = 0 ; j < fields.length ; j ++){
				fields[j].setAccessible(true);
				String eleFieldValue = fields[j].getName().toString().trim() ;
				String[] fieldStrArr = eleFieldValue.split("\\$");
				if(fieldStrArr.length==2){//无参函数，只需知道函数名
					Class[] cParams = {};
					//获取无参构造方法
					Method method = this.getClass().getDeclaredMethod(fieldStrArr[0].toString(),cParams);
					if(null != method){
						method.setAccessible(true);
						Object returnValue = method.invoke(this);
						fields[j].set(obj, returnValue.toString());
					}
				}else if(fieldStrArr.length==3){
					//对象名$属性名$format$_v
					Field field1 = this.getClass().getDeclaredField(fieldStrArr[0]);
					field1.setAccessible(true); 
					Object objectInThis = field1.get(this);
					if(null != objectInThis ){
						Field field2 = objectInThis.getClass().getDeclaredField(fieldStrArr[1]);
						field2.setAccessible(true);
						Object objectInThisField = field2.get(objectInThis);
						if(null!=objectInThisField){
							fields[j].set(obj, objectInThisField.toString());
						}
					}
				}else if(fieldStrArr.length==4){//一个参数的函数，参数为   对象名$属性名的值，
					//对象名$属性名$format方法名$_v  
					
					//获取当前对象的field
					Field field1 = this.getClass().getDeclaredField(fieldStrArr[0]);
					field1.setAccessible(true); 
					Object objectInThis = field1.get(this);
					if(null != objectInThis ){
						Field field2 = objectInThis.getClass().getDeclaredField(fieldStrArr[1]);
						field2.setAccessible(true);
						Object objectInThisField = field2.get(objectInThis);
						Object returnValue = null;
						if(null!=objectInThisField){
							Class[] cParams = new Class[1];
							//获取一个参数为object 的方法
							cParams[0] = Object.class;
							Method method = this.getClass().getDeclaredMethod(fieldStrArr[2].toString(),cParams);
							if(null != method){
								method.setAccessible(true);
								returnValue = method.invoke(this, objectInThisField);
							}
							fields[j].set(obj, returnValue.toString());
						}
					}
				}
			}
			//更新抵质押,担保合同要素
			generaterMortgageElement((BaseElementCode)obj);
			//更新我方主体相关要素
			generaterEntityElement((BaseElementCode)obj);
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj ;
	} 
	
	public String decimalBigFormat(Object obj){
		String str = "";
		if(null==obj){
			return str;
		}
		try{
			BigDecimal bd = (BigDecimal)obj;
			str = bd.doubleValue()==0?"零元整":MoneyFormat.getInstance().hangeToBig(bd)+"元整";
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		return str;
	}
	//承租人法人代表
	public String legalpersonName(Object obj){
		String str="";
		try{
			CreditBaseDao creditBaseDao=(CreditBaseDao) AppUtil.getBean("creditBaseDao");
			Enterprise e=(Enterprise) creditBaseDao.getById(Enterprise.class, (Integer)obj);
			Person person=(Person) creditBaseDao.getById(Person.class, e.getLegalpersonid());
			str=person.getName();
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	//项目合同编号
	public String generateContractNum(){//生成合同编号  默认自动获取 projectNumber,rnum
		//project 最好继承自baseProject,使用公共属性
		String projectNumber = ((BaseProject)getProject()).getProjectNumber();
		int count = 0;
		if(!StringUtil.isNumeric(getRnum())){
			rnum = "0";
		}
		count = Integer.parseInt(rnum);
		if(count <10){
			return projectNumber + "-1" + "-0" + count;
		}else{
			return projectNumber+"-1"+"-"+count;
		}	
	}
	//租赁物合同编号
	public String generateLeaseContractNum(){//生成合同编号  默认自动获取 projectNumber,rnum
		//project 最好继承自baseProject,使用公共属性
		if(getProject() != null && getvObjectInfo() != null){
			String projectNumber = ((BaseProject)getProject()).getProjectNumber();
			String leaseObjectName = ((VLeaseFinanceObjectInfo)getvObjectInfo()).getName();
			projectNumber +="-"+leaseObjectName;
			int count = 0;
			if(!StringUtil.isNumeric(getRnum())){
				rnum = "0";
			}
			count = Integer.parseInt(rnum);
			if(count <10){
				return projectNumber + "-1" + "-0" + count;
			}else{
				return projectNumber+"-1"+"-"+count;
			}	
		}else{
			return "";
		}
	}
	//典当合同编号
	public String getPawnItemContract(){//生成典当合同编号  默认自动获取 projectNumber,rnum
		//project 最好继承自baseProject,使用公共属性
		if(getProject() != null && getPawnItem() != null){
			String projectNumber = ((BaseProject)getProject()).getProjectNumber();
			String pawnItemName= ((PawnItemsList)getPawnItem()).getPawnItemName();
			projectNumber +="-"+pawnItemName;
			int count = 0;
			if(!StringUtil.isNumeric(getRnum())){
				rnum = "0";
			}
			count = Integer.parseInt(rnum);
			if(count <10){
				return projectNumber + "-1" + "-0" + count;
			}else{
				return projectNumber+"-1"+"-"+count;
			}	
		}else{
			return "";
		}
	}
	
	/*仿照过去的反担保合同要素赋值*/
	public void generaterMortgageElement(BaseElementCode elementCode) throws Exception{
			
			CreditBaseDao creditBaseDao = (CreditBaseDao)AppUtil.getBean("creditBaseDao");
			BaseVProcreditDictionaryExceptFinancing mortgage = (BaseVProcreditDictionaryExceptFinancing)getMortgage();
			SpouseService spouseService = (SpouseService)AppUtil.getBean("spouseService");
			HouseService houseService = (HouseService)AppUtil.getBean("houseService");
			OfficebuildingService officebuildingService = (OfficebuildingService)AppUtil.getBean("officebuildingService");
			HousegroundService housegroundService = (HousegroundService)AppUtil.getBean("housegroundService");
			BusinessServMort businessServMort = (BusinessServMort)AppUtil.getBean("businessServMort");
			VProcreditContractDaoImpl vProcreditContractDao = (VProcreditContractDaoImpl)AppUtil.getBean("vProcreditContractDao");
			IndustryService industryService = (IndustryService)AppUtil.getBean("industryService");
			BusinessandliveService businessandliveService = (BusinessandliveService)AppUtil.getBean("businessandliveService");
			
			EnterpriseView e = null ;
			VPersonDic p = null ;
			if(null != mortgage){
				//抵、质押物
				if(mortgage.getAssuretypeid() == 604 || mortgage.getAssuretypeid() == 605) {
					if(mortgage.getMortgagepersontypeforvalue() != null){
						elementCode.setDywmc_v(mortgage.getMortgagepersontypeforvalue());
					}
					if(mortgage.getTypeid()==7 || mortgage.getTypeid()==15 || mortgage.getTypeid()==16 || mortgage.getTypeid()==17){
						List list=houseService.seeHouse(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortHouse house=(VProjMortHouse) list.get(0);
							if(null!=house){
								elementCode.setGyr_v(house.getMutualofperson());
							}
						}
					}
					if(mortgage.getTypeid()==8){
						List list=officebuildingService.seeOfficebuilding(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortOfficeBuilding office=(VProjMortOfficeBuilding) list.get(0);
							if(null!=office){
								elementCode.setSycqr_v(office.getPropertyperson());
								elementCode.setGyr_v(office.getMutualofperson());
							}
						}
					}
					if(mortgage.getTypeid()==9){
						List list=housegroundService.seeHouseground(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortHouseGround houseGround=(VProjMortHouseGround) list.get(0);
							if(null!=houseGround){
								elementCode.setSycqr_v(houseGround.getPropertyperson());
							}
						}
					}
					if(mortgage.getTypeid()==10){
						List list=businessServMort.seeBusiness(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortBusiness business=(VProjMortBusiness) list.get(0);
							if(null!=business){
								elementCode.setSycqr_v(business.getPropertyperson());
							}
						}
					}
					if(mortgage.getTypeid()==11){
						List list=businessandliveService.seeBusinessandlive(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortBusAndLive bus=(VProjMortBusAndLive) list.get(0);
							if(null!=bus){
								elementCode.setSycqr_v(bus.getPropertyperson());
							}
						}
					}
					if(mortgage.getTypeid()==13){
						List list=industryService.seeIndustry(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortIndustry industry=(VProjMortIndustry) list.get(0);
							if(null!=industry){
								elementCode.setSycqr_v(industry.getPropertyperson());
							}
						}
					}
				}
				if(mortgage.getAssuretypeid()==604){
					int count = 0;
					if(!StringUtil.isNumeric(rnum)){
						rnum = "0";
					}
					count = Integer.parseInt(rnum);
					if(count <10){
						/*project向上转型 */
						elementCode.setDydbhtbh_v("MA-"+((BaseProject)getProject()).getProjectNumber().replaceAll("_", "-")+"-0"+count);//抵押合同编号
					}else{
						elementCode.setDydbhtbh_v("MA-"+((BaseProject)getProject()).getProjectNumber().replaceAll("_", "-")+"-"+count);//抵押合同编号
					}
				}
				if(mortgage.getAssuretypeid() == 605) {
					if(mortgage.getMortgagepersontypeforvalue() != null){
						elementCode.setZywmc_v(mortgage.getMortgagepersontypeforvalue());
					}
					int count = 0;
					if(!StringUtil.isNumeric(rnum)){
						rnum = "0";
					}
					count = Integer.parseInt(rnum);
					if(count <10){
						elementCode.setZydbhtbh_v("PA-"+((BaseProject)getProject()).getProjectNumber().replaceAll("_", "-")+"-0"+count);//质押合同编号
					}else{
						elementCode.setZydbhtbh_v("PA-"+((BaseProject)getProject()).getProjectNumber().replaceAll("_", "-")+"-"+count);//质押合同编号
					}
					//creditBaseDao.saveDatas(contract);
				}
				//抵押物认定价值
				if(mortgage.getFinalCertificationPrice() != null) {
					elementCode.setDywgyjz_v(mortgage.getFinalCertificationPrice().toString()+"元");
				}
				if(mortgage.getPersonTypeId() == 603){//自然人
					p = (VPersonDic)creditBaseDao.getById(VPersonDic.class, mortgage.getAssureofname());
					if(null != p.getName()){
						elementCode.setBzrmc_v(p.getName());//保证人
						elementCode.setDyr_v(p.getName());//抵押人
						elementCode.setCzr_v(p.getName());//出质人
					}
					if(p.getCardtypevalue() != null) {//证件种类
						elementCode.setBzrzjzl_v(p.getCardtypevalue().toString());
						elementCode.setDyrzjzl(p.getCardtypevalue().toString());
						elementCode.setCzrzjzl_v(p.getCardtypevalue().toString());
					}
					if(null != p.getCardnumber()){//证件号码
						elementCode.setBzrzjhm_v(p.getCardnumber());
						elementCode.setDyrzjhm(p.getCardnumber());//抵押人身份证号码
						elementCode.setCzrzjhm_v(p.getCardnumber());//出质人身份证号码
					}
					if(null != p.getPostaddress()){//地址
						elementCode.setBzrdz_v(p.getPostaddress());
						elementCode.setDyrdz_v(p.getPostaddress());//抵押人通讯地址
						elementCode.setCzrtxdz_v(p.getPostaddress());//出质人通讯地址
					}
					if(null != p.getPostcode()){
						elementCode.setBzryzbm_v(p.getPostcode());
						elementCode.setDyryzbm_v(p.getPostcode());//抵押人邮政编码
						elementCode.setCzryzbm_v(p.getPostcode());//出质人邮政编码
					}
					if(null != p.getTelphone()&& null!= p.getCellphone()){
						elementCode.setBzrlxdh_v(p.getCellphone()+"/"+p.getTelphone());
						elementCode.setDyrdh_v(p.getCellphone()+"/"+p.getTelphone());//抵押人联系电话
						elementCode.setCzrlxdh_v(p.getCellphone()+"/"+p.getTelphone());//出质人联系电话
					}else if(null == p.getTelphone()&& null!= p.getCellphone()){
						elementCode.setBzrlxdh_v(p.getCellphone());
						elementCode.setDyrdh_v(p.getCellphone());//抵押人联系电话
						elementCode.setCzrlxdh_v(p.getCellphone());//出质人联系电话
					}else if(null != p.getTelphone()&& null== p.getCellphone()){
						elementCode.setBzrlxdh_v(p.getTelphone());
						elementCode.setDyrdh_v(p.getTelphone());//抵押人联系电话
						elementCode.setCzrlxdh_v(p.getTelphone());//出质人联系电话
					}
					if(null != p.getFax()){
						elementCode.setBzrczhm_v(p.getFax());
						elementCode.setDyrcz_v(p.getFax());//抵押人传真
						elementCode.setCzrczhm_v(p.getFax());//出质人传真号码
					}
					Spouse spouse = spouseService.getByPersonId(p.getId());
					if(spouse != null) {//配偶信息
						elementCode.setBzrpomc_v(spouse.getName());
						elementCode.setBzrpozjhm_v(spouse.getCardnumber());
					}
				}else if(mortgage.getPersonTypeId() == 602){//法人
					e = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, mortgage.getAssureofname());
					if(e.getEnterprisename()!= null){
						elementCode.setBzrmc_v(e.getEnterprisename());//保证人
						elementCode.setDyr_v(e.getEnterprisename());//抵押人
						elementCode.setCzr_v(e.getEnterprisename());//出质人
					}
					if(e.getLegalperson() != null) {
						elementCode.setBzffddbr_v(e.getLegalperson());
						elementCode.setDyffddbr_v(e.getLegalperson());
						elementCode.setCzffddbr_v(e.getLegalperson());
					}
					if(e.getCciaa()!= null){
						elementCode.setBzfyyzzh_v(e.getCciaa());
						elementCode.setDyfyyzzhm_v(e.getCciaa());//抵押公司营业执照号码
						elementCode.setCzfyyzzhm_v(e.getCciaa());//出质方营业执照号码
					}
					if(e.getArea() != null){
						elementCode.setBzrdz_v(e.getArea());
						elementCode.setDyrdz_v(e.getArea());//抵押公司通讯地址
						elementCode.setCzrtxdz_v(e.getArea());//出质方通讯地址
					}
					if(e.getPostcoding()!= null){
						elementCode.setBzryzbm_v(e.getPostcoding());
						elementCode.setDyryzbm_v(e.getPostcoding());//抵押公司邮政编码
						elementCode.setCzryzbm_v(e.getPostcoding());//出质方邮政编码
					}
					if(e.getTelephone()!= null){
						elementCode.setBzrlxdh_v(e.getTelephone());
						elementCode.setDyrdh_v(e.getTelephone());//抵押人联系电话
						elementCode.setCzrlxdh_v(e.getTelephone());//出质人联系电话
					}
					if(e.getFax() != null){
						elementCode.setBzrczhm_v(e.getFax());
						elementCode.setDyrcz_v(e.getFax());//抵押人传真
						elementCode.setCzrczhm_v(e.getFax());//出质人传真号码
					}
					VPersonDic vpd= (VPersonDic)creditBaseDao.getById(VPersonDic.class, e.getLegalpersonid());
					if(vpd.getJobvalue() != null) {
						elementCode.setBzffddbrzw_v(vpd.getJobvalue());
						elementCode.setDyffddbrzw_v(vpd.getJobvalue());
						elementCode.setCzffddbrzw_v(vpd.getJobvalue());
					}
					Spouse spouse = spouseService.getByPersonId(vpd.getId());
					if(spouse != null) {//配偶信息
						elementCode.setBzrpomc_v(spouse.getName());
						elementCode.setBzrpozjhm_v(spouse.getCardnumber());
					}
				}
				//产权人，房地产地点，建筑面积，证件号码
				if(mortgage.getId()!= null){

					
					StringBuffer fdbwxq = new StringBuffer();
					if(mortgage.getTypeid()!= 0 && (mortgage.getTypeid()==7 || mortgage.getTypeid()==15 || mortgage.getTypeid()==16 || mortgage.getTypeid()==17)){//住宅、公寓、联排别墅、独栋别墅
						String hql3 = "from ProcreditMortgageHouse where mortgageid="+mortgage.getId();
						List list3 = creditBaseDao.queryHql(hql3);
						if(list3 != null && list3.size()>0){
							ProcreditMortgageHouse pmb = (ProcreditMortgageHouse)list3.get(0);
							if(pmb.getHouseaddress() != null) {
								elementCode.setDywszd_v(pmb.getHouseaddress());
							}
							if(pmb.getMutualofperson() != null) {
								elementCode.setDywgyr_v(pmb.getMutualofperson());
							}
							if(pmb.getCertificatenumber() != null) {
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
						} 
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==10){//商业用地
						String hql = "from ProcreditMortgageBusiness where mortgageid="+mortgage.getId();
						List list = creditBaseDao.queryHql(hql);
						if(list != null && list.size()>0){
							ProcreditMortgageBusiness pmb = (ProcreditMortgageBusiness)list.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress() != null) {
								pmb.setAddress("");
							}
							if(pmb.getAddress() != null) {
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==8){//商铺写字楼
						String hql6 = "from ProcreditMortgageOfficebuilding where mortgageid="+mortgage.getId();
						List list6 = creditBaseDao.queryHql(hql6);
						if(list6 != null && list6.size()>0){
							ProcreditMortgageOfficebuilding pmb = (ProcreditMortgageOfficebuilding)list6.get(0);
							if(pmb.getMutualofperson()!= null){
								elementCode.setDywgyr_v(pmb.getMutualofperson());
							}
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getHouseaddress()!= null){
								elementCode.setDywszd_v(pmb.getHouseaddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==9){//住宅用地
						String hql4 = "from ProcreditMortgageHouseground where mortgageid="+mortgage.getId();
						List list4 = creditBaseDao.queryHql(hql4);
						if(list4 != null && list4.size()>0){
							ProcreditMortgageHouseground pmb = (ProcreditMortgageHouseground)list4.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress()!= null){
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==11){//商住用地
						String hql1 = "from ProcreditMortgageBusinessandlive where mortgageid="+mortgage.getId();
						List list1 = creditBaseDao.queryHql(hql1);
						if(list1!= null && list1.size()>0){
							ProcreditMortgageBusinessandlive pmb = (ProcreditMortgageBusinessandlive)list1.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress()!= null){
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==12){//教育用地
						String hql2 = "from ProcreditMortgageEducation where mortgageid="+mortgage.getId();
						List list2 = creditBaseDao.queryHql(hql2);
						if(list2 != null && list2.size()>0){
							ProcreditMortgageEducation pmb = (ProcreditMortgageEducation)list2.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress()!= null){
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==13){//工业用地
						String hql5 = "from ProcreditMortgageIndustry where mortgageid="+mortgage.getId();
						List list5 = creditBaseDao.queryHql(hql5);
						if(list5 != null && list5.size()>0){
							ProcreditMortgageIndustry pmb = (ProcreditMortgageIndustry)list5.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress()!= null){
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==6){//存货商品
						String hql = "from VProjMortProduct where mortgageid ="+mortgage.getId();
						VProjMortProduct pmb = null;
						List list = creditBaseDao.queryHql(hql);
						if(list != null && list.size()>0){
							pmb = (VProjMortProduct)list.get(0);
							if(pmb.getDepositary() != null) {
								elementCode.setZywcfdd_v(pmb.getDepositary());
							}
							if(pmb.getAmount() != null) {
								elementCode.setZywsl_v(pmb.getAmount().toString());
							}
						}
				}
			}
		
			//担保人（所有担保人）
			String hql = "from VProcreditMortgageGlobal where projid ="+ ((BaseProject)getProject()).getProjectId()+" and businessType= '"+((BaseProject)getProject()).getBusinessType()+"'";
			/*向上转型，父类包含所有合同要素的基本信息  add by gao*/
			List<BaseVProcreditDictionaryExceptFinancing> vdList = creditBaseDao.queryHql(hql);
			String dbrStr = "";
			for(BaseVProcreditDictionaryExceptFinancing vd : vdList) {
				if(vd.getAssureofnameEnterOrPerson() != null && vd.getAssureofnameEnterOrPerson() != "") {
					dbrStr = dbrStr +"/"+ vd.getAssureofnameEnterOrPerson();
				}
			}
			elementCode.setDbrmc_v(dbrStr);
			//借款合同编号
			String dbht="";
			List<VProcreditContract> vpcclist= vProcreditContractDao.getList(((BaseProject)getProject()).getProjectId().toString(), ((BaseProject)getProject()).getBusinessType(), null);
			for(VProcreditContract v:vpcclist){
				dbht=dbht+"/"+v.getContractNumber();
				
			}
	    	elementCode.setJkhtbh_v(dbht);
	    	//担保合同编号（所有）
	    	String sydbht="";
			List<VProcreditContract> vpcclist1= vProcreditContractDao.getList(((BaseProject)getProject()).getProjectId().toString(), ((BaseProject)getProject()).getBusinessType(),"('thirdContract')");
			for(VProcreditContract v:vpcclist1){
				sydbht = sydbht + "/"+v.getContractNumber();
			}
	    	elementCode.setDbhtbh_v(sydbht);
		}
	}
	/*设置我方主体*/
	public void generaterEntityElement(BaseElementCode elementCode){
		BaseProject baseProject = (BaseProject)getProject();
		CreditBaseDao creditBaseDao = (CreditBaseDao)AppUtil.getBean("creditBaseDao");
		/*
		 * 我方主体-债权人
		 * 个人person_ourmain，企业company_ourmain
		 * */
		if(baseProject.getMineType().equals("person_ourmain")){//我方主体为个人   无论是否是集团版
			SlPersonMain personMain = null;
			if(baseProject.getMineId()!=0){
				try {
					personMain = (SlPersonMain)creditBaseDao.getById(SlPersonMain.class, baseProject.getMineId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(personMain != null){
					if(personMain.getName()!=null){
						elementCode.setZqrmc_v(personMain.getName());
					}
					if(personMain.getAddress()!=null){
						elementCode.setZqrdz_v(personMain.getAddress());
					}
					if(personMain.getPostalCode()!= null){
						elementCode.setZqryb_v(personMain.getPostalCode());//债权人邮政编码
					}
					if(personMain.getLinktel()!=null){
						elementCode.setZqrdh_v(personMain.getLinktel());
					}
					if(personMain.getTax()!= null){
						elementCode.setZqrcz_v(personMain.getTax());//债权人传真号码
					}
				}
			}
		}else if(baseProject.getMineType().equals("company_ourmain")&&!"true".equals(AppUtil.getSystemIsGroupVersion())){//我方主体为企业    且非集团版本   ，集团办mineId关联
			SlCompanyMain companyMain = null;
			if(baseProject.getMineId()!=0){
				try {
					companyMain = (SlCompanyMain)creditBaseDao.getById(SlCompanyMain.class, baseProject.getMineId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(companyMain != null){
					if(companyMain.getCorName()!= null){
						elementCode.setZqrmc_v(companyMain.getCorName());
					}
					if(companyMain.getMessageAddress() != null){
						elementCode.setZqrdz_v(companyMain.getMessageAddress());
					}
					if(companyMain.getPostalCode()!= null){
						elementCode.setZqryb_v(companyMain.getPostalCode());//债权公司邮政编码
					}
					if(companyMain.getTel()!= null){
						elementCode.setZqrdh_v(companyMain.getTel());
					}
					if(companyMain.getTax()!= null){
						elementCode.setZqrcz_v(companyMain.getTax());
					}
					if(companyMain.getLawName()!=null){
						elementCode.setZqffddbr_v(companyMain.getLawName());//债权方法定代表人
					}
				}
			}
		}else{//企业   且集团版
			Organization org = null;
			if(baseProject.getCompanyId()!=0){
				try {
					org = (Organization)creditBaseDao.getById(Organization.class, baseProject.getCompanyId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(org != null){
					if(org.getOrgName()!= null){
						elementCode.setZqrmc_v(org.getOrgName());
					}
					if(org.getAddress() != null){
						elementCode.setZqrdz_v(org.getAddress());
					}
					if(org.getPostCode()!= null){
						elementCode.setZqryb_v(org.getPostCode());//债权公司邮政编码
					}
					if(org.getLinktel()!= null){
						elementCode.setZqrdh_v(org.getLinktel());
					}
					if(org.getFax()!= null){
						elementCode.setZqrcz_v(org.getFax());
					}
				/*	if(org.get()!=null){
						elementCode.setZqffddbr_v(org.getLawName());//债权方法定代表人
					}*/
				}
			}
		}
	}
	


	public Object getProject() {
		return project;
	}


	public void setProject(Object project) {
		this.project = project;
	}


	public Object getSupplior() {
		return supplior;
	}


	public void setSupplior(Object supplior) {
		this.supplior = supplior;
	}


	public Object getvObjectInfo() {
		return vObjectInfo;
	}


	public void setvObjectInfo(Object vObjectInfo) {
		this.vObjectInfo = vObjectInfo;
	}



	public Object getOrganization() {
		return organization;
	}


	public void setOrganization(Object organization) {
		this.organization = organization;
	}



	public Object getvCustomer() {
		return vCustomer;
	}



	public void setvCustomer(Object vCustomer) {
		this.vCustomer = vCustomer;
	}



	public String getRnum() {
		return rnum;
	}



	public void setRnum(String rnum) {
		this.rnum = rnum;
	}



	public Object getMortgage() {
		return mortgage;
	}



	public void setMortgage(Object mortgage) {
		this.mortgage = mortgage;
	}

	public Object getPawnItem() {
		return pawnItem;
	}

	public void setPawnItem(Object pawnItem) {
		this.pawnItem = pawnItem;
	}



}
