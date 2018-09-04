/**
 * @author
 * @createtime
 * @class UpdateBranchCompanyFlowView
 * @extends Ext.Window
 * @description UpdateBranchCompanyFlowView标准版本的流程发布新版本后，更新到对应的分公司。
 * @company 智维软件
 */
UpdateBranchCompanyFlowView = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.processName)!="undefined"){
           this.processName=_cfg.processName;
        }
        if(typeof(_cfg.flowName)!="undefined"){
           this.flowName=_cfg.flowName;
        }
        if(typeof(_cfg.defId)!="undefined"){
           this.defId=_cfg.defId;
        }
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		UpdateBranchCompanyFlowView.superclass.constructor.call(this, {
					id : 'UpdateBranchCompanyFlowViewWin',
					layout : 'fit',
					items : this.gridPanel,
					modal : true,
					height : 420,
					width : 800,
					maximizable : true,
					title : '请选择分公司(总公司)进行流程<font color=\'red\'>【'+this.flowName+'】</font>更新(可多选)',
					buttonAlign : 'center',
					buttons : [{
								text : '更新流程',
								iconCls : 'btn-refresh',
								scope : this,
								handler : this.save
							}, /*{
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}, */{
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			//tbar : this.topbar,
			// 使用RowActions
			rowActions : false,
			id : 'UpdateBranchCompanyFlowViewGrid',
			//url : __ctxPath + '/flow/listProDefinition.do?branchCompanyId=0',
			url : __ctxPath + '/system/listByProcessNameOrganization.do?processName='+this.processName,
			fields : [{
				name : 'orgId',
				type : 'int'
				}, 'orgName', 'orgDesc', 'updatetime', 'chargeUser', 'linkman',
				'linktel', 'address', 'fax', 'key','acronym','branchNO','delFlag','capital'],
			columns : [{
						header : 'orgId',
						dataIndex : 'orgId',
						hidden : true
					}, {
						header : "分公司名称",
						width : 270,
						dataIndex : 'orgName'
					}, {
						header : "分公司编号",
						width : 100,
						sortable : true,
						dataIndex : 'branchNO'
					}, {
						header : "分公司缩写",
						width : 100,
						sortable : true,
						dataIndex : 'acronym'
					}]
				// end of columns
		});
		this.gridPanel.addListener('rowdblclick', this.rowClick);
	},// end of the initcomponents

	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {},
	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	/*reset : function() {
		this.formPanel.getForm().reset();
	},*/
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 提交记录
	 */
	save : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择记录!');
			return;
		}else{
			var orgIds = "";
			var rows = this.gridPanel.getSelectionModel().getSelections();
			for(var i=0;i<rows.length;i++){
				orgIds = orgIds+rows[i].get('orgId');
				if(i!=rows.length-1){
					orgIds = orgIds+',';
				}
			}
		}
		Ext.Ajax.request({
			url : __ctxPath + '/flow/updateBranchCompanyFlowVersionProDefinition.do',
			method : 'POST',
			scope : this,
			success : function(response, op) {
				var res = Ext.util.JSON.decode(response.responseText);
				Ext.Msg.alert('操作提示', res.msg);
				if (res.success) {
					alert("res.successres.success="+res.success);
					//this.gridPanel.getStore().reload();
					this.close();
				}
			},
			failure : function(response) {
				Ext.ux.Toast.msg('操作提示', '对不起，操作失败!');
			},
			params : {
				orgIds : orgIds,
				defId : this.defId
			}
		})
	},// end of save
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.save.call(this, record);
				break;
			case 'btn-edit' :
				this.save.call(this, record);
				break;
			default :
				break;
		}
	}
});