InvestEnterpriseView = Ext.extend(Ext.Panel, {
	isHiddenSearchPanel:false,
	constructor : function(_cfg) {
		
		if (typeof(_cfg.isHiddenSearchPanel) != "undefined") {
			if(_cfg.isHiddenSearchPanel=="false"){
				this.isHiddenSearchPanel = false;
			}else{
				this.isHiddenSearchPanel = _cfg.isHiddenSearchPanel;
			}
		} 
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		InvestEnterpriseView.superclass.constructor.call(this, {
					id : 'InvestEnterpriseView'+this.businessType,
					title :this.isHiddenSearchPanel?"":("Pawn"==this.businessType?'典当企业客户管理':("Guarantee"==this.businessType?'担保企业客户':("SmallLoan"==this.businessType?'小贷企业客户管理':'企业客户管理'))),
					region : 'center',
					layout : 'border',
					iconCls : 'btn-team22',
					items : this.isHiddenSearchPanel?[this.gridPanel]:[this.fPanel_searchEnterprise, this.gridPanel]
				});
	},
	initUIComponents : function() {
		// 快速录入	
		var button_addFastEnterprise = new Ext.Button({
					text : '快速新增',
					iconCls : 'addIcon',
					disabled : false,
					tooltip : '快速录入一条新的记录',
					scope : this,
					handler : function() {
						addFastEnterprise();
					}
					
				});
		var importButton = new Ext.Button({
			text : '导入企业数据',
			iconCls : 'addIcon',
			tooltip : '导入企业原始记录',
			scope : this,
			disabled : false,
			handler : function() {
				new Ext.Window({
					id : 'importEnterpriseWin',
					title : '导入数据',
					layout : 'fit',
					width : (screen.width - 180) * 0.6 - 150,
					height : 130,
					closable : true,
					resizable : true,
					plain : false,
					bodyBorder : false,
					border : false,
					modal : true,
					constrainHeader : true,
					bodyStyle : 'overflowX:hidden',
					buttonAlign : 'center',
					items : [new Ext.form.FormPanel({
						id : 'importEnterpriseFrom',
						monitorValid : true,
						labelAlign : 'right',
						url : __ctxPath
								+ '/creditFlow/customer/enterprise/batchImportEBatchImportDatabase.do',
						buttonAlign : 'center',
						enctype : 'multipart/form-data',
						fileUpload : true,
						layout : 'column',
						frame : true,
						items : [{
									columnWidth : 1,
									layout : 'form',
									labelWidth : 90,
									defaults : {
										anchor : '95%'
									},
									items : [{}, {
												xtype : 'textfield',
												fieldLabel : '请选择文件',
												allowBlank : false,
												blankText : '文件不能为空',
												inputType : 'file',
												id : 'fileBatch',
												name : 'fileBatch'
											}]
								}]
					})],
					buttons : [{
						text : '导入',
						iconCls : 'uploadIcon',
						formBind : true,
						handler : function() {
							Ext.getCmp('importEnterpriseFrom').getForm()
									.submit({
										method : 'POST',
										waitTitle : '连接',
										waitMsg : '消息发送中...',
										success : function(form, action) {
											Ext.ux.Toast.msg('状态', '导入成功!');
											Ext.getCmp('importEnterpriseWin')
													.destroy();
											jStore_enterprise.reload();

										},
										failure : function(form, action) {
											Ext.ux.Toast.msg('状态', '导入失败!');
										}
									});
						}
					}]
				}).show();
			}
		})
		var isShow = false;
		var itemwidth = 0.2;
		if (RoleType == "control") {
			isShow = true;
			itemwidth = 0.17;
		}
		this.fPanel_searchEnterprise = new Ext.FormPanel({
			frame : false,
			region : 'north',
			height : 35,
			monitorValid : true,
			layout : 'column',
			bodyStyle : 'padding:0px 0px 0px 0px',
			border : false,
			defaults : {
				layout : 'form',
				border : false,
				labelWidth : 80,
				bodyStyle : 'padding:5px 0px 0px 5px'
			},
			labelAlign : "right",
			keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.searchByCondition,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
			items : [isShow ? {
				columnWidth : itemwidth,
				bodyStyle : 'padding:5px 0px 0px 0px',
				items : [{
					xtype : "combo",
					labelWidth : "65",
					anchor : "100%",
					fieldLabel : '所属分公司',
					hiddenName : "companyId",

					displayField : 'companyName',
					valueField : 'companyId',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
								autoLoad : true,
								url : __ctxPath
										+ '/system/getControlNameOrganization.do',
								fields : ['companyId', 'companyName']
							})
				}]
			}
					: {
						columnWidth : 0.01
					}, {
				columnWidth : itemwidth,
				bodyStyle : 'padding:5px 0px 0px 0px',
				items : [{
							xtype : 'textfield',
							fieldLabel : '企业名称',
							name : 'enterprisename',
							maxLength : 50,
							maxLengthText : '长度不能超过50',
							labelWidth : "65",
							anchor : "100%"
						}]
			}, {
				columnWidth : itemwidth,
				items : [{
							xtype : 'textfield',
							fieldLabel : '组织机构代码',
							name : 'organizecode',
							labelWidth : "65",
							anchor : "100%"
						}]
			}, {
				columnWidth : itemwidth,
				items : [{
							xtype : "combo",
							triggerClass : 'x-form-search-trigger',
							name : "hangyeType",
							fieldLabel : "行业类别",
							anchor : '100%',
							scope : this,
							readOnly : this.isAllReadOnly,
							onTriggerClick : function(e) {
								var obj = this;
								var oobbj = obj.ownerCt.ownerCt
										.getCmpByName("hangye");
								selectTradeCategory(obj, oobbj);
							}
						},{
							xtype : 'hidden',
							name : 'hangye'
						}]
			}, {
				columnWidth : itemwidth,
				items : [{
							xtype : 'textfield',
							fieldLabel : '营业执照号码',
							name : 'cciaa',
							labelWidth : "65",
							anchor : "100%"
						}]

			}, {
				columnWidth : 0.07,
				items : [{
							id : 'searchButton',
							xtype : 'button',
							text : '查询',
							iconCls : 'btn-search',
							width : 60,
							// labelWidth : "30",
							formBind : true,
							labelWidth : 20,
							bodyStyle : 'padding:5px 0px 0px 0px',
							scope : this,
							handler : this.search
						}]
			}, {

				columnWidth : 0.07,
				items : [{
							xtype : 'button',
							text : '重置',
							width : 60,
							scope : this,
							iconCls : 'btn-reset',
							handler : this.reset
						}]

			}]
		});
		this.topbar = new Ext.Toolbar({
			items : [{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						hidden : isGranted('_create_qykh_invest_'+this.businessType) ? false : true,
						scope : this,
						handler : this.createRs
					}, new Ext.Toolbar.Separator({
								hidden : isGranted('_create_qykh_invest_'+this.businessType)? false: true
							}), {
						iconCls : 'btn-detail',
						text : '查看',
						xtype : 'button',
						scope : this,// handler作用域,must
						hidden : isGranted('_detail_qykh_invest_'+this.businessType) ? false : true,
						handler : this.seeRs
					}, new Ext.Toolbar.Separator({
								hidden : isGranted('_detail_qykh_invest_'+this.businessType)
										? false
										: true
							}), {
						iconCls : 'btn-edit',
						text : '编辑',
						xtype : 'button',
						hidden : isGranted('_edit_qykh_invest_'+this.businessType) ? false : true,
						scope : this,
						handler : this.editRs
					}, new Ext.Toolbar.Separator({
								hidden : isGranted('_edit_qykh_invest_'+this.businessType)
										? false
										: true
							}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						hidden : isGranted('_remove_qykh_invest_'+this.businessType) ? false : true,
						scope : this,
						handler : this.removeRs
					}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_remove_qykh_invest_'+this.businessType)
										? false
										: true)
							}), {
						iconCls : 'btn-detail',
						text : '业务往来',
						xtype : 'button',
						scope : this,
						hidden : true,
						handler : function() {
							var ss = this.gridPanel.getSelectionModel()
									.getSelections();
							var len = ss.length;
							if (len > 1) {
								Ext.ux.Toast.msg('状态', '只能选择一条记录');
							} else if (0 == len) {
								Ext.ux.Toast.msg('状态', '请选择一条记录');
							} else {
								var organizecode = ss[0].data.organizecode;
								var enterpriseId = ss[0].data.id;
								Ext.Ajax.request({
									url : __ctxPath
											+ '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',
									method : 'post',
									params : {
										organizecode : organizecode
									},
									success : function(response, option) {
										var obj = Ext
												.decode(response.responseText);
										var enterpriseId = obj.data.id
										new EnterpriseAll({
											customerType : 'company_customer',
											customerId : enterpriseId,
											personType : 602,
											shareequityType : 'company_shareequity'
										}).show()
									},
									failure : function(response, option) {
										return true;
										Ext.MessageBox.alert('友情提示',
												"异步通讯失败，请于管理员联系！");
									}
								});
							}
						}

					}, new Ext.Toolbar.Separator({
							}), {
						iconCls : 'btn-xls',
						text : '导出到Excel',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_export_qykh_invest_'+this.businessType) ? false : true,
						hidden:this.isHiddenSearchPanel,
						handler : function() {
							var enterprisename=this.getCmpByName('enterprisename').getValue();
							var companyId=(isShow==true?this.getCmpByName('companyId').getValue():1);
							var organizecode=this.getCmpByName('organizecode').getValue();
							var hangye=this.getCmpByName('hangye').getValue();
							var cciaa=this.getCmpByName('cciaa').getValue()
							window.open(__ctxPath+ '/customer/outputExcelInvestEnterprise.do?enterprisename='+encodeURIComponent(encodeURIComponent(enterprisename))+'&companyId='+companyId+'&organizecode='+encodeURIComponent(encodeURIComponent(organizecode))
							+'&hangye='+hangye+"&cciaa="+encodeURIComponent(encodeURIComponent(cciaa))+"&businessType="+this.businessType,'_blank');
						}
					}]
		});
		this.gridPanel = new HT.GridPanel({
					name : 'SmallProjectGrid',
					region : 'center',
					tbar : this.topbar,
					rowActions : false,// 不启用RowActions
					loadMask : true,
//					url : __ctxPath + "/customer/listInvestEnterprise.do?businessType="+this.businessType+"&isAll="+ isGranted('_editBelongeder_qykh_invest'),
					url : __ctxPath + "/customer/listInvestEnterprise.do?businessType="+this.businessType+"&isAll="+this.isAll,
					fields : [{
						name : 'id'
					}, {
						name : 'enterprisename'
					}, {
						name : 'shortname'
					}, {
						name : 'ownership'
					}, {
						name : 'registermoney'
					}, {
						name : 'organizecode'
					}, {
						name : 'tradetypev'
					}, {
						name : 'ownershipv'
					}, {
						name : 'telephone'
					}, {
						name : 'legalperson'
					}, {
						name : 'postcoding'
					}, {
						name : 'cciaa'
					}, {
						name : 'managecityName'
					}, {
						name : 'area'
					}, {
						name : 'opendate'
					}, {
						name : 'hangyetypevalue'
					}, {
						name : 'hangyetypevalue'
					}, {
						name : 'mainHangye'
					}, {
						name : 'mainperson'
					}],
					columns : [{
								header : "所属分公司",
								width : 160,
								sortable : true,
								hidden : RoleType == "control" ? false : true,
								dataIndex : 'orgName'
							}, {
								header : "企业名称",
								width : 160,
								sortable : true,
								dataIndex : 'enterprisename'
							}, {
								header : "企业简称",
								width : 120,
								sortable : true,
								dataIndex : 'shortname'
							}, {
								header : "组织机构代码",
								width : 120,
								sortable : true,
								dataIndex : 'organizecode'
							}, {
								header : "营业执照号码",
								width : 120,
								sortable : true,
								dataIndex : 'cciaa'
							}, {
								header : "行业类别",
								width : 100,
								sortable : true,
								dataIndex : 'mainHangye'
							}, {
								header : "法人",
								width : 100,
								sortable : true,
								dataIndex : 'mainperson'
							}, {
								header : "联系电话",
								width : 100,
								sortable : true,
								dataIndex : 'telephone'
							}],
					listeners : {
						scope:this,
						'rowdblclick' : function(grid, index, e) {
							if(this.isHiddenSearchPanel){
								var selected = grid.getStore().getAt(index) ;
								this.callbackFun(selected);
								Ext.getCmp('selectInvestEnterPriseWin').destroy();
							}else{
								enterpriseId = grid.getSelectionModel().getSelected().get('id');
								seeEnterpriseCustomer(enterpriseId);
							}
						},
						'sortchange' : function(grid, sortInfo) {
						}
					}
				});
	},
	search : function() {
		$search( {
			searchPanel : this.fPanel_searchEnterprise,
			gridPanel : this.gridPanel
		});
	},
	reset : function() {
		this.fPanel_searchEnterprise.getForm().reset();
	},
	createRs : function(){
		new InvestEnterpriseForm({isAllReadOnly : false,gridpanel : this.gridPanel,businessType:this.businessType}).show()
	},
	editRs : function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new InvestEnterpriseForm( {
				id : record.data.id,isAllReadOnly : false,gridpanel : this.gridPanel,businessType:this.businessType
			}).show();
		}	
	},
	seeRs : function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new InvestEnterpriseForm( {
				id : record.data.id,isAllReadOnly : true,gridpanel : this.gridPanel
			}).show();
		}	
	},
	removeRs : function(){
			var griddel = this.gridPanel;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}
			Ext.Msg.confirm("提示!",'确定要删除吗？',
				function(btn) {

					if (btn == "yes") {
						 var ids = Array();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							
								ids.push(row.data.id)
						}
						Ext.Ajax.request( {
							url : __ctxPath + '/customer/multiDelInvestEnterprise.do',
							method : 'POST',
							success : function(response) {
								Ext.ux.Toast.msg('操作信息', '删除成功!');
								storedel.reload()	
							},
							params : {ids:ids}
						});
					}
				})
		}
});


var amselectControlperson = function(obj) {
	Ext.getCmp('amcontrolpersonName').setValue(obj.name);
	Ext.getCmp('amcontrolpersonId').setValue(obj.id);
}
var getEnterObjArray = function(objArray) {
	Ext.getCmp('entergslname').setValue(objArray[(objArray.length) - 1].text
			+ "_" + objArray[(objArray.length) - 2].text);
	Ext.getCmp('entergslnameid').setValue(objArray[(objArray.length) - 1].id
			+ "," + objArray[(objArray.length) - 2].id);
}

