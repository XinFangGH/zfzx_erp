/**
 * @author
 * @class OurProcreditMaterialsView
 * @extends Ext.Panel
 * @description [OurProcreditMaterials]管理
 * @company 智维软件
 * @createtime:
 */
BranchOfficeView = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		BranchOfficeView.superclass.constructor.call(this, {
			title : '加盟商管理',
			region : 'center',
			id : 'BranchOfficeView',
			layout : 'border',
			iconCls : 'btn-team49',
			items : [this.fPanel_searchOrganization,this.gridPanel]
		});
	},
	initUIComponents : function() {
		var itemwidth=0.2;
		var row=0;
		this.topbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '创建',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_create_dc') ? false : true,
				handler : this.createRs
			}, new Ext.Toolbar.Separator({
				hidden : !isGranted('_create_dc' + this.projectStatus)
						|| !isGranted('_remove_dc' + this.projectStatus)
			}), {
				iconCls : 'btn-del',
				text : '禁用',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_remove_dc') ? false : true,
				handler : this.removeSelRs
			}, new Ext.Toolbar.Separator({
				hidden : !isGranted('_remove_dc' + this.projectStatus)
						|| !isGranted('_edit_dc' + this.projectStatus)
			}), {
				iconCls : 'btn-edit',
				text : '编辑',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_edit_dc') ? false : true,
				handler : this.editRs
			}]
		});
       this.fPanel_searchOrganization = new Ext.FormPanel({
			frame : false,
			region : 'north',
			height : 35,
			monitorValid : true,
			layout : 'column',
			bodyStyle : 'padding:0px 0px 0px 0px',
			border : false,
			defaults : {
				layout : 'form',
				border : false,
				labelWidth : 80,
				bodyStyle : 'padding:5px 0px 0px 5px'
			},
			labelAlign : "right",
			keys : [{
				key : Ext.EventObject.ENTER,
				fn : this.searchByCondition,
				scope : this
			}, {
				key : Ext.EventObject.ESC,
				fn : this.reset,
				scope : this
			}],
			items : [{
				columnWidth :itemwidth,
				bodyStyle : 'padding:5px 0px 0px 0px',
				items : [{
					xtype : 'textfield',
					fieldLabel : '加盟商名称',
					name : 'branchName',
					maxLength : 50, 
					maxLengthText : '长度不能超过50',
					labelWidth : "65",
					anchor : "100%",
					listeners : {
						'specialkey' : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								this.searchByCondition();
							}
						}
					}
				}]
			},{
				columnWidth : 0.07,
				items : [{
					id : 'searchButton',
					xtype : 'button',
					text : '查询',
					iconCls : 'btn-search',
					width : 60,
					formBind : true,
					labelWidth : 20,
					bodyStyle : 'padding:5px 0px 0px 0px',
					scope : this,
					handler : this.searchByCondition
				}]
			},{
							
				columnWidth : 0.07,
				items : [{
					xtype : 'button',
					text : '重置',
					width : 60,
					scope : this,
					iconCls : 'btn-reset',
					handler : this.reset
				}]
			
			}]
		});
		this.gridPanel =new HT.EditorGridPanel({
			region : 'center',
			tbar : this.topbar,
			url : __ctxPath + '/system/getAllCompanyOrganization.do',
			fields : [{
				name : 'orgId',
				type : 'int'
			}, 'orgName', 'orgDesc', 'updatetime', 'chargeUser', 'linkman',
					'linktel', 'address', 'fax', 'key','acronym','branchNO','delFlag','capital'],
			    columns : [{
				header : 'org_id',
				dataIndex : 'orgId',
				hidden : true
			}, {
				header : '加盟商名称',
				dataIndex : 'orgName'
			}, {
				header : '加盟商访问后缀',
				dataIndex : 'key'
			},{
				header : '加盟商编号',
				dataIndex : 'branchNO'
			},{
				header : '加盟商缩写',
				dataIndex : 'acronym'
			},{
				header : '资本金',
				dataIndex : 'capital',
				align : 'right',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
				
					return Ext.util.Format.number(value,'0,000.00')+"元"	
				}
			},{
				header : '联系人',
				dataIndex : 'linkman'
			}, {
				header : '联系电话',
				dataIndex : 'linktel'
			}, {
				header : '传真',
				dataIndex : 'fax'
			},{
				header : '创建时间',
				dataIndex : 'updatetime'
			},{
				header : '状态',
				dataIndex : 'delFlag',
				renderer : function(value) {
					if(value == '1'){//激活用户
						return '<font color="green">激活</font>';
					}else{
						return '<font color="red">禁用</font>';
					}
				},
				editor:new Ext.form.ComboBox({
				allowBlank : false,
				editable : false,
				mode : 'local',
				triggerAction : 'all',
				store : [['0', '禁用'], ['1', '激活']],
				listeners : {
				'change' : function(field, newValue,oldValue) {
							var gridPanel = Ext.getCmp("BranchOfficeView").get(1);
							var record = gridPanel.getStore().getAt(row);
							var id=record.data.orgId;
							var status=newValue;
							Ext.Ajax.request({
								url : __ctxPath+'/system/saveOrganization.do',
								params : {
									'organization.orgId': id,
									'organization.delFlag': status
								},
								method : 'POST',
								success : function(response, options) {
									record.commit();
									Ext.ux.Toast.msg('操作信息', '修改成功！');
								},
								failure : function(response, options) {
									Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
								}
							});
						}
					}
				  })
				}]
		});
		this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
				row = rowindex;
		});
	},
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	search : function() {
		$search({
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
	createRs : function() {
		new BranchOfficeForm({
			operateGrid : this.gridPanel,
			title : '添加加盟商'
		}).show();
	},
	removeRs : function(id) {
		$postDel({
			url : __ctxPath + '/system/multiDelOrganization.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	searchByCondition : function() {
		
		var grid = this.gridPanel;
		var store = grid.getStore();
		var branchName= this.fPanel_searchOrganization.getForm().findField('branchName').getValue();
		store.baseParams.orgName=branchName;
		store.load({
			params : {
				start : 0,
				limit : this.pageSize
			}
		});
		
	},
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath + '/system/multiDelOrganization.do',
			grid : this.gridPanel,
			idName : 'orgId'
		});
	},
	editRs : function() {
		var grid = this.gridPanel;
		var store = grid.getStore();
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s.length <= 0) {
			Ext.Msg.alert('状态', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.Msg.alert('状态', '只能选中一条记录');
			return false;
		} else {
			var rec = s[0];
			var orgId = rec.data.orgId;
			new BranchOfficeForm({
				orgId : orgId,
				operateGrid : grid,
				title : '编辑加盟商'
			}).show();
		}
	}
});
