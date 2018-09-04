/**
 * @author
 * @class BpFinanceApplyView
 * @extends Ext.Panel
 * @description [BpFinanceApply]管理
 * @company 智维软件
 * @createtime:
 */
BpComFinanceApplyView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpComFinanceApplyView.superclass.constructor.call(this, {
							id : 'BpComFinanceApplyView',
							title : '非注册企业申请审核',
							region : 'center',
							layout : 'border',
							items : [this.tabpanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel

				// 初始化搜索条件Panel

				// 初始化搜索条件Panel
				var itemschange=[
								 	new BpComApplyNotAccepted({tabflag:'BpComApplyNotAccepted',type:0}),
								 	new BpComApplyAccepted({tabflag:'BpComApplyAccepted',type:0}),
								 	new BpComApplyUserOfficialUser({tabflag:'BpComApplyUserOfficialUser',type:2}),
								 	new BpComApplyUserRejected({tabflag:'BpComApplyUserRejected',type:3})
								 	//new uploadReject({tabflag:'uploadReject',type:this.type})
								];
								this.tabpanel = new Ext.TabPanel({
									resizeTabs : true, // turn on tab resizing
									minTabWidth : 115,
									tabWidth : 135,
									enableTabScroll : true,
									Active : 0,
									width : 600,
									defaults : {
										autoScroll : true
									},
									region : 'center', 
									//layout:'fit'
									deferredRender : true,
									activeTab :0, // first tab initially active
									xtype : 'tabpanel',
										items : itemschange
									});
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
