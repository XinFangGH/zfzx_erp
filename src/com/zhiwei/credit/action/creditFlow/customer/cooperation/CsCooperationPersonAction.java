package com.zhiwei.credit.action.creditFlow.customer.cooperation;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationPerson;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.cooperation.CsCooperationPersonService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.system.DictionaryService;
/**
 * 
 * @author 
 *
 */
public class CsCooperationPersonAction extends BaseAction{
	@Resource
	private CsCooperationPersonService csCooperationPersonService;
	private CsCooperationPerson csCooperationPerson;
	@Resource
	private DictionaryService dictionaryService;
	@Resource 
	private BpCustMemberService bpCustMemberService;
	@Resource
	private BpCustRelationService bpCustRelationService;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsCooperationPerson getCsCooperationPerson() {
		return csCooperationPerson;
	}

	public void setCsCooperationPerson(CsCooperationPerson csCooperationPerson) {
		this.csCooperationPerson = csCooperationPerson;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		QueryFilter filter=new QueryFilter(getRequest());
		String name=this.getRequest().getParameter("name");
		String cardNumber=this.getRequest().getParameter("cardNumber");
		String tempType=this.getRequest().getParameter("type");
		Boolean isHideP2PAccount = Boolean.valueOf(this.getRequest().getParameter("isHideP2PAccount"));
		if(null!=name && !"".equals(name)){
			filter.addFilter("Q_name_S_LK",name);
		}
		if(null!=cardNumber && !"".equals(cardNumber)){
			filter.addFilter("Q_cardNumber_S_LK",cardNumber);
		}
		if(null!=tempType && !"".equals(tempType)){
			if("lenders".equals(tempType)){//lenders=个人债权,需要将原来的个人理财顾问的数据也要加载出来
				List<String> list=new ArrayList<String>();
				list.add("lenders");
				list.add("financial");
				filter.addFilterIn("Q_type_S_IN",list);
			}else{
				filter.addFilter("Q_type_S_EQ",tempType);
			}
		}
		String flag=this.getRequest().getParameter("flag");
		if(!isHideP2PAccount){//当为理财产品时，查询的时候不用开通P2P账号
			if(null!=flag && "1".equals(flag)){
				filter.addFilter("Q_isOpenP2P_S_EQ",0);
			}
		}
		List<CsCooperationPerson> list= csCooperationPersonService.getAll(filter);
		
		if(list!=null&&list.size()>0){
			for(int i = 0 ; i < list.size() ; i++) {
				CsCooperationPerson cs = list.get(i);
				csCooperationPersonService.evict(cs);
				//证件类型
				if(cs.getCardType()!=null){
					Dictionary dictionary = dictionaryService.get(Long.valueOf(cs.getCardType()));
					if (dictionary!=null) {
						cs.setCardType(dictionary.getItemValue());
					}
				}
				//p2p信息
				if("0".equals(cs.getIsOpenP2P())){
					bpCustMemberService.setP2PInfo(cs);
				}
			}
		}
		
		Type type=new TypeToken<List<CsCooperationPerson>>(){}.getType();
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
				csCooperationPersonService.remove(new Long(id));
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
		CsCooperationPerson csCooperationPerson=csCooperationPersonService.get(id);
		//查询该客户是否已经开通过p2p账号及实名认证
		List<BpCustRelation> br_list=bpCustRelationService.getByCustIdAndCustType(csCooperationPerson.getId(),"p_cooperation");
		if(null!=br_list && br_list.size()>0){
			BpCustMember bm=bpCustMemberService.get(br_list.get(0).getP2pCustId());
			if(null!=bm){
				csCooperationPerson.setIsCheckCard(bm.getIsCheckCard());
			}
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(csCooperationPerson));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(csCooperationPerson.getId()==null){
			csCooperationPersonService.save(csCooperationPerson);
		}else{
			CsCooperationPerson orgCsCooperationPerson=csCooperationPersonService.get(csCooperationPerson.getId());
			try{
				BeanUtil.copyNotNullProperties(orgCsCooperationPerson, csCooperationPerson);
				csCooperationPersonService.save(orgCsCooperationPerson);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	/**
	 *  启用和禁用合作机构P2P账户
	 */
	public String p2pisForbidden(){
		
		
		if(csCooperationPerson.getId()!=null){
			CsCooperationPerson csCp=csCooperationPersonService.get(csCooperationPerson.getId());
			csCp.setIsUsing(csCooperationPerson.getIsUsing());
			BpCustMember bpCustMember = bpCustMemberService.setP2PInfo(csCp);
			if(bpCustMember!=null){
				if("0".equals(csCp.getIsUsing())){
					bpCustMember.setIsForbidden(null);
				}else if("1".equals(csCp.getIsUsing())){
					bpCustMember.setIsForbidden(Integer.valueOf(1));
				}
				bpCustMemberService.save(bpCustMember);
				csCooperationPersonService.save(csCp);
				setJsonString("{success:true}");
			}else{
				setJsonString("{success:true,msg:'P2P账户已不存在!!!'}");
			}
			
		}
		
		return SUCCESS;
		
		
	}
	
	
	
	/**
	 * 查询个人网站账户管理
	 */
	public String listAccount(){
		PagingBean pb=new PagingBean(start,limit);
		Map<?,?> map=ContextUtil.createResponseMap(this.getRequest());
		List<CsCooperationPerson> list=csCooperationPersonService.accountList(map,pb);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pb.getTotalItems()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	/**
	 * 验证证件号码是否存在
	 */
	public void verification(){
		try{
		String cardNum=this.getRequest().getParameter("cardNum");
		String personId=this.getRequest().getParameter("personId");
		if(null==personId || "".equals(personId) || "0".equals(personId)){
			CsCooperationPerson csCooperationPerson=csCooperationPersonService.queryByCardnumber(cardNum);
			if(null!=csCooperationPerson){
				JsonUtil.responseJsonString("{success:true,msg:false}");
			}else{
				JsonUtil.responseJsonString("{success:true,msg:true}");
			}
		}else{
			CsCooperationPerson csCooperationPerson=csCooperationPersonService.get(Long.valueOf(personId));
			if(!csCooperationPerson.getCardNumber().equals(cardNum)){
				CsCooperationPerson c=csCooperationPersonService.queryByCardnumber(cardNum);
				if(null!=c){
					JsonUtil.responseJsonString("{success:true,msg:false}");
				}else{
					JsonUtil.responseJsonString("{success:true,msg:true}");
				}
			}else{
				JsonUtil.responseJsonString("{success:true,msg:true}");
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//查询资金账户信息
	public String fianceAccountList(){
		PagingBean pb=new PagingBean(start,limit);
		Map<?,?> map=ContextUtil.createResponseMap(this.getRequest());
		List<CsCooperationPerson> list= csCooperationPersonService.getAllAccountList(map,pb);
		Type type=new TypeToken<List<CsCooperationPerson>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pb.getTotalItems()).append(",result:");
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	
	
	
	
}
