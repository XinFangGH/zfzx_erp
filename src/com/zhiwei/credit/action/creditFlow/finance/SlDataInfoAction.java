package com.zhiwei.credit.action.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.util.ElementUtil;
import com.zhiwei.credit.model.creditFlow.finance.SlDataInfo;
import com.zhiwei.credit.service.creditFlow.finance.SlDataInfoService;
/**
 * 
 * @author 
 *
 */
public class SlDataInfoAction extends BaseAction{
	@Resource
	private SlDataInfoService slDataInfoService;
	private SlDataInfo slDataInfo;
	
	private Long dataInfoId;
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getDataInfoId() {
		return dataInfoId;
	}

	public void setDataInfoId(Long dataInfoId) {
		this.dataInfoId = dataInfoId;
	}

	public SlDataInfo getSlDataInfo() {
		return slDataInfo;
	}

	public void setSlDataInfo(SlDataInfo slDataInfo) {
		this.slDataInfo = slDataInfo;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		String dataId=this.getRequest().getParameter("dataId");
		List<SlDataInfo> list= slDataInfoService.getListByDataId(Long.valueOf(dataId));
		
		Type type=new TypeToken<List<SlDataInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,result:");		
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
				slDataInfoService.remove(new Long(id));
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
		SlDataInfo slDataInfo=slDataInfoService.get(dataInfoId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slDataInfo));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slDataInfo.getDataInfoId()==null){
			slDataInfoService.save(slDataInfo);
		}else{
			SlDataInfo orgSlDataInfo=slDataInfoService.get(slDataInfo.getDataInfoId());
			try{
				BeanUtil.copyNotNullProperties(orgSlDataInfo, slDataInfo);
				slDataInfoService.save(orgSlDataInfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public void download(){

		HttpServletResponse response = getResponse();
				String p = super.getRequest().getRealPath("/")+slDataInfoService.get(dataInfoId).getFilePath();
				ElementUtil.downloadFile(p, response);
			}
		
}
