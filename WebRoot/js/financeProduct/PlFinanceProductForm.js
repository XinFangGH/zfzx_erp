/**
 * @author
 * @class BpCouponsView
 * @extends Ext.Panel
 * @description [BpCoupons]管理
 * @company 智维软件
 * @createtime:
 */
 
 PlFinanceProductForm = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PlFinanceProductForm.superclass.constructor.call(this, {
							id : 'PlFinanceProductForm'+this.typeId,
							title : "系统理财项目设置",
							region : 'center',
							layout : 'fit',
							modal : true,
							height :600,
							width : 900,
							maximizable : true,
							iconCls :'btn-team30',
							tbar : this.ttbar,
							items : [this.formPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				this.ttbar=new Ext.Toolbar({
					items : [{
									iconCls : 'btn-save',
									text : '保存',
									xtype : 'button',
									scope:this,
									handler : this.save
								}]
				});
				var leftlabel = 150;
				var rightlabel = 150;
				this.formPanel = new Ext.FormPanel({
					region : 'center',
					layout : 'form',
					bodyStyle : 'padding:10px',
					autoScroll : true,
					frame : true,
					labelAlign : 'right',
					defaults : {
							anchor : '60%',
							columnWidth : 1,
							labelWidth : 60
						},
					items : [{
						layout : "column",
						border : false,
						scope : this,
						defaults : {
							anchor : '100%',
							columnWidth : 1,
							isFormField : true,
							labelWidth : leftlabel
						},
						items : [{
										xtype : 'hidden',
										name : 'plFinanceProduct.id'
								},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'textfield',
													fieldLabel : '理财专户产品名称',
													name : 'plFinanceProduct.productName',
													anchor : '100%',
													allowBlank : false,
													blankText : '描述',
													readOnly : this.isReadOnly
												}]
							
								},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{					
														fieldLabel : '起息模式',
														hiddenName : 'plFinanceProduct.intestModel',
														allowBlank : false,
														readOnly : this.isReadOnly,
														anchor : '100%',
														xtype : 'combo',
														mode : 'local',
														valueField : 'value',
														editable : false,
														displayField : 'item',
														store : new Ext.data.SimpleStore({
															fields : ["item","value"],
															data : [["T+0","0"],["T+1","1"], 
																	["T+2","2"],["T+3","3"]]
														}),
														triggerAction : "all"
												},{
												  	xtype:"hidden",
												  	name : 'plFinanceProduct.intestModelName'
												}]
								},/*{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{					
														fieldLabel : '转出到账日',
														hiddenName : 'plFinanceProduct.exitModel',
														allowBlank : false,
														readOnly : this.isReadOnly,
														anchor : '100%',
														xtype : 'combo',
														mode : 'local',
														valueField : 'value',
														editable : false,
														displayField : 'item',
														store : new Ext.data.SimpleStore({
															fields : ["item","value"],
															data : [["T+0","0"],["T+1","1"], 
																	["T+2","2"],["T+3","3"], 
																	["T+4","4"],["T+5","5"], 
																	["T+6","6"],["T+7","7"]]
														}),
														triggerAction : "all"
												}]
								},*/{
										columnWidth : 0.95, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'numberfield',
													fieldLabel : '起投金额',
													name : 'plFinanceProduct.minBidMoney',
													allowBlank : false,
													anchor : '100%',
													blankText : '起投金额为必填内容',
													readOnly : this.isReadOnly
												}]
							
									},{
										columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
											fieldLabel : "元",
											labelSeparator : '',
											anchor : "100%"
										}]
							
									},{
						            	columnWidth:0.95,
						                layout: 'form',
						                labelWidth : leftlabel,
						                labelAlign :'right',
						                items :[{
												xtype : 'numberfield',
												fieldLabel : '单笔投资限额',
												name : 'plFinanceProduct.maxBidMoney',
												allowBlank : false,
												anchor : '100%',
												blankText : '单笔投资限额',
												readOnly : this.isReadOnly
											}]
					            	},{
										columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
											fieldLabel : "元",
											labelSeparator : '',
											anchor : "100%"
										}]
							
									},/*{
						            	columnWidth:0.95,
						                layout: 'form',
						                labelWidth : leftlabel,
						                labelAlign :'right',
						                items :[{
												xtype : 'numberfield',
												fieldLabel : '累积投资限额',
												name : 'plFinanceProduct.totalBidMoney',
												allowBlank : false,
												anchor : '100%',
												blankText : '累积投资限额',
												readOnly : this.isReadOnly
											}]
					            	},{
										columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
											fieldLabel : "元",
											labelSeparator : '',
											anchor : "100%"
										}]
							
									},*/{
						            	columnWidth:0.95,
						                layout: 'form',
						                labelWidth : leftlabel,
						                labelAlign :'right',
						                items :[{
												xtype : 'numberfield',
												fieldLabel : '转入加入费用',
												name : 'plFinanceProduct.plateRate',
												allowBlank : false,
												anchor : '100%',
												blankText : '转入加入费用',
												value:0,
												readOnly : this.isReadOnly
											}]
					            	},{
										columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
											fieldLabel : "%",
											labelSeparator : '',
											anchor : "100%"
										}]
							
									},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'textfield',
													fieldLabel : '安全保障',
													name : 'plFinanceProduct.productGuarantee',
													anchor : '100%',
													blankText : '描述',
													readOnly : this.isReadOnly
												}]
							
								},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													fieldLabel : "发布日期",
													xtype : "datefield",
													style : {
														imeMode : 'disabled'
													},
													name : "plFinanceProduct.productStartDate",
													allowBlank : false,
													readOnly : this.isAllReadOnly,
													blankText : "发布日期不能为空，请正确填写!",
													anchor : "100%",
													format : 'Y-m-d'
												}]
									},/*{
										columnWidth : 0.95, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'numberfield',
													fieldLabel : '平台费率',
													name : 'plFinanceProduct.plateRate',
													allowBlank : false,
													anchor : '100%',
													blankText : '平台费率为必填内容',
													readOnly : this.isReadOnly
												}]
							
									},{
										columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
											fieldLabel : "元",
											labelSeparator : '',
											anchor : "100%"
										}]
							
									},*/{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'textarea',
													fieldLabel : '理财专户产品介绍',
													name : 'plFinanceProduct.productDes',
													anchor : '100%',
													allowBlank : false,
													blankText : '描述',
													readOnly : this.isReadOnly
												}]
							
										},{
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : leftlabel,
										labelAlign :'right',
										items:[{
											html : "<font color=red >注：默认系统只有一个理财专有产品</font>"
										}]
								}]
					}]
				});

				 this.formPanel.loadData({
								url : __ctxPath + '/financeProduct/getPlFinanceProduct.do?typeId='+this.typeId,
								root : 'data',
								preName : 'plFinanceProduct'
							});

			},
			
			save : function() {
				if(this.formPanel.getForm().isValid()) {
						this.formPanel.getForm().submit({
							        url:__ctxPath + '/financeProduct/savePlFinanceProduct.do',
									method : 'post',
									waitMsg : '正在提交数据...',
									success : function(fp, action) {
										Ext.ux.Toast.msg('操作信息', '成功信息保存！');
										
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
			}
		);

