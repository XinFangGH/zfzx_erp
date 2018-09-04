Ext.ns('bank');

/**
 * @author: zcb
 * @class bank
 * @extends Ext.Panel
 * @description [bank]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
bank = Ext.extend(Ext.Window, {
	personData : null,
	isRead : false,
	enterpriseid : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
	
		Ext.applyIf(this, _cfg);
		this.initUIComponents()
		bank.superclass.constructor.call(this, {
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 450,
			width : 650,
			maximizable : true,
			id : 'enterpriseBankWin',
			title : '申请换卡',
			buttonAlign : 'center',
			buttons : [{
						text : '保存',
						iconCls : 'btn-save',
						scope : this,
						disabled : this.isReadOnly,
						handler : this.save
					}, {
						text : '重置',
						iconCls : 'btn-reset',
						scope : this,
						disabled : this.isReadOnly,
						handler : this.reset
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						scope : this,
						handler : this.cancel
					}]
		});
	},
		initUIComponents : function() {
		
		this.formPanel = new Ext.FormPanel({
			layout : 'column',
			border : false,
			autoScroll : true,
			monitorValid : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
				anchor : anchor,
				labelWidth :80,
				columnWidth : 1

			},
			items : [{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'combo',
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
					hiddenName : "enterpriseBank.openType",
					fieldLabel : '开户类型',
					anchor : '100%',
					allowBlank : false,
					name : 'enterpriseBank.openType',
					listeners : {
						scope : this,
						select : function(combox, record, index) {
							var v = record.data.id;
							var obj = Ext
									.getCmp('accountTypeid');
							obj.enable();
							var arrStore = null;
							if (v == 0) {
								arrStore = new Ext.data.SimpleStore(
										{
											fields : ["name",
													"id"],
											data : [["个人储蓄户",
													"0"]]
										});
							} else {
								arrStore = new Ext.data.SimpleStore(
										{
											fields : ["name",
													"id"],
											data : [
													["基本户", "1"],
													["一般户", "2"]]
										});
							}
							obj.clearValue();
							obj.store = arrStore;
							obj.view.setStore(arrStore);
							// arrStore.load();
						},
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								if (combox.getValue() == ''
										|| combox.getValue() == null) {
									combox
											.setValue(st
													.getAt(0).data.itemId);
									combox.clearInvalid();
								} else {
									combox.setValue(combox
											.getValue());
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
					allowBlank : false,
					readOnly : this.isRead,
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
					listeners : {
						scope : this,
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
										combox.setValue(combox
												.getValue());

									})
							combox.clearInvalid();
						}

					}
				}, {
						fieldLabel : "网点名称",
	                      name : 'enterpriseBank.bankOutletsName',
						 xtype:'textfield',
					allowBlank : false,
					readOnly : this.isRead,
					anchor : "100%"
					
				}, {

					fieldLabel : '开户名称',
					name : 'enterpriseBank.name',
					xtype : 'textfield',
					anchor : '100%',
					readOnly : this.isRead,
					allowBlank : false
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						//id : 'accountTypeid',
						editable : false,
						width : 70,
						triggerAction : "all",
						hiddenName : "enterpriseBank.accountType",
						fieldLabel : '账户类型',
						anchor : '100%',
						readOnly : this.isRead,
						allowBlank : false,
						store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["个人储蓄户", "0"],
											["基本户", "1"],
											["一般户", "2"]]
								}),
						name : 'enterpriseBank.accountType',
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
											combox.setValue(combox
													.getValue());
	
										})
								combox.clearInvalid();
							}
	
						}
					}, {
						xtype : 'radiogroup',
						fieldLabel : '银行开户类别',
	
						items : [{
							boxLabel : '本币开户',
							name : 'enterpriseBank.openCurrency',
							disabled : this.isRead,
							inputValue : "0",
							checked :true
						}, {
							boxLabel : '外币开户',
							name : 'enterpriseBank.openCurrency',
							disabled : this.isRead,
							inputValue : "1"
						}]
					}, {
						name : 'enterpriseBank.areaId',
						xtype : 'hidden'
					}, {
						name : 'enterpriseBank.areaName',
						//hiddenName : 'enterpriseBank.areaName',
						fieldLabel : '开户地区',
						anchor : '100%',
						readOnly : this.isRead,
						allowBlank : false,
						xtype : 'trigger',
						triggerClass : 'x-form-search-trigger',
						editable : false,
						scope : this,
						onTriggerClick : function() {
							var com = this
							var selectBankLinkMan = function(array) {
								var str = "";
								var idStr = ""
								for (var i = array.length - 1; i >= 0; i--) {
									str = str + array[i].text + "-"
									idStr = idStr + array[i].id
											+ ","
								}
								if (str != "") {
									str = str.substring(0,
											str.length - 1);
								}
								if (idStr != "") {
									idStr = idStr.substring(0,
											idStr.length - 1)
								}
								com.previousSibling()
										.setValue(idStr)
								com.setValue(str);
							};
							selectDictionary('area',
									selectBankLinkMan);
						}
					}, {
						fieldLabel : '贷款卡卡号',
						name : 'enterpriseBank.accountnum',
						maxLength : 100,
						xtype : 'textfield',
						readOnly : this.isRead,
						anchor : '100%',
						allowBlank : false
	
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				items : [{
					xtype : 'textarea',
					anchor : '100%',
					fieldLabel : '备注',
					readOnly : this.isRead,
					height : 80,
					name : 'enterpriseBank.remarks'
				}]
			}, {
				xtype : 'hidden',
				name : 'enterpriseBank.enterpriseid',
				value : this.investId
			}, {
				xtype : 'hidden',
				name : 'enterpriseBank.id'
			}, {
				xtype : 'hidden',
				name : 'enterpriseBank.isEnterprise',
				value : 1
			}, {
				xtype : 'hidden',
				name : 'enterpriseBank.isInvest',
				value : 3
					// 表示这个属于债权转让的客户
			}, {
				xtype : 'hidden',
				name : 'enterpriseBank.iscredit',
				value : 0
			}, {
				xtype : 'hidden',
				name : 'enterpriseBank.companyId',
				value : this.companyId
			}, {
				xtype : 'hidden',
				name : 'personYHKZId'
			}, {
				xtype : 'hidden',
				name : 'personYHKFId'
			},{
				columnWidth : 1,
				layout : 'form',
				items : [{
					 xtype : 'fieldset',
					title : '银行卡扫描件',
					bodyStyle : 'padding-left:0px',
					collapsible : true,
					labelAlign : 'right',
					autoHeight : true,
					layout : 'column',
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
							style : 'margin-left : 20px',
							html : this.isRead?'': '银行卡正面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson_new(\'银行卡正面\',\'cs_investmentperson_yhkz\',\'yinhangka-z\',\'personYHKZId\',\'enterpriseBankWin\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'personYHKZId\',\'yinhangka-z\',\'enterpriseBankWin\')>删除</a>'
						}, {
							name:'yinhangka-z',
							xtype : 'label',
							style : 'margin-left : 20px',
							html : function(){
								
									return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
											+ __ctxPath
											+ '/images/nopic.jpg" width =140 height=80/></div>'
								
							}()
							
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
							html : this.isRead?'': '银行卡反面&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBtnPerson_new(\'银行卡反面\',\'cs_investmentperson_yhkf\',\'yinhangka-f\',\'personYHKFId\',\'enterpriseBankWin\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delePhotoFile_new(\'personYHKFId\',\'yinhangka-f\',\'enterpriseBankWin\')>删除</a>'
						}, {
							name:'yinhangka-f',
							xtype : 'label',
							html : function(){
								
									return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
											+ __ctxPath
											+ '/images/nopic.jpg" width =140 height=80/></div>'
								
							}()
							
						}]
					}]
				}]
	
			}]
		});
		// 加载表单对应的数据
		if (this.investId != null && this.investId != 'undefined') {
			
			this.formPanel.loadData({
						url : __ctxPath
								+ '/creditFlow/creditAssignment/customer/getBankInfoCsInvestmentperson.do?investId='
								+ this.investId,
						root : 'data',
						preName : ['enterpriseBank','personYHKZId'],
						scope : this,
						success : function(resp, options) {
							var result = Ext.decode(resp.responseText);
							this.getCmpByName('enterpriseBank.areaName').setValue(result.data.enterpriseBank.areaName)
							var yhkzObj=this.getCmpByName('yinhangka-z')
							var personYHKZIdObj=this.getCmpByName('personYHKZId');
							personYHKZIdObj.setValue(result.data.personYHKZId)
							if(result.data.personYHKZId!=null && (result.data.personYHKZExtendName == ".jpg" || result.data.personYHKZExtendName == ".jpeg")){
								yhkzObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ "/"
													+ result.data.personYHKZUrl
													+ '" ondblclick=showPic("'
													+ result.data.personYHKZUrl
													+ '") width =140 height=80  /></div>',false)
							}else if( result.data.personYHKZId!=null && result.data.personYHKZExtendName == ".pdf"){
								yhkzObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+ result.data.personYHKZId
													+ ',"'
													+ result.data.personYHKZUrl
													+ '") width =140 height=80  /></div>',false)
							}
							
							var yhkfObj=this.getCmpByName('yinhangka-f')
							var personYHKFIdObj=this.getCmpByName('personYHKFId');
							personYHKFIdObj.setValue(result.data.personYHKFId)
							if(result.data.personYHKFId!=null && (result.data.personYHKFExtendName == ".jpg" || result.data.personYHKFExtendName == ".jpeg")){
								yhkfObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ "/"
													+ result.data.personYHKFUrl
													+ '" ondblclick=showPic("'
													+ result.data.personYHKFUrl
													+ '") width =140 height=80  /></div>',false)
							}else if( result.data.personYHKFId!=null && result.data.personYHKFExtendName == ".pdf"){
								yhkfObj.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
													+ getRootPath()
													+ '//images//desktop//pdf_win.jpg" ondblclick=showPdf('
													+ result.data.personYHKFId
													+ ',"'
													+ result.data.personYHKFUrl
													+ '") width =140 height=80  /></div>',false)
							}
						}
					});
		}

	},
	cancel:function(){
		this.close()
	},
	reset : function(){
		this.formPanel.getForm().reset()
	},
	save : function(){
		$postForm( {
			formPanel : this.formPanel,
			scope : this,
			msg : '保存成功',
			url : __ctxPath + '/creditFlow/creditAssignment/customer/saveBankInfoCsInvestmentperson.do',
			callback : function(fp, action) {
				if(this.gridPanel!=null||typeof(this.gridPanel)!="undefined"){
					this.gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}
});