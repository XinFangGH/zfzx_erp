/**
 * @author zhangyl
 * @createtime
 * @class SlProcreditAssuretenetedForm
 * @extends Ext.form.FieldSet
 * @description 企业贷款准入原则
 * @company 北京智维软件有限公司
 */
SlProcreditAssuretenetedForm = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	projId : null,
	businessType : null,
	headTitle : "企业贷款准入原则",
	isEditBbusinessmanageropinion : false,
	isEditRiskmanageropinion : true,
	isEditRiskmanagercombox : false,// 用来控制是否能编辑风险经理意见 默认是不能编辑 true 表示可以编辑
	isAutoHeight : true,
	constructor : function(_cfg) {

		if (typeof(_cfg.projectId) != "undefined") {
			this.projId = _cfg.projectId;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.headTitle) != "undefined") {
			this.headTitle = _cfg.headTitle;
		}
		if (typeof(_cfg.isEditBbusinessmanageropinion) != "undefined") {
			this.isEditBbusinessmanageropinion = _cfg.isEditBbusinessmanageropinion;
		}
		if (typeof(_cfg.isEditRiskmanageropinion) != "undefined") {
			this.isEditRiskmanageropinion = _cfg.isEditRiskmanageropinion;
		}
		if (typeof(_cfg.isEditRiskmanagercombox) != "undefined") {
			this.isEditRiskmanagercombox = _cfg.isEditRiskmanagercombox;
		}
		if (typeof(_cfg.isAutoHeight) != "undefined") {
			this.isAutoHeight = _cfg.isAutoHeight;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlProcreditAssuretenetedForm.superclass.constructor.call(this, {
					id : 'SlProcreditAssuretenetedFormWin',
					items : this.gridPanel
				});
	},
	initUIComponents : function() {
		var objP = this;
		this.comBox = new Ext.form.ComboBox({
			mode : 'local',
			editable : false,
			store : new Ext.data.SimpleStore({
						data : [['符合', '符合'], ['不符合', '不符合'], ['无法核实', '无法核实']],
						fields : ['text', 'value']
					}),
			displayField : 'text',
			valueField : 'value',
			triggerAction : 'all'
		});
		this.gridPanel = new HT.EditorGridPanel({
			async : false,
			hiddenCm : true,
			clicksToEdit : 1,
			border : false,
			isShowTbar : false,
			showPaging : false,
			autoHeight : this.isAutoHeight,
			height : 334,
			url : __ctxPath + '/assuretenet/listSlProcreditAssuretenet.do',
			fields : ['assuretenetId', 'assuretenet', 'projid', 'sortvalue',
					'logger', 'businessmanageropinion', 'riskmanageropinion'],
			baseParams : {
				projId : this.projId,
				businessType : this.businessType
			},
			columns : [
				{
						header : this.headTitle,
						sortable : true,
						width : 880,
						dataIndex : 'assuretenet'
					}
					,
					
						{
						header : '业务经理意见',
						width : 94,
						sortable : true,
						editor : this.isEditBbusinessmanageropinion
								? this.comBox
								: null,
						dataIndex : 'businessmanageropinion',
						hidden : this.isEditRiskmanageropinion,
						renderer : function(v, metaData, record, rowIndex,
								colIndex, store) {
							if (v == '' || !v) {
								if (objP.isEditBbusinessmanageropinion) {
									return '<font color=red>请点击选择</font>';
								} else {
									return '未选择意见';
								}
							} else if (v == '符合') {
								return '<font color=green>' + v + '</font>';
							} else if (v == '不符合') {
								return '<font color=red>' + v + '</font>';
							} else if (v == '无法核实') {
								return '<font color=blue>' + v + '</font>';
							} else {
								return v;
							}
						}
					}, {
						header : '风险经理意见',
						sortable : true,
						width : 94,
						editor : this.isEditRiskmanagercombox
								? this.comBox
								: null,
						hidden : this.isEditRiskmanageropinion,
						dataIndex : 'riskmanageropinion',
						renderer : function(v, metaData, record, rowIndex,
								colIndex, store) {
							if (v == '' || !v) {
								if (objP.isEditRiskmanagercombox) {
									return '<font color=red>请点击选择</font>';
								} else {
									return '未选择意见';
								}
							} else if (v == '符合') {
								return '<font color=green>' + v + '</font>';
							} else if (v == '不符合') {
								return '<font color=red>' + v + '</font>';
							} else if (v == '无法核实') {
								return '<font color=blue>' + v + '</font>';
							} else {
								return v;
							}
						}
					}
					
					],
			listeners : {
				afteredit : function(e) {
					var args;
					var value = e.value;
					var id = e.record.data['assuretenetId'];
					if (e.originalValue != e.value) {// 编辑行数据发生改变
						if (e.field == 'businessmanageropinion') {
							args = {
								'slProcreditAssuretenet.businessmanageropinion' : value,
								'slProcreditAssuretenet.assuretenetId' : id
							};
						}
						if (e.field == 'riskmanageropinion') {
							args = {
								'slProcreditAssuretenet.riskmanageropinion' : value,
								'slProcreditAssuretenet.assuretenetId' : id
							};
						}
						Ext.Ajax.request({
							url : __ctxPath
									+ '/assuretenet/saveSlProcreditAssuretenet.do',
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

	assuretenetRenderer : function(v) {

	}
});