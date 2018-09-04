package com.zhiwei.credit.action.creditFlow.multiLevelDic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
/**
 * 
 * @author 张斌
 *2010.08.12
 */
public class AreaDicAction extends BaseAction{

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	@Resource
	protected AreaDicService areaDicService ;
	protected Integer id;
	protected Integer parentId;
	protected Integer number; 
	protected String text;
	protected String imgUrl;
	protected Boolean leaf;
	protected String lable;
	protected String remarks;
	protected int orderid;
	protected String remarks1;//为添加担保材料和归档材料的材料收集方式字段新添加
	
	protected int sourcepid;
	private String node;
	
	
	
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	@LogResource(description = "增加或修改地区数据字典")
	public void add(){
		AreaDic  area = new AreaDic();
		area.setParentId(parentId);
		//area.setImgUrl(imgUrl);
		area.setLeaf(true);
		//area.setNumber(0);
		area.setText(text);
		area.setIsOld(0);
		area.setRemarks(parentId == 1||parentId ==89||parentId ==10092?text:remarks+"_"+text);
		area.setOrderid(orderid);
		//area.setIsOld(0);
		area.setRemarks1(remarks1);//////11.24
		areaDicService.addDic(area); 
	}
	@LogResource(description = "添加数据字典分类")
	public void saveDicType(){
		AreaDic dicType = new AreaDic();
		dicType.setText(text);
		dicType.setParentId(0);
		dicType.setLeaf(leaf);
		dicType.setLable(lable);
		dicType.setRemarks(remarks);
		dicType.setOrderid(orderid);
		areaDicService.addDicType(dicType);
	}
	
	
	public String getAreaId() throws Exception{
		AreaDic area =areaDicService.getById(id);
		JsonUtil.jsonFromObject(area, true) ;
		return SUCCESS;
	}
	
	//获取根节点
	public void getListAreaDic() throws Exception{
		List<AreaDic> list = new ArrayList<AreaDic>();
		HttpServletRequest req = getRequest();
		HttpServletResponse res = getResponse();
		Integer node = Integer.parseInt(req.getParameter("node"));
		PrintWriter out ;
		String json = "";
		JSONArray jsonArray = null;
		list = areaDicService.listByParentId(node);
		jsonArray=JSONArray.fromObject(list); 
		json = jsonArray.toString();
		try {
			res.setCharacterEncoding("utf-8");
			res.setContentType("text/json; charset=utf-8");
			out = res.getWriter();
			out.write(json);
			out.flush() ;
			out.close() ;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	@SuppressWarnings("deprecation")
	public void updateDic() throws Exception{
		String remarksnew="";
		if(parentId==89){
			remarksnew=text;
			
		}else{
		String[] a=remarks.split("_");
		for(int i=0;i<a.length-1;i++){
			remarksnew+=a[i]+"_";
			
		}
		remarksnew+=text;
		}
		areaDicService.updateDic(id,text,parentId,orderid,remarks,sourcepid,remarks1);
	}
	
	public void getAreaNameByID(){
		
	}
	/**
	 * 字典类型操作
	 * @throws Exception 
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public void getAllDicType() throws Exception{
		int parent = Integer.parseInt(getRequest().getParameter("parentId"));
		List<AreaDic> list = areaDicService.listByParentId(parent);
		JsonUtil.jsonFromList(list);
	}
	
	/**
	 * 获得数据字典的省一级
	 */
	public String listByParentId(){
		try{
			List<AreaDic> list=areaDicService.listByParentId(parentId);
			if(null!=list && list.size()>0){
				StringBuffer buff = new StringBuffer("[");
				for (AreaDic areaDic:list) {
					buff.append("[").append(areaDic.getId()).append(",'")
							.append(areaDic.getText()).append("'],");
				}
				if (list.size() > 0) {
					buff.deleteCharAt(buff.length() - 1);
				}
				buff.append("]");
				
				setJsonString(buff.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public void getRecordCountByParentID(){
		areaDicService.getRecordCountByParentID(parentId);
	}
	
	public void getDicTypeId() throws Exception{
		AreaDic area = areaDicService.getById(id);
		JsonUtil.jsonFromObject(area, true) ;
	}
	
	public void updateDicType() throws Exception{
		areaDicService.updateDicType(id,text,lable,remarks,orderid);
	}
	@LogResource(description = "删除字典分类")
	public void deleteDicType(){
		areaDicService.deleteDicType(id);
	}
	
	public void deleteById(){
		areaDicService.deleteById(id);
	}
	public void returnDicType(){
		String selectedString = getRequest().getParameter("selectedString");
		areaDicService.returnDicType(selectedString);
	}
	/**
	 * 根据parentId获得List
	 */
	public void getDicTypeTree(){
		HttpServletRequest req = getRequest();
		String lable = req.getParameter("lable");
		int node=Integer.parseInt(req.getParameter("node"));
		//int node = Integer.parseInt(req.getParameter("node"));
		areaDicService.getDicTypeTree(lable,node);
		
	}
	@SuppressWarnings("unchecked")
	public void findLikeTree(){
		HttpServletRequest request = getRequest();
		HttpServletResponse res = getResponse();
		String text = request.getParameter("text");
		PrintWriter out ;
		List list = this.areaDicService.findLikeText(text);
		String json = JSONArray.fromObject(list).toString();
		try{
			res.setCharacterEncoding("utf-8");
			res.setContentType("text/json; charset=utf-8");
			out = res.getWriter();
			out.write(json);
			out.flush() ;
			out.close() ;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public String getHtmlLocation() throws Exception {
		List<AreaDic> list = new ArrayList<AreaDic>();
		list = this.areaDicService.listByParentId(10092);
		StringBuilder sb = new StringBuilder();
		int r = list.size();
		for (int i = 0; i < r; i++) {
			AreaDic areaDic = (AreaDic) list.get(i);
			String headerText = areaDic.getText();
			int id = areaDic.getId();
			List<AreaDic> clist = new ArrayList<AreaDic>();
			clist = this.areaDicService.listByParentId(id);
			int d = clist.size();
			int rowspan = 0;
			if(d % 3 == 0) {
				rowspan = d/3;
			}else if((d+1) % 3 == 0) {
				rowspan = (d+1)/3;
			}else if((d+2) % 3 == 0) {
				rowspan = (d+2)/3;
			}
			if((i+1) % 2 != 0) {
				sb.append("<tr class=\"bgGray\">");
			}else {
				sb.append("<tr>");
			}
			sb.append("<td class=\"bigOrange\" rowspan=\"");
			sb.append(rowspan);
			sb.append("\">");
			sb.append(headerText);
			sb.append("</td>");
			for (int j = 0; j < d; j++) {
				AreaDic careaDic = (AreaDic) clist.get(j);
				String text = careaDic.getText();
				String lid = careaDic.getId().toString();
				sb.append("<td style=\"padding-bottom: 5px; cursor: pointer\" onmouseover=\"javascript:this.className='oncurrenttd'\" onmouseout=\"javascript:this.className=''\">");
				sb.append("<a onclick=\"javascript:setLocation(");
				sb.append(lid);
				sb.append(",'");
				sb.append(text);
				sb.append("');\">");
				sb.append(text);
				sb.append("</a></td>");
				if ((j+1) % 3 == 0) {
					sb.append("</tr>");
					if(j+1 < d) {
						if((i+1) % 2 != 0) {
							sb.append("<tr class=\"bgGray\">");
						}else {
							sb.append("<tr>");
						}
					}
				}else if(j+1 == d) {
					if((j+2) % 3 == 0) {
						sb.append("<td style=\"padding-bottom: 5px;\" ></td>");
					}else if((j+3) % 3 == 0) {
						sb.append("<td style=\"padding-bottom: 5px;\"></td><td style=\"padding-bottom: 5px;\"></td>");
					}
					sb.append("</tr>");
				}
			}
		}
		getRequest().setAttribute("htmlLocation",sb.toString());
		return "detail";
	
	}
	public void getDictionaryTreeWindow() {
		areaDicService.getDictionaryTreeWindow(lable,node);
	}
	public void getDictionaryNotLastNodeTreeWindow() {
		areaDicService.getDictionarNotLastNodeyTreeWindow(lable,node);
	}
	/*public void findPersonAddress(){
		try{
			this.areaDicServ.selectPersonAddress(
					Integer.parseInt(getRequest().
							getParameter("parentId"))) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}*/
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public String getRemarks1() {
		return remarks1;
	}

	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}

	public int getSourcepid() {
		return sourcepid;
	}

	public void setSourcepid(int sourcepid) {
		this.sourcepid = sourcepid;
	}



	
	
}
