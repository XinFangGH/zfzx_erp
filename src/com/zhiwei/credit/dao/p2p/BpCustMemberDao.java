package com.zhiwei.credit.dao.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.p2p.BpCustMember;

/**
 * 
 * @author 
 *
 */
public interface BpCustMemberDao extends BaseDao<BpCustMember>{ 

	/**
	 * 显示没有被删除的用户
	 * @return
	 */
	public List<BpCustMember> get();

	public BpCustMember getMemberByPIdAndFlag(String flag, String pid);

	public BpCustMember getMemberByFlagId(String ret);

	public BpCustMember getMemberUserName(String userName, String cardNum);
	public BpCustMember getMemberUserName(String userName);
	public BpCustMember isExist(String loginname);
	/**
	 * 判断邀请的推荐码是否存在
	 * @param recommandPerson
	 * @return
	 */
	public BpCustMember isRecommandPerson(String recommandPerson);
	/**
	 * 获取线上所有用户的列表
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<BpCustMember> getAllList(HttpServletRequest request,
			Integer start, Integer limit);
	
	
	//svn:songwj
	public List<BpCustMember> getAllList1(HttpServletRequest request,
			Integer start, Integer limit);
	/**
	 * 更新邀请数量的值
	 * @param recommandPerson
	 */
	public BpCustMember updateNum(String recommandPerson);

	/**
	 * 刷新邀请推荐用户数
	 * svn：songwj
	 * @return
	 */
	public List<BpCustMember> getBpCustMemberList();
	/**
	 * 按照推荐码查询推荐的人的信息
	 * svn：songwj
	 * @param recommandPerson
	 * @return
	 */
	public  List<BpCustMember> getBpCustMemberByrecommandPerson(String  recommandPerson);


	public List<BpCustMember> getByCardId(String userId);

	
	public List cusrNumList(HttpServletRequest request,PagingBean pb);
	public Long cusrNumSize(HttpServletRequest request);

		
	/**
	 * 合作机构账户查询
	 * @param map
	 * @return
	 */
	public List<BpCustMember>  listAccount(Map<String, String> map);
	/**
	 * 合作机构账户查询--总记录数
	 * @param map
	 * @return
	 */
	public Long listAccountNumber(Map<String, String> map);

	public void executeSql(String sql);
	
	public BpCustMember getPlainpassword(String recommandPerson);
	/**
	 * 根据用户名模糊查询
	 */
	public List<BpCustMember> getByTrueName(String trueName);

	List<BpCustMember> getBpCustMemberByInternalCode(String departmentRecommend);

	List<BpCustMember> getAllListCount(HttpServletRequest request,
			Integer start, Integer limit);

    void changeRecommand(String s, String recommandPerson);

	List<BpCustMember> getActicityCount(String loginname,String plainpassword,String truename);

	BigDecimal getYearMoney(String plainpassword,String yearThan);

	Integer getInventCount(String plainpassword);

	BigDecimal getSumActicityMoney(String plainpassword,String sumThird,String sumSecond, String sumFirst);

    List<BpCustMember> getAddressCount(String startDate, String endDate);

	List<BpCustMember> getSexCount(String startDate, String endDate);

	List<Object[]>  getAgeInfo(String startDate, String endDate);

    List<PlBidInfo> searchInvitesById(Long userId,String name , String startDate, String endDate);

	/**
	 *获取某人投资总额
	 *
	 * @auther: XinFang
	 * @date: 2018/8/13 11:29
	 */
	BigDecimal getTotalInvestMoney(Long id,String startDate, String endDate);

    List<PlBidInfo> getInvestmentInfo(Long id, String startDate, String endDate);

	List<BpCustMember> getBirThDay();


	String getMemberTel(Integer start, int limit);
}