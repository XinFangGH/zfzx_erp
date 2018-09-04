/**担保材料 单个附件上传	jiang*/
var singleUplaod_Materials_Fn = function(projectId, projectNum,callBackF){
	
	var fp = new Ext.FormPanel({
		url : 'materialsUpload.do',
        fileUpload: true,
        enctype:'multipart/form-data',
        width: 500,
        frame: true,
        autoHeight: true,
        bodyStyle: 'padding: 10px 10px 0 10px;',
        labelWidth: 50,
        defaults: {
            anchor: '95%',
            allowBlank: false,
            msgTarget: 'side'
        },
        items: [{
        	id: 'id_fileName',
            xtype: 'textfield',
            fieldLabel: 'Name',
            name: 'fileName',
            emptyText:'注意：标准上传压缩包结构为：“项目编号.zip/项目编号/担保材料”'
//            readOnly: true
        },{
            xtype: 'fileuploadfield',
            id: 'form-file',
            emptyText: 'Select a file',
            fieldLabel: 'File',
            name: 'fileUpload',
            buttonText: '',
            buttonCfg: {
                iconCls: 'uploadIcon'
            },
            listeners:{
	            'fileselected':function(t,v){
	            	Ext.getCmp('id_fileName').setValue(v);
	            }
            }
        }]
    });
    
    var win = new Ext.Window({
		layout : 'fit',
		width : 500,
		height : 150,
		closable : true,
		resizable : false,
		buttonAlign : 'center',
		plain : true,
		border : false,
		modal : true,
		items : [fp],
		title : '上传附件【仅限： zip打包文件】',
		collapsible : true,
		buttons : [{
			text : '上传',
			iconCls : 'uploadIcon',
			handler : function(){
				fp.getForm().submit({
					method : 'POST',
					waitTitle : '连接',
					waitMsg : '消息发送中...',
					success : function() {
						Ext.Msg.alert('状态', '上传成功!');
						win.destroy();
						if(!Ext.isEmpty(callBackF)){
							callBackF();
						}
								
					},
					failure : function(form, action) {
						Ext.Msg.alert('状态','上传失败!');								
		//							top.getForm().reset();
					},
					params : {
						projectId : projectId,
						projectNum : projectNum
					}
				});
			}
		},{
			text : '重置',
			iconCls : 'resetIcon',
			tooltip : 'reset',
			handler : function() {
				fp.getForm().reset();							
			}
		
		}]//buttons
    });
	
    win.show();
    
} 