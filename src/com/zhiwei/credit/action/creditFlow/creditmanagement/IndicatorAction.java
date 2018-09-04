package com.zhiwei.credit.action.creditFlow.creditmanagement;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.IndicatorDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.IndicatorStoreDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.MakeScoreGetSubDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.OptionDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Indicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.IndicatorStore;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Options;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateIndicator;
import com.zhiwei.credit.model.creditFlow.permission.CatalogModel;
import com.zhiwei.credit.model.creditFlow.permission.CheckCatalogModel;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.MySelfService;

public class IndicatorAction extends BaseAction {
	private int id;

	private Indicator indicator;
	@Resource
	private IndicatorDao indicatorDao;
	@Resource
	private IndicatorStoreDao indicatorStoreDao;
	private MakeScoreGetSubDao makeScoreGetSubDao;
	@Resource
	private MySelfService mySelfService;
	@Resource
	private OptionDao optionDao;

	private String indicatorType = "";
	private String indicatorName = "";
	private String creater = "";
	private int indicatorTypeId;

	private int templateId;
	private int update;// 属性，修改模版指标

	private Boolean leaf;
	private String optionStr;

	public void add() {
		boolean b = indicatorDao.addIndicator(indicator);
		boolean s = false;
		if (null != optionStr && !"".equals(optionStr)) {

			String[] optionArr = optionStr.split("@");

			for (int i = 0; i < optionArr.length; i++) {
				String str = optionArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try {
					Options options = (Options) JSONMapper.toJava(parser.nextValue(), Options.class);
					options.setSortNo(i + 1);
					options.setIndicatorId(indicator.getId());
					options.setPtype(indicator.getPtype());
					if (0 == options.getId()) {
						s = optionDao.addOption(options);
					} else {
						s = optionDao.updateOption(options);
					}

				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		}
		String msg = "";
		if (((null == optionStr || "".equals(optionStr)) && b == true)
				|| ((null != optionStr || !"".equals(optionStr)) && b == true && s == true)) {
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

	public void indicatorList1() {

	}

	public void getInfo() {
		indicator = indicatorDao.getIndicator(id);

		try {
			JsonUtil.jsonFromObject(indicator, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void list1() {
		String type =this.getRequest().getParameter("type");
		List list = null;
		int totalProperty = 0;
		if (indicatorTypeId == 0) {
			list = indicatorDao.getAllIndicator(start, limit,type);
			totalProperty = indicatorDao.getAllIndicatorNum(type);
		} else if (indicatorTypeId != 0 && leaf == false) {
			list = indicatorDao.getIndicatorByType(indicatorTypeId, start,
					limit,type);
			totalProperty = indicatorDao.getIndicatorNum(indicatorTypeId,type);
		} else if (indicatorTypeId != 0 && leaf == true) {
			list = indicatorDao.getIndicatorList(indicatorTypeId,
					indicatorType, indicatorName, creater, start, limit,type);
			totalProperty = indicatorDao.getIndicatorListNum(indicatorTypeId,
					indicatorType, indicatorName, creater,type);
		}
		int maxScore = 0;
		int minScore = 0;
		if (indicatorTypeId != 0 && leaf == true) {
			maxScore = indicatorDao.getMaxStore(indicatorTypeId,type);
			minScore = indicatorDao.getMinStore(indicatorTypeId,type);
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(totalProperty).append(",result:[");
		for (int i = 0; i < list.size(); i++) {
			Indicator indicator = (Indicator) list.get(i);
			int indicatorId = indicator.getId();
			List list_option = indicatorDao.getOptionsList(indicatorId);
			if (null != list_option && list_option.size() > 0) {
				for (int j = 0; j < list_option.size(); j++) {
					Options options = (Options) list_option.get(j);
					String s = "";
					switch (j) {
					case 0:
						s = "A";
						break;
					case 1:
						s = "B";
						break;
					case 2:
						s = "C";
						break;
					case 3:
						s = "D";
						break;
					case 4:
						s = "E";
						break;
					case 5:
						s = "F";
						break;
					case 6:
						s = "H";
						break;
					case 7:
						s = "I";
						break;
					case 8:
						s = "J";
						break;
					case 9:
						s = "K";
						break;
					case 10:
						s = "L";
						break;
					case 11:
						s = "M";
						break;
					case 12:
						s = "N";
						break;
					case 13:
						s = "O";
						break;
					case 14:
						s = "P";
						break;
					case 15:
						s = "Q";
						break;
					case 16:
						s = "R";
						break;
					case 17:
						s = "S";
						break;
					default:
						s = "X";
					}
					buff.append("{\"indicatorId\":");
					buff.append(indicatorId);
					buff.append(",\"indicatorName\":'");
					buff.append((i + 1) + "、" + indicator.getIndicatorName());
					buff.append("',\"id\":");
					buff.append(options.getId());
					buff.append(",\"optionName\":'");
					buff.append(s + ".&nbsp;&nbsp;&nbsp;"
							+ options.getOptionName());
					buff.append("',\"score\":");
					buff.append(options.getScore());
					buff.append(",\"maxScore\":");
					buff.append(maxScore);
					buff.append(",\"minScore\":");
					buff.append(minScore);
					buff.append(",\"optionStart\":'");
					if(!"".equals(options.getOptionStart()) && !"".equals(options.getOptionEnd())){
						buff.append(s+ ".&nbsp;&nbsp;&nbsp;"+options.getOptionStart()+"<=X");
						buff.append("',\"optionEnd\":");
						buff.append("'<="+options.getOptionEnd()+"'");
					}else{
						buff.append(s+ ".&nbsp;&nbsp;&nbsp;X>="+options.getOptionStart());
						buff.append("',\"optionEnd\":");
						buff.append("'"+options.getOptionEnd()+"'");
					}
					buff.append("},");
				}
			} else {
				buff.append("{\"indicatorId\":");
				buff.append(indicatorId);
				buff.append(",\"indicatorName\":'");
				buff.append(indicator.getIndicatorName());
				buff.append("'},");
			}
		}

		if (null != list && list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
		JsonUtil.responseJsonString(buff.toString());
	}

	public void update() {
		Indicator i = indicatorDao.getIndicator(indicator.getId());
		i.setIndicatorName(indicator.getIndicatorName());
		i.setIndicatorTypeId(indicator.getIndicatorTypeId());
		i.setIndicatorType(indicator.getIndicatorType());
		i.setIndicatorDesc(indicator.getIndicatorDesc());
		i.setOperationType(indicator.getOperationType());
		i.setElementCode(indicator.getElementCode());
		boolean b = indicatorDao.updateIndicator(i);
		boolean s = false;
		if (null != optionStr && !"".equals(optionStr)) {

			String[] shareequityArr = optionStr.split("@");

			for (int j = 0; j < shareequityArr.length; j++) {
				String str = shareequityArr[j];
				JSONParser parser = new JSONParser(new StringReader(str));
				try {
					Options options = (Options) JSONMapper.toJava(parser
							.nextValue(), Options.class);
					options.setSortNo(j + 1);
					options.setIndicatorId(indicator.getId());
					options.setPtype(indicator.getPtype());
					if (0 == options.getId()) {
						s = optionDao.addOption(options);
					} else {
						s = optionDao.updateOption(options);
					}

				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		}
		String msg = "";
		if (((null == optionStr || "".equals(optionStr)) && b == true)
				|| ((null != optionStr || !"".equals(optionStr)) && b == true && s == true)) {
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

	public void deleteRs() {
		List list_option = indicatorDao.getOptionsList(id);
		for (int i = 0; i < list_option.size(); i++) {
			Options options = (Options) list_option.get(0);
			if(null!=options){
				optionDao.remove(options);
			}
		}
		boolean b = indicatorDao.deleteIndicator(id);

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

	public void getCheckTree() {
		List<TemplateIndicator> list_templateIndicator = null;
		if (templateId != 0) {
			list_templateIndicator = indicatorDao
					.getTemplateIndicator(templateId);
		}

		HttpServletRequest request = ServletActionContext.getRequest();

		String node = request.getParameter("node");

		String json = "";
		JSONArray jsonObject = null;
		if (node.equals("0")) {// 跟元素
			jsonObject = getNode0(request, node);
		} else if (node.startsWith("i")) {// 以i开头的
			jsonObject = getNodeI(list_templateIndicator, node);
		} else {// 以t开头的
			jsonObject = getNodeT(node);
		}

		json = jsonObject.toString();
		try {
			JsonUtil.responseJsonString(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JSONArray getNodeT(String node) {
		JSONArray jsonObject;
		List<CatalogModel> catalogList = new ArrayList<CatalogModel>();
		node = node.substring(1);
		List<IndicatorStore> list = new ArrayList<IndicatorStore>();
		list = indicatorDao.getIndicatorStoreList(Integer.parseInt(node));
		for (IndicatorStore rt : list) {
			CatalogModel cm = new CatalogModel();
			if (rt.isIsleaf() == true) {
				cm.setId("i" + rt.getId());
			} else {
				cm.setId("t" + rt.getId());
			}
			cm.setText(rt.getIndicatorType());
			cm.setLeaf(false);
			catalogList.add(cm);
		}

		jsonObject = JSONArray.fromObject(catalogList);
		return jsonObject;
	}

	private JSONArray getNodeI(List<TemplateIndicator> list_templateIndicator,
			String node) {
		JSONArray jsonObject;
		List<CheckCatalogModel> catalogList = new ArrayList<CheckCatalogModel>();
		String type =this.getRequest().getParameter("type");
		String operationType =this.getRequest().getParameter("operationType");
		node = node.substring(1);
		List<Indicator> list = new ArrayList<Indicator>();
		if(operationType!=null&&!"".equals(operationType)){
			list = indicatorDao.getIndicatorListByType(Integer.parseInt(node),type,operationType);
		}else{
			list = indicatorDao.getIndicatorList(Integer.parseInt(node),type);
		}
		for (Indicator i : list) {
			CheckCatalogModel cm = new CheckCatalogModel();
			cm.setId(i.getId() + "");
			cm.setText(i.getIndicatorName());
			cm.setLeaf(true);
			 Integer maxScore=indicatorDao.getMaxScore(i.getId());
             Integer minScore=indicatorDao.getMinScore(i.getId());
             cm.setIconCls(Integer.toString(maxScore));
			cm.setModel(Integer.toString(minScore));
			if(list_templateIndicator != null) {
				for(TemplateIndicator ti : list_templateIndicator) {
					if(ti.getParentIndicatorId() == i.getId()) {
                       
						cm.setChecked(true);
						cm.setDisabled(true);
						
					}
				}
			}
			if(update == 1)
				cm.setDisabled(false);
			catalogList.add(cm);
		}
		
		jsonObject = JSONArray.fromObject(catalogList);
		return jsonObject;
	}

	private void setCmType(String name, Map m, CheckCatalogModel cm) {
		Object[] o = (Object[]) m.get(name);// 从企业m中查询
		if (o == null) {
			cm.setType("gr");
		} else {
			cm.setType("qy");
		}

	}

	private JSONArray getNode0(HttpServletRequest request, String node) {
		JSONArray jsonObject;
		String type =request.getParameter("type");
		String operationType =request.getParameter("operationType");
		// 重新写,一次传入所有数据,对数据进行分析
		List<CatalogModel> catalogList = new ArrayList<CatalogModel>();

		List<IndicatorStore> list = new ArrayList<IndicatorStore>();
		
		list = indicatorDao.getIndicatorStoreList(Integer.parseInt(node),type);
		
		
		// 获取

		for (IndicatorStore rt : list) {
			CatalogModel cm = new CatalogModel();
			if (rt.isIsleaf() == true) {
				cm.setId("i" + rt.getId());
			} else {
				cm.setId("t" + rt.getId());
			}
			cm.setText(rt.getIndicatorType());
			cm.setLeaf(false);
			cm.setType(rt.getPtype());
			catalogList.add(cm);
		}

		jsonObject = JSONArray.fromObject(catalogList);
		return jsonObject;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public IndicatorDao getIndicatorDao() {
		return indicatorDao;
	}

	public void setIndicatorDao(IndicatorDao indicatorDao) {
		this.indicatorDao = indicatorDao;
	}

	public String getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(String indicatorType) {
		this.indicatorType = indicatorType;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	

	public int getIndicatorTypeId() {
		return indicatorTypeId;
	}

	public void setIndicatorTypeId(int indicatorTypeId) {
		this.indicatorTypeId = indicatorTypeId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getUpdate() {
		return update;
	}

	public void setUpdate(int update) {
		this.update = update;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public IndicatorStoreDao getIndicatorStoreDao() {
		return indicatorStoreDao;
	}

	public void setIndicatorStoreDao(IndicatorStoreDao indicatorStoreDao) {
		this.indicatorStoreDao = indicatorStoreDao;
	}

	public String getOptionStr() {
		return optionStr;
	}

	public void setOptionStr(String optionStr) {
		this.optionStr = optionStr;
	}

	public OptionDao getOptionDao() {
		return optionDao;
	}

	public void setOptionDao(OptionDao optionDao) {
		this.optionDao = optionDao;
	}

	public void listForDL() {
		// 不允许传参-----放弃吧
		String type = "dl";
		// 处理List，以dl
		//doForList(type);
	}

	public void listForDX() {
		String type = "dx";
		// 处理List，以dx
		//doForList(type);
	}

	private void doForList() {
		String type =this.getRequest().getParameter("type");
		List list = null;
		int totalProperty = 0;
		// 获取全部题目/参数
		Object[] o = getSource(type);
		if (o != null) {
			list = (List) o[0];
			totalProperty = (Integer) o[1];
		}
		int maxScore = 0;
		int minScore = 0;
		if (indicatorTypeId != 0 && leaf == true) {
			maxScore = indicatorDao.getMaxStore(indicatorTypeId);
			minScore = indicatorDao.getMinStore(indicatorTypeId);
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(totalProperty).append(",result:[");
		for (int i = 0; i < list.size(); i++) {
			Indicator indicator = (Indicator) list.get(i);
			String mark = indicator.getIndicatorDesc();
			int indicatorId = indicator.getId();
			List list_option;
			if ("dl".equals(type)) {
				list_option = indicatorDao.getOptionsListOrder(indicatorId,
						"asc");
			} else {
				list_option = indicatorDao.getOptionsList(indicatorId);
			}
			if (null != list_option && list_option.size() > 0) {
				for (int j = 0; j < list_option.size(); j++) {
					Options options = (Options) list_option.get(j);
					String s = "";
					if (j > 24) {
						s = "Z";
					}
					s = String.valueOf((char) (j + 65));
					buff.append("{\"indicatorId\":");
					buff.append(indicatorId);
					buff.append(",\"indicatorName\":'");
					buff.append((i + 1) + "、" + indicator.getIndicatorName())
							.append("<font color=\"red\">(").append(mark)
							.append(")</font>");
					buff.append("',\"id\":");
					buff.append(options.getId());
					buff.append(",\"optionName\":'");
					if ("dl".equals(type)) {
						String values = ("".equals(options.getOptionName()) ? "0"
								: options.getOptionName());
						if (j < list_option.size() - 1) {

							buff.append(s + ".&nbsp;&nbsp;&nbsp;>" + values);
						} else {
							buff.append(s + ".&nbsp;&nbsp;&nbsp;<=" + values);

						}
					} else {
						buff.append(s + ".&nbsp;&nbsp;&nbsp;"
								+ options.getOptionName());
					}
					buff.append("',\"score\":");
					buff.append(options.getScore());
					buff.append(",\"maxScore\":");
					buff.append(maxScore);
					buff.append(",\"minScore\":");
					buff.append(minScore);
					buff.append("},");
				}
			} else {
				buff.append("{\"indicatorId\":");
				buff.append(indicatorId);
				buff.append(",\"indicatorName\":'");
				buff.append(indicator.getIndicatorName());
				buff.append("'},");
			}
		}

		if (null != list && list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
		JsonUtil.responseJsonString(buff.toString());
	}

	private Object[] getSource(String type) {
		Object[] o = new Object[2];
		if (leaf) {
			o = indicatorDao.getIndicatorForLX(type, indicatorTypeId, start,
					limit);
		} else {
			//全部  0/by
			if(indicatorTypeId==0){
			o=mySelfService.getForList("Indicator", start,limit);
			}
			else{
				o=mySelfService.getForList("Indicator", start,limit);
			}
		}
		return o;
	}

	public MakeScoreGetSubDao getMakeScoreGetSubDao() {
		return makeScoreGetSubDao;
	}

	public void setMakeScoreGetSubDao(MakeScoreGetSubDao makeScoreGetSubDao) {
		this.makeScoreGetSubDao = makeScoreGetSubDao;
	}

}
