
CompensatoryRecords = Ext.extend(Ext.Panel, {
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
		CompensatoryRecords.superclass.constructor.call(this, {
					items : [this.grid_Compensatory]
					
					
				})
	},
	initUIComponents : function() {
        var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计:'+Ext.util.Format.number(v,'0,000,000,000.00')+"元";
		}
		this.grid_Compensatory = new Ext.grid.GridPanel({
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
							+ '/creditFlow/guarantee/EnterpriseBusiness/listGlCompensatory.do?projectId='
							+ this.projectId+"&businessType="+this.businessType,
					method : "POST"
				}),
				reader : new Ext.data.JsonReader({
							fields : Ext.data.Record.create([{
										name : 'compensatoryId'
									}, {
										name : 'loanMoney'
									}, {
										name : 'bondMoney'
									}, {
										name : 'compensatoryAmount'
									}, {
										name : 'compensatoryDate'
									}, {
										name : 'compensatoryRemarks'
									}

							]),
							root : 'result'
						})
			}),
			columns : [ new Ext.grid.RowNumberer(), {
						header : '贷款余额',
						dataIndex : 'loanMoney',
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
						header : '保证金余额',
						dataIndex : 'bondMoney',
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
						header : '代偿余额',
						dataIndex : 'compensatoryAmount',
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
						header : '代偿时间',
						format : 'Y-m-d',
						dataIndex : 'compensatoryDate',
						width:72,
						fixed : true,
						sortable : true
						
					}, {
						header : '代偿说明',
						align : 'center',
						dataIndex : 'compensatoryRemarks',
						sortable : true,
						width:380,
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					}]

		});

		this.grid_Compensatory.getStore().load();

	}
});
