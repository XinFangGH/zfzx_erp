/**
 * 分类管理
 * @class ProModalManager
 * @extends Ext.Panel
 */
ControlRoleManager=Ext.extend(Ext.Panel,{
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		ControlRoleManager.superclass.constructor.call(this,{
			id:'ControlRoleManager',
			height:450,
			autoScroll:true,
			layout:'border',
			title:'加盟商管控角色管理',
			iconCls:"btn-team51",
			items:[
				this.leftPanel,
				this.centerPanel
			]
		});
	},
	initUIComponents:function(){
		grantControl = function(id, roleName,org_type) {
			new RoleGrantRightView(id, roleName,'1','control','control');
		};
		copyControl = function(id) {
			new AppRoleForm({roleType:'control',roleGridId:'ControlRoleGrid',org_type:null,roleId : id,isCopy : 1,biaoshi:'control',orgId:null}).show();// 1代表是复制
		}
		removeControl = function(id,grid) {
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
											start : 0,
											limit : 25
										}
									});
						}
					});
				}
			});
		};
		this.topbar = new Ext.Toolbar( {
			items : [ {
				iconCls : 'btn-add',
				text : '新增',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_add_control')?false:true,			
				handler : function() {
					new AppRoleForm({roleType:'control',roleGridId:'ControlRoleGrid',org_type:null,biaoshi:'control',orgId:null}).show();
				}
			}, {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_remove_control')?false:true,
				handler : function(){
					
						var grid = this.gridPanel;
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
							removeControl(ids,grid);
						} else {
							Ext.ux.Toast.msg("信息", idsN + "不能被删除！");
						}
					
				}
			}, {
				iconCls : 'btn-edit',
				text : '修改',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_remove_control')?false:true,
				handler : function(){
					
						var grid = this.gridPanel;
						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要修改的记录！");
							return;
						}
						rec=selectRecords[0]
						new AppRoleForm( {
							roleType : rec.data.roleType,roleGridId:'ControlRoleGrid',org_type:null,biaoshi:'control',roleId:rec.data.roleId,orgId:rec.data.orgId
						}).show();
					
				}
			},{
				iconCls : 'btn-add',
				text : '设置人员',
				xtype : 'button',
				scope : this,
				hidden : isGranted('_add_control')?false:true,			
				handler : this.setUpUser
			}, {
				iconCls : 'btn-collapsez',
				text : '导出授权',
				xtype : 'button',
				scope : this,
				//hidden : isGranted('_remove_control')?false:true,
				handler : function(){
					
						var grid = this.gridPanel;
						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						var idsN = '';
						var names = Array();
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
					roleGrantView:this.leftPanel,
					orgId:0,
					flag :"accessupload"
				}).show();
				
			}
		}]
		});
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
									gridPanel : this.gridPanel
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
					}]
		});
		this.gridPanel = new HT.GridPanel( {
			id:'ControlRoleGrid',
			tbar : this.topbar,
			height: Ext.getBody().getViewSize().height-115,
			autoScroll:true,
			url : __ctxPath + '/system/listAppRole.do?roleType=control',
			
			fields : [ {
					name : 'roleId',
					type : 'int'
				}, 'roleName', 'roleDesc', {
					name : 'status',
					type : 'int'
				}, 'isDefaultIn','controlCompanyId','roleType','appUsers'],
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
				hidden : isGranted('_resource_control')?false:true,	
				renderer : function(value, metadata, record, rowIndex,
						colIndex) {
					var editId = record.data.roleId;
					var roleName = record.data.roleName;
					var isDefaultIn = record.data.isDefaultIn;
					var str = '';
					if (editId != -1&&editId != 1) {
						if (isDefaultIn == '0') {
							
								str += '&nbsp;<button title="授权" value=" " class="btn-grant" onclick="grantControl('
										+ editId
										+ ',\''
										+ roleName
										+ '\')">&nbsp;</button>';

						} else {
							str = '<button title="复制" value=" " class="btn-copyrole" onclick="copyControl('
									+ editId + ')"></button>';
						}
					}
					return str;
				}
			}],
			listeners:{
				scope:this,
			   'click':function(){
			   	 var s=this.gridPanel.getSelectionModel().getSelections();
			   	  this.centerPanel.setTitle("【"+s[0].data.roleName+"】加盟商授权")
			   	  this.centerPanel.getStore().reload({
			   	     params:{roleId:s[0].data.roleId}
			   	  })
			   },
			   'rowdblclick':function(grid, rowindex, e){
			   	grid.getSelectionModel().each(function(rec) {
					new AppRoleForm( {
						roleType : rec.data.roleType,roleGridId:'ControlRoleGrid',org_type:null,biaoshi:'control',roleId:rec.data.roleId,orgId:rec.data.orgId
					}).show();
				});
			   }
			}
		//end of columns
				});
		this.leftPanel = new Ext.Panel({
			title:'管控角色管理',
			region : 'west',
			layout : 'anchor',
			collapsible : true,
			split : true,
			width : 600,
			autoHeight:true,
			items : [this.searchPanel,this.gridPanel]
		}

		);
		
	
		var tbar=new Ext.Toolbar({
			items:[{
					xtype:'button',
					text:'保存',
					iconCls:'btn-save',
					scope:this,
					hidden : isGranted('_save_control')?false:true,
					handler:function(){
						var s=this.gridPanel.getSelectionModel().getSelections();
						if(s.length<=0){
						    Ext.ux.Toast.msg('操作信息',"请选择管控角色");
						}else if(s.length>1){
							Ext.ux.Toast.msg('操作信息',"只能选择一种管控角色");
						}else{
							var roleId=s[0].data.roleId
							var grid=this.gridPanel
							var vRecords = this.centerPanel.getStore().getRange(0, this.centerPanel.getStore().getCount()); // 得到修改的数据（记录对象）
							var vCount = vRecords.length; // 得到记录长度
							// vDatas = s[0].data.controlCompanyId;
                           vDatas='';
							if (vCount > 0) {
			               // begin 将记录对象转换为字符串（json格式的字符串）
							for ( var i = 0; i < vCount; i++) {
								if(vRecords[i].data.isControl==true || vRecords[i].data.isControl=='true'){
										vDatas=vDatas+vRecords[i].data.orgId+","
								}
							}
					        if(vDatas!=''){
								vDatas = vDatas.substr(0, vDatas.length - 1);
					        }
							Ext.Ajax.request({
			                   url:  __ctxPath + '/system/saveControlComIdAppRole.do',
			                   method : 'POST',
			                   params : {
										roleId : roleId,
										controlComId:vDatas
									},
			                  success : function(response,request) {
	                        	  Ext.ux.Toast.msg('操作信息',"保存成功");
	                        	  grid.getStore().reload()
		                      },
		                      failure : function(response,request) {
		                      		Ext.ux.Toast.msg('操作信息',"保存失败");
		                      }
	                         });  
							}
						}
					}
				}
			]
		});
		var isControlColumn = new Ext.grid.CheckColumn({
			header : '是否管控',
			dataIndex : 'isControl',
			width : 70
			
		});
		this.centerPanel= new HT.GridPanel({
			title:'加盟商授权',
			region : 'center',
			tbar : tbar,
			hiddenCm:true,
			plugins:[isControlColumn],
			url : __ctxPath + "/system/getBranchCompanyAppRole.do",
			fields : [ {
				name : 'orgId',
				type : 'long'
			}, 'orgName','isControl'],
			columns : [ {
				header : 'id',
				dataIndex : 'orgId',
				hidden : true
			}, isControlColumn, {
				header : '加盟商名称',
				dataIndex : 'orgName'
			}]
			
		});
		
		//导出授权
		exportAccess = function(roleNames,roleIds,orgId) {
			if(typeof(orgId)=="undefined"){
				orgId=0;
			}
		
			var grid=this.gridPanel;
			Ext.Msg.confirm('信息确认', '您确认要导出授权记录吗？', function(btn) {
				if (btn == 'yes') {
					
					window.open( __ctxPath + "/system/exitExcelAppRole.do?roleNames="+roleNames+"&roleIds="+roleIds+"&orgId="+orgId);
				}
			});
		};
		
	},//end of initUIComponents
	setUpUser:function(){
		var s=this.gridPanel.getSelectionModel().getSelections();
		if(s.length<=0){
		    Ext.ux.Toast.msg('操作信息',"请选择管控角色");
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息',"只能选择一种管控角色");
		}else{
			var roleId=s[0].data.roleId
			Ext.Ajax.request({
               url:  __ctxPath + '/system/getAppUsersAppRole.do',
               method : 'POST',
               params : {
						roleId : roleId
					},
              success : function(response,request) {
              	var object = Ext.util.JSON.decode(response.responseText.trim())
              	var userIds=object.userIds;
              	var userNames=object.userNames;
        	 	new UserDialog({
					userIds : userIds,
					userName :userNames,
					type:'all',
					single : false,
					title : "设置人员",
					callback : function(uId, uname) {

							Ext.Ajax.request({
				               url:  __ctxPath + '/system/saveAppUsersAppRole.do',
				               method : 'POST',
				               params : {
										roleId : roleId,
										userIds : uId
									},
				              success : function(response,request) {
				              	var obj = Ext.util.JSON.decode(response.responseText)
				              	if(obj.unique==false){
				              	 	Ext.ux.Toast.msg('操作信息',"设置人员失败!"+obj.username+"已经拥有一种管控角色，不能再设置另一种管控角色");
				              	 	return;
				              	}else{
				            	  	Ext.ux.Toast.msg('操作信息',"设置成功");
				              	}
				              },
				              failure : function(response,request) {
				              	  Ext.ux.Toast.msg('操作信息',"设置失败");
				              }
				             });
					}
				}).show();
              },
              failure : function(response,request) {
              		Ext.ux.Toast.msg('操作信息',"数据加载失败");
              }
             });  
		
		}
	}
});