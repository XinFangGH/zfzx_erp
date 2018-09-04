var getPhoneCheckInfoData = function(grid) {
	if (typeof (grid) == "undefined" || null == grid) {
		return "";
		return false;
	}
	var vRecords = grid.items.get(0).getStore().getRange(0, grid.items.get(0).getStore().getCount()); // 得到修改的数据（记录对象）
	var vCount = vRecords.length; // 得到记录长度
	var vDatas = '';
	if (vCount > 0) {
		// begin 将记录对象转换为字符串（json格式的字符串）
		for ( var i = 0; i < vCount; i++) {
		var str = Ext.util.JSON.encode(vRecords[i].data);
		vDatas += str + '@';
	}
		vDatas = vDatas.substr(0, vDatas.length - 1);
	}
	return vDatas;
}

/**
 * @author
 * @class BpPersonPhonecheckInfoView
 * @extends Ext.Panel
 * @description [BpPersonPhonecheckInfo]管理
 * @company 智维软件
 * @createtime:
 */
BpPersonPhonecheckInfoView = Ext.extend(Ext.Panel, {
	// 构造函数
	btnTitleText:'',
	personType:'',
	projectId:'',
	personId:'',
	isAllReadOnly:false,
	constructor : function(_cfg) {
		if (typeof(_cfg.btnTitleText) != "undefined") {
			this.btnTitleText = _cfg.btnTitleText;
		}
		if (typeof(_cfg.personType) != "undefined") {
			this.personType = _cfg.personType;
		}
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.personId) != "undefined") {
			this.personId = _cfg.personId;
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BpPersonPhonecheckInfoView.superclass.constructor.call(this, {
			id:'BpPersonPhonecheckInfoView'+this.personType,
			layout : 'anchor',
			anchor : '100%',
			autoHeight : true,
			items : [this.gridPanel]
		});
	},
	// 初始化组件
	initUIComponents : function() {
		this.topbar = new Ext.Toolbar({
			items : [{
					html:'电核信息-<font style="color:black;font-weight:bold;">'+this.btnTitleText+'</font>'
				},{
					iconCls : 'btn-add',
					text : '添加',
					xtype : 'button',
					scope : this,
					handler : this.createRs
				}, {
					iconCls : 'btn-del',
					text : '删除',
					xtype : 'button',
					scope : this,
					handler : this.removeSelRs
				}]
		});
		var personType=this.personType;
		var appUserCom = new Ext.form.ComboBox({
			readOnly:this.isAllReadOnly,
			triggerClass : 'x-form-search-trigger',
			resizable : true,
			disabled : this.disALLabled,
			scope : this,
			mode : 'remote',
			editable : false,
			lazyInit : false,
			allowBlank : false,
			typeAhead : true,
			minChars : 1,
			width : 100,
			listWidth : 150,
			store : new Ext.data.JsonStore({}),
			triggerAction : 'all',
			onTriggerClick : function(cc) {
				var userIds = this.getValue();
				if ("" == this.getValue()) {
					userIds = "";
				}
				new UserDialog({
					userIds : userIds,
					userName : this.getValue(),
					single : false,
					title : "选择审核员",
					callback : function(uId, uname) {
						var gridId='RelationPersonTelInfo'+personType;
						var gridPanel = Ext.getCmp(gridId);
						gridPanel.getSelectionModel().getSelected().data['checkUserId'] = uId;
						gridPanel.getSelectionModel().getSelected().data['checkUserName'] = uname;
						gridPanel.getView().refresh();
					}
				}).show();
			},
			listeners : {
				'select' : function(combo, record, index) {
					this.getView().refresh();
				},
				'blur' : function(f) {
					if (f.getValue() != null && f.getValue() != '') {
					}
				}
			}
		})

		this.gridPanel = new HT.EditorGridPanel({
			id : 'RelationPersonTelInfo'+this.personType,
			name:'gridPanel',
			url : __ctxPath +'/bpPersonPhonecheckInfo/getPhoneListBpPersonPhonecheckInfo.do?projectId='+ this.projectId+"&type="+this.personType+"&personId="+this.personId,
			border : false,
			clicksToEdit : 1,
			layout : 'anchor',
			tbar :null,
			autoScroll : true,
			showPaging : false,
			autoHeight : true,
			stripeRows : true,
			isAllReadOnly:this.isAllReadOnly,
			plain : true,
			viewConfig : {
				forceFit : true
			},
			sm : new Ext.grid.CheckboxSelectionModel({hidden:this.isAllReadOnly}),
			fields : [{
					name : 'id'
				}, {
					name : 'personRelationName'
				}, {
					name : 'relation'
				}, {
					name : 'telphone'
				}, {
					name : 'isKnowLoan'
				}, {
					name : 'phoneText'
				}, {
					name : 'checkUserId'
				}, {
					name : 'checkUserName'
				}
			],
			columns : [
				{
					header : '联系人',
					dataIndex : 'personRelationName'
				}, {
					header : '关系',
					dataIndex : 'relation'
				}, {
					header : '手机号码',
					dataIndex : 'telphone'
				}, {
					header : '知悉贷款',
					dataIndex : 'isKnowLoan',
					editor : {
						xtype : 'combo',
						//id : this.personType,
						mode : 'local',
						displayField : 'name',
						valueField : 'number',
						width : 70,
						triggerAction:"all",
						readOnly : this.isAllReadOnly,
						store : new Ext.data.SimpleStore({
							fields : ["name", "number"],
							data : [["是", "1"], ["否", "0"]]
						})
					},
					renderer : function(value, metaData, record, rowIndex,colIndex, store) {
						if (value == '0') {
							return '否'
						} else if (value == '1') {
							return '是'
						}
					}
				},{
					header : '电核内容',
					dataIndex : 'phoneText',
					editor:{
						readOnly:this.isAllReadOnly,
						xtype:'textfield'
					}
				},{
					header : '审核员',
					dataIndex : 'checkUserName',
					scope : this,
					editor : new Ext.grid.GridEditor(appUserCom)
				},{
					hidden:true,
					dataIndex : 'checkUserId'
				}
			]
		});
	},
	// 创建记录
	createRs : function() {
		var gridadd = this.gridPanel;
		var storeadd = this.gridPanel.getStore();
		var keys = storeadd.fields.keys;
		var p = new Ext.data.Record();
		p.data = {};
		for (var i = 1; i < keys.length; i++) {
			p.data[keys[i]] = null
		}
		var count = storeadd.getCount() + 1;
		gridadd.stopEditing();
		storeadd.addSorted(p);
		gridadd.getView().refresh();
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath+ '/creditFlow/personrelation/phonecheck/multiDelBpPersonPhonecheckInfo.do',
			grid : this.gridPanel,
			idName : 'id'
		});
	},
	getGridDate : function() {
		var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			// begin 将记录对象转换为字符串（json格式的字符串）
			for (var i = 0; i < vCount; i++) {
				st = {
					"id" : vRecords[i].data.id,
					"isKnowLoan" : vRecords[i].data.isKnowLoan,
					"phoneText" : vRecords[i].data.phoneText,
					"checkUserId" : vRecords[i].data.checkUserId
				};
				vDatas += Ext.util.JSON.encode(st) + '@';
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	}
});
