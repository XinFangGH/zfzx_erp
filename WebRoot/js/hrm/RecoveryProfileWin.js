/**
 * @author:
 * @class RecoveryProfileWin
 * @extends Ext.Panel
 * @description 档案管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
RecoveryProfileWin = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		// 初始化组件
		this.initComponents();
		// 调用父类构造
		RecoveryProfileWin.superclass.constructor.call(this, {
					id : 'RecoveryProfileWin',
					iconCls : 'btn-empProfile-recovery',
					title : '档案管理',
					border : false,
					region : 'center',
					width : 720,
					modal : true,
					height : 450,
					layout : 'border',
					items : [this.searchPanel, this.gridPanel],
					bottons : this.buttons,
					buttonAlign : 'center'
				});
	},// end of constructor

	// 档案分类ID
	typeId : null,

	// 条件搜索Panel
	searchPanel : null,

	// 数据展示Panel
	gridPanel : null,

	// GridPanel的数据Store
	store : null,

	// 头部工具栏
	topbar : null,

	// 初始化组件
	initComponents : function() {
		this.buttons = [{
					text : '取消',
					iconCls : 'btn-cancel',
					handler : this.cancel.createCallback(this)
				}];
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
					columnWidth : 1,
					height : 35,
					region : 'north',
					frame : false,
					border : false,
					layout : 'hbox',
					layoutConfig : {
						padding : '5',
						align : 'middle'
					},
					defaults : {
						style : 'padding:0px 5px 0px 5px;',
						border : false,
						anchor : '98%,98%',
						labelWidth : 70,
						xtype : 'label'
					},
					items : [{
								text : '档案编号'
							}, {
								name : 'Q_profileNo_S_LK',
								width : 80,
								xtype : 'textfield'
							}, {
								text : '员工姓名'
							}, {
								name : 'Q_fullname_S_LK',
								width : 80,
								xtype : 'textfield'
							}, {
								text : '身份证号'
							}, {
								width : 80,
								name : 'Q_idCard_S_LK',
								xtype : 'textfield'
							}, {
								text : '审核状态'
							}, {
								hiddenName : 'Q_approvalStatus_SN_EQ',
								width : 80,
								xtype : 'combo',
								editable : false,
								mode : 'local',
								triggerAction : 'all',
								store : [['', '　'], ['0', '未审核'],
										['1', '审核通过'], ['2', '审核未通过']]
							}, {
								xtype : 'button',
								text : '查询',
								iconCls : 'search',
								handler : this.search.createCallback(this)
							}, {
								// 状态为删除的',// 0=未删除 1=删除',
								name : 'Q_delFlag_SN_EQ',
								width : 80,
								xtype : 'hidden',
								value : 1
							}]
				});// end of the searchPanel

		// 加载数据至store
		this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/hrm/listEmpProfile.do",
					baseParams : {
						"Q_delFlag_SN_EQ" : 1
					},// 只查询被删除的档案
					root : 'result',
					totalProperty : 'totalCounts',
					remoteSort : true,
					fields : [{
								name : 'profileId',
								type : 'int'
							}, 'profileNo', 'fullname', 'designation',
							'creator', 'createtime', 'approvalStatus', 'memo',
							'depName']
				});
		this.store.setDefaultSort('profileId', 'desc');
		// 加载数据
		this.store.load({
					params : {
						start : 0,
						limit : 25
					}
				});

		// 初始化ColumnModel
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
						header : 'profileId',
						dataIndex : 'profileId',
						hidden : true
					}, {
						header : '档案编号',
						dataIndex : 'profileNo'
					}, {
						header : '员工姓名',
						dataIndex : 'fullname'
					}, {
						header : '建档人',
						dataIndex : 'creator'
					}, {
						header : '建档时间',
						dataIndex : 'createtime'
					}, {
						header : '部门或公司',
						dataIndex : 'depName'
					}, {
						header : '职称',
						dataIndex : 'designation'
					}, {
						header : '审核状态',// 0=未删除 1=删除',
						dataIndex : 'approvalStatus',
						renderer : function(value) {
							if (value == '0') {
								return '未审核'
							} else if (value == '1') {
								return '<img title="通过审核" src="'
										+ __ctxPath
										+ '/images/flag/customer/effective.png"/>';
							} else {
								return '<img title="没通过审核" src="'
										+ __ctxPath
										+ '/images/flag/customer/invalid.png"/>';
							}
						}
					}],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});
		// 初始化工具栏
		this.topbar = new Ext.Toolbar({
					height : 30,
					bodyStyle : 'text-align:left',
					items : [{
								iconCls : 'btn-empProfile-recovery',
								text : '恢复档案',
								xtype : 'button',
								handler : this.recoveryRecord
							}]
				});

		this.gridPanel = new Ext.grid.GridPanel({
					id : 'RecoveryProfileGrid',
//					height : 300,
					region : 'center',
					columnWidth : 1,
					stripeRows : true,
					tbar : this.topbar,
					autoScroll : true,
					store : this.store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					cm : cm,
					sm : sm,
					plugins : this.rowActions,
					viewConfig : {
						forceFit : true,
						autoFill : true, // 自动填充
						forceFit : true
					},
					bbar : new HT.PagingBar({store : this.store})
				});
	},// end of the initComponents()

	/**
	 * 
	 * @param {}
	 *            self 当前窗体对象
	 */
	search : function(self) {
		if (self.searchPanel.getForm().isValid()) {// 如果合法
			$search({
				searchPanel :self.searchPanel,
				gridPanel : self.gridPanel
			});
		}
	},
	/**
	 * 恢复记录
	 * 
	 * @param {}
	 *            record
	 */
	recoveryRecord : function() {
		var grid = Ext.getCmp('RecoveryProfileGrid');

		var selectRecords = grid.getSelectionModel().getSelections();

		if (selectRecords.length == 0) {
			Ext.Msg.alert("信息", "请选择要恢复的档案记录！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.profileId);
		}
		Ext.Msg.confirm('信息确认', '您确认要恢复该档案记录吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : __ctxPath
											+ '/hrm/recoveryEmpProfile.do',
									params : {
										ids : ids
									},
									method : 'POST',
									success : function(response, options) {
										Ext.ux.Toast.msg('操作信息', '成功恢复档案！');
										Ext.getCmp('EmpProfileGrid').getStore()
												.reload();
										Ext.getCmp('RecoveryProfileGrid')
												.getStore().reload();
									},
									failure : function(response, options) {
										Ext.ux.Toast
												.msg('操作信息', '操作出错，请联系管理员！');
									}
								});
					}
				});// end of comfirm
	},
	/**
	 * 关闭窗口事件
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function(window) {
		window.close();
	}
});
