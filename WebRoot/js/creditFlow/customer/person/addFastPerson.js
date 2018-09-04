var addFastPersonPanel = new Ext.form.FormPanel({
	id : 'addFastPersonPanel',
	url : __ctxPath+'/creditFlow/customer/person/addInfoPerson.do',
	monitorValid : true,
	bodyStyle:'padding:10px',
	renderTo : 'addFastPersonDiv',
	autoScroll : true ,
	labelAlign : 'right',
	buttonAlign : 'center',
	height : 90,
	frame : true ,
	layout : 'column',
	items :[{
		columnWidth : .5,
		layout : 'form',
		labelWidth : 65,
		defaults : {anchor : '100%'},
		items :[{
			xtype : 'textfield',
			fieldLabel : '姓名',
			name : 'person.name',
			allowBlank : false,
			blankText : '姓名为必填内容'
			/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
			regexText : '只能输入中文'*/
		},{
			xtype : "dickeycombo",
			nodeKey :'card_type_key',
			fieldLabel : '证件类型',
			emptyText : '请选证件类型',
			hiddenName : 'person.cardtype',
			allowBlank : false,
			blankText : '证件类型为必填内容',
			//dicId : cardtypeDicId,
			listeners : {
				afterrender : function(combox) {
					var st = combox.getStore();
					st.on("load", function() {
						if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
							combox.setValue("");
						}else{
							combox.setValue(combox
								.getValue());
						}
						combox.clearInvalid();
					})
				},
				'change' : function(field,newValue,oldValue){
					if(newValue == 628){
						Ext.getCmp('cardnumberfast').index = 628;
						Ext.getCmp('cardnumberfast').regex ;
						Ext.getCmp('cardnumberfast').regexText = '身份证号码无效';
					}else if(newValue == 629){
						Ext.getCmp('cardnumberfast').index = 629;
						Ext.getCmp('cardnumberfast').regex  ;
						Ext.getCmp('cardnumberfast').regexText = '军官证号码无效';
					}else if(newValue == 630){
						Ext.getCmp('cardnumberfast').index = 630;
						Ext.getCmp('cardnumberfast').regex  ;
						Ext.getCmp('cardnumberfast').regexText = '护照号码无效';
					}else {
						Ext.getCmp('cardnumberfast').index = 0;
					}
				}
			}
		}]
	},{
		columnWidth : .5,
		layout : 'form',
		labelWidth : 65,
		defaults : {anchor : '100%'},
		items :[{
			xtype : "dickeycombo",
			nodeKey :'sex_key',
			fieldLabel : '性别',
			emptyText : '请选性别',
			hiddenName : 'person.sex',
			allowBlank : false,
			blankText : '性别为必填内容',
			afterrender : function(combox) {
				var st = combox.getStore();
				st.on("load", function() {
					if(combox.getValue() == 0||combox.getValue()==1||combox.getValue()==""||combox.getValue()==null){
						combox.setValue("");
					}else{
						combox.setValue(combox
							.getValue());
					}
					combox.clearInvalid();
				})
			}
		},{
			id : 'cardnumberfast',
			xtype : 'textfield',
			fieldLabel : '证件号码',
			name : 'person.cardnumber',
			allowBlank : false,
			blankText : '证件号码为必填内容',
			listeners : {
				'blur':function(f){
					ajaxUniquenessValidator(this,"ajaxValidatorCardPersonAjaxValidator","该人员已存在！");
				}
            }
		}]
	},{
		xtype : 'hidden',
		name : 'person.job'
	},{
		xtype : 'hidden',
		name : 'person.cellphone'
	}]
})