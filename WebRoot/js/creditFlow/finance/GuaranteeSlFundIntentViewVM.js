/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */


GuaranteeSlFundIntentViewVM = Ext.extend(Ext.Panel, {
			infoObj : null,
			isHiddenAddBtn : true,
			isHiddenDelBtn : true,
			isHiddenCanBtn : true,
			isHiddenResBtn : true,
			isHiddenTitle : false, 
			// 构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				if (typeof (_cfg.projectId) != "undefined") {
					this.projectId = _cfg.projectId;
				}
			    if (typeof (_cfg.businessType) != "undefined") {
					this.businessType = _cfg.businessType;
				}
				if (typeof (_cfg.isHiddenAddBtn) != "undefined") {
					this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
				}
				if (typeof (_cfg.isHiddenDelBtn) != "undefined") {
					this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
				}
				if (typeof (_cfg.isHiddenCanBtn) != "undefined") {
					this.isHiddenCanBtn = _cfg.isHiddenCanBtn;
				}
				if (typeof (_cfg.isHiddenResBtn) != "undefined") {
					this.isHiddenResBtn = _cfg.isHiddenResBtn;
				}
				if (typeof (_cfg.enableEdit) != "undefined") {
					this.enableEdit = _cfg.enableEdit;
				}
				if (typeof (_cfg.isHiddenTitle) != "undefined") {
					this.isHiddenTitle = _cfg.isHiddenTitle;
				}
	//			this.businessType="SmallLoan"
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				GuaranteeSlFundIntentViewVM.superclass.constructor.call(this, {
							region : 'center',
							layout : 'anchor',
							items : [
								{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【款项信息】</font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>',hidden : this.isHiddenTitle},
								this.gridPanel
							]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				this.datafield=new Ext.form.DateField( {
									format : 'Y-m-d',
									allowBlank : false,
									readOnly:this.isHidden1
								});
				this.datafield1=new Ext.form.DateField( {
									format : 'Y-m-d',
									allowBlank : false,
									readOnly:this.isHidden1
				}); 
				this.comboType= new DicIndepCombo({
									editable : true,
									lazyInit : false,
									forceSelection : false,
									nodeKey : "Guarantee_fund",
							//		itemVale : 1149,
							//		itemName : '贷款资金类型',
									readOnly:this.isHidden1
								})
				this.comboType.store.reload();
				this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-add',
									text : '增加',
									xtype : 'button',
									scope : this,
									hidden : this.isHiddenAddBtn,
									handler : this.createRs
								},new Ext.Toolbar.Separator({
									hidden : this.isHiddenAddBtn
								}),{
									iconCls : 'btn-del',
									text : '删除',
									xtype : 'button',
									scope : this,
									hidden : this.isHiddenDelBtn,
									handler : this.removeSelRs
								},new Ext.Toolbar.Separator({
									hidden : this.isHiddenDelBtn
								}),{
									iconCls : 'btn-close',
									text : '取消',
									xtype : 'button',
									scope : this,
									hidden : this.isHiddenCanBtn,
									handler : this.cancel
								},new Ext.Toolbar.Separator({
									hidden : this.isHiddenCanBtn
								}),{
									iconCls : 'btn-reset',
									text : '还原',
									xtype : 'button',
									scope : this,
									hidden : this.isHiddenResBtn,
									handler : this.back
								},new Ext.Toolbar.Separator({
									hidden : this.isHiddenResBtn
								}),{
									iconCls : 'btn-detail',
									text : '查看单项流水记录',
									xtype : 'button',
									scope : this,
									handler : function() {
										var selRs = this.gridPanel.getSelectionModel().getSelections();
						               	if (selRs <= 0) {
											Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
											return ;
										}else if(selRs.length>1){
											Ext.ux.Toast.msg('操作信息','只能选中一条记录');
											return ;
										}
										this.fundIntentWaterReconciliationInfo.call(this,selRs[0],1);
									}
								},'-',{
									iconCls : 'btn-details',
									text : '项目总流水记录',
									xtype : 'button',
									scope : this,
									handler : function() {
										this.projectWaterReconciliationInfo(2);
									}
								},'-',{
									iconCls : 'btn-setting',
									text : '预览凭证',
									xtype : 'button',
									scope : this,
									handler : function() {
										this.preview();
									}
								}
				
								]
				});
				var field = Ext.data.Record.create([{name : 'fundIntentId'},{name : 'fundType'},{name : 'fundTypeName'},{name : 'incomeMoney'},{name : 'payMoney'},{name : 'intentDate'},{name : 'factDate'},{name : 'afterMoney'}
				,{name : 'notMoney'},{name : 'accrualMoney'},{name : 'isValid'},{name : 'flatMoney'},{name : 'overdueRate'},{name : 'isOverdue'},{name : 'remark'}]);
				var jStore = new Ext.data.JsonStore({
					url : __ctxPath+ "/creditFlow/finance/listbyGuranteeSlFundIntent.do",
					root : 'result',
					fields : field
				});
				jStore.load({
					params : {
						projectId : this.projectId,
						businessType :this.businessType
					}
				});
				this.projectFundsm = new Ext.grid.CheckboxSelectionModel({header:'序号'});
				this.gridPanel = new HT.EditorGridPanel({
					border : false,
					name : 'gridPanel',
					scope : this,
					store : jStore,
					autoScroll : true,
					autoWidth : true,
					layout : 'anchor',
					clicksToEdit : 1,
					viewConfig : {
						forceFit : true
					},
					bbar : false,
					tbar : this.topbar,
					rowActions : false,
					showPaging : false,
					stripeRows : true,
					plain : true,
					loadMask : true,
					autoHeight : true,
					sm : this.projectFundsm,
					plugins: [summary],
					columns : [
							{
								header : 'fundIntentId',
								dataIndex : 'fundIntentId',
								hidden : true
							}, {
								header : '资金类型',
								dataIndex : 'fundType',
								editor :this.comboType,
								width : 150,
								summaryType: 'count',
								summaryRenderer: totalMoney,
								renderer : function(value,metaData, record,rowIndex, colIndex,store) {
									
									var objcom = this.editor;
									var objStore = objcom.getStore();
						
									var idx = objStore.find("dicKey", value);
									var flag1=0;            //incomeMoney
								     if(record.data.payMoney !=0){   //payMoney
								     	flag1=1;
								     }
									if (idx != "-1") {
                                           if(record.data.isValid==1){
										return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+objStore.getAt(idx).data.itemValue+"</font>"
									      }else {
									      	if(record.data.notMoney==0){
										      		 return '<font style="color:gray">'+objStore.getAt(idx).data.itemValue+"</font>";
										      	}
									      	  if(record.data.isOverdue=="是" && flag1 !=1){
										      	
										      		 return '<font style="color:red">'+objStore.getAt(idx).data.itemValue+"</font>";
										      	}
										      	
										      	if(record.data.afterMoney==0){
										      	   return objStore.getAt(idx).data.itemValue;
										      	}
										      	
										     return objStore.getAt(idx).data.itemValue;
									     
									      }
										
									} else {
								        
										if(record.data.fundTypeName==""){
											
											record.data.fundTypeName=value
											
										}else{
											var x = store.getModifiedRecords();
											if(x!=null && x!=""){
												record.data.fundTypeName=value
											}
										}
										if(record.data.isValid==1){
										return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+record.get("fundTypeName")+"</font>"
									      }else{
									      	if(record.data.notMoney==0){
										      		 return '<font style="color:gray">'+record.get("fundTypeName")+"</font>";
										      	}
									      	  if(record.data.isOverdue=="是" && flag1 !=1){
										      	
										      		 return '<font style="color:red">'+record.get("fundTypeName")+"</font>";
										      	}
									         	
										      	if(record.data.afterMoney==0){
										      	  return record.get("fundTypeName");
										      	
										      	}
									            
										      	 return record.get("fundTypeName");
									      } 
										 
									}
									
						     }
							},{
								header : '计划收入金额',
								dataIndex : 'incomeMoney',
								summaryType: 'sum',
								align : 'right',
								editor :{
									xtype : 'numberfield',
									allowBlank : false,
									readOnly:this.isHidden1
								},
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
										var flag1=0;            //incomeMoney
								     if(record.data.payMoney !=0){   //payMoney
								     	flag1=1;
								     }
										if(record.data.isValid==1){
										return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+"元</font>"
									}else{     if(record.data.notMoney==0){
										      		 return '<font style="color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+"元"+"</font>";
										      	}
										        if(record.data.isOverdue=="是" && flag1 !=1){
										      	
										      		 return '<font style="color:red">'+Ext.util.Format.number(value,',000,000,000.00')+"元"+"</font>";
										      	}
									         
										      	if(record.data.afterMoney==0){
										      	  return Ext.util.Format.number(value,',000,000,000.00')+"元"
										      	
										      	}
									           
										      	 return Ext.util.Format.number(value,',000,000,000.00')+"元";
									}
												  
								
												  
								}
							}, {
								header : '计划支出金额',
								dataIndex : 'payMoney',
								align : 'right',
								summaryType: 'sum',
								editor :  {
									xtype : 'numberfield',
									allowBlank : false,
									readOnly:this.isHidden1
								},
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
										var flag1=0;            //incomeMoney
								     if(record.data.payMoney !=0){   //payMoney
								     	flag1=1;
								     }
									if(record.data.isValid==1){
										return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+"元</font>"
									}else{    
										if(record.data.notMoney==0){
										      		 return '<font style="color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+"元"+"</font>";
										      	}
										       if(record.data.isOverdue=="是" && flag1 !=1){
										      	
										      		 return '<font style="color:red">'+Ext.util.Format.number(value,',000,000,000.00')+"元"+"</font>";
										      	}
									          
										      	if(record.data.afterMoney==0){
										      	  return Ext.util.Format.number(value,',000,000,000.00')+"元";
										      	
										      	}
									            
									       return Ext.util.Format.number(value,',000,000,000.00')+"元";
									}
												  
								}
							}, {
								header : '计划到帐日',
								dataIndex : 'intentDate',
								format : 'Y-m-d',
								editor :this.datafield,
								width : 80,
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
										var flag1=0;            //incomeMoney
								     if(record.data.payMoney !=0){   //payMoney
								     	flag1=1;
								     }
									var v;
									try{
								             if(typeof (value) == "string"){ 
								               v= value; 
								               return v;
								              } 
								              return Ext.util.Format.date(value, 'Y-m-d'); 
								         }
								         catch(err) {
								                v=value;
								                return v;
								         }
									if(record.data.isValid==1){
										return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+value+"</font>";
									}else{      
										          if(record.data.notMoney==0){
										      		 return '<font style="color:gray">'+v+"</font>";
										      	}
										         if(record.data.isOverdue=="是" && flag1 !=1){
										      	
										      		 return '<font style="color:red">'+v+"</font>";
										      	}
									         
										      	if(record.data.afterMoney==0){
										      	 return v;
										      	
										      	}
									            return v;
										          	 
										      	 
									}
												  
								
												  
								}
							}
							,  {
								header : '实际到帐日',
								dataIndex : 'factDate',
								format : 'Y-m-d',
								width : 80,
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
										var flag1=0;            //incomeMoney
								     if(record.data.payMoney !=0){   //payMoney
								     	flag1=1;
								     }
									var v;
									try{
								             if(typeof (value) == "string"){ 
								               v= value; 
								              } 
								              return Ext.util.Format.date(value, 'Y-m-d'); 
								         }
								         catch(err) {
								                v=value;
								         }
									 if(v !=null){
										  if(record.data.isValid==1){
											
										        return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+v+"</font>";
										    
									      }else{
									      	      if(record.data.notMoney==0){
										      		 return '<font style="color:gray">'+v+"</font>";
										      	}
										         if(record.data.isOverdue=="是" && flag1 !=1){
										      	
										      		 return '<font style="color:red">'+v+"</font>";
										      	}
									         
										      	if(record.data.afterMoney==0){
										      	 return v;
										      	
										      	}
									           
										      	 return v;
									     }
												  
								    }else{
								         return "";
								    }
												  
								}
							}
							, {
								header : '已对账金额',
								dataIndex : 'afterMoney',
								align : 'right',
								summaryType: 'sum',
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
										var flag1=0;            //incomeMoney
								     if(record.data.payMoney !=0){   //payMoney
								     	flag1=1;
								     }
									            if(value !=null){
											            	if(record.data.isValid==1){
															return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+"元</font>"
														}else{
															  if(record.data.notMoney==0){
													      		 return '<font style="color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+"元"+"</font>";
													      	}
															 if(record.data.isOverdue=="是" && flag1 !=1){
													      	
													      		 return '<font style="color:red">'+Ext.util.Format.number(value,',000,000,000.00')+"元"+"</font>";
													      	}
															
													      	if(record.data.afterMoney==0){
													      	  return Ext.util.Format.number(value,',000,000,000.00')+"元"
													      	
													      	}
												            
													      	 return Ext.util.Format.number(value,',000,000,000.00')+"元"
																	}
												}else return "";
												  
								}
							}, {
								header : '未对账金额',
								dataIndex : 'notMoney',
								align : 'right',
								summaryType: 'sum',
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
										var flag1=0;            //incomeMoney
								     if(record.data.payMoney !=0){   //payMoney
								     	flag1=1;
								     }
									     if(value !=null){
											            	if(record.data.isValid==1){
															return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+"元</font>"
														}else{
															 if(record.data.notMoney==0){
													      		 return '<font style="color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+"元"+"</font>";
													      	}
															 if(record.data.isOverdue=="是" && flag1 !=1){
													      	
													      		 return '<font style="color:red">'+Ext.util.Format.number(value,',000,000,000.00')+"元"+"</font>";
													      	}
															
													      	if(record.data.afterMoney==0){
													      	  return Ext.util.Format.number(value,',000,000,000.00')+"元"
													      	
													      	}
												            
													      	 return Ext.util.Format.number(value,',000,000,000.00')+"元"
																	}
												}else return "";
								}
							}, {
								header : '已对账金额',
								dataIndex : 'flatMoney',
								align :'right',
								summaryType: 'sum',
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
										var flag1=0;            //incomeMoney
								     if(record.data.payMoney !=0){   //payMoney
								     	flag1=1;
								     }
									            if(value !=null){
											            	if(record.data.isValid==1){
															return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+"元</font>"
														}else{
															if(record.data.notMoney==0){
													      		 return '<font style="color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+"元"+"</font>";
													      	}
															 if(record.data.isOverdue=="是" && flag1 !=1){
													      	
													      		 return '<font style="color:red">'+Ext.util.Format.number(value,',000,000,000.00')+"元"+"</font>";
													      	}
														
													      	if(record.data.afterMoney==0){
													      	  return Ext.util.Format.number(value,',000,000,000.00')+"元"
													      	
													      	}
												            
													      	 return Ext.util.Format.number(value,',000,000,000.00')+"元"
														}
												}else return "";
												  
								}

							},{
								header : '逾期费率',
								dataIndex : 'overdueRate',
								align : 'center',
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
									
									            if(value !=null && record.data.fundType=="GuaranteeToCharge"){
											            	if(record.data.isValid==1){
															return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+Ext.util.Format.number(value,',000,000,000.000')+"%/日</font>"
														}else{
															if(record.data.notMoney==0){
													      		 return '<font style="color:gray">'+Ext.util.Format.number(value,',000,000,000.000')+"‰/月"+"</font>";
													      	}
															 if(record.data.isOverdue=="是"){
													      	
													      		 return '<font style="color:red">'+Ext.util.Format.number(value,',000,000,000.000')+"‰/月"+"</font>";
													      	}
															
													      	if(record.data.afterMoney==0){
													      	  return Ext.util.Format.number(value,',000,000,000.000')+"‰/月"
													      	
													      	}
												           
													      	 return Ext.util.Format.number(value,',000,000,000.000')+"‰/月"
														}
												}else return "";
												  
								}
							}, {
								header : '逾期违约金总额',
								dataIndex : 'accrualMoney',
								align :'right',
								summaryType: 'sum',
								width : 100,
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
									
											  if(value !=null && record.data.fundType=="GuaranteeToCharge"){
															if(record.data.isValid==1){
																	return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+"元</font>"
													    }else{ 
													    	if(record.data.notMoney==0){
													      		 return '<font style="color:gray">'+Ext.util.Format.number(value,',000,000,000.00')+"元"+"</font>";
														      	}
													    	     if(record.data.isOverdue=="是"){
														      	
														      		 return '<font style="color:red">'+Ext.util.Format.number(value,',000,000,000.00')+"元"+"</font>";
														      	}
																
														      	if(record.data.afterMoney==0){
														      	  return Ext.util.Format.number(value,',000,000,000.00')+"元"
														      	
														      	}
													          
														      	 return Ext.util.Format.number(value,',000,000,000.00')+"元"
														}
													}
												  else return "";
												  
								}
								
								}, {
								header : '备注',
								dataIndex : 'remark',
								align :'right',
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
										var flag1=0;            //incomeMoney
								     if(record.data.payMoney !=0){   //payMoney
								     	flag1=1;
								     }
									            if(value !=null){
											            	if(record.data.isValid==1){
															return '<font style="font-style:italic;text-decoration: line-through;color:gray">'+value+"</font>";
														}else{
															if(record.data.notMoney==0){
													      		 return '<font style="color:gray">'+value+"</font>";
													      	}
															 if(record.data.isOverdue=="是" && flag1 !=1){
													      	
													      		 return '<font style="color:red">'+value+"</font>";
													      	}
														
													      	if(record.data.afterMoney==0){
													      	   return value;
													      	
													      	}
												            
													      	  return value;
														}
												}else return "";
												  
								}

							}

							],
						listeners : {
							scope : this,
							beforeedit : function(e) {
								if(e.record.data['isValid'] !=null){
									if(e.record.data['isValid'] == 1 || e.record.data['afterMoney'] !=0) {
										e.cancel = true;
									}
								}
								if(e.record.data['fundIntentId'] != "") {
									if(this.enableEdit == true) {
										e.cancel = false;
									}else if (this.enableEdit == false) {
										e.cancel = true;
									}
								}else if(e.record.data['fundIntentId'] == "") {
									e.cancel = false;
								}
							},
								afteredit : function(e) {
										if(e.record.data['fundType'] !='BackCustomGuarantMoney'){
												e.record.set('payMoney',0);
												e.record.commit()
										}
										if(e.record.data['fundType'] =='BackCustomGuarantMoney'){
											e.record.set('incomeMoney',0);
											e.record.commit();
								}
							  }
					    } 
				});
				this.infoPanel= new Ext.Panel({
					border:false,
					layout : {
						type : 'hbox',
						pack : 'left'
					},
					defaults : {
						margins : '10 10 0 0'
					},
					name : "infoPanel",
					items:[]
				});
				
			},// end of the initComponents()
			
			createRs : function() {
				this.datafield.setValue('');
				var newRecord = this.gridPanel.getStore().recordType;
				var newData = new newRecord( {
					fundIntentId : '',
					fundType : '',
					payMoney : 0,
					incomeMoney : 0,
					intentDate : new Date(),
					factDate : ''
				});
				this.gridPanel.stopEditing();
				this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(), newData);
				this.gridPanel.getView().refresh();
				this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore().getCount()-1));
				this.gridPanel.startEditing(0, 0);
			},
				
			getGridDate : function() {
				var vRecords = this.gridPanel.getStore().getRange(0,
						this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
				var vCount = vRecords.length; // 得到记录长度
				var vDatas = '';
				if (vCount > 0) {
					// begin 将记录对象转换为字符串（json格式的字符串）
					for ( var i = 0; i < vCount; i++) {
						
					if(vRecords[i].data.fundType !=null && vRecords[i].data.fundType !=""){
                        if(vRecords[i].data.fundIntentId == null || vRecords[i].data.fundIntentId == "")  {
                        
                        	   var st="";
								if(vRecords[i].data.factDate==null || vRecords[i].data.factDate=="" ){
		                          st={"fundType":vRecords[i].data.fundType,"fundTypeName":vRecords[i].data.fundTypeName,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark};
		                         }else{
		                            st={"fundType":vRecords[i].data.fundType,"fundTypeName":vRecords[i].data.fundTypeName,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"factDate":vRecords[i].data.factDate,"remark":vRecords[i].data.remark};
		                         }
		                         vDatas += Ext.util.JSON
								.encode(st) + '@';
                            } else  {
	                                   if(vRecords[i].data.isValid == 0 && vRecords[i].data.afterMoney ==0){
			                                  var st="";
											if(vRecords[i].data.factDate==null || vRecords[i].data.factDate=="" ){
					                          st={"fundIntentId":vRecords[i].data.fundIntentId,"fundType":vRecords[i].data.fundType,"fundTypeName":vRecords[i].data.fundTypeName,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"remark":vRecords[i].data.remark};
					                         }else{
					                            st={"fundIntentId":vRecords[i].data.fundIntentId,"fundType":vRecords[i].data.fundType,"fundTypeName":vRecords[i].data.fundTypeName,"incomeMoney":vRecords[i].data.incomeMoney,"payMoney":vRecords[i].data.payMoney,"intentDate":vRecords[i].data.intentDate,"factDate":vRecords[i].data.factDate,"remark":vRecords[i].data.remark};
					                         }
					                         vDatas += Ext.util.JSON
											.encode(st) + '@';
	                                   
	                                    }
                            }
					}
					}
              
					vDatas = vDatas.substr(0, vDatas.length - 1);
				}
				return vDatas;
			},
			
			//保存数据
			saveRs : function() {
				var data = this.getGridDate();
				Ext.Ajax.request( {
							url : __ctxPath + '/creditFlow/finance/savejson1SlFundIntent.do',
							method : 'POST',
							scope : this,
							params : {
								slFundIentJson : data,
								projectId : this.projectId,
								businessType : this.businessType
							},
							success : function(response, request) {
								this.gridPanel.getStore().reload();
								this.gridPanel.getView().refresh();	
							}
						});

			},
			save : function(){
				this.gridPanel.getStore().reload();
				this.gridPanel.getView().refresh();	
			},
			
			cancel : function(){
				
				var gridPanel = this.gridPanel;
				var projId=this.projectId;
				 var inforpanel=this.getCmpByName("infoPanel")
				var selRs = gridPanel.getSelectionModel().getSelections();
                 if (selRs <= 0) {
												Ext.ux.Toast.msg('操作信息','请选择要取消的记录');
												return false;
								};
				var deleteFun = function(url, prame, sucessFun,i,j) {
					Ext.Ajax.request( {
						url : url,
						method : 'POST',
						success : function(response) {
							if (response.responseText.trim() == '{success:true}') {
								if(i==(j-1)){
												    Ext.ux.Toast.msg('操作信息', '取消成功!');
												   
												}
							var s = gridPanel.getSelectionModel().getSelections();
							for ( var i = 0; i < s.length; i++) {
								var row = s[i];
								if (row.data.fundIntentId == null || row.data.fundIntentId == '') {
								} else {
									row.data.isValid=1;
								}
							}
								gridPanel.getView().refresh();	
							
						} else if (response.responseText.trim() == '{success:false}') {
								Ext.ux.Toast.msg('状态', '取消失败!');
							}
						},
						params : prame
					});
				};
	           
				Ext.Msg.confirm("提示!",'确定要取消吗？',function(btn) {
					if (btn == "yes") {
						var s = gridPanel.getSelectionModel().getSelections();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.fundIntentId == null || row.data.fundIntentId == '') {
	//							gridPanel.getStore().remove(row);
							} else {

								deleteFun(
									__ctxPath + '/creditFlow/finance/cancelSlFundIntent.do',
									{
										fundIntentId : row.data.fundIntentId,
										projectId : projId,
										businessType :this.businessType     
									},
									function() {
										gridPanel.getStore().reload();
									},i,s.length);
									
							}
							
					 
						}
						  
					}
				});
		
			}, 
			back : function(){
				var gridPanel = this.gridPanel;
				var projId=this.projectId;
				 var inforpanel=this.getCmpByName("infoPanel")
				var selRs = gridPanel.getSelectionModel().getSelections();
                 if (selRs <= 0) {
												Ext.ux.Toast.msg('操作信息','请选择要还原的记录');
												return false;
								};
				var deleteFun = function(url, prame, sucessFun,i,j) {
					Ext.Ajax.request( {
						url : url,
						method : 'POST',
						success : function(response) {
							if (response.responseText.trim() == '{success:true}') {
								if(i==(j-1)){
												    Ext.ux.Toast.msg('操作信息', '还原成功!');
												}
								var s = gridPanel.getSelectionModel().getSelections();
							for ( var i = 0; i < s.length; i++) {
								var row = s[i];
								if (row.data.fundIntentId == null || row.data.fundIntentId == '') {
								} else {
									row.data.isValid=0;
								}
							}
								gridPanel.getView().refresh();
							} else if (response.responseText.trim() == '{success:false}') {
								Ext.ux.Toast.msg('状态', '还原失败!');
							}
						},
						params : prame
					});
				};
	           
				Ext.Msg.confirm("提示!",'确定要还原吗？',function(btn) {
					if (btn == "yes") {
						var s = gridPanel.getSelectionModel().getSelections();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.fundIntentId == null || row.data.fundIntentId == '') {
	//							gridPanel.getStore().remove(row);
							} else {
								deleteFun(
									__ctxPath + '/creditFlow/finance/backSlFundIntent.do',
									{
										fundIntentId : row.data.fundIntentId,
										projectId : projId,
										businessType :this.businessType
									},
									function() {
									gridPanel.getStore().reload();
									},i,s.length);
							}
						}
					}
				});

			}, 
			removeSelRs : function() {
				var gridPanel = this.gridPanel;
				var projId=this.projectId;
				 var inforpanel=this.getCmpByName("infoPanel")
				var selRs = gridPanel.getSelectionModel().getSelections();
                 if (selRs <= 0) {
												Ext.ux.Toast.msg('操作信息','请选择要删除的记录');
												return false;
								};
				var deleteFun = function(url, prame, sucessFun,i,j) {
					Ext.Ajax.request( {
						url : url,
						method : 'POST',
						success : function(response) {
							if (response.responseText.trim() == '{success:true}') {
								if(i==(j-1)){
												    Ext.ux.Toast.msg('操作信息', '删除成功!');
												}
								sucessFun();
								
							} else if (response.responseText.trim() == '{success:false}') {
								Ext.ux.Toast.msg('状态', '删除失败!');
							}
						},
						params : prame
					});
				};
	           
				Ext.Msg.confirm("提示!",'确定要删除吗？',function(btn) {
					if (btn == "yes") {
						var s = gridPanel.getSelectionModel().getSelections();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.fundIntentId == null || row.data.fundIntentId == '') {
								gridPanel.getStore().remove(row);
							} else {
								deleteFun(
									__ctxPath + '/creditFlow/finance/deleteSlFundIntent.do',
									{
										fundIntentId : row.data.fundIntentId,
										projectId : projId,
										businessType :this.businessType
									},
									function() {
										gridPanel.getStore().remove(row);
										gridPanel.getStore().reload();
									},i,s.length);
							}
						}
					}
				});
			},
					
			//查看款项流水对账
			fundIntentWaterReconciliationInfo : function(record,flag) {
				var hidden=false;
				if(record.data.fundType == "ToCustomGuarantMoney" || record.data.payMoney !=0)
				{
					hidden=true;
				}
				new detailView({
					fundIntentId : record.get("fundIntentId"),
					flag : flag,//1.款项流水2.项目流水,
					fundType : record.data.fundType,
					hidden1 : hidden,
					hidden2 :true
				}).show();
			},
			
			//查看项目流水对账
			projectWaterReconciliationInfo : function(flag) {
				new detailView({
					projectId : this.projectId,
					flag : flag,//1.款项流水2.项目流水
					hidden1 : false,
					hidden2 :true,
					businessType :this.businessType
				}).show();
			},
			
			onRowAction : function(grid, record, action, row, col) {
				this.fundIntentWaterReconciliationInfo.call(this,record,1);
			},
			  preview:function(){
				var s = this.gridPanel.getSelectionModel().getSelections();
				var businessType=this.businessType;
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var	record=s[0];
					var	projectId =record.data.projectId;
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
	
			}
			
});
