package com.zhiwei.credit.action.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.DepreRecord;
import com.zhiwei.credit.model.admin.FixedAssets;
import com.zhiwei.credit.service.admin.DepreRecordService;
import com.zhiwei.credit.service.admin.FixedAssetsService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * 
 * @author csx
 * 
 */
public class DepreRecordAction extends BaseAction {
	@Resource
	private DepreRecordService depreRecordService;
	private DepreRecord depreRecord;
	@Resource
	private FixedAssetsService fixedAssetsService;

	private Long recordId;

	static int YEARS = 11;

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public DepreRecord getDepreRecord() {
		return depreRecord;
	}

	public void setDepreRecord(DepreRecord depreRecord) {
		this.depreRecord = depreRecord;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<DepreRecord> list = depreRecordService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		JSONSerializer serializer = new JSONSerializer();
		serializer.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"),
				"calTime");
		buff.append(serializer.exclude(new String[] { "class" }).serialize(
				list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				depreRecordService.remove(new Long(id));
			}
		}
		jsonString = "{success:true}";
		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		DepreRecord depreRecord = depreRecordService.get(recordId);
		Gson gson = new Gson();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(depreRecord));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	/**
	 * 折旧运算 方法：1代表平均年限法，2代表工作量法，3代表双倍余额递减折旧法，4代表年数总和折旧法
	 * A、平均年限法又称为直线法，是将固定资产的折旧均衡地分摊到各期的一种方法。采用这种方法计算的每期折旧额均是等额的。计算公式如下：
	 * 　　年折旧率＝（1－预计净利残值率）/预计使用年限×100％ 　　月折旧率＝年折旧率÷12 　　月折旧额＝固定资产原价×月折旧率
	 * 　　上述计算的折旧率是按个别固定资产单独计算的
	 * ，称为个别折旧率，即某项固定资产在一定期间的折旧额与该固定资产原价的比率。通常，企业按分类折旧来计算折旧率，计算公式如下：
	 * 　　某类固定资产年折旧额＝（某类固定资产原值－预计残值＋清理费用）/该类固定资产的使用年限 　　某类固定资产月折旧额＝某类固定资产年折旧额/12
	 * 　　某类固定资产年折旧率＝该类固定资产年折旧额/该类固定资产原价×100％
	 * 　　采用分类折旧率计算固定资产折旧，计算方法简单，但准确性不如个别折旧率。
	 * 　　采用平均年限法计算固定资产折旧虽然简单，但也存在一些局限性。例如，固定资产在不同使用年限提供的经济效益不同
	 * ，平均年限法没有考虑这一事实。又如，固定资产在不同使用年限发生的维修费用也不一样，平均年限法也没有考虑这一因素。
	 * 因此，只有当固定资产各期的负荷程度相同，各期应分摊相同的折旧费时，采用平均年限法计算折旧才是合理的。
	 * 
	 * B、工作量法是根据实际工作量计提折旧额的一种方法。这种方法可以弥补平均年限法只重使用时间，不考虑使用强度的缺点，计算公式为：
	 * 每一工作量折旧额＝{固定资产原价×（1－残值率）预计总工作量某项固定资产月折旧额＝该项固定资产当月工作量×第一工作量折旧额
	 * 
	 * C、双倍余额递减法是在不考虑固定资产残值的情况下，根据每一期期初固定资产账面净值和双倍直线法折旧额计算固定资产折旧的一种方法。计算公式如下：
	 * 　　年折旧率＝2/预计的折旧年限×100％ 　　月折旧率＝年折旧率÷12 　　月折旧额＝固定资产账面净值×月折旧率
	 * 　　这种方法没有考虑固定资产的残值收入
	 * ，因此不能使固定资产的账面折余价值降低到它的预计残值收入以下，即实行双倍余额递减法计提折旧的固定资产，应当在其固定资产折旧年限到期的最后两年
	 * ，将固定资产净值扣除预计净残值后的余额平均摊销。 　　例如：某企业一固定资产的原价为10
	 * 000元，预计使用年限为5年，预计净残值200元，按双倍余额递减法计算折旧，每年的折旧额为： 　　双倍余额年折旧率＝2/5×100％＝40％
	 * 　　第一年应提的折旧额＝10 000×40％＝4000（元） 　　第二年应提的折旧额＝（10 000－4 000）×40％＝2 400（元）
	 * 　　第三年应提的折旧额＝（6 000－2 400）×40％＝1 440（元） 　　从第四年起改按平均年限法（直线法）计提折旧。
	 * 第四、第五年的年折旧额＝（10 000－4 000－2 400－1 400－200）/2＝980（元）
	 * 
	 * D、年数总和法也称为合计年限法，是将固定资产的原值减去净残值后的净额和以一个逐年递减的分数计算每年的折旧额，
	 * 这个分数的分子代表固定资产尚可使用的年数，分母代表使用年数的逐年数字总和。计算公式为： 　　年折旧率＝尚可使用年限/预计使用年限折数总和
	 * 　　或：年折旧率＝（预计使用年限－已使用年限）／（预计使用年限×{预计使用年限＋1}÷2×100％ 　　月折旧率＝年折旧率÷12
	 * 　　月折旧额＝（固定资产原值－预计净残值）×月折旧率 　　仍以前例来说明，若采用年数总和法计算，各年的折旧额如下表：
	 * 　　原值－净残值　每年折旧额　累计折旧 　　年份　尚可使用年限　变动折旧率 　　（元）　（元）　（元） 　　1　5　9 800　5／15　3
	 * 266.7　3 266.7 　　2　4　9 800　4／15　2 613.3　5 880 　　3　3　9 800　3／15　1 960　7 840
	 * 　　4　2　9 800　2／15　1 306.7　9 146.7 　　5　1　9 800　1／15　653.3　9 800
	 * 　　由上表可以看出，年数总和法所计算的折旧费随着年数的增加而逐渐递减，这样可以保持固定资产使用成本的均衡性和防止固定资产因无损耗而遭受的损失。
	 * 
	 * 
	 */
	public String depreciate() {
		String strAssetsId = getRequest().getParameter("ids");
		if (StringUtils.isNotEmpty(strAssetsId)) {
			FixedAssets fixedAssets = fixedAssetsService.get(new Long(
					strAssetsId));
			BigDecimal cruValue = fixedAssets.getAssetCurValue();
			BigDecimal assetValue = fixedAssets.getAssetValue();
			short method = fixedAssets.getDepreType().getCalMethod();
			Integer i = 0;
			if (method == 1) { // 平均年限法
				BigDecimal yearRate = new BigDecimal(0);
				yearRate = fixedAssets.getDepreRate();// 年折旧率
				BigDecimal monthRate = yearRate
						.divide(new BigDecimal(12), 2, 2); // 月折旧率
				Date lastCalTime = depreRecordService.findMaxDate(new Long(
						strAssetsId));
				if (lastCalTime == null) {
					lastCalTime = fixedAssets.getStartDepre();
				}
				Integer deprePeriod = fixedAssets.getDepreType()
						.getDeprePeriod();
				BigDecimal value = assetValue.multiply(
						new BigDecimal(deprePeriod.toString())).multiply(
						monthRate);// 一个周期折旧的值
				GregorianCalendar gc1 = new GregorianCalendar();
				gc1.setTime(lastCalTime);
				GregorianCalendar gcYears = new GregorianCalendar();
				gcYears.setTime(fixedAssets.getStartDepre());
				Integer years = fixedAssets.getIntendTerm().intValue();
				gcYears.add(Calendar.YEAR, years);
				while (deprePeriod >= 1) {
					gc1.add(Calendar.MONTH, deprePeriod);
					Date curDate = gc1.getTime();
					if (curDate.after(new Date())
							|| curDate.after(gcYears.getTime())) {
						break;
					} else {
						i++;
						DepreRecord depreR = new DepreRecord();
						depreR.setFixedAssets(fixedAssets);
						depreR.setCalTime(curDate);
						cruValue = cruValue.subtract(value);
						if (cruValue.compareTo(new BigDecimal("0.001")) == -1) {
							break;
						}
						depreR.setDepreAmount(value);
						depreRecordService.save(depreR);
					}
				}
			} else if (method == 2) {// 工作量法
				i++;
				String cruCalDate = getRequest().getParameter("cruCalDate");
				if (StringUtils.isNotEmpty(cruCalDate)) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date calDate = new Date();
					try {
						calDate = sdf.parse(cruCalDate);
					} catch (ParseException e) {
						e.printStackTrace();
						setJsonString("{success:false}");
						return SUCCESS;
					}
					BigDecimal total = new BigDecimal(1).subtract(
							fixedAssets.getRemainValRate().divide(
									new BigDecimal(100))).multiply(
							fixedAssets.getAssetValue());
					BigDecimal per = total.divide(fixedAssets
							.getIntendWorkGross(), 2, 2);
					cruValue = cruValue.subtract(per.multiply(depreRecord
							.getWorkCapacity()));

					depreRecord.setCalTime(calDate);
					depreRecord.setFixedAssets(fixedAssets);
					depreRecord.setDepreAmount(per.multiply(depreRecord
							.getWorkCapacity()));
					depreRecordService.save(depreRecord);
				} else {
					setJsonString("{success:false}");
					return SUCCESS;
				}
			} else if (method == 3) {// 双倍余额递减折旧法
				Integer deprePeriod = fixedAssets.getDepreType()
						.getDeprePeriod();
				Date lastCalTime = depreRecordService.findMaxDate(new Long(
						strAssetsId));
				if (lastCalTime == null) {
					lastCalTime = fixedAssets.getStartDepre();
				}
				Date startDepre = fixedAssets.getStartDepre();
				GregorianCalendar gc1 = new GregorianCalendar();
				GregorianCalendar gcYear = new GregorianCalendar();
				GregorianCalendar gcYears = new GregorianCalendar();
				BigDecimal yearRate = new BigDecimal(2).divide(fixedAssets
						.getIntendTerm(), 2, 3);
				BigDecimal monthRate = yearRate
						.divide(new BigDecimal(12), 2, 3);
				gcYear.setTime(startDepre);
				Integer years = fixedAssets.getIntendTerm().intValue();
				if (years > 2) {
					gcYear.add(Calendar.YEAR, years - 2);
				}
				gcYears.setTime(startDepre);
				gcYears.add(Calendar.YEAR, years);
				gc1.setTime(lastCalTime);
				Integer flag = 0;
				BigDecimal twoYearValue = new BigDecimal(0);
				while (deprePeriod > 0) {
					DepreRecord depreR = new DepreRecord();
					BigDecimal bd = new BigDecimal(0);
					Date last = gc1.getTime();
					gc1.add(Calendar.MONTH, deprePeriod);
					if (gc1.getTime().after(new Date())
							|| gc1.getTime().after(gcYears.getTime())) {
						break;
					}
					i++;
					if (!gc1.getTime().after(gcYear.getTime())) {
						bd = cruValue.multiply(monthRate).multiply(
								new BigDecimal(deprePeriod.toString()));
						cruValue = cruValue.subtract(bd);
					} else {
						GregorianCalendar lastGc = new GregorianCalendar();
						lastGc.setTime(last);
						Integer j = 0;
						Date lastDate = lastGc.getTime();
						Date gcDate = gcYear.getTime();

						while (lastDate.before(gcDate)) {
							lastGc.add(Calendar.MONTH, 1);
							cruValue = cruValue.subtract(cruValue
									.multiply(monthRate));
							bd = bd.add(cruValue.multiply(monthRate));
							j++;
						}
						if (deprePeriod - j > 0) {

							if (flag == 0) {
								twoYearValue = cruValue.subtract(cruValue
										.multiply(fixedAssets
												.getRemainValRate().divide(
														new BigDecimal(100), 2,
														2))); // 扣除净残值
							}
							flag++;
							Integer w = deprePeriod - j;
							if (fixedAssets.getIntendTerm().intValue() > 1) {
								bd = bd.add(twoYearValue.divide(
										new BigDecimal(24), 2, 3).multiply(
										new BigDecimal(w.toString())));
								cruValue = cruValue
										.subtract(twoYearValue.divide(
												new BigDecimal(24), 2, 3)
												.multiply(
														new BigDecimal(w
																.toString())));
							} else {
								bd = bd.add(twoYearValue.divide(
										new BigDecimal(12), 2, 3).multiply(
										new BigDecimal(w.toString())));
								cruValue = cruValue
										.subtract(twoYearValue.divide(
												new BigDecimal(12), 2, 3)
												.multiply(
														new BigDecimal(w
																.toString())));
							}

						}
					}
					Date calTime = gc1.getTime();
					depreR.setCalTime(calTime);
					depreR.setFixedAssets(fixedAssets);
					depreR.setDepreAmount(bd);
					depreRecordService.save(depreR);
				}
			} else if (method == 4) {// 年数总和折旧法
				Integer deprePeriod = fixedAssets.getDepreType()
						.getDeprePeriod();
				Date lastCalTime = depreRecordService.findMaxDate(new Long(
						strAssetsId));
				if (lastCalTime == null) {
					lastCalTime = fixedAssets.getStartDepre();
				}
				Date startDepre = fixedAssets.getStartDepre();
				BigDecimal intendTerm = fixedAssets.getIntendTerm();
				BigDecimal total = intendTerm.multiply(
						intendTerm.add(new BigDecimal(1))).divide(
						new BigDecimal(2));
				GregorianCalendar gc1 = new GregorianCalendar();
				GregorianCalendar gcStart = new GregorianCalendar();
				gcStart.setTime(startDepre);
				gc1.setTime(lastCalTime);
				GregorianCalendar gcYears = new GregorianCalendar();
				gcYears.setTime(fixedAssets.getStartDepre());
				Integer years = fixedAssets.getIntendTerm().intValue();
				gcYears.add(Calendar.YEAR, years);
				BigDecimal stValue = fixedAssets.getAssetValue().multiply(
						new BigDecimal(1).subtract(fixedAssets
								.getRemainValRate().divide(new BigDecimal(100),
										2, 2)));
				while (deprePeriod > 0) {
					Date last = gc1.getTime();
					GregorianCalendar gcLast = new GregorianCalendar();
					gcLast.setTime(last);
					gc1.add(Calendar.MONTH, deprePeriod);

					BigDecimal depValue = new BigDecimal(0);
					Integer jian = gc1.get(Calendar.YEAR)
							- gcLast.get(Calendar.YEAR);// 两个时间的间隔年数,从而判定用什么的折算率来折算
					if (gc1.getTime().after(new Date())
							|| gc1.getTime().after(gcYears.getTime())) {
						break;
					}
					i++;
					Integer be = gc1.get(Calendar.YEAR)
							- gcStart.get(Calendar.YEAR);
					BigDecimal rate = intendTerm.subtract(new BigDecimal(be))
							.divide(total, 2, 2);
					if (jian == 0) {
						depValue = stValue.multiply(rate).multiply(
								new BigDecimal(deprePeriod).divide(
										new BigDecimal(12), 2, 2));
						cruValue = cruValue.subtract(depValue);
					} else {
						Integer beLast = gcLast.get(Calendar.YEAR)
								- gcStart.get(Calendar.YEAR);// 上一年时已经使用的年数
						BigDecimal rateLast = intendTerm.subtract(
								new BigDecimal(beLast)).divide(total, 2, 2);// 上一年的折算率
						Integer months = YEARS - gcLast.get(Calendar.MONTH);// 计算剩下的月数
						depValue = stValue.multiply(rateLast).multiply(
								new BigDecimal(months).divide(
										new BigDecimal(12), 2, 2));

						Integer monthsNextYear = gc1.get(Calendar.MONTH) + 1;// 下一年的月数
						depValue = depValue.add(stValue.multiply(rate)
								.multiply(
										new BigDecimal(monthsNextYear).divide(
												new BigDecimal(12), 2, 2)));
						cruValue = cruValue.subtract(depValue);
					}
					DepreRecord depreR = new DepreRecord();
					Date cruDate = gc1.getTime();
					depreR.setCalTime(cruDate);
					depreR.setFixedAssets(fixedAssets);
					depreR.setDepreAmount(depValue);
					depreRecordService.save(depreR);
				}
			}
			fixedAssets.setAssetCurValue(cruValue);
			fixedAssetsService.save(fixedAssets);
			if (i == 0) {
				setJsonString("{success:false,message:'还没到折算时间!'}");
			} else {
				setJsonString("{success:true}");
			}
		} else {
			setJsonString("{success:false}");
		}

		return SUCCESS;
	}

	/**
	 * 传输折算时间
	 * 
	 * @return
	 */
	public String work() {
		String id = getRequest().getParameter("ids");
		if (StringUtils.isNotEmpty(id)) {
			Long assetsId = new Long(id);
			FixedAssets fixedAssets = fixedAssetsService.get(assetsId);
			Date lastCalTime = depreRecordService.findMaxDate(assetsId);
			if (lastCalTime == null) {
				lastCalTime = fixedAssets.getStartDepre();
			}
			if (lastCalTime != null) {
				Integer deprePeriod = fixedAssets.getDepreType()
						.getDeprePeriod();
				GregorianCalendar gc1 = new GregorianCalendar();
				gc1.setTime(lastCalTime);
				gc1.add(Calendar.MONTH, deprePeriod);
				Date cruCalTime = gc1.getTime();
				if (cruCalTime.before(new Date())) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String strLastDate = sdf.format(lastCalTime);
					String strCruDate = sdf.format(cruCalTime);
					String unit = fixedAssets.getWorkGrossUnit();
					BigDecimal defPerWorkGross = fixedAssets
							.getDefPerWorkGross();
					setJsonString("{success:true,lastCalTime:'" + strLastDate
							+ "',cruCalTime:'" + strCruDate
							+ "',workGrossUnit:'" + unit
							+ "',defPerWorkGross:'"
							+ defPerWorkGross.toString() + "'}");
				} else {
					setJsonString("{success:false,message:'还没到折算时间!'}");
				}
			} else {
				setJsonString("{success:false,message:'未设定开始执行折算时间!'}");
			}
		} else {
			setJsonString("{success:false,message:'请联系管理员!'}");
		}

		return SUCCESS;

	}
}
