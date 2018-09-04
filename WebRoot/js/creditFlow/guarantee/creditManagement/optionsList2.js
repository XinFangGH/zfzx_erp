var getOptionGridDate2 = function(grid) {

	if (typeof(grid) == "undefined" || null == grid) {
		return "";
	}
	var vRecords = grid.getStore().getRange(0, grid.getStore().getCount()); // 得到修改的数据（记录对象）

	var vCount = vRecords.length; // 得到记录长度

	var vDatas = '';
	if (vCount > 0) {
		// begin 将记录对象转换为字符串（json格式的字符串）
		for (var i = 0; i < vCount; i++) {
			vDatas += Ext.util.JSON.encode(vRecords[i].data) + '@';
		}

		vDatas = vDatas.substr(0, vDatas.length - 1);
	}
	return vDatas;
}
OptionList = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	isHidden : false,
	constructor : function(_cfg) {
		if (typeof(_cfg.isHidden) != "undefined") {
			this.isHidden = _cfg.isHidden
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		OptionList.superclass.constructor.call(this, {
					items : [this.gPanel_option]
				})
	},
	initUIComponents : function() {
		var deleteFun = function(url, prame, sucessFun, i, j) {
			Ext.Ajax.request({
				url : url,
				method : 'POST',
				success : function(response) {
					if (response.responseText.trim() == '{success:true}') {
						if (i == (j - 1)) {
							Ext.ux.Toast.msg('操作信息', '删除成功!');
						}
						sucessFun();
					} else if (response.responseText.trim() == '{success:false}') {

						Ext.ux.Toast.msg('操作信息', '删除失败!');
					}
				},
				params : prame
			});
		};
		this.button_addOption = new Ext.Button({
					text : '增加',
					tooltip : '增加一条新的选项',
					iconCls : 'addIcon',
					scope : this,
					handler : function() {

						var gridadd = this.gPanel_option;
						var storeadd = this.gPanel_option.getStore();
						var keys = storeadd.fields.keys;
						var p = new Ext.data.Record();
						p.data = {};
						var count = storeadd.getCount() + 1;
						for (var i = 1; i < keys.length; i++) {
							p.data[keys[i]] = '';
							p.data[keys[1]] = 0;
							p.data[keys[3]] = count;
						}

						gridadd.stopEditing();
						storeadd.addSorted(p);
						gridadd.getView().refresh();
						gridadd.startEditing(0, 1);

					}
				});

		this.button_deleteOption = new Ext.Button({
			text : '删除选项',
			tooltip : '删除选中的指标选项',
			iconCls : 'deleteIcon',
			scope : this,
			disabled : false,
			handler : function() {

				var griddel = this.gPanel_option;
				var storedel = griddel.getStore();
				var s = griddel.getSelectionModel().getSelections();
				if (s.length <= 0) {
					Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
					return false;
				}
				Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {

					if (btn == "yes") {
						griddel.stopEditing();
						for (var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.id == null || row.data.id == '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								deleteFun(
										__ctxPath
												+ '/creditFlow/creditmanagement/deleteRsOption.do',
										{
											id : row.data.id
										}, function() {

										}, i, s.length)
							}

							storedel.remove(row);
							griddel.getView().refresh();
						}
					}
				})
			}

		});

		this.jStore_option = new Ext.data.JsonStore({
					url : __ctxPath + '/creditFlow/creditmanagement/list1Option.do',
					totalProperty : 'totalProperty',
					root : 'topics',
					fields : [{
								name : 'id'
							}, {
								name : 'sortNo'
							}, {
								name : 'optionName'
							}, {
								name : 'score'
							}]
				});

		this.cModel_option = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer({
							header : "序号",
							width : 40
						}), {
					header : "指标选项",
					width : 300,
					sortable : true,
					dataIndex : 'optionName',
					editor : {
						xtype : 'textfield',
						readOnly : this.isHidden

					}
				}, {
					header : "分值",
					width : 40,
					sortable : true,
					dataIndex : 'score',
					editor:{
						xtype:'combo',
						mode : 'local',
					    displayField : 'name',
					    valueField : 'id',
					    store : new Ext.data.SimpleStore({
								fields : ["name", "id"],
								data : [["1", "1"],
										["2", "2"],
										["3", "3"],
										["4", "4"],
										["5", "5"],
										["6", "6"],
										["7", "7"],
										["8", "8"],
										["9", "9"],
										["10", "10"]]
						}),
						triggerAction : "all",
						hiddenName:"score",
				        anchor : "100%"
					
					}
				}/*, {
					header : '向上',
					width : 40,
					sortable : true,
					dataIndex : 'score',
					hidden : this.isHidden,
					renderer : function() {
						return "<img src='" + __ctxPath
								+ "/images/upload-start.gif'/>";
					}
				}, {
					header : '向下',
					width : 40,
					sortable : true,
					dataIndex : 'score',
					hidden : this.isHidden,
					renderer : function() {
						return "<img src='" + __ctxPath
								+ "/images/download-start.gif'/>";
					}
				}*/]);

		this.gPanel_option = new Ext.grid.EditorGridPanel({
					name : 'gPanel_option',
					store : this.jStore_option,
					selModel : new Ext.grid.RowSelectionModel(),
					colModel : this.cModel_option,
					autoHeight : true,
					clicksToEdit : 1,
					stripeRows : true,
					viewConfig : {
						forceFit : true
					},
					tbar : (this.isHidden == false) ? [this.button_addOption,
							this.button_deleteOption] : null

				});
		this.gPanel_option.addListener('cellclick', this.cellClick);
		// this.gPanel_option.getStore().load();
	},
	cellClick : function(grid, rowIndex, columnIndex, e) {

		if (3 == columnIndex) {

			var optionName = grid.getStore().getAt(rowIndex - 1)
					.get('optionName');
			var optionName1 = grid.getStore().getAt(rowIndex).get('optionName')
			var rec = grid.getStore().getAt(rowIndex - 1)
			var rec1 = grid.getStore().getAt(rowIndex);
			rec.set('optionName', optionName1);
			rec1.set('optionName', optionName);
			rec.commit();
			rec1.commit()
		}
		if (4 == columnIndex) {
			var optionName = grid.getStore().getAt(rowIndex + 1)
					.get('optionName');
			var optionName1 = grid.getStore().getAt(rowIndex).get('optionName')
			var rec = grid.getStore().getAt(rowIndex + 1)
			var rec1 = grid.getStore().getAt(rowIndex);
			rec.set('optionName', optionName1);
			rec1.set('optionName', optionName);
			rec.commit();
			rec1.commit()
		}
	}

});
