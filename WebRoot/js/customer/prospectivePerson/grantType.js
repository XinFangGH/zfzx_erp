//按创建人
GrantByCreator = Ext.extend(Ext.Panel, {
	layout:"form",
	autoHeight : true,
	border:false,
	isRead : false,
	isDiligenceReadOnly : false,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isRead) != 'undefined') {
			this.isRead = _cfg.isRead;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 105;
			CsProspectivePersonFollowView.superclass.constructor.call(this, {
				layout : "column",
				border : false,
				style :'padding-top:20px',
				title:"按创建人",
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'right',
					labelWidth : leftlabel
				},
				items : [{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							border : false,
							items : [{
									xtype : "combo",
									triggerClass : 'x-form-search-trigger',
									hiddenName : "creatorId1",
									editable : false,
									fieldLabel : "创建人",
									blankText : "创建人不能为空，请正确填写!",
									allowBlank : false,
									anchor : "90%",
									onTriggerClick : function(cc) {
										var obj = this;
										var appuerIdObj = obj.nextSibling();
										var userIds = appuerIdObj.getValue();
										if ("" == obj.getValue()) {
											userIds = "";
										}
										new UserDialog({
											userIds : userIds,
											userName : obj.getValue(),
											single : false,
											title : "选择创建人",
											callback : function(uId, uname) {
												obj.setValue(uId);
												obj.setRawValue(uname);
												appuerIdObj.setValue(uId);
											}
										}).show();
									}
								},{
	                       	 	xtype : 'hidden',
	                        	name : 'creatorId'
							}]
						},{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							labelAlign : 'right',
							border : false,
							items : [{
									xtype : "combo",
									triggerClass : 'x-form-search-trigger',
									hiddenName : "grantCreatorId11",
									editable : false,
									fieldLabel : "分配人",
									blankText : "跟进人不能为空，请正确填写!",
									allowBlank : false,
									anchor : "90%",
									onTriggerClick : function(cc) {
										var obj = this;
										var appuerIdObj = obj.nextSibling();
										var userIds = appuerIdObj.getValue();
										if ("" == obj.getValue()) {
											userIds = "";
										}
										new UserDialog({
											userIds : userIds,
											userName : obj.getValue(),
											single : false,
											title : "选择跟进人",
											callback : function(uId, uname) {
												obj.setValue(uId);
												obj.setRawValue(uname);
												appuerIdObj.setValue(uId);
											}
										}).show();
									}
								},{
	                       	 	xtype : 'hidden',
	                        	name : 'grantCreatorId'
							}]
						}]
			/*}]*/
		});
	},
	initComponents : function() {
	},
	cc : function() {
	}
});


//按共享人
GrantByBelongId = Ext.extend(Ext.Panel, {
	layout:"form",
	autoHeight : true,
	style :'padding-top:20px',
	isRead : false,
	isDiligenceReadOnly : false,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isRead) != 'undefined') {
			this.isRead = _cfg.isRead;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 105;
			GrantByBelongId.superclass.constructor.call(this, {
				layout : "column",
				border : false,
				title:"按共享人",
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'right',
					labelWidth : leftlabel
				},
				items : [{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							border : false,
							items : [{
									xtype : "combo",
									triggerClass : 'x-form-search-trigger',
									hiddenName : "belongId1",
									editable : false,
									fieldLabel : "共享人",
									blankText : "创建人不能为空，请正确填写!",
									allowBlank : false,
									anchor : "90%",
									onTriggerClick : function(cc) {
										var obj = this;
										var appuerIdObj = obj.nextSibling();
										var userIds = appuerIdObj.getValue();
										if ("" == obj.getValue()) {
											userIds = "";
										}
										new UserDialog({
											userIds : userIds,
											userName : obj.getValue(),
											single : false,
											title : "选择创建人",
											callback : function(uId, uname) {
												obj.setValue(uId);
												obj.setRawValue(uname);
												appuerIdObj.setValue(uId);
											}
										}).show();
									}
								},{
	                       	 	xtype : 'hidden',
	                        	name : 'belongId'
							}]
						},{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							labelAlign : 'right',
							border : false,
							items : [{
									xtype : "combo",
									triggerClass : 'x-form-search-trigger',
									hiddenName : "grantbelongId1",
									editable : false,
									fieldLabel : "分配人",
									blankText : "跟进人不能为空，请正确填写!",
									allowBlank : false,
									anchor : "90%",
									onTriggerClick : function(cc) {
										var obj = this;
										var appuerIdObj = obj.nextSibling();
										var userIds = appuerIdObj.getValue();
										if ("" == obj.getValue()) {
											userIds = "";
										}
										new UserDialog({
											userIds : userIds,
											userName : obj.getValue(),
											single : false,
											title : "选择跟进人",
											callback : function(uId, uname) {
												obj.setValue(uId);
												obj.setRawValue(uname);
												appuerIdObj.setValue(uId);
											}
										}).show();
									}
								},{
	                       	 	xtype : 'hidden',
	                        	name : 'grantBelongId'
							}]
						}]
			/*}]*/
		});
	},
	initComponents : function() {
	},
	cc : function() {
	}
});

//按地区
GrantByAreaId = Ext.extend(Ext.Panel, {
	layout:"form",
	autoHeight : true,
	style :'padding-top:20px',
	isRead : false,
	isDiligenceReadOnly : false,
	labelAlign : 'right',
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		if (typeof(_cfg.isRead) != 'undefined') {
			this.isRead = _cfg.isRead;
		}
		Ext.applyIf(this, _cfg);
		this.initComponents();
		var leftlabel = 105;
			GrantByAreaId.superclass.constructor.call(this, {
				layout : "column",
				border : false,
				scope : this,
				title:"按地区",
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelAlign : 'right',
					labelWidth : leftlabel
				},
				items : [{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							border : false,
							items : [{
									xtype : 'textfield',
									fieldLabel : '地区',
									readOnly : this.isRead,
									name : 'area',
									scope:this,
									anchor : "90%",
									listeners : {
										'focus' : function() { 
											      var obj=this;
											      var getEnterAreaObjArray1=function(objArray){
											      obj.setValue(objArray[(objArray.length) - 1].text+ "_" + objArray[(objArray.length) - 2].text + "_"+ objArray[0].text);
												  obj.nextSibling().setValue(objArray[(objArray.length) - 1].id+ "," + objArray[(objArray.length) - 2].id + "," + objArray[0].id);
										    	   
										       }
											   selectDictionary('area',getEnterAreaObjArray1);
											}
										}
									}, {
										name : 'areaId',
										xtype : 'hidden'
									}]
						},{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							border : false,
							labelAlign : 'right',
							items : [{
									xtype : "combo",
									triggerClass : 'x-form-search-trigger',
									hiddenName : "grantAreaId1",
									editable : false,
									fieldLabel : "分配人",
									blankText : "跟进人不能为空，请正确填写!",
									allowBlank : false,
									anchor : "90%",
									onTriggerClick : function(cc) {
										var obj = this;
										var appuerIdObj = obj.nextSibling();
										var userIds = appuerIdObj.getValue();
										if ("" == obj.getValue()) {
											userIds = "";
										}
										new UserDialog({
											userIds : userIds,
											userName : obj.getValue(),
											single : false,
											title : "选择分配人",
											callback : function(uId, uname) {
												obj.setValue(uId);
												obj.setRawValue(uname);
												appuerIdObj.setValue(uId);
											}
										}).show();
									}
								},{
	                       	 	xtype : 'hidden',
	                        	name : 'grantAreaId'
							}]
						}]
			/*}]*/
		});
	},
	initComponents : function() {
	},
	cc : function() {
	}
});