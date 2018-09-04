package com.zhiwei.credit.action.flow;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.proxy.map.MapProxy;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.service.DynamicService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.util.FunctionsUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.flow.FormField;
import com.zhiwei.credit.model.flow.FormTable;

import com.zhiwei.credit.service.flow.FormFieldService;
import com.zhiwei.credit.service.flow.FormTableService;
import com.zhiwei.credit.service.flow.impl.FormTableGenServiceImpl;

import flexjson.JSONSerializer;

/**
 * 
 * @author
 * 
 */
public class FlowFormQueryAction extends BaseAction {
	@Resource
	private FormTableService formTableService;
	private FormTable formTable;
	@Resource
	private FormFieldService formFieldService;

	private final static String packageStr = "com.zhiwei.credit.entity.";

	private Long tableId;

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public FormTable getFormTable() {
		return formTable;
	}

	public void setFormTable(FormTable formTable) {
		this.formTable = formTable;
	}

	public String queryForms() {

		String typeId = getRequest().getParameter("typeId");
		String Q_tableName_S_LK=getRequest().getParameter("Q_tableName_S_LK");

		PagingBean pb = getInitPagingBean();
		String s = getRequest().getParameter("start");
		String l = getRequest().getParameter("limit");
		if (s != null && !s.equals(""))
			pb.setStart(Integer.parseInt(s));
		if (l != null && !l.equals(""))
			pb.setPageSize(Integer.parseInt(l));

		 String sort=getRequest().getParameter("sort");
		 String dir=getRequest().getParameter("dir");

		 List<FormTable> form_table_list =
		 formTableService.getListFromPro(typeId,Q_tableName_S_LK,ContextUtil.getCurrentUser(), pb);
		 StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		 .append(pb.getTotalItems()).append(
		 ",result:");

		 JSONSerializer serializer = new JSONSerializer();
		buff.append(serializer.serialize(form_table_list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	public String queryEntity() {
		String tableKey = getRequest().getParameter("tableKey");
		
		String ClassName=FormTable.TABLE_PRE_NAME+tableKey;
		try {
			DynamicService dynamicService = BeanUtil
					.getDynamicServiceBean(ClassName);
			QueryFilter filter = new QueryFilter(getRequest());
			List list = dynamicService.getAll(filter);
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(filter.getPagingBean().getTotalItems()).append(
							",result:");
			buff.append(JsonUtil.listEntity2Json(list,ClassName));
			buff.append("}");

			jsonString = buff.toString();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.debug(jsonString);

		return SUCCESS;
	}

	public String fieldList() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<FormField> fieldList = formFieldService.getAll(filter);

		java.util.Iterator<FormField> fieldIterator = fieldList.iterator();
		List list = new ArrayList<Map>();

		while (fieldIterator.hasNext()) {
			FormField ff = fieldIterator.next();

			if (StringUtils.isNotEmpty(ff.getForeignTable())
					&& StringUtils.isNotEmpty(ff.getForeignKey())) {
//				Map m = new HashMap();
//
//				m.put("fieldSize", ff.getFieldSize());
//				m.put("showFormat", ff.getShowFormat());
//				m.put("fieldDscp", ff.getFieldLabel());
//				m.put("fieldType", ff.getFieldType().trim());
//				m.put("isList", ff.getIsList());
//				m.put("isQuery", ff.getIsQuery());
//				String foreignTable = ff.getForeignTable();
//				m.put("foreignEntity", FormTable.TABLE_PRE_NAME+foreignTable);
//				String foreignKey = ff.getForeignKey();
//				m.put("foreignKey", foreignKey);
//				list.add(m);

			} else {
				Map m = new HashMap();
				m.put("fieldSize", ff.getFieldSize());
				m.put("showFormat", ff.getShowFormat());
				if(ff.getFieldLabel()!=null){
					m.put("fieldDscp", ff.getFieldLabel());
				}else{
					m.put("fieldDscp", ff.getFieldName());
				}
				m.put("fieldType", ff.getFieldType().trim());
				m.put("isList", ff.getIsList());
				m.put("isQuery", ff.getIsQuery());
				String fieldName = ff.getFieldName();
				m.put("property", fieldName);
				list.add(m);

			}
		}
	
		if(list!=null&&list.size()>0){
			Map m = new HashMap();
			m.put("fieldDscp", "任务ID");
			m.put("fieldType","bigint");
			m.put("isList", 1);
			m.put("isQuery", 0);
			m.put("property", "runId");
			list.add(m);
		}

			


		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		Gson gson = new Gson();
		buff.append(gson.toJson(list));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

}
