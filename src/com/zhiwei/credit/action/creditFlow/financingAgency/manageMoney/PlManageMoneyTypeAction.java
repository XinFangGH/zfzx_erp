package com.zhiwei.credit.action.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
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


import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyType;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyTypeService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class PlManageMoneyTypeAction extends BaseAction{
	@Resource
	private PlManageMoneyTypeService plManageMoneyTypeService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	private PlManageMoneyType plManageMoneyType;
	
	
	private Long manageMoneyTypeId;

	public Long getManageMoneyTypeId() {
		return manageMoneyTypeId;
	}

	public void setManageMoneyTypeId(Long manageMoneyTypeId) {
		this.manageMoneyTypeId = manageMoneyTypeId;
	}

	public PlManageMoneyType getPlManageMoneyType() {
		return plManageMoneyType;
	}

	public void setPlManageMoneyType(PlManageMoneyType plManageMoneyType) {
		this.plManageMoneyType = plManageMoneyType;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<PlManageMoneyType> list= plManageMoneyTypeService.getAll(filter);
		
		Type type=new TypeToken<List<PlManageMoneyType>>(){}.getType();
		

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"),new String[]{"nextPublisPlanTime"});
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
		if(ids!=null){
			for(String id:ids){
				plManageMoneyType=	plManageMoneyTypeService.get(new Long(id));
				plManageMoneyType.setState(1);
				plManageMoneyTypeService.save(plManageMoneyType);
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
		PlManageMoneyType plManageMoneyType=plManageMoneyTypeService.get(manageMoneyTypeId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plManageMoneyType));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	public String getListbykeystr(){
		String keystr=this.getRequest().getParameter("keystr");
		List<PlManageMoneyType> list= plManageMoneyTypeService.getListbykeystr(keystr);
		
		StringBuffer buff = new StringBuffer("[");
		for(PlManageMoneyType bank:list){
			buff.append("[").append(bank.getManageMoneyTypeId()).append(",'")
			.append(bank.getName()).append("','").append(bank.getReceivablesAccount())
			.append("','").append(bank.getAccountType())
			.append("'],");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		StringBuffer sb = new StringBuffer();
		String keystr=this.getRequest().getParameter("keystr");
		plManageMoneyType.setKeystr(keystr);
		if(keystr.equals("mmproduce")){//理财产品类型保存
			if(plManageMoneyType.getManageMoneyTypeId()==null){
				plManageMoneyType.setState(0);
				plManageMoneyTypeService.save(plManageMoneyType);
				sb.append("{success:true}");
			}else{
				PlManageMoneyType orgPlManageMoneyType=plManageMoneyTypeService.get(plManageMoneyType.getManageMoneyTypeId());
				try{
					BeanUtil.copyNotNullProperties(orgPlManageMoneyType, plManageMoneyType);
					plManageMoneyTypeService.save(orgPlManageMoneyType);
					sb.append("{success:true}");
				}catch(Exception ex){
					logger.error(ex.getMessage());
				}
			}
		}else{//当类型不为理财产品时，进行收款账户是否存在验证
			BpCustMember member = bpCustMemberService.isExist(plManageMoneyType.getReceivablesAccount());
			if(null == member && plManageMoneyType.getAccountType().equals("zc")){//收款账户不存在
				sb.append("{success:true,id:0,msg:'保存失败，原始债权人专户不存在！'}");
			}else if (member.getThirdPayFlagId() == null || "".equals(member.getThirdPayFlagId())){
				sb.append("{success:true,id:0,msg:'保存失败，原始债权人专户没有开通第三方账户！'}");
			}else{
				if(plManageMoneyType.getManageMoneyTypeId()==null){
					plManageMoneyType.setState(0);
					plManageMoneyTypeService.save(plManageMoneyType);
					sb.append("{success:true,id:0,msg:\"保存成功！\"}");
				}else{
					PlManageMoneyType orgPlManageMoneyType=plManageMoneyTypeService.get(plManageMoneyType.getManageMoneyTypeId());
					try{
						BeanUtil.copyNotNullProperties(orgPlManageMoneyType, plManageMoneyType);
						plManageMoneyTypeService.save(orgPlManageMoneyType);
						sb.append("{success:true,id:0,msg:\"保存成功！\"}");
					}catch(Exception ex){
						logger.error(ex.getMessage());
					}
				}
			}
		}
		setJsonString(sb.toString());
		return SUCCESS;
		
	}
	
	/**
	 * U计划判断该类型是否被使用
	 */
	public String yesOrNo(){
		String manageMoneyTypeId=this.getRequest().getParameter("manageMoneyTypeId");
		PlManageMoneyPlan plManageMoneyPlan=plManageMoneyTypeService.yesOrNo(Long.valueOf(manageMoneyTypeId) );
		StringBuffer sb = new StringBuffer();
		if (plManageMoneyPlan != null) {//已使用
			sb.append("{\"success\":false");
			sb.append(",\"result\":1");
			sb.append("}");
		} else {//未使用
			sb.append("{\"success\":true");
			sb.append(",\"result\":0");
			sb.append("}");
		}
		
		setJsonString(sb.toString());
		return SUCCESS;
		
	}
	/**
	 * 删除U计划
	 */
	public String delete(){
		String manageMoneyTypeId=getRequest().getParameter("manageMoneyTypeId");
		StringBuffer sb = new StringBuffer();
		if(manageMoneyTypeId!=null){
			plManageMoneyType=	plManageMoneyTypeService.get(Long.valueOf(manageMoneyTypeId));
			PlManageMoneyPlan plManageMoneyPlan=plManageMoneyTypeService.yesOrNo(Long.valueOf(manageMoneyTypeId));
			if(null !=plManageMoneyPlan){
				sb.append("{\"success\":false,\"result\":'此类型已使用，不能删除！'}");
			}else{
				plManageMoneyType.setState(1);
				plManageMoneyTypeService.save(plManageMoneyType);
				sb.append("{\"success\":true,\"result\":'删除成功！'}");
			}
		}
		setJsonString(sb.toString());
		return SUCCESS;
	}
}
