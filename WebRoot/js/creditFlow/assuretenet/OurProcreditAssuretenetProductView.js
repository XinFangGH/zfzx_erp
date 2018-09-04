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
			items : [/*{
				html:this.isHiddenGd?'<font style="color:black;font-weight:bold;">【贷款必备条件】</font>':'<font style="color:black;font-weight:bold;">【归档材料清单】</font>',
				hidden:!this.isFlow
			},*/{
				iconCls : 'btn-add',
				text : '添加',
				xtype : 'button',
				scope : this,
//				hidden : isGranted('_create_dbzr')?false:true,
				hidden:this.hiddenAdd,
				handler : this.createRs
			}, "-",{
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
//				hidden : isGranted('_remove_dbzr')?false:true,
				hidden:this.hiddenDel,
				handler : this.removeSelRs
			}]
		});
		var checkColumn = new Ext.grid.CheckColumn({
			hidden:this.isHiddenGd,
			header : '是否已归档',
			editable:true,
			dataIndex : 'isfile',
			width : 70
		});
		this.gridPanel = new Ext.grid.EditorGridPanel({
				tbar :this.isHiddentbar?null:this.topbar,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				plugins : [checkColumn],
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
							fields : Ext.data.Record.create( [{
								name : 'assuretenetId'
							},{
								name : 'assuretenet'
							},{
								name : 'outletopinion'
							},{
								name : 'isfile'
							},{
								name : 'xxnums'
							},{
								name : 'remark'
							},{
								name : 'platFormoutletopinion'
							}
						]),
						root : 'result'
					})
				}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isAllReadOnly}),new Ext.grid.RowNumberer({width :20}),
					{
						header : 'assuretenetId',
						dataIndex : 'assuretenetId',
						hidden : true
					}, {
						header : this.isHiddenGd?'必备条件':'归档材料名称',
						id:'assuretenet',
						dataIndex : 'assuretenet',
						editor : {
							xtype : 'textfield',
							readOnly : this.isBiBeiReadOnly
						}
					},{
						header :this.headerText,
						sortable : true,
						hidden:this.isHiddenSh,
						editor : new Ext.form.ComboBox({
							mode : 'local',
							editable : false,
							readOnly : this.isReadOnly,
							store : new Ext.data.SimpleStore({
								data : [['符合', '符合'], ['不符合', '不符合'],
										['无法核实', '无法核实']],
								fields : ['text', 'value']
							}),
							displayField : 'text',
							valueField : 'value',
							triggerAction : 'all'
						}),
						dataIndex : 'outletopinion',
						renderer : function(v){
							if(v==''||!v){
								if(objP.isReadOnly){
								    return '未选择意见';
								}
								return '<font color=red>请点击选择</font>' ;
							}else if(v=='符合'){
								return '<font color=green>'+v+'</font>';
							}else if(v=='不符合'){
								return '<font color=red>'+v+'</font>';
							}else if(v=='无法核实'){
								return '<font color=blue>'+v+'</font>';
							}else{
								return v;
							}
						}
					},{
						header : this.headerText2,
						sortable : true,
						hidden:this.isHiddenPlatForm,
						editor : new Ext.form.ComboBox({
							mode : 'local',
							editable : false,
							readOnly : this.isReadOnly,
							store : new Ext.data.SimpleStore({
								data : [['符合', '符合'], ['不符合', '不符合'],
										['无法核实', '无法核实']],
								fields : ['text', 'value']
							}),
							displayField : 'text',
							valueField : 'value',
							triggerAction : 'all'
						}),
						dataIndex : 'platFormoutletopinion',
						renderer : function(v){
							if(v==''||!v){
								if(objP.isReadOnly){
								    return '未选择意见';
								}
								return '<font color=red>请点击选择</font>' ;
							}else if(v=='符合'){
								return '<font color=green>'+v+'</font>';
							}else if(v=='不符合'){
								return '<font color=red>'+v+'</font>';
							}else if(v=='无法核实'){
								return '<font color=blue>'+v+'</font>';
							}else{
								return v;
							}
						}
					},{
						header : '线下份数',	
						width : 60,
						dataIndex : 'xxnums',
						hidden:this.isHiddenGd,
						editor: {
						   xtype:'numberfield'
						}
					},checkColumn,{
						header : '归档备注',	
						align : "center",
						dataIndex : 'remark',
						hidden : this.isHiddenGd,
						editor : new Ext.form.TextField({})
					}
				],
				listeners : {
					'afteredit' : function(e) {
						var  gridObj=this;
						var args;
						var value = e.value;
						var assuretenetId = e.record.get('assuretenetId');
						var outletopinion = e.record.get('outletopinion');
						if(e.originalValue != e.value){
								if (e.field == 'assuretenet') {
									if(this.ownerCt.projectId){
										args = {
											'ourProcreditAssuretenet.assuretenet' : value,
											'ourProcreditAssuretenet.assuretenetId' : ""==assuretenetId?null:assuretenetId,
											'ourProcreditAssuretenet.projectId':this.ownerCt.projectId/*,
											'ourProcreditAssuretenet.productId':this.ownerCt.productId*/	
										};
									}
								}
								if (e.field == 'outletopinion') {
									args = {
										'ourProcreditAssuretenet.outletopinion' : value,
										'ourProcreditAssuretenet.assuretenetId' : ""==assuretenetId?null:assuretenetId,
										'ourProcreditAssuretenet.projectId':this.ownerCt.projectId/*,
										'ourProcreditAssuretenet.productId':this.ownerCt.productId*/	
									};
								}
								if (e.field == 'platFormoutletopinion') {
									args = {
										'ourProcreditAssuretenet.platFormoutletopinion' : value,
										'ourProcreditAssuretenet.assuretenetId' : ""==assuretenetId?null:assuretenetId,
										'ourProcreditAssuretenet.projectId':this.ownerCt.projectId/*,
										'ourProcreditAssuretenet.productId':this.ownerCt.productId*/	
									};
								}
								if (e.field == 'riskmanageropinion') {
									args = {
										'ourProcreditAssuretenet.riskmanageropinion' : value,
										'ourProcreditAssuretenet.assuretenetId' : ""==assuretenetId?null:assuretenetId,
										'ourProcreditAssuretenet.projectId':this.ownerCt.projectId/*,
										'ourProcreditAssuretenet.productId':this.ownerCt.productId*/	
									};
								}
								
								if (e.field == 'remark') {
									args = {
										'ourProcreditAssuretenet.remark' : value,
										'ourProcreditAssuretenet.assuretenetId' : ""==assuretenetId?null:assuretenetId,
										'ourProcreditAssuretenet.projectId':this.ownerCt.projectId/*,
										'ourProcreditAssuretenet.productId':this.ownerCt.productId*/	
									};
								}
								if (e.field == 'xxnums') {
									args = {
										'ourProcreditAssuretenet.xxnums' : value,
										'ourProcreditAssuretenet.assuretenetId' : ""==assuretenetId?null:assuretenetId,
										'ourProcreditAssuretenet.projectId':this.ownerCt.projectId/*,
										'ourProcreditAssuretenet.productId':this.ownerCt.productId*/	
									};
								}
								if (e.field == 'isfile') {
									args = {
										'ourProcreditAssuretenet.isfile' : value,
										'ourProcreditAssuretenet.assuretenetId' : ""==assuretenetId?null:assuretenetId,
										'ourProcreditAssuretenet.projectId':this.ownerCt.projectId/*,
										'ourProcreditAssuretenet.productId':this.ownerCt.productId*/	
									};
								}
						}
						if(this.ownerCt.projectId){
							Ext.Ajax.request({
								url : __ctxPath + '/assuretenet/save2OurProcreditAssuretenet.do',
								method : 'POST',
								success : function(response, request) {
									 e.record.commit();
									  gridObj.store.reload();
								},
								failure : function(response) {
									Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
								},
								params :args
							})
						}
				}
			}
		});
		this.gridPanel.getStore().load();
	},
	// 创建记录
	createRs : function() {
		/*var newRecord = this.gridPanel.getStore().recordType;
		var newData = new newRecord({
			assuretenetId : '',
			assuretenet : '',
			outletopinion:''
		});
		this.gridPanel.stopEditing();
		this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(),newData);
		this.gridPanel.getView().refresh();
		this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore().getCount() - 1));
		this.gridPanel.startEditing(0,1);*/
		new OurProcreditAssuretenetGuaranteeForm({
			gridPanel:this.gridPanel,
			productId:this.productId
		}).show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath + '/assuretenet/multiDelOurProcreditAssuretenet.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var grid=this.gridPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
		} else {
			if(selected.data.assuretenetId){
				$delGridRs({
					url : __ctxPath + '/assuretenet/multiDelOurProcreditAssuretenet.do',
					grid : this.gridPanel,
					idName : 'assuretenetId'
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
					if (vRecords[i].data.assuretenetId != null && vRecords[i].data.assuretenetId != "") {
						st = {
							"assuretenetId" : vRecords[i].data.assuretenetId,
							"assuretenet" : vRecords[i].data.assuretenet
						};
					} else {
						st = {
							"assuretenetId" : null,
							"assuretenet" : vRecords[i].data.assuretenet
						};
					}
					vDatas += Ext.util.JSON.encode(st) + '@';
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	}
});
