OnlineDocumentDetail=Ext.extend(Ext.Window,{

	constructor:function(_cfg){
	
		Ext.applyIf(this,_cfg);
		this.initUI();
		OnlineDocumentDetail.superclass.constructor.call(this,{
		       width:700,
               title : '在线文档',
               width : 680,
			   height : 640,
               shim:false,
               modal : true,
               iconCls:'menu-onlinedoc',
               layout:'fit',
               maximizable:true,
               buttonAlign : 'center',
			   buttons:this.buttons,
               items:[
                  this.formPanel
               ]
		});
	},
	initUI:function(){
	     Ext.useShims=true;
     	 this.docPanel=new NtkOfficePanel({showToolbar:false,height:500,doctype:this.docType,unshowMenuBar:true});
         this.formPanel=new HT.FormPanel({
         	  url : __ctxPath + '/document/saveOnlineDocument.do',
              defaults : {
					anchor : '98%,98%'
			  },
			  defaultType:'displayfield',
              items:[{
                 xtype:'hidden',
                 name:'document.docId'
              },{
				name : 'folderId',
				xtype : 'hidden',
				value:this.folderId
			},{
				fieldLabel:'目录名称',
				name : 'OnlineDocFolderName'
			},{
              	 fieldLabel:'文档名称',
                 name:'document.docName'
              },
              this.docPanel.panel,
              {
                 xtype:'hidden',
                 name:'documentFileId'
              },{
                 xtype:'hidden',
                 name:'document.docType'
              }
              ]
         
         });
         var dPanel=this.docPanel;
         var formPanel=this.formPanel;
         if (this.docId != null && this.docId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath + '/document/getDocument.do?docId=' + this.docId,		
				waitMsg : '正在载入数据...',
				preName:'document',
				root:'data',
				success : function(response,options) {
					var doc=Ext.util.JSON.decode(response.responseText).data;
					var af = doc.attachFiles;
					var folderId = doc.docFolder.folderId;
					var folderName = doc.docFolder.folderName;
					formPanel.getCmpByName('folderId').setValue(folderId);
					formPanel.getCmpByName('OnlineDocFolderName').setValue(folderName);
					if(af!=null){
					   var fileId=af[0].fileId;
					   formPanel.getCmpByName('documentFileId').setValue(fileId);
					   dPanel.openDoc(fileId);
					   dPanel.setReadOnly();
					}
				},
				failure : function(response,options) {
					Ext.MessageBox.show({
								title : '操作信息',
								msg : '载入信息失败，请联系管理员！',
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
				}
			});
		}
		
		this.buttons=[{
		    xtype:'button',
		    text:'关闭',
		    iconCls:'close',
		    scope:this,
		    handler:function(){
		    	this.docPanel.closeDoc();
		        this.close();
		    }
		}];
	}
});