package com.zhiwei.credit.action.creditFlow.financingAgency.persion;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpBadCredit;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionOrProService;
import com.zhiwei.credit.service.creditFlow.smallLoan.supervise.SlSuperviseRecordService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.system.DictionaryService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpPersionOrProAction extends BaseAction{
	@Resource
	private BpPersionOrProService bpPersionOrProService;
	@Resource
	private PlBidPlanService plBidPlanService;
	@Resource
	private PlMmObligatoryRightChildrenService plMmObligatoryRightChildrenService;
	@Resource
	private SlSuperviseRecordService slSuperviseRecordService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private BpCustRelationService bpCustRelationService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private PersonService personService;
	private BpPersionOrPro bpPersionOrPro;
	
	private Long porProId;

	public Long getPorProId() {
		return porProId;
	}

	public void setPorProId(Long porProId) {
		this.porProId = porProId;
	}

	public BpPersionOrPro getBpPersionOrPro() {
		return bpPersionOrPro;
	}

	public void setBpPersionOrPro(BpPersionOrPro bpPersionOrPro) {
		this.bpPersionOrPro = bpPersionOrPro;
	}
	/**
	 * 统计发标情况
	 */
	@SuppressWarnings("null")
	public String listPublish() {

		QueryFilter filter = new QueryFilter(getRequest());
		filter.addSorted("proNumber", "DESC");
		List<BpPersionOrPro> list = bpPersionOrProService.getAll(filter);
		List<BpPersionOrPro> listCurr = new ArrayList<BpPersionOrPro>();
		for (BpPersionOrPro pack : list) {
			// 计算打包项目的剩余金额
			pack = bpPersionOrProService.residueMoneyMeth(pack);
			listCurr.add(pack);
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer();
		serializer.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"createTime", "updateTime","loanStarTime", "loanEndTime"});
		buff.append(serializer.exclude(new String[] { "class" }).serialize(
				listCurr));
		buff.append("}");

		jsonString = buff.toString();
		return SUCCESS;
	}
	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		filter.addSorted("createTime", "DESC");
		filter.addSorted("proNumber", "DESC");
		List<BpPersionOrPro> list= bpPersionOrProService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
			.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer();
		serializer.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"bidTime",
			"createTime", "updateTime","loanStarTime","loanEndTime"});
		buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
		buff.append("}");
				
		jsonString=buff.toString();
		
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
				bpPersionOrProService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 获取剩余招标金额 招标人数等信息
	 * 
	 * @return
	 */
	public String getBidInfo() {
		BpPersionOrPro bpPersionOrPro=bpPersionOrProService.get(porProId);

		bpPersionOrPro = bpPersionOrProService.residueMoneyMeth(bpPersionOrPro);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer();
		serializer.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"createTime", "updateTime","loanStarTime", "loanEndTime"});
		sb.append(serializer.exclude(new String[] { "class"}).serialize(bpPersionOrPro));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		BpPersionOrPro bpPersionOrPro=bpPersionOrProService.get(porProId);
		if(null == bpPersionOrPro.getDisclosureCreateDate() || "".equals(bpPersionOrPro.getDisclosureCreateDate())){
			bpPersionOrPro.setDisclosureCreateDate(new Date());
		}
		  Long personId=bpPersionOrPro.getPersionId();
		  Person person=personService.getById(Integer.valueOf(personId.toString()));
		//查询该表时如果用户名没有维护，就查询借款人的loginName进行维护下，为了解决公示信息维护没有用户名的问题
		if(null==bpPersionOrPro.getUserName() || "".equals(bpPersionOrPro.getUserName())){
		    BpCustRelation relation=bpCustRelationService.getByLoanTypeAndId("p_loan", personId);
		    if(null!=relation && !"".equals(relation)){
			    BpCustMember member=bpCustMemberService.get(relation.getP2pCustId());
			    bpPersionOrPro.setUserName(member.getLoginname());
		       }
		     }
		if(null==bpPersionOrPro.getAge()||"".equals(bpPersionOrPro.getAge())){
			bpPersionOrPro.setAge(person.getAge());
		}
		if(null==bpPersionOrPro.getSex()||"".equals(bpPersionOrPro.getSex())){
			if(null != person.getSex()){
				bpPersionOrPro.setSex(dictionaryService.get(Long.valueOf(person.getSex())).getItemValue());//性别
			}
		}
		if(null==bpPersionOrPro.getMarriage()||"".equals(bpPersionOrPro.getMarriage())){
			if(null!=person.getMarry() && !"".equals(person.getMarry())){
			Dictionary dictionary2=dictionaryService.get(Long.valueOf(person.getMarry()));
			if(null!=dictionary2){
				bpPersionOrPro.setMarriage(dictionary2.getItemValue());//婚姻
			}
		 }
		}
		if(null==bpPersionOrPro.getEducation()||"".equals(bpPersionOrPro.getEducation())){
			if(null!=person.getDgree() && !"".equals(person.getDgree())){
			Dictionary dictionary1=dictionaryService.get(Long.valueOf(person.getDgree()));
			if(null!=dictionary1){
				bpPersionOrPro.setEducation(dictionary1.getItemValue());//学历
			}
		 }
		}
		if(null==bpPersionOrPro.getMonthIncome()||"".equals(bpPersionOrPro.getMonthIncome())){
			if(person.getJobincome()!=null){//月收入
				bpPersionOrPro.setMonthIncome(new BigDecimal(person.getJobincome().toString()));
			}
		}
		if(null==bpPersionOrPro.getAddress()||"".equals(bpPersionOrPro.getAddress())){
			bpPersionOrPro.setAddress(person.getFamilyaddress());//现居住地
		}
		if(null==bpPersionOrPro.getWorkTime()||"".equals(bpPersionOrPro.getWorkTime())){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			if(person.getJobstarttime()==null||"".equals(person.getJobstarttime())){
				bpPersionOrPro.setWorkTime(null);//工作时间
			}else{
				bpPersionOrPro.setWorkTime(format.format(person.getJobstarttime()));//工作时间
			}
		}
		if(null==bpPersionOrPro.getWorkCity()||"".equals(bpPersionOrPro.getWorkCity())){
			bpPersionOrPro.setWorkCity(person.getUnitaddress());//工作城市
		}
		if(null==bpPersionOrPro.getCompanyIndustry()||"".equals(bpPersionOrPro.getCompanyIndustry())){
			bpPersionOrPro.setCompanyIndustry(person.getHangyeName());//行业
		}
		if(null==bpPersionOrPro.getCompanyScale()||"".equals(bpPersionOrPro.getCompanyScale())){
			if(null !=person.getCompanyScale()&&!"".equals(person.getCompanyScale()) && !"null".equals(person.getCompanyScale())){
				Dictionary dictionary=dictionaryService.get(Long.valueOf(person.getCompanyScale()));
				bpPersionOrPro.setCompanyScale(dictionary.getItemValue());//公司规模
			}
		}
		if(null==bpPersionOrPro.getPosition()||"".equals(bpPersionOrPro.getPosition())){
			if(null!=person.getJob()){
				Dictionary dictionary3=dictionaryService.get(Long.valueOf(person.getJob()));
				if(null!=dictionary3){
					bpPersionOrPro.setPosition(dictionary3.getItemValue());//职务
				}
			}
		}
		  bpPersionOrProService.merge(bpPersionOrPro);
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer( "createDate","updateDate","disclosureCreateDate","disclosureUpdateDate") ;
		sb.append(serializer.exclude(new String[] { "class" }).serialize(bpPersionOrPro));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpPersionOrPro.getPorProId()==null){
			bpPersionOrProService.save(bpPersionOrPro);
		}else{
			BpPersionOrPro orgBpPersionOrPro=bpPersionOrProService.get(bpPersionOrPro.getPorProId());
			try{
				BeanUtil.copyNotNullProperties(orgBpPersionOrPro, bpPersionOrPro);
				bpPersionOrProService.save(orgBpPersionOrPro);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 显示个人展期债权表列表
	 */
	public String bpSuperviseList(){
		PagingBean pb = new PagingBean(start, limit);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
	    List<BpPersionOrPro> listS=bpPersionOrProService.bpPersionOrProList(pb, getRequest());
	    Long scount=bpPersionOrProService.bpPersionOrProCount(getRequest());
		     buff=buff.append(scount).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			 buff.append(gson.toJson(listS));
			 buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String savePlMmObligatoryRightChildren(){
		//BpBusinessOrPro的id
		String borProId=this.getRequest().getParameter("borProId");
		//BpPersionOrPro的id
		String porProId=this.getRequest().getParameter("porProId");
		//Small表id
		String sprojectId=this.getRequest().getParameter("sprojectId");
		//BpFundProject的id
		String fundProjectId=this.getRequest().getParameter("fundProjectId");
		//SlSuperviseRecord的id
		String slSuperviseRecordId=this.getRequest().getParameter("projectId");
		//如果porProId不为空，则为个人债权标
		if(null!=porProId && !"".equals(porProId)){
			List<PlBidPlan> list=plBidPlanService.listByTypeId( "P_Or", Long.valueOf(porProId));
			if(null!=list && list.size()>0){
				PlBidPlan plan=list.get(0);
				//如果borProId不为空，则为企业债权标，传borProId过去
					plMmObligatoryRightChildrenService.createOblSuperviseRightChildren(plan,Long.valueOf(slSuperviseRecordId),Long.valueOf(porProId));
			}
		}
		//如果borProId不为空，则为企业债权标
		if(null!=borProId && !"".equals(borProId)){
			List<PlBidPlan> list=plBidPlanService.listByTypeId( "B_Or", Long.valueOf(borProId));
			if(null!=list && list.size()>0){
				PlBidPlan plan=list.get(0);
					plMmObligatoryRightChildrenService.createOblSuperviseRightChildren(plan,Long.valueOf(slSuperviseRecordId),Long.valueOf(borProId));
			
			}
		}
		SlSuperviseRecord slSuperviseRecord=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
		//展期债权进库后将展期的记录的isInRights置为1
		slSuperviseRecord.setIsInRights(Short.valueOf("1"));
		slSuperviseRecordService.merge(slSuperviseRecord);
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
