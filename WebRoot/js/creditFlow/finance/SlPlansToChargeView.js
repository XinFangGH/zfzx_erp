

SlPlansToChargeView = Ext.extend(Ext.Panel,{
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.businessType)!="undefined"){
			this.businessType=_cfg.businessType;
		}
		Ext.applyIf(this, _cfg);
		  	this.isGranted = function(a){
			var b = "";
			if("LeaseFinance"==this.businessType){
				b="_"+this.businessType;
			}else if(this.businessType == "SmallLoan"){
				b="_"+this.businessType;
			}
		    if(this.plat=='p2p'){
		    	return isGranted(a+b+"_p2p");
		    }else{
		    	return isGranted(a+b);
		    }
			
		}
		// 必须先初始化组件
		this.initUIComponents();
		SlPlansToChargeView.superclass.constructor.call(this, {
			id : 'SlPlansToChargeView',
			layout:'anchor',
	        anchor : '100%',
	        iconCls:"menu-finance",
			items : this.gridPanel,
			title:'基础收费类型'
		});
	},// end of the constructor
	// 初始化组件

	initUIComponents : function() {
		this.datafield=new Ext.form.DateField( {
			format : 'Y-m-d',
			allowBlank : false
		})

		this.comboType= new Ext.form.ComboBox({
		   	mode : 'local',
		   	displayField : 'name',
		   	valueField : 'id',
		   	editable : false,
		   	triggerAction : 'all',
		   	width : 70,
		   	autoload : false,
		   	store : new Ext.data.SimpleStore({
	       		fields : ["name", "id"],
           		data : [["百分比 (%)", "0"],["固定金额", "1"]]
			})
		})
		
		this.comboType1= new Ext.form.ComboBox({
			mode : 'local',
			displayField : 'name',
			valueField : 'id',
			editable : false,
			triggerAction : 'all',
			width : 70,
			store : new Ext.data.SimpleStore({
				fields : ["name", "id"],
				data : [["公有", "0"],["私有", "1"]]
			})
		})
		
		this.gridPanel = new HT.EditorGridPanel( {
			clicksToEdit : 1,
			border : false,
			isShowTbar : false,
			showPaging : false,
			autoHeight : true,
			// 使用RowActions
			// rowActions : true,
//				id : 'SlPlansToChargeView',
			url : __ctxPath + "/creditFlow/finance/listSlPlansToCharge.do?businessType="+this.businessType,
			fields : [ {
				name : 'plansTochargeId'
			}, {
				name : 'name'
			},{
			  name : 'chargeStandard'
			},{
				name : 'sort'
			},{
				name : 'isType'
			},{
				name : 'businessType'
			}],
			tbar : [ {
				text : '增加',
				iconCls : 'btn-add',
				scope : this,
				hidden : isGranted('_create_zxpz')?false:true,
				handler : this.createRs
			}, '-', {
				iconCls : 'btn-del',
				scope : this,
				text : '删除',
				hidden : isGranted('_remove_zxpz')?false:true,
				handler : this.removeSelRs
			}, '-', {
				iconCls : 'btn-save' + '',
				scope : this,
				text : '保存',
				hidden : isGranted('_save_zxpz')?false:true,
				handler : this.saveRs
			}, '-', {
				iconCls : 'btn-refresh' + '',
				scope : this,
				text : '刷新',
				hidden : isGranted('_save_zxpz')?false:true,
				handler : this.refresh
			}/*, '-', {
				iconCls : 'btn-save',
				scope : this,
				text : '保存',
				handler : this.saveRs
			} */],
			columns : [ {
				header : '费用类型',
				dataIndex : 'name',
				align:'center',
				editor :new Ext.form.TextField( {
					allowBlank : false
				})
			},{
				header : '费用标准',
				align:'center',
				dataIndex : 'chargeStandard',
				editor :new Ext.form.TextField( {
					allowBlank : false
				})
			},{
				header : '类型 ',
				dataIndex : 'isType',
				align:'center',
				editor :this.comboType1,
				renderer:ZW.ux.comboBoxRenderer(this.comboType1)
			},{
				header : '排序值 ',
				align:'center',
				dataIndex : 'sort',
				editor :new Ext.form.NumberField( {
					allowBlank : false
				})
			},{
				header : '业务类型 ',
				align:'center',
				dataIndex : 'businessType',
				hidden : true,
				editor :new Ext.form.NumberField( {
					allowBlank : false
				})
			}]
		// end of columns
		});
	},
	createRs : function() {
		this.datafield.setValue('');
		var newRecord = this.gridPanel.getStore().recordType;
		var newData = new newRecord( {
			plansTochargeId : '',
			name : '',
			chargeStandard : '',
			isType : 1,
			sort : 1,
			businessType : this.businessType
		});
		this.gridPanel.stopEditing();
		this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(), newData);
		this.gridPanel.getView().refresh();
		this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore().getCount()-1));
		this.gridPanel.startEditing(0, 0);

	},

	getGridDate : function() {
		var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			// begin 将记录对象转换为字符串（json格式的字符串）
			for ( var i = 0; i < vCount; i++) {
				if(vRecords[i].data.sort !=null &&　vRecords[i].data.sort !=""){
				 st={"plansTochargeId":vRecords[i].data.plansTochargeId,"name":vRecords[i].data.name,"chargeStandard":vRecords[i].data.chargeStandard,"isType":vRecords[i].data.isType,"businessType":vRecords[i].data.businessType,"sort":vRecords[i].data.sort};
				}else{
					st={"plansTochargeId":vRecords[i].data.plansTochargeId,"name":vRecords[i].data.name,"chargeStandard":vRecords[i].data.chargeStandard,"isType":vRecords[i].data.isType,"businessType":vRecords[i].data.businessType};
				}
				vDatas += Ext.util.JSON.encode(st) + '@';
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		//Ext.getCmp('fundIntentJsonData2').setValue(vDatas);
//						this.gridPanel.getColumnsgetEditor().getCmpByName('fundIntentJsonData').setValue(vDatas);
//						alert(this.gridPanel.getEditor().getCmpByName('fundIntentJsonData').getValue());
		return vDatas;
	},
	refresh :function(){
	this.gridPanel.getStore().reload();
		
	},
	saveRs : function() {
		var data = this.getGridDate();
		Ext.Ajax.request( {
			url : __ctxPath + '/creditFlow/finance/savejsonSlPlansToCharge.do',
			method : 'POST',
			scope : this,
			params : {
				chargejson : data
			},
			success : function(response, request) {
			Ext.ux.Toast.msg('操作信息', '保存成功!');
			this.gridPanel.getStore().reload();
			}
		});
	},
	removeSelRs : function() {
		var fundIntentGridPanel = this.gridPanel;
		var deleteFun = function(url, prame, sucessFun,i,j) {
			Ext.Ajax.request( {
				url : url,
				method : 'POST',
				success : function(response) {
					if (response.responseText.trim() == '{success:true}') {
					if(i==(j-1)){
						    Ext.ux.Toast.msg('操作信息', '删除成功!');
						}
						sucessFun();
					} else if (response.responseText
							.trim() == '{success:false}') {
					Ext.ux.Toast.msg('操作信息', '删除失败!');
					}
				},
				params : prame
			});
		};
		var a = fundIntentGridPanel.getSelectionModel().getSelections();
	    if (a <= 0) {
			Ext.ux.Toast.msg('操作信息','请选择要删除的记录');
			return false;
		};
		
		Ext.Msg.confirm("提示!",'确定要删除吗？',function(btn) {
			if (btn == "yes") {
				var s = fundIntentGridPanel.getSelectionModel().getSelections();
				for ( var i = 0; i < s.length; i++) {
					var row = s[i];
					if (row.data.plansTochargeId == null || row.data.plansTochargeId == '') {
						fundIntentGridPanel.getStore().remove(row);
					} else {
						deleteFun(
								__ctxPath + '/creditFlow/finance/deleteSlPlansToCharge.do',
							{
								plansTochargeId : row.data.plansTochargeId
							},
							function() {
								fundIntentGridPanel.getStore().remove(row);
								fundIntentGridPanel.getStore().reload();
							},i,s.length);
					}
				}
			}
		});
	}
});