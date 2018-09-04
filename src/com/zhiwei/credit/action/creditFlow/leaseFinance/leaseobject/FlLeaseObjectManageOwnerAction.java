package com.zhiwei.credit.action.creditFlow.leaseFinance.leaseobject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import javax.annotation.Resource;

import java.io.StringReader;
import java.lang.reflect.Type;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManageOwner;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManagePlace;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManageOwnerService;
/**
 * 
 * @author 
 *
 */
public class FlLeaseObjectManageOwnerAction extends BaseAction{
	@Resource
	private FlLeaseObjectManageOwnerService flLeaseObjectManageOwnerService;
	private FlLeaseObjectManageOwner flLeaseObjectManageOwner;
	
	private Long recordId;
	private Long leaseObjectId;
	
	
	

	public Long getLeaseObjectId() {
		return leaseObjectId;
	}

	public void setLeaseObjectId(Long leaseObjectId) {
		this.leaseObjectId = leaseObjectId;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public FlLeaseObjectManageOwner getFlLeaseObjectManageOwner() {
		return flLeaseObjectManageOwner;
	}

	public void setFlLeaseObjectManageOwner(FlLeaseObjectManageOwner flLeaseObjectManageOwner) {
		this.flLeaseObjectManageOwner = flLeaseObjectManageOwner;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		List<FlLeaseObjectManageOwner> list= flLeaseObjectManageOwnerService.getListByLeaseObjectId(leaseObjectId);
		Type type=new TypeToken<List<FlLeaseObjectManageOwner>>(){}.getType();
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
				flLeaseObjectManageOwnerService.remove(new Long(id));
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
		FlLeaseObjectManageOwner flLeaseObjectManageOwner=flLeaseObjectManageOwnerService.get(recordId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(flLeaseObjectManageOwner));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(flLeaseObjectManageOwner.getRecordId()==null){
			flLeaseObjectManageOwnerService.save(flLeaseObjectManageOwner);
		}else{
			FlLeaseObjectManageOwner orgFlLeaseObjectManageOwner=flLeaseObjectManageOwnerService.get(flLeaseObjectManageOwner.getRecordId());
			try{
				BeanUtil.copyNotNullProperties(orgFlLeaseObjectManageOwner, flLeaseObjectManageOwner);
				flLeaseObjectManageOwnerService.save(orgFlLeaseObjectManageOwner);
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
		String flLeaseObjectManageOwnerInfo = this.getRequest().getParameter("flLeaseObjectManageOwnerInfo");
		try {
			if (flLeaseObjectManageOwnerInfo!=null&&!"".equals(flLeaseObjectManageOwnerInfo)) {
				String[] insuranceyArr = flLeaseObjectManageOwnerInfo.split("@");
				for(int i=0; i<insuranceyArr.length;i++) {
					String str = insuranceyArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					FlLeaseObjectManageOwner flLeaseObjectManageOwner1 = (FlLeaseObjectManageOwner) JSONMapper.toJava(parser.nextValue(),FlLeaseObjectManageOwner.class);
					flLeaseObjectManageOwnerService.save(flLeaseObjectManageOwner1);
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
