Ext.onReady(function() {
	var combox_width=150;
	/*
	 * ! 定义 Combo 数据
	 * 
	 * 第一个下拉框
	 */
	var jsonIsicRev1 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'getComboxList.do?remark=0&code='
						}),
				reader : new Ext.data.JsonReader({
							root : 'topics'
						}, [{
									name : 'code',
									mapping : 'code'
								}, {
									name : 'description',
									mapping : 'description'
								}, {
									name : 'sortorder',
									mapping : 'sortorder'
								}, {
									name : 'explanatoryNoteInclusion',
									mapping : 'explanatoryNoteInclusion'
								}, {
									name : 'explanatoryNoteExclusion',
									mapping : 'explanatoryNoteExclusion'
								}])
			});
	jsonIsicRev1.load();

	// 第二个下拉框
	var jsonIsicRev2 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : ''
						}),
				reader : new Ext.data.JsonReader({
							root : 'topics'
						}, [{
									name : 'code',
									mapping : 'code'
								}, {
									name : 'description',
									mapping : 'description'
								}, {
									name : 'sortorder',
									mapping : 'sortorder'
								}, {
									name : 'explanatoryNoteInclusion',
									mapping : 'explanatoryNoteInclusion'
								}, {
									name : 'explanatoryNoteExclusion',
									mapping : 'explanatoryNoteExclusion'
								}])
			});
	
		// 第三个下拉框
	var jsonIsicRev3 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : ''
						}),
				reader : new Ext.data.JsonReader({
							root : 'topics'
						}, [{
									name : 'code',
									mapping : 'code'
								}, {
									name : 'description',
									mapping : 'description'
								}, {
									name : 'sortorder',
									mapping : 'sortorder'
								}, {
									name : 'explanatoryNoteInclusion',
									mapping : 'explanatoryNoteInclusion'
								}, {
									name : 'explanatoryNoteExclusion',
									mapping : 'explanatoryNoteExclusion'
								}])
			});
	// 第四个下拉框
	var jsonIsicRev4 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : ''
						}),
				reader : new Ext.data.JsonReader({
							root : 'topics'
						}, [{
									name : 'code',
									mapping : 'code'
								}, {
									name : 'description',
									mapping : 'description'
								}, {
									name : 'sortorder',
									mapping : 'sortorder'
								}, {
									name : 'explanatoryNoteInclusion',
									mapping : 'explanatoryNoteInclusion'
								}, {
									name : 'explanatoryNoteExclusion',
									mapping : 'explanatoryNoteExclusion'
								}])
			});
	
	
	var testForm = new Ext.FormPanel({
		labelAlign : 'top',
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		width : '95%',
		layout : 'form',
		items : [{
			xtype : "combo",
			name : "IsicRev1",
			fieldLabel : "行业类别",
			store : jsonIsicRev1,
			displayField : 'description',// 显示字段值
			valueField : 'code',// 显示字段值
			mode : 'local',
			listWidth : 230,
			tpl: '<tpl for="."><div ext:qtip="{code}.{description}<br/>{explanatoryNoteInclusion}<br/>{explanatoryNoteInclusion}" class="x-combo-list-item">{description}</div></tpl>',
			typeAhead : true,
			emptyText : "---请选择---",
			editable : false,// 不允许输入
			triggerAction : 'all',// 因为这个下拉是只能选择的，所以一定要设置属性triggerAction为all，不然当你选择了某个选项后，你的下拉将只会出现匹配选项值文本的选择项，其它选择项是不会再显示了，这样你就不能更改其它选项了。
			autoload : true,
			allowBlank : false,// 该选项值不能为空
			width: combox_width,
			//anchor : "55%",
			listeners : {
				select : function(combo, record, index) {
						
						Ext.getCmp('IsicRev2').store.proxy=new Ext.data.HttpProxy({
								url: 'getComboxList.do?remark=1&code='+ record.get('code')
						});
						Ext.getCmp('IsicRev2').getStore().reload();
						
						Ext.getCmp('IsicRev2').getStore().removeAll(true);
						Ext.getCmp('IsicRev3').getStore().removeAll(true);
						Ext.getCmp('IsicRev4').getStore().removeAll(true);
						Ext.getCmp('IsicRev2').reset();
						Ext.getCmp('IsicRev3').reset();
						Ext.getCmp('IsicRev4').reset();
						
						
						
					
					
				}
			}
		}, {
			id : 'IsicRev2',
			xtype : "combo",
			name : "IsicRev2",
			fieldLabel : "",
			listWidth : 230,
			store : jsonIsicRev2,
			displayField : 'description',// 显示字段值
			valueField : 'code',// 显示字段值
			mode : 'local',
			tpl: '<tpl for="."><div ext:qtip="{code}.{description}<br/>{explanatoryNoteInclusion}<br/>{explanatoryNoteInclusion}" class="x-combo-list-item">{description}</div></tpl>',
			emptyText : "---请选择---",
			editable : false,// 不允许输入
			typeAhead : true,
			triggerAction : 'all',// 因为这个下拉是只能选择的，所以一定要设置属性triggerAction为all，不然当你选择了某个选项后，你的下拉将只会出现匹配选项值文本的选择项，其它选择项是不会再显示了，这样你就不能更改其它选项了。
			// allowBlank:false,//该选项值不能为空
			//anchor : "55%",
			width: combox_width,
			listeners : {
				select : function(combo, record, index) {
				
						Ext.getCmp('IsicRev3').store.proxy=new Ext.data.HttpProxy({
								url: 'getComboxList.do?remark=2&code='+ record.get('code')
						}); 
						Ext.getCmp('IsicRev3').store.load(); 
						Ext.getCmp('IsicRev3').reset();
						Ext.getCmp('IsicRev4').reset();
						Ext.getCmp('IsicRev3').getStore().removeAll(true);
						Ext.getCmp('IsicRev4').getStore().removeAll(true);
						
				}
			}
		}, {
			id : 'IsicRev3',
			xtype : "combo",
			name : "IsicRev3",
			fieldLabel : "",
			store : jsonIsicRev3,
			listWidth : 230,
			tpl: '<tpl for="."><div ext:qtip="{code}.{description}<br/>{explanatoryNoteInclusion}<br/>{explanatoryNoteInclusion}" class="x-combo-list-item">{description}</div></tpl>',
			displayField : 'description',// 显示字段值
			valueField : 'code',// 显示字段值
			mode : 'local',
			emptyText : "---请选择---",
			editable : false,// 不允许输入
			typeAhead : true,
			triggerAction : 'all',// 因为这个下拉是只能选择的，所以一定要设置属性triggerAction为all，不然当你选择了某个选项后，你的下拉将只会出现匹配选项值文本的选择项，其它选择项是不会再显示了，这样你就不能更改其它选项了。
			//anchor : "55%",
			width: combox_width,
			listeners : {
				select : function(combo, record, index) {
						Ext.getCmp('IsicRev4').reset();
						Ext.getCmp('IsicRev4').store.proxy=new Ext.data.HttpProxy({
								url: 'getComboxList.do?remark=3&code='+ record.get('code')
						}); 
						Ext.getCmp('IsicRev4').store.load(); 
						Ext.getCmp('IsicRev4').getStore().removeAll(true);
				}
			}
		}, {
			id : 'IsicRev4',
			xtype : "combo",
			name : "IsicRev4",
			fieldLabel : "",
			store : jsonIsicRev4,
			tpl: '<tpl for="."><div ext:qtip="{code}.{description}<br/>{explanatoryNoteInclusion}<br/>{explanatoryNoteInclusion}" class="x-combo-list-item">{description}</div></tpl>',
			displayField : 'description',// 显示字段值
			valueField : 'code',// 显示字段值
			listWidth : 230,
			mode : 'local',
			typeAhead : true,
			emptyText : "---请选择---",
			editable : false,// 不允许输入
			triggerAction : 'all',// 因为这个下拉是只能选择的，所以一定要设置属性triggerAction为all，不然当你选择了某个选项后，你的下拉将只会出现匹配选项值文本的选择项，其它选择项是不会再显示了，这样你就不能更改其它选项了。
			//anchor : "55%",
			width: combox_width,
			listeners : {
				select : function(combo, record, index) {

				}
			}
		}]
	});

	var dic_Viewport = new Ext.Viewport({
				enableTabScroll : true,
				layout : 'fit',
				items : [testForm]
			});

});
