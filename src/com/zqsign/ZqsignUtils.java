package com.zqsign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zqsign.method.dependency.Base64Utils;
import com.zqsign.method.dependency.EncryptData;
import com.zqsign.method.dependency.HttpRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.xml.crypto.Data;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ZqsignUtils {
    public static final String private_key = ZqsignManage.Key.PRIVATE_KEY; //私钥
    public static final String zqid = ZqsignManage.Zqid.ZQID; //账号id

    public static final String request_url = ZqsignManage.Url.REQUEST_URL;  //请求方法地址 后面需要加上方法名

    public static String pdfId;

    /**
     * 个人用户颁发数字证书
     *
     * @auther: XinFang
     * @date: 2018/5/15 15:51
     */
    public static Map personReg(BpCustMember bpCustMember) {

        Map<String, String> map = basePerson(bpCustMember);


        Map data = baseSend(map, "personReg");
        return data;
    }

    /**
     * 个人用户颁发数字证书(无校验)
     *
     * @auther: XinFang
     * @date: 2018/5/15 15:51
     */
    public static Map personRegNV(BpCustMember bpCustMember) {
        Map<String, String> map = basePerson(bpCustMember);

        Map data = baseSend(map, "personRegNV");

        return data;
    }

    /**
     * 企业用户颁发数字证书
     *
     * @auther: XinFang
     * @date: 2018/5/15 16:32
     */
    public static Map entpReg(Enterprise e) {
        Map<String, String> map = new HashMap<String, String>();

        map.put("user_code", e.getUserCode());//用户唯一标示，该值不能重复
        map.put("name", e.getEnterprisename());//企业名称
        map.put("certificate", e.getOrganizecode());//营业执照号或社会统一代码
        map.put("address", e.getFactaddress()); //企业注册地址
        map.put("contact", e.getLinkman());//联系人
        map.put("mobile", e.getTelephone());//联系电话（手机号码）

        Map data = baseSend(map, "entpReg");
        return data;
    }

    /**
     * 个人用户更新数字证书
     *
     * @auther: XinFang
     * @date: 2018/5/15 16:50
     */

    public static Map personUpdate(BpCustMember bpCustMember) {

        Map<String, String> map = basePerson(bpCustMember);
        Map data = baseSend(map, "personUpdate");
        return data;
    }

    /**
     * 个人用户更新数字证书(无校验)
     *
     * @auther: XinFang
     * @date: 2018/5/15 16:52
     */
    public static Map personUpdateNV(BpCustMember bpCustMember) {

        Map<String, String> map = basePerson(bpCustMember);

        Map data = baseSend(map, "personUpdateNV");
        return data;
    }

    /**
     * 企业用户更新数字证书
     *
     * @auther: XinFang
     * @date: 2018/5/15 16:53
     */
    public static Map entpUpdate(BpCustMember bpCustMember) {

        Map<String, String> map = basePerson(bpCustMember);

        Map data = baseSend(map, "entpUpdate");
        return data;
    }

    /**
     * 上传PDF文件创建合同
     *
     * @auther: XinFang
     * @date: 2018/5/15 17:01
     */
    public static Map uploadPdf(String pdfUrl,BpCustMember bp) {


        Map<String, String> map = new HashMap<String, String>();

        byte[] fileToByte = new byte[0];//转义字符
        String encode = null;
        try {
            fileToByte = Base64Utils.fileToByte(pdfUrl);
            encode = Base64Utils.encode(fileToByte);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        pdfId = bp.getId()+"_"+bp.getLoginname()+"_"+  format.format(new Date())+"_"+RandomStringUtils.randomNumeric(4);
        map.put("no",pdfId);//自行创建合同编号，该值不可重复使用
        map.put("name", "升升投合同_"+bp.getTruename());//合同名称
        map.put("contract", encode);//合同文件的base64/平台方合同文件的网络地址
        Map data = baseSend(map, "uploadPdf");
        return data;
    }

    /**
     *企业上传PDF文件创建合同
     *
     * @auther: XinFang
     * @date: 2018/6/21 14:54
     */
    public static Map uploadPdfEnterprise(String pdfUrl,Enterprise en) {


        Map<String, String> map = new HashMap<String, String>();

        byte[] fileToByte = new byte[0];//转义字符
        String encode = null;
        try {
            fileToByte = Base64Utils.fileToByte(pdfUrl);
            encode = Base64Utils.encode(fileToByte);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        pdfId = en.getId()+"_"+en.getOrganizecode()+"_"+  format.format(new Date())+"_"+RandomStringUtils.randomNumeric(4);
        map.put("no",pdfId);//自行创建合同编号，该值不可重复使用
        map.put("name", "升升投合同_"+en.getEnterprisename());//合同名称
        map.put("contract", encode);//合同文件的base64/平台方合同文件的网络地址
        Map data = baseSend(map, "uploadPdf");
        return data;
    }

    /**
     * 模板创建合同
     *
     * @auther: XinFang
     * @date: 2018/5/15 17:07
     */
    public static Map pdfTemplate() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("no", UUID.randomUUID().toString());//自行创建合同编号，该值不可重复使用
        map.put("name", "需要用户修改");//合同名称
        map.put("t_no", "需要用户修改");//模板编号
        map.put("contract_val", "需要用户修改");//表单的json串

        Map data = baseSend(map, "pdfTemplate");
        return data;
    }

    /**
     * 后台自动签署
     *
     * @auther: XinFang
     * @date: 2018/5/15 17:09
     */
    public static Map signAuto(BpCustMember bp,Enterprise e) {

        Map<String, String> map = new HashMap<String, String>();

        map.put("no", pdfId);//已存在的合同编号
        map.put("signers",bp.getUserCode()+",SST_TJHW,SST_LZL,"+e.getUserCode());//签署人列表

        Map data = baseSend(map, "signAuto");
        return data;
    }

    /**
     * 后台自动签署
     *
     * @auther: XinFang
     * @date: 2018/5/15 17:09
     */
    public static Map signAutoNew(BpCustMember bp,Enterprise e) {

        Map<String, String> map = new HashMap<String, String>();

        map.put("no", pdfId);//已存在的合同编号
        map.put("signers",bp.getUserCode()+",ZFZX,"+e.getUserCode());//签署人列表

        Map data = baseSend(map, "signAuto");
        return data;
    }
    /**
     *企业后台自动签署
     *
     * @auther: XinFang
     * @date: 2018/6/21 14:53
     */

    public static Map signAutoEnterprise(Enterprise e) {

        Map<String, String> map = new HashMap<String, String>();

        map.put("no", pdfId);//已存在的合同编号
        map.put("signers","ZFZX,"+e.getUserCode());//签署人列表

        Map data = baseSend(map, "signAuto");
        return data;
    }
    /**
     * 后台无签章自动签署
     *
     * @auther: XinFang
     * @date: 2018/5/15 17:09
     */
    public static Map signAutoNI() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("no", "需要用户修改");//已存在的合同编号
        map.put("signers", "需要用户修改");//签署人列表


        Map data = baseSend(map, "signAutoNI");
        return data;
    }

    /**
     * 骑缝章签署
     *
     * @auther: XinFang
     * @date: 2018/5/15 17:09
     */
    public static Map signCheckMark() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("no", "需要用户修改");//已存在的合同编号
        map.put("signers", "需要用户修改");//签署人列表

        Map data = baseSend(map, "signCheckMark");
        return data;
    }

    /**
     * 显示页面签署
     *
     * @auther: XinFang
     * @date: 2018/5/15 17:09
     */
    public static Map signView() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("no", "需要用户修改");//已存在的合同编号
        map.put("user_code", "需要用户修改");//签署人的user_code
        map.put("sign_type", "SIGNATURE");//签章不验证签署
        map.put("return_url", "需要用户修改");//同步回调地址
        map.put("notify_url", "需要用户修改");//异步回调地址

        Map data = baseSend(map, "signView");
        return data;
    }


    /**
     * 获取合同图片地址
     *
     * @auther: XinFang
     * @date: 2018/5/15 17:09
     */
    public static List getImg() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("no", pdfId);//已存在的合同编号

        Map data = baseSend(map, "getImg");
        return (List) data.get("imgList");
    }


    /**
     * 获取合同PDF地址
     *
     * @auther: XinFang
     * @date: 2018/5/15 17:09
     */
    public static Map getPdfUrl() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("no", pdfId);//已存在的合同编号

        Map data = baseSend(map, "getPdfUrl");
        return data;
    }

    /**
     * 获取合同文件流
     *
     * @auther: XinFang
     * @date: 2018/5/15 17:09
     */
    public static Map getPdf() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("no", "需要用户修改");//已存在的合同编号
        Map data = baseSend(map, "getPdf");
        return data;
    }

    /**
     * 合同撤销
     *
     * @auther: XinFang
     * @date: 2018/5/15 17:09
     */
    public static Map undoSign() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("no", "需要用户修改");//已存在的合同编号

        Map data = baseSend(map, "undoSign");
        return data;
    }

    /**
     * 合同生效标记(必调接口)
     *
     * @auther: XinFang
     * @date: 2018/5/15 17:09
     */
    public static Map completionContract() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("no", pdfId);//已存在的合同编号

        Map data = baseSend(map, "completionContract");
        return data;
    }


    /**
     * 抽取个人公共方法
     *
     * @auther: XinFang
     * @date: 2018/5/15 16:27
     *
     *
     */
    private static Map<String, String> basePerson(BpCustMember bpCustMember) {

        Map<String, String> map = new HashMap<String, String>();

        map.put("user_code", bpCustMember.getUserCode());//用户唯一标示，该值不能重复
        map.put("name", bpCustMember.getTruename());//平台
        // 方用户姓名
        map.put("id_card_no", bpCustMember.getCardcode());//身份证号
        map.put("mobile", bpCustMember.getTelphone());//联系电话（手机号码）
        System.out.println("user_code:"+ bpCustMember.getUserCode());
        return map;
    }

    /**
     * 抽取公共方法
     *
     * @auther: XinFang
     * @date: 2018/5/15 17:16
     */
    private static Map<String, Object> baseSend(Map map, String methodUrl) {

        EncryptData ed = new EncryptData();

        map.put("zqid", zqid);//商户的zqid,该值需要与private_key对应
        String sign_val = ed.encrptData(map, private_key);//对请求进行签名加密
        map.put("sign_val", sign_val); //请求参数的签名值
        System.out.println(methodUrl+"请求内容：" + map);//请求内容数据
        String response_str = null;//向服务端发送请求，并接收请求结果
        try {
            response_str = HttpRequest.sendPost(request_url + methodUrl, map);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(methodUrl+"请求结果：" + response_str);//输出服务器响应结果
        JSONObject resData = JSON.parseObject(response_str);

        return resData;
    }


}
