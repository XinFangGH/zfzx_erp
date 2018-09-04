/**
 * @author:
 * @class ArchFondView
 * @extends Ext.Panel
 * @description 全宗管理
 * @company 北京互融时代软件有限公司
 * @createTime:
 */
FlowFormQueryEntity = Ext.extend(Ext.Panel, {
	javaType : {
		'java.lang.String' : 'S',
		'java.lang.Long' : 'L',
		'java.lang.Integer' : 'N',
		'java.lang.Short' : 'SN',
		'java.lang.Float' : 'FT',
		'java.lang.Double' : 'BD',
		'java.util.Date' : 'D',

		'String' : 'S',
		'Long' : 'L',
		'Integer' : 'N',
		'Short' : 'SN',
		'Float' : 'FT',
		'Double' : 'BD',
		'Date' : 'D',

		'String' : 'S',
		'long' : 'L',
		'int' : 'N',
		'short' : 'SN',
		'float' : 'FT',
		'double' : 'BD',

		'tinyint' : 'N',
		'smallint' : 'N',
		'mediumint' : 'N',
		'int' : 'N',
		'bigint' : 'L',

		'float' : 'FT',
		'double' : 'BD',

		'char' : 'S',
		'varchar' : 'S',
		'tinytext' : 'S',
		'text' : 'S',
		'mediumtext' : 'S',
		'longtext' : 'S',

		'blob' : 'S',
		'longblob' : 'S',

		'date' : 'D',
		'time' : 'D',
		'datetime' : 'D',
		'timestamp' : 'D',

		'TINYINT' : 'N',
		'SMALLINT' : 'N',
		'MEDIUMINT' : 'N',
		'INT' : 'N',
		'BIGINT' : 'N',

		'FLOAT' : 'FT',
		'DOUBLE' : 'BD',

		'CHAR' : 'S',
		'VARCHAR' : 'S',
		'TINYTEXT' : 'S',
		'TEXT' : 'S',
		'MEDIUMTEXT' : 'S',
		'LONGTEXT' : 'S',

		'BLOB' : 'S',
		'LONGBLOB' : 'S',

		'DATE' : 'D',
		'TIME' : 'D',
		'DATETIME' : 'D',
		'TIMESTAMP' : 'D',
		'decimal' : 'BD',
		'DECIMAL' : 'BD'
	},

	extType : {

		'java.lang.String' : 'textfield',
		'java.lang.Long' : 'numberfield',
		'java.lang.Integer' : 'numberfield',
		'java.lang.Short' : 'numberfield',
		'java.lang.Float' : 'numberfield',
		'java.lang.Double' : 'numberfield',
		'java.util.Date' : 'datefield',

		'String': 'textfield',
		'Long' : 'numberfield',
		'Integer' : 'numberfield',
		'Short' : 'numberfield',
		'Float' : 'numberfield',
		'Double' : 'numberfield',
		'Date' : 'datefield',

		'String' : 'textfield',
		'long' : 'numberfield',
		'int' : 'numberfield',
		'short' : 'numberfield',
		'float' : 'numberfield',
		'double' : 'numberfield',

		'tinyint' : 'numberfield',
		'smallint' : 'numberfield',
		'mediumint' : 'numberfield',
		'int' : 'numberfield',
		'bigint' : 'numberfield',

		'float' : 'numberfield',
		'double' : 'numberfield',

		'char' : 'textfield',
		'varchar' : 'textfield',
		'tinytext' : 'textfield',
		'text' : 'textfield',
		'mediumtext' : 'textfield',
		'longtext' : 'textfield',

		'blob' : 'textfield',
		'longblob' : 'textfield',

		'date' : 'datefield',
		'time' : 'datefield',
		'datetime' : 'datefield',
		'timestamp' : 'datefield',

		'TINYINT' : 'numberfield',
		'SMALLINT' : 'numberfield',
		'MEDIUMINT' : 'numberfield',
		'INT' : 'numberfield',
		'BIGINT' : 'numberfield',

		'FLOAT' : 'numberfield',
		'DOUBLE' : 'numberfield',

		'CHAR' : 'textfield',
		'VARCHAR' : 'textfield',
		'TINYTEXT' : 'textfield',
		'TEXT' : 'textfield',
		'MEDIUMTEXT' : 'textfield',
		'LONGTEXT' : 'textfield',

		'BLOB' : 'textfield',
		'LONGBLOB' : 'textfield',

		'DATE' : 'datefield',
		'TIME' : 'datefield',
		'DATETIME' : 'datefield',
		'TIMESTAMP' : 'datefield',
		'decimal' : 'numberfield',
		'DECIMAL' : 'numberfield'
	},
	
	
	searchType : {

		'java.lang.String' : 'LK',
		'java.lang.Long' : 'EQ',
		'java.lang.Integer' : 'EQ',
		'java.lang.Short' : 'EQ',
		'java.lang.Float' : 'EQ',
		'java.lang.Double' : 'EQ',
		

		'String' : 'LK',
		'Long' : 'EQ',
		'Integer' : 'EQ',
		'Short' : 'EQ',
		'Float' : 'EQ',
		'Double' : 'EQ',
		'Date' : 'datefield',

		'String' : 'LK',
		'long' : 'EQ',
		'int' : 'EQ',
		'short' : 'EQ',
		'float' : 'EQ',
		'double' : 'EQ',

		'tinyint' : 'EQ',
		'smallint' : 'EQ',
		'mediumint' : 'EQ',
		'int' : 'EQ',
		'bigint' : 'EQ',

		'float' : 'EQ',
		'double' : 'EQ',

		'char' : 'LK',
		'varchar' : 'LK',
		'tinytext' : 'LK',
		'text' : 'LK',
		'mediumtext' : 'LK',
		'longtext' : 'LK',

		'blob' : 'LK',
		'longblob' : 'LK',

		

		'TINYINT' : 'EQ',
		'SMALLINT' : 'EQ',
		'MEDIUMINT' : 'EQ',
		'INT' : 'EQ',
		'BIGINT' : 'EQ',

		'FLOAT' : 'EQ',
		'DOUBLE' : 'EQ',

		'CHAR' : 'LK',
		'VARCHAR' : 'LK',
		'TINYTEXT' : 'LK',
		'TEXT' : 'LK',
		'MEDIUMTEXT' : 'LK',
		'LONGTEXT' : 'LK',

		'BLOB' : 'LK',
		'LONGBLOB' : 'LK',

	
		'decimal' : 'EQ',
		'DECIMAL' : 'EQ'
	},
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents(this.tableKey, this.tableId);
		// 调用父类构造
		FlowFormQueryEntity.superclass.constructor.call(this, {
					id : 'FlowFormQueryEntity',
					region : 'center',
					border : false,
					items : [this.searchPanel, this.gridPanel],
					layout : {
						type : 'border',
						align : 'stretch'
					}
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function(tableKey, tableId) {

		var items = [];
		var fields = [];
		var columns = [];

		if (tableId) {
			fields.push({
				name : 'showCheck',
				type : 'boolean'
			});
			this.rowAtction = new Ext.ux.grid.RowActions({
						header : '管理',
						width : 55,
						actions : [{
									iconCls : 'btn-showDetail',
									qtip : '审批信息',
									hideIndex:'showCheck',
									style : 'margin:0 3px 0 3px'
								}

						],
						listeners : {
							scope : this,
							'action' : this.onRowAction
						}
					});

			Ext.Ajax.request({
				url : __ctxPath + '/flow/fieldListFlowFormQuery.do',
				method : 'POST',
				async : false,
				scope : this,
				success : function(response, opts) {

					var obj = Ext.decode(response.responseText);

					for (i = 0; i < obj.result.length; i++) {
						// 查询条件
						// 如果查询
						var fieldLabel="无列名";
						if(obj.result[i].fieldDscp)
							fieldLabel=obj.result[i].fieldDscp;
						if (obj.result[i].isQuery == 1) {
							// 如果外键
							if (obj.result[i].foreignEntity
									&& obj.result[i].foreignKey) {

//								items.push({
//											flex : 1,
//											fieldLabel : fieldLabel,
//											name : 'Q_'
//													+ obj.result[i].foreignEntity
//													+ '.'
//													+ obj.result[i].foreignKey
//													+ '_'
//													+ eval('this.javaType.'
//															+ obj.result[i].fieldType)
//													+ '_LK',
//											xtype : eval('this.extType.'
//													+ obj.result[i].fieldType)
//
//										});
							} else {

								if (eval('this.extType.'
										+ obj.result[i].fieldType) == "datefield") {
									items.push({
										flex : 1,
										fieldLabel : fieldLabel +'从',
										format:'Y-m-d',
										name : 'Q_'
												+ obj.result[i].property
												+ '_'
												+ eval('this.javaType.'
														+ obj.result[i].fieldType)
												+ '_GE',
										xtype : eval('this.extType.'
												+ obj.result[i].fieldType)

									});

									items.push({
										flex : 1,
										fieldLabel : '到',
										format:'Y-m-d',
										name : 'Q_'
												+ obj.result[i].property
												+ '_'
												+ eval('this.javaType.'
														+ obj.result[i].fieldType)
												+ '_LE',
										xtype : eval('this.extType.'
												+ obj.result[i].fieldType)

									});

								} else {
									items.push({
										flex : 1,
										fieldLabel : fieldLabel,
										name : 'Q_'
												+ obj.result[i].property
												+ '_'
												+ eval('this.javaType.'
														+ obj.result[i].fieldType)
												+'_'
												+ eval('this.searchType.'
														+ obj.result[i].fieldType),
										xtype : eval('this.extType.'
												+ obj.result[i].fieldType)

									});
								}

							}

						}

						// 数据读取

						if (obj.result[i].foreignEntity
								&& obj.result[i].foreignKey) {
							fields.push( obj.result[i].foreignEntity);
						} else {
							fields.push(
										obj.result[i].property
									);
						}

						// 数据显示
						if (obj.result[i].isList == 1) {
							// 如果外键
							if (obj.result[i].foreignEntity
									&& obj.result[i].foreignKey) {

								var foreignKey=	obj.result[i].foreignKey;	
								columns.push({
											header : fieldLabel,
											dataIndex : obj.result[i].foreignEntity,
											renderer : function(foreignEntity) {
												if (foreignEntity){
													
													return eval('foreignEntity.'+ foreignKey);
												}
											}
										});
							} else {
								columns.push({
											header : fieldLabel,
											dataIndex : obj.result[i].property
										});
							}

						}

					}
					
					//alert(Ext.encode(columns));alert(Ext.encode(fields));
					// 查看按钮
					columns.push(this.rowAtction);

				},

				failure : function(response, opts) {

				},
				params : {
					'Q_formTable.tableId_L_EQ' : tableId
				}
			});

		}

		

		this.searchPanel = new HT.SearchPanel({
					layout : 'form',
					region : 'north',
					colNums : 4,
					defaults : {
						anchor : '96%,96%',
						xtype:'textfield'
					},
					items : items,
					buttons : [{
								xtype : 'button',
								text : '查询',
								iconCls : 'search',
								scope : this,
								handler : this.search
							}, {
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
				});
	

		this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/flow/queryEntityFlowFormQuery.do",
					root : 'result',
					totalProperty : 'totalCounts',
					remoteSort : true,
					fields : fields
				});
		this.store.on('load', function(s, records) {
					s.each(function(r) {
								if(r.get('runId')){
									r.set('showCheck', false);
									
								}else {
									r.set('showCheck', true);
								}
								r.commit();

							})
				});
		this.store.on('beforeload', function(store, options) {
					if (tableKey) {
						var baseParams = {};
						if (this.searchPanel.getForm().getEl()) {
							var formParam = Ext.Ajax
									.serializeForm(this.searchPanel.getForm()
											.getEl());
							baseParams = Ext.urlDecode(formParam);

						}
						baseParams.tableKey = tableKey;
						baseParams.start = 0;
						if (store.baseParams.limit)
							baseParams.limit = store.baseParams.limit;
						else
							baseParams.limit = 25;

						store.baseParams = baseParams;
					} else {
						return false;
					}

				}, this);

		this.rowActions = new Ext.ux.grid.RowActions({
					header : '管理',
					width : 100,
					actions : [{
								iconCls : 'btn-showDetail',
								qtip : '详细',
								style : 'margin:0 5px 0 5px'
							}]
				});

		var cm = new Ext.grid.ColumnModel({
					columns : columns,
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					}
				});
		this.gridPanel = new Ext.grid.GridPanel({
					region : 'center',
					store : this.store,
					cm : cm,
					plugins : this.rowAtction,
					viewConfig : {
						forceFit : true,
						autoFill : true
					},
					bbar : new HT.PagingBar({
								store : this.store
							})

				});
		this.store.load();

	},
	// 重置搜索条件
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		this.gridPanel.getBottomToolbar().moveFirst();
	},
	showDetail : function(record) {
		new FlowFormEntityView({
					runId : record.data.runId,
					title : this.title
					
				}).show();
	},
	onRowAction : function(grid, record, action, row, col) {

		switch (action) {
			case 'btn-showDetail' :
				this.showDetail.call(this, record);
				break;
			default :
				break;
		}
	}
});
