cashflowandSaleIncome = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	constructor : function(_cfg) {

		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		cashflowandSaleIncome.superclass.constructor.call(this, {
					id:'cashflowandSaleIncome',
			        buttonAlign:'center',
			        title:'企业现金流及销售收入',
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
		this.cashflowandSaleIncomeList =new cashflowandSaleIncomeList({
		
			isHiddenAddBtn:this.isReadOnly,
			isHiddenDelBtn:this.isReadOnly,
			isHiddenSeeBtn:false,
			enterpriseId:this.enterpriseId,
			isReadOnly:this.isReadOnly
		})

		this.formPanel = new Ext.FormPanel( {
			url :  __ctxPath + '/creditFlow/customer/enterprise/saveBpCustEntCashflowAndSaleIncome.do',
			layout : 'fit',
			border : false,
			items : this.cashflowandSaleIncomeList,
			modal : true,
			height : 345,
			width : 610
			
		})
		
	},
	save:function(){
			var win=this;
			var cashflowandSaleIncomeList=this.cashflowandSaleIncomeList;
			var cashflowandSaleIncomeListData=getcashflowandSaleIncomeData(cashflowandSaleIncomeList);
			this.formPanel.getForm().submit({
				method : 'POST',
				waitTitle : '连接',
				waitMsg : '消息发送中...',
				params : {enterpriseId:this.enterpriseId,
							'cashflowandSaleIncomeListData':cashflowandSaleIncomeListData
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
