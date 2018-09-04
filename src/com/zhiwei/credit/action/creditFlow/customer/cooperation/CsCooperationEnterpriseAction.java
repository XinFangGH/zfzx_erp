package com.zhiwei.credit.action.creditFlow.customer.cooperation;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
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
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationEnterprise;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.cooperation.CsCooperationEnterpriseService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.system.DictionaryService;
/**
 * 
 * @author 
 *
 */
public class CsCooperationEnterpriseAction extends BaseAction{
	@Resource
	private CsCooperationEnterpriseService csCooperationEnterpriseService;
	private CsCooperationEnterprise csCooperationEnterprise;
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

	public CsCooperationEnterprise getCsCooperationEnterprise() {
		return csCooperationEnterprise;
	}

	public void setCsCooperationEnterprise(CsCooperationEnterprise csCooperationEnterprise) {
		this.csCooperationEnterprise = csCooperationEnterprise;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		Boolean isHideP2PAccount = Boolean.valueOf(this.getRequest().getParameter("isHideP2PAccount"));
		QueryFilter filter=new QueryFilter(getRequest());
		if(!isHideP2PAccount){//当为理财产品时，查询的时候不用开通P2P账号
			filter.addFilter("Q_isOpenP2P_S_EQ",0);
		}
		List<CsCooperationEnterprise> list= csCooperationEnterpriseService.getAll(filter);
		
		if(list!=null&&list.size()>0){
			for(int i = 0 ; i < list.size() ; i++) {
				CsCooperationEnterprise cs = list.get(i);
				csCooperationEnterpriseService.evict(cs);
				/*//机构类型
				if(cs.getType()!=null){
					Dictionary dictionary = dictionaryService.get(Long.valueOf(cs.getType()));
					if (dictionary!=null) {
						cs.setType(dictionary.getItemValue());
					}
				}*/
				//机构来源
				if(cs.getTypeFrom()!=null){
					Dictionary dictionary = dictionaryService.get(Long.valueOf(cs.getTypeFrom()));
					if (dictionary!=null) {
						cs.setTypeFrom(dictionary.getItemValue());
					}
				}
				if("0".equals(cs.getIsOpenP2P())){
					bpCustMemberService.setP2PInfo(cs);
				}
			}
			
		}
		
		Type type=new TypeToken<List<CsCooperationEnterprise>>(){}.getType();
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
				csCooperationEnterpriseService.remove(new Long(id));
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
		CsCooperationEnterprise csCooperationEnterprise=csCooperationEnterpriseService.get(id);
		//查询该客户是否已经开通过p2p账号及实名认证
		List<BpCustRelation> br_list=bpCustRelationService.getByCustIdAndCustType(csCooperationEnterprise.getId(),"b_cooperation");
		if(null!=br_list && br_list.size()>0){
			BpCustMember bm=bpCustMemberService.get(br_list.get(0).getP2pCustId());
			if(null!=bm){
				csCooperationEnterprise.setIsCheckCard(bm.getIsCheckCard());
			}
		}
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(csCooperationEnterprise));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(csCooperationEnterprise.getId()==null){
			csCooperationEnterpriseService.save(csCooperationEnterprise);
		}else{
			CsCooperationEnterprise orgCsCooperationEnterprise=csCooperationEnterpriseService.get(csCooperationEnterprise.getId());
			try{
				BeanUtil.copyNotNullProperties(orgCsCooperationEnterprise, csCooperationEnterprise);
				csCooperationEnterpriseService.save(orgCsCooperationEnterprise);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	
	/**
	 * 查询企业网站账户管理
	 */
	public String listAccount(){
		PagingBean pb=new PagingBean(start,limit);
		Map map=ContextUtil.createResponseMap(this.getRequest());
		List<CsCooperationEnterprise> list=csCooperationEnterpriseService.accountList(map,pb);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
		/* .excludeFieldsWithoutExposeAnnotation() */.create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
		
		
		/*String p2ploginname = this.getRequest().getParameter("p2ploginname");
		QueryFilter filter=new QueryFilter(getRequest());
		List<CsCooperationEnterprise> list= csCooperationEnterpriseService.getAll(filter);

		ArrayList<CsCooperationEnterprise> resultList = new ArrayList<CsCooperationEnterprise>();
		
		if(list!=null&&list.size()>0){
			for(int i = 0 ; i < list.size() ; i++) {
				CsCooperationEnterprise cs = list.get(i);
				csCooperationEnterpriseService.evict(cs);
				//p2p信息
				if("0".equals(cs.getIsOpenP2P())){
					bpCustMemberService.setP2PInfo(cs);
				}
				
				if(p2ploginname!=null&&!"".equals(p2ploginname)){
					if(cs.getP2ploginname()!=null&&cs.getP2ploginname().contains(p2ploginname)){
						resultList.add(cs);
					}
				}else{
					resultList.add(cs);
				}
				
			}
		}
		
		
		Type type=new TypeToken<List<CsCooperationPerson>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()-((list==null?0:list.size())-resultList.size())).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(resultList, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;*/
	}
	
	/**
	 *  启用和禁用合作机构P2P账户
	 */
	public String p2pisForbidden(){
		if(csCooperationEnterprise.getId()!=null){
			CsCooperationEnterprise csCe=csCooperationEnterpriseService.get(csCooperationEnterprise.getId());
			csCe.setIsUsing(csCooperationEnterprise.getIsUsing());
			BpCustMember bpCustMember = bpCustMemberService.setP2PInfo(csCe);
			if(null!=bpCustMember){
				if("0".equals(csCe.getIsUsing())){
					bpCustMember.setIsForbidden(null);
				}else if("1".equals(csCe.getIsUsing())){
					bpCustMember.setIsForbidden(Integer.valueOf(1));
				}
				bpCustMemberService.save(bpCustMember);
				csCooperationEnterpriseService.save(csCe);
				setJsonString("{success:true}");
			}else{
				setJsonString("{success:true,msg:'P2P账户已不存在!!!'}");
			}
		}
		return SUCCESS;
		
		
		
	}
	
	
	//查询出来合作渠道的资金账号信息
	public String fianceAccountList(){
		PagingBean pb=new PagingBean(start,limit);
		Map map=ContextUtil.createResponseMap(this.getRequest());
		List<CsCooperationEnterprise> list= csCooperationEnterpriseService.getAllAccountList(map,pb);
		Type type=new TypeToken<List<CsCooperationEnterprise>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:");
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
}
