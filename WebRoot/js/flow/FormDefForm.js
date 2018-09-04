/**
 * @description 表单选择器
 * @author YHZ
 * @createtime 2010-12-28AM
 * @class FormDefForm
 * @extends Ext.Window
 * @description FormDef表单
 * @company 智维软件
 */
FormDefForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				FormDefForm.superclass.constructor.call(this, {
					id : 'FormDefFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 500,
					width : 650,
					maximizable : true,
					title : '新增/编辑表单信息',
					iconCls : 'menu-form',
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
						}
					]
				});
			},// end of the constructor
			// 初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					// id : 'FormDefForm',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
						name : 'formDef.formDefId',
						xtype : 'hidden',
						value : this.formDefId == null ? '' : this.formDefId
					}, {
						name : 'defId',
						xtype : 'hidden',
						value : this.defId == null ? '' : this.defId
					}, {
						fieldLabel : '表单标题',
						name : 'formDef.formTitle',
						allowBlank : false,
						maxLength : 128
					}, {
						fieldLabel : '描述',
						name : 'formDef.formDesp',
						xtype : 'textarea',
						maxLength : 65535
					}, {
						fieldLabel : '定义HTML',
						name : 'formDef.defHtml',
						xtype : 'fckeditor',
						anchor : '96%',
						width : 490,
						height : 250,
						maxLength : 65535
					}, {
						anchor : '96%',
						fieldLabel : '状态',
						hiddenName : 'formDef.status',
						allowBlank : false,
						xtype : 'combo',
						store : [['0','草稿'],['1','正式']],
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						emptyText : '--请选择表单状态--'
					}]
				});
				// 加载表单对应的数据
				if (this.formDefId != null && this.formDefId != 'undefined') {
					this.formPanel.loadData({
						url : __ctxPath + '/arch/getFormDef.do?formDefId='
								+ this.formDefId,
						root : 'data',
						preName : 'formDef'
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
					url : __ctxPath + '/flow/saveFormDef.do',
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('FormDefGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
			}// end of save

		});