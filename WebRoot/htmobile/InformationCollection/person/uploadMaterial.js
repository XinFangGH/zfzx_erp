
Ext.define('htmobile.InformationCollection.person.uploadMaterial', {
    extend: 'Ext.form.Panel',
    name: 'uploadMaterial',
    id:'uploadMaterial',
    constructor: function (config) {
    	config = config || {};
    	this.resouce=config.resouce;
    	this.personid=config.id;
    	this.mark=config.mark;
    	this.successcount=0;
    	 this.picUrl = [];
    	Ext.apply(config,{
    		width:"100%",
		    height:"100%",
		    id:"addImage",
		    fullscreen: true,
		    title:"资料录入",
		    scrollable:{
		    	direction: 'vertical'
		    },
		
		   items: [
							{
								xtype: 'panel',
								padding:'20px',
								docked: 'top',
								layout: {
								type: 'hbox',
								align: 'middle'
							   },
							   	items : [{
								xtype : 'spacer'
							}, {
								xtype : 'button',
								text : '返回',
								iconMask : true,
								scope : this,
								width:100,
								//flex : .5,
								handler : this.returnlast
							}, {
								xtype : 'button',
								text : '选择',
								iconMask : true,
								scope : this,
								width:100,
								//flex : .5,
								handler : this.capturePhoto
							}, {
								xtype : 'button',
								text : '上传',
								iconMask : true,
								scope : this,
								width:100,
								//flex : .5,
								handler : this.uploading
							}, {
								xtype : 'spacer'
							}]
					          /*  html:"<input type='button' onclick='javascript:capturePhoto();' value='选择图片'/>" +
					            		"&nbsp;&nbsp;&nbsp;<input type='button' onclick='javascript:uploading({\"id\":\""+id+"\",\"mark\":\""+mark+"\"});' value='点击上传'/>"
					        
						    */
							}
		  ]
    	});

 deletepic=function(data){
  	var uploadMaterial=Ext.getCmp("addImage");
    uploadMaterial.picUrl[data.ia]=null;
   var picUrli=Ext.getCmp("picUrli"+data.ia);
   picUrli.destroy();
    
}
    	this.callParent([config]);
    	
 
    
    	
},
returnlast:function(){
	if(this.resouce=="newperson"){
	
	 mobileNavi.pop();
	}else if(this.resouce=="materiallist"){
		 mobileNavi.pop();
	   mobileNavi.getActiveItem().getStore().loadPage(1);
	}
	
    			  

},
   	uploading :function(){
    		var mark=this.mark;
    		var id=this.personid;
    		//网络是否连接
    	//	alert(this.picUrl);
    		if(navigator.connection.type!=Connection.NONE){
    			//上传的文件
    			var flieURI = this.picUrl;
    			for (var i=0;i<flieURI.length;i++)
    			{
    				if(null!=flieURI[i]){
	    				var options = new FileUploadOptions();
	    				options.fileKey = "myFile";
	    				options.fileName = flieURI[i].substr(flieURI[i].lastIndexOf('/')+1);
	    				options.mimeType = "image/jpeg";
	    				
	    				var ft = new FileTransfer();
	    				ft.upload(flieURI[i],__ctxPath+"/UploadMartiallAction?id="+id+"&filemark="+mark+"&arrayi="+i,this.onUploadSuccess,this.onUploadError,options);
    				}
    			}
    			 Ext.Msg.alert('','正在上传请耐心等候！');
    		/*	 this.myMask = new Ext.LoadMask(Ext.getCmp("uploadMaterial"), {msg:"正在上传请耐心等候！"}); 
    			  this.myMask.show();//显示 
*/    			/*if(){
    			   mobileNavi.pop();
    			   mobileNavi.getActiveItem().getStore().loadPage(1);
    			}*/
    		}else{
    		
    		    Ext.Msg.alert('','没有网络');
    		}
    		
    	},
    		
    onUploadSuccess:function (resp){
    	//this.myMask.hide();
    	var objm=Ext.getCmp("picUrli"+resp.response);
    	objm.setHtml(objm.getHtml().split("--")[0]+"成功上传</p>");
    	var uploadMaterial=Ext.getCmp("addImage");
    	uploadMaterial.picUrl[resp.response]=null;
		/*	consloe.log("Code="+resp.responseCode);
			consloe.log("Response="+resp.response);
			consloe.log("Sent="+resp.bytesSent);*/
		},

	   onUploadError:	function onUploadError(err){
	 //  	this.myMask.hide();
			var errorCode = err.code;
			var reasons = {};
			reasons[FileTransferError.CONNECTION_ERR]='连接错误';
			reasons[FileTransferError.FILE_NOT_FOUND_ERR]='要上传的文件不存在';
			reasons[FileTransferError.INVALID_URL_ERR]='服务器目标地址不正确';
			reasons[FileTransferError.ABORT_ERR]='操作被中断';
			alert(reasons[err.code]);
		},
		capturePhoto :function(){
			
		    	var obj=this;
			  obj.actionSheet=  Ext.create('Ext.ActionSheet', {
			    items: [
		            {
		                text: '拍照',
		                xtype: 'button',
		                ui  : 'decline',
		                listeners:{
		 		    	　　 tap:function(){
		 		    		 	
		 		    		 	navigator.camera.getPicture(onPhotoURISuccess,onPhotoURIError,{
				    				quality:50,destinationType:Camera.DestinationType.FILE_URI
				    			});
			 		    		function onPhotoURIError(message){
			 		    			alert('操作失败'+message);
			 		    		}
			
			 		    		function onPhotoURISuccess(imageURI){
			 		    			
			 		    			
			 		    				obj.picUrl.push(imageURI);
		 		    				var uploadMaterial=Ext.getCmp("addImage");
		 		    				var ia=obj.picUrl.length-1;
		 		    				var myimg = Ext.create('Ext.Label',{
		 		    					id:"picUrli"+ia,
		 		    					html: "<p style='text-align:center'><img src="+imageURI+" style='width:200px;height:200px;' /> --<input type='button' onclick='javascript:deletepic({\"ia\":\""+ia+"\"});' value='删除'/></p>"
									});
					        		uploadMaterial.add(myimg);
					        		obj.actionSheet.hide();
			 		    			
			 		    		}
		 		    	　　 }
		 		    	　}
		            },
		            {
		                text: '相册',
		                xtype: 'button',
		                ui  : 'confirm',
		            	listeners:{
				    	　　 tap:function(){
				    		 	navigator.camera.getPicture(onPhotoURISuccess,onPhotoURIError,{
				    				quality:50,destinationType:Camera.DestinationType.FILE_URI,sourceType:Camera.PictureSourceType.PHOTOLIBRARY
				    			});
				    		 
				    		 	function onPhotoURIError(message){
			 		    			alert('操作失败'+message);
			 		    		}
			
			 		    		function onPhotoURISuccess(imageURI){
			 		    			
			 		    				obj.picUrl.push(imageURI);
		 		    				var uploadMaterial=Ext.getCmp("addImage");
		 		    				var ia=obj.picUrl.length-1;
		 		    				var myimg = Ext.create('Ext.Label',{
		 		    					id:"picUrli"+ia,
		 		    					html: "<p style='text-align:center'><img src="+imageURI+" style='width:200px;height:200px;' /> --<input type='button' onclick='javascript:deletepic({\"ia\":\""+ia+"\"});' value='删除'/></p>"
									});
					        		uploadMaterial.add(myimg);
					        		obj.actionSheet.hide();
			 		    			
					        		
					        		
			 		    		}
				    	　　 }
				    	　}
		            },
		            {
		            	xtype: 'button',
		                text: '取消',
		                listeners:{
					    	　　 tap:function(){
		            				obj.actionSheet.hide();
					    	　　 }
					    	　}
		            }
		            
		        ]
		    }
		)

    
    		Ext.Viewport.add(obj.actionSheet);
    	  		  obj.actionSheet.show();
    	}
    	
    
});





