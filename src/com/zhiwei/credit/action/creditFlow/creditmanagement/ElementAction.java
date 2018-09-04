package com.zhiwei.credit.action.creditFlow.creditmanagement;



import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.service.creditFlow.creditmanagement.ElementService;


/**
 * @function 指标管理----要素【题库-题目】列表
 * @author credit004
 *
 */
public class ElementAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	@Resource
	private ElementService elementService;
	
	public ElementService getElementService() {
		return elementService;
	}

	public void setElementService(ElementService elementService) {
		this.elementService = elementService;
	}


	/**
	 * @列表
	 * @throws Exception
	 */
	public void allElement() throws Exception{
		
		
//		int totalNumber = elementService.getAllElementAmount();
//		
//		List<ProblibElement> list = elementService.getAllElement(start,limit);
//		
//		JsonUtil.jsonFromList(list,totalNumber);
	}
	
}
