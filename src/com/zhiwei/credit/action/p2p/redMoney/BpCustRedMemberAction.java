package com.zhiwei.credit.action.p2p.redMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
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


import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedEnvelope;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedMember;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.redMoney.BpCustRedEnvelopeService;
import com.zhiwei.credit.service.p2p.redMoney.BpCustRedMemberService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustRedMemberAction extends BaseAction{
	@Resource
	private BpCustRedMemberService bpCustRedMemberService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private BpCustRedEnvelopeService bpCustRedEnvelopeService;
	
	
	private BpCustRedMember bpCustRedMember;
	
	private Long redTopersonId;

	public Long getRedTopersonId() {
		return redTopersonId;
	}

	public void setRedTopersonId(Long redTopersonId) {
		this.redTopersonId = redTopersonId;
	}

	public BpCustRedMember getBpCustRedMember() {
		return bpCustRedMember;
	}

	public void setBpCustRedMember(BpCustRedMember bpCustRedMember) {
		this.bpCustRedMember = bpCustRedMember;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		QueryFilter filter=new QueryFilter(getRequest());
		String bpCustMemberName = this.getRequest().getParameter("bpCustMemberName");
		String activityNumber = this.getRequest().getParameter("activityNumber");
		String distributeTime = this.getRequest().getParameter("distributeTime");
		String trueName = this.getRequest().getParameter("trueName");
		BpCustMember member=null;
		if(bpCustMemberName!=null&&!bpCustMemberName.equals("")){
			member = bpCustMemberService.getMemberUserName(bpCustMemberName);
			if(member!=null){
				filter.addFilter("Q_bpCustMemberId_L_EQ", member.getId());
			}else{
				filter.addFilter("Q_bpCustMemberId_L_EQ", "01");
			}
		}
		if(activityNumber!=null&&!activityNumber.equals("")){
			filter.addFilter("Q_activityNumber_S_EQ", activityNumber);
		}
		if(distributeTime!=null&&!distributeTime.equals("")){
			filter.addFilter("Q_distributeTime_D_LT", distributeTime+" 23:59:59");
			filter.addFilter("Q_distributeTime_D_GT", distributeTime+" 00:00:00");
		}
		
		filter.addSorted("distributeTime", "DESC");
		List<BpCustRedMember> list= bpCustRedMemberService.getAll(filter);
		ArrayList<BpCustRedMember> listResult = new ArrayList<BpCustRedMember>();
		if(list!=null&&list.size()>0){
			for(BpCustRedMember b : list){
				bpCustRedMemberService.evict(b);
				BpCustMember bpCustMember = bpCustMemberService.get(b.getBpCustMemberId());
				b.setLogginName(bpCustMember.getLoginname());
				if(bpCustMember!=null){
					b.setBpCustMemberName(bpCustMember.getTruename());
				}
				if(trueName==null||"".equals(trueName)||(b.getBpCustMemberName()!=null&&b.getBpCustMemberName().contains(trueName))){
					listResult.add(b);
				}
			}
		}
		Type type=new TypeToken<List<BpCustRedMember>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		if(listResult!=null&&listResult.size()>0){
			buff.append(gson.toJson(listResult, type));
		}else{
			buff.append(gson.toJson(list, type));
		}
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	public void exportExcel(){
		QueryFilter filter=new QueryFilter(getRequest());
		String bpCustMemberName = this.getRequest().getParameter("bpCustMemberName");
		String activityNumber = this.getRequest().getParameter("activityNumber");
		String distributeTime = this.getRequest().getParameter("distributeTime");
		String trueName = this.getRequest().getParameter("trueName");
		BpCustMember member=null;
		if(bpCustMemberName!=null&&!bpCustMemberName.equals("")){
			member = bpCustMemberService.getMemberUserName(bpCustMemberName);
		}
		if(member!=null){
			filter.addFilter("Q_bpCustMemberId_L_EQ", member.getId());
		}
		if(activityNumber!=null&&!activityNumber.equals("")){
			filter.addFilter("Q_activityNumber_S_EQ", activityNumber);
		}
		if(distributeTime!=null&&!distributeTime.equals("")){
			filter.addFilter("Q_distributeTime_D_LT", distributeTime+" 23:59:59");
			filter.addFilter("Q_distributeTime_D_GT", distributeTime+" 00:00:00");
		}
		filter.addSorted("distributeTime", "DESC");
		List<BpCustRedMember> list= bpCustRedMemberService.getAll(filter);
		ArrayList<BpCustRedMember> listResult = new ArrayList<BpCustRedMember>();
		if(list!=null&&list.size()>0){
			for(BpCustRedMember b : list){
				bpCustRedMemberService.evict(b);
				BpCustMember bpCustMember = bpCustMemberService.get(b.getBpCustMemberId());
				b.setLogginName(bpCustMember.getLoginname());
				if(bpCustMember!=null){
					b.setBpCustMemberName(bpCustMember.getTruename());
				}
				if(trueName==null||"".equals(trueName)||(b.getBpCustMemberName()!=null&&b.getBpCustMemberName().contains(trueName))){
					listResult.add(b);
				}
			}
		}
		
		String[] tableHeader = { "序号","用户名", "投资人姓名", "金额","派发时间","活动编号","说明"};
		String[] fields = {"logginName","bpCustMemberName","redMoney","distributeTime","activityNumber","description"};
		try {
			if(listResult!=null&&listResult.size()>0){
				ExportExcel.export(tableHeader, fields, listResult,"红包派发记录", BpCustRedMember.class);
			}else{
				ExportExcel.export(tableHeader, fields, list,"红包派发记录", BpCustRedMember.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
      public String listbyredId(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		String redId=getRequest().getParameter("redId");
		String types=getRequest().getParameter("type");
		List<BpCustRedMember> list=new ArrayList<BpCustRedMember>();
		if(null!=redId&&!"".equals(redId)&&!"undefined".equals(redId)){
			list= bpCustRedMemberService.listbyredId(Long.valueOf(redId),types);
		}
		
		BpCustMember	bpCustMember=new BpCustMember();
		for(BpCustRedMember l:list){
		   	bpCustMember=bpCustMemberService.get(l.getBpCustMemberId());
		   	l.setEmail(bpCustMember.getEmail());
		   	l.setTelphone(bpCustMember.getTelphone());
		   	l.setLoginname(bpCustMember.getLoginname());
		   	l.setTruename(bpCustMember.getTruename());
		   	l.setThirdPayFlag0(bpCustMember.getThirdPayFlag0());
			
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(",result:");
        JSONSerializer json = JsonUtil.getJSONSerializer("distributeTime");
        json.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), new String[] {"distributeTime"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		String redId=getRequest().getParameter("redId");
		BpCustRedEnvelope bpCustRedEnvelope=	bpCustRedEnvelopeService.get(Long.valueOf(redId));
		if(ids!=null){
			for(String id:ids){
				bpCustRedMember=bpCustRedMemberService.get(new Long(id));
				bpCustRedEnvelope.setDistributemoney(bpCustRedEnvelope.getDistributemoney().subtract(bpCustRedMember.getRedMoney()));
				bpCustRedMemberService.remove(bpCustRedMember);
				
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
		BpCustRedMember bpCustRedMember=bpCustRedMemberService.get(redTopersonId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustRedMember));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpCustRedMember.getRedTopersonId()==null){
			bpCustRedMemberService.save(bpCustRedMember);
		}else{
			BpCustRedMember orgBpCustRedMember=bpCustRedMemberService.get(bpCustRedMember.getRedTopersonId());
			try{
				BeanUtil.copyNotNullProperties(orgBpCustRedMember, bpCustRedMember);
				bpCustRedMemberService.save(orgBpCustRedMember);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
