/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
DutypersonForm = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		DutypersonForm.superclass.constructor.call(this, {
			anchor : '95%',
			items : [{
				layout : 'column',
				xtype : 'fieldset',
				title : '填写<无限连带责任-个人>详细信息',
				collapsible : true,
				autoHeight : true,
				anchor : '95%',
				items : [{
					columnWidth : .6,
					layout : 'form',
					labelWidth : 100,
					defaults : {
						xtype : 'textfield',
						anchor : '100%'
					},
					items : [{
						id:'cardtype',
						xtype:'hidden',
						name:'cardtype'
					}, {
						id : 'card_number',
						xtype : 'textfield',
						fieldLabel : '证件号码',
						name : 'person.cardnumber',
						allowBlank : false,
						blankText : '证件号码为必填内容'
					}]
				}, {
					columnWidth : .4,
					layout : 'form',
					labelWidth : 110,
					defaults : {
						xtype : 'textfield',
						anchor : '100%'
					},
					items : [{
								id : 'person_phone',
								fieldLabel : '家庭电话',
								maxLength : 50,
								maxLengthText : '最大输入长度50',
								name : 'person.cellphone'
							}]
				},{
				   columnWidth : .6,
				   layout:'form',
				   items : [{
				   	   id : 'person_address',
				       xtype:'textfield',
				       fieldLabel:'家庭住址',
				       name : 'person.familyaddress',
				       anchor:'100%'
				   }]
				},{
				   columnWidth : .4,
				   layout : 'form',
				   items : [{
				   	    id :'person_ispublicservant',
				        xtype:'checkbox',
				        boxLabel : '是否为公务员',
				        name:'person.ispublicservant',
				        listeners:{
				           scope:this,
				           'check':function(box,newValue,oldValue){
				           		Ext.getCmp('mor_isCivilServant').setValue(newValue);
				           }
				        }
				   },{
				   	    id:'mor_isCivilServant',
				        xtype:'hidden',
				        name :'procreditMortgagePerson.isCivilServant'
				       
				   }]
				},{
				    columnWidth : 1,
				    layout : 'form',
				    labelWidth:100,
				    items:[{
				        xtype:'textfield',
				        fieldLabel:'主营业务或职务',
				        name:'procreditMortgagePerson.business',
				      	anchor:'100%'
				    }]
				},{
				    columnWidth: .6,
				    layout : 'form',
				    items:[{
				    	xtype : 'textfield',
				    	fieldLabel:'主要资产',
				    	name:'procreditMortgagePerson.assets',
				        anchor:'100%'
				    }]
				},{
				   columnWidth : .4,
				   layout : 'form',
				   items : [{				       
						xtype : 'numberfield',
						fieldLabel : '资产价值.万元',
						maxLength : 23,
						maxLengthText : '最大输入长度23',
						name : 'procreditMortgagePerson.assetvalue',
				       	anchor:'100%'
					
				   }]
				},{
				   columnWidth :1,
				   layout:'form',
				   items : [{
				       xtype:'textfield',
				       fieldLabel:'月收入',
				       name:'procreditMortgagePerson.monthlyIncome',
				       anchor:'100%'
				   }]
				}]
			}]
		});
		var businessType=this.businessType;
		if(null!=this.id && null!=this.type){
			this.loadData({
				url : __ctxPath +'/credit/mortgage/getMortgageByType.do?mortgageid='
						+ this.id+'&typeid='+this.type,
				root : 'data',
				preName : ['procreditMortgagePerson','person','cardtypevalue'],
				scope : this,
				success : function(resp, options) {
					var obj=Ext.util.JSON.decode(resp.responseText)
					if(businessType=='Financing'){
						Ext.getCmp('person_ispublicservant').setValue(obj.data.procreditMortgagePerson.isCivilServant);//是否为公务员
						Ext.getCmp('mor_isCivilServant').setRawValue(obj.data.procreditMortgagePerson.isCivilServant);
						Ext.getCmp('card_number').setValue(obj.data.person.cardnum);//证件号码
						Ext.getCmp('person_phone').setValue(obj.data.person.linktel);//联系电话
						Ext.getCmp('person_address').setValue(obj.data.person.home);//家庭住址
						Ext.getCmp('cardtype').setValue(obj.data.person.cardtype);
					}else{
						Ext.getCmp('person_ispublicservant').setValue(obj.data.person.ispublicservant);//是否为公务员
						Ext.getCmp('mor_isCivilServant').setRawValue(obj.data.person.ispublicservant);
						Ext.getCmp('card_number').setValue(obj.data.person.cardnumber);//证件号码
						Ext.getCmp('person_phone').setValue(obj.data.person.cellphone);//联系电话
						Ext.getCmp('person_address').setValue(obj.data.person.familyaddress);//家庭住址
						Ext.getCmp('cardtype').setValue(obj.data.person.cardtype);
					}
				}
			});
		}
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
	}
});
