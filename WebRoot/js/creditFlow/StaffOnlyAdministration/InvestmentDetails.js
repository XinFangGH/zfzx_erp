/**
 * 投资详情查询
 *
 */
var userId;
var truename;
InvestmentDetails = Ext.extend(Ext.Window, {
    // 构造函数
    constructor: function (_cfg) {
        Ext.applyIf(this, _cfg);
        userId = this.id;
        truename = this.name;
        // 初始化组件
        this.initUIComponents();
        // 调用父类构造
        InvestmentDetails.superclass.constructor.call(this, {
            id: 'InvestmentDetails',
            title: "投资详情查询",
            height: 450,
            width: 1000,
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
            },{
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
                xtype: 'button',
                text: '<h1>客户姓名:' + truename + '</h1>',
                scope: this
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
            url: __ctxPath + "/p2p/InvestmentDetailsBpCustMember.do?id=" + userId,
            fields: [
                'bidName'
                , 'newInvestPersonName'
                , 'userMoney'
                , 'bidtime'
                , 'state'
            ],
            columns: [{
                header: '标的名称',
                dataIndex: 'bidName',
                align: 'center',
                summaryRenderer : totalMoney,
                anchor: "100%"
            }, {
                header: '标的期限',
                align: 'center',
                dataIndex: 'newInvestPersonName',
                anchor: "100%"
            }, {
                header: '投资金额(元)',
                dataIndex: 'userMoney',
                align: 'center',
                summaryType : 'sum',
                anchor: "100%"
            }, {
                header: '投资时间',
                align: 'center',
                dataIndex: 'bidtime',
                anchor: "100%"
            }, {
                header: '标的状态',
                align: 'center',
                dataIndex: 'state',
                anchor: "100%",
                renderer: function (value) {
                    if (value == 7) {
                        return "<font color='red'>还款中</font>"
                    } else if (value == 10) {
                        return "<font color='red' >已完成</font>"
                    } else {
                        return "<font color='red' >招标中</font>"
                    }
                }
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
        } else if (0 == len) {
            Ext.ux.Toast.msg('状态', '请选择一条记录');
            return;
        } else if (selections[0].data.totalInvestMoney == "" || selections[0].data.totalInvestMoney == "0") {
            Ext.ux.Toast.msg('状态', '该用户没有投资记录');
            return;
        }


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

