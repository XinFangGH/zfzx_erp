package com.zhiwei.credit.action.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.Car;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.admin.CarService;
import com.zhiwei.credit.service.system.FileAttachService;
/**
 * 
 * @author lyy
 *
 */
public class CarAction extends BaseAction{
	@Resource
	private CarService carService;
	@Resource
	private FileAttachService fileAttachService;
	private Car car;
	
	private Long carId;

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		QueryFilter filter=new QueryFilter(getRequest());
		List<Car> list= carService.getAll(filter);
		Type type=new TypeToken<List<Car>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
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
				carService.remove(new Long(id));
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
		Car car=carService.get(carId);
		if(car.getCartImage()!=null&&!car.getCartImage().equals("")){
			FileAttach fileattach = fileAttachService.getByPath(car.getCartImage());
			if(fileattach!=null){
				car.setCartImageId(fileattach.getFileId().toString());
			}
		}
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(car));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		carService.save(car);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	/**
	 * 删除图片
	 */
	public String delphoto(){
		String strCarId=getRequest().getParameter("carId");
		if(StringUtils.isNotEmpty(strCarId)){
			car = carService.get(new Long(strCarId));
			car.setCartImage("");
			carService.save(car);
			setJsonString("{success:true}");
		}
		return SUCCESS;
	}
}

