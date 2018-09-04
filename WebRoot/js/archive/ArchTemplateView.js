/**
 * @author:csx
 * @class ArchTemplateView
 * @extends Ext.Panel
 * @description 公文模板管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
ArchTemplateView=Ext.extend(Ext.Panel, {
	//公文模板分类ID
	typeId:null,
	//公文分类名称
	typeName:null,
	//条件搜索Panel
	searchPanel:null,
	
	//数据展示Panel
	gridPanel:null,
	
	//GridPanel的数据Store
	store:null,
	
	//头部工具栏
	topbar:null,
	
	//构造函数
	constructor:function(config){
		Ext.applyIf(this, config);
		this.initUIComponents();
		ArchTemplateView.superclass.constructor.call(this,{
			title:'公文模板管理',
			region:'center',
			layout:'border',
			items:[this.searchPanel,this.gridPanel]
		});
	},
	
	//初始化UI
	initUIComponents : function() {
		//初始化搜索条件Panel
		this.searchPanel=new Ext.FormPanel({
		    layout : 'column',
		    region:'north',
		    height:40,
			bodyStyle: 'padding:6px 10px 6px 10px',
			border:false,
			defaults:{
				border:false
			},
		    items : [
		    		{
		    			columnWidth:.6,
		    			layout:'form',
		    			items:{
			    			fieldLabel:'模板名称',
			    			name:'Q_tempName_S_LK',
			    			anchor:'96%,96%',
			    			xtype:'textfield'
		    			}
		    		},{
		    			columnWidth:.2,
		    			layout:'form',
		    			items:{
							xtype : 'button',
							text : '查询',
							iconCls : 'search',
							handler : this.search.createCallback(this)
		    			}
					}
				]
		});//end of the searchPanel
		
		//加载数据至store
		this.store = new Ext.data.JsonStore({
							url : __ctxPath+"/archive/listArchTemplate.do",
							root : 'result',
							totalProperty : 'totalCounts',
							remoteSort : true,
							fields : [{name : 'templateId',type:'int'}, 'archivesType', 'tempName', 'tempPath','fileId']
		});
		this.store.setDefaultSort('templateId', 'desc');
		//加载数据
		this.store.load({params : {
					start : 0,
					limit : 25
		}});
		
		
		var actions=[];
		
		if(this.allowEdit){//允许编辑
			if(isGranted('_ArchviesTempDel')){
				actions.push({
					 iconCls:'btn-del',
					 qtip:'删除',
					 style:'margin:0 3px 0 3px'
					}
				 );
			}
			if(isGranted('_ArchivesTempEdit')){
				actions.push({
					 iconCls:'btn-edit',
					 qtip:'编辑',
					 style:'margin:0 3px 0 3px'
				});
			}
		}
		if(isGranted('_ArchiveTypeTempQuery')){
			actions.push({
					iconCls:'btn-readdocument',
					qtip:'查看',
					style:'margin:0 3px 0 3px'
				});
		}
		this.rowActions = new Ext.ux.grid.RowActions({
			header:'管理',
			width:80,
			actions:actions
		});
		
		var sm=null;
		if(this.singleSelect){
			sm=new Ext.grid.CheckboxSelectionModel({singleSelect: true});	
		}else{
		 	sm= new Ext.grid.CheckboxSelectionModel();
		}
		
		//初始化ColumnModel
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
						header : 'templateId',
						dataIndex : 'templateId',
						hidden : true
					}, {
						header : '所属类型',
						dataIndex : 'archivesType'
//						renderer:function(val){
//							alert(val)
//							if(val!=null){
//								return val.typeName;
//							}
//						}
					}, {
						header : '模板名称',
						dataIndex : 'tempName'
					}, {
						header : '文件路径',
						hidden:true,
						dataIndex : 'tempPath'
					}, this.rowActions],
				defaults : {
					sortable : true,
					menuDisabled : false,
					width : 100
				}
			});
		//初始化工具栏
		this.topbar=new Ext.Toolbar({
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
		if(isGranted('_ArchivesTempAdd')){
			this.topbar.add({
							iconCls : 'btn-add',
							text : '添加公文模板',
							xtype : 'button',
							scope : this,
							handler:this.createRecord
						});
		};
		if(isGranted('_ArchviesTempDel')){
			if(isGranted('_ArchivesTempAdd')){
				this.topbar.add('-');
			}
			this.topbar.add({
							iconCls : 'btn-del',
							text : '删除公文模板',
							xtype : 'button',
							handler :this.delRecords,
							scope: this
						});
		}
		this.gridPanel=new Ext.grid.GridPanel({
			    id : 'ArchTemplateGrid',
				region:'center',
				stripeRows:true,
				tbar : this.topbar,
				store : this.store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				//autoHeight : true,
				cm : cm,
				sm : sm,
				plugins:this.rowActions,
				viewConfig : {
					forceFit : true,
					autoFill : true, //自动填充
					forceFit : true
					//showPreview : false
				},
				bbar : new HT.PagingBar({store : this.store})
			});

			this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
						new ArchTemplateView(rec.data.templateId).show();
				});
			});		
			this.rowActions.on('action', this.onRowAction, this);
			
		
	},
	
	/**
	 * 
	 * @param {} self 当前窗体对象
	 */
	search:function(self){
		if(self.searchPanel.getForm().isValid()){//如果合法
				self.searchPanel.getForm().submit({
					waitMsg:'正在提交查询',
					url:__ctxPath+'/archive/listArchTemplate.do',
					params:{'Q_archivesType.proTypeId_L_EQ':self.typeId},
					success:function(formPanel,action){
			            var result=Ext.util.JSON.decode(action.response.responseText);
			            self.gridPanel.getStore().loadData(result);
					}
			});
		}
	},
	
	/**
	 * 添加记录
	 */
	createRecord:function(){
		new ArchTemplateForm({
			typeId : this.typeId,
			typeName : this.typeName
		}).show();
	},
	/**
	 * 按IDS删除记录
	 * @param {} ids
	 */
	delByIds:function(ids,gridPanel){
		Ext.Msg.confirm('信息确认','您确认要删除所选记录吗？',function(btn){
			if(btn=='yes'){
				Ext.Ajax.request({
								url:__ctxPath+'/archive/multiDelArchTemplate.do',
								params:{ids:ids},
								method:'POST',
								success:function(response,options){
									Ext.ux.Toast.msg('操作信息','成功删除所选公文分类！');
									gridPanel.getStore().reload();
								},
								failure:function(response,options){
									Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
								}
							});
			}
		});//end of comfirm
	},
	/**
	 * 删除多条记录
	 */
	delRecords:function(){
		var selectRecords = this.gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.templateId);
		}
		this.delByIds(ids,this.gridPanel);
	},
	
	/**
	 * 编辑记录
	 * @param {} record
	 */
	editRecord:function(record){
		new ArchTemplateForm({templateId:record.data.templateId}).show();
	},
	/**
	 * 管理列中的事件处理
	 * @param {} grid
	 * @param {} record
	 * @param {} action
	 * @param {} row
	 * @param {} col
	 */
	onRowAction:function(gridPanel, record, action, row, col) {
		switch(action) {
			case 'btn-del':
				this.delByIds(record.data.templateId,gridPanel);
				break;
			case 'btn-edit':
				this.editRecord(record);
				break;
			case 'btn-readdocument':
				new OfficeTemplateView(record.data.fileId);
				break;
			default:
				break;
		}
	},
	/**
	 * 设置当前模板分类的ID
	 * @param {} typeId
	 */
	setTypeId:function(typeId){
		if(typeId==0){//表示为所有类型
			this.typeId=null;
			return;
		}
		this.typeId=typeId;
	},
	setTypeName:function(typeName){
		if(typeName){
			this.typeName=typeName;
		}
	}
});