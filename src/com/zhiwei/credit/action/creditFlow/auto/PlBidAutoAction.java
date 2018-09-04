package com.zhiwei.credit.action.creditFlow.auto;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.CriteriaCommand;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.auto.PlBidAuto;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.service.creditFlow.auto.PlBidAutoService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
/**
 * 
 * @author 
 *
 */
public class PlBidAutoAction extends BaseAction{
	@Resource
	private PlBidAutoService plBidAutoService;
	private PlBidAuto plBidAuto;
	@Resource
	private BpCustMemberService bpCustMemberService;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlBidAuto getPlBidAuto() {
		return plBidAuto;
	}

	public void setPlBidAuto(PlBidAuto plBidAuto) {
		this.plBidAuto = plBidAuto;
	}

	/**
	 * 显示列表
	 */
	@SuppressWarnings("unchecked")
	public String list(){
		HttpServletRequest req=this.getRequest();
		QueryFilter filter=new QueryFilter(req);
		String truename=this.getRequest().getParameter("truename");
		String ids = "";
		if(truename!=null&&!"".equals(truename)){
			List<BpCustMember> memList=bpCustMemberService.getByTrueName(truename);
			if(memList!=null&&memList.size()>0){
				List<CriteriaCommand> comm = filter.getCommands();
				if(comm!=null&&comm.size()>0){
					comm.remove(0);
				}
				for(BpCustMember mem : memList){
					ids += mem.getId()+",";
				}
				ids = ids.substring(0, ids.length()-1);
			}else{
				ids = "0";
			}
		}
		String isOpen = this.getRequest().getParameter("Q_isOpen_N_EQ");
		String banned = this.getRequest().getParameter("Q_banned_N_EQ");
		//List<PlBidAuto> list= plBidAutoService.getAll(filter);
		List<PlBidAuto> list= plBidAutoService.queryCardcode(ids, isOpen,banned,start,limit);
		List<PlBidAuto> listCount= plBidAutoService.queryCardcode(ids,isOpen,banned,null,null);
		List<PlBidAuto> currlist=new ArrayList<PlBidAuto>();
		if(list!=null&&list.size()>0){
			for(PlBidAuto auto:list){
			//	System.out.println(auto.getUserID());
				if(auto.getUserID()!=null){
					BpCustMember  bp=bpCustMemberService.get(auto.getUserID());
					if(bp!=null){
						String userName=bp.getTruename();
						auto.setUserName(userName);
					}
					
				}
				currlist.add(auto);
			}
		}
		
		Type type=new TypeToken<List<PlBidAuto>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		//.append(filter.getPagingBean().getTotalItems()).append(",result:");
		.append(listCount!=null?listCount.size():0).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		buff.append(gson.toJson(currlist, type));
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
				plBidAutoService.remove(new Long(id));
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
		PlBidAuto plBidAuto=plBidAutoService.get(id);
		BpCustMember mem=bpCustMemberService.get(plBidAuto.getUserID());
		plBidAuto.setUserName(mem.getTruename());
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plBidAuto));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plBidAuto.getId()==null){
			plBidAutoService.save(plBidAuto);
		}else{
			String ret=plBidAutoService.savechk(plBidAuto);
			if(ret.indexOf(Constants.FAILDFLAG)==-1){
				PlBidAuto orgPlBidAuto=plBidAutoService.get(plBidAuto.getId());
				try{
					BeanUtil.copyNotNullProperties(orgPlBidAuto, plBidAuto);
					plBidAutoService.save(orgPlBidAuto);
				}catch(Exception ex){
					logger.error(ex.getMessage());
				}
				setJsonString("{success:true,msg:'保存成功'}");
			}else{
				jsonString="{\"success\":false,"+ret.substring(1)+"";
			}
			
		}
		
		return SUCCESS;
		
	}
}
