//ConfirmTransferFeeWindow.js
ConfirmTransferFeeWindow = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				ConfirmTransferFeeWindow.superclass.constructor.call(this, {
							layout : 'fit',
							border : false,
							items : this.formPanel,
							modal : true,
							height : 240,
							width : 700,
//							maximizable : true,
							title : this.transferType=="cancel"?'取消预收费用':'确认实收费用',
							buttonAlign : 'center',
							buttons : [{
										text : '确认',
										iconCls : 'btn-save',
										scope : this,
										handler : this.save
									}, {
										text : '取消',
										iconCls : 'btn-cancel',
										scope : this,
										hidden:this.isAllReadOnly,
										disabled:this.isAllReadOnly,
										handler : this.cancel
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
											fieldLabel : '债权名称',
											name : 'plBidSale.bidProName',
											anchor : '98%',
											readOnly:true
											
										  
									},{
											xtype : 'hidden',
											name : 'plBidSale.id'
									},{
											xtype : 'hidden',
											name : 'plBidSale.preTransferFeeStatus'
									}]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '出让人',
											name : 'plBidSale.outCustName',
											anchor : '98%',
											readOnly:true,
											value:this.thirdPayType
										  
									}]
								},{  
									 columnWidth : 0.5,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '受让人',
											name : 'plBidSale.inCustName',
											anchor : '98%',
											readOnly:true,
											value:this.plateFormnumber
										  
									}]
								},{  
									 columnWidth : 0.45,
								     layout : 'form',
									 items :[{
											xtype : 'textfield',
											fieldLabel : '出让金额',
											name : 'plBidSale.saleMoney',
											anchor : '98%',
											readOnly:true,
											value:this.accountName
										  
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
											fieldLabel : '手续费率',
											readOnly:true,
											name : 'feeRate',
											anchor : '98%',
											value:1
										  
									 }]
								},{
									 columnWidth : .05, // 该列在整行中所占的百分比
									 layout : "form", // 从上往下的布局
									 labelWidth :13,
									 items : [{
											fieldLabel : "<span style='margin-left:1px'>%</span> ",
											labelSeparator : '',
											anchor : "100%"
								     }]
							   },{  
									 columnWidth : .45,
								     layout : 'form',
									 items :[{
											xtype : 'numberfield',
											fieldLabel : '预收服务费',
											readOnly:true,
											name : 'plBidSale.preTransferFee',
											anchor : '98%'
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
											fieldLabel : '实际服务费',
											allowBlank : false,
											readOnly:true,
											name : 'plBidSale.transferFee',
											anchor : '98%'
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
											fieldLabel : '退还费用',
											allowBlank : false,
											readOnly:true,
											name : 'plBidSale.reBackFee',
											anchor : '98%'
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
						
						//加载表单对应的数据	
				if (this.id != null && this.id != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/creditFlow/financingAgency/getPlBidSale.do?id='+ this.id,
								root : 'data',
								preName : 'plBidSale'
							});
				}
						
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
			 * 保存记录
			 */
			save : function() {
				var preTransferStatus=this.formPanel.getCmpByName("plBidSale.preTransferFeeStatus").getValue();
				var rebackFee= this.formPanel.getCmpByName("plBidSale.reBackFee").getValue();
				if(preTransferStatus!=null&&preTransferStatus==2){
					Ext.ux.Toast.msg('操作信息',"已经完成了实收转预收");
					this.close();
					return;
				}/*else if(preTransferStatus!=null&&preTransferStatus==1&&rebackFee==0){
					Ext.ux.Toast.msg('操作信息',"已经完成了实收转预收并无退费");
					this.close();
					return;
				}*/else{
					if(this.transferType != null && this.transferType != 'undefined'&&preTransferStatus!=null&&preTransferStatus==1){
						Ext.ux.Toast.msg('操作信息',"已经完成了实收转预收,不能取消预收也不能再次转实收");
						this.close();
						return;
					}else{
						var url=__ctxPath + '/creditFlow/financingAgency/pretransferFeeChangeFactTransferPlBidSale.do';
						if(this.transferType != null && this.transferType != 'undefined'){
							url=__ctxPath + '/creditFlow/financingAgency/pretransferFeeChangeFactTransferPlBidSale.do?transferType='+this.transferType;
						}
						this.formPanel.getForm().submit({
									clientValidation: false, 
									scope : this,
									method : 'post',
									waitMsg : '数据正在提交，请稍后...',
									scope: this,
									url : url,
									success : function(fp, action) {
										var object = Ext.util.JSON.decode(action.response.responseText)
										Ext.ux.Toast.msg('操作信息',object.msg);
										this.close();
									},
									failure : function(fp, action) {
										Ext.MessageBox.show({
											title : '操作信息',
											msg : '信息保存出错，请联系管理员！',
											buttons : Ext.MessageBox.OK,
											icon : 'ext-mb-error'
										});
									}
								});
					}
				}
				
			}// end of save
		});