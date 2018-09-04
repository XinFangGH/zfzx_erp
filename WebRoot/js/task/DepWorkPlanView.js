Ext.ns('DepWorkPlanView');
DepWorkPlanView=Ext.extend(Ext.Panel,{
    searchFormPanel:null,
    gridPanel:null,
    constructor:function(_cfg){
        Ext.applyIf(this,_cfg);
        this.initUI();
        DepWorkPlanView.superclass.constructor.call(this,{
            id:'DepWorkPlanView',
		    title:'部门计划列表',
		    iconCls:'menu-depplan',
		    region:'center',
		    layout:'border',
		    items:[this.searchFormPanel,this.gridPanel]
        });    
        
    },
    initUI:function(){
        this.searchFormPanel=new Ext.FormPanel({
				height : 40,
				region:'north',
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
							name : 'workPlan.planName'
						}, {
							text : '计划类型'
						}, {
							xtype : 'textfield',
							name : 'workPlan.typeName'
						},
						{
							text : '负责人'
						}, {
							xtype : 'textfield',
							name : 'workPlan.principal'
						},{
							xtype : 'button',
							text : '查询',
							iconCls : 'search',
							scope:this,
							handler : this.search
						},{
						 xtype:'button',
						 text:'重置',
						 scope:this,
						 handler:function(){
						   this.searchFormPanel.getForm().reset();
						 }
						}]
			});
			
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
							dataIndex : 'globalType'
							,renderer:function(obj){
								if(obj!=null){
							 	  return obj.typeName;
								}
							}
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
						}
						
						],
				defaults : {
					sortable : true,
					menuDisabled : false,
					width : 100
				}
			});
		    this.store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : __ctxPath + '/task/departmentWorkPlan.do'
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
											'endTime','globalType','appUser',
	//											{
	//										  name:'globalType.typeName',
	//										  mapping:'globalType.typeName'
	//										}, 
											{
											  name:'userName',
											  mapping:'appUser.fullname'
											},
											'issueScope', 'participants', 'principal',
											'note', 'status', 'isPersonal', 'icon']
								}),
						remoteSort : true
					});
			this.store.setDefaultSort('planId', 'desc');
			this.store.load({
						params : {
							start : 0,
							limit : 25
						}
					});
			this.topbar=new Ext.Toolbar({
			   items:[{
			       xtype:'button',
			       text:'添加部门计划',
			       iconCls:'btn-add',
			       handler:this.createRecord
			   },{
			       xtype:'button',
			       text:'编辑部门计划',
			       iconCls:'btn-edit',
                   scope:this,
			       handler:this.editRecord
			   },{
			       xtype:'button',
			       text:'删除',
			       iconCls:'btn-del',
			       scope:this,
			       handler:this.delRecords
			   }]
			});
			this.gridPanel = new Ext.grid.GridPanel({
						id : 'DepWorkPlanGrid',
						tbar : this.topbar,
						store : this.store,
						region:'center',
						trackMouseOver : true,
						disableSelection : false,
						loadMask : true,
	//					autoHeight : true,
						cm : cm,
						sm : sm,
						viewConfig : {
							forceFit : true,
							enableRowBody : false,
							showPreview : false
						},
						bbar : new HT.PagingBar({store : this.store})
					});
	
			this.gridPanel.addListener('rowdblclick',this.rowdblclickaction );	
				
	    },
	    search:function() {
			var searchPanel = this.searchFormPanel;
			var gridPanel = this.gridPanel;
			if (searchPanel.getForm().isValid()) {
				$search({
					searchPanel :searchPanel,
					gridPanel : gridPanel
				});
			}

		},
		rowdblclickaction:function(grid, rowindex, e) {
			grid.getSelectionModel().each(function(rec) {
//				if(rec.data.appUser.userId==curUserInfo.userId){
//						new DepWorkPlanForm({planId:rec.data.planId,planName:rec.data.planName}).show();
//				}else{
				        new WorkPlanDetail({planId:rec.data.planId,planName:rec.data.planName}).show();
//				}
			});
		},
		createRecord:function(){
		   new DepWorkPlanForm().show();
		},
		editRecord:function(){
		    var selRs = this.gridPanel.getSelectionModel().getSelections();
		    if(selRs.length==0){
		        Ext.ux.Toast.msg("操作信息", "请选择要编辑的记录");
		        return;
		    }else if(selRs.length>1){
		        Ext.ux.Toast.msg("操作信息", "只可以编辑一条记录");
		        return;
		    }
		    
		    var rec=selRs[0];
			if(rec.data.appUser.userId==curUserInfo.userId){
				new DepWorkPlanForm({planId:rec.data.planId,planName:rec.data.planName}).show();
			}else{
			   Ext.ux.Toast.msg("操作信息", "你无权修改此记录");
		        return;
			}
		},
		delRecords:function(){
		    var selRs = this.gridPanel.getSelectionModel().getSelections();
		    if(selRs.length==0){
		        Ext.ux.Toast.msg("操作信息", "请选择要删除的记录");
		        return;
		    }
		    var ids=[];
		    var flag=false;
		    for(var i=0;i<selRs.length;i++){
		       var rec=selRs[i];
		       if(rec.data.appUser.userId==curUserInfo.userId){
		          ids.push(rec.data.planId);
		       }else{
		          flag=true;
		       }
		    }
		    
		    if(flag){
		        Ext.ux.Toast.msg("操作信息", "只有当前用户创建的记录才能被删除！");
		        return;
		    }
		    
		    $postDel({
		         url:__ctxPath + '/task/multiDelWorkPlan.do',
				 ids:ids,
				 grid:this.gridPanel
		    });
		    
			
		}
    
});
