//compencsatoryFiance.js
/**
 *	compencsatoryFiance.js
 *	散标代偿款项台账
 *	主要作用：查询出代偿的款项并计算出代偿后产生的罚息，同时登记借款人偿还代偿款
 *  add by linyan  2015-10-26
 */
  compensatoryFiance = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				compensatoryFiance.superclass.constructor.call(this, {
							id : 'compensatoryFiance_'+this.planType+this.backFlow+this.backFlowRecord+this.subType,
							region : 'center',
							layout : 'border',
							title:this.title==null?'':'代偿追偿提醒',
							iconCls : 'btn-tree-team30',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				var tabflag=this.tabflag;
               	var labelsize=100;
				var labelsize1=115;
				var isShow=false;
				if(RoleType=="control"){
				  isShow=true;
				}
				this.searchPanel = new HT.SearchPanel({
							layout : 'column',
							style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
							region : 'north',
							height : 40,
							anchor : '96%',
							keys : [{
								key : Ext.EventObject.ENTER,
								fn : this.search,
								scope : this
							}, {
								key : Ext.EventObject.ESC,
								fn : this.reset,
								scope : this
							}],
							layoutConfig: {
				               align:'middle',
				               padding : '5px'
				               
				            },
							items : [ {   
								columnWidth : 0.2,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
											fieldLabel : '招标项目名称',
											name : 'bidPlanname',
											flex : 1,
											editable : false,
											width:105,
											xtype :'textfield',
											anchor : '100%'
										},{
											fieldLabel : '招标项目编号',
											name : 'bidPlanNum',
											flex : 1,
											editable : false,
											width:105,
											xtype :'textfield',
											anchor : '100%'
										
										}]
							},{   
								columnWidth : 0.2,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
										fieldLabel : '代垫天数：从',
										name : 'compensatoryDaysS',
										flex : 1,
										width:105,
										xtype :'numberfield',
										anchor : '100%'
										
										},{
										fieldLabel : '代垫金额：从',
										name : 'compensatoryMoneyS',
										flex : 1,
										width:105,
										xtype :'numberfield',
										anchor : '100%'
										
										}]
							},{ 
							    columnWidth : 0.17,
								layout : 'form',
								border : false,
								labelWidth : labelsize-55,
								labelAlign : 'right',
								items : [{
										fieldLabel : '到',
										name : 'compensatoryDaysE',
										flex : 1,
										width:105,
										xtype :'numberfield',
										anchor : '100%'
										
								},{
										fieldLabel : '到',
										name : 'compensatoryMoneyE',
										flex : 1,
										width:105,
										xtype :'numberfield',
										anchor : '100%'
										
								}]
							},{   columnWidth : 0.2,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
									
										fieldLabel : '付款人名称',
										name : 'custmerName',
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '100%'
										
								
										
								}]}
							, {
								columnWidth : .07,
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
								}]}
								, {
								columnWidth : .07,
								xtype : 'container',
								layout : 'form',
								defaults : {
									xtype : 'button'
								},
								style : 'padding-left:10px;',
								items : [{
									text : '重置',
									scope : this,
									iconCls : 'btn-reset',
									handler : this.reset
								}]}	

							]

						});// end of searchPanel

				this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-xls',
						text :'导出Excel',
						xtype : 'button',
						hidden:false,
						scope : this,
						handler : this.toExcel
					}, '-',{
						iconCls : 'btn-setting2',
						text :'代偿回收流水',
						xtype : 'button',
						hidden:this.backFlow,
						scope : this,
						handler : this.plateCompensatory
					}, '-', {
						iconCls : 'btn-write',
						text : '线下代偿回收登记',
						xtype : 'button',
						scope : this, 
						hidden:this.backFlowRecord,
						handler : this.backCompensatoryrecord
					}
					]
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
					// 使用RowActions
					rowActions : false,
					id : 'compencsatoryFianceGird',
					//isautoLoad:true,
					url : __ctxPath + "/compensatory/listPlBidCompensatory.do?planType="+this.planType,
					fields : ['id','loanerp2pId','custmerId','custmerType','planId','compensatoryMoney','compensatoryType',					
							'compensatoryName','compensatoryP2PId','payintentPeriod','requestNo','compensatoryDate','compensatoryDays',
						    'punishRate','plateMoney','punishMoney','backPunishMoney','backCompensatoryMoney', 'backDate','backStatus',
						    'custmerName','bidPlanname','bidPlanNumber','totalMoney'
							],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								align:'center',
								hidden : true
							}, {
								header : '付款人',
								dataIndex : 'custmerName',
								width : 100
							}
							, {
								header : '招标项目名称',
								summaryRenderer : totalMoney,
								dataIndex : 'bidPlanname',
								width : 120
							}, {
								header : '招标项目编号',
								dataIndex : 'bidPlanNumber',
							
								width : 120
							}, {
								header : '代偿期数',
								dataIndex : 'payintentPeriod',
								align : 'center',
								width : 100,
								renderer:function(v){
								  return "第"+v+"期";
								}
							
							}, {
								header : '代偿方',
								dataIndex : 'compensatoryName',
								width : 120
							},{
								header : '代偿日期',
								dataIndex : 'compensatoryDate',
								width:80,
								align : 'center'
							},{
								header : "代偿金额",
								dataIndex : 'compensatoryMoney',
								align :'right',
								width:110,
								summaryType : 'sum',
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : '代偿天数',
								dataIndex : 'compensatoryDays',
								align : 'center',
								summaryType : 'sum',
								width : 100,
								renderer:function(v){
								  return v+"天";
								}
							
							},{
								header : "罚息总金额",
								dataIndex : 'punishMoney',
								align :'right',
								summaryType : 'sum',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "已偿还罚息总金额",
								dataIndex : 'backPunishMoney',
								align :'right',
								summaryType : 'sum',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "已偿还代垫总金额",
								dataIndex : 'backCompensatoryMoney',
								align :'right',
								summaryType : 'sum',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "已平账总金额",
								dataIndex : 'plateMoney',
								align :'right',
								summaryType : 'sum',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							},{
								header : "待偿还总金额",
								dataIndex : 'totalMoney',
								align :'right',
								summaryType : 'sum',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							}, {
								header : '最近一次还款日期',
								dataIndex : 'backDate',
								width:80,
								format : 'Y-m-d',
								align : 'center'
							}]
					});


			},	
			reset : function() {
				this.searchPanel.getForm().reset();
				var obj = Ext.getCmp('Q_fundType_N_EQ'+tabflag);
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
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			},
			//回款记录
			plateCompensatory:function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					var record = s[0];
					var compensatoryId=record.data.id;
					new backCompensatoryFlow({
							compensatoryId:compensatoryId
							
						}).show();
				}
			},
			
			//登记回款记录
			backCompensatoryrecord:function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					var record = s[0];
					var totalMoney = record.data.totalMoney;
					var compensatoryId=record.data.id;
					if(eval(totalMoney)<eval(0)){
						  Ext.ux.Toast.msg('操作信息', '未偿付金额应大于0元');
						  return ;
					}else{
						new backCompensatoryRecord({
							compensatoryId:compensatoryId,
							flashTargat:this.gridPanel.getStore()
						}).show();
					}
					
				}
			},
			//导出到Excel
	toExcel : function(){
		var bidPlanname=this.searchPanel.getCmpByName('bidPlanname').getValue();
		var bidPlanNum=this.searchPanel.getCmpByName('bidPlanNum').getValue();
		var compensatoryDaysS=this.searchPanel.getCmpByName('compensatoryDaysS').getValue();
		var compensatoryDaysE=this.searchPanel.getCmpByName('compensatoryDaysE').getValue();
		var compensatoryMoneyS=this.searchPanel.getCmpByName('compensatoryMoneyS').getValue();
		var compensatoryMoneyE=this.searchPanel.getCmpByName('compensatoryMoneyE').getValue();
		var custmerName=this.searchPanel.getCmpByName('custmerName').getValue();
		window.open(__ctxPath + "/compensatory/listOverDueToExcelPlBidCompensatory.do?"
		        +"bidPlanname="+bidPlanname
		        +"&bidPlanNum="+bidPlanNum
				+"&compensatoryDaysS="+compensatoryDaysS
				+"&compensatoryDaysE="+compensatoryDaysE
				+"&compensatoryMoneyS="+compensatoryMoneyS
				+"&compensatoryMoneyE="+compensatoryMoneyE
				+"&custmerName="+custmerName
				);
	}
	
	
});
 