/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PersonBusiness = Ext.extend(Ext.Panel, {
	
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.customerType)!="undefined"){
			this.customerType=_cfg.customerType
		}
		if(typeof(_cfg.customerId)!="undefined"){
			this.customerId=_cfg.customerId
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PersonBusiness.superclass.constructor.call(this, {
			id : 'PersonBusiness',
			title : '业务往来',
			region : 'center',
			layout : 'border',
			iconCls :'menu-company',
			items : [this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		this.topbar = new Ext.Toolbar({
			items : [ {
						iconCls : 'btn-detail',
						text : '查看项目详细',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_loanAccept_flow')? false: true,
						handler : this.seeProjectInfo
					}]
		});
		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			hiddenCm:true,
			tbar:this.topbar,
			showPaging:false,
			autoScroll:true,
			url : __ctxPath + "/creditFlow/getBusinessCreditProject.do?customerType="+this.customerType+"&customerId="+this.customerId,
			fields : [ {
				name : 'projectId',
				type : 'long'
			}, 'businessType',
			'projectName',
			'projectMoney',
			'startDate',
			'endDate',
			'status',
			'principalMoney',
			'interestMoney'
			],
			columns : [ {
				header : 'id',
				dataIndex : 'projectId',
				hidden : true
			}, {
				header : '业务品种',
				dataIndex : 'businessType',
				renderer:function(value){
					if(value=="SmallLoan"){
						return '小额贷款'
					}else if(value=="Guarantee"){
						return '金融担保'
					}else if(value=="Financing"){
						return '资金融资'
					}
				}
			}, {
				header : '项目名称',
				dataIndex : 'projectName',
				width:300
			}, {
				header : '项目金额',
				dataIndex : 'projectMoney',
				align:'right',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
			 		metaData.attr = ' ext:qtip="' + Ext.util.Format.number(value,'0,000.00') + '元"';
					return Ext.util.Format.number(value,'0,000.00')+"元"
				}
				
			}, {
				header : '开始日期',
				align:'center',
				dataIndex : 'startDate'
			}, {
				header : '结束日期',
				align:'center',
				dataIndex : 'endDate'
			},{
				header : '本金逾期金额',
				dataIndex : 'principalMoney',
				align:'right',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
			 		metaData.attr = ' ext:qtip="' + Ext.util.Format.number(value,'0,000.00') + '元"';
					return Ext.util.Format.number(value,'0,000.00')+"元"
				}
			},{
				header : '利息逾期金额',
				dataIndex : 'interestMoney',
				align:'right',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
			 		metaData.attr = ' ext:qtip="' + Ext.util.Format.number(value,'0,000.00') + '元"';
					return Ext.util.Format.number(value,'0,000.00')+"元"
				}
			}, {
				header : '状态',
				dataIndex : 'status',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
					if(value=="已结束"){
						return '<font color="red">已结束</font>'
					}else if(value=="进行中"){
						return '<font color="green">进行中</font>'
					}else{
						return ''
					}
				}
			}]
		//end of columns
				});
	},// end of the initComponents()
	//重置查询表单
	
	seeProjectInfo:function(){
			var selected = this.gridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{

				var projectId = selected.get('projectId');
				var businessType = selected.get('businessType');

					var idPrefix = "";
					if (businessType == 'SmallLoan') {
						idPrefix = "SmallLoanProjectInfoEdit_";
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
		}
});
