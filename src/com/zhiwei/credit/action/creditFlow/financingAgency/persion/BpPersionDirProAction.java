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
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionDirProService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.system.DictionaryService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class BpPersionDirProAction extends BaseAction{
	@Resource
	private BpPersionDirProService bpPersionDirProService;
	@Resource
	private BpCustRelationService bpCustRelationService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private PersonService personService;
	private BpPersionDirPro bpPersionDirPro;
	
	private Long pdirProId;

	public Long getPdirProId() {
		return pdirProId;
	}

	public void setPdirProId(Long pdirProId) {
		this.pdirProId = pdirProId;
	}

	public BpPersionDirPro getBpPersionDirPro() {
		return bpPersionDirPro;
	}

	public void setBpPersionDirPro(BpPersionDirPro bpPersionDirPro) {
		this.bpPersionDirPro = bpPersionDirPro;
	}
	
	/**
	 * 统计发标情况
	 */
	@SuppressWarnings("null")
	public String listPublish() {

		QueryFilter filter = new QueryFilter(getRequest());
		filter.addSorted("proNumber", "DESC");
		List<BpPersionDirPro> list = bpPersionDirProService.getAll(filter);
		List<BpPersionDirPro> listCurr = new ArrayList<BpPersionDirPro>();
		for (BpPersionDirPro pack : list) {
			// 计算打包项目的剩余金额
			pack = bpPersionDirProService.residueMoneyMeth(pack);
			listCurr.add(pack);
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer("singleTime",
				"createTime","bidTime", "updateTime");
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
		List<BpPersionDirPro> list= bpPersionDirProService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer("loanStarTime",
		"createTime", "updateTime","loanEndTime","bidTime");
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
				bpPersionDirProService.remove(new Long(id));
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
		BpPersionDirPro bpPersionDirPro=bpPersionDirProService.get(pdirProId);

		bpPersionDirPro = bpPersionDirProService.residueMoneyMeth(bpPersionDirPro);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer(
				"singleTime","bidTime", "createTime",
				"updateTime");
		sb.append(serializer.exclude(
				new String[] { "class"}).serialize(
						bpPersionDirPro));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		BpPersionDirPro bpPersionDirPro=bpPersionDirProService.get(pdirProId);
		if(null == bpPersionDirPro.getDisclosureCreateDate() || "".equals(bpPersionDirPro.getDisclosureCreateDate())){
			bpPersionDirPro.setDisclosureCreateDate(new Date());
		}
		  Long personId=bpPersionDirPro.getPersionId();
		  Person person=personService.getById(Integer.valueOf(personId.toString()));
		//查询该表时如果用户名没有维护，就查询借款人的loginName进行维护下，为了解决公示信息维护没有用户名的问题
		if(null==bpPersionDirPro.getUserName() || "".equals(bpPersionDirPro.getUserName())){
		    BpCustRelation relation=bpCustRelationService.getByLoanTypeAndId("p_loan", personId);
		    if(null!=relation && !"".equals(relation)){
			    BpCustMember member=bpCustMemberService.get(relation.getP2pCustId());
			    bpPersionDirPro.setUserName(member.getLoginname());
		       }
		     }
		if(null==bpPersionDirPro.getAge()|| "".equals(bpPersionDirPro.getAge())){
			bpPersionDirPro.setAge(person.getAge());
		}
		if(null==bpPersionDirPro.getSex()|| "".equals(bpPersionDirPro.getSex())){
			if(null != person.getSex()){
				bpPersionDirPro.setSex(dictionaryService.get(Long.valueOf(person.getSex())).getItemValue());//性别
			}
		}
		if(null==bpPersionDirPro.getMarriage()|| "".equals(bpPersionDirPro.getMarriage())){
			if(null!=person.getMarry() && !"".equals(person.getMarry())){
			Dictionary dictionary2=dictionaryService.get(Long.valueOf(person.getMarry()));
			if(null!=dictionary2){
				bpPersionDirPro.setMarriage(dictionary2.getItemValue());//婚姻
			}
			}
		}
		if(null==bpPersionDirPro.getEducation()|| "".equals(bpPersionDirPro.getEducation())){
			if(null!=person.getDgree() && !"".equals(person.getDgree())){
			Dictionary dictionary1=dictionaryService.get(Long.valueOf(person.getDgree()));
			if(null!=dictionary1){
				bpPersionDirPro.setEducation(dictionary1.getItemValue());//学历
			}
		 }
		}
		if(null==bpPersionDirPro.getMonthIncome()|| "".equals(bpPersionDirPro.getMonthIncome())){
			if(person.getJobincome()!=null){//月收入
				bpPersionDirPro.setMonthIncome(new BigDecimal(person.getJobincome().toString()));
			}
		}
		if(null==bpPersionDirPro.getAddress()|| "".equals(bpPersionDirPro.getAddress())){
			bpPersionDirPro.setAddress(person.getFamilyaddress());//现居住地
		}
		if(null==bpPersionDirPro.getWorkTime()|| "".equals(bpPersionDirPro.getWorkTime())){
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			if(person.getJobstarttime()==null||"".equals(person.getJobstarttime())){
				bpPersionDirPro.setWorkTime(null);//工作时间
			}else{
				bpPersionDirPro.setWorkTime(format.format(person.getJobstarttime()));//工作时间
			}
		}
		if(null==bpPersionDirPro.getWorkCity()|| "".equals(bpPersionDirPro.getWorkCity())){
			bpPersionDirPro.setWorkCity(person.getUnitaddress());//工作城市
		}
		if(null==bpPersionDirPro.getCompanyIndustry()|| "".equals(bpPersionDirPro.getCompanyIndustry())){
			bpPersionDirPro.setCompanyIndustry(person.getHangyeName());//行业
		}
		if(null==bpPersionDirPro.getCompanyScale()|| "".equals(bpPersionDirPro.getCompanyScale())){
			if(null !=person.getCompanyScale()&&!"".equals(person.getCompanyScale()) && !"null".equals(person.getCompanyScale())){
				Dictionary dictionary=dictionaryService.get(Long.valueOf(person.getCompanyScale()));
				bpPersionDirPro.setCompanyScale(dictionary.getItemValue());//公司规模
			}
		}
		if(null==bpPersionDirPro.getPosition() || "".equals(bpPersionDirPro.getPosition())){
			if(null!=person.getJob()){
				Dictionary dictionary3=dictionaryService.get(Long.valueOf(person.getJob()));
				if(null!=dictionary3){
					bpPersionDirPro.setPosition(dictionary3.getItemValue());//职务
				}
			}
		}
		  bpPersionDirProService.merge(bpPersionDirPro);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer( "createDate","updateDate","disclosureCreateDate","disclosureUpdateDate") ;
		sb.append(serializer.exclude(new String[] { "class" }).serialize(bpPersionDirPro));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpPersionDirPro.getPdirProId()==null){
			bpPersionDirProService.save(bpPersionDirPro);
		}else{
			BpPersionDirPro orgBpPersionDirPro=bpPersionDirProService.get(bpPersionDirPro.getPdirProId());
			try{
				BeanUtil.copyNotNullProperties(orgBpPersionDirPro, bpPersionDirPro);
				bpPersionDirProService.save(orgBpPersionDirPro);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
