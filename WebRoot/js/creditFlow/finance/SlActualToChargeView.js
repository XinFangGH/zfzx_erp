/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
SlActualToChargeView = Ext.extend(Ext.Panel, {
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
				SlActualToChargeView.superclass.constructor.call(this, {
							id : 'SlActualToChargeView',
							title : '手续收支核销',
							region : 'center',
							layout : 'border',
							iconCls :'btn-team30',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel = new HT.SearchPanel({
							layout : 'column',
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
				               align:'middle'
				            },
				 //            bodyStyle : 'padding:10px 10px 10px 10px',
							items : [
							{   columnWidth : 0.17,
								layout : 'form',
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								items : [ {
									 labelWidth:70,    
										fieldLabel : '项目名称',
										name : 'Q_proj_Name_N_EQ',
										flex : 1,
										editable : false,
										width:105,
										//forceSelection : false,
										xtype :'textfield',
										anchor : '96%'
										
								}, {
									 labelWidth:70,    
										fieldLabel : '业务类别',
										name : 'Q_operationType_N_EQ',
										hiddenName : "Q_operationType_N_EQ",
										flex : 1,
										editable : false,
										width:105,
										anchor : '96%',
										displayField : 'name',
									    valueField : 'id',
									    triggerAction : 'all',
										xtype : 'combo',
										 store : new Ext.data.SimpleStore({
												autoLoad : true,
												url : __ctxPath+ '/creditFlow/getBusinessTypeList1CreditProject.do',
												fields : ['id', 'name']
											}),
										listeners : {
											scope : this,
											select : function(combox, record, index) {
											var nodeKey = record.data.id;
											var obj = Ext.getCmp('Q_planChargeId_N_EQ');
											
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
								      
							},{   columnWidth : 0.19,
								layout : 'form',
								border : false,
								labelWidth : 100,
								labelAlign : 'right',
								items : [ {
										fieldLabel : '项目编号',
										name : 'Q_projNum_N_EQ',
										flex : 1,
										editable : false,
										width:105,
										//forceSelection : false,
										xtype :'textfield',
										anchor : '96%'
										
								}, {
									 xtype :'combo',
										fieldLabel : '手续收支类型',
										name : 'Q_planChargeId_N_EQ',
										id  : 'Q_planChargeId_N_EQ',
										hiddenName : "Q_planChargeId_N_EQ",
										flex : 1,
										editable : false,
										width:105,
										displayField : 'itemName',
									    valueField : 'itemId',
									    triggerAction : 'all',
									    store :new Ext.data.SimpleStore({}),
//									    store : new Ext.data.SimpleStore({
//										autoLoad : true,
//										url : __ctxPath+ '/creditFlow/finance/getall1SlPlansToCharge.do?businessType='+this.businessType,
//										fields : ['itemId', 'itemName']
//									
//									}),
										anchor : '96%'
								} ] 
								      
							},
							{   columnWidth : 0.2,
								layout : 'form',
								border : false,
								labelWidth : 100,
								labelAlign : 'right',
								items : [ {
									fieldLabel : '是否逾期',
									width:100,
									flex : 1,
									xtype:'combo',
						             mode : 'local',
					               displayField : 'name',
					               valueField : 'id',
					                 store : new Ext.data.SimpleStore({
							        fields : ["name", "id"],
						            data : [["不限",""],
						                    ["是", "是"],
									     	["否", "否"]]
					              	}),
						             triggerAction : "all",
					                hiddenName:"Q_isOverdue_S_E",
						          	name : 'Q_isOverdue_S_E',
						          	anchor : '96%'
								},  {
								     fieldLabel : '计划到账日期：从',
								     labelSeparator : '',
										name : 'Q_intentDate_D_GE',
										id :'Q_intentDate_D_GEcharge',
										xtype : 'datefield',
										format : 'Y-m-d',
										anchor : '96%'
										
									}  ] 
								      
									}
									,
									{
										columnWidth : 0.17,
										layout : 'form',
										border : false,
										labelWidth : 60,
										labelAlign : 'right', 
										items : [
											     
											{
												labelWidth:70,   
												fieldLabel : '是否结清',
												name : 'Q_notMoney_BD_LE',
												width:100,
												flex : 1,
												xtype:'combo',
									             mode : 'local',
								               displayField : 'name',
								               valueField : 'id',
								                 store : new Ext.data.SimpleStore({
										        fields : ["name", "id"],
									            data : [["不限",""],
									                    ["已结清", "0"],
												     	["未结清", "1"]]
								              	}),
									             triggerAction : "all",
								                hiddenName:"Q_notMoney_BD_LE",
								                anchor : '96%',
								                 value : 1
											},{
												fieldLabel : '到',
												name : 'Q_intentDate_D_LE',
												id : 'Q_intentDate_D_LEcharge',
												labelSeparator : '',
												xtype : 'datefield',
												format : 'Y-m-d',
												anchor : '96%'
												
											}]
										
										
									}, {
										columnWidth : .13,
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
										}, {
											style : 'padding-top:3px;',
											text : '重置',
											scope : this,
											iconCls : 'reset',
											handler : this.reset
										}]
									}
									
									

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
			        	iconCls : 'btn-user-sel',
			        	text : ' 流水对账',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_liushui_a_'+this.businessType)?false:true,
						handler : this.openliushuiwin
							
					}
//					,{
//			        	iconCls : 'btn-details',
//			        	text : '现金对账',
//						xtype : 'button',
//						scope : this,
//						hidden : isGranted('_cash_a_'+this.businessType)?false:true,
//						handler : this.opencash
//							
//					}
					,new Ext.Toolbar.Separator({
						hidden : isGranted('_cash_a_'+this.businessType)?false:true
					})
					,{
						iconCls : 'btn-detail',
						text : '查看',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_liushuisee_a_'+this.businessType)?false:true,
						handler : this.openliushuiwin1
			
					},new Ext.Toolbar.Separator({
						hidden : isGranted('_liushuisee_a_'+this.businessType)?false:true
					}),{
						iconCls : 'btn-ok',
						text :'核销',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_ping_a_'+this.businessType)?false:true,
						handler : this.pingAccount
			
					},new Ext.Toolbar.Separator({
						hidden : isGranted('_ping_a_'+this.businessType)?false:true
					}),{
						iconCls : 'btn-state',
						text :'逾期状态',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_sOverdue_a_'+this.businessType)?false:true,
						handler : this.isOverdue
			        	
			
					},'->',
						{
							xtype:'radio',
							scope : this,
							boxLabel : '已过期',
							id:"11charge",
							name : '1',
							inputValue : false,
							listeners:{
								scope :this,
							    check :function(){
							    var flag=Ext.getCmp("11charge").getValue();
							     if(flag==true){ 
							     	 Ext.getCmp("Q_intentDate_D_GEcharge").setValue();
									    var now=new Date();
									    var time=now.getTime();
										time-=1000*60*60*24*1;//加上3天
										now.setTime(time);
									    Ext.getCmp("Q_intentDate_D_LEcharge").setValue(now);
									    this.search();
							     }
							    }
							}
						},' ',' ',{
							xtype:'radio',
							boxLabel : '最近三天',
							id:"12charge",
							name : '1',
							inputValue : false,
							listeners:{
								scope :this,
							    check :function(){
							   var flag=Ext.getCmp("12charge").getValue();
							   
							     if(flag==true){
							     	 Ext.getCmp("Q_intentDate_D_LEcharge").setValue(new Date);
								    var now=new Date();
								   var time=now.getTime();
									time-=1000*60*60*24*2;//加上3天
									now.setTime(time);
									 Ext.getCmp("Q_intentDate_D_GEcharge").setValue(now);
							      this.search();
							      
							    }
							    }
							}
						},' ',' ', {
							xtype:'radio',
							boxLabel : '最近七天',
							name : '1',
							id:"13charge",
							inputValue : false,
							 listeners:{
							 	scope :this,
							    check :function(){
							     var flag=Ext.getCmp("13charge").getValue();
							   
							     if(flag==true){
								    Ext.getCmp("Q_intentDate_D_LEcharge").setValue(new Date);
								    var now=new Date();
								   var time=now.getTime();
									time-=1000*60*60*24*6;//加上3天
									now.setTime(time);
									 Ext.getCmp("Q_intentDate_D_GEcharge").setValue(now);
								      this.search();
							    }
							    }
							}
						},' ',' ', {
							xtype:'radio',
							boxLabel : '所有',
							name : '1',
							id:"14charge",
							inputValue : true,
							listeners:{
								scope :this,
							    check :function(){
							    var flag=Ext.getCmp("14charge").getValue();
							     if(flag==true){
							     Ext.getCmp("Q_intentDate_D_GEcharge").setValue();
							    Ext.getCmp("Q_intentDate_D_LEcharge").setValue();
							     this.search();
							    }
							    }
							}
						},' ',' ',' ',' ',
						{xtype:'label',html : '【<font style="line-height:20px">颜色预警：</font>'}
						,{xtype:'label',html : '<font color=red>&nbsp;&nbsp未结清项</font>'}
						,{xtype:'label',html : '&nbsp;&nbsp已结清项】'}
					]
				});

				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					tbar : this.topbar,
					viewConfig: {  
		            	forceFit:false  
		            },
					// 使用RowActions
					rowActions : false,
					width : 800,
					id : 'SlActualToChargeGrid',
					url : __ctxPath + "/creditFlow/finance/listSlActualToCharge.do?businessType="+this.businessType,
					fields : [{
								name : 'actualChargeId',
								type : 'int'
							}, 'projectName','projectNumber','typeName','planChargeId', 'actualChargeType', 'intentDate',
							'incomeMoney', 'payMoney', 'factDate',
							'afterMoney', 'notMoney','flatMoney', 'isOverdue',
							'overdueRate', 'accrualMoney', 'status','remark'],
					columns : [{
								header : 'actualChargeId',
								dataIndex : 'actualChargeId',
								hidden : true
							}, {
								header : '项目名称',
								dataIndex : 'projectName',
								width : 160
							}, {
								header : '项目编号',
								dataIndex : 'projectNumber',
								width : 120
							}, {
								header : '手续收支类型',
								dataIndex : 'typeName',
								width : 130
							},
//							, {
//								header : '计费方式',
//								dataIndex : 'actualChargeType',
//								renderer : function(v){
//									if(v==0){
//									return "固定金额";
//									}else {
//									return "百分比";
//									}
//								}
//							},
							{
								header : '计划收入金额',
								dataIndex : 'incomeMoney',
								align : 'right',
								width : 150,
								renderer:function(v){
                               return Ext.util.Format.number(v,',000,000,000.00')+"元";
                         	}
							
							},
							{
								header : '计划支出金额',
								dataIndex : 'payMoney',
								align : 'right',
								width : 150,
								renderer:function(v){
                               return Ext.util.Format.number(v,',000,000,000.00')+"元";
                         	}
                         	}, {
								header : '计划到帐日',
								dataIndex : 'intentDate',
								align : 'center',
								sortable:true
							},  {
								header : '实际到帐日',
								dataIndex : 'factDate',
								sortable:true
							}, {
								header : '已计划金额',
								dataIndex : 'afterMoney',
								width : 150,
								align :'right',
								renderer:function(v){
                               return Ext.util.Format.number(v,',000,000,000.00')+"元";
                         	}
								
							}, {
								header : '未计划金额',
								dataIndex : 'notMoney',
								width : 150,
								align : 'right',
								sortable:true,
								renderer : function(v) {
									switch (v) {
										case 0:
											return Ext.util.Format.number(v,',000,000,000.00')+"元";
											break;
										default:
											return '<font color="red">'+Ext.util.Format.number(v,',000,000,000.00')+'元</font>'+"";

									}
								}
							}
							, {
								header : '核销金额',
								width : 150,
								dataIndex : 'flatMoney',
								align : 'right',
								renderer:function(v){
                                return Ext.util.Format.number(v,',000,000,000.00')+"元";
                         	}
							}, {
								header : '是否逾期',
								dataIndex : 'isOverdue',
								align : 'center',
								sortable:true
							}

							, {
								header : '备注',
								dataIndex : 'remark',
								hidden : true
							}]
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
					this.manyInntentopenliushuiwin();
					
				}else if(s.length==1){
				   this.oneopenliushuiwin();
				}
				
			},
			oneopenliushuiwin :function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				var	record=s[0];
			     var flag=0;            //incomeMoney
			     if(record.data.payMoney !=0){   //payMoney
			     	flag=1;
			     }
					new SlActualToChargeForm({
					actualChargeId : record.data.actualChargeId,
					fundType : record.data.planChargeId,
	  			    flag:flag,
					businessType :record.data.businessType,
					notMoney : record.data.notMoney
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
						flag : flag
					}).show();
				}	
			},
			openliushuiwin1 : function() {
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var flag=1;
					var	record=s[0];
					new chargeDetailView({
					actualChargeId : record.data.actualChargeId,
					flag : flag,
					hidden2 : false
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
				
				
			
	
			}
		});
