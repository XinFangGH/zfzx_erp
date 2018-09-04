/**
 * @author
 * @class OurProcreditMaterialsView
 * @extends Ext.Panel
 * @description [OurProcreditMaterials]管理
 * @company 智维软件
 * @createtime:
 */
OurLawsuitMaterialsView = Ext.extend(Ext.Panel, {
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				OurLawsuitMaterialsView.superclass.constructor.call(this, {
							id : 'OurLawsuitMaterialsView',
							title : '贷款资料管理',
							region : 'center',
							layout : 'border',
							iconCls : 'menu-car_repair',
							items : [this.gridPanel]
						});
			},
			initUIComponents : function() {
				this.topbar = new Ext.Toolbar({
							items : [{
										iconCls : 'btn-add',
										text : '添加业务材料',
										xtype : 'button',
										scope : this,
										hidden : isGranted('_create_dc')?false:true,
										handler : this.createRs
									}, {
										iconCls : 'btn-del',
										text : '删除业务材料',
										xtype : 'button',
										scope : this,
										hidden : isGranted('_remove_dc')?false:true,
										handler : this.removeSelRs
									}, {
								iconCls : 'btn-edit',
								text : '编辑业务材料',
								xtype : 'button',
								scope : this,
								hidden : isGranted('_edit_dc')?false:true,
								handler : this.editRs
							}]
						});

				this.gridPanel = new HT.GridPanel({
					region : 'center',
					tbar : this.topbar,
					url : __ctxPath + "/materials/getbyoperationTypeKeyOurProcreditMaterials.do?operationTypeKey=LawsuitGuarantee",
					fields : [{
								name : 'materialsId',
								type : 'int'
							}, 'materialsName',
							'operationTypeName'],
					columns : [{
								header : 'materialsId',
								dataIndex : 'materialsId',
								hidden : true
							}, {
								header : '业务资料名称',
								dataIndex : 'materialsName'
							}, {
								header : '业务类别',
								dataIndex:'',
							    renderer :function(a){
							        return "金融担保"
							    }
							},
							{
								header : '业务种类',
								dataIndex : 'operationTypeName'
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
				new OurLawsuitMaterialsForm({operateGrid:this.get(0)}).show();
			},
			removeRs : function(id) {
				$postDel({
							url : __ctxPath
									+ '/materials/multiDelOurProcreditMaterials.do',
							ids : id,
							grid : this.gridPanel
						});
			},
			removeSelRs : function() {
				      $delGridRs({
							url : __ctxPath+ '/materials/multiDelOurProcreditMaterials.do',
							grid : this.gridPanel,
							idName : 'materialsId'
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
					    var materialsId =rec.data.materialsId;
						new OurLawsuitMaterialsForm({
							materialsId : materialsId,operateGrid:this.get(0)
						}).show();
					}
			}
		});
