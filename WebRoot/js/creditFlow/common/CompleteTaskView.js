/**
 * @author lisl
 * @extends Ext.Panel
 * @description 已完成任务
 * @company 智维软件
 * @createtime:
 */
//var userComProcessName="smallLoanFlow,smallLoanFast,FinancingFlow,guaranteeNormalFlow";
CompleteTaskView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				CompleteTaskView.superclass.constructor.call(this, {
							id : 'CompleteTaskView',
							iconCls:'btn-flowEnd',
							title : '办结任务',
							region : 'center',
							layout : 'border',
							iconCls : 'btn-tree-team2',
							items : [this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {

				this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-advice',
									text : '意见与说明记录',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_comFlowRecords')?false:true,
									handler:this.completeFlowRecords
								},new Ext.Toolbar.Separator({
									hidden : isGranted('_comShowFlowImg')?false:true
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_comShowFlowImg')?false:true,
									handler : this.showFlowImg
								}]
				});
				
				var myMask = new Ext.LoadMask(Ext.getBody(), {
					msg : "正在加载数据中······,请稍候······",
					removeMask : true// 完成后移除
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					loadMask : true,
					//使用RowActions
					rowActions:true,
					id:'CompleteTaskGrid',
					root : 'result',
					totalProperty: 'totalCounts',
					url : __ctxPath + "/flow/userCompleteAllTask.do?processName="+processNameFlowKey,
					/*baseParams:{
						'Q_runStatus_SN_EQ':2
					},*/
					fields : [{
								name : 'formId',
								align:'center',
								type : 'int'
							}, 'creatorName','runId','createtime','projectName','projectNumber','activityName','endtime','previousCreator','businessType'],// 'vCommonProjectFlow.creator', 'vCommonProjectFlow.projectName', 'vCommonProjectFlow.projectNumber', 'vCommonProjectFlow.defId','vCommonProjectFlow.createtime', 'vCommonProjectFlow.customerName','vCommonProjectFlow.activityName','vCommonProjectFlow.taskId','vCommonProjectFlow.piId','vCommonProjectFlow.defId','vCommonProjectFlow.userId','vCommonProjectFlow.taskCreateTime','vCommonProjectFlow.endTime','vCommonProjectFlow.taskLimitTime','vCommonProjectFlow.operationTypeValue'],
					columns : [{
								header : '项目名称',
								dataIndex : 'projectName',
								width : 490
							}, /*{
								header : '项目编号',
								dataIndex : 'projectNumber',
								width : 125
							}, */{
								header : '任务名称',
								dataIndex : 'activityName',
								width : 125
							}, {
								header : '创建时间',
								align:'center',
								dataIndex : 'createtime',
								width : 125
							}, {
								header : '完成时间',
								align:'center',
								dataIndex : 'endtime',
								width : 125
							}, {
								header : '上一节点处理人',
								align:'center',
								dataIndex : 'previousCreator',
								width : 125,
								renderer : function(v){
									if(v==null||v=='null'||v==""){
										return '<font color=red>无</font>';
									}else{
										return v;
									}
								}
							}/*,{
								header : '下一节点处理人',
								dataIndex : 'processForm.creatorName'
							}*/,new Ext.ux.grid.RowActions({
									header:'管理',
									width:100,
									align:'center',
									hidden : true,
									actions:[{
											iconCls:'btn-detail',qtip:'查看项目',style:'margin:0 3px 0 3px'
										},{
											iconCls:'btn-close1',qtip:'终止项目',style:'margin:0 3px 0 3px'
										},{
											iconCls:'btn-flow-chart',qtip:'项目流程图',style:'margin:0 3px 0 3px'
										}
									],
									listeners:{
										scope:this,
										'action':this.onRowAction
									}
								})
					],//end of columns
					listeners : {
						scope : this,
						rowdblclick : function(grid, rowIndex, e) {
							//var record = grid.getSelectionModel().getSelected();
							//alert(record.get('projectName')+" "+rowIndex);
							//new CreditProjectInfoForm({record : record}).show();
						}
					} 
				});
			},// end of the initComponents()
			
			
			//查看任务流转记录
			completeFlowRecords : function() {
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if(selRs.length==0){
				   Ext.ux.Toast.msg('操作信息','请选择记录！');
				   return;
				}
				if(selRs.length>1){
				   Ext.ux.Toast.msg('操作信息','只能选择一条记录！');
				   return;
				}
				new SlProcessRunView({runId : selRs[0].get('runId'),businessType : selRs[0].get('businessType')}).show();
				//new SlProcessFormView({runId : selRs[0].get('vCommonProjectFlow.runId'),title : '查看任务流转记录',isHiddenComments : true,isHiddenCreatorName : false}).show();
			},
			//显示项目流程图
			showFlowImg : function() {
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if(selRs.length==0){
				   Ext.ux.Toast.msg('操作信息','请选择记录！');
				   return;
				}
				if(selRs.length>1){
				   Ext.ux.Toast.msg('操作信息','只能选择一条记录！');
				   return;
				}
				var flowImagePanel=new Ext.Panel({
					autoHeight:true,
					border:false,
					html:'<img src="'+__ctxPath+ '/jbpmImage?runId='+selRs[0].get('runId')+ '&rand=' + Math.random()+ '"/>'
				});
				
				var panel=new Ext.Panel({
						autoHeight:true,
						layout:'form',
						border:false,
						items:[
							flowImagePanel
						]
					}
				);
				
				//若当前为子流程，则显示子流程
				if(this.isSubFlow){
					panel.add({
						xtype:'panel',
						autoHeight:true,
						border:false,
						html:'<img src="'+__ctxPath+ '/jbpmImage?runId='+selRs[0].get('runId')+ '&isSubFlow=true&rand=' + Math.random()+ '"/>'
					});
					panel.doLayout();
				}
				
				new Ext.Window({
					autoScroll:true,
						iconCls:'btn-flow-chart',
						bodyStyle:'background-color:white',
						maximizable : true,
						title:'流程示意图',
						width:800,
						height:600,
						modal:true,
						layout:'fit',
						items:panel
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-detail' :
						this.detailPro.call(this);
						break;
					case 'btn-close' :
						this.stopPro.call(this);
						break;
					case 'btn-flow-chart' :
						this.showFlowImg.call(this);
						break;
					default :
						break;
				}
			}
});
