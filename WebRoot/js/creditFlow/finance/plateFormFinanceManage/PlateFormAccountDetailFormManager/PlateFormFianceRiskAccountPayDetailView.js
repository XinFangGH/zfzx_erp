//PlateFormFianceRiskAccountPayDetailView.js
PlateFormFianceRiskAccountPayDetailView = Ext.extend(Ext.Panel, {
			title:"",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlateFormFianceRiskAccountPayDetailView.superclass.constructor.call(this, {
							id : 'PlateFormFianceRiskAccountPayDetailView',
							title : "风险保证金代偿台账",
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
										columnWidth : .3,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 90,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '招标项目名称',
											name : "bidPlanName",
											xtype : 'textfield',
											labelSeparator : ""
										},{
											fieldLabel : '招标项目编号',
											name : "bidPlanNumber",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },{
										columnWidth : .25,
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
											format : 'Y-m-d'
										},{
											fieldLabel : '借款人',
											name : "borrowerName",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },{
							         	columnWidth : .25,
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
											format : 'Y-m-d'
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
										},{
											text : '重置',
											scope : this,
											iconCls : 'reset',
											handler : this.reset
										}]
									}]
						});

				this.topbar = new Ext.Toolbar({
					items : [{
			        	iconCls : 'btn-user-sel',
			        	text : '查看偿还记录',
						xtype : 'button',
						scope : this,
						//hidden : isGranted('_liushui_f_'+this.businessType)?false:true,
						handler : this.seeRepaymentRecord
							
					},new Ext.Toolbar.Separator({
						//hidden : isGranted('_liushui_f_'+this.businessType)?false:true
					}),{
						iconCls : 'btn-detail',
						text : '借款人偿还保证金',
						xtype : 'button',
						scope : this,
						//hidden : isGranted('_liushuisee_f_'+this.businessType)?false:true,
						handler : this.recharge
			
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
					// 使用RowActions
					rowActions : false,
					id : 'PlateFormFianceRiskAccountPayDetailGrid',
					url : __ctxPath + "/plateForm/getpunishmentRecordSlRiskguaranteemoneyRepayment.do",
					fields : [{
								name : 'id',
								type : 'int'
							}, 'payMoney','payDate','notRebackMoney', 'rebackMoney','punishmentDate', 'factDate','rebackPunishmentMoney'
							,'planId','planrepayMentDate','punishmentRate','planName','planNumber','borrowerName','borrowerId','p2pborrowerId',
							 'p2pborrowerName','notRebackPunishmentMoney', 'totalPunishmentMoney','punishmentdays'],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true
							}, {
								header : '借款人',
								dataIndex : 'borrowerName',
								summaryType : 'sum',
				                summaryRenderer : totalMoney
							}, {
								header : '招标项目名称',
								dataIndex : 'planName'
							}, {
								header : '招标项目编号',
								dataIndex : 'planNumber',
								summaryType : 'sum',
				                summaryRenderer : totalMoney
							},{
								header : '平台代偿金额',
								dataIndex : 'payMoney',
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							}, {
								header : '平台代偿日期',
								dataIndex : 'payDate'
							},{
								header : '已偿还代偿金额',
								dataIndex : 'rebackMoney',
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							} ,{
								header : '未偿还代偿金额',
								dataIndex : 'notRebackMoney',
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							}, {
								header : '罚息费率',
								dataIndex : 'punishmentRate',
								renderer:function(v){
								 return v+"%/日"
                         	    }
							},  {
								header : '罚息天数',
								dataIndex : 'punishmentdays'
							},{
								header : '罚息总额',
								dataIndex : 'totalPunishmentMoney',
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							},{
								header : '已偿还罚息',
								dataIndex : 'rebackPunishmentMoney',
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							} ,{
								header : '未偿还罚息',
								dataIndex : 'notRebackPunishmentMoney',
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							},{
								header : '最近偿还时间',
								dataIndex : 'factDate',
								renderer:function(v){
									return  Ext.isEmpty(v)?"--":v;
                         	    }
							}
						]
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
			},
			//查看代偿记录
			seeRepaymentRecord:function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				}else{
					var record=s[0];
					var  repaymentRecord =new LoanerRepaymentRecord({
							punishmentId:s[0].data.id
					     });
					     
					repaymentRecord.show();
				}	
			}
					
		});