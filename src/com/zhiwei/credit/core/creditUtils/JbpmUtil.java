package com.zhiwei.credit.core.creditUtils;


/**
 * @funciton 工作流 工具类
 * 
 *  项目主键 ：UUID
 *  
 *  =========================================================================================================
 *  
 * 关于 项目id 和 jbpm piKey的关系我是这样定义的：
 *
 * 一个项目 只对应一个主流程piKey , 这个项目的其余流程全是子流程
 *
 * 主流程piKey形式是固定的 ,即有了项目id后 主piKey就是固定的了。这样就可以通过项目id直接找到piKey
 * 
 * 再有子流程的话,直接在 主piKey 后面添加后缀，依次类推
 * 
 * 
 * processInstanceId 和 processInstanceKey 的关系：processInstanceKey = processName.processInstanceId
 * 
 * @author credit004
 *
 */
public class JbpmUtil{
	
	public static final String FIRST_CREDITSOFT = "cs";//credit soft
	public static final String SECOND_ENTERPRISE_BEFORE_PROCESS = "eb";//enterpris before 【企业融资 保前】
	public static final String SECOND_ENTERPRISE_MIDDLE_PROCESS = "em";//enterpris middle 【企业融资 保中】
	public static final String SECOND_ENTERPRISE_BEFORE_SECOND_PROCESS = "ebs";//enterpris before 【企业融资 保前】
	public static final String CHILD_ACCOUNT_PROCESS = "ea";//企业融资-保前-台账
	public static final String SECOND_CAR_BEFORE_PROCESS = "cb";//car before【个人车贷 保前】
	public static final String SECOND_CAR_MIDDLE_PROCESS = "cm";//car middle【个人车贷 保中】
		
	/**
	 * @function JBPM生成key 规则   4字符
	 * 
	 * @author credit004
	 */
	private static final String getProcessInstanceKey(String first, String second){
		
//		Date date = new Date();
//		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");//这意味着  主流程不能和子流程在同一天开启
		
		//key生成规则：credit+流程名(流程定义key)+日期 
		String processInstanceKey = first+second;
		
		return processInstanceKey;
	}
	
	/**
	 * @funciton 创建主piKey.	创建主流程piKey时 规则为 projectId(UUID) + 项目编号	一个项目对应一个主流程
	 * 
	 * size : 4 + 2 + 36 = 42
	 * 
	 * @param projectId
	 * @param first
	 * @param second
	 * @return
	 */
	public static final String getMainProcessInstanceKey(String projectId, String first, String second){
		return projectId+"->"+getProcessInstanceKey(first,second);
	}
	
	/**
	 * @function  创建子流程 piKey = mainPiKey + childPostfix + yyyymmddhhmmss
	 * 
	 * @size 64位
	 * 
	 * @param mainPiKey
	 * @param childPostfix
	 * @return
	 */
	public static final String getChildProcessInstanceKey(String mainPiKey, String childPostfix){
		return mainPiKey + "->" + childPostfix+DateUtil.getNowDateTime("yyyyMMddhhmmss");
	}
	
	/**项目主键 --> 主piKey     请勿调用        jiang*/
	public static final String projectIdToMainPiKey(String projectId, String first, String second){
		
		return projectId+"->"+getProcessInstanceKey(first,second);
	}
	
	/**piKey --> 项目主键 	 如果传入的是projectId 同样返回projectId 	jiang*/
	public static final String piKeyToProjectId(String piKey){
		
		return piKey.substring(0, 36);
		
	}
	
	/**processInstanceId --> processInstanceKey jiang*/
	public static final String pId2piKey(String processInstanceId){
		//注意 这是因为 processName 是4位的字符，否则出错··
		return processInstanceId.substring(5);
	}
	
	/**processInstanceKey --> processInstanceId*/
	public static final String piKey2pId(String processInstanceKey, String processName){
		return processName+"."+processInstanceKey;
	}
	
}
