AddPawnItemsWin = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	constructor : function(_cfg) {

		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		AddPawnItemsWin.superclass.constructor.call(this, {
			        buttonAlign:'center',
			        title:'新增当物',
			        iconCls : 'btn-add',
					width : (screen.width-100)*0.6,
					height : 460,
					constrainHeader : true ,
					collapsible : true, 
					frame : true ,
					border : false ,
					resizable : true,
					layout:'fit',
					autoScroll : true ,
					bodyStyle:'overflowX:hidden',
					constrain : true ,
					closable : true,
					modal : true,
					maximizable :true,
					items : [this.formPanel],
					buttons:[{
					    text : '保存',
						iconCls : 'btn-save',
						scope : this,
						handler : this.saveInfo
					},{
					    text : '提交',
						iconCls : 'btn-save',
						scope : this,
						handler : this.save
					},{
					    text : '关闭',
						iconCls : 'close',
						scope : this,
						handler : function(){
							this.close();
						}
					}]
					
				})
	},
	initUIComponents : function() {
	    var data = [['车辆',1],['股权',2],['机器设备',5],['存货/商品',6],['无形权利',14],['住宅',7],['商铺写字楼',8],['住宅用地',9],['商业用地',10],['商住用地',11],/*['教育用地',12],*/['工业用地',13],['公寓',15],['联排别墅',16],['独栋别墅',17]];
		this.formPanel = new Ext.FormPanel({
			url : __ctxPath +'/creditFlow/pawn/pawnItems/addItemsPawnItemsList.do',
			monitorValid : true,
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true ,
			border : false,
			items : [{
				layout : 'column',
				xtype:'fieldset',
	            title: '填写<当物>基础信息',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
				items : [{
					columnWidth : 1,
					layout : 'form',
					items : [{
						xtype:'combo',
						hiddenName : 'pawnItemsList.pawnItemType',
						anchor : '95%',
						fieldLabel:'当物类型',
						allowBlank :false,
						mode : 'local',
						forceSelection : true, 
						displayField : 'typeValue',
						valueField : 'typeId',
						editable : false,
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							data : data,
							fields:['typeValue','typeId']
						}),
						listeners:{
						scope:this,
						'select':function(combox,record,index){
							this.formPanel.remove(this.formPanel.items.last());
							this.formPanel.remove(this.formPanel.items.last());
							var fileSet=new Ext.form.FieldSet({
						            title: '当物材料',
						            collapsible: true,
						            autoHeight:true,
						            anchor : '95%',
								 	items :[new PawnMaterialsView({businessType:this.businessType,isHidden_materials:false,objectPanel:this})]
							})
							if(combox.getValue()==1){	
								this.formPanel.add(new VehicleCarForm({objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout()
							}else if(combox.getValue()==2){
								this.formPanel.add(new StockownershipForm({businessType:this.businessType,objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}else if(combox.getValue()==5){
								this.formPanel.add(new MachineinfoForm({objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}else if(combox.getValue()==6){
								this.formPanel.add(new ProductForm({objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}else if(combox.getValue()==7){
								this.formPanel.add(new HouseForm({type:7,objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}else if(combox.getValue()==8){
								this.formPanel.add(new OfficeBuildingForm({objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}else if(combox.getValue()==9){
								this.formPanel.add(new HousegroundForm({objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}else if(combox.getValue()==10){
							    this.formPanel.add(new BusinessForm({objectType:'pawn'}))
							    this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}else if(combox.getValue()==11){
								this.formPanel.add(new BusinessandliveForm({objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}else if(combox.getValue()==12){
								this.formPanel.add(new EducationForm({objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}else if(combox.getValue()==13){
								this.formPanel.add(new IndustryForm({objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}else if(combox.getValue()==14){
								this.formPanel.add(new DroitForm({objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}else if(combox.getValue()==15){
								this.formPanel.add(new HouseForm({type:15,objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}else if(combox.getValue()==16){
								this.formPanel.add(new HouseForm({type:16,objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}else if(combox.getValue()==17){
								this.formPanel.add(new HouseForm({type:17,objectType:'pawn'}))
								this.formPanel.add(fileSet)
								this.formPanel.doLayout();
							}
						}
					}
					}]
					},{
				columnWidth : 1,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel : '当物名称',
					anchor : '95%',
					allowBlank :false,
					name : 'pawnItemsList.pawnItemName'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel : '规格和状态',
					anchor : '90%',
					allowBlank :false,
					name : 'pawnItemsList.specificationsStatus'
				}]
			},{
				columnWidth : .45,
				layout : 'form',
				items : [{
					xtype : 'numberfield',
					fieldLabel : '数量',
					anchor : '100%',
					allowBlank :false,
					name : 'pawnItemsList.counts'
				}]
			},{
				columnWidth : .45,
				layout : 'form',
				items : [{
					xtype : 'numberfield',
					fieldLabel : '评估价值',
					anchor : '100%',
					fieldClass : 'field-align',
					allowBlank :false,
					name : 'pawnItemsList.assessedValuationValue'
				}]
			}, {
				columnWidth : .05, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 20,
				items : [{
					fieldLabel : "元",
					labelSeparator : '',
					anchor : "100%"
				}]
			},{
				columnWidth : .45,
				layout : 'form',
				items : [{
					xtype : 'numberfield',
					fieldLabel : '折当率',
					anchor : '100%',
					fieldClass : 'field-align',
					allowBlank :false,
					name : 'pawnItemsList.discountRate'
				}]
			}, {
				columnWidth : .05, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 20,
				items : [{
					fieldLabel : "%",
					labelSeparator : '',
					anchor : "100%"
				}]
			},{
				columnWidth : .45,
				layout : 'form',
				items : [{
					xtype : 'numberfield',
					fieldLabel : '当物金额',
					anchor : '100%',
					fieldClass : 'field-align',
					allowBlank :false,
					name : 'pawnItemsList.pawnItemMoney'
				}]
			}, {
				columnWidth : .05, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 20,
				items : [{
					fieldLabel : "元",
					labelSeparator : '',
					anchor : "100%"
				}]
			},{
				columnWidth : .45,
				layout : 'form',
				items : [{
					xtype : 'datefield',
					fieldLabel : '获取时间',
					anchor : '100%',
					format : 'Y-m-d',
					name : 'pawnItemsList.accessTime'
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				items : [{
					xtype : 'textarea',
					fieldLabel : '备注',
					anchor : '95%',
					name : 'pawnItemsList.remarks'
				},{
					xtype : 'hidden',
					name : 'pawnItemsList.projectId',
					value : this.projectId
				},{
					xtype : 'hidden',
					name : 'pawnItemsList.businessType',
					value : this.businessType
				},{
					xtype : 'hidden',
					name : 'pawnItemsList.pawnItemId'
				}]
			}]
			},{
				anchor:'95%',
			   name:'otherInfo',
			   items:[new VehicleCarForm({objectType:'pawn'})]
			},{
				xtype:'fieldset',
	            title: '当物材料',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
			 	items :[new PawnMaterialsView({businessType:this.businessType,isHidden_materials:false,objectPanel:this})]
			}]
		})
		
	},
	save:function(){
		var win=this;
		var gridPanel=this.gridPanel
		this.formPanel.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			success : function(form, action) {
				Ext.ux.Toast.msg('操作信息', '保存成功!');
				win.destroy();
				gridPanel.getStore().reload()
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('操作信息', '保存失败!');
			}
		});				
	},
	saveInfo : function(){
		var win=this;
		var gridPanel=this.gridPanel
		var formPanel=this.formPanel;
		var typeid=this.getCmpByName('pawnItemsList.pawnItemType').getValue();
		this.formPanel.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			success : function(form, action) {
				Ext.ux.Toast.msg('操作信息', '保存成功!');
				var o=Ext.util.JSON.decode(action.response.responseText)
				formPanel.loadData({
					url :__ctxPath +'/creditFlow/pawn/pawnItems/getPawnItemTypePawnItemsList.do?mortgageid='+ o.pawnItemId+'&typeid='+typeid+'&objectType=pawn',
					root : 'data',
					preName : ['pawnItemsList'],
					scope : this,
					success : function(resp, options) {
						var obj=Ext.util.JSON.decode(resp.responseText)
						formPanel.getCmpByName('pawnItemsList.pawnItemType').setValue(obj.data.pawnItemsList.pawnItemType)
						formPanel.getCmpByName('pawnItemsList.pawnItemName').setValue(obj.data.pawnItemsList.pawnItemName)
						formPanel.getCmpByName('pawnItemsList.specificationsStatus').setValue(obj.data.pawnItemsList.specificationsStatus)
						formPanel.getCmpByName('pawnItemsList.counts').setValue(obj.data.pawnItemsList.counts)
						formPanel.getCmpByName('pawnItemsList.assessedValuationValue').setValue(obj.data.pawnItemsList.assessedValuationValue)
						formPanel.getCmpByName('pawnItemsList.discountRate').setValue(obj.data.pawnItemsList.discountRate)
						formPanel.getCmpByName('pawnItemsList.pawnItemMoney').setValue(obj.data.pawnItemsList.pawnItemMoney)
						formPanel.getCmpByName('pawnItemsList.accessTime').setValue(obj.data.pawnItemsList.accessTime)
						formPanel.getCmpByName('pawnItemsList.remarks').setValue(obj.data.pawnItemsList.remarks)
						formPanel.getCmpByName('pawnItemsList.businessType').setValue(obj.data.pawnItemsList.businessType)
						formPanel.getCmpByName('pawnItemsList.pawnItemId').setValue(obj.data.pawnItemsList.pawnItemId)
						formPanel.getCmpByName('pawnItemsList.projectId').setValue(obj.data.pawnItemsList.projectId)
						formPanel.remove(formPanel.items.last());
						var fileSet=new Ext.form.FieldSet({
					            title: '当物材料',
					            collapsible: true,
					            autoHeight:true,
					            anchor : '95%',
							 	items :[new PawnMaterialsView({projId:obj.data.pawnItemsList.pawnItemId,businessType:obj.data.pawnItemsList.businessType,isHidden_materials:false,objectPanel:formPanel})]
						})
						formPanel.add(fileSet)
						formPanel.doLayout();
					}
				});
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('操作信息', '保存失败!');
			}
		});				
	}
});
