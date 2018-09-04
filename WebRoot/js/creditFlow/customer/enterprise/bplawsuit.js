bplawsuit = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	constructor : function(_cfg) {

		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		bplawsuit.superclass.constructor.call(this, {
					id:'bplawsuit',
			        buttonAlign:'center',
			        title:'诉讼情况',
			        iconCls : 'btn-add',
					height : 500,
					width : 1000,
					constrainHeader : true ,
					collapsible : true, 
					frame : true ,
					border : false ,
					resizable : true,
					layout:'fit',
					autoScroll : true ,
					bodyStyle:'overflowX:hidden',
					constrain : true ,
					closable : true,
					modal : true,
					items : [this.formPanel],
					buttons:[{
					    text : '保存',
						iconCls : 'btn-save',
						scope : this,
						hidden : this.isReadOnly,
						handler : this.save
					},{
					    text : '关闭',
						iconCls : 'close',
						scope : this,
						handler : function(){
							this.close();
						}
					}]
					
				})
	},
	initUIComponents : function() {   
		this.bplawsuitList =new bplawsuitList({
		
			isHiddenAddBtn:this.isReadOnly,
			isHiddenDelBtn:this.isReadOnly,
			isHiddenSeeBtn:false,
			enterpriseId:this.enterpriseId,
			isReadOnly:this.isReadOnly
		})

		this.formPanel = new Ext.FormPanel( {
			url :  __ctxPath + '/creditFlow/customer/enterprise/saveBpCustEntLawsuit.do',
			layout : 'fit',
			border : false,
			items : this.bplawsuitList,
			modal : true,
			height : 345,
			width : 610
			
		})
		
	},
	save:function(){
			var win=this;
			var bplawsuitList=this.bplawsuitList;
			var bplawsuitListData=getbplawsuitData(bplawsuitList);
			this.formPanel.getForm().submit({
				method : 'POST',
				waitTitle : '连接',
				waitMsg : '消息发送中...',
				params : {enterpriseId:this.enterpriseId,
							'bplawsuitListData':bplawsuitListData
							},
				success : function(form, action) {
					Ext.ux.Toast.msg('操作信息', '保存成功!');
					win.destroy();
				},
				failure : function(form, action) {
					Ext.ux.Toast.msg('操作信息', '保存失败!');
				}
			});				
		}		    
	
});
relevance = Ext.extend(Ext.Window,{
	win:'',
	constructor:function(_cfg){
		Ext.applyIf(this,_cfg);
		this.initComponents();
		relevance.superclass.constructor.call(this,{
			//id:'relevance',
		    title:'关联公司',
			height : 500,
			width : 1000,
			collapsible : true, 
			frame : true ,
			border : false ,
			resizable : true,
			layout:'fit',
			autoScroll : true ,
			bodyStyle:'overflowX:hidden',
			constrain : true ,
			closable : true,
			modal : true,
			items : [this.gridPanel]		
		});
	},
	initComponents:function(){
		win = this;
		var store = this.store;
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				hidden : this.isReadOnly==true?true:(isGranted('_create_qykh') ? false : true),
				scope:this,
				handler: function() {
					
					if(typeof(this.type)=="undefined"||this.type==null){
						new relevance({enterId:this.enterId,isReadOnly:false,type:'type',store:this.jStore_enterprise}).show()
					}else{
						addEnterpriseWin(this.jStore_enterprise);
					}
					//addEnterpriseWin(this.jStore_enterprise,this.enterId);
					//new enterpriseObj({winId:id,enterId:this.enterId})
				}
			},new Ext.Toolbar.Separator({
				hidden :(typeof(this.type)=="undefined"||this.type==null)?true:false
			}),{
				iconCls : 'btn-select',
				text : '选择',
				xtype : 'button',
				hidden :(typeof(this.type)=="undefined"||this.type==null)?true:false,
				scope:this,
				handler: function() {
					var ss = this.gridPanel.getSelectionModel().getSelections();
					var datas = "";
					var enterId = this.enterId;
					var len = ss.length;
					if (0 == len) {
						Ext.ux.Toast.msg('状态', '请选择一条记录');
					} else {
						for(var i=0;i<len;i++){
							datas += ss[0].data.id+",";
						}
						Ext.Ajax.request({
							url:__ctxPath + '/creditFlow/customer/enterprise/addRevEnterprise.do?',
							method:'POST',
							success:function(response){
								store.reload();
								win.close();
							},
							failure:function(response){
							},
							params: { ids: datas,enterId: enterId}
						});
						//enterpriseId = ss[0].data.id;
						//seeEnterpriseCustomer(enterpriseId);
					}
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
				hidden :this.isReadOnly==true?true:(isGranted('_edit_qykh') ? false : true) ,
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
				hidden :this.isReadOnly==true?true:(isGranted('_remove_qykh') ? false : true) ,
				scope:this,
				handler: function() {
					var obj = this;
					var selected = obj.gridPanel.getSelectionModel().getSelections();
					var len = selected.length;
					var list = "";
					var mycars = new Array()
					for (var j = 0; j < len; j++) {
						mycars[j]= selected[j];
						if (j == (len - 1)) {
							list += selected[j].id;
						} else
							list += selected[j].id + ",";
					}
					if (0 == len) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					} else {
						var type = this.type;
						var enterId = this.enterId;
						var grid = obj.gridPanel;
						Ext.MessageBox.confirm('确认删除', '您确认要删除所选记录吗？', function(btn) {
							if (btn == 'yes') {
								if(typeof(type)=="undefined"||type==null){
									Ext.Ajax.request({
										url : __ctxPath + '/creditFlow/customer/enterprise/ajaxDeleteEnterprise.do',
										method : 'POST',
										timeout : 60000,
										scope : this,
										success : function(response,request) {
											var object=Ext.util.JSON.decode(response.responseText)
											if(object.flag=='false'){
												Ext.ux.Toast.msg('状态',object.msg)
											}else{
												Ext.ux.Toast.msg('状态', '删除成功!');
											}
											grid.getStore().removeAll(mycars);
										},
										failure : function(result, action) {
											var msg = Ext.decode(action.response.responseText);
											Ext.ux.Toast.msg('状态', msg);
										},
										params : {
											listId : list,
											enterId:enterId
										}
									})
									return ;
								}
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
			}]
		});
		 this.pageSize = 25;
			this.jStore_enterprise = new Ext.data.JsonStore({
			url : __ctxPath + '/creditFlow/customer/enterprise/getListEnterprise.do?',
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
			},{
				name:'taxnum'
			}],
			listeners : {
				'loadexception' : function(proxy, options, response, err) {
					var data = Ext.decode(response.responseText);
					//Ext.ux.Toast.msg('提示', '数据加载失败，请保证网络畅通！');
				}
			}
		});
		this.jStore_enterprise.load({
			params : {
				start : 0,
				enterId:this.enterId,
				type:this.type,
				limit : this.pageSize
			}
		});
		this.gridPanel=new HT.GridPanel({
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
					header : "税务登记号",
					width : 110,
					sortable : true,
					dataIndex : 'taxnum'
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
});
relevance1 = Ext.extend(Ext.Window,{
	constructor:function(_cfg){
		Ext.applyIf(this,_cfg);
		this.initComponents();
		relevance1.superclass.constructor.call(this,{
			//id:'relevance',
		    title:'关联公司',
			height : 500,
			width : 1000,
			collapsible : true, 
			frame : true ,
			border : false ,
			resizable : true,
			layout:'fit',
			autoScroll : true ,
			bodyStyle:'overflowX:hidden',
			constrain : true ,
			closable : true,
			modal : true,
			items : [this.gridPanel]		
		});
	},
	initComponents:function(){
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				hidden : isGranted('_create_qykh') ? false : true,
				scope:this,
				handler: function() {
					if(typeof(this.type)=="undefined"||this.type==null){
						new relevance({enterId:this.enterId,isReadOnly:false,type:'type'}).show()
					}else{
						addEnterpriseWin(this.jStore_enterprise,this.enterId);
					}
					//addEnterpriseWin(this.jStore_enterprise,this.enterId);
					//new enterpriseObj({winId:id,enterId:this.enterId})
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
			}]
		});
		 this.pageSize = 25;
			this.jStore_enterprise = new Ext.data.JsonStore({
			url : __ctxPath + '/creditFlow/customer/enterprise/getListEnterprise.do?',
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
			},{
				name:'taxnum'
			}],
			listeners : {
				'loadexception' : function(proxy, options, response, err) {
					var data = Ext.decode(response.responseText);
					//Ext.ux.Toast.msg('提示', '数据加载失败，请保证网络畅通！');
				}
			}
		});
		this.jStore_enterprise.load({
			params : {
				start : 0,
				enterId:this.enterId,
				type:this.type,
				limit : this.pageSize
			}
		});
		this.gridPanel=new HT.GridPanel({
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
					header : "税务登记号",
					width : 110,
					sortable : true,
					dataIndex : 'taxnum'
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
});
