/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京互融时代软件有限公司
 */
 		var  uploadPhotoBtnInvest= function(title,mark,displayProfilePhoto,personPId,panelId) {
			var panelObj=Ext.getCmp(panelId)
	var uploadWindow=new Ext.Window({
			title : title,
			layout : 'fit',
			width : (screen.width-180)*0.6,
			height : 130,
			closable : true,
			resizable : true,
			plain : false,
			bodyBorder : false,
			border : false,
			modal : true,
			constrainHeader : true ,
			bodyStyle:'overflowX:hidden',
			buttonAlign : 'right',
			items:[new Ext.form.FormPanel({
				url : getRootPath()+'/creditFlow/customer/enterprise/uploadPhotoEnterprise.do',
				monitorValid : true,
				labelAlign : 'right',
				buttonAlign : 'center',
				enctype : 'multipart/form-data',
			    permitted_extensions:['JPG','jpg','jpeg','JPEG'],  
				fileUpload: true, 
				layout : 'column',
				frame : true ,
				items : [{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 140,
					defaults : {anchor : '95%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : title,
						allowBlank : false,
						blankText : title+'不能为空',
						id : 'fileUpload',
						name: 'fileUpload',
	    				inputType: 'file'
					},{
						xtype : 'hidden',
						name: 'mark',
						value :mark
					}]
				}],
				scope:this,
				buttons : [{
					text : '上传',
					iconCls : 'uploadIcon',
					formBind : true,
					handler : function() {
					  var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/  
					  var url = 'file://'+ Ext.get('fileUpload').dom.value;  
                      var furl=Ext.get('fileUpload').dom.value;
					  var extendname=furl.substring(furl.lastIndexOf("."),furl.length);
					  if (!img_reg.test(url) && extendname!=".pdf") {  
                      	 alert('您选择的文件格式不正确,请重新选择!');
                         return false;
                      };
					
					  var pwindow=this.ownerCt.ownerCt.ownerCt;
					   var collection = this.ownerCt.ownerCt.ownerCt.items; 
					   var formPanel=collection.first();
					   formPanel.getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							params:{extendname:extendname},
							success : function(form ,action) {
								Ext.ux.Toast.msg('提示',title+'上传成功！',
								pwindow.destroy(),
									function(btn, text) {
								});
								
								var fileid = action.result.fileid;
								var webPath = action.result.webPath;
								var display =panelObj.getCmpByName(displayProfilePhoto);
								if(extendname==".jpg" || extendname==".jpeg"){
								    display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+ getRootPath()+"/"+webPath+'" ondblclick=showPic("'+webPath+'") width =140 height=80  /></div>',false);
								}else if(extendname==".pdf"){
									display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+getRootPath()+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+fileid+',"'+webPath+'") width =140 height=80  /></div>',false);
								}
								panelObj.getCmpByName(personPId).setValue(fileid);
								
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('提示',title+'上传失败！');		
							}
						});
					}
				}]
			})]
		});
	    uploadWindow.show();
};
var delePhotoFile_invest=function(hiddenId,displayId,panelId){
	var panelObj=Ext.getCmp(panelId);
	var adV=panelObj.getCmpByName(hiddenId).getValue();
	if(null==adV || ""==adV || adV==0){
	   alert('请先上传图片');return false}
     Ext.Ajax.request({
		url : __ctxPath+'/creditFlow/fileUploads/DeleRsFileForm.do?fileid='+adV,
		method : 'POST',
		scope : this,
		success : function() {
			Ext.ux.Toast.msg('状态', '删除成功！');
			var display = panelObj.getCmpByName(displayId);
			display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px;"><img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80/></div>',false);
			panelObj.getCmpByName(hiddenId).setValue(null);
		},
		failure : function(result, action) {
			Ext.ux.Toast.msg('状态', '删除失败，请重试！');
		}
	});
}
InvestEnterpriseForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		var anchor = '95%';
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		InvestEnterpriseForm.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 500,
					width : 900,
					maximizable : true,
					title : '企业客户详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								hidden : this.isAllReadOnly,
								handler : this.save
							}, {
								text : '关闭',
								iconCls : 'close',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {

		this.customeLinkmanGridPanel=new CustomeLinkmanGridPanel({enterpriseId:this.id,isHidden:this.isAllReadOnly})
		this.formPanel = new Ext.FormPanel({
			id : 'investEnterpriseForm',
			layout : 'anchor',
			border : false,
			autoScroll : true,
			monitorValid : true,
			frame : true,
			labelAlign : 'right',
			anchor : "96%",
			items : [{
					columnWidth : 1,
					items : [{
						layout : 'column',
						xtype : 'fieldset',
						title : '填写企业基本信息',
						autoHeight : true,
						collapsible : false,
						anchor : "96%",
						items : [{
							bodyStyle : 'padding-right:10px',
							columnWidth : 1, 
							layout : "form", 
							items : [{
								xtype : 'fieldset',
								layout : "column",
								border : false,
								defaults : {
									anchor : '96%',
									columnWidth : 1,
									isFormField : true,
									labelWidth : 90
								},
								items : [{
									columnWidth : 1, 
									layout : "form", 
									labelWidth : 90,
									items : [{
												xtype : 'hidden',
												name : 'investEnterprise.id'
											}, {
												xtype : 'textfield',
												anchor : '100%',
												allowBlank : false,
												fieldLabel : "企业名称",
												name : "investEnterprise.enterprisename",
												readOnly : this.isAllReadOnly,
												blankText : "企业名称不能为空，请正确填写!"
											}]
								}, {
									columnWidth : 0.5, 
									layout : "form", 
									labelWidth : 90,
									items : [{
												xtype : "textfield",
												anchor : '100%',
												name : "investEnterprise.shortname",
												fieldLabel : "企业简称",
												readOnly : this.isAllReadOnly,
												blankText : "企业简称不能为空，请正确填写!"

											}]
								}, {
									columnWidth : 0.5, 
									layout : "form", 
									labelWidth : 90,
									items : [{
										xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										name : "investEnterprise.mainHangye",
										fieldLabel : "行业类别",
										anchor : '100%',
										scope : this,
										readOnly : this.isAllReadOnly,
										onTriggerClick : function(e) {
											var obj = this;
											var oobbj = obj.ownerCt.ownerCt
													.getCmpByName("investEnterprise.hangyetype");
											selectTradeCategory(obj, oobbj);
										}
									}]
								}, {
									columnWidth : 0.5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : 90,
									items : [{
										xtype : "textfield",
										name : "investEnterprise.organizecode",
										allowBlank : false,
										fieldLabel : "组织机构代码",
										regex : /^[A-Za-z0-9]{8}-[A-Za-z0-9]{1}/,
										regexText : '组织机构代码格式不正确',
										readOnly : this.isAllReadOnly,
										blankText : "组织机构代码不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											'blur' : function(tf) {
												var organizecode = tf
														.getValue();
												var enterpriseId = this
														.getCmpByName("investEnterprise.id")
														.getValue();
												Ext.Ajax.request({
													url : __ctxPath + '/customer/verificationOrganizecodeInvestEnterprise.do',
													method : 'POST',
													params : {
														organizecode : organizecode,
														id : enterpriseId
													},
													success : function(
															response, request) {

														var obj = Ext.util.JSON
																.decode(response.responseText);
														
														if (obj.unique==false) {

															Ext.ux.Toast.msg('操作信息',"该组织机构代码已存在，请重新输入");
															tf.setValue("");
														}
													}
												});
											}
										}
									}]
								}, {
									xtype : "hidden",
									name : 'investEnterprise.hangyetype'
								}, {
									xtype : "hidden",
									name : 'investEnterprise.rootHangYeType'
								}, {
									columnWidth : 0.5, 
									layout : "form", 
									labelWidth : 90,
									items : [{
												xtype : "textfield",
												name : "investEnterprise.cciaa",
												fieldLabel : "营业执照号码",
												readOnly : this.isAllReadOnly,
												blankText : "营业执照号码不能为空，请正确填写!",
												allowBlank : false,
												anchor : "100%"
											}]
								}, {
									columnWidth : 0.5, 
									layout : "form", 
									labelWidth : 90,
									items : [{
										xtype : "textfield",
										name : "investEnterprise.telephone",
										allowBlank : false,
										fieldLabel : "联系电话",
										readOnly : this.isAllReadOnly,
										blankText : "联系电话不能为空，请正确填写!",
										regex : /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
										regexText : '联系电话格式不正确',
										anchor : "100%"
									}]
								}, {
									columnWidth : 0.5, 
									layout : "form", 
									labelWidth : 90,
									items : [{
												xtype : "textfield",
												allowBlank : false,
												name : "investEnterprise.postcoding",
												fieldLabel : "邮政编码",
												readOnly : this.isAllReadOnly,
												blankText : "邮政编码不能为空，请正确填写!",
												regex : /^[0-9]{6}$/,
												regexText : '邮政编码格式不正确',
												anchor : "100%"
											}]
								}, {
									columnWidth : 1, 
									layout : "form", 
									labelWidth : 90,
									items : [{
												xtype : "textfield",
												fieldLabel : "通讯地址",
												readOnly : this.isAllReadOnly,
												allowBlank : false,
												name : "investEnterprise.area",
												anchor : '100%'
											}]
								}, {
									columnWidth : 1, 
									layout : "form", 
									labelWidth : 90,
									items : [{
												xtype : "textarea",
												fieldLabel : "简介",
												readOnly : this.isAllReadOnly,
											
												name : "investEnterprise.summary",
												anchor : '100%'
											}]
								},{
									columnWidth:1,
									layout:'column',
									hidden:true,
									labelWidth:90,
									items:[{
										layout:'form',
										columnWidth:.48,
										items:[{
											xtype:'numberfield',
											anchor:'100',
											fieldLabel:'授信额度',
											readOnly:this.isAllReadOnly,
											allowBlank:false,
											name:'investEnterprise.sumCreditLimit',
											value:'0'
										}]
									},{
										layout:'form',
										columnWidth:.02,
										items:[{
											style:'margin-left:3px;margin-top:3px;',
											html:'元'
										}]
									},{
										layout:'form',
										columnWidth:.48,
										items:[{
											xtype:'textfield',
											fieldLabel:'可用额度',
											anchor:'100',
											readOnly:this.isAllReadOnly,
											allowBlank:false,
											name:'investEnterprise.nowCreditLimit',
											value:'0'
										}]
									},{
										layout:'form',
										columnWidth:.02,
										items:[{
											style:'margin-left:3px;margin-top:3px;',
											html:'元'
										}]
									}]
								},{
									columnWidth:1,
									hidden:true,
									layout:'form',
									labelWidth:90,
									items:[{
										xtype : "textfield",
										fieldLabel : "税务登记号",
										readOnly : this.isAllReadOnly,
										allowBlank : false,
										name : "investEnterprise.taxnum",
										anchor : '100%',
										value:'0'
									}]
								}]
							}]

						}]
					}, {
						layout : 'column',
						xtype : 'fieldset',
						title : '公司资料扫描件',
						autoHeight : true,
						collapsible : false,
						anchor : "96%",
						items : [{
							columnWidth : 1,
							layout : 'column',
							labelWidth : 65,
							defaults : {
								anchor : '96%'
							},
							scope : this,
							items : [{
								columnWidth : 0.25,
								layout : 'form',
								labelWidth : 25,
								defaults : {
									anchor : '100%'
								},
								items : [{
									xtype : 'label',
									style : 'padding-left :10px',
									scope : this,
									html : this.isAllReadOnly == false
											? '营业执照副本&nbsp;&nbsp;&nbsp;<a href="#"   onClick =uploadPhotoBtnInvest(\'营业执照副本\',\'cs_invest_enterprise_yyzz\',\'yingyezhizhao\',\'enterpriseYyzzId\',\'investEnterpriseForm\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_invest(\'enterpriseYyzzId\',\'yingyezhizhao\',\'investEnterpriseForm\')>删除</a>'
											: '营业执照副本'
								}, {
									name : 'yingyezhizhao',
									xtype : 'label',
									style : 'padding-left :20px',
									html : function() {
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '/images/nopic.jpg" width =140 height=80/></div>'
									}()
								},{
									xtype : 'hidden',
									name : 'enterpriseYyzzId'
								}]
							}, {
								columnWidth : 0.25,
								layout : 'form',
								labelWidth : 25,
								defaults : {
									anchor : '100%'
								},
								items : [{
									xtype : 'label',
									style : 'padding-left :10px',
									html : this.isAllReadOnly == false
											? '组织机构代码证&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnInvest(\'组织机构代码证\',\'cs_invest_enterprise_zzjgdmz\',\'zhuzhijigou\',\'enterpriseZzjgId\',\'investEnterpriseForm\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_invest(\'enterpriseZzjgId\',\'zhuzhijigou\',\'investEnterpriseForm\')>删除</a>'
											: '组织机构代码证'
								}, {
									name : "zhuzhijigou",
									xtype : 'label',
									html : function() {
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '/images/nopic.jpg" width =140 height=80/></div>'
									}()

								},{
									xtype : 'hidden',
									name : 'enterpriseZzjgId'
								}]
							}, {
								columnWidth : 0.25,
								layout : 'form',
								labelWidth : 25,
								defaults : {
									anchor : '100%'
								},
								items : [{
									xtype : 'label',
									style : 'padding-left :10px',
									html : this.isAllReadOnly == false
											? '税务证扫描件&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnInvest(\'税务证扫描件\',\'cs_invest_enterprise_swzsmj\',\'shuiwuzheng\',\'enterpriseSwzId\',\'investEnterpriseForm\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_invest(\'enterpriseSwzId\',\'shuiwuzheng\',\'investEnterpriseForm\')>删除</a>'
											: '税务证扫描件'
								}, {
									name : 'shuiwuzheng',
									xtype : 'label',
									html : function() {

											return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '/images/nopic.jpg" width =140 height=80/></div>'

									}()
								},{
									xtype : 'hidden',
									name : 'enterpriseSwzId'
								}]
							}, {
								columnWidth : 0.25,
								layout : 'form',
								labelWidth : 25,
								defaults : {
									anchor : '100%'
								},
								items : [{
									xtype : 'label',
									style : 'padding-left :10px',
									html : this.isAllReadOnly == false
											? '贷款卡扫描件&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnInvest(\'贷款卡扫描件\',\'cs_invest_enterprise_dkksmj\',\'daikuanka\',\'enterpriseDkkId\',\'investEnterpriseForm\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_invest(\'enterpriseDkkId\',\'daikuanka\',\'investEnterpriseForm\')>删除</a>'
											: '贷款卡扫描件'
								}, {
									name : 'daikuanka',
									xtype : 'label',
									html : function() {
											return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '/images/nopic.jpg" width =140 height=80/></div>'
									}()
								},{
									xtype : 'hidden',
									name : 'enterpriseDkkId'
								}]
							}]
						}]
					}, {
						layout : 'column',
						xtype : 'fieldset',
						title : '法定代表人信息',
						autoHeight : true,
						collapsible : false,
						anchor : "96%",
						items : [{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 100,
							defaults : {
								anchor : "96%"
							},
							items : [{
									xtype : 'combo',
									triggerClass : 'x-form-search-trigger',
									fieldLabel : "法定代表人姓名",
									name : "investPerson.perName",
									readOnly : this.isAllReadOnly,
									allowBlank : false,
									blankText : "客户名称不能为空，请正确填写!",
									anchor : "100%",
									scope : this,
									onTriggerClick : function(com) {
										var perIdObj=this.ownerCt.ownerCt.getCmpByName('investPerson.perId')
										var perNameObj=this.ownerCt.ownerCt.getCmpByName('investPerson.perName')
										var perSexObj=this.ownerCt.ownerCt.getCmpByName('investPerson.perSex')
										var cardTypeObj=this.ownerCt.ownerCt.getCmpByName('investPerson.cardType')
										var cardNumberObj=this.ownerCt.ownerCt.getCmpByName('investPerson.cardNumber')
										var phoneNumberObj=this.ownerCt.ownerCt.getCmpByName('investPerson.phoneNumber')
										var perEmailObj=this.ownerCt.ownerCt.getCmpByName('investPerson.perEmail')
										var sfzzObj=this.ownerCt.ownerCt.ownerCt.getCmpByName('shenfenzheng-z')
										var sfzfObj=this.ownerCt.ownerCt.ownerCt.getCmpByName('shenfenzheng-f')
										var personSFZZIdObj=this.ownerCt.ownerCt.getCmpByName('personSFZZId')
										var personSFZFIdObj=this.ownerCt.ownerCt.getCmpByName('personSFZFId')
							
										var selectPerson=function(obj){
											perIdObj.setValue(obj.perId)
											perNameObj.setValue(obj.perName)
											perSexObj.setValue(obj.perSex)
											cardTypeObj.setValue(obj.cardType)
											cardNumberObj.setValue(obj.cardNumber)
											phoneNumberObj.setValue(obj.phoneNumber)
											perEmailObj.setValue(obj.perEmail)
											personSFZZIdObj.setValue(obj.personSFZZId)
											personSFZFIdObj.setValue(obj.personSFZFId)
											if(null!=obj && obj.personSFZZId!=null && (obj.personSFZZExtendName == ".jpg" || obj.personSFZZExtendName == ".jpeg")){
												sfzzObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
																	+ getRootPath()
																	+ "/"
																	+ obj.personSFZZUrl
																	+ '" ondblclick=showPic("'
																	+ obj.personSFZZUrl
																	+ '") width =140 height=80  /></div>',false)
											}else if(null!=obj && obj.personSFZZId!=null && obj.personSFZZExtendName == ".pdf"){
												sfzzObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
																	+ getRootPath()
																	+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
																	+ obj.personSFZZId
																	+ ',"'
																	+ obj.personSFZZURL
																	+ '") width =140 height=80  /></div>',false)
											}
											
											if(null!=obj && obj.personSFZFId!=null && (obj.personSFZFExtendName == ".jpg" || obj.personSFZFExtendName == ".jpeg")){
												sfzfObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
																	+ getRootPath()
																	+ "/"
																	+ obj.personSFZFUrl
																	+ '" ondblclick=showPic("'
																	+ obj.personSFZFUrl
																	+ '") width =140 height=80  /></div>',false)
											}else if(null!=obj && obj.personSFZFId!=null && obj.personSFZFExtendName == ".pdf"){
												sfzfObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
																	+ getRootPath()
																	+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
																	+ obj.personSFZFId
																	+ ',"'
																	+ obj.personSFZFURL
																	+ '") width =140 height=80  /></div>',false)
											}
										}
										selectInvest(selectPerson);
									}
								}, {
								name : 'investPerson.perId',
								xtype : 'hidden'
							}]
						}, {
							columnWidth : 0.5, 
							layout : "form", 
							labelWidth : 70,
							items : [{
								xtype : "dickeycombo",
								nodeKey : 'sex_key',
								fieldLabel : '性别',
								columnWidth : 3,
								hiddenName : 'investPerson.perSex',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								editable : false,
								blankText : "性别不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == 0
													|| combox.getValue() == 1
													|| combox.getValue() == ""
													|| combox.getValue() == null) {
												combox.setValue("");
											} else {
												combox.setValue(combox
														.getValue());
											}
											combox.clearInvalid();
										})
									}
								}
							}]
						}, {
							columnWidth : 0.5, 
							layout : "form", 
							items : [{
								xtype : "diccombo",
								hiddenName : "investPerson.cardType",
								fieldLabel : "证件类型",
								readOnly : this.isAllReadOnly,
								itemName : '证件类型', 
								allowBlank : false,
								editable : false,
								emptyText : "请选择",
								blankText : "证件类型不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											if (combox.getValue() == ''
													|| combox.getValue() == null) {
												combox
														.setValue(st.getAt(0).data.itemId);
												combox.clearInvalid();
											} else {
												combox.setValue(combox
														.getValue());
												combox.clearInvalid();
											}
										})
									}
								}
							}]
						}, {
							columnWidth : 0.5, 
							layout : "form", 
							labelWidth : 70,
							items : [{
								xtype : "textfield",
								name : "investPerson.cardNumber",
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								fieldLabel : "证件号码",
								blankText : "证件号码不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									'blur' : function(f){
									var cardNumber = f.getValue();
									var personId = this.getCmpByName('investPerson.perId').getValue()?0:this.getCmpByName('investPerson.perId').getValue();
									Ext.Ajax.request({
					                   url:  __ctxPath + '/customer/verificationPersonInvestPerson.do',
					                   method : 'POST',
					                   scope : this,
					                   params : {
												cardNum : cardNumber,
												personId : personId
											},
					                  success : function(response,request) {
											var obj=Ext.util.JSON.decode(response.responseText);
			                        		if(obj.msg == false){					                            			
			                        			Ext.ux.Toast.msg('操作信息',"该证件号码已存在，请重新输入");
			                        			f.setValue("");
			                        		}
				                      }
			                       });  
									}
								}
							}]
						}, {
							columnWidth : 0.5, 
							layout : "form", 
							items : [{
								xtype : "textfield",
								name : "investPerson.phoneNumber",
								readOnly : this.isAllReadOnly,
								fieldLabel : "手机号码",
								anchor : "100%",
								regex : /^[1][34578][0-9]{9}$/,
								regexText : '手机号码格式不正确'
							}]
						}, {
							columnWidth : 0.5, 
							layout : "form", 
							labelWidth : 70,
							items : [{
								xtype : "textfield",
								name : "investPerson.perEmail",
								readOnly : this.isAllReadOnly,
								fieldLabel : "电子邮箱",
								anchor : "100%",
								regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
								regexText : '电子邮箱格式不正确',
								listeners : {
									afterrender : function(obj) {

										if (obj.getValue() == ""
												|| obj.getValue() == null) {
											Ext.apply(obj, {
														vtype : ""
													});
										}
									},
									'afterrender' : function(com) {
										com.clearInvalid();
									},
									blur : function(obj) {

										if (obj.getValue() == ""
												|| obj.getValue() == null) {
											Ext.apply(obj, {
														vtype : ""
													});
										} else {
											Ext.apply(obj, {
														vtype : 'email'
													});
										}
									}

								}
							}]
						}, {
							xtype : 'hidden',
							name : 'personSFZZId'
						}, {
							xtype : 'hidden',
							name : 'personSFZFId'
						}]
					}, {
						layout : 'column',
						xtype : 'fieldset',
						title : '法定代表人身份证扫描件',
						autoHeight : true,
						collapsible : false,
						anchor : anchor,
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
								items : [{
									xtype : 'label',
									style : 'padding-left :20px',
									html :this.isAllReadOnly == false
											? '身份证正面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnInvest(\'身份证正面\',\'cs_invest_person_sfzz\',\'shenfenzheng-z\',\'personSFZZId\',\'investEnterpriseForm\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_invest("personSFZZId","shenfenzheng-z",\'investEnterpriseForm\')>删除</a>'
											: '身份证正面'
								}, {
									name : 'shenfenzheng-z',
									xtype : 'label',
									style : 'padding-left :20px',
									html :'<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '/images/nopic.jpg" width =140 height=80/></div>'
											
								}]
							}, {
								columnWidth : 0.33,
								layout : 'form',
								labelWidth : 65,
								defaults : {
									anchor : '100%'
								},
								items : [{
									xtype : 'label',
									style : 'padding-left :10px',
									html : this.isAllReadOnly == false
											? '身份证反面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnInvest(\'身份证反面\',\'cs_invest_person_sfzf\',\'shenfenzheng-f\',\'personSFZFId\',\'investEnterpriseForm\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_invest("personSFZFId","shenfenzheng-f",\'investEnterpriseForm\')>删除</a>'
											: '身份证反面'
								}, {
									name : 'shenfenzheng-f',
									xtype : 'label',
									html : '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '/images/nopic.jpg"  width =140 height=80/></div>'
											
								}]
							}]
						}]
					}, {
						layout : "column",
						xtype : 'fieldset',
						title : '银行开户信息',
						collapsible : true,
						columnWidth : .5,
						items : [{
							xtype : 'button',
							text : '银行开户信息',
							width : 140,
							style : 'margin-top :10px;padding:10px',
							scope : this,
							handler : function() {
								var id=this.getCmpByName('investEnterprise.id').getValue();
								if (null==id || id=='') {
									Ext.ux.Toast.msg("友情提示", "先保存客户信息");
									return;
								} else {
									bankInfoWin(id, this.isAllReadOnly,1,0);
								}
							}
						}, {
							xtype : 'button',
							text : '客户关怀记录',
							width : 140,
							style : 'margin-top :10px;padding:10px',
							scope : this,
							handler : function() {
							var enterpriseid=this.getCmpByName('investEnterprise.id').getValue();
								if (null==enterpriseid || enterpriseid=='') {
									Ext.ux.Toast.msg("友情提示", "先保存客户信息");
									return;
								} else {
									new InvestEnterpriseCareForm({
										enterpriseId:enterpriseid,
										careHidden :true,
										isHiddenEdit:true,
										isHidden:true
									}).show();
									
							}
							}

						}]
					},{
						//layout : "column",
						xtype : 'fieldset',
						title : '联系人信息',
						collapsible : true,
						autoWidth : true,
						items : [this.customeLinkmanGridPanel]
					}]
				}]
		});
		// 加载表单对应的数据
	
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath
								+ '/customer/getInvestEnterprise.do?id='
								+ this.id,
						root : 'data',
						preName : ['investEnterprise','investPerson'],
						scope : this,
						success : function(resp, options) {
							var result = Ext.decode(resp.responseText);
							var enterprise=result.data.investEnterprise
							var yyzzObj=this.getCmpByName('yingyezhizhao')
							this.getCmpByName('enterpriseYyzzId').setValue(enterprise.enterpriseYyzzId)
							if(null!=enterprise && enterprise.enterpriseYyzzId!=null && (enterprise.enterpriseYyzzExtendName == ".jpg" || enterprise.enterpriseYyzzExtendName == ".jpeg")){
								yyzzObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="'
													+ getRootPath()
													+ "/"
													+ enterprise.enterpriseYyzzURL
													+ '" ondblclick=showPic("'
													+ enterprise.enterpriseYyzzURL
													+ '") width =140 height=80  /></div>',false)
							}else if(null!=enterprise && enterprise.enterpriseYyzzId!=null && enterprise.enterpriseYyzzExtendName == ".pdf"){
								yyzzObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+ enterprise.enterpriseYyzzId
													+ ',"'
													+ enterprise.enterpriseYyzzURL
													+ '") width =140 height=80  /></div>',false)
							}
							var zzjgObj=this.getCmpByName('zhuzhijigou')
							this.getCmpByName('enterpriseZzjgId').setValue(enterprise.enterpriseZzjgId)
							if(null!=enterprise && enterprise.enterpriseZzjgId!=null && (enterprise.enterpriseZzjgExtendName == ".jpg" || enterprise.enterpriseZzjgExtendName == ".jpeg")){
								zzjgObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="'
													+ getRootPath()
													+ "/"
													+ enterprise.enterpriseZzjgURL
													+ '" ondblclick=showPic("'
													+ enterprise.enterpriseZzjgURL
													+ '") width =140 height=80  /></div>',false)
							}else if(null!=enterprise && enterprise.enterpriseZzjgId!=null && enterprise.enterpriseZzjgExtendName == ".pdf"){
								zzjgObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+ enterprise.enterpriseZzjgId
													+ ',"'
													+ enterprise.enterpriseZzjgURL
													+ '") width =140 height=80  /></div>',false)
							}
							var swzObj=this.getCmpByName('shuiwuzheng')
							this.getCmpByName('enterpriseSwzId').setValue(enterprise.enterpriseSwzId)
							if(null!=enterprise && enterprise.enterpriseSwzId!=null && (enterprise.enterpriseSwzExtendName == ".jpg" || enterprise.enterpriseSwzExtendName == ".jpeg")){
								swzObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="'
													+ getRootPath()
													+ "/"
													+ enterprise.enterpriseSwzURL
													+ '" ondblclick=showPic("'
													+ enterprise.enterpriseSwzURL
													+ '") width =140 height=80  /></div>',false)
							}else if(null!=enterprise && enterprise.enterpriseSwzId!=null && enterprise.enterpriseSwzExtendName == ".pdf"){
								swzObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+ enterprise.enterpriseSwzId
													+ ',"'
													+ enterprise.enterpriseSwzURL
													+ '") width =140 height=80  /></div>',false)
							}
							var dkkObj=this.getCmpByName('daikuanka')
							this.getCmpByName('enterpriseDkkId').setValue(enterprise.enterpriseDkkId)
							if(null!=enterprise && enterprise.enterpriseDkkId!=null && (enterprise.enterpriseDkkExtendName == ".jpg" || enterprise.enterpriseDkkExtendName == ".jpeg")){
								dkkObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="'
													+ getRootPath()
													+ "/"
													+ enterprise.enterpriseDkkURL
													+ '" ondblclick=showPic("'
													+ enterprise.enterpriseDkkURL
													+ '") width =140 height=80  /></div>',false)
							}else if(null!=enterprise && enterprise.enterpriseDkkId!=null && enterprise.enterpriseDkkExtendName == ".pdf"){
								dkkObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+ enterprise.enterpriseDkkId
													+ ',"'
													+ enterprise.enterpriseDkkURL
													+ '") width =140 height=80  /></div>',false)
							}
							var person=result.data.investPerson
							var sfzzObj=this.getCmpByName('shenfenzheng-z')
							this.getCmpByName('personSFZZId').setValue(person.personSFZZId)
							if(null!=person && person.personSFZZId!=null && (person.personSFZZExtendName == ".jpg" || person.personSFZZExtendName == ".jpeg")){
								sfzzObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ "/"
													+ person.personSFZZUrl
													+ '" ondblclick=showPic("'
													+ person.personSFZZUrl
													+ '") width =140 height=80  /></div>',false)
							}else if(null!=person && person.personSFZZId!=null && person.personSFZZExtendName == ".pdf"){
								sfzzObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+ person.personSFZZId
													+ ',"'
													+ person.personSFZZURL
													+ '") width =140 height=80  /></div>',false)
							}
							var sfzfObj=this.getCmpByName('shenfenzheng-f')
							this.getCmpByName('personSFZFId').setValue(person.personSFZFId)
							if(null!=person && person.personSFZFId!=null && (person.personSFZFExtendName == ".jpg" || person.personSFZFExtendName == ".jpeg")){
								sfzfObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ "/"
													+ person.personSFZFUrl
													+ '" ondblclick=showPic("'
													+ person.personSFZFUrl
													+ '") width =140 height=80  /></div>',false)
							}else if(null!=person && person.personSFZFId!=null && person.personSFZFExtendName == ".pdf"){
								sfzfObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+ person.personSFZFId
													+ ',"'
													+ person.personSFZFURL
													+ '") width =140 height=80  /></div>',false)
							}
						}
					});
		}

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	
	/**
	 * 保存记录
	 */
	save : function() {
		var linkManJson=this.customeLinkmanGridPanel.getData();
		var gridpanel=this.gridpanel;
		var win=this;
		if(this.formPanel.getForm().isValid()){
			 this.formPanel.getForm().submit({
			    clientValidation: false, 
				url : __ctxPath+ '/customer/saveInvestEnterprise.do',
				params : {
					"linkManJson" : linkManJson,
					"businessType":this.businessType
				},
				method : 'post',
				waitMsg : '数据正在提交，请稍后...',
				success : function(fp, action) {
				 Ext.ux.Toast.msg('操作信息', '保存成功!');
				 win.close()
				   gridpanel.getStore().reload();
				   
				   
				},
				failure : function(fp, action) {
					Ext.MessageBox.show({
						title : '操作信息',
						msg : '信息保存出错，请联系管理员！',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
				}
			});
		}
	}// end of save

});

/*var selectSlPerson = function(slPersonMain){
	Ext.getCmp('farenId').setValue(slPersonMain.personMainId);
	Ext.getCmp('farenName').setValue(slPersonMain.name) ;
};*/