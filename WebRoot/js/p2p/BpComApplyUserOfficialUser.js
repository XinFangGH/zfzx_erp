/**
 * @author
 * @class BpFinanceApplyView
 * @extends Ext.Panel
 * @description [BpFinanceApply]管理
 * @company 智维软件
 * @createtime:
 */
BpComApplyUserOfficialUser = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpComApplyUserOfficialUser.superclass.constructor.call(this, {
							id : 'BpComApplyUserOfficialUser',
							title : '正式用户',
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
								border : false,
								items : [{
									fieldLabel : '联系人',
									name : 'Q_linkPersion_S_LK',
									flex : 1,
									xtype : 'textfield',
									anchor : '100%'
								}]
							}, {
								columnWidth : .2,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								border : false,
								items : [{
									fieldLabel : '手机',
									name : 'Q_phone_S_LK',
									flex : 1,
									xtype : 'textfield',
									anchor : '100%'
								}]
							}, {
								columnWidth : .2,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								border : false,
								items : [{
									fieldLabel : '融资金额',
									name : 'Q_loanMoney_BD_EQ',
									flex : 1,
									xtype : 'numberfield',
									anchor : '100%'
								}]
							}, {
								columnWidth : .2,
								layout : 'form',
								labelWidth : 60,
								labelAlign : 'right',
								border : false,
								items : [{
									fieldLabel : '地区',
									name : 'Q_area_S_LK',
									flex : 1,
									xtype : 'textfield',
									anchor : '100%'
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
							items : [/*{
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
									},*/
									new Ext.Toolbar.Separator({
										hidden : isGranted('_create_grkh') ? false : true
									}), {
										iconCls:'btn-readdocument',
										text:'查看',
										xtype:'button',
										scope:this,
										handler : function() {
										var rows = this.gridPanel.getSelectionModel().getSelections();
										if (rows.length == 0) {
											Ext.ux.Toast.msg('操作信息', '请选择记录!');
											return;
										} else if (rows.length > 1) {
											Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
											return;
										} else {
											new BpFinanceApplyForm({
												loanId : rows[0].data.loanId,
												applyCom:"1"
											}).show();
											
										}}
									}/*,{
										iconCls : 'btn-save',
										text : '受理',
										xtype : 'button',
										scope : this,
										handler : this.updateSelRs
									}, {
										iconCls : 'btn-reset',
										text : '转正式用户',
										xtype : 'button',
										scope:this,
										handler : this.updateSelRs
									},{
										iconCls : 'btn-del',
										text : '拒绝',
										xtype : 'button',
										scope:this,
										handler : this.updateSelRs
									}*/
								]
						});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					// 使用RowActions
					rowActions : true,
					id : 'BpComFinanceApplyGrid',
					url : __ctxPath + "/p2p/listBpFinanceApply.do?ck=1&state=3",
					fields : [{
								name : 'loanId',
								type : 'int'
							}, 'productId', 'linkPersion', 'phone',
							'loanMoney', 'isOnline', 'loanTimeLen', 'area',
							'remark', 'createTime', 'state','productName','businessName'],
					columns : [{
								header : 'loanId',
								dataIndex : 'loanId',
								hidden : true
							},/* {
								header : '产品名称',
								dataIndex : 'productName'
							},*/
							{
								header : '借款企业',
								dataIndex : 'businessName'
							},
							 {
								header : '状态',
								dataIndex : 'state',
								renderer:function(v){
                                    if(v==0){
									return '新申请';
                                    }else if(v==1){
                                    	return '已受理';
                                    }else if(v==2){
                                    	return '已拒绝';
                                    }else if(v==3){
                                    	return '已转为正式客户';
                                    }
								}
							},{
								header : '联系人',
								dataIndex : 'linkPersion'
							}, {
								header : '申请时间',
								dataIndex : 'createTime'
							}, {
								header : '地区',
								dataIndex : 'area'
							}, {
								header : '手机',
								dataIndex : 'phone'
							}, {
								header : '融资金额（万元）',
								dataIndex : 'loanMoney',
								align:'right'
							}/*, {
								header : '客户类型',
								dataIndex : 'isOnline',
								renderer : function(v) {
                                    if(v==0){
									return '线上客户';
                                    }else{
                                    	return '线下客户';
                                    }

								}
							}*/, {
								header : '融资期限（月）',
								dataIndex : 'loanTimeLen'
							}, new Ext.ux.grid.RowActions({
										header : '',
										width : 0,
										actions : [/*{
													iconCls : 'btn-del',
													qtip : '删除',
													style : 'margin:0 3px 0 3px'
												}, {
													iconCls : 'btn-edit',
													qtip : '编辑',
													style : 'margin:0 3px 0 3px'
												}*/],
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
			//把选中ID删除
			updateSelRs : function(obj,e) {
				var flashPanel=this.gridPanel;
				var rows = this.gridPanel.getSelectionModel().getSelections();
				if (rows.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择记录!');
					return;
				} else if (rows.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
					var loanId=rows[0].data.loanId;
					var btest1=obj.text;
					var rowState;
					if(btest1=="受理"){
						rowState='1';
					}else if(btest1=="转正式用户"){
						rowState='3';
					}else if(btest1=="拒绝"){
						rowState='2';
					}
					Ext.Ajax.request({
						url:__ctxPath + '/p2p/updateBpFinanceApply.do',
						params:{'loanId':loanId,'state':rowState},
						success: function(resp,opts) {
							Ext.ux.Toast.msg('操作信息', '操作成功!');
							flashPanel.getStore().reload();
						}
					});
				}
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
