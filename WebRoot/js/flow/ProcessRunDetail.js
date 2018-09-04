/**
 * 
 * @param {} piId 流程实例id
 * @param {} name 流程名称
 * @param {} defId  流程定义id
 */
var ProcessRunDetail=function(runId,defId,piId,name){
	this.runId=runId;
	this.defId=defId;
	this.piId=piId;
	//this.piId=(piId=='null' || piId==null)? null :piId;
	this.name=name;
	return this.setup();
};

ProcessRunDetail.prototype.setup=function(){
	var piId=this.piId;
	var defId=this.defId;
	var leftPanel=new Ext.Panel({
 		title:'流程示意图',
 		width:500,
 		autoScroll:true,
 		height:800,
 		split:true,
 		collapsible: true,
 		/*collapsed:true,*/
 		region:'west',
 		margin:'5 5 5 5',
 		html:'<img src="'+__ctxPath+ '/jbpmImage?piId='+piId+'&defId='+defId+'&runId='+this.runId+'&rand='+ Math.random()+'"/>'
 	});
 	
 	var rightPanel=this.getRightPanel(this.piId,this.runId,this.defId);
 	
 	var toolbar=new Ext.Toolbar({
 		height:28,
 		items:[
 			{
 				text:'刷新',
 				iconCls:'btn-refresh',
 				handler:function(){
 					leftPanel.body.update('<img src="'+__ctxPath+ '/jbpmImage?piId='+piId+'&defId='+defId+'&rand='+ Math.random()+'"/>');
 					rightPanel.doLayout(true);
 				}
 			},'-',
 			{
 			   text:'打印表单',
 			   id:'formTablePrintButton',
 			   iconCls:'btn-print',
 			   scope:this,
 			   handler:function(){
 			   	   var body=this.formPanel.body;
 			       var html=body.dom.innerHTML;
// 			       var win=new Ext.Window({
// 			           width:800,height:500,
// 			           layout:'form',
// 			           defaults:{
// 			           	  anchor:'100% 100%'
// 			           },
// 			           items:[this.formPanel]
// 			       });
// 			       win.show();
// 			       document.body.innerHTML=html;
// 			       var win=window.open(__ctxPath+'/pages/flow/formPrintPage.jsp');
// 			       win.document.body.innerHTML=html;
 			       
 			        var OpenWindow=window.open('','newwindow');
				　　//写成一行
 			        OpenWindow.document.write('<HEAD>');
				　　OpenWindow.document.write("<TITLE>打印表单</TITLE>")
//				    OpenWindow.document.write('<link rel="stylesheet" type="text/css" href="'+__ctxPath+'/ext3/resources/css/ext-all-notheme.css" />' +
//				    		'<link rel="stylesheet" type="text/css" href="'+__ctxPath+'/ext3/resources/css/ext-patch.css" />');
//				    OpenWindow.document.write('<link rel="stylesheet" type="text/css" href="'+__ctxPath+'/ext3/ux/css/ux-all.css"/>');
				    OpenWindow.document.write('<link rel="stylesheet" type="text/css" href="'+__ctxPath+'/css/admin.css"/>');
				    OpenWindow.document.write('</HEAD>');
				　　OpenWindow.document.write("<BODY BGCOLOR=#ffffff>");
				　　OpenWindow.document.write(html);
				　　OpenWindow.document.write("</BODY>");
				　　OpenWindow.document.write("</HTML>");
				　　OpenWindow.document.close() ;
 			   }
 				
 			}
 		]
 	});
 	
 	var topPanel=new Ext.Panel({
 		id:'ProcessRunDetail'+this.runId,
 		title:'流程详细－'+this.name,
 		iconCls:'menu-flowEnd',
 		layout:'border',
 		tbar:toolbar,
 		autoScroll:true,
 		items:[
 			leftPanel,rightPanel
 		]
 	});
 	return topPanel;
};
ProcessRunDetail.prototype.getFormHtmlCallback=function(){
		//使用自定义Ext模板表单
		var json=document.getElementById('entity_'+this.runId);
		var button=Ext.getCmp('formTablePrintButton');
		if(!json||(json!=null&&!json.value)){
		   this.formPanel.hide();
		   button.hide();
		   return;
		}else{
		   this.formPanel.show();
		   button.show();
		}
		this.formPanel.doLayout();
		var formPanel=this.formPanel;
		//回填数据
		
		var form=this.formPanel.getForm().getEl().dom;
		var fElements = form.elements || (document.forms[form] || Ext.getDom(form)).elements;
		try{
			//var json=document.getElementById('entity_'+this.runId);
			var name,type,value,xtype;
		    //加载JS回调函数
			var callback=function(){
				    var entityJson=null;
					if(json!=null&&json.value){
						entityJson=Ext.decode(json.value);
					}
			        $converDetailToRead.call(this,entityJson);
	        	};
	        //后加载文档的JS
        	$ImportSimpleJs([
        	     __ctxPath+'/js/core/ntkoffice/NtkOfficePanel.js',
        	     __ctxPath + '/js/selector/SealSelector.js',
	             __ctxPath + '/js/selector/PaintTemplateSelector.js'
        	 ],callback,this);
			
		}catch(e){
			//alert(e);
		}
	};
ProcessRunDetail.prototype.getRightPanel=function(piId,runId,defId){
	this.formPanel=new Ext.FormPanel({
	    title:'流程表单信息',
	    width:400,
	    autoScroll:true,
	    height:300,
	    autoLoad:{
				url:__ctxPath+ "/flow/getFormProcessActivity.do",
				nocache: true,
				params:{piId:piId,runId:runId,defId:defId},
				scope:this,
				callback:this.getFormHtmlCallback
			}
	});
	var panel=new Ext.Panel({
		title:'流程审批信息',
		region:'center',
		width:400,
		autoScroll:true,
		autoLoad:{
			url:__ctxPath+'/flow/processRunDetail.do?piId='+piId + "&runId="+ runId,
			nocache:true
		}
	});
	var rightPanel=new Ext.Panel({
//		title:'流程信息',
	    region:'center',
	    width:400,
	    autoScroll:true,
	    border:false,
	    layout:'form',
	    defaults:{
	        anchor:'100% 100%'
	    	
	    },
	    items:[this.formPanel,panel]
	});
	return rightPanel;
};