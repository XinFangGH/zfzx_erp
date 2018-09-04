package com.zhiwei.credit.action.creditFlow.guarantee.guaranteefinance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBank;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountRecord;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountRecordService;
import com.zhiwei.credit.service.system.CsDicAreaDynamService;
/**
 * 
 * @author 
 *
 */
public class GlAccountBankAction extends BaseAction{
	@Resource
	private GlAccountBankService glAccountBankService;
	@Resource
	private CsDicAreaDynamService csDicAreaDynamService;
	@Resource
	private GlAccountBankCautionmoneyService glAccountBankCautionmoneyService;
	@Resource
	private GlAccountRecordService glAccountRecordService;
	@Resource
	private GlBankGuaranteemoneyService glBankGuaranteemoneyService;
	private GlAccountBank glAccountBank;
	private String node;
	
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}
	private Long glAccountBankId;

	
	public Long getGlAccountBankId() {
		return glAccountBankId;
	}

	public void setGlAccountBankId(Long glAccountBankId) {
		this.glAccountBankId = glAccountBankId;
	}

	public GlAccountBank getGlAccountBank() {
		return glAccountBank;
	}

	public void setGlAccountBank(GlAccountBank glAccountBank) {
		this.glAccountBank = glAccountBank;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<GlAccountBank> list= glAccountBankService.getAll(filter);
		
		Type type=new TypeToken<List<GlAccountBank>>(){}.getType();
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
				glAccountBankService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
public String delete(){
		List<GlAccountBankCautionmoney> list=glAccountBankCautionmoneyService.getbyparentId(glAccountBankId);
		List<GlAccountRecord> list1=glAccountRecordService.getallbyglAccountBankId(glAccountBankId,0,999999999);
		List<GlBankGuaranteemoney> list2=glBankGuaranteemoneyService.getallbyglAccountBankId(glAccountBankId,0,999999999);
		for(GlAccountRecord s:list1){
			s.setIdDelete(Short.valueOf("1"));
			glAccountRecordService.save(s);
		}
//		for(GlBankGuaranteemoney t:list2){
//			glBankGuaranteemoneyService.remove(t);
//		}
		for(GlAccountBankCautionmoney l:list){
			l.setIdDelete(Short.valueOf("1"));
			glAccountBankCautionmoneyService.save(l);
		}
		GlAccountBank glAccountBank=glAccountBankService.get(glAccountBankId);
		glAccountBank.setIdDelete(Short.valueOf("1"));
		glAccountBankService.remove(glAccountBank);
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		GlAccountBank glAccountBank=glAccountBankService.get(glAccountBankId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(glAccountBank));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
	//	glAccountBank.setGlAccountBankId(null);
		if(glAccountBank.getId()==null){
			List<GlAccountBank> listglAccountBank=glAccountBankService.getalllist();
			for(GlAccountBank l:listglAccountBank){
				
				if(l.getBankParentId().equals(glAccountBank.getBankParentId())){
					JsonUtil.responseJsonString("{success:true,exsit:false,msg:'新增<授信银行>信息失败!!!'}");
					return SUCCESS;
				}
			
			}
			glAccountBank.setText(csDicAreaDynamService.get(glAccountBank.getBankParentId()).getText());
			Date d=new Date();
			Long companyId = ContextUtil.getLoginCompanyId();//add by gaoqingrui 保存登录用户id
			glAccountBank.setId(d.getTime());
			glAccountBank.setCreateDate(new Date());
			glAccountBank.setLeaf(false);
			glAccountBank.setCompanyId(companyId);
			glAccountBankService.save(glAccountBank);
			 JsonUtil.responseJsonString("{success:true,exsit:true,msg:'新增<授信银行>信息成功!!!'}");
				return SUCCESS;
		}else{
			GlAccountBank orgGlAccountBank=glAccountBankService.get(glAccountBank.getId());
			try{
				BeanUtil.copyNotNullProperties(orgGlAccountBank, glAccountBank);
				glAccountBankService.save(orgGlAccountBank);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
	
		return SUCCESS;
		
	}
	public String getAccountBankTree(){
		
		Long companyId = ContextUtil.getLoginCompanyId();
		String isNotController = this.getRequest().getParameter("isNotController");
//		boolean flag = Boolean.getBoolean(isNotController);
		boolean flag = Boolean.valueOf(isNotController);
		String s = null;
		if(flag){
			s = glAccountBankService.getAccountBankTree(node);	
		}else{
			s=glAccountBankService.getAccountBankTree(node,companyId.toString());	
		}
		
		
		JsonUtil.responseJsonString(s);
		return SUCCESS;
	}
	
	
}
