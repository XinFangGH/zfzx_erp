package com.zhiwei.credit.action.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.p2p.BpFinanceApplyUser;
import com.zhiwei.credit.model.p2p.loan.P2pLoanProduct;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionDirProService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.BpFinanceApplyUserService;
import com.zhiwei.credit.service.p2p.loan.P2pLoanProductService;
import com.zhiwei.credit.service.system.DictionaryService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class BpFinanceApplyUserAction extends BaseAction{
	@Resource
	private BpFinanceApplyUserService bpFinanceApplyUserService;
	@Resource
	private BpCustRelationService bpCustRelationService;
	@Resource
	private PersonService personService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private AreaDicService areaDicService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private P2pLoanProductService p2pLoanProductService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private BpPersionDirProService bpPersionDirProService;
	
	private BpFinanceApplyUser bpFinanceApplyUser=new BpFinanceApplyUser();
	
	private Long loanId;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public BpFinanceApplyUser getBpFinanceApplyUser() {
		return bpFinanceApplyUser;
	}

	public void setBpFinanceApplyUser(BpFinanceApplyUser bpFinanceApplyUser) {
		this.bpFinanceApplyUser = bpFinanceApplyUser;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpFinanceApplyUser> list= bpFinanceApplyUserService.getAll(filter);
		Type type=new TypeToken<List<BpFinanceApplyUser>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 得到审请用户信息（稳安贷）
	 * @return
	 */
	public String personList(){
		Integer state=Integer.parseInt(this.getRequest().getParameter("state"));
		List<BpFinanceApplyUser> listPerson=bpFinanceApplyUserService.personList(start,limit,state,this.getRequest());
		if(listPerson!=null&&listPerson.size()>0){
			for(BpFinanceApplyUser user : listPerson){
				if(null!=user.getLoanUse()){
					Dictionary dic = dictionaryService.get(Long.valueOf(user.getLoanUse()));
					if(null!=dic){
						user.setLoanUseStr(dic.getItemValue());//借款用途
					}
				}
			}
		}
		List<BpFinanceApplyUser> listPersonCount=bpFinanceApplyUserService.personList(null,null,state,this.getRequest());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")  //totalProperty(之前数据)
		.append(listPersonCount!=null?listPersonCount.size():0).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
		/* .excludeFieldsWithoutExposeAnnotation() */.create();
		buff.append(gson.toJson(listPerson));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	
	public String loanList(){
		Integer state=Integer.parseInt(this.getRequest().getParameter("state"));
		List<BpFinanceApplyUser> listPerson=bpFinanceApplyUserService.personList(start,limit,state,this.getRequest());
		List<BpFinanceApplyUser> listPersonCount=bpFinanceApplyUserService.personList(null,null,state,this.getRequest());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(listPersonCount!=null?listPersonCount.size():0).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(listPerson));
		buff.append("}");
		jsonString = buff.toString();
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
				bpFinanceApplyUserService.remove(new Long(id));
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
		
		long loanId=Long.parseLong(this.getRequest().getParameter("loanId"));
		BpFinanceApplyUser bpFinanceApplyUser=bpFinanceApplyUserService.get(loanId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpFinanceApplyUser));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	public String getInfo(){
		try{
			Map<String,Object> map=new HashMap<String,Object>();
			BpFinanceApplyUser bpFinanceApplyUser=bpFinanceApplyUserService.get(loanId);
			map.put("bpFinanceApplyUser", bpFinanceApplyUser);
			if(null!=bpFinanceApplyUser && null!=bpFinanceApplyUser.getUserID()){
				BpCustMember member=bpCustMemberService.get(bpFinanceApplyUser.getUserID());
				if(null!=member.getNativePlaceCity()){
					AreaDic areaDic=areaDicService.getById(member.getNativePlaceCity());
					member.setParentNativePlaceCity(areaDic.getParentId());
				}
				if(null!=member.getLiveCity()){
					AreaDic areaDic=areaDicService.getById(member.getLiveCity());
					member.setParentLiveCity(areaDic.getParentId());
				}
				if(null!=member.getHireCity()){
					AreaDic areaDic=areaDicService.getById(member.getHireCity());
					if(areaDic!=null&&!"".equals(areaDic)){
					member.setParentHireCity(areaDic.getParentId());
					}
				}
				if(null!=member.getHireCompanycategory()){
					AreaDic areaDic=areaDicService.getById(member.getHireCompanycategory());
					if(areaDic!=null&&!"".equals(areaDic)){
					member.setHireCompanycategoryName(areaDic.getText());
					}   
			  }
				map.put("bpCustMember", member);
			}
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			//将数据转成JSON格式
			StringBuffer sb = new StringBuffer("{success:true,data:");
			sb.append(gson.toJson(map));
			sb.append("}");
			setJsonString(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		long loaId=Long.parseLong(this.getRequest().getParameter("loanId"));
		String state=this.getRequest().getParameter("state");
		
		bpFinanceApplyUser.setLoanId(loaId);
		if(bpFinanceApplyUser.getLoanId()==null){
			bpFinanceApplyUserService.save(bpFinanceApplyUser);
		}else{
			BpFinanceApplyUser orgBpFinanceApplyUser=bpFinanceApplyUserService.get(bpFinanceApplyUser.getLoanId());
			orgBpFinanceApplyUser.setState(state);
			try{
				BeanUtil.copyNotNullProperties(orgBpFinanceApplyUser, bpFinanceApplyUser);
				bpFinanceApplyUserService.save(orgBpFinanceApplyUser);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String startLoanApprovalFlow(){
		try{
			String state=this.getRequest().getParameter("state");
			bpFinanceApplyUser=bpFinanceApplyUserService.get(loanId);
			BpCustRelation bpCustRelation=bpCustRelationService.getByTypeAndP2pCustId(bpFinanceApplyUser.getUserID(),"p_loan");
			if(null==bpCustRelation){
				jsonString="{success:true,flag:false}";
				return SUCCESS;
			}else{
				Person person=personService.getById(bpCustRelation.getOfflineCusId().intValue());
				String str = creditProjectService.startLoanApprovalFlow(bpFinanceApplyUser,person,state);
				jsonString="{success:true,"+str+"}";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 线上借款流程启动方法
	 * @return
	 */
	public String startLoanApprovalFlow2(){
		try{
			bpFinanceApplyUser=bpFinanceApplyUserService.get(loanId);
			BpCustRelation bpCustRelation=bpCustRelationService.getP2pCustById(bpFinanceApplyUser.getUserID());
			if(null==bpCustRelation){
				jsonString="{success:true,flag:false}";
				return SUCCESS;
			}else{
				if("p_loan".equals(bpCustRelation.getOfflineCustType())){//个人
					jsonString="{success:true,custType:'p_loan',custId:"+bpCustRelation.getOfflineCusId()+"}";
				}else if("b_loan".equals(bpCustRelation.getOfflineCustType())){//企业
					jsonString="{success:true,custType:'b_loan',custId:"+bpCustRelation.getOfflineCusId()+"}";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获得线上申请用户信息
	 * @return
	 */
	public String getApplyUserInfo(){
		String productId=this.getRequest().getParameter("productId");//产品id
		String userId=this.getRequest().getParameter("userId");//用户id
		String userType=this.getRequest().getParameter("userType");//用户类别  b_loan企业,p_loan
		Map<String, Object> map = new HashMap<String, Object>();
		// 读取个人客户信息
		Person p = null;
		if (userType.equals("b_loan")) {
			Enterprise enterprise1 = enterpriseService.getById(Integer.valueOf(userId));
			if (enterprise1.getLegalpersonid() != null) {
				p = this.personService.getById(enterprise1.getLegalpersonid());
				map.put("person", p);
			}
			if (null != enterprise1.getHangyeType()) {
				if (null != areaDicService.getById(enterprise1.getHangyeType())) {
					enterprise1.setHangyeName(areaDicService.getById(enterprise1.getHangyeType()).getText());
				}
			}
			map.put("enterprise", enterprise1);
		}else if (userType.equals("p_loan")) {
			p = this.personService.getById(Integer.valueOf(userId));
			map.put("person", p);
		}
		P2pLoanProduct product=p2pLoanProductService.get(Long.valueOf(productId));
		map.put("productName",product.getProductName());
		map.put("productId",productId);
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 查询详细信息
	 * @return
	 */
	public String getDetailed(){
		
		String loanId=this.getRequest().getParameter("loanId");
		BpFinanceApplyUser bpFinanceApplyUser=bpFinanceApplyUserService.getDetailed(loanId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpFinanceApplyUser));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
	/**
	 * 审批通过
	 */
	public String approvalByData(){
		long loaId=Long.parseLong(this.getRequest().getParameter("loanId"));
		BpFinanceApplyUser bpFinanceApplyUser1=bpFinanceApplyUserService.get(loaId);
		P2pLoanProduct p2pLoanProduct = p2pLoanProductService.get(bpFinanceApplyUser1.getProductId());
		BpCustMember bpCustMember = bpCustMemberService.get(bpFinanceApplyUser1.getUserID());
		bpFinanceApplyUser1.setState("4");
		bpFinanceApplyUser1.setApprovalDate(new Date());
		try{
			BeanUtil.copyNotNullProperties(bpFinanceApplyUser1, bpFinanceApplyUser);
			bpFinanceApplyUserService.save(bpFinanceApplyUser1);
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		
		//往cs_persion表插入数据===========================开始===============================
		Person person = null;
		BpCustRelation bpCustRelation = null;
		try {
			person = personService.queryPersonCardnumber(bpCustMember.getCardcode());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(null == person){//cs_person表中不存在此客户时，新插入一条数据
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			AppUser user= ContextUtil.getCurrentUser();
			person = new Person();
			person.setName(bpCustMember.getTruename());
			person.setCardtype(309);//默认身份证
			person.setCardnumber(bpCustMember.getCardcode());//身份证号码
			person.setCellphone(bpCustMember.getTelphone());
			person.setCustomerSource("credit");//客户来源为：信贷客户
			person.setBelongedId(user.getUserId().toString());//如果为新增的客户，则当前登陆人为共享人及创建者
			person.setCreaterId(user.getUserId());
			person.setCompanyId(user.getCompanyId());
			person.setSex(bpCustMember.getSex());//性别
			person.setDegreewei(bpCustMember.getCollegeDegree());//学历
			person.setMarry(bpCustMember.getMarry());//婚姻
			person.setJobincome(bpCustMember.getHireMonthlyincome().doubleValue());//月收入
			person.setFamilyaddress(bpCustMember.getRelationAddress());//现居住地
			person.setUnitaddress(bpCustMember.getHireAddress());//公司地址
			if(null!=bpCustMember.getHireCompanycategory() && !"".equals(bpCustMember.getHireCompanycategory())){
				List<Dictionary> dicList=dictionaryService.getByProTypeId(bpCustMember.getHireCompanycategory());
				if(null!=dicList && dicList.size()>0){
					person.setCompanyScale(dicList.get(0).getItemValue());//公司行业
				}
			}
			if(null!=bpCustMember.getHireCompanysize() && !"".equals(bpCustMember.getHireCompanysize())){
				List<Dictionary> dicList=dictionaryService.getByProTypeId(bpCustMember.getHireCompanysize());
				if(null!=dicList && dicList.size()>0){
					person.setCompanyScale(dicList.get(0).getItemValue());//公司规模
				}
			}
			if(null!=bpCustMember.getHirePosition() && !"".equals(bpCustMember.getHirePosition())){
				List<Dictionary> dicList=dictionaryService.getByProTypeId(bpCustMember.getHirePosition());
				if(null!=dicList && dicList.size()>0){
					person.setCompanyScale(dicList.get(0).getItemValue());//职位
				}
			}
			try {
				if(null!=bpCustMember.getTeacherStartYear() && !"".equals(bpCustMember.getTeacherStartYear())){
					person.setJobstarttime(sd.parse(bpCustMember.getTeacherStartYear()));//工作时间
				}else if(null!=bpCustMember.getHireStartyear() && !"".equals(bpCustMember.getHireStartyear())){
					person.setJobstarttime(bpCustMember.getHireStartyear());//工作时间
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String birthDay=bpCustMember.getCardcode().substring(6,10)+"-"+bpCustMember.getCardcode().substring(10,12)+"-"+bpCustMember.getCardcode().substring(12,14);
			try {
				person.setBirthday(sd.parse(birthDay));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			personService.save(person);
			
			bpCustRelation = new BpCustRelation();
			bpCustRelation.setP2pCustId(bpCustMember.getId());
			bpCustRelation.setOfflineCusId(Long.valueOf(person.getId()));
			bpCustRelation.setOfflineCustType("p_loan");
			bpCustRelationService.save(bpCustRelation);
		}else{//cs_person存在此客户
			bpCustRelation = bpCustRelationService.getByTypeAndP2pCustId(bpCustMember.getId(),"p_loan");
			if(null == bpCustRelation){
				bpCustRelation = new BpCustRelation();
				bpCustRelation.setP2pCustId(bpCustMember.getId());
				bpCustRelation.setOfflineCusId(Long.valueOf(person.getId()));
				bpCustRelation.setOfflineCustType("p_loan");
				bpCustRelationService.save(bpCustRelation);
			}
		}
		//往cs_persion表插入数据===========================结束===============================
		
		//往sl_smallloan_project表插入数据===========================开始===============================
		String preName="zxxd_";//项目编号的前缀，zxxd代表在线信贷
		String strDdate = DateUtil.getNowDateTime("yyMMdd");
		String projectNumber = ""; //项目编号
		String projectName="";//项目名称
		SlSmallloanProject project = new SlSmallloanProject(); //初始化项目信息
		SlSmallloanProject slproject = slSmallloanProjectService.getProjectNumber(preName+strDdate);
		if(slproject!=null){
			String number = "";
			String proNum = slproject.getProjectNumber();
			String[] proArrs = proNum.split("_");
			int num = new Integer(proArrs[2]);
			num+=1;
			if(num<10){
				number = "00"+num;
			}else if(num>=10&&num<100){
				number = "0"+num;
			}else{
				number = String.valueOf(num);
			}
			projectNumber =preName+strDdate+"_"+number;
		}else{
			projectNumber = preName+strDdate+"_"+"001";
		}
		 //生成项目名开始 （客户名+年月+信用借款项目+流程名称）
		SimpleDateFormat formart=new SimpleDateFormat("yyyy",Locale.CHINA);
		SimpleDateFormat formart1=new SimpleDateFormat("MM",Locale.CHINA);
		Calendar calendar=Calendar.getInstance(Locale.CHINA);
		String yearStr=formart.format(calendar.getTime());
		String monthStr=formart1.format(calendar.getTime());
		String yearAndMonthStr=yearStr+"年"+monthStr+"月";       //年月字符串
		String cusName=bpCustMember.getTruename();//客户真实姓名
		String flowTypeName =bpFinanceApplyUser.getProductName();
		projectName=cusName+yearAndMonthStr+"信用借款项目";
		
		project.setOppositeType("person_customer");
		project.setProjectName(projectName);
		project.setProjectNumber(projectNumber);
		
		//必填项==============开始============================
		project.setCompanyId(1L);
		project.setFlowType("信贷业务");
		project.setOperationType("PersonalCreditLoanBusiness");
	//	project.setOppositeType("oppositeType");
		project.setOppositeID(Long.valueOf(person.getId()));
		project.setBusinessType("SmallLoan");
		//必填项==============结束============================
		
		//贷款金额、计息方式、还款周期、   贷款利率、管理咨询费率、财务服务费率
		project.setProjectMoney(bpFinanceApplyUser1.getApprovalLoanMoney());//审批金额
		if(null != bpFinanceApplyUser1.getPayIntersetWay()){
			project.setAccrualtype(bpFinanceApplyUser1.getPayIntersetWay());//还款方式
		}
		project.setPayaccrualType(p2pLoanProduct.getPayaccrualType());//还款周期
		project.setPayintentPeriod(bpFinanceApplyUser1.getApprovalLoanTimeLen().intValue());//贷款期限
		project.setIsPreposePayAccrual(p2pLoanProduct.getIsPreposePayAccrual());//前置付息
		project.setIsInterestByOneTime(p2pLoanProduct.getIsInterestByOneTime());//是否一次性支付利息
		
		//贷款年化利率
		BigDecimal yearRate = bpFinanceApplyUser1.getApprovalYearAccrualRate();
		if(null != yearRate){
			project.setYearAccrualRate(yearRate);
			project.setAccrual(yearRate.divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_EVEN));//月化利率
			project.setDayAccrualRate(yearRate.divide(new BigDecimal(360),10,BigDecimal.ROUND_HALF_EVEN));//日化利率
			project.setSumAccrualRate(yearRate.divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_EVEN));//合计利率
		}
		//财务服务费年化利率
		BigDecimal financeRate = bpFinanceApplyUser1.getApprovalYearFinanceServiceOfRate();
		if(null!= financeRate){
			project.setYearFinanceServiceOfRate(financeRate);
			project.setFinanceServiceOfRate(financeRate.divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_EVEN));
			project.setDayFinanceServiceOfRate(financeRate.divide(new BigDecimal(360),10,BigDecimal.ROUND_HALF_EVEN));
			project.setSumFinanceServiceOfRate(financeRate.divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_EVEN));//合计利率
		}
		//咨询管理费年化利率
		BigDecimal managementRate=bpFinanceApplyUser1.getApprovalYearManagementConsultingOfRate();
		if(null != managementRate){
			project.setYearManagementConsultingOfRate(managementRate);
			project.setManagementConsultingOfRate(managementRate.divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_EVEN));
			project.setDayManagementConsultingOfRate(managementRate.divide(new BigDecimal(360),10,BigDecimal.ROUND_HALF_EVEN));
			project.setSumManagementConsultingOfRate(managementRate.divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_EVEN));
		}
		//逾期贷款利率
		BigDecimal overdueRateLoan=bpFinanceApplyUser1.getApprovalOverdueRateLoan();
		if(null != overdueRateLoan){
			project.setOverdueRateLoan(overdueRateLoan);
		}
		//逾期罚息利率
		BigDecimal overdueRate=bpFinanceApplyUser1.getApprovalOverdueRate();
		if(null != overdueRate){
			project.setOverdueRate(overdueRate);
		}
		project.setPurposeType(bpFinanceApplyUser1.getLoanUse());//借款用途
		
		//add by zcb
		project.setStartDate(bpFinanceApplyUser1.getCreateTime());
		project.setIntentDate(DateUtil.addMonthsToDate(project.getStartDate(),Integer.valueOf(bpFinanceApplyUser.getApprovalLoanTimeLen().toString())));
		project.setProductId(p2pLoanProduct.getProductId());
		project.setIsStartDatePay("2");//是否按还款日还款
	//	project.setAccrualtype("sameprincipalandInterest");//还款方式(等额本息)
		//end
		//贷款用途
		project.setPurposeType(bpFinanceApplyUser1.getLoanUse());
		slSmallloanProjectService.save(project);
		//往sl_smallloan_project表插入数据===========================结束===============================
		
		BpFundProject bpFundProject=new BpFundProject();
		try {
			BeanUtil.copyNotNullProperties(bpFundProject, project);
		}  catch (Exception e) {
			e.printStackTrace();
		}
		bpFundProject.setFlag(Short.valueOf("1"));
		bpFundProject.setPlatFormJointMoney(bpFinanceApplyUser1.getApprovalLoanMoney());
		bpFundProject.setResource("credit");//线上信贷项目申请（线上个人借款）类型
		bpFundProject.setLoanId(loanId);//线上信贷主键Id（线上个人借款：BpFinanceApplyUser）
		bpFundProjectService.save(bpFundProject);
		 
		BpPersionDirPro pDirPro = new BpPersionDirPro();
		slSmallloanProjectService.initPersionP2p(bpFundProject, pDirPro, person);
		pDirPro.setResource("credit");//线上信贷项目申请（线上个人借款）类型
		pDirPro.setLoanId(loanId);//线上信贷主键Id（线上个人借款：BpFinanceApplyUser）
		bpPersionDirProService.save(pDirPro);
		
		setJsonString("{success:true,msg:'审核成功！'}");
		return SUCCESS;
		
	}
	
}
