/**
 * @author:hcy
 * @class OutMailSetView
 * @extends Ext.Panel
 * @description [OutMailSetView]管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-12-26
 */
OutMailSetView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				OutMailSetView.superclass.constructor.call(this, {
					id : 'OutMailSetView',
					title : '外部邮箱配置',
					iconCls : 'menu-mail_send',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
					});
			},// end of constructor

			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new Ext.FormPanel({
							id : 'OutMailSetSearchForm',
							region : 'north',
							height : 40,
							frame : false,
							border : false,
							layout : 'hbox',
							layoutConfig : {
								padding : '5',
								align : 'middle'
							},
							defaults : {
								xtype : 'label',
								margins : {
									top : 0,
									right : 4,
									bottom : 4,
									left : 4
								}
							},
							keys : {
								key : Ext.EventObject.ENTER,
								fn : this.search.createCallback(this),
								scope : this
							},
							items : [{
										text : '查询条件:'
									}, {
										text : '用户名称'
									}, {
										anchor : '40%',
										xtype : 'textfield',
										name : 'userName'
									}, {
										xtype : 'button',
										text : '查询',
										iconCls : 'search',
										handler : this.search
												.createCallback(this)
									}, {
										xtype : 'button',
										text : '重置',
										iconCls : 'reset',
										handler : this.clear,
										scope : this
									}]
						});// end of the searchPanel

				// 加载数据至store
				this.store = new Ext.data.JsonStore({
							url : __ctxPath + '/system/listOutMailSet.do',
							totalProperty : 'totalCounts',
							root : 'result',
							baseParams : {
								start : 0,
								limit : 25
							},
							remoteSort : true,
							fields : [{
										name : 'id',
										type : 'int'
									}, {
										name : 'reuserId',
										mapping : 'userId',
										type : 'int'
									}, 'userName', 'mailAddress', 'mailPass',
									'smtpHost', 'smtpPort', 'popHost',
									'popPort']
						});
				this.store.setDefaultSort('id', 'desc');
				// 加载数据
				this.store.load({
							params : {
								start : 0,
								limit : 25
							}
						});

				// 初始化ColumnModel
				var sm = new Ext.grid.CheckboxSelectionModel();
				var cm = new Ext.grid.ColumnModel({
							columns : [sm, new Ext.grid.RowNumberer(), {
										header : 'id',
										dataIndex : 'id',
										hidden : true
									}, {
										header : 'userId',
										dataIndex : 'reuserId',
										hidden : true
									}, {
										header : '用户名称',
										dataIndex : 'userName'
									}, {
										header : '外部邮件地址',
										dataIndex : 'mailAddress',
										vtype : 'email',
										editor : new Ext.form.TextField({
													allowBlank : false,
													vtype : 'email'
												})
									}, {
										header : '密码',
										dataIndex : 'mailPass',
										renderer : function(value) {
											var length = value.length;
											var str = '';
											for (var i = 0; i < length; i++) {
												str += '*';
											}
											return str;
										},
										editor : new Ext.form.TextField({
													allowBlank : false,
													inputType : 'password'
												})
									}, {
										header : 'smtp主机',
										dataIndex : 'smtpHost',
										editor : new Ext.form.TextField({
													allowBlank : false
												})
									}, {
										header : 'smtp端口',
										dataIndex : 'smtpPort',
										vtype : 'alphanum',
										editor : new Ext.form.NumberField({
													allowBlank : false,
													vtype : 'alphanum',
													blankText : '端口必须是填写数字'
												})
									}, {
										header : 'pop主机',
										dataIndex : 'popHost',
										editor : new Ext.form.TextField({
													allowBlank : false
												})
									}, {
										header : 'pop端口',
										dataIndex : 'popPort',
										vtype : 'alphanum',
										blankText : '端口必须是填写数字',
										editor : new Ext.form.NumberField({
													allowBlank : false,
													vtype : 'alphanum',
													blankText : '端口必须是填写数字'
												})
									}],
							defaults : {
								sortable : true,
								menuDisabled : true,
								width : 100
							}
						});
				// 初始化工具栏
				this.topbar = new Ext.Toolbar({
							height : 30,
							bodyStyle : 'text-align:left',
							items : []
						});
				if (isGranted('_OutMailSetAdd')) {
					this.topbar.add(new Ext.Button({
								text : '保存外部邮箱配置',
								iconCls : 'btn-save',
								handler : this.saveRecord,
								scope : this
							}));
				}

				this.gridPanel = new Ext.grid.EditorGridPanel({
							id : 'OutMailSetGrid',
							tbar : this.topbar,
							clicksToEdit : 1,
							region : 'center',
							stripeRows : true,
							prunemodifiedrecords : true,
							store : this.store,
							trackMouseOver : true,
							disableSelection : false,
							loadMask : true,
							cm : cm,
							sm : sm,
							viewConfig : {
								forceFit : true,
								autoFill : true, // 自动填充
								forceFit : true
							},
							bbar : new Ext.PagingToolbar({
										pageSize : 25,
										store : this.store,
										displayInfo : true,
										displayMsg : '当前页记录索引{0}-{1}， 共{2}条记录',
										emptyMsg : "当前没有记录"
									})
						});

			},// end of the initComponents()

			/**
			 * 
			 * @param {}
			 *            self 当前窗体对象
			 */
			search : function(self) {
				var searchPanel = Ext.getCmp('OutMailSetSearchForm');
				if (searchPanel.getForm().isValid()) {// 如果合法
					var grid = Ext.getCmp('OutMailSetGrid');
					var store = grid.getStore();
					var baseParam = Ext.Ajax.serializeForm(searchPanel
							.getForm().getEl());
					var deParams = Ext.urlDecode(baseParam);
					deParams.start = 0;
					deParams.limit = store.baseParams.limit;
					store.baseParams = deParams;
					grid.getBottomToolbar().moveFirst();
				}
			},

			/**
			 * 清空
			 */
			clear : function() {
				Ext.getCmp('OutMailSetSearchForm').getForm().reset();
			},
			/**
			 * 添加记录
			 */
			saveRecord : function() {
				var params = [];
				var grid = Ext.getCmp('OutMailSetGrid');
				var store = grid.getStore();
				for (i = 0, cnt = store.getCount(); i < cnt; i += 1) {
					var record = store.getAt(i);
					if (record.data.id == '' || record.data.id == null) {// 设置未保存的id标记，方便服务端进行gson转化
						record.set('id', -1);
					}

					if (record.dirty) {
						params.push(record.data);
					}
				}
				if (params.length == 0) {
					Ext.ux.Toast.msg('信息', '没有对数据进行任何更改');
					return;
				}

				Ext.Ajax.request({
							method : 'post',
							url : __ctxPath + '/system/saveOutMailSet.do',
							success : function(request) {
								Ext.ux.Toast.msg('操作信息', '成功设置外部邮箱！');
								store.reload();
								grid.getView().refresh();
							},
							failure : function(request) {
								Ext.MessageBox.show({
											title : '操作信息',
											msg : '信息保存出错，请联系管理员！',
											buttons : Ext.MessageBox.OK,
											icon : 'ext-mb-error'
										});
							},
							params : {
								data : Ext.encode(params)
							}
						});
			}
		});
