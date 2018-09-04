DeskRoleForm = Ext.extend(Ext.Window, {
	nodes:'',//保存到数据的字符串
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
	  	DeskRoleForm.superclass.constructor.call(this, {
			id : 'DeskRoleForm',
			layout : 'fit',
			resizable:false,
			bodyStyle:'overflow-y:auto;',
			items : this.deskTopView,
	        frame:true,
			modal : true,
			width : 300,
			height : 450,
			title :'角色['+this.roleName+']个人桌面配置',
			buttonAlign : 'center',
			buttons : [{
				text : '保存',
				iconCls:'btn-save',
				scope:this,
				handler : function() {
					//1.获得树选中节点信息
					var p1=this.deskTopView.root.childNodes;
					var flag=true;
					for(var i=0;i<p1.length;i++){
						//1.父节点
						if(p1[i].attributes.checked){
							this.nodes+=p1[i].attributes.id+",";
							flag=false;
						}
						//2.子节点
						var c1=p1[i].childNodes;
						for(var j=0;j<c1.length;j++){
							if(c1[j].attributes.checked){
								if(flag){
									this.nodes+=p1[i].attributes.id+",";
									flag=false;
								}
								this.nodes+=c1[j].attributes.id+",";
							}
						}
					}
					var desk='TaskPanelView,MessagePanelView'+this.nodes;
					if(this.nodes.charAt(this.nodes.length-1)==','){
						desk=this.nodes.substring(0,this.nodes.length-1);
					}
					desk=desk.replace(' ','');
					var roleId=this.roleId;
					//2.保存到app_role
					Ext.Ajax.request({
						url : __ctxPath + '/system/grantDeskAppUser.do?roleId='+roleId+'&desk='+desk,
						method : 'POST',
						success : function(response, options) {debugger
							Ext.ux.Toast.msg('操作提示','保存成功!');
							Ext.getCmp('DeskRoleForm').close();
							
							//1.保存成功后替换curUserInfo.userDesk
							//如果不是admin
							if(currentUserId!=1){
								curUserInfo.deskRights=desk;
							}
						},
						failure : function(response, options) {
							Ext.ux.Toast.msg('操作信息','保存出错，请联系管理员！');
						}
					});
				}
			}]
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.tbar=new Ext.Toolbar({
			items:[{
				xtype:'button',
				text:'展开',
				iconCls : 'btn-expand',
				scope:this,
				handler : function() {
					this.deskTopView.expandAll();
				}
			},{
				xtype : 'button',
				text : '收起',
				iconCls : 'btn-collapse1',
				scope:this,
				handler : function() {
					this.deskTopView.collapseAll();
				}
			}]
		});
		this.deskTopView = new Ext.tree.TreePanel({
	        animate : true,// 展开动画
	        lines : true,// 节点间的虚线条是否显示
			autoScroll : true,
			rootVisible : true,
			tbar:this.tbar,
			enableDD:true,//允许拖动
			root : new Ext.tree.TreeNode({
				expanded : true,
				text:'桌面种类'
			}),
			listeners:{
				scope:this,
				'afterrender':function(v){
					var roleId=this.roleId;
					Ext.Ajax.request({
						url : __ctxPath + '/system/getAppRole.do?roleId='+roleId,
						method : 'POST',
						success : function(response, options) {
							var alarm= Ext.util.JSON.decode(response.responseText);
							var roleDesk=alarm.data.desks;
							if(initDeskMenus){
								var initDesks=initDeskMenus.replace("{","").replace("}","").split(',');//具有个人桌面的业务信息
								for(var i=0;i<initDesks.length;i++){//循环父节点
									var child={};
									var parentId=initDesks[i].split('=')[0].replace(" ","");//节点id
									var parentName=initDesks[i].split('=')[1];//节点名称
									if(roleDesk){//用户已经配置了个人桌面
										//创建新的节点
										child=new Ext.tree.TreeNode({
											text:parentName,
											id:parentId,
											leaf:false,
											singleClickExpand:true,
											checked:roleDesk.includes(parentId)//判断当前循环的记录是否已被用户选择
										})
										var temp=nodeObj[parentId];
										if(temp){//如果nodeObj对象中有parentId的详细定义
											for(var j=0;j<temp.length;j++){
												var eChild="";
												var childId=temp[j].nodeId;//子节点id
												var childName=temp[j].nodeName;//子节点名称
												
												//判断当前循环的记录是否已被用户选择
												var flag1=childId.indexOf(parentId+"_")!=-1;//判断当前子节点是否属于当前父节点
												var flag2=roleDesk.indexOf(childId)!=-1;//用来控制子节点是否被选中
												var flag3=roleDesk.charAt(roleDesk.indexOf(childId)+(childId.length))==','?true:false;//其后是逗号
												if(!flag3){
													flag3=roleDesk.charAt(roleDesk.indexOf(childId)+(childId.length))==''?true:false;//字符串的尾
												}
												
												if(flag1){//如果属于父节点
													eChild=new Ext.tree.TreeNode({
														text:childName,
														id:childId,
														leaf:true,
														checked:(flag2 && flag3),
														iconCls:"btn-tree-team2"
													});
													if(eChild){
														child.appendChild(eChild);
													}
												}
											}
										}
									}else{
										child=new Ext.tree.TreeNode({
											text:parentName,
											id:parentId,
											leaf:false,
											singleClickExpand:true,
											checked:false
										});
										var temp=nodeObj[parentId];
										if(temp){//如果nodeObj对象中有parentId的详细定义
											for(var j=0;j<temp.length;j++){
												var eChild="";
												var childId=temp[j].nodeId;//子节点id
												var childName=temp[j].nodeName;//子节点名称
												var flag1=childId.indexOf(parentId)!=-1;//当前子节点属于当前父节点
												if(flag1){//如果属于父节点
													eChild=new Ext.tree.TreeNode({
														text:childName,
														id:childId,
														leaf:true,
														checked:false,
														iconCls:"btn-tree-team2"
													});
													if(eChild){
														child.appendChild(eChild);
													}
												}
											}
										}
									}
									v.root.appendChild(child);
								}
							}
						},
						failure : function(response, options) {
							Ext.ux.Toast.msg('操作信息','查询出错，请联系管理员！');
						}
					});
				}
			}
		});
		
		this.deskTopView.on('checkchange', function(v,state) {
			if(v.hasChildNodes()){//如果有子节点
		        for (var j = 0; j < v.childNodes.length; j++) {
		        	v.childNodes[j].getUI().toggleCheck(state);
		        }
		    }
		});
	}
});