FormEditorWindow=Ext.extend(Ext.Window,{
	constructor:function(config){
		Ext.apply(this,config);
		var vmPanel=new Ext.Panel({
			title:'VM表单源代码',
			layout:'fit',
			items:[
				{
					xtype:'textarea',
					anchor:'98%,98%',
					height:420,
					name:'vmSources',
					id:'vmSources'
				}
			]
		});
//		var xmlPanel=new Ext.Panel({
//			title:'XML表单源代码',
//			layout:'fit',
//			items:[{
//					xtype:'textarea',
//					anchor:'98%,98%',
//					height:420,
//					name:'xmlSources',
//					id:'xmlSources'
//				}
//			]
//		});
		
		this.tabPanel = new Ext.TabPanel({
	        activeTab: 0,
	        border:false,
	        defaults:{border:false},
	        items:[
	        	vmPanel
	        ]
	    });
		
		this.buttons=[
		{
			text:'保存',
			iconCls:'btn-save',
			scope:this,
			handler:this.onSave
		},{
			text:'取消',
			iconCls:'btn-cancel',
			scope:this,
			handler:this.close
		}
		]
		
		FormEditorWindow.superclass.constructor.call(this,{
			title:'流程表单--源代码',
			iconCls:'btn-form-tag',
			layout:'fit',
			border:false,
			height:500,
			width:800,
			modal:true,
			maximizable : true,
			buttonAlign:'center',
			items:this.tabPanel
		});

	},
	
	show:function(){
		FormEditorWindow.superclass.show.call(this);
		Ext.Ajax.request({
			url:__ctxPath+'/flow/getVmXmlFormDef.do',
			params:{
				defId:this.defId,
				activityName:this.activityName
			},
			method:'POST',
			success:function(response,options){
				var result=Ext.util.JSON.decode(response.responseText);
				Ext.getCmp('vmSources').setValue(result.vmSources);
			},
			failure:function(response,options){
				Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
			}
		});
		
	},
	
	/**
	 * 保存
	 */
	onSave:function(){
		var win=this;		
		var vmSources=Ext.getCmp("vmSources").getValue();

		Ext.Ajax.request({
			url:__ctxPath+'/flow/saveVmXmlFormDef.do',
			params:{
				defId:this.defId,
				activityName:this.activityName,
				vmSources:vmSources
			},
			method:'POST',
			success:function(response,options){
				Ext.ux.Toast.msg('操作信息','成功保存该流程表单定义！');
				win.close();
			},
			failure:function(response,options){
				Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
			}
		});
	}
});