package com.zhiwei.credit.dao.p2p.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.p2p.BpCustMemberDao;
import com.zhiwei.credit.model.p2p.BpCustMember;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCustMemberDaoImpl extends BaseDaoImpl<BpCustMember> implements BpCustMemberDao{

	public BpCustMemberDaoImpl() {
		super(BpCustMember.class);
	}

	@Override
	public List<BpCustMember> get() {
		String hql = "from BpCustMember where (isDelete!=1 or isDelete is null) and (isForbidden!=1 or isForbidden is null)";
		List<BpCustMember> list = findByHql(hql);
		return list;
	}
	@Override
	public BpCustMember getMemberByPIdAndFlag(String flag, String pid) {

		String hql = "from BpCustMember cust where cust.moneymoremoreId=? ";
		Object[] params = {flag};
		return (BpCustMember)findUnique(hql, params);
	
	}

	@Override
	public BpCustMember getMemberUserName(String userName, String cardNum) {
		String hql = "from BpCustMember cust where cust.loginname=? and cust.cardcode=?";
		Object[] params = {userName,cardNum};
		return (BpCustMember)findUnique(hql, params);
	}
	/**
	 * 判断邀请的验证码是否存在
	 */
	@Override
	public BpCustMember isRecommandPerson(String recommandPerson) {
		BpCustMember bpCustMember=null;
		StringBuffer hql = new StringBuffer(" from BpCustMember ");
		hql.append(" where  plainpassword= '"+recommandPerson+"'");
		//hql.append(" where  id= '"+recommandPerson+"'");
		List<BpCustMember> list = findByHql(hql.toString());
		if(list.size()>0){
			bpCustMember=list.get(0);
		}
		return bpCustMember;
	}
	@Override
	public BpCustMember getMemberByFlagId(String userName) {
		String hql = "from BpCustMember cust where cust.thirdPayFlagId=? ";
		Object[] params = {userName};
		return (BpCustMember)findUnique(hql, params);
	}

	@Override
	public List<BpCustMember> getAllList(HttpServletRequest request,
			Integer start, Integer limit) {
		//System.out.println("进入dao层方法");
		String sql="SELECT bp.id as id," +
				"bp.loginname as loginname," +
				"bp.truename as truename, " +
				"bp.telphone as telphone, " +
				"bp.sex as sex, " +
				"bp.thirdPayFlagId as thirdPayFlagId, " +
				"bp.email as email, " +
				"bp.cardtype as cardtype, " +
				"bp.cardcode as cardcode, " +
				"bp.birthday as birthday, " +
				"bp.isForbidden as isForbidden, " +
				"bp.score as score, " +
				"bp.isVip as isVip, " +
				"bp.departmentRecommend as departmentRecommend, " +
				"bp.registrationDate as registrationDate, " +
				"b.loginname as directReferralsName, " +
				"b1.loginname as indirectReferenceName " +
				"from (select * from bp_cust_member as b where 1=1 ";
		if(request!=null){
			String isForBidType =request.getParameter("isForBidType");
			if(isForBidType!=null &&!"".equals(isForBidType)){
				if(isForBidType.equals("1")){
					sql =sql+" and b.isForbidden="+Integer.parseInt(isForBidType);
				}else{
					sql =sql+" and (b.isForbidden  is null or b.isForbidden=0)";
				}
			}
			String  loginname=request.getParameter("Q_loginname_S_LK");
			if(loginname!=null &&!"".equals(loginname)){
				sql =sql+" and b.loginname like '%"+loginname+"%'";
			}
			String  truename=request.getParameter("Q_truename_S_LK");
			if(truename!=null &&!"".equals(truename)){
				sql =sql+" and b.truename like '%"+truename+"%'";
			}
			String  telphone=request.getParameter("Q_telphone_S_LQ");
			if(telphone!=null &&!"".equals(telphone)){
				sql =sql+" and b.telphone like '%"+telphone+"%'";
			}
			String  cardcode=request.getParameter("Q_cardcode_S_LQ");
			if(cardcode!=null &&!"".equals(cardcode)){
				sql =sql+" and b.cardcode like '%"+cardcode+"%'";
			}
			String isvip = request.getParameter("Q_isVip_SN_EQ");
			if(isvip!=null &&!"".equals(isvip)){
				if("1".equals(isvip)){
					sql =sql+" and b.isVip="+isvip;
				}else{
					sql =sql+" and (b.isVip="+isvip+" or b.isVip is null)";
				}
			}
		}
		if(start!=null&&limit!=null){
			sql = sql + " limit "+start+","+limit;
		}
		sql = sql+") as bp " +
		" left join bp_cust_member as b1 on bp.secondRecommandPerson=b1.plainpassword " +
		" left join bp_cust_member as b on bp.recommandPerson=b.plainpassword  where 1=1 ";
		if(request!=null){
			String  sort=request.getParameter("sort");
			if(null!=sort && !"".equals(sort)){
				String  dir=request.getParameter("dir");
				sql+=" order by bp."+sort+" "+dir;
			}else{
				sql+=" order by bp.registrationDate desc ";
			}
		}
		List<BpCustMember> list =null;
		if(start==null|| limit==null){
			list=this.getSession().createSQLQuery(sql)
			.addScalar("id",Hibernate.LONG)
			.addScalar("loginname",Hibernate.STRING)
			.addScalar("truename",Hibernate.STRING)
			.addScalar("telphone",Hibernate.STRING)
			.addScalar("thirdPayFlagId",Hibernate.STRING)
			.addScalar("email",Hibernate.STRING)
			.addScalar("sex",Hibernate.INTEGER)
			.addScalar("cardtype",Hibernate.INTEGER)
			.addScalar("cardcode",Hibernate.STRING)
			.addScalar("birthday",Hibernate.DATE)
			.addScalar("isForbidden",Hibernate.INTEGER)
			.addScalar("score",Hibernate.LONG)
			.addScalar("isVip",Hibernate.SHORT)
			.addScalar("registrationDate",Hibernate.TIMESTAMP)
			.addScalar("directReferralsName",Hibernate.STRING)
			.addScalar("indirectReferenceName",Hibernate.STRING)
			.addScalar("departmentRecommend",Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(BpCustMember.class)).list();
		}else{
			list=this.getSession().createSQLQuery(sql)
			.addScalar("id",Hibernate.LONG)
			.addScalar("loginname",Hibernate.STRING)
			.addScalar("truename",Hibernate.STRING)
			.addScalar("telphone",Hibernate.STRING)
			.addScalar("sex",Hibernate.INTEGER)
			.addScalar("thirdPayFlagId",Hibernate.STRING)
			.addScalar("email",Hibernate.STRING)
			.addScalar("cardtype",Hibernate.INTEGER)
			.addScalar("cardcode",Hibernate.STRING)
			.addScalar("birthday",Hibernate.DATE)
			.addScalar("isForbidden",Hibernate.INTEGER)
			.addScalar("score",Hibernate.LONG)
			.addScalar("isVip",Hibernate.SHORT)
			.addScalar("registrationDate",Hibernate.TIMESTAMP)
			.addScalar("directReferralsName",Hibernate.STRING)
			.addScalar("indirectReferenceName",Hibernate.STRING)
			.addScalar("departmentRecommend",Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(BpCustMember.class))
			/*.setFirstResult(start).setMaxResults(limit)*/.list();
		}
		return list;
	}
	@Override
	public List<BpCustMember> getAllListCount(HttpServletRequest request,
			Integer start, Integer limit){
		String sql = "select * from bp_cust_member as bp where 1=1 ";
		if(request!=null){
			String isForBidType =request.getParameter("isForBidType");
			if(isForBidType!=null &&!"".equals(isForBidType)){
				if(isForBidType.equals("1")){
					sql =sql+" and bp.isForbidden="+Integer.parseInt(isForBidType);
				}else{
					sql =sql+" and (bp.isForbidden  is null or bp.isForbidden=0)";
				}
			}
			String  loginname=request.getParameter("Q_loginname_S_LK");
			if(loginname!=null &&!"".equals(loginname)){
				sql =sql+" and bp.loginname like '%"+loginname+"%'";
			}
			String  truename=request.getParameter("Q_truename_S_LK");
			if(truename!=null &&!"".equals(truename)){
				sql =sql+" and bp.truename like '%"+truename+"%'";
			}
			String  telphone=request.getParameter("Q_telphone_S_LQ");
			if(telphone!=null &&!"".equals(telphone)){
				sql =sql+" and bp.telphone like '%"+telphone+"%'";
			}
			String  cardcode=request.getParameter("Q_cardcode_S_LQ");
			if(cardcode!=null &&!"".equals(cardcode)){
				sql =sql+" and bp.cardcode like '%"+cardcode+"%'";
			}
			String isvip = request.getParameter("Q_isVip_SN_EQ");
			if(isvip!=null &&!"".equals(isvip)){
				if("1".equals(isvip)){
					sql =sql+" and bp.isVip="+isvip;
				}else{
					sql =sql+" and (bp.isVip="+isvip+" or b.isVip is null)";
				}
			}
		}
		List<BpCustMember> list =null;
		list=this.getSession().createSQLQuery(sql).addEntity(BpCustMember.class).list();
		return list;
	}

	@Override
	public void changeRecommand(String id, String recommandPerson) {
		String hql = "UPDATE  BpCustMember  set recommandPerson =? where id =?";

		this.getSession().createQuery(hql).setParameter(0,recommandPerson).setParameter(1,Long.valueOf(id)).executeUpdate();

	}

	@Override
	public List<BpCustMember>  getActicityCount(String loginname,String plainpassword,String truename) {
		StringBuffer sql = new StringBuffer("SELECT b1.loginname ,b1.truename ,b1.plainpassword ,count(b1.id) as recommandNum from bp_cust_member b1 ,bp_cust_member b2 where b1.plainpassword = b2.recommandPerson " );

		if (StringUtils.isNotBlank(loginname)){
			sql.append(" and b1.loginname='"+loginname+"'");
		}
		if (StringUtils.isNotBlank(plainpassword)){
			sql.append(" and b1.plainpassword='"+plainpassword+"'");
		}
		if (StringUtils.isNotBlank(truename)){
			sql.append(" and b1.truename='"+truename+"'" );
		}
		sql.append(" GROUP BY b1.id ORDER BY count(b1.id) desc ");
		List list = this.getSession().createSQLQuery(sql.toString())
				.addScalar("loginname", Hibernate.STRING)
				.addScalar("truename", Hibernate.STRING)
				.addScalar("plainpassword", Hibernate.STRING)
				.addScalar("recommandNum", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(BpCustMember.class)).list();


		return list;
	}

	@Override
	public BigDecimal getYearMoney(String plainpassword,String yearThan) {
		String sql = "SELECT IFNULL(x.money,0) from (SELECT IFNULL(SUM( investMoney ) ,0) as money from invest_person_info i , (SELECT bidId from pl_bid_plan where createtime >  '2018-07-01'  and ((payMoneyTimeType = 'monthPay' and payMoneyTime =12 ) or (payMoneyTimeType = 'yearPay') ))as a ,(select id from bp_cust_member where recommandPerson = '"+plainpassword+"') as c WHERE i.bidPlanId = a.bidId  and i.investPersonId = c.id) as x";
		if (StringUtils.isNotBlank(yearThan)){
		    sql += " where x.money >= 100000";
        }
		BigDecimal money = (BigDecimal) this.getSession().createSQLQuery(sql).uniqueResult();

		if (money == null){
		    money = new BigDecimal(0);
        }

		return money;
	}

	@Override
	public Integer getInventCount(String plainpassword) {
		String sql = "SELECT count(*) from bp_cust_member where recommandPerson = '"+plainpassword+"' and registrationDate > '2018-07-01'";
		BigInteger count = (BigInteger) this.getSession().createSQLQuery(sql).uniqueResult();

		return count.intValue();
	}

	@Override
	public BigDecimal getSumActicityMoney(String plainpassword,String sumThird,String sumSecond, String sumFirst) {
		StringBuffer sb = new StringBuffer("SELECT IFNULL(three.money+six.money*1.4+year.money*2,0) from (SELECT IFNULL(SUM( investMoney ) ,0) as money from invest_person_info i , (SELECT bidId from pl_bid_plan where createtime >  '2018-07-01'  AND payMoneyTimeType = 'monthPay' AND payMoneyTime = 3 )as a ,(select id from bp_cust_member where recommandPerson = '"+plainpassword+"') as c WHERE i.bidPlanId = a.bidId  and i.investPersonId = c.id)as three,(SELECT IFNULL(\tSUM( investMoney ) ,0) as money from invest_person_info i , (SELECT bidId from pl_bid_plan where createtime >  '2018-07-01'  and payMoneyTimeType = 'monthPay' and payMoneyTime =6 )as a ,(select id from bp_cust_member where recommandPerson = '"+plainpassword+"') as c WHERE i.bidPlanId = a.bidId  and i.investPersonId = c.id)as six,(SELECT IFNULL(\tSUM( investMoney ) ,0) as money from invest_person_info i , (SELECT bidId from pl_bid_plan where createtime >  '2018-07-01'  and ((payMoneyTimeType = 'monthPay' and payMoneyTime =12 ) or (payMoneyTimeType = 'yearPay') ))as a ,(select id from bp_cust_member where recommandPerson = '"+plainpassword+"') as c WHERE i.bidPlanId = a.bidId  and i.investPersonId = c.id)as year where 1=1 ");

		if (StringUtils.isNotBlank(sumThird)){
		    sb.append(" and three.money+six.money*1.4+year.money*2 >= 80000 and  three.money+six.money*1.4+year.money*2 < 210000");
        }
        if (StringUtils.isNotBlank(sumSecond)){
            sb.append(" and three.money+six.money*1.4+year.money*2 >= 210000 and  three.money+six.money*1.4+year.money*2 < 510000");
        }
        if (StringUtils.isNotBlank(sumFirst)){
            sb.append(" and three.money+six.money*1.4+year.money*2 >= 510000");
        }



		BigDecimal money = (BigDecimal) this.getSession().createSQLQuery(sb.toString()).uniqueResult();

        if (money == null){
            money = new BigDecimal(0);
        }
		return money;
	}

	@Override
	public List<BpCustMember> getAddressCount(String startDate, String endDate) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.a as relationAddress,count(*) as sex,count(*)/b.countAll as totalMoney from (SELECT \n" +
				"case left(cardcode,2) \n" +
				"when '11' then '北京市' \n" +
				"when '12' then '天津市' \n" +
				"when '13' then '河北省' \n" +
				"when '14' then '山西省' \n" +
				"when '15' then '内蒙古自治区' \n" +
				"when '21' then '辽宁省' \n" +
				"when '22' then '吉林省' \n" +
				"when '23' then '黑龙江省' \n" +
				"when '31' then '上海市' \n" +
				"when '33' then '浙江省' \n" +
				"when '34' then '安徽省' \n" +
				"when '35' then '福建省' \n" +
				"when '36' then '江西省' \n" +
				"when '37' then '山东省' \n" +
				"when '41' then '河南省' \n" +
				"when '42' then '湖北省' \n" +
				"when '43' then '湖南省' \n" +
				"when '44' then '广东省' \n" +
				"when '45' then '广西壮族自治区' \n" +
				"when '46' then '海南省' \n" +
				"when '50' then '重庆市' \n" +
				"when '51' then '四川省' \n" +
				"when '52' then '贵州省' \n" +
				"when '53' then '云南省' \n" +
				"when '54' then '西藏自治区' \n" +
				"when '61' then '陕西省' \n" +
				"when '62' then '甘肃省' \n" +
				"when '63' then '青海省' \n" +
				"when '64' then '宁夏回族自治区' \n" +
				"when '65' then '新疆维吾尔自治区' \n" +
				"when '71' then '台湾省' \n" +
				"when '81' then '香港特别行政区' \n" +
				"when '82' then '澳门特别行政区' \n" +
				"else '江苏省' \n" +
				"end AS a \n" +
				"FROM bp_cust_member b where customerType != 1 and  cardcode is not null ");

		if (StringUtils.isNotBlank(startDate) && !startDate.equals("undefined") && StringUtils.isNotBlank(endDate) && !endDate.equals("undefined")){
				sb.append("and registrationDate BETWEEN '"+startDate+" 00:00:00' and '"+endDate+" 23:59:59'");
		}else if ( StringUtils.isNotBlank(endDate) && !endDate.equals("undefined")){
				sb.append("and registrationDate <  '"+endDate+" 23:59:59'");
		}

		sb.append(") as c ,(SELECT count(*) as countAll FROM bp_cust_member b where customerType != 1 and  cardcode is not null ");

		if (StringUtils.isNotBlank(startDate) && !startDate.equals("undefined") && StringUtils.isNotBlank(endDate) && !endDate.equals("undefined")){
			sb.append("and registrationDate BETWEEN '"+startDate+" 00:00:00' and '"+endDate+" 23:59:59'");
		}else if ( StringUtils.isNotBlank(endDate) && !endDate.equals("undefined")){
			sb.append("and registrationDate <  '"+endDate+" 23:59:59'");
		}
		sb.append(" )  as b GROUP BY c.a ORDER BY count(*) desc");

		List list = this.getSession().createSQLQuery(sb.toString()).addScalar("relationAddress", Hibernate.STRING).addScalar("sex", Hibernate.INTEGER)
				.addScalar("totalMoney", Hibernate.BIG_DECIMAL).setResultTransformer(Transformers.aliasToBean(BpCustMember.class)).list();

		return list;
	}

	@Override
	public List<BpCustMember> getSexCount(String startDate, String endDate) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.a as relationAddress ,count(*) as sex,count(*)/b.countAll as totalMoney from (SELECT \n" +
				"case if(length(cardcode)=18, cast(substring(cardcode,17,1) as UNSIGNED)%2, if(length(cardcode)=15,cast(substring(cardcode,15,1) as UNSIGNED)%2,3)) \n" +
				"when 1 then '男' \n" +
				"else '女' \n" +
				"end AS a FROM bp_cust_member b where customerType != 1 and  cardcode is not null ");
		if (StringUtils.isNotBlank(startDate) && !startDate.equals("undefined") && StringUtils.isNotBlank(endDate) && !endDate.equals("undefined")){
			sb.append("and registrationDate BETWEEN '"+startDate+" 00:00:00' and '"+endDate+" 23:59:59'");
		}else if ( StringUtils.isNotBlank(endDate) && !endDate.equals("undefined")){
			sb.append("and registrationDate <  '"+endDate+" 23:59:59'");
		}

		sb.append(" ) as c ,(SELECT count(*) as countAll FROM bp_cust_member b where customerType != 1 and  cardcode is not null ");
		if (StringUtils.isNotBlank(startDate) && !startDate.equals("undefined") && StringUtils.isNotBlank(endDate) && !endDate.equals("undefined")){
			sb.append("and registrationDate BETWEEN '"+startDate+" 00:00:00' and '"+endDate+" 23:59:59'");
		}else if ( StringUtils.isNotBlank(endDate) && !endDate.equals("undefined")){
			sb.append("and registrationDate <  '"+endDate+" 23:59:59'");
		}

		sb.append(" )  as b  GROUP BY c.a ORDER BY count(*) desc");

		List list = this.getSession().createSQLQuery(sb.toString()).addScalar("relationAddress", Hibernate.STRING).addScalar("sex", Hibernate.INTEGER)
				.addScalar("totalMoney", Hibernate.BIG_DECIMAL).setResultTransformer(Transformers.aliasToBean(BpCustMember.class)).list();

		return list;
	}

	@Override
	public List<Object[]>  getAgeInfo(String startDate, String endDate) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT\n" +
				"sum( CASE WHEN c.age BETWEEN 18 AND 20 THEN 1 ELSE 0 END )/count(c.age) AS '18-20proportion',\n" +
				"sum( CASE WHEN c.age BETWEEN 18 AND 20 THEN 1 ELSE 0 END ) AS '18-20',\n" +
				"sum( CASE WHEN c.age BETWEEN 21 AND 30 THEN 1 ELSE 0 END )/count(c.age) AS '20-30proportion',\n" +
				"sum( CASE WHEN c.age BETWEEN 21 AND 30 THEN 1 ELSE 0 END ) AS '20-30',\n" +
				"sum( CASE WHEN c.age BETWEEN 31 AND 40 THEN 1 ELSE 0 END )/count(c.age) AS '30-40proportion',\n" +
				"sum( CASE WHEN c.age BETWEEN 31 AND 40 THEN 1 ELSE 0 END ) AS '30-40',\n" +
				"sum( CASE WHEN c.age BETWEEN 41 AND 50 THEN 1 ELSE 0 END )/count(c.age) AS '40-50proportion',\n" +
				"sum( CASE WHEN c.age BETWEEN 41 AND 50 THEN 1 ELSE 0 END ) AS '40-50',\n" +
				"sum( CASE WHEN c.age BETWEEN 51 AND 60 THEN 1 ELSE 0 END )/count(c.age) AS '50-60proportion',\n" +
				"sum( CASE WHEN c.age BETWEEN 51 AND 60 THEN 1 ELSE 0 END ) AS '50-60',\n" +
				"sum( CASE WHEN c.age > 60  THEN 1 ELSE 0 END )/count(c.age) AS '60proportion',\n" +
				"sum( CASE WHEN c.age > 60  THEN 1 ELSE 0 END ) AS '60'\n" +
				"FROM\n" +
				"(\n" +
				"SELECT YEAR\n" +
				"\t( curdate( ) ) -\n" +
				"IF\n" +
				"(\n" +
				"\tlength( cardcode ) = 18,\n" +
				"\tsubstring( cardcode, 7, 4 ),\n" +
				"IF\n" +
				"\t( length( cardcode ) = 15, concat( '19', substring( cardcode, 7, 2 ) ), NULL ) \n" +
				") AS age \n" +
				"FROM\n" +
				"\tbp_cust_member b \n" +
				"WHERE\n" +
				"\tcustomerType != 1 \n" +
				"AND cardcode IS NOT NULL");

			if (StringUtils.isNotBlank(startDate) && !startDate.equals("undefined") && StringUtils.isNotBlank(endDate) && !endDate.equals("undefined")){
				sb.append(" and registrationDate BETWEEN '"+startDate+" 00:00:00' and '"+endDate+" 23:59:59'");
			}else if ( StringUtils.isNotBlank(endDate) && !endDate.equals("undefined")){
				sb.append(" and registrationDate <  '"+endDate+" 23:59:59'");
			}
 					 sb.append(" ) AS c");
		List list = this.getSession().createSQLQuery(sb.toString()).list();
		return list;
	}




	@Override
	public BpCustMember getMemberUserName(String userName) {
		String hql = "from BpCustMember cust where cust.loginname=? ";
		Object[] params = {userName};
		return (BpCustMember)findUnique(hql, params);
	}
	
	//svn:songwj
	@Override
	public List<BpCustMember> getAllList1(HttpServletRequest request,
			Integer start, Integer limit) {
		//System.out.println("进入dao层方法");
		String sql="SELECT "+
					"p.id as id, "+
					"p.loginname AS loginname, "+
					"p.truename AS truename, "+
					"p.plainpassword as plainpassword, "+
					"p.telphone as telphone, "+
					"p.recommandPerson as recommandPerson, "+
					"p.cardcode as cardcode, "+
					"sum(CASE WHEN p.plainpassword = p1.recommandPerson THEN 1 ELSE 0 END ) AS recommandNum, "+
					"sum(CASE WHEN p.plainpassword = p2.secondRecommandPerson THEN 1 ELSE 0 END) AS secondRecommandNum "+
					"FROM "+
					" bp_cust_member AS p "+
					"LEFT JOIN bp_cust_member AS p1 ON (p.plainpassword = p1.recommandPerson) "+
					"LEFT JOIN bp_cust_member AS p2 ON (p.plainpassword = p2.secondRecommandPerson) where 1=1 ";
		if(request!=null){
			String  loginname=request.getParameter("Q_loginname_S_LK");
			if(loginname!=null &&!"".equals(loginname)){
				sql =sql+" and p.loginname like '%"+loginname+"%'";
			}
			String  truename=request.getParameter("Q_truename_S_LK");
			if(truename!=null &&!"".equals(truename)){
				sql =sql+" and p.truename like '%"+truename+"%'";
			}
			String  recommandPerson=request.getParameter("Q_recommandPerson_S_LK");
			if(recommandPerson!=null &&!"".equals(recommandPerson)){
				sql =sql+" and p.recommandPerson like '%"+recommandPerson+"%'";
			}
			String  cardcode=request.getParameter("Q_cardcode_S_EQ");
			if(cardcode!=null &&!"".equals(cardcode)){
				sql =sql+" and bp.cardcode= '"+cardcode+"'";
			}
			
			//sql = sql + " order by recommandNum  desc";
		}
		
		sql=sql+"  GROUP BY p.id  order by recommandNum desc";
		System.out.println("BpCustMemberDaoImpl-->>getAllList1：  "+sql);
		List<BpCustMember> list =null;
		if(start==null|| limit==null){
			list=this.getSession().createSQLQuery(sql).
			addScalar("id",Hibernate.LONG).
			addScalar("loginname",Hibernate.STRING).
			addScalar("truename",Hibernate.STRING).
			addScalar("plainpassword",Hibernate.STRING).
			addScalar("telphone",Hibernate.STRING).
			addScalar("recommandPerson",Hibernate.STRING).
			addScalar("cardcode",Hibernate.STRING).
			addScalar("recommandNum",Hibernate.INTEGER).
			addScalar("secondRecommandNum",Hibernate.INTEGER).
			setResultTransformer(Transformers.aliasToBean(BpCustMember.class)).
			list();
		}else{
			list=this.getSession().createSQLQuery(sql).
			addScalar("id",Hibernate.LONG).
			addScalar("loginname",Hibernate.STRING).
			addScalar("truename",Hibernate.STRING).
			addScalar("plainpassword",Hibernate.STRING).
			addScalar("telphone",Hibernate.STRING).
			addScalar("recommandPerson",Hibernate.STRING).
			addScalar("cardcode",Hibernate.STRING).
			addScalar("recommandNum",Hibernate.INTEGER).
			addScalar("secondRecommandNum",Hibernate.INTEGER).
			setResultTransformer(Transformers.aliasToBean(BpCustMember.class)).
			setFirstResult(start).setMaxResults(limit).
			list();
			//list=this.getSession().createQuery(sql).setFirstResult(start).setMaxResults(limit).list();
		}
		return list;
	}
	
	
	/**
	 * 刷新邀请推荐用户数
	 * svn：songwj
	 * @return
	 */
	public List<BpCustMember> getBpCustMemberList(){
		String sql=" UPDATE bp_cust_member as bm "
				+" INNER JOIN (select count(*) as num ,bp.plainpassword,bp.recommandPerson   from bp_cust_member bp   where   !ISNULL(bp.recommandPerson) GROUP BY bp.recommandPerson) as  tablenum"
				+" on tablenum.recommandPerson = bm.plainpassword"
				+" INNER JOIN (select count(*) as num2 ,bp.plainpassword,bp.secondRecommandPerson   from bp_cust_member bp   where   !ISNULL(bp.secondRecommandPerson) GROUP BY bp.secondRecommandPerson) as  tablenum2"
				+" on tablenum.recommandPerson = bm.plainpassword"
				+" SET bm.recommandNum=tablenum.num ,bm.secondRecommandNum=tablenum2.num2";
		System.out.println("sql=="+sql);
		this.getSession().createSQLQuery(sql);
		return null;
	}

	/**
	 * 更新邀请数量的总数
	 * svn：songwj
	 */
	public BpCustMember updateNum(String recommandPerson){
		String hql = "from BpCustMember cust where cust.plainpassword=? ";
		Object[] params = {recommandPerson};
		return   (BpCustMember) findUnique(hql, params);
	} 
	
	/**
	 * 按照邀请码查询邀请的人的数据信息
	 * svn：songwj
	 * @return
	 */
	public  List<BpCustMember> getBpCustMemberByrecommandPerson(String  recommandPerson){
//		System.out.println("dao类中的recommandPerson值===="+recommandPerson);
		String hql = "from BpCustMember where recommandPerson='"+recommandPerson+"'";
		if(recommandPerson==null){
			hql = "from BpCustMember where recommandPerson is null";
		}
//		System.out.println("dao类中的hql值===="+hql);
		List<BpCustMember> list = findByHql(hql);
		return list;
	}
	/**
	 * 按照内部邀请码查询客户
	 */
	@Override
	public  List<BpCustMember> getBpCustMemberByInternalCode(String  departmentRecommend){
//		System.out.println("dao类中的recommandPerson值===="+recommandPerson);
		String hql = "from BpCustMember where departmentRecommend='"+departmentRecommend+"'";
		if("".equals(departmentRecommend)){
			hql = "from BpCustMember where 1<>1";
		}
		if(departmentRecommend==null){
			hql = "from BpCustMember where departmentRecommend is null or departmentRecommend=''";
		}
//		System.out.println("dao类中的hql值===="+hql);
		List<BpCustMember> list = findByHql(hql);
		return list;
	}

	@Override

	public List<BpCustMember> getByCardId(String userId) {
		String hql = "from BpCustMember where cardcode  like '%"+userId+"%' ";
		return this.getSession().createQuery(hql).list();

	}
	public List cusrNumList(HttpServletRequest request, PagingBean pb) {
		String sql="SELECT "
			+" CONCAT(c.id,'') ,"
			+" CONCAT(p.id,'') as personId,"
			+" c.loginname,"
			+" c.cardcode,"
			+" p.cardtype,"
			+" d.itemValue,"
			+" p.cardnumber,"
			+" c.truename,"
			+" p.`name`,"
			+" c.telphone,"
			+" p.cellphone,"
			+" c.email,"
			+" p.selfemail"
			+" FROM bp_cust_member AS c"
			+" LEFT JOIN bp_cust_relation AS r ON r.p2pCustId = c.id"
			+" AND r.offlineCustType = 'p_loan'"
			+" left join cs_person as p on p.id=r.offlineCusId"
			+" left join dictionary as d on d.dicId=p.cardtype"
			+" WHERE (c.isForbidden IS NULL OR c.isForbidden = 0)";
		
		String onlineCust=request.getParameter("online");//只查询来源于线上的个人借款客户信息
		if(null!=onlineCust && onlineCust.equals("0")){
			sql=sql+" and r.p2pCustId is not null ";
		}
		
		String loginname=request.getParameter("loginname");
		if(null!=loginname && !loginname.equals("")){
			sql=sql+" and c.loginname like '%"+loginname+"%'";
		}
		String truename=request.getParameter("truename");
		if(null!=truename && !truename.equals("")){
			sql=sql+" and c.truename like '%"+truename+"%'";
		}
		String telphone=request.getParameter("telphone");
		if(null!=telphone && !telphone.equals("")){
			sql=sql+" and c.telphone like '%"+telphone+"%'";
		}
		String cardcode=request.getParameter("cardcode");
		if(null!=cardcode && !cardcode.equals("")){
			sql=sql+" and c.cardcode like '%"+cardcode+"%'";
		}
		return this.getSession().createSQLQuery(sql).setFirstResult(pb.getStart()).setMaxResults(pb.getPageSize()).list();

	}


	@Override
	
	public Long cusrNumSize(HttpServletRequest request) {
		String sql="SELECT count(c.id)"
			+" FROM bp_cust_member AS c"
			+" LEFT JOIN bp_cust_relation AS r ON r.p2pCustId = c.id"
			+" AND r.offlineCustType = 'p_loan'"
			+" left join cs_person as p on p.id=r.offlineCusId"
			+" left join dictionary as d on d.dicId=p.cardtype"
			+" WHERE (c.isForbidden IS NULL OR c.isForbidden = 0)";
		String loginname=request.getParameter("loginname");
		if(null!=loginname && !loginname.equals("")){
			sql=sql+" and c.loginname like '%"+loginname+"%'";
		}
		String truename=request.getParameter("truename");
		if(null!=truename && !truename.equals("")){
			sql=sql+" and c.truename like '%"+truename+"%'";
		}
		String telphone=request.getParameter("telphone");
		if(null!=telphone && !telphone.equals("")){
			sql=sql+" and c.telphone like '%"+telphone+"%'";
		}
		String cardcode=request.getParameter("cardcode");
		if(null!=cardcode && !cardcode.equals("")){
			sql=sql+" and c.cardcode like '%"+cardcode+"%'";
		}
		Long count=0l;
		List list=this.getSession().createSQLQuery(sql).list();
		if(null!=list && list.size()>0){
			BigInteger size=(BigInteger) list.get(0);
			count=size.longValue();
		}
		return count;
	}

	public BpCustMember updateNum(String recommandPerson, Integer num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BpCustMember> listAccount(Map<String, String> map) {
		String sql = " SELECT " + 
				" bm.id,  " + 
				" bm.loginname, " + 
				" bm.truename, " + 
				" bm.telphone, " + 
				" bm.sex, " + 
				" bm.cardcode, " + 
				" bm.birthday, " + 
				" bm.email, " + 
				" bm.isForbidden, " + 
				" bm.score, " + 
				" bm.registrationDate " + 
				" FROM " + 
				" bp_cust_member bm " + 
				" LEFT JOIN bp_cust_relation  br on bm.id = br.p2pCustId " + 
				" WHERE " + 
				" br.offlineCustType = ? ";
		
/*		if(map.get("bidProName")!=null&&!"".equals(map.get("bidProName"))){
			sql += " and p.bidProName like '%"+map.get("bidProName")+"%' ";
		}
*/		
		return this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG)
				.addScalar("loginname", Hibernate.STRING)
				.addScalar("truename", Hibernate.STRING)
				.addScalar("telphone", Hibernate.STRING)
				.addScalar("sex", Hibernate.INTEGER)
				.addScalar("cardcode", Hibernate.STRING)
				.addScalar("birthday", Hibernate.DATE)
				.addScalar("email", Hibernate.STRING)
				.addScalar("score", Hibernate.LONG)
				.addScalar("registrationDate", Hibernate.DATE)
				.setResultTransformer(Transformers.aliasToBean(BpCustMember.class))
				.setParameter(0, map.get("offlineCustType"))
				.setFirstResult(Integer.valueOf(map.get("start")))
				.setMaxResults(Integer.valueOf(map.get("limit"))).list();
		
	}

	@Override
	public Long listAccountNumber(Map<String, String> map) {
		String sql = " SELECT " + 
				" count(*) " + 
				" FROM " + 
				" bp_cust_member bm " + 
				" LEFT JOIN bp_cust_relation  br on bm.id = br.p2pCustId " + 
				" WHERE " + 
				" br.offlineCustType = ? ";
		
		Object uniqueResult = this.getSession().createSQLQuery(sql)
							  .setParameter(0, map.get("offlineCustType"))
							  .uniqueResult();
		return  ((BigInteger)uniqueResult).longValue();
		
	}
	
	@Override
	public BpCustMember isExist(String loginname) {
		BpCustMember bpCustMember=null;
		StringBuffer hql = new StringBuffer(" from BpCustMember ");
		hql.append(" where (isDelete!=1 or isDelete is null) and (isForbidden!=1 or isForbidden is null)  and loginname= '").append(loginname).append("'");
		List<BpCustMember> list = findByHql(hql.toString());
		if(list.size()>0){
			bpCustMember=list.get(0);
		}
		return bpCustMember;
	}

	@Override
	public void executeSql(String sql) {
		this.jdbcTemplate.update(sql);
	}

	@Override
	public BpCustMember getPlainpassword(String recommandPerson) {
		String hql = "from BpCustMember where plainpassword='"+recommandPerson+"'";
		System.out.println("dao类中的hql值===="+hql);
		List<BpCustMember> list = findByHql(hql);
		BpCustMember member=null;
		if(null !=list && !list.isEmpty()){
			member=list.get(0);
		}
		return member;
	}

	@Override
	public List<BpCustMember> getByTrueName(String trueName) {
		// TODO Auto-generated method stub
        String hql = "from BpCustMember where truename  like '%"+trueName+"%' ";
		return this.getSession().createQuery(hql).list();
	}

	@Override
	public List<PlBidInfo> searchInvitesById(Long userId, String name, String startDate, String endDate) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT\n" +
				"\tp.userId,\n" +
				"\tp.userName,\n" +
				"\ta.truename,\n" +
				"\ta.telphone,\n" +
				"\tp.bidName,\n" +
				"CASE\n" +
				"\t\tb.payMoneyTimeType \n" +
				"\t\tWHEN 'monthPay' THEN\n" +
				"\t\tCONCAT( b.payMoneyTime, '个月' ) \n" +
				"\t\tWHEN 'yearPay' THEN\n" +
				"\t\tCONCAT( b.payMoneyTime, '年' ) ELSE CONCAT( b.payMoneyTime, '天' ) \n" +
				"\tEND AS newInvestPersonName,\n" +
				"\tp.userMoney,\n" +
				"\tp.bidtime,\n" +
				"\tb.state \n" +
				"FROM\n" +
				"\tpl_bid_info p,\n" +
				"\t(\n" +
				"\tSELECT\n" +
				"\t\tb.id AS ids,\n" +
				"\t\tb.truename, \n" +
				"\t\tb.telphone \n" +
				"\tFROM\n" +
				"\t\tbp_cust_member b,\n" +
				"\t\t( SELECT b.plainpassword FROM bp_cust_relation r LEFT JOIN bp_cust_member b ON r.p2pCustId = b.id WHERE r.offlineCusId = ");
		sb.append(userId+" ) c \n" +
				"\tWHERE\n" +
				"\t\tb.recommandPerson = c.plainpassword \n" +
				"\t) AS a,\n" +
				"\tpl_bid_plan b \n" +
				"WHERE\n" +
				"\ta.ids = p.userId \n" +
				"\tAND p.bidId = b.bidId \n" +
				"\tAND p.state IN (1,2)");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
			sb.append("  and p.bidtime BETWEEN '"+startDate+ " 00:00:00'  AND '"+endDate+" 23:59:59'");
		}else {
			sb.append("  and p.bidtime BETWEEN '"+DateUtil.getFirstDay()+ " 00:00:00'  AND '"+DateUtil.getLastDay()+" 23:59:59'");
		}
		if (StringUtils.isNotBlank(name)){
			sb.append(" and (p.userName='"+name+"' or a.truename='"+name+"' or a.telphone='"+name+"') ");
		}
		sb.append(" order by p.bidtime desc ");
		List list = getSession().createSQLQuery(sb.toString()).addScalar("bidName",Hibernate.STRING)
				.addScalar("newInvestPersonName",Hibernate.STRING)
				.addScalar("userId",Hibernate.LONG)
				.addScalar("truename",Hibernate.STRING)
				.addScalar("userName",Hibernate.STRING)
				.addScalar("telphone",Hibernate.STRING)
				.addScalar("userMoney",Hibernate.BIG_DECIMAL)
				.addScalar("bidtime",Hibernate.DATE)
				.addScalar("state",Hibernate.SHORT)
				.setResultTransformer(Transformers.aliasToBean(PlBidInfo.class))
				.list();
		return list;
	}

	/**
	 *获取某人投资总额
	 *
	 * @auther: XinFang
	 * @date: 2018/8/13 11:29
	 */
	@Override
	public BigDecimal getTotalInvestMoney(Long id, String startDate, String endDate) {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT SUM(userMoney) from pl_bid_info where userId = "+id+" and state in (1,2)  ");
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
			sb.append("  and bidtime BETWEEN '"+startDate+" 00:00:00'  AND '"+endDate+" 23:59:59'");
		}

		BigDecimal money = (BigDecimal) getSession().createSQLQuery(sb.toString()).uniqueResult();

		if (money == null || money.compareTo(BigDecimal.ZERO)==0){
			money = new BigDecimal(0);
		}

		return money;
	}

    @Override
    public List<PlBidInfo> getInvestmentInfo(Long id, String startDate, String endDate) {
        String sql = "SELECT\n" +
                        "\tp.bidName,\n" +
                        "CASE\n" +
                        "\t\tb.payMoneyTimeType \n" +
                        "\t\tWHEN 'monthPay' THEN\n" +
                        "\t\tCONCAT( b.payMoneyTime, '个月' ) \n" +
                        "\t\tWHEN 'yearPay' THEN\n" +
                        "\t\tCONCAT( b.payMoneyTime, '年' ) ELSE CONCAT( b.payMoneyTime, '天' ) \n" +
                        "\tEND AS newInvestPersonName,\n" +
                        "\tp.userMoney,\n" +
                        "\tp.bidtime,\n" +
                        "\tb.state \n" +
                        "FROM\n" +
                        "\tpl_bid_info p\n" +
                        "\tLEFT JOIN pl_bid_plan b ON p.bidId = b.bidId \n" +
                        "WHERE\n" +
                        "\tp.userId = " +id+
                        "  AND p.state in (1,2) ";

        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            sql = sql+("  and p.bidtime BETWEEN '"+startDate+" 00:00:00'  AND '"+endDate+" 23:59:59'");
        }

        List list = getSession().createSQLQuery(sql).addScalar("bidName",Hibernate.STRING)
                .addScalar("newInvestPersonName",Hibernate.STRING)
                .addScalar("userMoney",Hibernate.BIG_DECIMAL)
                .addScalar("bidtime",Hibernate.DATE)
                .addScalar("state",Hibernate.SHORT)
                .setResultTransformer(Transformers.aliasToBean(PlBidInfo.class))
                .list();

        return list;
    }


	@Override
	public List<BpCustMember> getBirThDay() {
		String hql = "from BpCustMember where DATE_FORMAT(NOW(),'%m-%d') = DATE_FORMAT(birthday,'%m-%d')";
		return this.getSession().createQuery(hql).list();
	}

	@Override
	public String getMemberTel(Integer start, int limit) {
		String sql = "SELECT GROUP_CONCAT(telphone ORDER BY id asc) as sexname from (SELECT telphone,id from bp_cust_member where telphone is not null AND telphone !='' ORDER BY id ASC LIMIT ?,?) as a";
		List<BpCustMember> list =  this.getSession().createSQLQuery(sql).addScalar("sexname",Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(BpCustMember.class)).setParameter(0,start).setParameter(1,limit).list();
		String result = "";
		for (BpCustMember bpCustMember : list) {
			result = bpCustMember.getSexname();
		}
		return result;
	}

}