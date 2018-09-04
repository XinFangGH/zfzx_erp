upAndDownStream = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	constructor : function(_cfg) {

		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		upAndDownStream.superclass.constructor.call(this, {
					id:'upAndDownStream',
			        buttonAlign:'center',
			        title:'上下游客户信息',
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
		this.DownStreamCustom =new DownStreamCustom({
		
			isHiddenAddBtn:this.isReadOnly,
			isHiddenDelBtn:this.isReadOnly,
			isHiddenSeeBtn:false,
			upAndDownCustomId:this.upAndDownCustomId,
			isReadOnly:this.isReadOnly
		})
		
		this.UpStreamCustom =new UpStreamCustom({

			isHiddenAddBtn:this.isReadOnly,
			isHiddenDelBtn:this.isReadOnly,
			isHiddenSeeBtn:false,
			upAndDownCustomId:this.upAndDownCustomId,
			isReadOnly:this.isReadOnly
		})
		this.formPanel = new Ext.FormPanel( {
			url :  __ctxPath + '/creditFlow/customer/enterprise/saveBpCustEntUpanddownstream.do',
			monitorValid : true,
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true ,
			border : false,
			layout : 'form',
			labelWidth:75,
			items : [
			         {
					xtype : 'fieldset',
					title : '主要上游客户及结算方式',
					name:"personHouseInfo",
					collapsible : true,
					autoHeight : true,
					bodyStyle : 'padding-left: 0px',
					items : [this.UpStreamCustom]
				},{
					xtype : 'fieldset',
					title : '主要下游客户及结算方式',
					name:"personCarInfo",
					collapsible : true,
					autoHeight : true,
					bodyStyle : 'padding-left: 0px',
					items : [this.DownStreamCustom]
				},
				{
					xtype : 'fieldset',
					title : '',
					collapsible : true,
					autoHeight : true,
					layout : 'column',
					bodyStyle : 'padding-left: 0px',
					items : [{
					columnWidth : .5,
					layout : 'form',
					//labelWidth : 70,
					defaults : {
						anchor : anchor
					},
					items : [ {
						name : 'bpCustEntUpanddownstream.upAndDownCustomId',
						xtype : 'hidden'
					},{
						name : 'bpCustEntUpanddownstream.enterpriseId',
						xtype : 'hidden'
					}]
				}, {
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					items : [{
									xtype : "textarea",
									name : "bpCustEntUpanddownstream.remarks",
									fieldLabel : '备注',
									anchor : '100%',
									readOnly:this.isReadOnly
													
								}]
				}]
				}]
			/*,*/
		})
		if (this.upAndDownCustomId != null && this.upAndDownCustomId != 'undefined') {
			var panel=this;
			this.formPanel.loadData( {
				url :	__ctxPath + '/creditFlow/customer/enterprise/getebyEnterpriseIdBpCustEntUpanddownstream.do?enterpriseId=' + this.enterpriseId,
				root : 'data',
				preName : 'bpCustEntUpanddownstream',
				success : function(response, options) {
					//Ext.util.JSON.decode(response.responseText).data.grossasset
							var respText = response.responseText;
							var alarm_fields = Ext.util.JSON.decode(respText);
					//		panel.getCmpByName('grossasset').setValue(Ext.util.Format.number(alarm_fields.data.grossasset,'0,000.00'));
					}
			});
		}
	},
	save:function(){
			var win=this;
	
			var UpStreamCustom=this.UpStreamCustom;
			var UpstreamInfoData=getupAnddownData(UpStreamCustom);
			var DownStreamCustom=this.DownStreamCustom;
			var DownstreamInfoData=getupAnddownData(DownStreamCustom);
			this.formPanel.getForm().submit({
				method : 'POST',
				waitTitle : '连接',
				waitMsg : '消息发送中...',
				params : {enterpriseId:this.enterpriseId,
							'UpstreamInfoData':UpstreamInfoData,
							'DownstreamInfoData':DownstreamInfoData},
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
