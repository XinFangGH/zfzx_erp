/**
 * @author
 * @createtime
 * @class SlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
SlBankAccountQlide = Ext.extend(Ext.Window, {
	
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				SlBankAccountQlide.superclass.constructor.call(this, {
							id : 'SlBankAccountQlideWin',
							region : 'center',
							layout : 'border',
							items : [this.gridPanel],
							modal : true,
							height : 589,
						    width : screen.width*0.95,
							border : false,
							autoScroll:true,
							maximizable : true,
							title : '我方账户流水明细'

						});
			},// end of the constructor
			// 初始化组件
			
			initUIComponents : function() {
		   	var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
							return '总计(元)';
						}
				this.topbar = new Ext.Toolbar({
							items : [
						{xtype:'label',html : '【<font style="line-height:20px" color=red>我方开户银行：</font>'+this.bankName}
						,{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp我方账户名称：</font>'+this.name}
						,{xtype:'label',html : '&nbsp;&nbsp&nbsp;&nbsp<font color=red>我方账号：</font>'+this.account+'】'}
									
						
							]
						});
				
				this.gridPanel = new HT.GridPanel({
					rowActions : false,
					border : false,
					viewConfig: {  
		            	forceFit:false  
		            },
					region : 'center',
					 plugins: [summary],
					bodyStyle : "width : 100%",
					width : 1214,
					id : 'SlFundQlideGridcheck',
					tbar : this.topbar,
					url : __ctxPath + "/creditFlow/finance/listbyaccountSlFundQlide.do?myaccount="+this.account,
					fields : [{
								name : 'fundQlideId',
								type : 'int'
							}	,'myAccount'
								,'dialMoney'
								,'afterMoney'
								,'notMoney'
								,'factDate'
								,'opAccount',
								'fundType',
								'currency',
								'transactionType',
								'bankName',
								'openName',
								'incomeMoney',
								'payMoney',
								'opOpenName',
								'opBankName',
								'isProject',
								'finalMoney',
								'bankTransactionTypeName',
								'frozenfinalMoney' ,
								'surplusfinalMoney'
																																				],
					columns : [{
                                 header : '币种',	
								dataIndex : 'currency',
								width :62,
								summaryRenderer: totalMoney
								},{
								header : '到账时间',	
								dataIndex : 'factDate',
								width : 130,
								sortable:true
								},{
								header : '收入金额',	
								dataIndex : 'incomeMoney',
								align : 'right',
								width : 120,
								summaryType: 'sum',
								renderer:function(v){
									if(v !=null){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
										}else{
										return v}
																			
							    }
								},{
								header : '支出金额',	
								dataIndex : 'payMoney',
								width : 120,
								summaryType: 'sum',
								align : 'right',
								renderer:function(v){
									if(v !=null){
										return Ext.util.Format.number(v,',000,000,000.00')+"元"
									return v+"元"
										}else{
										return v}
										
                              	}
								},{
								header : '期末总余额',	
								dataIndex : 'finalMoney',
								width : 120,
								align : 'right',
								renderer:function(v){
									if(v !=null){
										return Ext.util.Format.number(v,',000,000,000.00')+"元"
									return v+"元"
										}else{
										return v}
										
								}
								}	
/*//							,{
//								header : '可用期末余额',	
//								dataIndex : 'surplusfinalMoney',
//								width : 150,
//								align : 'right',
//								renderer:function(v){
//								if(v !=null){
//									return Ext.util.Format.number(v,',000,000,000.00')+"元"
//								return v+"元"
//								}else{
//									return v}
//																			
//								}
//								},{
//								header : '冻结期末余额',	
//								dataIndex : 'frozenfinalMoney',
//								width : 150,
//								align : 'right',
//								renderer:function(v){
//								if(v !=null){
//									return Ext.util.Format.number(v,',000,000,000.00')+"元"
//								return v+"元"
//								}else{
//									return v}
//																			
//								}
//								}									
*/								,{
								header : '已对账金额',
								align : 'right',
								dataIndex : 'afterMoney',
								width : 120,
								renderer:function(v){
									if(v !=null){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
										}else{
										return v}
										
                              	}																	
								},{
								header : '未对账金额',
								align : 'right',
								dataIndex : 'notMoney',
								width : 120,
								renderer:function(v){
									if(v !=null){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
										}else{
										return v}
										
                              	}
																	
								},{
	                             header:"对方开户银行",
	                             dataIndex:"opBankName"
									                                 
								},{
                                 header:"对方账户名称",
                                 dataIndex:"opOpenName"
									                                 
								},{
                                 header:"对方账号",
                                 dataIndex:"opAccount"
									                                 
								}
//								, {
//								header : "银行交易类型",
//								dataIndex : "bankTransactionTypeName"
//																	
//													
//									}
								,{
                                 header:"是否项目相关",
                                 dataIndex:"isProject"									                                 
								},{
	                             header : '交易摘要',	
								 dataIndex : 'transactionType'
								}
							]
						// end of columns
				});

			//	this.gridPanel.addListener('rowdblclick',this.rowClick);

			}
			

		});

