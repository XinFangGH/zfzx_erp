package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.GroupUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderInfoDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.investInfoManager.Investproject;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderInfo;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author
 */
@SuppressWarnings("unchecked")
public class PlManageMoneyPlanBuyinfoDaoImpl extends BaseDaoImpl<PlManageMoneyPlanBuyinfo> implements PlManageMoneyPlanBuyinfoDao {

    public PlManageMoneyPlanBuyinfoDaoImpl() {
        super(PlManageMoneyPlanBuyinfo.class);
    }

    @Resource
    private PlMmOrderInfoDao plMmOrderInfoDao;

    @Override
    public List<PlManageMoneyPlanBuyinfo> listbysearch(PagingBean pb,
                                                       Map<String, String> map) {
        StringBuffer hql = new StringBuffer("from PlManageMoneyPlanBuyinfo fmpb where  fmpb.startinInterestTime is not null and fmpb.state in(2,7) "); //fmpb.currentMatchingMoney >0
        String seachDate = map.get("seachDate");
        String currentMatchingMoney = map.get("currentMatchingMoney");
        String mmName = map.get("mmName");
        String keystr = map.get("keystr");
        String investPersonId = map.get("investPersonName");
        String startinInterestTimeF = map.get("startinInterestTimeF");
        String startinInterestTimeT = map.get("startinInterestTimeT");
        String plManageMoneyPlanBuyinfoId = map.get("plManageMoneyPlanBuyinfoId");

        if (null != currentMatchingMoney && !currentMatchingMoney.equals("") && currentMatchingMoney.equals("currentMatchingMoney")) {
            hql.append(" and fmpb.earlierOutDate is null and fmpb.isAtuoMatch =1  and fmpb.currentMatchingMoney >0  "); //and fmpb.optimalDayRate >0
        }
        if (null != currentMatchingMoney && !currentMatchingMoney.equals("") && currentMatchingMoney.equals("optimalDayRate")) {
            hql.append(" and fmpb.earlierOutDate is null and fmpb.currentMatchingMoney >0 ");//and fmpb.optimalDayRate >0
        }
        if (null != seachDate && !seachDate.equals("")) {
            hql.append(" and fmpb.startinInterestTime <= '" + seachDate + "'");
            hql.append(" and fmpb.endinInterestTime >= '" + seachDate + "'");
        }
        if (null != investPersonId && !investPersonId.equals("")) {
            hql.append(" and fmpb.investPersonName  like '%" + investPersonId + "%'");
        }
        if (null != keystr && !keystr.equals("")) {
            hql.append(" and fmpb.keystr='" + keystr + "'");
        }
        if (null != startinInterestTimeF && !startinInterestTimeF.equals("")) {
            hql.append(" and fmpb.startinInterestTime >= '" + startinInterestTimeF + " 00:00:00'");
        }
        if (null != startinInterestTimeT && !startinInterestTimeT.equals("")) {
            hql.append(" and fmpb.startinInterestTime <= '" + startinInterestTimeT + " 23:59:59'");
        }
        if (null != mmName && !mmName.equals("")) {
            hql.append(" and fmpb.mmName like '%/" + mmName + "%'  escape '/' ");
        }
        if (null != plManageMoneyPlanBuyinfoId && !"".equals(plManageMoneyPlanBuyinfoId)) {
            PlMmOrderInfo plMmOrderInfo = plMmOrderInfoDao.get(Long.valueOf(plManageMoneyPlanBuyinfoId));
            hql.append(" and fmpb.orderId  =  '" + plMmOrderInfo.getOrderId() + "' ");
        }
        hql.append(" order by fmpb.optimalDayRate desc");
        if (null == pb) {
            return findByHql(hql.toString());
        } else {
            return findByHql(hql.toString(), null, pb);
        }

    }

    /**
     * 投资人投资管理查询方法
     * add by linyan
     * 2014-5-16
     *
     * @param map
     * @return
     */
    @Override
    public List<PlManageMoneyPlanBuyinfo> listbyfirstMatching(Map<String, String> map) {
        StringBuffer hql = new StringBuffer("from PlManageMoneyPlanBuyinfo fmpb where  fmpb.firstProjectIdcount<5"); //fmpb.currentMatchingMoney >0
        String seachDate = map.get("seachDate");
        if (null != seachDate && !seachDate.equals("")) {
            hql.append(" and fmpb.startinInterestTime <= '" + seachDate + "'");
            hql.append(" and fmpb.endinInterestTime >= '" + seachDate + "'");
        }
        hql.append(" order by fmpb.optimalDayRate desc");
        //	hql.append(" order by fmpb.buyMoney desc");
        return findByHql(hql.toString());
    }

    //投资人投资管理查询方法
    @Override
    public List<Investproject> getPersonInvestProject(HttpServletRequest request, Integer start, Integer limit) {
        // TODO Auto-generated method stub

        String sql = " select * from " +
                "(SELECT  " +
                "info.id as infoId," +
                "info.bidId as planId," +
                "plan.proType as proType," +
                "plan.bidProName as bidName," +
                "0 as userType," +
                "info.userId as userId," +
                "info.userName as userName," +
                "FLOOR(info.userMoney) AS userMoney,"+ //去掉查询结果的小数点
                // "info.userMoney as userMoney," +
                " DATE_FORMAT(info.bidtime,'%Y-%m-%d') as bidtime," +
                " DATE_FORMAT(info.bidtime,'%Y-%m-%d') as bidtimeStr," +
                "sl.payintentPeriod as periodTime," +
                "sl.yearAccrualRate as yeaRate," +
                "bm.cardcode as cardcode," +
                "bm.truename AS trueName,"+
                "bm.telphone as telphone," +
                "info.state as state," +
                "CASE info.state " +
                "WHEN '0' THEN '投资失败'" +
                "WHEN  '1' THEN '投资中'" +
                "WHEN '2'  THEN '投资成功'" +
                "WHEN '3' THEN '已流标'" +
                "END " +
                "\n" +
                "as stateShow," +
                "null as isOtherFlow," +
                "'pl_bid_plan' as planTable, " +
                "NULL AS earlierOutDate, " +
                "null as runId ," +
                "null as contractNo, " +
                        /*"null  as startinInterestTime, "+
                        "null  as endinInterestTime, " +*/
                " plan.startIntentDate As startinInterestTime,"
                + "plan.endIntentDate AS endinInterestTime, " +
                "null  as customerManagerNameId, " +
                "NULL AS departId " +
                "FROM " +
                "`pl_bid_info` AS info " +
                "LEFT JOIN pl_bid_plan AS plan ON info.bidId = plan.bidId " +
                "left join bp_business_dir_pro as bdir on bdir.bdirProId =plan.bdirProId and plan.proType='B_Dir' " +
                "left join bp_business_or_pro  as bor on bor.borProId =plan.borProId and plan.proType='B_Or' " +
                "left join bp_persion_dir_pro  as pdir on pdir.pDirProId =plan.pDirProId and plan.proType='P_Dir' " +
                "left join bp_persion_or_pro  as por on por.pOrProId =plan.pOrProId and plan.proType='P_Or' " +
                "LEFT JOIN bp_cust_member as bm  on info.userId = bm.id " +
                "left join bp_fund_project as sl " +
                "on ( sl.flag in ('1','0') AND((sl.projectId=bdir.proId ) " +
                "or (sl.projectId=bor.proId ) " +
                "or(sl.projectId=pdir.proId ) " +
                "or (sl.projectId=por.proId ) " +
                "))" + " where info.state=1 or info.state = 2  "+
                "UNION ALL  " +
                "SELECT  " +
                "buyInfo.orderId as infoId, " +
                "buyInfo.mmplanId as planId, " +
                "buyPlan.keystr as proType, " +
                "buyInfo.mmName as bidName, " +
                "buyInfo.persionType as userType, " +
                "buyInfo.investPersonId as userId, " +
                "buyInfo.investPersonName as userName, " +
                "FLOOR(buyInfo.buyMoney) as userMoney, " +
                "buyInfo.buyDatetime as bidtime, " +
                "buyInfo.buyDatetime as bidtimeStr, " +
                "buyPlan.investlimit as periodTime, " +
                "buyPlan.yeaRate as yeaRate, " +
                "NULL as cardcode," +
                "NULL as trueName, "+
                "NULL as telphone, " +
                "buyInfo.state as state, " +
                "NULL AS stateShow, " +
                "buyInfo.isOtherFlow as isOtherFlow," +
                "'pl_managemoney_plan' as planTable, " +
                "buyInfo.earlierOutDate as earlierOutDate, " +
                "buyInfo.runId as runId  , " +
                "buyInfo.contractNo as contractNo, " +
                "buyInfo.startinInterestTime as startinInterestTime1, " +
                "buyInfo.endinInterestTime as endinInterestTime1, " +
                "pmoi.customerManagerNameId, " +
                "pmoi.departId as departId " +

                "FROM " +
                "pl_managemoneyplan_buyinfo AS buyInfo " +
                "LEFT JOIN pl_managemoney_plan AS buyPlan ON buyInfo.mmplanId = buyPlan.mmplanId " +
                "LEFT JOIN pl_mm_order_info as pmoi on pmoi.orderId = buyInfo.orderId" +
                ") as investBuyInfo where 1=1 ";
        if (request != null) {

            Object ids = request.getSession().getAttribute("userIds");
            Map<String, String> map = GroupUtil.separateFactor(request, ids);
            String userIds = map.get("userId");
            String shopId = map.get("shopId");
            String userType = request.getParameter("userType");
            if (null != userType && !"".equals(userType) && !"null".equals(userType) && !"0".equals(userType)) {
                sql = sql + " and investBuyInfo.runId is not NULL";
            }
            //按上下级分离
            if (null != userIds && !"".equals(userIds)) {
                sql = sql + " and  fn_check_repeat(investBuyInfo.customerManagerNameId,'" + userIds + "') = 1 ";
            }
            //按门店分离数据
            if (null != shopId && !"".equals(shopId)) {
                sql = sql + " and investBuyInfo.departId=" + shopId;
            }

            //String userType =request.getParameter("userType");
            if (null != userType && !"".equals(userType) && !"null".equals(userType)) {
                sql = sql + " and investBuyInfo.userType=" + Short.valueOf(userType);
            }
            String userId = request.getParameter("userId");
            if (null != userId && !"".equals(userId) && !"null".equals(userId)) {
                sql = sql + " and investBuyInfo.userId=" + Long.valueOf(userId);
            }
            String userName = request.getParameter("userName");

            String bidName = request.getParameter("bidName");

            String isExcel = request.getParameter("isExcel");
            if (null != isExcel && !"".equals(isExcel) && !"1".equals(isExcel)) {

                try {
                    userName = java.net.URLDecoder.decode(userName, "UTF-8");
                    bidName = java.net.URLDecoder.decode(bidName, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (null != userName && !"".equals(userName) && !"null".equals(userName)) {
                sql = sql + " and investBuyInfo.userName like '%" + userName + "%'";
            }
            if (null != bidName && !"".equals(bidName) && !"null".equals(bidName)) {
                sql = sql + " and investBuyInfo.bidName like '%" + bidName + "%'";
            }


            String state = request.getParameter("state");
            if (null != state && !"".equals(state) && !"null".equals(state)) {
                sql = sql + " and investBuyInfo.state=" + Short.valueOf(state);
            }


            String yeaRate = request.getParameter("yeaRate");
            if (null != yeaRate && !"".equals(yeaRate) && !"null".equals(yeaRate)) {
                sql = sql + " and investBuyInfo.yeaRate = " + new BigDecimal(yeaRate);
            }

            String userMoney = request.getParameter("userMoney");
            if (null != userMoney && !"".equals(userMoney) && !"null".equals(userMoney)) {
                sql = sql + " and investBuyInfo.userMoney = " + new BigDecimal(userMoney);
            }

            String periodTime = request.getParameter("periodTime");
            if (null != periodTime && !"".equals(periodTime) && !"null".equals(periodTime)) {
                sql = sql + " and investBuyInfo.userMoney = " + Integer.valueOf(userMoney);
            }
            String proType = request.getParameter("proType");
            if (null != proType && !"".equals(proType) && !"null".equals(proType)) {
                sql = sql + " and investBuyInfo.proType='" + proType + "'";
            }

            String contractNo = request.getParameter("contractNo");
            if (null != contractNo && !"".equals(contractNo) && !"null".equals(contractNo)) {
                sql = sql + " and investBuyInfo.contractNo  like '%" + contractNo + "%'";
            }


        }
        sql = sql + " order by investBuyInfo.bidtime desc";
        System.out.println("查询方法的sql是" + sql);
        List<Investproject> list = new ArrayList<Investproject>();
        if (start == null || limit == null) {
            List listcount = this.getSession().createSQLQuery(sql).
                    addScalar("infoId", Hibernate.LONG).
                    addScalar("planId", Hibernate.LONG).
                    addScalar("proType", Hibernate.STRING).
                    addScalar("bidName", Hibernate.STRING).
                    addScalar("userType", Hibernate.SHORT).
                    addScalar("userId", Hibernate.LONG).
                    addScalar("userName", Hibernate.STRING).
                    addScalar("userMoney", Hibernate.BIG_DECIMAL).
                    addScalar("bidtime", Hibernate.TIMESTAMP).
                    addScalar("bidtimeStr", Hibernate.STRING).
                    addScalar("periodTime", Hibernate.INTEGER).
                    addScalar("yeaRate", Hibernate.BIG_DECIMAL).
                    addScalar("cardcode", Hibernate.STRING).
                    addScalar("trueName",Hibernate.STRING).
                    addScalar("telphone", Hibernate.STRING).
                    addScalar("state", Hibernate.SHORT).
                    addScalar("stateShow", Hibernate.STRING).
                    addScalar("planTable", Hibernate.STRING).
                    addScalar("earlierOutDate", Hibernate.DATE).
                    addScalar("isOtherFlow", Hibernate.SHORT).
                    addScalar("runId", Hibernate.LONG).
                    addScalar("contractNo", Hibernate.STRING).
                    addScalar("startinInterestTime", Hibernate.TIMESTAMP).
                    addScalar("endinInterestTime", Hibernate.TIMESTAMP).

                    setResultTransformer(Transformers.aliasToBean(Investproject.class)).list();
            list = listcount;
        } else {
            list = this.getSession().createSQLQuery(sql).
                    addScalar("infoId", Hibernate.LONG).
                    addScalar("planId", Hibernate.LONG).
                    addScalar("proType", Hibernate.STRING).
                    addScalar("bidName", Hibernate.STRING).
                    addScalar("userType", Hibernate.SHORT).
                    addScalar("userId", Hibernate.LONG).
                    addScalar("userName", Hibernate.STRING).
                    addScalar("userMoney", Hibernate.BIG_DECIMAL).
                    addScalar("bidtime", Hibernate.TIMESTAMP).
                    addScalar("bidtimeStr", Hibernate.STRING).
                    addScalar("periodTime", Hibernate.INTEGER).
                    addScalar("yeaRate", Hibernate.BIG_DECIMAL).
                    addScalar("cardcode", Hibernate.STRING).
                    addScalar("trueName",Hibernate.STRING).
                    addScalar("telphone", Hibernate.STRING).
                    addScalar("state", Hibernate.SHORT).
                    addScalar("stateShow", Hibernate.STRING).
                    addScalar("planTable", Hibernate.STRING).
                    addScalar("earlierOutDate", Hibernate.DATE).
                    addScalar("isOtherFlow", Hibernate.SHORT).
                    addScalar("runId", Hibernate.LONG).
                    addScalar("contractNo", Hibernate.STRING).
                    addScalar("startinInterestTime", Hibernate.TIMESTAMP).
                    addScalar("endinInterestTime", Hibernate.TIMESTAMP).
                    setResultTransformer(Transformers.aliasToBean(Investproject.class)).
                    setFirstResult(start).setMaxResults(limit).
                    list();

        }
        return list;
    }

    @Override
    public List<PlManageMoneyPlanBuyinfo> getListByPlanId(Long mmplanId) {
        String hql = "from PlManageMoneyPlanBuyinfo as pbi where pbi.plManageMoneyPlan.mmplanId=?";
        return super.findByHql(hql, new Object[]{mmplanId});
    }


    @Override
    public List<PlManageMoneyPlanBuyinfo> getListToEnd(Date endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        String str = simpleDateFormat.format(endDate);
        String hql = "from PlManageMoneyPlanBuyinfo as pbi where pbi.endinInterestTime='" + str + "' and pbi.state=2";
        return super.findByHql(hql, new Object[]{});
    }

    @Override
    public List<PlManageMoneyPlanBuyinfo> getListByPlanId(Long mmplanId,
                                                          Short status) {
        String hql = "from PlManageMoneyPlanBuyinfo as pbi where pbi.plManageMoneyPlan.mmplanId=? and pbi.state=?";
        return this.getSession().createQuery(hql).setParameter(0, mmplanId).setParameter(1, status).list();
    }

    @Override
    public List<PlManageMoneyPlanBuyinfo> listByMmplanId(PagingBean pb,
                                                         Map<String, String> map) {
        StringBuffer sql = new StringBuffer("SELECT" +
                " plmb.orderId," +
                "plmb.investPersonName," +
                "plmb.investPersonId," +
                "plmb.couponsMoney," +
                "plmb.investmentProportion," +
                "plmb.buyDatetime," +
                "plma.incomeMoney," +
                "plma.intentDate," +
                "plma.factDate" +
                " FROM pl_managemoneyplan_buyinfo AS plmb" +
                " LEFT JOIN pl_mm_order_assigninterest AS plma ON plma.orderId=plmb.orderId where");

        String mmplanId = map.get("mmplanId");
        if (null != mmplanId && !"".equals(mmplanId) && !mmplanId.equals("undefined")) {
            sql.append(" plmb.mmplanId=" + mmplanId);
        } else {
            return null;
        }
        String state = map.get("state");
        if (null != state && !"".equals(state) && !state.equals("undefined")) {
            sql.append(" AND plmb.state in(" + state + ")");
        } else {
            return null;
        }
        sql.append(" AND plmb.keystr='experience'");
        if (null == pb) {
            return this.getSession().createSQLQuery(sql.toString())
                    .addScalar("orderId", Hibernate.LONG)
                    .addScalar("investPersonName", Hibernate.STRING)
                    .addScalar("investPersonId", Hibernate.LONG)
                    .addScalar("couponsMoney", Hibernate.BIG_DECIMAL)
                    .addScalar("investmentProportion", Hibernate.BIG_DECIMAL)
                    .addScalar("buyDatetime", Hibernate.DATE)
                    .addScalar("incomeMoney", Hibernate.BIG_DECIMAL)
                    .addScalar("intentDate", Hibernate.DATE)
                    .addScalar("factDate", Hibernate.DATE)
                    .setResultTransformer(Transformers.aliasToBean(PlManageMoneyPlanBuyinfo.class)).list();
        } else {
            return this.getSession().createSQLQuery(sql.toString())
                    .addScalar("orderId", Hibernate.LONG)
                    .addScalar("investPersonName", Hibernate.STRING)
                    .addScalar("investPersonId", Hibernate.LONG)
                    .addScalar("couponsMoney", Hibernate.BIG_DECIMAL)
                    .addScalar("investmentProportion", Hibernate.BIG_DECIMAL)
                    .addScalar("buyDatetime", Hibernate.DATE)
                    .addScalar("incomeMoney", Hibernate.BIG_DECIMAL)
                    .addScalar("intentDate", Hibernate.DATE)
                    .addScalar("factDate", Hibernate.DATE)
                    .setResultTransformer(Transformers.aliasToBean(PlManageMoneyPlanBuyinfo.class))
                    .setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
        }

    }

    @Override
    public List<PlManageMoneyPlanBuyinfo> listbyState(PagingBean pb, Map<String, String> map) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        StringBuffer hql = new StringBuffer("from PlManageMoneyPlanBuyinfo fmpb where 1=1");
        String keystr = map.get("keystr");
        String state = map.get("state");

        if (null != keystr && !"".equals(keystr)) {
            hql.append(" and fmpb.keystr='" + keystr + "'");
        } else {
            return null;
        }

        if (null != state && !"".equals(state)) {
            hql.append(" and fmpb.state in (" + state + ")");
        } else if (null == state) {//默认查询出购买中的
            hql.append(" and fmpb.state in (1)");
        } else {//任何情况下，都不查询等待支付的
            hql.append(" and fmpb.state !=0");
        }
        String mmNumber = map.get("mmNumber");
        String mmName = map.get("mmName");
        String buyDatetime_GE = map.get("buyDatetime_GE");
        String buyDatetime_LE = map.get("buyDatetime_LE");

        if (null != mmNumber && !"".equals(mmNumber)) {
            hql.append(" and fmpb.plManageMoneyPlan.mmNumber  like '%" + mmNumber + "%'");
        }

        if (null != mmName && !"".equals(mmName)) {
            hql.append(" and fmpb.plManageMoneyPlan.mmName  like '%" + mmName + "%'");
        }

        if (null != buyDatetime_GE && !"".equals(buyDatetime_GE)) {
            hql.append(" and fmpb.buyDatetime >= '" + buyDatetime_GE + " 00:00:00'");
        } else if (null == buyDatetime_GE) {//默认查询当天的
            hql.append(" and fmpb.buyDatetime >= '" + df.format(new Date()) + " 00:00:00'");
        }

        if (null != buyDatetime_LE && !"".equals(buyDatetime_LE)) {
            hql.append(" and fmpb.buyDatetime <= '" + buyDatetime_LE + " 23:59:59'");
        }
        hql.append(" ORDER BY fmpb.buyDatetime DESC");
        if (null == pb) {
            return findByHql(hql.toString());
        } else {
            return findByHql(hql.toString(), null, pb);
        }

    }

    @Override
    public List<PlManageMoneyPlanBuyinfo> getByDate(Map<String, String> map) {
        StringBuffer sb = new StringBuffer("select * from pl_managemoneyplan_buyinfo as pbi where pbi.mmplanId=? and pbi.state=?");
        String date = map.get("date");
        if (date != null && !"".equals(date)) {
            sb.append(" and pbi.buyDatetime > '" + date + " 00:00:00' and pbi.buyDatetime < '" + date + " 23:59:59'");
        }
        String isCreateReward = map.get("isCreateReward");
        if (null != isCreateReward && !"".equals(isCreateReward)) {
            sb.append(" and pbi.isCreateReward =" + isCreateReward);
        }
        return this.getSession().createSQLQuery(sb.toString()).addEntity(PlManageMoneyPlanBuyinfo.class)
                .setParameter(0, Long.valueOf(map.get("mmplanId")))
                .setParameter(1, Short.valueOf(map.get("state")))
                .list();
    }

    @Override
    public PlManageMoneyPlanBuyinfo getByDealInfoNumber(String dealInfoNumber) {
        String hql = "from PlManageMoneyPlanBuyinfo as pbi where pbi.dealInfoNumber=";
        Object[] params = {dealInfoNumber};
        return (PlManageMoneyPlanBuyinfo) findUnique(hql, params);
    }

    @Override
    public List<PlManageMoneyPlanBuyinfo> getPreAuthorizationNum(
            String preAuthorizationNum) {
        String hql = "from PlManageMoneyPlanBuyinfo as pbi where pbi.preAuthorizationNum in (" + preAuthorizationNum + ")";
        return super.findByHql(hql, new Object[]{});
    }


}