/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
VChargeDetail = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				VChargeDetail.superclass.constructor.call(this, {
							id : 'VChargeDetail'+this.businessType,
							title : '费用收支对账日志',
							region : 'center',
							layout : 'border',
							iconCls :'btn-tree-team2',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var isShow=false;
				if(RoleType=="control"){
				  isShow=true;
				}
				// 初始化搜索条件Panel
				this.searchPanel = new HT.SearchPanel({
							layout : 'column',
							style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
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
				               align:'middle',
				               padding : '5px'
				               
				            },
				 //            bodyStyle : 'padding:10px 10px 10px 10px',
							items : [{   columnWidth : 0.2,
								layout : 'form',
								border : false,
								labelWidth : 80,
								labelAlign : 'right',
								items : [ {
								     fieldLabel : '操作日期：从',
										name : 'Q_intentDate_D_GE',
										labelSeparator : '',
										xtype : 'datefield',
										format : 'Y-m-d',
										anchor : '100%'
										
									},{
									 labelWidth:70,    
										fieldLabel : '项目名称',
										name : 'Q_proj_Name_N_EQ',
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '100%'
									}  ] 
								      
									}
									,
									{
									columnWidth : 0.2,
										layout : 'form',
										border : false,
										labelWidth : 60,
										labelAlign : 'right', 
										items : [
											{
												fieldLabel : '到',
												name : 'Q_intentDate_D_LE',
												labelSeparator : '',
												xtype : 'datefield',
												format : 'Y-m-d',
												anchor : '96%'
												
											},{
											   labelWidth:70,    
												fieldLabel : '项目编号',
												name : 'Q_projNum_N_EQ',
												flex : 1,
												editable : false,
												width:105,
												//forceSelection : false,
												xtype :'textfield',
												anchor : '96%'
											}]
										
										
							}/*,
							 { 
								columnWidth : 0.2,
								layout : 'form',
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								items : [{
									labelWidth:70,    
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
										anchor : '96%',
										listeners : {
											scope : this,
											select : function(combox, record, index) {
											var nodeKey = record.data.id;
											var obj = Ext.getCmp('Q_planChargeId_N_EQdetail');
											
											obj.enable();
										  var	arrStore = new Ext.data.ArrayStore({
								                autoLoad : true,
								                baseParams : {
								                    businessType : nodeKey
								                },
								                url : __ctxPath+ '/creditFlow/finance/getall1SlPlansToCharge.do',
										fields : ['itemId', 'itemName']
								        });
				                        
				                        obj.clearValue();
				                        obj.store = arrStore;
									    arrStore.load({"callback":test});
									    function test(r){
									    	if (obj.view) { // 刷新视图,避免视图值与实际值不相符
									    		obj.view.setStore(arrStore);
									        }
									       
									    }
										}
									
							}
								
								} ] 
								      
							}*/, { 
								columnWidth : 0.2,
								layout : 'form',
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								items : [ {
									  labelWidth:70,   
									 xtype :'combo',
										fieldLabel : '费用类型',
										name : 'Q_planChargeId_N_EQ',
										//id  : 'Q_planChargeId_N_EQdetail',
										hiddenName : "Q_planChargeId_N_EQ",
										flex : 1,
										editable : false,
										width:105,
										displayField : 'itemName',
									    valueField : 'itemId',
									    triggerAction : 'all',
									    store :new Ext.data.SimpleStore({}),
									    store : new Ext.data.SimpleStore({
											autoLoad : true,
											url : __ctxPath+ '/creditFlow/finance/getall1SlPlansToCharge.do?businessType='+this.businessType,
											fields : ['itemId', 'itemName']
										}),
										anchor : '96%'
								},{
								
								 labelWidth:70,    
										fieldLabel : '交易摘要',
										name : 'Q_transactionType_S_LK',
										flex : 1,
										width:105,
										xtype :'textfield',
										anchor : '96%'
								} ] 
								      
							},{
								columnWidth : .19,
								layout : 'form',
								border : false,
								labelWidth : 80,
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
								},isShow?{
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
								}:{border : false}]
							},
//							,{   columnWidth : 0.18,
//								layout : 'form',
//								border : false,
//								labelWidth : 60,
//								labelAlign : 'right',
//								items : [{
//									 labelWidth:70,    
//										fieldLabel : '交易摘要',
//										name : 'Q_transactionType_S_LK',
//										flex : 1,
//										width:105,
//										xtype :'textfield',
//										anchor : '96%'
//										
//								}]}
								
							 {
										columnWidth : .08,
										xtype : 'container',
										layout : 'form',
										defaults : {
											xtype : 'button'
										},
										items : [{
											text : '查询',
											scope : this,
											iconCls : 'btn-search',
											handler : this.search
										},{
											text : '重置',
											scope : this,
											style:'padding-top:5px;',
											iconCls : 'reset',
											handler : this.reset
										}]}
//									, {
//										columnWidth : .07,
//										xtype : 'container',
//										layout : 'form',
//										defaults : {
//											xtype : 'button'
//										},
//										style : 'padding-left:10px;',
//										items : [ {
//											style : 'padding-top:3px;',
//											text : '重置',
//											scope : this,
//											iconCls : 'reset',
//											handler : this.reset
//										}]
//									}
									
									

							]
//							,
//							buttons : [{
//										text : '查询',
//										scope : this,
//										iconCls : 'btn-search',
//										handler : this.search
//									}, {
//										text : '重置',
//										scope : this,
//										iconCls : 'btn-reset',
//										handler : this.reset
//									}]
						});// end of searchPanel

				this.topbar = new Ext.Toolbar({
					items : [{
			        	iconCls : 'btn-detailsa',
			        	text : '撤销对账',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_cxfydzlsjl_f_'+this.businessType)?false:true,
						handler : this.cancelAccount
							
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
					// 使用RowActions
					rowActions : false,
					plugins : [summary],
					viewConfig: {  
		            	forceFit:false  
		            },
					width : 800,
					id : 'VChargeDetailGrid',
					isautoLoad:false,
					loadMask : true,
					url : __ctxPath + "/creditFlow/finance/listVChargeDetail.do?businessType="+this.businessType,
					fields : [{
								name : 'chargeDetailId',
								type : 'int'
							}, 'projectName','projectNumber', 'intentincomeMoney','chargeName', 'intentDate',
							'intentpayMoney', 'operTime', 'factDate','fundType',
							'afterMoney', 'myAccount','flatMoney', 'isOverdue',
							'operTime', 'transactionType', 'qlideincomeMoney','qlidepayMoney','businessType','actualChargeId','fundQlideId','checkuser','iscancel','cancelremark','orgName'],
					columns : [{
								header : 'chargeDetailId',
								dataIndex : 'chargeDetailId',
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
								header : '手续收支类型',
								dataIndex : 'chargeName',
								summaryType : 'count',
								summaryRenderer : totalMoney,
								width : 130
								
							}, {
								header : '计划收入金额',
								dataIndex : 'intentincomeMoney',
								align : 'right',
								width : 110,
								summaryType: 'sum',
								renderer : function(v, metadata, record, rowIndex, columnIndex, store) {
				                metadata.attr = ' ext:qtip="' + Ext.util.Format.number(v,',000,000,000.00')+"元" + '"'
									if(v ==null){
									return "";
									}else{
								     return Ext.util.Format.number(v,',000,000,000.00')+"元"
									}
                         	     }
							
							}, {
								header : '计划支出金额',
								dataIndex : 'intentpayMoney',
								align :'right',
								width : 110,
								summaryType: 'sum',
								 renderer : function(v, metadata, record, rowIndex, columnIndex, store) {
				                metadata.attr = ' ext:qtip="' + Ext.util.Format.number(v,',000,000,000.00')+"元" + '"'
								if(v ==null){
									return "";
									}else{
								     return Ext.util.Format.number(v,',000,000,000.00')+"元"
									}
                         	     }
							
							}, {
								header : '计划到帐日',
								dataIndex : 'intentDate',
								align : 'center',
								width : 100,
								sortable:true
							}, {
								header : '实际到帐日',
								dataIndex : 'factDate',
								width : 100,
								sortable:true
							}, {
								header : '我方账户',
								dataIndex : 'myAccount',
					//			align : 'right',
								width : 150,
								 renderer : function(v, metadata, record, rowIndex, columnIndex, store) {
				                metadata.attr = ' ext:qtip="' +v+ '"'
									if(v=="1111"){
									return "";
									}else{
									  return v;
									}
                         	}
								
							}, {
								header : '收入金额',
								dataIndex : 'qlideincomeMoney',
								align : 'right',
								width : 150,
								sortable:true,
								summaryType: 'sum',
								 renderer : function(value, metadata, record, rowIndex, columnIndex, store) {
				                metadata.attr = ' ext:qtip="' + Ext.util.Format.number(value,',000,000,000.00')+"元" + '"'
									if(value ==null){
									return "";
									}else{
										if(record.data.myAccount =="1111")return "";
								        else{ 
								        	return Ext.util.Format.number(value,',000,000,000.00')+"元"
								        	}
									}
                         	     }
							}
							, {
								header : '支出金额',
								dataIndex : 'qlidepayMoney',
								width : 150,
								align : 'right',
								summaryType: 'sum',
							    renderer : function(value, metadata, record, rowIndex, columnIndex, store) {
				                metadata.attr = ' ext:qtip="' + Ext.util.Format.number(value,',000,000,000.00')+"元" + '"';
								if(value ==null){
									return "";
									}else{
										if(record.data.myAccount =="1111")return "";
								        else{ 
								        	return Ext.util.Format.number(value,',000,000,000.00')+"元"
								        	}
									}
                         	}
							}, {
								header : '划入本款项金额',
								width : 150,
								dataIndex : 'afterMoney',
								sortable:true,
								summaryType: 'sum',
								renderer : function(value, metadata, record, rowIndex, columnIndex, store) {
				                metadata.attr = ' ext:qtip="' + "现金"+Ext.util.Format.number(value,',000,000,000.00')+"元"	 + '"';
								if(record.data.myAccount =="1111") return "现金"+Ext.util.Format.number(value,',000,000,000.00')+"元"	
								else {
								return Ext.util.Format.number(value,',000,000,000.00')+"元"	
								}
							}
							}, {

								header : '操作人',
								align :'center',
								dataIndex : 'checkuser',
								width : 150
							}, {

								header : '操作时间',
								width : 150,
								align :'center',
								dataIndex : 'operTime'
							}, {
								header : '交易摘要',
								align :'center',
								width : 150,
								dataIndex : 'transactionType'
								
                         
							}
//							, {
//								header : '状态',
//								dataIndex : 'status',
//								renderer : function(v) {
//									switch (v) {
//										case "0":
//											return v;
//											break;
//										default:
//											return '<font color="red">'+v+'</font>';
//
//									}
//								}
//							}
							, {
								header : '备注',
								dataIndex : 'cancelremark',
								align : 'center',
								width :300,
								renderer : function(v, metadata, record, rowIndex, columnIndex, store) {
				                metadata.attr = ' ext:qtip="' + v + '"';
				                if(v !=null){
				                return '<font color="red">'+v+'</font>';
				                }else{
				                
				                 return "";
				                }
				                    }
							
							}
						]

						// end of columns
					});

				 this.gridPanel.addListener('rowdblclick',this.rowClick);

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
					var obj = Ext.getCmp('Q_planChargeId_N_EQdetail');
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
			
			cancelAccount:function(){
				var gridPanel = this.gridPanel;
				  var actualChargeId = $getGdSelectedIds(gridPanel,'actualChargeId');
				    var ids = $getGdSelectedIds(gridPanel,'chargeDetailId');
				 	var s = gridPanel.getSelectionModel().getSelections();
				  if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					
						if(s[0].data.iscancel==1){
						Ext.ux.Toast.msg('操作信息','此对账记录已被撤销，无须再撤！');
				     	return false;
					}
			Ext.MessageBox.confirm('确认撤销吗', '撤销就不能恢复了', function(btn) {
					if (btn == 'yes') {
			       Ext.Ajax.request( {
									url : __ctxPath + '/creditFlow/finance/cancelAccountSlActualToCharge.do',
									method : 'POST',
									scope : this,
									params : {
										actualChargeId : actualChargeId,
										qlideId:ids
									},
									success : function(response, request) {
										Ext.ux.Toast.msg('操作信息', '撤销成功！');
								         gridPanel.getStore().reload();
									},
									checkfail:function(response, request) {
										Ext.Msg.alert('状态', "撤销失败");
										

									}
								});

		
			
				}
			
				})
			}
			}
		});
