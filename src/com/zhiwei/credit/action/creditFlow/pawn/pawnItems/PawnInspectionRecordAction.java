package com.zhiwei.credit.action.creditFlow.pawn.pawnItems;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnInspectionRecord;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.pawn.pawnItems.PawnInspectionRecordService;
import com.zhiwei.credit.service.system.DictionaryService;
/**
 * 
 * @author 
 *
 */
public class PawnInspectionRecordAction extends BaseAction{
	@Resource
	private PawnInspectionRecordService pawnInspectionRecordService;
	@Resource
	private DictionaryService dictionaryService;
	private PawnInspectionRecord pawnInspectionRecord;
	
	private Long inspectionId;

	public Long getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(Long inspectionId) {
		this.inspectionId = inspectionId;
	}

	public PawnInspectionRecord getPawnInspectionRecord() {
		return pawnInspectionRecord;
	}

	public void setPawnInspectionRecord(PawnInspectionRecord pawnInspectionRecord) {
		this.pawnInspectionRecord = pawnInspectionRecord;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		String projectId=this.getRequest().getParameter("projectId");
		String businessType=this.getRequest().getParameter("businessType");
		String pawnItemId=this.getRequest().getParameter("pawnItemId");
		List<PawnInspectionRecord> list= pawnInspectionRecordService.getListByPawnItemId(Long.valueOf(projectId), businessType, Long.valueOf(pawnItemId));
		for(PawnInspectionRecord record:list){
			Dictionary dic=dictionaryService.get(record.getOperateType());
			if(null!=dic){
				record.setOperateTypeValue(dic.getItemValue());
			}
		}
		StringBuffer buff = new StringBuffer("{success:true,result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
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
				pawnInspectionRecordService.remove(new Long(id));
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
		PawnInspectionRecord pawnInspectionRecord=pawnInspectionRecordService.get(inspectionId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(pawnInspectionRecord));
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
					PawnInspectionRecord pawnInspectionRecord = (PawnInspectionRecord)JSONMapper.toJava(parser.nextValue(),PawnInspectionRecord.class);
					pawnInspectionRecord.setProjectId(Long.valueOf(projectId));
					pawnInspectionRecord.setBusinessType(businessType);
					pawnInspectionRecord.setPawnItemId(Long.valueOf(pawnItemId));
					if(null==pawnInspectionRecord.getInspectionId()){
						pawnInspectionRecordService.save(pawnInspectionRecord);
					}else{
						PawnInspectionRecord orgPawnInspectionRecord=pawnInspectionRecordService.get(pawnInspectionRecord.getInspectionId());
						BeanUtil.copyNotNullProperties(orgPawnInspectionRecord, pawnInspectionRecord);
						pawnInspectionRecordService.save(orgPawnInspectionRecord);
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
