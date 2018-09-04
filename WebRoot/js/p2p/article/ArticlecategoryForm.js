/**
 * @author
 * @createtime
 * @class ArticlecategoryForm
 * @extends Ext.Window
 * @description Articlecategory表单
 * @company 智维软件
 */
ArticlecategoryForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				ArticlecategoryForm.superclass.constructor.call(this, {
							id : 'ArticlecategoryFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '顶级类别',
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
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
										name : 'articlecategory.id',
										xtype : 'hidden',
										value : this.id == null ? '' : this.id
									},{
								xtype : "combo",
								anchor : "96%",
								hiddenName : "articlecategory.parentId",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								disable : true,
								store : new Ext.data.SimpleStore({
									autoLoad : true,
									url : __ctxPath
											+ '/p2p/getListArticlecategory.do',
									fields : ['itemId', 'itemName','itemKey']
								}),
								fieldLabel : "上级类别",
								id:'ccc',
								readOnly:true,
								blankText : "上级类别不能为空，请正确填写!",
								listeners : {
									scope:this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
													var record = st.getAt(0);
													var v = Ext.getCmp('ccc').getValue();
													combox.setValue(v);
													combox.fireEvent("select",
															combox, record, 0);
												})
										combox.clearInvalid();
									}
								}
							}, {
								fieldLabel : '类别名称',
								name : 'articlecategory.name',
								allowBlank : false,
								maxLength : 255
							}, {
								fieldLabel : '类别key值',
								name : 'articlecategory.cateKey',
								allowBlank : false,
								readOnly:this.isEdit,
								xtype : 'textfield'
							}, {
								hiddenName : 'articlecategory.type',
								xtype : 'combo',
								fieldLabel : '所属类型',
								allowBlank : false,
								editable : false,
								mode : 'local',
								triggerAction : 'all',
								store : [['1', '新闻/公告类'],['0', '单页面']],
								value : 0
							}, {
								hiddenName : 'articlecategory.isShow',
								xtype : 'combo',
								fieldLabel : '是否展示',
								allowBlank : false,
								editable : false,
								mode : 'local',
								triggerAction : 'all',
								store : [['1', '不展示'],['0', '展示']],
								value : 0
							}, {
								fieldLabel : '排序',
								name : 'articlecategory.orderList',
								allowBlank : false,
								xtype : 'numberfield'
							}, {
								fieldLabel : '页面关键字',
								name : 'articlecategory.metaKeywords',
								xtype : 'textarea',
								maxLength : 65535
							}, {
								fieldLabel : '页面描述',
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
							url : __ctxPath
									+ '/p2p/saveArticlecategory.do',
							callback : function(fp, action) {
								var gridPanel = Ext
										.getCmp('ArticlecategoryGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}
						});
			}// end of save

		});