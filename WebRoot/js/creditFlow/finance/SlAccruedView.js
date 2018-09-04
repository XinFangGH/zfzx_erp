/**
 * @author
 * @class SlAccruedView
 * @extends Ext.Panel
 * @description [SlAccrued]管理
 * @company 智维软件
 * @createtime:
 */
SlAccruedView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				SlAccruedView.superclass.constructor.call(this, {
							id : 'SlAccruedView',
							title : '罚息',
							region : 'center',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				

				
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					//使用RowActions
					rowActions:false,
					id:'SlAccruedGrid',
					url : __ctxPath + "/creditFlow.finance/listSlAccrued.do",
					fields : [{
									name : 'accruedId',
									type : 'int'
								}
																																																	,'projectId'
																																										,'businessType'
																																										,'factDate'
																																										,'accruedDate'
																																										,'accruedMoney'
																																			],
					columns:[
								{
									header : 'accruedId',
									dataIndex : 'accruedId',
									hidden : true
								}
																																																								,{
																	header : 'projectId',	
																	dataIndex : 'projectId'
								}
																																																,{
																	header : 'businessType',	
																	dataIndex : 'businessType'
								}
																																																,{
																	header : 'factDate',	
																	dataIndex : 'factDate'
								}
																																																,{
																	header : 'accruedDate',	
																	dataIndex : 'accruedDate'
								}
																																																,{
																	header : 'accruedMoney',	
																	dataIndex : 'accruedMoney'
								}
					]//end of columns
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
					new SlAccruedForm({accruedId:rec.data.accruedId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new SlAccruedForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/creditFlow.finance/multiDelSlAccrued.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/creditFlow.finance/multiDelSlAccrued.do',
					grid:this.gridPanel,
					idName:'accruedId'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new SlAccruedForm({
					accruedId : record.data.accruedId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.accruedId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
