/**
 * @author 
 * @createtime 
 * @class BpCouponSettingForm
 * @extends Ext.Window
 * @description BpCouponSetting表单
 * @company 智维软件
 */
BpCouponSettingForm = Ext.extend(Ext.Window, {
	 isChange:false,
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				BpCouponSettingForm.superclass.constructor.call(this, {
							id : 'BpCouponSettingFormWin',
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
								columnWidth : .45, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : rightlabel,
								labelAlign :'right',
								items : [{					
												fieldLabel : '优惠券类型',
												hiddenName : 'bpCouponSetting.couponType',
												anchor : "100%",
												allowBlank : false,
												readOnly : this.isReadOnly,
												xtype : 'combo',
												mode : 'local',
												valueField : 'value',
												editable : false,
												displayField : 'item',
												store : new Ext.data.SimpleStore({
													fields : ["item","value"],
													data : [["优惠券","1"], 
															/*["体验券","2"],*/
															["加息券","3"]
														    ]
												}),
												listeners:{
												 scope : this,
												 select :function(combox){
												  if(combox.getRawValue()=="加息券"){
												  		Ext.getCmp('yuan').setVisible(false);
												  		Ext.getCmp('yuan2').setVisible(true);
												  		Ext.getCmp('couponValue').setValue("");
												  }else{
												  		Ext.getCmp('yuan').setVisible(true);
												  		Ext.getCmp('yuan2').setVisible(false);
												  		Ext.getCmp('couponValue').setValue("");
												  }
												 }
												
												},
												triggerAction : "all"
												
										},{
										  	xtype:"hidden",
										  	name : 'bpCouponSetting.couponTypeValue'
										}]
								},{
										columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
											fieldLabel : " ",
											labelSeparator : '',
											anchor : "100%"
										}]
							
									},{
										columnWidth : .45, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{					
												fieldLabel : '派发类型',
												hiddenName : 'bpCouponSetting.setType',
												anchor : "100%",
												allowBlank : false,
												readOnly : this.isReadOnly,
												xtype : 'combo',
												mode : 'local',
												valueField : 'value',
												editable : false,
												displayField : 'item',
												store : new Ext.data.SimpleStore({
													fields : ["item","value"],
													data : [["单个派发","1"], 
															["批量派发","2"]
														    ]
												}),
												triggerAction : "all"
												
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
													id : 'couponValue',
													allowBlank : false,
													anchor : '100%',
													blankText : '面值为必填内容',
													readOnly : this.isReadOnly
												}]
							
									},{
										columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										id : 'yuan',
										items : [{
											fieldLabel : "元",
											labelSeparator : '',
											anchor : "100%"
										}]
									},{								
										columnWidth : .03, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 15,
										id:'yuan2',
										hidden:true,
										items : [{
											fieldLabel : "%",
											labelSeparator : ''
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
										columnWidth : .45, // 该列在整行中所占的百分比
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
										columnWidth : .05, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 20,
										items : [{
											fieldLabel : " ",
											labelSeparator : '',
											anchor : "100%"
										}]
							
									},{
										columnWidth : .45, // 该列在整行中所占的百分比
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
							
										}/*,{
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : leftlabel,
										labelAlign :'right',
										items:[{
											html : "<font color=red >注：优惠券中体验券只能用在体验标中，返现券的使用规则参见招标计划</font>"
										}]
								}*/]
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
				var newTime = new Date();
				var time = newTime.format("Y-m-d");
				var startTime = this.getCmpByName("bpCouponSetting.couponStartDate").getValue();
				var endTime = this.getCmpByName("bpCouponSetting.couponEndDate").getValue();
				var eTime = endTime.format("Y-m-d");
				if(time!=eTime){
						if(endTime.getTime()<newTime.getTime()){
						Ext.Msg.alert('操作信息','结束日期必须大于等于当前时间');
						return;
					}
				}
				if(endTime.getTime()<startTime.getTime()){
					Ext.Msg.alert('操作信息','结束日期不能小于开始日期');
					return;
				}
				
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