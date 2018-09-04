var DocumentSharedPanel=function(docId,docName){
  	this.docId = docId;
  	this.docName=docName;
    var pa=this.setup();
	var panel=new Ext.Panel({
	        id : 'DocumentShared',
			title : this.docName+'-详细信息',
			iconCls:'menu-folder-shared',
			width : 510,
			autoHeight :true,
			modal : true,
			autoScroll:true,
			layout : 'anchor',
			plain : true,
			bodyStyle : 'padding:5px;',
			items : [this.setup()]
		});
	return panel;
}
DocumentSharedPanel.prototype.setup=function(){
	var panel=new Ext.Panel({
	     id:'DocumentSharedPanel',
	     modal : true,
	     autoScroll:true,
	     bodyStyle : 'padding-left:10%;padding-right:10%;',
	     height:450,
	     autoLoad:{url:__ctxPath+'/document/detailDocument.do?docId='+this.docId}	     
	});
	return panel;
		
}