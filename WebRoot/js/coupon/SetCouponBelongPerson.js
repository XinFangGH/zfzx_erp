//SetCouponBelongPerson.js
SetCouponBelongPerson = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				SetCouponBelongPerson.superclass.constructor.call(this, {
							id : 'SetCouponBelongPersonWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 200,
							width : 800,
							maximizable : true,
							title : '优惠券派发',
							buttonAlign : 'center',
							buttons : [{
											text : '确定',
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
										name : 'bpCoupons.couponId',
										value:this.couponId
								},{
										columnWidth : 1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : rightlabel,
										labelAlign :'right',
										items : [{
															xtype : 'textfield',
															fieldLabel : '投资人账户',
															name : 'p2pAccount',
															allowBlank : false,
															anchor : '100%',
															blankText : '投资人账户为必填内容',
															readOnly : this.isReadOnly
												}]
								},{
										columnWidth : 1,
										layout : "form", // 从上往下的布局
										labelWidth : leftlabel,
										labelAlign :'right',
										items:[{
											html : "<font color=red >注：投资人账户是指投资人的P2P登陆账号</font>"
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
				$postForm({
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/coupon/saveBpCouponSetting.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('BpCouponSettingGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});