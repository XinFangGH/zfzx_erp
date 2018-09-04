/**
 * 
 * @class UserFormPanel
 * @extends Ext.FormPanel
 * @description 用于窗口的信息编辑管理
 */
UserFormPanel=Ext.extend(Ext.FormPanel,{
	constructor:function(conf){
		Ext.applyIf(this,conf);
		this.userId=this.userId?this.userId:'';
		this.companyId=this.companyId?this.companyId:'';
		this.gridObj=this.gridObj?this.gridObj:null;
		this.initUI();
		UserFormPanel.superclass.constructor.call(this,{
			id:'UserFormPanel_'+this.userId,
			iconCls:'add-user',
			title:(this.username?this.username:'')+'员工信息',
			autoScroll:true,
			width:1000,
			layout:'form',
			tbar:[{
				text:'保存',
				iconCls:'btn-save',
				scope:this,
				handler:this.save
			},''],
			items:[
				this.basePanel,
				//this.posPanel,
				this.depPanel,
				this.rolePanel
			],
			listeners:{
				scope:this,
				'afterrender':function(){
					var store=this.depGridPanel.getStore();
					var recordType=store.recordType;
					store.add(new recordType({
						orgId:this.bmID,
						orgName:this.bmMC,
						isPrimary:store.getCount()>0?0:1
					}));
				}
			}
		});
		
		if(this.userId!=''){
			this.requiredPanel.loadData({
				url:__ctxPath+'/system/loadAppUser.do?userId='+this.userId,
				root:'data',
				preName:'appUser',
				scope:this,
				success:function(resp,options){
					var photo=this.getCmpByName('appUser.photo').getValue();
					var sexValue=this.getCmpByName('appUser.title').getValue();
					if (sexValue == '0') {
						this.photoPanel.body.update('<img src="'+ __ctxPath	+ '/images/default_image_female.jpg"/>');
					} else {
						this.photoPanel.body.update('<img src="'+ __ctxPath+ '/images/default_image_male.jpg"/>');
					}
					if(photo!='' && photo!=null){
						this.photoPanel.body.update('<img src="' + __ctxPath + '/attachFiles/' + photo + '"  width="100%" height="100%"/>');	
					}
				}
			});
			
			this.getTopToolbar().insert(2,{
				text:'重设密码',
				iconCls:'btn-password',
				scope:this,
				handler:function(){
					new setPasswordForm(this.userId);
				}
			});
		}else{
			this.requiredPanel.loadData({
				url:__ctxPath+'/system/loadCountAppUser.do',
				root:'data',
				preName:'appUser',
				scope:this,
				success:function(resp,options){
					var respText = resp.responseText;
					var alarm= Ext.util.JSON.decode(respText);
					var userNumber=this.getCmpByName('appUser.userNumber');
					var value = alarm.size.replace(/[^0-9]/ig,""); 
					var i=Number(value)+1;
					userNumber.setValue(i); 
				}
			});
		}
	},
	//==================functions start===============
	/**
	 * 上传图片
	 */
	uploadPhoto:function(){
		var dialog = App.createUploadDialog({
			file_cat : 'system/appUser',
			scope:this,
			callback : function(data){
				var photo = this.requiredPanel.getCmpByName('appUser.photo');
				var photoId = this.requiredPanel.getCmpByName('appUser.photoId');
				photo.setValue(data[0].filePath);
				photoId.setValue(data[0].fileId);
				this.photoPanel.body.update('<img src="' + __ctxPath + '/attachFiles/' + data[0].filePath + '"  width="100%" height="100%"/>');
			},
			permitted_extensions : ['jpg','png','bmp','gif']
		}).show();
	},
	deleteUserPhoto : function(userId) {
	var photo =  this.requiredPanel.getCmpByName('appUser.photo');
	var photoId =  this.requiredPanel.getCmpByName('appUser.photoId');
	if (photo.value != null && photo.value != '' && photo.value != 'undefined') {
		var msg = '照片一旦删除将不可恢复,';
		Ext.Msg.confirm('确认信息', msg + '是否删除?', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					//url : __ctxPath + '/system/deleteFileAttach.do',
					url : __ctxPath + '/system/deleteFileIdFileAttach.do',
					method : 'post',
					params : {
						//filePath : photo.value
						photoId : photoId.value
					},
					scope:this,
					success : function(response, request) {
						var obj= Ext.util.JSON.decode(response.responseText);
						if(obj.success){
								if (userId != '' && userId != null
									&& userId != 'undefined') {
								Ext.Ajax.request({
									url : __ctxPath + '/system/photoAppUser.do',
									method : 'post',
									params : {
										userId : userId
									},
									scope:this,
									success : function() {
										photo.setValue('');
										var profileTitle =this.requiredPanel.getCmpByName('appUser.title');
										if (profileTitle.value == 1) {
											this.photoPanel.body.update('<img src="' + __ctxPath + '/images/default_image_male.jpg" />');
										} else {
											this.photoPanel.body.update('<img src="' + __ctxPath + '/images/default_image_female.jpg" />');
										}
									}
								});
							} else {
								photo.setValue('');
								var profileTitle =this.requiredPanel.getCmpByName('appUser.title');;
								if (profileTitle.value == 1) {
									this.photoPanel.body.update('<img src="' + __ctxPath + '/images/default_image_male.jpg" />');
								} else {
									this.photoPanel.body.update('<img src="' + __ctxPath + '/images/default_image_female.jpg" />');
								}
							}
						}else{
							Ext.ux.Toast.msg('提示信息', obj.msg);
						}
					}
				});
			}
		},this);
	}// end if
	else {
		Ext.ux.Toast.msg('提示信息', '您还未增加照片.');
	}

},

	/**
	 * 选择Title时，进行图片切换
	 * @param {} combo
	 * @param {} record
	 * @param {} index
	 */
	selectedTitle:function(combo,record,index){		
		if(this.photoPanel.body.html=='<img src="'+ __ctxPath	+ '/images/default_image_female.jpg"/>'||this.photoPanel.body.html=='<img src="'+ __ctxPath	+ '/images/default_image_female.jpg"/>'){
			
		}else{
			if (combo.value == '0') {
				this.photoPanel.body.update('<img src="'+ __ctxPath	+ '/images/default_image_female.jpg"/>');
			} else {
				this.photoPanel.body.update('<img src="'+ __ctxPath+ '/images/default_image_male.jpg"/>');
			}	
		}
	},

	//==================function end==================
	
	//初始化UI
	initUI:function(){
		this.photoPanel = new Ext.Panel({
							tbar : [{
										text : '上传',
										iconCls : 'btn-upload',
										scope:this,
										handler : this.uploadPhoto},
										'-', {
										text : '删除',
										iconCls : 'btn-delete',
										scope:this,
										handler : function() {
											this.deleteUserPhoto(this.userId);
										}
										
								}],
									
							title : '个人照片',
							width : 230,
							height : 380,
							html:'<img src="'+ __ctxPath+ '/images/default_image_male.jpg"/>'
		});
		
		
		
		
		this.requiredPanel=new Ext.Panel({
			title:'基本信息',
			width:400,
			height:380,
			layout:'form',
			labelAlign : "right",
			defaultType:'textfield',
			bodyStyle:'padding:6px',
			defaults : {
				anchor : '98%,98%'
			},
			items:[
							{
								xtype : 'hidden',
								name : 'appUser.userId'
							}, {
								fieldLabel : '登录账号',
								labelwidth:60,
								name : 'appUser.username',
								allowBlank : false
							},  {
								fieldLabel : '员工姓名',
								name : 'appUser.fullname',
								allowBlank : false
							},{
								fieldLabel : '员工编号',
								name : 'appUser.userNumber',
								allowBlank : false
							}, {
								fieldLabel : '性别',
								xtype : 'combo',
								hiddenName : 'appUser.title',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : [['1', '先生'], ['0', '女士']],
								allowBlank : false,
								listeners : {
									scope:this,
									'select':this.selectedTitle
								}
							},{
								fieldLabel : 'E-mail',
								name : 'appUser.email',
								vtype : 'email',
								allowBlank : false,
								blankText : '邮箱不能为空!',
								vtypeText : '邮箱格式不正确!'
							},
							{
								fieldLabel : '入职时间',
								xtype : 'datefield',
								format : 'Y-m-d',
								name : 'appUser.accessionTime',
								allowBlank : false,
								length : 50
							}, {
								fieldLabel : '是否可用',
								hiddenName : 'appUser.status',
								xtype : 'combo',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : [['1', '激活'], ['0', '禁用']],
								value : 1
							},
							{
								fieldLabel : '家庭电话',
								name : 'appUser.phone'
							}, {
								fieldLabel : '移动电话',
								xtype : 'numberfield',
								allowBlank : false,
                    			regex: /^[1][3,4,5,7,8][0-9]{9}$/,
                    			regexText:"请输入正确的手机号",
								name : 'appUser.mobile'
							}, {
								fieldLabel : '传真',
								name : 'appUser.fax'
							}, {
								fieldLabel : '家庭住址',
								name : 'appUser.address'
							}, {
								fieldLabel : '邮编',
								xtype : 'numberfield',
								name : 'appUser.zip'
							}, {
								xtype : 'hidden',
								name : 'appUser.photo'
							}, {
								xtype : 'hidden',
								name : 'appUser.photoId'
							}
			]
		});
		
		if(this.userId==''){
			this.requiredPanel.insert(2,new Ext.form.TextField({
				fieldLabel : '登录密码',
				name : 'appUser.password',
				inputType : 'password',
				allowBlank : false,
				regex : /^[a-zA-Z\d_]{6,18}$/,
				regexText : '6-18位数字或字符'
			}));
			this.requiredPanel.doLayout();
		}
		
		//可选择的部门树
		this.depTreePanel=new zhiwei.ux.TreePanelEditor({
			title:'部门',
			url : __ctxPath+'/system/treeCompanyOrganization.do?demId=1&companyId='+this.companyId+'&onlyShowDepart='+true,//1代表行政维度
			scope:this,
			autoScroll:true,
			hidden:true,
			showContextMenu:false,
			height:250,
			width:230,
			dblclick:this.depTreePanelDblClick
		});
		
		this.depGridPanel=new HT.EditorGridPanel({
			clicksToEdit:1,
			isShowTbar:false,
			showPaging:false,
			title:'已选部门',
            hidden:true,
			width:400,
			height:250,
			url:__ctxPath+'/system/findUserOrg.do?userId='+this.userId,
			fields:['userOrgId','orgId','orgName','isPrimary'],
			columns:[{
					header:'部门名',
					dataIndex:'orgName'
				},
				{
					header:'是否主部门',
					dataIndex:'isPrimary',
					renderer:function(val){
						return val==1 ? '是' : '否';
					},
					editor:{
						id:'orgPrimary',
						xtype : 'combo',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						store : [['1', '是'], ['0', '否']],
						value : 0,
						listeners:{
							scope:this,
							'select':this.setPrimary
						}
					}
				}
			],
			listeners:{
				scope:this,
				'rowdblclick':this.depGridPanelDblClick
			}
		});
		
		//部门选择Panel
		this.depPanel=new HT.HBoxPanel({
			items:[
				this.depTreePanel,
				this.depGridPanel
			]
		});
		
		
		this.roleForSelPanel=new HT.GridPanel({
			width:230,
			height:250,
			isShowTbar:false,
			showPaging:false,
			autoScroll:true,
			title:'可选角色(双击选择)',
			url:__ctxPath+'/system/getRolesAppUser.do?userId='+this.userId+"&companyId="+this.companyId,
			fields:[
				'roleId',
				'roleType',
				'roleName'
			],
			columns:[{
					header:'角色名',
					dataIndex:'roleName'
				}
			],
			listeners:{
				scope:this,
				'rowdblclick':this.roleForSelPanelDblClick
			}
		});
		
	
		this.roleSelectedPanel=new HT.GridPanel({
			width:400,
			height:250,
			isShowTbar:false,
			showPaging:false,
			autoScroll:true,
			title:'已选角色(双击删除)',
			url:__ctxPath+'/system/getSelRolesAppUser.do?userId='+this.userId,
			fields:['roleId','roleName','roleType'],
			columns:[{
					header:'角色名',
					dataIndex:'roleName'
				}
			],
			listeners:{
				scope:this,
				'rowdblclick':this.roleSelectedPanelDblClick
			}
		});
		
		//岗位树Panel
		this.posTreePanel=new zhiwei.ux.TreePanelEditor({
			height:250,
			width:230,
			title:'岗位(双击添加)',
			url : __ctxPath+'/system/treePosition.do',
			scope : this,
			autoScroll:true,
			dblclick:this.posTreePanelDblClick
		});
		
		this.posGridPanel=new HT.EditorGridPanel({
			id:'posGridPanel',
			clicksToEdit:1,
			title:'已有岗位(双击删除)',
			height:250,
			width:400,
			isShowTbar:false,
			showPaging:false,
			url:__ctxPath+'/system/findUserPosition.do?userId='+this.userId,
			fields:['userPosId','posId','posName','isPrimary'],
			columns:[
				{
					header:'岗位名称',
					dataIndex:'posName'
				},
				{
					header:'是否主岗位',
					dataIndex:'isPrimary',
					renderer:function(val){
						return val==1 ? '是':'否';
					},
					editor:{
						id:'posPrimary',
						xtype : 'combo',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						store : [['1', '是'], ['0', '否']],
						value : 0,
						listeners:{
							select:function(curCbo){
								var primaryFlag = false;
								var selRe = this.posGridPanel.getSelectionModel().getSelections();
								var store = this.posGridPanel.getStore();
								var index;
								for(var i=0;i<store.getCount();i++){
									var data = store.getAt(i).data;
									if(data.isPrimary==1&&data.posId!=selRe[0].data.posId){
										primaryFlag = true;
										break;
									}
								}
								if(primaryFlag&&curCbo.value==1){
									Ext.getCmp('posPrimary').setValue(0);
									Ext.ux.Toast.msg('操作信息', '只能有一个主岗位');
								}
								primaryFlag = false;
							},scope:this
						}
					}
					
				}
				],
				listeners:{
					scope:this,
					'rowdblclick':this.posGridPanelDblClick
				}
		});
		
		//角色选择Panel
		this.rolePanel=new HT.HBoxPanel({
			items:[
				this.roleForSelPanel,
				this.roleSelectedPanel
			]	
		});
		
		//职位Panel
		this.posPanel=new HT.HBoxPanel({
			items:[
				this.posTreePanel,
				this.posGridPanel
			]
		});
		
		//创建基本信息的FieldSet
		this.basePanel=new HT.HBoxPanel({
			items:[
				this.photoPanel,
				this.requiredPanel
			]
		});
		
		
	},
	
	//双击已选择的部门
	posGridPanelDblClick:function(grid,rowIndex,e){
		var userPosId=grid.getStore().getAt(rowIndex).data.userPosId;
		if(userPosId){
			Ext.Ajax.request({
				url:__ctxPath+'/system/delUserPosition.do?userPosId='+userPosId,
				method:'POST',
				scope:this,
				success:function(response,options){
					Ext.ux.Toast.msg('操作','成功删除所选择角色！');
					this.posGridPanel.getStore().removeAt(rowIndex);
				}
			});
		}else{
			this.posGridPanel.getStore().removeAt(rowIndex);
		}
	},
	
	//双击职位树
	posTreePanelDblClick:function(node){
		if(node.id==0) return;
		var store=this.posGridPanel.getStore();
		for(var i=0;i<store.getCount();i++){
			if(store.getAt(i).data.posId==node.id){
				return;
			}
		}
		var recordType=store.recordType;
		store.add(new recordType({
			posId:node.id,
			posName:node.text,
			isPrimary:store.getCount()>0?0:1
		}));
	},
	
	//删除已选择的角色
	roleSelectedPanelDblClick:function(grid,rowIndex,e){
		if(this.userId!=''){
			var roleId=grid.getStore().getAt(rowIndex).data.roleId;
			Ext.Ajax.request({
				url:__ctxPath+'/system/delRoleAppUser.do?userId='+this.userId,
				params:{
					roleId:roleId
				},
				method:'POST',
				scope:this,
				success:function(response,options){
					Ext.ux.Toast.msg('操作','成功删除角色！');
					this.roleSelectedPanel.getStore().removeAt(rowIndex);
				}
			});
		}else{
			this.roleSelectedPanel.getStore().removeAt(rowIndex);
		}
	},
	
	//删除已选部门数据
	depGridPanelDblClick:function(grid,rowIndex,e){
		var userOrgId=grid.getStore().getAt(rowIndex).data.userOrgId;
	/*	if(userOrgId){
			Ext.Ajax.request({
				url:__ctxPath+'/system/delUserOrg.do?userOrgId='+userOrgId,
				method:'Post',
				scope:this,
				success:function(response,options){
					Ext.ux.Toast.msg('操作信息','成功删除所属部门');
					this.depGridPanel.getStore().removeAt(rowIndex);
				}
			});
		}else{*/
			this.depGridPanel.getStore().removeAt(rowIndex);
		/*}*/
	},
	//角色选择器单击
	roleForSelPanelDblClick:function(grid,rowIndex,e){
		var store=grid.getStore();
		var record=store.getAt(rowIndex);
		var selStore=this.roleSelectedPanel.getStore();
		for(var i=0;i<selStore.getCount();i++){
			if(selStore.getAt(i).data.roleId==record.data.roleId) return;
		}
		var recordType=selStore.recordType;
		if(record.data.roleType=="control"){
		     var isHave=false;
			 for(var i=0;i<selStore.getCount();i++){
			     
			 	  var r=selStore.getAt(rowIndex);
			 	  if(r.roleType=="control"){
			 	      return false;
			 	  }
			 }
		}
		selStore.add(new recordType(record.data));
	},
	//设置为主部门
	setPrimary:function(combo,record,index){
		
		var primaryFlag = false;
		var selRe = this.depGridPanel.getSelectionModel().getSelections();
		var store = this.depGridPanel.getStore();
		var index;
		for(var i=0;i<store.getCount();i++){
			var data = store.getAt(i).data;
			if(data.isPrimary==1&&data.orgId!=selRe[0].data.orgId){
				primaryFlag = true;
				break;
			}
		}
		if(primaryFlag&&combo.value==1){
			Ext.getCmp('orgPrimary').setValue(0);
			Ext.ux.Toast.msg('操作信息', '只能有一个主岗位');
			primaryFlag = false;
			return;
		}
		
//		var isPrimary=record.data.field1;
//		if(isPrimary==1){
//			for(var i=0;i<store.getCount();i++){
//				var record=store.getAt(i);
//				record.data['field0']=0;
//				record.commit();
//			}
//			this.depGridPanel.stopEditing();
//		}
	},
	//部门树的双击
	depTreePanelDblClick:function(node){
		if(node.id==0) return;
		var store=this.depGridPanel.getStore();
		var nodeId=node.id;
		var orgType=node.attributes.orgType;//点击的组织类型 只有点击部门才可以添加
		if(orgType==2){//门店不能当做部门使用
			for(var i=0;i<store.getCount();i++){
				if(store.getAt(i).data.orgId==nodeId){
					return;
				}
			}
			var recordType=store.recordType;
			store.add(new recordType({
				orgId:node.id,
				orgName:node.text,
				isPrimary:store.getCount()>0?0:1
			}));
		}else{
			Ext.ux.Toast.msg('操作信息', '只能选择部门类型!');
			return false;
		}
		
	},
	//保存
	save:function(){
		if(this.userId==''){
			var reg = /^[a-zA-Z\d_]{6,18}$/;
	        var flag = reg.test(this.getCmpByName('appUser.password').getValue());
	        if (!flag) {
					this.getCmpByName('appUser.password').setValue(null);
					Ext.ux.Toast.msg('操作信息', '密码为6-18位数字或字符,请重新输入');
			        return;
					
					}
		
		
		}
		//取得选择的角色
		var roleIds='';
		
		//取得选择的部门
		var orgIds=[];
		 //取得选择的职位
		var posIds=[];
		
		for(var i=0;i<this.roleSelectedPanel.getStore().getCount();i++){
			if(i>0) roleIds+=',';
			roleIds+=this.roleSelectedPanel.getStore().getAt(i).data.roleId;
		}
		
		var primaryOrgFlag = false;
		for(var i=0;i<this.depGridPanel.getStore().getCount();i++){
			var data = this.depGridPanel.getStore().getAt(i).data
			orgIds.push(data);
			if(data.isPrimary==1){
				primaryOrgFlag = true;
			}
		}
		if(orgIds.length==0||!primaryOrgFlag){
			Ext.ux.Toast.msg('操作信息', '必须选择一个主部门');
			return;
		}
		
		for(var i=0;i<this.posGridPanel.getStore().getCount();i++){
			posIds.push(this.posGridPanel.getStore().getAt(i).data);
		}
        var userGrid=this.gridObj;
        var companyId=this.companyId;
		$postForm({
			url:__ctxPath+'/system/saveOrUpdateAppUser.do',
			formPanel:this,
			params:{
				roleIds:roleIds,
				companyId:companyId,
				orgIds:Ext.encode(orgIds),
				posIds:Ext.encode(posIds)
			},
			scope:this,
			isShow:false,
			callback:function(fp,action){
				if(action.result.success==true){
					App.getContentPanel().remove(this,true);
					if(null!=userGrid){
					    userGrid.getStore().reload();
					 }else{
					 Ext.getCmp('AppUserGrid').getStore().reload();}
				}else{
					Ext.ux.Toast.msg('操作信息', action.result.msg);
				}
			}
		});
	}
});