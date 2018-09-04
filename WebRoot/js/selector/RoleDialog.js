/**
 * 角色列表选择
 * @class RoleDialog
 * @extends Ext.Window
 */
RoleDialog = Ext.extend(Ext.Window, {
			constructor : function(conf) {
				Ext.applyIf(this, conf);
				this.initUI();
				RoleDialog.superclass.constructor.call(this, {
					modal:true,
					width : 830,
					height : 420,
					title:'角色选择',
					layout : 'border',
					maximizable:true,
					items : [this.searchPanel, this.gridPanel, this.buttonPanel, this.resultPanel],
					buttonAlign:'center',
					buttons:[
						{
							iconCls : 'btn-ok',
							text : '确定',
							scope:this,
							handler :this.ok
						}, {
							text : '取消',
							iconCls : 'btn-cancel',
							scope:this,
							handler : this.close
						}
					]
				});
				this.initData();
			},
			//确定
			ok:function(){
				var grid = this.resultPanel;
				var rows = grid.getStore();
				var roleIds = '';
				var roleNames = '';
				for (var i = 0; i < rows.getCount(); i++) {
					if (i > 0) {
						roleIds += ',';
						roleNames += ',';
					}
					roleIds += rows.getAt(i).data.roleId;
					roleNames += rows.getAt(i).data.roleName;
				}
				if (this.callback) {
					
					var scope=this.scope?this.scope:this;
					this.callback.call(scope, roleIds, roleNames);
				}
				this.close();
			},
			//搜索
			search : function() {
				$search({
							searchPanel : this.searchPanel,
							gridPanel : this.gridPanel
						});
			},
			reset : function() {
				this.searchPanel.getForm().reset();
			},
			initUI : function() {
				this.searchPanel = new HT.SearchPanel({
					layout : 'form',
					region : 'north',
					colNums : 2,
					items : [{
								fieldLabel : '角色名称',
								name : 'Q_roleName_S_LK',
								flex : 1,
								xtype : 'textfield'
							}],
					buttons : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}, {
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
				});
				this.gridPanel = new HT.GridPanel({
					isShowTbar:true,
					tbar:[{
				        xtype:'label',
				        text: '选择角色(双击选中)'
				    }],
					region:'west',
					width: 390,
					id : 'RoleSelectorGrid',
					url : __ctxPath + '/system/listAllAppRole.do',
					fields : [{
								name : 'roleId',
								type : 'int'
							}, 'roleName', 'roleDesc'],
					columns : [{
								header : 'roleId',
								dataIndex : 'roleId',
								hidden : true
							}, {
								header : "角色名称",
								dataIndex : 'roleName',
								width : 60
							}, {
								header : "角色描述",
								dataIndex : 'roleDesc',
								width : 60
							}],
					listeners:{
						scope:this,
						'rowdblclick':this.roleForSelPanelDblClick
					}
				});
				
				this.buttonPanel = new Ext.Panel({
					region:'center',
					id: 'BtnPanel',
					layout : {
	                    type : 'vbox',
	                    pack : 'center',
	                    align : 'center'
	                },
					defaultType : 'button',
	                items : [{
	                	iconCls : 'add-all',
	                	text : '',
	                	handler : this.addAll
	                },{
	                	iconCls : 'rem-all',
	                	text : '',
	                	handler : this.removeAll
	                }],
	                border: false
				});
				
				this.resultPanel = new HT.GridPanel({
					region:'east',
					width: 370,
					id: 'ResultRoleGrid',
					isShowTbar:true,
					showPaging: false,
					tbar:[{
				        xtype:'label',
				        text: '已选角色(双击删除)'
				    }],
					fields : [{
						name : 'roleId',
						type : 'int'
					}, 'roleName', 'roleDesc'],
					columns : [{
								header : 'roleId',
								dataIndex : 'roleId',
								hidden : true
							}, {
								header : "角色名称",
								dataIndex : 'roleName',
								width : 60
							}, {
								header : "角色描述",
								dataIndex : 'roleDesc',
								width : 60
							}],
					listeners:{
						scope:this,
						'rowdblclick':this.roleForResPanelDblClick
					}
				});
			},
			
			initData:function(){
				var roleids = document.getElementsByName("roleid");
				var rolenames = document.getElementsByName("rolename");
				var roledescs = document.getElementsByName("roledesc");
				var rrg = Ext.getCmp('ResultRoleGrid');
				var rrgStore = rrg.getStore();
				if(roleids){
					for(var index=0;index<roleids.length;index++){
						var roleId = roleids[index].value;
						var roleName = rolenames[index].value;
						var roleDesc = roledescs[index].value;
						var newData = {roleId:roleId,roleName:roleName,roleDesc:roleDesc};
						var newRecord = new rrgStore.recordType(newData);
						rrg.stopEditing();
						rrgStore.add(newRecord);
					}
				}
				
				if(this.roleIds){
					var arrRoleIds = this.roleIds.split(',');
					var arrRoleName = this.roleName.split(',');
					for(var index=0;index<arrRoleIds.length;index++){
						var roleId = arrRoleIds[index];
						var roleName = arrRoleName[index];
						var newData = {roleId:roleId,roleName:roleName,roleDesc:''};
						var newRecord = new rrgStore.recordType(newData);
						rrg.stopEditing();
						rrgStore.add(newRecord);
					}
				}
				
			},
			
			/**
			 * 添加所有
			 */
			addAll : function(){
				var rsg = Ext.getCmp('RoleSelectorGrid');
				var rrg = Ext.getCmp('ResultRoleGrid');
				var rrgStore = rrg.getStore();
				var rsgRows = rsg.getSelectionModel().getSelections();
				for(var i = 0; i<rsgRows.length; i++){
					var roleId = rsgRows[i].data.roleId;
					var roleName = rsgRows[i].data.roleName;
					var roleDesc = rsgRows[i].data.roleDesc;
					var isExist = false;
					//查找是否存在该记录
					for(var j=0; j<rrgStore.getCount(); j++){
						if(rrgStore.getAt(j).data.roleId== roleId){
							isExist = true;
							break;
						}
					}
					if(!isExist){
						var newData = {roleId:roleId,roleName:roleName,roleDesc:roleDesc};
						var newRecord = new rrgStore.recordType(newData);
						rrg.stopEditing();
						rrgStore.add(newRecord);
					}
				}
			},
			
			/**
			 * 移除所有
			 */
			removeAll : function(){
				var selGrid=Ext.getCmp('ResultRoleGrid');
				var rows = selGrid.getSelectionModel().getSelections();
				var selStore = selGrid.getStore();
				for(var i=0 ;i<rows.length; i++){
					selGrid.stopEditing();
					selStore.remove(rows[i]);
				}
			},
			
			/**
			 * 双击选择角色
			 * @param grid
			 * @param rowIndex
			 * @param e
			 */
			roleForSelPanelDblClick:function(grid,rowIndex,e){
				var store=grid.getStore();
				var record=store.getAt(rowIndex);
				var selStore=this.resultPanel.getStore();
				for(var i=0;i<selStore.getCount();i++){
					if(selStore.getAt(i).data.roleId==record.data.roleId) return;
				}
				var recordType=selStore.recordType;
				selStore.add(new recordType(record.data));
			},
			
			/**
			 * 双击删除已选角色
			 * @param grid
			 * @param rowIndex
			 * @param e
			 */
			roleForResPanelDblClick:function(grid,rowIndex,e){
				var selStore=this.resultPanel.getStore();
				selStore.removeAt(rowIndex);
			}
			
		});