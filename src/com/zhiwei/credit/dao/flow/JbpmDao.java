package com.zhiwei.credit.dao.flow;

import java.util.Date;
import java.util.List;

public interface JbpmDao {
	/**
	 * 取得流程定义的XML
	 * @param deployId
	 * @return
	 */
	public String getDefXmlByDeployId(String deployId);
	
	/**
	 * 把修改过的xml更新至回流程定义中
	 * @param deployId
	 * @param defXml
	 */
	public void wirteDefXml(String deployId,String defXml);
	
	/**
	 * 更新流程发起人
	 * @param startUserId
	 * @param executionId
	 * @param startUserIdKey
	 * add by lu 2012.08.23
	 */
	public void updateStartUserId(Long startUserId,Long executionId,String startUserIdKey);
	
	/**
	 * 解决类似deployment 440001 doesn't contain object smallLoanCommonFlow的错误的方法。
	 * @param piId
	 * @return
	 * add by lu 2013.06.24
	 */
	public List<String> getDeployIdByPdId(String pdId);
	
}
