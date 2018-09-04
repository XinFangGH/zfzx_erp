package com.zhiwei.credit.action.creditFlow.leaseFinance.leaseobject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManagePlace;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManagePlaceService;
import com.zhiwei.credit.service.system.AppUserService;
/**
 * 
 * @author 
 *
 */
public class FlLeaseObjectManagePlaceAction extends BaseAction{
	@Resource
	private FlLeaseObjectManagePlaceService flLeaseObjectManagePlaceService;
	private FlLeaseObjectManagePlace flLeaseObjectManagePlace;
	@Resource
	private AppUserService appUserService;
	private Long id;
	private Long leaseObjectId;
	
	

	public Long getLeaseObjectId() {
		return leaseObjectId;
	}

	public void setLeaseObjectId(Long leaseObjectId) {
		this.leaseObjectId = leaseObjectId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FlLeaseObjectManagePlace getFlLeaseObjectManagePlace() {
		return flLeaseObjectManagePlace;
	}

	public void setFlLeaseObjectManagePlace(FlLeaseObjectManagePlace flLeaseObjectManagePlace) {
		this.flLeaseObjectManagePlace = flLeaseObjectManagePlace;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		List<FlLeaseObjectManagePlace> list= flLeaseObjectManagePlaceService.getListByLeaseObjectId(leaseObjectId);
		for(FlLeaseObjectManagePlace flom : list){
			String personId = flom.getOperationPersonId();
			AppUser au = appUserService.get(Long.valueOf(personId));
			flom.setOperationPersonName(au.getFullname());
		}
		Type type=new TypeToken<List<FlLeaseObjectManagePlace>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
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
				flLeaseObjectManagePlaceService.remove(new Long(id));
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
		FlLeaseObjectManagePlace flLeaseObjectManagePlace=flLeaseObjectManagePlaceService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(flLeaseObjectManagePlace));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(flLeaseObjectManagePlace.getId()==null){
			flLeaseObjectManagePlaceService.save(flLeaseObjectManagePlace);
		}else{
			FlLeaseObjectManagePlace orgFlLeaseObjectManagePlace=flLeaseObjectManagePlaceService.get(flLeaseObjectManagePlace.getId());
			try{
				BeanUtil.copyNotNullProperties(orgFlLeaseObjectManagePlace, flLeaseObjectManagePlace);
				flLeaseObjectManagePlaceService.save(orgFlLeaseObjectManagePlace);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 接受json字符串保存信息
	 */
	public String jsonSave(){
		String FlLeaseObjectManagePlace = this.getRequest().getParameter("flLeaseObjectManagePlaceInfo");
		try {
			if (FlLeaseObjectManagePlace!=null&&!"".equals(FlLeaseObjectManagePlace)) {
				String[] insuranceyArr = FlLeaseObjectManagePlace.split("@");
				for(int i=0; i<insuranceyArr.length;i++) {
					String str = insuranceyArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					FlLeaseObjectManagePlace flLeaseObjectManagePlace1 = (FlLeaseObjectManagePlace) JSONMapper.toJava(parser.nextValue(),FlLeaseObjectManagePlace.class);
					flLeaseObjectManagePlaceService.save(flLeaseObjectManagePlace1);
				}
			}
		} catch (TokenStreamException e) {
			e.printStackTrace();
		} catch (RecognitionException e) {
			e.printStackTrace();
		} catch (MapperException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
