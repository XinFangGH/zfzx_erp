/**
 * @author
 * @class CsHolidayView
 * @extends Ext.Panel
 * @description [CsHoliday]管理
 * @company 智维软件
 * @createtime:
 */
CsHolidayView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				CsHolidayView.superclass.constructor.call(this, {
							id : 'CsHolidayView',
							title : '公休节日管理',
							region : 'center',
							layout : 'border',
							iconCls:"menu-flowManager",
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new Ext.form.FormPanel({
					height : 50,
					region : "north",
					border : false,
					width : '100%',
					monitorValid : true,
					layout : 'column',
					defaults : {
						layout : 'form',
						border : false,
						bodyStyle : 'padding:5px 0px 0px 20px'
					},
					items : [{
						columnWidth : 0.2,
						labelWidth : 65,
						items : [{
							fieldLabel : '开始日期',
							name : 'Q_dateStr_D_EQ',
							xtype : 'datefield',
							format : 'Y-m-d'
						}]
					},{
						columnWidth : 0.2,
						labelWidth : 65,
						items : [{
							fieldLabel : '截止日期',
							name : 'Q_yearStr_D_EQ',
							xtype : 'datefield',
							format : 'Y-m-d'
						}]
					},{
						columnWidth : 0.2,
						labelWidth : 65,
						items : [{
							xtype : "dickeycombo",
							nodeKey : 'Holday',
							fieldLabel : '节假日类别',
							name :"dicId",
							anchor : "90%",
							hiddenName : 'dicId',
							valueField : 'companyId',
							triggerAction : 'all',
							listeners : {
								afterrender : function(combox) {
									var st = combox.getStore();
									st.on("load", function() {
										if (combox.getValue() == ""|| combox.getValue() == null) {
											combox.setValue(st.getAt(0).data.itemId);
										} else {
											combox.setValue(combox.getValue());
										}
										combox.clearInvalid();
									})
								}
							}
						}]
					},{
						columnWidth : 0.07,
						items : [{
							xtype : 'button',
							text : '查询',
							tooltip : '根据查询条件过滤',
							iconCls : 'btn-search',
							width : 60,
							formBind : true,
							scope : this,
							handler : this.search
						}]
					}, {
						columnWidth : 0.07,
						items : [{
							xtype : 'button',
							text : '重置',
							width : 60,
							scope : this,
							iconCls : 'btn-reset',
							handler : this.reset
						}]
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
					id : 'CsHolidayGrid',
					url : __ctxPath + "/system/listCsHoliday.do",
					fields : [{
								name : 'id',
								type : 'int'
							}, 'dateStr', 'yearStr', 'dicId','dicType'],
					columns : [{
								header : 'id',
								align:'center',
								dataIndex : 'id',
								hidden : true
							}, {
								header : '开始日期',
								align:'center',
								dataIndex : 'dateStr'
							}, {
								header : '截止日期',
								align:'center',
								dataIndex : 'yearStr'
							}, {
								header : '天数',
								align:'center',
								dataIndex : 'yearStr',
								renderer : function(value, metadata, record, rowIndex,colIndex){
									var d1=new Date(record.data.dateStr);
									var d2=new Date(record.data.yearStr);
									return ((d2-d1)/1000/60/60/24)+1
								}
							}, {
								header : '类别',
								align:'center',
								dataIndex : 'dicType'
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
							new CsHolidayForm({
										id : rec.data.id
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
				new CsHolidayForm().show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath + '/system/multiDelCsHoliday.do',
							ids : id,
							grid : this.gridPanel,
							errorMsg:'确定要删除吗?'
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath + '/system/multiDelCsHoliday.do',
							grid : this.gridPanel,
							idName : 'id'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new CsHolidayForm({
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
