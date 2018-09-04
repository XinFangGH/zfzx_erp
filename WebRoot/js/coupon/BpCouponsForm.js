/**
 * @author 
 * @createtime 
 * @class BpCouponsForm
 * @extends Ext.Window
 * @description BpCoupons表单
 * @company 智维软件
 */
 BpCouponsForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				BpCouponsForm.superclass.constructor.call(this, {
							id : 'BpCouponsFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 300,
							width : 800,
							maximizable : true,
							title : '优惠券生成',
							buttonAlign : 'center',
							buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
											handler : this.save
										}, {
											text : '重置',
											iconCls : 'btn-reset',
											scope : this,
											handler : this.reset
										}, {
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											handler : this.cancel
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				var leftlabel = 100;
				var rightlabel = 100;
				this.formPanel = new Ext.form.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					autoScroll : true,
					frame : true,
					labelAlign : 'right',
					defaults : {
							anchor : '96%',
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
										name : 'bpCouponSetting.categoryId'
								},{
								columnWidth : 1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : rightlabel,
								labelAlign :'right',
								items : [{					
												fieldLabel : '优惠券类型',
												hiddenName : 'bpCouponSetting.couponType',
												allowBlank : false,
												readOnly : this.isReadOnly,
												anchor : '100%',
												xtype : "dicIndepCombo",
												nodeKey : 'couponType',
												editable : false,
												blankText : "优惠券类型要求不能为空，请正确填写!",
												anchor : "100%",
												listeners : {
													scope:this,
													afterrender : function(combox) {
														var st = combox.getStore();
														st.on("load", function() {
															if (combox.getValue() == null) {
																st.load({
																	"callback" : function() {
																		if (st.getCount() > 0) {
																			var record = st.getAt(0);
																			var v = record.data.itemId;
																			combox.setValue(v);
																		}
																	}
																});
															}
															if (combox.getValue() > 0) {
																combox.setValue(combox.getValue());
				
															}
														})
												}/*,
												change:function(combox){
												   combox.getStore().fin()
												   combox.getAt(number).data.
												}*/}
										},{
										  	xtype:"hidden",
										  	name : 'bpCouponSetting.couponTypeValue'
										}]
								},{
										columnWidth : .45, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'numberfield',
													fieldLabel : '面值',
													name : 'bpCouponSetting.couponValue',
													allowBlank : false,
													anchor : '100%',
													blankText : '面值为必填内容',
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
						            	columnWidth:0.45,
						                layout: 'form',
						                labelWidth : leftlabel,
						                labelAlign :'right',
						                items :[{
												xtype : 'numberfield',
												fieldLabel : '张数',
												name : 'bpCouponSetting.counponCount',
												allowBlank : false,
												anchor : '100%',
												blankText : '张数',
												readOnly : this.isReadOnly
											}]
					            	},{
										columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
											fieldLabel : "张",
											labelSeparator : '',
											anchor : "100%"
										}]
							
									},{
										columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													fieldLabel : "优惠券有效期",
													xtype : "datefield",
													style : {
														imeMode : 'disabled'
													},
													name : "bpCouponSetting.couponStartDate",
													allowBlank : false,
													readOnly : this.isAllReadOnly,
													blankText : "期望资金到位日期不能为空，请正确填写!",
													anchor : "100%",
													format : 'Y-m-d'
												}]
									},{
										columnWidth : .5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													fieldLabel : "至",
													xtype : "datefield",
													style : {
														imeMode : 'disabled'
													},
													name : "bpCouponSetting.couponEndDate",
													allowBlank : false,
													readOnly : this.isAllReadOnly,
													blankText : "期望资金到位日期不能为空，请正确填写!",
													anchor : "100%",
													format : 'Y-m-d'
												}]
									},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'textarea',
													fieldLabel : '描述',
													name : 'bpCouponSetting.couponDescribe',
													anchor : '100%',
													blankText : '描述',
													readOnly : this.isReadOnly
												}]
							
										},{
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : leftlabel,
										labelAlign :'right',
										items:[{
											html : "<font color=red >注：优惠券中体验券只能用在体验标中，返现券的使用规则参见招标计划</font>"
										}]
								}]
					}]
				});
				//加载表单对应的数据	
				if (this.categoryId != null && this.categoryId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/coupon/getBpCouponSetting.do?categoryId='+ this.categoryId,
								root : 'data',
								preName : 'bpCouponSetting'
							});
				}
				
			},//end of the initcomponents

			/**
			 * 重置
			 * @param {} formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * @param {} window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
				var gridPanel=this.gridPanel;
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/coupon/saveBpCouponSetting.do',
						callback:function(fp,action){
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});
		
CouponsDistributeForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				CouponsDistributeForm.superclass.constructor.call(this, {
							id : 'CouponsDistributeFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 200,
							width : 400,
							maximizable : true,
							title : '优惠券派发',
							buttonAlign : 'center',
							buttons : [
										{
											text : '派发',
											iconCls : 'btn-distribute',
											scope : this,
											handler : this.save
										}, {
											text : '重置',
											iconCls : 'btn-reset',
											scope : this,
											handler : this.reset
										}, {
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											handler : this.cancel
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				var leftlabel = 100;
				var rightlabel = 100;
				this.formPanel = new Ext.form.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					autoScroll : true,
					frame : true,
					labelAlign : 'right',
					defaults : {
							anchor : '96%',
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
										name : 'couponId',
										value:this.couponId
								},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
													xtype : 'textfield',
													fieldLabel : '投资人账号',
													name : 'p2pAccount',
													allowBlank : false,
													anchor : '100%',
													blankText : '投资人账号为必填内容',
													readOnly : this.isReadOnly
												}]
							
									},{
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : leftlabel,
										labelAlign :'right',
										items:[{
											html : "<br>系统派发（系统自动绑定用户，无需用户自己激活）<br>"
										}]
								},{
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : leftlabel,
										labelAlign :'right',
										items:[{
											html : "<br><font color=red >注：投资人账号是指P2P网站登陆账号</font>"
										}]
								}]
					}]
				});
				
			},//end of the initcomponents

			/**
			 * 重置
			 * @param {} formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * @param {} window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
				var gridPanel=this.gridPanel;
				this.formPanel.getForm().submit({
						scope:this,
						url:__ctxPath + '/coupon/couponsDistributeBpCoupons.do',
						method : 'post',
						waitMsg : '正在提交数据...',
			           	success : function(response, request) {
							var obj = Ext.util.JSON.decode(request.response.responseText);
							Ext.ux.Toast.msg('操作信息', obj.msg);
							this.close();
			                gridPanel.getStore().reload();
			            },
			            failure : function(fp, action) {
			                Ext.MessageBox.show({
			                    title : '操作信息',
			                    msg : '信息保存出错，请联系管理员！',
			                    buttons : Ext.MessageBox.OK,
			                    icon : 'ext-mb-error'
			                });
			            }
					}
				);
			}

		});	
 
 /*
BpCouponsForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				BpCouponsForm.superclass.constructor.call(this, {
							id : 'BpCouponsFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[BpCoupons]详细信息',
							buttonAlign : 'center',
							buttons : [
										{
											text : '保存',
											iconCls : 'btn-save',
											scope : this,
											handler : this.save
										}, {
											text : '重置',
											iconCls : 'btn-reset',
											scope : this,
											handler : this.reset
										}, {
											text : '取消',
											iconCls : 'btn-cancel',
											scope : this,
											handler : this.cancel
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll:true,
							//id : 'BpCouponsForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'bpCoupons.couponId',
								xtype : 'hidden',
								value : this.couponId == null ? '' : this.couponId
							}
																																																	,{
																fieldLabel : '优惠券编号(唯一字段)',	
								 								name : 'bpCoupons.couponNumber'
																,allowBlank:false
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '优惠券来源',	
								 								name : 'bpCoupons.couponResourceType'
																,allowBlank:false
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '优惠券来源表Id（主键）',	
								 								name : 'bpCoupons.resourceId'
																,allowBlank:false
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '单张优惠券状态  默认状态0 表示为未派发',	
								 								name : 'bpCoupons.couponStatus'
																,allowBlank:false
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '执行优惠券派发人姓名',	
								 								name : 'bpCoupons.bindOpratorName'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '执行优惠券派发人Id',	
								 								name : 'bpCoupons.bindOpratorId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '执行优惠券派发时间',	
								 								name : 'bpCoupons.bindOpraterDate'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '拥有者姓名',	
								 								name : 'bpCoupons.belongUserName'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '拥有者ID',	
								 								name : 'bpCoupons.belongUserId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '优惠券使用项目名',	
								 								name : 'bpCoupons.useProjectName'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '优惠券使用项目编号',	
								 								name : 'bpCoupons.useProjectNumber'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '优惠券使用项目ID',	
								 								name : 'bpCoupons.useProjectId'
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '优惠券使用项目类型',	
								 								name : 'bpCoupons.useProjectType'
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '优惠券使用时间',	
								 								name : 'bpCoupons.useTime'
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '创建时间',	
								 								name : 'bpCoupons.createDate'
																,allowBlank:false
								 																,xtype:'datefield',
								format:'Y-m-d',
								value:new Date()
								 							}
																																										,{
																fieldLabel : '创建人姓名',	
								 								name : 'bpCoupons.createName'
																,allowBlank:false
								 																 								,maxLength: 255
								 							}
																																										,{
																fieldLabel : '创建人Id',	
								 								name : 'bpCoupons.createUserId'
																,allowBlank:false
								 																,xtype:'numberfield'
								 							}
																																										,{
																fieldLabel : '所属公司   默认值为1，表示总公司',	
								 								name : 'bpCoupons.companyId'
																,allowBlank:false
								 																,xtype:'numberfield'
								 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.couponId != null && this.couponId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/coupon/getBpCoupons.do?couponId='+ this.couponId,
								root : 'data',
								preName : 'bpCoupons'
							});
				}
				
			},//end of the initcomponents

			*//**
			 * 重置
			 * @param {} formPanel
			 *//*
			reset : function() {
				this.formPanel.getForm().reset();
			},
			*//**
			 * 取消
			 * @param {} window
			 *//*
			cancel : function() {
				this.close();
			},
			*//**
			 * 保存记录
			 *//*
			save : function() {
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/coupon/saveBpCoupons.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('BpCouponsGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});*/