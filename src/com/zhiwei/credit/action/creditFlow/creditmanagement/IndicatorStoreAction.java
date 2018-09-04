package com.zhiwei.credit.action.creditFlow.creditmanagement;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.IndicatorStoreDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.IndicatorStore;
import com.zhiwei.credit.model.creditFlow.permission.CatalogModel;

public class IndicatorStoreAction extends BaseAction {
	private int id;
	private IndicatorStore indicatorStore;
	private int parentId;
	@Resource
	private IndicatorStoreDao indicatorStoreDao;

	public void storeList() {

		HttpServletRequest request = ServletActionContext.getRequest();
		List<IndicatorStore> list = null;
		String node = request.getParameter("node");
		String type = request.getParameter("type");
		list = indicatorStoreDao.getIndicatorStoreList(Integer
				.parseInt(node),type);
		/*if ("dl".equals(type)) {
			list = indicatorStoreDao.getIndicatorStoreListDL(Integer
					.parseInt(node));
		} else {

			list = indicatorStoreDao.getIndicatorStoreListDX(Integer
					.parseInt(node));
		}*/
		List<CatalogModel> catalogList = new ArrayList<CatalogModel>();
		for (IndicatorStore o : list) {
			CatalogModel cm = new CatalogModel();
			cm.setId(o.getId() + "");
			cm.setText(o.getIndicatorType());
			cm.setLeaf(o.isIsleaf());
			catalogList.add(cm);
		}
		JSONArray jsonObject = JSONArray.fromObject(catalogList);
		String json = jsonObject.toString();

		JsonUtil.responseJsonString(json);

	}

	public void indicatorStoreList1() {
		List<IndicatorStore> list = indicatorStoreDao.getIndicatorStoreList();

		try {
			JsonUtil.jsonFromList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void add() {
		HttpServletRequest request = ServletActionContext.getRequest();
		boolean b = indicatorStoreDao.addIndicatorStrore(indicatorStore);
		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
		JsonUtil.responseJsonString(msg);
	}

	public void addIndicatorStoreByDL() {
		indicatorStore.setPtype("dl");
		boolean b = indicatorStoreDao.addIndicatorStrore(indicatorStore);
		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
		JsonUtil.responseJsonString(msg);
	}

	public void addIndicatorStoreByDX() {
		indicatorStore.setPtype("dx");
		boolean b = indicatorStoreDao.addIndicatorStrore(indicatorStore);
		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
		JsonUtil.responseJsonString(msg);
	}

	public void update() {
		IndicatorStore is = indicatorStoreDao.getIndicatorStore(indicatorStore
				.getId());
		is.setIndicatorType(indicatorStore.getIndicatorType());
		is.setRemarks(indicatorStore.getRemarks());
		boolean b = indicatorStoreDao.updateIndicatorStore(is);

		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
		JsonUtil.responseJsonString(msg);
	}

	public void deleteRs() {
		boolean b = indicatorStoreDao.deleteIndicatorStore(id);

		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
		try {
			JsonUtil.responseJsonString(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getInfo() {
		IndicatorStore indicatorStore = indicatorStoreDao.getIndicatorStore(id);
		JsonUtil.jsonFromObject(indicatorStore, true);
	}

	public IndicatorStore getIndicatorStore() {
		return indicatorStore;
	}

	public void setIndicatorStore(IndicatorStore indicatorStore) {
		this.indicatorStore = indicatorStore;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public IndicatorStoreDao getIndicatorStoreDao() {
		return indicatorStoreDao;
	}

	public void setIndicatorStoreDao(IndicatorStoreDao indicatorStoreDao) {
		this.indicatorStoreDao = indicatorStoreDao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
