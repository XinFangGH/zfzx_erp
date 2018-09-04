/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京金智万维软件有限公司
 * @createtime:
 */
PromotePackage = Ext.extend(Ext.Panel, {
	// 构造函数
	
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		// 调用父类构造
		PromotePackage.superclass.constructor.call(this, {
					id : 'PromotePackage',
					title : '推介资料包',
					region : 'center',
					layout : 'border',
					iconCls : 'menu-company',
					items : [this.gridPanel]
				});
	},
	initUIComponents : function() {
		
		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '推介资料包管理',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_create_cm') ? false : true,
								handler : this.editRs
							}]
				});
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			url : __ctxPath
					+ '/project/findListSlSmallloanProject.do?projectStatus=1&isGrantedShowAllProjects='
					+ this.isGrantedShowAllProjects,
			fields : [{
						name : 'projectId',
						type : 'long'
					}, 'projectName','oppositeType','oppositeID', 'projectNumber', 'projectMoney',
					'loanStartDate', 'customerName'],
			columns : [{
						header : 'fundIntentId',
						dataIndex : 'fundIntentId',
						hidden : true
					}, {
						header : 'oppositeType',
						dataIndex : 'oppositeType',
						hidden : true
					}, {
						header : '项目编号',
						dataIndex : 'projectNumber'
					}, {
						header : '客户名称',
						dataIndex : 'oppositeID',
						renderer : function(value,metaData, record,rowIndex, colIndex,store){
							var oppositeType = record.data.oppositeType;
							var oppositeID = record.data.oppositeID;
							Ext.Ajax.request({
								url : __ctxPath + '/customer/getCustNameCustomer.do',
								params : {'customerId':oppositeID,'oppositeType':oppositeType},
								async:false,
								success : function(response){
									value = response.responseText;
								}
							});
							return value;
						}
					}, {
						header : '拟贷款金额',
						dataIndex : 'projectMoney',
						renderer : function(value){
							var v = Ext.util.Format.number(value,'0,000.00');
							return v+('元');
						}
					}, {
						header : '推介资料包个数',
						dataIndex : 'projectId',
						renderer : function(value) {
							var result = 0;
							Ext.Ajax.request({
								url : __ctxPath
										+ '/creditFlow/common/getFileCountFileForm.do?mark=promote_package.'
										+ value,
								async : false,
								success : function(response) {
									result = response.responseText;
								}
							});
							return "<center><div style='color:red;'>" + result
									+ "</div></center>";
						}
					}]
				// end of columns
		});
		

		this.gridPanel.addListener('rowdblclick', this.rowClick);
		

	},
	editRs : function() {
		var record = this.gridPanel.getSelectionModel().getSelected();
		if (record == null || record == 'undefined') {
			Ext.ux.Toast.msg('操作信息', '请选中一条记录');
			return;
		}
		this.packageWin = new Ext.Window({
					title : '上传推介资料包',
					height : 500,
					width : 700,
					items : [new uploads({
								tableName : 'promote_package',
								typeisfile : 'promotePackage',
								titleName : '推介资料包',
								businessType : 'SmallLoan',
								setname : '推介资料包',
								projectId : record.data.projectId,
								uploadsSize : 100
							})]
				}).show();
		this.packageWin.addListener('close',this.gridReload);

	},
	gridReload : function(){
		Ext.getCmp('PromotePackage').gridPanel.getStore().reload();
	}

});
