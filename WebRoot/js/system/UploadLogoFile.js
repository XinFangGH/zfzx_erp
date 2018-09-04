/**
 * 
 * @param {} id 			父对象id 例:P2PSystemBaseParaView
 * @param {} title 			title    例:微信二维码
 * @param {} mark           mark     例:system_p2p
 * @param {} field			所删除的对象id   例:weixin
 * @param {} width_height   默认宽高
 */
var uploadLogoFile= function(id,title,mark,field,width_height){
	var parent=Ext.getCmp(id);
	new Ext.Window({
		title : title,
		layout : 'fit',
		width : (screen.width-180)*0.6,
		height : 130,
		closable : true,
		resizable : true,
		plain : false,
		bodyBorder : false,
		border : false,
		modal : true,
		constrainHeader : true ,
		bodyStyle:'overflowX:hidden',
		buttonAlign : 'right',
		items:[new Ext.form.FormPanel({
			url : getRootPath()+'/system/uploadFileSystemProperties.do',
			monitorValid : true,
			labelAlign : 'right',
			buttonAlign : 'center',
			enctype : 'multipart/form-data',
			fileUpload: true, 
			layout : 'column',
			frame : true ,
			items : [{
				columnWidth : 1,
				layout : 'form',
				labelWidth : 140,
				defaults : {anchor : '95%'},
				items :[{
					xtype : 'textfield',
					fieldLabel : title,
					allowBlank : false,
					blankText : title+'不能为空',
					id : 'fileUpload',
					name: 'fileUpload',
    				inputType: 'file'
				},{
					xtype : 'hidden',
					name: 'mark',
					value :mark
				}]
			}],
			scope:this,
			buttons : [{
				text : '上传',
				iconCls : 'uploadIcon',
				formBind : true,
				handler : function() {
				       var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$|\.([iI][cC][oO]){1}$/  
					   var url = 'file://'+ Ext.get('fileUpload').dom.value;  
                       var furl=Ext.get('fileUpload').dom.value;
					   var extendname=furl.substring(furl.lastIndexOf("."),furl.length);
					   if (!img_reg.test(url) && extendname!=".pdf") {  
                      	 	alert('您选择的文件格式不正确,请重新选择!');
                         	return false;
                       };
					   var pwindow=this.getOriginalContainer();
					   var collection = pwindow.items; 
					   var formPanel=collection.first();
					   formPanel.getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							params:{extendname:extendname,remark:field},
							success : function(form ,action) {
								if(null==action.result.fileid){
									pwindow.destroy();
									alert("文件已经上传成功,但360极速模式下上传存在问题请关闭弹窗进行查看!");
								}else{
									Ext.ux.Toast.msg('提示',title+action.result.msg,
										pwindow.destroy(),
										function(btn, text) {
									});
								}
								var fileid = action.result.fileid;
								var webPath = action.result.webPath;
								var display =parent.getCmpByName(field);
								var suffix=extendname.toLowerCase();
								if(suffix==".jpg" || suffix==".jpeg" || suffix==".png" || suffix==".ico"){
									display.setText('<img src="'+ getRootPath()+"/"+webPath+'" ondblclick=showPic("'+webPath+'") width =140 height=80  /><a href="#" onClick =uploadLogoFile(\''+id+'\',\''+title+'\',\''+mark+'\',\''+field+'\',\''+width_height+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile(\''+id+'\',\''+title+'\','+action.result.fileid+',\''+field+'\',\''+width_height+'\',\''+mark+'\')>删除</a><font style="padding-left:20px;">图片大小： '+width_height+'</font>',false);
								}
								parent.doLayout();
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('提示',title+'上传失败！');		
							}
					});
				}
			}]
		})]
	}).show();
};

/**
 * 
 * @param {} id 			父对象id 例:P2PSystemBaseParaView
 * @param {} title 			title    例:微信二维码
 * @param {} tempId         记录id   例:20
 * @param {} field			所删除的对象id   例:weixin
 * @param {} width_height   默认宽高
 * @param {} mark           mark     例:system_p2p
 */
var delLogoFile=function(id,title,tempId,field,width_height,mark){
	 var parent=Ext.getCmp(id);
     if(tempId){
     	var flag=false;
     	if(confirm('删除后将显示系统默认图片,确定要删除吗?')){
        	flag=true;
        }
        if(flag){
			Ext.Ajax.request({
				url : __ctxPath	+ '/system/delFileSystemProperties.do',
				method : 'post',
				params : {
					fileId : tempId
				},
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if(obj.success){
						Ext.ux.Toast.msg('操作信息',obj.msg);
						var display =parent.getCmpByName(field);
						display.setText('<img src="'+ __ctxPath+ '/images/nopic.jpg" width =140 height=80 /><a href="#" onClick =uploadLogoFile(\''+id+'\',\''+title+'\',\''+mark+'\',\''+field+'\',\''+width_height+'\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =delLogoFile(\''+id+'\',\''+title+'\',\'\',\''+field+'\',\''+width_height+'\',\''+mark+'\')>删除</a><font style="padding-left:20px;">图片大小： '+width_height+'</font>',false);
						parent.doLayout();
					}
				}
			});
        }
     }else{
     	Ext.ux.Toast.msg('操作信息','请先上传!');
     }
}