/**
 * @author
 * @createtime
 * @class InvestPersonForm
 * @extends Ext.Window
 * @description InvestPerson表单
 * @company 智维软件
 */
InvestPersonForm = Ext.extend(Ext.Window, {
	isLook : false,
	isEdit : false,
	// 构造函数
	investPersonPanel : null,
	constructor : function(_cfg) {
		if (typeof (_cfg.investPersonPanel) != "undefined") {
			this.investPersonPanel = _cfg.investPersonPanel;
		}
		Ext.applyIf(this, _cfg);
		if(_cfg.isLook){
			this.isLook = _cfg.isLook;
		}
		if(_cfg.investPerson != null){
			this.investPerson = _cfg.investPerson;
			this.isflag = true;
		};
		if(typeof(_cfg.isEdit) != "undefined"){
			this.isEdit = _cfg.isEdit;
		};
		if(typeof(_cfg.isCareHidden) != "undefined"){
			this.isCareHidden = _cfg.isCareHidden;
		}else{
			this.isCareHidden = true;
		};
		if(typeof(_cfg.isHiddenEdit)!="undefined"){
			this.isHiddenEdit = _cfg.isHiddenEdit;
		}
		// 必须先初始化组件
		this.initUIComponents();
		InvestPersonForm.superclass.constructor.call(this, {
					id : 'InvestPersonFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					width : 690,
					height : 400,
					border : false,
					maximizable : true,
					title : '个人投资客户详细信息',
					buttonAlign : 'center',
					buttons : this.isLook?null:[{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var investPerson = this.investPerson;
		this.formPanel = new Ext.form.FormPanel({
			id : "InvestPersonFormPanel",
			layout : 'form',
			bodyStyle : 'padding:10px',
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
					anchor : '96%',
					columnWidth : 1,
					labelWidth : 60
				},
				
			items : [{
				layout : "column",
				xtype : 'fieldset',
				title : this.isLook?'个人基本信息':'填写个人基本信息',
				collapsible : true,
				
				items : [{
					layout : "column",
					columnWidth : 1,
					items : [{
						layout : 'form',
						columnWidth : .33,
						labelWidth : 70,
						labelAlign : 'right',
						
						items : [{
									fieldLabel : '姓名',
									allowBlank : false,
									readOnly : this.isLook,
									xtype : 'textfield',
									blankText : "性名不能为空，请正确填写!",
									value : investPerson == null ? '' : investPerson.perName,
									name : 'investPerson.perName',
									anchor : "100%",
									maxLength : 15
								}, {
									xtype : "dickeycombo",
									nodeKey : 'card_type_key',
									fieldLabel : '证件类型',
									readOnly : this.isLook,
									hiddenName : 'investPerson.cardType',
									value : investPerson == null ? '' : investPerson.cardType,
									allowBlank :this.isLook?true: false,
									editable : false,
									blankText : "证件类型不能为空，请正确填写!",
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
								},{
									fieldLabel : '联系人姓名',
									xtype : 'textfield',
									name : 'investPerson.linkmanName',
									readOnly : this.isLook,
									value : investPerson == null ? '' : investPerson.linkmanName,
									anchor : '100%'
								}]
					}, {
						layout : 'form',
						columnWidth : .33,
						labelWidth : 70,
						items : [{
							xtype : "dickeycombo",
							nodeKey : 'sex_key',
							fieldLabel : '性别',
							columnWidth : 3,
							hiddenName : 'investPerson.perSex',
							value : investPerson == null ? '' : investPerson.perSex,
							allowBlank : false,
							readOnly : this.isLook,
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
						}, {
							fieldLabel : '证件号码',
							name : 'investPerson.cardNumber',
							value : investPerson == null ? '' : investPerson.cardNumber,
							readOnly : this.isLook,
							allowBlank :this.isLook?true: false,
							xtype : 'textfield',
							blankText : "证件号码不能为空，请正确填写!",
							anchor : "100%",
							maxLength : 25,
							listeners : {
								scope:this,
								'beforerender':function(com){
									if(this.getCmpByName('investPerson.cardType').getValue()==309){
										if(validateIdCard(com.getValue())==1){
											Ext.ux.Toast.msg('身份证号码验证','证件号码不正确,请仔细核对')
										}else if(validateIdCard(com.getValue())==2){
											Ext.ux.Toast.msg('身份证号码验证','证件号码地区不正确,请仔细核对')
											
										}else if(validateIdCard(com.getValue())==3){
											Ext.ux.Toast.msg('身份证号码验证','证件号码生日日期不正确,请仔细核对')														
										}
									}
								},
								'blur' : function(f) {
									if(this.getCmpByName('investPerson.cardType').getValue()==309){
										if(validateIdCard(f.getValue())==1){
											Ext.Msg.alert('身份证号码验证','证件号码不正确,请仔细核对')
											return;
										}else if(validateIdCard(f.getValue())==2){
											Ext.Msg.alert('身份证号码验证','证件号码地区不正确,请仔细核对')
											return;
										}else if(validateIdCard(f.getValue())==3){
											Ext.Msg.alert('身份证号码验证','证件号码生日日期不正确,请仔细核对')
											return;
										}
									}
									var penal=this.getCmpByName("investPerson.perBirthday");
									var cardNumber = f.getValue();
									var personId = (investPerson == null)?0:investPerson.perId;
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
			                        		}else{
			                        			// 拆分身份证号码 ，拿出出生年月日
			                        			if(!this.isEdit&&investPerson == null){// 只有新增才需要默认加载身份证上的出生年月日
			                            			var brithday= cardNumber.substr(6,8);
													var formatBrithday = brithday.substr(0,4)+"-"+brithday.substr(4,2)+"-"+brithday.substr(6,2);
			                            			penal.setValue(formatBrithday)
			                        			}
			                        		}
				                      }
			                       });  
								}
							}
						},{
								fieldLabel : '联系人电话',
								xtype : 'textfield',
								name : 'investPerson.linkmanPhone',
								value : investPerson == null ? '' : investPerson.linkmanPhone,
								readOnly : this.isLook,
								anchor : '100%'
							
						}]
					}, {
						layout : 'form',
						columnWidth : .34,
						labelWidth : 60,
						items : [{
									fieldLabel : '手机号码',
									readOnly : this.isLook,
									xtype : 'textfield',
									name : 'investPerson.phoneNumber',
									value : investPerson == null ? '' : investPerson.phoneNumber,
									allowBlank : false,
									blankText : "手机号码不能为空，请正确填写!",
									anchor : "100%",
									maxLength : 11
								}, {
									fieldLabel : '出生日期',
									readOnly : this.isLook,
									name : 'investPerson.perBirthday',
									value : investPerson == null ? '' : investPerson.perBirthday,
									xtype : 'datefield',
									anchor : "100%",
									format : 'Y-m-d'
									// value : new Date()
							},{
								fieldLabel : '关系',
								xtype : 'textfield',
								name : 'investPerson.filiation',
								readOnly : this.isLook,
								value : investPerson == null ? '' : investPerson.filiation,
								anchor :'100%'
							}]
					}, {
						name : 'investPerson.perId',
						xtype : 'hidden',
						value : investPerson == null ? '' : investPerson.perId
					}]
				}, {
					layout : "column",
					columnWidth : 1,
					items : [{
						layout : 'form',
						columnWidth : .33,
						labelAlign : 'right',
						labelWidth : 70,
						items : [{
									xtype : "dickeycombo",
									hiddenName : "investPerson.customerLevel",
									nodeKey : 'finaing_customerLevel', // xx代表分类名称
									fieldLabel : "客户级别",
									emptyText : "请选择",
									readOnly : this.isLook,
									value : investPerson == null ? '' : investPerson.customerLevel,
									anchor : '100%',
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
						}
						,{
							layout : 'form',
							columnWidth : .33,
							labelAlign : 'right',
							labelWidth : 70,
							items : [{
									name:'investPerson.areaId',
									xtype:'hidden',
									value:investPerson == null ? '' : investPerson.areaId
								},{
									xtype : "combo",
									triggerClass : 'x-form-search-trigger',
									hiddenName : "areaName",
									editable : false,
									fieldLabel : "所在区域",
									blankText : "所在区域不能为空，请正确填写!",
									allowBlank : false,
									value:investPerson == null ? '' : investPerson.areaText,
									anchor : "99%",
									readOnly : this.isLook,

									onTriggerClick : function() {
												var com = this
												var selectBankLinkMan = function(array) {
													var str = "";
													var idStr = ""
													for (var i = array.length - 1; i >= 0; i--) {
														str = str + array[i].text + "-"
														idStr = idStr + array[i].id + ","
													}
													if (str != "") {
														str = str.substring(0, str.length - 1);
													}
													if (idStr != "") {
														idStr = idStr.substring(0, idStr.length - 1)
													}
													com.previousSibling().setValue(idStr)
													com.setValue(str);
												};
												selectDictionary('area', selectBankLinkMan);
										}
								}]
						},{
							layout : 'form',
							columnWidth : .34,
							labelWidth : 60,
								items : [{
									fieldLabel : '邮箱',
									readOnly : this.isLook,
								//	allowBlank : false,
									name : 'investPerson.perEmail',
									value : investPerson == null ?'': investPerson.perEmail,
									xtype : 'textfield',
									anchor : "100%"
								}]
							}]
				},{
					columnWidth:1,
					items:[{
						layout:'form',
						labelAlign : 'right',
						labelWidth : 70,
						items:[{
							fieldLabel : '通信地址',
							readOnly : this.isLook,
							allowBlank : false,
							name : 'investPerson.postAddress',
							value : investPerson == null ? '' : investPerson.postAddress,
							xtype : 'textfield',
							anchor : "100%"
						}]
					}]
				},{
					columnWidth:1,
					items:[{
						layout:'form',
						labelAlign : 'right',
						labelWidth : 70,
						items:[{
							name : 'investPerson.remarks',
							fieldLabel:'备注',
							readOnly : this.isLook,
							xtype:'textarea',
							value : investPerson == null ? '' : investPerson.remarks,
							width:'100%',
							height:150
						}]
					}]
					
				}]
			}, {
				layout : 'column',
				xtype : 'fieldset',
				title : '身份证扫描件',
				autoHeight : true,
				collapsible : true,
				style : 'margin-top :10px;padding:10px',
				columnWidth : 1,
				items : [{
					columnWidth : 1,
					layout : 'column',
					labelWidth : 70,
					defaults : {
						anchor : '100%'
					},
					items : [{
						columnWidth : 0.3,
						layout : 'form',
						labelWidth : 25,
						defaults : {
							anchor : '100%'
						},
						items : [{
							xtype : 'label',
							style : 'padding-left :10px',
							html : this.isRead == false
									? '身份证正面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson_new(\'身份证正面\',\'cs_invest_person_sfzz\',\'shenfenzheng-z\',\'personSFZZId\',\'InvestPersonFormWin\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new("personSFZZId","shenfenzheng-z",\'InvestPersonFormWin\')>删除</a>'
									: '身份证正面'
						}, {
							name : 'shenfenzheng-z',
							xtype : 'label',
							style : 'padding-left :10px',
							html : (investPerson == null
									|| null == investPerson.personSFZZId
									|| "" == investPerson.personSFZZId || investPerson.personSFZZId == 0)
									? '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
											+ getRootPath()
											+ '/images/nopic.jpg" width =140 height=80/></div>'
									: '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
											+ getRootPath()
											+ "/"
											+ investPerson.personSFZZUrl
											+ '" ondblclick=showPic("'
											+ investPerson.personSFZZUrl
											+ '") width =140 height=80  /></div>'
						}]
					}, {
						columnWidth : 0.3,
						layout : 'form',
						labelWidth : 25,
						defaults : {
							anchor : '100%'
						},
						items : [{
							xtype : 'label',
							style : 'padding-left :10px',
							html : this.isRead == false
									? '身份证反面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson_new(\'身份证正面\',\'cs_invest_person_sfzf\',\'shenfenzheng-f\',\'personSFZFId\',\'InvestPersonFormWin\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new("personSFZFId","shenfenzheng-f",\'InvestPersonFormWin\')>删除</a>'
									: '身份证反面'
						}, {
							name : 'shenfenzheng-f',
							xtype : 'label',
							html : (investPerson == null
									|| null == investPerson.personSFZFId
									|| "" == investPerson.personSFZFId || investPerson.personSFZFId == 0)
									? '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
											+ getRootPath()
											+ '/images/nopic.jpg"  width =140 height=80/></div>'
									: '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
											+ getRootPath()
											+ "/"
											+ investPerson.personSFZFUrl
											+ '" ondblclick=showPic("'
											+ investPerson.personSFZFUrl
											+ '") width =140 height=80  /></div>'
						}, {
							xtype : 'hidden',
							name : 'personSFZZId',
							value : investPerson == null?null:investPerson.personSFZZId
						}, {
							xtype : 'hidden',
							name : 'personSFZFId',
							value : investPerson == null?null:investPerson.personSFZFId
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
						var flag=this.isflag;
						var read=this.isRead;
						if (!flag) {
							Ext.ux.Toast.msg("友情提示", "先保存个人信息");
							return;
						} else {
							if(investPerson != null) {
								var id = investPerson.perId;
								Ext.Ajax.request({
									url : __ctxPath + '/customer/getInvestPerson.do?perId=' + id,
									method : 'post',
									success : function(response, option) {
										var obj = Ext.decode(response.responseText);
										var personIdValue = obj.data.perId;
										bankInfoWin(id, read,1,1);
										//bankInfoListPersonWin(personIdValue,read,true);
									}
								});
							}
						}
					}
				},{
					xtype : 'button',
					text : '客户关怀记录',
					width : 140,
					style : 'margin-top :10px;padding:10px',
					scope : this,
					handler : function() {
						var read=this.isRead;
						if(investPerson !=null){
//							var id = investPerson.perId;
//							alert(investPerson.perId);
							Ext.Ajax.request({
								url : __ctxPath + '/customer/getInvestPerson.do?perId=' + investPerson.perId,
								method : 'POST',
								scope : this,
								success : function(response, request) {
									obj = Ext.util.JSON.decode(response.responseText);
									var investPerson = obj.data;
									var investPersonId = investPerson.perId;
									new InvestPersonCareForm({
						            	investPerson : investPerson,
						            	investPersonId : investPersonId,
										isRead : true,
										isLook : true,
										isLookCare : false,
										isHiddenEdit : this.isHiddenEdit,
										careHidden : this.isCareHidden,
										isHidden : true
									}).show();
								},
								failure : function(response) {
									Ext.ux.Toast.msg('状态', '操作失败，请重试');
								}
									});
						}else{
							Ext.ux.Toast.msg("友情提示", "先保存个人信息");
						}
				}
				
				}]
			}]
		});
		// this.gridPanel.addListener('rowdblclick', this.rowClick);
		// 加载表单对应的数据
//		if (this.perId != null && this.perId != 'undefined') {
//			this.formPanel.loadData({
//				url : __ctxPath + '/customer/getInvestPerson.do?perId='
//						+ this.perId,
//				root : 'data',
//				preName : 'investPerson',
//				success : function(response, options) {
//					var respText = response.responseText;
//					var alarm_fields = Ext.util.JSON.decode(respText);
//				}
//			});
//		}

	},// end of the initcomponents
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.bankId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	},
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
			new InvestBankNoForm({
				bankId : rec.data.bankId
			}).show();
		})
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath + '/customer/multiDelInvestBankNo.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	editRs : function(record) {
		new InvestBankNoForm({
			bankId : record.data.bankId
		}).show();
	},
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
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath + '/customer/saveInvestPerson.do',
			callback : function(fp, action) {
					Ext.ux.Toast.msg('状态', '保存成功!');
				var gridPanel = Ext.getCmp('InvestPersonGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				if(this.investPersonPanel!=null){
					this.investPersonPanel.getStore().reload();
				}
				this.close();
			}
		});
	}// end of save

});