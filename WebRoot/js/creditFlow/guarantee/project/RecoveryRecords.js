
RecoveryRecords = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	projectId : null,
	isHidden : false,
	businessType:null,
	constructor : function(_cfg) {
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.isHidden) != "undefined") {
			this.isHidden = _cfg.isHidden;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		RecoveryRecords.superclass.constructor.call(this, {
					items : [this.grid_Recovery]
				})
	},
	initUIComponents : function() {

        var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计:'+Ext.util.Format.number(v,'0,000,000,000.00')+"元";
		}
		this.grid_Recovery = new Ext.grid.GridPanel({
			border : false,
			autoHeight : true,
			stripeRows : true,
			viewConfig : {
				forceFit : true
			},
			plugins: [summary],
			store : new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : __ctxPath
							+ '/creditFlow/guarantee/EnterpriseBusiness/listGlRecovery.do?projectId='
							+ this.projectId+"&businessType="+this.businessType,
					method : "POST"
				}),
				reader : new Ext.data.JsonReader({
							fields : Ext.data.Record.create([{
										name : 'recoveryId'
									}, {
										name : 'compensatoryAmount'
									}, {
										name : 'compensatoryBalance'
									}, {
										name : 'compensatoryDate'
									}, {
										name : 'recoveryMoney'
									}, {
										name : 'recoveryDate'
									}, {
										name : 'recoverySource'
									}, {
										name : 'recoveryRemarks'
									},{
										name : 'recoverySourceName'
									}

							]),
							root : 'result'
						})
			}),
			columns : [ new Ext.grid.RowNumberer(), {
						header : '代偿金额',
						dataIndex : 'compensatoryAmount',
						sortable : true,
						align : "right",
						width : 105,
						fixed : true,
						summaryType: 'sum',
						summaryRenderer: totalMoney,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.number(value,
									'0,000,000,000.00')
									+ "元"
						}
					}, {
						header : '代偿余额',
						dataIndex : 'compensatoryBalance',
						sortable : true,
						align : "right",
						width : 105,
						fixed : true,
						summaryType: 'sum',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.number(value,
									'0,000,000,000.00')
									+ "元"
						}
					}, {
						header : '代偿日期',
						format : 'Y-m-d',
						dataIndex : 'compensatoryDate',
						width:72,
						fixed : true,
						sortable : true
					}, {
						header : '追偿金额',
						dataIndex : 'recoveryMoney',
						sortable : true,
						align : "right",
						width : 105,
						fixed : true,
						summaryType: 'sum',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.number(value,
									'0,000,000,000.00')
									+ "元"
						}
					}, {
						header : '追偿资金来源',
						dataIndex : 'recoverySourceName',
						width:80,
						fixed : true,
						sortable : true					
					}, {
						header : '追偿日期',
						format : 'Y-m-d',
						dataIndex : 'recoveryDate',
						width:72,
						fixed : true,
						sortable : true
					}, {
						header : '追偿说明',
						align : 'center',
						dataIndex : 'recoveryRemarks',
						sortable : true,
						width:320,
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					}]

		});

		this.grid_Recovery.getStore().load();

	}
	
});
