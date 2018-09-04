Ext.ns('AppRoleView');
/**
 * @author:
 * @class AppRoleView
 * @extends Ext.Panel
 * @description 用户角色列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
AppRoleView = Ext.extend(Ext.Panel, {
	// 条件搜索Panel
	searchPanel : null,
	// 数据展示Panel
	gridPanel : null,
	// GridPanel的数据Store
	store : null,
	// 头部工具栏
	topbar : null,
	//机构id
	oId:null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		AppRoleView.superclass.constructor.call(this, {
					id : 'AppRoleView',
					title : '角色权限设置',
					iconCls:"menu-flowManager",
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			height : 35,
			region : 'north',
			frame : false,
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			id : 'AppRoleSearchForm',
			layout : 'hbox',
			defaults : {
				xtype : 'label',
				border : false,
				margins : {
					top : 2,
					right : 4,
					bottom : 2,
					left : 4
				}
			},
			items : [{
						text : '角色名称'
					}, {
						xtype : 'textfield',
						name : 'Q_roleName_S_LK'
					}, {
						text : '角色描述'
					}, {
						xtype : 'textfield',
						name : 'Q_roleDesc_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('AppRoleSearchForm');
							var gridPanel = Ext.getCmp('AppRoleGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					},{
						xtype : 'button',
						text : '重置',
						iconCls : 'btn-reset',
						scope : this,
						handler : function(){
							this.searchPanel.getForm().reset()
						}
					}]
		});// end of the searchPanel
		this.store = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath + '/system/listForOneAppRole.do'
							}),
					reader : new Ext.data.JsonReader({
								root : 'result',
								totalProperty : 'totalCounts',
							//	id : 'id',
								fields : [{
											name : 'roleId',
											type : 'long'
										}, 'roleName', 'roleDesc', {
											name : 'status',
											type : 'int'
										}, 'isDefaultIn','orgId','org_type']
							}),
					remoteSort : true
				});// end of store
		this.store.setDefaultSort('roleId', 'desc');
		this.store.load({
					params : {
						start : 0,
						limit : 25
					}
				});
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
						header : 'roleId',
						dataIndex : 'roleId',
						hidden : true
					}, {
						header : "状态",
						dataIndex : 'status',
						width : 30,
						renderer : function(value) {
							var str = '';
							if (value == '1') {// 激活用户
								str += '<img title="激活" src="'
										+ __ctxPath
										+ '/images/flag/customer/effective.png"/>'
							} else {// 禁用用户
								str += '<img title="禁用" src="'
										+ __ctxPath
										+ '/images/flag/customer/invalid.png"/>'
							}
							return str;
						}
					}, {
						header : "角色名称",
						dataIndex : 'roleName',
						width : 200
					}, {
						header : "角色描述",
						dataIndex : 'roleDesc',
						width : 400,
						renderer : function(value, metadata, record, rowIndex,
								colIndex) {
							if(record.data.isDefaultIn == '1') {
								return "【系统内置】" + value;
							}else {
								return value;
							
							}
						}
					}, {
						header : '管理',
						dataIndex : 'roleId',
						width : 80,
						renderer : function(value, metadata, record, rowIndex,
								colIndex) {
							var editId = record.data.roleId;
							var roleName = record.data.roleName;
							var orgId=record.data.orgId;
							this.oId=record.data.orgId;
							var isDefaultIn = record.data.isDefaultIn;
							var org_type=record.data.org_type;
							var str = '';
							if (editId != -1) {
								if (isDefaultIn == '0') {

									if (isGranted('_AppRoleDel'))
										str = '<button title="删除" value=" " class="btn-del" onclick="AppRoleView.remove('
												+ editId + ')"></button>';
									if (isGranted('_AppRoleEdit'))
										str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="AppRoleView.edit('
												+ editId + ','+orgId+','+org_type+')"></button>';
									if (isGranted('_AppRoleGrant'))
										str += '&nbsp;<button title="授权" value=" " class="btn-grantrights" onclick="AppRoleView.grant('
												+ editId
												+ ',\''
												+ roleName
												+ '\',\''
												+ org_type
												+ '\')">&nbsp;</button>';
									if (isGranted('_setDesk'))
										str += '&nbsp;<button title="配置桌面" value=" " class="btn-deskSets" onclick="AppRoleView.setDesk('
												+ editId+ ',\''
												+ roleName+ '\''
												+')">&nbsp;</button>';
								} else {
									str = '<button title="复制" value=" " class="btn-copyrole" onclick="AppRoleView.copy('
											+ editId + ','+orgId+','+org_type+')"></button>';
								}
							}
							return str;
						}
					}],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});// end of cm

		this.gridPanel = new Ext.grid.GridPanel({
					id : 'AppRoleGrid',
					region : 'center',
					tbar : this.topbar(),
					store : this.store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					// fbar : this.footbar(),
					cm : cm,
					sm : sm,
					// customize view config
					viewConfig : {
						forceFit : true,
						enableRowBody : false,
						showPreview : false
					},

					// paging bar on the bottom
					bbar : new HT.PagingBar({store : this.store})
				});

		this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
					grid.getSelectionModel().each(function(rec) {
						if (rec.data.isDefaultIn == '0'
								&& rec.data.roleId != -1) {
							AppRoleView.edit(rec.data.roleId,rec.data.orgId);
						}
					});
				});

	}// end of the initUIComponents

});

/**
 * 建立操作的Toolbar
 */
AppRoleView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'AppRoleFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_AppRoleAdd')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加角色',
					handler : function() {
						new AppRoleFormOnly({roleId:null,roleType:'business',orgId:null,org_type:null}).show();
					}
				}));
	}
	if (isGranted('_AppRoleDel')) {
		toolbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除角色',
					handler : function() {
						var grid = Ext.getCmp("AppRoleGrid");
						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						var idsN = '';
						for (var i = 0; i < selectRecords.length; i++) {
							if (selectRecords[i].data.isDefaultIn == '0'
									&& selectRecords[i].data.roleId != -1) {
								ids.push(selectRecords[i].data.roleId);
							} else {
								idsN += selectRecords[i].data.roleName + ',';
							}
						}
						if (idsN == '') {
							AppRoleView.remove(ids);
						} else {
							Ext.ux.Toast.msg("信息", idsN + "不能被删除！");
						}
					}
				}));
	}
	if (isGranted('_AppRoleExit')) {
		toolbar.add(new Ext.Button({
				iconCls : 'btn-collapsez',
				text : '导出授权',
				xtype : 'button',
				scope : this,
				//hidden : isGranted('_remove_business')?false:true,
				handler : function(){
						var grid = Ext.getCmp("AppRoleGrid");
						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要导出的记录！");
							return;
						}
						var ids = Array();
						var idsN = '';
						var names = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							if (selectRecords[i].data.isDefaultIn == '0'
									&& selectRecords[i].data.roleId != -1) {
								ids.push(selectRecords[i].data.roleId);
								names.push(selectRecords[i].data.roleName);
							} else {
								idsN += selectRecords[i].data.roleName + ',';
							}
						}
						if (idsN == '') {
							exportAccess(names,ids,selectRecords[0].data.orgId);
						} else {
						    Ext.ux.Toast.msg("信息", idsN + "不能被导出！");
						}
					}
			}));
	}
	
	if (isGranted('_AppRoleImport')) {
		toolbar.add(new Ext.Button({
			xtype : 'button',
			text : '导入授权',
			iconCls : 'btn-collapse',
			scope:this,
			handler : function() {
			
				new SlAccessView({
					roleGrantView:this.gridPanel,
					orgId:1,//应该为 this.oId 但是 this.oId 取不到值 所以暂时设置为 1 1为中宇汇通机构id
					flag :"accessupload"
				}).show();
				
			}
		}));
	}

	return toolbar;
};
	//导出授权
		exportAccess = function(roleNames,roleIds,orgId) {
			var grid=Ext.getCmp('BusinessRoleGrid')
			if(typeof(orgId)=="undefined"){
				orgId=0;
			}
			roleNames=encodeURI(roleNames);
			Ext.Msg.confirm('信息确认', '您确认要导出授权记录吗？', function(btn) {
				if (btn == 'yes') {
					
					window.open( __ctxPath + "/system/exitExcelAppRole.do?roleNames="+roleNames+"&roleIds="+roleIds+"&orgId="+orgId);
				}
			});
		};

/**
 * 删除单个记录
 */
AppRoleView.remove = function(id) {
	var grid = Ext.getCmp("AppRoleGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : __ctxPath + '/system/multiDelAppRole.do',
				params : {
					ids : id
				},
				method : 'post',
				success : function(response, request) {
					var obj = Ext.util.JSON.decode(response.responseText);
					if(obj.message==true){
						Ext.ux.Toast.msg("信息", "成功删除所选记录！");
					}else{
						Ext.ux.Toast.msg("信息", obj.message);
					}
					grid.getStore().reload({
						params : {
							start : 0,
							limit : 25
						}
					});
				}
			});
		}
	});
};

/**
 * 
 */
AppRoleView.edit = function(id,orgId,org_type) {
	new AppRoleFormOnly({roleId:id,roleType:'business',orgId:orgId,org_type:org_type}).show();
};

AppRoleView.grant = function(id, roleName,org_type) {
new RoleGrantRightView(id, roleName,org_type,'business','business');};

AppRoleView.copy = function(id,orgId,org_type) {
	new AppRoleFormOnly({roleId : id,roleType:'business',orgId:orgId,isCopy : 1,org_type:org_type}).show();// 1代表是复制
}

/**
 * 配置角色下的桌面
 * @param {} id   角色id
 * @param {} name 角色名称
 */
AppRoleView.setDesk = function(id,name){
	new DeskRoleForm({
		roleId:id,
		roleName:name
	}).show();
};