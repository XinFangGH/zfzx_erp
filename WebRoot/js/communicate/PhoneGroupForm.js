
PhoneGroupForm = Ext.extend(Ext.Window,{
    formPanel : null,
	constructor : function(_cfg){
	    Ext.applyIf(this,_cfg);
	    this.initUI();
	    PhoneGroupForm.superclass.constructor.call(this,{
	        id : 'PhoneGroupFormWin',
			title : '通讯分组详细信息',
			iconCls : 'menu-phonebook',
			width : 300,
			height : 150,
			modal : true,
			layout : 'fit',
			border : false,
			buttonAlign : 'center',
			items : this.formPanel,
			buttons : this.buttons,
			keys : {
	    		key : Ext.EventObject.ENTER,
	    		fn : this.saveRecord,
	    		scope : this
	    	}
	    });
	},
	initUI:function(){
		this.formPanel = new Ext.FormPanel( {
			url : __ctxPath + '/communicate/savePhoneGroup.do',
			layout : 'form',
			id : 'PhoneGroupForm',
			defaultType : 'textfield',
			bodyStyle : 'padding:5px;',
			items : [ {
				name : 'phoneGroup.groupId',
				id : 'groupId',
				xtype : 'hidden',
				value : this.groupId == null ? '' : this.groupId
			}, {
				fieldLabel : '分组名称',
				name : 'phoneGroup.groupName',
				id : 'groupName',
				width:140,
				allowBlank :false
			}, {
	           	 xtype : 'combo',
	           	 fieldLabel: '是否共享*',
	             hiddenName: 'phoneGroup.isShared',
	             id:'isShared',
	             value:'0',
	             mode : 'local',
				 editable : false,
				 allowBlank :false,
				 triggerAction : 'all',
				 store : [['1','是'],['0','否']],
				 width:80
	        }, {
	            xtype:'hidden',
				name : 'phoneGroup.sn',
				id : 'sn',
				width:80
			}, {
	            xtype:'hidden',
				name : 'phoneGroup.isPublic',
				value : this.isPublic
			}]
		});
	
		if (this.groupId != null && this.groupId != 'undefined') {
			this.formPanel.getForm().load({
				deferredRender : false,
				url : __ctxPath + '/communicate/getPhoneGroup.do?groupId=' + this.groupId,
				method : 'post',
				waitMsg : '正在载入数据...',
				success : function(form, action){
				},
				failure : function(form, action) {
					Ext.ux.Toast.msg('编辑', '载入失败');
				}
			});
		}
		
		this.buttons=[ {
			text : '保存',
			iconCls : 'btn-save',
			scope : this,
			handler : this.saveRecord
		}, {
			text : '取消',
			iconCls : 'btn-cancel',
			scope : this,
			handler : function() {
				this.close();
			}
		} ];
	},
	saveRecord : function(){
	    var fp = this.formPanel;
	    var win = this;
		if (fp.getForm().isValid()) {
			fp.getForm().submit( {
				method : 'post',
				waitMsg : '正在提交数据...',
				success : function(fp, action) {
					Ext.ux.Toast.msg('操作信息', '成功信息保存！');
					var phoneGroupTree=Ext.getCmp('leftBookPanel');
					if(phoneGroupTree!=null){
						phoneGroupTree.root.reload();
					}
					win.close();
				},
				failure : function(fp, action) {
					Ext.MessageBox.show( {
						title : '操作信息',
						msg : '信息保存出错，请联系管理员！',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
					win.close();
				}
			});
		}
	}

});
