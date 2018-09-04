var DutySystemForm = function(systemId) {
	this.systemId = systemId;
	var fp = this.setup();
	var window = new Ext.Window({
				id : 'DutySystemFormWin',
				title : '班制定义详细信息',
				width : 830,
				iconCls:'btn-clock',
				height : 230,
				modal : true,
				layout : 'fit',
				buttonAlign : 'center',
				items : [this.setup()],
				buttons : [{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('DutySystemForm');
						if (fp.getForm().isValid()) {
							var params = [];
							var store=Ext.getCmp("DutySystemSettingGird").getStore();
							var isValid=true;
							for (i = 0, cnt = store.getCount(); i < cnt; i += 1) {
								var record = store.getAt(i);
								for(var ct=0;ct<7;ct++){
									var ids=record.get('day'+ct);
									if(ids==null || ids==''){
										isValid=false;
										break;
									}
								}
								params.push(record.data);
							}
							
							if(!isValid){
								Ext.Msg.alert('警告','没有完全设置星期的班次!');
								return ;
							}
							
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								params:{
									data:Ext.encode(params)
								},
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									Ext.getCmp('DutySystemGrid').getStore().reload();
									window.close();
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									window.close();
								}
							});
						}
					}
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					handler : function() {
						window.close();
					}
				}]
			});
	window.show();
};

DutySystemForm.prototype.setup = function() {
	var gridPanel=DutySystemForm.grid(this.systemId);
	var formPanel = new Ext.FormPanel({
				url : __ctxPath + '/personal/saveDutySystem.do',
				layout : 'form',
				id : 'DutySystemForm',
				border : false,
				defaults : {
					anchor : '98%,98%'
				},
				bodyStyle : 'padding:5px;',
				defaultType : 'textfield',
				items : [{
							name : 'dutySystem.systemId',
							id : 'systemId',
							xtype : 'hidden',
							value : this.systemId == null ? '' : this.systemId
						}, {
							fieldLabel : '班制名称',
							name : 'dutySystem.systemName',
							id : 'systemName',
							allowBlank:false
						},
						{
							
							xtype:'radiogroup',
							fieldLabel : '是否缺省',
							autoHeight: true,
							columns :2,
							items : [{
										boxLabel : '是',
										name : 'dutySystem.isDefault',
										inputValue : 1,
										id:'isDefault1',
										checked : true
									},{
										boxLabel : '否',
										name : 'dutySystem.isDefault',
										inputValue : 0,
										id:'isDefault0'
									}]
						
						}
						,gridPanel
				]
			});
	if (this.systemId != null && this.systemId != undefined) {
		formPanel.getForm().load({
			deferredRender : false,
			url : __ctxPath + '/personal/getDutySystem.do?systemId='
					+ this.systemId,
			waitMsg : '正在载入数据...',
			success : function(form, action) {
				var ds=action.result.data;
				if(ds.isDefault==1){
					Ext.getCmp("isDefault1").setValue(true);
				}else{
					Ext.getCmp("isDefault0").setValue(true);
				}
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('编辑', '载入失败');
			}
		});
	}
	return formPanel;
};

DutySystemForm.grid=function(systemId){
	if(systemId==undefined)systemId='';
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/personal/settingDutySystem.do?systemId='+systemId
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							fields : ['day0','day1','day2','day3','day4','day5','day6','dayId0','dayId1','dayId2','dayId3','dayId4','dayId5','dayId6']
				})
	}); 
     
    store.load();
	
	var row = 0;
	var col = 0;
	//班次选择器
	var dutySecEditor=new Ext.form.TriggerField({
				//triggerClass : 'x-form-browse-trigger',
				onTriggerClick : function(e) {
					DutySectionSelector.getView(function(ids, names) {
							var grid=Ext.getCmp("DutySystemSettingGird");
							
							var store = grid.getStore();
							var record = store.getAt(0);
							//计算对应的index值,注意到列头的顺序不能变换
							var columns=(col-1)/2;
							record.set('dayId' + columns, ids);
							record.set('day' + columns, names);
					}).show();
				}
	});
	
	var grid = new Ext.grid.EditorGridPanel({
				id:'DutySystemSettingGird',
				region:'center',
				title:'班次设置',
				height:100,
				store:store,
				columns : [new Ext.grid.RowNumberer(),
						{
							header : "周日",
							dataIndex : 'day0',
							editor:dutySecEditor
						},{
							dataIndex:'dayId0',
							hidden:true
						}, {
							header : "周一",
							dataIndex : 'day1',
							editor:dutySecEditor
						}, {
							dataIndex:'dayId1',
							hidden:true
						},{
							header : "周二",
							dataIndex : 'day2',
							editor:dutySecEditor
						},{
							dataIndex:'dayId2',
							hidden:true
						},{
							header : "周三",
							dataIndex : 'day3',
							editor:dutySecEditor
						},{
							dataIndex:'dayId3',
							hidden:true
						},{
							header : "周四",
							dataIndex : 'day4',
							editor:dutySecEditor
						},{
							dataIndex:'dayId4',
							hidden:true
						},{
							header : "周五",
							dataIndex : 'day5',
							editor:dutySecEditor
						},{
							dataIndex:'dayId5',
							hidden:true
						},{
							header : '周六',
							dataIndex:'day6',
							editor:dutySecEditor
						},{
							dataIndex:'dayId6',
							hidden:true
						}]
			});
			grid.on('cellclick', function(grid,rowIndex,columnIndex,e) {
				row=rowIndex;
				col=columnIndex;
			});
	return grid;
}
