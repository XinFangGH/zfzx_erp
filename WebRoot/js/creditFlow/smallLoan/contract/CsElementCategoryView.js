/**
 * @author
 * @class CsElementCategoryView
 * @extends Ext.Panel
 * @description [CsElementCategory]管理
 * @company 智维软件
 * @createtime:
 */
CsElementCategoryView = Ext.extend(Ext.Panel, {
			businessType:"SmallLoan",//默认小贷，可以通过传值改变businessType
			// 构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg)!="undefined"){
					if(typeof(_cfg.businessType)!="undefined"){
						this.businessType = _cfg.businessType;
					}
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				CsElementCategoryView.superclass.constructor.call(this, {
							id : 'CsElementCategoryView',
							title : '合同要素管理',
							region : 'center',
							layout : 'border',
							iconCls :'btn-tree-team2',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
					// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			layout : 'column',
			region : 'north',
			border : false,
			height : 40,
			anchor : '100%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
			items : [{
				columnWidth : 0.8,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '要素编码',
					anchor : '100%',
					name : 'Q_elementCode_S_LK'
				}]
			},{
					xtype : 'hidden',
					name : 'Q_businessType_S_EQ',
					value:this.businessType
			},{
			   	  columnWidth:0.001,
			      border:false
			     },{
				columnWidth : 0.1,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '查询',
					//width : 60,
					anchor:'100%',
					scope : this,
					style:'margin-left:22',
					iconCls : 'btn-search',
					handler : this.search
				} ]
			} , {
				columnWidth : 0.1,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '重置',
					//width : 60,
					anchor:'100%',
					scope : this,
					style:'margin-left:23',
					iconCls : 'btn-reset',
					handler : this.reset
				} ]
			} ]

		})
				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-add',
										text : '添加',
										xtype : 'button',
										scope : this,
										handler : this.createRs
									}, {
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
					id : 'CsElementCategoryGrid',
					url : __ctxPath
							+ "/contract/listCsElementCategory.do?Q_businessType_S_EQ="+this.businessType,
					fields : [{
								name : 'id',
								type : 'int'
							}, 'elementCode', 'description', 'method',
							'businessType'],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true
							}, {
								header : '要素编码',
								dataIndex : 'elementCode'
							}, {
								header : '要素描述',
								dataIndex : 'description'
							}, {
								header : '方法',
								dataIndex : 'method'
							}, {
								header : '业务类别',
								dataIndex : 'businessType',
								hidden:true
							}, new Ext.ux.grid.RowActions({
										header : '管理',
										width : 100,
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
							new CsElementCategoryForm({
										id : rec.data.id
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
				new CsElementCategoryForm({businessType:this.businessType}).show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath
									+ '/contract/multiDelCsElementCategory.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath
									+ '/contract/multiDelCsElementCategory.do',
							grid : this.gridPanel,
							idName : 'id'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new CsElementCategoryForm({
							id : record.data.id
						}).show();
			},
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
