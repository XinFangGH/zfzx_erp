/**
 * @author lisl
 * @class EnterpriseView
 * @description 企业客户列表
 * @extends Ext.Panel
 */
EnterpriseView = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		EnterpriseView.superclass.constructor.call(this, {
			id : 'EnterpriseView'+this.singleType,
			title : '企业客户档案管理',
			region : 'center',
			layout : 'border',
			iconCls : 'btn-tree-team23',
			items : [
				this.fPanel_searchEnterprise,
				this.gridPanel
			]
		});
	},
	initUIComponents : function() {
		    
		    this.pageSize = 25;
			this.jStore_enterprise = new Ext.data.JsonStore({
			url : __ctxPath + '/creditFlow/customer/enterprise/entListEnterprise.do?isAll='+isGranted('_seeAll_qykh'),
	//		url : __ctxPath + '/creditFlow/customer/enterprise/ajaxQueryEnterprise.do?isAll='+isGranted('_seeAll_qykh'),
			root : 'topics',
			totalProperty : 'totalProperty',
			remoteSort : true,
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
				name : 'legalpersonName'
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
				name : 'orgName'
			}, {
				name : 'shopId'
			}, {
				name : 'shopName'
			},{
				name:'taxnum'
			},{
				name:'cardnumber'
			},{
				name:'selfemail'
			},{
				name:'cellphone'
			},{
				name:'email'
			}],
			listeners : {
				'loadexception' : function(proxy, options, response, err) {
					var data = Ext.decode(response.responseText);
					Ext.ux.Toast.msg('提示', '数据加载失败，请保证网络畅通！');
				}
			}
		});
		var jStore_enterprise=this.jStore_enterprise;
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
						url :  __ctxPath +'/creditFlow/customer/enterprise/batchImportEBatchImportDatabase.do',
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
							Ext.getCmp('importEnterpriseFrom').getForm().submit({
								method : 'POST',
								waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function(form, action) {
									Ext.ux.Toast.msg('状态', '导入成功!');
									Ext.getCmp('importEnterpriseWin').destroy();
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
		var isShow=false;
		var itemwidth=0.2;
		if(RoleType=="control"){
		  isShow=true;
		  itemwidth=0.2;
		}
		this.fPanel_searchEnterprise = new Ext.FormPanel({
			frame : false,
			region : 'north',
			height : 60,
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
			items : [isShow?{
				columnWidth :itemwidth,
				bodyStyle : 'padding:5px 0px 0px 0px',
				items : [
				{
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
							url : __ctxPath+'/system/getControlNameOrganization.do',
							fields : ['companyId', 'companyName']
						})
				}
			]}:{columnWidth : 0.01},{
				columnWidth :itemwidth,
				bodyStyle : 'padding:5px 0px 0px 0px',
				items : [{
					xtype : 'textfield',
					fieldLabel : '企业名称',
					name : 'enterprisename',
					maxLength : 50, 
					maxLengthText : '长度不能超过50',
					labelWidth : "65",
					anchor : "100%",
					listeners : {
						'specialkey' : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								this.searchByCondition();
							}
						}
					}
				}]
			}, {
				columnWidth : itemwidth,
				items : [{
					xtype : 'textfield',
					fieldLabel : '证件号码',
					name : 'organizecode',
					labelWidth : "65",
					anchor : "100%",
					listeners : {
						'specialkey' : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								this.searchByCondition();
							}
						}
					}
				}]
			}/*, {
				columnWidth :itemwidth,
				items : [{
					xtype : "dickeycombo",
					nodeKey : 'smallloan_haveCharcter',
					fieldLabel : '所有制性质',
					hiddenName : 'ownership',
					labelWidth : "65",
					anchor : "100%",
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								if (combox.getValue() == 0
										|| combox.getValue() == 1) {
									combox.setValue("");
								} else {
									combox.setValue(combox.getValue());
								}
								combox.clearInvalid();
							})
						},
						'specialkey' : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								this.searchByCondition();
							}
						}
					}
				}]
			}*//*,{
				columnWidth : itemwidth,
				items : [{
					xtype : 'textfield',
					fieldLabel : '营业执照号码',
					name : 'cciaa',
					labelWidth : "65",
					anchor : "100%",
					listeners : {
						'specialkey' : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								this.searchByCondition();
							}
						}
					}
				}]
			
			}*/,{
					columnWidth :itemwidth,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '所属门店',
						xtype : "combo",
						anchor : "100%",
						hiddenName : "shopId",
						displayField : 'orgUserName',
						valueField : 'orgUserId',
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath
									+ '/system/getOrgUserNameOrganization.do',
							fields : ['orgUserId', 'orgUserName']
						})
					}]
				},/*{
			
				columnWidth :itemwidth-0.02,
				items : [{
					xtype : "dickeycombo",
					hiddenName : "customerLevel",
					nodeKey : 'customerLevel', // xx代表分类名称
					fieldLabel : "客户级别",
					labelWidth : "65",
					anchor : "100%",
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
			
			},*/{
				columnWidth : 0.07,
				items : [{
					id : 'searchButton',
					xtype : 'button',
					text : '查询',
					iconCls : 'btn-search',
					width : 60,
					//labelWidth : "30",
					formBind : true,
					labelWidth : 20,
					bodyStyle : 'padding:5px 0px 0px 0px',
					scope : this,
					handler : this.searchByCondition
				}]
			},{
							
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
				text : '建档',
				xtype : 'button',
				hidden : isGranted('_create_qykh') ? false : true,
				scope:this,
				handler: function() {
					addEnterpriseWin(this.jStore_enterprise);
				}
			},new Ext.Toolbar.Separator({
				hidden : isGranted('_detail_qykh') ? false : true
			}),{
				iconCls : 'btn-detail',
				text : '查看',
				xtype : 'button',
				scope:this,
				hidden : isGranted('_detail_qykh') ? false : true,		
				handler : function() {
					var ss = this.gridPanel.getSelectionModel().getSelections();
					var len = ss.length;
					if (len > 1) {
						Ext.ux.Toast.msg('状态', '只能选择一条记录');
					} else if (0 == len) {
						Ext.ux.Toast.msg('状态', '请选择一条记录');
					} else {
						enterpriseId = ss[0].data.id;
						seeEnterpriseCustomer(enterpriseId);
					}
				}
			},new Ext.Toolbar.Separator({
				hidden : isGranted('_create_qykh') ? false : true
			}),{
				iconCls : 'btn-edit',
				text : '编辑',
				xtype : 'button',
				hidden : isGranted('_edit_qykh') ? false : true,
				scope:this,
				handler: function() {
					var selections = this.gridPanel.getSelectionModel().getSelections();
					var len = selections.length;
					if (len > 1) {
						Ext.ux.Toast.msg('状态', '只能选择一条记录');
						return;
					} else if (0 == len) {
						Ext.ux.Toast.msg('状态', '请选择一条记录');
						return;
					}
					var enterpriseId = selections[0].data.id;
					editEnterpriseInfo(enterpriseId,this.jStore_enterprise,null);
				}
			},new Ext.Toolbar.Separator({
				hidden : isGranted('_remove_qykh') ? false : true
			}),{
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				hidden : isGranted('_remove_qykh') ? false : true,
				scope:this,
				handler: function() {
					var obj = this;
					var selected = obj.gridPanel.getSelectionModel().getSelections();
					var len = selected.length;
					var list = "";
					for (var j = 0; j < len; j++) {
						if (j == (len - 1)) {
							list += selected[j].id;
						} else
							list += selected[j].id + ",";
					}
					if (0 == len) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					} else {
						Ext.MessageBox.confirm('确认删除', '您确认要删除所选记录吗？', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/customer/enterprise/ajaxDeleteWithIdEnterprise.do',
									method : 'POST',
									timeout : 60000,
									scope : this,
									success : function(response,request) {
										obj.searchByCondition();
										var object=Ext.util.JSON.decode(response.responseText)
										if(object.flag=='false'){
											Ext.ux.Toast.msg('状态',object.msg)
										}else{
											Ext.ux.Toast.msg('状态', '删除成功!');
										}
									},
									failure : function(result, action) {
										var msg = Ext.decode(action.response.responseText);
										Ext.ux.Toast.msg('状态', msg);
									},
									params : {
										listId : list
									}
								});
							}
						});
					}
				}
			}, new Ext.Toolbar.Separator({
				hidden : (isGranted('_edit_qykh') ? false : true)||(isGranted('_enterprise_ywwl') ? false : true)
			}), {
				iconCls : 'btn-detail',
				text : '业务往来',
				xtype : 'button',
				scope:this,
				hidden : isGranted('_enterprise_ywwl') ? false : true,		
				handler : function() {
					var ss = this.gridPanel.getSelectionModel().getSelections();
					var len = ss.length;
					if (len > 1) {
						Ext.ux.Toast.msg('状态', '只能选择一条记录');
					} else if (0 == len) {
						Ext.ux.Toast.msg('状态', '请选择一条记录');
					} else {
						var  organizecode = ss[0].data.organizecode;
						var  enterpriseId =ss[0].data.id;
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
				}
			
			},new Ext.Toolbar.Separator({
				hidden : (isGranted('_enterprise_ywwl') ? false : true)||(isGranted('_export_qykh') ? false : true)
			}),{
				iconCls : 'btn-xls',
				text : '导出到Excel',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_export_qykh') ? false : true,
				handler : function() {
					var companyId = (isShow==true?this.getCmpByName("companyId").getValue():null);//分公司id
					var enterprisename = this.getCmpByName("enterprisename").getValue();
					var organizecode = this.getCmpByName("organizecode").getValue();
//					var cciaa = this.getCmpByName("cciaa").getValue();
					var shopId = this.getCmpByName("shopId").getValue();//门店id
					window.open(__ctxPath + '/creditFlow/customer/enterprise/outputExcelEnterprise.do?enterprisename='
								+enterprisename+'&organizecode='+organizecode
								+'&shopId='+shopId,'_blank');
				}
			},{
				iconCls : 'btn-add',
				text : '加入黑名单',
				xtype : 'button',
				hidden : isGranted('_addBlack_qykh') ? false : true,
				scope:this,
				handler: this.addBlackList
			}]
		});
		this.jStore_enterprise.load({
			params : {
				start : 0,
				limit : this.pageSize
			}
		});
		var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(万元)';
				}
		this.gridPanel=new HT.GridPanel({
			name : 'SmallProjectGrid',
			region : 'center',
			tbar : this.topbar,
		    plugins : [summary],	
			rowActions : false,//不启用RowActions
			loadMask : true,
			store : this.jStore_enterprise,
			columns : [{
					header : "所属分公司",
					width : 160,
					sortable : true,
					hidden:RoleType=="control"?false:true,
					dataIndex : 'orgName'
				}, {
					header : "所属门店",
					width : 160,
					sortable : true,
					dataIndex : 'shopName'
				}, {
					header : "企业名称",
					width : 160,
					sortable : true,
					summaryRenderer : totalMoney,
					dataIndex : 'enterprisename'
				}/*, {
					header : "企业简称",
					width : 120,
					sortable : true,
					dataIndex : 'shortname'
				}*/, {
					header : "证件号码",
					width : 120,
					align:'center',
					sortable : true,
					dataIndex : 'organizecode'
				}/*, {
					header : "营业执照号码",
					width : 120,
					sortable : true,
					dataIndex : 'cciaa'
				}*/, {
					header : "税务登记号",
					width : 110,
					align:'center',
					sortable : true,
					dataIndex : 'taxnum'
				}, {
					header : "法人",
					width : 55,
					align:'center',
					sortable : true,
					dataIndex : 'legalpersonName'
				}, {
					header : "注册资金(万元)",
					width : 80,
					align:'right',
					summaryType : 'sum',
					sortable : true,
					dataIndex : 'registermoney',
					renderer : function(val) {
						if (val != "0" || val != null) {
							return val;
						} else {
							return '';
						}
					}
				}, {
					header : "联系电话",
					width : 100,
					align:'center',
					sortable : true,
					dataIndex : 'telephone'
				}, {
					id : 'content',
					header : "企业成立日期",
					width : 90,
					align:'center',
					sortable : true,
					dataIndex : 'opendate'
				}
			],
			listeners : {
				'rowdblclick' : function(grid, index, e) {
					enterpriseId = grid.getSelectionModel().getSelected()
							.get('id');
					seeEnterpriseCustomer(enterpriseId);
				},
				'sortchange' : function(grid, sortInfo) {
				}
			}
		});
	},
	searchByCondition : function() {
		this.jStore_enterprise.baseParams.organizecode = this.fPanel_searchEnterprise.getForm().findField('organizecode').getValue();
		this.jStore_enterprise.baseParams.enterprisename = this.fPanel_searchEnterprise.getForm().findField('enterprisename').getValue();
		//this.jStore_enterprise.baseParams.ownership = this.fPanel_searchEnterprise.getForm().findField('ownership').getValue();
		this.jStore_enterprise.baseParams.shopId = this.fPanel_searchEnterprise.getForm().findField('shopId').getValue();
		//新添加查询条件   营业执照号码  隐藏了客户级别
//		this.jStore_enterprise.baseParams.cciaa = this.fPanel_searchEnterprise.getForm().findField('cciaa').getValue();
		//this.jStore_enterprise.baseParams.customerLevel = this.fPanel_searchEnterprise.getForm().findField('customerLevel').getValue();
		if(null!=this.fPanel_searchEnterprise.getForm().findField('companyId')){
		   this.jStore_enterprise.baseParams.companyId = this.fPanel_searchEnterprise.getForm().findField('companyId').getValue();
		}
		this.jStore_enterprise.load({
			params : {
				start : 0,
				limit : this.pageSize
			}
		});
	},
	reset : function() {
		this.fPanel_searchEnterprise.getForm().reset();
	},
	
	// 添加p2p账户
	addP2pUser : function(obj, e) {
		var selections = this.gridPanel.getSelectionModel().getSelections();
		var grid=this.gridPanel;
		var len = selections.length;
		var bStr = obj.ptype;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		}
		var enterpriseId = selections[0].data.id;
		var cellphone = selections[0].data.telephone;
		var selfemail = selections[0].data.email;
		var cardnumber = selections[0].data.organizecode;
		new BpCustRelationForm({
			type:"b_loan",
			pType:'4',//4为企业
			bStr:bStr,
			userId:enterpriseId,
			cellphone:cellphone,
			selfemail:selfemail,
			cardnumber:cardnumber
		}).show();
	},
	addBlackList : function(){
		var selections = this.gridPanel.getSelectionModel().getSelections();
		var grid=this.gridPanel;
		var len = selections.length;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		}
		var enterpriseId = selections[0].data.id;
		var fp=new Ext.FormPanel({
			frame:true,
			labelAlign:'right',
			bodyStyle : 'padding:5px 5px 5px 5px',
			labelWidth:60,
			border:false,
			url:__ctxPath + '/creditFlow/customer/enterprise/addBlackEnterprise.do?id='+enterpriseId,
		    items:[{
		        xtype:'textarea',
		        fieldLabel:'原因说明',
		        allowBlank:false,
		        name:'blackReason',
		        anchor:'100%'
		    }]
		}) 
		var window=new Ext.Window({
				title:'加入黑名单',
				width:400,
				height:150,
				modal : true,
			  	items:fp,
			  	buttonAlign:'center',
			  	 buttons:[{
				  	 text:'提交',
				  	 iconCls : 'btn-save',
				  	 handler:function(){
				  	 	fp.getForm().submit({
				  	 	   	waitMsg : '正在提交...',
				  	 	  	method : 'post',
							success : function(form, action) {
								Ext.ux.Toast.msg('状态', '添加成功');
								window.destroy();
								grid.getStore().reload()
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('状态', '添加失败');
							}
				  	 	})
				  	 }
				   },{
			   		 text:'取消',
			   		 iconCls : 'btn-cancel',
			   		 handler:function(){
			   		 	window.destroy()
			   		 }
			   	}]
			})
            window.show()
	}
});

