package com.zhiwei.credit.action.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.model.creditFlow.bonusSystem.gradeSet.MemberGradeSet;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonRelation;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.bonusSystem.gradeSet.MemberGradeSetService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonRelationService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.customer.person.SpouseService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.util.MD5;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author 
 *
 */
public class BpCustMemberAction extends BaseAction{
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private ObSystemAccountService obSystemAccountService;
	@Resource
	private PersonService personService;
	@Resource
	private BpCustRelationService bpCustRelationService;
	@Resource
	private PersonRelationService personRelationService;
	@Resource
	private AreaDicService areaDicService;
	@Resource
	private MemberGradeSetService memberGradeSetService;
	@Resource
	private SpouseService spouseService;
	
	private BpCustMember bpCustMember;
	
	private Long id;
	private String recommandPerson;
	public  String plainpassword;
	
	/**
	 * 刷新推荐用户数据
	 * svn:songwj
	 */
	public  void  refreshUserNum(){
		bpCustMemberService.getBpCustMemberList();
		listCommand();
	}
	
	/**
	 * 查询单个人推荐的会员信息
	 * 1、累计充值金额  totalRecharge     1
	 * 2、累计投资金额  totalInvestMoney  4
	 * 3、累计收益      totalprofitMoney  3
	 * 4、累计取现金额  totalEnchashment  2
	 * @return
	 */
	public String 	listRecommandPerson(){
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		List<BpCustMember> list = bpCustMemberService.getBpCustMemberByrecommandPerson(plainpassword);
		if(list != null){
			for(int i=0;i<list.size();i++){
				BpCustMember  bpCustMember = new BpCustMember();
				bpCustMember = list.get(i);
				bpCustMember = obSystemAccountService.getAccountSumMoney(bpCustMember);
			}
		}
		Type type=new TypeToken<List<BpCustMember>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).
		append(",result:");
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	 /**
	  * 刷新要求数据总量
	  * SVN：songwj
	  * @return
	  */
	public String listCommand(){
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		//将数据转成JSON格式
		List<BpCustMember> list1=bpCustMemberService.getAllList1(this.getRequest(),start,limit);
		List<BpCustMember> list2=bpCustMemberService.getAllList1(this.getRequest(),null,null);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list2!=null?list2.size():0).append(",result:");
		buff.append(gson.toJson(list1));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		//将数据转成JSON格式
		List<BpCustMember> list1=bpCustMemberService.getAllList(this.getRequest(),start,limit);
		List<BpCustMember> list2=bpCustMemberService.getAllListCount(this.getRequest(),null,null);
		List<BpCustMember> list = new ArrayList<BpCustMember>();
		if(null!=list1){
			for(int i=0;i<list1.size();i++){
				BpCustMember bpCustMember = list1.get(i);
				//查询会员等级
				if(bpCustMember.getScore()!=null){
					MemberGradeSet memberGradeSet=memberGradeSetService.findByUserScore(bpCustMember.getScore());
					if(memberGradeSet!=null){
						bpCustMember.setLevelMark(memberGradeSet.getLevelMark()!=null?memberGradeSet.getLevelMark():null);
					}
				}
				list.add(bpCustMember);
			}
		}
		Type type=new TypeToken<List<BpCustMember>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list2!=null?list2.size():0).append(",result:");
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	/**2014-08-06
	 * 显示该用户在p2p上传的信息
	 * @return
	 */
	public String getCustMem(){
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		long id=Long.parseLong(getRequest().getParameter("id"));
		BpCustMember bpCustMember=bpCustMemberService.get(id);
		if(bpCustMember!=null){
			if(bpCustMember.getHavehouse()==0){
				bpCustMember.setStrHouse("无");
			}else if(bpCustMember.getHavehouse()==1){
				bpCustMember.setStrHouse("有");
			}else{
				bpCustMember.setStrHouse("");
			}
			if(bpCustMember.getHavehouseloan()==0){
				bpCustMember.setStrHouseLoan("无");
			}else if(bpCustMember.getHavehouseloan()==1){
				bpCustMember.setStrHouseLoan("有");
			}else{
				bpCustMember.setStrHouseLoan("");
			}
			if(bpCustMember.getHavecar()==0){
				bpCustMember.setStrCar("无");
			}else if(bpCustMember.getHavecar()==1){
				bpCustMember.setStrCar("有");
			}else{
				bpCustMember.setStrCar("");
			}
			if(bpCustMember.getHavecarloan()==0){
				bpCustMember.setStrCarLoan("无");
			}else if(bpCustMember.getHavecarloan()==1){
				bpCustMember.setStrCarLoan("有");
			}else{
				bpCustMember.setStrCarLoan("");
			}
			if(bpCustMember.getMarry()==0){
				bpCustMember.setStrMarry("未婚");
			}else if(bpCustMember.getMarry()==1){
				bpCustMember.setStrMarry("已婚");
			}else{
				bpCustMember.setStrMarry("");
			}
			if(bpCustMember.getHavechildren()==0){
				bpCustMember.setStrChildren("无");
			}else if(bpCustMember.getHavechildren()==1){
				bpCustMember.setStrChildren("有");
			}else{
				bpCustMember.setStrChildren("");
			}
		}
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustMember));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 批量禁用
	 * @return
	 */
	public String multiForbi(){
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				BpCustMember bpCustMember = bpCustMemberService.get(new Long(id));
				String isForBidType=this.getRequest().getParameter("isForBidType");
				if(isForBidType!=null&&!"".equals(isForBidType)){
					bpCustMember.setIsForbidden(Integer.parseInt(isForBidType));//是否被禁用
				}
				bpCustMemberService.merge(bpCustMember);
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
		long id=Long.parseLong(this.getRequest().getParameter("id"));
		BpCustMember bpCustMember=bpCustMemberService.get(id);
		if(bpCustMember.getHavecar()!=null){
			if(bpCustMember.getHavehouse()==0){
				bpCustMember.setStrHouse("无");
			}else if(bpCustMember.getHavehouse()==1){
				bpCustMember.setStrHouse("有");
			}else{
				bpCustMember.setStrHouse("");
			}
		}
		if(bpCustMember.getHavecarloan()!=null){
			if(bpCustMember.getHavehouseloan()==0){
				bpCustMember.setStrHouseLoan("无");
			}else if(bpCustMember.getHavehouseloan()==1){
				bpCustMember.setStrHouseLoan("有");
			}else{
				bpCustMember.setStrHouseLoan("");
			}
		}
		if(bpCustMember.getHavecar()!=null){
			if(bpCustMember.getHavecar()==0){
				bpCustMember.setStrCar("无");
			}else if(bpCustMember.getHavecar()==1){
				bpCustMember.setStrCar("有");
			}else{
				bpCustMember.setStrCar("");
			}
		}
		
		if(bpCustMember.getMarry()!=null){
			if(bpCustMember.getMarry()==0){
				bpCustMember.setStrMarry("未婚");
			}else if(bpCustMember.getMarry()==1){
				bpCustMember.setStrMarry("已婚");
			}else{
				bpCustMember.setStrMarry("");
			}
		}
		if(bpCustMember.getHavechildren()!=null){
			if(bpCustMember.getHavechildren()==0){
				bpCustMember.setStrChildren("无");
			}else if(bpCustMember.getHavechildren()==1){
				bpCustMember.setStrChildren("有");
			}else{
				bpCustMember.setStrChildren("");
			}
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustMember));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpCustMember.getId()==null){
			bpCustMemberService.save(bpCustMember);
		}else{
			BpCustMember orgBpCustMember=bpCustMemberService.get(bpCustMember.getId());
			try{
				BeanUtil.copyNotNullProperties(orgBpCustMember, bpCustMember);
				bpCustMemberService.save(orgBpCustMember);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	public String updatePassword(){
		String id = getRequest().getParameter("custId");
		String password = getRequest().getParameter("password");
		if(null==id||"".equals(id)||null==password||"".equals(password)){
			setJsonString("{success:true,msg:'操作错误!'}");
		}else{
			BpCustMember member = bpCustMemberService.get(Long.parseLong(id));
			if(null==member){
				setJsonString("{success:true}");
			}else{
				MD5 md5 = new MD5();
				member.setPassword(md5.md5(password, "utf-8"));
				bpCustMemberService.merge(member);
				setJsonString("{success:true,msg:'操作成功'}");
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * 注册用户
	 */
	public String custList(){
		try{
			PagingBean pb=new PagingBean(start,limit);
			List<?> list=bpCustMemberService.cusrNumList(this.getRequest(), pb);
			Long count=bpCustMemberService.cusrNumSize(this.getRequest());
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(count).append(",result:[");
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Object[] obj=(Object[]) list.get(i);
					buff.append("{\"p2pId\":'");
					if(null!=obj[0]){
						buff.append(obj[0]);
					}
					buff.append("',\"personId\":'");
					if(null!=obj[1]){
						buff.append(obj[1]);
					}
					buff.append("',\"loginname\":'");
					if(null!=obj[2]){
						buff.append(obj[2]);
					}
					buff.append("',\"p2pCardNum\":'");
					if(null!=obj[3]){
						buff.append(obj[3]);
					}
					buff.append("',\"cardtype\":'");
					if(null!=obj[4]){
						buff.append(obj[4]);
					}
					buff.append("',\"cardtypeValue\":'");
					if(null!=obj[5]){
						buff.append(obj[5]);
					}
					buff.append("',\"cardNum\":'");
					if(null!=obj[6]){
						buff.append(obj[6]);
					}
					buff.append("',\"truename\":'");
					if(null!=obj[7]){
						buff.append(obj[7]);
					}
					buff.append("',\"name\":'");
					if(null!=obj[8]){
						buff.append(obj[8]);
					}
					buff.append("',\"telphone\":'");
					if(null!=obj[9]){
						buff.append(obj[9]);
					}
					buff.append("',\"cellphone\":'");
					if(null!=obj[10]){
						buff.append(obj[10]);
					}
					buff.append("',\"email\":'");
					if(null!=obj[11]){
						buff.append(obj[11]);
					}
					buff.append("',\"selfemail\":'");
					if(null!=obj[12]){
						buff.append(obj[12]);
					}
					buff.append("'},");
				}
			}
			if(null!=list && list.size()>0){
				buff.deleteCharAt(buff.length()-1);
			}
			buff.append("]}");
			jsonString=buff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getPersonInfo(){
		try{
			BpCustMember member = bpCustMemberService.get(id);
			if(null!=member){
				Person person=personService.queryPersonCardnumber(member.getCardcode());
				if(null!=person){
					jsonString="{success:true,msg:'该客户已存在，请手动绑定P2P账户'}";
				}else{
					jsonString="{success:true}";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String savePersonInfo(){
		try{
			BpCustMember member = bpCustMemberService.get(id);
			if(null!=member){
				Person person=new Person();
				person.setName(member.getTruename());
				person.setCardtype(309);
				person.setCardnumber(member.getCardcode());
				person.setCellphone(member.getTelphone());
				person.setSelfemail(member.getEmail());
				person.setCompanyId(Long.valueOf("1"));
				person.setCreatedate(new Date());
				person.setCreater(ContextUtil.getCurrentUser().getFullname());
				person.setBelongedId(ContextUtil.getCurrentUserId().toString());
				person.setCreaterId(ContextUtil.getCurrentUserId());
				person.setMarry(member.getMarry());
				person.setSex(member.getSex());
				person.setPostaddress(member.getRelationAddress());
				person.setDgree(member.getCollegeDegree());
				personService.save(person);
				BpCustRelation bpCustRelation=new BpCustRelation();
				bpCustRelation.setP2pCustId(id);
				bpCustRelation.setOfflineCusId(person.getId().longValue());
				bpCustRelation.setOfflineCustType("p_loan");
				bpCustRelationService.merge(bpCustRelation);
				jsonString="{success:true}";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String updatePersonInfo(){
		try{
			BpCustMember member = bpCustMemberService.get(id);
			if(null!=member){
				String personId=this.getRequest().getParameter("personId");
				if(null!=personId && !personId.equals("")){
					Person p=personService.getById(Integer.valueOf(personId));
					if(null!=p){
						if(null!=member.getTruename() && !"".equals(member.getTruename())){
							p.setName(member.getTruename());
						}
						if(null!=member.getSex() && !"".equals(member.getSex())){
							p.setSex(member.getSex());
						}
						if(null!=member.getCardcode() && !"".equals(member.getCardcode())){
							p.setCardnumber(member.getCardcode());
						}
						if(null!=member.getHomePhone() && !"".equals(member.getHomePhone())){
							p.setTelphone(member.getHomePhone());
						}
						if(null!=member.getTelphone() && !"".equals(member.getTelphone())){
							p.setCellphone(member.getTelphone());
						}
						if(null!=member.getFax() && !"".equals(member.getFax())){
							p.setFax(member.getFax());
						}
						if(null!=member.getPostCode() && !"".equals(member.getPostCode())){
							p.setPostcode(member.getPostCode());
						}
						if(null!=member.getRelationAddress() && !"".equals(member.getRelationAddress())){
							p.setPostaddress(member.getRelationAddress());
						}
						p.setCardtype(309);
						if(null!=member.getCollegeDegree() && !"".equals(member.getCollegeDegree())){
							p.setDgree(member.getCollegeDegree());
						}
						if(null!=member.getMarry() && !"".equals(member.getMarry())){
							if(member.getMarry()==0){
								member.setStrMarry("未婚");
							}else if(member.getMarry()==1){
								member.setStrMarry("已婚");
							}else if(member.getMarry()==2){
								member.setStrMarry("离异");
							}else if(member.getMarry()==3){
								member.setStrMarry("丧偶");
							}else{
								member.setStrMarry("");
							}
						}
						if(null!=member.getBirthday() && !"".equals(member.getBirthday())){
							p.setBirthday(member.getBirthday());
						}
						if(null!=member.getNation() && !"".equals(member.getNation())){
							p.setNationality(member.getNation());
						}	
						if(null!=member.getLiveProvice() && !"".equals(member.getLiveProvice())){
						p.setParenthukou(member.getLiveProvice().toString());
						}
						if(null!=member.getLiveCity() && !"".equals(member.getLiveCity())){
							p.setHukou(member.getLiveCity().toString());
						}
						if(null!=member.getNativePlaceCity() && !"".equals(member.getNativePlaceCity())){
							p.setHomeland(member.getNativePlaceCity());
						}
						if(null!=member.getHireCompanyname() && !"".equals(member.getHireCompanyname())){
							p.setCurrentcompany(member.getHireCompanyname());
						}
						if(null!=member.getHirePosition() && !"".equals(member.getHirePosition())){
							p.setJob(member.getHirePosition());
						}
						if(null!=member.getCollegename() && !"".equals(member.getCollegename())){
							p.setGraduationunversity(member.getCollegename());
						}
						if(null!=member.getHireStartyear() && !"".equals(member.getHireStartyear())){
							p.setJobstarttime(member.getHireStartyear());
						}
						if(null!=member.getWebshopStartYear() && !"".equals(member.getWebshopStartYear())){
							p.setWebstarttime(member.getWebshopStartYear());
						}
						if(null!=member.getBossStartYear() && !"".equals(member.getBossStartYear())){
							p.setBossstarttime(member.getBossStartYear());
						}
						if(null!=member.getTeacherStartYear() && !"".equals(member.getTeacherStartYear())){
							p.setTeacherStartYear(member.getTeacherStartYear());
						}
						if(null!=member.getTeacherAddress() && !"".equals(member.getTeacherAddress())){
							p.setTeacherAddress(member.getTeacherAddress());
						}
						if(null!=member.getTeacherCity() && !"".equals(member.getTeacherCity())){
							p.setTeacherCity(member.getTeacherCity());
						}
						if(null!=member.getTeacherCompanyName() && !"".equals(member.getTeacherCompanyName())){
							p.setTeacherCompanyName(member.getTeacherCompanyName());
						}
						if(null!=member.getTeacherCompanyPhone() && !"".equals(member.getTeacherCompanyPhone())){
							p.setTeacherCompanyPhone(member.getTeacherCompanyPhone());
						}
						if(null!=member.getTeacherEmail() && !"".equals(member.getTeacherEmail())){
							p.setTeacherEmail(member.getTeacherEmail());
						}
						if(null!=member.getTeacherPosition() && !"".equals(member.getTeacherPosition())){
							p.setTeacherPosition(member.getTeacherPosition());
						}
						if(null!=member.getTeacherMonthlyIncome() && !"".equals(member.getTeacherMonthlyIncome())){
							p.setTeacherMonthlyIncome(member.getTeacherMonthlyIncome());
						}
						//网店信息注入
						if(null!=member.getWebshopMonthlyIncome() && !"".equals(member.getWebshopMonthlyIncome())){
							p.setWebshopMonthlyIncome(member.getWebshopMonthlyIncome());
						}
						if(null!=member.getWebshopAddress() && !"".equals(member.getWebshopAddress())){
							p.setWebshopAddress(member.getWebshopAddress());
						}
						if(null!=member.getWebshopCity() && !"".equals(member.getWebshopCity())){
							p.setWebshopCity(member.getWebshopCity());
						}
						if(null!=member.getWebshopEmail() && !"".equals(member.getWebshopEmail())){
							p.setWebshopEmail(member.getWebshopEmail());
						}
						if(null!=member.getWebshopName() && !"".equals(member.getWebshopName())){
							p.setWebshopName(member.getWebshopName());
						}
						if(null!=member.getWebshopPhone() && !"".equals(member.getWebshopPhone())){
							p.setWebshopPhone(member.getWebshopPhone());
						}
						if(null!=member.getWebshopProvince() && !"".equals(member.getWebshopProvince())){
							p.setWebshopProvince(member.getWebshopProvince());
						}
						if(null!=member.getWebshopStartYear() && !"".equals(member.getWebshopStartYear())){
							p.setWebshopStartYear(member.getWebshopStartYear());
						}
						if(null!=member.getHireMonthlyincome() && !"".equals(member.getHireMonthlyincome())){
							p.setJobincome(Double.valueOf(member.getHireMonthlyincome().toString()));
							p.setJobYearincom(Double.valueOf(member.getHireMonthlyincome().multiply(new BigDecimal(12)).toString()));
						}
						if(null!=member.getHireAddress() && !"".equals(member.getHireAddress())){
							p.setUnitaddress(member.getHireAddress());
						}
						if(null!=member.getHireCompanytype() && !"".equals(member.getHireCompanytype())){
							p.setUnitproperties(member.getHireCompanytype());
						}
						if(null!=member.getMarry() && !"".equals(member.getMarry())){
							p.setMarry(member.getMarry());
						}
						if(null!=member.getHireCompanyphone() && !"".equals(member.getHireCompanyphone())){
							p.setUnitphone(member.getHireCompanyphone());
						}
						if(null!=member.getEmail() && !"".equals(member.getEmail())){
							p.setSelfemail(member.getEmail());
						}
						if(null!=member.getHavechildren() && !"".equals(member.getHavechildren())){
							p.setChildrenCount(member.getHavechildren().toString());
						}
						if(null!=member.getHireCompanycategory() && !"".equals(member.getHireCompanycategory())){
							p.setHangyeType(member.getHireCompanycategory());
							if(null!=areaDicService.getById(Integer.valueOf(member.getHireCompanycategory()))){
								p.setHangyeName(areaDicService.getById(Integer.valueOf(member.getHireCompanycategory())).getText());
							}
						}
						if(null!=member.getHireCompanysize() && !"".equals(member.getHireCompanysize())){
							p.setCompanyScale(String.valueOf(member.getHireCompanysize()));
						}
						if(null!=member.getCollegeYear() && !"".equals(member.getCollegeYear())){
							p.setCollegeYear(member.getCollegeYear());
						}
						if(null!=member.getHireEmail() && !"".equals(member.getHireEmail())){
							p.setHireEmail(member.getHireEmail());
						}
						if(null!=member.getHireCity() && !"".equals(member.getHireCity())){
							p.setHireCity(member.getHireCity());
						}
						if(null!=member.getHavehouse() && !"".equals(member.getHavehouse())){
							p.setHavehouse(Boolean.valueOf(member.getHavehouse().toString()));
						}
						if(null!=member.getHavehouseloan() && !"".equals(member.getHavehouseloan())){
							p.setHavehouseloan(Boolean.valueOf(member.getHavehouseloan().toString()));
						}
						if(null!=member.getHavecar() && !"".equals(member.getHavecar())){
							p.setHavecar(Boolean.valueOf(member.getHavecar().toString()));
						}
						if(null!=member.getHavecarloan() && !"".equals(member.getHavecarloan())){
							p.setHavecarloan(Boolean.valueOf(member.getHavecarloan().toString()));
						}
						if(null!=member.getLiveCity() && !"".equals(member.getLiveCity())){
							p.setLiveCity(member.getLiveCity());
						}
						personService.merge(p);
						//添加紧急联系人，家庭联系人,工作联系人，配偶信息
						addRersonRelation(member,p);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 添加紧急联系人，家庭联系人
	 * @param member
	 */
	public void addRersonRelation(BpCustMember member,Person p){
		//添加配偶信息
		if(member.getRelDirType()!=null&&member.getRelDirType()==954){
			QueryFilter filter = new QueryFilter(this.getRequest());
			filter.addFilter("Q_personId_IN_EQ", p.getId());
			filter.addFilter("Q_name_S_EQ", member.getRelDirName());
			List<Spouse> list=spouseService.getAll(filter);
			if(list.size()<=0){
				Spouse sp = new Spouse();
				sp.setName(member.getRelDirName());
				sp.setPersonId(p.getId());
				spouseService.save(sp);
			}
		}
		//家庭联系人
		if(member.getRelDirName()!=null){
			List<PersonRelation> list=personRelationService.getByNameAndFlag(member.getRelDirName(), "0", p.getId());
			if(list.size()>0){
				PersonRelation pr=list.get(0);
				pr.setRelationName(member.getRelDirName());
				if(member.getRelDirType()!=null){
					pr.setRelationShip(member.getRelDirType());
				}
				if(member.getRelDirPhone()!=null){
					pr.setRelationPhone(member.getRelDirPhone());
				}
				personRelationService.merge(pr);
			}else{
				PersonRelation relation = new PersonRelation();
				relation.setFlag("0");
				relation.setRelationName(member.getRelDirName());
				if(member.getRelDirType()!=null){
					relation.setRelationShip(member.getRelDirType());
				}
				if(member.getRelDirPhone()!=null){
					relation.setRelationPhone(member.getRelDirPhone());
				}
				relation.setPersonId(p.getId());
				personRelationService.save(relation);
			}
		}
		//其他亲属
		if(member.getRelOtherName()!=null){
			List<PersonRelation> list=personRelationService.getByNameAndFlag(member.getRelOtherName(), "0", p.getId());
			if(list.size()>0){
				PersonRelation pr=list.get(0);
				pr.setRelationName(member.getRelOtherName());
				if(member.getRelOtherType()!=null){
					pr.setRelationShip(member.getRelOtherType());
				}
				if(member.getRelOtherPhone()!=null){
					pr.setRelationPhone(member.getRelOtherPhone());
				}
				personRelationService.merge(pr);
			}else{
				PersonRelation relation1 = new PersonRelation();
				relation1.setFlag("0");
				relation1.setRelationName(member.getRelOtherName());
				if(member.getRelOtherType()!=null){
					relation1.setRelationShip(member.getRelOtherType());
				}
				if(member.getRelOtherPhone()!=null){
					relation1.setRelationPhone(member.getRelOtherPhone());
				}
				relation1.setPersonId(p.getId());
				personRelationService.save(relation1);
			}
		}
		//紧急联系人
		if(member.getRelFriendName()!=null&&member.getRelFriendType()!=null&&member.getRelFriendType()==955){//同学
			List<PersonRelation> list=personRelationService.getByNameAndFlag(member.getRelFriendName(), "2", p.getId());
			if(list.size()>0){
				PersonRelation pr=list.get(0);
				pr.setRelationName(member.getRelFriendName());
				pr.setRelationShip(member.getRelFriendType());
				if(member.getRelFriendPhone()!=null){
					pr.setRelationPhone(member.getRelFriendPhone());
				}
				personRelationService.merge(pr);
			}else{
				PersonRelation friendRelation = new PersonRelation();
				friendRelation.setFlag("2");
				if(member.getRelFriendName()!=null){
					friendRelation.setRelationName(member.getRelFriendName());
				}
				friendRelation.setRelationShip(member.getRelFriendType());
				if(member.getRelFriendPhone()!=null){
					friendRelation.setRelationPhone(member.getRelFriendPhone());
				}
				friendRelation.setPersonId(p.getId());
				personRelationService.save(friendRelation);
			}
		}
		if(member.getRelFriendName()!=null&&member.getRelFriendType()!=null&&member.getRelFriendType()==576){//朋友
			List<PersonRelation> list=personRelationService.getByNameAndFlag(member.getRelFriendName(), "2", p.getId());
			if(list.size()>0){
				PersonRelation pr=list.get(0);
				pr.setRelationName(member.getRelFriendName());
				pr.setRelationShip(member.getRelFriendType());
				if(member.getRelFriendPhone()!=null){
					pr.setRelationPhone(member.getRelFriendPhone());
				}
				personRelationService.merge(pr);
			}else{
				PersonRelation friendRelation = new PersonRelation();
				friendRelation.setFlag("2");
				if(member.getRelFriendName()!=null){
					friendRelation.setRelationName(member.getRelFriendName());
				}
				friendRelation.setRelationShip(member.getRelFriendType());
				if(member.getRelFriendPhone()!=null){
					friendRelation.setRelationPhone(member.getRelFriendPhone());
				}
				friendRelation.setPersonId(p.getId());
				personRelationService.save(friendRelation);
			}
		}
		//工作证明人
		if(member.getRelFriendName()!=null&&member.getRelFriendType()!=null&&member.getRelFriendType()==577){//同事
			List<PersonRelation> list=personRelationService.getByNameAndFlag(member.getRelFriendName(), "1", p.getId());
			if(list.size()>0){
				PersonRelation pr=list.get(0);
				pr.setRelationName(member.getRelFriendName());
				pr.setRelationShip(member.getRelFriendType());
				if(member.getRelFriendPhone()!=null){
					pr.setRelationPhone(member.getRelFriendPhone());
				}
				personRelationService.merge(pr);
			}else{
				PersonRelation friendRelation = new PersonRelation();
				friendRelation.setFlag("1");
				if(member.getRelFriendName()!=null){
					friendRelation.setRelationName(member.getRelFriendName());
				}
				friendRelation.setRelationShip(member.getRelFriendType());
				if(member.getRelFriendPhone()!=null){
					friendRelation.setRelationPhone(member.getRelFriendPhone());
				}
				friendRelation.setPersonId(p.getId());
				personRelationService.save(friendRelation);
			}
		}
	}
	
	/**
	 * 判断用户是否存在
	 */
	public String isExist() {
		String loginName=this.getRequest().getParameter("loginName");
		// 将数据转成JSON格式
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		BpCustMember member = bpCustMemberService.isExist(loginName);
		if (member != null) {
			sb.append("{\"success\":true,\"errMsg\":");
			sb.append(gson.toJson("债权人P2P账号存在"));
			sb.append(",\"result\":1");
			sb.append("}");
		} else {
			sb.append("{\"success\":false,\"errMsg\":");
			sb.append(gson.toJson("债权人P2P账号不存在"));
			sb.append(",\"result\":0");
			sb.append("}");
		}
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 接口改造之后保存招标计划的时候必须保证借款用户开通了第三方账户
	 * @return
	 */
	public String chechIsOpenThird(){
		String customerId=this.getRequest().getParameter("customerId");
		String customerType=this.getRequest().getParameter("customerType");
		if(customerType.equals("p_loan") || customerType.equals("b_loan")){//直投标
			BpCustRelation bpCustRelation=bpCustRelationService.getByLoanTypeAndId(customerType,Long.valueOf(customerId));
			if(null==bpCustRelation){
				setJsonString("{success:false,msg:'借款人没有开通P2P账户!!!'}");
			}else{
				BpCustMember bpCustMember =bpCustMemberService.get(bpCustRelation.getP2pCustId());
				if(null!=bpCustMember){
					if(null!=bpCustMember.getThirdPayFlagId() && !"".equals(bpCustMember.getThirdPayFlagId())){
						setJsonString("{success:true,msg:'借款人已开通第三方!!!'}");
					}else{
						setJsonString("{success:false,msg:'借款人尚未开通第三方账户!!!'}");
					}
				}else{
					setJsonString("{success:false,msg:'借款人的P2P账户不存在!!!'}");
				}
			}
		}else if(customerType.equals("person") || customerType.equals("enterprise")){//债转标
			if(customerType.equals("person")){
				customerType = "p_cooperation";
			}else{
				customerType = "b_cooperation";
			}
			BpCustRelation bpCustRelation=bpCustRelationService.getByLoanTypeAndId(customerType,Long.valueOf(customerId));
			if(null==bpCustRelation){
				setJsonString("{success:false,msg:'债权持有人没有开通P2P账户!!!'}");
			}else{
				BpCustMember bpCustMember =bpCustMemberService.get(bpCustRelation.getP2pCustId());
				if(null!=bpCustMember){
					if(null!=bpCustMember.getThirdPayFlagId() && !"".equals(bpCustMember.getThirdPayFlagId())){
						setJsonString("{success:true,msg:'债权持有人已开通第三方!!!'}");
					}else{
						setJsonString("{success:false,msg:'债权持有人尚未开通第三方账户!!!'}");
					}
				}else{
					setJsonString("{success:false,msg:'债权持有人的P2P账户不存在!!!'}");
				}
			}
		}
		return SUCCESS;
	}
	public String changeVipState(){
		String[] ids = this.getRequest().getParameterValues("ids");
		String state = this.getRequest().getParameter("state");
		if(ids!=null&&ids.length>0){
			for(String id:ids){
				BpCustMember member = bpCustMemberService.get(Long.valueOf(id));
				member.setIsVip(new Short(state));
				bpCustMemberService.merge(member);
			}
		}
		return SUCCESS;
	}
	public String changeMark(){
		String[] ids = this.getRequest().getParameterValues("ids");
		String mark = this.getRequest().getParameter("mark");
		if(ids!=null&&ids.length>0){
			for(String id:ids){
				BpCustMember member = bpCustMemberService.get(Long.valueOf(id));
				member.setDepartmentRecommend(mark);
				bpCustMemberService.merge(member);
			}
		}
		return SUCCESS;
	}
	
	public String getRecommandPerson() {
		return recommandPerson;
	}

	public void setRecommandPerson(String recommandPerson) {
		this.recommandPerson = recommandPerson;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BpCustMember getBpCustMember() {
		return bpCustMember;
	}

	public void setBpCustMember(BpCustMember bpCustMember) {
		this.bpCustMember = bpCustMember;
	}
	
	public String getPlainpassword() {
		return plainpassword;
	}

	public void setPlainpassword(String plainpassword) {
		this.plainpassword = plainpassword;
	}

	/**
	 *修改客户邀请码
	 *
	 * @auther: XinFang
	 * @date: 2018/7/2 10:05
	 */
	public  String changeRecommand(){
		String ids = this.getRequest().getParameter("id");
		String recommandPerson = this.getRequest().getParameter("recommandPerson");
		String[] split = ids.split(",");
		bpCustMemberService.changeRecommand(split,recommandPerson);
		setJsonString("{success:true,msg:'修改成功'}");


		return  SUCCESS;
	}

	/**
	 *判断邀请码是否存在
	 *
	 * @auther: XinFang
	 * @date: 2018/7/2 11:09
	 */

	public  String findByPlainpassword(){
		String recommandPerson = this.getRequest().getParameter("recommandPerson");
		BpCustMember bpCustMember = bpCustMemberService.isRecommandPerson(recommandPerson);
		if (bpCustMember != null){
		setJsonString("{success:true}");
		}else {
		setJsonString("{success:false,msg:'邀请码不存在,请检查'}");
		}

		return SUCCESS;
	}

	/**
	 *活动推荐查询
	 *
	 * @auther: XinFang
	 * @date: 2018/7/5 14:56
	 */
	public String listActicity(){
		String loginname = this.getRequest().getParameter("Q_loginname_S_LK");
		String plainpassword = this.getRequest().getParameter("Q_recommandPerson_S_LK");
		String truename = this.getRequest().getParameter("Q_truename_S_LK");
		String yearThan = this.getRequest().getParameter("yearThan");
		String sumThird = this.getRequest().getParameter("sumThird");
		String sumSecond = this.getRequest().getParameter("sumSecond");
		String sumFirst = this.getRequest().getParameter("sumFirst");
		Gson gson = new Gson();
		List<BpCustMember> bpCustMember = bpCustMemberService.listActicity(loginname,plainpassword,truename,yearThan
		,sumThird,sumSecond,sumFirst);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(bpCustMember.size()).
				append(",result:");
		buff.append(gson.toJson(bpCustMember));
		buff.append("}");
		jsonString=buff.toString();

		return  SUCCESS;
	}

	/**
	 *
	 * 注册用户地区分布与男女比例
	 * @auther: XinFang
	 * @date: 2018/8/2 15:15
	 */
	public  String AddressAndSex(){
		String startDate = this.getRequest().getParameter("startDate");
		String endDate = this.getRequest().getParameter("endDate");
		String type = this.getRequest().getParameter("type");
		Gson gson = new Gson();
		List<BpCustMember> list = bpCustMemberService.getAddressAndSex(startDate,endDate);

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).
				append(",result:");
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString=buff.toString();

		if (StringUtils.isNotBlank(type)){

			String[] tableHeader = { "序号","类别", "总数","比例"};
			String[] fields = {"relationAddress","sex","totalMoney"};
			try {
				ExportExcel.export(tableHeader, fields, list,"地区分布与年龄统计", BpCustMember.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return  SUCCESS;
	}

	/**
	 *
	 * 查询注册用户年龄信息
	 * @auther: XinFang
	 * @date: 2018/8/2 16:57
	 */
	public  String searchAge(){
		String startDate = this.getRequest().getParameter("startDate");
		String endDate = this.getRequest().getParameter("endDate");

		List<Object[]> list = bpCustMemberService.getAge(startDate,endDate);

		Gson gson = new Gson();
		String s = gson.toJson(list);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).
				append(",result:");
		buff.append(gson.toJson(list.get(0)));
		buff.append("}");
		setJsonString(buff.toString());
		System.out.println(buff);
		return SUCCESS;
	}

	/**
	 *员工查询邀请详情
	 *
	 * @auther: XinFang
	 * @date: 2018/8/13 9:58
	 */
    public String searchInvites() {

        String startDate = getRequest().getParameter("startDate");
        String endDate = getRequest().getParameter("endDate");
		String name = getRequest().getParameter("Q_loginname_S_LK");
		String type = getRequest().getParameter("type");
		String fullname = getRequest().getParameter("fullname");

		String userId = getRequest().getParameter("userId");
        AppUser users = (AppUser) getSession().getAttribute("users");
        List<PlBidInfo> list = new ArrayList<>();
        if(StringUtils.isNotBlank(userId)){
				list = bpCustMemberService.searchInvitesById(Long.valueOf(userId),name, startDate, endDate);
		}else {
				list = bpCustMemberService.searchInvitesById(users.getUserId(),name, startDate, endDate);
		}

       //邀请详情生成excle
		if (StringUtils.isNotBlank(type) && type.equals("export")){
			String [] tableHeader = {"序号","用户名","姓名","手机号码","标的名称","标的期限","投资金额","投资时间"};
			String[] fields = {"userName","truename","telphone","bidName","newInvestPersonName","userMoney","bidtime"};
			StringBuilder sb = new StringBuilder();
			if (StringUtils.isNotBlank(fullname)){
				sb.append(fullname);
			}else {
				sb.append(users.getFullname());
			}
			if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
				sb.append(startDate+"至"+endDate);
			}else {
				sb.append(DateUtil.getFirstDay()+"至"+DateUtil.getLastDay());
			}
			sb.append("业绩详情");
			try {
				ExportExcel.export(tableHeader,fields,list,sb.toString(),PlBidInfo.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}


        StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        buff.append(gson.toJson(list));
        buff.append("}");

        jsonString = buff.toString();

        return SUCCESS;
	}

	/**
	 *被邀请人投资详情记录
	 * 
	 * @auther: XinFang
	 * @date: 2018/8/13 16:52
	 */
	public  String InvestmentDetails(){

		String id = getRequest().getParameter("id");
		String startDate = getRequest().getParameter("startDate");
		String endDate = getRequest().getParameter("endDate");

		List<PlBidInfo> list =  bpCustMemberService.getInvestmentInfo(Long.valueOf(id),startDate,endDate);

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();


		return  SUCCESS;
	}


}