/**
 * @author
 * @createtime
 * @class PlOrProPackForm
 * @extends Ext.Window
 * @description PlOrProPack表单
 * @company 智维软件
 */
BpFinanceApplyUserTypeForm = Ext.extend(Ext.Window, {
	// 构造函数
	loanId : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if(_cfg.loanId != 'undefined'){
			this.loanId = _cfg.loanId
		}
		
		if(_cfg.panel != 'undefined'){
			this.panel = _cfg.panel
		}
		// 必须先初始化组件
		this.initUIComponents();
		BpFinanceApplyUserTypeForm.superclass.constructor.call(this, {
					id : 'BpFinanceApplyUserTypeForm',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					autoScroll : true,
					height : 410,
					width : 800,
					maximizable : true,
					title : '详细信息',
					buttonAlign : 'center',
					buttons : [{
								text : '审批通过',
								iconCls : 'btn-pass',
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
	
		this.formPanel = new Ext.form.FormPanel({
			id : 'BpFinanceApplyUserTypeFormPanel',
			autoHeight : true,
			autoWidth : true,
			layout : 'column',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
				anchor : '96%',
				labelWidth : 60
			},
			items : [{
						name : 'bpFinanceApplyUser.loanId',
						xtype : 'hidden',
						value : this.loanId
					}, {
						xtype : 'fieldset',
						autoHeight : true,
						collapsible : true,
						bodyStyle : 'padding: 5px',
						title : '借款信息',
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "column", // 从上往下的布局
						items : [{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								xtype : "textfield",
								fieldLabel : '借款标题',
								name : 'bpFinanceApplyUser.loanTitle',
								readOnly : true,
								anchor : '100%'
							}]
						},{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								fieldLabel : '贷款类别',
								xtype : "textfield",
								name : 'bpFinanceApplyUser.proName',
								readOnly : true,
								anchor : '100%'
							}]
						}, {
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								xtype : "textfield",
								fieldLabel : '用户账号',
								name : 'bpFinanceApplyUser.appName',
								readOnly : true,
								anchor : '100%'
							}]
						}, {
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								fieldLabel : '真实姓名',
								xtype : "textfield",
								name : 'bpFinanceApplyUser.truename',
								readOnly : true,
								anchor : '100%'
							}]
						}, {
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								fieldLabel : '电话号码',
								xtype : "numberfield",
								name : 'bpFinanceApplyUser.telphone',
								readOnly : true,
								anchor : '100%'
							}]
						}, {
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								fieldLabel : '申请借款日期',
								xtype : "datefield",
								name : 'bpFinanceApplyUser.createTime',
								readOnly : true,
								anchor : '100%',
								format : 'Y-m-d'
							}]
						}, {
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								fieldLabel : '申请借款金额',
								xtype : "numberfield",
								name : 'bpFinanceApplyUser.loanMoney',
								readOnly : true,
								anchor : '100%'
							}]
						},{
							columnWidth : .46, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								xtype : "numberfield",
								fieldLabel : '贷款年化利率',
								name : 'bpFinanceApplyUser.yearAccrualRate',
								readOnly : true,
								anchor : '100%'
							}]
						},{
							columnWidth : .04,
							layout : 'form',
							labelWidth : 20,
							labelAlign : 'left',
							items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .46, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								xtype : "numberfield",
								fieldLabel : '管理咨询费年化利率',
								name : 'bpFinanceApplyUser.yearManagementConsultingOfRate',
								readOnly : true,
								anchor : '100%'
							}]
						},{
							columnWidth : .04,
							layout : 'form',
							labelWidth : 20,
							labelAlign : 'left',
							items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .46, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								xtype : "numberfield",
								fieldLabel : '财务服务费年化利率',
								name : 'bpFinanceApplyUser.yearFinanceServiceOfRate',
								readOnly : true,
								anchor : '100%'
							}]
						},{
							columnWidth : .04,
							layout : 'form',
							labelWidth : 20,
							labelAlign : 'left',
							items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							}]
						}, {
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								fieldLabel : '借款用途',
								xtype : "textfield",
								name : 'bpFinanceApplyUser.loanUseStr',
								readOnly : true,
								anchor : '100%'
							}]
						},  {
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								fieldLabel : '借款期限',
								xtype : "numberfield",
								name : 'bpFinanceApplyUser.loanTimeLen',
								readOnly : true,
								anchor : '100%'
							}]
						}]
					},{
						xtype : 'fieldset',
						autoHeight : true,
						collapsible : true,
						bodyStyle : 'padding: 5px',
						title : '审批信息',
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "column", // 从上往下的布局
						items : [{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								xtype : "moneyfield",
								fieldLabel : '金额',
								name : 'bpFinanceApplyUser.approvalLoanMoney',
								allowBlank : false,
								readOnly : false,
								anchor : '100%'
							}]
						},{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								fieldLabel : '期限',
								xtype : "numberfield",
								name : 'bpFinanceApplyUser.approvalLoanTimeLen',
								allowBlank : false,
								readOnly : false,
								anchor : '100%'
							}]
						}, {
							columnWidth : .46, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								xtype : "numberfield",
								fieldLabel : '贷款年化利率',
								name : 'bpFinanceApplyUser.approvalYearAccrualRate',
								allowBlank : false,
								readOnly : false,
								anchor : '100%'
							}]
						},{
							columnWidth : .04,
							layout : 'form',
							labelWidth : 20,
							labelAlign : 'left',
							items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							}]
						}, {
							columnWidth : .46, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								xtype : "numberfield",
								fieldLabel : '咨询管理费年化利率',
								name : 'bpFinanceApplyUser.approvalYearManagementConsultingOfRate',
								allowBlank : false,
								readOnly : false,
								anchor : '100%'
							}]
						},{
							columnWidth : .04,
							layout : 'form',
							labelWidth : 20,
							labelAlign : 'left',
							items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							}]
						}, {
							columnWidth : .46, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								xtype : "numberfield",
								fieldLabel : '财务服务费年化利率',
								name : 'bpFinanceApplyUser.approvalYearFinanceServiceOfRate',
								allowBlank : false,
								readOnly : false,
								anchor : '100%'
							}]
						},{
							columnWidth : .04,
							layout : 'form',
							labelWidth : 20,
							labelAlign : 'left',
							items : [{
								fieldLabel : "%",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .46, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								xtype : "numberfield",
								fieldLabel : '逾期贷款利率',
								name : 'bpFinanceApplyUser.approvalOverdueRateLoan',
								allowBlank : false,
								readOnly : false,
								anchor : '100%'
							}]
						},{
							columnWidth : .04,
							layout : 'form',
							labelWidth : 30,
							labelAlign : 'left',
							items : [{
								fieldLabel : "%/日",
								labelSeparator : '',
								anchor : "100%"
							}]
						},{
							columnWidth : .46, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								xtype : "numberfield",
								fieldLabel : '逾期罚息利率',
								name : 'bpFinanceApplyUser.approvalOverdueRate',
								allowBlank : false,
								readOnly : false,
								anchor : '100%'
							}]
						},{
							columnWidth : .04,
							layout : 'form',
							labelWidth : 30,
							labelAlign : 'left',
							items : [{
								fieldLabel : "%/日",
								labelSeparator : '',
								anchor : "100%"
							}]
						}/*, {
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 120,
							labelAlign : 'right',
							items : [{
								fieldLabel : "贷款用途",
								xtype : "dickeycombo",
								hiddenName : 'bpFinanceApplyUser.purposeType',
								displayField : 'itemName',
								readOnly : this.isAllReadOnly,
								itemName : '贷款用途',
								nodeKey : 'smallloan_purposeType',	
								emptyText : "请选择",
								editable : false,
								anchor : "100%",
								allowBlank : false,
								listeners : {
									scope : this,
								    afterrender : function(combox) {
									var st = combox.getStore();
										st.on("load", function() {
									    if (combox.getValue() == 0	|| combox.getValue() == 1
										 || combox.getValue() == "" || combox.getValue() == null) {
										    combox.setValue("");
										} else {
											combox.setValue(combox.getValue());
												}
											combox.clearInvalid();	})
																	}
									}
							}]
						}*/]
					}]
		});
		
		// 加载表单对应的数据
		

		if (this.loanId!=null && this.loanId != '') {
			this.formPanel.loadData({
				root : 'data',
				url : __ctxPath+ '/p2p/getDetailedBpFinanceApplyUser.do?loanId='+ this.loanId,
				preName : 'bpFinanceApplyUser',
				success : function(form, action) {
					var obj = Ext.util.JSON.decode(form.responseText);
				},
				failure : function(form, action) {
					Ext.ux.Toast.msg('编辑', '载入失败');
				}
			});

		}

	},

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		$postForm({
			formPanel : this.formPanel,
			msg : '审核成功！',
			scope : this,
			url : __ctxPath
					+ '/p2p/approvalByDataBpFinanceApplyUser.do',
			params : {
				loanId : this.loanId
			},
			callback : function(fp, action) {
				var gridPanel = this.panel;
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}
  
  
});