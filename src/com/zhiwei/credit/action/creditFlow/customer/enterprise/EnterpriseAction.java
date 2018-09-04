package com.zhiwei.credit.action.creditFlow.customer.enterprise;

import java.io.File;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.contract.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.GroupUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.core.util.ElementUtil;
import com.zhiwei.credit.core.util.FileHelper;
import com.zhiwei.credit.model.creditFlow.contract.DocumentTemplet;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseView;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.VEnterprisePerson;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonCar;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonHouse;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.VPersonDic;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.contract.DocumentTempletService;
import com.zhiwei.credit.service.creditFlow.contract.ProcreditContractService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.CsPersonCarService;
import com.zhiwei.credit.service.creditFlow.customer.person.CsPersonHouseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessDirProService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.FTPIoadFileService;
import com.zhiwei.credit.service.p2p.impl.FTPUploadFileimpl;
import com.zhiwei.credit.service.system.AppUserService;

import com.zhiwei.credit.service.system.FTPService;
import com.zqsign.ZqsignUtils;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author 
 *
 */
public class EnterpriseAction extends BaseAction{
	@Resource
	private AppUserService appUserService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private FileFormService fileFormService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private PersonService personService;
	@Resource
	private AreaDicService areaDicService;
	@Resource
	private CsPersonCarService csPersonCarService;
	@Resource
	private CsPersonHouseService csPersonHouseService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private BpCustRelationService bpCustRelationService;
	@Resource
	private DocumentTempletService documentTempletService;
	@Resource
	private BpBusinessDirProService bpBusinessDirProService;
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private PlBidPlanService plBidPlanService;
	@Resource
	private SignP2PElement signP2PElement;


	private Boolean isGrantedSeeAllEnterprises;
	private String enterprisename="";
	private String ownership=""; 
	private String shortname="";
	private String organizecode="";
	private String cciaa="";
	private String customerLevel="";
	private Enterprise enterprise;
	private Person person;
	private String extendname="";
	private File fileUpload;
	private String fileUploadFileName;
	public final int UPLOAD_SIZE = 1024 * 1024 * 10;
	private String listId ;
	private String blackReason;
	private Integer id;
	private String businessType;
	private Boolean isGrantedSeeAllBlackList;
	private String customerType;
	private String customerName="";
	private String cardNum="";
	private String customerId;
	private String query;
	private Boolean isAll;
	
	
	//查询客户旗下公司信息
	public void getEnterByProject(){
		String legalpersonid = getRequest().getParameter("legalpersonid");
		if(null!=legalpersonid && !"".equals(legalpersonid)){
			enterpriseService.getEnterByProject(Integer.parseInt(legalpersonid));
		}
	}
	
	/**
	 * ajax查询企业信息
	 */
	public String getList(){
		HttpServletRequest request = getRequest();
		QueryFilter filter = new QueryFilter(request);
		PagingBean pb = filter.getPagingBean();
		String enterId = request.getParameter("enterId");
		if(enterId==null||enterId.equals("")){
			return SUCCESS;
		}
		Enterprise enterprise = enterpriseService.getById(Integer.parseInt(enterId));
		String type = request.getParameter("type");
		if(type==null||"".equals(type)||"undefined".equals(type)){
			if(enterprise!=null){
				String revIds = enterprise.getRevIds();
				if(revIds!=null&&!"".equals(revIds)&&revIds.trim().length()!=0){
					//List<Enterprise> list = enterpriseService.getList(revIds, pb,"1");
					//JsonUtil.jsonFromList(list, pb.getTotalItems()) ;
					enterpriseService.getList(revIds, pb,"1");
				}
			}
		}else{
			if(enterprise!=null){
				String revIds = enterprise.getRevIds();
				StringBuffer buff = new StringBuffer("");
				if(revIds!=null&&!"".equals(revIds)){
					buff.append(revIds).append(",").append(enterprise.getId());
				}else{
					buff.append(enterprise.getId());
				}
				//List<Enterprise> list = enterpriseService.getList(buff.toString(), pb,"0");
				///JsonUtil.jsonFromList(list, pb.getTotalItems()) ;
				enterpriseService.getList(buff.toString(), pb,"0");
			}
		}
		
		return SUCCESS;
	}
	public void ajaxQuery() {
		Object ids=this.getRequest().getSession().getAttribute("userIds");
		Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
		String companyId= map.get("companyId");
		String userIds= map.get("userId");
		enterpriseService.ajaxQueryEnterprise(companyId,userIds,enterprisename, ownership,shortname, organizecode,cciaa, start, limit,sort,dir,customerLevel);
	}
	
	public String entList(){
		try{
			Object ids=this.getRequest().getSession().getAttribute("userIds");
			Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
			PageBean<Enterprise> pageBean = new PageBean<Enterprise>(start,limit,getRequest());
			enterpriseService.entList(pageBean,map);
			JsonUtil.jsonFromList(pageBean.getResult(),pageBean.getTotalCounts());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查询借款企业剩余借款额度
	 * @return
	 */
	public String loanQuota() {
		PageBean<Enterprise>  pageBean = new PageBean<Enterprise>(start,limit,this.getRequest());
		enterpriseService.loanEnterpriseQuota(pageBean);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(pageBean.getTotalCounts()).append(
						",result:");
		JSONSerializer serializer = com.zhiwei.core.util.JsonUtil.getJSONSerializer();
		buff.append(serializer.exclude(new String[] { "class" })
				.serialize(pageBean.getResult()));
		buff.append("}");

		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * ajax方式新增企业
	 */
	
	public void ajaxAdd(){
		String isMobile = this.getRequest().getParameter("isMobile");

		if(isMobile!=null&&isMobile.endsWith("1")){
			if(person.getId()!=null){
				person = personService.getById(person.getId());
			}
		}
		try {
			String shareequity=this.getRequest().getParameter("gudongInfo");
	   		List<EnterpriseShareequity> listES=new ArrayList<EnterpriseShareequity>(); //股东信息
        	if(null != shareequity && !"".equals(shareequity)) {
				String[] shareequityArr = shareequity.split("@");
				 for(int i=0; i<shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try{
						EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity)JSONMapper.toJava(parser.nextValue(),EnterpriseShareequity.class);
						listES.add(enterpriseShareequity);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
    		}
        	String enterpriseGsdjzId = super.getRequest().getParameter("enterpriseGsdjzId");
        	String enterpriseYyzzId = super.getRequest().getParameter("enterpriseYyzzId");
        	String enterpriseZzjgId = super.getRequest().getParameter("enterpriseZzjgId");
        	String enterpriseDsdjId = super.getRequest().getParameter("enterpriseDsdjId");

            //新增图片
			String enterpriseXztpyId = super.getRequest().getParameter("enterpriseXztpyId");
			String enterpriseXztpeId = super.getRequest().getParameter("enterpriseXztpeId");
			String enterpriseXztpsId = super.getRequest().getParameter("enterpriseXztpsId");
			String enterpriseXztpssId = super.getRequest().getParameter("enterpriseXztpssId");

			//新增图片名称
			String yyzz = this.getRequest().getParameter("yyzz");
			String zzjg = this.getRequest().getParameter("zzjg");
			String swzj = this.getRequest().getParameter("swzj");
			String dkk = this.getRequest().getParameter("dkk");
			String xzmc1 = this.getRequest().getParameter("xzmc1");
			String xzmc2 = this.getRequest().getParameter("xzmc2");
			String xzmc3 = this.getRequest().getParameter("xzmc3");
			String xzmc4 = this.getRequest().getParameter("xzmc4");

        	Map<String,String> map= new HashMap<String, String>();
        	map.put("enterpriseGsdjzId", enterpriseGsdjzId);
        	map.put("enterpriseYyzzId", enterpriseYyzzId);
        	map.put("enterpriseZzjgId", enterpriseZzjgId);
        	map.put("enterpriseDsdjId", enterpriseDsdjId);

        	//新增
			map.put("enterpriseXztpyId", enterpriseXztpyId);
			map.put("enterpriseXztpeId", enterpriseXztpeId);
			map.put("enterpriseXztpsId", enterpriseXztpsId);
			map.put("enterpriseXztpssId", enterpriseXztpssId);

			//新增图片名称  XF
			map.put("yyzz", yyzz);
			map.put("zzjg", zzjg);
			map.put("swzj", swzj);
			map.put("dkk", dkk);
			map.put("xzmc1", xzmc1);
			map.put("xzmc2", xzmc2);
			map.put("xzmc3", xzmc3);
			map.put("xzmc4", xzmc4);

        	String personSFZZId = super.getRequest().getParameter("personSFZZId");
        	String personSFZFId = super.getRequest().getParameter("personSFZFId");
        	String currentUserName = ContextUtil.getCurrentUser().getFullname(); 
        	Long currentUserId =  ContextUtil.getCurrentUserId();
        	
        	Long companyId = ContextUtil.getLoginCompanyId();
        	if(null!=companyId){
        		if(null==person.getCompanyId()){
        			person.setCompanyId(companyId);
        		}
        		if(null==enterprise.getCompanyId()){
        			enterprise.setCompanyId(companyId);
        		}
        	}
        	enterprise.setCreater(currentUserName);
        	enterprise.setCreaterId(currentUserId);
        	if("".equals(enterprise.getBelongedId())|| enterprise.getBelongedId()==null){
        		enterprise.setBelongedId(currentUserId.toString());
        	}
        	enterprise.setCreatedate(DateUtil.getNowDateTimeTs());
        	person.setCreater(currentUserName);
        	person.setCreaterId(currentUserId);
        	person.setBelongedId(currentUserId.toString());
        	person.setCreatedate(DateUtil.getNowDateTimeTs());
        	
			enterpriseService.ajaxAddEnterprise(enterprise,person,listES,personSFZZId,personSFZFId,map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//更新企业信息
	public String ajaxUpdate() throws IllegalAccessException, InvocationTargetException{
		Integer id = enterprise.getId();
		Enterprise enterprise2=enterpriseService.getById(id);
		BeanUtil.copyNotNullProperties(enterprise2,enterprise);
		enterpriseService.merge(enterprise2);
		return SUCCESS;
	}

	@Resource
	private FTPIoadFileService ftpIoadFileService;
	public void uploadPhoto(){
		HttpServletRequest request = getRequest();
		String mark = getRequest().getParameter("mark");
		String realPath = super.getRequest().getRealPath("/");
		File file = new File(realPath+"attachFiles\\uploads\\cs_enterprise_tx");
		if (!file.exists()) {
			file.mkdirs();
        }
		String guid = UUID.randomUUID().toString();
		String webPath = "attachFiles/uploads/cs_enterprise_tx"+"/"+guid+extendname;
		String filepath = "attachFiles\\uploads\\cs_enterprise_tx"+"\\"+guid+extendname;
		file = new File(realPath+"attachFiles\\uploads\\cs_enterprise_tx"+"\\"+guid+extendname);
		String dir = "uploads/cs_enterprise_tx";
		String uploadFilePath = "uploads\\cs_enterprise_tx";
		boolean flag = FileHelper.fileUpload(fileUpload ,file , new byte[UPLOAD_SIZE]);
		FileForm fileinfo = new FileForm();
		if(flag){
			fileinfo.setMark(mark);
			fileinfo.setContentType("");
			fileinfo.setSetname(fileUploadFileName);
			fileinfo.setFilename("");
			fileinfo.setFilepath(filepath);
			fileinfo.setExtendname(extendname);
			fileinfo.setCreatetime(DateUtil.getNowDateTimeTs());
			Long sl=fileUpload.length();
			fileinfo.setFilesize(sl.intValue());
			fileinfo.setCreatorid(1);
			//fileinfo.setRemark("");
			fileinfo.setWebPath(webPath);
			fileFormService.save(fileinfo);

			//Map<String, String> map = new HashMap<>();
			ftpIoadFileService.ftpUploadFile1(file,webPath,uploadFilePath,dir,extendname,guid);

		}
		String jsonStr = "{success : true,fileid :"+fileinfo.getFileid()+",webPath :'"+fileinfo.getWebPath()+"'}";
		JsonUtil.responseJsonString(jsonStr,"text/html; charset=utf-8");
		
	}
	/*
	 * 删除关联企业
	 * **/
	public String ajaxDelete(){
		String enterId  = this.getRequest().getParameter("enterId");
		
		String listId = this.getRequest().getParameter("listId");
		
		if(null!=enterId&&!"".equals(enterId)&&null!=listId&&!"".equals(listId)){
			Enterprise enter = enterpriseService.getById(Integer.parseInt(enterId));
			if(null!=enter){
				String revIds = enter.getRevIds();
				String[] ids = listId.split(",");
				if(null!=ids&&ids.length!=0){
					for(int i= 0; i<ids.length;i++){
						String id = ids[i];
						if(null!=id&&!"".equals(id)){
							revIds = revIds.replace(","+id, "");//不在首位
							revIds = revIds.replace(id+",", "");//不在尾位
							revIds = revIds.replace(id, "");//单独一个
						}
					}
					if("".equals(revIds)||revIds.trim().length()==0){
						enter.setRevIds(null);
					}else{
						enter.setRevIds(revIds);
					}
					
					enterpriseService.merge(enter);
				}
			}
			
		}
		JsonUtil.responseJsonString("{success:true,flag:'true',msg:'删除成功!'}");
		return SUCCESS;
	}
	/**
	 * 根据id删除企业信息
	 */
	
	public void ajaxDeleteWithId() {
		/*String[] strTable = {"cs_enterprise-id","cs_enterprise_attachfile-enterpriseid",
				"cs_enterprise_bank-enterpriseid","cs_enterprise_creditor-enterpriseid",
				"cs_enterprise_debt-enterpriseid","cs_enterprise_lawsuit-enterpriseid",
				"cs_enterprise_leadteam-enterpriseid","cs_enterprise_managecase-enterpriseid",
				"cs_enterprise_outassure-enterpriseid","cs_enterprise_outinvest-enterpriseid",
				"cs_enterprise_prize-enterpriseid","cs_enterprise_relatecompany-enterpriseid",
				"cs_enterprise_relatedata-enterpriseid","cs_enterprise_relationperson-enterpriseid",
				"cs_enterprise_shareequity-enterpriseid"};*/
		String[] strTable = {"cs_enterprise-id",
				"cs_enterprise_bank-enterpriseid","cs_enterprise_creditor-enterpriseid",
				"cs_enterprise_debt-enterpriseid",/*"cs_enterprise_lawsuit-enterpriseid",*/
				"cs_enterprise_leadteam-enterpriseid","cs_enterprise_managecase-enterpriseid",
				"cs_enterprise_outassure-enterpriseid","cs_enterprise_outinvest-enterpriseid",
				"cs_enterprise_prize-enterpriseid","cs_enterprise_relatecompany-enterpriseid",
				"cs_enterprise_relatedata-enterpriseid","cs_enterprise_relationperson-enterpriseid",
				"cs_enterprise_shareequity-enterpriseid"};
		String [] listArrayId =listId.split(",");
		
		try {
			boolean flag=true;
			for(int i=0;i<listArrayId.length;i++){
				if(null!=listArrayId[i]){
					List<SlSmallloanProject> slist=slSmallloanProjectService.getListOfCustomer("company_customer", Long.valueOf(listArrayId[i]));
					if(null!=slist && slist.size()>0){
						flag=false;
						Enterprise e=enterpriseService.getById(Integer.valueOf(listArrayId[i]));
						JsonUtil.responseJsonString("{success:true,flag:'false',msg:'"+e.getEnterprisename()+"在项目中已被使用，不能删除'}");
					}
				}
			}
			if(flag==true){
				String  turl = super.getRequest().getRealPath("/");
				enterpriseService.ajaxDeleteEnterpriseWithId(strTable, listArrayId,turl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void isEntEmpty(){
		try {
			if("" == organizecode){
				JsonUtil.jsonFromObject(null, true);
			}else{
				Enterprise enterprise = enterpriseService.isEmpty(organizecode);
				enterpriseService.evict(enterprise);
				enterprise.setBpCustEntCashflowAndSaleIncomelist(null);
				enterprise.setBpCustEntLawsuitlist(null);
				enterprise.setBpCustEntUpanddownstreamlist(null);
				enterprise.setBpCustEntUpanddowncontractlist(null);
				enterprise.setBpCustEntPaytaxlist(null);
				JsonUtil.jsonFromObject(enterprise, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void outputExcel(){
		String [] tableHeader = {"序号","企业名称","企业简称","组织机构代码","法人","注册资金(万元)","联系电话","企业成立日期"};
		try {
			Object ids=this.getRequest().getSession().getAttribute("userIds");
			Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
			PageBean<Enterprise> pageBean = new PageBean<Enterprise>(null,null,getRequest());
			enterpriseService.entList(pageBean,map);
			ExcelHelper.export(pageBean.getResult(),tableHeader,"企业客户列表");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addBlack(){
		String msg="";
		try {
			Enterprise ep=enterpriseService.getById(id);
			ep.setIsBlack(true);
			ep.setBlackReason(blackReason);
			enterpriseService.merge(ep);
			msg="{success:true}";
		} catch (Exception e) {
			msg="{success:false}";
			e.printStackTrace();
		}
		JsonUtil.responseJsonString(msg);
	}
	
	public void loadInfo() {
		try{
			if("Financing".equals(businessType)){
				enterpriseService.getSlCompanyInfo(id);
			}else{
				Enterprise enterprise1=this.enterpriseService.getById(id);
				List<BpCustRelation> br_list=bpCustRelationService.getByCustIdAndCustType(Long.valueOf(enterprise1.getId()),"b_loan");
        		if(null!=br_list && br_list.size()>0){
        			BpCustMember bm=bpCustMemberService.get(br_list.get(0).getP2pCustId());
        			if(null!=bm){
        				enterprise1.setIsCheckCard(bm.getIsCheckCard());
        			}
        		}
				Map<String, Object> mapObject = new HashMap<String, Object>();
				if(null != enterprise1.getHangyeType()) {
					  if(null!=areaDicService.getById(enterprise1.getHangyeType())){
						  enterprise1.setHangyeName(areaDicService.getById(enterprise1.getHangyeType()).getText());
					  }
				}
				String belongedName = "";
				if(enterprise1.getBelongedId() != null && enterprise1.getBelongedId() != "") {
					String []belongedIds = enterprise1.getBelongedId().split(",");
					Set<AppUser> userSet = appUserService.getAppUserByStr(belongedIds);
					for(AppUser s:userSet){
						belongedName += s.getFamilyName() + ",";
					}
					if(!"".equals(belongedName)){
						belongedName = belongedName.substring(0,belongedName.length() - 1);
					}
				}
				enterprise1.setBelongedName(belongedName);
				if(null!=enterprise1.getManagecity() && !enterprise1.getManagecity().trim().equals("")){
					String[] regx=enterprise1.getManagecity().split(",");
					String rex="";
					for(int i=0;i<regx.length;i++){
						String temp=areaDicService.getById(Integer.valueOf(regx[i])).getText();
						if(i==(regx.length-1)){
							rex+=temp;
						}else{
							rex+=temp+"_";
						}
					}
					enterprise1.setManagecityName(rex);
				}
				
				FileForm fileForm1_enterprise = fileFormService.getFileByMark("cs_enterprise_dsdjz."+enterprise1.getId());
				FileForm fileForm2_enterprise = fileFormService.getFileByMark("cs_enterprise_gsdjz."+enterprise1.getId());
				FileForm fileForm3_enterprise = fileFormService.getFileByMark("cs_enterprise_yyzzfb."+enterprise1.getId());
				FileForm fileForm4_enterprise = fileFormService.getFileByMark("cs_enterprise_zzjgdmz."+enterprise1.getId());

				//新增 图片字段
				FileForm fileForm5_enterprise = fileFormService.getFileByMark("cs_enterprise_xxtpy."+enterprise1.getId());
				FileForm fileForm6_enterprise = fileFormService.getFileByMark("cs_enterprise_xxtpe."+enterprise1.getId());
				FileForm fileForm7_enterprise = fileFormService.getFileByMark("cs_enterprise_xxtps."+enterprise1.getId());
				FileForm fileForm8_enterprise = fileFormService.getFileByMark("cs_enterprise_xxtpss."+enterprise1.getId());

				if(fileForm1_enterprise != null){
					enterprise1.setEnterpriseDsdjId(fileForm1_enterprise.getFileid());
					enterprise1.setEnterpriseDsdjURL(fileForm1_enterprise.getWebPath());
					enterprise1.setEnterpriseDsdjExtendName(fileForm1_enterprise.getExtendname());
					enterprise1.setDkk(fileForm1_enterprise.getSetname());
				}
	            if(fileForm2_enterprise != null){
	            	enterprise1.setEnterpriseGsdjzId(fileForm2_enterprise.getFileid());
	            	enterprise1.setEnterpriseGsdjzURL(fileForm2_enterprise.getWebPath());
	            	enterprise1.setEnterpriseGsdjzExtendName(fileForm2_enterprise.getExtendname());
	            	enterprise1.setSwzj(fileForm2_enterprise.getSetname());
				}
				if(fileForm3_enterprise != null){
					enterprise1.setEnterpriseYyzzId(fileForm3_enterprise.getFileid());	
					enterprise1.setEnterpriseYyzzURL(fileForm3_enterprise.getWebPath());
					enterprise1.setEnterpriseYyzzExtendName(fileForm3_enterprise.getExtendname());
					enterprise1.setYyzz(fileForm3_enterprise.getSetname());
				}
				if(fileForm4_enterprise != null){
					enterprise1.setEnterpriseZzjgId(fileForm4_enterprise.getFileid());
					enterprise1.setEnterpriseZzjgURL(fileForm4_enterprise.getWebPath());
					enterprise1.setEnterpriseZzjgExtendName(fileForm4_enterprise.getExtendname());
					enterprise1.setZzjg(fileForm4_enterprise.getSetname());
				}

				//新增
				if(fileForm5_enterprise != null){
					enterprise1.setEnterpriseXztpyId(fileForm5_enterprise.getFileid());
					enterprise1.setEnterpriseXztpyURL(fileForm5_enterprise.getWebPath());
					enterprise1.setEnterpriseXztpyExtendName(fileForm5_enterprise.getExtendname());
					enterprise1.setXzmc1(fileForm5_enterprise.getSetname());
				}
				if(fileForm6_enterprise != null){
					enterprise1.setEnterpriseXztpeId(fileForm6_enterprise.getFileid());
					enterprise1.setEnterpriseXztpeURL(fileForm6_enterprise.getWebPath());
					enterprise1.setEnterpriseXztpeExtendName(fileForm6_enterprise.getExtendname());
					enterprise1.setXzmc2(fileForm6_enterprise.getSetname());
				}
				if(fileForm7_enterprise != null){
					enterprise1.setEnterpriseXztpsId(fileForm7_enterprise.getFileid());
					enterprise1.setEnterpriseXztpsURL(fileForm7_enterprise.getWebPath());
					enterprise1.setEnterpriseXztpsExtendName(fileForm7_enterprise.getExtendname());
					enterprise1.setXzmc3(fileForm7_enterprise.getSetname());
				}
				if(fileForm8_enterprise != null){
					enterprise1.setEnterpriseXztpssId(fileForm8_enterprise.getFileid());
					enterprise1.setEnterpriseXztpssURL(fileForm8_enterprise.getWebPath());
					enterprise1.setEnterpriseXztpssExtendName(fileForm8_enterprise.getExtendname());
					enterprise1.setXzmc4(fileForm8_enterprise.getSetname());
				}

				this.enterpriseService.evict(enterprise1);
				enterprise1.setBpCustEntCashflowAndSaleIncomelist(null);
				enterprise1.setBpCustEntLawsuitlist(null);
				enterprise1.setBpCustEntUpanddownstreamlist(null);
				enterprise1.setBpCustEntPaytaxlist(null);
				enterprise1.setBpCustEntUpanddowncontractlist(null);
				mapObject.put("enterprise", enterprise1);
				
				if(null!=enterprise1.getLegalpersonid() && !"".equals(enterprise1.getLegalpersonid())){
					VPersonDic vPersonDic = personService.queryPerson(enterprise1.getLegalpersonid());
					if(null!=vPersonDic){
						FileForm fileForm2 = fileFormService.getFileByMark("cs_person_sfzz."+vPersonDic.getId());
						vPersonDic.setPersonSFZZId(fileForm2.getFileid());
						vPersonDic.setPersonSFZZUrl(fileForm2.getWebPath());
						FileForm fileForm3 = fileFormService.getFileByMark("cs_person_sfzf."+vPersonDic.getId());
						if(fileForm3 != null){
							vPersonDic.setPersonSFZFId(fileForm3.getFileid());
							vPersonDic.setPersonSFZFUrl(fileForm3.getWebPath());
						}
						mapObject.put("person",vPersonDic);
					}
				}
				JsonUtil.jsonFromObject(mapObject, true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	public void addRev(){
		String ids = getRequest().getParameter("ids");
		String enterId = getRequest().getParameter("enterId");
		if(enterId==null||enterId.equals("")){
			return;
		}
		if(ids==null||"".equals(ids)||ids.length()==0){
			
		}else{
			Enterprise enterprise = enterpriseService.getById(Integer.parseInt(enterId));
			if(enterprise!=null){
				String revIds = enterprise.getRevIds();
				if(revIds==null||revIds.equals("")){
					enterprise.setRevIds(ids.substring(0, ids.length()-1));
				}else{
					enterprise.setRevIds(ids+enterprise.getRevIds());
				}
				enterpriseService.merge(enterprise);
			}
		}
		
	}
	/**
	 * 黑名单列表
	 */
	public void getBlackList(){
		AppUser currentUser= ContextUtil.getCurrentUser();
		String RoleNames =currentUser.getRoleNames();
		String roles[]=RoleNames.split(",");
		Boolean RoleKey =false;//用来判断当前登陆者是不是超级管理员： true表示是超级管理员
		for(int i =0;i<roles.length;i++){
			if("超级管理员".equals(roles[i])){
				RoleKey=true;
			}
		}
		//授权查询全部客户的代码begin 在这里为companyId赋值
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());//判断当前是否为集团版本 
	    String  roleType=ContextUtil.getRoleTypeSession();
		String companyId="";
		if(!RoleKey){
			if(flag){//表示为集团版本
				if(roleType.equals("control")){//具有管控角色
					companyId=ContextUtil.getBranchIdsStr();	
				}else{//不具有管控角色
					if(!isGrantedSeeAllBlackList){//不具有查看所有客户的权限
						companyId=String.valueOf(ContextUtil.getLoginCompanyId());
					}
				}
			
			}else {//表示不为集团版本
				if(!isGrantedSeeAllBlackList){
					companyId=String.valueOf(ContextUtil.getLoginCompanyId());
				}
		}
		}
		
		String customerType=getRequest().getParameter("customerType");
		if(customerType.equals("1")){
			customerType="person";
		}else{
			customerType="company";
		}
		
		//授权查询全部客户的代码end
		enterpriseService.getList(customerType, customerName, cardNum,companyId, start, limit);
	}
	/**
	 * 撤销黑名单
	 */
	public void RevocateBlack(){
	    String msg="";
		try {
			if(null!=customerType && customerType.equals("company")){
				Enterprise ep=enterpriseService.getById(Integer.parseInt(customerId));
				ep.setIsBlack(false);
				ep.setCustomerLevel(customerLevel);
				enterpriseService.save(ep);
				msg="{success:true}";
			}else if(null!=customerType && customerType.equals("person")){
				Person p=personService.getById(Integer.parseInt(customerId));
				p.setIsBlack(false);
				p.setCustomerLevel(customerLevel);
				personService.save(p);
				msg="{success:true}";
			}
		} catch (Exception e) {
			msg="{success:false}";
			e.printStackTrace();
		}
		JsonUtil.responseJsonString(msg);
	}
	 public void getHeaderInfo(){
	    	try{
		    	String enterpriseId=this.getRequest().getParameter("enterpriseId");
		    	if(null!=enterpriseId && !"".equals(enterpriseId)){
		    		Enterprise e=enterpriseService.getById(Integer.parseInt(enterpriseId));
		    		if(null!=e){
		    			Person p=null;
		    			if(null!=e.getHeaderId()){
				    		p=personService.getById(e.getHeaderId());
				    		
		    			}
		    			
//		    			JsonUtil.jsonFromObject(p, true);
		    			
//		    			JSONSerializer json = com.zhiwei.core.util.JsonUtil.getJSONSerializer();
//		    			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {});
//		    			JsonUtil.responseJsonString(json.serialize(p).toString());
		    			
		    			StringBuffer buff = new StringBuffer("{success:true,").append("data:");
		    			JSONSerializer json = com.zhiwei.core.util.JsonUtil.getJSONSerializer();
		    			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {});
		    			buff.append(json.serialize(p));
		    			buff.append("}");
		    			JsonUtil.responseJsonString(buff.toString());
		    		}
		    	}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	//return SUCCESS;
	    }
    public void addHeader(){
    	String msg="";
    	try{
    		String enterpriseId=this.getRequest().getParameter("enterpriseId");
    		String personId =this.getRequest().getParameter("personId");
			String personCarInfo =this.getRequest().getParameter("personCarInfo");
			String personHouseInfo =this.getRequest().getParameter("personHouseInfo");
			
    		if(null!=enterpriseId && !"".equals(enterpriseId)){
    			Enterprise e=enterpriseService.getById(Integer.parseInt(enterpriseId));
    			if(null==person.getCompanyId()){
    				Long companyId=ContextUtil.getLoginCompanyId();
    				
    				person.setCompanyId(companyId);
    			}
    			Person personOld=personService.getById(Integer.valueOf(personId));
    			if(personCarInfo!=null&&!"".equals(personCarInfo)){
					String[] personCarInfoArr = personCarInfo.split("@");
					for (int i = 0; i < personCarInfoArr.length; i++) {
						String str = personCarInfoArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						CsPersonCar csPersonCar = (CsPersonCar) JSONMapper.toJava(
								parser.nextValue(), CsPersonCar.class);
						csPersonCar.setPersonId(Integer.valueOf(personId));
						if(csPersonCar.getId()==null||"".equals(csPersonCar.getId())){
							csPersonCarService.save(csPersonCar);
						}else{
							CsPersonCar temp=csPersonCarService.get(csPersonCar.getId());
							BeanUtil.copyNotNullProperties(temp, csPersonCar);
							csPersonCarService.save(temp);
						}
					}
					
				}
				if(personHouseInfo!=null&&!"".equals(personHouseInfo)){
					String[] personHouseInfoArr = personHouseInfo.split("@");
					for (int i = 0; i < personHouseInfoArr.length; i++) {
						String str = personHouseInfoArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						CsPersonHouse csPersonHouse = (CsPersonHouse) JSONMapper.toJava(
								parser.nextValue(), CsPersonHouse.class);
						csPersonHouse.setPersonId(Integer.valueOf(personId));
						if(csPersonHouse.getId()==null||"".equals(csPersonHouse.getId())){
							csPersonHouseService.save(csPersonHouse);
						}else{
							CsPersonHouse tempHouse=csPersonHouseService.get(csPersonHouse.getId());
							BeanUtil.copyNotNullProperties(tempHouse, csPersonHouse);
							csPersonHouseService.save(tempHouse);
						}
					}
					
				}
				if(personOld!=null){
					BeanUtil.copyNotNullProperties(personOld, person);
				}
				personService.merge(personOld);
    			e.setHeaderId(person.getId());
    			enterpriseService.merge(e);
    			msg="{success:true}";
    		}
    	}catch(Exception e){
    		msg="{success:false}";
    		e.printStackTrace();
    	}
    	JsonUtil.responseJsonString(msg);

    }
    public void verificationOrganizecode(){
		try{
		String organizecode=this.getRequest().getParameter("organizecode");
		String enterpriseId=this.getRequest().getParameter("enterpriseId");
		if(null==enterpriseId || "".equals(enterpriseId) || "0".equals(enterpriseId)){
			Enterprise enterprise=enterpriseService.isEmpty(organizecode);
			if(null!=enterprise){
				JsonUtil.responseJsonString("{success:true,msg:false}");
			}
		}else{
			Enterprise e=enterpriseService.getById(Integer.parseInt(enterpriseId));
			if(!e.getOrganizecode().equals(organizecode)){
				Enterprise enterprise=enterpriseService.isEmpty(organizecode);
				if(null!=enterprise){
					JsonUtil.responseJsonString("{success:true,msg:false}");
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    public void ajaxQueryForCombo(){
		enterpriseService.ajaxQueryEnterpriseForCombo(query, start, limit) ;
		
	}
    public void ajaxSee() {
    	try{
			if("Financing".equals(businessType)){
				enterpriseService.getSlCompanyInfo(id);
			}else{
				EnterpriseView ep =enterpriseService.getByViewId(id);
				ep = enterpriseService.setEnterpriseView(ep);
			
				if(null!=ep){
					JsonUtil.jsonFromObject(ep, true) ;
				}else{
					JsonUtil.jsonFromObject("没有查找到相应的企业信息", false) ;
				}
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
    
    public void getBlackListToExcel(){
    	try {
    		AppUser currentUser= ContextUtil.getCurrentUser();
    		String RoleNames =currentUser.getRoleNames();
    		String roles[]=RoleNames.split(",");
    		Boolean RoleKey =false;//用来判断当前登陆者是不是超级管理员： true表示是超级管理员
    		for(int i =0;i<roles.length;i++){
    			if("超级管理员".equals(roles[i])){
    				RoleKey=true;
    			}
    		}
    		//授权查询全部客户的代码begin 在这里为companyId赋值
    		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());//判断当前是否为集团版本 
    	    String  roleType=ContextUtil.getRoleTypeSession();
    		String companyId="";
    		if(!RoleKey){
    			if(flag){//表示为集团版本
    				if(roleType.equals("control")){//具有管控角色
    					companyId=ContextUtil.getBranchIdsStr();	
    				}else{//不具有管控角色
    					if(!isGrantedSeeAllBlackList){//不具有查看所有客户的权限
    						companyId=String.valueOf(ContextUtil.getLoginCompanyId());
    					}
    				}
    			
    			}else {//表示不为集团版本
    				if(!isGrantedSeeAllBlackList){
    					companyId=String.valueOf(ContextUtil.getLoginCompanyId());
    				}
    		}
    		}
    		
    		String customerType=getRequest().getParameter("customerType");
    		if(customerType.equals("1")){
    			customerType="person";
    		}else{
    			customerType="company";
    		}
    		
    		//授权查询全部客户的代码end
    		List<VEnterprisePerson> list= enterpriseService.getBlackListToExcel(customerType, customerName, cardNum,companyId, start, limit);
			//授权查询全部客户的代码end
			String [] tableHeader = {"序号","客户名称","证件号码","联系电话","原因说明"};
			ExcelHelper.exportAllBlackList(list,tableHeader,"黑名单客户");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //查询企业户开通P2P账号情况
    public String list(){
		String userIdsStr = "";
		String roleTypeStr = ContextUtil.getRoleTypeSession();
		if (!isAll && !roleTypeStr.equals("control")) {// 如果用户不拥有查看所有项目信息的权限
			userIdsStr = appUserService.getUsersStr();// 当前登录用户以及其所有下属用户
		}
    	List<EnterpriseView> listEnterprise=enterpriseService.enterPriseList(start,limit,this.getRequest(),userIdsStr);
		List<EnterpriseView> listEnterpriseCount=enterpriseService.enterPriseList(null,null,this.getRequest(),userIdsStr);
		StringBuffer buff = new StringBuffer("{success:true,'totalProperty':")
		.append(listEnterpriseCount!=null?listEnterpriseCount.size():0).append(",topics:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
		/* .excludeFieldsWithoutExposeAnnotation() */.create();
		buff.append(gson.toJson(listEnterprise));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
    }
    
    //查询企业客户资金账户表
    public String fianceAccountList(){
    	String userIdsStr = "";
		String roleTypeStr = ContextUtil.getRoleTypeSession();
		if (!isAll && !roleTypeStr.equals("control")) {// 如果用户不拥有查看所有项目信息的权限
			userIdsStr = appUserService.getUsersStr();// 当前登录用户以及其所有下属用户
		}
    	PagingBean pb=new PagingBean(start,limit);
		Map map=ContextUtil.createResponseMap(this.getRequest());
		map.put("userIdsStr", userIdsStr);
		List<EnterpriseView> list= enterpriseService.getAllAccountList(map,pb);
		Type type=new TypeToken<List<EnterpriseView>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
    	return SUCCESS;
    }

    /**
     *企业生成担保函
     *
     * @auther: XinFang
     * @date: 2018/8/28 10:44
     */
	public String creatContract() {
		HttpServletRequest request = getRequest();
		String serverPath = request.getRealPath("/");
		String documenttempletId = request.getParameter("documenttempletId");//模板id
		String bdirProId = request.getParameter("bdirProId");//模板id
		String proId = request.getParameter("proId");//模板id

		DocumentTemplet dtemplet = documentTempletService.getById(Integer.valueOf(documenttempletId));
		FileForm fileForm = fileFormService.getById(dtemplet.getFileid());

		try {
			//1.拼接word源文件路径
			if (null != fileForm) {
				StringBuffer wordPath = new StringBuffer(serverPath).append(fileForm.getFilepath());
				//2.判断word文件是否存在
				File file = new File(wordPath.toString());
				if (file.exists()) {
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
					BpBusinessDirPro bpBusinessDirPro = bpBusinessDirProService.get(Long.valueOf(bdirProId));
					PlBidPlan plBidPlan = plBidPlanService.getByBdirProId(Long.valueOf(bdirProId));
					BpFundProject bpFundProject = bpFundProjectService.get(bpBusinessDirPro.getMoneyPlanId());
					SlSmallloanProject sproject = slSmallloanProjectService.get(bpFundProject.getProjectId());
					//3.拼接生成后的word文件路径
					StringBuffer newPath = new StringBuffer("attachFiles/projFile/contfolder/");
					newPath.append(sproject.getProjectNumber()).append("/");
					if (null != plBidPlan) {
						newPath.append(plBidPlan.getBidProNumber()).append("_");
						newPath.append(plBidPlan.getBidId());
						if (plBidPlan.getStartIntentDate()==null){
						    plBidPlan.setStartIntentDate(new Date());
                        }
					}
					newPath.append("/p2pContract/");
					//4.临时word文件路径
					int index = fileForm.getFilepath().lastIndexOf("/");
					StringBuffer tempPath = new StringBuffer(fileForm.getFilepath().substring(0, index)).append("/");
					String temsrc = serverPath.replaceAll("\\\\", "/") + (tempPath.toString());

					//5.获得该模板中合同要素
					//生成p2p合同要素的时候大多数要素都需要手动计算，所以传值为2。
					//如果codeMap没有模板中某个要素,则该要素是不会被替换掉的
//					Map<String,Object> codeMap=WordPOI.getReplaceElementsInWord("2",wordPath.toString(),"InternetFinance");
					Map<String, Object> codeMap = WordPOI.getReplaceElementsInWord("2", wordPath.toString(), businessType);
					//6.拼接生成后的word文件名称

					StringBuffer fileName = new StringBuffer();

					//企业借款
					Enterprise en = enterpriseService.getById(sproject.getOppositeID().intValue());
					fileName.append(en.getEnterprisename()).append("_");

					fileName.append(df.format(new Date()));
					fileName.append("_");
					fileName.append(BaseConstants.SUFIXDOCX);

					String newsrc = newPath.toString() + fileName;
					String dbpath = (serverPath + newsrc).replaceAll("\\\\", "/");

					//7.生成word文件路径
					BaseUtil.createDir(dbpath.substring(0, dbpath.lastIndexOf("/")));
					//8.copy word文件到指定路径下
					temsrc += "p2pContractx.docx";
					BaseUtil.fileChannelCopy(wordPath.toString(), temsrc);

					signP2PElement.updateP2PCodeCommon(sproject, bpFundProject, plBidPlan, codeMap, this.getRequest());

					Map<String, List<List<String>>> tableList = new TreeMap<String, List<List<String>>>();

					//9.替换word要素
					boolean flag = WordPOI.replaceAndGenerateWord(temsrc, dbpath, codeMap, true, tableList);
					//10.删除临时文件
					File tempFile = new File(temsrc);
					if (tempFile.isFile() && tempFile.exists()) {
						tempFile.delete();
					}
                    ElementUtil.downloadFile(dbpath, getResponse());
				}
			}
		} catch (Exception e) {
			JsonUtil.responseJsonString("{success:true,msg:'合同生成失败!'}");
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}



    public String getQuery() {
		return query;
	}


	public void setQuery(String query) {
		this.query = query;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getCustomerType() {
		return customerType;
	}


	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getCardNum() {
		return cardNum;
	}


	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}


	public Boolean getIsGrantedSeeAllBlackList() {
		return isGrantedSeeAllBlackList;
	}


	public void setIsGrantedSeeAllBlackList(Boolean isGrantedSeeAllBlackList) {
		this.isGrantedSeeAllBlackList = isGrantedSeeAllBlackList;
	}


	public String getBusinessType() {
		return businessType;
	}


	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getBlackReason() {
		return blackReason;
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


	public File getFileUpload() {
		return fileUpload;
	}


	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}


	public String getFileUploadFileName() {
		return fileUploadFileName;
	}


	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}


	public String getExtendname() {
		return extendname;
	}


	public void setExtendname(String extendname) {
		this.extendname = extendname;
	}


	public Enterprise getEnterprise() {
		return enterprise;
	}


	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	public String getEnterprisename() {
		return enterprisename;
	}


	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}


	public String getOwnership() {
		return ownership;
	}


	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}


	public String getShortname() {
		return shortname;
	}


	public void setShortname(String shortname) {
		this.shortname = shortname;
	}


	public String getOrganizecode() {
		return organizecode;
	}


	public void setOrganizecode(String organizecode) {
		this.organizecode = organizecode;
	}


	public String getCciaa() {
		return cciaa;
	}


	public void setCciaa(String cciaa) {
		this.cciaa = cciaa;
	}


	public String getCustomerLevel() {
		return customerLevel;
	}


	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}


	public Boolean getIsGrantedSeeAllEnterprises() {
		return isGrantedSeeAllEnterprises;
	}


	public void setIsGrantedSeeAllEnterprises(Boolean isGrantedSeeAllEnterprises) {
		this.isGrantedSeeAllEnterprises = isGrantedSeeAllEnterprises;
	}

	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}
	
}
