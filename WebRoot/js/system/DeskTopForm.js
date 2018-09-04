DeskTopForm = Ext.extend(Ext.Window, {
	nodes:'',//保存到数据的字符串
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
	  	DeskTopForm.superclass.constructor.call(this, {
			id : 'DeskTopForm',
			layout : 'fit',
			resizable:false,
			bodyStyle:'overflow-y:auto;',
			items : this.deskTopView,
	        frame:true,
			modal : true,
			width : 300,
			height : 450,
			title :'['+currentUserFullName+']个人桌面偏好配置',
			buttonAlign : 'center',
			buttons : [{
				text : '保存',
				iconCls:'btn-save',
				scope:this,
				handler : function() {
					//1.获得树选中节点信息
					var p1=this.deskTopView.root.childNodes;
					for(var i=0;i<p1.length;i++){
						var flag=true;
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
					var desk=this.nodes;
					if(this.nodes.charAt(this.nodes.length-1)==','){
						desk=this.nodes.substring(0,this.nodes.length-1);
					}
					desk=desk.replace(' ','');
					//2.保存到app_user
					Ext.Ajax.request({
						url : __ctxPath + '/system/grantDeskAppUser.do',
						method : 'POST',
						params:{userId:currentUserId,desk:desk},
						success : function(response, options) {
							Ext.ux.Toast.msg('操作提示','您已经成功进行了桌面配置!');
							Ext.getCmp('DeskTopForm').close();
							
							//1.保存成功后替换curUserInfo.userDesk
							curUserInfo.userDesk=desk;
							//2.如果用户已经配置了属于自己的个人桌面   或者   用户当前角色配置了个人桌面 
					    	changeDesk();
						},
						failure : function(response, options) {
							Ext.ux.Toast.msg('操作信息','配置出错，请联系管理员！');
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
			oneCheckModel:true,
			root : new Ext.tree.TreeNode({
				expanded : true,
				text:'可选择桌面'
			}),
			listeners:{
				scope:this,
				'afterrender':function(v){
					var tempUserDesk=curUserInfo.userDesk;
					//用户单独配置属于自己的个人桌面时授权数只能加载当前用户角色下的桌面功能
					if(initDeskMenus){
						var initDesks=initDeskMenus.replace("{","").replace("}","").split(',');//具有个人桌面的业务信息。例如:[team:'贷款业务',factoring:'保理业务']
						var arr=[];//重新定义数组对象()
						for(var i=0;i<initDesks.length;i++){//循环判断桌面角色里有没有,如果有则arr追加
							if(curUserInfo.rights.indexOf('__ALL')!=-1){
								arr.push(initDesks[i]);
							}else{
								if(curUserInfo.deskRights){
									var deskList=curUserInfo.deskRights.split(",");
									if(deskList.includes(initDesks[i].split('=')[0].trim())){
										arr.push(initDesks[i]);
									}
								}
							}
						}
						for(var i=0;i<arr.length;i++){//循环arr父节点数组
							var child={};
							var parentId=arr[i].split('=')[0].replace(" ","");//节点id
							var parentName=arr[i].split('=')[1];//节点名称
							var checked=false;
							if(curUserInfo.userDesk){
								checked=curUserInfo.userDesk.split(",").includes(parentId);

							}
							//创建新的节点
							child=new Ext.tree.TreeNode({
								text:parentName,
								id:parentId,
								leaf:true,
								singleClickExpand:true,
								checked:curUserInfo.userDesk.split(",").includes(parentId)
							})
							v.root.appendChild(child);
						}
					}
				}
			}
		});
		
	
	}
});