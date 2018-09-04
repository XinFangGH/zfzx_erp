/**
 * @author
 * @createtime
 * @class P2pLoanFlowSetView
 * @extends Ext.Window
 * @description P2pLoanFlowSetView表单
 * @company 智维软件
 */
P2pLoanFlowSetView = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		P2pLoanFlowSetView.superclass.constructor.call(this, {
				id : 'P2pLoanFlowSetView'+this.productId,
				layout : 'anchor',
				items : this.outPanel,
				modal : true,
				resizable:false,
				autoScroll:true,
				height : 500,
				width :(screen.width)*0.6,
				maximizable : true,
				frame:true,
				title : '流程配置',
				buttonAlign : 'center',
				buttons :[{
					text : '保存',
					iconCls : 'btn-save',
					scope : this,
					handler : this.save
				},{
					text : '提交',
					iconCls : 'btn-save',
					scope : this,
					handler : this.save
				}, {
					text : '关闭',
					iconCls : 'close',
					scope : this,
					handler : this.cancel
				}]
		});
	},
	// 初始化组件
	initUIComponents : function() {
		this.editor=new P2PDicKeyCombo({
			editable:false,
			nodeKey : 'p2p_stepName',
			anchor : "100%",
			triggerAction : "all",
			listeners:{
				scope : this,
					'select' : function(combox,record,index){
						var flag=true;
						var items=this.applyStep.store.data.items;
						for(var i=0;i<items.length;i++){
							if(combox.value==items[i].data.keyValue){
								flag=false;
								break;
							}
						}
						if(!flag){
							combox.setValue('');
							this.deleteStep();
						}
					}
			}
		});
		
		this.formPanel1=new P2pLoanProductModule_ProductDiscribe({
			isEditReadOnly:this.isEditReadOnly,
			productName : this.productName,
			operationType : this.operationType,
			userScope : this.userScope
		});
		
		this.applyStep=new HT.EditorGridPanel({
			height:220,
			autoScroll:true,
			border : false,
			clicksToEdit :1,
			enableDragDrop : false,
			showPaging:false,
			region : 'center',
			tbar :new Ext.Toolbar({
				items : [{
					iconCls : 'btn-add',
					text : '新增',
					xtype : 'button',
					scope : this,
					handler : this.addStep
				},{
					iconCls : 'btn-del',
					text : '删除',
					xtype : 'button',
					scope : this,
					handler : this.deleteStep
				}]
			}),
			url : __ctxPath + '/p2p/listP2pLoanApplyStep.do?Q_productId_L_EQ='+ this.productId,
			fields : [{
					name : 'stepId',type : 'int'
				},'stepName','conditionState','keyValue'
			],
			columns : [{
					header : 'stepId',
					dataIndex : 'stepId',
					hidden : true
				},{
					header : '步骤名称',	
					dataIndex : 'keyValue',
					align:'center',
					sortable : true,
					editor : this.editor,
					scope:this,
					renderer : function(value,metaData, record,rowIndex, colIndex,store) {
						var objcom = this.editor;
						var objStore = objcom.getStore();
						var idx = objStore.find("dicKey", value);
						if (idx != "-1") {
							return objStore.getAt(idx).data.itemValue;
						}else{
							return record.data.stepName;
						}
					}
				},{
					header : '状态',
					dataIndex : 'conditionState',
					sortable : true,
					align:'center',
					renderer:function(value,metaData, record,rowIndex, colIndex,store){
						if(value=='1' || record.data.keyValue=='nodePerson' || record.data.keyValue=='nodeOver'){
							return '必备'
						}else {
							return '可选'
						}
					}
				}
			]
		});
		
		this.outPanel = new Ext.form.FormPanel({
			autoWidth:true,
			height : 435,
			modal : true,
			labelWidth : 100,
			frame:true,
			buttonAlign : 'center',
			layout : 'form',
			border : false,
			defaults : {
				anchor : '100%',
				xtype : 'fieldset',
				columnWidth : 1,
				labelAlign : 'right',
				autoHeight : true
		    },
		    labelAlign : "right",
		    items:[{
				xtype : 'fieldset',
				border : false,
				title : '产品信息',
				collapsible : false,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.formPanel1]
		    },{
				xtype : 'fieldset',
				border : false,
				title : '申请步骤	<font style="padding-left:30px;color:red">第一步:填写个人信息	</font><font style="padding-left:30px;color:red">中间步骤没有限制</font><font style="padding-left:30px;color:red">最后一步:完成</font>',
				collapsible : false,
				autoHeight : true,
				labelAlign : 'right',
				items : [this.applyStep]
		    }]
		});
		// 加载表单对应的数据
		if (this.productId != null && this.productId != 'undefined') {
			var productId=this.productId;
			this.outPanel.loadData({
				url : __ctxPath+ '/p2p/getP2pLoanProduct.do?productId='+ this.productId,
				root : 'data',
				preName : 'p2pLoanProduct',
				success : function(response, options) {
					var respText = response.responseText;
					var alarm_fields = Ext.util.JSON.decode(respText);
				}
			});
		}

	},

	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function(v) {
		var state="";
		if(v.text=="提交"){
			state=3;
			var applyBZ = this.applyStep.store.data.items.length;
			if(0==applyBZ){
				Ext.ux.Toast.msg("操作信息","请增加申请步骤!!!");
				return;
			}
		}
		
		var gridPanel = this.parentGrid;
		var productId =this.productId
		var stepGridJson=this.getStepGridJson();
		if(stepGridJson){
			Ext.Ajax.request({
				url : __ctxPath+ '/p2p/saveStepP2pLoanApplyStep.do',
				method : 'POST',
				scope :this,
				waitMsg : '数据正在提交，请稍后...',
				success : function(response, request) {
					if (gridPanel != null) {
						Ext.ux.Toast.msg("操作信息","保存成功");
						gridPanel.getStore().reload();
					}
					this.close();
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试！');
				},
				params : {
					"stepGridJson" : stepGridJson,
					"state":state,
					"productId":productId
				}
			})
		}
	},
	addStep : function() {
		var gridadd = this.applyStep;
		var storeadd = this.applyStep.getStore();
		var keys = storeadd.fields.keys;
		var p = new Ext.data.Record();
		p.data = {};
		for ( var i = 1; i < keys.length; i++) {
			p.data[keys[i]] = '';
		}
		var count = storeadd.getCount() + 1;
		gridadd.stopEditing();
		storeadd.addSorted(p);
		gridadd.getView().refresh();
	},
	getStepGridJson : function() {
		var vRecords = this.applyStep.getStore().getRange(0,this.applyStep.getStore().getCount()); // 得到修改的数据（记录对象）
		var vDatas = '';
		if(vRecords.length > 0){
			var objcom = this.editor;
			var objStore = objcom.getStore();
			for (var i = 0; i < vRecords.length; i++) {
				if(vRecords[i].data.keyValue){
					var idx = objStore.find("dicKey",vRecords[i].data.keyValue);
					var stepName=objStore.getAt(idx).data.itemValue;
					if (vRecords[i].data.stepId != null && vRecords[i].data.stepId != "") {
							st = {
								"stepId" : vRecords[i].data.stepId,
								"keyValue" : vRecords[i].data.keyValue,
								"stepName" : stepName,
								"productId":this.productId,
								"conditionState":(vRecords[i].data.keyValue=='nodePerson' || vRecords[i].data.keyValue=='nodeOver')?1:2
							};
					} else {
							st = {
								"stepId" : null,
								"keyValue" : vRecords[i].data.keyValue,
								"stepName" : stepName,
								"productId":this.productId,
								"conditionState":(vRecords[i].data.keyValue=='nodePerson' || vRecords[i].data.keyValue=='nodeOver')?1:2
							};
					}
					vDatas += Ext.util.JSON.encode(st) + '@';
				}else{
					Ext.ux.Toast.msg('操作信息','请完善信息后再提交!');
					return;
				}
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}else{
			Ext.ux.Toast.msg('操作信息','没有修改的数据，无需提交!');
		}
		return vDatas;
	},
	deleteStep : function() {
			var griddel = this.applyStep;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}
			griddel.stopEditing();
			for ( var i = 0; i < s.length; i++) {
				var row = s[i];
				if (row.data.stepId == null || row.data.stepId == '') {
					storedel.remove(row);
					griddel.getView().refresh();
				} else {
					deleteFun(__ctxPath + '/p2p/multiDelP2pLoanApplyStep.do',{
						ids :row.data.stepId
					},
					function() {},i,s.length)
				}
				storedel.remove(row);
				griddel.getView().refresh();
			}
		}
});