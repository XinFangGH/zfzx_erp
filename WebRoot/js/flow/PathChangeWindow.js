/**
 * 执行路径更改窗口
 * @class PathChangeWindow
 * @extends Ext.Window
 */
PathChangeWindow=Ext.extend(Ext.Window,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		this.initUIComponents();
		PathChangeWindow.superclass.constructor.call(this,{
			title:'更改执行路径',
			width:800,
			height:400,
			layout:'border',
			maximizable:true,
			//maximized:true,
			items:[this.leftPanel,this.rightPanel]
		});
	},
	initUIComponents:function(){
		this.leftPanel=new Ext.Panel({
			autoScroll:true,
			width:400,
			collapsible:true,
			title:'流程示意图',
			html:'<img src="'+__ctxPath+ '/jbpmImage?taskId='+this.taskId+ '&rand='+Math.random()+'"/>',
			region:'west',
			split : true
		});
		
		this.rightPanel=new Ext.form.FormPanel({
			title:'更改执行路径',
			region:'center',
			layout:'form',
			autoScroll:true,
			bodyStyle:'padding:4px',
			defaults:{
				anchor:'98%,98%'
			},
			tbar:new Ext.Toolbar({
				items:[
					{
						xtype:'button',
						iconCls:'btn-save',
						text:'保存',
						scope:this,
						handler:this.save
					}
				]
			}),
			items:[
				{
					xtype:'hidden',
					name:'userIds'
				},
				{
					fieldLabel:'跳转节点',
					allowBlank:false,
					xtype:'combo',
					name:'destName',
					allowBlank:false,
					editable : false,
					lazyInit: false,
					anchor:'90%,90%',
					triggerAction : 'all',
					store : new Ext.data.ArrayStore({
							autoLoad : true,
							url : __ctxPath + '/flow/freeTransProcessActivity.do?taskId='+this.taskId,
							fields : ['signalName', 'destName']
					}),
					displayField : 'destName',
					valueField : 'signalName'
				},{
					xtype:'compositefield',
					fieldLabel:'下一步执行人',
					items:[
						{
							xtype:'textfield',
							name:'userNames',
							disabled : true,
							anchor:'98%,98%'
						},{
							xtype:'button',
							text:'选择执行人',
							scope:this,
							handler:this.setHandler
						}
					]
				}
			]
		});
	},
	setHandler:function(){
		var formPanel=this.rightPanel;
		UserSelector.getView(
			function(uIds,uNames){
				formPanel.getCmpByName('userIds').setValue(uIds);
				formPanel.getCmpByName('userNames').setValue(uNames);
			}
		).show();
	},
	save:function(){
		var win=this;
		var formPanel=this.rightPanel;
		var userIds=formPanel.getCmpByName('userIds').getValue();
		
		var destCombo=formPanel.getCmpByName('destName');
		var signalName=destCombo.getValue();
		var destName='';
		var destStore=destCombo.getStore();
		for(var i=0;i<destStore.getCount();i++){
			var rs=destStore.getAt(i);
			if(signalName==rs.data.signalName){
				destName=rs.data.destName;
				break;
			}
		}
		
		if(formPanel.getForm().isValid()){
			var taskGrid=this.taskGrid;
			formPanel.getForm().submit({
				url:__ctxPath+ "/flow/nextProcessActivity.do",
				method:'post',
				waitMsg:'正在提交处理，请稍等',
				params:{
						taskId:this.taskId,
						flowAssignId:userIds,
						signalName:signalName,
						destName:destName,
						comments:"【流程跳转："+this.activityName+"→"+destName+"】",
						isJump : 1// 1表示跳转
				},
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息','成功保存！');
					if(!taskGrid){
						taskGrid=Ext.getCmp('allTaskGrid');
					}
					taskGrid.getStore().reload();
					win.close();
				},
				failure : function(fp, action) {
					Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
					win.close();
				}
			});
		}
	}
});