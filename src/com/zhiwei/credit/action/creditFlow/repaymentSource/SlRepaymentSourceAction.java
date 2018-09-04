package com.zhiwei.credit.action.creditFlow.repaymentSource;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.repaymentSource.SlRepaymentSource;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.repaymentSource.SlRepaymentSourceService;
import com.zhiwei.credit.service.system.DictionaryService;
/**
 * 
 * @author 
 *
 */
public class SlRepaymentSourceAction extends BaseAction{
	@Resource
	private SlRepaymentSourceService slRepaymentSourceService;

	@Resource
	private DictionaryService dictionaryService;
	private SlRepaymentSource slRepaymentSource;
	
	private Long sourceId;
    private Long projectId;
    private String RepaymentSource;
    private String value;
    
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRepaymentSource() {
		return RepaymentSource;
	}

	public void setRepaymentSource(String repaymentSource) {
		RepaymentSource = repaymentSource;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public SlRepaymentSource getSlRepaymentSource() {
		return slRepaymentSource;
	}

	public void setSlRepaymentSource(SlRepaymentSource slRepaymentSource) {
		this.slRepaymentSource = slRepaymentSource;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		try{
	        List<SlRepaymentSource> list= slRepaymentSourceService.findListByProjId(projectId);
			
			StringBuffer buff = new StringBuffer("{success:true,result:[");
			int i=0;
			for(SlRepaymentSource sl :list){
				i++;
				buff.append("{\"sourceId\":");
				buff.append(sl.getSourceId());
				buff.append(",\"typeId\":'");
				buff.append(sl.getTypeId());
				buff.append("',\"objectName\":'");
				buff.append(sl.getObjectName());
				buff.append("',\"money\":");
				buff.append(sl.getMoney());
				buff.append(",\"repaySourceDate\":'");
				Date date=sl.getRepaySourceDate();
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				String sdate=df.format(date);
				buff.append(sdate);
				buff.append("',\"remarks\":'");
				buff.append(sl.getRemarks());
				buff.append("',\"typeName\":'");
				String typeName="";
				if(!"".equals(sl.getTypeId()))
				{
					Dictionary dic=dictionaryService.get(Long.valueOf(sl.getTypeId()));
					if(null!=dic){
					    typeName=dic.getItemValue();
					}
				}
				
				buff.append(typeName);
				if(i<list.size()){
					buff.append("'},");
				}else{
					buff.append("'}");
				}
			}
			buff.append("]");
			buff.append("}");
			jsonString=buff.toString();
		}catch(Exception e){
			logger.error("SlRepaymentSourceAction:"+e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String delete(){
		
		SlRepaymentSource slRepaymentSource=slRepaymentSourceService.get(sourceId);
		slRepaymentSourceService.remove(slRepaymentSource);

		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		SlRepaymentSource slRepaymentSource=slRepaymentSourceService.get(sourceId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slRepaymentSource));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(null != RepaymentSource && !"".equals(RepaymentSource)) {

			String[] repaymentSourceArr = RepaymentSource.split("@");
			
			for(int i=0; i<repaymentSourceArr.length; i++) {
				String str = repaymentSourceArr[i];
				String[] strArr=str.split(",");
				String typestr="";
				if(strArr.length==6){
					typestr=strArr[1]; 
				}else{
					typestr=strArr[0];
				}
				String typeId="";
				if(typestr.endsWith("\"")==true){
				   typeId=typestr.substring(typestr.indexOf(":")+2,typestr.length()-1);
				}else{
					typeId=typestr.substring(typestr.indexOf(":")+1,typestr.length());
				}
				List<Dictionary> diclist=dictionaryService.getByProTypeId(Long.valueOf("1110"));
				boolean flag=false;
				int j=0;
				for(Dictionary dic:diclist){
					
					if(dic.getDicId().toString().equals(typeId)){
						break;
					}else{
						j++;
					}
				}
				if(j==diclist.size()){
					flag=true;
				}
				Dictionary cdic=null;
				if(flag==true){
					cdic=new Dictionary();
					cdic.setProTypeId(Long.valueOf("1110"));
					cdic.setItemValue(typeId);
					cdic.setItemName("还款来源");
					cdic.setStatus("0");
					cdic.setDicKey("hkly"+diclist.size());
					dictionaryService.save(cdic);
				}
				JSONParser parser = new JSONParser(new StringReader(str));
				try{
					SlRepaymentSource slRepaymentSource = (SlRepaymentSource)JSONMapper.toJava(parser.nextValue(),SlRepaymentSource.class);
					if(flag==true){
						slRepaymentSource.setTypeId(cdic.getDicKey().toString());
					}
					slRepaymentSource.setProjId(projectId);
					if(null==slRepaymentSource.getSourceId() || "".equals(slRepaymentSource.getSourceId())){
						slRepaymentSourceService.save(slRepaymentSource);
					}else{
						SlRepaymentSource orgslRepaymentSource=slRepaymentSourceService.get(slRepaymentSource.getSourceId());
						BeanUtil.copyNotNullProperties(orgslRepaymentSource, slRepaymentSource);
						slRepaymentSourceService.save(orgslRepaymentSource);
					}
					setJsonString("{success:true}");
				
				} catch(Exception e) {
					setJsonString("{success:false}");
					logger.error("SlRepaymentSourceAction:"+e.getMessage());
					e.printStackTrace();
				
				}
				
			}
		}
		return SUCCESS;
		
	}

}
