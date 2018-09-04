/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
LegalPerson = Ext.extend(Ext.Panel, {

	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.legalPersonId)!="undefined"){
			this.legalPersonId=_cfg.legalPersonId
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		LegalPerson.superclass.constructor.call(this, {
			id : 'LegalPerson',
			title : '作为法人',
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
						text : '查看企业详细',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_loanAccept_flow')? false: true,
						handler : this.seeEnterpriseInfo
					}]
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			hiddenCm:true,
			tbar:this.topbar,
			showPaging:false,
			url :  __ctxPath +"/creditFlow/common/getEntByLegalPersonIdShareequity.do?personid="+this.legalPersonId,
			fields : [ {
				name : 'id',
				type : 'int'
			}, 'organizecode',
			'cciaa',
			'enterprisename',
			'ownership',
			'registermoney'
			],
			columns : [ {
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : '企业名称',
				dataIndex : 'enterprisename'
			}, {
				header : '组织机构代码',
				dataIndex : 'organizecode'
			}, {
				header : '营业执照号码',
				dataIndex : 'cciaa'
			}, {
				header : '所有制性质',
				dataIndex : 'ownership'
			}, {
				header : '注册资金',
				dataIndex : 'registermoney',
				align:'right',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
				
					return Ext.util.Format.number(value,'0,000.00')+"元"	
				}
			}]
		//end of columns
				});
	},// end of the initComponents()
	seeEnterpriseInfo : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
			seeEnterpriseCustomer(selected.get('id'))
		}
	}
});
