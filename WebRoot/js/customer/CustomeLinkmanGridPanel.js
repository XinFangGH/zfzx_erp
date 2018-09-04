CustomeLinkmanGridPanel = Ext.extend(Ext.Panel,{
		constructor : function(_cfg) {
			Ext.applyIf(this, _cfg);

			this.initUIComponents();
			CustomeLinkmanGridPanel.superclass.constructor.call(this,{	
				items :[this.gridPanel]
			})
		},
		initUIComponents : function() {
			 var rowEditIndex=-1;
			this.datefield=new Ext.form.DateField({
				format : 'Y-m-d',
				readOnly:this.isHidden,
				allowBlank : false
			})

			
			this.topbar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						handler : this.createRs
					},new Ext.Toolbar.Separator({
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						handler : this.deleteRs
					},new Ext.Toolbar.Separator({
					})]
				})
	var htstore = new Ext.data.SimpleStore({
						fields : ["htUse"],
						data : [['是'], ['否']]
					});
	var sexstore = new Ext.data.SimpleStore({
				fields : ["htUse"],
				data : [['男'], ['女']]
			});
	this.gridPanel = new HT.EditorGridPanel(
			{
				border : false,
				tbar :this.isHidden==true?null:this.topbar,
				clicksToEdit :1,
				autoHeight : true,
				//autoWidth :true,
				stripeRows : true,
				//enableDragDrop : false,
				showPaging : false,
				viewConfig : {
					forceFit : true
				},
				url : __ctxPath+ '/customer/listCustomerbanklinkman.do?enterpriseId='+ this.enterpriseId,
				fields : [{
					name : 'id'
				},{
					name : 'name'
				},
				{
					name : 'pos'
				},
				{
					name : 'sex'
				},
				{
					name : 'phone'
				},
				{
					name : 'main'
				},{
					name : 'address'
				},{
					name : 'enterpriseid'
				}],
			
				columns : [
				new Ext.grid.RowNumberer(),{
					header : '联系人姓名',	
					dataIndex : 'name',
					sortable : true,
					editor :  {					
						xtype:'textfield',
						readOnly:this.isHidden
					}
				  },{
						header : '职位',
						dataIndex : 'pos',
						sortable : true,
						editor : {
							xtype : 'textfield',
							 readOnly : this.isHidden
						}
	
					
					},{
						header : '性别',
						dataIndex : 'sex',
						sortable : true,
						editor : {
							xtype : 'combo',
							editable : false,
							store : sexstore,
							valueField : 'htUse',
							displayField : 'htUse',
							mode : 'local',
							triggerAction : 'all',
							readOnly : this.isHidden
						
						}
					},
					{
						header : '电话',
						dataIndex : 'phone',
						sortable : true,
						editor : {
							xtype : 'textfield',
							 readOnly : this.isHidden
							
						}
					},
					{
						header : '是否为主联系人',
						dataIndex : 'main',
						sortable : true,
						editor : {
							xtype : 'combo',
							editable : false,
							store : htstore,
							valueField : 'htUse',
							displayField : 'htUse',
							mode : 'local',
							triggerAction : 'all',
							readOnly : this.isHidden
						
						}
					},
					{
						header : '家庭地址',
						dataIndex : 'address',
						sortable : true,
						editor : {
							xtype : 'textfield',
							readOnly : this.isHidden
						}
					}]
              });

		},
		
		createRs : function() {
			
			var gridadd = this.gridPanel;
			var storeadd = this.gridPanel.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			for ( var i = 1; i < keys.length; i++) {
				p.data[keys[i]] = null;
			}

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
		},
		deleteRs : function() {
			var griddel = this.gridPanel;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}
			Ext.Msg.confirm("提示!",'确定要删除吗？',
				function(btn) {

					if (btn == "yes") {
						griddel.stopEditing();
						 var ids = Array();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.id == null || row.data.id == '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								ids.push(row.data.id)
							}
							storedel.remove(row);
							griddel.getView().refresh();
						}
						Ext.Ajax.request( {
							url : __ctxPath + '/customer/multiDelCustomerbanklinkman.do',
							method : 'POST',
							success : function(response) {
								Ext.ux.Toast.msg('操作信息', '删除成功!');
									
							},
							params : {ids:ids}
						});
					}
				})
		},
	
		getData : function() {
		var vRecords = this.gridPanel.getStore().getRange(0, this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）

		var vCount = vRecords.length; // 得到记录长度

		var vDatas = '';
		if (vCount > 0) {
			// begin 将记录对象转换为字符串（json格式的字符串）
			for ( var i = 0; i < vCount; i++) {
			var str = Ext.util.JSON.encode(vRecords[i].data);
			vDatas += str + '@';
		}

			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	}
});
