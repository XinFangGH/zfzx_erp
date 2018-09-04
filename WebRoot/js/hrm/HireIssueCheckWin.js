var HireIssueCheckWin=function(id){
     var panel=new Ext.Panel({
        id:'HireIssueCheckPanel',
        border:false,
        autoLoad : {url:__ctxPath+'/hrm/loadHireIssue.do?hireId='+id}
    });
    
    var formPanel=new Ext.FormPanel({
        id:'HireIssueCheckFormPanel',
        url : __ctxPath + '/hrm/checkHireIssue.do',
        formId : 'HireIssueCheckFormPanelId',
        layout:'form',
        frame:false,
        border:false,
        items:[{
          xtype:'hidden',
          name:'hireId',
          value:id
        },{
          fieldLabel : '审核意见',
          xtype:'textarea',
          name:'checkOpinion',
          anchor:'98%'
        },{
			name : 'status',
			xtype : 'hidden',
			id:'HireIssueCheckPanel.status'
		}]
    });
    var win=new Ext.Window({
       title:'招聘信息',
       id:'HireIssueChckeWin',
       iconCls:'menu-hireIssue',
       width:500,
       x:300,
       y:50,
       autoHeight:true,
       buttonAlign : 'center',
       items:[panel,formPanel],
       buttons: [{
		text : '通过审核',
		iconCls : 'btn-empProfile-pass',
		id:'PassHireIssueSb',
		handler : function(){
			Ext.getCmp('HireIssueCheckPanel.status').setValue(1);
			var fp=Ext.getCmp('HireIssueCheckFormPanel');
			if (fp.getForm().isValid()) {
					fp.getForm().submit({
						method : 'post',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							Ext.getCmp('HireIssueGrid').getStore()
									.reload();
							win.close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '信息保存出错，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
							win.close();
						}
					});
				}
		}
	},{
		text : '不通过审核',
		iconCls : 'btn-empProfile-notpass',
		id:'NotPassHireIssueSb',
		handler : function(){
			Ext.getCmp('HireIssueCheckPanel.status').setValue(2);
			var fp=Ext.getCmp('HireIssueCheckFormPanel');
			if (fp.getForm().isValid()) {
					fp.getForm().submit({
						method : 'post',
						waitMsg : '正在提交数据...',
						success : function(fp, action) {
							Ext.ux.Toast.msg('操作信息', '成功保存信息！');
							Ext.getCmp('HireIssueGrid').getStore()
									.reload();
							win.close();
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
										title : '操作信息',
										msg : '信息保存出错，请联系管理员！',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
							win.close();
						}
					});
				}
		}
	},{
	   text : '关闭',
	   iconCls:'close',
		handler : function(){
			win.close();
		}
	}]
    });
    win.show();
    
   
}
