package com.zhiwei.credit.action.creditFlow.financingAgency.business;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessOrProService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpBusinessOrProAction extends BaseAction{
	@Resource
	private BpBusinessOrProService bpBusinessOrProService;
	private BpBusinessOrPro bpBusinessOrPro;
	
	private Long borProId;

	public Long getBorProId() {
		return borProId;
	}

	public void setBorProId(Long borProId) {
		this.borProId = borProId;
	}

	public BpBusinessOrPro getBpBusinessOrPro() {
		return bpBusinessOrPro;
	}

	public void setBpBusinessOrPro(BpBusinessOrPro bpBusinessOrPro) {
		this.bpBusinessOrPro = bpBusinessOrPro;
	}
	/**
	 * 统计发标情况
	 */
	@SuppressWarnings("null")
	public String listPublish() {

		QueryFilter filter = new QueryFilter(getRequest());
		filter.addSorted("proNumber", "DESC");
		List<BpBusinessOrPro> list = bpBusinessOrProService.getAll(filter);
		List<BpBusinessOrPro> listCurr = new ArrayList<BpBusinessOrPro>();
		for (BpBusinessOrPro pack : list) {
			// 计算打包项目的剩余金额
			pack = bpBusinessOrProService.residueMoneyMeth(pack);
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
System.out.println(buff.toString());
		return SUCCESS;
	}
	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		filter.addSorted("createTime", "DESC");
		filter.addSorted("proNumber", "DESC");
		List<BpBusinessOrPro> list= bpBusinessOrProService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
			.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer();
		serializer.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"loanStarTime",
			"createTime", "updateTime","loanEndTime","bidTime"});
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
				bpBusinessOrProService.remove(new Long(id));
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
		BpBusinessOrPro bpBusinessOrPro=bpBusinessOrProService.get(borProId);

		bpBusinessOrPro = bpBusinessOrProService.residueMoneyMeth(bpBusinessOrPro);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer();
		serializer.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"loanStarTime",
			"createTime", "updateTime","loanEndTime","bidTime"});
		sb.append(serializer.exclude(new String[] { "class"}).serialize(bpBusinessOrPro));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		BpBusinessOrPro bpBusinessOrPro=bpBusinessOrProService.get(borProId);
		if(null == bpBusinessOrPro.getDisclosureCreateDate() || "" .equals( bpBusinessOrPro.getDisclosureCreateDate())){
			bpBusinessOrPro.setDisclosureCreateDate(new Date());
		}
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer( "bidTime", "createTime",
				"updateTime","loanStarTime","loanEndTime","disclosureCreateDate","disclosureUpdateDate");
		sb.append(serializer.exclude(new String[] { "class"}).serialize(bpBusinessOrPro));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpBusinessOrPro.getBorProId()==null){
			bpBusinessOrProService.save(bpBusinessOrPro);
		}else{
			BpBusinessOrPro orgBpBusinessOrPro=bpBusinessOrProService.get(bpBusinessOrPro.getBorProId());
			try{
				BeanUtil.copyNotNullProperties(orgBpBusinessOrPro, bpBusinessOrPro);
				bpBusinessOrProService.save(orgBpBusinessOrPro);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 显示企业展期债权表列表
	 */
	public String bpSuperviseList(){
		PagingBean pb = new PagingBean(start, limit);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
	    List<BpBusinessOrPro> listS=bpBusinessOrProService.bpBusinessOrProList(pb, getRequest());
	    Long scount=bpBusinessOrProService.bpBusinessOrProCount(getRequest());
		     buff=buff.append(scount).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			 buff.append(gson.toJson(listS));
			 buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
}
