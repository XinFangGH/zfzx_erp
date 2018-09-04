PublicActivityInfoWin = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		PublicActivityInfoWin.superclass.constructor.call(this, {
					id:'PublicActivityInfoWin',
			        buttonAlign:'center',
			        title:'社会活动',
					height : 500,
					width : 1000,
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
					items : [this.formPanel],
					buttons:[{
					    text : '保存',
						iconCls : 'btn-save',
						scope : this,
						hidden:this.isRead,
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
		this.PublicActivityInfoView =new PublicActivityInfo({
			personId:this.personId,
			createrId:this.createrId,
			creater:this.creater,
			isRead:this.isRead,
			isHiddenAddBtn:false,
			isHiddenDelBtn:false
		})
		
		this.formPanel = new Ext.FormPanel( {
			monitorValid : true,
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true ,
			border : false,
			layout : 'form',
			labelWidth:75,
			items : [{
					xtype : 'fieldset',
					title : '社会活动信息',
					name:"publicActivityInfo",
					collapsible : true,
					autoHeight : true,
					bodyStyle : 'padding-left: 0px',
					items : [this.PublicActivityInfoView]
				}]
		})
	},
	save:function(){
		var win=this;
		var publicActivityInfos=this.PublicActivityInfoView;
		// 得到修改的数据（记录对象）
		var vRecords = publicActivityInfos.items.get(0).getStore().getRange(0, publicActivityInfos.items.get(0).getStore().getCount()); 
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			// begin 将记录对象转换为字符串（json格式的字符串）
			for ( var i = 0; i < vCount; i++) {
				var str = Ext.util.JSON.encode(vRecords[i].data);
				//var index = str.lastIndexOf(",");
				//str = str.substring(0, index) + "}";
				vDatas += str + '@';
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		this.formPanel.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			url:__ctxPath + '/creditFlow/customer/person/saveBpCustPersonPublicActivity.do',
			params : {	 
						'personId':this.personId,
						'publicActivityInfos':vDatas
			},
			success : function(form, action) {
				Ext.ux.Toast.msg('操作信息', '保存成功!');
				win.destroy();
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('操作信息', '保存失败!');
			}
		});				
	 }		    
});

PublicActivityInfo = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		personId :null,
		createrId :null,
		creater :null,
		isHidden:true,
		isHiddenAddBtn : true,
		isHiddenDelBtn : true,
		constructor : function(_cfg) {
			if (typeof (_cfg.personId) != "undefined") {
				this.personId = _cfg.personId;
			}
			if (typeof (_cfg.createrId) != "undefined") {
				this.createrId = _cfg.createrId;
			}
			if (typeof (_cfg.creater) != "undefined") {
				this.creater = _cfg.creater;
			}
	        if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
				this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
			}
			if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
				this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
			}
			if (typeof(_cfg.isRead) != "undefined") {
				this.isRead = _cfg.isRead;
			}
			Ext.applyIf(this, _cfg);
			this.initUIComponents();
			PublicActivityInfo.superclass.constructor.call(this,{items : [ this.grid_PublicActivityInfo ]})
		},
		initUIComponents : function() {
			if(this.isHiddenDelBtn == true && this.isHiddenAddBtn == true) {
				this.isHidden = true;
			}
			/*this.datefield=new Ext.form.DateField({
				format : 'Y-m-d',
				readOnly:this.isHidden,
				allowBlank : false
			})*/

			this.PublicActivityInfoBar = new Ext.Toolbar({
				items : [{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						hidden : this.isRead,
						handler : this.createPublicActivityInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isRead
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						hidden : this.isRead,
						handler : this.deletePublicActivityInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isRead
					})]
				})

			this.grid_PublicActivityInfo = new Ext.grid.EditorGridPanel({
				border : false,
				name:"grid_PublicActivityInfo",
				tbar :this.PublicActivityInfoBar,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				enableDragDrop : false,
				viewConfig : {
					forceFit : true
				},
				sm : new Ext.grid.CheckboxSelectionModel({}),
				store : new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url:__ctxPath+ '/creditFlow/customer/person/listBpCustPersonPublicActivity.do?personId='+ this.personId,
								method : "POST"
					}),
					reader : new Ext.data.JsonReader({
						fields : Ext.data.Record.create( [
							{
								name : 'activityId'
							},
							{
								name : 'activityStartTime'
							},
							{
								name : 'activityEndTime'
							},
							{
								name : 'companyNature'
							},
							{
								name : 'companyName'
							},
							{
								name : 'duty'
							},
							{
								name : 'honorAndAchievement'
							},
							{
								name : 'personId'
							}
					]),
					root : 'result'
				})
			}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isRead}),
				new Ext.grid.RowNumberer(),{
						header : '开始时间',
						dataIndex : 'activityStartTime',
						sortable : true,
						align : "center",
						width:60,
						editor : {
							xtype : 'datefield',
							allowBlank : false,
							readOnly : this.isRead,
							editable : false
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.date(value,'Y-m-d');
						}
					},{
						header : '结束时间',
						dataIndex : 'activityEndTime',
						sortable : true,
						align : "center",
						width:60,
						editor : {
							xtype : 'datefield',
							allowBlank : false,
							readOnly : this.isRead,
							editable : false
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.date(value,'Y-m-d');
						}
					},{
						header : '单位性质',
						dataIndex : 'companyNature',
						sortable : true,
						align : "center",
						width:60,
						editor : {
							xtype : 'textfield',
							readOnly : this.isRead
						}
					},{
						header : '单位名称',
						dataIndex : 'companyName',
						sortable : true,
						align : "center",
						width:60,
						editor : {
							xtype : 'textfield',
							readOnly : this.isRead
						}
					},{
						header : '职务',
						dataIndex : 'duty',
						sortable : true,
						align : "center",
						width:80,
						editor : {
							xtype : 'textfield',
							readOnly : this.isRead
						}
					},{
						header : '社会荣誉与学术成就',
						dataIndex : 'honorAndAchievement',
						sortable : true,
						align : "center",
						width:80,
						editor : {
							xtype : 'textfield',
							readOnly : this.isRead
						}
					}]
              });
			this.grid_PublicActivityInfo.getStore().load();
		},
		
		createPublicActivityInfo : function() {
			var gridadd = this.grid_PublicActivityInfo;
			var storeadd = this.grid_PublicActivityInfo.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			p.data[keys[0]] = null;
			p.data[keys[1]] = null;
			p.data[keys[2]] = null;
			p.data[keys[3]] = null;
			p.data[keys[4]] = null;
			p.data[keys[5]] = null;
			p.data[keys[6]] = null;
			p.data[keys[7]] = this.personId;

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
		},
		deletePublicActivityInfo : function() {
			$delGridRs( {
				url : __ctxPath + '/creditFlow/customer/person/deleteBpCustPersonPublicActivity.do',
				grid : this.grid_PublicActivityInfo,
				idName : 'activityId'
			});
		}
	});
