/**
 * @author
 * @class OurProcreditMaterialsEnterpriseView
 * @extends Ext.Panel
 * @description [OurProcreditMaterialsEnterprise]管理
 * @company 智维软件
 * @createtime:
 */
OurProcreditMaterialsEnterpriseView = Ext.extend(Ext.Panel, {
	dic_GPanel : null,
	tree_GPanel : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		businessType = this.businessType;//给listener 传值 add by gaoqingrui
		this.initUIComponents();
		OurProcreditMaterialsEnterpriseView.superclass.constructor.call(this, {
					id : 'OurProcreditMaterialsEnterpriseView',
					layout:'border',
					iconCls :'menu-onlinedoc',
					title : '企业担保材料管理',
					/*autoHeight : true,
					autoWidth : true,
					autoScroll : true,*/
					border : false,
					items : [this.dic_GPanel,this.tree_GPanel/*{
								layout : 'fit',
								columnWidth : 0.42,
								items : [this.dic_GPanel]
							}, {
								id : 'gridtreeid',
								layout : 'fit',
								columnWidth : 0.52,
								items : [this.tree_GPanel]
							}*/, {
								id : 'gselectid',
								xtype : 'hidden',
								name : 'gselectid',
								value : '0'
							}, {
								id : 'gselecttext',
								xtype : 'hidden',
								name : 'gselecttext',
								value : '0'
							}, {
								id : 'gselecttype',
								xtype : 'hidden',
								name : 'gselecttype',
								value : '0'
							}]
				});
	},
	initUIComponents : function() {
			        var jsArr = [__ctxPath + '/js/creditFlow/guarantee/materials/OurProcreditMaterialsEnterpriseForm.js'];
					$ImportSimpleJs(jsArr, null);
			        this.tree_GPanel = new HT.EditorGridPanel
			        ({
			        	region:'center',
					    height : 800,
						width : 300,
						autoScroll : true,
						id:'enterperiseMaterials_dic_r',
						frame : false,
						border : false,
						iconCls : 'icon-grid',
						stripeRows : true,
						isautoLoad : false,
					    tbar : [add_button_r,update_button_r,delete_button_r],
					 	columns : [{
								header : businessType=='Guarantee'?'数据担保材料项':'统一担保材料',
								dataIndex : 'materialsName',
								width : 240
							}, {
								dataIndex : 'materialsId',
								hidden : true
							}, {
								header : '父节点名称',
								width : 100,
								dataIndex : 'parentText'
							}, {
								header : "合规说明",
								dataIndex : 'ruleExplain',
								width : 280
							}]
					 });
					 var tree_GPanel=this.tree_GPanel
					this.dic_GPanel =new HT.EditorGridPanel
					({
						id : 'enterperiseMaterials_dic',
						region : 'west',
						border : false,
						height : Ext.getBody().getViewSize().height-115,
						width : 300,
						split : true,
						autoScroll : true,
					   	url : __ctxPath+ '/materials/listByOperationTypeKeyOurProcreditMaterialsEnterprise.do?operationTypeKey=Guarantee_CompanyBusiness',
						fields : [{
							id : 'id',
							name : 'materialsId'
						}, {
							id : 'text',
							name : 'materialsName'
						}, {
							id : 'lable',
							name : 'lable'
						}, {
							id : 'remarks',
							name : 'remarks'
						}, {
							id : 'remarks',
							name : 'remarks'
						},{
							id : 'operationTypeName',
							name : 'operationTypeName'
					   }
					   ,{
							id : 'operationTypeKey',
							name : 'operationTypeKey'
					   }
					   ],
				       columns:[
					    {
							header : "担保材料类型",
							width : 160,
							sortable : true,
							dataIndex : 'materialsName'
						}, {
							header : '业务品种',
							width : 100,
							dataIndex : 'operationTypeName'
						}, {
							header : "编号",
							width : 100,
							dataIndex : 'materialsId'
						}],
						
						frame : false,
						iconCls : 'icon-grid',
						stripeRows : true,
					    tbar : [add_button_l, update_button_l,delete_button_l],
					    scope:this,
					    listeners : {
				        'rowclick' : function(grid, index, e) {
				        	var gtext = grid.getSelectionModel().getSelected().get('materialsName');
					          var gid = grid.getSelectionModel().getSelected().get('materialsId');
					          var rightStore=tree_GPanel.getStore();
					          var rightUrl= businessType=='Guarantee'?__ctxPath+ '/materials/listChildrenOurProcreditMaterialsEnterprise.do?node='+gid:__ctxPath+ '/materials/listChildrenGlobalGuaranteeMaterials.do?node='+gid
					          rightStore.proxy=new Ext.data.HttpProxy({  
						                url: rightUrl
					          })
					           rightStore.reader=new Ext.data.JsonReader({  
				                    root: 'result'  
				                }, [  
				                {name: 'materialsId'},{name: 'materialsName'},{ name: 'parentText'},{name:'ruleExplain'}  
				               ]);
					          rightStore.load();
				        }}
		});
	}
});
