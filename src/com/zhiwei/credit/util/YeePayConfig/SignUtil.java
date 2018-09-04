/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.zhiwei.credit.util.YeePayConfig;

import com.cfca.util.pki.PKIException;
import com.cfca.util.pki.cert.X509Cert;
import com.cfca.util.pki.cipher.JKey;


public class SignUtil {

	public static String sign(String sourceMessage, String file, String password) {

		JKey privateKey;
		X509Cert publicKey;
		try {
			privateKey = CFCACertSignUtils.getPrivaeKey(file, password);
			publicKey = CFCACertSignUtils.getPublicKey(file, password);
		} catch (PKIException e) {
			throw new RuntimeException("签名失败", e);
		}

		String signMsg = CFCACertSignUtils.sign(sourceMessage, privateKey,
				new X509Cert[] { publicKey }, "UTF-8");
		return signMsg;

	}

	public static boolean verifySign(String sourceMessage, String signMsg,
			String merchantNo) {
		return CFCACertSignUtils.verifySign(sourceMessage, signMsg,
				merchantNo);
	}

}
