package com.zhiwei.credit.action.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.GroupUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.customer.Customerbanklinkman;
import com.zhiwei.credit.model.customer.InvestEnterprise;
import com.zhiwei.credit.model.customer.InvestPerson;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.customer.CustomerbanklinkmanService;
import com.zhiwei.credit.service.customer.InvestEnterpriseService;
import com.zhiwei.credit.service.customer.InvestPersonService;
import com.zhiwei.credit.service.system.AppUserService;
/**
 * 
 * @author 
 *
 */
public class InvestEnterpriseAction extends BaseAction{
	@Resource
	private InvestEnterpriseService investEnterpriseService;
	@Resource
	private InvestPersonService investPersonService;
	@Resource
	private CustomerbanklinkmanService customerbanklinkmanService;
	@Resource
	private FileFormService fileFormService;
	@Resource
	private AppUserService appUserService;
	private InvestEnterprise investEnterprise;
	private InvestPerson investPerson;
	
	private Long id;
	private Boolean isAll;
	
	
	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}

	public InvestPerson getInvestPerson() {
		return investPerson;
	}

	public void setInvestPerson(InvestPerson investPerson) {
		this.investPerson = investPerson;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InvestEnterprise getInvestEnterprise() {
		return investEnterprise;
	}

	public void setInvestEnterprise(InvestEnterprise investEnterprise) {
		this.investEnterprise = investEnterprise;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		Object ids=this.getRequest().getSession().getAttribute("userIds");
		Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
		String userIds= map.get("userId");
		PagingBean pb=new PagingBean(start,limit);
		List<InvestEnterprise> list= investEnterpriseService.getList(this.getRequest(), pb,userIds);
		for(InvestEnterprise e:list){
			String belongedId=e.getBelongedId();
			if(null!=belongedId && !belongedId.equals("")){
				String[] belongedIdArr=belongedId.split(",");
				String belonger="";
				for(int i=0;i<belongedIdArr.length;i++){
					String belongedIdStr=belongedIdArr[i];
					if(!"".equals(belongedIdStr)){
						AppUser user=appUserService.get(Long.valueOf(belongedIdStr));
						if(null!=user){
							belonger=belonger+user.getFullname()+",";
						}
					}
				}
				if(!belonger.equals("")){
					belonger=belonger.substring(0,belonger.length()-1);
				}
				e.setBelongedName(belonger);
			}
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
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
				investEnterpriseService.remove(new Long(id));
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
		Map<String,Object> map=new HashMap<String,Object>();
		InvestEnterprise investEnterprise=investEnterpriseService.get(id);
		FileForm fileForm1 = fileFormService.getFileByMark("cs_invest_enterprise_yyzz."+investEnterprise.getId());
		if(null!=fileForm1 && null!=fileForm1.getFileid()){
			investEnterprise.setEnterpriseYyzzId(fileForm1.getFileid());
			investEnterprise.setEnterpriseYyzzURL(fileForm1.getWebPath());
			investEnterprise.setEnterpriseYyzzExtendName(fileForm1.getExtendname());
		}
		FileForm fileForm2 = fileFormService.getFileByMark("cs_invest_enterprise_zzjgdmz."+investEnterprise.getId());
		if(null!=fileForm2 && null!=fileForm2.getFileid()){
			investEnterprise.setEnterpriseZzjgId(fileForm2.getFileid());
			investEnterprise.setEnterpriseZzjgURL(fileForm2.getWebPath());
			investEnterprise.setEnterpriseZzjgExtendName(fileForm2.getExtendname());
		}
		FileForm fileForm3 = fileFormService.getFileByMark("cs_invest_enterprise_swzsmj."+investEnterprise.getId());
		if(null!=fileForm3 && null!=fileForm3.getFileid()){
			investEnterprise.setEnterpriseSwzId(fileForm3.getFileid());
			investEnterprise.setEnterpriseSwzURL(fileForm3.getWebPath());
			investEnterprise.setEnterpriseSwzExtendName(fileForm3.getExtendname());
		}
		FileForm fileForm4 = fileFormService.getFileByMark("cs_invest_enterprise_dkksmj."+investEnterprise.getId());
		if(null!=fileForm4 && null!=fileForm4.getFileid()){
			investEnterprise.setEnterpriseDkkId(fileForm4.getFileid());
			investEnterprise.setEnterpriseDkkURL(fileForm4.getWebPath());
			investEnterprise.setEnterpriseDkkExtendName(fileForm4.getExtendname());
		}
		map.put("investEnterprise", investEnterprise);

		if(null!=investEnterprise && null!=investEnterprise.getLegalpersonid()){
			InvestPerson person=investPersonService.get(investEnterprise.getLegalpersonid().longValue());
			if(null!=person){
				FileForm fileForm5 = fileFormService.getFileByMark("cs_invest_person_sfzz."+person.getPerId());
				if(null!=fileForm5 && null!=fileForm5.getFileid()){
					person.setPersonSFZZId(fileForm5.getFileid());
					person.setPersonSFZZUrl(fileForm5.getWebPath());
					person.setPersonSFZZExtendName(fileForm5.getExtendname());
				}
				FileForm fileForm6 = fileFormService.getFileByMark("cs_invest_person_sfzf."+person.getPerId());
				if(null!=fileForm6 && null!=fileForm6.getFileid()){
					person.setPersonSFZFId(fileForm6.getFileid());
					person.setPersonSFZFUrl(fileForm6.getWebPath());
					person.setPersonSFZFExtendName(fileForm6.getExtendname());
				}
				map.put("investPerson", person);
			}
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(map));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		try{
			if(investPerson.getPerId()==null){
				investPerson.setBelongedId(ContextUtil.getCurrentUserId().toString());
				investPerson.setCompanyId(ContextUtil.getLoginCompanyId());
				investPersonService.save(investPerson);
			}else{
				InvestPerson orgInvestPerson=investPersonService.get(investPerson.getPerId());
				BeanUtil.copyNotNullProperties(orgInvestPerson, investPerson);
				investPersonService.save(orgInvestPerson);
			}
			String personSFZZId=this.getRequest().getParameter("personSFZZId");
			String personSFZFId=this.getRequest().getParameter("personSFZFId");
			if(null!=personSFZZId && !personSFZZId.equals("")){
				fileFormService.updateFile("cs_invest_person_sfzz."+investPerson.getPerId(), Integer.valueOf(personSFZZId));
			}
			if(null!=personSFZFId && !personSFZFId.equals("")){
				fileFormService.updateFile("cs_invest_person_sfzf."+investPerson.getPerId(), Integer.valueOf(personSFZFId));
			}
			investEnterprise.setLegalpersonid(investPerson.getPerId());
			investEnterprise.setMainperson(investPerson.getPerName());
			String businessType=this.getRequest().getParameter("businessType");
			if(investEnterprise.getId()==null){
				investEnterprise.setBelongedId(ContextUtil.getCurrentUserId().toString());
				investEnterprise.setCompanyId(ContextUtil.getLoginCompanyId());
				investEnterprise.setCreater(ContextUtil.getCurrentUser().getFullname());
				investEnterprise.setBusinessType(businessType);
				investEnterprise.setBalanceCreditLimit(investEnterprise.getNowCreditLimit());				investEnterpriseService.save(investEnterprise);
				
			}else{
				InvestEnterprise orgInvestEnterprise=investEnterpriseService.get(investEnterprise.getId());
				BeanUtil.copyNotNullProperties(orgInvestEnterprise, investEnterprise);
				orgInvestEnterprise.setBusinessType(businessType);
				orgInvestEnterprise.setBalanceCreditLimit(orgInvestEnterprise.getNowCreditLimit());
				investEnterpriseService.merge(orgInvestEnterprise);
			}
			String enterpriseYyzzId=this.getRequest().getParameter("enterpriseYyzzId");
			String enterpriseZzjgId=this.getRequest().getParameter("enterpriseZzjgId");
			String enterpriseSwzId=this.getRequest().getParameter("enterpriseSwzId");
			String enterpriseDkkId=this.getRequest().getParameter("enterpriseDkkId");
			if(null!=enterpriseYyzzId && !enterpriseYyzzId.equals("")){
				fileFormService.updateFile("cs_invest_enterprise_yyzz."+investEnterprise.getId(), Integer.valueOf(enterpriseYyzzId));
			}
			if(null!=enterpriseZzjgId && !enterpriseZzjgId.equals("")){
				fileFormService.updateFile("cs_invest_enterprise_zzjgdmz."+investEnterprise.getId(), Integer.valueOf(enterpriseZzjgId));
			}
			if(null!=enterpriseSwzId && !enterpriseSwzId.equals("")){
				fileFormService.updateFile("cs_invest_enterprise_swzsmj."+investEnterprise.getId(), Integer.valueOf(enterpriseSwzId));
			}
			if(null!=enterpriseDkkId && !enterpriseDkkId.equals("")){
				fileFormService.updateFile("cs_invest_enterprise_dkksmj."+investEnterprise.getId(), Integer.valueOf(enterpriseDkkId));
			}
			String linkManJson=this.getRequest().getParameter("linkManJson");
			if(null != linkManJson && !"".equals(linkManJson)) {

				String[] linkArr = linkManJson.split("@");
				
				for(int i=0; i<linkArr.length; i++) {
					String str = linkArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					Customerbanklinkman customerbanklinkman = (Customerbanklinkman)JSONMapper.toJava(parser.nextValue(),Customerbanklinkman.class);
					customerbanklinkman.setEnterpriseid(investEnterprise.getId());
					if(null==customerbanklinkman.getId()){
						customerbanklinkmanService.save(customerbanklinkman);
					}else{
						Customerbanklinkman orgCustomerbanklinkman=customerbanklinkmanService.get(customerbanklinkman.getId());
						BeanUtil.copyNotNullProperties(orgCustomerbanklinkman, customerbanklinkman);
						customerbanklinkmanService.save(orgCustomerbanklinkman);
					}
					setJsonString("{success:true}");
					
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public void outputExcel(){
		try{
			List<InvestEnterprise> list= investEnterpriseService.getExcelList(this.getRequest(), null);
			String tableHeaderName = "投资企业客户列表";
			String[] tableHeader = { "序号", "企业名称", "企业简称", "组织机构代码", "营业执照号码",
					"行业类别", "法人", "联系电话"};
			ExcelHelper.investEnterpriseExcel(list, tableHeader, tableHeaderName);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String verificationOrganizecode(){
		try{
			String organizecode=this.getRequest().getParameter("organizecode");
			InvestEnterprise enterprise=investEnterpriseService.getByOrganizecode(organizecode);
			if(null!=enterprise && null==id){
				jsonString="{success:true,unique:false}";
			}
			if(null!=enterprise && null!=id){
				if(!(enterprise.getId().toString()).equals(id.toString())){
					jsonString="{success:true,unique:false}";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String arrayList(){
		String businessType = getRequest().getParameter("businessType");
		//String userIdStr = null;
		String currentUserId = ContextUtil.getCurrentUserId().toString();
		StringBuffer buff = new StringBuffer("");
		if(businessType==null||"".equals(businessType)||"undefined".equals(businessType)){
			//jsonString = "";
		}else{
			List<InvestEnterprise> list = investEnterpriseService.getList(businessType, currentUserId);
			if(null!=list&&list.size()>0){
				for(InvestEnterprise invest:list){
					if(invest==null){
						continue;
					}
					buff.append("[").append(invest.getId()).append(",'").append(invest.getEnterprisename()).append("',").append(invest.getNowCreditLimit()).append("],");
				}
			}
		}
		String datas = "";
		if(buff.toString().length()>0){
			datas = buff.toString().substring(0, buff.toString().length()-1);
		}
		jsonString="["+datas+"]";
		return SUCCESS;
	}
}
