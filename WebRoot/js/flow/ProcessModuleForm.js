/**
 * @author
 * @createtime
 * @class ProcessModuleForm
 * @extends Ext.Window
 * @description ProcessModule表单
 * @company 智维软件
 */
ProcessModuleForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				ProcessModuleForm.superclass.constructor.call(this, {
							id : 'ProcessModuleFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 240,
							width : 500,
							maximizable : true,
							title : '流程模块详细信息',
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
										handler : this.cancel
									}]
						});
			},// end of the constructor
			// 初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll : true,
							// id : 'ProcessModuleForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'processModule.moduleid',
								xtype : 'hidden',
								value : this.moduleid == null
										? ''
										: this.moduleid
							}, {
								fieldLabel : '模块名称',
								name : 'processModule.modulename',
								allowBlank : false,
								maxLength : 256
							}, {
								fieldLabel : '模块key',
								name : 'processModule.modulekey',
								allowBlank : false,
								maxLength : 128,
								editable : true,
								lazyInit : false,
								forceSelection : false,
								xtype : 'diccombo',
								itemName : '流程模块KEY'
							}, {
								fieldLabel : '描述',
								name : 'processModule.descp',
								xtype : 'textarea',
								maxLength : 4000
							}, {
								xtype : 'compositefield',
								fieldLabel : '绑定流程',
								items : [
									{
										xtype : 'textfield',
										name : 'proDefinition.processName',
										readOnly : true,
										width : '200'
									},{
										xtype : 'button',
										iconCls:'menu-flow',
										text : '选择流程',
										handler : this.selectFlow.createCallback(this)
									}
								]
							},{
								fieldLabel : 'defId',
								name : 'processModule.defId',
								xtype : 'hidden'
							}, {
								fieldLabel : '流程key',
								xtype : 'hidden',
								name : 'processModule.processkey',
								maxLength : 256
							}, {
								fieldLabel : '创建人',
								xtype : 'hidden',
								name : 'processModule.creator',
								value : curUserInfo.fullname,
								maxLength : 64
							}]
						});
				// 加载表单对应的数据
				if (this.moduleid != null && this.moduleid != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath
										+ '/flow/getProcessModule.do?moduleid='
										+ this.moduleid,
								root : 'data',
								preName : 'processModule'
							});
				}

			},// end of the initcomponents

			/**
			 * 重置
			 * 
			 * @param {}
			 *            formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * 
			 * @param {}
			 *            window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
				$postForm({
							formPanel : this.formPanel,
							scope : this,
							url : __ctxPath + '/flow/saveProcessModule.do',
							callback : function(fp, action) {
								var gridPanel = Ext.getCmp('ProcessModuleGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}
						});
			},// end of save
			/**
			 * 选择流程
			 */
			selectFlow : function(fPanel){
				FlowSelector.getView(
					function(id, name ,processName) {
						fPanel.getCmpByName('processModule.defId').setValue(id);
						fPanel.getCmpByName('proDefinition.processName').setValue(name);
						fPanel.getCmpByName('processModule.processkey').setValue(processName);
					}
				, true).show();//end of selector
			}
		});