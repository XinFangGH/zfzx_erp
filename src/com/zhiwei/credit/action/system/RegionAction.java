package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;
import javax.annotation.Resource;

import com.google.gson.Gson;

import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.system.Region;
import com.zhiwei.credit.service.system.RegionService;
/**
 * 
 * @author csx
 *
 */
public class RegionAction extends BaseAction{
	@Resource
	private RegionService regionService;
	private Region region;
	
	private Long regionId;

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		List<Region> list= null;
		StringBuffer buff = new StringBuffer("[");
		if(regionId==null){
			//查出所有省份
			list = regionService.getProvince();
			
			for(Region province :list){
				buff.append("['"+province.getRegionId()+"','"+province.getRegionName()+"'],");
			}
		}else{
			//根据省份ID查出该省所有市
			list = regionService.getCity(regionId);
			if(list.size()>0){
				for(Region city : list){
					buff.append("'"+city.getRegionName()+"',");
				}
			}else{
				setRegion(regionService.get(regionId));
				buff.append("'"+region.getRegionName()+"',");
			}
		}
		buff.deleteCharAt(buff.length()-1);
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	/**
	 * 以树形式展示地区数据(省份)
	 * @return
	 */
	public String tree(){
		List<Region> listProvince;
		StringBuffer buff = new StringBuffer("[{id:'"+0+"',text:'中国',expanded:true,children:[");
		listProvince=regionService.getProvince();//最顶层父节点
		for(Region province:listProvince){
			buff.append("{id:'"+province.getRegionId()+"',text:'"+province.getRegionName()+"',");
		    buff.append(findCity(province.getRegionId()));
		}
		if (!listProvince.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]}]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	/**
	 * 寻找子根节点(城市)
	 * @return
	 **/
	
	public String findCity(Long regionId){
		StringBuffer buff1=new StringBuffer("");
		List<Region> listCity=regionService.getCity(regionId);
		if(listCity.size()==0){
			buff1.append("leaf:true},");
			return buff1.toString(); 
		}else {
			buff1.append("children:[");
			for(Region city:listCity){				
				buff1.append("{id:'"+city.getRegionId()+"',text:'"+city.getRegionName()+"',leaf:true},");
				//buff1.append(findChild(city.getRegionId()));只有两级,这句用不上
			}
			buff1.deleteCharAt(buff1.length() - 1);
			buff1.append("]},");
			return buff1.toString();
		}
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				regionService.remove(new Long(id));
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
		Region region=regionService.get(regionId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(region));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		regionService.save(region);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
