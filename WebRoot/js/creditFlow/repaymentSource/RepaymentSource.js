var getSourceGridDate = function(grid) {

	if (typeof(grid) == "undefined" || null == grid) {
		return "";
		return false;
	}
	var vRecords = grid.getStore().getRange(0, grid.getStore().getCount()); // 得到修改的数据（记录对象）

	var vCount = vRecords.length; // 得到记录长度

	var vDatas = '';
	if (vCount > 0) {
		// begin 将记录对象转换为字符串（json格式的字符串）
		for (var i = 0; i < vCount; i++) {
			var str = Ext.util.JSON.encode(vRecords[i].data);
			var index = str.lastIndexOf(",");
			str = str.substring(0, index) + "}";
			vDatas += str + '@';
		}

		vDatas = vDatas.substr(0, vDatas.length - 1);
	}
	return vDatas;
}
RepaymentSource = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	name : "gudong_info",

	projectId : null,
	isHidden : false,
	isHiddenAddBtn : true,
	isHiddenDelBtn : true,
	constructor : function(_cfg) {
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.isHidden) != "undefined") {
			this.isHidden = _cfg.isHidden
		}
		if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
			this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
		}
		if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
			this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
		}
		if (typeof(_cfg.enableEdit) != "undefined") {
			this.enableEdit = _cfg.enableEdit;
		}
		Ext.applyIf(this, _cfg);

		this.initUIComponents();
		RepaymentSource.superclass.constructor.call(this, {
					items : [this.grid_RepaymentSource]
				})
	},
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
		if (this.isHiddenDelBtn == true && this.isHiddenAddBtn == true) {
			this.isHidden = true;
		}
		this.datefield = new Ext.form.DateField({
					format : 'Y-m-d',
					readOnly : this.isHidden,
					allowBlank : false
				})
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

		this.RepaymentSourceBar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '增加',
								xtype : 'button',
								scope : this,
								hidden : this.isHiddenAddBtn,
								handler : this.createRepaymentSource
							}, new Ext.Toolbar.Separator({
										hidden : this.isHiddenDelBtn
									}), {
								iconCls : 'btn-del',
								text : '删除',
								xtype : 'button',
								scope : this,
								hidden : this.isHiddenDelBtn,
								handler : this.deleteRepaymentSource
							}]
				})

		this.grid_RepaymentSource = new Ext.grid.EditorGridPanel({
			border : false,
			tbar : this.isHidden ? null : this.RepaymentSourceBar,
			autoHeight : true,
			clicksToEdit : 1,
			stripeRows : true,
			plugins : [summary],
			enableDragDrop : false,
			viewConfig : {
				forceFit : true
			},
			sm : new Ext.grid.CheckboxSelectionModel({}),
			store : new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : __ctxPath
							+ '/creditFlow/repaymentSource/listSlRepaymentSource.do?projectId='
							+ this.projectId,
					method : "POST"
				}),
				reader : new Ext.data.JsonReader({
							fields : Ext.data.Record.create([{
										name : 'sourceId'
									}, {
										name : 'typeId'
									}/*
										 * , { name : 'objectName' }
										 */, {
										name : 'money'
									}, {
										name : 'repaySourceDate'
									}, {
										name : 'remarks'
									}, {
										name : 'typeName'
									}

							]),
							root : 'result'
						})
			}),
			columns : [new Ext.grid.CheckboxSelectionModel({
								hidden : this.isHidden
							}), new Ext.grid.RowNumberer(), {
						header : '来源类型',
						dataIndex : 'typeId',
						sortable : true,
						width : 170,
						summaryType : 'count',
						summaryRenderer : totalMoney,
						editor : new DicKeyCombo({
									allowBlank : false,
									maxLength : 128,
									editable : true,
									lazyInit : false,
									nodeKey : 'repaymentSource',
									width : 200,
									readOnly : this.isHidden
								}),
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {

							var objcom = this.editor;
							var objStore = objcom.getStore();
							objStore.reload()
							var idx = objStore.find("itemId", value);
							if (idx != "-1") {
								return objStore.getAt(idx).data.itemName;
							} else {

								if (record.data.typeName == "") {

									record.data.typeName = value

								} else {
									var x = store.getModifiedRecords();
									if (x != null && x != ""
											&& record.data.typeName == "") {
										record.data.typeName = value
									}
								}

								return record.get("typeName")
							}
						}
					}/*
						 * , { header : '对象名称', dataIndex : 'objectName',
						 * sortable : true, width :127, editor : {
						 * xtype:'textfield', readOnly:this.isHidden, allowBlank :
						 * false } , renderer : function(data, metadata, record,
						 * rowIndex, columnIndex, store) { metadata.attr = '
						 * ext:qtip="' + data + '"'; return data; } }
						 */, {
						header : '资金规模',
						dataIndex : 'money',
						sortable : true,
						align : 'right',
						summaryType : 'sum',
						width : 127,
						editor : {
							xtype : 'numberfield',
							allowBlank : false,
							readOnly : this.isHidden,
							style : {
								imeMode : 'disabled'
							}
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.number(value, '0,000') + "元"
						}

					}, {
						header : '资金到位时间',
						format : 'Y-m-d',
						dataIndex : 'repaySourceDate',
						sortable : true,
						align : 'center',
						width : 82,
						editor : this.datefield,
						renderer : ZW.ux.dateRenderer(this.datefield)
					}, {
						header : '备注',
						dataIndex : 'remarks',
						sortable : true,
						width : 566,
						align : "center",
						editor : {
							xtype : 'textfield',
							readOnly : this.isHidden

						},
						renderer : function(data, metadata, record, rowIndex,
								columnIndex, store) {
							metadata.attr = ' ext:qtip="' + data + '"';
							return data;
						}
					}],
			listeners : {
				scope : this,
				beforeedit : function(e) {
					if (typeof(e.record.data['sourceId']) != "undefined") {
						if (this.enableEdit == true) {
							e.cancel = false;
						} else if (this.enableEdit == false) {
							e.cancel = true;
						}
					} else if (typeof(e.record.data['sourceId']) == "undefined") {
						e.cancel = false;
					}
				}
			}
		});

		this.grid_RepaymentSource.getStore().load();

	},
	save : function() {
		var gridsave = this.grid_RepaymentSource;

		var vDates = getSourceGridDate(this.grid_RepaymentSource);
		if (vDates == '') {
			Ext.Msg.alert("提示", "请先填写第一还款来源！")
			return;
		}
		Ext.Ajax.request({
					url : __ctxPath
							+ '/creditFlow/repaymentSource/saveSlRepaymentSource.do',
					method : 'POST',
					params : {
						RepaymentSource : vDates,
						projectId : this.projectId
					},
					success : function(response, request) {

						var xx = response.responseText.toString().trim();
						if (xx == "{success:true}") {

							Ext.ux.Toast.msg('操作信息', "保存成功");
							gridsave.getStore().reload()

						} else {
							Ext.ux.Toast.msg('操作信息', "保存失败");

						}
					}
				});
	},
	createRepaymentSource : function() {

		var gridadd = this.grid_RepaymentSource;
		var storeadd = this.grid_RepaymentSource.getStore();
		var keys = storeadd.fields.keys;
		var p = new Ext.data.Record();
		p.data = {};
		for (var i = 1; i < keys.length; i++) {
			p.data[keys[i]] = '';
			p.data[keys[2]] = 0;
			p.data[keys[3]] = new Date();
		}

		var count = storeadd.getCount() + 1;
		gridadd.stopEditing();
		storeadd.addSorted(p);
		gridadd.getView().refresh();
		gridadd.startEditing(0, 1);
	},
	deleteRepaymentSource : function() {
		var griddel = this.grid_RepaymentSource;
		var storedel = griddel.getStore();
		var s = griddel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		}
		Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {

			if (btn == "yes") {
				griddel.stopEditing();
				for (var i = 0; i < s.length; i++) {
					var row = s[i];
					if (row.data.sourceId == null || row.data.sourceId == '') {
						storedel.remove(row);
						griddel.getView().refresh();
					} else {
						deleteFun(
								__ctxPath
										+ '/creditFlow/repaymentSource/deleteSlRepaymentSource.do',
								{
									sourceId : row.data.sourceId
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
