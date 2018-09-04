package com.zhiwei.credit.action.creditFlow.riskControl.creditInvestigation;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpLoneExternal;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.riskControl.creditInvestigation.BpLoneExternalService;
/**
 * 
 * @author 
 *
 */
public class BpLoneExternalAction extends BaseAction{
	@Resource
	private BpLoneExternalService bpLoneExternalService;
	private BpLoneExternal bpLoneExternal;
	
	private Long externalId;

	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

	public BpLoneExternal getBpLoneExternal() {
		return bpLoneExternal;
	}

	public void setBpLoneExternal(BpLoneExternal bpLoneExternal) {
		this.bpLoneExternal = bpLoneExternal;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		PagingBean pb = new PagingBean(start, limit);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
	    List<BpLoneExternal> listS=bpLoneExternalService.bpLoneExternalList(pb, getRequest());
	    Long scount=bpLoneExternalService.bpLoneExternalCount(getRequest());
		     buff=buff.append(scount).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			 buff.append(gson.toJson(listS));
			 buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	/**
	 * 外部借款人登记项目记录导出到Excel中
	 */
	public void outExcel(){
		 List<BpLoneExternal> listS=null;
	   
		 listS=bpLoneExternalService.bpLoneExternalList(null, getRequest());
			String [] tableHeader = {"序号","业务品种","借款人","证件号码","借款金额","借款余额","借款单位","累计逾期天数","开始日期","结束日期","状态"};
		try {
			ExcelHelper.exportAllBpLoneExternalList(listS,tableHeader,"外部借贷项目列表");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				bpLoneExternalService.remove(new Long(id));
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
		BpLoneExternal bpLoneExternal=bpLoneExternalService.get(externalId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpLoneExternal));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpLoneExternal.getExternalId()==null){
			AppUser user= ContextUtil.getCurrentUser();
			bpLoneExternal.setCreator(user.getFullname());
			bpLoneExternal.setCreatorId(user.getUserId());
			bpLoneExternal.setCreateDate(new Date());
			bpLoneExternalService.save(bpLoneExternal);
		}else{
			BpLoneExternal orgBpLoneExternal=bpLoneExternalService.get(bpLoneExternal.getExternalId());
			try{
				BeanUtil.copyNotNullProperties(orgBpLoneExternal, bpLoneExternal);
				bpLoneExternalService.save(orgBpLoneExternal);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
