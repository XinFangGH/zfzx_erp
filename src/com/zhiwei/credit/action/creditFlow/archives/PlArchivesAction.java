package com.zhiwei.credit.action.creditFlow.archives;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.ArrayList;
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
import com.zhiwei.credit.model.creditFlow.archives.PlArchives;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.archives.PlArchivesService;
import com.zhiwei.credit.service.system.OrganizationService;
/**
 * 
 * @author 
 *
 */
public class PlArchivesAction extends BaseAction{
	@Resource
	private PlArchivesService plArchivesService;
	@Resource
	private OrganizationService organizationService;
	private PlArchives plArchives;
	
	private Long id;
    private Long archivesid;
    private String node;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public Long getArchivesid() {
		return archivesid;
	}

	public void setArchivesid(Long archivesid) {
		this.archivesid = archivesid;
	}

	public PlArchives getPlArchives() {
		return plArchives;
	}

	public void setPlArchives(PlArchives plArchives) {
		this.plArchives = plArchives;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<PlArchives> list= plArchivesService.getAll(filter);
		for(PlArchives l:list){
			if(null!=l.getCompanyId()){
				Organization organization=organizationService.get(l.getCompanyId());
				if(null!=organization){
					l.setOrgName(organization.getOrgName());
				}
			
			}
			
		}
		Type type=new TypeToken<List<PlArchives>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
    public String getallcabinet(){
		
		QueryFilter filter=new QueryFilter(getRequest());
	   String  archivesname=this.getRequest().getParameter("archivesname") ;
	   String  companyId=this.getRequest().getParameter("companyId") ;
		List<PlArchives> list= plArchivesService.searchcabinet(archivesname,companyId, start, limit);
		int size=0;
		if(null !=list){
			size=list.size();
			
		}
		for(PlArchives l:list){
			if(null!=l.getCompanyId()){
				Organization organization=organizationService.get(l.getCompanyId());
				if(null!=organization){
					l.setOrgName(organization.getOrgName());
				}
			
			}
			
		}
		Type type=new TypeToken<List<PlArchives>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(size).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
    public String getallchecker(){
    int size=0;
	QueryFilter filter=new QueryFilter(getRequest());
	List<PlArchives> list=new ArrayList<PlArchives>();
	if(archivesid.equals(Long.valueOf(0))){
		list= plArchivesService.getallchecker(start, limit);
		size=plArchivesService.getallcheckersize();
	}else{
		list= plArchivesService.getcheckerbyparentid(archivesid, start, limit);
		size=plArchivesService.getcheckerbyparentidsize(archivesid);
		
	}
	
	
	Type type=new TypeToken<List<PlArchives>>(){}.getType();
	StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
	.append(size).append(",result:");
	
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
				PlArchives plArchives1=plArchivesService.get(new Long(id));
				plArchives1.setIsdelete(Short.valueOf("1"));
				plArchivesService.save(plArchives1);
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
public String delete(){
		
	PlArchives plArchives1=plArchivesService.get(archivesid);
	plArchives1.setIsdelete(Short.valueOf("1"));
	plArchivesService.save(plArchives1);
	List<PlArchives> list =plArchivesService.getcheckerbyparentid(archivesid,0,99999);
	for(PlArchives l:list){
		l.setIsdelete(Short.valueOf("1"));
		plArchivesService.save(l);
	}
	
		jsonString="{success:true}";
		
		return SUCCESS;
	}
public String delete1(){
	
	plArchivesService.remove(archivesid);
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		PlArchives plArchives=plArchivesService.get(archivesid);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plArchives));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		Long companyId=ContextUtil.getLoginCompanyId();
		if(plArchives.getId()==null){
			plArchives.setCompanyId(companyId);
			plArchives.setParentid(Long.valueOf("0"));
			plArchives.setText(plArchives.getName());
			plArchives.setLeaf(false);
			plArchivesService.save(plArchives);
		}else{
			PlArchives orgPlArchives=plArchivesService.get(plArchives.getId());
			try{
				BeanUtil.copyNotNullProperties(orgPlArchives, plArchives);
				plArchives.setParentid(Long.valueOf("0"));
				plArchives.setText(orgPlArchives.getName());
				plArchives.setLeaf(false);
				plArchivesService.save(orgPlArchives);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String save1(){
		int start1=0;
		int end1=0;
	     int gznum=Integer.parseInt(this.getRequest().getParameter("gznum")) ;
		if(null !=plArchivesService.getcheckerbyparentid(archivesid,0,99999)){
			start1=plArchivesService.getcheckerbyparentid(archivesid,0,99999).size()+1;
			end1=plArchivesService.getcheckerbyparentid(archivesid,0,99999).size()+gznum+1;
		};
		for(int i=start1;i<end1;i++){
			PlArchives plArchives=new PlArchives();
			String code="gz"+String.valueOf(i);
			plArchives.setCode(code);
			plArchives.setParentid(archivesid);
			plArchives.setSortorder(i);
			plArchives.setText(plArchives.getCode());
			plArchives.setLeaf(true);
			plArchivesService.save(plArchives);
			
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String save2(){
		
		
			PlArchives orgPlArchives=plArchivesService.get(plArchives.getId());
			try{
				BeanUtil.copyNotNullProperties(orgPlArchives, plArchives);
				plArchives.setParentid(Long.valueOf("0"));
				plArchives.setText(orgPlArchives.getName());
				plArchives.setLeaf(false);
				plArchivesService.save(orgPlArchives);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String getCheckerTree(){
		
		String companyId=this.getRequest().getParameter("companyId");
		String s=plArchivesService.getCheckerTree(node,companyId);
		JsonUtil.responseJsonString(s);
		return SUCCESS;
	}
}
