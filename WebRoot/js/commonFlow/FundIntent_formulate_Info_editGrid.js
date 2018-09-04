FundIntent_formulate_editGrid = Ext.extend(Ext.Panel, {
			
			gridPanel : null,
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				FundIntent_formulate_editGrid.superclass.constructor.call(this,
						{
							items : this.gridPanel,
							modal : true,
							region : 'center',
							layout : 'fit',
							maximizable : true,
							autoScroll : true,
							title : '制定款项计划'
						});
			
			},// end of the constructor
			// 初始化组件

			initUIComponents : function() {
			createRs =function() {
				alert("ddd")
				alert( FundIntent_grid)
				var newRecord =FundIntent_grid.getStore().recordType;
				var newData = new newRecord({
							id : '',
							fundType : '',
							payMoney : 0,
							incomeMoney : 0,
							intentDate : '',
							remarks : ''
						});
				FundIntent_grid.stopEditing();
				FundIntent_grid.getStore().insert(0, newData);
				// this.gridPanel.getView().refresh();
				// this.gridPanel.getSelectionModel().selectRow(0);
				FundIntent_grid.startEditing(0, 0);

			}
				var FundIntent_grid = new HT.EditorGridPanel({
					clicksToEdit : 1,
					isShowTbar:false,
					showPaging:false,
					autoHeight:true,
					// 使用RowActions
					// rowActions : true,
					id : 'FundIntent_formulate_editGrid',
					url : __ctxPath + "/admin/listRegulation.do",
					fields : [{
								name : 'id'
							}, {
								name : 'fundType'
							}, {
								name : 'intentDate'
							}, {
								name : 'payMoney'
							}, {
								name : 'incomeMoney'
							}, {
								name : 'remark'
							}],
					tbar : [{
								text : '增加',
								iconCls : 'btn-add',
								handler : createRs
							}, '-', {
								iconCls : 'btn-del',
								text : '删除',
								handler : this.deleteRs
							}],
					columns : [{
								header : '资金类型',
								dataIndex : 'fundType',
								editor : new DicCombo({
											editable : true,
											lazyInit : false,
											forceSelection : false,
											xtype : 'diccombo',
											itemName : '贷款资金类型'
										})
							}, {
								header : '计划到账日',
								xtype : 'datecolumn',
								format : 'Y-m-d',
								dataIndex : 'intentDate',
								editor : new Ext.form.DateField({
											format : 'Y-m-d',
											allowBlank : false
										})

							}, {
								header : '支出金额(元)',
								xtype : 'numbercolumn',
								dataIndex : 'payMoney',
								xtype:"",
								editor : new Ext.form.NumberField({
											allowBlank : false
										})
							}, {
								header : '收入金额(元)',
								xtype : 'numbercolumn',
								dataIndex : 'incomeMoney',
								editor : new Ext.form.NumberField({
											allowBlank : false
										})
							}, {
								header : '备注',
								dataIndex : 'remark',
								editor : new Ext.form.TextField({
											allowBlank : false
										})
							},{
								header : '实际到账日',
								xtype : 'datecolumn',
								format : 'Y-m-d',
								dataIndex : 'intentDate',
								editor : new Ext.form.DateField({
											format : 'Y-m-d',
											allowBlank : false
										})

							}, {
								header : '已对账金额(元)',
								xtype : 'numbercolumn',
								dataIndex : 'payMoney',
								xtype:"",
								editor : new Ext.form.NumberField({
											allowBlank : false
										})
							}, {
								header : '未对账金额(元)',
								xtype : 'numbercolumn',
								dataIndex : 'incomeMoney',
								editor : new Ext.form.NumberField({
											allowBlank : false
										})
							}, {
								header : '逾期利息总额(元)',
								xtype : 'numbercolumn',
								dataIndex : 'incomeMoney',
								editor : new Ext.form.NumberField({
											allowBlank : false
										})
							}, {
								header : '对账查看',
								dataIndex : 'incomeMoney'
								
							}]
						// end of columns
					});
					this.gridPanel=FundIntent_grid;

			}
			
		

		});