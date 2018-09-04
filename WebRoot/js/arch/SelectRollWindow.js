/**
 * @author:
 * @class SelectRollWindow
 * @extends Ext.Panel
 * @description 案卷管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
SelectRollWindow = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SelectRollWindow.superclass.constructor.call(this, {
					id : 'SelectRollWindow',
					title : '案卷',
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
					layout : 'form',
					region : 'north',
					id : 'SelectRollWindowlSearchPanel',
					colNums : 4,
					items : [

					{
								fieldLabel : '全宗号',
								name : 'Q_archFond.afNo_S_LK',
								flex : 1,
								xtype : 'combo',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : new Ext.data.JsonStore({
											url : __ctxPath
													+ "/arch/listArchFond.do",
											autoLoad : true,
											autoShow : true,
											// reader configs
											root : 'result',

											fields : ['afNo', 'afNo']
										}),
								valueField : 'afNo',
								displayField : 'afNo'

							}

							, {
								fieldLabel : '案卷号',
								name : 'Q_rollNo_S_LK',
								flex : 1,
								xtype : 'textfield'
							}

							, {
								fieldLabel : '案卷名称',
								name : 'Q_rolllName_S_LK',
								flex : 1,
								xtype : 'textfield'
							}

							, {
								fieldLabel : '目录号',
								name : 'Q_catNo_S_LK',
								flex : 1,
								xtype : 'textfield'
							}, {
								fieldLabel : '保管期限',
								name : 'Q_timeLimit_S_LK',
								editable : true,
								lazyInit : false,
								forceSelection : false,
								xtype : 'diccombo',
								itemName : '保管期限'
							},

							// {
							// fieldLabel : '起始日期',
							// name : 'Q_startTime_D_EQ',
							// flex : 1,
							// xtype : 'datefield',
							// format : 'Y-m-d'
							// }, {
							// fieldLabel : '结束日期',
							// name : 'Q_endTime_D_EQ',
							// flex : 1,
							// xtype : 'datefield',
							// format : 'Y-m-d'
							// },

							{
								fieldLabel : '开放形式',
								name : 'Q_openStyle_S_LK',
								flex : 1,
								editable : true,
								lazyInit : false,
								forceSelection : false,
								xtype : 'diccombo',
								itemName : '开放形式'
							}

							// ,{
							// fieldLabel : '立卷人',
							// name : 'Q_author_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }, {
							// fieldLabel : '立卷时间',
							// name : 'Q_setupTime_D_EQ',
							// flex : 1,
							// xtype : 'datefield',
							// format : 'Y-m-d'
							// }, {
							// fieldLabel : '检查人',
							// name : 'Q_checker_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }, {
							// fieldLabel : '录入人',
							// name : 'Q_creator_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }, {
							// fieldLabel : '录入时间',
							// name : 'Q_createtime_D_EQ',
							// flex : 1,
							// xtype : 'datefield',
							// format : 'Y-m-d'
							// }, {
							// fieldLabel : '主题词',
							// name : 'Q_keywords_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }, {
							// fieldLabel : '制作单位',
							// name : 'Q_editCompany_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }, {
							// fieldLabel : '制作部门',
							// name : 'Q_editDep_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }, {
							// fieldLabel : '备考说明',
							// name : 'Q_decp_S_EQ',
							// flex : 1,
							// xtype : 'textfield'
							// }

							, {
								fieldLabel : '状态',
								hiddenName : 'Q_status_SN_EQ',
								flex : 1,
								xtype : 'combo',
								mode : 'local',
								editable : false,
								value : '1',
								triggerAction : 'all',
								store : [['', '全部'], ['1', '正常'], ['0', '销毁']]
							}

							, {
								fieldLabel : '案卷分类ID',
								id : 'SelectRollWindow.proTypeId',
								name : 'Q_globalType.proTypeId_L_EQ',
								flex : 1,
								xtype : 'hidden'
							}, {
								fieldLabel : '案卷分类名称',
								id : 'SelectRollWindow.typeName',
								name : 'Q_typeName_S_LK',
								flex : 1,
								xtype : 'hidden'
							}],
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
					split : true,
					width : 200,
					items : [

					{
						xtype : 'treePanelEditor',
						id : 'SelectRollWindowGlobalTypeTree',
						split : true,
						rootVisible : false,
						border : false,
						height : 380,
						autoScroll : true,
						scope : this,
						url : __ctxPath
								+ '/system/treeGlobalType.do?catKey=AR_RL',
						onclick : function(node) {

							var proTypeId = node.id;
							var typeName = node.text;

							if (proTypeId == '0') {
								Ext
										.getCmp('SelectRollWindowlSearchPanel')
										.getForm()
										.findField('SelectRollWindow.proTypeId')
										.setValue('');
								Ext.getCmp('SelectRollWindowlSearchPanel')
										.getForm()
										.findField('SelectRollWindow.typeName')
										.setValue('');

							} else {
								Ext
										.getCmp('SelectRollWindowlSearchPanel')
										.getForm()
										.findField('SelectRollWindow.proTypeId')
										.setValue(proTypeId);
								Ext.getCmp('SelectRollWindowlSearchPanel')
										.getForm()
										.findField('SelectRollWindow.typeName')
										.setValue(typeName);

							}

							Ext.getCmp('SelectRollWindow').search();

						}
					}]
				}

		);

		this.gridPanel = new HT.GridPanel({
			region : 'center',

			id : 'SelectRollWindowRollGrid',
			url : __ctxPath + "/arch/listArchRoll.do",
			fields : [{
						name : 'rollId',
						type : 'int'
					}, 'createTime', 'updateTime', 'creatorName', 'creatorId',
					'status', 'proTypeId', 'typeName', 'openStyle', 'archFond',
					'rolllName', 'rollNo', 'catNo', 'timeLimit', 'startTime',
					'endTime', 'author', 'setupTime', 'checker', 'keyWords',
					'editCompany', 'editDep', 'decp'],
			columns : [{
						header : 'rollId',
						dataIndex : 'rollId',
						hidden : true
					}

					// , {
					// header : '案卷分类ID',
					// dataIndex : 'proTypeId'
					// }
					// , {
					// header : '全宗ID',
					// dataIndex : 'archFond',
					// renderer:function(v){
					//							
					// return v.archFondId;
					// }
					// }

					, {
						header : '全宗号',
						dataIndex : 'archFond',
						renderer : function(v) {
							if (v)
								return v.afNo;
						}
					},

					{
						header : '案卷号',
						dataIndex : 'rollNo'
					}, {
						header : '案卷名称',
						dataIndex : 'rolllName'
					}

			// , {
			// header : '目录号',
			// dataIndex : 'catNo'
			// }, {
			// header : '案卷分类',
			// dataIndex : 'typeName'
			// }, {
			// header : '保管期限',
			// dataIndex : 'timeLimit'
			// }

			//					
			//
			// , {
			// header : '起始日期',
			// dataIndex : 'startTime'
			// }, {
			// header : '结束日期',
			// dataIndex : 'endTime'
			// }, {
			// header : '开放形式',
			// dataIndex : 'openStyle'
			// }, {
			// header : '立卷人',
			// dataIndex : 'author'
			// }, {
			// header : '立卷时间',
			// dataIndex : 'setupTime'
			// }, {
			// header : '检查人',
			// dataIndex : 'checker'
			// }, {
			// header : '录入人',
			// dataIndex : 'creatorName'
			// }, {
			// header : '录入时间',
			// dataIndex : 'createTime'
			// }, {
			// header : '主题词',
			// dataIndex : 'keyWords'
			// }, {
			// header : '制作单位',
			// dataIndex : 'editCompany'
			// }, {
			// header : '制作部门',
			// dataIndex : 'editDep'
			// }, {
			// header : '备考表',
			// dataIndex : 'decp'
			// }

			// , {
			// header : '状态',
			// dataIndex : 'status',
			// renderer : function(v) {
			// switch (v) {
			// case 1 :
			// return '正常';
			// break;
			// case 0 :
			// return '销毁';
			// break;
			// }
			//
			// }
			// }
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
		
		if (this.callBack != null) {
			this.close();
			this.callBack.call(this, selectRecords);
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
