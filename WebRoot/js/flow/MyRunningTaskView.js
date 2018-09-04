/**
 * 我参与的正在运行中的任务
 * 以方便可以撤回
 * @class MyRunningTaskView
 * @extends Ext.Panel
 */
//var processNameMyRunning="smallLoanFlow,smallLoanFast,FinancingFlow,guaranteeNormalFlow";
MyRunningTaskView=Ext.extend(Ext.Panel,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		this.initUI();
		MyRunningTaskView.superclass.constructor.call(this,{
			title:'项目事项追回',
			id:"runningtask",
			layout:'border',
			iconCls : 'btn-back',
			items:[
				this.gridPanel
			]
		});
	},
	initUI : function() {
				// GridPanel
				this.gridPanel = new HT.GridPanel({
							region:'center',
							tbar : [
								{
									text:'详细',
									iconCls:'btn-flowView',
									scope:this,
									handler : function() {
										this.showRunDetail(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : isGranted('_rollbackTask')?false:true
								}),{
									text:'追回',
									iconCls:'btn-back',
									scope:this,
									hidden : isGranted('_rollbackTask')?false:true,
									handler:this.rollbackTask
								}
							],
							rowActions : false,
							url : __ctxPath + '/flow/myRunningProcessRun.do?processName='+myProcessNameFlowKey,
							fields : [{name : 'runId',
										type : 'int'
									}, 'subject', 'createtime', 'defId',
									'piId', 'runStatus','tasks','exeUsers','businessType','projectId'],
							columns : [{
										header : 'runId',
										dataIndex : 'runId',
										hidden : true
									}, {
										header : '标题',
										dataIndex : 'subject',
										width : 620,
										sortable:false
									}, {
										header : '创建时间',
										dataIndex : 'createtime',
										width : 125,
										sortable:false
									},{
										header:'运行任务名',
										dataIndex:'tasks',
										width : 125,
										sortable:false
									},{
										header:'执行人',
										dataIndex:'exeUsers',
										width : 125,
										sortable:false
									}
									],
								listeners : {
									scope : this,
									rowdblclick : function(gridPanel,rowindex, e) {
										this.showRunDetail(gridPanel);
									}
								}
						});
			},
			showRunDetail:function(grid){
				editPro(grid);
			},
			/**
			 * 任务追回
			 */
			rollbackTask:function(){
				//取到需要进行任务追回的任务
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if(selRs.length==0){
					Ext.ux.Toast.msg('操作信息','请选择需要追回的流程!');
					return;
				}
				var runId=selRs[0].data.runId;				
				Ext.Ajax.request({
					url:__ctxPath+'/flow/rollbackProcessRun.do',
					method:'POST',
					params:{
						runId:runId
					},
					scope:this,
					success:function(resp,options){
						var result=Ext.decode(resp.responseText);
						if(!result.success){
							Ext.ux.Toast.msg('操作信息','该流程目前已经完成下一步的处理，不能追回！');
							return;
						}
						Ext.ux.Toast.msg('操作信息','已经成功追回，请查看待办事项！');
						this.gridPanel.getStore().reload();
					},
					failture:function(resp,options){
						Ext.ux.Toast.msg('操作信息','该流程目前已经完成下一步的处理，不能追回！');
					}
				});
			}
});