/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
synthesizeSlFundIntent = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg.businessType)!="undefined")
						{
						      this.businessType=_cfg.businessType;
						}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				synthesizeSlFundIntent.superclass.constructor.call(this, {
							id : 'synthesizeSlFundIntent'+this.businessType,
							title : '综合收款台账',
							region : 'center',
							layout : 'border',
							iconCls :'btn-team2',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				var isShow=false;
				if(RoleType=="control"){
				  isShow=true;
				}
				this.searchPanel = new HT.SearchPanel({
							layout : 'column',
							region : 'north',
							height : 20,
							anchor : '100%',
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
				               align:'middle'
				            },
							items : [
								 { 
								columnWidth : 0.29,
								layout : 'form',
								border : false,
								labelWidth : 100,
								labelAlign : 'right',
								items : [ {
									 labelWidth:70,    
										fieldLabel : '项目名称',
										name : 'Q_proj_Name_N_EQ',
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '96%'
										
								},{
										fieldLabel : '项目编号',
										name : 'Q_projNum_N_EQ',
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '96%'
										
								}] 
								      
							},
							{   columnWidth : 0.22,
								layout : 'form',
								border : false,
								labelWidth : 100,
								labelAlign : 'right',
								items : [ {
								     fieldLabel : '计划到账日期：从',
								     labelSeparator : '',
										name : 'Q_intentDate_D_GE',
										xtype : 'datefield',
										format : 'Y-m-d',
										anchor : '96%'
										
									},{
								     fieldLabel : '到',
								     labelSeparator : '',
										name : 'Q_intentDate_D_LE',
										xtype : 'datefield',
										format : 'Y-m-d',
										anchor : '96%'
										
									},{
										name : 'flagMoney',
										xtype : 'hidden',
										anchor : '96%'
									}] 
								      
							},{   columnWidth : 0.22,
								layout : 'form',
								border : false,
								labelWidth : 100,
								labelAlign : 'right',
								items : [ {
								     fieldLabel : '实际到账日期：从',
								     labelSeparator : '',
										name : 'startFactDate',
										xtype : 'datefield',
										format : 'Y-m-d',
										anchor : '96%'
										
									},{
								     fieldLabel : '到',
								     labelSeparator : '',
										name : 'endFactDate',
										xtype : 'datefield',
										format : 'Y-m-d',
										anchor : '96%'
										
									},{
										name : 'flagMoney',
										xtype : 'hidden',
										anchor : '96%'
										
									}] 
								      
							}
							,{
							columnWidth : 0.15,
				               border : false}, {
										columnWidth : 0.22,
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
										},{
											style : 'padding-top:3px;',
											text : '重置',
											scope : this,
											iconCls : 'reset',
											handler : this.reset
										}]
									}
									
									

							]
						});// end of searchPanel

				this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-user-sel',
						text : '流水对账',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_liushui_f_synthesize_'
								+ this.businessType) ? false : true,
						handler : this.openliushuiwin

					}, new Ext.Toolbar.Separator({
								hidden : isGranted('_liushui_f_synthesize_'
										+ this.businessType) ? false : true
							})
					, {
						iconCls : 'btn-detail',
						text : '查看对账明细',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_liushuisee_f_synthesize_'
								+ this.businessType) ? false : true,
						handler : this.openliushuiwin1

					}, new Ext.Toolbar.Separator({
								hidden : isGranted('_fa_f_synthesize_'
										+ this.businessType) ? false : true
							}), {
						iconCls : 'slupIcon',
						text : '上传',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_uploaddownpz_f_synthesize_'
								+ this.businessType) ? false : true,
						handler : this.upload

					}, new Ext.Toolbar.Separator({
								hidden : (isGranted('_uploaddownpz_f_synthesize_'
										+ this.businessType) ? false : true)
										|| (isGranted('_previewpz_f_'
												+ this.businessType)
												? false
												: true)
							})/* {
						iconCls : 'btn-setting',
						text : '预览凭证',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_previewpz_f_interestIncome_'
								+ this.businessType) ? false : true,
						handler : this.preview

					}, {
						iconCls : 'btn-setting',
						text : '还款',
						xtype : 'button',
						hidden:false,
						scope : this,
						//hidden : isGranted('_previewpz_f_principalIncome_'+this.businessType)?false:true,
						handler : this.borrowerToInvestor
					}, {
						iconCls : 'btn-setting',
						text : '保证金垫付',
						xtype : 'button',
						scope : this,
						//hidden : isGranted('_previewpz_f_principalIncome_'+this.businessType)?false:true,
						handler :this.guranteeMoney
					}*/, {
						iconCls : 'btn-xls',
						text : '导出到Excel',
						xtype : 'button',
						scope : this,
						handler : this.exportExcel
					},"->"/*,{
								xtype : 'checkbox',
								boxLabel : '未还款',
								inputValue : true,
								name : "pMoney",
								checked : false,
								singleSelect:true,
								scope : this,
								handler : function() {
									this.getCmpByName("flagMoney").setValue("notMoney"),
									 this.search();
								}
							}, '-', {
								xtype : 'checkbox',
								name : "pMoney",
								boxLabel : '已还款',
								inputValue : true,
								scope : this,
								singleSelect:true,
								checked : false,
								handler : function() {
									this.getCmpByName("flagMoney").setValue("isMoney"),
									 this.search();
								}
							}, '-', {
								xtype : 'checkbox',
								name : "pMoney",
								boxLabel : '已逾期',
								inputValue : true,
								scope : this,
								singleSelect:true,
								checked : false,
								handler : function() {
									this.getCmpByName("flagMoney").setValue("overdueMoney"),
									 this.search();
								}
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
					isautoLoad:false,
					plugins : [summary],
					viewConfig: {  
		            	forceFit:false  
		            },
					// 使用RowActions
					rowActions : false,
					width : 800,
					loadMask : true,
					id : 'synthesizeSlFundIntent1',
				url : __ctxPath
					+ "/creditFlow/finance/listbyLedgerNewSlFundIntent.do?businessType="+ this.businessType + "&fundType=all&typetab=all&synthesize=all",
					fields : [{
								name : 'actualChargeId',
								type : 'int'
							},'fundIntentId' ,'projectId','projectName','projectNumber','typeName','planChargeId', 'actualChargeType', 'intentDate',
							'incomeMoney', 'payMoney', 'factDate','sumFlatMoney',
							'afterMoney', 'notMoney','flatMoney', 'isOverdue',
							'overdueRate', 'accrualMoney', 'status','remark','orgName','companyId','payintentPeriod',
							'punishDays','principalRepayment','loanInterest','consultationMoney','serviceMoney','businessType','fundType','punishMoney','synthesizeMoney','synthesizeAfterMoney','notSynthesizeMoney'],
					columns : [{
								header : 'actualChargeId',
								dataIndex : 'actualChargeId',
								hidden : true
							}, {
								header : '借款项目名称',
								dataIndex : 'projectName',
								width : 160
							}, {
								header : '项目编号',
								summaryRenderer : totalMoney,
								dataIndex : 'projectNumber',
								width : 120
							}, {
								header : '期数',
								align:'center',
								summaryType : 'sum',
								dataIndex : 'payintentPeriod',
								width : 80
							},
							{
								header : '本金(元)',
								dataIndex : 'principalRepayment',
								align : 'right',
								width : 100,
								summaryType: 'sum',
								renderer:function(v){
									if(v==null){
										return Ext.util.Format.number(0,',000,000,000.00')+"元";
									}else{
		                               return Ext.util.Format.number(v,',000,000,000.00')+"元";
									}
	                         	}
							
							},{
								header : '利息(元)',
								dataIndex : 'loanInterest',
								align : 'right',
								width : 100,
								summaryType: 'sum',
								renderer:function(v){
									if(v==null){
										return Ext.util.Format.number(0,',000,000,000.00')+"元";
									}else{
		                               return Ext.util.Format.number(v,',000,000,000.00')+"元";
									}
	                         	}	
							},{
						header : '罚息(元)',
						width : 100,
						dataIndex : 'punishMoney',
						align : 'right',
						summaryType: 'sum',
						renderer : function(value, metadata, record, rowIndex,
								colIndex) {
							var flag = 0; //incomeMoney
							if (record.data.payMoney != 0
									|| record.data.fundType == "ToCustomGuarantMoney") { //payMoney
								flag = 1;
							}
							if (flag == 1) {
								return "";
							} else {
								if (value == 0) {
									return Ext.util.Format.number(value,
											',000,000,000.00')+"元";
								} else {
									return "<a><u>"
											+ Ext.util.Format.number(value,
													',000,000,000.00')+"元" 
											+ "</u></a>"
								}
							}
						}
					},
							 {
								header : '管理咨询费(元)',
								dataIndex : 'consultationMoney',
								align : 'right',
								width : 150,
								summaryType: 'sum',
								renderer:function(v){
									if(v==null){
										return Ext.util.Format.number(0,',000,000,000.00')+"元";
									}else{
		                               return Ext.util.Format.number(v,',000,000,000.00')+"元";
									}
	                         	}	
							},
							{
								header : '财务服务费(元)',
								dataIndex : 'serviceMoney',
								align : 'right',
								width : 150,
								summaryType: 'sum',
								renderer:function(v){
									if(v==null){
										return Ext.util.Format.number(0,',000,000,000.00')+"元";
									}else{
		                               return Ext.util.Format.number(v,',000,000,000.00')+"元";
									}
	                         	}	
							},{
								header : '合计金额(元)',
								dataIndex : 'synthesizeMoney',
								align : 'right',
								width : 150,
								summaryType: 'sum',
								renderer:function(v){
									if(v==null){
										return Ext.util.Format.number(0,',000,000,000.00')+"元";
									}else{
		                               return Ext.util.Format.number(v,',000,000,000.00')+"元";
									}
	                         	}	
							},{
								header : '核销金额',
								dataIndex : 'sumFlatMoney',
								align : 'right',
								width : 100,
								summaryType: 'sum',
								renderer:function(v){
									if(v==null){
										return Ext.util.Format.number(0,',000,000,000.00')+"元";
									}else{
			                           return Ext.util.Format.number(v,',000,000,000.00')+"元";
									}
			                 	}	
							}, {
								header : '未对账合计金额(元)',
								dataIndex : 'notSynthesizeMoney',
								align : 'right',
								width : 150,
								summaryType: 'sum',
								renderer:function(v){
									if(v==null){
										return Ext.util.Format.number(0,',000,000,000.00')+"元";
									}else{
		                               return Ext.util.Format.number(v,',000,000,000.00')+"元";
									}
	                         	}	
							},{
								header : '逾期天数(天)',
								summaryType : 'sum',
								renderer : function(v) {
					                 return v+"天";
				                   },
								dataIndex : 'punishDays',
								align:'center',
								width : 120
							}
							, {
						header : '计划还款日',
						width : 100,
						dataIndex : 'intentDate',
						align:'center'
				}, {
								header : '实际到账金额(元)',
								summaryType : 'sum',
								renderer : function(v) {
					                 return v+"元";
				                   },
								dataIndex : 'synthesizeAfterMoney',
								align : 'right',
								width : 100
							}

							, {
						header : '实际到帐日',
						width : 100,
						align:'center',
						dataIndex : 'factDate'
						//		sortable:true
				}]
					});

				 this.gridPanel.addListener('rowdblclick',this.rowClick);

			},
			reset : function() {
				this.searchPanel.getForm().reset();
					var obj = Ext.getCmp('Q_planChargeId_N_EQ');
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
			
			openliushuiwin : function() {
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','每次只能选择一条记录进行对账');
					return false;
				}else if(s.length==1){
					var notMoney=s[0].data.notSynthesizeMoney;
					if(0==notMoney){
						Ext.ux.Toast.msg('操作信息','未对账合计金额为0，不必进行对账操作');
						return false;
					}else{
					  this.oneopenliushuiwin();
					}
				}
			},
			oneopenliushuiwin :function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				var record = s[0];
				var flag = 0; //incomeMoney
				if (record.data.payMoney != 0) { //payMoney
					flag = 1;
				}
				var companyId=record.data.companyId;
				if(companyId==""){
					companyId=1;
				}
				new SynthesizeSlFundIntentForm({
					projectId : record.data.projectId,
					payintentPeriod : record.data.payintentPeriod,
					fundType : record.data.fundType,
					data : record.data,
					flag : flag,
					businessType : record.data.businessType,
					tabflag : "interestincome",
					companyId : companyId
				}).show();
			},
			manyInntentopenliushuiwin :function(){
			
			var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中记录');
					return false;
				}else{
			    var a=0;
			    var b=0;
			    var sumnotMoney=0;
			    for(var i=0;i<s.length;i++){
			    	if(s[i].data.payMoney >0)
			    	a++;
			    	if(s[i].data.incomeMoney >0)
				    b++;
			    	sumnotMoney=sumnotMoney+s[i].data.notMoney;
			    }
			    if(a>0 && b>0){
			    	Ext.ux.Toast.msg('操作信息','请选中的记录支出保持一致');
					return false;
			    	
			    }
			    var company=s[0];
			     for(var i=1;i<s.length;i++){
			    	if(s[i].data.companyId !=company){
			    	Ext.ux.Toast.msg('操作信息','请选中的记录分公司保持一致');
					return false;
			    	}
			    	
			    }
				var ids = $getGdSelectedIds(this.gridPanel,'actualChargeId');
				var	record=s[0];
			      var flag=0;            //incomeMoney
			     if(record.data.payMoney !=0){   //payMoney
			     	flag=1;
			     }
					new SlActualToChargeForm1({
						ids : ids,
						fundType : record.data.fundType,
						sumnotMoney :sumnotMoney,
						flag : flag,
						companyId :record.data.companyId
					}).show();
				}	
			},
			openliushuiwin1 : function() {
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
					return false;
				} else {
					var hidden = false;
					var flag = 1;
					var record = s[0];
		
					new detailView({
						projectId : record.data.projectId,
						payintentPeriod : record.data.payintentPeriod,
						fundType : record.data.fundType,
						flag : flag,
						hidden1 : false,
						hidden2 : false,
						businessType : record.data.businessType,
						type:'0'//用于表示查询所有相关资金明细
					}).show();
				}
			},
			
		opencash :function() {
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var	record=s[0];
					new chargeCashCheck({
						actualChargeId : record.data.actualChargeId,
						notMoney : record.data.notMoney
					}).show();
				
				}
			},
			onRowAction : function(grid, record, action, row, col) {
	     	
	     	switch (action) {
			case 'btn-user-sel' :
				this.openliushuiwin.call(this,record);
				break;
			case 'btn-detail' :
				this.openliushuiwin1.call(this,record,1);
				break;
			case 'btn-ok' :
				this.pingAccount.call(this,record);
				break;
			case 'btn-setting' :
				this.isOverdue.call(this,record);
				break;
			default :
				break;
		}
				
				
			},
			isOverdue:function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var	record=s[0];
					new chargeeditIsOverdueForm({
					actualChargeId : record.data.actualChargeId
						}).show();
				}	
				
		
				
			},
		
	      pingAccount:function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var	record=s[0];
					new chargeeditAfterMoneyForm({
					actualChargeId : record.data.actualChargeId,
					afterMoney : record.data.afterMoney,
					notMoney : record.data.notMoney,
					flatMoney : record.data.flatMoney
						}).show();
				}	
			},
		//上传
		upload : function(record) {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var record = s[0];
			var projectId = record.data.projectId;
			var businessType = record.data.businessType;
			var fundIntentId = record.data.fundIntentId;
			if (businessType == "Guarantee") {
				if (record.data.fundType == "ToCustomGuarantMoney") {
					var setname = '收取客户保证金凭证';
					var titleName = '收取客户保证金凭证';
					var tableName = 'sl_fund_intent_customGuarantMoney';
					var typeisfile = 'fundIntentId.' + fundIntentId;
				}
				if (record.data.fundType == "GuaranteeToCharge") {
					var setname = '收取保费凭证';
					var titleName = '收取保费凭证';
					var tableName = 'sl_fund_intent_GuaranteeToCharge';
					var typeisfile = 'fundIntentId.' + fundIntentId;
				}
				if (record.data.fundType == "BackCustomGuarantMoney") {
					var setname = '退还客户保证金凭证';
					var titleName = '退还客户保证金凭证';
					var tableName = 'sl_fund_intent_backCustomGuarantMoney';
					var typeisfile = 'fundIntentId.' + fundIntentId;
				}

				var mark = tableName + "." + projectId;
				uploadReportJS('上传/下载' + titleName + '文件', typeisfile, mark,
						15, null, null, null, projectId, businessType, setname);

			} else {

				var setname = '凭证';
				var titleName = '凭证';
				var tableName = 'sl_fund_intent_' + record.data.fundType;
				var typeisfile = 'fundIntentId.' + fundIntentId;

				var mark = tableName + "." + projectId;
				uploadReportJS('上传/下载' + titleName + '文件', typeisfile, mark,
						15, null, null, null, projectId, businessType, setname);
			}

		}

	},
	exportExcel:function(){
		var flagMoney=this.getCmpByName("flagMoney").getValue();//项目名称
		var Q_proj_Name_N_EQ=this.getCmpByName("Q_proj_Name_N_EQ").getValue();//项目名称
		var Q_projNum_N_EQ=this.getCmpByName("Q_projNum_N_EQ").getValue();//项目编号
		var Q_intentDate_D_GE=this.getCmpByName("Q_intentDate_D_GE").getValue();//从日期开始
		var Q_intentDate_D_LE=this.getCmpByName("Q_intentDate_D_LE").getValue();//从日期开始
		var endFactDate=this.getCmpByName("endFactDate").getValue();//到日期结束
		var startFactDate=this.getCmpByName("startFactDate").getValue();//到日期结束
		window.open( __ctxPath + "/creditFlow/finance/synthesizeExcelSlFundIntent.do?" +
				"businessType="+this.businessType+
				"&Q_proj_Name_N_EQ="+encodeURIComponent(encodeURIComponent(Q_proj_Name_N_EQ))+
				"&Q_projNum_N_EQ="+encodeURIComponent(encodeURIComponent(Q_projNum_N_EQ))+
				"&Q_intentDate_D_GE="+Q_intentDate_D_GE+
				"&Q_intentDate_D_LE="+Q_intentDate_D_LE+
				"&startFactDate="+startFactDate+
				"&flagMoney="+flagMoney+
				"&endFactDate="+endFactDate
				);
	}
		});
