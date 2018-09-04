package com.zhiwei.credit.service.thirdPay.goPay.config;

/* *
 *����GopayCommonConfig
 *���ܣ���������
 *��ϸ�������ʻ��й���Ϣ��ǰ��̨֪ͨ��ַ
 *�汾��2.1
 *���ڣ�2012-06-27
 *˵����
 *���´���ֻ��Ϊ�˷����̻����Զ��ṩ��������룬�̻����Ը���Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 *�ô������ѧϰ���о����ӿ�ʹ�ã�ֻ���ṩһ���ο���
 */
public class GopayConfig {
	/**
     * ���ṩ���̻����Ե���ص�ַ
     */
    public static String gopay_gateway = "https://mertest.gopay.com.cn/PGServer/Trans/WebClientAction.do";
	
    /**
     * ��������ʱ�䣬������ʱʹ��
     */
	public static String gopay_server_time_url = "https://mertest.gopay.com.cn/PGServer/time";
	
	/**
     * �ַ�����ʽ��Ŀǰ֧�� GBK �� UTF-8
     */
	public static String input_charset = "GBK";
}
