/**
 * @description 菜单管理新增编辑窗口
 * @class MenuForm
 * @extends Window
 * @author YHZ
 * @company www.credit-software.com
 * @createtime 2011-1-26PM
 */
MenuForm = Ext.extend(Ext.Window, {

	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponent(this.id, this.text, this.isChild);
		MenuForm.superclass.constructor.call(this, {
			id : 'menuFormWin',
			title : this.text != null ? '新增/编辑菜单['+this.text+']信息' : '新增编辑菜单信息',
			iconCls : 'menu-m',
			layout : 'fit',
			modal : true,
			plain : false,
			maximizable : true,
			width : 650,
			height : 500,
			style : 'padding: 5px 5px 5px 5px;',
			buttonAlign : 'center',
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.submit,
				scope : this
			},
			buttons : [{
				text : '确定',
				iconCls : 'btn-ok',
				handler : this.submit
			} , {
				text : '取消',
				iconCls : 'btn-cancel',
				handler : function(){
					Ext.getCmp('menuFormWin').close();
				}
			}],
			items : [this.formPanel]
		});
	}, // end of this constructor
	// 组件实例化
	initUIComponent : function(menuId, menuText, isChild) {
		// store
		var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + '/menu/getMenu.do'
			}),
			reader : new Ext.data.JsonReader({
				root : 'fn',
				totalProperty : 'totalCounts',
				id : 'id',
				fields : ['fId', 'fText', 'fIconCls']
			}),
			remoteSort : false
		});// end of store
		if(menuId != null && menuId != 'undefined'){
			store.load({
				params : {
					id : menuId
				}
			});
		}
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(),{
				header : 'fId',
				dataIndex : 'fId',
				hidden : true,
				sortable : true,
				menuDisabled : true
			} , {
				header : '权限名称',
				dataIndex : 'fText',
				sortable : true,
				menuDisabled : true
			} , {
				header : '图标',
				dataIndex : 'fIconCls',
				renderer : function(value){
					return value != null ? '<button style="width:13px;height:13px;" class="'+value+'"/>' : '';
				},
				sortable : true,
				menuDisabled : true
			} ]
		});
		//editGridPanel
		var editGrid = new Ext.grid.GridPanel({
			id : 'MenuFormEditGrid',
			title : '菜单权限列表',
			autoHeight : true,
			autoScroll : true,
			sm : sm,
			trackMouseOver : true,
			tbar : new Ext.Toolbar({
				height : 30,
				items : [{
					text : '添加',
					iconCls : 'btn-add',
					scope : this,
					handler : this.addFn.createCallback(menuId, menuText, isChild)
				} , '-' , {
					text : '删除',
					iconCls : 'btn-del',
					scope : this,
					handler : this.del.createCallback(menuId)
				}]
			}),
			store : store,
			cm : cm,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			}
		}); // end of editGridPanel
		//editGrid添加双击事件
		editGrid.addListener('rowdblclick', function(grid ,rowIndex ,e){
			grid.getSelectionModel().each(function(rec) {
				new MenuFunctionForm({
					fId : rec.data.fId,
					fText : rec.data.fText,
					menuId : menuId,
					menuText : menuText
				}).show();
			});
		});
			
		// formPanel
		this.formPanel = new Ext.FormPanel({
			id : 'MenuFormPanel',
			layout : 'form',
			region : 'center',
			border : false,
			anchor : '98% 98%',
			url : __ctxPath + '/system/saveMenu.do',
			defaultType : 'textfield',
			style : 'padding: 5px 5px 5px 5px;margins: 5px 5px 5px 5px;',
			autoScroll : true,
			items : [{
				xtype : 'fieldset',
				title : '基本信息',
				layout : 'form',
				defaultType : 'textfield',
				items : [ {
					xtype : 'hidden',
					name : 'parentId',
					value : this.parentId != null ? this.parentId : ''
				} , {
					name : 'id',
					width : '93%',
					fieldLabel : '编号',
					maxLength : 125,
					allowBlank : false,
//					regex : /^[A-Za-z0-9]*$/,
//					regexText : '菜单编号不能输入中文！',
					readOnly : this.id != null ? true : false
				} , {
					xtype : 'hidden',
					name : 'type',
					value : this.text != null ? 'edit' : 'add'
				} , {
					name : 'text',
					width : '93%',
					fieldLabel : '显示文本',
					maxLength : 125,
					allowBlank : false
				} , {
					xtype : 'container',
					height : 30,
					layout : 'column',
					defaultType : 'textfield',
					items : [{
						xtype : 'label',
						text : '图标:',
						width : 105
					} , {
						columnWidth : .93,
						name : 'iconCls',
						fieldLabel : '图标'
					} , {
						width : 100,
						xtype : 'button',
						iconCls : 'menu-m',
						text : '请选择',
						handler : function(){
							IconSelector.getView(function(text){
								var fm = Ext.getCmp("MenuFormPanel");
								fm.getCmpByName('iconCls').setValue(text);
							}).show();
						}
					}]
				}]
			} , {
				xtype : 'fieldset',
				title : '权限设置',
				layout : 'form',
				autoScroll : true,
				items : [editGrid]
			}]
		}); // end of the formPanel
		
		//加载数据
		if(this.id != null && this.id != 'undefined'){
			this.formPanel.getForm().load({
				deferredRender : true,
				url : __ctxPath+'/menu/getMenu.do',
				params : {
					id : menuId
				},
				waitMsg : '数据正在加载，请稍后...',
				success : function(form, action){ }
			});
		}
	}, // end of this initUIComponent
	
	/**
	 * 数据保存
	 */
	submit : function(){
		var fm = Ext.getCmp('MenuFormPanel');
		var path = fm.getCmpByName('type').getValue() == 'add' ? '/menu/addMenu.do' : '/menu/editMenu.do' ;
		if(fm.getForm().isValid()){
			fm.getForm().submit({
				deferredRender : false,
				url : __ctxPath + path,
				waitMsg : '数据正在提交，请稍后...',
				success : function(){
					Ext.ux.Toast.msg('操作提示','数据操作成功！');
					Ext.getCmp('menuViewTreePanel').root.reload();
					if(curUserInfo.username=='admin')
						loadWestMenu('true');
					else
						Ext.ux.Toast.msg('操作提示','仅对开发用户开放刷新菜单功能!');
					Ext.getCmp('menuFormWin').close();
				},
				failure : function(fp, action){
					var res = Ext.util.JSON.decode(action.response.responseText);
					Ext.ux.Toast.msg('操作提示',res.msg);
				}
			});
		}
	},
	
	/*
	 * 删除权限节点[Function]
	 */
	del : function(id){
		var egrid = Ext.getCmp('MenuFormEditGrid');
		var rows = egrid.getSelectionModel().getSelections();;
		if(rows != null && rows.length > 0){
			var fId = '';
			for(var i = 0 ; i < rows.length ; i++){
				fId += rows[i].data.fId;
				if(i != rows.length - 1)
					fId += ',';
			}
			Ext.Msg.confirm('操作提示','你真的要删除吗？',function(btn){
				if(btn == 'yes'){
					Ext.Ajax.request({
						url : __ctxPath + '/menu/delFnMenu.do',
						params : {
							id : id,
							fId : fId
						},
						method : 'post',
						waitMsg : '数据正在提交，请稍后...',
						success : function(){
							egrid.getStore().reload();
						},
						failure : function(){
							Ext.ux.Toast.msg('操作提示','删除权限信息失败！');
						}
					});
				}
			});
		} else 
			Ext.ux.Toast.msg('操作提示','请选择要删除的数据！');
	},
	
	/*
	 * 添加Function权限
	 */
	addFn : function(menuId,menuText,isChild){
		//判断是否为叶子节点isLeaf
		if(isChild){
			new MenuFunctionForm({
				menuId : menuId,
				menuText : menuText
			}).show();
		} else
			Ext.ux.Toast.msg('操作提示','对不起，该菜单存在子项，不能添加权限！');
	}
});