package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.Position;
import com.zhiwei.credit.service.system.AppRoleService;
import com.zhiwei.credit.service.system.PositionService;
/**
 * 
 * @author 
 *
 */
public class PositionAction extends BaseAction{
	@Resource
	private PositionService positionService;
	@Resource
	private AppRoleService appRoleService;
	
	private Position position;
	
	private Long posId;

	public Long getPosId() {
		return posId;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * 操作职位选择器
	 */
	public String list(){
		String posSupId=getRequest().getParameter("posSupId");
		String sameLevel=getRequest().getParameter("sameLevel");
		String posName = getRequest().getParameter("Q_posName_S_LK");
		String posId = getRequest().getParameter("posId");
		
		List<Position> list = null;
		Type type=new TypeToken<List<Position>>(){}.getType();
		StringBuffer buff = null;
		PagingBean pb = getInitPagingBean();
		
		if(!StringUtils.isNotEmpty(posSupId)){
			posSupId = "0";
		}
		
		if(sameLevel==null||"".equals(sameLevel)){
			QueryFilter filter=new QueryFilter(getRequest());
			Position position = positionService.get(new Long(posSupId));
			if(position!=null){
				Position supPos=positionService.get(position.getPosSupId());
				filter.addFilter("Q_path_S_LFK", supPos==null?"0.":supPos.getPath());
				Long depth = supPos==null?1 :supPos.getDepth()+1;
				filter.addFilter("Q_depth_L_EQ", depth.toString());
			}
			if(StringUtils.isNotEmpty(posId)){
				filter.addFilter("Q_path_S_LK", posId);
			}
			filter.addSorted("path", "asc");
			
			list= positionService.getAll(filter);
			buff = new StringBuffer("{success:true,'totalCounts':")
			.append(filter.getPagingBean().getTotalItems()).append(",result:");
		}else{
//			list= positionService.getUnderling(new Long(posId),new Long(posSupId),1,pb,posName);
			list = positionService.getSameLevel(new Long(posId), pb, posName);
			buff = new StringBuffer("{success:true,'totalCounts':")
			.append(list.size()).append(",result:");
		}
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	
	public String custList(){
		PagingBean pb = getInitPagingBean();
		StringBuffer buff = new StringBuffer();
		String posId = getRequest().getParameter("posId");
		if(posId == null || posId == ""){
			posId = "0";
		}
		List<Position> list = positionService.getUnderling(new Long(posId), pb);
		Gson gson=new Gson();
		Type type=new TypeToken<List<Position>>(){}.getType();
		buff.append("{success:true,'totalCounts':").append(pb.getTotalItems()).append(",result:");
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 点击职位树,取下属岗位
	 */
	public String underlingList(){
		String posId=getRequest().getParameter("posId");
		PagingBean pb = getInitPagingBean();
		List<Position> list= positionService.getRelativeUnderling(new Long(posId),pb);
		
		Type type=new TypeToken<List<Position>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:");
		
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
				//删除某个岗位及其下属岗位
				positionService.delPositionCascade(new Long(id));
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
		Position position = positionService.get(posId);
		
		String sameLevelIds = "";
		String sameLevelNames = "";
		Set<Position> set = position.getMainPositionSubs();
		for(Position p:set){
			if(sameLevelIds.length()>0){
				sameLevelIds += ",";
				sameLevelNames += ",";
			}
			sameLevelIds += p.getPosId();
			sameLevelNames += p.getPosName();
		}
		
		Set<Position>posSet=position.getSubPositionSubs();
		String underByPos = "";
		for(Position p:posSet){
			if(underByPos.length()>0){
				underByPos += ",";
			}
			underByPos+=p.getPosName();
		}
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append("{\"sameLevelIds\":\""+sameLevelIds+"\",");
		sb.append("\"sameLevelNames\":\""+sameLevelNames+"\",");
		sb.append("\"underByPos\":\""+underByPos+"\",");
		String json = gson.toJson(position);
		sb.append(json.substring(1,json.length()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(position.getPosId()==null){
			positionService.save(position);
			Long parentId=position.getPosSupId();
			if(parentId==0){
				position.setPath("0."+position.getPosId()+".");
				position.setDepth(1l);
			}else{
				Position supPos=positionService.get(parentId);
				position.setPath(supPos.getPath()+position.getPosId()+".");
				position.setDepth(supPos.getDepth()+1);
			}
			positionService.save(position);
		}else{
			String sameLevelIds = getRequest().getParameter("sameLevelIds");
			Position orgPosition=positionService.get(position.getPosId());
			try{
				BeanUtil.copyNotNullProperties(orgPosition, position);
				
				if(StringUtils.isNotEmpty(sameLevelIds)){
					Set<Position> positionSubs = new HashSet<Position>();
					String[] posIds = sameLevelIds.split(",");
					for(String posId : posIds){
						if(StringUtils.isNotEmpty(posId)){
							if(!posId.equals(orgPosition.getPosId().toString())){
								positionSubs.add(positionService.get(new Long(posId)));
							}
						}
					}
					orgPosition.setMainPositionSubs(positionSubs);
				}
				
				positionService.save(orgPosition);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 取得树
	 * @return
	 */
	public String tree(){
		StringBuffer buff = new StringBuffer("[{id:'"+0+"',text:'所有岗位',expanded:true,children:[");
		List<Position> posList=positionService.getByParent(new Long(0l));
		for(Position pos:posList){
			buff.append("{id:'"+pos.getPosId()).append("',text:'"+pos.getPosName()).append("',");
			 buff.append(getChildList(pos.getPosId()));
		}
		if (!posList.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]}]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	
	public String getChildList(Long parentId){
		StringBuffer buff=new StringBuffer();
		List<Position> posList=positionService.getByParent(parentId);
		if(posList.size()==0){
			buff.append("leaf:true,expanded:true},");
			return buff.toString(); 
		}else {
			buff.append("expanded:true,children:[");
			for(Position pos:posList){
				buff.append("{id:'"+pos.getPosId()).append("',text:'"+pos.getPosName()).append("',");
			    buff.append(getChildList(pos.getPosId()));
			}
			buff.deleteCharAt(buff.length() - 1);
			buff.append("]},");
			return buff.toString();
		}
	}
	
	public String detail(){
		String orgId=getRequest().getParameter("posId");
		if(StringUtils.isNotEmpty(orgId) && !"0".equals(posId)){
			Position position=positionService.get(new Long(orgId));
			
			if(position!=null&&position.getPosSupId()!=null && position.getPosSupId()!=-1){
				Position supPos=positionService.get(position.getPosSupId());
				getRequest().setAttribute("supPos",supPos);
			}
			
			getRequest().setAttribute("position", position);
		}
		return "detail";
	}
	/**
	 * 添加角色
	 * @return
	 */
	public String addRole(){
		String roleIds=getRequest().getParameter("roleIds");
		Position position=positionService.get(new Long(posId));
		if(StringUtils.isNotEmpty(roleIds)){
			String[] roleIdArr=roleIds.split("[,]");
			if(roleIdArr!=null){
				if(position!=null){
					position.getRoles().clear();
				}
				for(String roleId:roleIdArr){
					AppRole appRole=appRoleService.get(new Long(roleId));
					position.getRoles().add(appRole);
				}
			}
		}else{
			position.getRoles().clear();
		}
		positionService.save(position);
		return SUCCESS;
	}
}
