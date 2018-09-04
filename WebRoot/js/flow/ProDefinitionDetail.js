/**
 *　流程详细页 
 */
 var ProDefinitionDetail=function(defId,title){
 	this.defId=defId;
 	this.title=title;
 	return this.getView();
 };
 
 ProDefinitionDetail.prototype.getView=function(){
 	var leftPanel=new Ext.Panel({
 		title:'流程示意图',
 		width:500,
 		height:800,
 		autoScroll:true,
 		split:true,
 		region:'west',
 		margin:'5 5 5 5',
 		html:'<img src="'+__ctxPath+ '/jbpmImage?defId='+this.defId+ '&rand='+Math.random()+'"/>'
 	});
 	
 	var rightPanel=new Ext.Panel({
 		title:'流程描述',
 		width:400,
 		height:800,
 		margin:'5 5 5 5',
 		region:'center',
 		autoScroll:true,
 		autoLoad:{
			url:__ctxPath+'/flow/processDetail.do?defId='+this.defId+'&rand='+Math.random()
		}
 	});

 	var topPanel=new Ext.Panel({
 		id:'ProDefinitionDetail'+this.defId,
 		title:'流程详细信息－'+this.title,
 		iconCls:'btn-preview',
 		layout:'border',
 		items:[
 			leftPanel,rightPanel
 		]
 	});
 	
 	return topPanel;
 };