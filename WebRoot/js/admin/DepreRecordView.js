Ext.ns('DepreRecordView');
/**
 * @author:
 * @class DepreRecordView
 * @extends Ext.Panel
 * @description 固定资产折旧记录列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
DepreRecordView = Ext.extend(Ext.Panel, {
	// 条件搜索Panel
	searchPanel : null,
	// 数据展示Panel
	gridPanel : null,
	// GridPanel的数据Store
	store : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		DepreRecordView.superclass.constructor.call(this, {
					id : 'DepreRecordView',
					title : '固定资产折旧记录列表',
					iconCls:'menu-depRecord',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel,
		this.searchPanel = new Ext.FormPanel({
			id : 'DepreRecordSearchForm',
			region : 'north',
			height : 40,
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				xtype : 'label',
				margins : {
					top : 0,
					right : 4,
					bottom : 4,
					left : 4
				}
			},
			items : [{
						text : '请输入查询条件:'
					}, {
						text : '资产名称'
					}, {
						xtype : 'textfield',
						name : 'Q_fixedAssets.assetsName_S_LK'
					}, {
						text : '折旧时间:从'
					}, {				
						xtype : 'datefield',
						name : 'Q_calTime_D_GE',
						format:'Y-m-d',
						editable:false
					},{ 
						text:'到:'
					},{
					    xtype:'datefield',
					    name:'Q_calTime_D_LE',
					    format:'Y-m-d',
					    editable:false
					    
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext
									.getCmp('DepreRecordSearchForm');
							var gridPanel = Ext.getCmp('DepreRecordGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					}]
		});//end of the searchPanel
		
		this.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/admin/listDepreRecord.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'recordId',
										type : 'int'
									}

									,{ name:'assets',
									   mapping:'fixedAssets.assetsName'
									}, 'workCapacity',
									'depreAmount', 'calTime',{name:'workGrossUnit',
									mapping:'fixedAssets.workGrossUnit'}
									,{
									name:'depType',
									   mapping:'fixedAssets.depreType.typeName'
									}]
						}),
				remoteSort : true
			});
	this.store.setDefaultSort('recordId', 'desc');
	this.store.load({
		params : {
			start : 0,
			limit : 25
		}
	});
	
	var cm = new Ext.grid.ColumnModel({
		columns : [new Ext.grid.RowNumberer(), {
					header : 'recordId',
					dataIndex : 'recordId',
					hidden : true
				}, {
					header : '资产名称',
					dataIndex : 'assets'
				},{
				   header:'折算类型',
				   dataIndex:'depType'
				}, {
					header : '工作量',
					id:'workCapacity',
					dataIndex : 'workCapacity',
					renderer : function(value, metadata, record, rowIndex,colIndex){
						if(value!=null){
							var unit=record.data.workGrossUnit;	
							return value+' '+unit;
						}else{
							return '';						
						}
					}
				}, {
					header : '折旧值',
					dataIndex : 'depreAmount'
				}, {
					header : '计算时间',
					dataIndex : 'calTime'
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});//end of the cm
	this.gridPanel = new Ext.grid.GridPanel({
				id : 'DepreRecordGrid',
				store : this.store,
				region : 'center',
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				cm : cm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : this.store})
			});

	}//end of the initUIComponents
});
