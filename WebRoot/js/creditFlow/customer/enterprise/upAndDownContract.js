upAndDownContract = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	constructor : function(_cfg) {

		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		upAndDownContract.superclass.constructor.call(this, {
					id:'upAndDownContract',
			        buttonAlign:'center',
			        title:'上下游渠道合同',
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
		this.DownStreamContract =new DownStreamContract({
		
			isHiddenAddBtn:this.isReadOnly,
			isHiddenDelBtn:this.isReadOnly,
			isHiddenSeeBtn:false,
			upAndDownContractId:this.upAndDownContractId,
			isReadOnly:this.isReadOnly
		})
		
		this.UpStreamContract=new UpStreamContract({

			isHiddenAddBtn:this.isReadOnly,
			isHiddenDelBtn:this.isReadOnly,
			isHiddenSeeBtn:false,
			upAndDownContractId:this.upAndDownContractId,
			isReadOnly:this.isReadOnly
		})
		this.formPanel = new Ext.FormPanel( {
			url :  __ctxPath + '/creditFlow/customer/enterprise/saveBpCustEntUpanddowncontract.do',
			monitorValid : true,
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true ,
			border : false,
			layout : 'form',
			labelWidth:75,
			items : [{
				name : 'bpCustEntUpanddownContract.upAndDownContractId',
				xtype : 'hidden'
			},{
				name : 'bpCustEntUpanddownContract.enterpriseId',
				xtype : 'hidden'
			},
			         {
					xtype : 'fieldset',
					title : '上游渠道合同',
					name:"upStreamContract",
					collapsible : true,
					autoHeight : true,
					bodyStyle : 'padding-left: 0px',
					items : [this.UpStreamContract, {
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						items : [{
										xtype : "textarea",
										name : "bpCustEntUpanddownContract.upremarks",
										fieldLabel : '上游渠道分析',
										anchor : '100%',
										readOnly:this.isReadOnly
														
									}]
					}]
				},
				{
					xtype : 'fieldset',
					title : '下游渠道合同',
					name:"downStreamContract",
					autoHeight : true,
					bodyStyle : 'padding-left: 0px',
					items : [this.DownStreamContract ,{
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						items : [{
										xtype : "textarea",
										name : "bpCustEntUpanddownContract.downremarks",
										fieldLabel : '下游渠道分析',
										anchor : '100%',
										readOnly:this.isReadOnly
														
									}]
					}]
				}
				]
			/*,*/
		})
		if (this.upAndDownContractId != null && this.upAndDownContractId != 'undefined') {
			var panel=this;
			this.formPanel.loadData( {
				url :	__ctxPath + '/creditFlow/customer/enterprise/getebyEnterpriseIdBpCustEntUpanddowncontract.do?enterpriseId=' + this.enterpriseId,
				root : 'data',
				preName : 'bpCustEntUpanddownContract',
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
	
			var UpStreamContract=this.UpStreamContract;
			var UpContractInfoData=getupAnddownDataContract(UpStreamContract);
			var DownStreamContract=this.DownStreamContract;
			var DownContractInfoData=getupAnddownDataContract(DownStreamContract);
			this.formPanel.getForm().submit({
				method : 'POST',
				waitTitle : '连接',
				waitMsg : '消息发送中...',
				params : {enterpriseId:this.enterpriseId,
							'UpContractInfoData':UpContractInfoData,
							'DownContractInfoData':DownContractInfoData},
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
