/**
 * @author YungLocke
 */
ReMessageWin=Ext.extend(Ext.Window,{
	formPanel:null,
	constructor:function(_cfg){
	   Ext.applyIf(this,_cfg);
	   this.initUI();
	   ReMessageWin.superclass.constructor.call(this,{
		    id:'windowRe ',
			iconCls:'btn-mail_reply',
			title:'回复',
			width:280,
			height:200,
			x: 10,
	        y:375,
	        border:false,
		    items:this.formPanel,
			buttons : [ {
				text : '发送',
				iconCls: 'menu-mail_send',
				handler : this.send.createCallback(this,this.formPanel)

			}, {
				text : '重置',
				iconCls:'reset',
				handler : this.reset.createCallback(this.formPanel)
			} ]
	   });
     },
     initUI:function(){
    	 this.formPanel = new Ext.form.FormPanel( {
    			id : 'mmFormPanel',
    			frame : false,
    			bodyStyle : 'padding:5px 20px 0',
    			width : 275,
    			height : 180,
    			defaultType : 'textarea',
    			url : __ctxPath + '/info/sendShortMessage.do',
    			layout:'absolute',
    			items : [{
    					xtype : 'hidden',
    					name : 'userId',
    					id : 'ReMessageWin.userId',
    					value:this.id
    				}, {
    					x:0,
    					y:10,
    					xtype:'label',
    					text:'收信人:'
    				},
    				{
    					    x:40,
    					    y:10,
    						xtype : 'field',
    						width : 200,
    						name : 'userFullname',
    						id : 'ReMessageWin.userFullname',
    						allowBlank : false,
    						readOnly : true,
    						value:this.name
    				}, 
    			       {
    			    	   x:0,
    			    	   y:40,
    			    	   xtype:'label',
    			    	   text:'内容:'
    			       }, { x:40,
    			    	    y:40,
    						id : 'ReMessageWin.sendContent',
    						xtype : 'textarea',
    						width : 200,
    						name : 'content',
    						allowBlank:false
    			       } ]
    		});
     },
     send:function(self,panel){
    	 var message =panel;
			if (message.getForm().isValid()) {
				message.getForm().submit( {
					waitMsg : '正在 发送信息',
					success : function(message, o) {
						Ext.ux.Toast.msg('操作信息', '信息发送成功！');
						self.close();
					}
				});
			}
     },
     reset:function(panel){
    	 panel.getForm().findField("content").reset();
     }
});
