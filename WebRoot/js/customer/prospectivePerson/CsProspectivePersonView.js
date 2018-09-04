/**
 * @author
 * @class CsProspectivePersonView
 * @extends Ext.Panel
 * @description [CsProspectivePersonView]管理
 * @company 智维软件
 * @createtime:
 */
CsProspectivePersonView= Ext.extend(Ext.Panel, {
			otherType:null,
			// 构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg.otherType)!="undefined"){
				      this.otherType=_cfg.otherType;
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				CsProspectivePersonView.superclass.constructor.call(this, {
							id : 'CsProspectivePersonView'+this.otherType+this.ProsperctiveType+this.personType,
							title : this.title,
							region : 'center',
							layout : 'border',
							iconCls : 'btn-tree-team23',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var isShow = false;
				var rightValue =  isGranted('_Prospective_cust_see_All_'+this.ProsperctiveType+"_"+this.personType);
				var isShop = isGranted('_Prospective_cust_see_shop_'+this.ProsperctiveType+"_"+this.personType);
				if (RoleType == "control") {
					isShow = true;
		
				}
				
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
							layoutConfig: {
				               align:'middle',
				               padding : '5px'
				               
				            },
				 //            bodyStyle : 'padding:10px 10px 10px 10px',
							items : [isShow ? {
								columnWidth : 0.2,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
									xtype : "combo",
									anchor : "100%",
									fieldLabel : '所属分公司',
									hiddenName : "companyId",
									displayField : 'companyName',
									valueField : 'companyId',
									triggerAction : 'all',
									store : new Ext.data.SimpleStore({
										autoLoad : true,
										url : __ctxPath+ '/system/getControlNameOrganization.do',
										fields : ['companyId', 'companyName']
									})
								}]
							}: {
							},this.personType==0?{
								columnWidth : isShow ? 0.15 :0.2,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
										fieldLabel : '客户类型',
										hiddenName : 'customerType',
										editable : false,
										displayField : 'name',
									    valueField : 'id',
										editable : false,
										triggerAction : 'all',
									    mode : 'local',
										width:105,
										xtype :'combo',
										store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : [["企业", "1"],["个人", "2"]]
										}),
										anchor : '100%'
								}] 
							}:{}, {  
								columnWidth : isShow ? 0.15 :0.2,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
										fieldLabel : '客户名称',
										name : 'customerName',
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '100%'
										
								}] 
								      
							},{ columnWidth : isShow ? 0.15 :0.2,
								layout : 'form',
								border : false,
								labelWidth : labelsize,
								labelAlign : 'right',
								items : [{
										fieldLabel : '联系电话',
										name : 'telephoneNumber',
										flex : 1,
										editable : false,
										width:105,
										xtype :'textfield',
										anchor : '100%'
										
								} ]
							},/*{
								columnWidth : .2,
								layout : 'form',
								border : false,
								labelWidth : 80,
								labelAlign : 'right',
								items : [{
										fieldLabel : '客户类型',
										hiddenName : 'customerType',
										editable : false,
										width:105,
										displayField : 'name',
									    valueField : 'id',
									    triggerAction : 'all',
										xtype : 'combo',
										mode : 'local',
										autoload : true,
										store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : [["企业", "1"],["个人", "2"]]
													}),
										anchor : '96%'
								}]
							},*/{
								columnWidth :isShow ? 0.15 : .2,
								layout : 'form',
								border : false,
								labelWidth : 80,
								labelAlign : 'right',
								items : [{
									fieldLabel : "跟进状态",
									xtype : "dickeycombo",
									hiddenName : 'followUpType',
									displayField : 'itemName',
									readOnly : this.isRead,
									nodeKey : 'customer_followUpStatus',
									editable :false,
									anchor : "100%",
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												combox.setValue(combox.getValue());
												combox.clearInvalid();
											})
										}
			
									}
								}]
							}, {
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
										}]
							}, {
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
										}]
							}]

						});// end of searchPanel
				if(this.ProsperctiveType==1){
					this.topbar = new Ext.Toolbar({
						items : [{
				        	iconCls : 'btn-details',
				        	text : '查看',
							xtype : 'button',
							scope : this,
							hidden : isGranted('_Prospective_cust_see_'+this.ProsperctiveType+"_"+this.personType)?false:true,
							handler : this.seeCsProspectivePerson
						},new Ext.Toolbar.Separator({
							hidden : isGranted('_Prospective_cust_see_'+this.ProsperctiveType+"_"+this.personType)?false:true
						})/*,{
							iconCls : 'btn-setting',
							text :'导出Excel',
							xtype : 'button',
							scope : this,
							hidden : isGranted('_Prospective_cust_outToExcel_'+this.ProsperctiveType+"_"+this.personType)?false:true,
							handler : this.preview
				        	
				
						}*/]
					});
				}else if(this.ProsperctiveType==2){
					if(this.otherType==0){
						this.topbar = new Ext.Toolbar({
							items : [{
					        	iconCls : 'btn-add',
					        	text : '添加',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_cust_add_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.addCsProspectivePerson
									
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_add_'+this.ProsperctiveType+"_"+this.personType)?false:true
							}),{
					        	iconCls : 'btn-details',
					        	text : '查看',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_cust_see_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.seeCsProspectivePerson
									
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_see_'+this.ProsperctiveType+"_"+this.personType)?false:true
							}),{
								iconCls : 'btn-edit',
								text : '修改',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_cust_edit_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.editCsProspectivePerson
					
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_edit_'+this.ProsperctiveType+"_"+this.personType)?false:true
							}),{
								iconCls : 'btn-ok',
								text :'客户转化',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_cust_changeType_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.ChangeProspertiveType
					
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_changeType_'+this.ProsperctiveType+"_"+this.personType)?false:true
							}),  /*{
								iconCls : 'slupIcon',
								text :'联系人',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_cust_relationPerson_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.BpCustProspectiveRelationList
					        	
					
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_relationPerson_'+this.ProsperctiveType+"_"+this.personType)?false:true
							}),*/{
								iconCls : 'btn-setting',
								text :'跟进',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_cust_followUp_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.prospectiveFollowRecordinfo
					        	
					
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_followUp_'+this.ProsperctiveType+"_"+this.personType)?false:true
							}),{
								iconCls : 'btn-setting1',
								text :'设置下次跟进时间',
								xtype : 'button',
								scope : this,
								hidden :isGranted('_Prospective_cust_setNextTime_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.setNextFollowUpTimeAndRemark
					        	
					
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_setNextTime_'+this.ProsperctiveType+"_"+this.personType)?false:true
							})/*,{
								iconCls : 'btn-setting',
								text :'导出到Excel',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_cust_outToExcel_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.preview
					        	
					
							}*/]
						});
					}else if(this.otherType==1||this.otherType==2 ||this.otherType==3){
						this.topbar = new Ext.Toolbar({
							items : [{
					        	iconCls : 'btn-details',
					        	text : '查看',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_cust_see_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.seeCsProspectivePerson
									
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_see_'+this.ProsperctiveType+"_"+this.personType)?false:true
							}),{
								iconCls : 'btn-edit',
								text : '修改',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_cust_edit_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.editCsProspectivePerson
					
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_edit_'+this.ProsperctiveType+"_"+this.personType)?false:true
							}),{
								iconCls : 'btn-ok',
								text :'客户转化',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_cust_changeType_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.ChangeProspertiveType
					
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_changeType_'+this.ProsperctiveType+"_"+this.personType)?false:true
							})  ,/*{
								iconCls : 'slupIcon',
								text :'联系人',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_cust_relationPerson_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.BpCustProspectiveRelationList
					        	
					
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_relationPerson_'+this.ProsperctiveType+"_"+this.personType)?false:true
							}),*/{
								iconCls : 'btn-setting',
								text :'跟进',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_cust_followUp_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.prospectiveFollowRecordinfo
					        	
					
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_followUp_'+this.ProsperctiveType+"_"+this.personType)?false:true
							}),{
								iconCls : 'btn-setting1',
								text :'设置下次跟进时间',
								xtype : 'button',
								scope : this,
								hidden :isGranted('_Prospective_cust_setNextTime_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.setNextFollowUpTimeAndRemark
					        	
					
							},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_setNextTime_'+this.ProsperctiveType+"_"+this.personType)?false:true
							})/*,{
								iconCls : 'btn-setting',
								text :'导出Excel',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_Prospective_cust_outToExcel_'+this.ProsperctiveType+"_"+this.personType)?false:true,
								handler : this.preview
					        	
					
							}*/]
						});
					}
				}else if(this.ProsperctiveType==3){
					this.topbar = new Ext.Toolbar({
						items : [{
				        	iconCls : 'btn-details',
				        	text : '查看',
							xtype : 'button',
							scope : this,
							hidden : isGranted('_Prospective_cust_see_'+this.ProsperctiveType+"_"+this.personType)?false:true,
							handler : this.seeCsProspectivePerson
						},new Ext.Toolbar.Separator({
							hidden : isGranted('_Prospective_cust_see_'+this.ProsperctiveType+"_"+this.personType)?false:true
						}),{
					        iconCls : 'btn-detailsa',
					        text : '撤销排除',
							xtype : 'button',
							scope : this,
							hidden : isGranted('_Prospective_cust_recovering_'+this.ProsperctiveType+"_"+this.personType)?false:true,
							handler : this.changeRevocation
									
						},new Ext.Toolbar.Separator({
								hidden : isGranted('_Prospective_cust_recovering_'+this.ProsperctiveType+"_"+this.personType)?false:true
						})/*,{
							iconCls : 'btn-setting',
							text :'导出Excel',
							xtype : 'button',
							scope : this,
							hidden : isGranted('_Prospective_cust_outToExcel_'+this.ProsperctiveType+"_"+this.personType)?false:true,
							handler : this.preview
				        	
				
						}*/]
					});
				}
				
				
				this.gridPanel = new HT.EditorGridPanel({
					bodyStyle : "width : 100%",
					id:'BpCustProsperctive'+this.otherType+this.ProsperctiveType,
					region : 'center',
					tbar : this.topbar,
					viewConfig: { 
		            	forceFit:true  
		            },
					rowActions : false,
					url : __ctxPath + "/creditFlow/customer/customerProsperctive/listBpCustProsperctive.do?otherType="+this.otherType+
					            "&prosperctiveType="+this.ProsperctiveType+"&personType="+this.personType+
					            "&isAll="+rightValue+"&isShop="+isShop,
					fields : [{
								name : 'perId',
								type : 'int'
							},'orgName', 'customerType','customerName', 'telephoneNumber','postaddress', 'customerChannel','customerChannelValue',
							'email', 'hangyeType','hangyeName', 'area','loanMoney',
							'loanPeriod', 'loanDate','loanType', 'isMortgage',
							'mortgageRemark', 'remark', 'creatorId','creatorName','createDate','belongId','companyId','followUpType',
							'nextFollowDate','followUpRemark','followUpcount','lastFollowUpDate','conversionReason','sex','departmentId','department','postalcode'],
					columns : [{
								header : 'perId',
								align:'center',
								dataIndex : 'perId',
								hidden : true
							},{
								dataIndex : 'postalcode',
								hidden : true
							},{
								header : "所属分公司",
								sortable : true,
								width : 120,
								align:'center',
								hidden:RoleType=="control"?false:true,
								dataIndex : 'orgName'
							},/* {
								header : '客户类型',
								dataIndex : 'customerType',
								width : 100,
								renderer:function(v){
									if(eval(v)==eval(1)){
										return "企业";
									}else if(eval(v)==eval(2)){
										return "个人";
									}else{
										return "";
									}
                         	    }
							}, */{
								header : '客户名称',
								dataIndex : 'customerName',
								width : 100
							}, {
								header : '客户来源',
								dataIndex : 'customerChannel',
								xtype:'combocolumn',
                				gridId:'BpCustProsperctive'+this.otherType+this.ProsperctiveType,
								editor:{
									xtype : "dickeycombo",
									displayField : 'itemName',
									readOnly : true,
									nodeKey : 'customer_channel',
									editable :false,
									anchor : "100%",
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												combox.setValue(combox.getValue());
												combox.clearInvalid();
											})
										}
			
									}
								},
								width : 100
							}, {
								header : '地区',
								dataIndex : 'area',
								align:'center',
								width : 130
							
							}, {
								header : '联系电话',
								dataIndex : 'telephoneNumber',
								align:'center',
								width : 120
							
							},/* {
								header : '主营业务',
								width : 100,
								dataIndex : 'hangyeName',
								align : 'center'
							},*/ {
								header :'创建人',
								width : 100,
								align:'center',
								dataIndex : 'creatorName'
						//		sortable:true
							}, {
								header : '跟进状态',
								align:'center',
								dataIndex : 'followUpType',
								width : 100,
								xtype:'combocolumn',
                				gridId:'BpCustProsperctive'+this.otherType+this.ProsperctiveType,
                				editor:{fieldLabel : "跟进状态",
									xtype : "dickeycombo",
									hiddenName : 'bpCustProsperctive.followUpType',
									displayField : 'itemName',
									readOnly : this.isRead,
									nodeKey : 'customer_followUpStatus',
									emptyText : "请选择",
									editable :false,
									anchor : "100%"
								},
								align : 'right'
								
							}, {
								header : '录入时间',
								align:'center',
								dataIndex : 'createDate',
								align : 'right',
								width : 100,
								sortable:true
							}
							, {
								header : '最后跟进时间',
								align:'center',
								dataIndex : 'lastFollowUpDate',
								align : 'right',
								width : 100
							}, {
								header : '跟进次数(次)',
								align:'center',
								dataIndex : 'followUpcount',
								width : 100
							
							}, {
								header : '下次跟进时间',
								width : 150,
								dataIndex : 'nextFollowDate',
								align : 'center',
								hidden : false
							}, {
								header : '转化理由',
								dataIndex : 'conversionReason',
								align : 'left',
								width :250,
								hidden : ((this.ProsperctiveType==1 || this.ProsperctiveType==3)?false:true)
							}/*, {
								header : '提醒内容',
								width : 200,
								dataIndex : 'followUpRemark',
								align : 'left',
								hidden : false
							}*/
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
			/*	var obj = Ext.getCmp('Q_fundType_N_EQ'+tabflag);
			//	obj.setEditable(false);
				var arrStore= new Ext.data.SimpleStore({});
				obj.clearValue();
                obj.store = arrStore;
			    arrStore.load({"callback":test});
			    function test(r){
			    	if (obj.view) { // 刷新视图,避免视图值与实际值不相符
			    		obj.view.setStore(arrStore);
			        }
								       
								    }*/
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
			
			addCsProspectivePerson : function() {
				new CsProspectivePersonForm({
					titleChange:"添加意向客户",
					isHiddenCommRecord:true,
					flashTargat:this.gridPanel.getStore(),
					isReadOnly:false,
					personType:this.personType
				}).show()
			},
			seeCsProspectivePerson : function() {
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if (selRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
					return;
				}
				if (selRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
					return;
				}else{
					var record = this.gridPanel.getSelectionModel().getSelected();
					new CsProspectivePersonForm({
						perId:record.data.perId,
						titleChange:"查看意向客户",
						isHiddenCommRecord:false,
						isReadOnly:true,
						isLook:true,
						personType: this.personType
					}).show()
				}
				
			},
			editCsProspectivePerson : function() {
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if (selRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
					return;
				}
				if (selRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
					return;
				}else{
					var record = this.gridPanel.getSelectionModel().getSelected();
					new CsProspectivePersonForm({
						perId:record.data.perId,
						titleChange:"编辑意向客户",
						flashTargat:this.gridPanel.getStore(),
						isHiddenCommRecord:false,
						isReadOnly:false,
						isLook:false,
						personType: this.personType
					}).show()
				}
			},
			//联系人
			BpCustProspectiveRelationList:function(){
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if (selRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
					return;
				}
				if (selRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
					return;
				}else{
					var record = this.gridPanel.getSelectionModel().getSelected();
					new CsProspectiveRelationView({
						perId:record.data.perId
					}).show()
				}
			
			},
			//意向客户跟进
			prospectiveFollowRecordinfo:function(){
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if (selRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
					return;
				}
				if (selRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
					return;
				}else{
					var record = this.gridPanel.getSelectionModel().getSelected();
					new prospectiveFollowInfoForm({
						perId:record.data.perId,
						isReadOnly:true,
						personType:this.personType,
						gpanel : this.gridPanel
					}).show()
				}
			},
			//意向客户转化
			ChangeProspertiveType:function(){
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if (selRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
					return;
				}
				if (selRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
					return;
				}else{
					var record = this.gridPanel.getSelectionModel().getSelected();
					new ChangeProspertiveType({
						flashTargat:this.gridPanel,
						perId:record.data.perId,
						titleChange:"客户转化",
						parRecord:record,
						personType : this.personType//客户类型，区分客户为投资端客户还是借款端客户
					}).show()
				}
			},//意向客户设置下次跟进时间
			setNextFollowUpTimeAndRemark:function(){
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if (selRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
					return;
				}
				if (selRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
					return;
				}else{
					var record = this.gridPanel.getSelectionModel().getSelected();
					new setNextFollowUpTimeAndRemark({
						perId:record.data.perId,
						flashTargat:this.gridPanel,
						titleChange:"设置下次跟进时间"
					}).show()
				}
			},
			changeRevocation:function(){
				var panel =this.gridPanel;
				var selRs = this.gridPanel.getSelectionModel().getSelections();
				if (selRs.length == 0) {
					Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
					return;
				}
				if (selRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
					return;
				}else{
					var perId =this.gridPanel.getSelectionModel().getSelected().data.perId;
					var arg={
						"bpCustProsperctive.perId":perId,
						"bpCustProsperctive.prosperctiveType":2
					}
					Ext.Msg.confirm("信息提示!", '确定撤销排除客户，恢复为意向客户？', function(btn) {
						if (btn == 'yes'){
							Ext.Ajax.request({
								url : __ctxPath + "/creditFlow/customer/customerProsperctive/changePersonTypeBpCustProsperctive.do",
								method : 'POST',
								scope:this,
								success :function(response, request){
									var object=Ext.util.JSON.decode(response.responseText);
									panel.getStore().reload();
								},
								params : arg
					       })
						}
					})																																								
					
				}
			}
		});
