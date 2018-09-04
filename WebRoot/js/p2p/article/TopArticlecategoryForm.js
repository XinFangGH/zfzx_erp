/**
 * @author
 * @createtime
 * @class TopArticlecategoryForm
 * @extends Ext.Window
 * @company 智维软件
 */
TopArticlecategoryForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				TopArticlecategoryForm.superclass.constructor.call(this, {
							id : 'TopArticlecategoryFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '分类类别',
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
							// id : 'TopArticlecategoryForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
										name : 'articlecategory.id',
										xtype : 'hidden',
										value : this.id == null ? '' : this.id
									},{
								xtype : "hidden",
								anchor : "96%",
								name : "articlecategory.parentId",
								value:0
							}, {
								fieldLabel : '类别名称',
								name : 'articlecategory.name',
								allowBlank : false,
								maxLength : 255
							}, {
								fieldLabel : '类别key值',
								name : 'articlecategory.cateKey',
								allowBlank : false,
								readOnly:this.isEdit
							}, {
								hiddenName : 'articlecategory.webKey',
								xtype : 'combo',
								fieldLabel : '站点类型',
								allowBlank : false,
								editable : false,
								mode : 'local',
								triggerAction : 'all',
								store : [['P2P', '互联网金融'],['YunGou', '云购'],['Crowdfunding', '众筹']],
								value : 'P2P',
								readOnly:this.isEdit
							},{
								fieldLabel : '关键字',
								name : 'articlecategory.metaKeywords',
								xtype : 'textarea',
								maxLength : 65535
							}, {
								fieldLabel : '描述',
								name : 'articlecategory.metaDescription',
								xtype : 'textarea',
								maxLength : 65535
							}]
						});
				// 加载表单对应的数据
				if (this.id != null && this.id != 'undefined') {
					
					this.formPanel.loadData({
								url : __ctxPath
										+ '/p2p/getArticlecategory.do?id='
										+ this.id,
								root : 'data',
								preName : 'articlecategory'
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
							url : __ctxPath+ '/p2p/saveTopArticlecategory.do',
							callback : function(fp, action) {
								var gridPanel = Ext.getCmp('TopArticlecategoryGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}
						});
			}// end of save

		});