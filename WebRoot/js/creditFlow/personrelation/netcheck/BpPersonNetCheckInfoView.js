var getNetCheckInfoData = function(grid) {
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
 * @class BpPersonNetCheckInfoView
 * @extends Ext.Panel
 * @description [BpPersonNetCheckInfo]管理
 * @company 智维软件
 * @createtime:
 */
BpPersonNetCheckInfoView = Ext.extend(Ext.Panel, {
	// 构造函数
	projectId:'',
	isAllReadOnly:false,
	isReadOnly:false,
	userName:'',
	userId : '',
	constructor : function(_cfg) {
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
		if (typeof(_cfg.isAllReadOnly) != "undefined") {
			this.isAllReadOnly = _cfg.isAllReadOnly;
		}
		if (typeof(_cfg.isReadOnly) != "undefined") {
			this.isReadOnly = _cfg.isReadOnly;
		}
		this.userName=currentUserFullName;
		this.userId=currentUserId;
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		BpPersonNetCheckInfoView.superclass.constructor.call(this, {
			id:'BpPersonNetCheckInfoView'+this.personType,
			layout : 'anchor',
			anchor : '100%',
			autoHeight : true,
			items : [this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		
		var personType=this.personType;
		var appUserCom = new Ext.form.ComboBox({
			readOnly:this.isReadOnly,
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
					single : true,
					title : "选择审核员",
					callback : function(uId, uname) {
						var gridPanel = Ext.getCmp('RelationPersonNetInfo');
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
		var isHidden = this.isHidden;
		this.gridPanel = new HT.EditorGridPanel({
			id : 'RelationPersonNetInfo',
			name:'gridPanel',
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
			url : __ctxPath+ "/bpPersonNetCheckInfo/listBpPersonNetCheckInfo.do?Q_projectId_L_EQ="+this.projectId,
			fields : [{
				name : 'id',
				type : 'int'
			}, 'projectId', 'serachObj', 'serachInfo', 'isException','remark', 'checkUserId','checkUserName'],
			columns : [{
					header : 'id',
					dataIndex : 'id',
					hidden : true
				}, {
					header : '查询对象',
					dataIndex : 'serachObj'
				}, {
					header : '查询内容',
					dataIndex : 'serachInfo'
				}, {
					header : '有无异常',
					dataIndex : 'isException',
					editor : {
						xtype : 'combo',
						id : 'type',
						mode : 'local',
						displayField : 'name',
						valueField : 'number',
						width : 70,
						readOnly : this.isReadOnly,
						store : new Ext.data.SimpleStore({
							fields : ["name", "number"],
							data : [["有", "0"], ["无", "1"]]
						})
					},
					renderer : function(value, metaData, record, rowIndex,colIndex, store) {
						if (value == '0') {
							return '有'
						} else if (value == '1') {
							return '无'
						}
					}
				}, {
					header : '备注',
					dataIndex : 'remark',
					editor:{
						xtype:'textfield',
						readOnly : this.isReadOnly
					}
				}, {
					header : '审核员',
					dataIndex : 'checkUserName',
					scope : this,
					editor : new Ext.grid.GridEditor(appUserCom),
					renderer:function(){
		               return this.userName;
		            }
				},{
					header: "上传或下载",
					fixed: true,
					width:86,
					dataIndex: 'uploadOrDown',
					hidden:false,
					renderer:function(){
		                if(isHidden){
		                     return "<img src='"+__ctxPath+"/images/download-start.png'/>";
		                }else{
		                      return "<img src='"+__ctxPath+"/images/upload-start.png'/>";
		                }
		            }
				},{
					header: "预览",
					fixed: true,
					width:40,
					dataIndex: 'viewPic',
					renderer:this.imageView
				},{
					hidden:true,
					dataIndex : 'checkUserId',
					renderer:function(){
		               return this.userId;
		            }
				},{
					hidden:true,
					dataIndex:'projectId',
					value:this.projectId
				}],
				listeners : {
					'cellclick' : function(grid,rowIndex,columnIndex,e){
						var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
						if("uploadOrDown"==fieldName){
							 var markId=grid.getStore().getAt(rowIndex).get("id");
							 var projectId=grid.getStore().getAt(rowIndex).get("projectId");
							 var talbeName="bp_person_netCheckInfo.";
							 var mark=talbeName+markId;
							 var title = "上传材料";
							 if(isHidden){
							     uploadFileCounts=0;
							     title="下载材料";
							     uploadfile(title,mark,0,null,null,'SmallLoan');
							 }else{
								 uploadfile(title,mark,null,null,null,'SmallLoan');
							 }
						}
						if("viewPic"==fieldName){
							 var markId=grid.getStore().getAt(rowIndex).get("id");
							 var talbeName="bp_person_netCheckInfo.";
							 var mark=talbeName+markId;
						     picViewer(mark,false);
						}
					}
				}
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
					"isException" :(vRecords[i].data.isException==""?null:vRecords[i].data.isException),
					"remark" : vRecords[i].data.remark,
					"checkUserId" : (vRecords[i].data.checkUserId==""?null:vRecords[i].data.checkUserId),
					projectId:this.projectId
				};
				vDatas += Ext.util.JSON.encode(st) + '@';
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	},
	imageView : function(){
 		return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
	}
});
