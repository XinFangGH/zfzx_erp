package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.common.EnterpriseShareequityDao;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseView;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.VEnterprisePerson;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;


@SuppressWarnings("unchecked")
public class EnterpriseDaoImpl extends BaseDaoImpl<Enterprise> implements EnterpriseDao {
    @Resource
    private CreditBaseDao creditBaseDao;
    @Resource
    private PersonDao personDao;
    @Resource
    private EnterpriseShareequityDao enterpriseShareequityDao;

    public EnterpriseDaoImpl() {
        super(Enterprise.class);
    }

    @Override
    public void ajaxQueryEnterprise(String searchCompanyId, String[] userIds, String enterprisename, String ownership, String shortname, String organizecode, String cciaa, int start, int limit, String sort, String dir, String customerLevel) {
        Boolean flag = Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
        String roleType = ContextUtil.getRoleTypeSession();
        String userIdsStr = "";
        if (userIds != null) {
            StringBuffer sb1 = new StringBuffer();
            for (String userid : userIds) {
                sb1.append(userid);
                sb1.append(",");
            }
            userIdsStr = sb1.toString().substring(0, sb1.length() - 1);
        }
        if (flag && roleType.equals("control")) { //如果是集团版本  并且当前roleType为管控角色
            userIdsStr = "";
        }
        String hql = "select count(*) from EnterpriseView ep where 1=1";
        if (null != searchCompanyId && !"".equals(searchCompanyId)) {
            hql += " and ep.companyId in ('" + searchCompanyId + "')"; //
        }
        if (null != searchCompanyId) {
            searchCompanyId = searchCompanyId;
        }
        hql += " and (ep.isBlack=false or ep.isBlack is null) and (ep.enterprisename like ? or (ep.enterprisename is null and ''='" + enterprisename + "' )) and (ep.ownership like ? or (ep.ownership is null and ''='" + ownership + "')) and (ep.shortname like ? or(ep.shortname is null and ''='" + shortname + "' )) and (ep.organizecode like ? or(ep.organizecode is null and ''='" + organizecode + "' )) and (ep.cciaa like ? or(ep.cciaa is null and ''='" + cciaa + "' )) and (ep.customerLevel like ? or (ep.customerLevel is null and ''='" + customerLevel + "')) order by ep.id desc";
        Object params[] = new Object[]{enterprisename + "%", ownership + "%", shortname + "%", organizecode + "%", cciaa + "%", customerLevel + "%"};
        List totalList = new ArrayList();
        List list = new ArrayList();
        ;
        int totalProperty = 0;
        try {
            totalList = creditBaseDao.queryHql(hql, params);
            totalProperty = ((Long) totalList.get(0)).intValue();//记录总数
            //查询符合条件的部分记录数，数据库分页
            String hql2 = "from EnterpriseView ep where 1=1";
            if (null != searchCompanyId && !"".equals(searchCompanyId)) {
                hql2 += " and ep.companyId in ('" + searchCompanyId + "')"; //
            }
            if (sort != null && dir != null) {
                if (userIdsStr != "") {
                    hql2 += "  and (ep.isBlack=false or ep.isBlack is null) and (ep.enterprisename like ? or (ep.enterprisename is null and ''='" + enterprisename + "' )) and (ep.ownership like ? or (ep.ownership is null and ''='" + ownership + "')) and (ep.shortname like ? or(ep.shortname is null and ''='" + shortname + "' )) and (ep.organizecode like ? or(ep.organizecode is null and ''='" + organizecode + "' )) and (ep.cciaa like ? or(ep.cciaa is null and ''='" + cciaa + "' ))  and (ep.customerLevel like ? or (ep.customerLevel is null and ''='" + customerLevel + "')) and fn_check_repeat(ep.belongedId,'" + userIdsStr + "') = 1 order by  CONVERT(ep." + sort + " , 'GBK') " + dir;
                } else {
                    hql2 += "  and (ep.isBlack=false or ep.isBlack is null) and (ep.enterprisename like ? or (ep.enterprisename is null and ''='" + enterprisename + "' )) and (ep.ownership like ? or (ep.ownership is null and ''='" + ownership + "')) and (ep.shortname like ? or(ep.shortname is null and ''='" + shortname + "' )) and (ep.organizecode like ? or(ep.organizecode is null and ''='" + organizecode + "' )) and (ep.cciaa like ? or(ep.cciaa is null and ''='" + cciaa + "' ))  and (ep.customerLevel like ? or (ep.customerLevel is null and ''='" + customerLevel + "')) order by  CONVERT(ep." + sort + " , 'GBK') " + dir;
                }
            } else {
                if (userIdsStr != "") {

                    hql2 += "  and (ep.isBlack=false or ep.isBlack is null) and (ep.enterprisename like ? or (ep.enterprisename is null and ''='" + enterprisename + "' )) and (ep.ownership like ? or (ep.ownership is null and ''='" + ownership + "')) and (ep.shortname like ? or(ep.shortname is null and ''='" + shortname + "' )) and (ep.organizecode like ? or(ep.organizecode is null and ''='" + organizecode + "' )) and (ep.cciaa like ? or(ep.cciaa is null and ''='" + cciaa + "' ))  and (ep.customerLevel like ? or (ep.customerLevel is null and ''='" + customerLevel + "')) and  fn_check_repeat(ep.belongedId,'" + userIdsStr + "') = 1 order by ep.id desc";
                } else {
                    hql2 += "  and  (ep.isBlack=false or ep.isBlack is null) and (ep.enterprisename like ? or (ep.enterprisename is null and ''='" + enterprisename + "' )) and (ep.ownership like ? or (ep.ownership is null and ''='" + ownership + "')) and (ep.shortname like ? or(ep.shortname is null and ''='" + shortname + "' )) and (ep.organizecode like ? or(ep.organizecode is null and ''='" + organizecode + "' )) and (ep.cciaa like ? or(ep.cciaa is null and ''='" + cciaa + "' ))  and (ep.customerLevel like ? or (ep.customerLevel is null and ''='" + customerLevel + "')) order by ep.id desc";
                }

            }
            list = creditBaseDao.queryHql(hql2, params, start, limit);
            JsonUtil.jsonFromList(list, totalProperty);

        } catch (Exception e) {
            e.printStackTrace();
            JsonUtil.jsonFromObject("数据库查询出错，请重试", false);
        }
    }

    public Enterprise queryEnterpriseName(String name) throws Exception {
        String hql = "from Enterprise AS e where e.enterprisename=? ";
        List list = creditBaseDao.queryHql(hql, name);
        if (list == null) {
            return null;
        } else
            return (Enterprise) list.get(0);
    }

    ;

    public Enterprise isEmpty(String organizecode) throws Exception {
        String hql = "from Enterprise AS e where e.organizecode=? ";
        List list = creditBaseDao.queryHql(hql, organizecode);
        if (null == list) {
            return null;
        } else
            return (Enterprise) list.get(0);
    }

    @Override
    public Enterprise getById(int id) {
        String hql = "from Enterprise as e where e.id=?";
        return (Enterprise) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
    }


    @Override
    public List<Enterprise> getListByLegalPersonId(int legalpersonid) {
        String hql = "from Enterprise as e where e.legalpersonid=?";
        return getSession().createQuery(hql).setParameter(0, legalpersonid).list();
    }

    @Override
    public void getList(String customerType,
                        String customerName, String cardNum, String companyId, int start, int limit) {

        String hql = "from VEnterprisePerson v where v.name like ? and v.code like ?";
        if (null != companyId && !"".equals(companyId)) {
            hql = hql + " and v.companyId in (" + companyId + ") ";
        }
        String hql1 = "select count(*) from VEnterprisePerson v where v.name like ? and v.code like ?";
        if (null != companyId && !"".equals(companyId)) {
            hql1 = hql1 + " and v.companyId in (" + companyId + ") ";
        }
        if (null != customerType && !"".equals(customerType)) {
            hql = hql + " and v.type=('" + customerType + "')";
            hql1 = hql1 + " and v.type=('" + customerType + "')";
        }
        try {
            System.out.println(hql);
            //List<VEnterprisePerson> list=creditBaseDao.queryHql("from VEnterprisePerson v where v.name like '%"+customerName+"%' and v.code like '%"+customerName+"%' and v.companyId in ('"+companyId+"') " );
            List<VEnterprisePerson> list = creditBaseDao.queryHql(hql, new Object[]{'%' + customerName + '%', '%' + cardNum + '%'}, start, limit);
            List list1 = creditBaseDao.queryHql(hql1, new Object[]{'%' + customerName + '%', '%' + cardNum + '%'});
            int totalCount = 0;
            if (null != list1 && list1.size() > 0) {
                totalCount = ((Long) list1.get(0)).intValue();
            }
            JsonUtil.jsonFromList(list, totalCount);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public List<Enterprise> ajaxQueryEnterpriseForCombo(String query, int start, int limit) {
        if (query == null) {
            query = "";
        } else {
            query.replaceAll(" ", "");
        }
        String hql = "from Enterprise as e where e.enterprisename like '%" + query + "%'";
        return this.findByHql(hql, new Object[]{}, start, limit);
    }

    @Override
    public EnterpriseView getByViewId(Integer id) {
        String hql = "from EnterpriseView as e where e.id=?";
        return (EnterpriseView) this.getSession().createQuery(hql).setParameter(0, id).uniqueResult();
    }

    @Override
    public void getList(String enterIds, PagingBean pb, String type) {
        String hql = "";// "from Enterprise e where e.id in ("+enterIds+")";
        String hql1 = "";
        if (type.equals("1")) {//获取关联企业
            hql += "from EnterpriseView e where e.id in (" + enterIds + ")";
            hql1 += "select count(*) from EnterpriseView e where e.id in (" + enterIds + ")";
        } else if (type.equals("0")) {//获取不关联企业
            hql += "from EnterpriseView e where e.id not in (" + enterIds + ")";
            hql1 += "select count(*) from EnterpriseView e where e.id not in (" + enterIds + ")";
            ;
        }
        List list = new ArrayList();
        int totalProperty = 0;
        List totalList = new ArrayList();
        try {
            list = creditBaseDao.queryHql(hql, new Object[]{}, pb.getStart(), pb.getPageSize());
            totalList = creditBaseDao.queryHql(hql1, new Object[]{});
            totalProperty = ((Long) totalList.get(0)).intValue();//记录总数
            JsonUtil.jsonFromList(list, totalProperty);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ajaxQueryEnterprise(String searchCompanyId, String[] userIds, String enterprisename,
                                    String ownership, String shortname, String organizecode, String cciaa, int start, int limit,
                                    String sort, String dir, String customerLevel, String orgUserId) {
        Boolean flag = Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
        String roleType = ContextUtil.getRoleTypeSession();
        String userIdsStr = "";
        if (userIds != null) {
            StringBuffer sb1 = new StringBuffer();
            for (String userid : userIds) {
                sb1.append(userid);
                sb1.append(",");
            }
            userIdsStr = sb1.toString().substring(0, sb1.length() - 1);
        }
        if (flag && roleType.equals("control")) { //如果是集团版本  并且当前roleType为管控角色
            userIdsStr = "";
        }
        String hql = "select count(*) from EnterpriseView ep where 1=1";
        if (null != searchCompanyId && !"".equals(searchCompanyId)) {
            hql += " and ep.companyId in ('" + searchCompanyId + "')"; //
        }
        if (null != searchCompanyId) {
            searchCompanyId = searchCompanyId;
        }
        if (orgUserId != null && !orgUserId.equals("")) {
            hql += " and ep.orgUserId =" + Long.valueOf(orgUserId);
        }
        hql += " and (ep.isBlack=false or ep.isBlack is null) and (ep.enterprisename like ? or (ep.enterprisename is null and ''='" + enterprisename + "' )) and (ep.ownership like ? or (ep.ownership is null and ''='" + ownership + "')) and (ep.shortname like ? or(ep.shortname is null and ''='" + shortname + "' )) and (ep.organizecode like ? or(ep.organizecode is null and ''='" + organizecode + "' )) and (ep.cciaa like ? or(ep.cciaa is null and ''='" + cciaa + "' )) and (ep.customerLevel like ? or (ep.customerLevel is null and ''='" + customerLevel + "')) order by ep.id desc";
        Object params[] = new Object[]{enterprisename + "%", ownership + "%", shortname + "%", organizecode + "%", cciaa + "%", customerLevel + "%"};
        List totalList = new ArrayList();
        List list = new ArrayList();
        ;
        int totalProperty = 0;
        try {
            totalList = creditBaseDao.queryHql(hql, params);
            totalProperty = ((Long) totalList.get(0)).intValue();//记录总数
            //查询符合条件的部分记录数，数据库分页
            String hql2 = "from EnterpriseView ep where 1=1";
            if (null != searchCompanyId && !"".equals(searchCompanyId)) {
                hql2 += " and ep.companyId in ('" + searchCompanyId + "')"; //
            }
            if (orgUserId != null && !orgUserId.equals("")) {
                hql2 += " and ep.orgUserId =" + Long.valueOf(orgUserId);
            }
            if (sort != null && dir != null) {
                if (userIdsStr != "") {
                    hql2 += "  and (ep.isBlack=false or ep.isBlack is null) and (ep.enterprisename like ? or (ep.enterprisename is null and ''='" + enterprisename + "' )) and (ep.ownership like ? or (ep.ownership is null and ''='" + ownership + "')) and (ep.shortname like ? or(ep.shortname is null and ''='" + shortname + "' )) and (ep.organizecode like ? or(ep.organizecode is null and ''='" + organizecode + "' )) and (ep.cciaa like ? or(ep.cciaa is null and ''='" + cciaa + "' ))  and (ep.customerLevel like ? or (ep.customerLevel is null and ''='" + customerLevel + "')) and fn_check_repeat(ep.belongedId,'" + userIdsStr + "') = 1 order by  CONVERT(ep." + sort + " , 'GBK') " + dir;
                } else {
                    hql2 += "  and (ep.isBlack=false or ep.isBlack is null) and (ep.enterprisename like ? or (ep.enterprisename is null and ''='" + enterprisename + "' )) and (ep.ownership like ? or (ep.ownership is null and ''='" + ownership + "')) and (ep.shortname like ? or(ep.shortname is null and ''='" + shortname + "' )) and (ep.organizecode like ? or(ep.organizecode is null and ''='" + organizecode + "' )) and (ep.cciaa like ? or(ep.cciaa is null and ''='" + cciaa + "' ))  and (ep.customerLevel like ? or (ep.customerLevel is null and ''='" + customerLevel + "')) order by  CONVERT(ep." + sort + " , 'GBK') " + dir;
                }
            } else {
                if (userIdsStr != "") {

                    hql2 += "  and (ep.isBlack=false or ep.isBlack is null) and (ep.enterprisename like ? or (ep.enterprisename is null and ''='" + enterprisename + "' )) and (ep.ownership like ? or (ep.ownership is null and ''='" + ownership + "')) and (ep.shortname like ? or(ep.shortname is null and ''='" + shortname + "' )) and (ep.organizecode like ? or(ep.organizecode is null and ''='" + organizecode + "' )) and (ep.cciaa like ? or(ep.cciaa is null and ''='" + cciaa + "' ))  and (ep.customerLevel like ? or (ep.customerLevel is null and ''='" + customerLevel + "')) and  fn_check_repeat(ep.belongedId,'" + userIdsStr + "') = 1 order by ep.id desc";
                } else {
                    hql2 += "  and  (ep.isBlack=false or ep.isBlack is null) and (ep.enterprisename like ? or (ep.enterprisename is null and ''='" + enterprisename + "' )) and (ep.ownership like ? or (ep.ownership is null and ''='" + ownership + "')) and (ep.shortname like ? or(ep.shortname is null and ''='" + shortname + "' )) and (ep.organizecode like ? or(ep.organizecode is null and ''='" + organizecode + "' )) and (ep.cciaa like ? or(ep.cciaa is null and ''='" + cciaa + "' ))  and (ep.customerLevel like ? or (ep.customerLevel is null and ''='" + customerLevel + "')) order by ep.id desc";
                }

            }
            list = creditBaseDao.queryHql(hql2, params, start, limit);
            JsonUtil.jsonFromList(list, totalProperty);

        } catch (Exception e) {
            e.printStackTrace();
            JsonUtil.jsonFromObject("数据库查询出错，请重试", false);
        }
    }

    @Override
    public Enterprise saveSingleEnterprise(Enterprise enterprise, Person person, String gudongInfo) {
        try {
            Long companyId = ContextUtil.getLoginCompanyId();
            String currentUserId = ContextUtil.getCurrentUserId().toString();
            if (enterprise.getId() != null && !"".equals(enterprise.getId()) && enterprise.getId() != 0) {
                Enterprise persistentEnterprise = this.getById(enterprise.getId());
                BeanUtil.copyNotNullProperties(persistentEnterprise, enterprise);
                this.merge(persistentEnterprise);
                if (person.getId() != null && !"".equals(person.getId())) {
                    person.setCompanyId(companyId);
                    person.setCreater(ContextUtil.getCurrentUser().getFullname());
                    person.setBelongedId(currentUserId);
                    person.setCreaterId(Long.valueOf(currentUserId));
                    person.setCreatedate(new Date());
                    personDao.merge(person);
                    persistentEnterprise.setLegalpersonid(person.getId());
                    this.merge(persistentEnterprise);
                    logger.info("保存企业客户法人成功，个人客户Id：" + person.getId());
                } else {
                    Person p = personDao.getById(person.getId());
                    if (p != null) {
                        BeanUtil.copyNotNullProperties(p, person);
                        personDao.merge(p);
                    }
                }
                if (gudongInfo != null && !"".equals(gudongInfo)) {
                    String[] shareequityArr = gudongInfo.split("@");
                    for (int i = 0; i < shareequityArr.length; i++) {
                        String str = shareequityArr[i];
                        JSONParser parser = new JSONParser(new StringReader(str));
                        EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper.toJava(parser.nextValue(), EnterpriseShareequity.class);
                        if (enterpriseShareequity.getId() == null || "".equals(enterpriseShareequity.getId())) {
                            enterpriseShareequity.setEnterpriseid(persistentEnterprise.getId());
                            enterpriseShareequityDao.save(enterpriseShareequity);
                        } else {
                            EnterpriseShareequity ps = this.enterpriseShareequityDao.load(enterpriseShareequity.getId());
                            BeanUtil.copyNotNullProperties(ps, enterpriseShareequity);
                            enterpriseShareequityDao.save(ps);
                        }
                    }
                }

                return persistentEnterprise;
            } else {
                enterprise.setCompanyId(ContextUtil.getLoginCompanyId());
                enterprise.setId(null);
                if (person.getId() == null || person.getId() == 0) {//如果企业客户是新增，法人信息也是新增，添加保存法人信息代码 （add by ly   2013-6-18）
                    person.setId(null);
                    person.setCompanyId(ContextUtil.getLoginCompanyId());
                    person.setCreater(ContextUtil.getCurrentUser().getFullname());
                    person.setBelongedId(currentUserId);
                    person.setCreaterId(Long.valueOf(currentUserId));
                    person.setCreatedate(new Date());
                    personDao.save(person);
                }
                enterprise.setLegalpersonid(person.getId());
                enterprise.setCreater(ContextUtil.getCurrentUser().getFullname());
                enterprise.setBelongedId(currentUserId);
                enterprise.setCreaterId(Long.valueOf(currentUserId));
                enterprise.setCreatedate(new Date());
                this.save(enterprise);
                if (gudongInfo != null && !"".equals(gudongInfo)) {
                    String[] shareequityArr = gudongInfo.split("@");
                    for (int i = 0; i < shareequityArr.length; i++) {
                        String str = shareequityArr[i];
                        JSONParser parser = new JSONParser(new StringReader(str));
                        EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper.toJava(parser.nextValue(), EnterpriseShareequity.class);
                        if (enterpriseShareequity.getId() == null || "".equals(enterpriseShareequity.getId())) {
                            enterpriseShareequity.setEnterpriseid(enterprise.getId());
                            enterpriseShareequityDao.save(enterpriseShareequity);
                        } else {
                            EnterpriseShareequity ps = this.enterpriseShareequityDao.load(enterpriseShareequity.getId());
                            BeanUtil.copyNotNullProperties(ps, enterpriseShareequity);
                            enterpriseShareequityDao.save(ps);
                        }
                    }
                }
                return enterprise;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long entCount(HttpServletRequest request, String userIds) {
        StringBuffer hql = new StringBuffer("SELECT "
                + "count(e.id)"
                + "FROM cs_enterprise as e "
                + "where (e.isBlack=false or e.isBlack is null) ");
        String companyId = request.getParameter("companyId");
        String str = ContextUtil.getBranchIdsStr();
        if (null != companyId && !companyId.equals("")) {
            str = companyId;
        }
        if (null != str && !str.equals("")) {
            hql.append(" and e.companyId in (" + str + ")");
        }
        if (null != userIds && !userIds.equals("")) {
            hql.append(" and fn_check_repeat(e.belongedId,'" + userIds + "') = 1 ");
        }
        String enterprisename = request.getParameter("enterprisename");
        if (null != enterprisename && !enterprisename.equals("")) {
            hql.append(" and e.enterprisename like '%" + enterprisename + "%'");
        }
        String organizecode = request.getParameter("organizecode");
        if (null != organizecode && !organizecode.equals("")) {
            hql.append(" and e.organizecode like '%" + organizecode + "%'");
        }
        String cciaa = request.getParameter("cciaa");
        if (null != cciaa && !cciaa.equals("")) {
            hql.append(" and e.cciaa like '%" + cciaa + "%'");
        }
        String shopId = request.getParameter("shopId");
        if (null != shopId && !shopId.equals("")) {
            hql.append(" and e.shopId=" + Long.valueOf(shopId));
        }
        List list = this.getSession().createSQLQuery(hql.toString()).list();
        Long count = 0l;
        if (null != list && list.size() > 0) {
            if (null != list.get(0)) {
                BigInteger c = (BigInteger) list.get(0);
                count = c.longValue();
            }
        }
        return count;
    }

    @Override
    public void entList(PageBean<Enterprise> pageBean, Map<String, String> map) {

        /*--------查询总条数---------*/
        StringBuffer totalCounts = new StringBuffer("select count(*) from (");
        StringBuffer hql = new StringBuffer("SELECT "
                + "e.id,"
                + "e.enterprisename,"
                + "e.shortname,"
                + "e.organizecode,"
                + "e.cciaa,"
                + "e.taxnum,"
                + "e.registermoney,"
                + "e.telephone,"
                + "e.opendate,"
                + "e.shopId,"
                + "e.linkman,"
                + "e.shopName,"
                + "e.area,"
                + "e.postcoding,"
                + "e.legalpersonid,"
                + "e.companyId,"
                + "e.hangyetype as hangyeType,"
                + "e.documentType,"
                + "a.title as hangyeName,"
                + "p.`name` as legalpersonName,"
                + "p.`cellphone` as cellphone,"
                + "p.`selfemail` as selfemail,"
                + "p.`cardnumber` as cardnumber,"
                + "o.org_name as orgName,"
                + "bp.isCheckCard as isCheckCard, "
                +"\t1000000- IFNULL(\n" +
                "\t(\n" +
                "\tSELECT\n" +
                "\t\tSUM( intent.`notMoney` ) \n" +
                "\tFROM\n" +
                "\t\tbp_fund_intent intent \n" +
                "\tWHERE\n" +
                "\t\tintent.`fundType` = 'principalRepayment' \n" +
                "\t\tAND intent.`bidPlanId` IN ( SELECT p.bidId FROM pl_bid_plan p WHERE p.`receiverP2PAccountNumber` = bp.`loginname` AND p.state != 6 ) \n" +
                "\t),\n" +
                "\t0 \n" +
                "\t) - IFNULL(\n" +
                "\t(\n" +
                "\tSELECT\n" +
                "\t\tSUM( `bidMoney` ) \n" +
                "\tFROM\n" +
                "\t\tpl_bid_plan plan \n" +
                "\tWHERE\n" +
                "\t\tplan.`receiverP2PAccountNumber` = bp.`loginname` \n" +
                "\t\tAND plan.`state` IN ( 0, 1, 2, 6 ) \n" +
                "\t),\n" +
                "\t0 \n" +
                "\t) AS surplusMoney  "
                + "FROM cs_enterprise as e "
                + "left join cs_person as p on p.id=e.legalpersonid "
                + "left join cs_dic_area_dynam as a on a.id=e.hangyetype "
                + "LEFT JOIN bp_cust_relation AS br ON (e.id = br.offlineCusId AND br.offlineCustType='b_loan') "
                + "LEFT JOIN bp_cust_member AS bp ON ( bp.id = br.p2pCustId AND bp.`cardcode` = e.`organizecode` ) "
                + "left join organization as o on o.org_id=e.companyId where (e.isBlack=false or e.isBlack is null) ");
        String companyId = map.get("companyId");
        String userIds = map.get("userId");
		/*if(null!=userIds && !"".equals(userIds)){
			hql.append(" and fn_check_repeat(e.belongedId,'"+userIds+"') = 1");
		}*/
        if (null != companyId && !"".equals(companyId)) {
            hql.append(" and e.companyId in (" + companyId + ")");
        }
        if (pageBean != null) {
            HttpServletRequest request = pageBean.getRequest();
            if (request != null) {
                String enterprisename = request.getParameter("enterprisename");
                if (null != enterprisename && !enterprisename.equals("")) {
                    hql.append(" and e.enterprisename like '%" + enterprisename + "%'");
                }
                String organizecode = request.getParameter("organizecode");
                if (null != organizecode && !organizecode.equals("")) {
                    hql.append(" and e.organizecode like '%" + organizecode + "%'");
                }
                String cciaa = request.getParameter("cciaa");
                if (null != cciaa && !cciaa.equals("")) {
                    hql.append(" and e.cciaa like '%" + cciaa + "%'");
                }
                String shopId = request.getParameter("shopId");
                if (null != shopId && !shopId.equals("")) {
                    hql.append(" and e.shopId=" + Long.valueOf(shopId));
                }
            }
        }
        hql.append(" order by e.id desc");
        System.out.println("查询的sql是" + hql.toString());
        System.out.println("---" + hql.toString());
        totalCounts.append(hql).append(") as b");
        SQLQuery query = this.getSession().createSQLQuery(hql.toString())
                .addScalar("id", Hibernate.INTEGER)
                .addScalar("enterprisename", Hibernate.STRING)
                .addScalar("shortname", Hibernate.STRING)
                .addScalar("organizecode", Hibernate.STRING)
                .addScalar("cciaa", Hibernate.STRING)
                .addScalar("taxnum", Hibernate.STRING)
                .addScalar("registermoney", Hibernate.DOUBLE)
                .addScalar("telephone", Hibernate.STRING)
                .addScalar("opendate", Hibernate.DATE)
                .addScalar("shopId", Hibernate.LONG)
                .addScalar("shopName", Hibernate.STRING)
                .addScalar("area", Hibernate.STRING)
                .addScalar("postcoding", Hibernate.STRING)
                .addScalar("legalpersonid", Hibernate.INTEGER)
                .addScalar("companyId", Hibernate.LONG)
                .addScalar("hangyeType", Hibernate.INTEGER)
                .addScalar("documentType", Hibernate.LONG)
                .addScalar("hangyeName", Hibernate.STRING)
                .addScalar("legalpersonName", Hibernate.STRING)
                .addScalar("orgName", Hibernate.STRING)
                .addScalar("cellphone", Hibernate.STRING)
                .addScalar("selfemail", Hibernate.STRING)
                .addScalar("isCheckCard", Hibernate.STRING)
                .addScalar("linkman", Hibernate.STRING)
                .addScalar("cardnumber", Hibernate.STRING)
                .addScalar("surplusMoney", Hibernate.BIG_DECIMAL);
        if (null != pageBean) {
            if (null != pageBean.getStart() && null != pageBean.getLimit()) {
                query.setResultTransformer(Transformers.aliasToBean(Enterprise.class)).setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getLimit());
            } else {
                query.setResultTransformer(Transformers.aliasToBean(Enterprise.class));
            }
        } else {
            query.setResultTransformer(Transformers.aliasToBean(Enterprise.class));
        }
        if (null != pageBean) {
            pageBean.setResult(query.list());
            BigInteger total = (BigInteger) this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(totalCounts.toString()).uniqueResult();
            pageBean.setTotalCounts(total.intValue());
        } else {
            BigInteger total = (BigInteger) this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(totalCounts.toString()).uniqueResult();
        }
    }

    @Override
    public List<VEnterprisePerson> getBlackListToExcel(String customerType,
                                                       String customerName, String cardNum, String companyId, int start, int limit) {

        String hql = "from VEnterprisePerson v where v.name like ? and v.code like ?";
        if (null != companyId && !"".equals(companyId)) {
            hql = hql + " and v.companyId in (" + companyId + ") ";
        }
        if (null != customerType && !"".equals(customerType)) {
            hql = hql + " and v.type=('" + customerType + "')";
        }
        List<VEnterprisePerson> list = null;
        try {
            list = creditBaseDao.queryHql(hql, new Object[]{'%' + customerName + '%', '%' + cardNum + '%'}, start, limit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void ajaxQueryEnterprise(String searchCompanyId, String userIds,
                                    String enterprisename, String ownership, String shortname,
                                    String organizecode, String cciaa, int start, int limit,
                                    String sort, String dir, String customerLevel) {
        StringBuffer hql = new StringBuffer("select count(*)  ");
        StringBuffer hql2 = new StringBuffer("from EnterpriseView ep where 1=1 ")
                .append(" and (ep.isBlack=false or ep.isBlack is null) ")
                .append(" and (ep.enterprisename like ? or (ep.enterprisename is null and ''='" + enterprisename + "')) ")
                .append(" and (ep.ownership like ? or (ep.ownership is null and ''='" + ownership + "')) ")
                .append(" and (ep.shortname like ? or(ep.shortname is null and ''='" + shortname + "')) ")
                .append(" and (ep.organizecode like ? or(ep.organizecode is null and ''='" + organizecode + "')) ")
                .append(" and (ep.cciaa like ? or(ep.cciaa is null and ''='" + cciaa + "')) ")
                .append(" and (ep.customerLevel like ? or (ep.customerLevel is null and ''='" + customerLevel + "')) ");
        hql.append(hql2);
        if (null != searchCompanyId && !"".equals(searchCompanyId)) {
            hql.append(" and fn_check_repeat(ep.companyId,'" + searchCompanyId + "') = 1 and ep.companyId is not NULL ");
        }
        if (null != userIds) {
            hql.append(" and  fn_check_repeat(ep.belongedId,'" + userIds + "') = 1 ");
        }
        hql.append(" order by ep.id desc");
        Object params[] = new Object[]{enterprisename + "%", ownership + "%", shortname + "%", organizecode + "%", cciaa + "%", customerLevel + "%"};
        List totalList = new ArrayList();
        List list = new ArrayList();
        ;
        int totalProperty = 0;
        try {
            totalList = creditBaseDao.queryHql(hql.toString(), params);
            totalProperty = ((Long) totalList.get(0)).intValue();//记录总数
            //查询符合条件的部分记录数，数据库分页
            if (null != searchCompanyId && !"".equals(searchCompanyId)) {
                hql2.append(" and fn_check_repeat(ep.companyId,'" + searchCompanyId + "') = 1 and ep.companyId is not NULL ");
            }
            if (sort != null && dir != null) {
                if (null != userIds) {
                    hql2.append(" and fn_check_repeat(ep.belongedId,'" + userIds + "')=1 ");
                }
                hql2.append(" order by  CONVERT(ep." + sort + " , 'GBK') " + dir);
            } else {
                if (null != userIds) {
                    hql2.append(" and  fn_check_repeat(ep.belongedId,'" + userIds + "')=1 ");
                }
                hql2.append(" order by ep.id desc");
            }
            list = creditBaseDao.queryHql(hql2.toString(), params, start, limit);
            JsonUtil.jsonFromList(list, totalProperty);
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtil.jsonFromObject("数据库查询出错，请重试", false);
        }
    }

    /* (non-Javadoc)
     * @see com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao#enterPriseList(java.lang.Integer, java.lang.Integer, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public List<EnterpriseView> enterPriseList(Integer start, Integer limit,
                                               HttpServletRequest request, String userIdsStr) {
        // TODO 尚未写完该方法查询
        String enterprisename = request.getParameter("enterprisename");
        String organizecode = request.getParameter("organizecode");
        String cciaa = request.getParameter("cciaa");
        String sql = "SELECT  " +
                "e.id as id,  " +
                "e.enterprisename as enterprisename,  " +
                "e.cciaa as cciaa,  " +
                "e.organizecode as organizecode,  " +
                "e.telephone as telephone,  " +
                "bp.id as p2pId,  " +
                "bp.loginname as loginname,  " +
                "bp.truename as truename, " +
                "bp.cardcode as cardcode,  " +
                "bp.thirdPayFlagId  as thirdPayFlagId, " +
                "bp.telphone as p2ptelphone " +

                "FROM  " +
                "`cs_enterprise` AS e  " +
                "LEFT JOIN bp_cust_relation AS p ON (e.id = p.offlineCusId AND p.offlineCustType='b_loan')  " +
                "left join bp_cust_member as bp on (bp.id=p.p2pCustId) where 1=1";
        if (null != enterprisename && !"".equals(enterprisename)) {
            sql += " and e.enterprisename like '%" + enterprisename + "%' ";
        }
        if (null != organizecode && !"".equals(organizecode)) {
            sql += " and e.organizecode like '%" + organizecode + "%' ";
        }
        if (null != cciaa && !"".equals(cciaa)) {
            sql += " and e.cciaa like '%" + cciaa + "%' ";
        }
        if (null != userIdsStr && !userIdsStr.equals("")) {
            sql = sql + (" and fn_check_repeat(e.belongedId,'" + userIdsStr + "') = 1");
        }
        //System.out.println("-->"+sql);
        List list = null;
        if (start != null && limit != null) {
            list = this.getSession().createSQLQuery(sql).
                    addScalar("id", Hibernate.INTEGER).//主键id
                    addScalar("enterprisename", Hibernate.STRING).//企业名称
                    addScalar("cciaa", Hibernate.STRING).//营业执照
                    addScalar("organizecode", Hibernate.STRING).//组织机构代码
                    addScalar("telephone", Hibernate.STRING).//联系电话
                    addScalar("p2pId", Hibernate.LONG).//p2P主键Id
                    addScalar("loginname", Hibernate.STRING).//p2p登录名称
                    addScalar("truename", Hibernate.STRING).//注册用户名称
                    addScalar("cardcode", Hibernate.STRING).//注册用户证件号码
                    addScalar("thirdPayFlagId", Hibernate.STRING).//注册用户第三方账号
                    addScalar("p2ptelphone", Hibernate.STRING).//注册用户第三方账号
                    setResultTransformer(Transformers.aliasToBean(EnterpriseView.class)).
                    setFirstResult(start).setMaxResults(limit).
                    list();
        } else {
            list = this.getSession().createSQLQuery(sql).
                    addScalar("id", Hibernate.INTEGER).//主键id
                    addScalar("enterprisename", Hibernate.STRING).//企业名称
                    addScalar("cciaa", Hibernate.STRING).//营业执照
                    addScalar("organizecode", Hibernate.STRING).//组织机构代码
                    addScalar("telephone", Hibernate.STRING).//联系电话
                    addScalar("p2pId", Hibernate.LONG).//p2P主键Id
                    addScalar("loginname", Hibernate.STRING).//p2p登录名称
                    addScalar("truename", Hibernate.STRING).//注册用户名称
                    addScalar("cardcode", Hibernate.STRING).//注册用户证件号码
                    addScalar("thirdPayFlagId", Hibernate.STRING).//注册用户第三方账号
                    addScalar("p2ptelphone", Hibernate.STRING).//注册用户第三方账号
                    setResultTransformer(Transformers.aliasToBean(EnterpriseView.class)).
                    list();
        }
        return list;
    }

    @Override
    public List<EnterpriseView> getAllAccountList(Map map, PagingBean pb) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        String sql = "SELECT " +
                "cp.id AS id, " +
                "cp.enterprisename AS enterprisename  , " +
                "cp.organizecode AS organizecode, " +
                "cp.cciaa AS cciaa, " +
                "cp.telephone AS telephone, " +
                "o.id AS accountId, " +
                "o.accountName AS accountName, " +
                "o.accountNumber AS accountNumber, " +
                "o.investmentPersonId AS investmentPersonId, " +
                "o.investPersionType AS investPersionType, " +
                "o.totalMoney AS totalMoney " +
                "FROM " +
                "cs_enterprise AS cp " +
                "LEFT JOIN bp_cust_relation AS p ON (p.offlineCustType = 'p_loan' AND p.offlineCusId = cp.id) " +
                "LEFT JOIN ob_system_account AS o ON ( p.p2pCustId = o.investmentPersonId AND o.investPersionType = 0) " +
                "where 1=1 ";

        if (!map.isEmpty()) {
            Object accountNumber = map.get("accountNumber");
            if (accountNumber != null && !"".equals(accountNumber.toString())) {//查询出个人合作机构的各种类型
                sql = sql + " and o.accountNumber like '%" + accountNumber.toString() + "%'";
            }

            Object enterprisename = map.get("enterprisename");
            if (enterprisename != null && !"".equals(enterprisename.toString())) {//客户姓名
                sql = sql + " and cp.enterprisename like '%" + enterprisename.toString() + "%'";
            }

            Object organizecode = map.get("organizecode");
            if (organizecode != null && !"".equals(organizecode.toString())) {//组织架构
                sql = sql + " and cp.organizecode like '%" + organizecode.toString() + "%'";
            }

            Object cciaa = map.get("cciaa");
            if (cciaa != null && !"".equals(cciaa.toString())) {//营业执照号码
                sql = sql + " and cp.cciaa like '%" + cciaa.toString() + "%'";
            }
            Object userIdsStr = map.get("userIdsStr");
            if (userIdsStr != null && !"".equals(userIdsStr.toString())) {//拥有的客户
                sql = sql + (" and fn_check_repeat(cp.belongedId,'" + userIdsStr + "') = 1");
            }
        }
        //	System.out.println(sql);
        List<EnterpriseView> listCount = this.getSession().createSQLQuery(sql).
                addScalar("id", Hibernate.INTEGER).
                addScalar("enterprisename", Hibernate.STRING).
                addScalar("organizecode", Hibernate.STRING).
                addScalar("cciaa", Hibernate.STRING).
                addScalar("telephone", Hibernate.STRING).
                addScalar("accountId", Hibernate.LONG).
                addScalar("accountName", Hibernate.STRING).
                addScalar("accountNumber", Hibernate.STRING).
                addScalar("investmentPersonId", Hibernate.LONG).
                addScalar("investPersionType", Hibernate.SHORT).
                addScalar("totalMoney", Hibernate.BIG_DECIMAL).
                setResultTransformer(Transformers.aliasToBean(EnterpriseView.class)).
                list();

        if (pb != null) {
            pb.setTotalItems(listCount.size());
            if (pb.getStart() != null && pb.getPageSize() != null) {
                List<EnterpriseView> list = this.getSession().createSQLQuery(sql).
                        addScalar("id", Hibernate.INTEGER).
                        addScalar("enterprisename", Hibernate.STRING).
                        addScalar("organizecode", Hibernate.STRING).
                        addScalar("cciaa", Hibernate.STRING).
                        addScalar("telephone", Hibernate.STRING).
                        addScalar("accountId", Hibernate.LONG).
                        addScalar("accountName", Hibernate.STRING).
                        addScalar("accountNumber", Hibernate.STRING).
                        addScalar("investmentPersonId", Hibernate.LONG).
                        addScalar("investPersionType", Hibernate.SHORT).
                        addScalar("totalMoney", Hibernate.BIG_DECIMAL).
                        setResultTransformer(Transformers.aliasToBean(EnterpriseView.class)).
                        setFirstResult(pb.getStart()).setMaxResults(pb.getPageSize()).
                        list();
                return list;
            } else {
                return listCount;
            }
        } else {
            return listCount;
        }
    }

    @Override
    public Enterprise isEmpty(String organizecode, Integer enterId)
            throws Exception {
        String hql = "from Enterprise AS e where e.organizecode=? and  e.id !=?";
        List list = creditBaseDao.queryHql(hql, new Object[]{organizecode, enterId});
        if (null == list) {
            return null;
        } else {
            return (Enterprise) list.get(0);
        }
    }

    @Override
    public Enterprise getByOrganizecode(String cardcode) throws Exception {
        String hql = "from Enterprise AS e where e.organizecode=? ";
        List list = creditBaseDao.queryHql(hql, new Object[]{cardcode});
        if (null == list) {
            return null;
        } else {
            return (Enterprise) list.get(0);
        }
    }

    @Override
    public void loanEnterpriseQuota(PageBean pageBean) {
        StringBuffer sbCount = new StringBuffer("SELECT count(1) from (");
        StringBuffer sb = new StringBuffer("SELECT `id`,`enterprisename`,`organizecode`,`tenderingMoney`,`repayingMoney`,`totalMoney`,en.linkman,(1000000-`repayingMoney` - `tenderingMoney`) as `surplusMoney`");
        sb.append(" from ")
                .append("(SELECT e.`id`,e.`enterprisename`,e.`organizecode`,e.linkman, ")
                .append(" IFNULL( (SELECT SUM(`bidMoney`) from pl_bid_plan plan ")
                .append(" where plan.`receiverP2PAccountNumber` = member.`loginname` AND plan.`state` IN (0,1,2,6)) ")
                .append(",0) ")
                .append(" as `tenderingMoney`, ")
                .append(" IFNULL( ")
                .append(" (SELECT SUM(intent.`notMoney`) from bp_fund_intent intent  ")
                .append(" where intent.`fundType` = 'principalRepayment' and intent.`bidPlanId` in  ")
                .append(" (SELECT p.bidId from pl_bid_plan p where p.`receiverP2PAccountNumber` = member.`loginname` AND p.state != 6 ) ")
                .append(" ),0) ")
                .append(" as `repayingMoney`, ")
                .append(" IFNULL( ")
                .append("(SELECT SUM(`bidMoney`) from pl_bid_plan plan where plan.`receiverP2PAccountNumber` = member.`loginname` AND plan.`state` IN (0,1,2,6,7,10)) ")
                .append(",0)")
                .append("as `totalMoney` ")
                .append("from cs_enterprise e ")
                .append(" LEFT JOIN bp_cust_member member ON member.`cardcode` = e.`organizecode` where member.isCheckCard = 1 ) ")
                .append(" as en WHERE 1=1 ");

        HttpServletRequest request = pageBean.getRequest();
        if (StringUtils.isNotEmpty(request.getParameter("enterprisename"))){
            sb.append(" AND en.`enterprisename`  LIKE '%")
                    .append(request.getParameter("enterprisename"))
                    .append("%'");
        }
        if (StringUtils.isNotEmpty(request.getParameter("`organizecode`"))){
            sb.append(" AND en`.organizecode`  LIKE '%")
                    .append(request.getParameter("organizecode"))
                    .append("%'");
        }
        sbCount.append(sb);
        sbCount.append(") as c");
        if (pageBean.getStart() != null && pageBean.getLimit() != null){
            sb.append(" limit ")
                    .append(pageBean.getStart())
                    .append(",")
                    .append(pageBean.getLimit());
        }
        BigInteger count = (BigInteger)this.getSession().createSQLQuery(sbCount.toString()).uniqueResult();
        List<Enterprise> list = this.getSession().createSQLQuery(sb.toString())
                .addScalar("id", Hibernate.INTEGER).
                        addScalar("enterprisename", Hibernate.STRING).
                        addScalar("organizecode", Hibernate.STRING).
                        addScalar("tenderingMoney", Hibernate.BIG_DECIMAL).//-- 招标中金额
                addScalar("repayingMoney", Hibernate.BIG_DECIMAL).//-- 还款中金额
                addScalar("surplusMoney", Hibernate.BIG_DECIMAL).//-- 可用额度
                addScalar("linkman", Hibernate.STRING).//-- 可用额度
                addScalar("totalMoney", Hibernate.BIG_DECIMAL).//totalMoney    -- 总借款金额
                setResultTransformer(Transformers.aliasToBean(Enterprise.class)).
                        list();
        for (Enterprise enterprise : list) {
            BigDecimal availableMoney = personDao.getAvailableMoney(enterprise.getLinkman());
            enterprise.setLinkManSurplusMoney(availableMoney);
        }
        pageBean.setResult(list);
        pageBean.setTotalCounts(count.intValue());
    }

    @Override
    public BigDecimal getSurplusMoney(String organizecode) {
        String hql = "SELECT \t1000000- IFNULL(\n" +
                "\t(\n" +
                "\tSELECT\n" +
                "\t\tSUM( intent.`notMoney` ) \n" +
                "\tFROM\n" +
                "\t\tbp_fund_intent intent \n" +
                "\tWHERE\n" +
                "\t\tintent.`fundType` = 'principalRepayment' \n" +
                "\t\tAND intent.`bidPlanId` IN ( SELECT p.bidId FROM pl_bid_plan p WHERE p.`receiverP2PAccountNumber` = bp.`loginname` AND p.state != 6 ) \n" +
                "\t),\n" +
                "\t0 \n" +
                "\t) - IFNULL(\n" +
                "\t(\n" +
                "\tSELECT\n" +
                "\t\tSUM( `bidMoney` ) \n" +
                "\tFROM\n" +
                "\t\tpl_bid_plan plan \n" +
                "\tWHERE\n" +
                "\t\tplan.`receiverP2PAccountNumber` = bp.`loginname` \n" +
                "\t\tAND plan.`state` IN ( 0, 1, 2, 6 ) \n" +
                "\t),\n" +
                "\t0 \n" +
                ") AS surplusMoney\n" +
                "\tfrom cs_enterprise e\n" +
                "\tLEFT JOIN bp_cust_member bp  on bp.`cardcode` = e.`organizecode` \n" +
                "\twhere e.organizecode='" + organizecode+"'";
        Enterprise enterprise = (Enterprise) this.getSession().createSQLQuery(hql).addScalar("surplusMoney", Hibernate.BIG_DECIMAL).
                                setResultTransformer(Transformers.aliasToBean(Enterprise.class)).list().get(0);

        return enterprise.getSurplusMoney();
    }
}