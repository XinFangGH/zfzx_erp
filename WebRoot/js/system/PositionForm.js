/**
 * @author 
 * @createtime 
 * @class PositionForm
 * @extends Ext.Window
 * @description Position表单
 * @company 智维软件
 */
PositionForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
//				if(!this.isShowSameLevel){
//					var vlp = Ext.getCmp('sameLevelPost')
//					vlp.hide();
//					vlp.fieldLabel = '';
//				}
				PositionForm.superclass.constructor.call(this, {
							id : 'PositionFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 360,
							width : 500,
							maximizable : true,
							title : '岗位详细信息',
							buttonAlign : 'center',
							keys : {
								key : Ext.EventObject.ENTER,
								fn : this.save,
								scope : this
							},
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
										name : 'position.posId',
										xtype : 'hidden',
										value : this.posId == null ? '' : this.posId
									}, {
										fieldLabel : '岗位名称',
										name : 'position.posName',
										allowBlank : false,
										maxLength : 128
									}, {
										fieldLabel : '岗位描述',
										name : 'position.posDesc',
										xtype : 'textarea',
										height:160,
										maxLength : 1024
									}, {
										fieldLabel : '已从属岗位',
										name : 'underByPos',
										maxLength : 1024,
										readOnly: true
									}, {
										xtype : 'compositefield',
										id:'sameLevelPost',
										fieldLabel : '同级岗位',
										items : [{
												xtype : 'textfield',
												readOnly : true,
												width : 270,
												name : 'sameLevelNames'
											},{
												xtype : 'button',
												text : '选择',
												scope : this,
												iconCls : 'btn-select',
												handler : function(){
													var fPanel = this.formPanel;
													new PositionDialog({
														single : false,
//														posKey : fPanel.getCmpByName('position.posId').value?fPanel.getCmpByName('position.posId').value:0,
														posSupId : fPanel.getCmpByName('position.posSupId').getValue(),
														posId: fPanel.getCmpByName('position.posId').getValue()==''?0:fPanel.getCmpByName('position.posId').getValue(),
														sameLevelIds: fPanel.getCmpByName('sameLevelIds').getValue(),
														sameLevelNames: fPanel.getCmpByName('sameLevelNames').getValue(),
														sameLevel : true,
														callback : function(posIds,posNames,positions){
															fPanel.getCmpByName('sameLevelNames').setValue(posNames);
															fPanel.getCmpByName('sameLevelIds').setValue(posIds);
														}
													}).show();
												}
											}]
									},{
										fieldLabel : '上级岗位',
										name : 'position.posSupId',
										value:this.posSupId?this.posSupId:0,
										xtype : 'hidden'
									},{
										name : 'sameLevelIds',
										xtype : 'hidden'
									}]
						});
				//加载表单对应的数据	
				if (this.posId != null && this.posId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/system/getPosition.do?posId=' + this.posId,
								root : 'data',
								preName : 'position'
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
							url : __ctxPath + '/system/savePosition.do',
							callback : function(fp, action) {
								if(this.callback){
									this.callback.call(this.scope);
									this.close();
									return;
								}
								var gridPanel = Ext.getCmp('PositionGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}
						});
			}//end of save

		});