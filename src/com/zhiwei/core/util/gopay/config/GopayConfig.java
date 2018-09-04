package com.zhiwei.core.util.gopay.config;

/* *
 *类名：GopayCommonConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及前后台通知地址
 *版本：2.1
 *日期：2012-06-27
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究国付宝接口使用，只是提供一个参考。
 */
public class GopayConfig {
	/**
     * 国付宝提供给商户调试的网关地址
     */
    public static String gopay_gateway = "https://211.88.7.30/PGServer/Trans/WebClientAction.do";
	
    /**
     * 国付宝服务器时间，反钓鱼时使用
     */
	public static String gopay_server_time_url = "https://211.88.7.30/PGServer/time";
	
	/**
     * 字符编码格式，目前支持 GBK 或 UTF-8
     */
	public static String input_charset = "GBK";
}
