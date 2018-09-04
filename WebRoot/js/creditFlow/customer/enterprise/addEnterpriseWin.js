var jsonIsicRev1 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : ''
						}),
				reader : new Ext.data.JsonReader({
							root : 'topics'
						}, [{ 
									name : 'code',
									mapping : 'code'
								}, {
									name : 'description',
									mapping : 'description'
								}, {
									name : 'sortorder',
									mapping : 'sortorder'
								}, {
									name : 'explanatoryNoteInclusion',
									mapping : 'explanatoryNoteInclusion'
								}, {
									name : 'explanatoryNoteExclusion',
									mapping : 'explanatoryNoteExclusion'
								}])
			});
	jsonIsicRev1.load();

	// 第二个下拉框
	var jsonIsicRev2 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : ''
						}),
				reader : new Ext.data.JsonReader({
							root : 'topics'
						}, [{
									name : 'code',
									mapping : 'code'
								}, {
									name : 'description',
									mapping : 'description'
								}, {
									name : 'sortorder',
									mapping : 'sortorder'
								}, {
									name : 'explanatoryNoteInclusion',
									mapping : 'explanatoryNoteInclusion'
								}, {
									name : 'explanatoryNoteExclusion',
									mapping : 'explanatoryNoteExclusion'
								}])
			});
	
		// 第三个下拉框
	var jsonIsicRev3 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : ''
						}),
				reader : new Ext.data.JsonReader({
							root : 'topics'
						}, [{
									name : 'code',
									mapping : 'code'
								}, {
									name : 'description',
									mapping : 'description'
								}, {
									name : 'sortorder',
									mapping : 'sortorder'
								}, {
									name : 'explanatoryNoteInclusion',
									mapping : 'explanatoryNoteInclusion'
								}, {
									name : 'explanatoryNoteExclusion',
									mapping : 'explanatoryNoteExclusion'
								}])
			});
	// 第四个下拉框
	var jsonIsicRev4 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : ''
						}),
				reader : new Ext.data.JsonReader({
							root : 'topics'
						}, [{
									name : 'code',
									mapping : 'code'
								}, {
									name : 'description',
									mapping : 'description'
								}, {
									name : 'sortorder',
									mapping : 'sortorder'
								}, {
									name : 'explanatoryNoteInclusion',
									mapping : 'explanatoryNoteInclusion'
								}, {
									name : 'explanatoryNoteExclusion',
									mapping : 'explanatoryNoteExclusion'
								}])
	});
	var a ;
var panel_add = new Ext.form.FormPanel({
		id : 'enterAddFromRefer',
		labelAlign : 'right',
		buttonAlign : 'center',
		width: (screen.width-180)*0.7 - 30 ,
		frame : true ,
		url:'ajaxAddEnterprise.do',
		monitorValid : true,
		labelWidth : 110,
		autoScroll : true ,
		bodyStyle : 'overflowX:hidden',
		renderTo : 'addEnterpriseWinDiv',
		layout :'column',
		border : false,
		defaults : {
			layout : 'form',
			border : false,
			columnWidth:.5
		},
        items:[{
        	columnWidth : 1,
        	items : [{
        		id : 'enterName',
                xtype:'textfield',
                fieldLabel: '企业名称',
                allowBlank : false ,
                blankText : '企业名称不允许空',
                emptyText : '请输入企业名称',
                anchor : '100%',
                name: 'enterprise.enterprisename',
                listeners : {
					'blur':function(){
						existenterNameAction(this,"validatorEnterNameAjaxValidator","该企业已存在！");
					}
                }
            }]
        },{
            defaults : {
				xtype : 'textfield',
				anchor : '100%'
			},
            items: [{
                fieldLabel: '企业简称',
                name: 'enterprise.shortname',
                allowBlank : false,
				blankText : '企业简称不允许空',
				emptyText : '请输入企业简称'
            },{
            	id : 'legalpersonName',
            	xtype : 'combo',
                fieldLabel: '法人',
                triggerClass :'x-form-search-trigger',
                hiddenName : 'legalperson',
                onTriggerClick : function(){
					selectPWNameTwo(selectLegalperson);
				},
				resizable : true,
				mode : 'romote',
				editable : true,
				lazyInit : false,
				allowBlank : false,
				typeAhead : true,
				minChars : 1,
				listWidth : 150,
				store : new Ext.data.JsonStore({
					url : __ctxPath + '/creditFlow/customer/person/ajaxQueryForComboPerson.do?isAll='+isGranted('_detail_sygrkh'),
					root : 'topics',
					autoLoad : true,
					fields : [{
								name : 'id'
							}, {
								name : 'name'
							}],
					listeners : {
						'load' : function(s,r,o){
							if(s.getCount()==0){
								Ext.getCmp('legalpersonName').markInvalid('没有查找到匹配的记录') ;
							}
						}
					}
				}),
				displayField : 'name',
				valueField : 'id',
				triggerAction : 'all',
				listeners : {
					'select' : function(combo,record,index){
						Ext.getCmp('legalpersonId').setValue(record.get('id'));
						Ext.getCmp('legalpersonName').setValue(record.get('name'));
					},'blur' : function(f){
						if(f.getValue()!=null&&f.getValue()!=''){
							Ext.getCmp('legalpersonId').setValue(f.getValue());
						}
					}
				}
				//
            },
            
             {
					columnWidth:.3,
		            layout: 'form',
		            defaults : {anchor : anchor},
		            items :[{
					xtype : "diccombo",
					hiddenName : 'enterprise.hangyeType',
					itemName : '行业类型', // xx代表分类名称
					fieldLabel : "行业类型",
					readOnly : this.isAllReadOnly,
					anchor : '100%',
					editable : false,
					//emptyText : "请选择",
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox.getValue());
							})
						}
					}
		            }]}
            
            ,{
            	fieldLabel: '营业期限(年)',
                name : 'enterprise.businessterm'
            }, {
            	xtype : 'datefield',
            	format : 'Y-m-d',
                fieldLabel: '工商注册时间',
                name : 'enterprise.registerdate'
            },{
            	xtype : 'numberfield',
                fieldLabel: '工商注册资金(万)',
                name : 'enterprise.registermoney'
            },{
            	xtype: 'radiogroup',
                fieldLabel: '工商是否按时年检',
                items: [
                    {boxLabel: '是', name: 'enterprise.gslexamine', inputValue: true},
                    {boxLabel: '否', name: 'enterprise.gslexamine', inputValue: false}
                ]
            }]
        },{
        	defaults : {
				xtype : 'textfield',
				anchor : '100%'
			},
            items: [{
				fieldLabel : '组织机构代码',
				allowBlank : false ,
                blankText : '组织机构代码不允许空',
				name : 'enterprise.organizecode'
            }, {
            	id : 'controlpersonName',
            	xtype : 'combo',
            	fieldLabel : '实际控制人',
            	triggerClass :'x-form-search-trigger',
            	hiddenName : 'controlperson',
            	editable : false,
            	onTriggerClick : function(){
					selectPWNameTwo(selectControlperson);
				},
				resizable : true,
				mode : 'romote',
				editable : true,
				lazyInit : false,
				allowBlank : false,
				typeAhead : true,
				minChars : 1,
				listWidth : 150,
				store : new Ext.data.JsonStore({
					url : '../person/ajaxQueryPersonForCombo.do?isAll='+isGranted('_detail_sygrkh'),
					root : 'topics',
					autoLoad : true,
					fields : [{
								name : 'id'
							}, {
								name : 'name'
							}],
					listeners : {
						'load' : function(s,r,o){
							if(s.getCount()==0){
								Ext.getCmp('controlpersonName').markInvalid('没有查找到匹配的记录') ;
							}
						}
					}
				}),
				displayField : 'name',
				valueField : 'id',
				triggerAction : 'all',
				listeners : {
					'select' : function(combo,record,index){
						Ext.getCmp('controlpersonId').setValue(record.get('id'));
						Ext.getCmp('controlpersonName').setValue(record.get('name'));
					},'blur' : function(f){
						if(f.getValue()!=null&&f.getValue()!=''){
							Ext.getCmp('controlpersonId').setValue(f.getValue());
						}
					}
				}
            },
            {
            	xtype : 'csRemoteCombo',
				fieldLabel : '所有制性质',
				allowBlank : false ,
				blankText : '必填信息',
				hiddenName : 'enterprise.ownership',
				dicId : ownershipDicId
            },{
            	fieldLabel : '营业执照号码',
            	blankText : "营业执照号码不能为空，请正确填写!",
				allowBlank : false,
				name : 'enterprise.cciaa'
            },{
            	xtype : 'csRemoteCombo',
				fieldLabel : '注册资金币种',
				hiddenName : 'enterprise.capitalkind',
				dicId : currencytypeDicId
            }, {
            	xtype : 'datefield',
            	format : 'Y-m-d',
                fieldLabel: '下一年检时间',
                name : 'enterprise.gslexaminedate'
            },{
            	id : 'linkmanId',
            	name : 'enterprise.linkmampersonid',
            	xtype : 'hidden'
            },{
            	id : 'legalpersonId',
            	name : 'enterprise.legalpersonid',
            	xtype : 'hidden'
            },{
            	id : 'controlpersonId',
            	name : 'enterprise.controlpersonid',
            	xtype : 'hidden'
            }]
        },{
        	columnWidth : 1,
        	items : [{
        		xtype : 'textfield',
        		fieldLabel: '工商登记机关',
				anchor : '100%',
                name : 'enterprise.gslname'
        	}]
        },{
        	columnWidth : 1,
        	items : [{
            	fieldLabel : '国税机关名称',
            	xtype : 'textfield',
				anchor : '100%',
            	name : 'enterprise.taxname'
            }]
        },{
        	items : [{
            	xtype : 'textfield',
                fieldLabel: '国税登记号码',
                anchor : '100%',
                name : 'enterprise.taxnum'
            }]
        },{
        	items : [{
                xtype: 'radiogroup',
                fieldLabel: '国税是否按时年检',
                editable : false,
                items: [
                    {boxLabel: '是', name: 'enterprise.taxexamine', inputValue:true},
                    {boxLabel: '否', name: 'enterprise.taxexamine', inputValue:false}
                ]
            }]
        },{
        	columnWidth : 1,
        	defaults : {
				xtype : 'textfield',
				anchor : '100%'
			},
			items : [{
            	fieldLabel : '注册地址',
            	name : 'enterprise.address'
            },{
                fieldLabel: '实际经营地址',
                name : 'enterprise.factaddress'
            },{
				xtype : 'textarea',
				fieldLabel : '核准经营范围',
				name : 'enterprise.managescope'
			},{
            	fieldLabel : '企业网址',
            	xtype : 'textfield',
            	name : 'enterprise.website'
            }, {
                fieldLabel: '企业所属区域',
                name : 'enterprise.area'
            }/*,{
            	id : 'hiddentradetype',
            	xtype : 'hidden',
            	name : 'enterprise.hangyeType'
            }*/]
        } ]
	});
	var selectLinkman = function(obj){
		Ext.getCmp('linkmanName').setValue(obj.name);
		Ext.getCmp('linkmanId').setValue(obj.id) ;
	};
	var selectLegalperson = function(obj){
		Ext.getCmp('legalpersonName').setValue(obj.name);
		Ext.getCmp('legalpersonId').setValue(obj.id) ;
	}
	var selectControlperson = function(obj){
		Ext.getCmp('controlpersonName').setValue(obj.name);
		Ext.getCmp('controlpersonId').setValue(obj.id) ;
	}