/**
 * 用户管理
 * @class AppUserView
 * @extends Ext.Panel
 */
AppUserView=Ext.extend(Ext.Panel,{
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		AppUserView.superclass.constructor.call(this,{
			id : 'AppUserView',
			title : '账号信息',
			iconCls:'menu-appuser',
			layout:'border',
			autoScroll : true
		});
	},
	initUIComponents:function(){
		this.initSearchPanel();
		this.initGridPanel();

		this.items=[this.searchPanel,this.gridPanel];
	},
	onSearch:function(obj){
							var searchPanel = Ext.getCmp('AppUserSearchForm');
		
							var gridPanel = Ext.getCmp('AppUserGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}
	}
});

/**
 * 初始化SearchPanel
 */
AppUserView.prototype.initSearchPanel=function(){
	this.searchPanel=new Ext.FormPanel({
			region:'north',
			height : 35,
			frame : false,
			border:false,
			id : 'AppUserSearchForm',
			layout : 'hbox',
			layoutConfig: {
                    padding:'5',
                    align:'middle'
            },
			defaults : {
				xtype : 'label',
				border:false,
				margins:{top:0, right:4, bottom:4, left:4}
			},
			
			items : [ {
						text : '用户账号'
					}, {
						xtype : 'textfield',
						name : 'Q_username_S_LK'
					}, {
						text : '用户姓名'
					}, {
						xtype : 'textfield',
						name : 'Q_fullname_S_LK'
					},
					{
						text : '入职时间:从'
					}, {
						xtype : 'datefield',
						format: 'Y-m-d',
						name : 'Q_accessionTime_D_GT'
					}, {
						text : '至'
					},{
						xtype : 'datefield',
						format: 'Y-m-d',
						name : 'Q_accessionTime_D_LT'
					},
					{
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						scope:this,
						handler : this.onSearch.createCallback(this)
					}
					]
		});//end of search panel
};

AppUserView.prototype.initGridPanel=function(){
		
	this.toolbar = new Ext.Toolbar({
			height : 30,
			items : []
		});
	if (isGranted('_AppUserAdd')) {
		this.toolbar.add(new Ext.Button({
					text : '添加员工',
					iconCls : 'add-user',
					handler : function() {
						App.clickTopTab('UserFormPanel');
					}
				}));
	}
	if (isGranted('_AppUserDel')) {
		if(isGranted('_AppUserAdd')){
			this.toolbar.add('-');
		}
		this.toolbar.add(new Ext.Button({
			iconCls : 'btn-del',
			text : '删除员工',
			handler : function() {
				var grid = Ext.getCmp("AppUserGrid");

				var selectRecords = grid.getSelectionModel().getSelections();

				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}
				var ids = Array();
				var idsN = '';
				for (var i = 0; i < selectRecords.length; i++) {
					if (selectRecords[i].data.userId != 1) {
						ids.push(selectRecords[i].data.userId);
					} else {
						idsN += selectRecords[i].data.fullname + ',';
					}
				}
				if (idsN == '') {
					AppUserView.remove(ids);
				} else {
					Ext.ux.Toast.msg("信息", idsN + "不能被删除！");
				}
			}
		}));
	}
		
		var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/system/listAppUser.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							fields : [{
										name : 'userId',
										type : 'int'
									}, 'username', 'password', 'fullname','address',
									'email', 'depNames', //'title',// 性别
									'posNames','roleNames', 'dynamicPwd','dyPwdStatus',{
										name : 'accessionTime'
									}, {
										name : 'status',
										type : 'int'
									}]
						}),
				remoteSort : true
			});
		store.setDefaultSort('userId', 'desc');
		
		store.load({
					params : {
						start : 0,
						limit : 25
					}
		});
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
						header : 'userId',
						dataIndex : 'userId',
						hidden : true
					},{
						header : '状态',
						dataIndex : 'status',
						width : 30,
						renderer : function(value) {
							var str = '';
							if(value == '1'){//激活用户
								str += '<img title="激活" src="'+ __ctxPath +'/images/flag/customer/effective.png"/>'
							}else{//禁用用户
								str += '<img title="禁用" src="'+ __ctxPath +'/images/flag/customer/invalid.png"/>'
							}
							return str;
						}
					}, {
						header : '账号',
						dataIndex : 'username',
						width : 60,
						isExp: true
					}, {
						header:'地址',
						dataIndex:'address',
						hidden:true,
						exprint:true
					},{
						header : '用户名',
						dataIndex : 'fullname',
						width : 60
					}, {
						header : '邮箱',
						dataIndex : 'email',
						width : 120
					}, {// 先不显示
						header : '所属部门',
						dataIndex : 'depNames',
						sortable:false,
						width : 60
					},
					{
						header : '所在职位',
						dataIndex : 'posNames',
						sortable:false,
						width : 60
					}, {
						header : '拥有角色',
						dataIndex : 'roleNames',
						sortable:false,
						width : 60
					}, {
						header : '入职时间',
						dataIndex : 'accessionTime',
						width : 100
					}, {
						header : '令牌序列',
						dataIndex : 'dynamicPwd',
						sortable:false,
						hidden : true,
						width : 100
					}, {
						header : '令牌绑定',
						dataIndex : 'dyPwdStatus',
						width : 60,
						sortable:false,
						hidden : true,
						renderer : function(value){
							if(value !=null && value == 0){
								return '<font color="red">未绑定</font>'
							}else if(value !=null && value == 1){
								return '<font color="green">已绑定</font>'
							}
						}
					}, {
						header : '管理',
						dataIndex : 'userId',
						sortable:false,
						width : 60,
						renderer : function(value, metadata, record, rowIndex,
								colIndex) {
							var editId = record.data.userId;
							var editName = record.data.username;
							var str='';
							//if(editId!=1){
								if (isGranted('_AppUserDel')) {
									str += '<button title="删除" value=" " class="btn-del" onclick="AppUserView.remove('
											+ editId + ')"></button>';
								}
								if(isGranted('_AppUserEdit')){
									str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="AppUserView.edit('
											+ editId + ',\'' + editName + '\')"></button>';
								}
								if(isGranted('_AppUserReset')){
									str += '&nbsp;<button title="重置" value=" " class="btn-password" onclick="AppUserView.reset('
											+ editId + ')"></button>';
								}
							//}
							return str;
						}
					}],
			defaults : {
				sortable : true,
				menuDisabled : true,
				width : 100
			}
		});
	
		this.gridPanel = new Ext.grid.GridPanel({
					id : 'AppUserGrid',
					// title:'员工基本信息',
					tbar : this.toolbar,
					store : store,
					region:'center',
					//autoHeight:true,
					shim : true,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					cm : cm,
					sm : sm,
					viewConfig : {
						forceFit : true,
						enableRowBody : false,
						showPreview : false
					},
					bbar : new HT.PagingBar({store : store,exportable: true})
				});
				
		
		// 为Grid增加双击事件,双击行可编辑
		this.gridPanel.addListener('rowdblclick', rowdblclickFn);
		var gridPanel=this.gridPanel;
		function rowdblclickFn(gridPanel, rowindex, e) {
			gridPanel.getSelectionModel().each(function(rec) {
			   var userId=rec.data.userId;
			        if(isGranted('_AppUserEdit')&&userId!=1){
					AppUserView.edit(userId, rec.data.username);
			        }
				});
		}
		
};//end of the init GridPanel


/**
 * 用户删除
 * 
 * @param {}
 *            userId
 */
AppUserView.remove = function(_ids) {
	Ext.Msg.confirm('删除操作', '你确定要删除该用户吗?', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/system/multiDelAppUser.do',
								method : 'post',
								params : {
									ids : _ids
								},
								success : function(response) {
									var result = Ext.util.JSON.decode(response.responseText);
									if(result.msg == ''){
										Ext.ux.Toast.msg("操作信息", "用户删除成功");
									}else{
										Ext.ux.Toast.msg("操作信息", result.msg);
									}
									Ext.getCmp('AppUserGrid').getStore().reload();
								},
								failure : function() {
									Ext.ux.Toast.msg("操作信息", "用户删除失败");
								}
							});
				}
			});

};

/**
 * 重置密码
 * */
AppUserView.reset = function(userId){
	new setPasswordForm(userId);
};

/**
 * 用户编辑
 * 
 * @param {}
 *            userId
 */
AppUserView.edit = function(userId, username) {
	App.clickTopTab('UserFormPanel_'+userId,{userId:userId,username:username});
};
