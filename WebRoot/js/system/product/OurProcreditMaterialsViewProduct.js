/**
 * @author
 * @class OurProcreditMaterialsView
 * @extends Ext.Panel
 * @description [OurProcreditMaterials]管理
 * @company 智维软件
 * @createtime:
 */
 
 
  var tpl=new Ext.XTemplate(
            '<p><b>贷款材料类型:</b>{materialsType}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
            '<p><b>材料名称:</b>{materialsName}&nbsp;&nbsp;&nbsp;&nbsp;</p>'
          )
 var expander = new Ext.grid.RowExpander({
 	    enableCaching : false ,
        tpl : tpl
 });
var Employee = Ext.data.Record.create
([
             {name: 'materialsType', mapping: 'parentName'},
             {name: 'materialsName', mapping: 'materialsName'},
             {name: 'parentId', mapping: 'parentId'},
             {name: 'remarks', mapping: 'remarks'},
             {name: 'operationTypeKey', mapping: 'operationTypeKey'},
             {name: 'productId', mapping: 'productId'},
             {name: 'projectId', mapping: 'projectId'},
             {name: 'materialsId', mapping: 'materialsId'}
]);
var reader = new Ext.data.JsonReader({totalProperty : 3,root : 'result'},Employee);
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
				var jsArr = [__ctxPath+'/js/system/product/BaseMaterialsTreePanel.js'];
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
							text : '选择基础贷款材料',
							xtype : 'button',
							scope : this,
							hidden:this.hiddenAdd,
							handler : this.createRs
						}, '-',{
							iconCls : 'btn-edit',
							text : '新增产品贷款材料',
							xtype : 'button',
							scope : this,
							hidden:this.hiddenedit,
							handler : this.creditNewRs
						},'-',{
							iconCls : 'btn-del',
							text : '删除贷款资料',
							xtype : 'button',
							scope : this,
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
                    
				    width: '80%',
				    border : false,
				    plugins: expander,
				    animCollapse: false,
				    iconCls:'icon-grid',
                    renderTo: document.body,
                    scope:this,
					sm : new Ext.grid.CheckboxSelectionModel({}),
					autoExpandColumn:'materialsName',
					store:  new Ext.data.GroupingStore({
	                        	proxy : new Ext.data.HttpProxy({url : __ctxPath+ "/materials/listByProductIdOurProcreditMaterialsEnterprise.do?productId="+this.productId}) ,
	                        	reader: reader,
	                        	groupField:'materialsType'
	                        	
				        }),
					cm: new Ext.grid.ColumnModel([
				            //expander,
				            new Ext.grid.RowNumberer( {header : '序号',	width : 35}),
				            {header : "贷款材料类型",width : 110,sortable : true,dataIndex : 'materialsType',hidden : true},
				            {header : 'parentId',dataIndex : 'parentId',hidden : true},
				            {header : 'productId',dataIndex : 'productId',hidden : true},
				            {header : 'projectId',dataIndex : 'projectId',hidden : true},
				            {header : 'materialsId',dataIndex : 'materialsId',hidden : true},
				            {header : 'operationTypeKey',dataIndex : 'operationTypeKey',hidden : true},
				            {header: "贷款材料名称",dataIndex: 'materialsName'}
				            
				        ]),
				        selModel : new Ext.grid.RowSelectionModel(),
				        view: new Ext.grid.GroupingView({
                                 forceFit:true,
                                 groupTextTpl: '{text}'
                        })
				});
				this.gridPanel.getStore().load();

			},
			createRs : function() {
				//alert("productId=="+this.productId);
				new BaseMaterialsTreePanel({productId :this.productId,operateObj:this.gridPanel,businessType:this.businessType,operationType:this.operationType,isDelete:false}).show();
				
			},
			//新增产品特有的
			creditNewRs:function(){
				new addProductOwnMaterialForm({
					operateObj:this.gridPanel,
					productId:this.productId,
					oprerationType:this.operationType
				}).show();
			},
			removeSelRs : function(id) {
				new BaseMaterialsTreePanel({productId : this.productId,operateObj:this.gridPanel,businessType:this.businessType,operationType:this.operationType,isDelete:true}).show();
			},
			removeRs : function() {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var grid=this.gridPanel;
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录');
				} else {
					if(selected.data.materialsId){
						 $delGridRs({
							url : __ctxPath+ '/materials/multiDelOurProcreditMaterialsEnterprise.do',
							grid : grid,
							idName : 'materialsId'
						});
					}else{
						grid.store.remove(selected);
					}
				}
			   
			}
		});
