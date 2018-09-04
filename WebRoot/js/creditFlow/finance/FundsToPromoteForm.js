/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京金智万维软件有限公司
 */
FundsToPromoteForm = Ext.extend(Ext.Panel, {
	layout : 'form',
	anchor : '100%',
	
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		//this.initUIComponents();
		FundsToPromoteForm.superclass.constructor.call(this, {
			items :[{
				xtype : 'radio',
				boxLabel : '邮件推介',
				fieldLabel : '推介方式',
				checked :true
			},{
				xtype : 'textfield',
				fieldLabel :'推介标题',
				name : 'fundsToPromote.title',
				allowBlank : false,
				blankText : "推介标题不能为空，请正确填写!",
				anchor : '96%'
			},{
				xtype : "combo",
				triggerClass : 'x-form-search-trigger',
				hiddenName : "fundsToPromote.investPersonName",
				editable : false,
				fieldLabel : "投资客户",
				blankText : "投资客户不能为空，请正确填写!",
				allowBlank : false,
				readOnly : this.isAllReadOnly,
				anchor : "96%",
				scope :this,
				onTriggerClick : function() {
					var combox=this;
					var selectPerson=function(obj){
						combox.setValue(obj.perName)
						combox.nextSibling().setValue(obj.perId)
					}
					selectInvestPerson(selectPerson)
				}
			},{
				xtype : 'hidden',
				name : 'fundsToPromote.investPersonId'
			},{
				xtype : "combo",
				anchor : "96%",
				triggerClass : 'x-form-search-trigger',
				hiddenName : "fundsToPromote.fileId",
				displayField : 'itemName',
				valueField : 'itemId',
				triggerAction : 'all',
				multiSelect : false,
				allowBlank : false,
				blankText : "推介内容不能为空，请正确填写!",
				store : new Ext.data.SimpleStore({
					autoLoad : true,
					url : __ctxPath
							+ '/creditFlow/common/getFileListFileForm.do?mark=promote_package.'+this.projectId,
					fields : ['itemId', 'itemName']
				}),
				fieldLabel : "推介内容(附件)",
				blankText : "推介内容不能为空，请正确填写!"
			},{
				xtype : 'hidden',
				name : 'fundsToPromote.projectId',
				value : this.projectId
			}]
		});
		
	},
	
	initUIComponents : function() {
		
		this.formPanel = new Ext.form.FormPanel({
			labelWidth : 90,
			labelAlign : 'right',
			height : 400,
			width : 500,
			//buttonAlign : 'center',
			border : false,
			frame :true,
			items : [{
				xtype : 'radio',
				boxLabel : '邮件推介',
				fieldLabel : '推介方式',
				checked :true
			},{
				xtype : 'textfield',
				fieldLabel :'推介标题',
				name : 'fundsToPromote.title',
				allowBlank : false,
				blankText : "推介标题不能为空，请正确填写!",
				anchor : '96%'
			},{
				xtype : "combo",
				triggerClass : 'x-form-search-trigger',
				hiddenName : "fundsToPromote.investPersonName",
				editable : false,
				fieldLabel : "投资客户",
				blankText : "投资客户不能为空，请正确填写!",
				allowBlank : false,
				readOnly : this.isAllReadOnly,
				anchor : "96%",
				scope :this,
				onTriggerClick : function() {
					var combox=this;
					var selectPerson=function(obj){
						combox.setValue(obj.perName)
						combox.nextSibling().setValue(obj.perId)
					}
					selectInvestPerson(selectPerson)
				}
			},{
				xtype : 'hidden',
				name : 'fundsToPromote.investPersonId'
			},{
				xtype : "combo",
				anchor : "96%",
				triggerClass : 'x-form-search-trigger',
				hiddenName : "fundsToPromote.fileId",
				displayField : 'itemName',
				valueField : 'itemId',
				triggerAction : 'all',
				multiSelect : false,
				allowBlank : false,
				blankText : "推介内容不能为空，请正确填写!",
				store : new Ext.data.SimpleStore({
					autoLoad : true,
					url : __ctxPath
							+ '/creditFlow/common/getFileListFileForm.do?mark=promote_package.'+this.projectId,
					fields : ['itemId', 'itemName']
				}),
				fieldLabel : "推介内容(附件)",
				blankText : "推介内容不能为空，请正确填写!"
			},{
				xtype : 'hidden',
				name : 'fundsToPromote.projectId',
				value : this.projectId
			}]
		});
	},
	
	reset : function() {
		this.formPanel.getForm().reset();
	},
	cancel : function() {
		this.close();
	},
	save : function() {
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath+ '/creditFlow/finance/saveInfoFundsToPromote.do',
					callback : function(fp, action) {
						if(this.gridPanel!=null||typeof(this.gridPanel)!="undefined"){
							this.gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	}

});
