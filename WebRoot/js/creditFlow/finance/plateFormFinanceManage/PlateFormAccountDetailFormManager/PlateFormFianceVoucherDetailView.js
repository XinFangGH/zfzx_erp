//PlateFormFianceVoucherDetailView
PlateFormFianceVoucherDetailView = Ext.extend(Ext.Panel, {
			title:"",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlateFormFianceVoucherDetailView.superclass.constructor.call(this, {
							id : 'PlateFormFianceVoucherDetailView',
							title : "平台代金券台账",
							region : 'center',
							layout : 'border',
							iconCls :'btn-team29',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new HT.SearchPanel({
							layout : 'column',
							style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
							region : 'north',
							height : 20,
							anchor : '96%',
							keys : [{
								key : Ext.EventObject.ENTER,
								fn : this.search,
								scope : this
							}, {
								key : Ext.EventObject.ESC,
								fn : this.reset,
								scope : this
							}],
							layoutConfig: {
				               align:'middle',
				               padding : '5px'
				               
				            },
							items : [{
										columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '起始日期',
											name : "startDate",
											xtype : 'datefield',
											labelSeparator : "",
											format : 'Y-m-d',
											value:new Date()
										}]
							         },{
							         	columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '截止日期',
											name : "endDate",
											xtype : 'datefield',
											labelSeparator : "",
											format : 'Y-m-d',
											value:new Date()
										}]
							         },{
										columnWidth : .1,
										xtype : 'container',
										layout : 'form',
										defaults : {
											xtype : 'button'
										},
										style : 'padding-left:10px;',
										items : [{
											text : '查询',
											scope : this,
											iconCls : 'btn-search',
											handler : this.search
										}]
									},{
										columnWidth : .1,
										xtype : 'container',
										layout : 'form',
										defaults : {
											xtype : 'button'
										},
										style : 'padding-left:10px;',
										items : [{
											text : '重置',
											scope : this,
											iconCls : 'reset',
											handler : this.reset
										}]
									}]
						});

				this.topbar = new Ext.Toolbar({
					items : [{
					 xtype:'label',html : '【<font style="line-height:20px">功能开发中，敬请期待</font>】'
					}]
				});

				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					sm : this.projectFundsm,
					plugins : [summary],
					viewConfig: {  
		            	forceFit:false  
		            },
		            forceFit: true,
					// 使用RowActions
					rowActions : false,
					id : 'PlateFormFianceVoucherDetailGrid',
					//url : __ctxPath + "",
					fields : [{
								name : 'fundId',
								type : 'int'
							}, 'fundType','fundTypeName','borrowerName', 'borrowerId','p2pborrowerId', 'p2pborrowerName','bidPlanId','bidPlanName',
							 'bidPlanNumber','planIncomeMoney', 'planReciveDate','factIncomeMoney', 'factReciveDate','notMoney'],
					columns : [/*{
								header : 'fundId',
								dataIndex : 'fundId',
								hidden : true
							}, {
								header : '借款人',
								dataIndex : 'borrowerName',
								width : '8%',
								summaryType : 'sum',
				                summaryRenderer : totalMoney
							}, {
								header : '招标项目名称',
								dataIndex : 'bidPlanName',
								width : '19%',
								summaryType : 'sum',
				                summaryRenderer : totalMoney
							}, {
								header : '招标项目编号',
								dataIndex : 'bidPlanNumber',
								width : "19%",
								summaryType : 'sum',
				                summaryRenderer : totalMoney
							},  {
								header : '资金类型',
								dataIndex : 'fundTypeName',
								width : "10%",
								summaryType : 'sum',
				                summaryRenderer : totalMoney
							},{
								header : '计划收入金额',
								dataIndex : 'factIncomeMoney',
								width : "8%",
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							},{
								header : '计划收入时间',
								width : "8%",
								dataIndex : 'planReciveDate',
								align : 'center'
							},{
								header : '实际到账金额',
								dataIndex : 'factIncomeMoney',
								width : "8%",
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							} ,{
								header : '实际到账时间',
								width : "8%",
								dataIndex : 'factReciveDate'
							},{
								header : '未到账金额',
								dataIndex : 'notMoney',
								width : "8%",
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							}
						*/]
					});

				 //this.gridPanel.addListener('rowdblclick',this.rowClick);

			},
			
			reset : function() {
				this.searchPanel.getForm().reset();
			},
			// 按条件搜索
			search : function() {
				
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			}
		});