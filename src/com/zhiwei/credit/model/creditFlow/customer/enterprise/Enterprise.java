package com.zhiwei.credit.model.creditFlow.customer.enterprise;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.sdicons.json.validator.impl.predicates.Str;
import com.zhiwei.core.model.BaseModel;

/**
 * CsEnterprise entity. @author MyEclipse Persistence Tools
 */

public class Enterprise extends BaseModel implements  java.io.Serializable {

	// Fields

	public Long getDocumentType() {
		return documentType;
	}

	public void setDocumentType(Long documentType) {
		this.documentType = documentType;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getSelfemail() {
		return selfemail;
	}

	public void setSelfemail(String selfemail) {
		this.selfemail = selfemail;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer controlpersonid;    //控制人
	private Integer legalpersonid;     //法人
	private Integer linkmampersonid;   //企业联系人
	private String enterprisename;    //企业名称
	private String shortname;         //企业简称
	private String organizecode;     //组织机构代码
	private String ownership;            //所有制性质
	private String tradetype;            //行业类别
	private String cciaa;               //营业执照号码
	private String managescope;          //核准经营范围
	private String capitalkind;          //注册资金币种
	private Double registermoney;         //工商注册资金(万)
	private String address;              //工商注册地址
	private String factaddress;          //实际经营地址
	private String managecity;            //?经营所在地城市                   //参照 多级
	private String managecityName;
	private String gslname;              //工商登记机关                          //参照 多级
	private Boolean gslexamine;          //工商是否按时年检
	private java.util.Date gslexaminedate;    //工商下一年年检时间
	private String taxname;               //国税机关名称
	private String taxnum;                //国税登记证号码
	private Boolean taxexamine;           //国税是否按时年检
	private String website;               //企业网址
	private String changer;               //?
	private java.util.Date changedate;         //?
	private String lkmduty;               //?
	private java.util.Date taxexaminedate;       //?  国税下一年年检时间
	private String enjoytax;                //? 1 享受国税政策
	private String dstaxname;              //?  地税机关名称
	private String dstaxnum;               //?  地税登记证号码
	private Boolean dstaxexamine;           //?  地税是否按时年检
	private java.util.Date dsexaminedate;        //?  地税下一年年检时间
	private String enjoyds;                 //?  享受地税政策
	private String creater;                 //创建人
	private java.util.Date createdate;            //创建时间

	private String telephone ;               //联系电话
	private String fax ;                     //传真
	private String receiveMail ;             //收件人
	private java.util.Date registerstartdate;       //营业执照起始日期
	private java.util.Date registerenddate ;    //营业执照截止日期 
    private String creditaccountbum ;     //  企业贷款卡号码
    private String area;                 // 企业通信地址
    private java.util.Date opendate ;         //企业成立日期
    private Integer employeetotal ; // 职工人数
    private String  postcoding ; //邮政编码
    private String  email;
    private Long createrId;
    private String belongedId;//企业客户所有人
    private String belongedName;

    private Integer enterpriseGsdjzId;
    private String enterpriseGsdjzURL;
    private String enterpriseGsdjzExtendName;
    
    private Integer enterpriseYyzzId;
    private String enterpriseYyzzURL;
    private String enterpriseYyzzExtendName;
    
    private Integer enterpriseZzjgId;
    private String enterpriseZzjgURL;
    private String enterpriseZzjgExtendName;
    
    private Integer enterpriseDsdjId;
    private String enterpriseDsdjURL;
    private String enterpriseDsdjExtendName;

    //新增图片上传 黄
	private Integer enterpriseXztpyId;
	private String enterpriseXztpyURL;
	private String enterpriseXztpyExtendName;

	private Integer enterpriseXztpeId;
	private String enterpriseXztpeURL;
	private String enterpriseXztpeExtendName;

	private Integer enterpriseXztpsId;
	private String enterpriseXztpsURL;
	private String enterpriseXztpsExtendName;

	private Integer enterpriseXztpssId;
	private String enterpriseXztpssURL;
	private String enterpriseXztpssExtendName;

	//新增图片名称  XF
	private  String yyzz;
	private  String zzjg;
	private  String swzj;
	private  String dkk;
	private  String xzmc1;
	private  String xzmc2;
	private  String xzmc3;
	private  String xzmc4;




	public String getZzjg() {
		return zzjg;
	}

	public void setZzjg(String zzjg) {
		this.zzjg = zzjg;
	}

	public String getSwzj() {
		return swzj;
	}

	public void setSwzj(String swzj) {
		this.swzj = swzj;
	}

	public String getDkk() {
		return dkk;
	}

	public void setDkk(String dkk) {
		this.dkk = dkk;
	}

	public String getXzmc1() {
		return xzmc1;
	}

	public void setXzmc1(String xzmc1) {
		this.xzmc1 = xzmc1;
	}

	public String getXzmc2() {
		return xzmc2;
	}

	public void setXzmc2(String xzmc2) {
		this.xzmc2 = xzmc2;
	}

	public String getXzmc3() {
		return xzmc3;
	}

	public void setXzmc3(String xzmc3) {
		this.xzmc3 = xzmc3;
	}

	public String getXzmc4() {
		return xzmc4;
	}

	public void setXzmc4(String xzmc4) {
		this.xzmc4 = xzmc4;
	}

	public String getYyzz() {
		return yyzz;
	}

	public void setYyzz(String yyzz) {
		this.yyzz = yyzz;
	}

	public Integer getEnterpriseXztpyId() {
		return enterpriseXztpyId;
	}

	public void setEnterpriseXztpyId(Integer enterpriseXztpyId) {
		this.enterpriseXztpyId = enterpriseXztpyId;
	}

	public String getEnterpriseXztpyURL() {
		return enterpriseXztpyURL;
	}

	public void setEnterpriseXztpyURL(String enterpriseXztpyURL) {
		this.enterpriseXztpyURL = enterpriseXztpyURL;
	}

	public String getEnterpriseXztpyExtendName() {
		return enterpriseXztpyExtendName;
	}

	public void setEnterpriseXztpyExtendName(String enterpriseXztpyExtendName) {
		this.enterpriseXztpyExtendName = enterpriseXztpyExtendName;
	}

	public Integer getEnterpriseXztpeId() {
		return enterpriseXztpeId;
	}

	public void setEnterpriseXztpeId(Integer enterpriseXztpeId) {
		this.enterpriseXztpeId = enterpriseXztpeId;
	}

	public String getEnterpriseXztpeURL() {
		return enterpriseXztpeURL;
	}

	public void setEnterpriseXztpeURL(String enterpriseXztpeURL) {
		this.enterpriseXztpeURL = enterpriseXztpeURL;
	}

	public String getEnterpriseXztpeExtendName() {
		return enterpriseXztpeExtendName;
	}

	public void setEnterpriseXztpeExtendName(String enterpriseXztpeExtendName) {
		this.enterpriseXztpeExtendName = enterpriseXztpeExtendName;
	}

	public Integer getEnterpriseXztpsId() {
		return enterpriseXztpsId;
	}

	public void setEnterpriseXztpsId(Integer enterpriseXztpsId) {
		this.enterpriseXztpsId = enterpriseXztpsId;
	}

	public String getEnterpriseXztpsURL() {
		return enterpriseXztpsURL;
	}

	public void setEnterpriseXztpsURL(String enterpriseXztpsURL) {
		this.enterpriseXztpsURL = enterpriseXztpsURL;
	}

	public String getEnterpriseXztpsExtendName() {
		return enterpriseXztpsExtendName;
	}

	public void setEnterpriseXztpsExtendName(String enterpriseXztpsExtendName) {
		this.enterpriseXztpsExtendName = enterpriseXztpsExtendName;
	}

	public Integer getEnterpriseXztpssId() {
		return enterpriseXztpssId;
	}

	public void setEnterpriseXztpssId(Integer enterpriseXztpssId) {
		this.enterpriseXztpssId = enterpriseXztpssId;
	}

	public String getEnterpriseXztpssURL() {
		return enterpriseXztpssURL;
	}

	public void setEnterpriseXztpssURL(String enterpriseXztpssURL) {
		this.enterpriseXztpssURL = enterpriseXztpssURL;
	}

	public String getEnterpriseXztpssExtendName() {
		return enterpriseXztpssExtendName;
	}

	public void setEnterpriseXztpssExtendName(String enterpriseXztpssExtendName) {
		this.enterpriseXztpssExtendName = enterpriseXztpssExtendName;
	}

	private String customerLevel;//客户级别
    private Boolean isBlack;//是否是黑名单
    private String blackReason;//列入黑名单的原因
    private Short jyplace;//经营场所
    private BigDecimal areaMeasure;//面积
    private BigDecimal rent;//租金
    private Boolean isLicense;//是否有进/出口许可证
	private Integer hangyeType;
    private String hangyeName;
    private Integer headerId;
    private Integer rootHangYeType;//针对行业分类贷款统计报表而新添加字段
    private String linkman;//企业联系人名称
    private String legalpersonName;//法人姓名
    private String revIds; //关联企业
    private String enterId;//
    protected Long shopId;//门店Id
	protected String shopName;//门店名称
	
	protected String orgName;
	protected String hangyeTypeValue;
	
	protected Long documentType;//证件类型 ：三证合一   ，三证分开
	//private java.util.Date gsexaminedate;
	//private String enjoygs;

	private String userCode; 		//众签签章user_code

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	// Constructors
    protected Set<BpCustEntLawsuit> bpCustEntLawsuitlist=new HashSet<BpCustEntLawsuit>();
    protected Set<BpCustEntCashflowAndSaleIncome> bpCustEntCashflowAndSaleIncomelist=new HashSet<BpCustEntCashflowAndSaleIncome>();
    protected Set<BpCustEntUpanddownstream> bpCustEntUpanddownstreamlist=new HashSet<BpCustEntUpanddownstream>();
    protected Set<BpCustEntUpanddowncontract> bpCustEntUpanddowncontractlist=new HashSet<BpCustEntUpanddowncontract>();
    protected Set<BpCustEntPaytax> bpCustEntPaytaxlist=new HashSet<BpCustEntPaytax>();


    //不与数据库映射字段
    protected String cardnumber;//法人证件号码
    protected String selfemail;//法人邮箱
    protected String cellphone;//法人手机号
    private String isCheckCard;//身份证是否通过验证
	/**
	 * 招标中金额
	 */
	private BigDecimal tenderingMoney;
	/**
	 * 还款中金额
	 */
	private BigDecimal repayingMoney;
	/**
	 * 总借款金额
	 */
	private BigDecimal totalMoney;
	/**
	 *  可用额度
	 */
	private BigDecimal surplusMoney;
	/**
	 *  法人可用额度
	 */
	private BigDecimal linkManSurplusMoney;

	public String getHangyeTypeValue() {
		return hangyeTypeValue;
	}

	public void setHangyeTypeValue(String hangyeTypeValue) {
		this.hangyeTypeValue = hangyeTypeValue;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getEnterId() {
		return enterId;
	}

	public void setEnterId(String enterId) {
		this.enterId = enterId;
	}

	public String getRevIds() {
		return revIds;
	}

	public void setRevIds(String revIds) {
		this.revIds = revIds;
	}

	public Set<BpCustEntUpanddowncontract> getBpCustEntUpanddowncontractlist() {
		return bpCustEntUpanddowncontractlist;
	}

	public void setBpCustEntUpanddowncontractlist(
			Set<BpCustEntUpanddowncontract> bpCustEntUpanddowncontractlist) {
		this.bpCustEntUpanddowncontractlist = bpCustEntUpanddowncontractlist;
	}

	public BigDecimal getLinkManSurplusMoney() {
		return linkManSurplusMoney;
	}

	public void setLinkManSurplusMoney(BigDecimal linkManSurplusMoney) {
		this.linkManSurplusMoney = linkManSurplusMoney;
	}

	public Set<BpCustEntPaytax> getBpCustEntPaytaxlist() {
		return bpCustEntPaytaxlist;
	}

	public void setBpCustEntPaytaxlist(Set<BpCustEntPaytax> bpCustEntPaytaxlist) {
		this.bpCustEntPaytaxlist = bpCustEntPaytaxlist;
	}

	public Set<BpCustEntUpanddownstream> getBpCustEntUpanddownstreamlist() {
		return bpCustEntUpanddownstreamlist;
	}

	public void setBpCustEntUpanddownstreamlist(
			Set<BpCustEntUpanddownstream> bpCustEntUpanddownstreamlist) {
		this.bpCustEntUpanddownstreamlist = bpCustEntUpanddownstreamlist;
	}

	public Set<BpCustEntLawsuit> getBpCustEntLawsuitlist() {
		return bpCustEntLawsuitlist;
	}

	public void setBpCustEntLawsuitlist(Set<BpCustEntLawsuit> bpCustEntLawsuitlist) {
		this.bpCustEntLawsuitlist = bpCustEntLawsuitlist;
	}

	public Set<BpCustEntCashflowAndSaleIncome> getBpCustEntCashflowAndSaleIncomelist() {
		return bpCustEntCashflowAndSaleIncomelist;
	}

	public void setBpCustEntCashflowAndSaleIncomelist(
			Set<BpCustEntCashflowAndSaleIncome> bpCustEntCashflowAndSaleIncomelist) {
		this.bpCustEntCashflowAndSaleIncomelist = bpCustEntCashflowAndSaleIncomelist;
	}

	public Integer getRootHangYeType() {
		return rootHangYeType;
	}

	public String getLegalpersonName() {
		return legalpersonName;
	}

	public void setLegalpersonName(String legalpersonName) {
		this.legalpersonName = legalpersonName;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public void setRootHangYeType(Integer rootHangYeType) {
		this.rootHangYeType = rootHangYeType;
	}

	/** default constructor */
	public Enterprise() {
	}

	/** full constructor */
	public Enterprise(Integer controlpersonid, Integer legalpersonid,
			Integer linkmampersonid, String enterprisename, String shortname,
			String organizecode, String ownership, String tradetype,
			String area, String cciaa, String managescope,
			String capitalkind, Double registermoney,
			String address, String factaddress, String managecity,
			String gslname, Boolean gslexamine, java.util.Date gslexaminedate,
			String taxname, String taxnum, Boolean taxexamine, String website,
			String changer, java.util.Date changedate, String lkmduty,
			java.util.Date taxexaminedate, String enjoytax, String dstaxname,
			String dstaxnum, Boolean dstaxexamine, java.util.Date dsexaminedate,
			String enjoyds, String creater, java.util.Date createdate,
			String telephone ,String fax,String receiveMail,
			java.util.Date registerstartdate,java.util.Date registerenddate,String creditaccountbum,
			java.util.Date opendate ,Integer employeetotal ,String  postcoding,Integer hangyeType,
			String hangyeName,String customerLevel,Boolean isBlack,String blackReason,
			Short jyplace,BigDecimal areaMeasure,BigDecimal rent,Boolean isLicense,Integer headerId) {
		this.controlpersonid = controlpersonid;
		this.legalpersonid = legalpersonid;
		this.linkmampersonid = linkmampersonid;
		this.enterprisename = enterprisename;
		this.shortname = shortname;
		this.organizecode = organizecode;
		this.ownership = ownership;
		this.tradetype = tradetype;
		this.area = area;
		this.cciaa = cciaa;
		this.managescope = managescope;
		this.capitalkind = capitalkind;
		this.registermoney = registermoney;
		this.address = address;
		this.factaddress = factaddress;
		this.managecity = managecity;
		this.gslname = gslname;
		this.gslexamine = gslexamine;
		this.gslexaminedate = gslexaminedate;
		this.taxname = taxname;
		this.taxnum = taxnum;
		this.taxexamine = taxexamine;
		this.website = website;
		this.changer = changer;
		this.changedate = changedate;
		this.lkmduty = lkmduty;
		this.taxexaminedate = taxexaminedate;
		this.enjoytax = enjoytax;
		this.dstaxname = dstaxname;
		this.dstaxnum = dstaxnum;
		this.dstaxexamine = dstaxexamine;
		this.dsexaminedate = dsexaminedate;
		this.enjoyds = enjoyds;
		this.creater = creater;
		this.createdate = createdate;
		this.telephone =telephone ;
		this.fax = fax ;
		this.receiveMail = receiveMail ;
		this.registerstartdate = registerstartdate ;
		this.registerenddate = registerenddate ;
		this.creditaccountbum = creditaccountbum;
		this.opendate = opendate ;
		this.employeetotal = employeetotal ;
		this.postcoding = postcoding ;
		this.hangyeType=hangyeType;
		this.hangyeName=hangyeName;
		this.customerLevel=customerLevel;
		this.isBlack=isBlack;
		this.blackReason=blackReason;
		this.jyplace=jyplace;
		this.areaMeasure=areaMeasure;
		this.rent=rent;
		this.isLicense=isLicense;
		this.headerId=headerId;
	}
	
	// Property accessors
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getControlpersonid() {
		return this.controlpersonid;
	}

	public void setControlpersonid(Integer controlpersonid) {
		this.controlpersonid = controlpersonid;
	}

	public Integer getLegalpersonid() {
		return this.legalpersonid;
	}

	public void setLegalpersonid(Integer legalpersonid) {
		this.legalpersonid = legalpersonid;
	}

	public Integer getLinkmampersonid() {
		return this.linkmampersonid;
	}

	public void setLinkmampersonid(Integer linkmampersonid) {
		this.linkmampersonid = linkmampersonid;
	}

	public String getEnterprisename() {
		return this.enterprisename;
	}

	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}

	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getOrganizecode() {
		return this.organizecode;
	}

	public void setOrganizecode(String organizecode) {
		this.organizecode = organizecode;
	}

	public String getOwnership() {
		return this.ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public String getTradetype() {
		return this.tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCciaa() {
		return this.cciaa;
	}

	public void setCciaa(String cciaa) {
		this.cciaa = cciaa;
	}

	public String getManagescope() {
		return this.managescope;
	}

	public void setManagescope(String managescope) {
		this.managescope = managescope;
	}
	public String getCapitalkind() {
		return this.capitalkind;
	}

	public void setCapitalkind(String capitalkind) {
		this.capitalkind = capitalkind;
	}

	public Double getRegistermoney() {
		return registermoney;
	}

	public void setRegistermoney(Double registermoney) {
		this.registermoney = registermoney;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFactaddress() {
		return this.factaddress;
	}

	public void setFactaddress(String factaddress) {
		this.factaddress = factaddress;
	}

	public String getManagecity() {
		return this.managecity;
	}

	public void setManagecity(String managecity) {
		this.managecity = managecity;
	}

	public String getGslname() {
		return gslname;
	}

	public void setGslname(String gslname) {
		this.gslname = gslname;
	}

	public Boolean getGslexamine() {
		return this.gslexamine;
	}

	public void setGslexamine(Boolean gslexamine) {
		this.gslexamine = gslexamine;
	}

	public java.util.Date getGslexaminedate() {
		return this.gslexaminedate;
	}

	public void setGslexaminedate(java.util.Date gslexaminedate) {
		this.gslexaminedate = gslexaminedate;
	}

	public String getTaxname() {
		return this.taxname;
	}

	public void setTaxname(String taxname) {
		this.taxname = taxname;
	}

	public String getTaxnum() {
		return this.taxnum;
	}

	public void setTaxnum(String taxnum) {
		this.taxnum = taxnum;
	}

	public Boolean getTaxexamine() {
		return this.taxexamine;
	}

	public void setTaxexamine(Boolean taxexamine) {
		this.taxexamine = taxexamine;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getChanger() {
		return this.changer;
	}

	public void setChanger(String changer) {
		this.changer = changer;
	}

	public java.util.Date getChangedate() {
		return this.changedate;
	}

	public void setChangedate(java.util.Date changedate) {
		this.changedate = changedate;
	}

	public String getLkmduty() {
		return this.lkmduty;
	}

	public void setLkmduty(String lkmduty) {
		this.lkmduty = lkmduty;
	}
	public java.util.Date getTaxexaminedate() {
		return taxexaminedate;
	}

	public void setTaxexaminedate(java.util.Date taxexaminedate) {
		this.taxexaminedate = taxexaminedate;
	}

	public String getEnjoytax() {
		return enjoytax;
	}

	public void setEnjoytax(String enjoytax) {
		this.enjoytax = enjoytax;
	}

	public String getDstaxname() {
		return this.dstaxname;
	}

	public void setDstaxname(String dstaxname) {
		this.dstaxname = dstaxname;
	}

	public String getDstaxnum() {
		return this.dstaxnum;
	}

	public void setDstaxnum(String dstaxnum) {
		this.dstaxnum = dstaxnum;
	}

	public Boolean getDstaxexamine() {
		return this.dstaxexamine;
	}

	public void setDstaxexamine(Boolean dstaxexamine) {
		this.dstaxexamine = dstaxexamine;
	}

	public java.util.Date getDsexaminedate() {
		return this.dsexaminedate;
	}

	public void setDsexaminedate(java.util.Date dsexaminedate) {
		this.dsexaminedate = dsexaminedate;
	}

	public String getEnjoyds() {
		return this.enjoyds;
	}

	public void setEnjoyds(String enjoyds) {
		this.enjoyds = enjoyds;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public java.util.Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(java.util.Date createdate) {
		this.createdate = createdate;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getReceiveMail() {
		return receiveMail;
	}

	public void setReceiveMail(String receiveMail) {
		this.receiveMail = receiveMail;
	}
	public java.util.Date getRegisterstartdate() {
		return registerstartdate;
	}

	public void setRegisterstartdate(java.util.Date registerstartdate) {
		this.registerstartdate = registerstartdate;
	}

	public java.util.Date getRegisterenddate() {
		return registerenddate;
	}

	public void setRegisterenddate(java.util.Date registerenddate) {
		this.registerenddate = registerenddate;
	}

	public String getCreditaccountbum() {
		return creditaccountbum;
	}

	public void setCreditaccountbum(String creditaccountbum) {
		this.creditaccountbum = creditaccountbum;
	}

	public java.util.Date getOpendate() {
		return opendate;
	}

	public void setOpendate(java.util.Date opendate) {
		this.opendate = opendate;
	}

	public Integer getEmployeetotal() {
		return employeetotal;
	}

	public void setEmployeetotal(Integer employeetotal) {
		this.employeetotal = employeetotal;
	}

	public String getPostcoding() {
		return postcoding;
	}

	public void setPostcoding(String postcoding) {
		this.postcoding = postcoding;
	}

	public String getHangyeName() {
		return hangyeName;
	}

	public void setHangyeName(String hangyeName) {
		this.hangyeName = hangyeName;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public String getBelongedId() {
		return belongedId;
	}

	public void setBelongedId(String belongedId) {
		this.belongedId = belongedId;
	}

	public String getBelongedName() {
		return belongedName;
	}

	public void setBelongedName(String belongedName) {
		this.belongedName = belongedName;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public Boolean getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Boolean isBlack) {
		this.isBlack = isBlack;
	}

	public String getBlackReason() {
		return blackReason;
	}

	public void setBlackReason(String blackReason) {
		this.blackReason = blackReason;
	}
	public Integer getHangyeType() {
		return hangyeType;
	}

	public void setHangyeType(Integer hangyeType) {
		this.hangyeType = hangyeType;
	}
	public Short getJyplace() {
		return jyplace;
	}

	public void setJyplace(Short jyplace) {
		this.jyplace = jyplace;
	}

	public BigDecimal getAreaMeasure() {
		return areaMeasure;
	}

	public void setAreaMeasure(BigDecimal areaMeasure) {
		this.areaMeasure = areaMeasure;
	}

	public BigDecimal getRent() {
		return rent;
	}

	public void setRent(BigDecimal rent) {
		this.rent = rent;
	}

	public Boolean getIsLicense() {
		return isLicense;
	}

	public void setIsLicense(Boolean isLicense) {
		this.isLicense = isLicense;
	}

	public String getEnterpriseGsdjzExtendName() {
		return enterpriseGsdjzExtendName;
	}

	public void setEnterpriseGsdjzExtendName(String enterpriseGsdjzExtendName) {
		this.enterpriseGsdjzExtendName = enterpriseGsdjzExtendName;
	}

	public String getEnterpriseYyzzExtendName() {
		return enterpriseYyzzExtendName;
	}

	public void setEnterpriseYyzzExtendName(String enterpriseYyzzExtendName) {
		this.enterpriseYyzzExtendName = enterpriseYyzzExtendName;
	}

	public String getEnterpriseZzjgExtendName() {
		return enterpriseZzjgExtendName;
	}

	public void setEnterpriseZzjgExtendName(String enterpriseZzjgExtendName) {
		this.enterpriseZzjgExtendName = enterpriseZzjgExtendName;
	}

	public String getEnterpriseDsdjExtendName() {
		return enterpriseDsdjExtendName;
	}

	public void setEnterpriseDsdjExtendName(String enterpriseDsdjExtendName) {
		this.enterpriseDsdjExtendName = enterpriseDsdjExtendName;
	}

	public String getEnterpriseGsdjzURL() {
		return enterpriseGsdjzURL;
	}

	public void setEnterpriseGsdjzURL(String enterpriseGsdjzURL) {
		this.enterpriseGsdjzURL = enterpriseGsdjzURL;
	}

	public String getEnterpriseYyzzURL() {
		return enterpriseYyzzURL;
	}

	public void setEnterpriseYyzzURL(String enterpriseYyzzURL) {
		this.enterpriseYyzzURL = enterpriseYyzzURL;
	}

	public String getEnterpriseZzjgURL() {
		return enterpriseZzjgURL;
	}

	public void setEnterpriseZzjgURL(String enterpriseZzjgURL) {
		this.enterpriseZzjgURL = enterpriseZzjgURL;
	}

	public String getEnterpriseDsdjURL() {
		return enterpriseDsdjURL;
	}

	public void setEnterpriseDsdjURL(String enterpriseDsdjURL) {
		this.enterpriseDsdjURL = enterpriseDsdjURL;
	}

	public Integer getEnterpriseGsdjzId() {
		return enterpriseGsdjzId;
	}

	public void setEnterpriseGsdjzId(Integer enterpriseGsdjzId) {
		this.enterpriseGsdjzId = enterpriseGsdjzId;
	}


	public Integer getEnterpriseYyzzId() {
		return enterpriseYyzzId;
	}

	public void setEnterpriseYyzzId(Integer enterpriseYyzzId) {
		this.enterpriseYyzzId = enterpriseYyzzId;
	}


	public Integer getEnterpriseZzjgId() {
		return enterpriseZzjgId;
	}

	public void setEnterpriseZzjgId(Integer enterpriseZzjgId) {
		this.enterpriseZzjgId = enterpriseZzjgId;
	}

	public Integer getEnterpriseDsdjId() {
		return enterpriseDsdjId;
	}

	public void setEnterpriseDsdjId(Integer enterpriseDsdjId) {
		this.enterpriseDsdjId = enterpriseDsdjId;
	}


	public String getManagecityName() {
		return managecityName;
	}

	public void setManagecityName(String managecityName) {
		this.managecityName = managecityName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getHeaderId() {
		return headerId;
	}

	public void setHeaderId(Integer headerId) {
		this.headerId = headerId;
	}

	public Object toElementStr() {
		StringBuffer sb = new StringBuffer();
		sb.append("甲方（借款人）:"+this.getEnterprisename()+"</br>");
		sb.append("证件号码："+this.getCciaa()+"</br>");
		sb.append("通讯地址："+this.getAddress()+"</br>");
		sb.append("联系电话："+this.getTelephone()+"</br>");
		return sb.toString();
	}

	public String getIsCheckCard() {
		return isCheckCard;
	}

	public void setIsCheckCard(String isCheckCard) {
		this.isCheckCard = isCheckCard;
	}


	public BigDecimal getTenderingMoney() {
		return tenderingMoney;
	}

	public void setTenderingMoney(BigDecimal tenderingMoney) {
		this.tenderingMoney = tenderingMoney;
	}

	public BigDecimal getRepayingMoney() {
		return repayingMoney;
	}

	public void setRepayingMoney(BigDecimal repayingMoney) {
		this.repayingMoney = repayingMoney;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getSurplusMoney() {
		return surplusMoney;
	}

	public void setSurplusMoney(BigDecimal surplusMoney) {
		this.surplusMoney = surplusMoney;
	}
}