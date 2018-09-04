Ext.ns("ProDefinitionSetting");

ProDefinitionSetting=Ext.extend(Ext.Panel,{
	
	constructor:function(conf){
		Ext.applyIf(this,conf);
		this.initUIs();
		ProDefinitionSetting.superclass.constructor.call(this,{
			id : 'ProDefinitionSetting'+this.defId,
	 		title : '流程设置－'+this.name,
	 		layout : 'form',
	 		autoScroll : true,
	 		border : false,
	 		iconCls : 'btn-setting',
	 		items:[
	 			this.gridPanel,
	 			this.flowImagePanel,
	 			this.formSetPanel
	 		]
		});
	},
	initUIs:function(){
			this.gridPanel=this.usersPanel(this.defId);
			//流程图panel
			this.flowImagePanel=new Ext.Panel({
		 		title : '流程干预设置',
		 		layout : 'form',
		 		width : '100%',
		 		split : true,
		 		border : false,
		 		region : 'center',
		 		//collapsed : true,
		 		autoScroll : true,
		 		collapsible : true,
		 		margin : '5 5 5 5',
		 		autoLoad : {
		 			nocache: true,
		 			url : __ctxPath+'/flow/processImage.do?defId='+this.defId
		 		}
	 		});
			
			this.useTemplateCheckbox=new Ext.form.Checkbox({
				boxLabel:'使用表单模板',
				  scope:this,
				  handler:this.setExtTemplate
			});
			
	 		this.formSetPanel=new Ext.Panel({
	 			title:'表单设置',
	 			border:false,
	 			autoScroll:true,
	 			layout:'form',
	 			tbar : [
			 				this.useTemplateCheckbox,'-',{
							text : '设置流程表单',
							iconCls : 'btn-setting',
							scope : this,
							handler : this.setting.createCallback(this)
		 				  },{
		 				     text:'设置表单字段权限',
		 				     scope:this,
		 				     iconCls:'btn-setting',
		 				     handler:this.setRights
		 				  }
		 		],
	 			items:[
	 				//this.formPanel
	 			]
	 		});
	 	
	 	//通过配置查看表单使用的是什么模板
	 	Ext.Ajax.request({
	 		url:__ctxPath+'/flow/formTempProDefinition.do',
	 		params:{defId:this.defId},
	 		scope:this,
	 		success:function(resp,options){
	 			var result=Ext.decode(resp.responseText);
	 			this.mappingId=result.mappingId;
	 			this.formSetPanel.removeAll();
	 			if(result!=null && result.isUseTemplate==1){//使用Ext模板
	 				this.useTemplateCheckbox.setValue(true);
	 			}else{
	 				this.formSetPanel.add(this.getFormPanel.call(this));
	 			}
	 			this.formSetPanel.doLayout();
	 		},
	 		failure:function(resp,options){
	 		}
	 	});
	 		
	 		
	},//end of initUIs
	
	getFormPanel:function(){
		//表单设置panel
		this.formPanel=new Ext.FormPanel({
			autoLoad : {
				url : __ctxPath+'/flow/getProcessActivity.do?defId='+this.defId,
				scope:this,
				callback:this.getFormHtmlCallback
			}
	    });	
	   return this.formPanel;
	},
	
	/**
	 * 构造按模板设置的表单面板，方便为流程的每个任务设置表单
	 */
	getFormTemplateGrid:function(){
		
		this.formTempGridPanel = new HT.EditorGridPanel({
			clicksToEdit:1,
			autoHeight:true,
			showPaging:false,
			rowActions:true,
			isShowTbar:false,
			url : __ctxPath + "/flow/mappingsFormTemplate.do?mappingId=" + this.mappingId,
			fields : ['templateId', 'mappingId','nodeName','formUrl','tempType'],
			columns : [{
						header : 'templateId',
						dataIndex : 'templateId',
						hidden : true
					},{
						header:'表单名称',
						dataIndex:'nodeName',
						width:300
					},{
						header:'模板类型',
						dataIndex:'tempType',
						width:100,
						renderer:function(val){
							if(val==2){
								return 'URL模板';
							}else{
								return 'EXT模板';
							}
						},
						editor:new Ext.form.ComboBox({
								valueField : 'id',
								displayField : 'name',
								store : [['1','EXT模板'],['2','URL模板']],
								triggerAction : 'all',
								editable : false
						})
					},{
						header:'URL',
						dataIndex:'formUrl',
						width:250,
						editor:new Ext.form.TextField()
					},
					new Ext.ux.grid.RowActions({
						header : '管理',
						width : 100,
						actions : [{
									iconCls : 'btn-form-design',	
									qtip : '设计表单',
									style : 'margin:0 3px 0 3px',
									fn:function(rs){
										if(rs.data.templateId!=null){
											return true;
										}
										return false;
									}
								}, {
									iconCls : 'btn-form-tag',
									qtip : '设置表单源码',
									style : 'margin:0 3px 0 3px',
									fn:function(rs){
										if(rs.data.templateId!=null){
											return true;
										}
										return false;
									}
								}],
						listeners : {
							scope : this,
							'action' : this.onRowAction
						}
					})]
		});
		return this.formTempGridPanel;
	},
	
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-form-design' :
				this.taskFormDesigner.call(this, record);
				break;
			case 'btn-form-tag' :
				this.vmEditor.call(this, record);
				break;
			default :
				break;
		}
	},
	//流程表单设计器
	taskFormDesigner:function(record){
		var designWin=new FormDesignWindow(
	 		{
	 			defId:this.defId,
	 			templateId:record.data.templateId,
	 			activityName:record.data.nodeName
	 		}
	 	);
	 	designWin.show();
	},
	
	vmEditor:function(record){
		var vmEditorWin=new FormEditorWindow({
	 		defId:this.defId,
	 		activityName:record.data.nodeName
	 	});
	 	vmEditorWin.show();
	},
	/**
	 * 使用Ext模板以支持
	 */
	setExtTemplate:function(ck,checked){
		var items=this.formSetPanel.getTopToolbar().items;
		for(var i=0;i<items.getCount();i++){
			if(i>0){
				var it=items.get(i);
				if(checked && it.hide){
					it.hide();
				}else if(it.show){
						it.show();
				}
			}
		}
		
		//通过defId取得deployId及version，从而映射
		Ext.Ajax.request({
			url:__ctxPath+'/flow/saveFmProDefinition.do?defId='+this.defId,
			params:{
				useTemplate:checked
			},
			scope:this,
			success:function(resp,options){
				var result=Ext.decode(resp.responseText);
				this.mappingId=result.mappingId;
				this.formSetPanel.removeAll();
				if(checked){
					this.saveTemplateBtn=new Ext.Button({
						iconCls:'btn-save',
						text:'保存模板设置',
						scope:this,
						handler:this.saveFormTemplate
					});
					this.formSetPanel.getTopToolbar().insert(1,this.saveTemplateBtn);
					this.formSetPanel.add(this.getFormTemplateGrid.call(this));
				}else{
					if(this.saveTemplateBtn){
						this.formSetPanel.getTopToolbar().remove(this.saveTemplateBtn);
					}
					this.formSetPanel.add(this.getFormPanel.call(this));
				}
				this.formSetPanel.doLayout();
			}
		});
	},
	/**
	 * 保存模表单模板
	 */
	saveFormTemplate:function(){
		
		var store=this.formTempGridPanel.getStore();
		var formTemps=[];
		for(var i=0;i<store.getCount();i++){
			formTemps.push(store.getAt(i).data);
		}
		
		//保存表单模板
		Ext.Ajax.request({
			url:__ctxPath+'/flow/saveListFormTemplate.do',
			method:'post',
			params:{
				formTemps:Ext.encode(formTemps)
			},
			scope:this,
			success:function(resp,options){
				store.commitChanges();
				Ext.ux.Toast.msg('操作信息','成功保存表单设置！');	
			}
		});
	},
    setRights:function(){
    	var defId=this.defId;
    	Ext.Ajax.request({
    	    url:__ctxPath+'/flow/checkFieldRights.do',
    	    method:'post',
    	    params:{defId:defId},
    	    success:function(response,op){
    	       var res=Ext.util.JSON.decode(response.responseText);
    	       if(res.success){
	    	       new FieldRightsForm({
			    	   defId:defId
			       }).show();
    	       }else{
    	           Ext.ux.Toast.msg('操作提示',res.msg == null ? '未绑定表单！' : res.msg);
    	       }
    	    },
    	    failure:function(){}
    	
    	});

    },
	/**
	 * 表单设置
	 */
	setting : function(obj){
		
		FormDefSelector.getView(function(formDefId,formTitle){
			if(formDefId != null){
				obj.save(obj,obj.defId,formDefId);
			}
		},obj.defId).show();

	},
	//添加操作【设置表单数据】
    save:function(obj,defId,formDefId){
		Ext.Ajax.request({
			url : __ctxPath + '/flow/addFormDef.do?defId='+defId+'&formDefId='+formDefId,
			method : 'post',
			waitMsg : '数据正在提交，请稍后...',
			success : function(response,options){
				var res = Ext.util.JSON.decode(response.responseText);
				if(res.success){
					Ext.ux.Toast.msg('操作提示','设置流程表单操作成功！');
					var panel=obj.formPanel;
					panel.getUpdater().update({url:__ctxPath+'/flow/getProcessActivity.do?defId='+defId,
					                           scope:obj,
						                       callback:obj.getFormHtmlCallback});
				}else
					Ext.ux.Toast.msg('操作提示',res.msg);
			}
		}
	);
   },
   getFormHtmlCallback:function(){
        var form=this.formPanel.getForm().getEl().dom;
		var formPanel=this.formPanel;
		var fElements = form.elements || (document.forms[form] || Ext.getDom(form)).elements;
		try{
			 $converDetail.call(this,null);
		}catch(e){
			//alert(e);
		}
   }
});//end of class extend


/**
  * 人员设置
  * @param defId
  * @return
  */
 ProDefinitionSetting.prototype.usersPanel=function(defId){
 	var toolbar = new Ext.Toolbar({
		items : [{
			text : '保存',
			iconCls : 'btn-save',
			handler : function() {
				var params = [];
				for (i = 0, cnt = store.getCount(); i < cnt; i += 1) {
					var record = store.getAt(i);
					if(record.data.assignId=='' || record.data.assignId==null){//设置未保存的assignId标记，方便服务端进行gson转化
						record.set('assignId',-1);
					}
					if(record.data.isSigned=='' || record.data.isSigned==null){
						record.set('isSigned',0);
					}
					if(record.data.timeLimitType=='' || record.data.timeLimitType==null || record.data.timeLimitType!=2){
						record.set('timeLimitType',1);//默认为工作日
					}
					if(record.data.taskTimeLimit=='' || record.data.taskTimeLimit==null){
						record.set('taskTimeLimit',3);//默认节点处理时限为3天。
					}
					if(record.data.isJumpToTargetTask!=0||record.data.isJumpToTargetTask!='0'){
						record.set('isJumpToTargetTask',-1);//默认设置值
					}
					if(record.data.isProjectChange!=0||record.data.isProjectChange!='0'){
						record.set('isProjectChange',-1);//默认设置值
					}
					if(record.data.taskSequence=='' || record.data.taskSequence==null){
						record.set('taskSequence',0);//默认节点顺序为0。相当于第一个节点。
					}
					if (record.dirty) // 得到所有修改过的数据
						params.push(record.data);
				}
				if (params.length == 0) {
					Ext.ux.Toast.msg('信息', '没有对数据进行任何更改');
					return;
				}
				Ext.Ajax.request({
					method : 'post',url : __ctxPath+ '/flow/saveProUserAssign.do',
					success : function(request) {
						Ext.ux.Toast.msg('操作信息', '成功设置流程表单');
						store.reload();
						grid.getView().refresh();
					},
					failure : function(request) {
						Ext.MessageBox.show({
							title : '操作信息',
							msg : '信息保存出错，请联系管理员！',
							buttons : Ext.MessageBox.OK,
							icon : 'ext-mb-error'
						});
					},
					params : {
						data : Ext.encode(params)
					}
				});
			}
		}]
	});
	
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/flow/listProUserAssign.do?defId='+this.defId
						}),
				reader : new Ext.data.JsonReader({
					root : 'result',
					id : 'id',
					fields : ['assignId', 'deployId', 'activityName','reJobId','reJobName',{name:'posUserFlag',type:'int'},
							'userId','username', 'roleId','roleName','depPosIds','depPosNames','jobId','jobName',
							'depIds','depNames','isSigned','timeLimitType','taskTimeLimit','isJumpToTargetTask','taskSequence','isProjectChange'
							,'isseparate','isReSetNext']
				})
			}); 
     
     store.load();
	
    var row = 0;
	var col = 0;
	//用户的行选择器
	var userEditor = new Ext.form.TriggerField({
		triggerClass : 'x-form-browse-trigger',
		editable:false,
		onTriggerClick : function(e) {
			var store = grid.getStore();
			var record = store.getAt(row);
			new UserDialog({
				scope:this,
				single:false,
				isForFlow:true,
				type : 'all',
				userIds:record.get('userId'),
				userName:record.get('username'),
				callback:function(ids,names){
					record.set('userId', ids);
					record.set('username', names);
				}
			}).show();
			grid.stopEditing();
		}
	});
	
	var roleEditor=new Ext.form.TriggerField({
		triggerClass : 'x-form-browse-trigger',
		editable:false,
		onTriggerClick : function(e) {
			var store = grid.getStore();
			var record = store.getAt(row);
			new RoleDialog({
				scope:this,
				single:false,
				roleIds:record.get('roleId'),
				roleName:record.get('roleName'),
				callback:function(ids,names){
					record.set('roleId', ids);
					record.set('roleName', names);
				}
			}).show();
			grid.stopEditing();
		}
	});
	
	//是否按门店分离下拉选项
	var isseparateCombo = new Ext.form.ComboBox({
				mode : 'local',
				displayField : 'name',
				valueField : 'id',
				editable : false,
				triggerAction : 'all',
				width : 70,
				store : new Ext.data.SimpleStore({
					fields : ["name", "id"],
					data : [["否", "0"],["是", "1"]]
				})
	});
	
	//相对岗位选择器
	var reJobEditor = new Ext.form.TriggerField({
		triggerClass : 'x-form-browse-trigger',
		editable:false,
		onTriggerClick : function(e){
			var store = grid.getStore();
			var record = store.getAt(row);
			new ReJobDialog({
				scope:this,
				single:false,
				reJobId:record.get('reJobId'),
				reJobName:record.get('reJobName'),
				posUserFlag: record.get('posUserFlag')==null||record.get('posUserFlag')==''?0:record.get('posUserFlag'),
				callback:function(reJobId, reJobName, posUserFlag){
					record.set('reJobId',reJobId);
					record.set('reJobName',reJobName);
					record.set('posUserFlag',posUserFlag);
				}
			}).show();
			grid.stopEditing();
		}
	}); // end of this reJobEditor+
	
	//职位选择器
	var jobEditor = new Ext.form.TriggerField({
		triggerClass : 'x-form-browse-trigger',
		editable:false,
		onTriggerClick : function(e){
			var store = grid.getStore();
			var record = store.getAt(row);
			new PositionDialog({
				scope:this,
				single:false,
				sameLevelIds:record.get('jobId'),
				sameLevelNames: record.get('jobName'),
				callback:function(ids,names){
					record.set('jobId',ids);
					record.set('jobName',names);
				}
			}).show();
			grid.stopEditing();
		}
	}); // end of this jobEditor
	
	// 部门选择
	var depEditor = new Ext.form.TriggerField({
		triggerClass : 'x-form-browse-trigger',
		editable:false,
		onTriggerClick : function(e){
			var store = grid.getStore();
			var record = store.getAt(row);
			DepSelector.getView(function(ids, names) {
				record.set('depIds',ids);
				record.set('depNames',names);
			}, false,true,record.get('depIds'),record.get('depNames')).show();
			grid.stopEditing();
		}
	}); 
	
	//部门职位选择器
	var depPosEditor = new Ext.form.TriggerField({
		triggerClass : 'x-form-browse-trigger',
		editable:false,
		onTriggerClick : function(e){
			var store = grid.getStore();
			var record = store.getAt(row);
			new PositionDialog({
				scope:this,
				single:false,
				sameLevelIds:record.get('depPosIds'),
				sameLevelNames: record.get('depPosNames'),
				callback:function(ids,names){
					record.set('depPosIds',ids);
					record.set('depPosNames',names);
				}
			}).show();
			grid.stopEditing();
		}
	}); 
	
	//人员设置
	var grid = new Ext.grid.EditorGridPanel({
				title:'人员设置',
				id:'ProDefinitionSettingGrid'+this.defId,
				autoHeight:true,
				clicksToEdit:1,
				store : store,
				tbar : toolbar,
				columns : [new Ext.grid.RowNumberer(),{
							header : "assignId",
							dataIndex : 'assignId',
							hidden : true
						}, {
							header : "deployId",
							dataIndex : 'deployId',
							hidden : true
						}, {
							header : "流程任务",
							dataIndex : 'activityName',
							width : 100,
							sortable : true
						}, {
							dataIndex:'userId',
							header:'userId',
							hidden : true
						},{
							header : "用户",
							dataIndex : 'username',
							width : 150,
							sortable : true,
							readOnly: true,
							editor: new Ext.grid.GridEditor(userEditor.show())
						}, {
							dataIndex : 'roleId',
							hidden:true
						}, {
							header : '角色',
							dataIndex:'roleName',
							width:150,
							editor:roleEditor
						}, {
							header : '是否按门店分离',
							dataIndex : 'isseparate',
							width : 150,
							editor : isseparateCombo,
							renderer:ZW.ux.comboBoxRenderer(isseparateCombo)
						}, {
							header : '是否指派下一节点处理人',
							dataIndex : 'isReSetNext',
							width : 200,
							editor : isseparateCombo,
							renderer:ZW.ux.comboBoxRenderer(isseparateCombo)
						},/*{
							header : '岗位',
							dataIndex : 'jobName',
							width : 150,
							//hidden : true,
							editor : jobEditor ///添加
						},{
							header : 'jobId',
							dataIndex : 'jobId',
							hidden : true
						},{
							header : '部门岗位',
							dataIndex : 'depPosNames',
							width : 150,
							//hidden : true,
							editor : depPosEditor
						},{
							header : 'depPosIds',
							dataIndex : 'depPosIds',
							hidden : true
						},*/{
							header : '上下级',
							id:'reJobName',
							dataIndex : 'reJobName',
							width : 150,
							//hidden : true,
							editor : reJobEditor //添加
						}, {
							header : 'reJobId',
							id:'reJobId',
							dataIndex : 'reJobId',
							hidden : true
						}, {
							header : 'posUserFlag',
							dataIndex : 'posUserFlag',
							hidden : true
						},/*{
							header : '部门负责人',
							dataIndex : 'depNames',
							//hidden : true,
							renderer: function(value){
								if(value!=""){value += " 负责人";}
								return value;
							},
							width : 150,
							editor : depEditor //添加
						}, {
							header : 'depIds',
							dataIndex : 'depIds',
							hidden : true
						},*/{
							header : '是否会签',
							dataIndex : 'isSigned',
							width : 100,
							renderer : function(value){
								//return value == 1 ? '是':'否';
								if(value==1){
									return '<font color=green>是</font>';
								}else{
									return '否';
								}
							},
							editor : new Ext.form.ComboBox({
								valueField : 'id',
								displayField : 'name',
								store : [['0','否'],['1','是']],
								triggerAction : 'all',
								editable : false
							})
						}, {
							header : '会签设置',
							dataIndex : 'isSigned',
							width : 120,
							renderer : function(value,metadata,record,rowIndex,colIndex){
								var assignId = record.get('assignId');
								var av = record.get('activityName');
								return value == 1 ? '<button class="btn-setting" title ="会签设置" onclick="ProDefinitionSetting.setTaskAssign('+assignId+',\''+av+'\')"/>':'';
							}
						},{
							header : '节点顺序设置',
							dataIndex : 'taskSequence',
							width : 80,
							editor :{
								xtype :'textfield'
							},
							renderer : function(value){
								if(value==0||value==''){
									return 0;
								}else{
									return value;
								}
							}
						},{
							header : '时限类型',
							dataIndex : 'timeLimitType',
							width : 80,
							renderer : function(value){
								if(value==2){
									return '<font color=green>正常日</font>';
								}else{
									return '<font color=blue>工作日</font>';
								}
							},
							editor : new Ext.form.ComboBox({
								valueField : 'id',
								displayField : 'name',
								store : [['1','工作日'],['2','正常日']],
								triggerAction : 'all',
								editable : false
							})
						},{
							header : '任务时限',
							dataIndex : 'taskTimeLimit',
							width : 80,
							editor :{
								xtype :'numberfield'
							},
							renderer : function(value){
								if(value==0){
									return '3 天';
								}else{
									return value+' 天';
								}
							}
						},{
							header : '是否允许跳转至该节点',
							dataIndex : 'isJumpToTargetTask',
							width : 150,
							renderer : function(value){
								if(value!=0||value!='0'){
									return '<font color=green>是</font>';
								}else{
									return '否';
								}
							},
							editor : new Ext.form.ComboBox({
								valueField : 'id',
								displayField : 'name',
								store : [['0','否'],['1','是']],
								triggerAction : 'all',
								editable : false
							})
						},{
							header : '是否允许项目换人',
							dataIndex : 'isProjectChange',
							width : 100,
							renderer : function(value){
								if(value!=0||value!='0'){
									return '<font color=green>是</font>';
								}else{
									return '否';
								}
							},
							editor : new Ext.form.ComboBox({
								valueField : 'id',
								displayField : 'name',
								store : [['0','否'],['1','是']],
								triggerAction : 'all',
								editable : false
							})
						}]
			});
			grid.on('cellclick', function(grid,rowIndex,columnIndex,e) {
				row=rowIndex;
			});		 
	return grid;
 };  // end of this getRightNorthPanel[人员设置]
 


/**
 * 点击流程图上的任务节点
 * @param {} defId
 * @param {} activityName
 */
ProDefinitionSetting.clickNode=function(defId,activityName,nodeType){
	$ImportSimpleJs([__ctxPath+'/js/flow/ProcessNodeSetting.js'],function(){
		new ProcessNodeSetting({
			defId:defId,
			activityName:activityName,
			nodeType:nodeType
		}).show();
	},this);
	
};

 
 /**
  * 角色选择
  * @param {} rowIndex
  * @param {} colIndex
  * @param {} defId
  */
 ProDefinitionSetting.roleSelect=function(rowIndex,colIndex,defId){
 	var grid=Ext.getCmp("ProDefinitionSettingGrid"+defId);
 	var record=grid.getStore().getAt(rowIndex);
 	grid.getStore().reload();
 	grid.doLayout();
 };

 
/**
 * 设置任务标签
 * @param assignId 流程人员设置id
 * @param assignName 任务名称
 */
 ProDefinitionSetting.setTaskAssign = function(assignId,av){
	 new TaskSignForm({
		 assignId : assignId,
		 activityName : av
		}).show();
 };
 


