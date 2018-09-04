/**
 * @author
 * @createtime
 * @class detailView
 * @extends Ext.Window
 * @description 资金流水详细
 * @company 智维软件
 */
chargeDetailView = Ext.extend(Ext.Window, {
	
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				chargeDetailView.superclass.constructor.call(this, {
							layout : 'fit',
							items : this.gridPanel,
							modal : true,
							height : 500,
							width : screen.width-252,
							autoScroll:true,
							maximizable : true,
							title : '资金明细'

						});
			},// end of the constructor
			// 初始化组件
			
			initUIComponents : function() {
				
				var url = "";
				if(this.flag == 1) {
					url = __ctxPath + "/creditFlow/finance/MoneyDetailSlActualToCharge.do?actualChargeId="+this.actualChargeId;
				}else if(this.flag == 2){
					url = __ctxPath + "/creditFlow/finance/slProjectFundDetailSlFundIntent.do?projectId="+this.projectId;
				}
				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
							return '总计(元)';
						}
				this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-user-sel',
								text : '撤销',
								xtype : 'button',
								scope : this,
								hidden : this.hidden2,
								handler : this.cancelAccount
							}
				
					]
				});
				this.gridPanel = new HT.GridPanel({
					border : false,
					isShowTbar:false,
					rowActions : true,
					bbar:false,
					tbar :this.topbar,
					plugins: [summary],
					id : 'chargeDetailView',
					url : url,
					fields : [{
								name : 'chargeDetailId',
								type : 'int'
							}
                                                                                                                                                                        ,'qlidemyAccount'
																																										,'dialMoney'
																																										,'qlideafterMoney'
																																										,'qlidenotMoney'
																																										,'factDate'
																																										,'opAccount',
																																										'fundType',
																																										'qlidecurrency',
																																										'qlidetransactionType',
																																										'bankName',
																																										'openName',
																																										'qlideincomeMoney',
																																										'qlidepayMoney',
																																										'opOpenName',
																																										'opBankName',
																																										'afterMoney',
																																										'overdueNum',
																																										'overdueAccrual'],
					columns : [{
								header : 'chargeDetailId',
								dataIndex : 'chargeDetailId',
								hidden : true
							}

								
																																																,{
																	header : '我方账号',	
																	dataIndex : 'qlidemyAccount',
																	width:160,
																	summaryRenderer: totalMoney
								},{
								                                     header : '币种',	
																	dataIndex : 'qlidecurrency',
																	width:50
								}
								,
									{
																	header : '到账时间',	
																	dataIndex : 'factDate',
																	sortable:true,
																	width:76
								}
								
								,{
																	header : '收入金额',	
																	dataIndex : 'qlideincomeMoney',
																	align : 'right',
																	width : 105,
																	renderer:function(v){
																		if(v !=null){
																		return Ext.util.Format.number(v,',000,000,000.00')+"元"
																			}else{
																			return v}
																			
							                                      	}
								},{
																	header : '支出金额',	
																	dataIndex : 'qlidepayMoney',
																	align : 'right',
																	width : 105,
																	renderer:function(v){
																		if(v !=null){
																			return Ext.util.Format.number(v,',000,000,000.00')+"元"
																		return v+"元"
																			}else{
																			return v}
																			
							                                      	}
								}	
									
								,
//									{
//									                                 header:"对方开户银行",
//									                                 dataIndex:"opBankName"
//									                                 
//								},{
//									                                 header:"对方开户账号",
//									                                 dataIndex:"opOpenName"
//									                                 
//								},{
//									                                 header:"对方账号",
//									                                 dataIndex:"opAccount"
//									                                 
//								}, 
									{
										                               header : '交易摘要',	
																	dataIndex : 'qlidetransactionType'
								 },{
								header : '划入本手续收支金额',
								dataIndex : 'afterMoney',
								align : 'right',
								width : 80,
								summaryType: 'sum',
								renderer : function(value, metadata, record, rowIndex,
										colIndex){
											if(record.data.qlidemyAccount!=null){
											return Ext.util.Format.number(value,',000,000,000.00')+"元"
												
							              }else{
							                return "现金"+Ext.util.Format.number(value,',000,000,000.00')+"元"
							              
							              }
												
							    }
								
							},
							new Ext.ux.grid.RowActions({
									header:'管理',
									width:100,
									hidden : true,
									actions:[{
		//									iconCls:'btn-detail',qtip:'撤销',style:'margin:0 3px 0 3px'
										text:'撤销',xtype : 'button',style:'margin:0 3px 0 3px'
										}
									],
									listeners:{
										scope:this,
										'action':this.onRowAction
									}
								})
							]
						// end of columns
				});

		//		this.gridPanel.addListener('rowdblclick',this.rowClick);

			},
		
			//行的Action
			cancelAccount:function(){
				var qlidePanel = Ext.getCmp('chargeDetailView');
				  var ids = $getGdSelectedIds(qlidePanel,'chargeDetailId');
				  var actualChargeId = this.actualChargeId;
				  var this1=this;
			   var s = qlidePanel.getSelectionModel().getSelections();
				  if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{
					Ext.MessageBox.confirm('确认撤销吗', '撤销就不能恢复了', function(btn) {
					if (btn == 'yes') {
			           Ext.Ajax.request( {
									url : __ctxPath + '/creditFlow/finance/cancelAccountSlActualToCharge.do',
									method : 'POST',
									scope : this,
									params : {
										actualChargeId : actualChargeId,
										qlideId:ids
									},
									success : function(response, request) {
										  Ext.ux.Toast.msg('操作信息', '撤销成功！');
										var gridPanel = Ext.getCmp('SlActualToChargeGrid');
                                        if (gridPanel != null) {
								         gridPanel.getStore().reload();
						             	}
							                   this1.close();
										

									},
									checkfail:function(response, request) {
										Ext.Msg.alert('状态', "撤销失败");
										var gridPanel = Ext.getCmp('SlActualToChargeGrid');
                                        if (gridPanel != null) {
								         gridPanel.getStore().reload();
						             	}
							                   this1.close();
										

									}
								});

		
					}
			
				})
			}
				
			},
			onRowAction : function(grid, record, action, row, col) {
				this.cancelAccount.call(this);
			}
		});