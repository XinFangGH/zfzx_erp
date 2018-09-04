Ext.ns('App');

App.TreeLoader = Ext.extend(Ext.ux.tree.XmlTreeLoader, {
			processAttributes : function(attr) {
				if(attr.tagName == 'Function'){
					attr.leaf = true;
				}else if (attr.tagName == 'Item') {
					attr.loaded = true;
					attr.expanded = true;
				} else if (attr.tagName == 'Items') {
					attr.loaded = true;
					attr.expanded = true;
				}
			}
});

/**
 * 角色授权窗口
 * 
 * @param {}
 *            roleId
 */
var RoleGrantRightView = function(roleId,roleName,org_type,roleType,biaos) {

	var roleGrantView = new Ext.ux.tree.CheckTreePanel({
				
				title : '为角色['+roleName+']授权',
				id : 'roleGrantView',
				autoScroll : true,
				rootVisible : false,
				loader : new App.TreeLoader({
							dataUrl : __ctxPath + '/system/grantXmlAppRole.do?biaos='+biaos+'&companyType='+org_type
						}),
				root : new Ext.tree.AsyncTreeNode({
							expanded : true
						}),
				tools : [{
							id : 'refresh',
							qtip : '重新加载树',
							handler : function() {
								roleGrantView.getRootNode().reload();
							}
						}]
			});
			
	roleGrantView.on('load',function(){
		Ext.Ajax.request({
			url : __ctxPath + '/system/getAppRole.do',
			method : 'POST',
		    params:{roleId:roleId},
			success : function(response, options) {
				var object=Ext.util.JSON.decode(response.responseText);
				/*if(roleType=='control'){
					if(object.data.controlRights!=null){
						roleGrantView.setValue(object.data.controlRights);
					}
				}else{*/
					if(object.data.rights!=null){
						roleGrantView.setValue(object.data.rights);
					}
				//}
			},
			failure : function(response, options) {
				Ext.ux.Toast.msg('操作信息','加载权限出错！');
			},
			scope : this
		});	
	});
	
	var tbar=new Ext.Toolbar({items:[
	{
		xtype:'button',
		text:'展开',
		iconCls : 'btn-expand',
		scope:this,
		handler : function() {
			roleGrantView.expandAll();
		}
	},{
			xtype : 'button',
			text : '收起',
			iconCls : 'btn-collapse1',
			scope:this,
			handler : function() {
				roleGrantView.collapseAll();
			}
		}
	]});
	var granWin = new Ext.Window({
				id : 'RoleGrantView',
				title : '角色授权设置',
				width : 600,
				tbar:tbar,
				height : 450,
				modal : true,
				layout : 'fit',
				plain : true,
				maximizable : true,
				bodyStyle : 'padding:5px;',
				buttonAlign : 'center',
				items : [roleGrantView],
				buttons : [
						{
							text : '保存',
							iconCls:'btn-save',
							handler : function() {
								//alert(roleGrantView.getValue().toString());
								Ext.Ajax.request({
									url : __ctxPath + '/system/grantAppRole.do',
									method : 'POST',
									params:{roleId:roleId,rights:roleGrantView.getValue().toString()/*,roleType:roleType*/},
									success : function(response, options) {
										Ext.ux.Toast.msg('操作提示','你已经成功为角色[<b>{0}</b>]进行了授权',roleName);
										granWin.close();
									},
									failure : function(response, options) {
										Ext.ux.Toast.msg('操作信息','授权出错，请联系管理员！');
									},
									scope : this
								});
							}
						},{
							text : '取消',
							iconCls:'btn-cancel',
							handler:function(){
								granWin.close();
							}
						}
				]
			});

	granWin.show();

}