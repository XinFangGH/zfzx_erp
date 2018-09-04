/**
 * @author lisl
 * @class SlBreachDetailView
 * @description 违约处理详情
 * @extends Ext.Window
 */
SlBreachDetailView = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlBreachDetailView.superclass.constructor.call(this, {
			title : '违约处理详情',
			modal : true,
			width : 1125,
			height : 428,
			autoScroll : true,
			items : [this.panel]
		});
	},
	initUIComponents : function() {
		this.panel = new Ext.form.FormPanel({
			frame : true,
			region : 'center',
			layout : 'form',
			bodyStyle : 'padding:15px,15px,15px,15px',
			defaults : {
				anchor : '100%,100%'
			},
			items : [{
				fieldLabel : "是否将此客户",
				name : "fcbm_ip",
				xtype : 'checkboxgroup',
				disabled : true,
				ctCls : "data_tab_tdr2",
				items : [{
					boxLabel : '加入黑名单',
					name : 'cb-col-1',
					checked : this.isListed == true ? true : false
				}]
			}, {
				xtype : 'panel',
				border : false,
				anchor : "100%",
				bodyStyle : 'margin-bottom:5px',
				html : '<B><font class="x-myZW-fieldset-title">'
						+ '【放款收息信息处理】</font></B><font class="x-myZW-fieldset-title">（</font>颜色预警：<font color=red>逾期款项</font>&nbsp;&nbsp<font style="line-height:20px">未结清项</font>&nbsp;&nbsp<font color=gray>已结清项</font><font class="x-myZW-fieldset-title" >）：</font>'
			}, new SlFundIntentViewVM({
				projectId : this.projectId,
				isHidden1 : true,
				businessType : 'SmallLoan',
				isHiddenCanBtn : true,
				isHiddenResBtn : true,
				enableEdit : true,
				isHiddenTitle : true
			}), new DZYMortgageView({
				projectId : this.projectId,
				titleText : '抵质押物处理',
				businessType : 'SmallLoan',
				isHiddenAddBtn : true,
				isHiddenDelBtn : true,
				isHiddenEdiBtn : true,
				isHiddenAddContractBtn : true,
				isHiddenDelContractBtn : true,
				isHiddenEdiContractBtn : true,
				isHiddenRelieve : true,
				isblHidden : true,
				isgdHidden : true,
				isSeeContractHidden : false,
				isHandleHidden : false,
				isHandleEdit : false
			}), {
				xtype : 'panel',
				border : false,
				anchor : "100%",
				bodyStyle : 'margin-bottom:5px;margin-top:15px',
				html : '<B><font class="x-myZW-fieldset-title">'
						+ '【违约处理说明文档】</font></B>'
			}, new uploads({
				title_c : '上传违约处理说明文档',
				anchor : "100%",
				isHidden : true,
				typeisfile : 'breachSmallloan',
				projectId : this.projectId
			}), {
				xtype : "textarea",
				fieldLabel : "违约说明",
				name : "comments",
				style : 'margin-top:5px;',
				anchor : "100%",
				readOnly : true,
				value : this.breachComment != null ? this.breachComment : ""
			}]
		});
	}
});