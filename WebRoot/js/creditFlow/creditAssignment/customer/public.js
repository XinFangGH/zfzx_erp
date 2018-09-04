/**
 * 
 * @class
 * @extends Ext.form.FormPanel
 * 设置参数  id:随机ID
 *           personData:add时null edit时为从数据库查询的对象
 */
var investmentObj = Ext.extend(Ext.form.FormPanel, {
	isRead : false,
	isflag : false,
	tempTitle:'填写个人基本信息',
	isHidden:true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.applyIf(this, _cfg);
		if(_cfg.isReadOnly)
		{
			this.isRead=_cfg.isReadOnly;
		};
		if(null!=_cfg.personData){
		    this.isflag=true;
		};
		if(null!=_cfg.accountData){
		    this.isflag=true;
		};
		if(null!=_cfg.tempTitle){
		    this.tempTitle=_cfg.tempTitle;
		};
		if(null!=_cfg.isHidden){
		    this.isHidden=_cfg.isHidden;
		};
		this.initUIComponents();
		var personData = this.personData;
		var accountData = this.accountData;
		var panelId=_cfg.id;
		var panel_add=this;
		var isEdit =true;//用来标示是否为添加页面   false表示添加页面
		/*if(accountData==null||accountData=="undefined"){
				isEdit=false;
		}*/
		if(personData==null||personData=="undefined"){
				isEdit=false;
		}
		investmentObj.superclass.constructor.call(this, {
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true,
			monitorValid : true,
			labelWidth : 110,
			id:panelId,
			autoScroll : true,
			bodyStyle : 'overflowX:hidden',
			layout : 'form',
			url:this.url,
			border : false,
			items : [{
				layout : 'form',
				autoHeight : true,
				collapsible : false,
				anchor : '100%',
				items : [{
						layout : 'column',
						xtype : 'fieldset',
						title : this.tempTitle,
						collapsible : true,
						autoHeight : true,
						anchor : '100%',
						items : [{
							columnWidth : 1,
							labelWidth : 90,
							layout : 'column',
							items : [{
								columnWidth : .33,
								labelWidth : 90,
								layout : 'column',
								items : [{
									columnWidth : 1,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									scope : this,
									items : [{
										xtype : "hidden",
										name : "csInvestmentperson.creater",
										value : personData == null?null:personData.creater
									},{
										xtype : "hidden",
										name : "csInvestmentperson.investId",
										value : personData == null?null:personData.investId
									}, {
										xtype : "hidden",
										name : "csInvestmentperson.createrId",
										value : personData == null? null: personData.createrId
									}, {
										xtype : "hidden",
										name : "csInvestmentperson.createdate",
										value : personData == null?null:personData.createdate
									}, {
										xtype : "hidden",
										name : "csInvestmentperson.companyId",
										value : personData == null?null:personData.companyId
									}, {
										xtype : 'textfield',
										fieldLabel : '<font color=red>*</font>姓名',
										allowBlank : false,
										name : 'csInvestmentperson.investName',
										blankText : '姓名为必填内容',
										readOnly : this.isRead,
										value : personData == null?null:personData.investName,
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
												
									}, {
										xtype : "dickeycombo",
										nodeKey : 'card_type_key',
										hiddenName : "csInvestmentperson.cardtype",
										itemName : '证件类型', // xx代表分类名称
										fieldLabel : "证件类型",
										allowBlank : false,
										editable : true,
										readOnly : this.isRead,
										value : personData == null
												? null
												: personData.cardtype,
										// emptyText : "请选择",
										blankText : "证件类型不能为空，请正确填写!",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													if(combox.getValue()=='' || combox.getValue()==null){												
														combox.setValue(st.getAt(0).data.itemId);
														combox.clearInvalid();
													}else{
														combox.setValue(combox.getValue());
														combox.clearInvalid();
													}
												})
											}
										}
									},{
										xtype : 'textfield',
										fieldLabel : '邮政编码',
										name : 'csInvestmentperson.postcode',
										readOnly : this.isRead,
										allowBlank : false,
										blankText : '邮政编码为必填内容',
										regex : /^[0-9]{6}$/,
										regexText : '邮政编码格式不正确',
										value : personData == null
												? null: personData.postcode
												
									}, {
										xtype : 'hidden',
										name : 'personSFZZId',
										value : personData==null?null:personData.personSFZZId
									}, {
										xtype : 'hidden',
										name : 'personSFZFId',
										value : personData==null?null:personData.personSFZFId
									}]

								}]
							},{
								columnWidth : .66,
								labelWidth : 90,
								layout : 'column',
								items : [{
									columnWidth : 0.5,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									scope : this,
									items : [{
										xtype : "dickeycombo",
										nodeKey : 'sex_key',
										hiddenName : 'csInvestmentperson.sex',
										fieldLabel : "性别",
										allowBlank : false,
										editable : true,
										blankText : "性别不能为空，请正确填写!",
										readOnly : this.isRead,
										value : personData == null? null: personData.sex,
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													if (combox.getValue() == 0
															|| combox
																	.getValue() == 1
															|| combox
																	.getValue() == ""
															|| combox
																	.getValue() == null) {
														combox.setValue("");
													} else {
														combox.setValue(combox
																.getValue());
													}
													combox.clearInvalid();
												})
											}
										}
									},  {
										xtype : 'textfield',
										fieldLabel : '证件号码',
										name : 'csInvestmentperson.cardnumber',
										allowBlank : false,
										blankText : '证件号码为必填内容',
										readOnly : this.isRead,
										value : personData == null
												? null
												: personData.cardnumber,	
										listeners : {
											scope:this,
											'beforerender':function(com){/*
												if(this.getCmpByName('csInvestmentperson.cardtype').getValue()==309){
													if(validateIdCard(com.getValue())==1){
														Ext.ux.Toast.msg('身份证号码验证','证件号码不正确,请仔细核对')
													}else if(validateIdCard(com.getValue())==2){
														Ext.ux.Toast.msg('身份证号码验证','证件号码地区不正确,请仔细核对')
														
													}else if(validateIdCard(com.getValue())==3){
														Ext.ux.Toast.msg('身份证号码验证','证件号码生日日期不正确,请仔细核对')														
													}
												}
											*/},
											'blur' : function(f) {
												if(this.getCmpByName('csInvestmentperson.cardtype').getValue()==309){
													if(validateIdCard(f.getValue())==1){
														f.setValue(null);
														Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
														return;
													}else if(validateIdCard(f.getValue())==2){
														f.setValue(null);
														Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
														return;
													}else if(validateIdCard(f.getValue())==3){
														f.setValue(null);
														Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
														return;
													}
												}
												if(!isEdit&&personData==null){
													var cardNumber = f.getValue();
													var brithday= cardNumber.substr(6,8);
													var formatBrithday = brithday.substr(0,4)+"-"+brithday.substr(4,2)+"-"+brithday.substr(6,2);
													this.getCmpByName("csInvestmentperson.birthDay").setValue(formatBrithday)
												}
												
												var penal=this.getCmpByName("csInvestmentperson.birthDay");
												var sex = this.getCmpByName('csInvestmentperson.sex');
												var cardNumber = f.getValue();
												var personId = (personData==null)?0:personData.personId;
												Ext.Ajax.request({
								                   url:  __ctxPath + '/creditFlow/creditAssignment/customer/verificationCsInvestmentperson.do',
								                   method : 'POST',
								                   params : {
															cardNum : cardNumber,
															personId:personId
														},
								                  success : function(response,request) {
														var obj=Ext.util.JSON.decode(response.responseText);
					                            		if(obj.msg==false){					                            			
					                            			Ext.ux.Toast.msg('操作信息',"该证件号码已存在，请重新输入");
					                            			f.setValue("");
					                            			//penal.setValue("");
					                            		}else{
					                            			
					                            			if(cardNumber.split("").reverse()[1]%2==0){
					                            				sex.setValue(313);
					                            				sex.setRawValue("女")
					                            			}else{
					                            				sex.setValue(312);
					                            				sex.setRawValue("男")
					                            			}
					                            			//拆分身份证号码 ，拿出出生年月日  
//					                            			if(!isEdit&&personData==null){//只有新增才需要默认加载身份证上的出生年月日
						                            			var brithday= cardNumber.substr(6,8);
																var formatBrithday = brithday.substr(0,4)+"-"+brithday.substr(4,2)+"-"+brithday.substr(6,2);
						                            			penal.setValue(formatBrithday);
//					                            			}
					                            		}
							                      }
					                             });  
												if(!isEdit&&personData==null){
													var cardNumber = f.getValue();
													var brithday= cardNumber.substr(6,8);
													var formatBrithday = brithday.substr(0,4)+"-"+brithday.substr(4,2)+"-"+brithday.substr(6,2);
													penal.setValue(formatBrithday);
												}
//												ajaxUniquenessValidator(this,"validatorPersonCard","该人员已存在！");
											}
										}
									},{
										xtype : 'textfield',
										fieldLabel : '电子邮箱',
										name : 'csInvestmentperson.selfemail',
										readOnly : this.isRead,
										value : personData == null
												? null
												: personData.selfemail,
										regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
										regexText : '电子邮箱格式不正确',
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}	

									}]

								}, {
									columnWidth : 0.5,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									items : [{
										xtype : 'textfield',
										fieldLabel : '电话号码',
										name : 'csInvestmentperson.cellphone',
										value : personData == null? null: personData.cellphone,
										allowBlank : false,
										readOnly : this.isRead,
										regex : /^[1][34578][0-9]{9}$/,
										regexText : '手机号码格式不正确'										
										/*listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}*/
										
									},{
										xtype : 'datefield',
										labelSeparator : '',
										format : 'Y-m-d',
										fieldLabel : '出生日期',
										name : 'csInvestmentperson.birthDay',
										readOnly : this.isRead,
										
										value : personData == null
												? null: personData.birthDay
												
									},{
										xtype : "hidden",
										name : 'csInvestmentperson.belongedId',
										value : personData == null? null: personData.belongedId
									},{
										hiddenName : 'belongedName',
										xtype : 'trigger',
										fieldLabel : '客户授权人',
										submitValue : true,
										triggerClass : 'x-form-search-trigger',
										editable : false,
										readOnly : (this.isRead == false&&isGranted('_editBelongeder_grkh'))
												? false
												: true,
										value : personData == null
												? null
												: personData.belongedName,
										scope : this,
										onTriggerClick : function() {
											var obj = this;
											var belongedObj = obj
													.previousSibling();
											var userIds = belongedObj
													.getValue();
											if (null == obj.getValue()
													|| "" == obj.getValue()) {
												userIds = "";
											}
											new UserDialog({
												userIds : userIds,
												userName : obj.getValue(),
												single : false,
												title : "客户授权人",
												callback : function(uId, uname) {
													if((!isEdit)&&((","+uId+",").indexOf(","+curUserInfo.userId+",")==-1)){
														uId=uId+","+curUserInfo.userId
														uname=uname+","+curUserInfo.fullname
													}
													obj.setRawValue(uname);
													belongedObj.setValue(uId);
												}
											}).show();
										}
									}]

								}]
							},{
									columnWidth : 1,
									layout : 'form',
									defaults : {
										anchor : '99%'
									},
									items : [ {
										fieldLabel : '登记门店',
										allowBlank : false,
										editable : false,
										readOnly : this.isRead,
										xtype:'trigger',
										triggerClass :'x-form-search-trigger',
										value : personData == null? null: personData.shopName,
										name : 'csInvestmentperson.shopName',
										hiddenName:'csInvestmentperson.shopName',
										onTriggerClick : function() {
										var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
										var EnterpriseNameStockUpdateNew = function(obj) {
											if (null != obj.orgName&& "" != obj.orgName)
												op.getCmpByName('csInvestmentperson.shopName').setValue(obj.orgName);
											if (null != obj.orgId&& "" != obj.orgId)
												op.getCmpByName('csInvestmentperson.shopId').setValue(obj.orgId);
										}
										selectShop(EnterpriseNameStockUpdateNew);
									}
									},{
										xtype : 'hidden',
										name : 'csInvestmentperson.shopId'
									},{
										xtype : 'textfield',
										fieldLabel : '通讯地址',
										allowBlank : false,
										readOnly : this.isRead,
										value : personData == null
												? null
												: personData.postaddress,
										name : 'csInvestmentperson.postaddress'
									}]

								}]
						}]
					}, {
						layout : 'column',
						xtype : 'fieldset',
						title : '身份证扫描件',
						labelWidth : 90,
						collapsible : true,
						autoHeight : true,
						anchor : '100%',
						items : [{
							columnWidth : 1,
							layout : 'column',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							items : [{
								columnWidth : 0.45,
								layout : 'form',
								labelWidth : 65,
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : 'label',
									style : 'padding-left :10px',
									html : this.isRead
											? ''
											: '身份证正面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson_new(\'身份证正面\',\'cs_investMentperson_sfzz\',\'shenfenzheng-z\',\'personSFZZId\',\''+ panelId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'personSFZZId\',\'shenfenzheng-z\',\''+ panelId+'\')>删除</a>'
								}, {
									xtype : 'label',
									style : 'padding-left :20px',
									name:'shenfenzheng-z',
									html :function(){
										if(personData==null || null==personData.personSFZZId || ""==personData.personSFZZId || personData.personSFZZId==0){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ __ctxPath
													+ '/images/nopic.jpg" width =140 height=80/></div>'
													
										}else if(personData!=null && null!=personData.personSFZZId && ""!=personData.personSFZZId && personData.personSFZZId!=0 && (personData.personSFZZExtendName==".jpg" || personData.personSFZZExtendName==".jpeg")){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px"><img src="'
													+ __ctxPath
													+ '/'
													+ personData.personSFZZUrl
													+ '" ondblclick=showPic("'
													+ personData.personSFZZUrl
													+ '") width =140 height=80  /></div>'
										}else if(personData!=null && null!=personData.personSFZZId && ""!=personData.personSFZZId && personData.personSFZZId!=0 && personData.personSFZZExtendName==".pdf"){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px"><img src="'
													+ __ctxPath
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+personData.personSFZZId+',"'
													+ personData.personSFZZUrl
													+ '") width =140 height=80  /></div>'
										}
									}()
									
									/*(personData==null || null==personData.personSFZZId || ""==personData.personSFZZId || personData.personSFZZId==0)?
											'<img src="'
													+ __ctxPath
													+ '/images/nopic.jpg" width =140 height=80 />'
											        : '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px"><img src="'
													+ __ctxPath
													+ '/'
													+ personData.personSFZZUrl
													+ '" ondblclick=showPic("'
													+ personData.personSFZZUrl
													+ '") width =140 height=80  /></div>'*/
								}]
							}, {
								columnWidth : 0.45,
								layout : 'form',
								labelWidth : 65,
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : 'label',
									html : this.isRead?'': '身份证反面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson_new(\'身份证反面\',\'cs_investMentperson_sfzf\',\'shenfenzheng-f\',\'personSFZFId\',\''+ panelId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'personSFZFId\',\'shenfenzheng-f\',\''+ panelId+'\')>删除</a>'
								}, {
									name:'shenfenzheng-f',
									xtype : 'label',
									html : function(){
										if(personData==null || null==personData.personSFZFId || ""==personData.personSFZFId || personData.personSFZFId==0){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ __ctxPath
													+ '/images/nopic.jpg" width =140 height=80/></div>'
										}else if(personData!=null && null!=personData.personSFZFId && ""!=personData.personSFZFId && personData.personSFZFId!=0 && (personData.personSFZFExtendName==".jpg" || personData.personSFZFExtendName==".jpeg")){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px;"><img src="'
												+ __ctxPath
												+ '/'
												+ personData.personSFZFUrl
												+ '" ondblclick=showPic("'
												+ personData.personSFZFUrl
												+ '") width =140 height=80  /></div>'
										}else if(personData!=null && null!=personData.personSFZFId && ""!=personData.personSFZFId && personData.personSFZFId!=0 && personData.personSFZFExtendName==".pdf"){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px"><img src="'
													+ __ctxPath
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+personData.personSFZFId+',"'
													+ personData.personSFZFUrl
													+ '") width =140 height=80  /></div>'
										}
									}()
									
									
								}]
							}]
						}]
					},{
						layout : 'column',
						xtype : 'fieldset',
						title : '银行账户信息',
						collapsible : true,
						autoHeight : true,
						hidden:this.isHiddenBank,//意向客户转化的时候会传
						disabled:this.isHiddenBank,
						anchor : '100%',
						items : [{
							columnWidth : 0.9,
							labelWidth : 90,
							layout : 'column',
							items : [{
								columnWidth : .5,
								labelWidth : 90,
								layout : 'column',
								items : [{
									columnWidth : 1,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									scope : this,
									items : [{
												xtype:'combo',
										          mode : 'local',
									               displayField : 'name',
									              valueField : 'id',
									              editable : false,
									              readOnly : this.isRead,
									                 width : 70,
									                 store : new Ext.data.SimpleStore({
											        fields : ["name", "id"],
										            data : [["个人", "0"],
													     	["公司", "1"]]
									              	}),
										            triggerAction : "all",
									                hiddenName:"enterpriseBank.openType",
								                	fieldLabel : '开户类型',	
								                	anchor : '100%',
								                	allowBlank:false,
								                	disabled:this.isHiddenBank,
								                	value : personData == null?null:personData.openType,
										          	name : 'enterpriseBank.openType',
										          	listeners : {
															scope : this,
															select : function(combox, record, index) {
															var v = record.data.id;
															var obj = Ext.getCmp('accountTypeid');
															obj.enable();
															var arrStore = null;
															if(v==0){
																arrStore = new Ext.data.SimpleStore({
															        fields : ["name", "id"],
														            data : [["个人储蓄户", "0"]]
																});
															}else{
																arrStore = new Ext.data.SimpleStore({
															        fields : ["name", "id"],
														            data : [["基本户", "1"],["一般户", "2"]]
												              	});
															}
															obj.clearValue();
								                            obj.store = arrStore;
								                            if(obj.view){
								                            	obj.view.setStore(arrStore);
								                            }
								                          //arrStore.load();
														},
														afterrender : function(combox) {
															var st = combox.getStore();
															st.on("load", function() {
																if(combox.getValue()=='' || combox.getValue()==null){												
																	combox.setValue(st.getAt(0).data.itemId);
																	combox.clearInvalid();
																}else{
																	combox.setValue(combox.getValue());
																	combox.clearInvalid();
																}
															})
														}
													
											}
								 }, {
										fieldLabel : "银行名称",
										xtype : "combo",
										displayField : 'itemName',
										valueField : 'itemId',
										allowBlank:false,
										readOnly : this.isRead,
										disabled:this.isHiddenBank,
										triggerAction : 'all',
										store : new Ext.data.ArrayStore({
											url : __ctxPath
															+ '/creditFlow/common/getBankListCsBank.do',
													fields : ['itemId', 'itemName'],
													autoLoad : true
										}),
										mode : 'remote',
										hiddenName : "enterpriseBank.bankid",
										editable : false,
										blankText : "银行名称不能为空，请正确填写!",
										anchor : "100%",
										value : personData == null?null:personData.bankId,
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
													
												})
												combox.clearInvalid();
											}
											
										}
								},{
											name : 'enterpriseBank.areaId',
											xtype:'hidden',
											value:personData == null?null:personData.areaId
								},{					
											name : 'enterpriseBank.areaName',
										    hiddenName : 'enterpriseBank.areaName',
											fieldLabel : '开户地区',	
											anchor : '100%',
											readOnly : this.isRead,
											allowBlank:false,
					                      	xtype:'trigger',
											triggerClass :'x-form-search-trigger',
											editable : false,
											scope : this,
											disabled:this.isHiddenBank,
											value:personData == null?null:personData.areaName,
											onTriggerClick : function(){
												var com=this
												var selectBankLinkMan = function(array){
													var str="";
													var idStr=""
													for(var i=array.length-1;i>=0;i--){
														str=str+array[i].text+"-"
														idStr=idStr+array[i].id+","
													}
													if(str!=""){
														str=str.substring(0,str.length-1);
													}
													if(idStr!=""){
														idStr=idStr.substring(0,idStr.length-1)
													}
													com.previousSibling().setValue(idStr)
													com.setValue(str);
												};
												selectDictionary('area',selectBankLinkMan);
											}
									},{
									 
										fieldLabel : '开户名称',	
		                              	name : 'enterpriseBank.name',
										xtype:'textfield',
										anchor : '100%',
										readOnly : this.isRead,
										disabled:this.isHiddenBank,
										allowBlank:false,
										value : personData == null?null:personData.khname
								 	}]

								}]
							},{
								columnWidth : .5,
								labelWidth : 90,
								layout : 'column',
								items : [{
									columnWidth : 1,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									scope : this,
									items : [{
											xtype:'combo',
								            mode : 'local',
							                displayField : 'name',
							                valueField : 'id',
							                editable : false,
							                width : 70,
								            triggerAction : "all",
								            id:'accountTypeid',
							                hiddenName:"enterpriseBank.accountType",
						                	fieldLabel : '账户类型',	
						                	anchor : '100%',
						                	readOnly : this.isRead,
						                	disabled:this.isHiddenBank,
						                	allowBlank:false,
						                	store:new Ext.data.SimpleStore({
												        	fields : ["name", "id"],
											            data : [["个人储蓄户", "0"],["基本户", "1"],["一般户", "2"]]
													}),
								          	name : 'enterpriseBank.accountType',
								          	value : personData == null?null:personData.accountType,
								          	listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
													
												})
												combox.clearInvalid();
											}
											
										}
							 		},{	
						                	xtype: 'radiogroup',
							                fieldLabel: '银行开户类别',
							                items: [
							                    {boxLabel: '本币开户', name: 'enterpriseBank.openCurrency',  disabled : this.isRead,inputValue: "0",checked:personData == null?true:(personData.openCurrency==0?true:false)},
							                    {boxLabel: '外币开户', name: 'enterpriseBank.openCurrency',  disabled : this.isRead,inputValue: "1",checked:personData == null?false:(personData.openCurrency==1?true:false)}
							                ]
				                	}, {
									
										fieldLabel : "网点名称",
	                                    name : 'enterpriseBank.bankOutletsName',
									    xtype:'textfield',
									    disabled:this.isHiddenBank,
										allowBlank:false,
										readOnly : this.isRead,
										anchor : "100%",
										value : personData == null?null:personData.bankOutletsName
									},{
										fieldLabel : '卡号',	
									 	name : 'enterpriseBank.accountnum',
									  	maxLength: 100,
									  	xtype:'textfield',
									  	readOnly : this.isRead,
									  	disabled:this.isHiddenBank,
									  	anchor : '100%',
									  	allowBlank:false,
									  	value : personData == null?null:personData.bankNum
								}]

							}]
							},{
				              	columnWidth : 1,
								layout : 'form',
								labelWidth : 90,
								hidden:!this.isHidden,
								items :[{
									xtype : 'textarea',
									 anchor : '100%',
									fieldLabel : '备注',
									readOnly : this.isRead,
									height : 80,
									name : 'enterpriseBank.remarks',
									value : personData == null?null:personData.remarks
								}]
				              },{
				              	xtype : 'hidden',
								name : 'enterpriseBank.enterpriseid',
								value : personData == null?null:(personData.enterpriseid==0?null:personData.enterpriseid)
				              },{
				              	xtype : 'hidden',
								name : 'enterpriseBank.id',
								value : personData == null?null:(personData.enterpriseBankId==0?null:personData.enterpriseBankId)
				              },{
				              	xtype : 'hidden',
								name : 'enterpriseBank.isEnterprise',
								value : "1"
				              }, {
								xtype : 'hidden',
								name : 'enterpriseBank.isInvest',
								value : "3"//表示这个属于债权转让的客户
							}, {
								xtype : 'hidden',
								name : 'enterpriseBank.isCredit',
								value : "0"
							},{
										xtype : 'hidden',
										name : 'personYHKZId',
										value : personData==null?null:personData.personYHKZId
							}, {
										xtype : 'hidden',
										name : 'personYHKFId',
										value : personData==null?null:personData.personYHKFId
							}]
						}]
					
					},{
						layout : 'column',
						xtype : 'fieldset',
						title : '银行卡扫描件',
						labelWidth : 80,
						collapsible : true,
						autoHeight : true,
						anchor : '100%',
						hidden:this.isHiddenBank,//意向客户转化的时候会传
						items : [{
							columnWidth : 1,
							layout : 'column',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							items : [{
								columnWidth : 0.33,
								layout : 'form',
								labelWidth : 65,
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : 'label',
									style : 'padding-left :20px',
									html : this.isRead
											? ''
											: '银行卡正面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson_new(\'银行卡正面\',\'cs_investmentperson_yhkz\',\'yinhangka-z\',\'personYHKZId\',\''+ panelId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'personYHKZId\',\'yinhangka-z\',\''+ panelId+'\')>删除</a>'
								}, {
									xtype : 'label',
									style : 'padding-left :20px',
									name:'yinhangka-z',
									html :function(){
										if(personData==null || null==personData.personYHKZId || ""==personData.personYHKZId || personData.personYHKZId==0){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ __ctxPath
													+ '/images/nopic.jpg" width =140 height=80/></div>'
										}else if(personData!=null && null!=personData.personYHKZId && ""!=personData.personYHKZId && personData.personYHKZId!=0 && (personData.personYHKZExtendName==".jpg" || personData.personYHKZExtendName==".jpeg")){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px"><img src="'
													+ __ctxPath
													+ '/'
													+ personData.personYHKZUrl
													+ '" ondblclick=showPic("'
													+ personData.personYHKZUrl
													+ '") width =140 height=80  /></div>'
										}else if(personData!=null && null!=personData.personYHKZId && ""!=personData.personYHKZId && personData.personYHKZId!=0 && personData.personYHKZExtendName==".pdf"){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px"><img src="'
													+ __ctxPath
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+personData.personYHKZId+',"'
													+ personData.personYHKZUrl
													+ '") width =140 height=80  /></div>'
										}
									}()
									
									/*(personData==null || null==personData.personSFZZId || ""==personData.personSFZZId || personData.personSFZZId==0)?
											'<img src="'
													+ __ctxPath
													+ '/images/nopic.jpg" width =140 height=80 />'
											        : '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px"><img src="'
													+ __ctxPath
													+ '/'
													+ personData.personSFZZUrl
													+ '" ondblclick=showPic("'
													+ personData.personSFZZUrl
													+ '") width =140 height=80  /></div>'*/
								}]
							}, {
								columnWidth : 0.33,
								layout : 'form',
								labelWidth : 65,
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : 'label',
									html : this.isRead?'': '银行卡反面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson_new(\'银行卡反面\',\'cs_investmentperson_yhkf\',\'yinhangka-f\',\'personYHKFId\',\''+ panelId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'personYHKFId\',\'yinhangka-f\',\''+ panelId+'\')>删除</a>'
								}, {
									name:'yinhangka-f',
									xtype : 'label',
									html : function(){
										if(personData==null || null==personData.personYHKFId || ""==personData.personYHKFId || personData.personYHKFId==0){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ __ctxPath
													+ '/images/nopic.jpg" width =140 height=80/></div>'
										}else if(personData!=null && null!=personData.personYHKFId && ""!=personData.personYHKFId && personData.personYHKFId!=0 && (personData.personYHKFExtendName==".jpg" || personData.personYHKFExtendName==".jpeg")){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px;"><img src="'
												+ __ctxPath
												+ '/'
												+ personData.personYHKFUrl
												+ '" ondblclick=showPic("'
												+ personData.personYHKFUrl
												+ '") width =140 height=80  /></div>'
										}else if(personData!=null && null!=personData.personYHKFId && ""!=personData.personYHKFId && personData.personYHKFId!=0 && personData.personYHKFExtendName==".pdf"){
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px"><img src="'
													+ __ctxPath
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+personData.personYHKFId+',"'
													+ personData.personYHKFUrl
													+ '") width =140 height=80  /></div>'
										}
									}()
									
									/*(personData==null || null==personData.personSFZFId || ""==personData.personSFZFId || personData.personSFZFId==0)? '<img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80 />'
										        : '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px;"><img src="'
												+ __ctxPath
												+ '/'
												+ personData.personSFZFUrl
												+ '" ondblclick=showPic("'
												+ personData.personSFZFUrl
												+ '") width =140 height=80  /></div>'*/
								}]
							}]
						}]
					
					},{
						layout : 'column',
						xtype : 'fieldset',
						title : '平台账户信息',
						collapsible : true,
						autoHeight : true,
						hidden:this.isHidden,
						anchor : '100%',
						items : [{
							columnWidth : 1,
							labelWidth : 95,
							layout : 'column',
							items : [{
								columnWidth : .33,
								labelWidth : 95,
								layout : 'column',
								items : [{
									columnWidth : 1,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									scope : this,
									items : [{
										xtype : 'textfield',
										fieldLabel : '<font color=red>*</font>平台账户',
										name : 'obSystemAccount.accountName',
										readOnly : this.isRead,
										value : accountData == null?null:accountData.accountName,
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}		
									}]
								}]
							},{
									columnWidth : .33,
									layout : 'form',
									defaults : {
										anchor : '99%'
									},
									items : [{
										xtype : 'numberfield',
										fieldLabel : '平台账号',
										name : 'obSystemAccount.accountNumber',
										allowBlank : this.isHidden,
										readOnly : this.isRead,
										value : accountData == null? null: accountData.accountNumber
									}]
								
							},{
									columnWidth : .33,
									layout : 'form',
									defaults : {
										anchor : '99%'
									},
									items : [{
										xtype : 'numberfield',
										fieldLabel : '余额',
										name : 'obSystemAccount.totalMoney',
										allowBlank : this.isHidden,
										readOnly : this.isRead,
										value : accountData == null? null: accountData.totalMoney
									}]
							}]
						}]
					}]
			}]
		})
	},
	initUIComponents : function() {
	}

})