/**
 * 人员架构列表
 * @class RecommendCodeCreateView
 * @extends Ext.Panel
 */
staffOnlyAll = Ext.extend(Ext.Panel, {
	constructor : function(config) {
		Ext.applyIf(this, config);
		this.initUIComponents();
        staffOnlyAll.superclass.constructor.call(this, {
			id : 'staffOnlyAll',
			height : 450,
			autoScroll : true,
			layout : 'border',
			title : '分级员工管理',
			iconCls:"menu-finance",
			items : [this.searchPanel, this.centerPanel]
			// items : [ this.centerPanel]
		});
	},
	initUIComponents : function() {
		var isShow = false;
		if (RoleType == "control") {
			isShow = true;
		}
		this.pageSize = 25;
		this.store = new Ext.data.JsonStore({
			url : __ctxPath+ '/system/findLowerAppUser.do',
			totalProperty : 'totalCounts',
			root : 'result',
			remoteSort : true,
			fields : [{name : 'userId'},{name:'fullname'},{name:'companyName'},{name:'username'},{name:'p2pAccount'},
					  {name : 'plainpassword'},{name:'mobile'},{name:'recommandNum'},{name:'status'},{name:'sumInvestMoney'},{name:'secondRecommandNum'},
					  {name : 'bpCustMemberId'}]
		});
		this.myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
		});

        // 查询面板
        this.searchPanel = new Ext.form.FormPanel({
            height : 45,
            region : "north",
            bodyStyle : 'padding:7px 0px 7px 10px',
            border : false,
            width : '100%',
            monitorValid : true,
            layout : 'column',
            defaults : {
                layout : 'form',
                border : false,
                bodyStyle : 'padding:5px 0px 0px 20px'
            },

            items: [{
                columnWidth: 0.2,
                layout: 'form',
                border: false,
                labelWidth: 100,
                labelAlign: 'right',
                items: [{
                    fieldLabel: '查询时间:从',
                    name: 'startDate',
                    flex: 1,
                    width: 105,
                    xtype: 'datefield',
                    format: "Y-m-d",
                    anchor: '100%'

                }]
            }, {
                columnWidth: 0.17,
                layout: 'form',
                border: false,
                labelWidth: 100 - 55,
                labelAlign: 'right',
                items: [{
                    fieldLabel: '截止到',
                    name: 'endDate',
                    flex: 1,
                    width: 105,
                    xtype: 'datefield',
                    format: "Y-m-d",
                    anchor: '100%'

                }]
            }
                , {
                    columnWidth: .07,
                    xtype: 'container',
                    layout: 'form',
                    defaults: {
                        xtype: 'button'
                    },
                    style: 'padding-left:10px;',
                    items: [{
                        text: '查询',
                        scope: this,
                        iconCls: 'btn-search',
                        handler: this.search
                    }]
                }
                , {
                    columnWidth: .07,
                    xtype: 'container',
                    layout: 'form',
                    defaults: {
                        xtype: 'button'
                    },
                    style: 'padding-left:10px;',
                    items: [{
                        text: '重置',
                        scope: this,
                        iconCls: 'btn-reset',
                        handler: this.reset
                    }]
            }]
        }); // 查询面板结束
		// 加载数据
		this.store.load({
			scope : this,
			params : {
				start : 0,
				limit : this.pageSize,
				isAll : isGranted('_seeAll_erp')
			}
		});
		var personStore = this.store;
		var tbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-unablep2p',
				text : '绑定P2P账户',
				xtype : 'button',
				hidden : isGranted('_bindingP2PAccount') ? false : true,
				scope : this,
				type:1,
				handler : this.addP2pUser
			}, new Ext.Toolbar.Separator({
				hidden : isGranted('_openP2PAccount') ? false : true
			}),{
                iconCls : 'btn-unablep2p',
                text : '查看所选下级',
                xtype : 'button',
                hidden : isGranted('_bindingP2PAccount') ? false : true,
                scope : this,
                type:1,
                handler : this.showLower
			}, new Ext.Toolbar.Separator({
                hidden : isGranted('_openP2PAccount') ? false : true
            }),{
                iconCls : 'btn-unablep2p',
                text : '查看推荐详情',
                xtype : 'button',
                hidden : isGranted('_bindingP2PAccount') ? false : true,
                scope : this,
                type:1,
                handler : this.showLowerInvent
            }]
		});
        var summary = new Ext.ux.grid.GridSummary();
        function totalMoney(v, params, data) {
            return '总计';
        }
		this.centerPanel = new HT.GridPanel({
			region : 'center',
			tbar : tbar,
			clicksToEdit : 1,
			store : this.store,
            plugins : [summary],
			height : 450,
			loadMask : this.myMask,
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			},
			viewConfig : {
				forceFit : true,
				autoFill : true
			},
			columns : [{
				header : "姓名",
				width : 100,
				align:'center',
                summaryRenderer : totalMoney,
				sortable : true,
				dataIndex : 'fullname'
			}, {
				header : "职位名称",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'companyName'
			}, {
				header : "推荐码",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'plainpassword'
			}, {
				header : "手机号",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'mobile'
			},{
				header : "邀请人数",
				align:'center',
				width : 100,
                summaryType : 'sum',
				sortable : true,
				dataIndex : 'recommandNum'
			},{
				header : "邀请已投资人数",
				align:'center',
                summaryType : 'sum',
				width : 100,
				sortable : true,
				dataIndex : 'secondRecommandNum'
			},{
				header : "业绩总额",
				width : 100,
                summaryType : 'sum',
				align:'center',
				sortable : true,
				dataIndex : 'sumInvestMoney'
			}]
		});

	},
    reset: function () {
        this.searchPanel.getForm().reset();
    },

    search: function () {
        $search({
            searchPanel : this.searchPanel,
            gridPanel : this.centerPanel
        });

    },
	//添加p2p账户
	addP2pUser : function(v) {
		new RecommendCodeCreateFormStaff({
			type:v.type//0代表开通，1代表绑定
		}).show();
	},
	//展示下级员工
    showLower : function(v) {
        var gridPanel=this.centerPanel;
        var selections = gridPanel.getSelectionModel().getSelections();
        var len = selections.length;
        if (len > 1) {
            Ext.ux.Toast.msg('状态', '只能选择一条记录');
            return;
        } else if (0 == len) {
            Ext.ux.Toast.msg('状态', '请选择一条记录');
            return;
        }else if (selections[0].data.userId=="" || selections[0].data.userId=="undefined") {
            Ext.ux.Toast.msg('状态', '请重新选择一条记录');
            return;
        }else if (selections[0].data.p2pAccount=="") {
            Ext.ux.Toast.msg('状态', '请先绑定P2P账号!!!');
            return;
        }
        new lowerStaff({
            id:selections[0].data.userId,
            fullname:selections[0].data.fullname
        }).show();
        },


    //展示下级员工邀请详情
    showLowerInvent : function(v) {
        var gridPanel=this.centerPanel;
        var selections = gridPanel.getSelectionModel().getSelections();
        var len = selections.length;
        if (len > 1) {
            Ext.ux.Toast.msg('状态', '只能选择一条记录');
            return;
        } else if (0 == len) {
            Ext.ux.Toast.msg('状态', '请选择一条记录');
            return;
        }else if (selections[0].data.userId=="" || selections[0].data.userId=="undefined") {
            Ext.ux.Toast.msg('状态', '请重新选择一条记录');
            return;
        }else if (selections[0].data.p2pAccount=="") {
            Ext.ux.Toast.msg('状态', '请先绑定P2P账号!!!');
            return;
        }
        new InvitesPersonsWindow({
            id:selections[0].data.userId//0代表开通，1代表绑定
        }).show();
    },

	//禁用P2P账号
	changeP2PStatus:function(v){
		var gridPanel=this.centerPanel;
		var selections = gridPanel.getSelectionModel().getSelections();
		var len = selections.length;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		} else if (selections[0].data.p2pAccount=="") {
			Ext.ux.Toast.msg('状态', '请先开通P2P账号!!!');
			return;
		}
		var isForbidden=0;
		//type用来标识是禁用操作还是开启操作
		if(v.type==0){
			isForbidden=1;
		}
		Ext.Ajax.request({
			url :__ctxPath + '/customer/changeP2PStatusBpCustRelation.do',
			params : {
				bpCustMemberId : selections[0].data.bpCustMemberId,
				isForbidden:isForbidden
			},
			scope:this,
			method : 'post',
			success : function(response) {
				var result = Ext.util.JSON.decode(response.responseText);
				if (result.success) {
					Ext.ux.Toast.msg('提示信息', result.msg);
					gridPanel.getStore().reload();
					this.close();
				} else {
					Ext.ux.Toast.msg('提示信息', result.msg);
				}
			}
		})
	}
});