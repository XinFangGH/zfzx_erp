/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京金智万维软件有限公司
 * @createtime:
 */
DZYMortgageViewProduct = Ext.extend(Ext.Panel, {
	isProduct:false,
	productId:null,
	addText:'增加抵质押物',
	delText:'删除抵质押物',
	type:null,
	isFlow:true,
	constructor : function(_cfg) {
		if(typeof(_cfg.isProduct)!="undefined"){
	          this.isProduct=_cfg.isProduct;
	    }
	    if(typeof(_cfg.productId)!="undefined"){
	          this.productId=_cfg.productId;
	    }
	    if(typeof(_cfg.addText)!="undefined"){
	          this.addText=_cfg.addText;
	    }
	    if(typeof(_cfg.delText)!="undefined"){
	          this.delText=_cfg.delText;
	    }
	    if(typeof(_cfg.type)!="undefined"){
	          this.type=_cfg.type;
	    }
	    if (typeof (_cfg.isFlow) != "undefined") {
			this.isFlow = _cfg.isFlow;
		}
		Ext.applyIf(this, _cfg);

		this.initUIComponents();
		// 调用父类构造
		DZYMortgageViewProduct.superclass.constructor.call(this, {
			layout : 'anchor',
			autoWidth:true,
			items : [ {xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【'+this.titleText+'】:</font></B>',hidden:this.isFlow},
				this.gridPanel ]
		});
	},
	// 初始化组件
	initUIComponents : function() {
		/*var data = [['住宅','住宅'],['商铺写字楼','商铺写字楼'],['住宅用地','住宅用地'],['商业用地','商业用地'],['商住用地','商住用地'],['工业用地','工业用地'],['公寓','公寓'],['联排别墅','联排别墅'],['独栋别墅','独栋别墅']];
		var data1 = [['车辆','车辆'],['股权','股权'],['机器设备','机器设备'],['存货/商品','存货/商品'],['无形权利','无形权利']];
		var data2 = [['企业','企业'],['个人','个人']];*/
		var data = this.isProduct?[['住宅','住宅'],['车辆','车辆']]: [['住宅','住宅'],['车辆','车辆'],['商铺写字楼','商铺写字楼'],['住宅用地','住宅用地'],['商业用地','商业用地'],
					['商住用地','商住用地'],['工业用地','工业用地'],['公寓','公寓'],['联排别墅','联排别墅'],['独栋别墅','独栋别墅'],
					['股权','股权'],['机器设备','机器设备'],['存货/商品','存货/商品'],['无形权利','无形权利']];
		var data2 = [['企业','企业'],['个人','个人']];
		this.topbar = new Ext.Toolbar( {
			items : [{
				iconCls : 'btn-add',
				text : this.addText,
				xtype : 'button',
				scope : this,
				handler : this.createMortgage
			}, {
				iconCls : 'btn-del',
				text : this.delText,
				xtype : 'button',
				scope : this,
				handler : this.removeSelRs
			}]
		});
		this.gridPanel = new Ext.grid.EditorGridPanel( {
				tbar :this.isAllReadOnly?null:this.topbar,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				enableDragDrop : false,
				viewConfig : {
					forceFit : true
				},
				sm : this.isAllReadOnly?null:new Ext.grid.CheckboxSelectionModel({}),
				autoExpandColumn:'remarks',
				store : new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
						url : __ctxPath +'/system/getByProductIdBpProductParameter.do?productId='+this.productId+"&type="+this.type,
						method : "POST"
					}),
					reader : new Ext.data.JsonReader({
							fields : Ext.data.Record.create( [{
								name : 'id'
							},{
								name : 'guaranteeType'
							},{
								name : 'remarks'
							},{
								name : 'assuretypeid'
							}
						]),
						root : 'result'
					})
				}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isAllReadOnly}),new Ext.grid.RowNumberer({width :10}),
					{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					},/*{
						header : '担保类型',
						hidden:this.isProduct,
						dataIndex : 'assuretypeid',
						listeners : {
							afterrender:function(obj){
								if(obj.value=='604'){
									return obj.setValue('抵押担保');
								}else if(obj.value=='605'){
									return obj.value='质押担保';
								}else{
									return obj.value='保证担保';
								}
							}
						}
					}, */{
						header : '类别名称',
						dataIndex : 'guaranteeType',
						editor : {
							xtype : 'combo',
							mode:'local',
							triggerAction : 'all',
							displayField:'typeValue',
							valueField:'typeId',
							store:new Ext.data.SimpleStore({}),
							readOnly : this.isReadOnly,
							listeners : {
								scope:this,
								afterrender : function(obj){
									if(this.type=='604'){
										obj.setValue("")
										var store=new Ext.data.SimpleStore({
										   data : data,
										   fields:['typeValue','typeId']
									    });
										obj.store.insert(0,store.getAt(0))
										obj.store.insert(1,store.getAt(1))
										obj.store.insert(2,store.getAt(2))
										obj.store.insert(3,store.getAt(3))
										obj.store.insert(4,store.getAt(4))
										obj.store.insert(5,store.getAt(5))
										obj.store.insert(6,store.getAt(6))
										obj.store.insert(7,store.getAt(7))
										obj.store.insert(8,store.getAt(8))
										
										obj.store.insert(9,store.getAt(9))
										obj.store.insert(10,store.getAt(10))
										obj.store.insert(11,store.getAt(11))
										obj.store.insert(12,store.getAt(12))
										obj.store.insert(13,store.getAt(13))
									}/*else if(this.type=='605'){
										obj.setValue("")
										var store=new Ext.data.SimpleStore({
										   data : data1,
										   fields:['typeValue','typeId']
									    });
										obj.store.insert(0,store.getAt(0))
										obj.store.insert(1,store.getAt(1))
										obj.store.insert(2,store.getAt(2))
										obj.store.insert(3,store.getAt(3))
										obj.store.insert(4,store.getAt(4))
									}*/else{
										obj.setValue("")
										var store=new Ext.data.SimpleStore({
										   data : data2,
										   fields:['typeValue','typeId']
									    });
										obj.store.insert(0,store.getAt(0))
										obj.store.insert(1,store.getAt(1))
									}
								}
							}
						}
					}, {
						header : '描述',
						id:'remarks',
						dataIndex : 'remarks',
						editor : {
							xtype : 'textfield',
							readOnly : this.isReadOnly
						}
					}
				]
		});
		this.gridPanel.getStore().load();
	},
	//创建记录
	createMortgage : function() {
		var newRecord = this.gridPanel.getStore().recordType;
		var newData = new newRecord({
			id : '',
			guaranteeType : '',
			remarks:''
		});
		this.gridPanel.stopEditing();
		this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(),newData);
		this.gridPanel.getView().refresh();
		this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore().getCount() - 1));
		this.gridPanel.startEditing(0,1);
	},
	//把选中ID删除
	removeSelRs : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var grid=this.gridPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
			if(selected.data.id){
				$delGridRs({
					url : __ctxPath +'/credit/mortgage/deleteByMortgageIds.do',
					grid : this.gridPanel,
					idName : 'id'
				});
				grid.store.reload();
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
					if (vRecords[i].data.id != null && vRecords[i].data.id != "") {
						st = {
							"id" : vRecords[i].data.id,
							"guaranteeType" : vRecords[i].data.guaranteeType,
							"remarks":vRecords[i].data.remarks,
							"assuretypeid":this.type
						};
					} else {
						st = {
							"id" :null,
							"guaranteeType" : vRecords[i].data.guaranteeType,
							"remarks":vRecords[i].data.remarks,
							"assuretypeid":this.type
						};
					}
					vDatas += Ext.util.JSON.encode(st) + '@';
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	}
});
