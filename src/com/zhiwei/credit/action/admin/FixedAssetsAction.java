package com.zhiwei.credit.action.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.admin.DepreType;
import com.zhiwei.credit.model.admin.FixedAssets;
import com.zhiwei.credit.service.admin.DepreRecordService;
import com.zhiwei.credit.service.admin.DepreTypeService;
import com.zhiwei.credit.service.admin.FixedAssetsService;

import flexjson.JSONSerializer;
/**
 * 
 * @author csx
 *
 */
public class FixedAssetsAction extends BaseAction{
	@Resource
	private FixedAssetsService fixedAssetsService;
	private FixedAssets fixedAssets;
	
	@Resource
	private DepreRecordService depreRecordService;
	@Resource
	private DepreTypeService depreTypeService;
	
	private Long assetsId;

	public Long getAssetsId() {
		return assetsId;
	}

	public void setAssetsId(Long assetsId) {
		this.assetsId = assetsId;
	}

	public FixedAssets getFixedAssets() {
		return fixedAssets;
	}

	public void setFixedAssets(FixedAssets fixedAssets) {
		this.fixedAssets = fixedAssets;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<FixedAssets> list= fixedAssetsService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer serializer=JsonUtil.getJSONSerializer("buyDate","startDepre","manuDate");
		buff.append(serializer.exclude(new String[]{"class"}).serialize(list));
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
//				Long assetsId=new Long(id);
//				FixedAssets fixedAssets=fixedAssetsService.get(assetsId);
//				Set<DepreRecord> records=fixedAssets.getDepreRecords();
//				Iterator<DepreRecord> it=records.iterator();
//				while(it.hasNext()){
//					depreRecordService.remove(it.next().getRecordId());
//				}
				fixedAssetsService.remove(new Long(id));
			}
		}
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	/*
	 * 导出Wxcel表格
	 * 
	 * */
	public String toExcel(){
		List list = null;
		String [] tableHeader = {"资产编号","资产名称","资产类别","规格型号","资产值","资产当前值","制造厂商","出厂日期","置办日期","所属部门","保管人","折旧类型","开始折旧日期","预计使用年限","残值率","备注"};
		String hql = "from FixedAssets bp order by  CONVERT(bp.assetsNo , 'GBK') ASC";
		try {
			list  = fixedAssetsService.getAll();
			ExcelHelper.export1(list,tableHeader,"固定资产档案("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+")");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		FixedAssets fixedAssets=fixedAssetsService.get(assetsId);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer= JsonUtil.getJSONSerializer("manuDate","buyDate","startDepre");
		sb.append(serializer.exclude(new String[]{"class"}).serialize(fixedAssets));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss-SSSS");//自动生成商品号
	    if(fixedAssets.getAssetsId()==null){
	    	fixedAssets.setAssetsNo(sdf.format(new Date()));
	    }
	    Long typeId=fixedAssets.getDepreType().getDepreTypeId();
	    if(typeId!=null){
	    	DepreType depreType =depreTypeService.get(typeId);
		    if(depreType.getCalMethod()!=2){//不为工作量折算时
			    BigDecimal remainRate=fixedAssets.getRemainValRate();
			    BigDecimal depreRate=new BigDecimal("1").subtract(remainRate.divide(new BigDecimal("100"))).divide(fixedAssets.getIntendTerm(),2,2);  //折旧率
			    fixedAssets.setDepreRate(depreRate);
		    }
	    }
	    fixedAssetsService.save(fixedAssets);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	

}
