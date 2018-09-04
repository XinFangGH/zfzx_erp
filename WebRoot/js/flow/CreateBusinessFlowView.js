/**
 * @author
 * @createtime
 * @class CreateBusinessFlowView
 * @extends Ext.Window
 * @description CreateBusinessFlowView创建业务流程(为分公司分配标准流程)
 * @company 智维软件
 */
CreateBusinessFlowView = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.branchCompanyId)!="undefined"){
           this.branchCompanyId=_cfg.branchCompanyId;
        }
        if(typeof(_cfg.orgName)!="undefined"){
           this.orgName=_cfg.orgName;
        }
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		CreateBusinessFlowView.superclass.constructor.call(this, {
					id : 'CreateBusinessFlowViewWin',
					layout : 'fit',
					items : this.gridPanel,
					modal : true,
					height : 420,
					width : 800,
					maximizable : true,
					title : '请为<font color=\'red\'>【'+this.orgName+'】</font>选择标准流程(可多选)',
					buttonAlign : 'center',
					buttons : [{
								text : '创建业务流程',
								iconCls : 'btn-save',
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
			id : 'CreateBusinessFlowViewGrid',
			//url : __ctxPath + '/flow/listProDefinition.do?branchCompanyId=0',
			url : __ctxPath + '/flow/listByBranchCompanyIdProDefinition.do?branchCompanyId=0',
			fields : [{
					name : 'defId',
					type : 'int'
				}, 'proType', 'name', 'description',
				'createtime', 'deployId',{
					name : 'newVersion',
					type : 'int'
				},'status','drawDefXml'],
			columns : [{
						header : 'defId',
						dataIndex : 'defId',
						hidden : true
					}, {
						header : "业务品种",
						width : 80,
						dataIndex : 'proType',
						renderer : function(value) {
							if (value != null)
								return value.typeName;
							else
								return '<font color=\'red\'>未定义</font>';
						}
					}, {
						header : "流程名称",
						width : 120,
						sortable : true,
						dataIndex : 'name'
					}, {
						header : "描述",
						width : 120,
						sortable : true,
						dataIndex : 'description'
					}, {
						header : "流程版本",
						width : 60,
						sortable : true,
						dataIndex : 'newVersion'
					}, new Ext.ux.grid.RowActions({
								header : '管理',
								width : 100,
								hidden : true,
								actions : [{
											iconCls : 'btn-del',
											qtip : '删除',
											style : 'margin:0 3px 0 3px'
										}, {
											iconCls : 'btn-edit',
											qtip : '编辑',
											style : 'margin:0 3px 0 3px'
										}],
								listeners : {
									scope : this,
									'action' : this.onRowAction
								}
							})]
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
		if(this.orgName==null||this.orgName==''||this.orgName=='undefined'){
			Ext.ux.Toast.msg('信息提示', '没有选择分公司,请重新选择!');
			return;
		}
		if(this.branchCompanyId==null||this.branchCompanyId==''||this.branchCompanyId=='undefined'){
			Ext.ux.Toast.msg('信息提示', '没有选择分公司,请重新选择!');
			return;
		}
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择记录!');
			return;
		}else{
			var defIds = "";
			var rows = this.gridPanel.getSelectionModel().getSelections();
			for(var i=0;i<rows.length;i++){
				defIds = defIds+rows[i].get('defId');
				if(i!=rows.length-1){
					defIds = defIds+',';
				}
			}
		}
		Ext.Ajax.request({
			url : __ctxPath + '/flow/assignFlowsProDefinition.do',
			method : 'POST',
			scope : this,
			success : function(response, op) {
				var res = Ext.util.JSON.decode(response.responseText);
				Ext.Msg.alert('操作提示', res.msg);
				if (res.success) {
					this.grid.store.reload();
				}
				this.close();
			},
			failure : function(response) {
				Ext.ux.Toast.msg('操作提示', '对不起，操作失败!');
			},
			params : {
				branchCompanyId : this.branchCompanyId,
				defIds : defIds
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