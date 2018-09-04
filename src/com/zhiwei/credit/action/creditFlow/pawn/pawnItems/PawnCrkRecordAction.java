package com.zhiwei.credit.action.creditFlow.pawn.pawnItems;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnCrkRecord;
import com.zhiwei.credit.service.creditFlow.pawn.pawnItems.PawnCrkRecordService;
/**
 * 
 * @author 
 *
 */
public class PawnCrkRecordAction extends BaseAction{
	@Resource
	private PawnCrkRecordService pawnCrkRecordService;
	private PawnCrkRecord pawnCrkRecord;
	
	private Long crkRecordId;

	public Long getCrkRecordId() {
		return crkRecordId;
	}

	public void setCrkRecordId(Long crkRecordId) {
		this.crkRecordId = crkRecordId;
	}

	public PawnCrkRecord getPawnCrkRecord() {
		return pawnCrkRecord;
	}

	public void setPawnCrkRecord(PawnCrkRecord pawnCrkRecord) {
		this.pawnCrkRecord = pawnCrkRecord;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		String projectId=this.getRequest().getParameter("projectId");
		String businessType=this.getRequest().getParameter("businessType");
		String pawnItemId=this.getRequest().getParameter("pawnItemId");
		List<PawnCrkRecord> list= pawnCrkRecordService.getListByPawnItemId(Long.valueOf(projectId), businessType, Long.valueOf(pawnItemId));
		
		Type type=new TypeToken<List<PawnCrkRecord>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,result:");
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
				pawnCrkRecordService.remove(new Long(id));
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
		PawnCrkRecord pawnCrkRecord=pawnCrkRecordService.get(crkRecordId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(pawnCrkRecord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		String recordData=this.getRequest().getParameter("recordData");
		String projectId=this.getRequest().getParameter("projectId");
		String businessType=this.getRequest().getParameter("businessType");
		String pawnItemId=this.getRequest().getParameter("pawnItemId");
		try{
			if(null != recordData && !"".equals(recordData)) {

				String[] recordArr = recordData.split("@");
				
				for(int i=0; i<recordArr.length; i++) {
					String str = recordArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					PawnCrkRecord pawnCrkRecord = (PawnCrkRecord)JSONMapper.toJava(parser.nextValue(),PawnCrkRecord.class);
					pawnCrkRecord.setProjectId(Long.valueOf(projectId));
					pawnCrkRecord.setBusinessType(businessType);
					pawnCrkRecord.setPawnItemId(Long.valueOf(pawnItemId));
					if(null==pawnCrkRecord.getCrkRecordId()){
						pawnCrkRecordService.save(pawnCrkRecord);
					}else{
						PawnCrkRecord orgPawnCrkRecord=pawnCrkRecordService.get(pawnCrkRecord.getCrkRecordId());
						BeanUtil.copyNotNullProperties(orgPawnCrkRecord, pawnCrkRecord);
						pawnCrkRecordService.save(orgPawnCrkRecord);
					}
				}
			}
			setJsonString("{success:true}");
		}catch(Exception e){
			setJsonString("{success:false}");
			e.printStackTrace();
		}
		
		return SUCCESS;
		
	}
}
