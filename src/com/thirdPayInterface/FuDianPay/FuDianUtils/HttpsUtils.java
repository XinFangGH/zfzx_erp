package com.thirdPayInterface.FuDianPay.FuDianUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rd.bds.common.property.CommonPro;
import com.rd.bds.sign.util.DecryptUtil;


/**
 * 工具类-Https协议请求
 * 不再使用apache commons HttpClient项目，使用apache HttpClient和HttpCore项目
 * 
 * apache commons HttpClient官网的解释是：
 * <a href="http://hc.apache.org/httpclient-3.x/">
 * The Commons HttpClient project is now end of life, and is no longer being developed. 
 * It has been replaced by the Apache HttpComponents project in its HttpClient and HttpCore modules, 
 * which offer better performance and more flexibility.</a>
 */
@SuppressWarnings("deprecation")
public class HttpsUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpsUtil.class);
	/**
	 * HTTPS协议方案
	 */
	public static final String HTTPS_PROTOCOL_SCHEME = "https";

	/**
	 * HTTPS默认端口号
	 */
	public static final int DEFAULT_PORT_NUMBER_FOR_HTTPS = 443;
	
	
	/**
	 * HttpClient连接超时时间(毫秒)
	 */
	public static final String UFX_CONN_TIME = "ufx_conn_time";
	
	/**
	 * HttpClient数据传输超时时间(毫秒)
	 */
	public static final String UFX_DATA_TRANS = "ufx_data_trans";
	
    /**
     * 请求Https协议地址
     * 
     * 使用HttpClient向HTTPS地址发送POST请求
     * 
     * @param postUrl 地址
     * @param requestParams 请求参数
     * @param charset 字符编码集
     * @param timeout 超时时间
     * @return
     * @throws PayException
     */
    @SuppressWarnings("resource")
    @Deprecated
	public static String postClient(String postUrl,
			Map<String, String> requestParams, String charset, int timeout)
			throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			SSLContext ctx = SSLContext.getInstance(SSLSocketFactory.TLS);
			ctx.init(null, new TrustManager[] { new HttpsX509TrustManager() },
					null);
			SSLSocketFactory sslSocketFactory = new SSLSocketFactory(ctx);

			httpClient
					.getConnectionManager()
					.getSchemeRegistry()
					.register(
							new Scheme(
									HttpsUtils.HTTPS_PROTOCOL_SCHEME,
									sslSocketFactory,
									HttpsUtils.DEFAULT_PORT_NUMBER_FOR_HTTPS));

			HttpPost httpPost = new HttpPost(postUrl);
			//设置请求和传输时长
			Builder builder = RequestConfig.custom();
			builder.setSocketTimeout(5000);
			builder.setConnectTimeout(5000);
			RequestConfig config = builder.build();
			httpPost.setConfig(config);
			//初始化请求参数
			List<NameValuePair> formParams = convert2NameValuePair(requestParams);
			if (!formParams.isEmpty()) {
				httpPost.setEntity(new UrlEncodedFormEntity(formParams, charset));
			}
			//发送请求
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String responseContent = "";
			if (entity != null) {
				responseContent = EntityUtils.toString(entity, charset);
				entity.consumeContent();
			}
			LOGGER.info("HtppsUtil发送请求状态码为："+response.getStatusLine().getStatusCode()+"通知参数:"+responseContent);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && StringUtils.isNotEmpty(responseContent)) {
				return responseContent;
			} else {
				return "{\"retCode\":\"1001\",\"retMsg\":\"提交失败\"}";
			}
		} catch (NoSuchAlgorithmException noSuex) {
			LOGGER.info("请求发送失败，NoSuchAlgorithmException原因：", noSuex);
			return "{\"retCode\":\"1001\",\"retMsg\":\"连接异常\"}";
		} catch (KeyManagementException keyMaEx) {
			LOGGER.info("请求发送失败，KeyManagementException原因：秘钥异常", keyMaEx);
			return "{\"retCode\":\"1001\",\"retMsg\":\"连接异常\"}";
		} catch (UnsupportedEncodingException unsuEx) {
			LOGGER.info("请求发送失败，UnsupportedEncodingException原因：", unsuEx);
			return "{\"retCode\":\"1001\",\"retMsg\":\"连接异常\"}";
		} catch (ClientProtocolException clPrEx) {
			LOGGER.info("请求发送失败，ClientProtocolException原因：", clPrEx);
			return "{\"retCode\":\"1001\",\"retMsg\":\"连接异常\"}";
		}catch(SocketTimeoutException e){
			LOGGER.info("请求发送失败，SocketTimeoutException原因：", e);
			return "{\"retCode\":\"1001\",\"retMsg\":\"连接异常\"}";
		} catch (IOException ioEx) {
			LOGGER.info("请求发送失败，ClientProtocolException原因：", ioEx);
			return "{\"retCode\":\"1001\",\"retMsg\":\"连接异常\"}";
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
    
    /**
     * 使用HttpClient向HTTPS地址发送POST请求
     * 
     * @param postUrl 地址
     * @param requestParams 请求参数
     * @param charset 字符编码集
     * @param timeout 超时时间
     * @return
     * @throws PayException
     */
    public static String doPost(String postUrl,
            Map<String, String> requestParams, String charset, int timeout)
            throws Exception {
        CloseableHttpClient httpClient = null;
        try {
            SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            ctx.init(null, new TrustManager[] { new HttpsX509TrustManager() }, null);
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);

            httpClient = HttpClients.custom().setSSLSocketFactory(sslSocketFactory).build();

            HttpPost httpPost = new HttpPost(postUrl);
            //设置请求和传输时长
            Builder builder = RequestConfig.custom();
            builder.setSocketTimeout(5000);
            builder.setConnectTimeout(5000);
            RequestConfig config = builder.build();
            httpPost.setConfig(config);
            //初始化请求参数
            List<NameValuePair> formParams = convert2NameValuePair(requestParams);
            if (!formParams.isEmpty()) {
                httpPost.setEntity(new UrlEncodedFormEntity(formParams, charset));
            }
            //发送请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseContent = "";
            if (entity != null) {
                responseContent = EntityUtils.toString(entity, charset);
                EntityUtils.consume(entity);
            }
            LOGGER.info("HtppsUtil发送请求状态码为："+response.getStatusLine().getStatusCode()+"通知参数:"+responseContent);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && StringUtils.isNotEmpty(responseContent)) {
            	String returnParams = DecryptUtil.decryptByPrivateKey(responseContent, CommonPro.getDemoCustomPrivateKey());
            	return returnParams;
            } else {
            	return "{\"retCode\":\"1001\",\"retMsg\":\"提交失败\"}";
            }
        } catch (NoSuchAlgorithmException noSuex) {
            LOGGER.info("请求发送失败，NoSuchAlgorithmException原因：", noSuex);
            return "{\"retCode\":\"1001\",\"retMsg\":\"连接异常\"}";
        } catch (KeyManagementException keyMaEx) {
            LOGGER.info("请求发送失败，KeyManagementException原因：秘钥异常", keyMaEx);
            return "{\"retCode\":\"1001\",\"retMsg\":\"连接异常\"}";
        } catch (UnsupportedEncodingException unsuEx) {
            LOGGER.info("请求发送失败，UnsupportedEncodingException原因：", unsuEx);
            return "{\"retCode\":\"1001\",\"retMsg\":\"连接异常\"}";
        } catch (ClientProtocolException clPrEx) {
            LOGGER.info("请求发送失败，ClientProtocolException原因：", clPrEx);
            return "{\"retCode\":\"1001\",\"retMsg\":\"连接异常\"}";
        }catch(SocketTimeoutException e){
            LOGGER.info("请求发送失败，SocketTimeoutException原因：", e);
            return "{\"retCode\":\"1001\",\"retMsg\":\"连接异常\"}";
        } catch (IOException ioEx) {
            LOGGER.info("请求发送失败，ClientProtocolException原因：", ioEx);
            return "{\"retCode\":\"1001\",\"retMsg\":\"连接异常\"}";
        } finally {
            httpClient.close();
        }
    }

    private static List<NameValuePair> convert2NameValuePair(Map<String, String> requestParams) {
        if (requestParams == null || requestParams.isEmpty()) {
            return null;
        }

        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formParams;
    }

	private static class HttpsX509TrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// 不校验服务器端证书，什么都不做，视为通过检查
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// 不校验服务器端证书，什么都不做，视为通过检查
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}
	
	/**
	 * GET请求Https协议地址
	 * @param postUrl
	 * @param requestParams
	 * @param charset
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	@Deprecated
	public static String getClient(String postUrl, String charset, int timeout) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			SSLContext ctx = SSLContext.getInstance(SSLSocketFactory.TLS);
			ctx.init(null, new TrustManager[] { new HttpsX509TrustManager() },
					null);
			SSLSocketFactory sslSocketFactory = new SSLSocketFactory(ctx);

			httpClient
					.getConnectionManager()
					.getSchemeRegistry()
					.register(
							new Scheme(
									HttpsUtils.HTTPS_PROTOCOL_SCHEME,
									sslSocketFactory,
									HttpsUtils.DEFAULT_PORT_NUMBER_FOR_HTTPS));

			HttpGet httpGet = new HttpGet(postUrl);

			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			String responseContent = "";
			if (entity != null) {
				responseContent = EntityUtils.toString(entity, charset);
				entity.consumeContent();
			}

			return responseContent;
		} catch (NoSuchAlgorithmException noSuex) {
		} catch (KeyManagementException keyMaEx) {
		} catch (UnsupportedEncodingException unsuEx) {
		} catch (ClientProtocolException clPrEx) {
		} catch (IOException ioEx) {
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}
	
	/**
     * GET请求Https协议地址
     * @param postUrl
     * @param requestParams
     * @param charset
     * @param timeout
     * @return
     * @throws Exception
     */
	public static String doGet(String postUrl, String charset, int timeout) throws Exception {
	    CloseableHttpClient httpClient = null;
        try {
            SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            ctx.init(null, new TrustManager[] { new HttpsX509TrustManager() }, null);
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);

            httpClient = HttpClients.custom().setSSLSocketFactory(sslSocketFactory).build();

            HttpGet httpGet = new HttpGet(postUrl);

            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            String responseContent = "";
            if (entity != null) {
                responseContent = EntityUtils.toString(entity, charset);
                EntityUtils.consume(entity);
            }

            return responseContent;
        } catch (NoSuchAlgorithmException noSuex) {
        } catch (KeyManagementException keyMaEx) {
        } catch (UnsupportedEncodingException unsuEx) {
        } catch (ClientProtocolException clPrEx) {
        } catch (IOException ioEx) {
        } finally {
            httpClient.close();
        }
        return null;
    }
}
