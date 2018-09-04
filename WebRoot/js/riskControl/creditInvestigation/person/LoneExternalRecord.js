/**
 * @author:
 * @class LoneExternalRecord
 * @extends Ext.Panel
 * @description [LoneExternalRecord]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
LoneExternalRecord = Ext.extend(Ext.Panel, {
	
	// 构造函数
	constructor : function(_cfg) {
	   if(typeof(_cfg.customerType)!="undefined"){
	 		this.customerType=_cfg.customerType
		}
	/*		if(typeof(_cfg.customerId)!="undefined"){
			this.customerId=_cfg.customerId
		}*/
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		LoneExternalRecord.superclass.constructor.call(this, {
			id : 'LoneExternalRecord'+this.customerType,
			title : '外部借贷登记',
			region : 'center',
			layout : 'border',
			iconCls:"btn-tree-team36",
			items : [this.searchPanel ,this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
			// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			layout : 'column',
			border : false,
			region : 'north',
			height : 65,
			anchor : '100%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
			items : [{
				columnWidth : 1,
				layout : 'column',
				border : false,
				items : [{
					columnWidth : .16,
					layout : 'form',
					labelWidth : 60,
					labelAlign : 'right',
					border : false,
					items : [{
						xtype : "combo",
						fieldLabel : "业务类型",
						anchor : '100%',
						hiddenName : "businessType",
						allowBlank : true,
						displayField : 'itemName',
						valueField : 'itemId',
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath
									+ '/creditFlow/getBusinessTypeListAllCreditProject.do',
							fields : ['itemId', 'itemName']
						}),
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
					
								})
								combox.clearInvalid();
							}
						}
					}]
				},{
											xtype : 'hidden',
											name : 'customerId'
										},this.customerType=='person_customer'?{
					columnWidth : .2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
												xtype : 'combo',
												triggerClass : 'x-form-search-trigger',
												fieldLabel : "借款人",
												name : "customerName",
												readOnly : this.isPersonNameReadOnly,
												allowBlank : true,
												editable:false,
												blankText : "借款人不能为空，请正确填写!",
												anchor : "100%",
												onTriggerClick : function() {
												//	ressetProject(businessType);
													var op=this.ownerCt.ownerCt.ownerCt.ownerCt;
													var selectCusNew = function(obj){
														op.getCmpByName('customerId').setValue("");
												    op.getCmpByName('customerName').setValue("");
												   
													if(obj.id!=0 && obj.id!="")	
													op.getCmpByName('customerId').setValue(obj.id);
													if(obj.name!=0 && obj.name!="")	
													op.getCmpByName('customerName').setValue(obj.name);
													}
														selectPWName(selectCusNew);
												},
												resizable : true,
												mode : 'romote',
												//editable : false,
												lazyInit : false,
												typeAhead : true,
												minChars : 1,
												/*store : new Ext.data.JsonStore({}),
												displayField : 'name',
												valueField : 'id',*/
												triggerAction : 'all',
												listeners : {
													scope : this,
													'select' : function(combo, record, index) {
														selectCusCombo(record);
													},
													'blur' : function(f) {},
													'change':function(combox, record, index){
														var obj_n = combox.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt;
														ressetProjuect(obj_n);
													}
				
												}
				
											}]
				}:{
					columnWidth : .2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
										xtype : 'combo',
										anchor : '100%',
										triggerClass : 'x-form-search-trigger',
										fieldLabel : "借款人",
										name : "customerName",
										blankText : "借款人不能为空，请正确填写!",
										allowBlank : true,
										scope : this,
										onTriggerClick : function() {
											var op = this.ownerCt.ownerCt.ownerCt.ownerCt;
											var EnterpriseNameStockUpdateNew = function(obj) {
												op.getCmpByName('customerName').setValue("");
												op.getCmpByName('customerId').setValue("");

												if (obj.enterprisename != 0&& obj.enterprisename != "")
													op.getCmpByName('customerName').setValue(obj.enterprisename);
												if (obj.id != 0 && obj.id != "")
													op.getCmpByName('customerId').setValue(obj.id);
												}

											selectEnterprise(EnterpriseNameStockUpdateNew);

										},
										resizable : true,
										mode : 'romote',
										// editable : false,
										lazyInit : false,
										typeAhead : true,
										minChars : 1,
										triggerAction : 'all'
									}]
				},{
					columnWidth : .2,
					layout : 'form',
					labelWidth : 110,
					labelAlign : 'right',
					border : false,
					items : [{
						xtype : 'datefield',
						fieldLabel : '放款日期：从',
						labelSeparator : '',
						format : 'Y-m-d',
						name : 'startDate',
						anchor : '100%'
					}]
				},{
					columnWidth : .14,
					layout : 'form',
					labelWidth : 20,
					labelAlign : 'right',
					border : false,
					items : [{
						xtype : 'datefield',
						fieldLabel : '到',
						labelSeparator : '',
						format : 'Y-m-d',
						name : 'endDate',
						anchor : '100%'
					}]
				},{
				columnWidth : 0.07,
				layout : 'form',
				border : false,
				labelAlign : 'right',
				style : 'margin-left:20px',
				items : [ {
					xtype : 'button',
					text : '查询',
					anchor : "100%",
					scope : this,
					iconCls : 'btn-search',
					handler : this.search
				} ]
			}, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,
				style : 'margin-left:20px',
				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '重置',
					anchor : "100%",
					scope : this,
					iconCls : 'btn-reset',
					handler : this.reset
				} ]
			}]
			} ]

		})
		this.topbar = new Ext.Toolbar({
			items : [ {
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				scope : this,
				hidden:!isGranted('_add_LoneExternal_'+this.customerType),
				handler : function() {
					new LoneExternalForm({listPanel :this.gridPanel,type:'add',customerType:this.customerType}).show();
				}
			}, new Ext.Toolbar.Separator({
						hidden : isGranted('_add_LoneExternal_'+this.customerType) ? false : true
					}),{
				iconCls : 'btn-edit',
				text : '编辑',
				xtype : 'button',
				scope : this,
				hidden:!isGranted('_edit_LoneExternal_'+this.customerType),
				handler : this.editSelR
			}, new Ext.Toolbar.Separator({
						hidden : isGranted('_edit_LoneExternal_'+this.customerType) ? false : true
					}),  {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				hidden:!isGranted('_del_LoneExternal_'+this.customerType),
				handler : this.removeSelRs
			}, new Ext.Toolbar.Separator({
						hidden : isGranted('_del_LoneExternal_'+this.customerType) ? false : true
					}),{
											iconCls : 'btn-xls',
											text : '导出Excel',
											xtype : 'button',
											scope : this,
											hidden:!isGranted('_out_LoneExternal_'+this.customerType),
											handler : this.outToExcel
										}]
		});
		var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计（元）';
				}
		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			hiddenCm:false,
			tbar:this.topbar,
			plugins : [summary],
			showPaging:true,
			autoScroll:true,
			url : __ctxPath + "/creditFlow/riskControl/creditInvestigation/listBpLoneExternal.do?customerType="+this.customerType,
			fields : [ {
				name : 'externalId',
				type : 'long'
			}, 'businessType','projectStatusValue','customerName',
			'customerType','customerId','cardnumber','loneMoney','onLoneMoney',
			'loneCompany','overdueDays',
			'startDate','intentDate','projectStatus',
			'remarks'
			],
			columns : [ {
				header : 'externalId',
				align:'center',
				dataIndex : 'externalId',
				hidden : true
			},{
				header : '业务品种',
				align:'center',
				dataIndex : 'businessType',
				renderer:function(value){
					if(value=="SmallLoan"){
						return '小额贷款'
					}else if(value=="InternetFinance"){
						return '互联网金融'
					}else if(value=="Guarantee"){
						return '金融担保'
					}else if(value=="Financing"){
						return '资金融资'
					}
				}
			}, {
				header : '借款人',
				summaryRenderer : totalMoney,
				dataIndex : 'customerName',
				width:200
			},{
				header : '证件号码',
				align:'center',
				dataIndex : 'cardnumber',
				width:200
			},{
				header : '借款金额(天)',
				dataIndex : 'loneMoney',
				align:'right',
				summaryType : 'sum',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
			 		metaData.attr = ' ext:qtip="' + Ext.util.Format.number(value,'0,000.00') + '"';
					return Ext.util.Format.number(value,'0,000.00')
				}
			},{
				header : '借款余额(天)',
				dataIndex : 'onLoneMoney',
				align:'right',
				summaryType : 'sum',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
			 		metaData.attr = ' ext:qtip="' + Ext.util.Format.number(value,'0,000.00') + '"';
					return Ext.util.Format.number(value,'0,000.00')
				}
			},{
				header : '借款单位',
				dataIndex : 'loneCompany',
				width:200
			},{
				header : '累计逾期天数（天）',
				dataIndex : 'overdueDays',
				width:100
			},{
				header : '开始日期',
				align:'center',
				dataIndex : 'startDate'
			}, {
				header : '结束日期',
				align:'center',
				dataIndex : 'intentDate'
			},{
				header : '状态',
				align:'center',
				dataIndex : 'projectStatusValue'
			}]
		//end of columns
				});
	},// end of the initComponents()
	//重置查询表单
		// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
/*		var businessType=this.searchPanel.getCmpByName('businessType');
		if(null==businessType || ""==businessType){
			Ext.MessageBox.show({
					title : '操作信息',
					msg : '请先选择业务类别！',
					buttons : Ext.MessageBox.OK,
					icon : 'ext-mb-error'
				});
				return;
		}*/
		$search({
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function(id) {
		var selectRecords = this.gridPanel.getSelectionModel().getSelections();

		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.externalId);
		}
			Ext.Ajax.request({
						url : __ctxPath
								+ '/creditFlow/riskControl/creditInvestigation/multiDelBpLoneExternal.do',
						params : {
							ids : ids
						},
						method : 'post',
						scope:this,
						success : function() {
							Ext.ux.Toast.msg("信息", "成功删除所选记录！");
							this.gridPanel.getStore().reload()
						}
					});
	},
		// 把选中ID的编辑
	editSelR : function(id) {
		var selectRecords = this.gridPanel.getSelectionModel().getSelections();

		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要编辑的记录！");
			return;
		}
			if (selectRecords.length >1) {
			Ext.ux.Toast.msg("信息", "请选择要编辑的记录！");
			return;
		}
		var externalId=selectRecords[0].data.externalId;
		new LoneExternalForm({listPanel :this.gridPanel,type:'edit',customerType:this.customerType,externalId:externalId}).show();

	},
		//导出到Excel
	outToExcel:function(){
		var businessType=this.searchPanel.getCmpByName('businessType').getValue();
		var customerId=this.searchPanel.getCmpByName('customerId').getValue();
		var startDate=this.searchPanel.getCmpByName('startDate').getValue();
		var endDate=this.searchPanel.getCmpByName('endDate').getValue();
		var sdate="";
		var edate="";
		if(null!=startDate && ""!=startDate){
		  var  sydate=startDate.getFullYear();
		  var  smdate=startDate.getMonth()+1;
		  var  sddate=startDate.getDate();
		  sdate=sydate+"-"+smdate+"-"+sddate;
		}
		if(null!=endDate && ""!=endDate){
		    var  eydate=endDate.getFullYear();
		  var  emdate=endDate.getMonth()+1;
		  var  eddate=endDate.getDate();
		  edate=eydate+"-"+emdate+"-"+eddate;
		}
		window.open(__ctxPath + '/creditFlow/riskControl/creditInvestigation/outExcelBpLoneExternal.do?customerType='+this.customerType+'&businessType='+businessType+"&customerId="+customerId+"&startDate="+sdate+"&endDate="+edate,'_blank');
	}
		
});
