package com.zhiwei.credit.action.creditFlow.creditmanagement;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.RatingTemplateDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.TemplateOptionDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Indicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Options;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingTemplate;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateIndicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateOptions;

public class RatingTemplateAction extends BaseAction {
	private int id;
	private String idArray;// 指标ID串
	private RatingTemplate ratingTemplate;

	private int indicatorId;
	@Resource
	private RatingTemplateDao ratingTemplateDao;
	@Resource
	private TemplateOptionDao templateOptionDao;
	private String indicatorStr;

	public void getInfo() {
		ratingTemplate = ratingTemplateDao.getRatingTemplate(id);

		try {
			JsonUtil.jsonFromObject(ratingTemplate, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 删除模板某项指标
	public void deleteTemplateIndicator() {
		boolean b = ratingTemplateDao.deleteTemplateIndicator(indicatorId);

		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
		JsonUtil.responseJsonString(msg);
	}

	// 为模板单独添加指标
	public void addTemplateIndicator() {
		String[] idArr = idArray.split(",");
		for (int i = 0; i < idArr.length; i++) {
			Indicator indicator = (Indicator) ratingTemplateDao
					.getIndicator(Integer.parseInt(idArr[i]));
			TemplateIndicator ti = new TemplateIndicator(indicator.getId(),
					indicator.getIndicatorTypeId(), indicator
							.getIndicatorType(), indicator.getIndicatorName(),
					indicator.getIndicatorDesc(), indicator.getCreater(),
					indicator.getCreateTime(), indicator.getLastModifier(),
					indicator.getLastModifTime(), id);
			int indicatorId = (Integer) ratingTemplateDao
					.addTemplateIndicator(ti);
			List list_option = ratingTemplateDao.getOptionList(Integer
					.parseInt(idArr[i]));

			for (int j = 0; j < list_option.size(); j++) {
				Options o_old = (Options) list_option.get(j);
				TemplateOptions o = new TemplateOptions();
				o.setIndicatorId(indicatorId);
				if(null!=o_old.getOptionName() && !"".equals(o_old.getOptionName())){
					o.setOptionName(o_old.getOptionName());
				}else{
					if(null!=o_old.getOptionEnd() && !"".equals(o_old.getOptionEnd())){
						o.setOptionName(o_old.getOptionStart()+"<=X<="+o_old.getOptionEnd());
					}else{
						o.setOptionName("X>="+o_old.getOptionStart());
					}
				}
				o.setScore(o_old.getScore());
				o.setSortNo(o_old.getSortNo());
				ratingTemplateDao.addTemplateOptions(o);
			}
		}
		String msg = "";
		msg = "{success:true}";
		JsonUtil.responseJsonString(msg);
	}

	public void update() {
		String[] idArr = idArray.split(",");

		RatingTemplate rt = ratingTemplateDao.getRatingTemplate(ratingTemplate
				.getId());
		rt.setTemplateName(ratingTemplate.getTemplateName());
		rt.setCustomerType(ratingTemplate.getCustomerType());
		rt.setApplyPoint(ratingTemplate.getApplyPoint());
		rt.setClassId(ratingTemplate.getClassId());
		rt.setClassName(ratingTemplate.getClassName());

		List<TemplateIndicator> list_templateIndicator = null;
		list_templateIndicator = ratingTemplateDao
				.getTemplateIndicator(ratingTemplate.getId());
		for (TemplateIndicator ti : list_templateIndicator) {
			for (int i = 0; i < idArr.length; i++) {
				if (idArr[i].equals("")) {

				} else if (ti.getParentIndicatorId() == Integer
						.parseInt(idArr[i])) {
					break;
				}
				if (i == idArr.length - 1) {
					ratingTemplateDao.deleteTemplateIndicator(ti.getId());
				}
			}

		}

		int templateId = rt.getId();

		List list = new ArrayList();
		for (int i = 0; i < idArr.length; i++) {
			if (list_templateIndicator.size() == 0) {
				if (idArr[i].equals("")) {
					continue;
				}
				Indicator indicator = (Indicator) ratingTemplateDao
						.getIndicator(Integer.parseInt(idArr[i]));

				TemplateIndicator ti = new TemplateIndicator(indicator.getId(),
						indicator.getIndicatorTypeId(), indicator
								.getIndicatorType(), indicator
								.getIndicatorName(), indicator
								.getIndicatorDesc(), indicator.getCreater(),
						indicator.getCreateTime(), indicator.getLastModifier(),
						indicator.getLastModifTime(), templateId);
				list.add(ti);
				int indicatorId = (Integer) ratingTemplateDao
						.addTemplateIndicator(ti);
				List list_option = ratingTemplateDao.getOptionList(Integer
						.parseInt(idArr[i]));

				for (int j = 0; j < list_option.size(); j++) {
					Options o_old = (Options) list_option.get(j);
					TemplateOptions o = new TemplateOptions();
					o.setIndicatorId(indicatorId);
					o.setOptionName(o_old.getOptionName());
					o.setScore(o_old.getScore());
					o.setSortNo(o_old.getSortNo());
					ratingTemplateDao.addTemplateOptions(o);
				}
			}
			for (int ii = 0; ii < list_templateIndicator.size(); ii++) {
				if (idArr[i].equals("")) {
					break;
				} else if (list_templateIndicator.get(ii)
						.getParentIndicatorId() == Integer.parseInt(idArr[i])) {
					break;
				}
				if (ii == list_templateIndicator.size() - 1) {
					Indicator indicator = (Indicator) ratingTemplateDao
							.getIndicator(Integer.parseInt(idArr[i]));

					TemplateIndicator ti = new TemplateIndicator(indicator
							.getId(), indicator.getIndicatorTypeId(), indicator
							.getIndicatorType(), indicator.getIndicatorName(),
							indicator.getIndicatorDesc(), indicator
									.getCreater(), indicator.getCreateTime(),
							indicator.getLastModifier(), indicator
									.getLastModifTime(), templateId);
					list.add(ti);
					int indicatorId = (Integer) ratingTemplateDao
							.addTemplateIndicator(ti);
					List list_option = ratingTemplateDao.getOptionList(Integer
							.parseInt(idArr[i]));

					for (int j = 0; j < list_option.size(); j++) {
						Options o_old = (Options) list_option.get(j);
						TemplateOptions o = new TemplateOptions();
						o.setIndicatorId(indicatorId);
						o.setOptionName(o_old.getOptionName());
						o.setScore(o_old.getScore());
						o.setSortNo(o_old.getSortNo());
						ratingTemplateDao.addTemplateOptions(o);
					}
				}
			}
		}

		boolean b = ratingTemplateDao.updateRatingTemplate(rt);

		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
		JsonUtil.responseJsonString(msg);

	}

	public void deleteRs() {
		boolean b = ratingTemplateDao.deleteRatingTemplate(id);

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

	public void rtList() {
		// 打开模板的时候使用
		String ptype = getRequest().getParameter("ptype");// dx/dl
		String ctype = getRequest().getParameter("ctype");// 企业/个人
		List<RatingTemplate> list = ratingTemplateDao.getRatingTemplateList(ptype,ctype);
		int totalProperty =list.size();
		try {
			JsonUtil.jsonFromList(list, totalProperty);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void add() {

		ratingTemplateDao.addRatingTemplate(ratingTemplate);
		int templateId = ratingTemplate.getId();

		String[] idArr = idArray.split(",");

		List list = new ArrayList();
		for (int i = 0; i < idArr.length; i++) {
			Indicator indicator = (Indicator) ratingTemplateDao
					.getIndicator(Integer.parseInt(idArr[i]));
			TemplateIndicator ti = new TemplateIndicator(indicator.getId(),
					indicator.getIndicatorTypeId(), indicator
							.getIndicatorType(), indicator.getIndicatorName(),
					indicator.getIndicatorDesc(), indicator.getCreater(),
					indicator.getCreateTime(), indicator.getLastModifier(),
					indicator.getLastModifTime(), templateId);
			list.add(ti);
			int indicatorId = (Integer) ratingTemplateDao.addTemplateIndicator(ti);
			List list_option = ratingTemplateDao.getOptionList(Integer.parseInt(idArr[i]));

			for (int j = 0; j < list_option.size(); j++) {
				Options o_old = (Options) list_option.get(j);
				TemplateOptions o = new TemplateOptions();
				o.setIndicatorId(indicatorId);
				if(null!=o_old.getOptionName() && !"".equals(o_old.getOptionName())){
					o.setOptionName(o_old.getOptionName());
				}else{
					if(null!=o_old.getOptionEnd() && !"".equals(o_old.getOptionEnd())){
						o.setOptionName(o_old.getOptionStart()+"<=X<="+o_old.getOptionEnd());
					}else{
						o.setOptionName("X>="+o_old.getOptionStart());
					}
				}
				o.setScore(o_old.getScore());
				o.setSortNo(o_old.getSortNo());
				ratingTemplateDao.addTemplateOptions(o);
			}
		}
		boolean b = ratingTemplateDao.subTemplateIndicatorSuccess(templateId);
		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
		JsonUtil.responseJsonString(msg);
	}

	/*
	 * 模板内容----------------------------------------------------------------------
	 * -----------
	 */
	public void getTemplateContentList() {
	}

	public void templateContentList() {
		List<TemplateIndicator> list = ratingTemplateDao
				.templateContentList(id);
		int maxScore = ratingTemplateDao.getMaxScore(id);
		int minScore = ratingTemplateDao.getMinScore(id);
		int typeNum = ratingTemplateDao.getIndicatorTypeNum(id);
		int qxScore = 0;
		int qnScore = 0;
		StringBuffer buff = new StringBuffer("{success:true,topics:[");
		int k = 1;
		for (int i = 0; i < list.size(); i++) {
			TemplateIndicator ti = (TemplateIndicator) list.get(i);
			if (i > 0) {
				TemplateIndicator ti1 = (TemplateIndicator) list.get(i - 1);
				if (ti.getIndicatorTypeId() == ti1.getIndicatorTypeId()) {
					k++;
				} else {
					k = 1;
				}
			}
			int indicatorId = ti.getId();
			qxScore = qxScore
					+ ratingTemplateDao.getMaxScoreOfOption(indicatorId)
					* ti.getQuanzhong();
			qnScore = qnScore
					+ ratingTemplateDao.getMinScoreOfOption(indicatorId)
					* ti.getQuanzhong();
			List list_option = ratingTemplateDao.getOptionsList(indicatorId);
			ti.setOptionsList(list_option);
			String indicatorDesc = "";
			String creater = "";
			String modifer = "";
			for (int j = 0; j < list_option.size(); j++) {
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
				default:
					s = "X";
				}
				TemplateOptions to = (TemplateOptions) list_option.get(j);
				indicatorDesc += s
						+ "."
						+ ((TemplateOptions) list_option.get(j))
								.getOptionName() + "<br/><br/>";
				creater += to.getScore() + "<br/><br/>";
				modifer += to.getScore() * ti.getQuanzhong() + "<br/></br>";
			}
			buff.append("{\"id\":");
			buff.append(indicatorId);
			buff.append(",\"indicatorTypeId\":");
			buff.append(ti.getIndicatorTypeId());
			buff.append(",\"indicatorType\":'");
			buff.append(ti.getIndicatorType());
			buff.append("',\"indicatorName\":'");
			buff.append(ti.getIndicatorName());
			buff.append("',\"parentIndicatorId\":");
			buff.append(ti.getParentIndicatorId());
			buff.append(",\"templateId\":");
			buff.append(ti.getTemplateId());
			buff.append(",\"quanzhong\":");
			buff.append(ti.getQuanzhong());
			buff.append(",\"indicatorDesc\":'");
			buff.append(indicatorDesc);
			buff.append("',\"creater\":'");
			buff.append(creater);
			buff.append("',\"maxScore\":");
			buff.append(maxScore);
			buff.append(",\"minScore\":");
			buff.append(minScore);
			buff.append(",\"qxScore\":");
			buff.append(qxScore);
			buff.append(",\"qnScore\":");
			buff.append(qnScore);
			buff.append(",\"xuhao\":");
			buff.append(k);
			buff.append(",\"modifer\":'");
			buff.append(modifer);
			buff.append("',\"typeNum\":");
			buff.append(typeNum);
			buff.append(",\"indicatorNum\":");
			buff.append(i + 1);
			buff.append("},");
		}

		if (null != list && list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");

		JsonUtil.responseJsonString(buff.toString());

	}

	/*
	 * 模板内容结束--------------------------------------------------------------------
	 * -------------
	 */

	// 提交模板指标
	public void subTemplateIndicator() {
		String[] idArr = idArray.split(",");

		List list = new ArrayList();
		for (int i = 0; i < idArr.length; i++) {
			Indicator indicator = (Indicator) ratingTemplateDao
					.getIndicator(Integer.parseInt(idArr[i]));
			TemplateIndicator ti = new TemplateIndicator(indicator.getId(),
					indicator.getIndicatorTypeId(), indicator
							.getIndicatorType(), indicator.getIndicatorName(),
					indicator.getIndicatorDesc(), indicator.getCreater(),
					indicator.getCreateTime(), indicator.getLastModifier(),
					indicator.getLastModifTime(), id);
			list.add(ti);
			int indicatorId = (Integer) ratingTemplateDao
					.addTemplateIndicator(ti);
			List list_option = ratingTemplateDao.getOptionList(Integer
					.parseInt(idArr[i]));

			for (int j = 0; j < list_option.size(); j++) {
				Options o_old = (Options) list_option.get(j);
				TemplateOptions o = new TemplateOptions();
				o.setIndicatorId(indicatorId);
				o.setOptionName(o_old.getOptionName());
				o.setScore(o_old.getScore());
				o.setSortNo(o_old.getSortNo());
				ratingTemplateDao.addTemplateOptions(o);
			}

		}

		boolean b = ratingTemplateDao.subTemplateIndicatorSuccess(id);

		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
		JsonUtil.responseJsonString(msg);

	}

	// 计算模板分数
	public void countTemplateScore() {
		int score = ratingTemplateDao.countTemplateScore(id);
		TemplateOptions to = new TemplateOptions();
		to.setScore(score);
		JsonUtil.jsonFromObject(to, true);
	}

	public void updateTemplateIndicator() {
		boolean s = false;
		if (null != indicatorStr && !"".equals(indicatorStr)) {

			String[] optionArr = indicatorStr.split("@");

			for (int i = 0; i < optionArr.length; i++) {
				String str = optionArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try {
					TemplateIndicator templateIndicator = (TemplateIndicator) JSONMapper
							.toJava(parser.nextValue(), TemplateIndicator.class);
					s = ratingTemplateDao
							.updateTemplateIndicator(templateIndicator);
					List<TemplateOptions> list = ratingTemplateDao
							.getOptionsList(templateIndicator.getId());
					for (TemplateOptions to : list) {
						to.setQzScore(to.getScore()
								* templateIndicator.getQuanzhong());
						templateOptionDao.updateTemplateOption(to);
					}

				} catch (Exception e) {
					e.printStackTrace();

				}

			}
			String msg = "";
			if (s == true) {
				msg = "{success:true}";
			} else {
				msg = "{success:false}";
			}
			JsonUtil.responseJsonString(msg);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RatingTemplate getRatingTemplate() {
		return ratingTemplate;
	}

	public void setRatingTemplate(RatingTemplate ratingTemplate) {
		this.ratingTemplate = ratingTemplate;
	}

	public RatingTemplateDao getRatingTemplateDao() {
		return ratingTemplateDao;
	}

	public void setRatingTemplateDao(RatingTemplateDao ratingTemplateDao) {
		this.ratingTemplateDao = ratingTemplateDao;
	}

	public String getIdArray() {
		return idArray;
	}

	public void setIdArray(String idArray) {
		this.idArray = idArray;
	}

	public int getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(int indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getIndicatorStr() {
		return indicatorStr;
	}

	public void setIndicatorStr(String indicatorStr) {
		this.indicatorStr = indicatorStr;
	}

	public TemplateOptionDao getTemplateOptionDao() {
		return templateOptionDao;
	}

	public void setTemplateOptionDao(TemplateOptionDao templateOptionDao) {
		this.templateOptionDao = templateOptionDao;
	}

}
