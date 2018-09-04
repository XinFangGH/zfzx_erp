package com.zhiwei.credit.util;

/***
 * 与P2P通信
 * **/

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.hibernate.MappingException;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.id.Configurable;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zhiwei.core.dao.GenericDao;
import com.zhiwei.core.util.AppUtil;

public class FundIntentUtil{

	public BigDecimal getP2PInterest(Long precentId,Long investPersonId,String orderNo)throws Exception{
		if(null==precentId||"".equals(precentId)){
			throw new Exception("传入recentId为空!");
		}
		if(null==investPersonId||"".equals(investPersonId)){
			throw new Exception("传入investPersonId为空!");
		}
		JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		
		String sql = "";//"select sum(incomeMoney) as incomeMoney from bp_fund_intent where preceptId = "+precentId+" and investPersonId = "+investPersonId+" and fundType = 'loanInterest'";
		if(null==orderNo||"".equals(orderNo)||orderNo.length()==0){
			sql = "select sum(incomeMoney) as incomeMoney from bp_fund_intent where preceptId = "+precentId+" and investPersonId = "+investPersonId+" and fundType = 'loanInterest'";
		}else{
			sql = "select sum(incomeMoney) as incomeMoney from bp_fund_intent where orderNo = '"+orderNo+"' and fundType = 'loanInterest'";
		}
		BigDecimal m = (BigDecimal) jdbcTemplate.queryForObject(sql, new RowMapper(){
			BigDecimal money = new BigDecimal(0);
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				money = rs.getBigDecimal("incomeMoney");
				return money;
			}
			
		});
		
		return m;
	}
	
	public BigDecimal getP2PPrincipal(Long precentId,Long investPersonId,String orderNo)throws Exception{
		if(null==precentId||"".equals(precentId)){
			throw new Exception("传入recentId为空!");
		}
		if(null==investPersonId||"".equals(investPersonId)){
			throw new Exception("传入investPersonId为空!");
		}

		JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		String sql = "";//"select sum(incomeMoney) as incomeMoney from bp_fund_intent where preceptId = "+precentId+" and investPersonId = "+investPersonId+" and fundType = 'principalRepayment'";
		if(null==orderNo||"".equals(orderNo)||orderNo.length()==0){
			sql = "select sum(incomeMoney) as incomeMoney from bp_fund_intent where preceptId = "+precentId+" and investPersonId = "+investPersonId+" and fundType = 'principalRepayment'";
		}else{
			sql = "select sum(incomeMoney) as incomeMoney from bp_fund_intent where orderNo = '"+orderNo+"'  and fundType = 'principalRepayment'";
		}
		
		BigDecimal m = (BigDecimal) jdbcTemplate.queryForObject(sql, new RowMapper(){
			BigDecimal money = new BigDecimal(0);
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				money = rs.getBigDecimal("incomeMoney");
				return money;
			}	
		});
		return m;
	}
}
