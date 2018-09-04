LeaseObjectList = Ext.extend(Ext.Panel, {
	// 构造函数
	isHandleHidden : true,
	isHandleEdit : true,
	manageBtnHiddent:true,
	isManagedEditable:false,//默认不可编辑 是否处理
	isManagedHidden:true,//默认 不显示 是否处理
	isBuyBackHidden:true,//默认 不显示  是否回购
	isBuyBackEditable:false,//默认 不可编辑 是否回购      this.isBuyBackEditable?false:this.isBuyBackHidden
	constructor : function(_cfg) {
		if(typeof(_cfg.readOnly)!="undefined")
        {
              this.readOnly=_cfg.readOnly;
        }
        if(typeof(_cfg.projectId)!="undefined")
        {
              this.projectId=_cfg.projectId;
        }
        if(typeof(_cfg.manageBtnHiddent)!="undefined")
        {
              this.manageBtnHiddent=_cfg.manageBtnHiddent;
        }
        if(typeof(_cfg.isManagedHidden)!="undefined"){
        	  this.isManagedHidden = _cfg.isManagedHidden;
        }
        if(typeof(_cfg.isBuyBackHidden)!="undefined"){
        	  this.isBuyBackHidden = _cfg.isBuyBackHidden;
        }
        if(typeof(_cfg.isBuyBackEditable)!="undefined"){
        	  this.isBuyBackEditable = _cfg.isBuyBackEditable;
        }
        if(typeof(_cfg.isManagedEditable)!="undefined"){
        	  this.isManagedEditable = _cfg.isManagedEditable;
        }
		Ext.applyIf(this, _cfg);
		var jsArr = [
				__ctxPath + '/js/creditFlow/leaseFinance/leaseobject/seeObjectInfo.js'//租赁标的查看功能
						]
		 $ImportSimpleJs(jsArr, this.constructPanel, this);
		
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		LeaseObjectList.superclass.constructor.call(this, {
			layout : 'anchor',
			anchor : "100",
			autoWidth:true,
			items : [ 
//					{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【'+this.titleText+'】:</font></B>'},
					this.gridPanel ]
		});
	},
	initUIComponents : function() {
		this.topbar = new Ext.Toolbar( {
			items : [{
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				scope : this,
				hidden : this.readOnly,
				handler : this.createLeaseObject
			}, {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				hidden : this.readOnly,
				handler : this.removeLeaseObject
			}, {
				iconCls : 'btn-edit',
				text : '编辑',
				xtype : 'button',
				scope : this,
				hidden : this.readOnly,
				handler : this.editLeaseObject
			},{
				iconCls : 'btn-readdocument',
				text : '查看',
				xtype : 'button',
				scope : this,
				hidden : false,
				handler : this.seeObject
			}, this.manageBtnHiddent?{}:{
				iconCls : 'btn-flow-design',	
				text : '办理手续',
				xtype : 'button',
				scope : this,
				handler : this.manageLeaseObject
			}		
			]
		});
		this.confirmDatefield=new Ext.form.DateField({
					format : 'Y-m-d',					
					readOnly:!this.isgdEdit,
					allowBlank : false
				})
		this.gridPanel = new HT.EditorGridPanel({
			border : false,
			isShowTbar : true,
			tbar : this.topbar,
			clicksToEdit : 1,
			showPaging:false,
			autoHeight:true,
			viewConfig : {
				forceFit : true
			},
			listeners : {
				scope : this,
				afteredit : function(e) {
						if (e.originalValue != e.value) {
							var args ;
							if(e.field =='isManaged'){
								args = {
										'flLeaseobjectInfo.isManaged' : e.value,
										'flLeaseobjectInfo.id' : e.record.data['id']
									}
							}else if(e.field =='isBuyBack'){
								args = {
										'flLeaseobjectInfo.isBuyBack' : e.value,
										'flLeaseobjectInfo.id' : e.record.data['id']
									}
							}
							Ext.Ajax.request({
									url :__ctxPath	+ '/creditFlow/leaseFinance/project/updateFlLeaseobjectInfo.do',
									method : 'POST',
									scope :this,
									success : function(response, request) {
										//this.grid_contractPanel.getStore().reload();
										e.record.commit();
									},
									failure : function(response) {
										Ext.ux.Toast.msg('状态', '操作失败，请重试！');
									},
									params: args
								})
						}
				},
				'cellclick':function(grid,row,col,e){
					var fieldName = grid.getColumnModel().getDataIndex(col); //Get field name
					var objId = grid.getStore().getAt(row).get("id");
					var fileCountVal = grid.getStore().getAt(row).get("fileCount");
					if(fieldName == 'fileCount'){//重新上传
						if(0==fileCountVal||'0'==fileCountVal){
							return false;
						}else{
							this.seeFileCountInfo(objId);
						}
					}
				}
			},
			rowActions : false,
			url : __ctxPath +'/creditFlow/leaseFinance/project/listViewFlLeaseobjectInfo.do?projectId='+this.projectId,//+'&businessType='+this.businessType+'&isReadOnly='+this.isReadOnly,
			fields : [{
						name : 'id',
						type : 'int'
					}, 'name','standardSize','originalPrice','buyPrice','objectCount','useYears','buyDate','suppliorName','owner','fileCount','isManaged','isBuyBack'],
			columns : [ {
				header : 'id',
				dataIndex : 'id',
				align:'center',
				hidden : true
			}, {
				header : '租赁标的名称',
				align:'center',
				dataIndex : 'name'
			}, {
				header : '规格型号',
				align:'center',
				dataIndex : 'standardSize'
			}, {
				header : '原价格',
				dataIndex : 'originalPrice',
				align:'center',
				renderer : function(v){
					if(v==''||v=='null'||v==null){
						return '';
					}else{
						return Ext.util.Format.number(v,'0,000.00')+"元"	
					}
				}
			}, {
				header : '评估价格',
				dataIndex :'buyPrice',
				align:'center',
				renderer : function(v){
					if(v==''||v=='null'||v==null){
						return '';
					}else{
						return Ext.util.Format.number(v,'0,000.00')+"元"	
					}
				}
			},{
				header : '数量',
				align:'center',
				dataIndex : 'objectCount'
			},{
				header : '使用年限',
				align:'center',
				dataIndex : 'useYears'
			},{
				header : '购入日期',
				dataIndex : 'buyDate',
				align : "center",
				hidden : this.isgdHidden,
				editor : this.confirmDatefield,
				renderer : ZW.ux.dateRenderer(this.confirmDatefield)
			},{
				header : '供货单位',
				align:'center',
				dataIndex : 'suppliorName'
			},{
				header : '材料份数',
				align:'center',
				dataIndex : 'fileCount',
				scope : this,
				renderer : function(val, m, r) {
						return '<a title="预览'+r.get('fileCount')+'" style ="TEXT-DECORATION:underline;cursor:pointer" >'+ val + '</a>';
				}
			},{
				header : '所有人',
				align:'center',
				dataIndex : 'owner'
			},{
				header : '是否办理',
				sortable : true,
				hidden : this.isManagedEditable?false:this.isManagedHidden, //新增 判断    兼容过去  未设置属性的
				editor : new Ext.form.ComboBox({
							mode : 'local',
							editable : this.isManagedEditable,
							store : new Ext.data.SimpleStore({
										data : [['是', true], ['否', false]],
										fields : ['text', 'value']
									}),
							displayField : 'text',
							valueField : 'value',
							triggerAction : 'all'
						}),
				dataIndex : 'isManaged',
				renderer : this.manageRenderer
			},{
				header : '是否回购',
				sortable : true,
				hidden : this.isBuyBackEditable?false:this.isBuyBackHidden, //新增 判断    兼容过去  未设置属性的
				editor : new Ext.form.ComboBox({
							mode : 'local',
							editable : this.isBuyBackEditable,
							store : new Ext.data.SimpleStore({
										data : [['是', true], ['否', false]],
										fields : ['text', 'value']
									}),
							displayField : 'text',
							valueField : 'value',
							triggerAction : 'all'
						}),
				dataIndex : 'isBuyBack',
				renderer : this.manageRenderer
			}]
		});
	},
	//创建租赁标的信息
	createLeaseObject : function() {
			new AddLeaseObjectWin({projectId:this.projectId,gridPanel:this.gridPanel}).show()//id  test
	},
	//把选中ID删除
	removeLeaseObject : function() {
		
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var grid=this.gridPanel;
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择记录!');
				}else{
					var leaseObjectIds = "";
					var rows = this.gridPanel.getSelectionModel().getSelections();
					for(var i=0;i<rows.length;i++){
						leaseObjectIds = leaseObjectIds+rows[i].get('id');
						
						if(i!=rows.length-1){
							leaseObjectIds = leaseObjectIds+',';
						}
					}
					Ext.MessageBox.confirm('确认删除', '该记录在附表同时存在相应记录,您确认要一并删除? ', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : __ctxPath +'/creditFlow/leaseFinance/project/multiDelFlLeaseobjectInfo.do',
								method : 'POST',
								success : function() {
									Ext.ux.Toast.msg('操作信息', '删除成功!');
									grid.getStore().remove(rows);
									grid.getStore().reload();
								},
								failure : function(result, action) {
									Ext.ux.Toast.msg('操作信息', '删除失败!');
								},
								params: { 
									ids: leaseObjectIds
								}
							});
						}
					});
				}
	},
	editLeaseObject : function(record) {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{
				record=s[0]
				new AddLeaseObjectWin({projectId:this.projectId,gridPanel:this.gridPanel,objectId:record.id}).show()//id  test
		}	
	},
	manageLeaseObject : function(record) {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{
				record=s[0]
				new AddLeaseObjectWin({projectId:this.projectId,gridPanel:this.gridPanel,objectId:record.id,isManageWin:true}).show()// isManageWin   true  则显示 办理表单项目
		}	
	},
	manageRenderer : function(v) {
		if (v == '' || v == null) {
			return '<font color=red>否</font>';
		} else if (v == true) {
			return '<font color=green>是</font>';
		} else if (v == false) {
			return '<font color=red>否</font>';
		} else {
			return v;
		}
	},
	seeFileCountInfo:function(objId){
		new AddLeaseObjectWin({projectId:this.projectId,gridPanel:this.gridPanel,objectId:objId,onlyFile:true}).show()//id  test
	},
	
	
	seeObject :function(record) {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
		var id = selected.get('id');
		seeObjectInfo(id,"leaseFinanceObject");
		}
	}
});
