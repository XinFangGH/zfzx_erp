package com.zhiwei.credit.action.creditFlow.creditAssignment.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObObligationInvestInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.project.ObObligationProject;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObObligationInvestInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.project.ObObligationProjectService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.system.OrganizationService;
/**
 * 
 * @author 
 *
 */
public class ObObligationProjectAction extends BaseAction{
	@Resource
	private ObObligationProjectService obObligationProjectService;
	@Resource
	private ObObligationInvestInfoService obObligationInvestInfoService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private SlFundIntentService slFundIntentService;
	private ObObligationProject obObligationProject;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ObObligationProject getObObligationProject() {
		return obObligationProject;
	}

	public void setObObligationProject(ObObligationProject obObligationProject) {
		this.obObligationProject = obObligationProject;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ObObligationProject> list= obObligationProjectService.getAll(filter);
		
		Type type=new TypeToken<List<ObObligationProject>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
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
				obObligationProjectService.remove(new Long(id));
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
		ObObligationProject obObligationProject=obObligationProjectService.get(id);
		List<ObObligationInvestInfo>  list =obObligationInvestInfoService.getInfoByobObligationProjectId(obObligationProject.getId(),"0");//保存金帆投资信息
		if(list!=null&&list.size()>0){
			ObObligationInvestInfo investInfo =list.get(0);
			BigDecimal mapping =obObligationProject.getProjectMoney().subtract(investInfo.getInvestMoney());
			obObligationProject.setMappingMoney(mapping);
			obObligationProject.setUnmappingMoney(investInfo.getInvestMoney());
			obObligationProject.setUnmappingQuotient(investInfo.getInvestQuotient());
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(obObligationProject));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(obObligationProject.getId()==null){
			obObligationProjectService.save(obObligationProject);
		}else{
			ObObligationProject orgObObligationProject=obObligationProjectService.get(obObligationProject.getId());
			try{
				BeanUtil.copyNotNullProperties(orgObObligationProject, obObligationProject);
				obObligationProjectService.save(orgObObligationProject);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	//查询债权产品的列表
	public String getList(){
		String projectNumber =this.getRequest().getParameter("projectNumber");
		String obligationName =this.getRequest().getParameter("obligationName");
		String obligationNumber =this.getRequest().getParameter("obligationNumber");
		String projectMoney =this.getRequest().getParameter("projectMoney");
		String payintentPeriod =this.getRequest().getParameter("payintentPeriod");
		String obligationState =this.getRequest().getParameter("obligationState");
		List<ObObligationProject> list =obObligationProjectService.getProjectList(projectNumber,obligationName,obligationNumber,projectMoney,payintentPeriod,obligationState);
		if(list!=null&&list.size()>0){
			for(ObObligationProject  temp :list){
				List<ObObligationInvestInfo>  listInfo=obObligationInvestInfoService.getInfoByobObligationProjectId(temp.getId(),"0");
				if(!listInfo.isEmpty()){
					ObObligationInvestInfo oi=listInfo.get(0);
					if(null!=oi){	
						temp.setUnmappingMoney(oi.getInvestMoney());//设置未匹配金额
						BigDecimal unmappingMoney=temp.getProjectMoney().subtract(oi.getInvestMoney());
						temp.setMappingMoney(unmappingMoney);//设置已匹配金额
						temp.setUnmappingQuotient(oi.getInvestQuotient());//设置未匹配份额
					}
				}
			}
		}
		Type type = new TypeToken<List<ObObligationProject>>() {}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list==null?0:list.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	//保存发布产品时产品信息
	public String saveObligationProject(){
		if(obObligationProject.getId()==null||"".equals(obObligationProject.getId())||"null".equals(obObligationProject.getId())){//新增债权产品
			obObligationProject.setProjectStatus(Integer.valueOf(1));
			obObligationProjectService.save(obObligationProject);
			if(obObligationProject.getId()!=null&&!"".equals(obObligationProject.getId())&&!"null".equals(obObligationProject.getId())){//表示债权产品已经保存成功
				Organization  org =organizationService.get(obObligationProject.getCompanyId());//查出当前是哪个公司
				ObObligationInvestInfo  investInfo =new ObObligationInvestInfo();//保存金帆投资信息
				investInfo.setObligationId(obObligationProject.getId());
				investInfo.setObligationName(obObligationProject.getObligationName());
				investInfo.setObligationAccrual(obObligationProject.getAccrual());
				investInfo.setCompanyId(obObligationProject.getCompanyId());
				investInfo.setInvestPersonName(org.getOrgName());
				investInfo.setInvestEndDate(obObligationProject.getIntentDate());
				investInfo.setInvestMoney(obObligationProject.getProjectMoney());
				investInfo.setInvestQuotient(Long.valueOf(obObligationProject.getTotalQuotient().toString()));
				investInfo.setInvestRate(new BigDecimal(100));
				investInfo.setInvestObligationStatus(Short.valueOf("1"));
				investInfo.setSystemInvest(Short.valueOf("0"));
				obObligationInvestInfoService.save(investInfo);
			}
			
		}else{//编辑债权产品
			ObObligationProject orgObObligationProject=obObligationProjectService.get(obObligationProject.getId());
			try{
				obObligationProject.setProjectStatus(Integer.valueOf(1));
				BeanUtil.copyNotNullProperties(orgObObligationProject, obObligationProject);
				obObligationProjectService.save(orgObObligationProject);
				Organization  org =organizationService.get(orgObObligationProject.getCompanyId());//查出当前是哪个公司
				List<ObObligationInvestInfo>  list =obObligationInvestInfoService.getInfoByobObligationProjectId(obObligationProject.getId(),"0");//保存金帆投资信息
				if(list!=null&&list.size()>0){
					ObObligationInvestInfo investInfo =list.get(0);;
					investInfo.setObligationId(obObligationProject.getId());
					investInfo.setObligationName(obObligationProject.getObligationName());
					investInfo.setObligationAccrual(obObligationProject.getAccrual());
					investInfo.setCompanyId(obObligationProject.getCompanyId());
					investInfo.setInvestPersonName(org.getOrgName());
					//investInfo.setInvestStartDate(obObligationProject.getStartDate());
					investInfo.setInvestEndDate(obObligationProject.getIntentDate());
					investInfo.setInvestMoney(obObligationProject.getProjectMoney());
					investInfo.setInvestQuotient(Long.valueOf(obObligationProject.getTotalQuotient().toString()));
					investInfo.setSystemInvest(Short.valueOf("0"));
					obObligationInvestInfoService.save(investInfo);
				}
				
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;	
	
	}
	//用来检查添加的债权产品是否能编辑和删除
	public String   checkEdit(){
		int  flag =0;
		List<ObObligationInvestInfo>  list =obObligationInvestInfoService.getInfoByobObligationProjectId(id,"1");
		if(list.size()>0){
			flag=1;
		}
		StringBuffer sb = new StringBuffer("{success:true,flag:");
		sb.append(flag);
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	public String getPayIntentPeriod(){
		try{
			String payintentPeriod=this.getRequest().getParameter("payintentPeriod");
			String payaccrualType=this.getRequest().getParameter("payaccrualType");
			String startDate=this.getRequest().getParameter("startDate");
			String intentDate=this.getRequest().getParameter("intentDate");
			
			StringBuffer buff = new StringBuffer("[");
			if(null!=payintentPeriod && !"".equals(payintentPeriod)){
				if(null!=payaccrualType && !"".equals(payaccrualType)){
					if(payaccrualType.equals("dayPay")){//按日付息
						Integer	period=DateUtil.getDaysBetweenDate(startDate,intentDate);//期限是按月写的 
						payintentPeriod=period.toString();
					}else if(payaccrualType.equals("seasonPay")){//按月付息
						Integer	period=Integer.valueOf(payintentPeriod)/3;//期限是按月填写的
						int remainMonths =Integer.valueOf(payintentPeriod)%3;//期限是按月填写的,这个是求余得出非整除剩余的月份	
						if(remainMonths!=0){
							period=period+1;
							payintentPeriod=period.toString();
						}else{
							payintentPeriod=period.toString();
						}
					}else if(payaccrualType.equals("yearPay")){//按年付息
						Integer	period=Integer.valueOf(payintentPeriod)/12;//期限是按月填写的
						int remainMonths =Integer.valueOf(payintentPeriod)%12;//期限是按月填写的,这个是求余得出非整除剩余的月份	
						if(remainMonths!=0){
							period=period+1;
							payintentPeriod=period.toString();
						}else{
							payintentPeriod=period.toString();
						}
					}
				}
				for (int i=0;i<Integer.valueOf(payintentPeriod)+1;i++) {
					buff.append("['").append((i)).append("','")
							.append((i)).append("'],");
				}
				if (Integer.valueOf(payintentPeriod) > 0) {
					buff.deleteCharAt(buff.length() - 1);
				}
			}
			buff.append("]");
			setJsonString(buff.toString());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询款项期数报错！"+e.getMessage());
		}
		
		return SUCCESS;
	}
	//用来保存债权产品匹配的投资人信息
	public String saveInvestMentPersonList(){
		try{
			String  msg="";
			BigDecimal compar =new BigDecimal(0);
			String investmentData =this.getRequest().getParameter("investmentData");
			String obligationId =this.getRequest().getParameter("obligationId");
			String slFundIntentList =this.getRequest().getParameter("slFundIntentList");//生成的款项
			AppUser user =ContextUtil.getCurrentUser();
			ObObligationProject obObligationProject =null;
			ObObligationInvestInfo  systemInvest =null;//默认查出当前债权产品的系统公司债权记录（）
			if(obligationId!=null&&!"".equals(obligationId)){
				obObligationProject =obObligationProjectService.get(Long.valueOf(obligationId));
				List<ObObligationInvestInfo>  list =obObligationInvestInfoService.getInfoByobObligationProjectId(Long.valueOf(obligationId),"0");
				if(list!=null&&list.size()>0){
					systemInvest =list.get(0);
				}
				if(investmentData!=null&&!"".equals(investmentData)){//准备保存投资人信息
					String[] investmentInfoArr = investmentData.split("@");
					for (int i = 0; i < investmentInfoArr.length; i++){
						String str = investmentInfoArr[i];
						
						JSONParser parser = new JSONParser(new StringReader(str));
						ObObligationInvestInfo bo = (ObObligationInvestInfo) JSONMapper.toJava(parser.nextValue(), ObObligationInvestInfo.class);
						if (bo.getId() == null) {
							bo.setCompanyId(obObligationProject.getCompanyId());
							bo.setObligationName(obObligationProject.getObligationName());
							bo.setCreatorId(user.getUserId());
							bo.setInvestObligationStatus(Short.valueOf("1"));
							bo.setSystemInvest(Short.valueOf("1"));
							bo.setObligationId(Long.valueOf(obligationId));
							obObligationInvestInfoService.save(bo);
						} else {
							ObObligationInvestInfo info =obObligationInvestInfoService.get(bo.getId());
							BeanUtil.copyNotNullProperties(info, bo);
							obObligationInvestInfoService.merge(info);
						}
					}
				}
				List<ObObligationInvestInfo>  listinvests =obObligationInvestInfoService.getInfoByobObligationProjectId(Long.valueOf(obligationId),"1");
				if(listinvests.size()>0&&listinvests!=null){
					BigDecimal totalInvest =new BigDecimal(0);
					Long Quotient =0l;
					BigDecimal rate =new BigDecimal(0);//默认公司债权还剩的比例
					for(ObObligationInvestInfo temp :listinvests){
						totalInvest=totalInvest.add(temp.getInvestMoney());
						Quotient=Quotient+temp.getInvestQuotient();
					}
					if(totalInvest.compareTo(compar)==0){
						
					}else if(totalInvest.compareTo(obObligationProject.getProjectMoney())==0||totalInvest.compareTo(obObligationProject.getProjectMoney())==1){
						systemInvest.setInvestMoney(new BigDecimal(0));
						systemInvest.setInvestQuotient(Long.valueOf("0"));
						systemInvest.setInvestRate(new BigDecimal(0));
						systemInvest.setInvestObligationStatus(Short.valueOf("2"));
						obObligationInvestInfoService.merge(systemInvest);
						obObligationProject.setObligationStatus(Short.valueOf("1"));//债权产品匹配成功后改变债权产品状态
						obObligationProjectService.merge(obObligationProject);
					}else if(totalInvest.compareTo(obObligationProject.getProjectMoney())==-1){//债权产品没有匹配完成
						BigDecimal avaible =obObligationProject.getProjectMoney().subtract(totalInvest);
						systemInvest.setInvestMoney(avaible);
						systemInvest.setInvestQuotient(Long.valueOf(obObligationProject.getTotalQuotient().toString())-Quotient);
						systemInvest.setInvestRate(avaible.divide(obObligationProject.getProjectMoney(),4,BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100)));
						obObligationInvestInfoService.merge(systemInvest);
					}
				}
				//移除没有对账的款项(没有回款的债权项目)
				if(listinvests.size()>0&&listinvests!=null){
					for(ObObligationInvestInfo temp :listinvests){
						if(temp.getFundIntentStatus()==0){
							List<SlFundIntent> lists =slFundIntentService.getListByTreeId(Long.valueOf(obligationId),temp.getInvestMentPersonId(),temp.getId());
							if(lists!=null&& list.size()>0){
								for (SlFundIntent s : lists) {
									slFundIntentService.remove(s);
								}
							}
						}
					}
				}
				//生成的款项保存
				if (null != slFundIntentList && !"".equals(slFundIntentList)) {
					String[] shareequityArr = slFundIntentList.split("@");
					for (int i = 0; i < shareequityArr.length; i++) {
						String str = shareequityArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
						if (null == SlFundIntent1.getFundIntentId()) {
							BigDecimal lin = new BigDecimal(0.00);
							if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
								SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
							} else {
								SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
							}
							Short isvalid = 0;
							SlFundIntent1.setCompanyId(obObligationProject.getCompanyId());
							SlFundIntent1.setAfterMoney(lin);
							SlFundIntent1.setIsValid(isvalid);
							SlFundIntent1.setIsCheck(Short.valueOf("0"));
							slFundIntentService.save(SlFundIntent1);
						} else {
							BigDecimal lin = new BigDecimal(0);
							if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
								SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
							} else {
								SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
							}
							SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
							if(slFundIntent2 != null) {
								if (slFundIntent2.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
									BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);
									SlFundIntent1.setIsCheck(Short.valueOf("0"));
									slFundIntentService.merge(slFundIntent2);
								}
							}
						}
					}
				}
				//给投资人购买债权，没有生成平台账户支出记录的投资人生成或者更新平台账户支出信息
				if(listinvests.size()>0&&listinvests!=null){
					Date currentDate =new Date();
					for(ObObligationInvestInfo temp :listinvests){
						if(temp.getFundIntentStatus()!=2&&temp.getInvestStartDate().compareTo(currentDate)<=0){//表示没有回款的债权
							obObligationInvestInfoService.checkSlFundQulid(temp);
						}
					}
				}
				msg="保存成功!";
			}else{
				msg="系统出错，请联系管理员。【债权表的主键Id丢失】";
			}
			StringBuffer sb = new StringBuffer("{success:true,msg:'");
			sb.append(msg);
			sb.append("'}");
			setJsonString(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("保存债权匹配投资人出错"+e.getMessage());
			String msg ="系统出错，请联系管理员。";
			StringBuffer sb = new StringBuffer("{success:false,msg:'");
			sb.append(msg);
			sb.append("'}");
			setJsonString(sb.toString());
		}
		return SUCCESS;
	}
	//关闭债权产品
	public String closeInvestProduct(){
		String obligationId =this.getRequest().getParameter("obligationId"); //获得债权表id
		ObObligationProject op=obObligationProjectService.get(Long.valueOf(obligationId));
		op.setObligationStatus(Short.valueOf("2"));//设置关闭状态
		op.setFactEndDate(new Date());//设置手动关闭时间
		obObligationProjectService.save(op);
		return SUCCESS;
	}
	//激活债权产品
	public String openInvestProduct(){
		String obligationId =this.getRequest().getParameter("obligationId"); //获得债权表id
		ObObligationProject op=obObligationProjectService.get(Long.valueOf(obligationId));
		op.setObligationStatus(Short.valueOf("0"));//设置激活状态
		op.setFactEndDate(null);//设置手动关闭时间
		obObligationProjectService.save(op);
		return SUCCESS;
	}
	//查询债权产品的列表
	public String getListInfo(){
		String projectName =this.getRequest().getParameter("projectName");
		String projectNumber =this.getRequest().getParameter("projectNumber");
		String payintentPeriod =this.getRequest().getParameter("payintentPeriod");
		List<ObObligationProject> list =obObligationProjectService.getProjectListInfo(projectName,projectNumber,payintentPeriod);
		if(!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				ObObligationProject op=list.get(i);
				List<ObObligationInvestInfo>  listInfo=obObligationInvestInfoService.getInfoByobObligationProjectId(op.getId(),"0");
				if(!listInfo.isEmpty()){
					ObObligationInvestInfo oi=listInfo.get(0);
					if(null!=oi){	
						op.setUnmappingMoney(oi.getInvestMoney());//设置未匹配金额
						BigDecimal unmappingMoney=op.getProjectMoney().subtract(oi.getInvestMoney());
						op.setMappingMoney(unmappingMoney);//设置已匹配金额
						op.setUnmappingQuotient(oi.getInvestQuotient());//设置未匹配份额
					}
				}
			}
		}
		Type type = new TypeToken<List<ObObligationProject>>() {}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list==null?0:list.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
}
