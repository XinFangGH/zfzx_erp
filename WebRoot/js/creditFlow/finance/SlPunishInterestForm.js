/**
 * @author
 * @createtime
 * @class SlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
SlPunishInterestForm = Ext.extend(Ext.Window, {
	
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				SlPunishInterestForm.superclass.constructor.call(this, {
							id : 'SlPunishInterestFormWin',
							title : '资金流水',
							region : 'center',
							layout : 'border',
							modal : true,
							height : 553,
							width : screen.width*0.9,
							maximizable : true,
							items : [this.searchPanel,this.gridPanel]

						});
			},// end of the constructor
			// 初始化组件
			
			initUIComponents : function() {
			var flag=this.flag;
			this.searchPanel = new HT.SearchPanel({
			layout : 'column',
			region : 'north',
			height : 30,
			anchor : '100%',
			keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
//			layoutConfig : {
//				align : 'middle'
//			},
			items : [
					{
				columnWidth : 0.42,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',

				items : [{
							fieldLabel : '我方账户',
							// submitValue:false,
						//	name : 'Q_myAccount_S_EQ',
							id : 'Q_dialAccounts_S_EQintentqlide',
							flex : 1,
							xtype : 'trigger',
							triggerClass : 'x-form-search-trigger',
							onTriggerClick : function() {
								selectAccountlForm(selectAccountkLinkManintent);
							},
							anchor : '100%'
						},{
							name : 'Q_myAccount_S_EQ',
							id : 'Q_dialAccounts_S_EQintentqlideid',
						//	hidden:true,
							xtype : 'hidden'
						}]

			}, {
				columnWidth : 0.14,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [{
							name : 'Q_factDate_D_GE',
							xtype : 'datefield',
							format : 'Y-m-d',
							fieldLabel : '到账日期',
							anchor : '100%'
						}]

			}, {
				columnWidth : 0.10,
				layout : 'form',
				border : false,
				labelWidth : 20,
				labelAlign : 'right',
				items : [{
							name : 'Q_factDate_D_LE',
							xtype : 'datefield',
							format : 'Y-m-d',
							fieldLabel : '到',
							anchor : '100%'
						}]

			}, {
				columnWidth : 0.13,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
							name : 'Q_dialMoney_BD_GE',
							xtype : 'textfield',
							width : 97,
							fieldLabel : '交易金额',
							anchor : '100%'

						}]

			},{
				columnWidth : 0.10,
				layout : 'form',
				border : false,
				labelWidth : 20,
				labelAlign : 'right',
				items : [ 
							{
							name : 'Q_dialMoney_BD_LE',
							xtype : 'textfield',
							width : 97,
							fieldLabel : '到',
							anchor : '100%'
						},{
							name : 'qlidetype',
							id:'qlidetype',
							xtype : 'textfield',
							hidden :true,
							value :'bankqlide'
						}]

			}

			, {
				columnWidth : .05,
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
			}, {
				columnWidth : .05,
				xtype : 'container',
				layout : 'form',
				defaults : {
					xtype : 'button'
				},
				style : 'padding-left:10px;',
				items : [ {
							text : '重置',
							scope : this,
							iconCls : 'reset',
							handler : this.reset
						}]
			}

			]
			});// end of searchPanel
				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-user-sel',
										text : '批量对账',
										xtype : 'button',
										scope : this,
										handler : this.checkRs
									},
									{
										iconCls : 'btn-user-sel',
										text : '单笔对账',
										xtype : 'button',
										scope : this,
										handler : this.editqlidecheckRs
									},'->',
						{
							xtype:'radio',
							scope : this,
							boxLabel : '银行流水',
							id:"bankqlide",
							name : 'qlide',
							inputValue : true,
							checked :true,
							listeners:{
								scope :this,
							    check :function(){
							    var flag=Ext.getCmp("bankqlide").getValue();
							     if(flag==true){ 
									    Ext.getCmp("qlidetype").setValue("bankqlide");
									    Ext.getCmp("Q_dialAccounts_S_EQintentqlide").setReadOnly(false);
									    Ext.getCmp("Q_dialAccounts_S_EQintentqlide").setValue("");
									    this.search();
							     }
							    }
							}
						},' ',' ',{
							xtype:'radio',
							boxLabel : '现金流水',
							id:"cashqlide",
							name : 'qlide',
							inputValue : false,
							listeners:{
								scope :this,
							    check :function(){
							   var flag=Ext.getCmp("cashqlide").getValue();
							     if(flag==true){
							     	 Ext.getCmp("qlidetype").setValue("cashqlide");
							     	  Ext.getCmp("Q_dialAccounts_S_EQintentqlide").setReadOnly(true);
							     	    Ext.getCmp("Q_dialAccounts_S_EQintentqlide").setValue("现金账户");
							     	    Ext.getCmp("Q_dialAccounts_S_EQintentqlideid").setValue(null);
									      this.search();
							    }
							    }
							}
						}
						
							]
						});
				
				this.gridPanel = new HT.GridPanel({
					rowActions : false,
					region : 'center',
					bodyStyle : "width : 100%",
					id : 'SlPunishInterestFormcheck',
					tbar : this.topbar,
					url : __ctxPath + "/creditFlow/finance/listSlFundQlide.do?fundType="+this.flag+"&companyId="+this.companyId,
					fields : [{
								name : 'fundQlideId',
								type : 'int'
							}	,'myAccount'
																																										,'dialMoney'
																																										,'afterMoney'
																																										,'notMoney'
																																										,'factDate'
																																										,'opAccount',
																																										'fundType',
																																										'currency',
																																										'transactionType',
																																										'bankName',
																																										'openName',
																																										'incomeMoney',
																																										'payMoney',
																																										'opOpenName',
																																										'opBankName',
																																										'isProject'
																																									],
					columns : [{
								header : 'fundQlideId11',
								dataIndex : 'fundQlideId',
								hidden : true
							}
//							, {
//								header : '资金流水号',
//								dataIndex : 'glideNum',
//								width : 100
//							}
							,{
																	header : '我方账户',	
																	dataIndex : 'bankName',
																	width:300
																	
								}
//								,{
//																	header : '我方账户名称',	
//																	dataIndex : 'openName',
//																	width:130
//																	
//								}
//								
//																																																,{
//																	header : '我方账号',	
//																	dataIndex : 'myAccount',
//																	width:130
//								}
								,{
								                                     header : '币种',	
																	dataIndex : 'currency',
																	width:60
								}
								,
									{
																	header : '到账时间',	
																	dataIndex : 'factDate',
																	sortable:true,
																	width:90
								}
								
								,{
																	header : '收入金额',	
																	dataIndex : 'incomeMoney',
																	align : 'right',
																	renderer:function(v){
																		if(v !=null){
																		return Ext.util.Format.number(v,',000,000,000.00')+"元"
																			}else{
																			return v}
																			
							                                      	}
								},{
																	header : '支出金额',	
																	dataIndex : 'payMoney',
																	align : 'right',
																	renderer:function(v){
																		if(v !=null){
																			return Ext.util.Format.number(v,',000,000,000.00')+"元"
																		return v+"元"
																			}else{
																			return v}
																			
							                                      	}
								}																																									,{
																	header : '已对账金额',
																	align : 'right',
																	dataIndex : 'afterMoney',
																	renderer:function(v){
																		if(v !=null){
																		return Ext.util.Format.number(v,',000,000,000.00')+"元"
																			}else{
																			return v}
																			
							                                      	}
																	
								}
								
								
																																																,{
																	header : '未对账金额',
																	align : 'right',
																	dataIndex : 'notMoney',
																	renderer:function(v){
																		if(v !=null){
																		return Ext.util.Format.number(v,',000,000,000.00')+"元"
																			}else{
																			return v}
																			
							                                      	}
																	
								},{
									                                 header:"对方账号",
									                                 dataIndex:"opAccount"
									                                 
								},{
									                                 header:"对方开户账名称",
									                                 dataIndex:"opOpenName"
									                                 
							},
//							,{
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
//									{
//									                                 header:"是否项目相关",
//									                                 dataIndex:"isProject"
//									                                 
//								}, 
									{
										                               header : '交易摘要',	
																	dataIndex : 'transactionType'
								 }
							]
						// end of columns
				});

			//	this.gridPanel.addListener('rowdblclick',this.rowClick);

			},
			editqlidecheckRs:function(){
			var s = this.gridPanel.getSelectionModel().getSelections();
			var tabflag=this.tabflag;
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
				var	record=s[0];
			     var flag=0;
					new punishEditQlideCheck({
						fundQlideId : record.data.fundQlideId,
						punishInterestId :this.punishInterestId,
						editqlideMoney : record.data.notMoney,
						notMoney :this.notMoney,
						tabflag :tabflag
					}).show();
				}	
				
				
		
				
				
			},
			checkRs:function(){
				var qlidePanel = this.gridPanel;
				  var ids = $getGdSelectedIds(qlidePanel,'fundQlideId');
				  var s = this.gridPanel.getSelectionModel().getSelections();
				var punishInterestId = this.punishInterestId;
				var businessType = this.businessType;
				var this1=this;
				  if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}
			Ext.MessageBox.confirm('确认', '确定对账吗', function(btn) {
					if (btn == 'yes') {
			       Ext.Ajax.request( {
									url : __ctxPath + '/creditFlow/finance/checkSlPunishInterest.do',
									method : 'POST',
									scope : this,
									params : {
										punishInterestId : punishInterestId,
										qlideId:ids,
										businessType : businessType
									},
									success : function(response, request) {
										   Ext.ux.Toast.msg('操作信息', '对账成功！');
										var gridPanel1 = Ext.getCmp('SlPunishInterestGrid');
                                        if (gridPanel1 != null) {
								         gridPanel1.getStore().reload();
						             	}
							                   this1.close();
										

									},
									checkfail:function(response, request) {
										Ext.Msg.alert('状态', "对账失败");
										var gridPanel = Ext.getCmp('SlPunishInterestGrid');
                                        if (gridPanel != null) {
								         gridPanel.getStore().reload();
						             	}
							                   this1.close();
										

									}
								});
				
					}
			
				})
			},
				reset : function() {
				  var flag=Ext.getCmp("qlidetype").getValue();
					this.searchPanel.getForm().reset();
					Ext.getCmp("qlidetype").setValue(flag);
					if(	flag=="cashqlide"){
				        Ext.getCmp("Q_dialAccounts_S_EQintentqlide").setValue("现金账户");
					
					}
				},
				// 按条件搜索
				search : function() {
					$search({
								searchPanel : this.searchPanel,
								gridPanel : this.gridPanel
							});
							
				},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-detail' :
						
						break;
					case 'btn-flow-chart' :
						this.showFlowImg.call(this);
						break;
					default :
						break;
				}
			}

		});
var selectAccountkLinkManintent = function(a,b,c,d,e,f) {
	Ext.getCmp('Q_dialAccounts_S_EQintentqlide').setValue(f+"-"+c+"-"+a);
	Ext.getCmp('Q_dialAccounts_S_EQintentqlideid').setValue(a);
	
}
