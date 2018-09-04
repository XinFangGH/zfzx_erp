Ext.ns('DepreTypeView');
/**
 * @author:
 * @class DepreTypeView
 * @extends Ext.Panel
 * @description 折算类型列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
DepreTypeView = Ext.extend(Ext.Panel, {
	// 条件搜索Panel
	searchPanel : null,
	// 数据展示Panel
	gridPanel : null,
	// GridPanel的数据Store
	store : null,
	// 头部工具栏
	topbar : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		DepreTypeView.superclass.constructor.call(this, {
					id : 'DepreTypeView',
					title : '折算类型列表',
					iconCls:'menu-depre-type',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			id : 'DepreTypeSearchForm',
			height : 40,
			region : 'north',
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
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search,
				scope : this
			},
			items : [{
				text : '请输入查询条件:'
			}, {
				text : '类型名称'
			}, {
				xtype : 'textfield',
				name : 'Q_typeName_S_LK'
			},{
				text : '折算类型'  //1=平均年限法2=工作量法 加速折旧法3=双倍余额递减法4=年数总和法
			}, {
				xtype : 'textfield',
				hiddenName : 'Q_calMethod_SN_EQ',
				xtype : 'combo',
				allowBlank : false,
				mode : 'local',
				editable : false,
				triggerAction : 'all',
				store : [['1', '平均年限法'], ['2', '工作量法'],['3','双倍余额递减法'],['4','年数总和法']]
			}, {
				xtype : 'button',
				text : '查询',
				iconCls : 'search',
				handler : this.search,
				scope : this
			}, {
				xtype : 'button',
				text : '清空',
				iconCls : 'reset',
				handler : this.clear,
				scope : this
			}]
		});//end of the searchPanel
		
		this.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/admin/listDepreType.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'depreTypeId',
										type : 'int'
									}

									, 'typeName',  'deprePeriod',
									'typeDesc', 'calMethod']
						}),
				remoteSort : true
			});
	this.store.setDefaultSort('depreTypeId', 'desc');
	this.store.load({
		params : {
			start : 0,
			limit : 25
		}
	});
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'depreTypeId',
					dataIndex : 'depreTypeId',
					hidden : true
				}, {
					header : '分类名称',
					dataIndex : 'typeName'
				}, {
					header : '折旧周期(月)',
					dataIndex : 'deprePeriod'
				}, {
					header : '折旧方法',
					dataIndex : 'calMethod',
					renderer : function(value){
					    if(value == '1')
					        return '平均年限法';
					    if(value == '2')
					    	return '工作量法';
					    if(value == '3')
					        return '双倍余额递减法';
					    if(value == '4')
					    	return '年数总和法';
					}
				}, {
					header : '方法描述',
					dataIndex : 'typeDesc'
				}, {
					header : '管理',
					dataIndex : 'depreTypeId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.depreTypeId;
						var str='';
						if(isGranted('_DepreTypeDel')){
						str = '<button title="删除" value=" " class="btn-del" onclick="DepreTypeView.remove('
								+ editId + ')">&nbsp;</button>';
						}
						if(isGranted('_DepreTypeEdit')){
						str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="DepreTypeView.edit('
								+ editId + ')">&nbsp;</button>';
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : true,
			width : 100
		}
	});//end of the cm
	this.topbar =  new Ext.Toolbar({
				id : 'DepreTypeFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
			if(isGranted('_DepreTypeAdd')){
			   this.topbar.add(new Ext.Button({
				   iconCls : 'btn-add',
				   text : '添加折旧类型',
				   handler : function() {
						new DepreTypeForm().show();
				   }
			   }));
			};
			
			if(isGranted('_DepreTypeDel')){
			  this.topbar.add(new Ext.Button({
			    iconCls : 'btn-del',
				text : '删除折旧类型',
				handler : function() {

					var grid = Ext.getCmp("DepreTypeGrid");

					var selectRecords = grid.getSelectionModel()
							.getSelections();

					if (selectRecords.length == 0) {
						Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
						return;
					}
					var ids = Array();
					for (var i = 0; i < selectRecords.length; i++) {
						ids.push(selectRecords[i].data.depreTypeId);
					}
					DepreTypeView.remove(ids);
				}
			  }));
			};
	this.gridPanel =  new Ext.grid.GridPanel({
			id : 'DepreTypeGrid',
			tbar : this.topbar,
			region : 'center',
			store : this.store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : cm,
			sm : sm,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			bbar : new HT.PagingBar({store : this.store})
		});

		this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
			grid.getSelectionModel().each(function(rec) {
				if(isGranted('_DepreTypeEdit')){
				DepreTypeView.edit(rec.data.depreTypeId);
				}
			});
		});
	},//end of the initUIComponents
	
	/**
	 * 搜索
	 */
	search : function() {
		var searchPanel = Ext.getCmp('DepreTypeSearchForm');
		var gridPanel = Ext.getCmp('DepreTypeGrid');
		if (searchPanel.getForm().isValid()) {
			$search({
				searchPanel :searchPanel,
				gridPanel : gridPanel
			});
		}
	},
	
	/**
	 * 清空
	 */
	clear : function(){
		Ext.getCmp('DepreTypeSearchForm').getForm().reset();
	}
});
/**
 * 删除单个记录
 */
DepreTypeView.remove = function(id) {
	var grid = Ext.getCmp("DepreTypeGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : __ctxPath + '/admin/multiDelDepreType.do',
				params : {
					ids : id
				},
				method : 'post',
				success : function(result,request) {
					var res = Ext.util.JSON.decode(result.responseText);
					if(res.success==false){
					    Ext.ux.Toast.msg("信息提示", res.message);
					}else{
						Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
						grid.getStore().reload({
							params : {
								start : 0,
								limit : 25
							}
						});
					}
				}
			});
		}
	});
};

/**
 * 
 */
DepreTypeView.edit = function(id) {
	new DepreTypeForm({
		depreTypeId : id
	}).show();
};
