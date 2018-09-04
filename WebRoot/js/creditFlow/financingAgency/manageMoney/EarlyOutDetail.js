//ConfirmTransferFeeWindow.js
EarlyOutDetail = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				EarlyOutDetail.superclass.constructor.call(this, {
							layout : 'fit',
							border : false,
							items : this.formPanel,
							modal : true,
							height : 240,
							width : 700,
//							maximizable : true,
							title : '提前支取审核',
							buttonAlign : 'center',
							buttons : [{
										text : '同意',
										iconCls : 'btn-save',
										scope : this,
										handler : this.agree
									}, {
										text : '驳回',
										iconCls : 'btn-cancel',
										scope : this,
										handler : this.back
									}]
						});
			},// end of the constructor
			// 初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout:'column',
							bodyStyle : 'padding:10px',
							autoScroll : true,
							monitorValid : true,
							frame : true,
						    plain : true,
						    labelAlign : "right",
							defaults : {
								anchor : '96%',
								labelWidth : 110,
								columnWidth : 1,
							    layout : 'column'
							},
							items : [{  
									columnWidth : 1,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '投资人',
											name : 'plBidSale.bidProName',
											anchor : '98%',
											readOnly:true,
											value:this.investPersonName
											
										  
									}]
								},{  
									 columnWidth : 0.45,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '欠派利息',
											name : 'plBidSale.saleMoney',
											anchor : '98%',
											readOnly:true,
											value:this.loanInterestMoney
										  
									 }]
								},{
									 columnWidth : .05, // 该列在整行中所占的百分比
									 layout : "form", // 从上往下的布局
									 labelWidth :13,
									 items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
								     }]
							   },{  
									 columnWidth : 0.45,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '本金',
											readOnly:true,
											name : 'feeRate',
											anchor : '98%',
											value:this.principalRepaymenMoney
										  
									 }]
								},{
									 columnWidth : .05, // 该列在整行中所占的百分比
									 layout : "form", // 从上往下的布局
									 labelWidth :13,
									 items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
								     }]
							   },{  
									 columnWidth : .45,
								     layout : 'form',
									 items :[{
											xtype : 'numberfield',
											fieldLabel : '提前支取违约金',
											readOnly:true,
											name : 'plBidSale.preTransferFee',
											anchor : '98%',
											value:this.liquidatedDamagesMoney
									 }]
								},{
									 columnWidth : .05, // 该列在整行中所占的百分比
									 layout : "form", // 从上往下的布局
									 labelWidth :13,
									 items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
								     }]
							   },{  
									 columnWidth : .45,
								     layout : 'form',
									 items :[{
											xtype : 'numberfield',
											fieldLabel : '结算金额',
											allowBlank : false,
											readOnly:true,
											name : 'plBidSale.transferFee',
											anchor : '98%',
											value:this.sumMoney
									 }]
								},{
									 columnWidth : .05, // 该列在整行中所占的百分比
									 layout : "form", // 从上往下的布局
									 labelWidth :13,
									 items : [{
											fieldLabel : "<span style='margin-left:1px'>元</span> ",
											labelSeparator : '',
											anchor : "100%"
								     }]
							   }]
						});
					
						
			},
			/**
			 * 取消
			 * 
			 * @param {}
			 *  window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 同意
			 */
			agree : function() {
					args = {
						    'state' : 8,
							'checkStatus':1,
							'earlyRedemptionId':this.earlyRedemptionId
							}    
					var loadMask1 = new Ext.LoadMask(Ext.getBody(), {
						msg : '正处理中······,请稍候······',
						removeMask : true// 完成后移除
					});
					loadMask1.show(); // 显示
						Ext.Ajax.request({
							url : __ctxPath + "/creditFlow/financingAgency/updateStatePlEarlyRedemption.do",
							method : 'POST',
							scope :this,
							success : function(response, request) {
								loadMask1.hide();
								this.close();
								this.gp.getStore().reload();
								var record = Ext.util.JSON.decode(response.responseText);
								Ext.ux.Toast.msg('信息提示', Ext.isEmpty(record.msg)?"操作成功":record.msg);
								
							},
							failure : function(response) {
								loadMask1.hide();
								Ext.ux.Toast.msg('状态', '操作失败，请重试！');
							},
							params: args
					    })				
			}// end of save
			,
					/**
			 * 驳回
			 */
			back : function() {
					args = {
							'state' : 2,
							'checkStatus':3,
							'earlyRedemptionId':this.earlyRedemptionId
							}    
			    var loadMask1 = new Ext.LoadMask(Ext.getBody(), {
					msg : '正处理中·······,请稍候······',
					removeMask : true// 完成后移除
				});
				loadMask1.show(); // 显示
				Ext.Ajax.request({
					url : __ctxPath + "/creditFlow/financingAgency/updateStatePlEarlyRedemption.do",
					method : 'POST',
					scope :this,
					success : function(response, request) {
						loadMask1.hide();
						this.close();
//						Ext.getCmp("EarlyOutListGrid").getStore().reload();
						var record = Ext.util.JSON.decode(response.responseText);
						Ext.ux.Toast.msg('信息提示', Ext.isEmpty(record.msg)?"操作成功":record.msg);
						this.gp.getStore().reload();
					},
					failure : function(response) {
						loadMask1.hide();
						Ext.ux.Toast.msg('状态', '操作失败，请重试！');
					},
					params: args
				})
			}
				
		});