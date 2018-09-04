
/**
 * compensatoryManager.js 
 * 散标代偿管理
 * 主要功能：查询出来逾期的款项，进行代偿款项（平台和担保用户代偿）
 * add by linyan 2015-10-26 
 */
 compensatoryManager = Ext.extend(Ext.Panel, {
 	        querysql:"&subType=underline", //查询条件，默认查询全部的直投项目
			// 构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
				_cfg = {};
				}
				if (typeof(_cfg.subType) != "undefined" && _cfg.subType=="onlineopen") {//查询线上借款生成的直投项目
					this.querysql="&subType=online";
					this.subType= _cfg.subType;
				}else if (typeof(_cfg.subType) != "undefined" && _cfg.subType=="all") {//查询全部的直投项目
					this.querysql="";
					this.subType= _cfg.subType;
				}
				if(typeof(_cfg.proType)!="undefined"){
				      this.proType=_cfg.proType;
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				compensatoryManager.superclass.constructor.call(this, {
							id : 'compensatoryManager'+this.mark+this.subType,
						//	title : '散标代偿管理',
							region : 'center',
							layout : 'border',
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
											name : 'bidPlanName',
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
										fieldLabel : '逾期天数：从',
										name : 'punishDaysS',
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
										name : 'punishDaysE',
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
									
										fieldLabel : '借款人',
										name : 'borrowName',
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
						iconCls : 'btn-setting5',
						text :'保证金垫付',
						xtype : 'button',
						scope : this,
						hidden : this.mark=='P_3' || this.mark=='C_3' ? true : false,
						handler : this.plateCompensatory
					}/*, '-', {
						iconCls : 'btn-detail',
						text : '担保户代偿',
						xtype : 'button',
						scope : this,
						handler : this.guanteeCompensatory
					}*/
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
					id : 'compensatoryGird'+this.mark,
					isautoLoad:true,
					url : __ctxPath + "/creditFlow/finance/listPeriodbyOverDueSlFundIntent.do?proType="+this.proType+this.querysql,
					fields : ['intentDate',
							'principalRepayment.afterMoney','principalRepayment.incomeMoney','principalRepayment.accrualMoney',
							'loanInterest.afterMoney','loanInterest.incomeMoney','loanInterest.accrualMoney',					
							'consultationMoney.afterMoney','consultationMoney.incomeMoney','consultationMoney.accrualMoney',							
							'serviceMoney.afterMoney','serviceMoney.incomeMoney','serviceMoney.accrualMoney',
						    'interestPenalty.afterMoney','interestPenalty.incomeMoney','interestPenalty.accrualMoney',
							 'factDate','accrualMoney', 'bidPlanName','bidPlanProjectNum'
							,'ptpborrowId','ptpborrowName','borrowName','borrowId','punishDays','repaySource','planId','authorizationStatus',
							'payintentPeriod','ids'],
					columns : [{
								header : 'planId',
								dataIndex : 'planId',
								summaryRenderer : totalMoney,
								hidden : true
							}, {
								header : (this.proType == 'B_Dir' || this.proType == 'P_Dir') ? '借款人' : '原始债权人',
								dataIndex : 'borrowName',
								align:'center',
								summaryType : 'count',
								renderer : function(v) {
					                 return v+"人";
				                   },
								width : 100
							},{
								header : '债权持有人',
								dataIndex : 'ptpborrowName',
								summaryType : 'count',
								renderer : function(v) {
					                 return v+"人";
				                   },
								width : 100,
								hidden : (this.proType == 'B_Dir' || this.proType == 'P_Dir') ? true : false
							}
							, {
								header : '招标项目名称',
								dataIndex : 'bidPlanName',
								width : 120
							}, {
								header : '招标项目编号',
								dataIndex : 'bidPlanProjectNum',
							
								width : 120
							}, {
								header : '期数',
								align :'center',
								dataIndex : 'payintentPeriod',
								width : 100,
								renderer:function(v){
								  return "第"+v+"期";
								}
							
							}, {
								header : "本金",
								dataIndex : 'principalRepayment.incomeMoney',
								align :'right',
								summaryType : 'sum',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "利息",
								dataIndex : 'loanInterest.incomeMoney',
								align :'right',
								width:110,
								summaryType : 'sum',
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "管理咨询费",
								dataIndex : 'consultationMoney.incomeMoney',
								align :'right',
								summaryType : 'sum',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							} ,{
								header : "财务服务费",
								dataIndex : 'serviceMoney.incomeMoney',
								align :'right',
								summaryType : 'sum',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							},{
								header : "补偿息",
								dataIndex : 'interestPenalty.incomeMoney',
								align :'right',
								summaryType : 'sum',
								width:110,
								renderer:function(v){
									return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							}, {
								header : "逾期天数",
								summaryType : 'sum',
								dataIndex : 'punishDays',
								align :'center',
								width:80,
								renderer:function(v){
									return v+"天";
                         	     }
							
							}, {
								header : "罚息",
								dataIndex : 'loanInterest.incomeMoney',
								align :'right',
								width:80,
								renderer:function(v,a,r){
								return Ext.util.Format.number((r.get("principalRepayment.accrualMoney")+
									r.get("loanInterest.accrualMoney")+r.get("consultationMoney.accrualMoney")+r.get("serviceMoney.accrualMoney")),',000,000,000.00')+"元"
                         	     }

							
							}, {
								header : "合计",
								dataIndex : 'loanInterest.incomeMoney',
								align :'right',
								width:110,
								renderer:function(v,a,r){
									return Ext.util.Format.number((r.get("principalRepayment.incomeMoney")+
									r.get("loanInterest.incomeMoney")+r.get("consultationMoney.incomeMoney")+r.get("serviceMoney.incomeMoney")+r.get("interestPenalty.incomeMoney")+r.get("principalRepayment.accrualMoney")+
									r.get("loanInterest.accrualMoney")+r.get("consultationMoney.accrualMoney")+r.get("serviceMoney.accrualMoney")),',000,000,000.00')+"元"
                         	     }
							
							}, {
								header : '计划还款日',
								dataIndex : 'intentDate',
								width:80,
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
			//平台代偿
			plateCompensatory:function(){
			 //弹出还款计划
				var s = this.gridPanel.getSelectionModel().getSelections();
				var thisPanel = this.gridPanel;
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					var record = s[0];
					if((record.get('principalRepayment.afterMoney')+record.get('loanInterest.afterMoney')+record.get('serviceMoney.afterMoney')+record.get('consultationMoney.afterMoney')+record.get('interestPenalty.afterMoney'))==(record.get('principalRepayment.incomeMoney')+record.get('loanInterest.incomeMoney')+record.get('serviceMoney.incomeMoney')+record.get('consultationMoney.incomeMoney')+record.get('interestPenalty.incomeMoney'))){
						  Ext.ux.Toast.msg('操作信息', '借款人当期应还金额已经全部还清，不需要进行平台垫付');
						  return ;
						}
					Ext.Msg.confirm("操作提示","是否平台垫付",function(btn){
						if(btn=="yes"){
							Ext.MessageBox.wait("还款进行中,请稍后.......");
										var planId = record.data.planId;
										var intentDate=record.data.intentDate;
										var ids=record.data.ids;
										var ptpborrowId=record.data.ptpborrowId;
										var periodId = record.data.payintentPeriod
										Ext.Ajax.request({
														url: __ctxPath + '/pay/reservePay.do',
														mothed:'POST',
														waitMsg : '数据正在提交，请稍后...',
														success:function(response){
															var res = Ext.util.JSON.decode(response.responseText);
															thisPanel.getStore().reload();
															Ext.ux.Toast.msg('操作信息', res.msg);
															Ext.MessageBox.hide();
														},
														failure:function(response){
														},
														params:{
															ids:ids,
															planId:planId,
															intentDate:intentDate,
															ptpborrowId:ptpborrowId,
															periodId:periodId
														}
													});
						}
					})
				}
			
			
			},
			
			//担保户代偿
			guanteeCompensatory:function(){
			
			},
					//导出到Excel
	toExcel : function(){
		var bidPlanName=this.searchPanel.getCmpByName('bidPlanName').getValue();
		var punishDaysS=this.searchPanel.getCmpByName('punishDaysS').getValue();
		var punishDaysE=this.searchPanel.getCmpByName('punishDaysE').getValue();
		var borrowName=this.searchPanel.getCmpByName('borrowName').getValue();
		window.open(__ctxPath + "/creditFlow/finance/listOverDueToExcelSlFundIntent.do?"
		        +"bidPlanName="+bidPlanName
				+"&punishDaysS="+punishDaysS
				+"&punishDaysE="+punishDaysE
				+"&borrowName="+borrowName
				+"&proType="+this.proType
				+this.querysql
				);
	}
	
	
});
 
 
