/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
Shareequity = Ext.extend(Ext.Panel, {

	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.shareequityType)!="undefined"){
			this.shareequityType=_cfg.shareequityType
		}
		if(typeof(_cfg.shareequityId)!="undefined"){
			this.shareequityId=_cfg.shareequityId
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		Shareequity.superclass.constructor.call(this, {
			id : 'Shareequity',
			title : '作为股东',
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
			url :  __ctxPath +"/creditFlow/common/getByTypeShareequity.do?customerType="+this.shareequityType+"&customerId="+this.shareequityId,
			fields : [ {
				name : 'id',
				type : 'int'
			}, 'personid',
			'enterpriseid',
			'enterpriseName',
			'shareholdercode',
			'capital',
			'capitaltype',
			'share',
			'remarks',
			'createTime'
			],
			columns : [ {
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : '企业名称',
				dataIndex : 'enterpriseName'
			}/*, {
				header : '证件号码',
				dataIndex : 'shareholdercode'
			}*/, {
				header : '登记时间',
				dataIndex : 'createTime'
			}, {
				header : '出资额',
				dataIndex : 'capital',
				align:'right',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
				
					return Ext.util.Format.number(value,'0,000.00')+"元"	
				}
			
				
			}, {
				header : '出资方式',
				dataIndex : 'capitaltype'
			}, {
				header : '持股比例',
				dataIndex : 'share',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
				
					return value+"%"	
				}
			}, {
				header : '备注',
				dataIndex : 'remarks'
			}]
		//end of columns
				});
	},// end of the initComponents()
	seeEnterpriseInfo : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
			seeEnterpriseCustomer(selected.get('enterpriseid'))
		}
	}
});
