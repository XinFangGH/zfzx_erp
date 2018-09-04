/**
 * @author 
 * @createtime 
 * @class SlActualToChargeProductForm
 * @extends Ext.Window
 * @description SlActualToChargeProductForm表单
 * @company 智维软件
 */
SlActualToChargeProductForm = Ext.extend(Ext.Window, {
	gridPanel:null,
	productId:null,
	constructor : function(_cfg) {
		if (typeof(_cfg.gridPanel) != "undefined") {
			this.gridPanel = _cfg.gridPanel;
		}
		if (typeof(_cfg.productId) != "undefined") {
			this.productId = _cfg.productId;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlActualToChargeProductForm.superclass.constructor.call(this, {
			layout : 'fit',
			items : this.materialsPanel,
			modal : true,
			height:220,
			width : 520,
			maximizable : true,
			title : '手续费用清单详细信息'
		});
	},//end of the constructor
	//初始化组件
	initUIComponents : function() {
		// 材料清单树Panel
		
		this.dic_TreeLoader = new Ext.tree.TreeLoader({
				dataUrl :  __ctxPath+"/creditFlow/finance/getActualToChargeTreeSlPlansToCharge.do?" +
						"businessType=SmallLoan&productId="+this.productId+"&isValid=0"
			})
		this.dic_Root = new Ext.tree.AsyncTreeNode({
			id : '0',
			text : "手续费用清单"
		});
		
		var gridPanel=this.gridPanel;
		
		this.materialsPanel= new Ext.Panel({
			id : 'dic_TreePanel',
			frame : false,
			autoWidth:true,
			collapsible : false,
			titleCollapse : false,
			autoScroll:true,
			height : Ext.getBody().getViewSize().height-115,
			items : [{
				id :'ActualToChargeTree_panel',
				xtype : 'treepanel',
				border : false,
				iconCls : 'icon-nav',
				rootVisible : true,
				loader : this.dic_TreeLoader,
				root : this.dic_Root,
				listeners:{
					'dblclick': function(node) {
						if(node.leaf){
							var flag=true;
							var items=gridPanel.getStore().data.items;
							if(items){
								for(var i=0;i<items.length;){
									if(items[i].data.costType==node.text){
										flag=false;
										break;
									}else{
										i++;
									}
								}
							}
							if(flag){
								var newRecord = gridPanel.getStore().recordType;
								var newData = new newRecord({
									actualChargeId :null,
									costType : node.text,
									chargeStandard :node.attributes.model,
									planChargeId:node.id,
									remark : ''
								});
								gridPanel.stopEditing();
								gridPanel.getStore().insert(gridPanel.getStore().getCount(),newData);
								gridPanel.getView().refresh();
								gridPanel.getSelectionModel().selectRow((gridPanel.getStore().getCount() - 1));
								gridPanel.startEditing(0,1);
							}
						}else{
							return;
						}
					}
				}
			}]
		});
	}
});