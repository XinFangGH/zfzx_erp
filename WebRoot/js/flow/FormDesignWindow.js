FormDesignWindow=Ext.extend(Ext.Window,{
	constructor:function(config){
		Ext.applyIf(this,config);
		this.buttons=[
			{
					text : '保存',
					scope : this,
					iconCls:'btn-save',
					handler : function() {
						var self=this;
						var formDesignFrame=document.getElementById('formDesignFrame');
						//取得表单定义的Ext Json源代码
						var extFormDef=formDesignFrame.contentWindow.xdsProject.getFormJsonCode();
						var formItemDef=formDesignFrame.contentWindow.xdsProject.getFormItems();
						var extDef=Ext.util.JSON.encode(formDesignFrame.contentWindow.xdsProject.getData());
						var params={
							defId:this.defId,
							templateId:this.templateId,
							activityName:this.activityName,
							extFormDef:extFormDef,
							formItemDef:formItemDef,
							extDef:extDef
						};
						//取得
						Ext.Ajax.request({
							url:__ctxPath+'/flow/saveFormTemplate.do',
							params:params,
							method:'POST',
							success:function(response,options){
								Ext.ux.Toast.msg('操作信息','已经成功定义了该表单！');
								self.close();
							},
							failure:function(response,options){
								Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
							}
						});
					}
				}, {
					text:'取消',
					scope:this,
					iconCls:'btn-cancel',
					handler:function(){
						this.close();
					}
				}
		]
		//用多一个panel，避免在ie下iframe出现空白页
		var panel=new Ext.Panel({
			height:400,
			width:700,
			border:false,
			html:'<iframe id="formDesignFrame" src="'+__ctxPath+'/pages/flow/formEditor.jsp?templateId='+this.templateId+'" width="100%" height="100%" frameborder="0"></iframe>'
		});
		
		FormDesignWindow.superclass.constructor.call(this,{
			title:'流程表单设计',
			height : 550,
			width : 1000,
			modal:true,
			layout:'fit',
			iconCls:'btn-form-design',
			maximizable : true,
			buttonAlign:'center',
			items:[panel]
		});
	}

	
});