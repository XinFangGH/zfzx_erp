/**
 * @author 
 * @createtime 
 * @class DemensionForm
 * @extends Ext.Window
 * @description Demension表单
 * @company 智维软件
 */
DemensionForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				DemensionForm.superclass.constructor.call(this, {
							id : 'DemensionFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '维度详细信息',
							buttonAlign : 'center',
							buttons : [{
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
										handler : this.close
									}]
						});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll : true,
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
										name : 'demension.demId',
										xtype : 'hidden',
										value : this.demId == null ? '' : this.demId
									}, {
										fieldLabel : '维度名称',
										name : 'demension.demName',
										allowBlank : false,
										maxLength : 128
									}, {
										fieldLabel : '维度描述',
										name : 'demension.demDesc',
										xtype : 'textarea',
										maxLength : 1024,
										allowBlank : false
									}, {
										fieldLabel : '维度类型',
										hiddenName : 'demension.demType',
										allowBlank : false,
										editable : false,
										triggerAction: 'all',
										xtype : 'combo',
										store:[['1','行政'],['2','其他']]
									}
									]
						});
				this.formPanel.getCmpByName('demension.demType').setValue(1);
				//加载表单对应的数据	
				if (this.demId != null && this.demId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/system/getDemension.do?demId=' + this.demId,
								root : 'data',
								preName : 'demension'
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
			 * 保存记录
			 */
			save : function() {
				$postForm({
							formPanel : this.formPanel,
							scope : this,
							url : __ctxPath + '/system/saveDemension.do',
							callback : function(fp, action) {
								if(this.callback!=null){
									this.callback.call(this.scope);
								}
								var gridPanel = Ext.getCmp('DemensionGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}
						});
			}//end of save

		});