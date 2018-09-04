/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
SlRepaymentView =  Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg.businessType)!="undefined")
				{
				      this.businessType=_cfg.businessType;
				}
				if(typeof(_cfg.fundType)!="undefined")
				{
				      this.fundType=_cfg.fundType; //principal ,interest
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				SlRepaymentView.superclass.constructor.call(this, {
							id : 'SlRepaymentView',
							region : 'center',
							layout : 'border',
							modal : true,
							height :600,
							width : 900,
							maximizable : true,
							title : '还款',
							items : [this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
                var labelsize=70;
				 var labelsize1=115;

				this.topbar = new Ext.Toolbar({
					items : [{
			        	iconCls : 'btn-user-sel',
			        	text : this.fundType=="principalLending"?"确定放款":"确定还款",
						xtype : 'button',
						scope : this,
						handler : this.openliushuiwin
							
					}
					]
				});

				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					viewConfig: {  
		            	forceFit:false  
		            },
					// 使用RowActions
					rowActions : false,
					id : 'SlReapyMentGrid',
					url : __ctxPath + "/creditFlow/finance/selectInvestPersonSlFundIntent.do?Q_bidPlanId_L_EQ="+this.bidPlanId+"&Q_fundType_S_EQ="+this.fundType+"&Q_projectId_L_EQ="+this.projectId+"&Q_payintentPeriod_N_EQ="+this.payintentPeriod,
					fields : [{
						name : 'fundIntentId'
					}, {
						name : 'fundType'
					}, {
						name : 'fundTypeName'
					}, {
						name : 'incomeMoney'
					}, {
						name : 'payMoney'
					}, {
						name : 'intentDate'
					}, {
						name : 'investPersonId'
					}, {
						name : 'investPersonName'
					}, {
						name : 'remark'
					}, {
						name : 'payintentPeriod'
					}, {
						name : 'isValid'
					}, {
						name : 'factDate'
					}, {
						name : 'afterMoney'
					}, {
						name : 'notMoney'
					}, {
						name : 'flatMoney'
					}, {
						name : 'fundResource'
					}, {
						name : 'preceptId'
					}, {
						name : 'bidPlanId'
					}, {
						name : 'projectName'
					}, {
						name : 'orderNo'
					}],
					columns : [{
						header : 'fundIntentId',
						dataIndex : 'fundIntentId',
						hidden : true
					}, {
						header : '订单号',
						dataIndex : 'orderNo',
						sortable : true,
						width : 100,
						scope : this,
						summaryType : 'count'
					}, {
						header : '投资项目名称',
						dataIndex : 'projectName',
						readOnly : this.isHidden,
						sortable : true,
						width : 100,
						scope : this,
						summaryType : 'count'
					}, {
						header : '投资方',
						dataIndex : 'investPersonName',
						readOnly : this.isHidden,
						sortable : true,
						width : 100,
						scope : this,
						summaryType : 'count'
					}, {
						header : '计划到帐日',
						dataIndex : 'intentDate',
						format : 'Y-m-d',
						fixed : true,
						width : 80,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var v;
							try {
								if (typeof(value) == "string") {
									v = value;
								} else {
									v = Ext.util.Format.date(value, 'Y-m-d');
								}
							} catch (err) {
								v = value;
							}
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ v + "</font>";
							} else {
								return v;
							}
						}
					}, {
						header : '计划收入金额',
						dataIndex : 'incomeMoney',
						summaryType : 'sum',
						align : 'right',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '实际到帐日',
						dataIndex : 'factDate',
						format : 'Y-m-d',
						hidden : false,
						editor : this.datafield,
						fixed : true,
						width : 80,
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var v;
							try {
								if (typeof(value) == "string") {
									v = value;
								} else {
									v = Ext.util.Format.date(value, 'Y-m-d');
								}
							} catch (err) {
								v = value;
							}
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ v + "</font>";
							} else {
								return v;
							}
						}
					}, {
						header : '已对账金额(元)',
						summaryType : 'sum',
						hidden : false,
						dataIndex : 'afterMoney',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '计划支出金额',
						dataIndex : 'payMoney',
						align : 'right',
						summaryType : 'sum',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}, {
						header : '期数',
						dataIndex : 'payintentPeriod',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (null != value) {
								if (record.data.isValid == 1) {
									return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
											+ '第' + value + '期' + "</font>"
								} else {
									return '第' + value + '期';
								}
							}
						}
					},  {
						header : '未对账金额(元)',
						summaryType : 'sum',
						hidden : true,
						dataIndex : 'notMoney',
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							if (record.data.isValid == 1) {
								return '<font style="font-style:italic;text-decoration: line-through;color:gray">'
										+ Ext.util.Format.number(value,
												',000,000,000.00') + "元</font>"
							} else {
								return Ext.util.Format.number(value,
										',000,000,000.00')
										+ "元";
							}
						}
					}]

						// end of columns
					});

				this.gridPanel.addListener('cellclick', this.cellClick);

			},// end of the initComponents()
			// 重置查询表单
//			rowClick : function(grid, rowindex, e) {
//				grid.getSelectionModel().each(function(rec) {
//							new editAfterMoneyForm({
//								fundIntentId : rec.data.fundIntentId,
//								afterMoney : rec.data.afterMoney,
//								notMoney : rec.data.notMoney,
//								flatMoney : rec.data.flatMoney
//									}).show();
//						});
//				
//			},
		
			
			openliushuiwin : function() {
				var fundType=this.fundType;
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请至少选中一条记录');
					return false;
				}else if(s.length>=1){
					var record = s[0];
					var ids = Array();
						for (var i = 0; i < s.length; i++) {
							ids.push(s[i].data.fundIntentId);
						}
				Ext.MessageBox.confirm('确认操作？', '操作后就不能恢复了', function(btn) {
					
					if (btn == 'yes') {
						var mk = new Ext.LoadMask('SlReapyMentGrid',{  
			msg: '正在提交数据，请稍候！',  
			removeMask: true //完成后移除  
     });  
	 mk.show(); //显示  
			           Ext.Ajax.request( {
									url : __ctxPath + '/creditFlow/finance/repayMentListSlFundIntent.do',
									method : 'POST',
									waitMsg : '正在发送',
									params : {
										ids : ids,
										postType:1,
										fundType:fundType
									},
									success : function(response, request) {
									   mk.hide();
										var record = Ext.util.JSON.decode(response.responseText);
										 Ext.Msg.alert('状态', record.msg);
										 Ext.getCmp('SlReapyMentGrid').getStore().reload();
									},
									checkfail:function(response, request) {
										mk.hide();
										Ext.Msg.alert('状态', "失败");
									}
								});

		
					}
			
				})
			
					
				}
			}
		});

