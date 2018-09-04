var getCompensatoryGridDate = function(grid) {

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
Compensatory = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	projectId : null,
	isHidden : false,
	businessType:null,
	constructor : function(_cfg) {
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.isHidden) != "undefined") {
			this.isHidden = _cfg.isHidden;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		Compensatory.superclass.constructor.call(this, {
			        width:700,
			        buttonAlign:'center',
			        title:'代偿',
			         modal:true,
					items : [this.grid_Compensatory],
					buttons:[{
					    text : '保存',
						iconCls : 'btn-save',
						scope : this,
						disabled : this.isReadOnly,
						handler : this.save
					},{
					    text : '关闭',
						iconCls : 'close',
						scope : this,
						disabled : this.isReadOnly,
						handler : function(){
							this.close();
						}
					}]
					
				})
	},
	initUIComponents : function() {
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计:'+Ext.util.Format.number(v,'0,000,000,000.00')+"元";
		}
		this.datefield = new Ext.form.DateField({
					format : 'Y-m-d',
					allowBlank : false,
					readOnly : this.isHidden
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

		this.CompensatoryBar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '增加',
								xtype : 'button',
								scope : this,
								handler : this.createBankloanProgram
							}, new Ext.Toolbar.Separator({

							}), {
								iconCls : 'btn-del',
								text : '删除',
								xtype : 'button',
								scope : this,
								handler : this.deleteBankLoanProgram
							}]
				})

		this.grid_Compensatory = new Ext.grid.EditorGridPanel({
			border : false,
			tbar : this.isHidden ? null : this.CompensatoryBar,
			autoHeight : true,
			autoWidth:true,
			clicksToEdit : 1,
			stripeRows : true,
			viewConfig : {
				forceFit : true
			},
			plugins: [summary],
			sm : new Ext.grid.CheckboxSelectionModel({}),
			store : new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : __ctxPath
							+ '/creditFlow/guarantee/EnterpriseBusiness/listGlCompensatory.do?projectId='
							+ this.projectId+"&businessType="+this.businessType,
					method : "POST"
				}),
				reader : new Ext.data.JsonReader({
							fields : Ext.data.Record.create([{
										name : 'compensatoryId'
									}, {
										name : 'loanMoney'
									}, {
										name : 'bondMoney'
									}, {
										name : 'compensatoryAmount'
									}, {
										name : 'compensatoryDate'
									}, {
										name : 'compensatoryRemarks'
									}

							]),
							root : 'result'
						})
			}),
			columns : [new Ext.grid.CheckboxSelectionModel({
								hidden : this.isHidden
							}), new Ext.grid.RowNumberer(), {
						header : '贷款余额',
						dataIndex : 'loanMoney',
						sortable : true,
						align : "right",
						summaryType: 'sum',
						summaryRenderer: totalMoney,
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
							return Ext.util.Format.number(value,
									'0,000,000,000.00')
									+ "元"
						}
					}, {
						header : '保证金余额',
						dataIndex : 'bondMoney',
						sortable : true,
						align : "right",
						summaryType: 'sum',
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
							return Ext.util.Format.number(value,
									'0,000,000,000.00')
									+ "元"
						}
					}, {
						header : '代偿余额',
						dataIndex : 'compensatoryAmount',
						sortable : true,
						align : "right",
						summaryType: 'sum',
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
							return Ext.util.Format.number(value,
									'0,000,000,000.00')
									+ "元"
						}
					}, {
						header : '代偿时间',
						format : 'Y-m-d',
						dataIndex : 'compensatoryDate',
						sortable : true,
						editor : this.datefield,
						renderer : ZW.ux.dateRenderer(this.datefield)
					}, {
						header : '代偿说明',
						align : 'center',
						dataIndex : 'compensatoryRemarks',
						sortable : true,
						editor : {
							xtype : 'textfield',
							readOnly : this.isHidden

						},
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					}]

		});

		this.grid_Compensatory.getStore().load();

	},
	createBankloanProgram : function() {
		var gridadd = this.grid_Compensatory;
		var storeadd = this.grid_Compensatory.getStore();
		var keys = storeadd.fields.keys;
		var p = new Ext.data.Record();
		p.data = {};
		for (var i = 1; i < keys.length; i++) {
			p.data[keys[i]] = 0;
			
			p.data[keys[4]] = new Date();
			p.data[keys[5]] = '';
		}

		var count = storeadd.getCount() + 1;
		gridadd.stopEditing();
		storeadd.addSorted(p);
		gridadd.getView().refresh();
		gridadd.startEditing(0, 1);
	},
	deleteBankLoanProgram : function() {
		var griddel = this.grid_Compensatory;
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
					if (row.data.compensatoryId == null
							|| row.data.compensatoryId == '') {
						storedel.remove(row);
						griddel.getView().refresh();
					} else {
						deleteFun(
								__ctxPath
										+ '/creditFlow/guarantee/EnterpriseBusiness/deleteGlCompensatory.do',
								{
									compensatoryId : row.data.compensatoryId
								}, function() {

								}, i, s.length)
					}

					storedel.remove(row);
					griddel.getView().refresh();
				}
			}
		})
	},
	save:function(){
		var vDates=getCompensatoryGridDate(this.grid_Compensatory);
		var grid=this.grid_Compensatory
		var window=this;
		Ext.Ajax.request({
			url : __ctxPath+ '/creditFlow/guarantee/EnterpriseBusiness/saveGlCompensatory.do',
			method : 'POST',
			params : {
				compensatory : vDates,
				projectId : this.projectId,
				businessType:this.businessType
			},
			success : function(response, request) {

				var xx = response.responseText
						.toString().trim();
				if (xx == "{success:true}") {

					Ext.ux.Toast.msg('操作信息',
							"保存成功");
					window.close();
					
				}
			}
		});
	}
});
