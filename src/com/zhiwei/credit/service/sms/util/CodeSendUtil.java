package com.zhiwei.credit.service.sms.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.thirdInterface.PlThirdInterfaceLog;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.sms.MessageStrategyService;
import com.zhiwei.credit.service.thirdInterface.PlThirdInterfaceLogService;
import com.zhiwei.credit.util.MyUserSession;

/**
 * 验证码发送信息发送设置
 * @author songwenjie
 * 
 */
@SuppressWarnings("serial")
public class CodeSendUtil extends BaseAction {
	 MessageStrategyService messageStrategy=AppUtil.getSysConfig().get("sms_benname")==null?(MessageStrategyService) AppUtil.getBean(AppUtil.getSysConfig().get("hXSmsManagerService").toString()):(MessageStrategyService) AppUtil.getBean(AppUtil.getSysConfig().get("sms_benname").toString());
	
	 
	 private String sms_code_type;// 验证码类型
//	private String telphone = "15701332275";// 手机号码
	private String randomCode;// 随机数
	private String token;//

	@Resource
	private BpCustMemberService bpCustMemberService;

	@Resource
	private PlThirdInterfaceLogService plThirdInterfaceLogService;
	
 

	// 得到config.properties读取的所有资源
	@SuppressWarnings("unchecked")
	private static Map configMap = AppUtil.getConfigMap();
	
//	/**
//	 * 发送验证码的方法
//	 * svn：songwj
//	 */
//	public  void codeSend(){
//		if(this.telphone!=null && !"".equals(telphone)){
//			System.out.println("judgeReqData()===="+judgeReqData());	
////			if(judgeReqData()){
//				String content = "";
//				content = code();
//				System.out.println("content===="+content);
//				messageStrategyImpl.sendMsg(this.telphone, "", content);
////			}
//		}
//	}

	/**
	 * 关于对短信发送的设置
	 * 
	 * 1、调用信息加入到session中----------ip、当前用户
	 * 2、发送信息加入到session中----------手机号码-----session会不会非常大
	 * 3、同一个手机号码或者同一ip地址1分钟之内调用该方法5、10次则将该用户放入表中 同时该ip或者该手机在系统中禁用30分钟--放入禁用表中
	 **/
	public boolean check(String telphone) {
		String remoteAddr = getRequest().getRemoteAddr();// 远程ip
		BpCustMember member = (BpCustMember) getSession().getAttribute(
				MyUserSession.MEMBEER_SESSION);// 当前用户

		String ip = (String) getSession().getAttribute(MyUserSession.SMS_IP);// 获取登陆ip
		@SuppressWarnings("unused")
		String user = (String) getSession()
				.getAttribute(MyUserSession.SMS_USER);// 获取登陆用户
		String phone = (String) getSession().getAttribute(
				MyUserSession.SMS_PHONE);// 获取接收手机号
		String number = (String) getSession().getAttribute(
				MyUserSession.SMS_NUMBER);// 获取发送次数
		String time = (String) getSession()
				.getAttribute(MyUserSession.SMS_TIME);// 获取首次发送时间

		if (null == phone || "".equals(phone)) {
			// 首次调用短信接口
			if (null == member) {
				setSession(remoteAddr, "", telphone, "1", new Date()
						.getTime()
						+ "");
			} else {
				setSession(remoteAddr, member.getLoginname(), telphone,
						"1", new Date().getTime() + "");
			}
			return true;
		} else {
			// 无论是手机或者是ip只要不是第一次调用 就要更新number
			// session中手机的存放13521*****111,1234***333,12.....
			// session中ip的存放 192.168.1.1,192.156.1.33，.......
			boolean bphone = phone.contains(telphone);
			boolean bip = ip.contains(remoteAddr);
			if (!bphone && !bip) {// 同时不包含ip和手机时
				if (null == member) {
					setSession(remoteAddr, "", telphone, "1", new Date()
							.getTime()
							+ "");
				} else {
					setSession(remoteAddr, member.getLoginname(),
							telphone, "1", new Date().getTime() + "");
				}
				return true;
			} else {
				// 首先判断第几次调
				if (number.length() > 2) {// 大于五次
					Long interval = (new Date().getTime() - Long
							.parseLong(time))
							/ (5 * 60 * 1000);
					// 时间差小于1分钟
					if (interval < 1L) {
						return false;
					} else {// 大于30分钟
						clareSession1();
						return true;
					}
				} else {
					phone += "," + telphone;
					ip += "," + remoteAddr;
					number += "1";
					if (null == member) {
						setSession(remoteAddr, "", telphone, number,
								new Date().getTime() + "");
					} else {
						setSession(remoteAddr, member.getLoginname(),
								telphone, number, new Date().getTime()
										+ "");
					}
					return true;
				}
			}
		}
	}

	// 移除登陆ip、发送次数、接收手机、登陆客户session信息
	public void clareSession1() {
		getSession().removeAttribute(MyUserSession.SMS_IP);
		getSession().removeAttribute(MyUserSession.SMS_NUMBER);
		getSession().removeAttribute(MyUserSession.SMS_PHONE);
		getSession().removeAttribute(MyUserSession.SMS_USER);
	}

	// 添加登陆ip、发送次数、接收手机、登陆用户、首次发送时间
	public void setSession(String ip, String user, String phone, String number,
			String time) {
		getSession().setAttribute(MyUserSession.SMS_IP, ip);// 登陆ip
		getSession().setAttribute(MyUserSession.SMS_NUMBER, number);// 发送次数
		getSession().setAttribute(MyUserSession.SMS_PHONE, phone);// 接收手机
		getSession().setAttribute(MyUserSession.SMS_USER, user);// 登陆用户
		getSession().setAttribute(MyUserSession.SMS_TIME, time);// 首次发送时间
	}

	// 发送登陆ip、发送次数、接收手机、登陆用户
	public void setSession(String ip, String user, String phone, String number) {
		getSession().setAttribute(MyUserSession.SMS_IP, ip);
		getSession().setAttribute(MyUserSession.SMS_NUMBER, number);
		getSession().setAttribute(MyUserSession.SMS_PHONE, phone);
		getSession().setAttribute(MyUserSession.SMS_USER, user);
	}
	
	/**
	 * 返回短信发送结果
	 * @param resultStr
	 * @return
	 */
//	public String judgeResult(String resultStr){
//		
//		StringBuffer sb = new StringBuffer();
//		if(resultStr.endsWith("SUCCESS")){
//			System.out.println("发送成功！");
//			sb.append("{\"success\":true,\"phone\":");
//			 sb.append(random);
//			 sb.append(",\"result\":1");
//			 sb.append(",\"remark\":1");
//			 sb.append(",\"status\":200");
//				sb.append("}");
//		}else{
//			sb.append("{\"success\":true,\"phone\":");
//			 sb.append(random);
//			 sb.append(",\"result\":1");
//			 sb.append(",\"remark\":");
//			 sb.append(gson.toJson("手机验证码发送失败"+strRelt[1]));
//			 sb.append(",\"status\":201");
//				sb.append("}");
//		}
//		return SUCCESS;
//	}

	
	/**
	 * 验证码发送
	 * svn：songwj
	 * @return
	 */
	public String  code(String phone){
//		if(judgeReqData(phone)){
		
		 //获取 注册手机验证码
		String randomif = (String) this.getSession().getAttribute(
				MyUserSession.TELPHONE_REG_RANDOM_SESSION);

		if (randomif != null) {// 如果手机验证码存在，删除掉，重新发送
			this.getSession().removeAttribute(
					MyUserSession.TELPHONE_REG_RANDOM_SESSION);
		}

		String random = createRandom(true, 6);// 生成的验证码
		System.out.println(random);
		
		 Map configMap=AppUtil.getSysConfig();
		
		 System.out.println("temp短信发送模板==="+configMap.get("sms_Sign").toString());
		// 获得短信发送模板
		String temp = configMap.get("sms_Sign").toString();
		// 替换模板中的特殊字段值
		String content = temp.replace("${code}", random).replace(
				"${phone4}",configMap.get("phone").toString() == null ? "" : configMap.get("phone").toString()).replace(
				"${subject}",
				configMap.get("subject").toString() == null ? ""
						: configMap.get("subject").toString());
		
		Map<String, String> map = new HashMap<String, String>();
					map.put(sms_code_type, random);
		this.getSession().setAttribute(
							MyUserSession.TELPHONE_REG_RANDOM_SESSION, random);
		return content;
//		}
//		return null;
	}
	/**
	 * 判断传入数据是规则
	 * 返回true为验证通过
	 *     false为验证未通过
	 * @return
	 */
	public Boolean judgeReqData(String telphone) {
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		BpCustMember mem = (BpCustMember) this.getSession().getAttribute(
				MyUserSession.MEMBEER_SESSION);

		// 刷新当前用户
//		if (mem != null) {
//			mem = bpCustMemberService.get(mem.getId());
//		}
//
//		String code = (String) this.getSession().getAttribute("applyToken");
//		if (null == mem && (null == code || "".equals(code))) {
//			sb.append("{\"success\":true,\"phone\":");
//			sb.append("");
//			sb.append(",\"result\":1");
//			sb.append(",\"remark\":");
//			sb.append(gson.toJson("操作非法"));
//			sb.append(",\"status\":201");
//			sb.append("}");
//			setJsonString(sb.toString());
//			
//			System.out.println(sb.toString());
//			return false;
//		}
		if (null != token && !"".equals(token) && !"null".equals(token)) {
//			if (null == code || "".equals(code)
//					|| !token.toLowerCase().equals(code.toLowerCase())) {
				sb.append("{\"success\":true,\"phone\":");
				sb.append(token);
				sb.append(",\"result\":1");
				sb.append(",\"remark\":");
				sb.append(gson.toJson("验证码错误"));
				sb.append(",\"status\":201");
				sb.append("}");
				setJsonString(sb.toString());
				return false;
//			}
		}

		// 将数据转成JSON格式
		if (StringUtil.isNumeric(telphone)) {// 判断手机号是不是由数字组成
			
			// if-else 需要重构
			boolean bool = check(telphone);
			if (!bool) {
				sb.append("{\"success\":true,\"phone\":");
				sb.append(telphone);
				sb.append(",\"result\":1");
				sb.append(",\"remark\":");
				sb.append(gson.toJson("操作非法"));
				sb.append(",\"status\":201");
				sb.append("}");
				setJsonString(sb.toString());
				return false;
			}
			// 当输入的新号码和旧号码相同
			if (null != mem.getTelphone()
					&& (mem.getTelphone().equals(telphone) || mem
							.getTelphone() == telphone)) {
				sb.append("{\"success\":true,\"phone\":");
				sb.append(telphone);
				sb.append(",\"result\":1");
				sb.append(",\"remark\":");
				sb.append(gson.toJson("输入的新号码和旧号码不能相同"));
				sb.append(",\"status\":201");
				sb.append("}");
				setJsonString(sb.toString());
				return false;
			}
		} else {
			sb.append("{\"success\":false,\"phone\":");
			sb.append("");
			sb.append(",\"result\":1");
			sb.append(",\"remark\":");
			sb.append(gson.toJson("手机号为非数字"));
			sb.append(",\"status\":000");
			sb.append("}");

			plThirdInterfaceLogService.saveLog("", "失败", "手机号为非数字 属于恶意攻击",
					"短信接口", mem == null ? null : mem.getId(),
					PlThirdInterfaceLog.MEMTYPE1, PlThirdInterfaceLog.TYPE2,
					PlThirdInterfaceLog.TYPENAME2, mem == null ? "" : mem
							.getLoginname(), telphone, "", "");
			return false;
		}
		// this.getSession().removeAttribute("");//移除令牌，没点击一次生成验证码
		setJsonString(sb.toString());
		System.out.println("============" + sb.toString());
		return true;
	}

	/**
	 * 创建指定数量的随机字符串
	 * 
	 * @param numberFlag
	 *            是否是数字
	 * @param length
	 *            长度
	 * @return
	 */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890"
				: "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr;
	}

	public String getSms_code_type() {
		return sms_code_type;
	}

	public void setSms_code_type(String smsCodeType) {
		sms_code_type = smsCodeType;
	}

//	public String getTelphone() {
//		return telphone;
//	}
//
//	public void setTelphone(String telphone) {
//		this.telphone = telphone;
//	}

	public String getRandomCode() {
		return randomCode;
	}

	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
