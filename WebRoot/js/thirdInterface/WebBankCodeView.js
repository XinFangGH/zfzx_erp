/**
 * @author
 * @class WebBankCodeView
 * @extends Ext.Panel
 * @description [WebBankCode]管理
 * @company 智维软件
 * @createtime:
 */
WebBankCodeView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				WebBankCodeView.superclass.constructor.call(this, {
							id : 'WebBankCodeView',
							title : '[WebBankCode]管理',
							region : 'center',
							layout : 'border',
							items : [ this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new HT.SearchPanel({
							layout : 'form',
							region : 'north',
							colNums : 3,
							items : [{
										fieldLabel : 'bankName',
										name : 'Q_bankName_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'bankCode',
										name : 'Q_bankCode_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'bankLogo',
										name : 'Q_bankLogo_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}, {
										fieldLabel : 'thirdPayConfig',
										name : 'Q_thirdPayConfig_S_EQ',
										flex : 1,
										xtype : 'textfield'
									}],
							buttons : [{
										text : '查询',
										scope : this,
										iconCls : 'btn-search',
										handler : this.search
									}, {
										text : '重置',
										scope : this,
										iconCls : 'btn-reset',
										handler : this.reset
									}]
						});// end of searchPanel

				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-add',
										text : '添加',
										xtype : 'button',
										scope : this,
										handler : this.createRs
									}, {
										iconCls : 'btn-add',
										text : '编辑',
										xtype : 'button',
										scope : this,
										handler : this.ediTool
									},{
										iconCls : 'btn-del',
										text : '删除',
										xtype : 'button',
										scope : this,
										handler : this.removeSelRs
									}]
						});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					// 使用RowActions
					rowActions : true,
					id : 'WebBankCodeGrid',
					url : __ctxPath + "/thirdInterface/listWebBankCode.do",
					fields : [{
								name : 'id',
								type : 'int'
							}, 'bankName', 'bankCode', 'bankLogo','imgURL',
							'thirdPayConfig','orderNum'],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true
							}, {
								header : '银行名称',
								dataIndex : 'bankName',
								width:80
							}, 
							{
								header : '银行编码',
								dataIndex : 'bankCode',
								align : 'center',
								width : 50
									 
							},
							{
								header : '银行logo路径',
								dataIndex : 'bankLogo',
								width:250
							},{
								header : '银行logo',
								width:60,
								dataIndex : 'imgURL',renderer:function(v){
//									 return "<img  src='http://localhost:8001/hurong_p2p_proj_bj_qszw/attachFiles/bankLogo/201407/2e76fcdccc904d11b98eb8058b8d3936.jpg'  width='50px' height='40px'/>";
									 return "<img  src='"+v+"'  width='50px' height='40px'/>";
								}
							}, {
								header : '排序',
								dataIndex : 'orderNum',
								width:20
							},
//							{
//								header : '是否显示',
//								dataIndex : 'thirdPayConfig',
//								renderer : function(v) {
//                                    if(v==0){return "显示"}
//                                    if(v==1){return "不显示"}
//								}
//							}, 
							
							new Ext.ux.grid.RowActions({
										header : '管理',
										width : 100,
										hidden:true,
										actions : [{
													iconCls : 'btn-del',
													qtip : '删除',
													style : 'margin:0 3px 0 3px'
												}, {
													iconCls : 'btn-edit',
													qtip : '编辑',
													style : 'margin:0 3px 0 3px'
												}],
										listeners : {
											scope : this,
											'action' : this.onRowAction
										}
									})]
						// end of columns
				});

				this.gridPanel.addListener('rowdblclick', this.rowClick);

			},// end of the initComponents()
			// 重置查询表单
			reset : function() {
				this.searchPanel.getForm().reset();
			},
			// 按条件搜索
			search : function() {
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			},
			// GridPanel行点击处理事件
			rowClick : function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							new WebBankCodeForm({
										id : rec.data.id
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
				new WebBankCodeForm({
					selectId:""
				}).show();
			},
			//工具栏中的编辑
			ediTool:function(){
				var grid = Ext.getCmp('WebBankCodeGrid'); 
				var selections = grid.getSelectionModel().getSelections();
				var len = selections.length;
				 
				if (len > 1) {
					Ext.ux.Toast.msg('状态', '只能选择一条记录');
					return;
				} else if (0 == len) {
					Ext.ux.Toast.msg('状态', '请选择一条记录');
					return;
				}
				var id = selections[0].data.id;
				new WebBankCodeForm({
					id : id,
					isAllReadOnly:false,
					selectId:id
					 
				}).show();
			}
			
			// 按ID删除记录
			,removeRs : function(id) {
				$postDel({
							url : __ctxPath
									+ '/thirdInterface/multiDelWebBankCode.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath
									+ '/thirdInterface/multiDelWebBankCode.do',
							grid : this.gridPanel,
							idName : 'id'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new WebBankCodeForm({
							id : record.data.id
						}).show();
			}
			
			,
			// 行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this, record.data.id);
						break;
					case 'btn-edit' :
						this.editRs.call(this, record);
						break;
					default :
						break;
				}
			}
		});
