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
							layout : 'border',
							iconCls : 'btn-team49',
							items : [this.gridPanel]
						});
			},
			initUIComponents : function() {
				var itemwidth=0.2;
				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-add',
										text : '创建加盟商',
										xtype : 'button',
										scope : this,
										hidden : isGranted('_create_dc')?false:true,
										handler : this.createRs
									}, {
										iconCls : 'btn-del',
										text : '删除加盟商',
										xtype : 'button',
										scope : this,
										hidden : isGranted('_remove_dc')?false:true,
										handler : this.removeSelRs
									}, {
								iconCls : 'btn-edit',
								text : '编辑加盟商',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_edit_dc')?false:true,
								handler : this.editRs
							}]
						});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					url : __ctxPath+'/system/getAllCompanyOrganization.do',
					fields : [{
								name : 'orgId',
								type : 'int'
							}, 'orgName',
							'orgDesc',
							'updatetime','chargeUser'],
					columns : [{
								header : 'org_id',
								dataIndex : 'orgId',
								hidden : true
							}, {
								header : '加盟商名称',
								dataIndex : 'orgName'
							}, {
								header : '主要负责人',
								dataIndex:'chargeUser'
							},
							{
								header : '描述',
								dataIndex : 'orgDesc'
							},{
							    header : '创建时间',
							    dataIndex :  'updatetime'
							}]
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
				new BranchOfficeForm({operateGrid:this.get(0),title:'添加加盟商'}).show();
			},
			removeRs : function(id) {
				$postDel({
							url : __ctxPath
									+ '/system/multiDelOrganization.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			removeSelRs : function() {
				      $delGridRs({
							url : __ctxPath+ '/system/multiDelOrganization.do',
							grid : this.gridPanel,
							idName : 'orgId'
						});
			},
			editRs : function() {
				   var grid=this.gridPanel;
			     	var store=grid.getStore();
			     	var s = this.gridPanel.getSelectionModel().getSelections();
					if (s <= 0) {
						Ext.Msg.alert('状态','请选中任何一条记录');
						return false;
					}else if(s.length>1){
						Ext.Msg.alert('状态','只能选中一条记录');
						return false;
					}else{
					    var rec=s[0];
					    var orgId =rec.data.orgId;
						new BranchOfficeForm({
							orgId : orgId,operateGrid:this.get(0),title:'编辑加盟商'
						}).show();
					}
			}
		});
