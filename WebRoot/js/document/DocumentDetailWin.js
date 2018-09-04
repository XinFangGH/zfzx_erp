
DocumentDetailWin=Ext.extend(Ext.Window,{
    showPanel:null,
	constructor:function(_cfg){
	   Ext.applyIf(this,_cfg);
	   this.initUI();
	   DocumentDetailWin.superclass.constructor.call(this,{
	       	title :'详细信息',
			iconCls:'menu-folder-shared',
			width : 710,
			modal : true,
			maximizable:true,
			layout : 'fit',
	        items:[this.showPanel],
	        buttonAlign:'center',
	        buttons:this.buttons
	   });
	},
	initUI:function(){
	    this.showPanel=new Ext.Panel({
	     id:'DocumentSharedPanel',
	     modal : true,
	     autoScroll:true,
//	     bodyStyle : 'padding-left:10%;padding-right:10%;',
	     height:450,
	     autoLoad:{url:__ctxPath+'/document/detailDocument.do?docId='+this.docId}	     
		});
		
		this.buttons=[{
		   xtype:'button',
		   text:'关闭',
		   iconCls:'close',
		   handler:function(){
		      this.close();
		   },
		   scope:this
		}];
	}
});

