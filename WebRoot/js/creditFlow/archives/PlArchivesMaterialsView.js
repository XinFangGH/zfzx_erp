/**
 * @author
 * @class SlProcreditMaterialsView
 * @extends Ext.form.FieldSet
 * @description [SlProcreditMaterials]管理 贷款材料上传
 * @company 智维软件
 * @createtime:
 */
PlArchivesMaterialsView = Ext.extend(Ext.Panel, {
	projId : null,
	gridPanel : null,
	businessType : null,
	isHidden_materials : true,
	isHiddenAffrim : true,
	isEditAffrim : false,
	isHiddenEdit : true,
	isAutoHeight : true,
	isHiddenType : true,
	affrimEditable : true,//add by gao  收到确认编辑，为兼容过去，默认为true,只有当editalbe为true&&isHiddenAffrim为false的时候才可以编辑，兼容过去（过去为isHiddenAffrim为false则可编辑）
	constructor : function(_cfg) {

		if (typeof(_cfg.projectId) != "undefined") {
			this.projId = _cfg.projectId;
		}
		if (typeof(_cfg.businessType) != "undefined") {

			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.isHiddenEdit) != "undefined") {
			this.isHiddenEdit = _cfg.isHiddenEdit;
		}
		if (typeof(_cfg.isHidden_materials) != "undefined") {
			this.isHidden_materials = _cfg.isHidden_materials;
		}
		if (typeof(_cfg.isHiddenAffrim) != "undefined") {
			this.isHiddenAffrim = _cfg.isHiddenAffrim;
		}
		if (typeof(_cfg.isEditAffrim) != "undefined") {
			this.isEditAffrim = _cfg.isEditAffrim;
		}
		if (typeof(_cfg.isAutoHeight) != "undefined") {
			this.isAutoHeight = _cfg.isAutoHeight;
		}
		if (typeof(_cfg.isHiddenType) != "undefined") {
			this.isHiddenType = _cfg.isHiddenType;
		}
		if (typeof(_cfg.affrimEditable) != "undefined") {
			this.affrimEditable = _cfg.affrimEditable;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		PlArchivesMaterialsView.superclass.constructor.call(this, {

					items : [this.gridPanel]
				});
	},
	initUIComponents : function() {
		var obj = this.gridPanel;
		var isHidden = this.isHidden_materials;
		var jsArr = [__ctxPath
				+ '/js/creditFlow/archives/PlArchivesMaterialsForm.js'];

		$ImportSimpleJs(jsArr, null);
		this.datefield = new Ext.form.DateField({
					format : 'Y-m-d',
					readOnly : !this.isAffrimEdit,
					allowBlank : false
				})
		this.recievedatefield = new Ext.form.DateField({
					format : 'Y-m-d',
					readOnly : !(!this.isHiddenAffrim && this.affrimEditable),
					allowBlank : false
				})
		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '增加',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							}, '-', {
								iconCls : 'btn-delete',
								text : '删除',
								xtype : 'button',
								scope : this,
								handler : this.updateRs
							}]
				});
		var checkColumn = new Ext.grid.CheckColumn({
					//hidden : this.isHiddenAffrim,
					hidden : this.isHiddenRecive,
					editable : this.isAffrimEdit,
					header : '是否已提交',
					dataIndex : 'isPigeonhole',
					width : 70
				});
		var recievecheckColumn = new Ext.grid.CheckColumn({
					hidden : this.isHiddenAffrim,
					editable : (!this.isHiddenAffrim && this.affrimEditable),
					header : '是否已收到',
					dataIndex : 'isReceive',
					width : 70
				});
		this.gridPanel = new HT.EditorGridPanel({
			hiddenCm : this.isHidden_materials,
			plugins : [checkColumn, recievecheckColumn],
			collapsible : false,
			clicksToEdit : 1,
			border : false,
			region : 'center',
			viewConfig : {
				forceFit : true,
				autoFill : true
			},
			defaults : {
				anchor : '96%'
			},
			autoWidth : true,
			showPaging : false,
			autoHeight : this.isAutoHeight,
			height : 375,
			tbar : this.isHidden_materials ? null : this.topbar,
			isShowTbar : this.isHidden_materials ? false : true,
			url : __ctxPath + "/creditFlow/archives/listPlArchivesMaterials.do",
			fields : ['proMaterialsId', 'projId', 'materialsId',
					'materialsName', 'isReceive', 'isShow', 'datumNums',
					'isPigeonhole', 'remark', 'archiveConfirmRemark', 'xxnums',
					'pigeonholeTime', 'recieveTime', 'materialsType'],
			columns : [{
						header : 'proMaterialsId',
						dataIndex : 'proMaterialsId',
						hidden : true
					}, {
						header : '归档材料名称',
						width : 250,
						dataIndex : 'materialsName'
					}, {
						header : '线下份数',
						width : 60,
						dataIndex : 'xxnums',
						editor : {
							xtype : 'numberfield'
						}
					}, {
						header : '归档材料是否必备',
						dataIndex : 'materialsType',
						hidden : this.isHiddenType,
						renderer : function(value) {
							if (value == 1) {
								return '必备'
							} else if (value == 2) {
								return '可选'
							} else {
								return ''
							}
						}
					}, {
						header : '线上份数',
						fixed : true,
						width : 60,
						dataIndex : 'datumNums',
						hidden : this.isHidden_materialsxs,
						renderer : function(v) {
							return "<font color=red>" + v + "</font>"
						}

					}, {
						header : '上传',
						fixed : true,
						width : 44,
						dataIndex : 'uploadOrDown',
						hidden : this.isHidden_materials,
						renderer : function() {
							if (isHidden) {
								return "<img src='" + __ctxPath
										+ "/images/download-start.png'/>";
							} else {
								return "<img src='" + __ctxPath
										+ "/images/upload-start.png'/>";
							}
						}
					}, {
						header : '预览',
						fixed : true,
						width : 60,
						dataIndex : 'viewPic',
						hidden : this.isHidden_materialsyl,
						renderer : this.imageView
					}, checkColumn, {
						header : '提交时间',
						dataIndex : 'pigeonholeTime',
						hidden : this.isHiddenRecive,
						editor : this.datefield,
						renderer : ZW.ux.dateRenderer(this.datefield)
					}, {
						header : '提交备注',
						align : "center",
						dataIndex : 'archiveConfirmRemark',
						hidden : this.isHiddenRecive,
						editor : new Ext.form.TextField({
									selectOnFocus : true,
									readOnly : !this.isAffrimEdit
								})
					}, recievecheckColumn, {
						header : '收到时间',
						dataIndex : 'recieveTime',
						hidden : this.isHiddenAffrim,
						editor : this.recievedatefield,
						renderer : ZW.ux.dateRenderer(this.recievedatefield)
					}, {
						header : '备注',
						align : "center",
						dataIndex : 'remark',
						hidden : this.isHiddenAffrim,
						editor : new Ext.form.TextField({
							readOnly : !(!this.isHiddenAffrim && this.affrimEditable),
							selectOnFocus : true
						})
					}],
			baseParams : {
				projectId : this.projId,
				show : true,
				bidPlanId:this.bidPlanId,
				businessType : this.businessType
			},
			scope : this,
			listeners : {
				'cellclick' : function(grid, rowIndex, columnIndex, e) {

					var record = grid.getStore().getAt(rowIndex); //Get the Record
					var fieldName = grid.getColumnModel()
							.getDataIndex(columnIndex); //Get field name

					var loadData = function(size) {
						var oldSize = grid.getStore().getAt(rowIndex)
								.get("datumNums");
						grid.getStore().getAt(rowIndex).set("datumNums", size);
						var e1 = {
							record : grid.getStore().getAt(rowIndex),
							field : 'datumNums',
							'value' : size,
							'originalValue' : oldSize
						}
						grid.fireEvent("afteredit", e1);
						return false;
					}
					if ("uploadOrDown" == fieldName) {
						if (this.ownerCt.isHidden_materials)
							return false;
						var markId = grid.getStore().getAt(rowIndex)
								.get("proMaterialsId");
						var talbeName = "sl_procredit_materials.";
						var mark = talbeName + markId;
						uploadfile("上传借款材料", mark, 15, null, null, loadData);
					}
					if ("viewPic" == fieldName) {
						var markId = grid.getStore().getAt(rowIndex)
								.get("proMaterialsId");
						var talbeName = "sl_procredit_materials.";
						var mark = talbeName + markId;
						picViewer(mark, this.ownerCt.isHiddenEdit);
					}
				},
				afteredit : function(e) {
					var gridObj = this;
					var args = "";
					var value = e.value;
					var id = e.record.data['proMaterialsId'];
					if (e.originalValue != e.value) {//编辑行数据发生改变
						if (e.field == 'isPigeonhole') {
							args = {
								'plArchivesMaterials.isPigeonhole' : value,
								'plArchivesMaterials.bidPlanId':this.bidPlanId,
								'plArchivesMaterials.proMaterialsId' : id
							};
						}

						if (e.field == 'archiveConfirmRemark') {
							args = {
								'plArchivesMaterials.archiveConfirmRemark' : value,
								'plArchivesMaterials.bidPlanId':this.bidPlanId,
								'plArchivesMaterials.proMaterialsId' : id
							};
						}
						if (e.field == 'datumNums') {
							args = {
								'plArchivesMaterials.datumNums' : value,
								'plArchivesMaterials.bidPlanId':this.bidPlanId,
								'plArchivesMaterials.proMaterialsId' : id
							};
						}
						if (e.field == 'xxnums') {
							args = {
								'plArchivesMaterials.xxnums' : value,
								'plArchivesMaterials.bidPlanId':this.bidPlanId,
								'plArchivesMaterials.proMaterialsId' : id
							};
						}
						if (e.field == 'pigeonholeTime') {
							args = {
								'plArchivesMaterials.pigeonholeTime' : value,
								'plArchivesMaterials.bidPlanId':this.bidPlanId,
								'plArchivesMaterials.proMaterialsId' : id
							};
						}
						if (e.field == 'recieveTime') {
							args = {
								'plArchivesMaterials.recieveTime' : value,
								'plArchivesMaterials.bidPlanId':this.bidPlanId,
								'plArchivesMaterials.proMaterialsId' : id
							};
						}
						if (e.field == 'remark') {
							args = {
								'plArchivesMaterials.remark' : value,
								'plArchivesMaterials.bidPlanId':this.bidPlanId,
								'plArchivesMaterials.proMaterialsId' : id
							};
						}
						if (e.field == 'isReceive') {
							args = {
								'plArchivesMaterials.isReceive' : value,
								'plArchivesMaterials.bidPlanId':this.bidPlanId,
								'plArchivesMaterials.proMaterialsId' : id
							};
						}
						Ext.Ajax.request({
							url : __ctxPath
									+ "/creditFlow/archives/savePlArchivesMaterials.do",
							method : 'POST',
							scope : this,
							success : function(response, request) {
								e.record.commit();
							},
							failure : function(response) {
								Ext.Msg.alert('状态', '操作失败，请重试');
							},
							params : args
						})
					}
				}
			}
		})
	},
	materialAffrimRenderer : function(v) {
		return v == true
				? '<font color=green>是</font>'
				: '<font color=red>否</font>';
	},
	createRs : function() {
		new PlArchivesMaterialsForm({
					projectId : this.projId,
					operateObj : this.gridPanel,
					businessType : this.businessType,
					bidPlanId:this.bidPlanId,
					operationType : "SmallLoan_SmallLoanBusiness"
				}).show();
	},
	updateRs : function() {
		var proMaterialIds = "";
		var rows = this.gridPanel.getSelectionModel().getSelections();
		var materialsName = "";
		for (var i = 0; i < rows.length; i++) {
			var materialsType = rows[i].get('materialsType');
			if (materialsType == 1) {
				materialsName = materialsName + rows[i].get('materialsName')
						+ ","
				Ext.Msg.alert("状态", materialsName + "是必备归档材料，不允许删除");
				continue;
			} else {
				proMaterialIds = proMaterialIds + rows[i].get('proMaterialsId');
				if (i != rows.length - 1) {
					proMaterialIds = proMaterialIds + ','
				}
			}
		}
		Ext.Ajax.request({
					url : __ctxPath
							+ "/creditFlow/archives/multiDelPlArchivesMaterials.do",
					method : 'POST',
					scope : this,
					success : function(response, request) {
						this.gridPanel.store.reload();
					},
					failure : function(response) {
						Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
					},
					params : {
						'materialsIds' : proMaterialIds,
						'show' : false
					}
				})
	},
	imageUpload : function() {
		return "<img src='" + __ctxPath + "/images/upload-start.png'/>";
	},
	imageView : function() {
		return "<img src='" + __ctxPath + "/images/btn/read_document.png'>";
	}

});
