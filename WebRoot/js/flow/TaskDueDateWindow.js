TaskDueDateWindow=Ext.extend(Ext.Window,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		TaskDueDateWindow.superclass.constructor.call(this,{
			title:'为任务设置限期时间',
			height:120,
			width:350,
			layout:'form',
			modal:true,
			bodyStyle:'padding:5px',
			buttonAlign:'center',
			items:[
				{
					fieldLabel:'限期时间',
					xtype:'datetimefield',
					format:'Y-m-d H:i:s',
					name:'dueDate',
					anchor:'98%,98%',
					value:this.dueDate?this.dueDate:''
				}
			],
			buttons:[
				{
					text:'保存',
					iconCls:'btn-save',
					scope:this,
					handler:this.save
				},{
					text:'关闭',
					iconCls:'close',
					scope:this,
					handler:this.close
				}
			]
		});
	},
	save:function(){
		var dueDate=this.getCmpByName('dueDate');
		var taskGrid=this.taskGrid?this.taskGrid:Ext.getCmp('allTaskGrid');
		var rs = taskGrid.getSelectionModel().getSelections();
		var taskIds='';
		for(var i=0;i<rs.length;i++){
			if(i>0)taskIds+=',';
			taskIds+=rs[i].data.taskId;
		}
		if(dueDate.getValue()==''){
			Ext.ux.Toast.msg('操作信息','请填写日期数据！');
			return;
		}
		var dueDateStr=Ext.util.Format.date(dueDate.getValue(),'Y-m-d H:i:s');
		
		//取到选择的记录行数
		Ext.Ajax.request({
			url:__ctxPath+'/flow/dueTask.do',
			method:'POST',
			scope:this,
			params:{
				taskIds:taskIds,
				dueDate:dueDateStr
			},
			success:function(response,options){
				taskGrid.getStore().reload();
			},
			failure:function(response,options){
				this.close();
			}
		});
		
		this.close();
	}
});
