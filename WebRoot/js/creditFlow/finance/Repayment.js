Repayment = Ext.extend(Ext.Window,{

	constructor:function(_cfg){
		Ext.applyIf(this,_cfg);
		this.initUIComponents();
		Repayment.superclass.constructor.call(this,{
					id : 'FormDefFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 300,
					width : 500,
					maximizable : true,
					title : '还款',
					iconCls : 'menu-form',
					buttonAlign : 'center',
					buttons : [{
							text : '还款',
							iconCls : 'btn-save',
							scope : this,
							handler : this.save
						}
			]
		});
		
	},
	initUIComponents:function(){
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					border : false,
					frame:true,
					autoScroll : true,
					labelAlign  :'right',
					 id : 'FormRepaymentForm',
					defaults : {
						labelAlign  :'right',
						width:300
					},
					defaultType : 'textfield',
					items : [{
						id : 'fundIntentId',
						xtype : 'hidden',
						value : this.record.data.fundIntentId
					}, {
						fieldLabel : '项目名称',
						readOnly:true,
						id:'projectName',
						labelAlign  :'right',
						value:this.record.data.projectName
					}, {
						fieldLabel : '投资人',
						readOnly:true,
						id : 'investPersonName',
						labelAlign  :'right',
						value:this.record.data.investPersonName,
						xtype : 'textfield'
					}, {
						fieldLabel : '期数',
						id : 'payintentPeriod',
						labelAlign  :'right',
						value:"第"+this.record.data.payintentPeriod+"期",
						xtype : 'textfield'
					}, {
						fieldLabel : '订单号',
						readOnly:true,
						id : 'orderNum',
						labelAlign  :'right',
						value:this.record.data.orderNo,
						xtype : 'textfield'
					}, {
						fieldLabel : '还款金额',
						id : 'transAmt',
						labelAlign  :'right',
						value:this.record.data.notMoney,
						xtype : 'textfield'
					}, {
						fieldLabel : 'fundtype',
						id : 'fundtype',
						value:this.record.data.fundType,
						xtype : 'hidden'
					}]
				});
	},
				/**
			 * 取消
			 * 
			 * @param {}
			 *            window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {Ext.MessageBox.confirm('确认还款吗', '还款以后就不能恢复了', function(btn) {
					var fundType=Ext.getCmp('fundtype').getValue();
					var fundIntentId=Ext.getCmp('fundIntentId').getValue();
					var ids = Array();
					ids.push(fundIntentId);
					if (btn == 'yes') {
						var mk = new Ext.LoadMask('FormRepaymentForm',{  
			msg: '正在提交数据，请稍候！',  
			removeMask: true //完成后移除  
     });  
	 mk.show(); //显示  
			           Ext.Ajax.request( {
									url : __ctxPath + '/creditFlow/finance/repayMentListSlFundIntent.do',
									method : 'POST',
									scope : this,
									params : {
										ids : ids,
										postType:1,
										fundType:fundType
									},
									success : function(response, request) {
										mk.hide();
										var record = Ext.util.JSON.decode(response.responseText);
										 Ext.Msg.alert('状态', record.msg);
									},
									checkfail:function(response, request) {
										mk.hide();
										Ext.Msg.alert('状态', "还款失败");
									}
								});

		
					}
			
				})}// end of save
});