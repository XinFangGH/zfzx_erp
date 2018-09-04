/**
 * Copyright: Copyright (c)2013
 * Company: PTU
 */
package com.zhiwei.credit.util.YeePayConfig;

import java.io.UnsupportedEncodingException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.cfca.util.pki.PKIException;
import com.cfca.util.pki.api.CertUtil;
import com.cfca.util.pki.api.KeyUtil;
import com.cfca.util.pki.api.SignatureUtil;
import com.cfca.util.pki.cert.X509Cert;
import com.cfca.util.pki.cipher.JCrypto;
import com.cfca.util.pki.cipher.JKey;
import com.cfca.util.pki.cipher.Session;
import com.cfca.util.pki.extension.SelfDefExtension;

/**
 * <pre>
 * CFCA证书工具类
 * help:http://www.360doc.com/content/13/0831/06/11482448_311087429.shtml
 * 
 * @author：陈志鹏
 * @since：2013-11-7 下午2:09:50
 * @version:1.0
 * </pre>
 */
public class CFCACertSignUtils {

	private static Session session = null;
	private static final String ALGORITHM = SignatureUtil.SHA1_RSA;
	public static final String DEFAULT_CHARSET = "UTF-8";
	public static String lock = "LOCK";
	
	public static final String YEEPAY_IDENTITY = "OU=ra.yeepay.com";
	
	public static final String CERT_EXT_INFO = new String("1.2.86.1");

	static {
		try {
			Security.addProvider(new BouncyCastleProvider());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized static void init() {
		if (session != null) {
			return;
		}
		try {
			JCrypto jcrypto = JCrypto.getInstance();
			jcrypto.initialize(JCrypto.JSOFT_LIB, null);
			session = jcrypto.openSession(JCrypto.JSOFT_LIB);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取证书的私钥对象信息
	 * 
	 * @param certPath
	 *            CFCA pfx格式证书文件路径
	 * @param certPswd
	 *            CFCA证书密码
	 * @return
	 */
	public static JKey getPrivaeKey(String certPath, String certPswd)
			throws PKIException {
		return KeyUtil.getPriKey(certPath, certPswd);
	}

	/**
	 * 获取证书的公钥对象信息
	 * 
	 * @param certPath
	 *            CFCA pfx格式证书文件路径
	 * @param certPswd
	 *            CFCA证书密码
	 * @return
	 */
	public static X509Cert getPublicKey(String certPath, String certPswd)
			throws PKIException {
		return CertUtil.getCert(certPath, certPswd);
	}

	/**
	 * CFCA非分离式PKCS#7签名--验签需要对应非分离式验签方法verifySignMessageP7
	 * 
	 * @param sourceMessage
	 *            源消息
	 * @param privateKey
	 *            私钥
	 * @param publicKey
	 *            公钥
	 * @return
	 */
	public static String sign(String sourceMessage, JKey privateKey,
			X509Cert[] publicKey, String charset) {
		if (session == null) {
			init();
		}

		try {
			String yphs = Digest.hmacSign(sourceMessage, charset);
			SignatureUtil signUtil = new SignatureUtil();
			byte[] input = null;
			if (charset == null) {
				input = yphs.getBytes();
			} else {
				input = yphs.getBytes(charset);
			}
			// 对消息签名
			byte[] b64SignData;
			b64SignData = signUtil.p7SignMessage(true, input, ALGORITHM,
					privateKey, publicKey, session);
			String signMessage = new String(b64SignData, DEFAULT_CHARSET);

			return signMessage;
		} catch (Exception e) {
			throw new RuntimeException("签名失败!", e);
		}
	}

	/**
	 * <pre>
	 * 验证签名的合法性 验证商户的CFCA非分离式PKCS#7签名 (验证签名信息的完整性和不可抵赖性)
	 * 
	 * @param sourceMessage
	 *            商户原始交易数据
	 * @param signMessage
	 *            CFCA签名结果(Base64编码)以UTF-8编码成的字符串
	 * @param customerNo
	 *            客户号（验证当前证书是否是授予该客户的证书）
	 * @return X509Cert[] 验签通过后返回签名证书的公钥信息
	 * @throws UnsupportedEncodingException
	 * @throws PKIException
	 * </pre>
	 */
	public static boolean verifySign(String sourceMessage, String signMessage,
			String customerNo) {

		if (customerNo == null) {
			throw new IllegalArgumentException("当前业务客户号未指定");
		}

		if (session == null) {
			init();
		}

		try {

			SignatureUtil signUtil = new SignatureUtil();
			// 对原始交易数据做hash摘要
			String digestMsg = Digest.hmacSign(sourceMessage, DEFAULT_CHARSET);
			byte signData[] = signMessage.getBytes(DEFAULT_CHARSET);// 再以UTF-8编码方式解码成字节数组

			boolean verify = signUtil.p7VerifySignMessage(signData, session);// 1.验证签名的不可抵赖性

			if (verify) {// 签名
				// 获得签名中的证书
				X509Cert x509Certs = signUtil.getSigerCert()[0];

				// 获得签名数据中的原文
				byte[] srcData = signUtil.getSignedContent();// 原始hash值的BASE64编码
				String reverseHashMessage = new String(srcData, DEFAULT_CHARSET);

				// 证书所有者身份校验
				identityVerify(customerNo, x509Certs);

				if (reverseHashMessage.equals(digestMsg)) {// 2.原始数据得到的摘要和验签得到的原始摘要做比较验证数据的完整性
					return true;
				} else {
					throw new RuntimeException("消息摘要信息不一致，信息可能被篡改!");
				}
			} else {
				throw new RuntimeException("验签失败");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 证书所有者身份校验
	private static void identityVerify(String customerNo, X509Cert x509Certs)
			throws PKIException {
		String certDN = x509Certs.getSubject();

		boolean isValidecertDN = false;
		String certDNItems[] = certDN.split(",");
		for (String item : certDNItems) {
			if (item.equals(YEEPAY_IDENTITY)) {
				isValidecertDN = true;
			}
		}

		if (!isValidecertDN) {
			throw new RuntimeException("不是yeepay颁发的CFCA证书");
		}

		String extension = null;
		SelfDefExtension extensionInfo = x509Certs
				.getSelfDefExtension(CERT_EXT_INFO);
		if (extensionInfo == null) {
			throw new RuntimeException("证书扩展信息未指定,无法识别客户身份信息");
		}
		extension = extensionInfo.getExtensionValue();
		if (extension == null) {
			throw new RuntimeException("证书扩展信息未指定,无法识别客户身份信息");
		}

		if (!customerNo.equals(extension)) {
			throw new RuntimeException("当前证书不是颁发给客户[" + customerNo + "]的证书!");
		}
	}
}
