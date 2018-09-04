var DocFolderMoveForm=function(){
	return this.setup();	
}

DocFolderMoveForm.prototype.setup = function() {
	
	var toolbar = this.initToolbar();
	var formPanel = new Ext.FormPanel({
		        id:'DocFolderMoveFormPanel',
				url : __ctxPath + '/document/moveDocFolder.do',
				layout : 'form',
				frame : false,
				border:false,
				bodyStyle:'padding-left:20px;padding-top:20px',
				items : [ 
				        {
				         xtype:'hidden',
				         id:'folderIdOld',
				         name:'folderIdOld'
				        },
				        {
				         xtype:'hidden',
				         id:'folderIdNew',
				         name:'folderIdNew'
				        },
						{
						layout:'column',
						border:false,
						height:28,
						items:[
						{text : '选择文件夹:',
						 xtype:'label',
						 width:98						 
						},{
						 name : 'docFolderNameOld',
						 id : 'docFolderNameOld',
						 xtype:'textfield',
						 width:165,
						 allowBlank:false
						},{
                         xtype:'button',
                         text:'请选择目录',
                         iconCls:'btn-select',
                         handler:function(){
                            PersonalDocFolderSelector.getView(
                              function(id, name){
                                  if(id==0){
                                      Ext.ux.Toast.msg('操作错误','不能转移该文件！');                 
                                  }else{
	                                  var docF=Ext.getCmp('docFolderNameOld');
	                                  var docFolderId=Ext.getCmp('folderIdOld');
	                                  docF.setValue(name);
	                                  docFolderId.setValue(id);
                                  }
                              }                           
                            ).show();
                         }
                         },{
                          xtype:'button',
                          text:'清除目录',
                          iconCls:'btn-clear',
                          handler:function(){
                            var docFolderName=Ext.getCmp('docFolderNameOld');
                            var folderId=Ext.getCmp('folderIdOld');
                            docFolderName.setValue('');
                            folderId.setValue('');                    	
                          }
                         }]},
                         {
						layout:'column',
						border:false,
						height:28,
						items:[
						{text : '转移到:',
						 xtype:'label',
						 width:98						 
						},{
						 name : 'docFolderNameNew',
						 id : 'docFolderNameNew',
						 xtype:'textfield',
						 width:165,
						 allowBlank:false
						},{
                         xtype:'button',
                         text:'请选择目录',
                         iconCls:'btn-select',
                         handler:function(){
                            PersonalDocFolderSelector.getView(
                              function(id, name){                                 
	                                  var docF=Ext.getCmp('docFolderNameNew');
	                                  var docFolderId=Ext.getCmp('folderIdNew');
	                                  docF.setValue(name);
	                                  docFolderId.setValue(id);
                              }                           
                            ).show();
                         }
                         },{
                          xtype:'button',
                          text:'清除目录',
                          iconCls:'btn-clear',
                          handler:function(){
                            var docFolderName=Ext.getCmp('docFolderNameNew');
                            var folderId=Ext.getCmp('folderIdNew');
                            docFolderName.setValue('');
                            folderId.setValue('');                    	
                          }
                         }]}
				]
			});
			
	var topPanel=new Ext.Panel({
		title:'文件夹转移',
		id : 'DocFolderMoveForm',
		iconCls:'menu-folder-go',
		tbar:toolbar,
		border:false,
		items:[formPanel]
	});
	
	return topPanel;

};

/**
 * 
 * @return {}
 */
DocFolderMoveForm.prototype.initToolbar = function() {

	var toolbar = new Ext.Toolbar({
				height : 30,
				items : [{
					text : '提交',
					iconCls:'btn-save',
					handler : function() {
						var folderIdOld=Ext.getCmp('folderIdOld');
						var folderIdNew=Ext.getCmp('folderIdNew');
						if(folderIdOld.getValue()==folderIdNew.getValue()){
						     Ext.ux.Toast.msg('错误信息','不能转移到自己的目录下！');
						}else{
						    var docForm = Ext.getCmp('DocFolderMoveFormPanel');
							if(docForm.getForm().isValid()){
							docForm.getForm().submit({
										waitMsg : '正在提交,请稍候...',
										success : function(docForm, o) {
											Ext.ux.Toast.msg('操作信息','提交成功！');
											var docForm = Ext.getCmp('DocFolderMoveFormPanel');
											docForm.getForm().reset();
										},
										failure : function(docForm,o){
											Ext.ux.Toast.msg('错误信息',o.result.msg);
										}
									});
							}
						}
					}

				}, {
					text : '重置',
					iconCls:'btn-reseted',
					handler : function() {
						var docForm = Ext.getCmp('DocFolderMoveFormPanel');
						docForm.getForm().reset();
					}
				}]
			});
	return toolbar;
};
