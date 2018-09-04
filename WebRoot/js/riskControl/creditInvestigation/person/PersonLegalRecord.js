/**
 * @author:
 * @class PersonLegalRecord
 * @extends Ext.Panel
 * @description [PersonLegalRecord]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PersonLegalRecord = Ext.extend(Ext.Panel, {
	
	// 构造函数
	constructor : function(_cfg) {
/*		if(typeof(_cfg.customerType)!="undefined"){
			this.customerType=_cfg.customerType
		}
		if(typeof(_cfg.customerId)!="undefined"){
			this.customerId=_cfg.customerId
		}*/
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PersonLegalRecord.superclass.constructor.call(this, {
			id : 'PersonLegalRecord',
			title : '法人借款登记',
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
						allowBlank : false,
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
											name : 'customerId',
											value : 0
										},{
					columnWidth : .2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
												xtype : 'combo',
												triggerClass : 'x-form-search-trigger',
												fieldLabel : "法人",
												name : "customerName",
												readOnly : this.isPersonNameReadOnly,
												allowBlank : false,
												editable:false,
												blankText : "保证人不能为空，请正确填写!",
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
						iconCls : 'btn-detail',
						text : '查看项目详细',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_loanAccept_flow')? false: true,
						handler : this.seeProjectInfo
					},'-', {
											iconCls : 'btn-xls',
											text : '导出Excel',
											xtype : 'button',
											scope : this,
											handler : this.outToExcel
										}]
		});
		var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			hiddenCm:true,
			tbar:this.topbar,
			plugins : [summary],
			showPaging:true,
			autoScroll:true,
			url : __ctxPath + "/creditFlow/personLegalProjectListCreditProject.do",
			fields : [ {
				name : 'projectId',
				type : 'long'
			}, 'businessType','id','oppositeID','oppositeType',
			'projectName','productId','operationType','flowType','projectNumber',
			'projectMoney','projectStatus',
			'startDate','flag','jointMoney',
			'intentDate','name','cardnumber',
			'ownJointMoney',
			'overduePrincipalRepayment',
			'overduePrincipalRepaymentDays','overdueLoanInterest','overdueLoanInterestDays'
			],
			columns : [ {
				header : 'id',
				dataIndex : 'id',
				align:'center',
				hidden : true
			}, {
				header : 'projectId',
				dataIndex : 'projectId',
				align:'center',
				hidden : true
			},{
				header : '业务品种',
				align:'center',
				dataIndex : 'businessType',
				renderer:function(value,metaData, record,rowIndex, colIndex,store){
					if(value=="SmallLoan"){
						var flag = record.get('flag');
						if(flag==1){
						return '互联网金融';
						}
						else{
						return '小额贷款'
						}
					}else if(value=="Guarantee"){
						return '金融担保'
					}else if(value=="Financing"){
						return '资金融资'
					}
				}
			}, {
				header : '法人',
				align:'center',
				dataIndex : 'name',
				width:150
			},
			 {
				header : '证件号码',
				align:'center',
				summaryRenderer : totalMoney,
				dataIndex : 'cardnumber',
				width:150
			},{
				header : '项目名称',
				dataIndex : 'projectName',
				width:300
			}, {
				header : '项目金额(元)',
				dataIndex : 'jointMoney',
				align:'right',
				summaryType : 'sum',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
			 		metaData.attr = ' ext:qtip="' + Ext.util.Format.number(value,'0,000.00') + '"';
					return Ext.util.Format.number(value,'0,000.00')
				}
				
			}, {
				header : '开始日期',
				align:'center',
				dataIndex : 'startDate'
			}, {
				header : '结束日期',
				align:'center',
				dataIndex : 'intentDate'
			},{
				header : '本金逾期金额(元)',
				summaryType : 'sum',
				dataIndex : 'overduePrincipalRepayment',
				align:'right',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
			 		metaData.attr = ' ext:qtip="' + Ext.util.Format.number(value,'0,000.00') + '"';
					return Ext.util.Format.number(value,'0,000.00')
				}
			},{
				header : '本金逾期天数（天）',
				dataIndex : 'overduePrincipalRepaymentDays',
				width:100
			},{
				header : '利息逾期金额(元)',
				summaryType : 'sum',
				dataIndex : 'overdueLoanInterest',
				align:'right',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
			 		metaData.attr = ' ext:qtip="' + Ext.util.Format.number(value,'0,000.00') + '"';
					return Ext.util.Format.number(value,'0,000.00')
				}
			},{
				header : '利息逾期天数（天）',
				dataIndex : 'overdueLoanInterestDays',
				width:100
			}, {
				header : '状态',
				align:'center',
				dataIndex : 'projectStatus',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
					if(value==0){
						return '<font color="red">办理中</font>'
					}else if(value==1){
						return '<font color="green">还款中</font>'
					}else if(value==2){
						return '<font color="green">已完成</font>'
					}else if(value==3){
						return '<font color="green">已终止</font>'
					}else if(value==4){
						return '<font color="green">已展期</font>'
					}else if(value==8){
						return '<font color="green">已违约</font>'
					}else{
						return ''
					}
				}
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
	seeProjectInfo:function(){
			var selected = this.gridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{
				var projectId = selected.get('projectId');
				var businessType = selected.get('businessType');
                var flag=0;
					var idPrefix = "";
					if (businessType == 'SmallLoan') {
						 flag=selected.get('flag');
						if( 0==flag){
						  idPrefix = "SmallLoanProjectInfoEdit_";
						projectId=selected.get('id')
						}
						else if(1==flag){
						businessType = 'ParentSmallLoan';
						}
						
					} else if (businessType == 'Financing') {
						idPrefix = "FinancingProjectInfoEdit_";
					} else if (businessType == 'Guarantee') {
						idPrefix = "GuaranteeProjectInfoEdit_";
					}
					Ext.Ajax.request({
						url : __ctxPath + '/creditFlow/getProjectViewObjectCreditProject.do',
						params : {
							businessType : businessType,
							projectId : projectId
						},
						method : 'post',
						success : function(resp, op) {
							var record = Ext.util.JSON.decode(resp.responseText);//JSON对象，root为data,通过record对象取数据必须符合"record.data.name"格式
							var gpObj=null;
							switch (businessType) {
								case 'SmallLoan' :
									 gpObj = new SmallLoanProjectInfoPanel({record : record,isHidden:true});
									break;
								case 'ParentSmallLoan' :
									  gpObj = new ApproveProjectInfoPanel({record : record, flag : 'see' });
									break;
								case 'Financing' :
										gpObj = new FinancingProjectInfoPanel({record : record,isHidden:true});
									break;
								case 'Guarantee' :
										gpObj = new GuaranteeProjectInfoPanel({record : record,isHidden:true});
								
									break;
								default :
									break;
							}
							var window=new Ext.Window({
								title:'查看项目信息——'+selected.get('projectName'),
								height:500,
								width:1200,
								modal : true,
								autoScroll:true,
							  	items:gpObj
							})
	                        window.show()
						},
						failure : function() {
							Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
						}
					})
				
			}
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
		window.open(__ctxPath + '/creditFlow/personLegalOutExcelCreditProject.do?businessType='+businessType+"&customerId="+customerId+"&startDate="+sdate+"&endDate="+edate,'_blank');
	}
		
});
