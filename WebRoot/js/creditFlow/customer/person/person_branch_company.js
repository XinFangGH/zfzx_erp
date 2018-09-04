person_branch_company = Ext.extend(Ext.Window,{
	constructor:function(_cfg){
		Ext.applyIf(this,_cfg);
		this.initUIComponents();
		companyView.superclass.constructor.call(this,{
			layout:'fit',
			width:(window.screen.width-100)*0.8,
			height:(window.screen.height-100)*0.8,
			items:[this.panel]
		});
	},
	initUIComponents:function(){
		
		this.panel = new companyView({personId:this.personId});
	}
	
});

var person_view = function(projectId){
	Ext.Ajax.request({
	   url:	__ctxPath + '/project/getSlSmallloanProject.do',
	   method:'POST',
	   success: function(response,action){
	   		var respText = response.responseText;
			var pro = Ext.util.JSON.decode(respText);	
	   		//var panel = new person_branch_company_v({personId:pro.data.oppositeID});
	   },
	   failure: function(response){
	   	
	   },
	   params: { projectId: projectId }
	});

}

person_branch_company_v = Ext.extend(Ext.Panel,{
	isHiddenAddBtn:false,
	isHiddenDelBtn:false,
	isHiddenEdiBtn:false,
	isHiddenSeeBtn:false,
	isFlow:false,
	isAllReadOnly:false,
	projectId:null,
	constructor:function(_cfg){
		var jsCtArr = [		
			__ctxPath + '/js/creditFlow/customer/common/common.js',
			__ctxPath + '/js/creditFlow/customer/enterprise/updateEnterprise.js'
		];
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
			this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
		}
		if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
			this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
		}
		if (typeof(_cfg.isHiddenEdiBtn) != "undefined") {
			this.isHiddenEdiBtn = _cfg.isHiddenEdiBtn;
		}
		if (typeof(_cfg.isHiddenSeeBtn) != "undefined") {
			this.isHiddenSeeBtn = _cfg.isHiddenSeeBtn;
		}
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.isFlow) != "undefined") {
			this.isFlow = _cfg.isFlow;
			this.isAllReadOnly=true;
		}
		Ext.applyIf(this,_cfg);
		$ImportSimpleJs(jsCtArr, this.initUIComponents, this);
		companyView.superclass.constructor.call(this,{
			layout:'anchor',
			anchor:'100%',
			autoHeight:true/*,
			items:[this.panel]*/
		});
	},
	initUIComponents:function(){
		//person_view(this.projectId);
		/*this.panel = new companyView1({
			legalpersonid:this.legalpersonid,
			isHiddenAddBtn : this.isHiddenAddBtn,
			isHiddenDelBtn : this.isHiddenDelBtn,
			isHiddenEdiBtn : this.isHiddenEdiBtn,
			isFlow : this.isFlow,
			projectId:this.projectId
		});*/
		this.topbar = new Ext.Toolbar( {
			items : [ {
				xtype : 'button',
				text : '增加',
				tooltip : '增加一条新的个人旗下公司信息',
				iconCls : 'btn-add',
				hidden:this.isHiddenAddBtn,
				scope : this,
				handler : this.createRs
			}, {
				xtype : 'button',
				text : '编辑',
				tooltip : '编辑选中的个人旗下公司信息',
				iconCls : 'btn-edit',
				hidden:this.isHiddenEdiBtn,
				scope : this,
				handler : this.editRs
			}, {
				xtype : 'button',
				text : '删除',
				tooltip : '删除选中的企业信息',
				iconCls : 'btn-delete',
				scope : this,
				hidden:this.isHiddenDelBtn,
				handler : this.removeSelRs
			},{
				xtype : 'button',
				text : '查看',
				tooltip : '查看选中的个人旗下公司信息',
				iconCls : 'btn-detail',
				scope : this,
				handler : this.seeRs
			}]
		});
		this.jStore_thereunder = new Ext.data.JsonStore( {
				url : __ctxPath+'/creditFlow/customer/person/queryPersonThereunder.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				autoLoad : true,
				fields : [ {
					name : 'id'
				}, {
					name : 'companyname'
				}, {
					name : 'licensenum'
				}, {
					name : 'value'
				}, {
					name : 'registerdate'  
				}, {
					name : 'registercapital'  
				}, {
					name : 'address'  
				}, {
					name : 'lnpname'  
				}, {
					name : 'phone'
				},{
					name : 'shortname'
				},{
					name : 'name'
				},{
					name : 'personid'
				},{
					name : 'relateValue'
				}],
				baseParams :{
				pid : this.legalpersonid,
				start : 0,
				limit : 25}
			});
		this.panel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			autoHeight:true,
			showPaging:false,
			store : this.jStore_thereunder,
			columns : [{
				header : "公司名称",
				width : 140,
				sortable : true,
				dataIndex : 'shortname'
			}, {
				header : "营业执照号码",
				width : 100,
				sortable : true,
				dataIndex : 'licensenum'
			}, {
				header : "关系",
				width : 90,
				sortable : true,
				dataIndex : 'relateValue'
			},{
				header : "联系人",
				width : 90,
				sortable : true,
				dataIndex : 'relateValue'
			},{
				header : "联系人电话",
				width : 100,
				sortable : true,
				dataIndex : 'phone'
			}, {
				header : "注册时间",
				width : 90,
				sortable : true,
				dataIndex : 'registerdate'					
			}, {
				id : 'registercapital',
				header : "注册资本",
				width : 80,
				sortable : true,
				dataIndex : 'registercapital'					
			}]
		//end of columns
		});
		this.add(this.panel)
		this.doLayout();
	},
	createRs : function() {
		new ThereunderForm({personId:this.legalpersonid,gridPanel : this.panel}).show();
	},
	
	//把选中ID删除
	removeSelRs : function() {
		$delGridRs( {
			url : __ctxPath + '/creditFlow/customer/person/deletePersonThereunder.do',
			grid : this.panel,
			idName : 'id'
		});
	},
	//编辑Rs
	editRs : function(record) {
		var s = this.panel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new ThereunderForm( {
				id : record.data.id,personId : record.data.personid,gridPanel : this.panel
			}).show();
		}	
	},
	seeRs : function(record){
		var s = this.panel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{	
			record=s[0]
			new ThereunderForm( {
				id : record.data.id,personId : record.data.personid,gridPanel : this.panel,isReadOnly:true
			}).show();
		}	
	}
});


companyView = Ext.extend(Ext.Panel,{
	constructor:function(_cfg){
		Ext.applyIf(this,_cfg);
		this.initUIComponents();
		companyView.superclass.constructor.call(this,{
			layout:'fit',
			items:[this.gridPanel]
		});
	},
	initUIComponents:function(){
		    this.pageSize = 25;
			this.jStore_enterprise = new Ext.data.JsonStore({
			//url : __ctxPath + '/creditFlow/customer/enterprise/getEnterByProjectEnterprise.do',
			url : __ctxPath+'/creditFlow/customer/person/queryPersonThereunder.do',
			baseParams :{
				//legalpersonid:this.personId
				pid : this.personId
			},
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
				name : 'orgName'
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
		  itemwidth=0.17;
		}
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				hidden : isGranted('_create_qykh') ? false : true,
				scope:this,
				handler: function() {
					//addEnterpriseWin1(this.);
					addEnterpriseWin1(this.jStore_enterprise,true,this.personId);
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
					editEnterpriseInfo1(enterpriseId,this.jStore_enterprise,null,this.personId,true);
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
						Ext.MessageBox.confirm('确认删除', '是否确认删除选中的<font color=red>'+ len + '</font>条记录', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/customer/enterprise/ajaxDeleteWithIdEnterprise.do',
									method : 'POST',
									timeout : 60000,
									scope : this,
									success : function(response,request) {
										//obj.searchByCondition();
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
								obj.gridPanel.getStore().reload();
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
					window.open(__ctxPath + '/creditFlow/customer/enterprise/outputExcelEnterprise.do','_blank');
				}
			},/*importButton,*/
			{
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
		this.gridPanel=new HT.GridPanel({
			isAllReadOnly:this.isAllReadOnly,
			name : 'SmallProjectGrid',
			region : 'center',
			tbar : this.topbar,
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
					header : "所有制性质",
					width : 110,
					sortable : true,
					dataIndex : 'ownershipv'
				}, {
					header : "法人",
					width : 55,
					sortable : true,
					dataIndex : 'legalperson'
				}, {
					header : "注册资金",
					width : 80,
					sortable : true,
					dataIndex : 'registermoney',
					renderer : function(val) {
						if (val != "0" || val != null) {
							return val + '万元';
						} else {
							return '';
						}
					}
				}, {
					header : "联系电话",
					width : 100,
					sortable : true,
					dataIndex : 'telephone'
				}, {
					id : 'content',
					header : "企业成立日期",
					width : 90,
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
	}
})

companyView1 = Ext.extend(Ext.Panel,{
	pId :'',
	isFlow:false,
	constructor:function(_cfg){
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
			this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
		}
		if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
			this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
		}
		if (typeof(_cfg.isHiddenEdiBtn) != "undefined") {
			this.isHiddenEdiBtn = _cfg.isHiddenEdiBtn;
		}
		if (typeof(_cfg.isHiddenSeeBtn) != "undefined") {
			this.isHiddenSeeBtn = _cfg.isHiddenSeeBtn;
		}
		if (typeof(_cfg.isFlow) != "undefined") {
			this.isFlow = _cfg.isFlow;
		}
		Ext.applyIf(this,_cfg);
		this.initUIComponents();
		companyView1.superclass.constructor.call(this,{
			layout:'fit',
			items:[this.gridPanel]
		});
	},
	initUIComponents:function(){
		   // this.pageSize = 25;
			this.jStore_enterprise = new Ext.data.JsonStore({
			url : __ctxPath + '/creditFlow/customer/enterprise/getEnterByProjectEnterprise.do',
			baseParams :{
				legalpersonid:this.legalpersonid
			},
			root : 'topics',
			totalProperty : 'totalProperty',
			remoteSort : true,
			fields : [{
				name : 'id'
			}, {
				name : 'enterprisename'
			},{
				name:'legalpersonid'
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
				name : 'orgName'
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
		  itemwidth=0.17;
		}
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				hidden : this.isHiddenAddBtn,
				//hidden : isGranted('_create_qykh') ? false : true,
				scope:this,
				handler: function() {
					//addEnterpriseWin1(this.);
					var personId = this.gridPanel.getStore().getAt(0);
					var gridPanel = this.gridPanel;
					if(personId==null){
						var store = this.jStore_enterprise;
							var projectId = this.projectId;
							Ext.Ajax.request({
							   url:	__ctxPath + '/project/getSlSmallloanProject.do',
							   method:'POST',
							   success: function(response,action){
							   		var respText = response.responseText;
									var pro = Ext.util.JSON.decode(respText);
									addEnterpriseWin1(store,true,pro.data.oppositeID,gridPanel);
							   		//var panel = new person_branch_company_v({personId:pro.data.oppositeID});
							   },
							   failure: function(response){
							   	
							   },
							   params: { projectId: projectId }
							});
					}else{
						addEnterpriseWin1(this.jStore_enterprise,true,personId.data.legalpersonid,this.gridPanel);
					}
					
					//addEnterpriseWin1(this.jStore_enterprise,true,personId.data.legalpersonid);
				}
			}/*,new Ext.Toolbar.Separator({
				hidden : isGranted('_detail_qykh') ? false : true
			})*/,{
				iconCls : 'btn-detail',
				text : '查看',
				xtype : 'button',
				scope:this,
				hidden : this.isHiddenSeeBtn,
				//hidden : isGranted('_detail_qykh') ? false : true,		
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
			},/*new Ext.Toolbar.Separator({
				hidden : isGranted('_create_qykh') ? false : true
			}),*/{
				iconCls : 'btn-edit',
				text : '编辑',
				xtype : 'button',
				//hidden : isGranted('_edit_qykh') ? false : true,
				scope:this,
				hidden : this.isHiddenEdiBtn,
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
					var personId = selections[0].data.legalpersonid;
					editEnterpriseInfo1(enterpriseId,this.jStore_enterprise,null,personId,true);
				}
			},/*new Ext.Toolbar.Separator({
				hidden : isGranted('_remove_qykh') ? false : true
			}),*/{
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				//hidden : isGranted('_remove_qykh') ? false : true,
				hidden : this.isHiddenDelBtn,
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
						Ext.MessageBox.confirm('确认删除', '是否确认删除选中的<font color=red>'+ len + '</font>条记录', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath + '/creditFlow/customer/enterprise/ajaxDeleteWithIdEnterprise.do',
									method : 'POST',
									timeout : 60000,
									scope : this,
									success : function(response,request) {
										//obj.searchByCondition();
										var object=Ext.util.JSON.decode(response.responseText)
										if(object.flag=='false'){
											Ext.ux.Toast.msg('状态',object.msg)
										}else{
											Ext.ux.Toast.msg('状态', '删除成功!');
											obj.gridPanel.store.reload();
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
								
								//obj.gridPanel.getStore().reload();
							}
							for(var j = 0; j< len; j++){
								obj.gridPanel.getStore().remove(selected[j]);
							}
							//obj.gridPanel.getStore().remove(row);
							obj.gridPanel.getStore().reload();
							obj.gridPanel.getStore().reload();
						});
					}
				}
			}/*, new Ext.Toolbar.Separator({
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
									    	url: __ctxPath + '/credit/customer/enterprise/isEnterpriseEmpty.do',   
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
					window.open(__ctxPath + '/credit/customer/enterprise/outputExcel.do','_blank');
				}
			},importButton,
			{
				iconCls : 'btn-add',
				text : '加入黑名单',
				xtype : 'button',
				hidden : isGranted('_addBlack_qykh') ? false : true,
				scope:this,
				handler: this.addBlackList
			}*/]
		});
		this.jStore_enterprise.load({
		});
		this.gridPanel=new HT.GridPanel({
			name : 'SmallProjectGrid',
			region : 'center',
			tbar : this.topbar,
			showPaging:'',
			rowActions : false,//不启用RowActions
			loadMask : true,
			autoHeight:true,
			store : this.jStore_enterprise,
			columns : [{
					header : "所属分公司",
					width : 160,
					sortable : true,
					hidden:RoleType=="control"?false:true,
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
					header : "所有制性质",
					width : 110,
					sortable : true,
					dataIndex : 'ownershipv'
				}, {
					header : "法人",
					width : 55,
					sortable : true,
					dataIndex : 'legalperson'
				}, {
					header : "注册资金",
					width : 80,
					sortable : true,
					dataIndex : 'registermoney',
					renderer : function(val) {
						if (val != "0" || val != null) {
							return val + '万元';
						} else {
							return '';
						}
					}
				}, {
					header : "联系电话",
					width : 100,
					sortable : true,
					dataIndex : 'telephone'
				}, {
					id : 'content',
					header : "企业成立日期",
					width : 90,
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
	}
})