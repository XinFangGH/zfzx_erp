package com.zhiwei.credit.action.creditFlow.creditmanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.CreditRatingDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.IndicatorDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.RatingTemplateDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.ScoreGradeOfClassDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.CreditRating;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Indicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Options;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingIndicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingOption;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingTemplate;
import com.zhiwei.credit.model.creditFlow.creditmanagement.ScoreGradeOfClass;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateIndicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateOptions;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseFinance;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;

public class CreditRatingAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private CreditBaseDao creditBaseDao;
	private int id;

	private String projectId = "";

	private CreditRating creditRating;

	private String customerType = "";
	private String customerName = "";
	private String creditTemplate = "";
	@Resource
	private CreditRatingDao creditRatingDao;
	@Resource
	private RatingTemplateDao ratingTemplateDao;
	@Resource
	private IndicatorDao indicatorDao;
	@Resource
	private ScoreGradeOfClassDao scoreGradeOfClassDao;
	private int creditTemplateId;// 指标模板id

	private int customerId;
	private String userId;
	private String startScore;
	private String endScore;
	private String grandName;
	private Boolean isAll;

	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}

	@Resource
	private GLGuaranteeloanProjectService glGuaranteeloanProjectService;

	private Integer[] arr;

	// 获取分数等级
	public void getScoreGrade() {
		float score = creditRating.getRatingScore();
		if (score > 100) {
			score = 100;
		}
		if (score < 0) {
			score = 0;
		}
		RatingTemplate rt = ratingTemplateDao
				.getRatingTemplate(creditTemplateId);
		ScoreGradeOfClass scoreGradeOfClass = scoreGradeOfClassDao
				.getScoreGrade(rt.getClassId(), score);
		/*
		 * ScoreGrade scoreGrade = creditRatingDao.getScoreGrade(score);
		 * 
		 * try { JsonUtil.jsonFromObject(scoreGrade, true); } catch (Exception
		 * e) { e.printStackTrace(); }
		 */
		JsonUtil.jsonFromObject(scoreGradeOfClass, true);
	}

	public String add() {
		int templateId = creditRating.getCreditTemplateId();
		// int templateId = creditTemplateId;
		List list = creditRatingDao.getIndicatorByTempId(templateId);

		int templateScore = 0;
		for (int i = 0; i < list.size(); i++) {
			TemplateIndicator ti = (TemplateIndicator) list.get(i);
			int indicatorId = ti.getId();
			List list_option = creditRatingDao.getOptionsList(indicatorId);
			ti.setOptionsList(list_option);
			int maxScore = 0;
			for (int ii = 0; ii < list_option.size(); ii++) {
				TemplateOptions to = (TemplateOptions) list_option.get(ii);
				int score = to.getScore();
				if (ii == 0) {
					maxScore = score;
				}
				maxScore = (maxScore > score) ? maxScore : score;
			}
			templateScore += maxScore;
		}
		creditRating.setTemplateScore(templateScore);
		getRequest().setAttribute("list", list);

		return SUCCESS;
	}

	// 节点添加企业资信评估
	public void addB() {
		creditRating = new CreditRating();

		List list = creditRatingDao.getIndicatorByTempId(creditTemplateId);

		int templateScore = 0;
		for (int i = 0; i < list.size(); i++) {
			TemplateIndicator ti = (TemplateIndicator) list.get(i);
			int indicatorId = ti.getId();
			List list_option = creditRatingDao.getOptionsList(indicatorId);
			ti.setOptionsList(list_option);
			int maxScore = 0;
			for (int ii = 0; ii < list_option.size(); ii++) {
				TemplateOptions to = (TemplateOptions) list_option.get(ii);
				int score = to.getScore();
				if (ii == 0) {
					maxScore = score;
				}
				maxScore = (maxScore > score) ? maxScore : score;
			}
			templateScore += maxScore;
		}
		creditRating.setTemplateScore(templateScore);

		try {
			JsonUtil.jsonFromList(list, list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String addJson() {

		// int templateId = creditRating.getCreditTemplateId();
		List list = creditRatingDao.getIndicatorByTempId(creditTemplateId);
		StringBuffer buff = new StringBuffer("{success:true,result:[");

		for (int i = 0; i < list.size(); i++) {
			TemplateIndicator ti = (TemplateIndicator) list.get(i);
			int indicatorId = ti.getId();
			List list_option = creditRatingDao.getOptionsList(indicatorId);

			int maxScore = creditRatingDao.getMaxStore(indicatorId);
			for (int j = 0; j < list_option.size(); j++) {

				TemplateOptions to = (TemplateOptions) list_option.get(j);
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
				buff.append("{\"indicatorId\":");
				buff.append(indicatorId);
				buff.append(",\"indicatorName\":'");
				buff.append((i + 1) + "、" + ti.getIndicatorName());
				buff.append("',\"optionId\":");
				buff.append(to.getId());
				buff.append(",\"xuhao\":'");
				buff.append(s);
				buff.append("',\"optionName\":\"");
				String str = "<input type='radio'  name='" + ti.getId() + "s"
						+ (i + 1) + "' value='" + to.getId()
						+ "' onclick=\\\"check('" + to.getScore() + "','"
						+ ti.getQuanzhong() + "','" + maxScore + "','"
						+ ti.getId() + "score','" + ti.getId()
						+ "maxStore','op" + to.getId() + "score','"
						+ ti.getId() + "quanzhong')\\\" />"
						+ to.getOptionName();
				buff.append(str);
				buff.append("\",\"score\":\"");
				buff.append("<label id='op" + to.getId() + "score"
						+ "' name='opt" + ti.getId() + "score'>"
						+ to.getScore() + "</label>");
				buff.append("\",\"defen\":\"");
				buff.append("<font color=red><label id='" + ti.getId()
						+ "score" + "'>0</label><label id='" + ti.getId()
						+ "quanzhong" + "'>0</label><label id='" + ti.getId()
						+ "maxStore" + "'>0</label></font>");
				buff.append("\"},");

			}

		}

		if (null != list && list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
		JsonUtil.responseJsonString(buff.toString());

		return null;
	}

	public String result() {
		creditRating = creditRatingDao.getCreditRating(id);

		String str_ratingIndicatorId = creditRating.getArr_id();
		String[] arr_ratingIndicatorId = str_ratingIndicatorId.split(",");

		int templateId = creditRating.getCreditTemplateId();
		List<?> list = creditRatingDao.getIndicatorByTempId(templateId);

		String str_score = creditRating.getArr_score();
		String str_ans = creditRating.getAns();// 获取答案集合
		String[] arr_ans = null;
		if (str_ans != null && !"".equals(str_ans)) {
			arr_ans = str_ans.split(",");
		} else {
			str_ans = null;// 用来判断，兼容原有
		}
		String[] arr_score = str_score.split(",");

		StringBuffer buff = new StringBuffer("{success:true,result:[");
		for (int i = 0; i < arr_ratingIndicatorId.length; i++) {
			if ("".equals(arr_ratingIndicatorId[i])) {
				continue;
			}
			int ratingIndicatorId = Integer.parseInt(arr_ratingIndicatorId[i]);
			RatingIndicator ri = (RatingIndicator) creditRatingDao.findRatingIndicator(ratingIndicatorId);

			int scoreResult = Integer.parseInt(arr_score[i]);
			String ansResult = "";
			if (str_ans != null) {
				ansResult = arr_ans[i];
				if ("".equals(ansResult.trim())) {
					ansResult = "(<font color=\"red\">未填入选项,已参照最低分数</font>)";
				} else {
					ansResult = "(<font color=\"red\">"+ansResult+"</font>)";
				}
				String name = ri.getIndicatorName();
				String _name=name+ansResult;
				ri.setIndicatorName(_name);
			}

			ri.setScoreResult(scoreResult);

			List<?> ratingOptionList = creditRatingDao.getRatingOptionListOrder(ri.getId());
			/*-------------------------*/
			for (int j = 0; j < ratingOptionList.size(); j++) {
				RatingOption ro = (RatingOption) ratingOptionList.get(j);
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
				buff.append("{\"indicatorId\":");
				buff.append(ratingIndicatorId);
				buff.append(",\"indicatorName\":'");
				buff.append((i + 1) + "、" + ri.getIndicatorName());
				buff.append("',\"optionId\":");
				buff.append(ro.getId());
				buff.append(",\"xuhao\":'");
				buff.append(s);
				buff.append("',\"optionName\":\"");
				String str = "<input type='radio' id='" + ratingIndicatorId
						+ "' value='" + ro.getId() + "' "
						+ ((ro.getScore() == scoreResult) ? "checked" : "")
						+ " disabled=true/>" + ro.getOptionName();
				buff.append(str);
				buff.append("\",\"score\":\"");
				buff
						.append((ro.getScore() == scoreResult) ? "<font color='red'>"
								+ ro.getScore() + "</font>"
								: "<font color='Gray'>" + ro.getScore()
										+ "</font>");
				buff.append("\"},");

			}

		}
		if (null != list && list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
		JsonUtil.responseJsonString(buff.toString());


		return null;
	}

	public void addSub() {
		// mid
		int templateId = creditRating.getCreditTemplateId();
		List<TemplateIndicator> indicatorList = creditRatingDao
				.getIndicatorByTempId(templateId);
		// 题号
		String str_indicatorId = creditRating.getArr_id();
		String[] arr_indicatorId = str_indicatorId.split(",");
		int[] arr_ratingIndicatorId = new int[arr_indicatorId.length];

		List<RatingIndicator> ratingIndicatorList = new ArrayList<RatingIndicator>();
		for (int i = 0; i < arr_indicatorId.length; i++) {
			if ("".equals(arr_indicatorId[i])) {
				continue;
			}
			int templateIndicatorId = Integer.parseInt(arr_indicatorId[i]);
			TemplateIndicator templateIndicator = (TemplateIndicator) creditRatingDao
					.findTemplateIndicator(templateIndicatorId);

			RatingIndicator ratingIndicator = new RatingIndicator(creditRating
					.getId(), templateIndicator.getId(), templateIndicator
					.getIndicatorTypeId(),
					templateIndicator.getIndicatorType(), templateIndicator
							.getIndicatorName(), templateIndicator
							.getIndicatorDesc(),
					templateIndicator.getCreater(), templateIndicator
							.getCreateTime(), templateIndicator
							.getLastModifier(), templateIndicator
							.getLastModifTime());

			ratingIndicatorList.add(ratingIndicator);

			int indicatorId = (Integer) creditRatingDao
					.addRatingIndicator(ratingIndicator);

			arr_ratingIndicatorId[i] = indicatorId;
			List templateOptionList = creditRatingDao
					.getTemplateOptionList(templateIndicator.getId());

			for (int j = 0; j < templateOptionList.size(); j++) {
				TemplateOptions o_old = (TemplateOptions) templateOptionList
						.get(j);
				RatingOption ro = new RatingOption();
				ro.setIndicatorId(indicatorId);
				ro.setOptionName(o_old.getOptionName());
				ro.setScore(o_old.getScore());
				ro.setSortNo(o_old.getSortNo());
				creditRatingDao.addTemplateOptions(ro);
			}
		}
		String arrId = "";
		for (int i = 0; i < arr_ratingIndicatorId.length; i++) {
			if (i == 0) {
				arrId += arr_ratingIndicatorId[0];
			} else {
				arrId += "," + arr_ratingIndicatorId[i];
			}
		}
		creditRating.setArr_id(arrId);
		// 增加评估日期 和评估人。
		Date date = new Date();
		creditRating.setRatingTime(date);
		AppUser userInfo = ContextUtil.getCurrentUser();

		creditRating.setRatingMan(userInfo.getFullname());
		creditRating.setUserId(userInfo.getUserId());
		creditRating.setCompanyId(ContextUtil.getLoginCompanyId());
		boolean b = creditRatingDao.addCreditRating(creditRating);
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

	// 获取用户与当前题目性质
	public void getCurrentUser() {
		String type = getRequest().getParameter("type");
		if (!"2".equals(type)) {
			AppUser userInfo = ContextUtil.getCurrentUser();
			String username = userInfo.getFullname();
			String msg = "{success:true,username:'" + username + "'}";
			JsonUtil.responseJsonString(msg);
		} else {
			String cid = getRequest().getParameter("cId");// 客户id
			String _mid = getRequest().getParameter("mid");// 模板id
			int mid = Integer.valueOf(_mid);
			// 总题目个数
			List<TemplateIndicator> indicatorList = creditRatingDao
					.getIndicatorByTempId(mid);// 题库
			// 自动计算数据
			// 题目下的选项。。
			List<TemplateOptions> xx;
			List<String> l = new ArrayList();// 题目
			// 规则
			Map<String, List<TemplateOptions>> m = new HashMap<String, List<TemplateOptions>>();
			for (int i = 0; i < indicatorList.size(); i++) {
				xx = creditRatingDao.getOptionsList(indicatorList.get(i)
						.getId());
				l.add(indicatorList.get(i).getIndicatorName());
				m.put(indicatorList.get(i).getIndicatorName(), xx);
			}

			// 答案信息
			Map dm = new HashMap();
			List<EnterpriseFinance> list = null;
			String hql = "";
			try {
				hql = "from EnterpriseFinance e where e.enterpriseId=?";
				int id = Integer.valueOf(cid);
				list = creditBaseDao.queryHql(hql, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (EnterpriseFinance e : list) {
				if ("id_xxxx_xxxx124".equals(e.getTextFeildId())) {
					dm.put("总资产", e.getTextFeildText());
				}
			}

			int scources = 0;

			for (int i = 0; i < l.size(); i++) {
				String exam = l.get(i);// 题目

				int value = getExamValue(exam, dm, m);
				scources = scources + scources;
				// dm.put(exam, value);

			}
			// dm("timu",daan) 答案 m("timu",List) 规则 L:题目
			//

			// for (int i = 0; i < l.size(); i++) {
			// String exam = (String) l.get(i);
			// int dn = Integer.valueOf((String) dm.get(exam)); // 数值 量化
			// scources = scources + getSource(dn, (List) dm.get(exam));
			// }

			String msg = "{success:true,scources:'" + scources + "'}";
			JsonUtil.responseJsonString(msg);

		}
	}

	// 通过答案与规则或则结果
	private int getSource(int v, List l) {
		// 分析规则l--ptionsList
		TemplateOptions s = (TemplateOptions) l.get(0);
		int score = s.getScore();
		// 规则由于数值的小到大排序
		for (int i = 0; i < l.size(); i++) {
			TemplateOptions ss = (TemplateOptions) l.get(i);
			if (v > Integer.valueOf(ss.getOptionName()))
				score = ss.getScore();
		}
		return score;

	}

	private int getExamValue(String exam, Map dm,
			Map<String, List<TemplateOptions>> m) {
		int answer = Integer.valueOf((String) dm.get(exam));
		List<TemplateOptions> gz = m.get(exam);// gz 小到大
		// List<TemplateOptions> gz = m.get("总资产");// gz 小到大
		int score = gz.get(0).getScore();
		for (TemplateOptions t : gz) {
			String s = t.getOptionName();
			int c = ("".equals(s) ? 0 : Integer.valueOf(s));
			if (answer > c) {
				score = t.getScore();
			} else {
				break;
			}

		}

		return score;
	}

	public String delete() {
		String msg = "";
		try {
			for (int i = 0; i < arr.length; i++) {
				if(arr[i]!=null){
					creditRatingDao.deleteCreditRating(arr[i]);
				}
			}
			msg = "{success:true}";
		} catch (Exception e) {
			msg = "{success:false}";
			e.printStackTrace();
		}
		JsonUtil.responseJsonString(msg);
        return null;
	}

	public void crList() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		String RoleNames = currentUser.getRoleNames();
		String roles[] = RoleNames.split(",");
		Boolean RoleKey = false;// 用来判断当前登陆者是不是超级管理员： true表示是超级管理员
		for (int i = 0; i < roles.length; i++) {
			if ("超级管理员".equals(roles[i])) {
				RoleKey = true;
			}
		}
		// 授权查询全部资信评估的代码begin 在这里为companyId赋值
		Boolean flag = Boolean.valueOf(AppUtil.getSystemIsGroupVersion());// 判断当前是否为集团版本
		String roleType = ContextUtil.getRoleTypeSession();
		String companyId = "";
		if (!RoleKey) {
			if (flag) {// 表示为集团版本
				if (roleType.equals("control")) {// 具有管控角色
					companyId = ContextUtil.getBranchIdsStr();
				} else {// 不具有管控角色
					if (!isAll) {// 不具有查看所有资信评估的权限
						companyId = String.valueOf(ContextUtil
								.getLoginCompanyId());
					}
				}

			} else {// 表示不为集团版本
				companyId = String.valueOf(ContextUtil.getLoginCompanyId());
				if (!isAll) {
					userId=currentUser.getId();
				}
			}
		}
		List list = creditRatingDao.getCreditRatingList(customerType,
				customerName, creditTemplate, projectId, start, limit, userId,
				startScore, endScore, grandName, companyId);
		int totalProperty = creditRatingDao.getCreditRatingListNum(
				customerType, customerName, creditTemplate, projectId, userId,
				startScore, endScore, grandName, companyId);
		try {
			JsonUtil.jsonFromList(list, totalProperty);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 节点
	public void listOfJD() {
		if (null != projectId && !"".equals(projectId)) {
			GLGuaranteeloanProject glGuaranteeloanProject = glGuaranteeloanProjectService
					.get(Long.parseLong(projectId));
			if (null != glGuaranteeloanProject) {
				customerId = glGuaranteeloanProject.getOppositeID().intValue();
				if (null != glGuaranteeloanProject.getOppositeType()
						&& glGuaranteeloanProject.getOppositeType().equals(
								"company_customer")) {
					customerType = "企业";
				} else if (null != glGuaranteeloanProject.getOppositeType()
						&& glGuaranteeloanProject.getOppositeType().equals(
								"person_customer")) {
					customerType = "个人";
				}
			}
		}
		List list = creditRatingDao.getCreditRatingListOfJD(customerType,
				customerId, start, limit);
		int totalProperty = creditRatingDao.getCreditRatingListNumOfJD(
				customerType, customerId);
		try {
			JsonUtil.jsonFromList(list, totalProperty);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//定量模板，系统自动计算分值
	public void autoAddSub() {
		try {
			String creditTemplateName=this.getRequest().getParameter("creditTemplateName");
			CreditRating creditRatingt=new CreditRating();
			creditRatingt.setCustomerId(customerId);
			creditRatingt.setCustomerName(customerName);
			creditRatingt.setCustomerType(customerType);
			Date date = new Date();
			creditRatingt.setRatingTime(date);
			AppUser userInfo = ContextUtil.getCurrentUser();

			creditRatingt.setRatingMan(userInfo.getFullname());
			creditRatingt.setUserId(userInfo.getUserId());
			String Atr_id="";
			String Atr_store="";
			String Atr_ants="";
			int maxScore=0;
			float ratingScore=0;
			if(creditTemplateId!=0 &&!"".equals(creditTemplateId)){
				creditRatingt.setCreditTemplateId(creditTemplateId);
				creditRatingt.setCreditTemplate(creditTemplateName);
				List<TemplateIndicator> indicatorList = creditRatingDao.getIndicatorByTempId(creditTemplateId);
				if(indicatorList!=null&&indicatorList.size()>0){
					for(TemplateIndicator  temp:indicatorList){
						int quanzhong=temp.getQuanzhong();
						maxScore+=ratingTemplateDao.getMaxScoreOfOption(temp.getId())+ratingTemplateDao.getMaxScoreOfOption(temp.getId())* quanzhong;
						RatingIndicator ratingIndicator = new RatingIndicator(0, temp.getId(), temp.getIndicatorTypeId(),temp.getIndicatorType(),
														  temp.getIndicatorName(), temp.getIndicatorDesc(),temp.getCreater(), temp.getCreateTime(),
				                                          temp.getLastModifier(), temp.getLastModifTime());
						int indicator_Id = (Integer) creditRatingDao.addRatingIndicator(ratingIndicator);
						Atr_id+=indicator_Id+",";
						List templateOptionList = creditRatingDao.getTemplateOptionList(temp.getId());
						for (int j = 0; j < templateOptionList.size(); j++) {
							TemplateOptions o_old = (TemplateOptions) templateOptionList
									.get(j);
							RatingOption ro = new RatingOption();
							ro.setIndicatorId(indicator_Id);
							ro.setOptionName(o_old.getOptionName());
							ro.setScore(o_old.getScore()*quanzhong);
							ro.setSortNo(o_old.getSortNo());
							creditRatingDao.addTemplateOptions(ro);
						}
						Indicator indicator =indicatorDao.getIndicator(temp.getParentIndicatorId());
						String valueTest="";
						//从系统中拿到值
						if(customerId!=0 && !"".equals(customerId) && null!=indicator && indicator.getOperationType()!=null &&!"".equals(indicator.getOperationType()) && indicator.getElementCode()!=null && !"".equals(indicator.getElementCode())){
							valueTest=indicatorDao.getSystemCreditelementCode(customerId,indicator.getOperationType(),indicator.getElementCode());
						}
						List<Options> options=indicatorDao.getOptionsListOrder(indicator.getId(), "asc");
						if(options!=null&&options.size()>0){
							if("".equals(valueTest)){
								for(Options  tempo :options){
									if(tempo.getOptionName().contains("<=")){
										String optionName=tempo.getOptionName().substring(2, tempo.getOptionName().length());
										valueTest=optionName;
										Atr_store+=tempo.getScore()+",";
										Atr_ants+=optionName+",";
										ratingScore+=tempo.getScore()+tempo.getScore()*quanzhong;
										break;
									}else{
										continue;
									}
								}
							}else{
								for(int i=0;i<options.size();i++){
									String startValue=options.get(i).getOptionStart();
									String endValue=options.get(i).getOptionEnd();
									if(i==options.size()-1){//最后一条
										Atr_store+=options.get(i).getScore()+",";
										Atr_ants+=valueTest+",";
										ratingScore+=options.get(i).getScore()+options.get(i).getScore()*quanzhong;
									}else{
										if(Integer.parseInt(valueTest)>=Integer.parseInt(startValue) && Integer.parseInt(valueTest)<=Integer.parseInt(endValue)){
											Atr_store+=options.get(i).getScore()+",";
											Atr_ants+=valueTest+",";
											ratingScore+=options.get(i).getScore()+options.get(i).getScore()*quanzhong;
											break;
										}else{
											continue;
										}
									}
								}
							}
							
						}
					}
					if(Atr_id.length()>0){
						Atr_id=Atr_id.substring(0, Atr_id.length()-1);
					}
					if(Atr_store.length()>0){
						Atr_store=Atr_store.substring(0, Atr_store.length()-1);
					}
					if(Atr_ants.length()>0){
						Atr_ants=Atr_ants.substring(0, Atr_ants.length()-1);
					}
					creditRatingt.setArr_id(Atr_id);
					creditRatingt.setArr_score(Atr_store);
					creditRatingt.setAns(Atr_ants);
					ratingScore=Math.round((ratingScore/maxScore*100)*100)/100;
					creditRatingt.setRatingScore(ratingScore);
					RatingTemplate rt = ratingTemplateDao.getRatingTemplate(creditTemplateId);
					ScoreGradeOfClass scoreGradeOfClass = scoreGradeOfClassDao.getScoreGrade(rt.getClassId(), ratingScore);
					creditRatingt.setCreditRegister(scoreGradeOfClass.getGrandname());
					creditRatingt.setAdvise_sb(scoreGradeOfClass.getHanyi());
				}
			}
			boolean b = creditRatingDao.addCreditRating(creditRatingt);
			String msg = "";
			if (b) {
				msg = "{success:true}";
			} else {
				msg = "{success:false}";
			}
			JsonUtil.responseJsonString(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public CreditRating getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(CreditRating creditRating) {
		this.creditRating = creditRating;
	}

	public CreditRatingDao getCreditRatingDao() {
		return creditRatingDao;
	}

	public void setCreditRatingDao(CreditRatingDao creditRatingDao) {
		this.creditRatingDao = creditRatingDao;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCreditTemplate() {
		return creditTemplate;
	}

	public void setCreditTemplate(String creditTemplate) {
		this.creditTemplate = creditTemplate;
	}

	public int getCreditTemplateId() {
		return creditTemplateId;
	}

	public void setCreditTemplateId(int creditTemplateId) {
		this.creditTemplateId = creditTemplateId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartScore() {
		return startScore;
	}

	public void setStartScore(String startScore) {
		this.startScore = startScore;
	}

	public String getEndScore() {
		return endScore;
	}

	public void setEndScore(String endScore) {
		this.endScore = endScore;
	}

	public RatingTemplateDao getRatingTemplateDao() {
		return ratingTemplateDao;
	}

	public void setRatingTemplateDao(RatingTemplateDao ratingTemplateDao) {
		this.ratingTemplateDao = ratingTemplateDao;
	}

	public ScoreGradeOfClassDao getScoreGradeOfClassDao() {
		return scoreGradeOfClassDao;
	}

	public void setScoreGradeOfClassDao(
			ScoreGradeOfClassDao scoreGradeOfClassDao) {
		this.scoreGradeOfClassDao = scoreGradeOfClassDao;
	}

	public CreditBaseDao getCreditBaseDao() {
		return creditBaseDao;
	}

	public void setCreditBaseDao(CreditBaseDao creditBaseDao) {
		this.creditBaseDao = creditBaseDao;
	}

	public String getGrandName() {
		return grandName;
	}

	public void setGrandName(String grandName) {
		this.grandName = grandName;
	}

	public Integer[] getArr() {
		return arr;
	}

	public void setArr(Integer[] arr) {
		this.arr = arr;
	}

}
