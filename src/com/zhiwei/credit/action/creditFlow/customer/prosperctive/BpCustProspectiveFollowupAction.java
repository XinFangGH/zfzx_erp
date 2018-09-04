package com.zhiwei.credit.action.creditFlow.customer.prosperctive;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProspectiveFollowup;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.RelativeUser;
import com.zhiwei.credit.service.creditFlow.customer.prosperctive.BpCustProspectiveFollowupService;
import com.zhiwei.credit.service.creditFlow.customer.prosperctive.BpCustProsperctiveService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.system.RelativeUserService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustProspectiveFollowupAction extends BaseAction{
	@Resource
	private BpCustProspectiveFollowupService bpCustProspectiveFollowupService;
	@Resource
	private BpCustProsperctiveService bpCustProsperctiveService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private RelativeUserService relativeUserService;
	
	private BpCustProspectiveFollowup bpCustProspectiveFollowup;
	
	private BpCustProsperctive bpCustProsperctive;
	
	private Long followId;
	
	private Boolean isAll;

	public Long getFollowId() {
		return followId;
	}

	public void setFollowId(Long followId) {
		this.followId = followId;
	}

	public BpCustProspectiveFollowup getBpCustProspectiveFollowup() {
		return bpCustProspectiveFollowup;
	}

	public void setBpCustProspectiveFollowup(BpCustProspectiveFollowup bpCustProspectiveFollowup) {
		this.bpCustProspectiveFollowup = bpCustProspectiveFollowup;
	}


	public void setBpCustProsperctive(BpCustProsperctive bpCustProsperctive) {
		this.bpCustProsperctive = bpCustProsperctive;
	}

	public BpCustProsperctive getBpCustProsperctive() {
		return bpCustProsperctive;
	}
	
	/**
	 * 显示列表
	 */
	public String list(){
		String []userIds = null;
		String currentUserId = ContextUtil.getCurrentUserId().toString();
		StringBuffer userIdsStr = new StringBuffer();
		isAll = super.getRequest().getParameter("isAll").equals("true")?true:false;
		Boolean  isShop = super.getRequest().getParameter("isShop").equals("true")?true:false;
		String departmentId=null;
		//授权查询全部客户的代码begin 在这里为companyId赋值
/*		List<AppUser> userList = appUserService.findRelativeUsersByUserId();
		if(!isAll) {//如果用户不拥有查看所有项目信息的权限
			if(userList.size() > 0) {
				for(AppUser appUser : userList) {
					userIdsStr.append(appUser.getUserId());
					userIdsStr.append(",");
				}
				userIds = (userIdsStr.toString() + currentUserId).split(",");//当前登录用户以及其所有下属用户
			}else {
				userIds = new String[]{currentUserId};
			}
		}*/
		
		//-------------
		List<RelativeUser> findSubordinate = relativeUserService.findSubordinate(ContextUtil.getCurrentUserId());
		ArrayList<AppUser> appUserList = new ArrayList<AppUser>();
		List<AppUser> diguiList = new ArrayList<AppUser>();
		diguiList = appUserService.diguiRelativeUser(findSubordinate, appUserList);
		if(!isAll) {//如果用户不拥有查看所有项目信息的权限
			if(!isShop){//判断是否按门店分离，如果没有授权按门店分离，默认按上下级分离
				if(diguiList.size() > 0) {
					for(AppUser appUser : diguiList) {
						userIdsStr.append(appUser.getUserId());
						userIdsStr.append(",");
					}
					userIds = (userIdsStr.toString() + currentUserId).split(",");//当前登录用户以及其所有下属用户
				}else {
					userIds = new String[]{currentUserId};
				}
			}else{
				//如果授权按门店分离，则按门店分离数据
				AppUser  appUser=appUserService.get(Long.valueOf(currentUserId));
				Organization organization=organizationService.getByUserIdToStoreNameAndStoreNameId(appUser.getDepartment().getDepId());
				departmentId = organization.getOrgId().toString();
			}
			
		}
		//--------------
		
		List<BpCustProspectiveFollowup> list= bpCustProspectiveFollowupService.getList(getRequest(),start,limit,userIds,departmentId);
		if(list!=null && list.size()>0){
			for(BpCustProspectiveFollowup  temp :list){
				if(null!=temp.getBpCustProsperctive().getCompanyId()){
					Organization org=organizationService.get(temp.getBpCustProsperctive().getCompanyId());
					if(null!=org){
						temp.getBpCustProsperctive().setOrgName(org.getOrgName());
					}
				}
				String appuers = "";
				
				if (null != temp.getFollowPersonId()) {
					String[] appstr = temp.getFollowPersonId().split(",");
					Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
					for (AppUser s : userSet) {
						appuers += s.getFullname() + ",";
					}
				}
				if (appuers.length() > 0) {
					appuers = appuers.substring(0, appuers.length() - 1);
				}
				temp.setName(appuers);
				
				if (null != temp.getCommentorrId()) {
					AppUser user= this.appUserService.get(Long.valueOf(temp.getCommentorrId()));
					if(user!=null){
						temp.setCommentorName(user.getFullname());
					}
					
				}
			}
		}
		List<BpCustProspectiveFollowup> listcount= bpCustProspectiveFollowupService.getList(getRequest(),null,null,userIds,departmentId);
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:");
		buff.append(listcount!=null?listcount.size():0);
		buff.append(",result:");
		JSONSerializer serializer=new JSONSerializer();
		buff.append(serializer.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), "followDate", "bpCustProsperctive.createDate", "bpCustProsperctive.nextFollowDate").exclude(new String[]{"class"}).serialize(list));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 根据潜在客户的id来查询跟进记录
	 * @return
	 */
	public String listByPerId (){
		String perId =this.getRequest().getParameter("perId");
		if(perId!=null&&!"".equals(perId)&&!"null".equals(perId)&&!"undefined".equals(perId)){
			List<BpCustProspectiveFollowup> list= bpCustProspectiveFollowupService.getListByPerId(perId,this.getRequest(),null,null);
			if(list!=null && list.size()>0){
				for(BpCustProspectiveFollowup  temp :list){
					String appuers = "";
					if (null != temp.getFollowPersonId()) {
						String[] appstr = temp.getFollowPersonId().split(",");
						Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
						for (AppUser s : userSet) {
							appuers += s.getFullname() + ",";
						}
					}
					if (appuers.length() > 0) {
						appuers = appuers.substring(0, appuers.length() - 1);
					}
					temp.setName(appuers);
					
					if (null != temp.getCommentorrId()) {
						AppUser user= this.appUserService.get(Long.valueOf(temp.getCommentorrId()));
						if(user!=null){
							temp.setCommentorName(user.getFullname());
						}
						
					}
					
					
				}
			}
			StringBuffer buff = new StringBuffer("{success:true,result:");
			/*buff.append(listcount!=null?listcount.size():0);
			buff.append(",result:");*/
			JSONSerializer serializer=new JSONSerializer();
			buff.append(serializer.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), "followDate", "bpCustProsperctive.createDate", "bpCustProsperctive.nextFollowDate").exclude(new String[]{"class"}).serialize(list));
			buff.append("}");
			jsonString=buff.toString();
		}
		return SUCCESS;
		
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		String perId =this.getRequest().getParameter("perId");
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				bpCustProspectiveFollowupService.remove(new Long(id));
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
		BpCustProspectiveFollowup bpCustProspectiveFollowup=bpCustProspectiveFollowupService.get(followId);
		String appuers = "";
		if (null != bpCustProspectiveFollowup.getFollowPersonId()) {
			String[] appstr = bpCustProspectiveFollowup.getFollowPersonId().split(",");
			Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
			for (AppUser s : userSet) {
				appuers += s.getFullname() + ",";
			}
		}
		if (appuers.length() > 0) {
			appuers = appuers.substring(0, appuers.length() - 1);
		}
		bpCustProspectiveFollowup.setName(appuers);
		if (null != bpCustProspectiveFollowup.getCommentorrId()) {
			AppUser user= this.appUserService.get(Long.valueOf(bpCustProspectiveFollowup.getCommentorrId()));
			if(user!=null){
				bpCustProspectiveFollowup.setCommentorName(user.getFullname());
			}
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("bpCustProspectiveFollowup", bpCustProspectiveFollowup);
		map.put("object", "");
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer serializer=new JSONSerializer();
		buff.append(serializer.transform(new DateTransformer("yyyy-MM-dd"), "bpCustProspectiveFollowup.followDate", "bpCustProspectiveFollowup.bpCustProsperctive.createDate", "bpCustProspectiveFollowup.bpCustProsperctive.nextFollowDate").serialize(map));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 添加及保存跟进记录操作
	 */
	public String save(){
		try{
			BpCustProsperctive  customer =bpCustProsperctiveService.get(bpCustProsperctive.getPerId());
			if(bpCustProsperctive.getNextFollowDate()!=null){
				customer.setNextFollowDate(bpCustProsperctive.getNextFollowDate());
			}
			if(bpCustProsperctive.getFollowUpType()!=null){
				customer.setFollowUpType(bpCustProsperctive.getFollowUpType());
			}
			bpCustProspectiveFollowup.setBpCustProsperctive(customer);
			if(bpCustProspectiveFollowup.getFollowDate()!=null){
				customer.setLastFollowUpDate(bpCustProspectiveFollowup.getFollowDate());
			}
			if(bpCustProspectiveFollowup.getFollowId()==null){
				bpCustProspectiveFollowup.setFollowUpStatus(bpCustProsperctive.getFollowUpType());
				bpCustProspectiveFollowupService.save(bpCustProspectiveFollowup);
			}
			bpCustProsperctiveService.merge(customer);
		}catch(Exception ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 保存点评人信息以及跟新跟进记录的信息
	 * @return
	 */
	public String update(){
		try{
			BpCustProspectiveFollowup orgBpCustProspectiveFollowup=bpCustProspectiveFollowupService.get(bpCustProspectiveFollowup.getFollowId());
			BeanUtil.copyNotNullProperties(orgBpCustProspectiveFollowup, bpCustProspectiveFollowup);
			bpCustProspectiveFollowupService.merge(orgBpCustProspectiveFollowup);
		}catch(Exception ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		
		setJsonString("{success:true}");
		return SUCCESS;
		
	}

	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}
	

}
