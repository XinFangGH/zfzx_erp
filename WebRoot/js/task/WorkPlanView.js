Ext.ns('WorkPlanView');
/**
 * 工作计划列表
 */
var WorkPlanView = function() {
	return new Ext.Panel({
		id : 'WorkPlanView',
		iconCls:'menu-planmanage',
		title : '工作计划列表',
		layout:'border',
		region : 'center',
		autoScroll : true,
		items : [new Ext.FormPanel({
			id : 'WorkPlanSearchForm',
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
						text : '计划名称'
					}, {
						xtype : 'textfield',
						name : 'Q_planName_S_LK'
					},{
						text : '是否为个人计划'
					}, {
						xtype : 'textfield',
						hiddenName : 'Q_isPersonal_SN_EQ',
						xtype : 'combo',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						store : [['1', '是'], ['0', '否']]
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('WorkPlanSearchForm');
							var gridPanel = Ext.getCmp('WorkPlanGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					},{
					 xtype:'button',
					 text:'重置',
					 handler:function(){
					   var searchPanel = Ext.getCmp('WorkPlanSearchForm');
					   searchPanel.getForm().reset();
					 }
					}]
		}), this.setup()]
	});
};
/**
 * 建立视图
 */
WorkPlanView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
WorkPlanView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'planId',
					dataIndex : 'planId',
					hidden : true
				}, {
					header : '标识',
					dataIndex : 'icon',
					renderer:function(value){
					   return '<div class="'+value+'"></div>';
					}
				}, {
					header : '计划名称',
					dataIndex : 'planName'
				}, {
					header : '开始日期',
					dataIndex : 'startTime'
				}, {
					header : '结束日期',
					dataIndex : 'endTime'
				}, {
					header : '计划类型',
					dataIndex : 'typeName'
				}, {
					header : '创建人',
					dataIndex : 'userName'
				}, {
					header : '发布范围',
					dataIndex : 'issueScope'
				}, {
					header : '负责人',
					dataIndex : 'principal'
				},{
				   header:'是否生效',
				   dataIndex:'startTime',
				   renderer:function(value, metadata, record, rowIndex,
								colIndex){
				       var startTime=new Date(getDateFromFormat(value, "yyyy-MM-dd H:mm:ss"));				
				     var endTime=new Date(getDateFromFormat(record.data.endTime, "yyyy-MM-dd H:mm:ss"));
				      var today=new Date();
				      if(startTime>today){
				        return '<a style="color:blue;">未生效</a>';
				      }else if(startTime<=today&&endTime>=today){
				        return '<a style="color:green;">已生效</a>'; 
				      }else if(endTime<today){
				       return '<a style="color:red;">已失效</a>';
				      }
				   }
				},{
					header : '状态',
					dataIndex : 'status',
					renderer:function(value){
						if(value=='1'){
						   return '<a style="color:green;">激活</a>';
						}
						if(value=='0'){
						   return '<a style="color:red;">禁用</a>';
						}
					}
				}, {
					header : '是否为个人计划',
					dataIndex : 'isPersonal',
					renderer:function(value){
					  if(value==1){
					    return '个人计划';
					  }
					  if(value!=1){
					    return '部门计划';
					  }
					}
				}, {
					header : '管理',
					dataIndex : 'planId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.planId;
						var editName=record.data.planName;
						var str='';
						if(isGranted('_WorkPlanDel')){
						str = '<button title="删除" value=" " class="btn-del" onclick="WorkPlanView.remove('
								+ editId + ')"></button>';
						}
						if(isGranted('_WorkPlanEdit')){
						str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="WorkPlanView.edit('
								+ editId + ',\''+editName+'\')"></button>';
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store();
	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
	var grid = new Ext.grid.GridPanel({
				id : 'WorkPlanGrid',
				tbar : this.topbar(),
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				region : 'center',
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store})
			});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
					  if(isGranted('_WorkPlanEdit')){
							WorkPlanView.edit(rec.data.planId,rec.data.planName);
					  }
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
WorkPlanView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/task/listWorkPlan.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'planId',
										type : 'int'
									}

									, 'planName', 'planContent', 'startTime',
									'endTime','typeName',
//									{
//									  name:'typeName',
//									  mapping:'planType.typeName'
//									}, 
										{
									  name:'userName',
									  mapping:'appUser.fullname'
									},
									'issueScope', 'participants', 'principal',
									'note', 'status', 'isPersonal', 'icon']
						}),
				remoteSort : true
			});
	store.setDefaultSort('planId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
WorkPlanView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'WorkPlanFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
			if(isGranted('_WorkPlanAdd')){
			  toolbar.add(new Ext.Button({
			    iconCls : 'btn-add',
							text : '添加工作计划',
							handler : function() {
								  
							      var tabs = Ext.getCmp('centerTabPanel');
							      var newWorkPlanForm=Ext.getCmp('NewWorkPlanForm');
							      if(newWorkPlanForm!=null){
							           tabs.remove('NewWorkPlanForm');
							      }
                                  newWorkPlanForm=new NewWorkPlanForm();
					              tabs.add(newWorkPlanForm);
					              tabs.activate(newWorkPlanForm);	
							
								Ext.getCmp('icon').setValue('ux-flag-blue');
							}
			  }));
			}
			
			if(isGranted('_WorkPlanDel')){
			  toolbar.add(new Ext.Button({
			    iconCls : 'btn-del',
							text : '删除工作计划',
							handler : function() {

								var grid = Ext.getCmp("WorkPlanGrid");

								var selectRecords = grid.getSelectionModel()
										.getSelections();

								if (selectRecords.length == 0) {
									Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
									return;
								}
								var ids = Array();
								for (var i = 0; i < selectRecords.length; i++) {
									ids.push(selectRecords[i].data.planId);
								}

								WorkPlanView.remove(ids);
							}
			  }));
			}
	return toolbar;
};

/**
 * 删除单个记录
 */
WorkPlanView.remove = function(id) {
	var grid = Ext.getCmp("WorkPlanGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/task/multiDelWorkPlan.do',
								params : {
									ids : id
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
									grid.getStore().reload({
												params : {
													start : 0,
													limit : 25
												}
											});
								}
							});
				}
			});
};

/**
 * 
 */
WorkPlanView.edit = function(id,name) {
//	new WorkPlanForm(id,name);
	var tabs = Ext.getCmp('centerTabPanel');
    var newWorkPlanForm=Ext.getCmp('NewWorkPlanForm');
    if(newWorkPlanForm!=null){
       tabs.remove('NewWorkPlanForm');
    }
      newWorkPlanForm=new NewWorkPlanForm(id,name+'-详细信息');
       tabs.add(newWorkPlanForm);
       tabs.activate(newWorkPlanForm);
}
