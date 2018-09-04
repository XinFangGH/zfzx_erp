package com.zhiwei.credit.action.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.admin.GoodsApply;
import com.zhiwei.credit.model.admin.OfficeGoods;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.admin.GoodsApplyService;
import com.zhiwei.credit.service.admin.OfficeGoodsService;
import com.zhiwei.credit.service.info.ShortMessageService;
import com.zhiwei.credit.service.system.AppUserService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author csx
 *
 */
public class GoodsApplyAction extends BaseAction{
	private static short PASS_APPLY=2;//通过审批
	private static short NOTPASS_APPLY=3;//未通过审批
	@Resource
	private GoodsApplyService goodsApplyService;
	private GoodsApply goodsApply;
	@Resource
	private AppUserService appUserService;
	@Resource
	private ShortMessageService shortMessageService;
	@Resource
	private OfficeGoodsService officeGoodsService;
	
	private Long applyId;

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public GoodsApply getGoodsApply() {
		return goodsApply;
	}

	public void setGoodsApply(GoodsApply goodsApply) {
		this.goodsApply = goodsApply;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<GoodsApply> list= goodsApplyService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer serializer=JsonUtil.getJSONSerializer("applyDate");
		buff.append(serializer.exclude(new String[]{"class"}).serialize(list));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	/*
	 * 导出到Excel
	 * */
	public String toExcel(){
		//filter.addFilter("Q_approvalStatus_SN_EQ", String.valueOf(this.PASS_APPLY));
		List<GoodsApply> list= goodsApplyService.getAll();
		/*for(GoodsApply goodsApply:list){
			AppUser appUser = appUserService.get(goodsApply.getUserId());
			goodsApply.setUsername(appUser.getFullname());
		}*/
		String [] tableHeader = {"流水号","用品编号","用品名称","出库日期","领用人","经手人","数量","单价","总额"};
		try {
			ExcelHelper.export3(list,tableHeader,"办公用品出库信息("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+")");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ExcelHelper.
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
				goodsApplyService.remove(new Long(id));
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
		GoodsApply goodsApply=goodsApplyService.get(applyId);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer=new JSONSerializer();
		serializer.transform(new DateTransformer("yyyy-MM-dd"),"applyDate");
		sb.append(serializer.exclude(new String[]{"class"}).serialize(goodsApply));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(goodsApply.getApplyId()!=null){
			GoodsApply orgGoodsApply=goodsApplyService.get(goodsApply.getApplyId());
			
			try {
				
				BeanUtil.copyNotNullProperties(orgGoodsApply, goodsApply);
				
					if(orgGoodsApply.getApprovalStatus()==PASS_APPLY){
						OfficeGoods officeGoods=officeGoodsService.get(orgGoodsApply.getGoodsId());
						Integer con=orgGoodsApply.getUseCounts();
						Integer least=officeGoods.getStockCounts()-con;
						if(least<0){
							setJsonString("{success:false,message:'库存不足!'}");
							return SUCCESS;
						}
						Long receiveId=orgGoodsApply.getUserId();
						String content="你申请的办公用品为"+officeGoods.getGoodsName()+"已经通过审批，请查收";
						shortMessageService.save(AppUser.SYSTEM_USER,receiveId.toString(), content, ShortMessage.MSG_TYPE_SYS);
						officeGoods.setStockCounts(least);
						officeGoodsService.save(officeGoods);
					}
					AppUser user = ContextUtil.getCurrentUser();
					orgGoodsApply.setUsername(user.getFullname());
					goodsApplyService.save(orgGoodsApply);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss-SSSS");//自动生成申请号
			goodsApply.setApplyNo("GA"+sdf.format(new Date()));
			goodsApplyService.save(goodsApply);
		}
		
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
