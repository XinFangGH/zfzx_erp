/* function uploadfile(title,modename,recordid,sessionid) {
 	
 	  Ext.Ajax.request({
        url: 'getSessionid.action',
        method: 'POST',
        success: function(response,request){
        	var result = Ext.decode(response.responseText).returnhtml;
        }       
    });

 	
 	function showupload(){
	 	basepath=this.basepath();
	 	var win = new Ext.Window({
			title : title,
			width : 600,
			height : 350,
			resizable : false,
			layout : 'fit',
	
			items : [{
				basepath:basepath,
				xtype : 'uploadpanel',
				uploadUrl: 'uploadFiles.action;jsessionid='+sessionid,
				filePostName : 'myUpload', // 这里很重要，默认值为'fileData',这里匹配action中的setMyUpload属性
				flashUrl: basepath+'publicmodel/uploads/js/swfupload.swf',
				fileSize : '500 MB',
				height : 400,
				border : false,
				fileTypes : '*.*', // 在这里限制文件类型:'*.jpg,*.png,*.gif'
				fileTypesDescription : '所有文件',
				modename:modename,
				recordid:recordid,
				postParams : {
					recordid:recordid, // 上传到服务器的files目录下面
					modename:modename
				}
			}]
			
		});
		win.show();	
 	}
 }*/
 
 global_up_fileNumLimit = 100;//默认上传数量限制100
 var global_myUp_fileNumLimit;/////本地的 上传数量限制【用于计算】
 
	
	
 /**
  * title: 		title | 
  * upLimit:	上传数量限制(int) 
  * upSize:		上传大小限制【'100 MB'】|  
  * allowType:	允许上传类型【'*.jpg,*.png,*.gif'】
  * mark: 		标识，用来表示附件与关联表之间的关系【当提供recordid的时候，代表是单-多的附件上传mark=modename.recordid 】
  * */
 function uploadReportJS(title,typeisfile,mark,upLimit,upSize,allowType,callUploadFunction,projId,businessType,setname) {
 	if(setname == null || typeof(setname)=='undefined'|| setname ==''){
 		setname = '未知类型';
 	}
 	var jStore_showUploaded = new Ext.data.JsonStore( {
		url : __ctxPath +'/creditFlow/fileUploads/getUploadedFileListFileForm.do',
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [{
			name : 'fileid'
		},{
			name : 'recordid'
		},{
			name : 'setname'
		},{
			name : 'filename'
		},{
			name : 'filepath'
		},{
			name : 'extendname'
		},{
			name : 'filesize'
		},{
			name : 'creatorid'
		},{
			name : 'createtime'
		},{
			name : 'remark'
		},{
			name : 'modename'
		}]
	});
 	//允许后三个参数 不填 所以要判断
 	if(null!=upLimit)
 	{
	 	if(Ext.isEmpty(upLimit)){
	 		upLimit = 100;//默认限制上传100个
	 	}
 	}
 	if(Ext.isEmpty(upSize)){
 		upSize = '100 MB';//默认限制上传大小 100M
 	}
 	if(Ext.isEmpty(allowType)){
 		allowType = "*.*";//默认允许上传所有类型
 	}
 	
 	
 
 	
 	if(null!=upLimit)
 	{
 	  global_up_fileNumLimit = upLimit;
 	  global_myUp_fileNumLimit = global_up_fileNumLimit;
 	  title = title+'【限制上传'+global_up_fileNumLimit+'个附件】';
 	}
 	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 	
 	jStore_showUploaded.setBaseParam('mark',mark);
 	jStore_showUploaded.setBaseParam('typeisfile',typeisfile);
 	jStore_showUploaded.setBaseParam('projId',projId);
 	jStore_showUploaded.setBaseParam('businessType',businessType);
 	var vasepath=basepath();
 	/*显示上传窗口  update by jiang*/
 	var showWin = function (sessionid){
	 	var win = new Ext.Window({
	 		//id : 'id_uploadWin',
			title : title,
			iconCls : 'db-icn-upload_',
			width : 700,
			height : 450,
			collapsible : true,
			maximizable : true,
			resizable : true,
			layout : 'fit',
			buttonAlign : 'center',
			modal:true,
			items : [{
				basepath:vasepath,
				xtype : 'uploadpanel',
				listStore : jStore_showUploaded,
				uploadUrl: vasepath+'/creditFlow/fileUploads/uploadReportJSFileForm.do;jsessionid='+sessionid,
				filePostName : 'myUpload', // 这里很重要，默认值为'fileData',这里匹配action中的setMyUpload属性
				flashUrl: vasepath+'publicmodel/uploads/js/swfupload.swf',
				fileSize : upSize,
				upLimit : upLimit,
				height : 400,
				border : false,
				fileTypes : allowType, // 在这里限制文件类型:'*.jpg,*.png,*.gif'
				fileTypesDescription : '所有文件',
//				modename:modename,
//				recordid:recordid,
				mark : mark,
				postParams : {
//					recordid:recordid, // 上传到服务器的files目录下面
//					modename:modename
					mark : mark,
					typeisfile :typeisfile,
					projId : projId,
					businessType :businessType,
					setname : encodeURI(setname,"utf-8")
				}
			}],
			
//			buttons : [{
//				text : '关闭',
//				iconCls : 'closeIcon',
//				handler : function(){
//					
//					win.close();
//				}
//			}],
			listeners : {
				'close' : function(p){
					var size = jStore_showUploaded.getCount() ;
					jStore_showUploaded.removeAll();
					if(!Ext.isEmpty(callUploadFunction)){
						callUploadFunction(size);
					}
				}
			}
			
		});
		jStore_showUploaded.load({"callback":function(){
 	        win.show();	
 	    }});
 	};
 	/*异步加载项目信息--得到sessionID*/
	Ext.Ajax.request({
	  url: vasepath+'/creditFlow/fileUploads/getSessionidFileForm.do',
	  method: 'POST',
	  success: function(response) {
//	  	alert(response.responseText);
	  	/*var xx=Ext.decode(response.responseText).sessionid;
		alert(response.responseText)*/
//	  	alert(Ext.decode(response.responseText).sid);
		showWin(Ext.decode(response.responseText).sid);
		  
	  }
	});
 	
 };
 /**
  * title: 		title | 
  * upLimit:	上传数量限制(int) 
  * upSize:		上传大小限制【'100 MB'】|  
  * allowType:	允许上传类型【'*.jpg,*.png,*.gif'】
  * mark: 		标识，用来表示附件与关联表之间的关系【当提供recordid的时候，代表是单-多的附件上传mark=modename.recordid 】
  * */
 function reUploadReportJS(title,typeisfile,mark,upLimit,upSize,allowType,callUploadFunction,projId,businessType,fileid,setname) {
 	if(setname == null || typeof(setname)=='undefined'|| setname ==''){
 		setname = '未知类型';
 	}
 	var jStore_showUploaded2 = new Ext.data.JsonStore( {
		url : __ctxPath +'/creditFlow/fileUploads/getUploadedFileListFileForm.do',
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [{
			name : 'fileid'
		},{
			name : 'recordid'
		},{
			name : 'setname'
		},{
			name : 'filename'
		},{
			name : 'filepath'
		},{
			name : 'extendname'
		},{
			name : 'filesize'
		},{
			name : 'creatorid'
		},{
			name : 'createtime'
		},{
			name : 'remark'
		},{
			name : 'modename'
		}]
	});
 	//允许后三个参数 不填 所以要判断
 	if(Ext.isEmpty(upLimit)){
 		upLimit = 100;//默认限制上传100个
 	}
 	if(Ext.isEmpty(upSize)){
 		upSize = '100 MB';//默认限制上传大小 100M
 	}
 	if(Ext.isEmpty(allowType)){
 		allowType = '*.*';//默认允许上传所有类型
 	}
 	
 	global_up_fileNumLimit = upLimit;
 	global_myUp_fileNumLimit = global_up_fileNumLimit;
 	
 	title = title+'【限制上传'+global_up_fileNumLimit+'个附件】';
 	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 	
 	jStore_showUploaded2.setBaseParam('mark',mark);
 	jStore_showUploaded2.setBaseParam('typeisfile',typeisfile);
 	jStore_showUploaded2.setBaseParam('projId',projId);
 	jStore_showUploaded2.setBaseParam('businessType',businessType);
 	jStore_showUploaded2.load();
 	
// 	alert(jStore_showUploaded.getCount());
 	
 	var vasepath=basepath();
 	/*显示上传窗口  update by jiang*/
 	var showWin = function (sessionid){
	 	var win = new Ext.Window({
	 		//id : 'id_uploadWin',
			title : title,
			iconCls : 'db-icn-upload_',
			width : 700,
			height : 450,
			collapsible : true,
			maximizable : true,
			resizable : true,
			layout : 'fit',
			buttonAlign : 'center',
			modal:true,
			items : [{
				basepath:vasepath,
				xtype : 'uploadpanel',
				listStore : jStore_showUploaded2,
				uploadUrl: vasepath+'/creditFlow/fileUploads/reUploadReportJSFileForm.do;jsessionid='+sessionid,
				filePostName : 'myUpload', // 这里很重要，默认值为'fileData',这里匹配action中的setMyUpload属性
				flashUrl: vasepath+'publicmodel/uploads/js/swfupload.swf',
				fileSize : upSize,
				upLimit : upLimit,
				height : 400,
				border : false,
				fileTypes : allowType, // 在这里限制文件类型:'*.jpg,*.png,*.gif'
				fileTypesDescription : '所有文件',
//				modename:modename,
//				recordid:recordid,
				mark : mark,
				postParams : {
//					recordid:recordid, // 上传到服务器的files目录下面
//					modename:modename
					fileid : fileid,
					mark : mark,
					typeisfile :typeisfile,
					projId : projId,
					businessType :businessType,
					setname : encodeURI(setname,"utf-8")
				}
			}],
			
//			buttons : [{
//				text : '关闭',
//				iconCls : 'closeIcon',
//				handler : function(){
//					
//					win.close();
//				}
//			}],
			listeners : {
				'close' : function(p){
					var size = jStore_showUploaded2.getCount() ;
					jStore_showUploaded2.removeAll();
					if(!Ext.isEmpty(callUploadFunction)){
						callUploadFunction(size);
					}
				}
			}
			
		});
		
		win.show();	
 	};
 	
 	
 	/*异步加载项目信息--得到sessionID*/
	Ext.Ajax.request({
	  url: vasepath+'/creditFlow/fileUploads/getSessionidFileForm.do',
	  method: 'POST',
	  success: function(response) {
//	  	alert(response.responseText);
	  	/*var xx=Ext.decode(response.responseText).sessionid;
		alert(response.responseText)*/
//	  	alert(Ext.decode(response.responseText).sid);
		showWin(Ext.decode(response.responseText).sid);
		  
	  }
	});
 	
 };
 /**
  * title: 		title | 
  * upLimit:	上传数量限制(int) 
  * upSize:		上传大小限制【'100 MB'】|  
  * allowType:	允许上传类型【'*.jpg,*.png,*.gif'】
  * mark: 		标识，用来表示附件与关联表之间的关系【当提供recordid的时候，代表是单-多的附件上传mark=modename.recordid 】
  * */
 function uploadfileContract(title,mark,upLimit,upSize,allowType,contractId,callUploadFunction,projId,businessType) {
 	var jStore_showUploaded3 = new Ext.data.JsonStore( {
		url : __ctxPath +'/creditFlow/fileUploads/getUploadedFileListFileForm.do',
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [{
			name : 'fileid'
		},{
			name : 'recordid'
		},{
			name : 'setname'
		},{
			name : 'filename'
		},{
			name : 'filepath'
		},{
			name : 'extendname'
		},{
			name : 'filesize'
		},{
			name : 'creatorid'
		},{
			name : 'createtime'
		},{
			name : 'remark'
		},{
			name : 'modename'
		}]
	});
 	//允许后三个参数 不填 所以要判断
 	if(Ext.isEmpty(upLimit)){
 		upLimit = 100;//默认限制上传100个
 	}
 	if(Ext.isEmpty(upSize)){
 		upSize = '100 MB';//默认限制上传大小 100M
 	}
 	if(Ext.isEmpty(allowType)){
 		allowType = '*.*';//默认允许上传所有类型
 	}
 	
 	global_up_fileNumLimit = upLimit;
 	global_myUp_fileNumLimit = global_up_fileNumLimit;
 	
 	title = title+'【限制上传'+global_up_fileNumLimit+'个附件】';
 	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 	
 	jStore_showUploaded3.setBaseParam('mark',mark);
 	jStore_showUploaded3.load();
 	
// 	alert(jStore_showUploaded.getCount());
 	
 	var vasepath=basepath();
 	/*显示上传窗口  update by jiang*/
 	var showWin = function (sessionid){
	 	var win = new Ext.Window({
	 		//id : 'id_uploadWin',
			title : title,
			iconCls : 'db-icn-upload_',
			width : 700,
			height : 450,
			collapsible : true,
			maximizable : true,
			resizable : true,
			layout : 'fit',
			buttonAlign : 'center',
			modal:true,
			items : [{
				basepath:vasepath,
				xtype : 'uploadpanel',
				listStore : jStore_showUploaded3,
				uploadUrl: vasepath+'/creditFlow/fileUploads/uploadFilesContractFileForm.do;jsessionid='+sessionid,
				filePostName : 'myUpload', // 这里很重要，默认值为'fileData',这里匹配action中的setMyUpload属性
				flashUrl: vasepath+'publicmodel/uploads/js/swfupload.swf',
				fileSize : upSize,
				upLimit : upLimit,
				height : 400,
				border : false,
				fileTypes : allowType, // 在这里限制文件类型:'*.jpg,*.png,*.gif'
				fileTypesDescription : '所有文件',
//				modename:modename,
//				recordid:recordid,
				mark : mark,
				postParams : {
//					recordid:recordid, // 上传到服务器的files目录下面
//					modename:modename
					projId :projId,
					businessType :businessType,
					contractId :contractId,
					mark : mark
				}
			}],
			
//			buttons : [{
//				text : '关闭',
//				iconCls : 'closeIcon',
//				handler : function(){
//					
//					win.close();
//				}
//			}],
			listeners : {
				'close' : function(p){
					var size = jStore_showUploaded3.getCount() ;
					jStore_showUploaded3.removeAll();
					if(!Ext.isEmpty(callUploadFunction)){
						callUploadFunction(size);
					}
				}
			}
			
		});
		
		win.show();	
 	};
 	/*异步加载项目信息--得到sessionID*/
	Ext.Ajax.request({
	  url: vasepath+'/creditFlow/fileUploads/getSessionidFileForm.do',
	  method: 'POST',
	  success: function(response) {
//	  	alert(response.responseText);
	  	/*var xx=Ext.decode(response.responseText).sessionid;
		alert(response.responseText)*/
//	  	alert(Ext.decode(response.responseText).sid);
		showWin(Ext.decode(response.responseText).sid);
		  
	  }
	});
};
 	
 /**
  * title: 				title
  * mark: 				标识，用来表示附件与关联表之间的关系【当提供recordid的时候，代表是单-多的附件上传mark=modename.recordid 】
  * upLimit:			上传数量限制(int) 
  * upSize:				上传大小限制【'100 MB'】|  
  * allowType:			允许上传类型【'*.jpg,*.png,*.gif'】
  * callUploadFunction  回调方法
  * projId      		项目Id
  * businessType 		业务类型
  * flag                用于区分是合同模板制作的上传(默认空值)还是流程中的合同上传(0)
  * panel  			    合同表格
  * */
 function uploadfile(title,mark,upLimit,upSize,allowType,callUploadFunction,projId,businessType,flag,panel) {
 	var postParams =null;
	if(projId != null&&businessType!=null){
		postParams = {
			mark:mark,
			projId:projId,
			businessType:businessType,
			flag:flag
		}
	}else{
		postParams = {
			mark:mark
		}
	}
 	
 	var jStore_showUploaded4 = new Ext.data.JsonStore( {
		url : __ctxPath +'/creditFlow/fileUploads/getUploadedFileListFileForm.do',
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [{
			name : 'fileid'
		},{
			name : 'recordid'
		},{
			name : 'setname'
		},{
			name : 'filename'
		},{
			name : 'filepath'
		},{
			name : 'extendname'
		},{
			name : 'filesize'
		},{
			name : 'creatorid'
		},{
			name : 'createtime'
		},{
			name : 'remark'
		},{
			name : 'modename'
		}]
	});
 	
 	//允许后三个参数 不填 所以要判断
 	if(Ext.isEmpty(upLimit)){
 		upLimit = 100;//默认限制上传100个
 	}
 	if(Ext.isEmpty(upSize)){
 		upSize = '100 MB';//默认限制上传大小 100M
 	}
 	if(Ext.isEmpty(allowType)){
 		allowType = '*.*';//默认允许上传所有类型
 	}
 	
 	global_up_fileNumLimit = upLimit;
 	global_myUp_fileNumLimit = global_up_fileNumLimit;
 	
 	title = title+'【限制上传'+global_up_fileNumLimit+'个附件】';
 	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 	
 	jStore_showUploaded4.setBaseParam('mark',mark);
 	jStore_showUploaded4.load();
 	
// 	alert(jStore_showUploaded.getCount());
 	
 	var vasepath=basepath();
 	/*显示上传窗口  update by jiang*/
 	var showWin = function (sessionid){
	 	var win = new Ext.Window({
	 		//id : 'id_uploadWin',
			title : title,
			iconCls : 'db-icn-upload_',
			width : 700,
			height : 450,
			collapsible : true,
			maximizable : true,
			resizable : true,
			layout : 'fit',
			buttonAlign : 'center',
			modal:true,
			items : [{
				basepath:vasepath,
				xtype : 'uploadpanel',
				flag:flag,
				listStore : jStore_showUploaded4,
				uploadUrl: vasepath+'/creditFlow/fileUploads/uploadFilesFileForm.do;jsessionid='+sessionid,
				filePostName : 'myUpload', // 这里很重要，默认值为'fileData',这里匹配action中的setMyUpload属性
				flashUrl: vasepath+'publicmodel/uploads/js/swfupload.swf',
				fileSize : upSize,
				upLimit : upLimit,
				height : 400,
				border : false,
				fileTypes : allowType, // 在这里限制文件类型:'*.jpg,*.png,*.gif'
				fileTypesDescription : '所有文件',
//				modename:modename,
//				recordid:recordid,
				mark : mark,
				postParams:postParams
/*				postParams : {
//					recordid:recordid, // 上传到服务器的files目录下面
//					modename:modename
					mark : mark
				}*/
			}],
			
//			buttons : [{
//				text : '关闭',
//				iconCls : 'closeIcon',
//				handler : function(){
//					
//					win.close();
//				}
//			}],
			listeners : {
				'close' : function(p){
					var size = jStore_showUploaded4.getCount() ;
					jStore_showUploaded4.removeAll();
					if(!Ext.isEmpty(callUploadFunction)){
						callUploadFunction(size,panel);
					}
				}
			}
			
		});
		
		win.show();	
 	};
 	
 	
 	/*异步加载项目信息--得到sessionID*/
	Ext.Ajax.request({
	  url: vasepath+'/creditFlow/fileUploads/getSessionidFileForm.do',
	  method: 'POST',
	  success: function(response) {
//	  	alert(response.responseText);
	  	/*var xx=Ext.decode(response.responseText).sessionid;
		alert(response.responseText)*/
//	  	alert(Ext.decode(response.responseText).sid);
		showWin(Ext.decode(response.responseText).sid);
		  
	  }
	});
 	
 }
 

//function deleteAttach(nid,id,pnid) {
//	//alert(nid);
//	//alert(id);
//	//alert(pnid);
//    // Basic request
//   Ext.Ajax.request({
//        url: 'DeleFile.do',
//        method: 'POST',
//        success: function(response,request){
//        	if(Ext.getDom(nid)!=null){
//        		Ext.removeNode(Ext.getDom(nid));
//        	}
//        	if(Ext.getDom(pnid)!=null){
//        		Ext.removeNode(Ext.getDom(pnid));
//        	}
//        	var filecount=Ext.getDom("ext_filecount").innerHTML;
//			filecount=parseInt(filecount)-1;
//			Ext.getDom("ext_filecount").innerHTML=filecount;
//			if(Ext.getDom("filevalue")!=null){
//				var ids=Ext.getDom("filevalue").value;
//    			Ext.getDom("filevalue").value=ids.replace(id,0);
//			}
//			
//        },
//        params: { fileid: id }
//    });
//}


function basepath(){
	var strFullPath=window.document.location.href;
	var strPath=window.document.location.pathname;
	var pos=strFullPath.indexOf(strPath);
	var prePath=strFullPath.substring(0,pos);
	var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
	return prePath+postPath+"/";
}



 /**
  * added by gao 重新上传功能 改进 要求重新上传时不用grid 而用单纯的 form表单
  * title: 		title | 
  * upLimit:	上传数量限制(int) 
  * upSize:		上传大小限制【'100 MB'】|  
  * allowType:	允许上传类型【'*.jpg,*.png,*.gif'】
  * mark: 		标识，用来表示附件与关联表之间的关系【当提供recordid的时候，代表是单-多的附件上传mark=modename.recordid 】
  * */
 function reUploadReportJSExt(title,typeisfile,mark,upLimit,upSize,allowType,callUploadFunction,projId,businessType,fileid,setname){
				 	if(setname == null || typeof(setname)=='undefined'|| setname ==''){
				 		setname = '未知类型';
				 	}
					var newUrl="";
					//判断是否是重新上传
					if(fileid){
						newUrl=__ctxPath+'/creditFlow/fileUploads/reUploadReportJSFileForm.do';
					}else{
						newUrl=__ctxPath + '/creditFlow/fileUploads/uploadReportJSFileForm.do';
					}
					new Ext.Window({
						id : 'reUploadExtWin',
						title : title,
						layout : 'fit',
						width : (screen.width - 180) * 0.6,
						height : 130,
						closable : true,
						resizable : true,
						plain : false,
						bodyBorder : false,
						border : false,
						modal : true,
						constrainHeader : true,
						bodyStyle : 'overflowX:hidden',
						buttonAlign : 'right',
						items : [new Ext.form.FormPanel({
									id : 'uploadContractFrom',
									url : newUrl,
									monitorValid : true,
									labelAlign : 'right',
									buttonAlign : 'center',
									enctype : 'multipart/form-data',
									fileUpload : true,
									layout : 'column',
									frame : true,
									items : [{
												columnWidth : 1,
												layout : 'form',
												labelWidth : 80,
												defaults : {
													anchor : '95%'
												},
												items : [{
															xtype : 'textfield',
															fieldLabel : '重新上传文件',
															allowBlank : false,
															blankText : '文件不能为空',
															id : 'fileUpload',
															name : 'myUpload',
															inputType : 'file'
														}]
											}],
									buttons : [{
										text : '上传',
										iconCls : 'uploadIcon',
										formBind : true,
										handler : function() {
											var str = Ext.getCmp("fileUpload").getValue().split('\\').last();
											/*var size = cpanel.getStore().data.length;
											if(!fileid){//fileid 必须存在
												if (size > 0) {
													fileid= cpanel.getStore().getAt(0).data.fileid;
													Ext.Ajax.request({
																url : __ctxPath+ '/credit/document/DeleFile.do?fileid='+ fileid,
																method : 'POST'
															});
												}
											}*/
											
											Ext.getCmp('uploadContractFrom').getForm().submit({
														method : 'POST',
														waitTitle : '连接',
														waitMsg : '消息发送中...',
														params : {
															fileid : fileid,
															myUploadFileName : str,
															mark : mark,
															typeisfile : typeisfile,
															projId : projId,
															businessType : businessType,
															setname : encodeURI(setname,"utf-8")
														},
														success : function(form, action) {
															Ext.ux.Toast.msg('提示','上传成功！',
																Ext.getCmp('reUploadExtWin').close(),
																function(btn,text) {
															});
															if(!Ext.isEmpty(callUploadFunction)){
																	callUploadFunction();
															}
															//cpanel.getStore().reload();
														},
														failure : function(form, action) {
															Ext.ux.Toast.msg('提示', '上传失败！');
															if(!Ext.isEmpty(callUploadFunction)){
																	callUploadFunction();
															}
														}
													});
										}
									}]
								})]
					}).show();
 }	
 
