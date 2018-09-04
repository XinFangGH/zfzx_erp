bppaytax = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	constructor : function(_cfg) {

		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		bppaytax.superclass.constructor.call(this, {
					id:'bppaytax',
			        buttonAlign:'center',
			        title:'纳税情况',
			        iconCls : 'btn-add',
					height : 500,
					width : 1000,
					constrainHeader : true ,
					collapsible : true, 
					frame : true ,
					border : false ,
					resizable : true,
					layout:'fit',
					autoScroll : true ,
					bodyStyle:'overflowX:hidden',
					constrain : true ,
					closable : true,
					modal : true,
					items : [this.formPanel],
					buttons:[{
					    text : '保存',
						iconCls : 'btn-save',
						scope : this,
						hidden : this.isReadOnly,
						handler : this.save
					},{
					    text : '关闭',
						iconCls : 'close',
						scope : this,
						handler : function(){
							this.close();
						}
					}]
					
				})
	},
	initUIComponents : function() {   
		this.bppaytaxList =new bppaytaxList({
		
			isHiddenAddBtn:this.isReadOnly,
			isHiddenDelBtn:this.isReadOnly,
			isHiddenSeeBtn:false,
			enterpriseId:this.enterpriseId,
			isReadOnly:this.isReadOnly
		})

		this.formPanel = new Ext.FormPanel( {
			url :  __ctxPath + '/creditFlow/customer/enterprise/saveBpCustEntPaytax.do',
			layout : 'fit',
			border : false,
			items : this.bppaytaxList,
			modal : true,
			height : 345,
			width : 610
			
		})
		
	},
	save:function(){
			var win=this;
			var bppaytaxList=this.bppaytaxList;
			var bppaytaxListData=getbppaytaxData(bppaytaxList);
			this.formPanel.getForm().submit({
				method : 'POST',
				waitTitle : '连接',
				waitMsg : '消息发送中...',
				params : {enterpriseId:this.enterpriseId,
							'bppaytaxListData':bppaytaxListData
							},
				success : function(form, action) {
					Ext.ux.Toast.msg('操作信息', '保存成功!');
					win.destroy();
				},
				failure : function(form, action) {
					Ext.ux.Toast.msg('操作信息', '保存失败!');
				}
			});				
		}		    
	
});
