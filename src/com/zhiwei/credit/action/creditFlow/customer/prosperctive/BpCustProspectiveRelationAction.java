package com.zhiwei.credit.action.creditFlow.customer.prosperctive;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProspectiveRelation;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive;
import com.zhiwei.credit.service.creditFlow.customer.prosperctive.BpCustProspectiveRelationService;
import com.zhiwei.credit.service.creditFlow.customer.prosperctive.BpCustProsperctiveService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustProspectiveRelationAction extends BaseAction{
	@Resource
	private BpCustProspectiveRelationService bpCustProspectiveRelationService;
	@Resource
	private BpCustProsperctiveService bpCustProsperctiveService;
	private BpCustProspectiveRelation bpCustProspectiveRelation;
	
	private Long relateId;

	public Long getRelateId() {
		return relateId;
	}

	public void setRelateId(Long relateId) {
		this.relateId = relateId;
	}

	public BpCustProspectiveRelation getBpCustProspectiveRelation() {
		return bpCustProspectiveRelation;
	}

	public void setBpCustProspectiveRelation(BpCustProspectiveRelation bpCustProspectiveRelation) {
		this.bpCustProspectiveRelation = bpCustProspectiveRelation;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpCustProspectiveRelation> list= bpCustProspectiveRelationService.getAll(filter);
		
		Type type=new TypeToken<List<BpCustProspectiveRelation>>(){}.getType();
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
				bpCustProspectiveRelationService.remove(new Long(id));
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
		BpCustProspectiveRelation bpCustProspectiveRelation=bpCustProspectiveRelationService.get(relateId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustProspectiveRelation));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存list
	 */
	public String saveList(){
		String perId =this.getRequest().getParameter("perId");
		String relationPerson =this.getRequest().getParameter("relationPerson");
		if(perId!=null && !"".equals(perId) &&  !"null".equals(perId) ){
			BpCustProsperctive person =bpCustProsperctiveService.get(Long.valueOf(perId));
			if(person!=null){
				bpCustProspectiveRelationService.saveRelationData(relationPerson, person);
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 根据潜在客户的Id查出来客户的联系人
	 * @return
	 */
	public String listByPerId(){
		String perId =this.getRequest().getParameter("perId");
		if(perId!=null&&!"".equals(perId)&&!"null".equals(perId)&&!"undefined".equals(perId)){
			List<BpCustProspectiveRelation> list= bpCustProspectiveRelationService.listByPerId(perId);
			StringBuffer buff = new StringBuffer("{success:true,result:");
			JSONSerializer serializer=new JSONSerializer();
			buff.append(serializer.transform(new DateTransformer("yyyy-MM-dd"), "loanDate").exclude(new String[]{"class"}).serialize(list));
			buff.append("}");
			jsonString=buff.toString();
		}
		return SUCCESS;
	}
}
