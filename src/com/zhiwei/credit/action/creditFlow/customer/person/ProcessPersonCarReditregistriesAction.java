package com.zhiwei.credit.action.creditFlow.customer.person;



import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.person.ProcessPersonCarReditregistries;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.service.creditFlow.customer.person.ProcessPersonCarReditregistriesService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
@SuppressWarnings("all")
public class ProcessPersonCarReditregistriesAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private ProcessPersonCarReditregistriesService processPersonCarReditregistriesService;
	@Resource
	private AreaDicService areaDicService;
	private ProcessPersonCarReditregistries reditregistries;
	private int personId;
	private int id;
	
	public ProcessPersonCarReditregistries getReditregistries() {
		return reditregistries;
	}
	public void setReditregistries(ProcessPersonCarReditregistries reditregistries) {
		this.reditregistries = reditregistries;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	

    //控制方法	
	public void queryList(){
		try {
			PagingBean pb=new PagingBean(start,limit);
			List<ProcessPersonCarReditregistries> list=processPersonCarReditregistriesService.queryList(personId, pb);
			List<ProcessPersonCarReditregistries> list1=processPersonCarReditregistriesService.queryList(personId, null);
			int count=0;
			if(null!=list1 && list1.size()>0){
				count=list1.size();
			}
			JsonUtil.jsonFromList(list1, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void add(){
		try {
			processPersonCarReditregistriesService.save(reditregistries);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void see(){
		
		try {
			reditregistries=processPersonCarReditregistriesService.getById(id);
			if(null!=reditregistries.getNowProvince() && !"".equals(reditregistries.getNowProvince())){
				AreaDic dic=areaDicService.getById(Integer.valueOf(reditregistries.getNowProvince()));
				reditregistries.setNowProvinceValue(dic.getText());
			}
			if(null!=reditregistries.getNowCity() && !"".equals(reditregistries.getNowCity())){
				AreaDic dic=areaDicService.getById(Integer.valueOf(reditregistries.getNowCity()));
				reditregistries.setNowCityValue(dic.getText());
			}
			if(null!=reditregistries.getNowDistrict() && !"".equals(reditregistries.getNowDistrict())){
				AreaDic dic=areaDicService.getById(Integer.valueOf(reditregistries.getNowDistrict()));
				reditregistries.setNowDistrictValue(dic.getText());
			}
			if(null!=reditregistries.getForeProvince() && !"".equals(reditregistries.getForeProvince())){
				AreaDic dic=areaDicService.getById(Integer.valueOf(reditregistries.getForeProvince()));
				reditregistries.setForeProvinceValue(dic.getText());
			}
			if(null!=reditregistries.getForeCity() && !reditregistries.getForeCity().equals("")){
				AreaDic dic=areaDicService.getById(Integer.valueOf(reditregistries.getForeCity()));
				reditregistries.setForeCityVlaue(dic.getText());
			}
			if(null!=reditregistries.getForeDistrict() && !reditregistries.getForeDistrict().equals("")){
				AreaDic dic=areaDicService.getById(Integer.valueOf(reditregistries.getForeDistrict()));
				reditregistries.setForeDistrictValue(dic.getText());
			}
			JsonUtil.jsonFromObject(reditregistries, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void update(){
		try {
			processPersonCarReditregistriesService.merge(reditregistries);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteRs(){
		try {
			reditregistries=processPersonCarReditregistriesService.getById(id);
			processPersonCarReditregistriesService.remove(reditregistries);
			jsonString="{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
