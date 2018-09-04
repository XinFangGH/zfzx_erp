Ext.onReady(function() {
	Ext.QuickTips.init();

	var see_button_c = new Ext.Button({
				text : '展开所有结点',
				tooltip : '展开树中所有结点',
				iconCls : 'seeIcon',
				scope : this,
				handler : function() {
					h_tree.expandAll();
				}
			});
	var collapse_button_c = new Ext.Button({
				text : '收起所有结点',
				tooltip : '收起树中所有结点',
				iconCls : 'closeIcon',
				scope : this,
				handler : function() {
					h_tree.collapseAll();
				}
			});

	var h_tree = new Ext.ux.tree.TreeGrid({
				// title : '行业类别数据字典',
				id : 'h_tree',
				height : 400,
				frame : false,
				enableDD : true,
				border : false,
				columns : [{
							header : '行业类别描述',
							dataIndex : 'Description',
							width : 420
						}, {
							header : '类别ID号',
							dataIndex : 'Sortorder',
							width : 60
						}, {
							header : '类别代码',
							dataIndex : 'Code',
							width : 60
						}],

				dataUrl : 'vocationData.json',
				listeners : {
					click : function(n) {
						var sortorder = n.attributes.Sortorder;
						Ext.Ajax.request({
							url :'getListBySortorder.do',
							method : 'Post',
							params : {
								sortorder :sortorder
							},
							success :function(response,request){
								var obj=Ext.util.JSON.decode(response.responseText);
								
								Ext.getCmp("isic_rev4_id").setValue(obj.data.sortorder);
								Ext.getCmp("isic_rev4_typeID").setValue(obj.data.code);
								Ext.getCmp("isic_rev4_description").setValue(obj.data.description);
								Ext.getCmp("isic_rev4_Inclusion").setValue(obj.data.explanatoryNoteInclusion);
								Ext.getCmp("isic_rev4_Exclusion").setValue(obj.data.explanatoryNoteExclusion);
							}

						})
					}
				}
			});

	var gridForm = new Ext.FormPanel({
		id : 'company-form',
		tbar : [see_button_c, collapse_button_c],
		// frame : true,
		labelAlign : 'left',
		title : '行业类别信息',
		//autoWidth : true,
		//autoHeight : true,
		
		layout: 'form',
		bodyStyle : 'background-color:#dfe8f6;padding-top:10px;padding-left:10px',
		anchor : '100%',
		border : false,
		height :465,
		bodyBorder : false,
		layout : 'column',
		items : [{
					border : false,
					columnWidth : 0.48,
					//xtype : 'form',
					anchor : '100%,100%',
					items : [h_tree]
				}, {
					border : false,
					columnWidth : 0.52,
					//xtype : 'form',
					
					height : 400,
					bodyStyle : 'background-color:#dfe8f6;padding-top:10px;padding-left:10px',
					frame : false,
					labelAlign : 'right',
					labelWidth : 90,
					items : [{
								xtype : 'fieldset',
								anchor : '80%',
								collapsible : true,
								title : '行业类别详细信息',
								items : [{
											id : 'isic_rev4_id',
											xtype : 'textfield',
											width :220,
											fieldLabel : '类别ID号',
											name : 'isic_rev4_id'
										}, {
											id :'isic_rev4_typeID',
											xtype : 'textfield',
											width :220,
											fieldLabel : '行业类别代码',
											name : 'isic_rev4_typeID'
										}, {
											id : 'isic_rev4_description',
											xtype : 'textfield',
											width :220,
											fieldLabel : '行业类别描述',
											name : 'isic_rev4_description'
										}, {
											id :'isic_rev4_Inclusion',
											xtype : 'textarea',
											width :460,
											height : 133,
											fieldLabel : '行业类别介绍1',
											name : 'isic_rev4_Inclusion'
										}, {
											id :'isic_rev4_Exclusion',
											xtype : 'textarea',
											width :460,
											height : 133,
											fieldLabel : '行业类别介绍2',
											name : 'isic_rev4_Exclusion'
										}]
							}]

				}],
		renderTo : Ext.getBody()
	});

});