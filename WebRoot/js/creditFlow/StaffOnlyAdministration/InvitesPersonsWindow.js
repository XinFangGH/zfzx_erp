/**
 * 推荐详情查询窗口
 *
 */
var parentId;
var fullname;
InvitesPersonsWindow = Ext.extend(Ext.Window, {
    // 构造函数
    constructor: function (_cfg) {
        Ext.applyIf(this, _cfg);
        parentId=this.id;
        fullname=this.fullname;
        // 初始化组件
        this.initUIComponents();
        // 调用父类构造
        InvitesPersonsWindow.superclass.constructor.call(this, {
            id: 'InvitesPersonsWindow'+parentId,
            height : 450,
            width:1000,
            title: "推荐详情查询",
            region: 'center',
            iconCls: "menu-finance",
            layout: 'border',
            items: [this.gridPanel, this.searchPanel]
        });
    },// end of constructor
    // 初始化组件

    initUIComponents: function () {

        // 初始化搜索条件Panel
        this.searchPanel = new HT.SearchPanel({
            layout: 'column',
            autoScroll: true,
            region: 'north',
            border: false,
            height: 50,
            anchor: '80%',
            layoutConfig: {
                align: 'middle'
            },
            bodyStyle: 'padding:10px 10px 10px 10px',
            items: [{
                columnWidth: .25,
                layout: 'form',
                labelWidth: 120,
                labelAlign: 'right',
                border: false,
                items: [{
                    fieldLabel: '用户名/手机号/姓名',
                    name: 'Q_loginname_S_LK',
                    flex: 1,
                    anchor: "90%",
                    xtype: 'textfield'
                }]
            }, {
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
                columnWidth: 0.2,
                layout: 'form',
                border: false,
                labelWidth: 100,
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
            }, {
                columnWidth: .1,
                layout: 'form',
                border: false,
                labelWidth: 50,
                items: [{
                    text: '查询',
                    xtype: 'button',
                    scope: this,
                    style: 'margin-left:30px',
                    anchor: "90%",
                    iconCls: 'btn-search',
                    handler: this.search
                }]
            }, {
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

        });// end of searchPanel

        this.topbar = new Ext.Toolbar({
            items: [{
                iconCls: 'btn-search',
                text: '查询投资详情',
                xtype: 'button',
                scope: this,
                hidden: false,
                handler: this.searchInfo
            },{
                iconCls: 'btn-xls',
                text: '导出到Excel',
                xtype: 'button',
                scope: this,
                hidden: false,
                handler: this.export2Excle
            }]
        });
        var summary = new Ext.ux.grid.GridSummary();
        function totalMoney(v, params, data) {
            return '总计(元)';
        }
        this.gridPanel = new HT.GridPanel({
            region: 'center',
            tbar: this.topbar,
            plugins : [summary],
            //使用RowActions
            //rowActions:true,
            url: __ctxPath + "/p2p/searchInvitesBpCustMember.do?userId="+parentId,
            fields: [{
                name: 'userId',
                type: 'int'
            }
                , 'userName'
                , 'truename'
                , 'userMoney'
                , 'telphone'
                , 'bidtime'
                , 'cardcode'
                , 'bidName'
                , 'newInvestPersonName'
            ],
            columns: [{
                header: 'ID',
                dataIndex: 'id',
                hidden: true,
                anchor: "100%"
            }, {
                header: '用户名',
                dataIndex: 'userName',
                align: 'center',
                anchor: "100%"
            }, {
                header: '姓名',
                align: 'center',
                dataIndex: 'truename',
                summaryRenderer : totalMoney,
                anchor: "100%"
            }, {
                header: '手机号码',
                align: 'center',
                dataIndex: 'telphone',
                anchor: "100%"
            }, {
                header: '标的名称',
                align: 'center',
                dataIndex: 'bidName',
                anchor: "100%"
            }, {
                header: '标的期限',
                align: 'center',
                dataIndex: 'newInvestPersonName',
                anchor: "100%"
            }, {
                header: '投资金额',
                align: 'center',
                dataIndex: 'userMoney',
                summaryType : 'sum',
                anchor: "100%"
            }, {
                header: '投资时间',
                align: 'center',
                dataIndex: 'bidtime',
                anchor: "100%"

            }]
        });

        //this.gridPanel.addListener('rowdblclick',this.rowClick);

    },// end of the initComponents()

    /**
     * 查看投资详细情况
     */
    searchInfo: function () {
        var selections = this.gridPanel.getSelectionModel().getSelections();
        var len = selections.length;
        if (len > 1) {
            Ext.ux.Toast.msg('状态', '只能选择一条记录');
            return;
        } else if (0 === len) {
            Ext.ux.Toast.msg('状态', '请选择一条记录');
            return;
        }
        new InvestmentDetails({
            id: selections[0].data.userId,
            name: selections[0].data.truename
        }).show()
    },

    /**
     * 导出到excle
     */
    export2Excle:function () {
        var counts = this.gridPanel.getStore().getCount();
        if (counts === 0){
            Ext.ux.Toast.msg('状态', '当前无数据不能导出');
            return;
        }

        var startDate=this.searchPanel.getCmpByName('startDate').getValue();
        var endDate=this.searchPanel.getCmpByName('endDate').getValue();
        var name=this.searchPanel.getCmpByName('Q_loginname_S_LK').getValue();
        if(startDate!=null&&startDate!="undefined"&&startDate!=""){
            startDate=startDate.format('Y-m-d');
        }
        if(endDate!=null&&endDate!=""&&endDate!="undefined"){
            endDate=endDate.format('Y-m-d');
        }
        window.open( __ctxPath + "/p2p/searchInvitesBpCustMember.do?type=export&startDate="+startDate+"&endDate="+endDate
            +"&Q_loginname_S_LK="+name+"&fullname="+fullname+"&userId="+parentId)
    },

    //重置查询表单
    reset: function () {
        this.searchPanel.getForm().reset();
    },
    //按条件搜索
    search: function () {
        $search({
            searchPanel: this.searchPanel,
            gridPanel: this.gridPanel
        });
    },


});

