/**
 * @author:
 * @class SelectFondWindow
 * @extends Ext.Panel
 * @description 全宗管理
 * @company 北京互融时代软件有限公司
 * @createTime:
 */
SelectFondWindow = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SelectFondWindow.superclass.constructor.call(this, {
					id : 'SelectFondWindow',
					title : '全宗',
					region : 'center',
					layout : 'border',
					modal : true,
					width : 800,
					height : 600,
					buttonAlign : 'center',
					buttons : [{
								text : '选择',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}, {
								text : '关闭',
								iconCls : 'close',
								scope : this,
								handler : this.cancel
							}],
					items : [this.searchPanel, this.leftPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					id : 'SelectFondWindowSearchPanel',
					layout : 'form',
					region : 'north',
					colNums : 4,
					items : [{
								fieldLabel : '全宗号',
								name : 'Q_afNo_S_LK',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : '全宗名',
								name : 'Q_afName_S_LK',
								flex : 1,
								xtype : 'textfield'
							},

							// {
							// fieldLabel : '全宗概述',
							// name : 'Q_shortDesc_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }, {
							// fieldLabel : '全宗描述',
							// name : 'Q_descp_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }, {
							// fieldLabel : '全宗整理描述',
							// name : 'Q_clearupDesc_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// },

							// {
							// fieldLabel : '创建人',
							// name : 'Q_creator_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }, {
							// fieldLabel : '创建人ID',
							// name : 'Q_creatorId_L_EQ',
							// flex : 1,
							// xtype : 'numberfield'
							// }, {
							// fieldLabel : '案件数',
							// name : 'Q_caseNums_N_EQ',
							// flex : 1,
							// xtype : 'numberfield'
							// },

							{
								fieldLabel : '状态',
								hiddenName : 'Q_status_SN_EQ',
								flex : 1,
								xtype : 'combo',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : [['', '全部'], ['0', '草稿'], ['1', '启用'],
										['-1', '禁用']]
							},

							{
								fieldLabel : '开放形式',
								name : 'Q_openStyle_S_EQ',
								flex : 1,
								editable : true,
								lazyInit : false,
								forceSelection : false,
								xtype : 'diccombo',
								itemName : '开放形式'
							}, {
								fieldLabel : '创建时间	从',
								name : 'Q_createtime_D_GE',
								flex : 1,
								xtype : 'datefield',
								format : 'Y-m-d'
							}, {
								fieldLabel : '至	',
								name : 'Q_createtime_D_LE',
								flex : 1,
								xtype : 'datefield',
								format : 'Y-m-d'
							},

							{
								fieldLabel : '最后更新时间	从',
								name : 'Q_updatetime_D_EQ',
								flex : 1,
								xtype : 'datefield',
								format : 'Y-m-d'
							}, {
								fieldLabel : '至',
								name : 'Q_updatetime_D_LE',
								flex : 1,
								xtype : 'datefield',
								format : 'Y-m-d'
							},

							{
								fieldLabel : '',
								id : 'SelectFondWindow.proTypeId',
								name : 'Q_globalType.proTypeId_L_EQ',
								xtype : 'hidden',
								hideLabel : true,
								flex : 1
							},

							{
								fieldLabel : '',
								id : 'SelectFondWindow.typeName',
								name : 'Q_typeName_S_LK',
								xtype : 'hidden',
								hideLabel : true,
								flex : 1

							}

					],
					buttons : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}, {
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
				});// end of searchPanel
		this.leftPanel = new Ext.Panel({
					region : 'west',
					layout : 'anchor',
					collapsible : true,
					frame : false,
					border : true,
					split : true,
					width : 200,
					items : [

					{
						xtype : 'treePanelEditor',
						id : 'SelectFondWindowGlobalTypeTree',
						split : true,
						rootVisible : false,
						border : false,
						height : 380,
						autoScroll : true,
						scope : this,
						url : __ctxPath
								+ '/system/treeGlobalType.do?catKey=AR_FD',
						onclick : function(node) {

							var proTypeId = node.id;
							var typeName = node.text;

							if (proTypeId == '0') {
								Ext
										.getCmp('SelectFondWindowSearchPanel')
										.getForm()
										.findField('SelectFondWindow.proTypeId')
										.setValue('');
								Ext.getCmp('SelectFondWindowSearchPanel')
										.getForm()
										.findField('SelectFondWindow.typeName')
										.setValue('');

							} else {
								Ext
										.getCmp('SelectFondWindowSearchPanel')
										.getForm()
										.findField('SelectFondWindow.proTypeId')
										.setValue(proTypeId);
								Ext.getCmp('SelectFondWindowSearchPanel')
										.getForm()
										.findField('SelectFondWindow.typeName')
										.setValue(typeName);

							}

							Ext.getCmp('SelectFondWindow').search();

						}
					}]
				}

		);

		this.gridPanel = new HT.GridPanel({
			region : 'center',

			// 使用RowActions
			rowActions : false,
			id : 'SelectFondWindowFondGrid',
			url : __ctxPath + "/arch/listArchFond.do",
			fields : [{
						name : 'archFondId',
						type : 'int'
					}, 'afNo', 'afName', 'shortDesc', 'descp', 'clearupDesc',
					'createTime', 'updateTime', 'creatorName', 'creatorId',
					'caseNums', 'status', 'globalType', 'typeName', 'openStyle'],
			columns : [{
						header : 'archFondId',
						dataIndex : 'archFondId',
						hidden : true
					}, {
						header : '全宗号',
						dataIndex : 'afNo'
					}, {
						header : '全宗名',
						dataIndex : 'afName'
					},
					// {
					// header : '全宗概述',
					// dataIndex : 'shortDesc'
					// }, {
					// header : '全宗描述',
					// dataIndex : 'descp'
					// }, {
					// header : '全宗整理描述',
					// dataIndex : 'clearupDesc'
					// },

					// {
					// header : '创建人ID',
					// dataIndex : 'creatorId'
					// },

					{
						header : '案卷数',
						dataIndex : 'caseNums'
					}

			// {
			// header : '状态',
			// dataIndex : 'status',
			// render : function(v) {
			// switch (v) {
			// case 0 :
			// return '草稿';
			// break;
			// case 1 :
			// return '启用';
			// break;
			// case -1 :
			// return '禁用';
			// break;
			//
			// }
			// }
			// },

			// {
			// header : '全宗分类ID',
			// dataIndex : 'globalType.proTypeId'
			// },

			// {
			// header : '全宗分类',
			// dataIndex : 'typeName'
			// }, {
			// header : '开放形式',
			// dataIndex : 'openStyle'
			// }

			// , {
			// header : '创建人',
			// dataIndex : 'creatorName'
			// }, {
			// header : '创建时间',
			// dataIndex : 'createTime'
			// }
			// {
			// header : '最后更新时间',
			// dataIndex : 'updateTime'
			// },
			]

				// end of columns
		});

	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	cancel : function() {
		this.close();
	},
	save : function() {

	
		var selectRecords = this.gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择记录！");
			return;
		}
		
		if(this.callBack!=null){
			this.close();
			this.callBack.call(this,selectRecords);
		}
		
		
	},
	// 按条件搜索
	search : function() {

		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	}
});
