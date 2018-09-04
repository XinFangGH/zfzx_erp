/**
 * 系统组织架构的设置
 * 
 * @class OrgSettingView
 * @extends Ext.Panel
 */

OrganizationStructureView = Ext.extend(Ext.Panel, {
	constructor : function(conf) {
		Ext.applyIf(this, conf);
		this.initUI();
		OrganizationStructureView.superclass.constructor.call(this, {
					title : '加盟商组织结构管理',
					iconCls : 'menu-OrgSetting',
					id : 'OrganizationStructureView',
					layout : 'border',
					items : [this.leftPanel, this.centerPanel, this.rightPanel]
				});
	},
	// 初始化UI
	initUI : function() {
		this.demStore = new Ext.data.ArrayStore({
					autoLoad : true,
					url : __ctxPath + '/system/comboDemension.do',
					fields : ['id', 'name'],
					listeners : {
						scope : this,
						'load' : function(store) {
						}
					}
				});
		// 维度选择下拉
		this.demensionCombo = new Ext.form.ComboBox({
					displayField : 'name',
					valueField : 'id',
					editable : false,
					emptyText : '所有维度',
					mode : 'local',
					width : 180,
					triggerAction : 'all',
					store : this.demStore,
					listeners : {
						scope : this,
						'select' : this.demensionSel
					}
				});
		// 组织树Panel
		this.orgPanel = new zhiwei.ux.TreePanelEditor({
					treeType : 'groupCompany',
					filter : false,
					url : __ctxPath
							+ '/system/treeGroupOrganization.do?isOnlyShowCompany='
							+ true,
					scope : this,
					autoScroll : true,
					onclick : this.orgTreeClick,
					contextMenuItems : [{
								text : '新建加盟商',
								iconCls : 'btn-add',
								scope : this,
								handler : this.addOrg
							}]
				});
		// this.cenPanel = this.getOrgDetailPanel();
		var initFlag = true;
		this.leftPanel = new Ext.Panel({
					collapsible : true,
					split : true,
					region : 'west',
					width : "23%",
					title : '加盟商列表',
					layout : 'fit',
					items : [this.orgPanel]
				});

		this.orgGridUserPanel = this.getOrgUsersGrid.call(this, "");
		this.rightPanel = new Ext.Panel({
					region : 'east',
					title : '人员管理',
					layout : 'fit',
					width : "50%",
					items : [this.orgGridUserPanel]
				});
		// 右边Panel
		this.centerPanel = new Ext.Panel({
					region : 'center',
					title : '部门管理',
					layout : 'fit',
					width : "30%",
					items : [this.getOrgDetailPanel.call(this, null, '', 0)]
				});

	},
	// 取得某个组织的人员列表
	getOrgUsersGrid : function(depId) {
		if (!depId) {
			depId = '';
		}
		var oneTbar = new Ext.Toolbar({
					items : [{
								xtype : 'button',
								text : '加入员工',
								iconCls : 'btn-add',
								scope : this,
								handler : this.addUser
							}, '-', {
								xtype : 'button',
								text : '删除员工',
								iconCls : 'btn-del',
								scope : this,
								handler : this.removeUserOrg
							}, '-', {
								text : '添加新员工',
								iconCls : 'btn-users',
								scope : this,
								handler : this.addNewUser
							}]
				});
		this.userGridPanel = new HT.GridPanel({
					tbar : ['姓名：', {
								xtype : 'textfield',
								name : 'fullname'
							}, '-', '账号：', {
								xtype : 'textfield',
								name : 'username'
							}, ' ', {
								xtype : 'button',
								text : '查询',
								iconCls : 'btn-query',
								scope : this,
								handler : this.query
							}],
					region : 'center',
					rowActions : true,
					url : __ctxPath + '/system/depBranchUsersAppUser.do',
					id : 'dpartment_userGrid',
					baseParams : {
						depId : depId,
						pageSize : this.pageSize
					},
					fields : ['userId', 'fullname', 'username', 'status', 'key','userNumber'],
					columns : [{
						header : '状态',
						dataIndex : 'status',
						width : 30,
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
						header : '账号',
						dataIndex : 'username',
						renderer : function(value) {
							if (value.split("@").length > 1) {
								return value.split("@")[0];
							}
							return value;

						}
					}, {
						header : '姓名',
						dataIndex : 'fullname'
					}, {
						header : '编号',
						dataIndex : 'userNumber'
					}, new Ext.ux.grid.RowActions({
								header : '管理',
								width : 100,
								actions : [{
									iconCls : 'btn-del',
									qtip : '删除',
									style : 'margin:0 3px 0 3px',
									fn : function(rs) {
										if (rs.data.userId == -1
												|| (rs.data.key == 'systemUser')) {
											return false;
										}
										return true;
									}
								}, {
									iconCls : 'btn-edit',
									qtip : '编辑',
									style : 'margin:0 3px 0 3px',
									fn : function(rs) {
										if (rs.data.userId == -1
												|| (rs.data.key == 'systemUser')) {
											return false;
										}
										return true;
									}
								}, {
									iconCls : 'btn-password',
									qtip : '重置密码',
									style : 'margin:0 3px 0 3px'
								}],
								listeners : {
									scope : this,
									'action' : this.onRowAction
								}
							})],
					listeners : {
						'render' : function() {
							oneTbar.render(this.tbar); // add one tbar
						}
					}
				});
		return this.userGridPanel;
	},
	query : function() {
		var toolbar = this.userGridPanel.getTopToolbar();
		var fullname = toolbar.getCmpByName('fullname').getValue();
		var username = toolbar.getCmpByName('username').getValue();
		var store = this.userGridPanel.getStore();
		var departId = 0;
		var departmentObj = this.centerPanel.get(0).get(0);
		var treeObj = departmentObj.get(0);
		if (treeObj.selectedNode) {
			departId = treeObj.selectedNode.id;
		}
		if (departId == 0) {
			if (this.orgPanel.selectedNode == null
					|| this.orgPanel.selectedNode == "") {
				departId = 1;
			} else {
				departId = this.orgPanel.selectedNode.id;
			}
		}
		var baseParam = {
			'Q_username_S_LK' : username,
			'Q_fullname_S_LK' : fullname,
			'depId' : departId
		};
		baseParam.start = 0;
		baseParam.limit = 25;
		store.baseParams = baseParam;
		this.userGridPanel.getBottomToolbar().moveFirst();
	},
	// 添加组织的员工
	addUser : function() {

		var orgId = 0;
		var departmentObj = this.centerPanel.get(0).get(0);
		var treeObj = departmentObj.get(0);
		var rightNode = treeObj.selectedNode;// 右侧node
		var selNode = this.orgPanel.selectedNode;
		var orgType = "";
		if (null != rightNode) {
			orgId = rightNode.id;
			orgType = rightNode.attributes.orgType;
		} else {
			if (selNode) {
				orgId = selNode.id;
				orgType = selNode.attributes.orgType;
			}
		}
		if (orgType != 2) {
			Ext.ux.Toast.msg('操作信息', '只能在部门下加入员工!');
			return;
		}
		if (orgId == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择对应的组织!');
			return;
		}
		var companyId = 1;
		if (selNode) {
			companyId = selNode.id;
		}
		var userStore = this.userGridPanel.getStore();
		var userIds = "";
		var userNames = "";
		userStore.each(function(record) {
					userIds += record.get('userId') + ",";
					userNames += record.get('fullname') + ",";
				});
		userIds = userIds.substring(0, userIds.length - 1); // 取子字符串。
		userNames = userNames.substring(0, userNames.length - 1); // 取子字符串。
		// 弹出用户选择器，根据当前选择的节点，把用户加入该组织或部门
		new UserDialog({
					title : '加入新用户',
					scope : this,
					single : false,
					companyId : companyId,
					userIds : userIds,
					userName : userNames,
					callback : function(ids, names) {
						Ext.Ajax.request({
									method : 'POST',
									scope : this,
									url : __ctxPath
											+ '/system/addOrgsUserOrg.do',
									params : {
										userIds : ids,
										orgId : orgId
									},
									success : function(resp, options) {
										this.userGridPanel.getStore().reload();
									}
								});
					}
				}).show();
	},
	// 添加新用户
	addNewUser : function() {
		var orgType = "";
		var companyId = 1;// 默认为总公司
		var selNode = this.orgPanel.selectedNode;
		var departmentObj = this.centerPanel.get(0).get(0);
		var treeObj = departmentObj.get(0);
		var rightNode = treeObj.selectedNode;// 右侧node
		var grid = Ext.getCmp("dpartment_userGrid");
		if (null != rightNode) {
			orgType = rightNode.attributes.orgType;
		} else {
			if (selNode) {
				orgType = selNode.attributes.orgType;
			}
		}
		if (selNode) {
			companyId = selNode.id;
		}
		App.clickTopTab('UserFormPanel', {
					companyId : companyId,
					gridObj : grid
				});
	},

	// 行编辑
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.userId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			case 'btn-password' :
				this.resetPassword.call(this, record.data.userId);
				break;
			default :
				break;
		}
	},
	// 编辑用户信息
	editRs : function(record) {
		var departmentObj = this.centerPanel.get(0).get(0);
		var selNode = this.orgPanel.selectedNode;
		var companyId = "";
		if (selNode) {
			companyId = selNode.id;
		}
		App.clickTopTab('UserFormPanel_' + record.data.userId, {
					userId : record.data.userId,
					username : record.data.username,
					companyId : companyId,
					listeners: {  
		                 'beforeclose': function(p){
		                 	if(p.depGridPanel.store.data.items.length==0){
		                 		Ext.ux.Toast.msg('操作','请为员工分配部门！');
                 				return false;
                 			}
		                 }  
		            }

				});
	},
	resetPassword : function(userId) {
		new setPasswordForm(userId);
	},
	// 删除的员工
	removeRs : function(userId) {

		var grid = Ext.getCmp("dpartment_userGrid");
		var orgID = "";
		var companyId = 1;

		var departmentObj = this.centerPanel.get(0).get(0);
		var treeObj = departmentObj.get(0);
		if (null != treeObj.selectedNode) {
			orgID = treeObj.selectedNode.id;
		}
		var selNode = this.orgPanel.selectedNode;
		if (selNode) {
			companyId = selNode.id;
		}
		if (orgID == "") {
			Ext.ux.Toast.msg("信息", "请选择部门！");
			return false;
		}
		Ext.Msg.confirm('删除操作', '你确定要删除该用户吗?', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : __ctxPath
											+ '/system/multiDelUserOrg.do',
									method : 'post',
									params : {
										ids : userId,
										companyId : companyId,
										orgId : orgID
									},
									success : function(response) {
										var result = Ext.util.JSON
												.decode(response.responseText);
										if (result.msg == null) {
											Ext.ux.Toast.msg("操作信息", "员工删除成功");
										}
										grid.getStore().reload();
									},
									failure : function() {
										Ext.ux.Toast.msg("操作信息", "用户删除失败");
									}
								});
					}
				});
	},
	// 删除部门员工
	removeUserOrg : function() {
		var departmentObj = this.centerPanel.get(0).get(0);
		var treeObj = departmentObj.get(0);
		var selNode = treeObj.selectedNode;
		var companyId = 1;
		if (selNode) {
			companyId = selNode.id;
		}
		var grid = Ext.getCmp("dpartment_userGrid");
		var selectRecords = grid.getSelectionModel().getSelections();
		var orgID = "";
		if (null != treeObj.selectedNode) {
			orgID = treeObj.selectedNode.id;
		}
		if (orgID == "") {
			Ext.ux.Toast.msg("信息", "请选择部门！");
			return false;
		}
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.userId);
		}
		Ext.Msg.confirm('删除操作', '你确定要删除该用户吗?？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : __ctxPath
											+ '/system/multiDelUserOrg.do',
									method : 'post',
									params : {
										ids : ids.toString(),
										companyId : companyId,
										orgId : orgID
									},
									success : function(response) {
										var result = Ext.util.JSON
												.decode(response.responseText);
										if (result.msg == null) {
											Ext.ux.Toast.msg("操作信息", "员工删除成功");
										}
										grid.getStore().reload();
									},
									failure : function() {
										Ext.ux.Toast.msg("操作信息", "员工删除失败");
									}
								});
					}
				}, this);
	},
	// 添加维度
	addDemension : function() {
		new DemensionForm({
					callback : this.reloadDemStore,
					scope : this
				}).show();
	},
	// 维度下拉选择
	demensionSel : function(combo, record, index) {
		var demId = combo.getValue();
		this.orgPanel.loader = new Ext.tree.TreeLoader({
					baseParams : {
						demId : demId
					},
					dataUrl : __ctxPath + '/system/treeOrganization.do',
					requestMethod : 'GET'
				});
		this.orgPanel.selectedNode = null;
		this.orgPanel.root.reload();
	},
	// 重新加载维度下接列表
	reloadDemStore : function() {
		this.demStore.reload();
	},
	// 重新加载岗位树
	reloadPosition : function() {
		this.posPanel.root.reload();
	},
	// 重新加载组织树
	reloadOrganization : function() {
		this.orgPanel.root.reload();
	},
	// 组织树节点点击
	orgTreeClick : function() {
		var group_name = this.orgPanel.selectedNode.parentNode.text;
		var orgId = this.orgPanel.selectedNode.id;
		var orgType = this.orgPanel.selectedNode.attributes.orgType;
		var orgName = this.orgPanel.selectedNode.attributes.text;
		var displayName = orgName;
		if (typeof(group_name) != "undefined") {
			displayName = group_name + "_" + orgName;
		} else {
			var rootNood = this.orgPanel.getNodeById("1");
			displayName = rootNood.text;
		}
		this.centerPanel.remove(this.cenPanel, false);
		this.cenPanel = this.getOrgDetailPanel.call(this, orgId, displayName,
				orgType);
		this.centerPanel.add(this.cenPanel);
		this.centerPanel.doLayout();
		this.centerPanel.setTitle('【' + displayName + '】部门管理');
		this.rightPanel.remove(this.orgGridUserPanel, false);
		this.orgGridUserPanel = this.getOrgUsersGrid.call(this, orgId);
		this.rightPanel.add(this.orgGridUserPanel);
		this.rightPanel.doLayout();
		this.rightPanel.setTitle("【" + displayName + "】" + "人员管理");
	},
	// 取得组织明细
	getOrgDetailPanel : function(orgId, orgName, orgType) {

		var departmentOrgId = 0;
		var centerPanel = this.centerPanel;
		var rightPanel = this.rightPanel;
		function departSelectNodeClick() {

			var orgDetailPanel = this.orgDetailPanel;// 包含树的panel
			var rigthSelectNode = this.orgDetailPanel.get(0).selectedNode;
			var parentName = this.getAllParentNodeName(rigthSelectNode);// 当前节点所有父节点名称的集合;
			var depatId = rigthSelectNode.id; // 当前节点中的ID
			var thisOrgname = rigthSelectNode.text;
			var gridPanel = rightPanel.get(0);
			var dispalyname = parentName + thisOrgname;
			rightPanel.setTitle("【" + dispalyname + "】" + "人员管理");
			var store = gridPanel.getStore();
			store.load({
						url : __ctxPath
								+ '/system/getOrganizationUsersAppUser.do',
						params : {
							start : 0,
							limit : 25,
							depId : depatId
						}
					});
		}
		if (!orgId) {
			orgId = 0;
		}
		this.orgDetailPanel = new Ext.Panel({
			orgId : orgId,
			border : false,
			tbar : [{
				text : '添加部门',
				iconCls : 'btn-add',
				scope : this,
				handler : function() {

					var rootNood = this.orgPanel.getNodeById("1");
					var orgId = rootNood.id; // 默认上级公司为总公司
					var orgText = rootNood.text;
					var orgType = rootNood.attributes.orgType;
					var parentName = "";
					var orgDetailPanel = this.orgDetailPanel;
					if (null == orgDetailPanel.get(0).selectedNode) { // 如果当前没有选择中间的加盟商
						var leftSelectNode = this.orgPanel.selectedNode;
						if (null != leftSelectNode) {
							orgId = leftSelectNode.id;
							orgText = leftSelectNode.text;
							orgType = leftSelectNode.attributes.orgType;
						}
					} else {
						var rightNodeSelect = orgDetailPanel.get(0).selectedNode;
						orgId = rightNodeSelect.id;
						orgText = rightNodeSelect.text;
						orgType = rightNodeSelect.attributes.orgType;
						var nonode = rightNodeSelect.parentNode;
						var names = new Array();// 存储id的数组
						var depth = rightNodeSelect.getDepth();// 获得深度
						for (var i = 0; i < (depth - 1); i++) {
							names.push(nonode.text);
							nonode = nonode.parentNode;
						}
						for (var j = (names.length - 1); j >= 0; j--) {
							parentName += names[j] + "_";
						}
						orgText = parentName + orgText;
					}
					function reloadDate() {
						orgDetailPanel.get(0).root.reload();
					}
					new DepartmentForm({
								'parentName' : orgText,
								'orgSupId' : orgId,
								'callback' : reloadDate
							}).show();

				}
			}, '-', {
				text : '编辑部门',
				iconCls : 'btn-edit',
				scope : this,
				handler : function() {

					var orgDetailPanel = this.orgDetailPanel;
					var rightNodeSelect = orgDetailPanel.get(0).selectedNode;
					var orgId = rightNodeSelect.id;
					var orgType = rightNodeSelect.attributes.orgType;
					var parentName = "";
					if (orgType != 2) {
						alert('只能修改部门!');
						return false;
					}
					var orgText = orgDetailPanel.get(0).selectedNode.text;
					parentName = this.getAllParentNodeName(rightNodeSelect);
					function reloadData() {
						orgDetailPanel.get(0).root.reload();
					}
					var pn = parentName + orgText;//
					new DepartmentForm({
								'parentName' : pn,
								'orgId' : orgId,
								'callback' : reloadData
							}).show();
				}
			}, '-', {
				text : '删除部门',
				iconCls : 'btn-del',
				scope : this,
				handler : this.delOrg
			}],
			autoScroll : true,
			height : 800,
			region : 'center',
			items : [new zhiwei.ux.TreePanelEditor({
				treeType : 'org',
				url : __ctxPath
						+ '/system/getDepartmentByCompanyOrganization.do?orgId='
						+ orgId,
				scope : this,
				border : false,
				filter : false,
				autoScroll : true,
				onclick : departSelectNodeClick
			})]
		});
		var orgPanel = new Ext.Panel({
					id : 'orgPanel',
					border : false,
					layout : 'border',
					items : [this.orgDetailPanel]
				});
		if (orgType == 0) {// 集团下只能添加公司
		} else if (orgType == 1 || orgType == 2) {// 公司或部门下才允许添加部门
			var parentName = "";
			parentName = orgName
		}
		return orgPanel;
	},
	addCompany : function(orgId, orgName) {
		var selectedNode = this.orgPanel.selectedNode;
		// var orgId=this.orgPanel.selectedNode.id;
		new CompanyWin({
					orgSupId : orgId,
					supOrgName : orgName,
					scope : this,
					callback : this.addCompanyCallback
				}).show();
	},
	getAllParentNodeName : function(node) {
		var nonode = node.parentNode;
		var names = new Array();// 存储id的数组
		var parentName = "";
		var depth = node.getDepth();// 获得深度
		for (var i = 0; i < (depth - 1); i++) {
			names.push(nonode.text);
			nonode = nonode.parentNode;
		}
		for (var j = (names.length - 1); j >= 0; j--) {
			parentName += names[j] + "_";
		}
		return parentName;
	},
	/**
	 * 公司添加回调
	 */
	addCompanyCallback : function() {
		this.orgPanel.root.reload();
	},
	/**
	 * 添加部门
	 */
	addDepartment : function(orgId, orgName) {
		new DepartmentWin({
					orgSupId : orgId,
					supOrgName : orgName,
					scope : this,
					callback : this.addDepartmentCallback
				}).show();
	},
	editDepartment : function(orgId, orgName) {
		new DepartmentWin({
					orgSupId : orgId,
					supOrgName : orgName,
					scope : this,
					callback : this.addDepartmentCallback
				}).show();
	},
	/**
	 * 添加部门回调
	 */
	addDepartmentCallback : function() {
		this.orgPanel.root.reload();
	},
	// 添加角色
	addRole : function(posId) {
		if (posId == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择对应的岗位!');
			return;
		}
		new RoleDialog({
					scope : this,
					callback : this.addRoleCallBack
				}).show();
	},

	// 新建加盟商
	addOrg : function() {
		if (this.orgPanel.selectedNode == null) {
			Ext.ux.Toast.msg('操作信息', '请选择左边的组组织树！');
			return;
		}
		var orgId = this.orgPanel.selectedNode.id;
		var orgType = this.orgPanel.selectedNode.attributes.orgType;
		if (orgType != 0) {
			orgId = 0;
		}
		var reloadPanel = this.orgPanel;
		function reloadTreeCallBack() {
			reloadPanel.root.reload();
		}
		new BranchOfficeForm({
					title : '添加加盟商',
					orgSupId : orgId,
					callback : reloadTreeCallBack
				}).show();
	},
	// 编辑加盟商
	editOrg : function() {
		var orgId = this.orgPanel.selectedNode.id;
		if (orgId == 0)
			return;
		var orgType = this.orgPanel.selectedNode.attributes.orgType;
		var reloadPanel = this.orgPanel;
		function reloadTreeCallBack() {
			reloadPanel.root.reload();
		}
		new BranchOfficeForm({
					orgId : orgId,
					title : '编辑加盟商',
					callback : reloadTreeCallBack
				}).show();
	},
	// 删除加盟商
	delOrg : function() {
		var orgDetailPanel = this.orgDetailPanel;
		if (null == orgDetailPanel.get(0).selectedNode) {
			Ext.ux.Toast.msg('操作信息', '请先选择要删除的组织!');
			return;
		}
		var orgId = orgDetailPanel.get(0).selectedNode.id;
		var orgType = orgDetailPanel.get(0).selectedNode.attributes.orgType;
		if (orgType != 2) {
			alert('只能删除部门!');
			return false;
		}
		if (orgId == 0)
			return;
		Ext.Msg.confirm('信息确认', '注意：删除该组织将会删除其下所有的子组织，您确认要删除所选组织吗？', function(
				btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
							url : __ctxPath + '/system/multiDelOrganization.do',
							method : 'POST',
							scope : this,
							params : {
								ids : orgId
							},
							success : function(response, options) {
								Ext.ux.Toast.msg('操作信息', '成功删除该组织!');
								orgDetailPanel.get(0).root.reload();
							},
							failure : function(response, options) {
								Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
							}
						});
			}
		}, this);
	}
});