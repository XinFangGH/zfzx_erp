package com.zqsign;
/**
 * 
 * @author shehongli
 * 一定要注意测试环境与生产环境作切换前，需要更改ZQID PRIVATE_KEY REQUEST_URL三处。
 */
public class ZqsignManage {
	
	public class Zqid{
		//生产环境zqid
		public static final String ZQID = "ZQ938742B7D4D5402C80FB52F5FA1F319A";
		//测试环境zqid
//		public static final String ZQID = "ZQABA206A379B342FB987B8DCCBA679549";
	}
	public class Key{
		//生产环境私钥
		public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKj7d+hQvR0MbrTCRHzQHcjnoiMi4Bf5QhereQHFjPNUXUGJHIZFcANiyjQIdg0ygpxF21X3s0L5G5iBvgInzblf1Z0l+y5czqjb+5cwhe5SoIGNC/Qkl5oIifMkE2t+4sMEBzrJ9TK6/Flc8P2dEZvYslKgalgJv0D10zcVFjP7AgMBAAECgYAiuereVxlGCvQFOZcRf0Nfshpj1hrvUk3+x0JK1uosZz3y5dZy0Xo/d+HN5586/xJQQxrcA9dXABJnuOHvV3azODoOSPAe1d8N952ImNgZZ+VgrNpweBB6dt8QbbTYEdUJ+bfE1Mw9kC61UmiZqdZurekLQFnp3BjGVe9tq+lzwQJBANQVl9D3xzMnWFwD3taS4j90J/NmmIPuaz2l5m531xvTZYONI1ynUIO1WRtzgICRJFgbb+KrXc3bMZvwkuTenEkCQQDL+RK7nGJCQIWr7HadTPNJ3Qml52eX0rlpUAdL5iIiyrZBQtgBZPPY6pfrKEFhO6XgF0gkNxDt6GxwW3a71CYjAkA4fhv3dNjh3lwh/oHwJCYSAu3VXwHHOs3kGPndeRyac9/5f7Hor0S6IKdAsa6MEJ1evsg5163f9MAtKpLmVgnJAkEAwkjEpx8WGLUMne3mLX6WXczE29nGur5lDQLFcG4MJcegQhlBVlKqUftiKQN3Av6lyQVkGdq5rWKtp3f3aIFysQJBAIsV0Jopa/98FGx/t+bzXBBERU9tmMxVyDH1HhDrlLzimxwddCW6Qv9ynn/9S+dAXE2qoG75I0rbggi5rL0pkos=";
		//测试环境key
//		public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANIwqMKRiZMTMerWYJsp54AoMUcIbgZsdB4FjtGAzabh/NYH9ptNgNBfBo78yShPCP5c0wB0MVqg3wv5ExQRcCA5uj1ajO+FuHy5ESxmDDftxOzQlpHlMdvxCLZwJjy0+Il2AsZcbcSy3HMDN8HGhOG01A9rllbx6JnyC8hFdd+7AgMBAAECgYBztZHRuqjPrGt4ahe4k3L73CR0hDF9m8q4lDqxHoUX76RudufNSvc0vnsvz/01EX1T+em2gECDMbhYMP/NtmPQegoVIsojSGSSF8Q+q7JOCQlDi9JXiRMkoj+uSMeSqa4EbqOdoFAj+F8BlzYJCUCdfdcJRR4Zb8seFNlpUfDToQJBAPMGQt8dWfFGDGlo9Tnif5GIlz09Of7odn/NOyFb6c+fca0ufrg816GWGgLBl0qnj8bO/93P+EY0MWsVF8RytRkCQQDdaZtWGm9YImGT+PKdKapQvt0C5RAfi2OAnRndqCs8bA1K1kPII8hg/t2QFPshx48pqayJ7ve5/dmeig1y0eHzAkAKWnHu32k9hiZxNy97T9LveEo5KaqW2YBy4WNrgGbtmXVWU2zCnJTzJVnmVCkF3S2a4qaz5HBHTWHtlfB1Rg3BAkEA0cpr3fTkRX0mOf/rWhENiL6gSUrjsQ/w8v9ob8cVWIYFPkCxLuUAyy8Snp/SqFofA1n62yMrZPbriTXDsmS+EwJBAOFhYJS/x04TKX3H4iGDXLKLTSaQWoDyHBIZG61HSLVI8UTTre/Efc8jrs6GnYXkXAA0KeAcUQDxdeF0YRFhc2g=";
	}

	public class Url{
		//正式服务器连接
		public static final String REQUEST_URL = "http://sign.zqsign.com/";
		//测试环境服务器连接
//		public static final String REQUEST_URL = "http://test.sign.zqsign.com:8081/";
	}


	public class SignType{
		//签章不验证
		public static final String SIGNATURE = "SIGNATURE";
		//签章验证
		public static final String SIGNATURECODE = "SIGNATURECODE";
		//签字不验证
		public static final String WRITTEN = "WRITTEN";
		//签字验证
		public static final String WRITTENCODE = "WRITTENCODE";
	}

	public class Result{

		//请求成功
		public static final String SUCCESS = "操作成功";
		public static final String REQUESR_SUCCESS = "请求成功";

		//用户唯一标识格式不正确
		public static final String USERCODE_IDINCORRECT = "用户ID格式不正确";
		//用户唯一标识已经存在
		public static final String USERCODE_EXIST = "用户已存在";
		//用户唯一标识不存在
		public static final String USERCODE_NOEXIST = "用户不存在";
		//用户唯一标识不能为空
		public static final String USERCODE_NOTNULL = "用户ID不能为空";
		//用户唯一标识不能为空
		public static final String USERCODE_NOSTYLE = "用户ID与需要更新的用户类型不匹配";
		//用户姓名格式不正确
		public static final String NAME_INCORRECT = "用户姓名格式不正确";
		//用户姓名不能为空
		public static final String NAME_NOTNULL = "用户姓名不能为空";
		//企业名称不能为空
		public static final String ENTERPRISENAME_NOTNULL = "企业名称不能为空";
		//用户身份证号格式不正确
		public static final String IDCARDNO_INCORRECT = "身份证号码格式不正确";
		//用户身份证号不能为空
		public static final String IDCARDNO_NOTNULL = "身份证号码不能为空";
		//用户电话号码格式不正确
		public static final String MOBILE_INCORRECT = "手机号码格式不正确";
		//用户电话号码不能为空
		public static final String MOBILE_NOTNULL = "手机号码不能为空";

		//企业注册证件号格式不正确
		public static final String CERTIFICATE_INCORRECT = "企业机构代码格式不正确";
		//企业注册证件号格式不正确
		public static final String CERTIFICATE_NOTNULL = "企业机构代码不能为空";
		//企业地址不能为空
		public static final String ADDRESS_NOTNULL = "企业注册地址不能为空";
		//联系人不能为空
		public static final String CONTACT_NOTNULL = "企业联系人不能为空";
		//联系人电话号码格式不正确
		public static final String CONTACT_MOBILE_INCORRECT = "联系人电话格式不正确";
		//联系人电话号码不能为空
		public static final String CONTACT_MOBILE_NOTNULL = "联系人电话不能为空";


		//验证码不能为空
		public static final String CODE_NOTNULL = "验证码不能为空";
		//验证码格式不正确
		public static final String CODE_INCORRECT = "验证码格式不正确";
		//验证码与验证id不匹配
		public static final String CODE_NO_MATCH = "验证码不匹配";
		//验证码id不能为空
		public static final String SMS_ID_NOTNULL = "验证码id不能为空";
		//验证码id格式不正确
		public static final String SMS_ID_INCORRECT = "验证码id格式不正确";
		//合同不存在
		public static final String CONTRACT_NOTNULL = "合同编号不能为空";
		//合同不存在
		public static final String CONTRACT_NOEXIST = "合同编号不存在";
		//合同编号格式不正确
		public static final String CONTRACT_INCORRECT = "合同编号格式不正确";
		//pdf_width不能为空
		public static final String PDF_WIDTH_NOTNULL = "PDF宽不能为空";
		//pdf_width格式不正确
		public static final String PDF_WIDTH_INCORRECT = "PDF宽格式不正确";
		//pdf_width不能为空
		public static final String PDF_HEIGHT_NOTNULL = "PDF高不能为空";
		//pdf_width格式不正确
		public static final String PDF_HEIGHT_INCORRECT = "PDF高格式不正确";
		//leftzb不能为空
		public static final String LEFTZB_NOTNULL = "签名左边距不能为空";
		//leftzb格式不正确
		public static final String LEFTZB_INCORRECT = "签名左边距格式不正确";
		//leftzb签名左边距超出范围
		public static final String LEFTZB_ULTRA = "签名左边距超出范围";
		//topzb不能为空
		public static final String TOPZB_NOTNULL = "签名上边距不能为空";
		//topzb格式不正确
		public static final String TOPZB_INCORRECT = "签名上边距格式不正确";
		//topzb签名左边距超出范围
		public static final String TOPZB_ULTRA = "签名上边距超出范围";
		//signature上传类型不正确
		public static final String SIGNATURE_NOSTYLE = "签名图片格式不正确";
		//signature不能为空
		public static final String SIGNATURE_NOTNULL = "签章图片不能为空";
		//sign_width不能为空
		public static final String SIGN_WIDTH_NOTNULL = "签章的宽不能为空";
		//sign_width格式不正确
		public static final String SIGN_WIDTH_INCORRECT = "签章的宽格式不正确";
		//sign_height不能为空
		public static final String SIGN_HEIGHT_NOTNULL = "签章的高不能为空";
		//sign_height格式不正确
		public static final String SIGN_HEIGHT_INCORRECT = "签章的高格式不正确";
		//关键字查找签名位置失败
		public static final String KEYWORD_FINDFAILED = "查找关键字签名位置失败";

		//page不能为空
		public static final String PAGE_NOTNULL = "页码不能为空";
		//page格式不正确
		public static final String PAGE_INCORRECT = "页码格式不正确 ";


	}

}
