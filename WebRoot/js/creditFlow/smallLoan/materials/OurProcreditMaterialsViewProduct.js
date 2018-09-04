/**
 * @author
 * @class OurProcreditMaterialsView
 * @extends Ext.Panel
 * @description [OurProcreditMaterials]管理
 * @company 智维软件
 * @createtime:
 */
OurProcreditMaterialsViewProduct = Ext.extend(Ext.Panel, {
			productId:null,
			isAllReadOnly:false,
			constructor : function(_cfg) {
				if(typeof(_cfg.productId)!="undefined"){
					this.productId=_cfg.productId;
				}
				if(typeof(_cfg.isAllReadOnly)!="undefined"){
					this.isAllReadOnly=_cfg.isAllReadOnly;
				}
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				OurProcreditMaterialsViewProduct.superclass.constructor.call(this, {
					region : 'center',
					layout : 'fit',
					items : [this.gridPanel]
				});
			},
			initUIComponents : function() {
				var jsArr = [__ctxPath+'/js/creditFlow/smallLoan/materials/addProductOwnMaterialForm.js'];
				$ImportSimpleJs(jsArr, null);
				var url="";
				if(this.projectId){
					url = __ctxPath+ "/materials/listByProductIdOurProcreditMaterialsEnterprise.do?projectId="+this.projectId
				}else{
					url = __ctxPath+ "/materials/listByProductIdOurProcreditMaterialsEnterprise.do?productId="+this.productId
				}
				this.topbar = new Ext.Toolbar({
					items : [{
							iconCls : 'btn-add',
							text : '选择产品贷款材料',
							xtype : 'button',
							scope : this,
//							hidden : isGranted('_create_dc')?false:true,
							hidden:this.hiddenAdd,
							handler : this.createRs
						}, '-',{
							iconCls : 'btn-edit',
							text : '新增产品贷款材料',
							xtype : 'button',
							scope : this,
//							hidden : isGranted('_remove_dc')?false:true,
							hidden:this.hiddenedit,
							handler : this.creditNewRs
						},'-',{
							iconCls : 'btn-del',
							text : '删除贷款资料',
							xtype : 'button',
							scope : this,
//							hidden : isGranted('_remove_dc')?false:true,
							hidden:this.hiddenDel,
							handler : this.removeSelRs
						}]
				});

				this.gridPanel = new Ext.grid.EditorGridPanel({
					tbar :this.isAllReadOnly?null:this.topbar,
					autoHeight : true,
					clicksToEdit :1,
					stripeRows : true,
					enableDragDrop : false,
					viewConfig : {
						forceFit : true
					},
					sm : new Ext.grid.CheckboxSelectionModel({}),
					autoExpandColumn:'materialsName',
					store : new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
							url :url,
							method : "POST"
						}),
						reader : new Ext.data.JsonReader({
								fields : Ext.data.Record.create( [{
									name : 'materialsId'
								},{
									name : 'materialsName'
								},{
									name : 'productId'
								},{
									name : 'projectId'
								}
							]),
							root : 'result'
						})
					}),
					columns : [new Ext.grid.CheckboxSelectionModel({}),
						{
							header : 'materialsId',
							dataIndex : 'materialsId',
							hidden : true
						},{
							header : 'productId',
							dataIndex : 'productId',
							hidden : true
						},{
							header : 'projectId',
							dataIndex : 'projectId',
							hidden : true
						},{
							header : 'parentId',
							dataIndex : 'parentId',
							hidden : true
						}, {
							header : '贷款资料名称',
							id:'materialsName',
							dataIndex : 'materialsName',
							editor : {
								xtype : 'textfield',
								readOnly : this.isReadOnly
							}
						}
					]
				});
				this.gridPanel.getStore().load();
//				this.gridPanel.addListener('cellclick', this.cellClick);
			},
			cellClick : function(grid,rowIndex,columnIndex,e){
				var materialsId = grid.getStore().getAt(rowIndex).get('materialsId');
				if(materialsId){
					
				}
			},
			createRs : function() {
				new OurProcreditMaterialsForm({
					gridPanel:this.gridPanel,
					productId:this.productId
				}).show();
				/*var newRecord = this.gridPanel.getStore().recordType;
				var newData = new newRecord({
					materialsId : '',
					materialsName : ''
				});
				this.gridPanel.stopEditing();
				this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(),newData);
				this.gridPanel.getView().refresh();
				this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore().getCount() - 1));
				this.gridPanel.startEditing(0,1);*/
			},
			//新增产品特有的
			creditNewRs:function(){
				new addProductOwnMaterialForm({
					gridPanel:this.gridPanel,
					productId:this.productId
				}).show();
			},
			removeRs : function(id) {
				$postDel({
					url : __ctxPath+ '/materials/multiDelOurProcreditMaterialsEnterprise.do',
					ids : id,
					grid : this.gridPanel
				});
			},
			removeSelRs : function() {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var grid=this.gridPanel;
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录');
				} else {
					if(selected.data.materialsId){
						 $delGridRs({
							url : __ctxPath+ '/materials/multiDelOurProcreditMaterialsEnterprise.do',
							grid : this.gridPanel,
							idName : 'materialsId'
						});
					}else{
						grid.store.remove(selected);
					}
				}
			   
			},
			getGridDate : function() {
				var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
				var vCount = vRecords.length; // 得到记录长度
				var vDatas = '';
				if (vCount > 0) {
					// begin 将记录对象转换为字符串（json格式的字符串）
					for (var i = 0; i < vCount; i++) {
							if (vRecords[i].data.materialsId != null && vRecords[i].data.materialsId != "") {
								st = {
									"materialsId" : vRecords[i].data.materialsId,
									"materialsName" : vRecords[i].data.materialsName,
									"parentId":vRecords[i].data.parentId==""?null:parseInt(vRecords[i].data.parentId),
									"projectId":vRecords[i].data.projectId==""?null:parseInt(vRecords[i].data.projectId)
								};
							} else {
								st = {
									"materialsId" : null,
									"materialsName" : vRecords[i].data.materialsName,
									"parentId":vRecords[i].data.parentId==""?null:parseInt(vRecords[i].data.parentId),
									"projectId":vRecords[i].data.projectId==""?null:parseInt(vRecords[i].data.projectId)
								};
							}
							vDatas += Ext.util.JSON.encode(st) + '@';
					}
					vDatas = vDatas.substr(0, vDatas.length - 1);
				}
				return vDatas;
			}
		});
