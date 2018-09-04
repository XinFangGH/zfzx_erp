/**
 * @author
 * @createtime
 * @class detailView
 * @extends Ext.Window
 * @description 资金流水详细
 * @company 智维软件
 */
punishDetailView = Ext.extend(Ext.Window, {
	
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				punishDetailView.superclass.constructor.call(this, {
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
				var fundType=this.fundType;
				var url = __ctxPath + "/creditFlow/finance/MoneyDetailSlPunishInterest.do?punishInterestId="+this.punishInterestId;
			
				
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
								handler : this.cancelAccount
							}
				
					]
				});
				this.gridPanel = new HT.GridPanel({
					border : false,
					isShowTbar : this.hidden2?false: true,
					rowActions : false,
					bbar:false,
					//tbar : this.hidden2?null: this.topbar,
					hiddenCm : this.hidden2,
					plugins: [summary],
			//		id : 'SlFundQlideGrid',
					url : url,
					viewConfig:{
						forceFit:true,
						getRowClass : function(record,rowIndex,rowParams,store){    
                            if(record.data.factDate == null){   
                                return 'grid_row_red';   
                            }                        
                        }   
            		}  ,
					fields : [{
								name : 'fundDetailId',
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
																																										'overdueAccrual',
																																										'operTime',
																																										'overdueRate',
																																										"intentFundType"],
					columns : [{
								header : 'fundDetailId',
								dataIndex : 'fundDetailId',
								hidden : true
							}
//							,
//								{
//
//																	header : '我方开户行',	
//																	dataIndex : 'bankName'
//																	
//								}
//								,{
//																	header : '我方账户名称',	
//																	dataIndex : 'openName'
//																	
//								}
								
																																																,{
																	header : '我方账号',	
																	summaryRenderer: totalMoney,
																	width:160,
																	dataIndex : 'qlidemyAccount'
																	
								},
									{
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
																	width : 105,
																	dataIndex : 'qlideincomeMoney',
																	align : 'right',
																	renderer:function(v){
																		if(v !=null){
																		return Ext.util.Format.number(v,',000,000,000.00')+"元"
																			}else{
																			return v}
																			
							                                      	}
								},{
																	header : '支出金额',	
																	width : 105,
																	align : 'right',
																	dataIndex : 'qlidepayMoney',
																	renderer:function(v){
																		if(v !=null){
																			return Ext.util.Format.number(v,',000,000,000.00')+"元"
																		return v+"元"
																			}else{
																			return v}
																			
							                                      	}
								}
								,
							{
														header : '划入本款项金额',
														dataIndex : 'afterMoney',
														align : 'right',
														width : 105,
														summaryType: 'sum',
														renderer : function(value, metadata, record, rowIndex,
																colIndex){
																	if(record.data.qlidemyAccount!=null){
																	return Ext.util.Format.number(value,',000,000,000.00')+"元"
													                }
													              if(record.data.qlidemyAccount ==null && record.data.factDate !=null){
													                return "现金"+Ext.util.Format.number(value,',000,000,000.00')+"元"
													              }
													              if(record.data.factDate ==null){
													                return "未还款项"+Ext.util.Format.number(value,',000,000,000.00')+"元"
													              }
																}
							},{
										                               header : '交易摘要',	
																	dataIndex : 'qlidetransactionType'
					    	 }
//					 
					    	
							]
						// end of columns
				});

		//		this.gridPanel.addListener('rowdblclick',this.rowClick);

			},
		
			//行的Action
			cancelAccount:function(){
				var qlidePanel = this.gridPanel;
				  var ids = $getGdSelectedIds(qlidePanel,'fundDetailId');
				 	var s = qlidePanel.getSelectionModel().getSelections();
				 	var businessType = this.businessType;
				 	var fundIntentId = this.fundIntentId;
				 	var this1=this;
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
									url : __ctxPath + '/creditFlow/finance/cancelAccountSlFundIntent.do',
									method : 'POST',
									scope : this,
									params : {
										fundIntentId : fundIntentId,
										qlideId:ids,
										businessType: businessType
									},
									success : function(response, request) {
										Ext.ux.Toast.msg('操作信息', '撤销成功！');
										var gridPanel = Ext.getCmp('SlFundIntentGrid');
                                        if (gridPanel != null) {
								         gridPanel.getStore().reload();
						             	}
							                   this1.close();
										

									},
									checkfail:function(response, request) {
										Ext.Msg.alert('状态', "撤销失败");
										var gridPanel = Ext.getCmp('SlFundIntentGrid');
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