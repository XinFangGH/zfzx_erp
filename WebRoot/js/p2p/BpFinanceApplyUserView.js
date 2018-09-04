/**
 * @author
 * @class BpFinanceApplyUserView
 * @extends Ext.Panel
 * @description [BpFinanceApplyUser]管理
 * @company 智维软件
 * @createtime:
 */
BpFinanceApplyUserView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				BpFinanceApplyUserView.superclass.constructor.call(this, {
							id : 'BpFinanceApplyUserView',
							title : '注册用户申请处理',
							region : 'center',
							layout : 'border',
							items : [this.tabpanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				var itemschange=[
									new BpFinanceApplyUserNotSubmitView({tabflag:'BpFinanceApplyUserNotSubmitView',type:this.type}),
								 	new BpFinanceApplyUserNotAcceptView({tabflag:'BpFinanceApplyUserNotAcceptView',type:this.type}),
								 	new BpFinanceApplyUserAcceptView({tabflag:'BpFinanceApplyUserAcceptView',type:this.type}),
								 	new BpFinanceApplyUserApproved({tabflag:'BpFinanceApplyUserApproved',type:this.type}),
								 	new BpFinanceApplyUserOfficialUser({tabflag:'BpFinanceApplyUserOfficialUser',type:this.type}),
								 	new BpFinanceApplyUserSupplementaryMaterials({tabflag:'BpFinanceApplyUserSupplementaryMaterials',type:this.type}),
								 	new BpFinanceApplyUserUploadMaterials({tabflag:'BpFinanceApplyUserSupplementaryMaterials',type:this.type}),
								 	new BpFinanceApplyUserPassed({tabflag:'BpFinanceApplyUserPassed',type:this.type}),
								 	new BpFinanceApplyUserBid({tabflag:'BpFinanceApplyUserBid',type:this.type}),
								 	new BpFinanceApplyUserRejected({tabflag:'BpFinanceApplyUserRejected',type:this.type}),
								 	new BpFinanceApplyUserFlowReject({tabflag:'BpFinanceApplyUserRejected',type:this.type})
								 	//new uploadReject({tabflag:'uploadReject',type:this.type})
								];
								this.tabpanel = new Ext.TabPanel({
									//resizeTabs : true, // turn on tab resizing
									minTabWidth : 150,
									//tabWidth : 200,
									//autoWidth :true,
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
			//重置查询表单
			reset : function(){
				this.searchPanel.getForm().reset();
			},
			//按条件搜索
			search : function() {
				$search({
					searchPanel:this.searchPanel,
					gridPanel:this.gridPanel
				});
			},
			//GridPanel行点击处理事件
			rowClick:function(grid,rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
					new BpFinanceApplyUserForm({loanId:rec.data.loanId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new BpFinanceApplyUserForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/p2p/multiDelBpFinanceApplyUser.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			updateSelRs : function(obj,e) {
				var  flashPanel=this.gridPanel;
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
						rowState='2';
					}else if(btest1=="转正式用户"){
						rowState='5';
					}else if(btest1=="驳回"){
						rowState='3';
					}else if(btest1=="通过审核"){
						rowState='4'
					}
					Ext.Ajax.request({
						url:__ctxPath + '/p2p/saveBpFinanceApplyUser.do',
						params:{'loanId':loanId,'state':rowState},
						success: function(resp,opts) {
							Ext.ux.Toast.msg('操作信息', '操作成功!');
							flashPanel.getStore().reload();
						}
					});
				}
			},
			//编辑Rs
			editRs : function(record) {
				new BpFinanceApplyUserForm({
					loanId : record.data.loanId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.loanId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
