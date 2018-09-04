/**
 * @author
 * @class SlEnterPriseProcreditMaterialsView
 * @extends Ext.form.FieldSet
 * @description [SlEnterPriseProcreditMaterialsView]管理 贷款材料上传
 * @company 智维软件
 * @createtime:
 */

var xg = Ext.grid;
var tpl = new Ext.XTemplate(
		'<p><b>贷款材料类型:</b>{materialsType}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
		'<p><b>贷款材料名称:</b>{materialsName}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
		'<p><b>线下材料份数:</b>{datumNumsOfLine}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
		'<p><b>上传材料份数:</b>{datumNums}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
		'<p><b>是否展示:</b>{isPackShow}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
		'<p><b>是否已收到:</b>&nbsp;&nbsp;&nbsp;&nbsp;<tpl if="isReceive==true">已收到</tpl><tpl if="isReceive==false">未收到</tpl></p>',
		'<p><b>合规说明:</b>{ruleExplain}</p>', '<p><b>备注:</b>{remark}</p>')
var expander = new xg.RowExpander({
			enableCaching : false,
			tpl : tpl
		});
var receiveRender = function(v) {
	if (v) {
		return "是"
	} else {
		return "否"
	}
}
var Employee = Ext.data.Record.create([{
			name : 'materialsType',
			mapping : 'parentName'
		}, {
			name : 'materialsName',
			mapping : 'materialsName'
		}, {
			name : 'datumNums',
			mapping : 'datumNums'
		}, {
			name : 'datumNumsOfLine',
			mapping : 'datumNumsOfLine'
		}, {
			name : 'remark',
			mapping : 'remark'
		}, {
			name : 'proMaterialsId',
			mapping : 'proMaterialsId'
		}, {
			name : 'isReceive',
			mapping : 'isReceive'
		}, {
			name : 'ruleExplain',
			mapping : 'ruleExplain'
		}, {
			name : 'isArchiveConfirm',
			mapping : 'isArchiveConfirm'
		},
		// add by gao
		{
			name : 'confirmTime',
			mapping : 'confirmTime'
		}, {
			name : 'isPigeonhole',
			mapping : 'isPigeonhole'
		}]);
var reader = new Ext.data.JsonReader({
			totalProperty : 3,
			root : 'result'
		}, Employee);
SlEnterPriseProcreditMaterialsView = Ext.extend(Ext.Panel, {
	projId : null,
	gridPanel : null,
	isHidden_materials : true,
	isHiddenArchive : true,
	businessType : null,
	isHiddenEdit : true,
	isarchives : true,
	isAutoHeight : true,
	isProjectInfoEdit : false,
	// add by gao
	showConfirmInfo : false,
	editConfirmInfo : false,// this.editConfirmInfo?false:!this.showConfirmInfo,//可编辑则
							// 不会隐藏
	// end
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
		if (typeof(_cfg.isHiddenArchive) != "undefined") {
			this.isHiddenArchive = _cfg.isHiddenArchive;
		}
		if (typeof(_cfg.isarchives) != "undefined") {
			this.isarchives = _cfg.isarchives;
		}
		if (typeof(_cfg.isAutoHeight) != "undefined") {
			this.isAutoHeight = _cfg.isAutoHeight;
		}
		if (typeof(_isProjectInfoEdit)) {
			this.isProjectInfoEdit = _cfg.isProjectInfoEdit;
		}
		if (typeof(_cfg.showConfirmInfo)) {
			this.showConfirmInfo = _cfg.showConfirmInfo;
		}
		if (typeof(_cfg.editConfirmInfo)) {
			this.editConfirmInfo = _cfg.editConfirmInfo;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlEnterPriseProcreditMaterialsView.superclass.constructor.call(this, {
					layout : 'anchor',
					anchor : '100%',
					modal : true,
					items : [this.gridPanel]
				});
	},
	initUIComponents : function() {
		// add by gao 融资租赁特殊显示字段
		this.confirmDatefield = new Ext.form.DateField({
					format : 'Y-m-d',
					readOnly : !this.editConfirmInfo,
					allowBlank : false
				})

		var isPackCheckColumn = new Ext.grid.CheckColumn({
					header : "是否展示",
					width : 65,
					dataIndex : 'isPackShowMaterials',
					hidden : false
				});

		// end add by gao
		var obj = this.gridPanel;
		var isHidden = this.isHidden_materials;
		var fenRenderer = function(v) {

			if (v && v > 0) {
				return v + "份";
			} else {

				if (isHidden) {

					return '<font color=red>未填写</font>';
				} else {
					return '<font color=red>请单击编辑</font>';
				}

			}
		};
		var remarkRender = function(v) {
			if (v && v != "") {
				return v;
			} else {
				if (isHidden) {
					return '<font color=red>未填写</font>';
				} else {
					return '<font color=red>请单击编辑</font>';
				}
			}
		};
		var jsArr = [__ctxPath
				+ '/js/creditFlow/guarantee/materials/SlEnterpriseProcreditMaterialsForm.js'];
		$ImportSimpleJs(jsArr, null);
		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '添加1',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							}, '-', {
								iconCls : 'btn-delete',
								text : '隐藏',
								xtype : 'button',
								scope : this,
								handler : this.updateRs
							}]
				});
		this.gridPanel = new xg.EditorGridPanel({
              id:'procredMaterialsGp',
			store : new Ext.data.GroupingStore({
				proxy : new Ext.data.HttpProxy({
					url : __ctxPath
							+ '/materials/listEnterpriseSlProcreditMaterials.do'
				}),
				reader : reader,
				groupField : 'materialsType',
				baseParams : {
					projId : this.projId,
					businessType : this.businessType,
					operationType:this.operationType,
					show : true
				}
			}),
			 //plugins : [isPackCheckColumn],
			clicksToEdit : 1,
			tbar : isHidden ? null : this.topbar,
			cm : new xg.ColumnModel([
					// expander,
					new Ext.grid.RowNumberer({
								header : '序号',
								width : 35
							}), {
						header : "贷款材料类型",
						width : 110,
						sortable : true,
						dataIndex : 'materialsType',
						hidden : true
					}, {
						header : 'proMaterialsId',
						dataIndex : 'proMaterialsId',
						hidden : true
					}, {
						header : "贷款材料名称",
						dataIndex : 'materialsName'
					}, {
						header : "上传材料份数",
						fixed : true,
						width : 93,
						dataIndex : 'datumNums',
						renderer : function(v) {
							if (v && v > 0) {
								return v + "份";
							} else {

								return "0份";
							}
						}
					}, {
						header : "线下材料份数",
						fixed : true,
						width : 93,
						dataIndex : 'datumNumsOfLine',
						renderer : fenRenderer,
						editor : isHidden ? null : {
							xtype : 'numberfield',
							readOnly : isHidden
						}
					},isPackCheckColumn, {
						header : "是否收到",
						fixed : true,
						width : 67,
						dataIndex : 'isReceive',
						renderer : receiveRender,
						editor : isHidden ? null : new Ext.form.ComboBox({
									mode : 'local',
									editable : false,
									store : new Ext.data.SimpleStore({
												data : [['是', true],
														['否', false]],
												fields : ['text', 'value']
											}),
									readOnly : isHidden,
									displayField : 'text',
									valueField : 'value',
									triggerAction : 'all'
								})
					}, {
						header : "是否归档",
						fixed : true,
						width : 67,
						dataIndex : 'isArchiveConfirm',
						renderer : receiveRender,
						hidden : this.isHiddenArchive,
						editor : isHidden && this.isarchives
								? null
								: new Ext.form.ComboBox({
											mode : 'local',
											editable : false,
											store : new Ext.data.SimpleStore({
														data : [['是', true],
																['否', false]],
														fields : ['text',
																'value']
													}),
											readOnly : this.isHiddenArchive,
											displayField : 'text',
											valueField : 'value',
											triggerAction : 'all'
										})
					},
					// 是否已提交 add by gao
					{
						header : "是否提交",
						fixed : true,
						width : 67,
						dataIndex : 'isPigeonhole',
						renderer : receiveRender,
						hidden : this.editConfirmInfo
								? false
								: !this.showConfirmInfo,
						editor : this.editConfirmInfo ? new Ext.form.ComboBox({
									mode : 'local',
									editable : true,
									store : new Ext.data.SimpleStore({
												data : [['是', true],
														['否', false]],
												fields : ['text', 'value']
											}),
									readOnly : false,
									displayField : 'text',
									valueField : 'value',
									triggerAction : 'all'
								}) : null
					},
					// 提交时间 add by gao
					{
						header : '提交时间',
						format : 'Y-m-d',
						dataIndex : 'confirmTime',
						sortable : true,
						width : 82,
						hidden : this.editConfirmInfo
								? false
								: !this.showConfirmInfo,
						editor : this.confirmDatefield,
						renderer : ZW.ux.dateRenderer(this.confirmDatefield)
					}, {
						header : "备注",
						fixed : true,
						width : 153,
						dataIndex : 'remark',
						renderer : remarkRender,
						editor : isHidden && this.isarchives ? null : {
							xtype : 'textfield',
							readOnly : this.isHidden
						}
					}, {
						header : "上传或下载",
						fixed : true,
						width : 86,
						dataIndex : 'uploadOrDown',
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
						header : "预览",
						fixed : true,
						width : 40,
						dataIndex : 'viewPic',
						renderer : this.imageView
					}]),
			selModel : new Ext.grid.RowSelectionModel(),
			view : new Ext.grid.GroupingView({
						forceFit : true,
						groupTextTpl : '{text}'
					}),
			stripeRows : true,
			width : '80%',
			autoHeight : this.isAutoHeight,
			height : 395,
			border : false,
			plugins : [expander,isPackCheckColumn],
			animCollapse : false,
			iconCls : 'icon-grid',
			scope : this,
			listeners : {
				'cellclick' : function(grid, rowIndex, columnIndex, e) {

					var record = grid.getStore().getAt(rowIndex); // Get the
																	// Record
					var fieldName = grid.getColumnModel()
							.getDataIndex(columnIndex); // Get field name

					if ("uploadOrDown" == fieldName) {
						var loadData = null;
						var uploadFileCounts = 0;
						if (!this.ownerCt.isHiddenArchive
								&& !this.ownerCt.isProjectInfoEdit) {// 项目保前归档节点并且不在项目信息编辑页面中
							loadData = function() {
							};// 不需要执行该方法
							uploadFileCounts = 0;// 不能上传附件
						} else {
							if (!this.ownerCt.isHidden) {
								uploadFileCounts = 15;
							}
							loadData = function(size) {
								grid.getStore().getAt(rowIndex).set(
										"datumNums", size);
								Ext.Ajax.request({
									url : __ctxPath
											+ '/materials/updateDataNumSlProcreditMaterials.do',
									method : 'POST',
									success : function(response, request) {
										grid.getStore().getAt(rowIndex)
												.commit();
									},
									failure : function(response) {
										Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
									},
									params : {
										'slProcreditMaterials.datumNums' : size,
										'slProcreditMaterials.proMaterialsId' : grid
												.getStore().getAt(rowIndex)
												.get("proMaterialsId")
									}
								})
							}
						}
						var markId = grid.getStore().getAt(rowIndex)
								.get("proMaterialsId");
						var talbeName = "sl_procredit_materials.";
						var mark = talbeName + markId;
						if (isHidden) {
							uploadFileCounts = 0;
						}
						uploadfile("上传担保材料", mark, uploadFileCounts, null,
								null, loadData);
					}
					if ("viewPic" == fieldName) {
						var markId = grid.getStore().getAt(rowIndex)
								.get("proMaterialsId");
						var talbeName = "sl_procredit_materials.";
						var mark = talbeName + markId;
						picViewer(mark, this.isHiddenEdit);
					}
				},
				'afteredit' : function(e) {
					var remark = e.record.get('remark');
					var datumNums = e.record.get('datumNums');
					var datumNumsOfLine = e.record.get('datumNumsOfLine');
					var isReceive = e.record.get('isReceive');
					var isArchiveConfirm = e.record.get('isArchiveConfirm');
					var proMaterialsId = e.record.get('proMaterialsId');
					// add by gao
					var isPigeonhole = e.record.get('isPigeonhole');
					var confirmTime = e.record.get('confirmTime');
					Ext.Ajax.request({
						url : __ctxPath
								+ '/materials/updateAfterSlProcreditMaterials.do',
						method : 'POST',
						success : function(response, request) {
							e.record.commit();
						},
						failure : function(response) {
							Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
						},
						params : {
							'slProcreditMaterials.datumNums' : datumNums,
							'slProcreditMaterials.datumNumsOfLine' : datumNumsOfLine,
							'slProcreditMaterials.isReceive' : isReceive,
							'slProcreditMaterials.proMaterialsId' : proMaterialsId,
							'slProcreditMaterials.remark' : remark,
							'slProcreditMaterials.isArchiveConfirm' : isArchiveConfirm,
							// add by gao
							'slProcreditMaterials.isPigeonhole' : isPigeonhole,
							'slProcreditMaterials.confirmTime' : confirmTime
						}
					})
				}
			}
		});
		this.gridPanel.getStore().load();
	},
	createRs : function() {
		new SlProcreditMaterialsEnterpriseForm({
					projectId : this.projId,
					operateObj : this.gridPanel,
					show : false,
					businessType : this.businessType
				}).show();
	},
	updateRs : function() {
		new SlProcreditMaterialsEnterpriseForm({
					projectId : this.projId,
					operateObj : this.gridPanel,
					show : true,
					businessType : this.businessType
				}).show(); // 改回原来的 gao
	},
	imageView : function() {
		return "<img src='" + __ctxPath + "/images/btn/read_document.png'>";
	}

});
