/**
 * @author
 * @class P2pLoanBasisMaterialProductView
 * @extends Ext.Panel
 * @description [P2pLoanBasisMaterialProductView]管理
 * @company 互融软件
 * @createtime:
 */
P2pLoanConditionOrMaterialProductView = Ext.extend(Ext.Panel, {
	// 构造函数
	productId:null,
	projectId:null,
	isAllReadOnly:false,
	isReadOnly:false,
	isBiBeiReadOnly:true,
	isFlow:true,
	isHiddenSh:true,
	isHiddenGd:true,
	isHiddenPlatForm:true,
	isHiddentbar:false,
	constructor : function(_cfg) {
		if(typeof(_cfg.isHiddentbar)!="undefined"){
			this.isHiddentbar=_cfg.isHiddentbar;
		}
		if(typeof(_cfg.productId)!="undefined"){
			this.productId=_cfg.productId;
		}
		if(typeof(_cfg.headerText)!="undefined"){
			this.headerText=_cfg.headerText;
		}
		if(typeof(_cfg.headerText2)!="undefined"){
			this.headerText2=_cfg.headerText2;
		}
		if(typeof(_cfg.projectId)!="undefined"){
			this.projectId=_cfg.projectId;
		}
		if(typeof(_cfg.isAllReadOnly)!="undefined"){
			this.isAllReadOnly=_cfg.isAllReadOnly;
		}
		if(typeof(_cfg.isBiBeiReadOnly)!="undefined"){
			this.isBiBeiReadOnly=_cfg.isBiBeiReadOnly;
		}
		if(typeof(_cfg.isReadOnly)!="undefined"){
			this.isReadOnly=_cfg.isReadOnly;
		}
		if(typeof(_cfg.isHiddenPlatForm)!="undefined"){
			this.isHiddenPlatForm=_cfg.isHiddenPlatForm;
		}
		if(typeof(_cfg.isFlow)!="undefined"){
			this.isFlow=_cfg.isFlow;
		}
		if(typeof(_cfg.isHiddenSh)!="undefined"){
			this.isHiddenSh=_cfg.isHiddenSh;
		}
		if(typeof(_cfg.isHiddenGd)!="undefined"){
			this.isHiddenGd=_cfg.isHiddenGd;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		P2pLoanConditionOrMaterialProductView.superclass.constructor.call(this, {
			region : 'center',
			layout : 'anchor',
			items : [{
				xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【'+this.titleText+'】:</font></B>',
				hidden:this.isFlow
			},this.gridPanel]
		});
	},// end of constructor
	initUIComponents : function() {
		var objP=this;
		var url="";
			url = __ctxPath+ "/p2p/listByProductIdP2pLoanConditionOrMaterial.do?productId="+this.productId+"&conditionType="+this.conditionType,
		this.topbar =new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text :   '选择贷款材料',
				xtype : 'button',
				scope : this,
				hidden:this.conditionType==2?(this.hiddenAdd==true?true:false):true,
				handler : this.createRs
			}, "-",{
				iconCls : 'btn-edit',
				text : '新增',
				xtype : 'button',
				scope : this,
				hidden:this.hiddenedit,
				handler : this.addnewRs
			},"-",{
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				hidden:this.hiddenDel,
				handler : this.removeSelRs
			},"-",{
				iconCls : 'btn-detail',
				text : '查看',
				xtype : 'button',
				hidden : this.conditionType==2?false:true,
				scope : this,
				handler : this.seeSelRs
					}]
		});
		
		this.gridPanel = new Ext.grid.EditorGridPanel({
				tbar :this.isHiddentbar?null:this.topbar,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				enableDragDrop : false,
				viewConfig : {
					forceFit : true
				},
				sm : this.isAllReadOnly?null:new Ext.grid.CheckboxSelectionModel({}),
				autoExpandColumn:'materialsName',
				store : new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
						url : url,
						method : "POST"
					}),
					reader : new Ext.data.JsonReader({
							fields : Ext.data.Record.create([
							  {name : 'conditionId'},
							  {name : 'productId'},
							  {name : 'conditionContent'},
							  {name : 'conditionState'},
							  {name : 'conditionType'},
							  {name : 'remark'}
						]),
						root : 'result'
					})
				}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isAllReadOnly}),
						   new Ext.grid.RowNumberer({width :20}),
					{
						header : 'conditionId',
						dataIndex : 'conditionId',
						hidden : true
					},{
						header : 'productId',
						dataIndex : 'productId',
						hidden : true
					},{
						header : 'conditionType',
						dataIndex : 'conditionType',
						hidden : true
					}, {
						header : this.conditionType==2?'贷款材料名称':'条件名称',
						dataIndex : 'conditionContent',
						editor : {
							xtype : 'textfield',
							readOnly : this.isBiBeiReadOnly
						}
					},
					{
						header : this.conditionType==2?'材料类型':'条件类型',
						dataIndex : 'conditionState',
						renderer : function(val){
								if(val==1){
									return "必备";
								}else if(val==2){
									return "可选";
								}
							}
					},
					{
						header : '备注',
						dataIndex : 'remark'
					}
				]
		});
		this.gridPanel.getStore().load();
	},
	// 创建记录
	createRs : function() {
		new P2pLoanConditionOrMaterialProductForm({
			operateObj:this.gridPanel,
			productId:this.productId,
			operationType:this.operationType
		}).show();
	},
	//新增产品贷款条件
	addnewRs:function(){
		new P2pAddBasisMaterialProduct({
			operateObj:this.gridPanel,
			conditionType:this.conditionType,
		    isAllReadOnly:false,
			productId:this.productId
		}).show();
	},
			//查看产品记录
   seeSelRs:function(record) {
				var selections = this.gridPanel.getSelectionModel().getSelections();
				var len = selections.length;
				if (len > 1) {
					Ext.ux.Toast.msg('状态', '只能选择一条记录');
					return;
				} else if (0 == len) {
					Ext.ux.Toast.msg('状态', '请选择一条记录');
					return;
				}
				var conditionId = selections[0].data.conditionId;
				new P2pAddBasisMaterialProduct({
					conditionId : conditionId,
					conditionType:this.conditionType,
					isHideBtns:true,
					isAllReadOnly:true
				}).show();
			},
	//把选中ID删除
	removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/p2p/multiDelP2pLoanConditionOrMaterial.do',
					grid:this.gridPanel,
					idName:'conditionId'
				});
			}
});
