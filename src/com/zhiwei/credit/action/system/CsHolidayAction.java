package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.system.CsHoliday;
import com.zhiwei.credit.service.system.CsHolidayService;
import com.zhiwei.credit.service.system.DictionaryService;
/**
 * 
 * @author 
 *
 */
public class CsHolidayAction extends BaseAction{
	@Resource
	private CsHolidayService csHolidayService;
	@Resource
	private DictionaryService dictionaryService;
	private CsHoliday csHoliday;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsHoliday getCsHoliday() {
		return csHoliday;
	}

	public void setCsHoliday(CsHoliday csHoliday) {
		this.csHoliday = csHoliday;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		filter.addSorted("dicId", "asc");
		List<CsHoliday> list= csHolidayService.getAll(filter);
		List<CsHoliday> returnList= new ArrayList<CsHoliday>();
		for(CsHoliday ch:list){
			ch.setDicType(dictionaryService.get(ch.getDicId()).getItemValue());
			returnList.add(ch);
		}
		Type type=new TypeToken<List<CsHoliday>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(returnList, type));
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
				csHolidayService.remove(new Long(id));
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
		CsHoliday csHoliday=csHolidayService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(csHoliday));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		Long id=null;
		if(getRequest().getParameter("id")!=null&&!getRequest().getParameter("id").equals("")){
			id=Long.valueOf(getRequest().getParameter("id"));
		}
		String d=getRequest().getParameter("dateStr");
		String y=getRequest().getParameter("yearStr");
		Date dateStr=DateUtil.parseDate(d,"yyyy-MM-dd");
		Date yStr=DateUtil.parseDate(y,"yyyy-MM-dd");
		Long type=Long.valueOf(getRequest().getParameter("dicId"));
		
		
		CsHoliday	csHoliday=new CsHoliday();
		if(dateStr!=null){
			csHoliday.setYearStr(yStr);
			csHoliday.setDateStr(dateStr);
			csHoliday.setDicId(type);
		}
		if(id==null){
			if(!checkRepeat(dateStr)){
				csHolidayService.save(csHoliday);
				setJsonString("{success:true,e:true,result:'保存成功！'}");
			}else{
				setJsonString("{success:true,e:false,result:'保存失败日期已存在！'}");
			}
		}else{
			CsHoliday orgCsHoliday=csHolidayService.get(id);
			csHoliday.setId(id);
			try{
				BeanUtil.copyNotNullProperties(orgCsHoliday, csHoliday);
				csHolidayService.save(orgCsHoliday);
				setJsonString("{success:true,e:true,result:'更新成功！'}");
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		return SUCCESS;
	}
	/**
	 * 判重检查
	 * @return
	 * 如果isRepeat 为true 说明有重复项
	 */
	private boolean checkRepeat(Date d){
		boolean isRepeat=false;
		List<CsHoliday> l=csHolidayService.checkRepeatByDate(d);
		if(l!=null&&l.size()!=0){
			isRepeat=true;
		}
		return isRepeat;
	}
}
