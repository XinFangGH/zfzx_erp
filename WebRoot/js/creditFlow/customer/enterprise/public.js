// 查看图片
// 删除图片
/**
 * 
 * @param {}
 *            fileId 要删除记录的ID or name
 * @param {}
 *            hiddenId 隐藏域 的ID
 * @param {}
 *            displayId 显示图片的ID
 * @param {}
 *            panelId 外部的ID
 * @return {Boolean}
 */
function getRootPath(){
    // 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    // 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    // 获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    // 获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}
var showPdf=function(fileid,src){
	new PdfTemplateView(fileid,src,null,null);
	return false;
}
var  uploadPhotoBtnEnterprise= function(title,mark,displayProfilePhoto,personPId,winID) {
	var  winobj=Ext.getCmp(winID);
	var panelObj=winobj.get(0);
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
			    permitted_extensions:['JPG','jpg','jpeg','JPEG','png','PNG'],  
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
								if(extendname==".jpg" || extendname==".jpeg"||extendname==".png"){
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
/**
 * 
 * @param {}
 *            title 上传标题
 * @param {}
 *            mark
 * @param {}
 *            displayProfilePhoto 显示图片的ID or name
 * @param {}
 *            personPId 保存附件id的 隐藏域的name or id
 * @param {}
 *            panelId formPanel的ID
 */
var enterpriseObj=Ext.extend(Ext.form.FormPanel, {
	  enterpriseId:null,
	  enterprise:null,
	  person:null,
	  winId:null,
	  isReadOnly:false,
	  constructor : function(_cfg) {
			if (_cfg == null)
			{
					 _cfg = {};
			};
			if(typeof(_cfg.enterpriseId)!="undefined")
			{
			      this.enterpriseId=_cfg.enterpriseId;
			}
			if(typeof(_cfg.winId)!="undefined")
			{
			      this.winId=_cfg.winId;
			}
		    if(typeof(_cfg.enterprise)!="undefined")
			{
			      this.enterprise=_cfg.enterprise;
			}
			if(typeof(_cfg.person)!="undefined")
			{
			      this.person=_cfg.person;
			}
			if(_cfg.isReadOnly)
			{
			      this.isReadOnly=_cfg.isReadOnly;
			}
			var enterprise=this.enterprise;
			var person=this.person;
			var isReadOnly=this.isReadOnly;
			Ext.applyIf(this, _cfg);
			this.initUIComponents();
			var panel_add=this;
			var winId=this.winId;
			var anchor='100%';
			var isEdit =true;//用来标示是否为添加页面   false表示添加页面
			if(this.enterpriseId==null||this.enterpriseId=="undefined"){
				isEdit=false;
			}
			enterpriseObj.superclass.constructor.call(this, {
						id:winId,
						labelAlign : 'right',
						buttonAlign : 'center',
						frame : true,
						monitorValid : true,
						isflag : enterprise==null?false:true,
						url : getRootPath() + '/creditFlow/customer/enterprise/ajaxAddEnterprise.do',
						monitorValid : true,
						labelWidth : 110,
						autoScroll : true,
						bodyStyle : 'overflowX:hidden',
						layout : 'form',
						border : false,
						items:[{
						layout : "column", // 定义该元素为布局为列布局方式
						defaults : {
							columnWidth : 1,
							isFormField : true
						},
						border : false,
						items : [{
							columnWidth : 0.8,
							items : [{
								layout : 'column',
								xtype : 'fieldset',
								title : '企业基本信息',
								autoHeight : true,
								collapsible : false,
								anchor : anchor,
								items : [{
									columnWidth : 1,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
											xtype : "hidden",
											value:enterprise==null?null:enterprise.id,
											name : 'enterprise.id'
									    },{
											xtype : "hidden",
											name:"enterpriseGsdjzId",
											value:enterprise==null?null:enterprise.enterpriseGsdjzId
									    },{
											xtype : "hidden",
											name:"enterpriseYyzzId",
											value:enterprise==null?null:enterprise.enterpriseYyzzId
									    },{
											xtype : "hidden",
											name:"enterpriseZzjgId",
											value:enterprise==null?null:enterprise.enterpriseZzjgId
									    },{
											xtype : "hidden",
											name:"enterpriseDsdjId",
											value:enterprise==null?null:enterprise.enterpriseDsdjId
									    },{
											xtype : "hidden",
											name:"enterpriseXztpyId",
											value:enterprise==null?null:enterprise.enterpriseXztpyId
                                    	},{
											xtype : "hidden",
											name:"enterpriseXztpeId",
											value:enterprise==null?null:enterprise.enterpriseXztpeId
                                    	},{
											xtype : "hidden",
											name:"enterpriseXztpsId",
											value:enterprise==null?null:enterprise.enterpriseXztpsId
                                    	},{
											xtype : "hidden",
											name:"enterpriseXztpssId",
											value:enterprise==null?null:enterprise.enterpriseXztpssId
                                    	},{
											xtype : "hidden",
											name:"enterprise.creater",
											value:enterprise==null?null:enterprise.creater
									    },{
											xtype : "hidden",
											name:"enterprise.createrId",
											value:enterprise==null?null:enterprise.createrId
									    },{
											xtype : "hidden",
											name:"enterprise.createdate",
											value:enterprise==null?null:enterprise.createdate
									    },{
											xtype : "hidden",
											name:"enterprise.companyId",
											value:enterprise==null?null:enterprise.companyId
									    },{
									    	xtype:'hidden',
									    	name:'enterprise.enterId',
									    	value:this.enterId
									    },{
										xtype : 'textfield',
										fieldLabel : '企业名称',
										allowBlank : false,
										readOnly:isReadOnly,
										blankText : '企业名称不允许空',
										emptyText : '请输入企业名称',
										name : 'enterprise.enterprisename',
										value:enterprise==null?null:enterprise.enterprisename,
										listeners : {
											'blur' : function() {
												existenterNameAction(this,"validateEnterNameAjaxValidator", "企业名称添加重复！",this.ownerCt.getCmpByName('enterprise.id').getValue());
											},
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
									}]
								}, {
									columnWidth : .5,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
										xtype : 'textfield',
										fieldLabel : '企业简称',
										name : 'enterprise.shortname',
										readOnly:isReadOnly,
										value:enterprise==null?null:enterprise.shortname,
										blankText : '企业简称不允许空',
										//emptyText : '请输入企业简称',
										listeners : {
											'blur' : function() {
												//existenterNameAction(this,"validatorEnterShortname","企业简称添加重复！");
														
											},
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
									}]
								}, {
									columnWidth : .5,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
										xtype : "combo",
										triggerClass : 'x-form-search-trigger',
										fieldLabel : "行业类别",
										//id:'hangyeName',
										name : 'enterprise.hangyeName',
								        hiddenName : 'enterprise.hangyeName',
										scope : this,
										editable : false,
										allowBlank : false,
										emptyText : '请选择行业类别',
										readOnly:isReadOnly,
										scope:true,
										value:enterprise==null?null:enterprise.hangyeName,
										onTriggerClick : function(e) {
											var obj = this;
											var oobbj=this.nextSibling();
											selectTradeCategory(obj,oobbj);
										/*	var com=this;
									var selectBankLinkMan = function(array){
										Ext.getCmp('hangyeType').setValue(array[0].id) ;
										com.setValue(array[0].text);
										Ext.getCmp('rootHangYeType').setValue(array[array.length-1].id) 
									};
									selectDictionary('hangye',selectBankLinkMan);*/
										}
									},{
										//id:'hangyeType',
										xtype : "hidden",
										value:enterprise==null?null:enterprise.hangyeType,
										name : 'enterprise.hangyeType'
										
									},{
										//id:'rootHangYeType',
										xtype : "hidden",
										value:enterprise==null?null:enterprise.rootHangYeType,
										name : 'enterprise.rootHangYeType'
									}/*,{
										xtype : "hidden",
										id:'hangyeName1',	
										value:enterprise==null?null:enterprise.hangyeName,
										name : 'enterprise.hangyeName'
										
									}*/]
								}, {
									columnWidth : .5,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
										xtype : 'combo',
										fieldLabel : '证件类型',
										readOnly:isReadOnly || (enterprise == null?false:enterprise.isCardcodeReadOnly),
										allowBlank : false,
										mode : 'local',
										editable : false,
										displayField : 'typeValue',
						                valueField : 'typeId',
						                triggerAction : 'all',
										value:enterprise==null?null:enterprise.documentType,
										hiddenName : 'enterprise.documentType',
										store : new Ext.data.SimpleStore({
								        data : [['三证合一',1],['非三证合一',2]],
								        fields:['typeValue','typeId']
							            }),
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var combo = this.getCmpByName('enterprise.organizecode'); 
												if(combox.getValue() == 1){
//													var a = combo.el.parent().parent().first();
//									 				a.dom.innerHTML ="<font color='red'>*</font>社会信用代码";
													combo.allowBlank=false;
									 				combo.fieldLabel = '社会信用代码';
													this.findById('cciaa').setVisible(false);  
													this.findById('taxnum').setVisible(false);  
												}else{
//													var a = combo.el.parent().parent().first();
//									 				a.dom.innerHTML ="<font color='red'>*</font>组织机构代码";
													combo.allowBlank=false;
									 				combo.fieldLabel = '组织机构代码';
													this.findById('cciaa').setVisible(true);  
													this.findById('taxnum').setVisible(true);  
												}
											},
											select:function(combox){
												var combo = this.getCmpByName('enterprise.organizecode'); 
												if(combox.getValue() == 1){
													var a = combo.el.parent().parent().first();
													combo.allowBlank=false;
									 				a.dom.innerHTML ="<font color='red'>*</font>社会信用代码";
									 				combo.fieldLabel = '社会信用代码';
													this.findById('cciaa').setVisible(false);  
													this.findById('taxnum').setVisible(false);  
												}else{
													var a = combo.el.parent().parent().first();
													combo.allowBlank=false;
									 				a.dom.innerHTML ="<font color='red'>*</font>组织机构代码";
									 				combo.fieldLabel = '组织机构代码';
													this.findById('cciaa').setVisible(true);  
													this.findById('taxnum').setVisible(true);  
												}
											}
										}
									}]
								}, {
									columnWidth : .5,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
										xtype : 'textfield',
										fieldLabel : '组织机构代码',
										readOnly:isReadOnly || (enterprise == null?false:enterprise.isCardcodeReadOnly),
										allowBlank : false,
										//emptyText : '请输入组织机构代码',
									//	blankText : '组织机构代码不允许空',
									//	regex : /^[A-Za-z0-9]{8}-[A-Za-z0-9]{1}/ ,
										regexText : '组织机构代码格式不正确',
										value:enterprise==null?null:enterprise.organizecode,
										name : 'enterprise.organizecode',
										listeners : {
											'blur' : function(f) {
												if(f.getValue() != '' && enterprise == null){
													existenterNameAction(this,"validatorEnterOrganizecodeAjaxValidator","代码添加重复！");
												}else if(f.getValue() != ''){
													existenterNameAction(this,"validatorEnterOrganizecodeAjaxValidator","代码添加重复！",enterprise.id);
												}
											},
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
									}]
								}, {
									columnWidth : .5,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									id:'cciaa',
									items : [{
										xtype : 'textfield',
										fieldLabel : '营业执照号码',
										blankText : "营业执照号码不能为空，请正确填写!",
										allowBlank : true,
										//emptyText : '请输入营业执照号码',
										readOnly:isReadOnly,
										regex : /^[0-9]*$/ ,
										regexText : '营业执照号码格式不正确',
										value:enterprise==null?null:enterprise.cciaa,
										name : 'enterprise.cciaa',
										listeners : {
											/*'blur' : function() {
												if(f.getValue() != ''){
													existenterNameAction(this,"validatorCciaaAjaxValidator","营业执照号码不能重复！");
												}
											},*/
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
									}]
								},{
									columnWidth:.5,
									layout:'form',
									defaults  :{
										anchor : anchor
									},
									id:'taxnum',
									items:[{
										xtype:'textfield',
									//	emptyText : '请输入税务登记号码',
										blankText : '税务登记号码不允许空',
										readOnly:isReadOnly,
										allowBlank : true,
										regex : /^[0-9]*$/ ,
										regexText : '税务登记号码格式不正确',
										fieldLabel:'税务登记号码',
										name:'enterprise.taxnum',
										value:enterprise==null?null:enterprise.taxnum
									}]
								},{
									columnWidth : .25,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
										xtype : 'textfield',
										fieldLabel : '企业联系人',
										 readOnly:isReadOnly,
										 value:enterprise==null?null:enterprise.linkman,
										name : 'enterprise.linkman'
									}]
								}, {
									columnWidth : .25,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									labelWidth : 60,
									items : [{
												 fieldLabel : '联系电话',
												 xtype : 'textfield',
												 readOnly:isReadOnly,
												 allowBlank : false,
												 blankText : '联系电话不允许空',
												 emptyText : '请输入联系电话',
												 value:enterprise==null?null:enterprise.telephone,
												 name : 'enterprise.telephone',
												// regex : /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
												regex : /^[1][34578][0-9]{9}$/,
											    regexText : '联系电话格式不正确，应为手机号'	,
												listeners : {
													scope:this,
											        'blur' : function(f) {
												     var reg=/^[1][34578][0-9]{9}$/;
												     var flag=reg.test(this.getCmpByName('enterprise.telephone').getValue());
												     if(!flag){
												     Ext.Msg.alert('操作信息','联系电话格式不正确，应为手机号');
													 this.getCmpByName('enterprise.telephone').setValue(null);
												}
											}
											}
											}]
								},  {
									columnWidth : .5,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
										xtype : 'numberfield',
										fieldLabel : '工商注册资金(万)',
										readOnly:isReadOnly,
										value:enterprise==null?null:enterprise.registermoney,
										name : 'enterprise.registermoney'
									}]
								},{
									columnWidth : .4,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
										xtype : 'dickeycombo',
										nodeKey : 'smallloan_haveCharcter',
										fieldLabel : '所有制性质',
										blankText : '必填信息',
										readOnly:isReadOnly,
									    value:enterprise==null?null:enterprise.ownership,
										anchor : '100%',
										editable : false,
										hiddenName : 'enterprise.ownership',
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox	.getValue());
													combox.clearInvalid();
												})
											}
										}
									}]
								}, {
									columnWidth : .3,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
												xtype : 'datefield',
												fieldLabel : '企业成立日期',
												format : 'Y-m-d',
												readOnly:isReadOnly,
												value:enterprise==null?null:enterprise.opendate,
												name : 'enterprise.opendate'
											}]
								},{
									columnWidth : .3,
									layout : 'form',
									labelWidth : 70,
									defaults : {
										anchor : anchor
									},
									items : [{
													xtype : "dickeycombo",
													nodeKey : 'workforce',
													hiddenName : "enterprise.employeetotal",
													fieldLabel : "职工人数",
													readOnly:isReadOnly,
													editable : false,
													value:enterprise==null?null:enterprise.employeetotal,
													itemName : '职工人数', // xx代表分类名称
													anchor : "100%",
													listeners : {
														afterrender : function(combox) {
															var st = combox.getStore();
															st.on("load", function() {
																combox.setValue(combox.getValue());
				                                                combox.clearInvalid ();
															})
														}
													}
												}]
								},{
								    columnWidth : .32,
								    layout : 'form',
								    defaults : {
										anchor : anchor
									},
									items : [{
										xtype : "dickeycombo",
										hiddenName : "enterprise.jyplace",
										nodeKey : 'jycs', // xx代表分类名称
										fieldLabel : "经营场所",
										emptyText : "请选择",
										editable : false,
										readOnly:isReadOnly,
										value:enterprise==null?null:enterprise.jyplace,
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													
													combox.setValue(combox.getValue());
													combox.clearInvalid();
												})
									       }
										}
									}]
								},{
								    columnWidth : .14,
								    layout : 'form',
								    defaults : {
										anchor : anchor
									},
									labelWidth : 40,
									items : [{
										xtype : 'numberfield',
										fieldLabel : '面积',
										fieldClass : 'field-align',
										style : {
											imeMode : 'disabled'
										},
										readOnly:isReadOnly,
										value:enterprise==null?null:enterprise.areaMeasure,
										name :'enterprise.areaMeasure'
									}]
								}, {
									columnWidth : .06, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									defaults : {
										anchor : anchor
									},
									labelWidth :30,
									items : [{
										fieldLabel : "平米 ",
										labelSeparator : ''
									}]
								},{
								    columnWidth : .15,
								    layout : 'form',
								    defaults : {
										anchor : anchor
									},
									labelWidth : 40,
									items : [{
										xtype : 'numberfield',
										fieldLabel : '房租',
										fieldClass : 'field-align',
										style : {
											imeMode : 'disabled'
										},
										readOnly:isReadOnly,
										value:enterprise==null?null:enterprise.rent,
										name :'enterprise.rent'
									}]
								}, {
									columnWidth : .03, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									defaults : {
										anchor : anchor
									},
									labelWidth : 20,
									items : [{
										fieldLabel : "元 ",
										labelSeparator : ''
									}]
								},{
									columnWidth : .3,
									layout : 'form',
									items :[{
										xtype : 'radiogroup',
										fieldLabel : '进/出口许可证',
										items : [{
											boxLabel : '有',
											name : 'enterprise.isLicense',
											inputValue : true,
											checked : enterprise==null?null:enterprise.isLicense
										}, {
											boxLabel : '无',
											name : 'enterprise.isLicense',
											inputValue : false,
											checked : !(enterprise==null?null:enterprise.isLicense)
										}]								
									}]
								},{
									columnWidth : .4,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
										xtype : 'textfield',
										fieldLabel : '经营所在地城市',
										readOnly:isReadOnly,
										value:enterprise==null?null:enterprise.managecityName,
										scope:this,
										listeners : {
											'focus' : function() { 
										       var obj=this;
										       var getEnterAreaObjArray1=function(objArray){
										    	    
										    	   	obj.setValue(objArray[(objArray.length) - 1].text+ "_" + objArray[(objArray.length) - 2].text + "_"+ objArray[0].text);
												    obj.nextSibling().setValue(objArray[(objArray.length) - 1].id+ "," + objArray[(objArray.length) - 2].id + "," + objArray[0].id);
										    	   
										       }
											   selectDictionary('area',getEnterAreaObjArray1);
											}
										}
									}, {
										name : 'enterprise.managecity',
										value:enterprise==null?null:enterprise.managecity,
										xtype : 'hidden'
									}]
								}, {
									columnWidth : .3,
									layout : 'form',
									labelWidth : 70,
									defaults : {
										anchor : anchor
									},
									items : [{
												xtype : 'textfield',
												fieldLabel : '电子邮箱',
												format : 'Y-m-d',
												readOnly:isReadOnly,
												value:enterprise==null?null:enterprise.email,
												name : 'enterprise.email',
												regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
												regexText : '电子邮箱格式不正确'
											}]
								},{
									columnWidth : .3,
									layout : 'form',
									labelWidth : 70,
									defaults : {
										anchor : anchor
									},
									items : [{
												fieldLabel : '收件人',
												xtype : 'textfield',
												readOnly:isReadOnly,
												value:enterprise==null?null:enterprise.receiveMail,
												name : 'enterprise.receiveMail'
											}]
								},{
									columnWidth : .4,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
												xtype : 'textfield',
												fieldLabel : '通讯地址',
												readOnly:isReadOnly,
												//allowBlank : false,
												value:enterprise==null?null:enterprise.area,
												name : 'enterprise.area',
											    listeners : {
													'afterrender':function(com){
													    com.clearInvalid();
													}
												}
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									labelWidth : 70,
									items : [{
												fieldLabel : '邮政编码',
												xtype : 'textfield',
												readOnly:isReadOnly,
												//allowBlank : false,
												regex : /^[0-9]{6}$/,
												regexText : '邮政编码格式不正确',
												value:enterprise==null?null:enterprise.postcoding,
												name : 'enterprise.postcoding'/*,
											    listeners : {
													'afterrender':function(com){
													    com.clearInvalid();
													}
												}*/
											}]
								},{
									columnWidth : .3,
									layout : 'form',
									labelWidth:70,
									defaults : {
										anchor : anchor
									},
									items : [{
										xtype : "dickeycombo",
										hiddenName : "enterprise.customerLevel",
										nodeKey : 'customerLevel', // xx代表分类名称
										fieldLabel : "客户级别",
										editable : false,
										readOnly : (isReadOnly == false && isGranted('_editCustomerLevel_qykh'))?false:true,
										emptyText : "请选择",
										value:enterprise==null?null:enterprise.customerLevel,
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													
													combox.setValue(combox.getValue());
													combox.clearInvalid();
												})
									       }
										}
									}]
								},{
									columnWidth : .5,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
										xtype : 'dickeycombo',
										nodeKey : 'capitalkind',
										fieldLabel : '注册资金币种',
										readOnly:isReadOnly,
										value:enterprise==null?null:enterprise.capitalkind,
										hiddenName : 'enterprise.capitalkind',
										listeners : {
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
													combox.clearInvalid();
												})
											}
										}
									}]
								},{
									columnWidth : .5,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
												xtype : 'textfield',
												fieldLabel : '实际经营地址',
												readOnly:isReadOnly,
												allowBlank : false,
												value:enterprise==null?null:enterprise.factaddress,
												name : 'enterprise.factaddress'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [ {
												fieldLabel : '企业网址',
												xtype : 'textfield',
												readOnly:isReadOnly,
												value:enterprise==null?null:enterprise.website,
												name : 'enterprise.website'
											}]
								},{
									columnWidth : .5,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
												fieldLabel : '传真',
												xtype : 'textfield',
												readOnly:isReadOnly,
												value:enterprise==null?null:enterprise.fax,
												name : 'enterprise.fax'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden:true,
									defaults : {
										anchor : anchor
									},
									items : [{
											xtype : "hidden",
											value:enterprise==null?null:enterprise.belongedId,
											name : 'enterprise.belongedId'
										},{
											hiddenName : 'belongedName',
											xtype:'trigger',
											fieldLabel : '客户授权人',
											submitValue : true,
											triggerClass :'x-form-search-trigger',
											editable : false,
											
											//readOnly : isReadOnly?true:isGranted('_editBelongeder_qykh')?false:true,
											value:enterprise==null?null:enterprise.belongedName,
											scope : this,
											onTriggerClick : function(){
											var obj = this;
											var belongedObj = obj.previousSibling();
											var userIds = belongedObj.getValue();
											if (null==obj.getValue() || "" == obj.getValue()) {
												userIds = "";
											}
											new UserDialog({
											  	userIds : userIds,
											  	userName : obj.getValue(),
												single : false,
												title:"客户授权人",
												callback : function(uId, uname) {
													if((!isEdit)&&((","+uId+",").indexOf(","+curUserInfo.userId+",")==-1)){
														uId=uId+","+curUserInfo.userId
														uname=uname+","+curUserInfo.fullname
													}
													/*uId=uId+","+curUserInfo.userId
													uname=uname+","+curUserInfo.fullname*/
													obj.setRawValue(uname);
													belongedObj.setValue(uId);
												}
											}).show();
										}
									}]
								},{
									columnWidth : 1,
									layout : 'form',
									defaults : {
										anchor : anchor
									},
									items : [{
										fieldLabel : '门店名称',
										anchor : '100%',
										xtype : 'combo',
//										readOnly : this.isRead,
										readOnly:isReadOnly,
										editable : false,
										triggerClass : 'x-form-search-trigger',
										hiddenName : "enterprise.shopName",
										value : enterprise == null
												? null
												: enterprise.shopName,
										onTriggerClick : function() {
											var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
											var EnterpriseNameStockUpdateNew = function(obj) {
												if (null != obj.orgName && "" != obj.orgName)
													op.getCmpByName('enterprise.shopName').setValue(obj.orgName);
												if (null != obj.orgId && "" != obj.orgId)
													op.getCmpByName('enterprise.shopId').setValue(obj.orgId);
											}
											selectShop(EnterpriseNameStockUpdateNew);
										}
									},{
										xtype:'hidden',
										name:'enterprise.shopId',
										value : enterprise == null
												? null
												: enterprise.shopId
									}]
								}
								]
							}
							, {
								layout : 'column',
								xtype : 'fieldset',
								title : '公司资料扫描件',
								autoHeight : true,
								collapsible : false,
								anchor : anchor,
								items : [
								{
							columnWidth :1,
							layout : 'column',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							scope:this,
							items : [{
								columnWidth :0.25,
								layout : 'form',
								labelWidth : 25,
								defaults : {
									anchor : '100%'
								},
								items : [{
										xtype :'label',
										style :'padding-left :10px',
										scope:this,
										html :
                                            function () {
                                                if (enterprise != null) {
                                                    return isReadOnly == false ? '<input name="yyzz" style="width:112px;" value=' + enterprise.yyzz + ' >&nbsp;&nbsp;&nbsp;<a href="#"   onClick =uploadPhotoBtnEnterprise(\'营业执照副本\',\'cs_enterprise_zzjgdmz\',\'yingyezhizhao\',\'enterpriseYyzzId\',\'' + winId + '\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseYyzzId\',\'yingyezhizhao\',\'' + winId + '\')>删除</a>' : enterprise.yyzz
                                                } else {
                                                    return isReadOnly == false ? '<input name="yyzz" style="width:112px;" value="" >&nbsp;&nbsp;&nbsp;<a href="#"   onClick =uploadPhotoBtnEnterprise(\'营业执照副本\',\'cs_enterprise_zzjgdmz\',\'yingyezhizhao\',\'enterpriseYyzzId\',\'' + winId + '\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseYyzzId\',\'yingyezhizhao\',\'' + winId + '\')>删除</a>' : "请输入图片名称"
                                                }
                                            }()
								},{
										name:'yingyezhizhao',
										xtype :'label',
										style :'padding-left :20px',
										html :function(){
											if(enterprise==null || enterprise.enterpriseYyzzId==0){
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
											+ getRootPath()
											+ '/images/nopic.jpg" width =140 height=80/></div>'
											}else if(null!=enterprise && enterprise.enterpriseYyzzId!=0 && (enterprise.enterpriseYyzzExtendName==".jpg" || enterprise.enterpriseYyzzExtendName==".jpeg")){
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="' + getRootPath()
												+"/"+ enterprise.enterpriseYyzzURL+'" ondblclick=showPic("'+enterprise.enterpriseYyzzURL+'") width =140 height=80  /></div>'
											}else if(null!=enterprise && enterprise.enterpriseYyzzId!=0 && enterprise.enterpriseYyzzExtendName==".pdf"){
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+getRootPath()+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+enterprise.enterpriseYyzzId+',"'+enterprise.enterpriseYyzzURL+'") width =140 height=80  /></div>'
											}
										}()
									}]
							},{
								columnWidth :0.25,
								layout : 'form',
								labelWidth : 25,
								defaults : {
									anchor : '100%'
								},
								items : [{
										xtype :'label',
										style :'padding-left :10px',
										html :
                                            function () {
                                                if (enterprise != null) {
                                                    return isReadOnly==false?'<input name="zzjg" style="width:112px;" value='+enterprise.zzjg+' >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'组织机构代码证\',\'cs_enterprise_zzjgdmz\',\'zhuzhijigou\',\'enterpriseZzjgId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseZzjgId\',\'zhuzhijigou\',\''+winId+'\')>删除</a>':enterprise.zzjg
                                                } else {
                                                    return isReadOnly==false?'<input name="zzjg" style="width:112px;" value="" >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'组织机构代码证\',\'cs_enterprise_zzjgdmz\',\'zhuzhijigou\',\'enterpriseZzjgId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseZzjgId\',\'zhuzhijigou\',\''+winId+'\')>删除</a>':"请输入图片名称"
                                                }
                                            }()
											},{
										name:"zhuzhijigou",
										xtype :'label',
										html : function(){	
											if(enterprise==null || enterprise.enterpriseZzjgId==0){
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
											+ getRootPath()
											+ '/images/nopic.jpg" width =140 height=80/></div>'
											}else if(null!=enterprise && enterprise.enterpriseZzjgId!=0 && (enterprise.enterpriseZzjgExtendName==".jpg" || enterprise.enterpriseZzjgExtendName==".jpeg")){
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="' + getRootPath()
												+"/"+ enterprise.enterpriseZzjgURL+'" ondblclick=showPic("'+enterprise.enterpriseZzjgURL+'") width =140 height=80  /></div>'
											}else if(null!=enterprise && enterprise.enterpriseZzjgId!=0 && enterprise.enterpriseZzjgExtendName==".pdf"){
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+getRootPath()+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+enterprise.enterpriseZzjgId+',"'+enterprise.enterpriseZzjgURL+'") width =140 height=80  /></div>'
											}
										
										}()
										
									}]
							},{
								columnWidth :0.25,
								layout : 'form',
								labelWidth :25,
								defaults : {
									anchor : '100%'
								},
								items : [{
										xtype :'label',
										style :'padding-left :10px',
										html :
                                            function () {
                                                if (enterprise != null) {
                                                    return isReadOnly==false?'<input name="swzj" style="width:112px;" value='+enterprise.swzj+' >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'税务证扫描件\',\'cs_enterprise_gsdjz\',\'guoshuidengji\',\'enterpriseGsdjzId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseGsdjzId\',\'guoshuidengji\',\''+winId+'\')>删除</a>':enterprise.swzj
                                                } else {
                                                    return isReadOnly==false?'<input name="swzj" style="width:112px;" value="" >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'税务证扫描件\',\'cs_enterprise_gsdjz\',\'guoshuidengji\',\'enterpriseGsdjzId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseGsdjzId\',\'guoshuidengji\',\''+winId+'\')>删除</a>':"请输入图片名称"
                                                }
                                            }()
												},{
										name:'guoshuidengji',
										xtype :'label',
										html : function(){
												
											if(enterprise==null || enterprise.enterpriseGsdjzId==0){
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
											+ getRootPath()
											+ '/images/nopic.jpg" width =140 height=80/></div>'
											}else if(null!=enterprise && enterprise.enterpriseGsdjzId!=0 && (enterprise.enterpriseGsdjzExtendName==".jpg" || enterprise.enterpriseGsdjzExtendName==".jpeg")){
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="' + getRootPath()
												+"/"+ enterprise.enterpriseGsdjzURL+'" ondblclick=showPic("'+enterprise.enterpriseGsdjzURL+'") width =140 height=80  /></div>'
											}else if(null!=enterprise && enterprise.enterpriseGsdjzId!=0 && enterprise.enterpriseGsdjzExtendName==".pdf"){
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+getRootPath()+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+enterprise.enterpriseGsdjzId+',"'+enterprise.enterpriseGsdjzURL+'") width =140 height=80  /></div>'
											}
										
										
										}()
									}]
							},{
								columnWidth :0.25,
								layout : 'form',
								labelWidth : 25,
								defaults : {
									anchor : '100%'
								},
								items : [{
										xtype :'label',
										style :'padding-left :10px',
										html :
                                            function () {
                                                if (enterprise != null) {
                                                    return isReadOnly==false?'<input name="dkk" style="width:112px;" value='+enterprise.dkk+' >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'贷款卡扫描件\',\'cs_enterprise_dsdjz\',\'dishuidengji\',\'enterpriseDsdjId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseDsdjId\',\'dishuidengji\',\''+winId+'\')>删除</a>':enterprise.dkk
                                                } else {
                                                    return isReadOnly==false?'<input name="dkk" style="width:112px;" value="" >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'贷款卡扫描件\',\'cs_enterprise_dsdjz\',\'dishuidengji\',\'enterpriseDsdjId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseDsdjId\',\'dishuidengji\',\''+winId+'\')>删除</a>':"请输入图片名称"
                                                }
                                            }()
												},{
										name:'dishuidengji',
										xtype :'label',
										html : function(){	
											if(enterprise==null || enterprise.enterpriseDsdjId==0){
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
											+ getRootPath()
											+ '/images/nopic.jpg" width =140 height=80/></div>'
											}else if(null!=enterprise && enterprise.enterpriseDsdjId!=0 && (enterprise.enterpriseDsdjExtendName==".jpg" || enterprise.enterpriseDsdjExtendName==".jpeg")){
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="' + getRootPath()
												+"/"+ enterprise.enterpriseDsdjURL+'" ondblclick=showPic("'+enterprise.enterpriseDsdjURL+'") width =140 height=80  /></div>'
											}else if(null!=enterprise && enterprise.enterpriseDsdjId!=0 && enterprise.enterpriseDsdjExtendName==".pdf"){
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+getRootPath()+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+enterprise.enterpriseDsdjId+',"'+enterprise.enterpriseDsdjURL+'") width =140 height=80  /></div>'
											}
										}()
									}]
							},{
                                columnWidth :0.25,
                                layout : 'form',
                                labelWidth :30,
                                defaults : {
                                    anchor : '100%'
                                },
                                items : [{
                                    xtype :'label',
                                    style :'padding-left :10px',
                                    html :
                                        function () {
                                            if (enterprise != null) {
                                                return isReadOnly==false?'<input name="xzmc1" style="width:112px;" value='+enterprise.xzmc1+' >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'税务证扫描件1\',\'cs_enterprise_xxtpy\',\'guoshuidengyi\',\'enterpriseXztpyId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseXztpyId\',\'guoshuidengyi\',\''+winId+'\')>删除</a>':enterprise.xzmc1
                                            } else {
                                                return isReadOnly==false?'<input name="xzmc1" style="width:112px;" value="" >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'税务证扫描件1\',\'cs_enterprise_xxtpy\',\'guoshuidengyi\',\'enterpriseXztpyId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseXztpyId\',\'guoshuidengyi\',\''+winId+'\')>删除</a>':"请输入图片名称"
                                            }
                                        }()
										    },{
                                    name:'guoshuidengyi',
                                    xtype :'label',
                                    html : function(){

                                        if(enterprise==null || enterprise.enterpriseXztpyId==0){
                                            return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
                                                + getRootPath()
                                                + '/images/nopic.jpg" width =140 height=80/></div>'
                                        }else if(null!=enterprise && enterprise.enterpriseXztpyId!=0 && (enterprise.enterpriseXztpyExtendName==".jpg" || enterprise.enterpriseXztpyExtendName==".jpeg")){
                                            return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="' + getRootPath()
                                                +"/"+ enterprise.enterpriseXztpyURL+'" ondblclick=showPic("'+enterprise.enterpriseXztpyURL+'") width =140 height=80  /></div>'
                                        }else if(null!=enterprise && enterprise.enterpriseXztpyId!=0 && enterprise.enterpriseXztpyExtendName==".pdf"){
                                            return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+getRootPath()+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+enterprise.enterpriseXztpyId+',"'+enterprise.enterpriseXztpyURL+'") width =140 height=80  /></div>'
                                        }
                                    }()
                                }]
                            },{
                                columnWidth :0.25,
                                layout : 'form',
                                labelWidth :25,
                                defaults : {
                                    anchor : '100%'
                                },
                                items : [{
                                    xtype :'label',
                                    style :'padding-left :10px',
                                    html :
                                        function () {
                                            if (enterprise != null) {
                                                return isReadOnly==false?'<input name="xzmc2" style="width:112px;" value='+enterprise.xzmc2+' >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'税务证扫描件2\',\'cs_enterprise_xxtpe\',\'guoshuidenger\',\'enterpriseXztpeId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseXztpeId\',\'guoshuidenger\',\''+winId+'\')>删除</a>':enterprise.xzmc2
                                            } else {
                                                return isReadOnly == false ? '<input name="xzmc2" style="width:112px;" value="" >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'税务证扫描件2\',\'cs_enterprise_xxtpe\',\'guoshuidenger\',\'enterpriseXztpeId\',\'' + winId + '\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseXztpeId\',\'guoshuidenger\',\'' + winId + '\')>删除</a>' : "请输入图片名称"
                                            }
                                        }()
										        },{
                                    name:'guoshuidenger',
                                    xtype :'label',
                                    html : function(){

                                        if(enterprise==null || enterprise.enterpriseXztpeId==0){
                                            return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
                                                + getRootPath()
                                                + '/images/nopic.jpg" width =140 height=80/></div>'
                                        }else if(null!=enterprise && enterprise.enterpriseXztpeId!=0 && (enterprise.enterpriseXztpeExtendName==".jpg" || enterprise.enterpriseXztpeExtendName==".jpeg")){
                                            return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="' + getRootPath()
                                                +"/"+ enterprise.enterpriseXztpeURL+'" ondblclick=showPic("'+enterprise.enterpriseXztpeURL+'") width =140 height=80  /></div>'
                                        }else if(null!=enterprise && enterprise.enterpriseXztpeId!=0 && enterprise.enterpriseXztpeExtendName==".pdf"){
                                            return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+getRootPath()+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+enterprise.enterpriseXztpeId+',"'+enterprise.enterpriseXztpeURL+'") width =140 height=80  /></div>'
                                        }


                                    }()
                                }]
                            },{
                                columnWidth :0.25,
                                layout : 'form',
                                labelWidth :25,
                                defaults : {
                                    anchor : '100%'
                                },
                                items : [{
                                    xtype :'label',
                                    style :'padding-left :10px',
                                    html :
                                        function () {
                                            if (enterprise != null) {
                                                return isReadOnly==false?'<input name="xzmc3" style="width:112px;" value='+enterprise.xzmc3+' >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'税务证扫描件3\',\'cs_enterprise_xxtps\',\'guoshuidengsan\',\'enterpriseXztpsId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseXztpsId\',\'guoshuidengsan\',\''+winId+'\')>删除</a>':enterprise.xzmc3
                                            } else {
                                                return isReadOnly==false?'<input name="xzmc3" style="width:112px;" value="" >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'税务证扫描件3\',\'cs_enterprise_xxtps\',\'guoshuidengsan\',\'enterpriseXztpsId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseXztpsId\',\'guoshuidengsan\',\''+winId+'\')>删除</a>':"请输入图片名称"
                                            }
                                        }()
										      },{
                                    name:'guoshuidengsan',
                                    xtype :'label',
                                    html : function(){

                                        if(enterprise==null || enterprise.enterpriseXztpsId==0){
                                            return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
                                                + getRootPath()
                                                + '/images/nopic.jpg" width =140 height=80/></div>'
                                        }else if(null!=enterprise && enterprise.enterpriseXztpsId!=0 && (enterprise.enterpriseXztpsExtendName==".jpg" || enterprise.enterpriseXztpsExtendName==".jpeg")){
                                            return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="' + getRootPath()
                                                +"/"+ enterprise.enterpriseXztpsURL+'" ondblclick=showPic("'+enterprise.enterpriseXztpsURL+'") width =140 height=80  /></div>'
                                        }else if(null!=enterprise && enterprise.enterpriseXztpsId!=0 && enterprise.enterpriseXztpsExtendName==".pdf"){
                                            return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+getRootPath()+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+enterprise.enterpriseXztpsId+',"'+enterprise.enterpriseXztpsURL+'") width =140 height=80  /></div>'
                                        }


                                    }()
                                }]
                            },{
                                columnWidth :0.25,
                                layout : 'form',
                                labelWidth :25,
                                defaults : {
                                    anchor : '100%'
                                },
                                items : [{
                                    xtype :'label',
                                    style :'padding-left :10px',
                                    html :
                                        function () {
                                            if (enterprise != null) {
                                                return isReadOnly==false?'<input name="xzmc4" style="width:112px;" value='+enterprise.xzmc4+' >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'税务证扫描件4\',\'cs_enterprise_xxtpss\',\'guoshuidengss\',\'enterpriseXztpssId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseXztpssId\',\'guoshuidengss\',\''+winId+'\')>删除</a>':enterprise.xzmc4
                                            } else {
                                                return isReadOnly==false?'<input name="xzmc4" style="width:112px;" value="" >&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnEnterprise(\'税务证扫描件4\',\'cs_enterprise_xxtpss\',\'guoshuidengss\',\'enterpriseXztpssId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'enterpriseXztpssId\',\'guoshuidengss\',\''+winId+'\')>删除</a>':"请输入图片名称"
                                            }
                                        }()
										     },{
                                    name:'guoshuidengss',
                                    xtype :'label',
                                    html : function(){

                                        if(enterprise==null || enterprise.enterpriseXztpssId==0){
                                            return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
                                                + getRootPath()
                                                + '/images/nopic.jpg" width =140 height=80/></div>'
                                        }else if(null!=enterprise && enterprise.enterpriseXztpssId!=0 && (enterprise.enterpriseXztpssExtendName==".jpg" || enterprise.enterpriseXztpssExtendName==".jpeg")){
                                            return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;border:1px solid #46A900;"><img src="' + getRootPath()
                                                +"/"+ enterprise.enterpriseXztpssURL+'" ondblclick=showPic("'+enterprise.enterpriseXztpssURL+'") width =140 height=80  /></div>'
                                        }else if(null!=enterprise && enterprise.enterpriseXztpssId!=0 && enterprise.enterpriseXztpssExtendName==".pdf"){
                                            return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+getRootPath()+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+enterprise.enterpriseXztpssId+',"'+enterprise.enterpriseXztpssURL+'") width =140 height=80  /></div>'
                                        }


                                    }()
                                }]
                            }]
						}
									  ]
								},  {
								layout : 'column',
								xtype : 'fieldset',
								title : '法定代表人信息',
								autoHeight : true,
								collapsible : false,
								anchor : anchor,
								items : [{
									columnWidth : .5,
									layout : 'form',
									labelWidth:150,
									defaults : {
										anchor : anchor
									},
									items : [{
										xtype : 'combo',
										fieldLabel : '法定代表人姓名',
										allowBlank : false,
										triggerClass : 'x-form-search-trigger',
										hiddenName : 'person.name',
										readOnly:isReadOnly,
										value:person==null?null:person.name,
										scope:this,
										onTriggerClick : function(com) {
										   var amselectLegalperson1 = function(obj) {
										   	    panel_add.getCmpByName('person.selfemail').setValue(obj.selfemail);
												panel_add.getCmpByName('person.name').setValue(obj.name);
												panel_add.getCmpByName('enterprise.legalpersonid').setValue(obj.id);
												panel_add.getCmpByName('person.cellphone').setValue(obj.cellphone);
												panel_add.getCmpByName('person.cardtype').setValue(obj.cardtype);
												panel_add.getCmpByName('person.sex').setValue(obj.sex);
												panel_add.getCmpByName('person.cardnumber').setValue(obj.cardnumber);
												panel_add.getCmpByName('person.id').setValue(obj.id);
												
												var urlz=getRootPath()+ '/images/nopic.jpg';
												var personSFZZId=obj.personSFZZId;
												var personSFZZUrl=obj.personSFZZUrl;
												if(null!=personSFZZId && null!=personSFZZUrl && ""!=personSFZZId && ""!=personSFZZUrl){
													panel_add.getCmpByName('personSFZZId').setValue(personSFZZId);
												    urlz=getRootPath()+"/"+personSFZZUrl;
												}
												var urlf=getRootPath()+ '/images/nopic.jpg';
											    var personSFZFId=obj.personSFZFId;
												var personSFZFUrl=obj.personSFZFUrl;
											    if(null!=personSFZFId && null!=personSFZFUrl && ""!=personSFZFId && ""!=personSFZFUrl){
											    	panel_add.getCmpByName('personSFZFId').setValue(personSFZFId);
												    urlf=getRootPath()+"/"+personSFZFUrl;
												}
												var display = panel_add.getCmpByName("shenfenzheng-z");
												display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+urlz+'" width =140 height=80/></div>',false);
												
											    display = panel_add.getCmpByName("shenfenzheng-f");
												display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+urlf+'" width =140 height=80/></div>',false);
												
											}
											selectPWName(amselectLegalperson1);
										},
										resizable : true,
										mode : 'romote',
										editable : true,
										lazyInit : false,
										typeAhead : true,
										minChars : 1,
										width : 80,
										store : new Ext.data.JsonStore({
											url : getRootPath()	+ '/creditFlow/customer/person/ajaxQueryForComboPerson.do?isAll='+isGranted('_detail_sygrkh'),
											root : 'topics',
											autoLoad : true,
											fields : [{
														name : 'id'
													}, {
														name : 'name'
													}],
											listeners : {
												'load' : function(s, r, o) {
													if (s.getCount() == 0) {
														Ext.getCmp('amlegalpersonName')
																.markInvalid('没有查找到匹配的记录');
													}
												}
											}
										}),
										displayField : 'name',
										valueField : 'id',
										triggerAction : 'all',
										listeners : {
											'select' : function(combo, record, index) {
												Ext.getCmp('amlegalpersonId').setValue(record.get('id'));
												Ext.getCmp('amlegalpersonName').setValue(record.get('name'));
														
											},
											'blur' : function(f) {
												if (f.getValue() != null
														&& f.getValue() != '') {
													Ext.getCmp('amlegalpersonId')
															.setValue(f.getValue());
												}
											},
										   'afterrender':function(com){
													    com.clearInvalid();
													}
												
										}
									}, {
										name : 'person.id',
										xtype : 'hidden',
									    value:person==null?null:person.id
									},{
										name : 'gudongInfo',
										xtype : 'hidden',
										value:null
									},{
										name : 'enterprise.legalpersonid',
										value:enterprise==null?null:enterprise.legalpersonid,
										xtype : 'hidden'
									}]
								}, {
													columnWidth : 0.5, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													items : [{
														fieldLabel : "性别",
														xtype : "diccombo",
														readOnly:isReadOnly,
														hiddenName : 'person.sex',
														displayField : 'itemName',
														itemName : '性别', // xx代表分类名称
														allowBlank : false,
														emptyText : "请选择",
														editable : false,
														value:person==null?null:person.sex,
														blankText : "性别不能为空，请正确填写!",
														anchor : "100%",
														listeners : {
															afterrender : function(combox) {
																var st = combox.getStore();
																st.on("load", function() {
																			combox
																					.setValue(combox
																							.getValue());
																			combox
																					.clearInvalid();
																		})
															}
														}
													}]
												},{
													columnWidth : 0.5, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													items : [{
														xtype : "diccombo",
														hiddenName : "person.cardtype",
														fieldLabel : "证件类型",
														readOnly:isReadOnly,
														value:person==null?null:person.cardtype,
														itemName : '证件类型', // xx代表分类名称
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
													}]
												}, {
													columnWidth : 0.5, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													items : [{
																xtype : "textfield",
																name : "person.cardnumber",
																value:person==null?null:person.cardnumber,
																allowBlank : false,
																readOnly:isReadOnly,
																fieldLabel : "证件号码",
																blankText : "证件号码不能为空，请正确填写!",
																anchor : "100%"	,
																listeners : {
																	scope:this,
																	'beforerender':function(com){
																		if(this.getCmpByName('person.cardtype').getValue()==309){
																			var sex=this.getCmpByName("person.sex");
																			if(validateIdCard(com.getValue())==1){
																				Ext.ux.Toast.msg('身份证号码验证','法定代表人证件号码不正确,请仔细核对')
																			}else if(validateIdCard(com.getValue())==2){
																				Ext.ux.Toast.msg('身份证号码验证','法定代表人证件号码地区不正确,请仔细核对')
																			}else if(validateIdCard(com.getValue())==3){
																				Ext.ux.Toast.msg('身份证号码验证','法定代表人证件号码生日日期不正确,请仔细核对')														
																			}else{
																				if(com.getValue().split("").reverse()[1]%2==0){
										                            				sex.setValue(313);
										                            				sex.setRawValue("女")
										                            			}else{
										                            				sex.setValue(312);
										                            				sex.setRawValue("男")
										                            			}
																			}
																		}
																	},
																	'blur':function(f){
																		if(this.getCmpByName('person.cardtype').getValue()==309){
																			var sex=this.getCmpByName("person.sex");
																			if(validateIdCard(f.getValue())==1){
																				Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
																				return;
																			}else if(validateIdCard(f.getValue())==2){
																				Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
																				return;
																			}else if(validateIdCard(f.getValue())==3){
																				Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
																				return;
																			}else{
																				if(f.getValue().split("").reverse()[1]%2==0){
										                            				sex.setValue(313);
										                            				sex.setRawValue("女")
										                            			}else{
										                            				sex.setValue(312);
										                            				sex.setRawValue("男")
										                            			}
																			}
																		}
																	}
																}
															}]
												},{
													columnWidth : 0.5, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													items : [{
																xtype : "textfield",
																name : "person.cellphone",
																value:person==null?null:person.cellphone,
																readOnly:isReadOnly,
																fieldLabel : "手机号码",
																anchor : "100%",
																regex : /^[1][34578][0-9]{9}$/,
																regexText : '手机号码格式不正确'
																/*listeners : {
																	'afterrender':function(com){
																	    com.clearInvalid();
																	}
																}*/
															}]
												}, {
													columnWidth : 0.5, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													items : [{
														xtype : "textfield",
														name : "person.selfemail",
														readOnly:isReadOnly,
														fieldLabel : "电子邮箱",
													    value:person==null?null:person.selfemail,
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
															'afterrender':function(com){
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
												name : 'personSFZZId',
												value : person==null?null:person.personSFZZId
											  },{
												xtype : 'hidden',
												name : 'personSFZFId',
												value:person==null?null:person.personSFZFId
											}]
								},{
								layout : 'column',
								xtype : 'fieldset',
								title : '法定代表人身份证扫描件',
								autoHeight : true,
								collapsible : false,
								anchor : anchor,
								items : [{
							columnWidth :1,
							layout : 'column',
							labelWidth : 65,
							defaults : {
								anchor : '100%'
							},
							items : [{
								columnWidth :0.33,
								layout : 'form',
								labelWidth : 65,
								defaults : {
									anchor : '100%'
								},
								items : [{
										xtype :'label',
										style :'padding-left :20px',
										html : isReadOnly==false?'身份证正面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson_new(\'身份证正面\',\'cs_person_sfzz\',\'shenfenzheng-z\',\'personSFZZId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new("personSFZZId","shenfenzheng-z",\''+winId+'\')>删除</a>':'身份证正面'
									},{
										name:'shenfenzheng-z',
										xtype :'label',
										style :'padding-left :20px',
										html : (person==null || null==person.personSFZZId || ""==person.personSFZZId || person.personSFZZId==0)?'<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
											+ getRootPath()
											+ '/images/nopic.jpg" width =140 height=80/></div>':'<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="' + getRootPath()
											+"/"+ person.personSFZZUrl+'" ondblclick=showPic("'+person.personSFZZUrl+'") width =140 height=80  /></div>'
									}]
							},{
								columnWidth :0.33,
								layout : 'form',
								labelWidth : 65,
								defaults : {
									anchor : '100%'
								},
								items : [{
										xtype :'label',
										style :'padding-left :10px',
										html : isReadOnly==false?'身份证反面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson_new(\'身份证反面\',\'cs_person_sfzf\',\'shenfenzheng-f\',\'personSFZFId\',\''+winId+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new("personSFZFId","shenfenzheng-f",\''+winId+'\')>删除</a>':'身份证反面'
									},{
										name:'shenfenzheng-f',
										xtype :'label',
										html : (person==null || null==person.personSFZFId || ""==person.personSFZFId || person.personSFZFId==0)?'<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
											+ getRootPath()
											+ '/images/nopic.jpg"  width =140 height=80/></div>':'<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="' + getRootPath()
									        +"/"+person.personSFZFUrl+'" ondblclick=showPic("'+person.personSFZFUrl+'") width =140 height=80  /></div>'
									}]
							}]
						}]
								}/*,{
								xtype : 'fieldset',
								title : '股东(投资人)信息',
								anchor : '96%',
								autoHeight : true,
								collapsible : false,
								layout : "form",
								name : "gudong_store",
								bodyStyle : 'padding-left: 0px;text-align:left;',
								anchor : anchor,
								items : [new EnterpriseShareequity({enId:enterprise!=null?enterprise.id:null
									    ,isHidden:isReadOnly,isTitle:false})]
								}*/]
						}, {
							columnWidth : 0.17,
							anchor : '96%',
							items : [/*{
								xtype : 'button', // 1
								text : '业务经营情况',
								tooltip : '企业业务经营情况信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									if (!panel_add.isflag) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
										var enterpriseOrganizecodeValue = enterprise.organizecode;
										var  enterpriseId=panel_add.getCmpByName("enterprise.id").getValue();
								    	managecaseListWin(enterpriseId, enterpriseOrganizecodeValue);
								}
							},*/{
								xtype : 'button', // 财务信息 2
								text : '股东信息',
								tooltip : '股东信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else{
										 new EnterpriseShareequityView({customerId:enterpriseId,isReadOnly:isReadOnly}).show()
									}
								}
							}, {
								xtype : 'button', // 财务信息 2
								text : '财务信息',
								tooltip : '企业财务信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
									financeInfo(organizecode,isReadOnly);
								}
							}, {
								xtype : 'button', // 员工结构2
								text : '员工结构',
								tooltip : '企业员工结构信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
												
									   employeeStructure(organizecode,isReadOnly);
								}
							}, {
								xtype : 'button', // 3
								text : '银行开户',
								tooltip : '企业银行开户信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
										var enterpriseOrganizecodeValue =organizecode;
										bankInfoWin(enterpriseId, isReadOnly,0,0);
									   //bankInfoListWin(enterpriseOrganizecodeValue,isReadOnly);
								}
							},{
								xtype : 'button', // 14
								text : '企业联系人信息',
								tooltip : '企业联系人信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
									    relationPersonListWin(organizecode,isReadOnly);
								}
							},{
								xtype : 'button', // 5
								text : '管理团队',
								tooltip : '企业管理团队信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
									    leadteamListWin(organizecode,isReadOnly);
								}
							}, {
								xtype : 'button', // 6
								text : '债务情况',
								tooltip : '企业债务情况信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
												
									debtListWin(organizecode,isReadOnly);
								}
							}, {
								xtype : 'button', // 7
								text : '债权情况',
								tooltip : '企业债权情况信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
									creditorListWin(organizecode,isReadOnly);
								}
							}, {
								xtype : 'button', // 8
								text : '对外担保',
								tooltip : '企业对外担保信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
									   outassureListWin(organizecode,isReadOnly);
								}
							}, {
								xtype : 'button', // 9
								text : '对外投资',
								tooltip : '企业对外投资信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
									outinvestListWin(organizecode,isReadOnly);
								}
							},{
								xtype : 'button', // 11
								text : '获奖情况',
								tooltip : '企业获奖情况信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
									prizeListWin(organizecode,isReadOnly);
								}
							},{
							
								xtype : 'button', // 11
								text : '企业资信评估',
								tooltip : '企业资信评估',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.MessageBox.alert("友情提示", "先保存企业信息");
										return;
									} else
										Ext.Ajax.request({   
									    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
									   	 	method:'post',   
									    	params:{organizecode:organizecode},   
									    	success: function(response, option) {   
									        	var obj = Ext.decode(response.responseText);
									        	var enterpriseId = obj.data.id
									            new EnterpriseEvaluationWin({customerId:enterpriseId,customerType:'企业',isReadonly:isReadOnly}).show()
									    	},   
									    	failure: function(response, option) {   
									        	return true;   
									        	Ext.MessageBox.alert('友情提示',"异步通讯失败，请于管理员联系！");   
									    	}   
										});
										
								}
							
							},{
								xtype : 'button', // 11
								text : '业务往来',
								tooltip : '业务往来',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.MessageBox.alert("友情提示", "先保存企业信息");
										return;
									} else
										Ext.Ajax.request({   
									    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
									   	 	method:'post',   
									    	params:{organizecode:organizecode},   
									    	success: function(response, option) {   
									        	var obj = Ext.decode(response.responseText);
									        	var enterpriseId = obj.data.id
									            new EnterpriseAll({customerType:'company_customer',customerId:enterpriseId,personType:602,shareequityType:'company_shareequity'}).show()
									    	},   
									    	failure: function(response, option) {   
									        	return true;   
									        	Ext.MessageBox.alert('友情提示',"异步通讯失败，请于管理员联系！");   
									    	}   
										});
										
								}
							
							
							},{								
								xtype : 'button', // 11
								text : '公司实际控制人信息',
								tooltip : '公司实际控制人信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.MessageBox.alert("友情提示", "先保存企业信息");
										return;
									} else{
										Ext.Ajax.request({   
									    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
									   	 	method:'post',   
									    	params:{organizecode:organizecode},   
									    	success: function(response, option) {   
									        	var obj = Ext.decode(response.responseText);
									        	var enterpriseId = obj.data.id
									        	var personId=obj.data.headerId
									        	new HeaderWin({enterpriseId:enterpriseId,isReadOnly:isReadOnly,personId:personId}).show()
									    	},   
									    	failure: function(response, option) {   
									        	return true;   
									        	Ext.MessageBox.alert('友情提示',"异步通讯失败，请于管理员联系！");   
									    	}   
										});
									}
										
										
								}

							},{
								
								
								xtype : 'button', // 11
								text : '上下游客户',
								tooltip : '上下游客户',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.MessageBox.alert("友情提示", "先保存企业信息");
										return;
									} else{
										Ext.Ajax.request({   
									    	url: __ctxPath + '/creditFlow/customer/enterprise/getbIdyEnterpriseIdBpCustEntUpanddownstream.do',   
									   	 	method:'post',   
									    	params:{enterpriseId:enterpriseId},   
									    	success: function(response, option) { 
									        	var obj = Ext.decode(response.responseText);
									        	var upAndDownCustomId=obj.upAndDownCustomId;
									        	new upAndDownStream({enterpriseId:enterpriseId,isReadOnly:isReadOnly,upAndDownCustomId:upAndDownCustomId}).show()
									    	},   
									    	failure: function(response, option) {   
									        	return true;   
									        	Ext.MessageBox.alert('友情提示',"异步通讯失败，请于管理员联系！");   
									    	}   
										});
									}
										
										
								}

							
								
							},{
								xtype : 'button', // 11
								text : '上下游渠道合同',
								tooltip : '上下游渠道合同',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.MessageBox.alert("友情提示", "先保存企业信息");
										return;
									} else{
										Ext.Ajax.request({   
									    	url: __ctxPath + '/creditFlow/customer/enterprise/getbIdyEnterpriseIdBpCustEntUpanddowncontract.do',   
									   	 	method:'post',   
									    	params:{enterpriseId:enterpriseId},   
									    	success: function(response, option) { 
									        	var obj = Ext.decode(response.responseText);
									        
									        	var upAndDownContractId=obj.upAndDownContractId;
									        	new upAndDownContract({enterpriseId:enterpriseId,isReadOnly:isReadOnly,upAndDownContractId:upAndDownContractId}).show()
									    	},   
									    	failure: function(response, option) {   
									        	return true;   
									        	Ext.MessageBox.alert('友情提示',"异步通讯失败，请于管理员联系！");   
									    	}   
										});
									}
										
										
								}
							}, {
								xtype : 'button', // 3
								text : '企业现金流量及销售收入',
								tooltip : '企业现金流量及销售收入',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
									new cashflowandSaleIncome({enterpriseId:enterpriseId,isReadOnly:isReadOnly}).show()
									   //bankInfoListWin(enterpriseOrganizecodeValue,isReadOnly);
								}
							}, {
								xtype : 'button', // 3
								text : '纳税情况',
								tooltip : '纳税情况',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
									new bppaytax({enterpriseId:enterpriseId,isReadOnly:isReadOnly}).show()
									   //bankInfoListWin(enterpriseOrganizecodeValue,isReadOnly);
								}
							}, {
								xtype : 'button', // 3
								text : '诉讼情况',
								tooltip : '诉讼情况',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
									new bplawsuit({enterpriseId:enterpriseId,isReadOnly:isReadOnly}).show()
									   //bankInfoListWin(enterpriseOrganizecodeValue,isReadOnly);
								}
							},{
								xtype:'button',
								text:'关联企业',
								tooltip:'关联企业',
								scope:this,
								style : 'margin-top:5px;margin-left:20px;',
								handler:function(){
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
										new relevance({enterId:enterpriseId,isReadOnly:isReadOnly}).show()
								}
							}
				/*			, {
								xtype : 'button', // 3
								text : '资产负债表',
								tooltip : '企业银行开户信息',
								scope : this,
								style : 'margin-top:5px;margin-left:20px;',
								handler : function() {
									var enterpriseId=this.getCmpByName('enterprise.id').getValue();
									var organizecode=this.getCmpByName('enterprise.organizecode').getValue();
									if (null==enterpriseId || ""==enterpriseId) {
										Ext.ux.Toast.msg("友情提示", "先保存企业信息");
										return;
									} else
									new bplawsuit({enterpriseId:enterpriseId,isReadOnly:isReadOnly}).show()
									   //bankInfoListWin(enterpriseOrganizecodeValue,isReadOnly);
								}
							}*/
							]
						}]
					}]
			});
	  	},
		initUIComponents : function() {
	  		
		}
});