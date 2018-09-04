/**
 * 分类管理
 * @class ProModalManager
 * @extends Ext.Panel
 */
BusinessRoleManager=Ext.extend(Ext.Panel,{
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		BusinessRoleManager.superclass.constructor.call(this,{
			id:'BusinessRoleManager',
			//height:450,
			//autoScroll:true,
			layout:'border',
			title:'加盟商业务角色管理',
			iconCls:"btn-team51",
			items:[
				this.leftPanel,
				this.panel
			]
		});
	},
	initUIComponents:function(){
		
			grant = function(id,org_type, roleName) {
			new RoleGrantRightView(id, roleName,org_type,'business','business');
		};
		copy = function(id) {
			new AppRoleForm({roleType:'business',roleGridId:'BusinessRoleGrid',roleId : id,isCopy : 1}).show();// 1代表是复制
		}
		edit = function(id,org_type,orgId) {
			new AppRoleForm({roleType:'business',roleGridId:'BusinessRoleGrid',roleId : id,isCopy : 0,org_type:org_type,biaoshi:'business',orgId:orgId}).show();// 0代表不是复制
		};
		remove = function(id,orgId) {
			var grid=Ext.getCmp('BusinessRoleGrid')
			Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
						url : __ctxPath + '/system/multiDelAppRole.do',
						params : {
							ids : id
						},
						method : 'post',
						success : function() {
							Ext.ux.Toast.msg("信息", "成功删除所选记录！");
							grid.getStore().reload({
										params : {
											orgId:orgId/*,
											start : 0,
											limit : 25*/
										}
									});
						}
					});
				}
			});
		};
		//导出授权
		exportAccess = function(roleNames,roleIds,orgId) {
			var grid=Ext.getCmp('BusinessRoleGrid')
			if(typeof(orgId)=="undefined"){
				orgId=0;
			}
			roleNames=encodeURI(roleNames);
			Ext.Msg.confirm('信息确认', '您确认要导出授权记录吗？', function(btn) {
				if (btn == 'yes') {
					
					window.open( __ctxPath + "/system/exitExcelAppRole.do?roleNames="+roleNames+"&roleIds="+roleIds+"&orgId="+orgId);
				}
			});
		};
			// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			height : 35,
			region : 'east',
			frame : false,
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			layout : 'hbox',
			defaults : {
				xtype : 'label',
				border : false,
				margins : {
					top : 2,
					right : 4,
					bottom : 2,
					left : 4
				}
			},
			items : [{
						text : '角色名称'
					}, {
						xtype : 'textfield',
						name : 'Q_roleName_S_LK'
					}, {
						text : '角色描述'
					}, {
						xtype : 'textfield',
						name : 'Q_roleDesc_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						scope : this,
						handler : function() {
							if (this.searchPanel.getForm().isValid()) {
								$search({
									searchPanel :this.searchPanel,
									gridPanel : this.centerPanel
								});
							}

						}
					}, {
						xtype : 'button',
						text : '重置',
						iconCls : 'btn-reset',
						scope : this,
						handler : function() {
							if (this.searchPanel.getForm().isValid()) {
								this.searchPanel.getForm().reset()
							}

						}
					},{
						xtype : 'hidden',
						name : 'orgId'
					}]
		});
		this.leftPanel = new Ext.Panel({
			title:'加盟商列表',
			region : 'west',
			layout : 'anchor',
			collapsible : true,
			split : true,
			width : 200,
			autoHeight:true,
			items : [

			{
				xtype : 'treePanelEditor',
				name:'orgPanel',
				split : true,
				rootVisible : false,
				border : false,
				height : Ext.getBody().getViewSize().height-115,
				autoScroll : true,
				scope : this,
				url:__ctxPath+'/system/companyTreeAppRole.do',
				onclick : function(node) {
				    this.selectedNode=node;
				    this.centerPanel.getStore().reload({
				        params:{orgId:node.id}
				    
				    })
				    this.oId=node.id;
				    this.searchPanel.getCmpByName('orgId').setValue(node.id)
				  //  this.centerPanel.getBottomToolbar().moveFirst()
	                this.centerPanel.setTitle("【"+node.text+"】业务角色管理")
				}
			}]
		}

		);
		
					
	
		var tbar=new Ext.Toolbar({
			items : [ {
				iconCls : 'btn-add',
				text : '新增',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_add_business')?false:true,			
				handler : function() {
					var selectedNode=this.leftPanel.getCmpByName('orgPanel').selectedNode;
					var orgId=null;
					var org_type=null;
					if(null!=selectedNode){
						orgId=selectedNode.id
						org_type=selectedNode.attributes.org_type
					}
					if(orgId==null || org_type==null){
						Ext.ux.Toast.msg('操作信息','请从左侧选择公司');
			            	return;
					}
					new AppRoleForm({roleType:'business',roleGridId:'BusinessRoleGrid',org_type:org_type,biaoshi:'business',orgId:orgId}).show();
				}
			}, {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_remove_business')?false:true,
				handler : function(){
					
						var grid = this.centerPanel;
						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						var idsN = '';
						for (var i = 0; i < selectRecords.length; i++) {
							if (selectRecords[i].data.isDefaultIn == '0'
									&& selectRecords[i].data.roleId != -1) {
								ids.push(selectRecords[i].data.roleId);
							} else {
								idsN += selectRecords[i].data.roleName + ',';
							}
						}
						if (idsN == '') {
							remove(ids,selectRecords[0].data.orgId);
						} else {
							Ext.ux.Toast.msg("信息", idsN + "不能被删除！");
						}
					
				}
			}, {
				iconCls : 'btn-collapsez',
				text : '导出授权',
				xtype : 'button',
				scope : this,
				//hidden : isGranted('_remove_business')?false:true,
				handler : function(){
					
						var grid = this.centerPanel;
						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要导出的角色！");
							return;
						}
						var ids = Array();
						var names = Array();
						var idsN = '';
						for (var i = 0; i < selectRecords.length; i++) {
							if (selectRecords[i].data.isDefaultIn == '0'
									&& selectRecords[i].data.roleId != -1) {
								ids.push(selectRecords[i].data.roleId);
								names.push(selectRecords[i].data.roleName);
							} else {
								idsN += selectRecords[i].data.roleName + ',';
							}
						}
						if (idsN == '') {
							exportAccess(names,ids,selectRecords[0].data.orgId);
						} else {
							Ext.ux.Toast.msg("信息", idsN + "不能被导出！");
						}
					
				}
			},{
			xtype : 'button',
			text : '导入授权',
			iconCls : 'btn-collapse',
			scope:this,
			handler : function() {
			
				new SlAccessView({
					roleGrantView:this.centerPanel,
					orgId:typeof(this.oId)=="undefined"?1:this.oId,
					flag :"accessupload"
				}).show();
				
			}
		}]
		});
		
		this.centerPanel= new HT.GridPanel({
			region:'center',
			id:'BusinessRoleGrid',
			height : Ext.getBody().getViewSize().height-150,
			tbar : tbar,
			url : __ctxPath + '/system/listAppRole.do',
			fields : [ {
					name : 'roleId',
					type : 'int'
				}, 'roleName', 'roleDesc', {
					name : 'status',
					type : 'int'
				}, 'isDefaultIn','orgId','org_type'],
			columns : [ {
				header : 'id',
				dataIndex : 'roleId',
				hidden : true
			}, {
				header : '状态',
				dataIndex : 'status',
				width:40,
				renderer : function(value) {
					var str = '';
					if (value == '1') {// 激活用户
						str += '<img title="激活" src="'
								+ __ctxPath
								+ '/images/flag/customer/effective.png"/>'
					} else {// 禁用用户
						str += '<img title="禁用" src="'
								+ __ctxPath
								+ '/images/flag/customer/invalid.png"/>'
					}
					return str;
				}
			}, {
				header : '角色名称',
				dataIndex : 'roleName'
			}, {
				header : '角色备注',
				dataIndex : 'roleDesc',
				renderer : function(value, metadata, record, rowIndex,
						colIndex) {
					if(record.data.isDefaultIn == '1') {
						return "【系统内置】" + value;
					}else {
						return value;
					
					}
				}
			}, {
				header : '授权',
				dataIndex : 'roleId',
				renderer : function(value, metadata, record, rowIndex,
						colIndex) {
					//alert(this.centerPanel)
					var editId = record.data.roleId;
					var org_type=record.data.org_type;
					var orgId=record.data.orgId;
					var roleName = record.data.roleName;
					var isDefaultIn = record.data.isDefaultIn;
					var str = '';
					if (editId != -1) {
						if (isDefaultIn == '0') {
							if (isGranted('_remove_business'))
								str = '<button title="删除" value=" " class="btn-del" onclick="remove('
										+ editId + ','+orgId+')"></button>';
							if (isGranted('_edit_business'))
								str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="edit('
										+ editId + ','+org_type+','+orgId+')"></button>';
							if (isGranted('_resource_business'))
								str += '&nbsp;<button title="授权" value=" " class="btn-grant" onclick="grant('
										+ editId
										+ ','
										+org_type
										+',\''
										+ roleName
										+ '\')">&nbsp;</button>';

						} else {
							str = '<button title="复制" value=" " class="btn-copyrole" onclick="copy('
									+ editId + ')"></button>';
						}
					}
					return str;
				}
			}]
				
		});
		this.panel=new Ext.Panel({
			region : 'center',
			title:'业务角色管理',
			items : [this.searchPanel,this.centerPanel]
		})
	}//end of initUIComponents
	
});