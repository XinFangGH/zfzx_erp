/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
SlLoanAccountErrorLogView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SlLoanAccountErrorLogView.superclass.constructor.call(this, {
			id : 'SlLoanAccountErrorLogView',
			title : '放款账号错误日志',
			region : 'center',
			layout : 'border',
			iconCls :'menu-company',
			items : [ this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var isShow=false; //不显示分公司查询条件
		if(RoleType=="control"){
		    isShow=true; //显示分公司查询条件
		}
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			layout : 'column',
			border : false,
			region : 'north',
			height : 40,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
			items : [/*{
				columnWidth : .2,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [{
					xtype : 'textfield',
					fieldLabel : '客户名称',
					anchor : '100%',
					name : 'customerName'
				}]
			},*/{
				columnWidth : .2,
				layout : 'form',
				labelWidth : 60,
				labelAlign : 'right',
				border:false,
				items : [{
					xtype : 'textfield',
					fieldLabel : '项目编号',
					anchor : '98%',
					name : 'projectNum'
				}]
			},isShow?{
			    columnWidth : 0.2,
			    layout : 'form',
			    border:false,
			   	labelWidth : 80,
				labelAlign : 'right',
			    items : [
			    {
			      xtype : "combo",
			      anchor : "98%",
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
			   ]}:{
			    columnWidth:0.01,
			    border:false
			    }, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '查询',
					width : 60,
					scope : this,
					iconCls : 'btn-search',
					handler : this.search
				} ]
			}, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '重置',
					width : 60,
					scope : this,
					iconCls : 'btn-reset',
					handler : this.reset
				} ]
			} ]

		})

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			hiddenCm:true,
			//autoLoad : true,
			url : __ctxPath + "/creditFlow/finance/listSlLoanAccountErrorLog.do",
			fields : [ {
				name : 'accountErrorId',
				type : 'long'
			}, 'corpno', 'duebillno', 'custname', 'custtype',
					'loantype', 'custbankname','bankname','accountno','accounttype'],
			columns : [ {
				header : 'id',
				dataIndex : 'accountErrorId',
				hidden : true
			},{
			   	header : '机构号',
				dataIndex : 'corpno'
			}, {
				header : '项目编号',
				dataIndex : 'duebillno'
			}, {
				header : '客户类别',
				dataIndex : 'custtype',
				renderer : function(value){
					if(value=='A01'){
						return '企业'
					}else{
						return '个人'
					}
				}
				
			},/* {
				header : '客户名称',
				dataIndex : 'custname'
			}, {
				header : '业务品种',
				dataIndex : 'loantype',
				renderer : function(value){
					if(value=='smallLoanNormalFlow'){
						return '小额贷款业务'
					}else if(value=='microNormalFlow'){
						return '微型贷款业务'
					}
				}
			},*/ {
				header : '账号性质',
				dataIndex : 'accounttype',
				renderer :function(value){
					if(value==0){
						return '个人'
					}else if(value==1){
						return '公司'
					}
					
				}
			}/*,{
				header : '开户行名称',
				dataIndex : 'bankname'
			},{
				header : '账户名称',
				dataIndex : 'custbankname'
			},{
				header : '账号',
				dataIndex : 'accountno'
			}*/]
		//end of columns
				});

	},// end of the initComponents()
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//按条件搜索
	search : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	}
	
	

});
