//PlateFormAccountManagerView  平台账户管理
UMPayBidFlowQuery = Ext.extend(Ext.Panel, {
			title:"",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				UMPayBidFlowQuery.superclass.constructor.call(this, {
							id : 'UMPayBidFlowQuery',
							title : "标的账户查询",
							region : 'center',
							layout : 'border',
							iconCls :'btn-tree-team39',
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
											anchor : anchor
										},
										items : [{
											fieldLabel : '标的Id',
											name : "bidId",
											xtype : 'textfield',
											labelSeparator : "",
											allowBlank : false
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

				
				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					//tbar : this.topbar,
					viewConfig: {  
		            	forceFit:false  
		            },
		            plugins : [summary],
		            isautoLoad:false,
		            forceFit: true,
		            loadMask : true,
					// 使用RowActions
					rowActions : false,
					id : 'UMPayCustFlowGrid',
					url :  __ctxPath +"/pay/bidFlowQueryPay.do",
					fields : [{
								name : 'id',
								type : 'int'
							}, 'thirdPayConfigId','amount','leftMoney', 'transferType', 'requestNo','status','time','fee'],
					columns : [{
								header : '订单流水号',
								summaryRenderer : totalMoney,
								dataIndex : 'requestNo'
							}, {
								header : '交易类型',
								dataIndex : 'transferType',
								width : 200
							}, {
								header : '交易时间',
								width : 150,
								dataIndex : 'time',
								align : 'center'
							}, {
								header : '交易金额',
								dataIndex : 'amount',
								width : 200,
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							}, {
								header : '手续费',
								dataIndex : 'fee',
								width : 200,
								align : 'right',
								summaryType : 'sum',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							}, {
								header : '剩余金额',
								dataIndex : 'leftMoney',
								width :200,
								align : 'right',
								summaryType : 'sum',
								renderer:function(v){
									if(v!=""){
										 return Ext.util.Format.number(v,',000,000,000.00')+"元"
									}else{
										 return "0元"
									}
                         	    }
							},{
								header : '交易状态',
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
				
			}
		});
