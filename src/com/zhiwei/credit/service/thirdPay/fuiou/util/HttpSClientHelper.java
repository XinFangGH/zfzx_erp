package com.zhiwei.credit.service.thirdPay.fuiou.util;

import java.io.*;
import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

public class HttpSClientHelper {
private static class TrustAnyTrustManager implements X509TrustManager {

   public void checkClientTrusted(X509Certificate[] chain, String authType)
     throws CertificateException {
   }

   public void checkServerTrusted(X509Certificate[] chain, String authType)
     throws CertificateException {
   }

   public X509Certificate[] getAcceptedIssuers() {
    return new X509Certificate[] {};
   }
}

private static class TrustAnyHostnameVerifier implements HostnameVerifier {
   public boolean verify(String hostname, SSLSession session) {
    return true;
   }
}

public static void main(String[] args) throws Exception {
   InputStream in = null;
   OutputStream out = null;
   byte[] buffer = new byte[4096];
   String str_return = "";
   try {
    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
      new java.security.SecureRandom());
    URL console = new URL(
      "https://fht.fuiou.com/req.do");
    HttpsURLConnection conn = (HttpsURLConnection) console
      .openConnection();
    conn.setSSLSocketFactory(sc.getSocketFactory());
    conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
    conn.connect();
    PrintWriter printWriter = null;
    try{
        printWriter = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
    }catch(UnsupportedEncodingException e){
        e.printStackTrace();
    }catch(IOException e){
        e.printStackTrace();
    }  
    printWriter.write("xml=%3C%3Fxml+version%3D%221.0%22+encoding%3D%22UTF-8%22+standalone%3D%22yes%22%3F%3E%3Cqrytransreq%3E%3Cver%3E1.0%3C%2Fver%3E%3Cbusicd%3EAC01%3C%2Fbusicd%3E%3Corderno%3E%3C%2Forderno%3E%3Cstartdt%3E%3C%2Fstartdt%3E%3Cenddt%3E%3C%2Fenddt%3E%3Ctransst%3E%3C%2Ftransst%3E%3C%2Fqrytransreq%3E&merid=0004210F0040026&reqtype=qrytransreq&mac=01d8a1591dfe6f98c41e6cddf585047d");
    printWriter.flush();
    InputStream is = conn.getInputStream();
    DataInputStream indata = new DataInputStream(is);
    String ret = "";

    while (ret != null) {
     ret = indata.readLine();
     if (ret != null && !ret.trim().equals("")) {
      str_return = str_return
        + new String(ret.getBytes("ISO-8859-1"), "UTF-8");
     }
    }
    System.out.println(str_return);
    conn.disconnect();
   } catch (ConnectException e) {
    System.out.println("ConnectException");
    System.out.println(e);
    throw e;

   } catch (IOException e) {
    System.out.println("IOException");
    System.out.println(e);
    throw e;

   } finally {
    try {
     in.close();
    } catch (Exception e) {
    }
    try {
     out.close();
    } catch (Exception e) {
    }
   }
   System.out.println(str_return);
}
}

