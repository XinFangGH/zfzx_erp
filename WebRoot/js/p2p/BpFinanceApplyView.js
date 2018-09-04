/**
 * @author
 * @class BpFinanceApplyView
 * @extends Ext.Panel
 * @description [BpFinanceApply]管理
 * @company 智维软件
 * @createtime:
 */
BpFinanceApplyView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpFinanceApplyView.superclass.constructor.call(this, {
							id : 'BpFinanceApplyView',
							title : '融资申请管理',
							region : 'center',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new HT.SearchPanel({
							layout : 'column',
							region : 'north',
							
							items : [{
								columnWidth : .2,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								border :false,
								items : [{
										fieldLabel : '联系人',
										name : 'Q_linkPersion_S_LK',
										flex : 1,
										anchor : '100%',
										xtype : 'textfield'
									}]
							}, {
								columnWidth : .2,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								border :false,
								items : [{
										fieldLabel : '手机',
										name : 'Q_phone_S_LK',
										flex : 1,
										anchor : '100%',
										xtype : 'textfield'
									}]
							}, {
								columnWidth : .2,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								border :false,
								items : [{
										fieldLabel : '融资金额',
										name : 'Q_loanMoney_BD_EQ',
										flex : 1,
										anchor : '100%',
										xtype : 'numberfield'
									}]
							}, {
								columnWidth : .2,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								border :false,
								items : [{
										fieldLabel : '地区',
										name : 'Q_area_S_LK',
										flex : 1,
										anchor : '100%',
										xtype : 'textfield'
									}]
							}, {
								columnWidth : .07,
								layout : 'form',
								border : false,
								items : [{
										xtype : 'button',
										text : '查询',
										scope : this,
										iconCls : 'btn-search',
										handler : this.search
									}]
							},{
								columnWidth : .07,
								layout : 'form',
								border : false,
								items : [{
										xtype : 'button',
										text : '重置',
										scope : this,
										iconCls : 'btn-reset',
										handler : this.reset
									}]
							}]
						});// end of searchPanel

				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-add',
										text : '添加[BpFinanceApply]',
										xtype : 'button',
										scope : this,
										handler : this.createRs
									}/*, {
										iconCls : 'btn-del',
										text : '删除[BpFinanceApply]',
										xtype : 'button',
										scope : this,
										handler : this.removeSelRs
									}*/]
						});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					//tbar : this.topbar,
					// 使用RowActions
					rowActions : true,
					id : 'BpFinanceApplyGrid',
					url : __ctxPath + "/p2p/listBpFinanceApply.do",
					fields : [{
								name : 'loanId',
								type : 'int'
							}, 'productId', 'linkPersion', 'phone',
							'loanMoney', 'isOnline', 'loanTimeLen', 'area',
							'remark', 'createTime', 'state','productName'],
					columns : [{
								header : 'loanId',
								dataIndex : 'loanId',
								hidden : true
							}, {
								header : '产品名称',
								dataIndex : 'productName'
							}, {
								header : '联系人',
								dataIndex : 'linkPersion'
							}, {
								header : '手机',
								dataIndex : 'phone'
							}, {
								header : '融资金额',
								dataIndex : 'loanMoney'
							}, {
								header : '客户类型',
								dataIndex : 'isOnline',
								renderer : function(v) {
                                    if(v==0){
									return '线上客户';
                                    }else{
                                    	return '线下客户';
                                    }

								}
							}, {
								header : '融资期限',
								dataIndex : 'loanTimeLen'
							}, {
								header : '地区',
								dataIndex : 'area'
							}, {
								header : '申请时间',
								dataIndex : 'createTime'
							}, {
								header : 'state',
								dataIndex : 'state',
								renderer:function(v){
                                    if(v==0){
									return '新申请';
                                    }else{
                                    	return '已审核';
                                    }
								}
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
							new BpFinanceApplyForm({
										loanId : rec.data.loanId
									}).show();
						});
			},
			// 创建记录
			createRs : function() {
				new BpFinanceApplyForm().show();
			},
			// 按ID删除记录
			removeRs : function(id) {
				$postDel({
							url : __ctxPath + '/p2p/multiDelBpFinanceApply.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			// 把选中ID删除
			removeSelRs : function() {
				$delGridRs({
							url : __ctxPath + '/p2p/multiDelBpFinanceApply.do',
							grid : this.gridPanel,
							idName : 'loanId'
						});
			},
			// 编辑Rs
			editRs : function(record) {
				new BpFinanceApplyForm({
							loanId : record.data.loanId
						}).show();
			},
			// 行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this, record.data.loanId);
						break;
					case 'btn-edit' :
						this.editRs.call(this, record);
						break;
					default :
						break;
				}
			}
		});
