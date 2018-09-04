/**
 * @author
 * @class OurProcreditAssuretenetView
 * @extends Ext.Panel
 * @description [OurProcreditAssuretenet]管理
 * @company 智维软件
 * @createtime:
 */
OurProcreditAssuretenetProductView = Ext.extend(Ext.Panel, {
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
	headerText:'门店负责人意见',
	headerText2:'平台负责人意见',
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
		OurProcreditAssuretenetProductView.superclass.constructor.call(this, {
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
		if(this.projectId){
			url = __ctxPath+ "/assuretenet/listByProjectIdOurProcreditAssuretenet.do?projectId="+this.projectId
		}else{
			url = __ctxPath+ "/assuretenet/listByProjectIdOurProcreditAssuretenet.do?productId="+this.productId
		}
		this.topbar =new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '选择基础贷款条件',
				xtype : 'button',
				scope : this,
				hidden:this.hiddenAdd,
				handler : this.createRs
			}, "-",{
				iconCls : 'btn-edit',
				text : '新增产品贷款条件',
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
							  {name : 'assuretenetId'},
							  {name : 'assuretenet'},
							  {name : 'businessTypeName'},
							  {name : 'operationTypeName'},
							  {name : 'businessTypeKey'},
							  {name : 'businessTypeGlobalId'},
							  {name : 'operationTypeGlobalId'} ,
							  {name : 'customerType'},
							  {name : 'productId'},
							  {name : 'projectId'}
						]),
						root : 'result'
					})
				}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isAllReadOnly}),
						   new Ext.grid.RowNumberer({width :20}),
					{
						header : 'assuretenetId',
						dataIndex : 'assuretenetId',
						hidden : true
					},{
						header : 'productId',
						dataIndex : 'productId',
						hidden : true
					},{
						header : 'projectId',
						dataIndex : 'projectId',
						hidden : true
					}, {
						header : '必备贷款条件',
						dataIndex : 'assuretenet',
						editor : {
							xtype : 'textfield',
							readOnly : this.isBiBeiReadOnly
						}
					}
				]
		});
		this.gridPanel.getStore().load();
	},
	// 创建记录
	createRs : function() {
		new OurProcreditAssuretenetProductForm({
			operateObj:this.gridPanel,
			businessType:this.businessType,
			operationType:this.operationType,
			isDelete:false,
			productId:this.productId
		}).show();
	},
	//新增产品贷款条件
	addnewRs:function(){
		new AddProductOwnAssuretenetForm({
			operateObj:this.gridPanel,
			businessType:this.businessType,
			operationType:this.operationType,
			isDelete:false,
			productId:this.productId
		}).show();
	},
	// 把选中ID删除
	removeSelRs : function() {
		new OurProcreditAssuretenetProductForm({
			operateObj:this.gridPanel,
			businessType:this.businessType,
			operationType:this.operationType,
			isDelete:true,
			productId:this.productId
		}).show();
	}
});
