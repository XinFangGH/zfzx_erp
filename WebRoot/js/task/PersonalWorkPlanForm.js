/**
 *@description 个人工作计划表单 
 */
PersonalWorkPlanForm=Ext.extend(Ext.Window,{
       formPanel : null,
       buttons : null,
       constructor : function(_cfg){
           Ext.applyIf(this,_cfg);
           this.initUI();
           PersonalWorkPlanForm.superclass.constructor.call(this,{
                id : 'PersonalWorkPlanWin',
                layout : 'fit',
                iconCls : 'menu-myplan',
                title : '我的计划详细信息',
				items : this.formPanel,
				modal : true,
				width : 770,
				minWidth : 700,
				maximizable : true,
				buttonAlign : 'center',
				buttons : this.buttons,
				keys : {
        	   		key : Ext.EventObject.ENTER,
        	   		scope : this,
        	   		fn : this.saveRecord
           		}
           });
       },
       initUI:function(){
            this.formPanel = new HT.FormPanel({
				url : __ctxPath + '/task/saveWorkPlan.do',
				autoHeight : true,
				frame : false,
				border : false,
				layout : 'form',
				region : 'center',
				id : 'NewWorkPlanFormPanel',
				bodyStyle : 'padding-left:8%;padding-bottom:20px;',		
				formId : 'NewWorkPlanFormId',
				items : [{
							name : 'workPlan.planId',
							xtype : 'hidden',
							value : this.planId == null ? '' : this.planId
						},{
						    name:'workPlan.isPersonal',
						    value:1,
						    xtype:'hidden'
						} ,{
						xtype:'panel',
						id:'PersonalWorkPlanFormPanel',
						layout:'form',
						border:false,
						width:680,
						defaultType : 'textfield',
						bodyStyle:'padding-top:10px',
						defaults:{border:false},
						items:[{
							fieldLabel : '计划类型',
							hiddenName : 'workPlan.proTypeId',
							xtype : 'combo',
							width : 520,
							editable : false,
							allowBlank:false,
							triggerAction : 'all',
							displayField : 'name',
							valueField : 'id',
							mode : 'local',
							store : new Ext.data.SimpleStore({
										autoLoad : true,
										baseParams:{catKey:'PWP'},
										url : __ctxPath + '/system/comboGlobalType.do',
										fields : ['id', 'name']
									})
						},{
							fieldLabel : '计划名称',
							name : 'workPlan.planName',
							width : 520,
							allowBlank:false
						}, {
							xtype : 'container',
							border : false,
							layout : 'hbox',
							layoutConfig : {
								padding : '0',
								align : 'middle'
							},
							defaults : {
								xtype : 'label',
								margins : {
									top : 0,
									right : 4,
									bottom : 4,
									left : 0
								}
							},
							width : 650,
							items : [{
										text : '时间范围:',
										width : 101,
										name:'display',
										style : 'padding-left:0px;padding-top:3px;'
									}, {
										xtype : 'datetimefield',
										width : 250,
										format : 'Y-m-d H:i:s',
										allowBlank:false,
										editable : false,
										name : 'workPlan.startTime'
									}, {
										text : '至',
										style : 'padding-left:0px;padding-top:3px;',
										width:8
									}, {
										xtype : 'datetimefield',
										width : 250,
										format : 'Y-m-d H:i:s',
										allowBlank:false,
										editable : false,
										name : 'workPlan.endTime'
									}
									]
						}, {
							fieldLabel : '计划内容',
							name : 'workPlan.planContent',
							xtype : 'htmleditor',
							height : 180,
							width : 520,
							allowBlank:false
						}, {
							layout : 'column',
							style : 'padding-left:0px;',
							width : 670,
							border:false,
							xtype : 'container',
							items : [{
										columnWidth : .82,
										border:false,
										style : 'padding-left:0px;',
										layout : 'form',
										items : [{
													fieldLabel : '附件',
													xtype : 'panel',
													frame:false,
													id : 'planFilePanel',
													height : 50,
													autoScroll : true,
													html : ''
												}]
									}, {
										columnWidth : .18,
										border:false,
										items : [{
											iconCls : 'menu-attachment',
											xtype : 'button',
											text : '添加附件',
											handler : function() {
												var dialog = App.createUploadDialog({
													file_cat : 'task/plan/personalWorkPlan',
													callback : function(data) {
														var fileIds = Ext.getCmp("planFileIds");
														var filePanel = Ext.getCmp('planFilePanel');
		
														for (var i = 0; i < data.length; i++) {
															if (fileIds.getValue() != '') {
																fileIds.setValue(fileIds.getValue() + ',');
															}
															fileIds.setValue(fileIds.getValue() + data[i].fileId);
															Ext.DomHelper.append(filePanel.body,
																'<span><a href="#" onclick="FileAttachDetail.show('
																		+ data[i].fileId
																		+ ')">'
																		+ data[i].fileName
																		+ '</a> <img class="img-delete" src="'
																		+ __ctxPath
																		+ '/images/system/delete.gif" onclick="removeResumeFile(this,'
																		+ data[i].fileId
																		+ ')"/>&nbsp;|&nbsp;</span>');
														}
													}
												});
												dialog.show(this);
											}
										}, {
											iconCls : 'reset',
											xtype : 'button',
											text : '清除附件',
											handler : function() {
												var fileAttaches = Ext.getCmp("planFileIds");
												var filePanel = Ext.getCmp('planFilePanel');
												filePanel.body.update('');
												fileAttaches.setValue('');
											}
										}, {
											xtype : 'hidden',
											id : 'planFileIds',
											name : 'planFileIds'
										}]
									}]
						},  {
							xtype : 'radiogroup',
							fieldLabel : '是否启用',
							autoHeight : true,
							columns : 2,
							width : 520,
							items : [{
										boxLabel : '是',
										name : 'workPlan.status',
										inputValue : 1,
//										id : 'status1',
										checked : true
									}, {
										boxLabel : '否',
										name : 'workPlan.status',
										inputValue : 0
//										id : 'status0'
									}]
						},  {
							fieldLabel : '标识',
							hiddenName : 'workPlan.icon',
							id : 'icon',
							xtype : 'iconcomb',
							mode : 'local',
							allowBlank : false,
							width : 520,
							editable : false,
							store : new Ext.data.SimpleStore({
										fields : ['flagStyle', 'flagName'],
										data : [['ux-flag-blue', '日常计划'],
												['ux-flag-orange', '重要计划'],
												['ux-flag-green', '特殊计划'],
												['ux-flag-pink','个人计划'],
												['ux-flag-red','紧急计划'],
												['ux-flag-purple','部门计划'],
												['ux-flag-yellow','待定计划']]
									}),
							valueField : 'flagStyle',
							displayField : 'flagName',
							//iconClsField : 'flagClass',
							triggerAction : 'all',
							value:'ux-flag-blue'
						}, {
							fieldLabel : '备注',
							name : 'workPlan.note',
							xtype : 'textarea',
//							id : 'note',
							width : 520,
							height : 50
						}]}]
			}
			);
            if(this.planId!=''&&this.planId!=null&&this.planId!=undefined){
	            this.formPanel.loadData({
		                url : __ctxPath + '/task/getWorkPlan.do?planId=' + this.planId,
		                preName:'workPlan',
		                root:'data',
						waitMsg : '正在载入数据...',
						success:function(response,options){
							var json=Ext.util.JSON.decode(response.responseText);
							var workPlan=json.data;
							var startTime = workPlan.startTime;
							var status = workPlan.status;
							var endTime = workPlan.endTime;
							var af = workPlan.planFiles;
							var filePanel = Ext.getCmp('planFilePanel');
							var fileIds = Ext.getCmp("planFileIds");
							for (var i = 0; i < af.length; i++) {
								if (fileIds.getValue() != '') {
									fileIds.setValue(fileIds.getValue() + ',');
								}
								fileIds.setValue(fileIds.getValue() + af[i].fileId);
								Ext.DomHelper
										.append(
												filePanel.body,
												'<span><a href="#" onclick="FileAttachDetail.show('
														+ af[i].fileId
														+ ')">'
														+ af[i].fileName
														+ '</a><img class="img-delete" src="'
														+ __ctxPath
														+ '/images/system/delete.gif" onclick="removeResumeFile(this,'
														+ af[i].fileId
														+ ')"/>&nbsp;|&nbsp;</span>');
							
							}
							
						var typeId=json.proTypeId;
						var type=Ext.getCmp('PersonalWorkPlanFormPanel').getCmpByName('workPlan.proTypeId');
						type.getStore().on('load',function(){
						    type.setValue(typeId);
						},this);
				
						},
						failure : function(response,options){
							Ext.ux.Toast.msg('编辑', '载入失败');
						}
	            });
            }
            
            this.buttons = [{
                text : '保存',
                iconCls : 'btn-save',
                scope : this,
                handler : this.saveRecord
            },{
                text : '关闭',
                iconCls : 'close',
                scope : this,
                handler : this.closeWin
            }];
       },
       saveRecord:function(){
             var fp = this.formPanel;
             var win=this;
	           if (fp.getForm().isValid()) {
				fp.getForm().submit({
							method : 'post',
							waitMsg : '正在提交数据...',
							success : function(fp, action) {
								Ext.ux.Toast.msg('操作信息', '成功保存信息！');
								 Ext.getCmp('PersonalWorkPlanGrid').getStore()
								 .reload();
								 win.close();
							},
							failure : function(fp, action) {
								Ext.MessageBox.show({
											title : '操作信息',
											msg : '信息保存出错，请联系管理员！',
											buttons : Ext.MessageBox.OK,
											icon : 'ext-mb-error'
										});
								win.close();
							}
						});
				}
       },//save method over
       closeWin:function(){
          this.close();
       }
});

/**
* 上传文件删除
*/
function removeResumeFile(obj, fileId) {
	var fileIds = Ext.getCmp("planFileIds");
	var value = fileIds.getValue();
	if (value.indexOf(',') < 0) {// 仅有一个附件
		fileIds.setValue('');
	} else {
		value = value.replace(',' + fileId, '').replace(fileId + ',', '');
		fileIds.setValue(value);
	}
	var el = Ext.get(obj.parentNode);
	el.remove();
};