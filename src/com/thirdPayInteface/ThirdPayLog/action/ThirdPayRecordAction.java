package com.thirdPayInteface.ThirdPayLog.action;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.thirdPayInteface.ThirdPayLog.model.ThirdPayRecord;
import com.thirdPayInteface.ThirdPayLog.service.ThirdPayRecordService;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
/**
 * 
 * @author 
 *
 */
public class ThirdPayRecordAction extends BaseAction{
	@Resource
	private ThirdPayRecordService thirdPayRecordService;
	private ThirdPayRecord thirdPayRecord;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ThirdPayRecord getThirdPayRecord() {
		return thirdPayRecord;
	}

	public void setThirdPayRecord(ThirdPayRecord thirdPayRecord) {
		this.thirdPayRecord = thirdPayRecord;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ThirdPayRecord> list= thirdPayRecordService.getAll(filter);
		
		Type type=new TypeToken<List<ThirdPayRecord>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
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
				thirdPayRecordService.remove(new Long(id));
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
		ThirdPayRecord thirdPayRecord=thirdPayRecordService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(thirdPayRecord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(thirdPayRecord.getId()==null){
			thirdPayRecordService.save(thirdPayRecord);
		}else{
			ThirdPayRecord orgThirdPayRecord=thirdPayRecordService.get(thirdPayRecord.getId());
			try{
				BeanUtil.copyNotNullProperties(orgThirdPayRecord, thirdPayRecord);
				thirdPayRecordService.save(orgThirdPayRecord);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
