/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
PrincipalDivert = Ext.extend(Ext.Panel, {
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
				PrincipalDivert.superclass.constructor.call(this, {
							id : 'PrincipalDivert',
							title : '本金挪用',
							region : 'center',
							layout : 'border',
							iconCls :'btn-team29',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				var tabflag=this.tabflag;
               var labelsize=70;
				 var labelsize1=115;
				 	var isShow=false;
				if(RoleType=="control"){
				  isShow=true;
				}
				this.searchPanel = new HT.SearchPanel({
							layout : 'column',
							style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
							region : 'north',
							height : 20,
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
				 //            bodyStyle : 'padding:10px 10px 10px 10px',
							items : [
							isShow?{
						   columnWidth : .19,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 70,
							defaults : {
								anchor : '96%'
							},
								border : false,
								items : [
								{
										xtype : "combo",
										anchor : "96%",
										fieldLabel : '所属分公司',
										hiddenName : "companyId",
										displayField : 'companyName',
										valueField : 'companyId',
										triggerAction : 'all',
										store : new Ext.data.SimpleStore({
											autoLoad : true,
											url : __ctxPath+'/system/getControlNameOrganization.do',
											fields : ['companyId', 'companyName']
										})
								}
							]}:{columnWidth : 0.002,
				border : false}, {   columnWidth : 0.25,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								hidden : true,
								items : [{
										fieldLabel : '业务类别',
										name : 'Q_operationType_N_EQ',
										hiddenName : "Q_operationType_N_EQ",
										flex : 1,
										editable : false,
										width:105,
										displayField : 'name',
									    valueField : 'id',
									    triggerAction : 'all',
										xtype : 'combo',
										 mode : 'local',
										 store : new Ext.data.SimpleStore({
												autoLoad : true,
												url : __ctxPath+ '/creditFlow/getBusinessTypeList1CreditProject.do',
												fields : ['id', 'name']
											}),
										anchor : '96%'
										
								
								} ] 
								      
							},{   columnWidth : 0.18,
								layout : 'form',
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
										fieldLabel : '项目名称',
										name : 'Q_proj_Name_N_EQ',
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '100%'
										
								}]},{   columnWidth : 0.18,
								layout : 'form',
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								items : [ {
										fieldLabel : '项目编号',
										name : 'Q_projNum_N_EQ',
										flex : 1,
										editable : false,
										width:105,
										//forceSelection : false,
										xtype :'textfield',
										anchor : '100%'
										
								} ] 
								      
							},{   columnWidth : 0.17,
								layout : 'form',
								border : false,
								labelWidth : 80,
								labelAlign : 'right',
								items : [ {
									fieldLabel : '开始日期：从',
										name : 'Q_intentDate_D_GE',
										labelSeparator : '',
										//id :'Q_intentDate_D_GE'+tabflag,
										xtype : 'datefield',
										format : 'Y-m-d',
										anchor : '100%'
										
								} ] 
								      
							},{   columnWidth : 0.12,
								layout : 'form',
								border : false,
								labelWidth : 30,
								labelAlign : 'right',
								items : [ {
									fieldLabel : '到',
												name : 'Q_intentDate_D_LE',
												labelSeparator : '',
												//id : 'Q_intentDate_D_LE'+tabflag,
												xtype : 'datefield',
												format : 'Y-m-d',
												anchor : '100%'
										
								} ] 
								      
							},{
								columnWidth : .19,
								layout : 'form',
								border : false,
								labelWidth : 70,
								labelAlign : 'right',
								items : [{
									xtype : 'lovcombo',
									fieldLabel : '项目属性',
									anchor : '96%',
									hiddenName : 'projectProperties',
									triggerAction : 'all',
									editable :false,
									readOnly : false,
						            store :new Ext.data.ArrayStore({
						                autoLoad : true,
						                baseParams : {
						                    nodeKey : 'projectProperties'
						                },
						                url : __ctxPath + '/system/loadIndepItemsDictionaryIndependent.do',
						                fields : ['dicKey', 'itemValue']
						            }),
						            displayField : 'itemValue',
						            valueField : 'dicKey',
						            listeners :{
										afterrender : function(combox) {
										var st = combox.getStore();
									}
									}
								}]
							}
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
										}]},	
											{columnWidth : .07,
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
										}]
									}
									
									

							]

						});// end of searchPanel

					var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计';
				}			

				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
			//		tbar : this.topbar,
					plugins : [summary],
					viewConfig: {  
		            	forceFit:false  
		            },
					// 使用RowActions
					rowActions : false,
					id : 'SlFundIntentGrid1principalincome',
					url : __ctxPath + "/creditFlow/finance/listbyLedgerSlFundIntent.do?businessType=SmallLoan&fundType=('principalDivert')&typetab=all",
					fields : [{
								name : 'fundIntentId',
								type : 'int'
							}, 'projectName','projectNumber', 'incomeMoney','fundTypeName', 'intentDate',
							'payMoney', 'payInMoney', 'factDate','fundType',
							'afterMoney', 'notMoney','flatMoney', 'isOverdue',
							'overdueRate', 'accrualMoney', 'status','remark','businessType','projectId','tabType','orgName','companyId','punishDays','punishAccrual'],
					columns : [{
								header : 'fundIntentId',
								dataIndex : 'fundIntentId',
								hidden : true
							},{
								header : "所属分公司",
								sortable : true,
								width : 120,
								hidden:RoleType=="control"?false:true,
								dataIndex : 'orgName'
							}, {
								header : '项目名称',
								dataIndex : 'projectName',
								width : 150
							}
							, {
								header : '项目编号',
								dataIndex : 'projectNumber',
								width : 120
							}, {
								header : '资金类型',
								dataIndex : 'fundTypeName',
								summaryType : 'count',
								summaryRenderer : totalMoney,
								width : 130
								
							}, {
								header : '挪用金额',
								dataIndex : 'incomeMoney',
								align : 'right',
								width : 150,
								summaryType: 'sum',
								renderer:function(v){
								return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							}, {
								header : '罚息利率',
								dataIndex : 'punishAccrual',
								align : 'right',
								width : 150,
								renderer:function(v){
								return v+"%"
                         	     }
							
							}, {
								header : '开始日期',
								width : 100,
								dataIndex : 'intentDate',
								align : 'center'
						//		sortable:true
							}, {
								header : '截至日期',
								width : 100,
								dataIndex : 'factDate',
								renderer:function(v){
								if(v==null){
									return "今天"
								}
								return v;
								
							}
								
						//		sortable:true
							}, {
								header : '累计天数',
								dataIndex : 'punishDays',
								width : 100,
								align : 'right'
								
							}, {
								header : '罚息总额',
								width : 100,
								dataIndex : 'accrualMoney',
								align : 'right',
								summaryType: 'sum',
								renderer : function(value, metadata, record, rowIndex,
										colIndex){
								
									 	if(value==0){
								 		    return Ext.util.Format.number(value,',000,000,000.00')+"元"
								 	    }else{
									         return "<a><u>"+Ext.util.Format.number(value,',000,000,000.00')+"元"+"</u></a>"
								 }																					
							}
								
                         
							}
							, {
								header : '备注',
								width : 150,
								dataIndex : 'remark',
								align : 'center'
								
							}
						]

						// end of columns
					});

				this.gridPanel.addListener('cellclick', this.cellClick);

			},// end of the initComponents()
			// 重置查询表单
//			rowClick : function(grid, rowindex, e) {
//				grid.getSelectionModel().each(function(rec) {
//							new editAfterMoneyForm({
//								fundIntentId : rec.data.fundIntentId,
//								afterMoney : rec.data.afterMoney,
//								notMoney : rec.data.notMoney,
//								flatMoney : rec.data.flatMoney
//									}).show();
//						});
//				
//			},
			reset : function() {
				this.searchPanel.getForm().reset();
				var obj = Ext.getCmp('Q_fundType_N_EQ'+tabflag);
			//	obj.setEditable(false);
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
			
			rowClick:function(){
				
			},
			
			openliushuiwin : function() {
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中记录');
					return false;
				}else if(s.length>1){
					this.manyInntentopenliushuiwin();
					
				}else if(s.length==1){
				   this.oneopenliushuiwin();
				}
			},
			oneopenliushuiwin : function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				var	record=s[0];
			     var flag=0;            //incomeMoney
			     if(record.data.payMoney !=0){   //payMoney
			     	flag=1;
			     }
					new SlFundIntentForm({
						fundIntentId : record.data.fundIntentId,
						fundType : record.data.fundType,
						notMoney : record.data.notMoney,
						flag:flag,
						businessType :record.data.businessType,
						tabflag :"principalincome",
						companyId:record.data.companyId
					}).show();
				
			},
			manyInntentopenliushuiwin : function(){
				
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中记录');
					return false;
				}else{
				var company=s[0].data.companyId;
			     for(var i=1;i<s.length;i++){
			    	if(s[i].data.companyId !=company){
			    	Ext.ux.Toast.msg('操作信息','请选中的记录分公司保持一致');
					return false;
			    	}
			    	
			    }
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
			     
				var ids = $getGdSelectedIds(this.gridPanel,'fundIntentId');
				var	record=s[0];
				var flag=0;            //incomeMoney
			     if(record.data.payMoney !=0){   //payMoney
			     	flag=1;
			     }
					new SlFundIntentForm1({
						ids : ids,
						flag:flag,
						fundType : record.data.fundType,
						sumnotMoney :sumnotMoney,
						tabflag :"principalincome",
						companyId:record.data.companyId
					}).show();
				}	
				
			},
			openliushuiwin1 : function(record,flag) {
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var hidden=false;
					var flag=1;
					var	record=s[0];
					
					
					
					new detailView({
						fundIntentId : record.data.fundIntentId,
						fundType : record.data.fundType,
						flag : flag,
						hidden1 : true,
						hidden2 :false,
						businessType: record.data.businessType
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
					new CashCheck({
						fundIntentId : record.data.fundIntentId,
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
			case 'btn-details' :
				this.opencash.call(this,record);
				break;
			default :
				break;
		}
				
				
			},
			isOverdue:function(record){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var	record=s[0];
					if(record.data.fundType != "principalLending")
					{
						new editIsOverdueForm({
							fundIntentId : record.data.fundIntentId
								}).show();
					}
				}	
				
				
		
				
			},
		
	      pingAccount:function(record){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var	record=s[0];
					new editAfterMoneyForm({
						fundIntentId : record.data.fundIntentId,
						afterMoney : record.data.afterMoney,
						notMoney : record.data.notMoney,
						flatMoney : record.data.flatMoney
							}).show();
				}
				
				


	
			},
			  upload:function(record){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var	record=s[0];
					var	projectId =record.data.projectId;
					var businessType=record.data.businessType;
					var fundIntentId=record.data.fundIntentId;
					if(businessType=="Guarantee"){
						if(record.data.fundType=="ToCustomGuarantMoney"){
						var setname ='收取客户保证金凭证';
						var titleName ='收取客户保证金凭证';
						var tableName ='sl_fund_intent_customGuarantMoney';
						var typeisfile ='fundIntentId.'+fundIntentId;
						}
						if(record.data.fundType=="GuaranteeToCharge"){
						var setname ='收取保费凭证';
						var titleName ='收取保费凭证';
						var tableName ='sl_fund_intent_GuaranteeToCharge';
						var typeisfile ='fundIntentId.'+fundIntentId;
						}
						if(record.data.fundType=="BackCustomGuarantMoney"){
						var setname ='退还客户保证金凭证';
						var titleName ='退还客户保证金凭证';
						var tableName ='sl_fund_intent_backCustomGuarantMoney';
						var typeisfile ='fundIntentId.'+fundIntentId;
						}
						
						var mark=tableName+"."+projectId;
						uploadReportJS('上传/下载'+titleName+'文件',typeisfile,mark,15,null,null,null,projectId,businessType,setname);
						
					}else{
						
						Ext.ux.Toast.msg('操作信息','此款项没有此功能');
				       }
				
				
				}
	
			}
			,
			  preview:function(record){
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var	record=s[0];
					var	projectId =record.data.projectId;
					var businessType=record.data.businessType;
					var fundIntentId=record.data.fundIntentId;
					if(businessType=="Guarantee"){
						if(record.data.fundType=="ToCustomGuarantMoney"){
						var setname ='收取客户保证金凭证';
						var titleName ='收取客户保证金凭证';
						var tableName ='sl_fund_intent_customGuarantMoney';
						var typeisfile ='typeisToCustomGuarantMoney';
						}
						if(record.data.fundType=="GuaranteeToCharge"){
						var setname ='收取保费凭证';
						var titleName ='收取保费凭证';
						var tableName ='sl_fund_intent_GuaranteeToCharge';
						var typeisfile ='typeisGuaranteeToCharge';
						}
						if(record.data.fundType=="BackCustomGuarantMoney"){
						var setname ='退还客户保证金凭证';
						var titleName ='退还客户保证金凭证';
						var tableName ='sl_fund_intent_backCustomGuarantMoney';
						var typeisfile ='typeisbackCustomGuarantMoney';
						}
						var mark=tableName+"."+projectId;
						var remark='fundIntentId.'+fundIntentId;
						picViewer(remark,false,typeisfile);
						
					}else{
						
						Ext.ux.Toast.msg('操作信息','此款项没有此功能');
				       }
					
				}
	
			},
			cellClick : function(grid,rowIndex,columnIndex,e){
				if(12==columnIndex){
				 
					var accrualMoney = grid.getStore().getAt(rowIndex).get('accrualMoney');
					var fundIntentId = grid.getStore().getAt(rowIndex).get('fundIntentId');
					var businessType=this.businessType;
					 if(accrualMoney !=0){
					 	new SlPunishInterestView({
					 	fundIntentId:fundIntentId,
					 	businessType:"all"
							}).show();
					 }
				
					
				
				
				}
		
	}
		});
