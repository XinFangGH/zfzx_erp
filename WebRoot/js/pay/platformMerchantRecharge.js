//PlateFormAccountManagerView  平台账户管理
platformMerchantRecharge = Ext.extend(Ext.Panel, {
			title:"",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				platformMerchantRecharge.superclass.constructor.call(this, {
							id : 'platformMerchantRecharge',
							title : "商户充值",
							region : 'center',
							layout : 'border',
							 iconCls:"menu-finance",
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new HT.SearchPanel({
							layout : 'column',
							style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
							region : 'north',
							height : 20,
							anchor : '96%',
							layoutConfig: {
				               align:'middle',
				               padding : '5px'
				               
				            },
							items : [{
										columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : '100%'
										},
										items : [{
											fieldLabel : '起始日期',
											name : "startDate",
											xtype : 'datefield',
											labelSeparator : "",
											format : 'Y-m-d',
											value:new Date(),
											listeners:{
												scope:this,
												change :function(t,newDate,oldDate){
												 var endDate=new Date(newDate.getTime()+30*24*60*60*1000);
												  this.ownerCt.ownerCt.getCmpByName("endDate").setMaxValue(endDate);
												  
													
												}
											}
										}]
							         },{
							         	columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '截止日期',
											name : "endDate",
											xtype : 'datefield',
											labelSeparator : "",
											format : 'Y-m-d',
											maxValue :new Date(new Date().getTime()+30*24*60*60*1000),
											value:new Date()
										}]
							         },{
							         	columnWidth : .2,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '查询页码',
											name : "pageNumber",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },{
										columnWidth : .1,
										xtype : 'container',
										layout : 'form',
										defaults : {
											xtype : 'button'
										},
										style : 'padding-left:10px;',
										items : [{
											text : '查询',
											scope : this,
											iconCls : 'btn-search',
											handler : this.search
										}]
									},{
										columnWidth : .1,
										xtype : 'container',
										layout : 'form',
										defaults : {
											xtype : 'button'
										},
										style : 'padding-left:10px;',
										items : [{
											text : '重置',
											scope : this,
											iconCls : 'reset',
											handler : this.reset
										}]
									}]
						});

				this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-addmoney',
						text : '充值',
						xtype : 'button',
						scope : this,
						handler : this.recharge
			
					},new Ext.Toolbar.Separator({
					   hidden : isGranted('_ping_add_principalPay_2')?false:true
					})/*,{
						iconCls : 'btn-ok',
						text :'取现',
						xtype : 'button',
						scope : this,
						handler : this.withdraw
					}*/,new Ext.Toolbar.Separator({
						hidden : isGranted('_ping_f_principalPay_2')?false:true
					}) ,'->',{
					    xtype:'label',html : '【<font style="line-height:20px">账号：</font><font style="line-height:20px"></font> <font style="line-height:20px">账户余额：</font><font style="line-height:20px">0元</font>】'
				    },{
						iconCls : 'btn-setting',
						text :'刷新余额',
						xtype : 'button',
						scope : this,
						handler : this.refresh
					}]
				});
                var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					plugins : [summary],
					viewConfig: {  
		            	forceFit:false  
		            },
		            isautoLoad:false,
		            forceFit: true,
					// 使用RowActions
					rowActions : false,
					id : 'PlateFormAccountManagerGrid',
					url : __ctxPath + "/pay/plateFlowQueryPay.do",
					fields : [{
								name : 'id',
								type : 'int'
							}, 'thirdPayConfigId','amount','balance', 'bizType','markType', 'requestNo','status','time','fee'],
					columns : [{
								header : '订单流水号',
								dataIndex : 'requestNo',
								align:'center',
								width : 200
							},{
								header : '借贷方向',
								align:'center',
								summaryRenderer : totalMoney,
								width :100,
								dataIndex : 'markType'
							}, {
								header : '交易类型',
								align:'center',
								dataIndex : 'bizType',
								width :100
							}, {
								header : '交易时间',
								width : 150,
								align:'center',
								dataIndex : 'time',
								align : 'center'
							}, {
								header : '交易金额',
								align:'right',
								summaryType : 'sum',
								dataIndex : 'amount',
								width : 150,
								align : 'center',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							}, {
								header : '手续费',
								align:'right',
								summaryType : 'sum',
								dataIndex : 'fee',
								width : 100,
								align : 'center',
								renderer:function(v){
									if(v==null||v==""){
										v="0";
									}
								 	return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							},/* {
								header : '剩余金额',
								dataIndex : 'balance',
								width :200,
								align : 'right',
								renderer:function(v){
									if(v!=""){
										 return Ext.util.Format.number(v,',000,000,000.00')+"元"
									}else{
										 return "0元"
									}
                         	    }
							},*/{
								header : '交易状态',
								align:'center',
								width :100,
								align : 'center',
								dataIndex : 'status'
							}
						]
					});
			},
			
			reset : function() {
				this.searchPanel.getForm().reset();
				var obj = Ext.getCmp('Q_fundType_N_EQ');
				var arrStore= new Ext.data.SimpleStore({});
				obj.clearValue();
                obj.store = arrStore;
			    arrStore.load({"callback":test});
			    function test(r){
			    	if (obj.view) { // 刷新视图,避免视图值与实际值不相符
			    		obj.view.setStore(arrStore);
			        }
			   }
			},
			// 按条件搜索
			search : function() {
				var startDate=this.searchPanel.getCmpByName("startDate").getValue();
				var endDate=this.searchPanel.getCmpByName("endDate").getValue();
				if(startDate==null||startDate==""){
					this.searchPanel.getCmpByName("startDate").setValue(new Date());
				}
				if(endDate==null||endDate==""){
					this.searchPanel.getCmpByName("endDate").setValue(new Date());
				}
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			},
			
			rowClick:function(){
				
			},
		//
			refresh:function(){
				var accountType=this.accountType;
				var topbar=this.topbar;
				var loadMask = new Ext.LoadMask(Ext.getBody(), {
				 	msg  : '正在加载数据中······,请稍候······'
				});
			    loadMask.show(); //显示
				Ext.Ajax.request({
					url: __ctxPath + '/pay/getPlatformMoneyPay.do',
					scope : this,
					method : 'post',
					success : function(response){debugger
						var result = Ext.util.JSON.decode(response.responseText)
						var code=result.msg;
						topbar.items.get(topbar.items.length-2).setText(code,false);
						loadMask.hide();
					},
					failure : function(){
						Ext.ux.Toast.msg('操作信息','操作出错,请联系管理员');
						loadMask.hide();
					}
				});
			},
			recharge :function(){//充值方法
				var accountType=this.accountType;
				var refreshPanel=this.gridPanel;
				var createnewForm=new platformRechargeForm({
					 refreshPanel:refreshPanel
				});
				createnewForm.show();
			},
			withdraw:function(){
				var accountType=this.accountType;
				var refreshPanel=this.gridPanel;
				var createnewForm=new umPayWithDrawForm({
								refreshPanel:refreshPanel //表示充值页面是否需要用window.open 打开页面 0表示不需要，1需要
							});
							createnewForm.show();
			}
		});
