//CsProspectiveRelationView.js
CsProspectiveRelationView = Ext.extend(Ext.Window, {
	isLook : false,
	//isReadOnly : false,
	// 构造函数
	constructor : function(_cfg) {
		
		if (_cfg == null) {
			_cfg = {};
		};
		
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		CsProspectiveRelationView.superclass.constructor.call(this, {
					layout : 'fit',
					autoScroll:true,
					items : this.formPanel,
					modal : true,
					height : 300,
					width : 600,
					maximizable : true,
					title : "联系人信息",
					buttonAlign : 'center',
					items : [this.PersonRelationView],
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								hidden : this.isLook,
								scope : this,
								handler : this.save
							},{
								text : '取消',
								iconCls : 'btn-cancel',
								hidden : this.isLook,
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
	//	alert(this.perId)
		this.PersonRelationView =new PersonRelationView({
			perId:this.perId,
			isReadOnly:this.isReadOnly,
			isHiddenAddBtn:this.isReadOnly,
			isHiddenDelBtn:this.isReadOnly
		})
	},// end of the initcomponents

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
		var flashTargat =this.flashTargat;
		var vDates=this.PersonRelationView.getSourceGridDate();
		
		var perId =this.perId;
		var panel =this;
		Ext.Ajax.request({
						url : __ctxPath + "/creditFlow/customer/customerProsperctiveRelation/saveListBpCustProspectiveRelation.do",
						method : 'POST',
						scope:this,
						success :function(response, request){
							var object=Ext.util.JSON.decode(response.responseText);
							panel.close();
						},
						params : {
							"perId":perId,
							"relationPerson" : vDates
						}
			       })
	}// end of save

});