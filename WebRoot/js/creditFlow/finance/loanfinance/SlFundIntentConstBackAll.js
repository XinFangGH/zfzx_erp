/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
SlFundIntentConstBackAll = Ext.extend(Ext.Panel, {
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
				SlFundIntentConstBackAll.superclass.constructor.call(this, {
							id : 'SlFundIntentConstBackAll',
							title : '全部款项',
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
						   columnWidth : .25,
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
								      
							},{   columnWidth : 0.25,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
										fieldLabel : '项目名称',
										name : 'Q_proj_Name_N_EQ',
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '96%'
										
								}]},{   columnWidth : 0.25,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
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
										
								} ] 
								      
							},{   columnWidth : 0.25,
								layout : 'form',
								border : false,
								labelWidth : labelsize1,
								labelAlign : 'right',
								items : [ {
									fieldLabel : '计划退回日：从',
										name : 'Q_intentDate_D_GE',
										labelSeparator : '',
										//id :'Q_intentDate_D_GE'+tabflag,
										xtype : 'datefield',
										format : 'Y-m-d',
										anchor : '96%'
										
								} ] 
								      
							},{   columnWidth : 0.25,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [ {
									fieldLabel : '到',
												name : 'Q_intentDate_D_LE',
												labelSeparator : '',
												//id : 'Q_intentDate_D_LE'+tabflag,
												xtype : 'datefield',
												format : 'Y-m-d',
												anchor : '96%'
										
								} ] 
								      
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

				this.topbar = new Ext.Toolbar({
					items : [{
			        	iconCls : 'btn-user-sel',
			        	text : '流水对账',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_liushui_f_principalPay_'+this.businessType)?false:true,
						handler : this.openliushuiwin
							
					},new Ext.Toolbar.Separator({
						hidden : isGranted('_liushui_f_principalPay_'+this.businessType)?false:true
					})
//					,{
//			        	iconCls : 'btn-details',
//			        	text : '现金对账',
//						xtype : 'button',
//						scope : this,
//						hidden : isGranted('_cash_f_'+this.businessType)?false:true,
//						handler : this.opencash
//							
//					}
					,{
						iconCls : 'btn-detail',
						text : '查看对账明细',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_liushuisee_f_principalPay_'+this.businessType)?false:true,
						handler : this.openliushuiwin1
			
					},new Ext.Toolbar.Separator({
						hidden : isGranted('_liushuisee_fprincipalPay__'+this.businessType)?false:true
					}),{
						iconCls : 'btn-ok',
						text :'核销',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_ping_f_principalPay_'+this.businessType)?false:true,
						handler : this.pingAccount
			
					},new Ext.Toolbar.Separator({
						hidden : isGranted('_ping_f_principalPay_'+this.businessType)?false:true
					})  ,{
						iconCls : 'slupIcon',
						text :'上传/下载凭证',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_uploaddownpz_f_principalPay_'+this.businessType)?false:true,
						handler : this.upload
			        	
			
					},new Ext.Toolbar.Separator({
						hidden : (isGranted('_uploaddownpz_f_principalPay_'+this.businessType)?false:true)||(isGranted('_previewpz_f_'+this.businessType)?false:true)
					}),{
						iconCls : 'btn-setting',
						text :'预览凭证',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_previewpz_f_principalPay_'+this.businessType)?false:true,
						handler : this.preview
			        	
			
					}
//					,'->',
//						{
//							xtype:'radio',
//							scope : this,
//							boxLabel : '已过期',
//							id:"11",
//							name : '1',
//							inputValue : false,
//							listeners:{
//								scope :this,
//							    check :function(){
//							    var flag=Ext.getCmp("11").getValue();
//							     if(flag==true){ 
//							     	 Ext.getCmp("Q_intentDate_D_GE"+tabflag).setValue();
//							     	 var now=new Date();
//									   var time=now.getTime();
//										time-=1000*60*60*24*1;//加上3天
//										now.setTime(time);
//									    Ext.getCmp("Q_intentDate_D_LE"+tabflag).setValue(now);
//									    this.search();
//							     }
//							    }
//							}
//						},' ',' ',{
//							xtype:'radio',
//							boxLabel : '最近三天',
//							id:"12",
//							name : '1',
//							inputValue : false,
//					//		style : "margin-bottom : 7px",
//					//		labelStyle : "margin-top : 4px",
//							listeners:{
//								scope :this,
//							    check :function(){
//							   var flag=Ext.getCmp("12").getValue();
//							     if(flag==true){
//							     	 Ext.getCmp("Q_intentDate_D_LE"+tabflag).setValue(new Date);
//									    var now=new Date();
//									   var time=now.getTime();
//										time-=1000*60*60*24*2;//加上3天
//										now.setTime(time);
//										 Ext.getCmp("Q_intentDate_D_GE"+tabflag).setValue(now);
//									      this.search();
//							    }
//							    }
//							}
//						},' ',' ', {
//							xtype:'radio',
//							boxLabel : '最近七天',
//							name : '1',
//							id:"13",
//							inputValue : false,
//							 listeners:{
//							 	scope :this,
//							    check :function(){
//							     var flag=Ext.getCmp("13").getValue();
//							     if(flag==true){
//							     	  Ext.getCmp("Q_intentDate_D_LE"+tabflag).setValue(new Date);
//									    var now=new Date();
//									   var time=now.getTime();
//										time-=1000*60*60*24*6;//加上3天
//										now.setTime(time);
//										 Ext.getCmp("Q_intentDate_D_GE"+tabflag).setValue(now);
//							      this.search();
//							    }
//							    }
//							}
//						},' ',' ', {
//							xtype:'radio',
//							boxLabel : '所有',
//							name : '1',
//							id:"14",
//							inputValue : true,
//							listeners:{
//								scope :this,
//							    check :function(){
//							    var flag=Ext.getCmp("14").getValue();
//							     if(flag==true){
//							     Ext.getCmp("Q_intentDate_D_GE"+tabflag).setValue();
//							    Ext.getCmp("Q_intentDate_D_LE"+tabflag).setValue();
//							     this.search();
//							    }
//							    }
//							}
//						},' ',' ',' ',' '
//			,{xtype:'label',html : '【<font style="line-height:20px">颜色预警：</font>'}
//						,{xtype:'label',html : '<font color=red>&nbsp;&nbsp未结清项</font>'}
//						,{xtype:'label',html : '&nbsp;&nbsp已结清项】'}
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
					id : 'SlFundIntentGrid1principalpay',
					url : __ctxPath + "/creditFlow/finance/listbyLedgerSlFundIntent.do?businessType=SmallLoan&fundType=('constBack')&typetab=all",
					fields : [{
								name : 'fundIntentId',
								type : 'int'
							}, 'projectName','projectNumber', 'incomeMoney','fundTypeName', 'intentDate',
							'payMoney', 'payInMoney', 'factDate','fundType',
							'afterMoney', 'notMoney','flatMoney', 'isOverdue',
							'overdueRate', 'accrualMoney', 'status','remark','businessType','projectId','tabType','orgName','companyId'],
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
								width : 130
							}
//							, {
//								header : '计划收入金额',
//								dataIndex : 'incomeMoney',
//								align : 'right',
//								width : 150,
//								renderer:function(v){
//								return Ext.util.Format.number(v,',000,000,000.00')+"元"
//                         	     }
//							
//							}
							, {
									header : '退回金额',
								dataIndex : 'payMoney',
								align :'right',
								width : 150,
								renderer:function(v){
								return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							}, {
								header : '计划退回日',
								width : 100,
								dataIndex : 'intentDate',
								align : 'center'
						//		sortable:true
							}, {
								header : '实际退回日',
								width : 100,
								dataIndex : 'factDate'
						//		sortable:true
							}, {
								header : '已对账金额',
								dataIndex : 'afterMoney',
								width : 150,
								align : 'right',
								renderer:function(v){
								return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	}
								
							}, {
								header : '未对账金额',
								dataIndex : 'notMoney',
								align : 'right',
								width : 150,
								sortable:true,
								renderer : function(v) {
									switch (v) {
										case 0:
											return Ext.util.Format.number(v,',000,000,000.00')+"元"
											break;
										default:
											return '<font color="red">'+Ext.util.Format.number(v,',000,000,000.00')+'元</font>';

									}
								}
							}
							, {
								header : '核销金额',
								dataIndex : 'flatMoney',
								align : 'right',
								width : 150,
								renderer:function(v){
								return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	}
							}, {
								header : '退回状态',
								dataIndex : 'tabType'
							
							}
							, {
								header : '备注',
								width : 150,
								dataIndex : 'remark',
								align : 'center',
								hidden : false
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
						tabflag :"principalpay",
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
						tabflag :"principalpay",
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
						
							var setname ='凭证';
							var titleName ='凭证';
							var tableName ='sl_fund_intent_'+record.data.fundType;
							var typeisfile ='fundIntentId.'+fundIntentId;
							
							var mark=tableName+"."+projectId;
							uploadReportJS('上传/下载'+titleName+'文件',typeisfile,mark,15,null,null,null,projectId,businessType,setname);
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
						
							var setname ='凭证';
							var titleName ='凭证';
							var tableName ='sl_fund_intent_'+record.data.fundType;
							var typeisfile ='typeis'+record.data.fundType;
							var mark=tableName+"."+projectId;
							var remark='fundIntentId.'+fundIntentId;
							picViewer(remark,false,typeisfile);
				       }
					
				}
	
			}
		});
