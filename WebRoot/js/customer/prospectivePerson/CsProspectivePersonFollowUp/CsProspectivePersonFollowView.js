/**
 * CsProspectivePersonFollowView.js
 */
CsProspectivePersonFollowView = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	name:"CsProspectivePersonFollowView_info",
	titlePrefix : "",
	tabIconCls : "",
	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		CsProspectivePersonFollowView.superclass.constructor.call(this, {
			items : [this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
	
		this.topbar = new Ext.Toolbar({
				items : [{
						iconCls : 'btn-detail',
						text : '查看',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenseeBtn,
						handler : this.seeProductInfo
					},{
						iconCls : 'btn-edit',
						text : '编辑',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddeneditBtn,
						handler : this.editProductInfo
					},{
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenDeleBtn,
						handler : this.deleteProductInfo
					}]
				})
	
		
		this.gridPanel = new HT.EditorGridPanel({
			name : 'CsProspectivePersonFollowViewGrid',
			region : 'center',
			showPaging:false,
			id:"CsProspectivePersonFollowView",
			tbar : this.topbar,
			//notmask : true,
			// 不适用RowActions
			autoHeight:true,
			url : __ctxPath + "/creditFlow/customer/customerProsperctiveFollowup/listByPerIdBpCustProspectiveFollowup.do?perId"+this.perId,
			baseParams : {
				'perId' : this.perId
			},
			fields : [{
				name : 'followId',
				type : 'int'
			}, 'followPersonId','name','followDate', 'followType', 'followTitle', 'followInfo', 'successRate',
					'followUpStatus', 'commentorrId','commentorName', 'commentRemark','bpCustProsperctive.perId','bpCustProsperctive.customerName',
					'bpCustProsperctive.customerType','bpCustProsperctive.telephoneNumber'],

			columns : [{
				header : 'followId',
				dataIndex : 'followId',
				hidden : true
			},{
				header : '跟进人',
				width : 130,
				dataIndex : 'name'
			}, {
				header : '跟进时间',
				width : 410,
				dataIndex : 'followDate'
			}, {
				header : '跟进方式',
				align : 'right',
				width : 110,
				sortable : true,
				xtype:'combocolumn',
                gridId:'CsProspectivePersonFollowView',
				dataIndex : 'followType',
				editor:{
						xtype : "dickeycombo",
						nodeKey : 'comm_type',
						anchor : '100%',
						editable : true,
						readOnly : true,
						listeners : {
							afterrender : function(combox) {
							var st = combox.getStore();
								st.on("load", function() {
									if (combox.getValue() == 0|| combox.getValue() == 1|| combox.getValue() == ""|| combox.getValue() == null) {
									combox.setValue("");
									} else {
											combox.setValue(combox.getValue());
									}
									combox.clearInvalid();
								})
							},
							select : function(combo, record,index) {}
					}
									
				}
			}, {
				header : '标题',
				width : 98,
				dataIndex : 'followTitle'
			},{
				header : '跟进内容',
				width : 80,
				dataIndex : 'followInfo'
			}, {
				header : '点评人',
				width : 130,
				dataIndex : 'commentorName'
			},{
				header : '点评内容',
				width : 80,
				dataIndex : 'commentRemark'
			}]
		});
		this.gridPanel.addListener('afterrender', function() {
			this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
				msg : '正在加载数据中······,请稍候······',
				store : this.gridPanel.store,
				removeMask : true
					// 完成后移除
					});
			this.loadMask1.show(); // 显示

		}, this);
	},

	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//查看跟进记录
	seeProductInfo:function(){
		var rows = this.gridPanel.getSelectionModel().getSelections();
		if (rows.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (rows.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
				var followId=rows[0].data.followId
				var editForm=new CommRecorForm({
					followId:followId,
					flashTargat:this.gridPanel,
					titleChange:"查看跟进记录",
					isLook:true,
					isRead:true,
					isReadOnly:true
				})
				editForm.show();	
		}
	
		
	},
	//编辑跟进记录
	editProductInfo:function(){
		var rows = this.gridPanel.getSelectionModel().getSelections();
		if (rows.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (rows.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
				var followId=rows[0].data.followId
				var editForm=new CommRecorForm({
					followId:followId,
					flashTargat:this.gridPanel.getStore(),
					titleChange:"编辑跟进记录",
					isRead:false,
					isReadOnly:true
					
				})
				editForm.show();	
		}
	
		
	},
	//删除跟进记录
	deleteProductInfo:function(){
		var griddel =this.gridPanel.getStore();
		var rows = this.gridPanel.getSelectionModel().getSelections();
		if (rows.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (rows.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
				var followId=rows[0].data.followId
				var perId =rows[0].get("bpCustProsperctive.perId");
				Ext.Msg.confirm("提示!",'确定要删除吗？',
					function(btn) {
						if (btn == "yes") {
							deleteFun(__ctxPath+'/creditFlow/customer/customerProsperctiveFollowup/multiDelBpCustProspectiveFollowup.do',
										{ids :followId ,perId:perId},function() {},0,1)
									griddel.reload();
								}
							}
						)	
		}
		
	}
	
});