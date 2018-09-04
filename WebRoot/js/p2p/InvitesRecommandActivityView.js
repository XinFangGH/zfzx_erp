/**
 *限时活动推荐查询
 *
 * @auther: XinFang
 * @date: 2018/7/4 17:43
 */
InvitesRecommandActivityView = Ext.extend(Ext.Panel, {
    // 构造函数
    constructor: function (_cfg) {
        Ext.applyIf(this, _cfg);
        // 初始化组件
        this.initUIComponents();
        // 调用父类构造
        InvitesRecommandActivityView.superclass.constructor.call(this, {
            id: 'InvitesRecommandActivityView',
            title: "活动推荐信息查询",
            region: 'center',
            iconCls: "menu-finance",
            layout: 'border',
            items: [this.searchPanel, this.gridPanel]
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
                columnWidth: .2,
                layout: 'form',
                labelWidth: 80,
                labelAlign: 'right',
                border: false,
                items: [{
                    fieldLabel: '推荐人用户名',
                    name: 'Q_loginname_S_LK',
                    flex: 1,
                    anchor: "100%",
                    xtype: 'textfield'
                }, {
                    fieldLabel: '年大于10万',
                    name: 'yearThan',
                    flex: 1,
                    anchor: "90%",
                    xtype: 'checkbox'
                }]
            }, {
                columnWidth: .2,
                layout: 'form',
                labelWidth: 80,
                labelAlign: 'right',
                border: false,
                items: [{
                    fieldLabel: '推荐人邀请码',
                    name: 'Q_recommandPerson_S_LK',
                    flex: 1,
                    anchor: "100%",
                    xtype: 'textfield'
                }, {
                    fieldLabel: '总额8-20万',
                    name: 'sumThird',
                    flex: 1,
                    anchor: "90%",
                    xtype: 'checkbox'
                }]
            }, {
                columnWidth: .2,
                layout: 'form',
                labelWidth: 80,
                labelAlign: 'right',
                border: false,
                items: [{
                    fieldLabel: '推荐人姓名',
                    name: 'Q_truename_S_LK',
                    flex: 1,
                    anchor: "100%",
                    xtype: 'textfield'
                }, {
                    fieldLabel: '总额21-51万',
                    name: 'sumSecond',
                    flex: 1,
                    anchor: "90%",
                    xtype: 'checkbox'
                }]
            }, {
                columnWidth: .2,
                layout: 'form',
                border: false,
                labelWidth: 80,
                items: [{
                    text: '查询',
                    xtype: 'button',
                    scope: this,
                    name: 'aaa',
                    style: 'margin-left:30px',
                    anchor: "50%",
                    iconCls: 'btn-search',
                    handler: this.search
                }, {
                    fieldLabel: '总额51万以上',
                    name: 'sumFirst',
                    flex: 1,
                    anchor: "90%",
                    xtype: 'checkbox'
                }]
            }]

        });// end of searchPanel


        this.gridPanel = new HT.GridPanel({
            region: 'center',
            //使用RowActions
            //rowActions:true,
            id: 'BpCustMemberGrid',
            url: __ctxPath + "/p2p/listActicityBpCustMember.do",
            fields: [{
                name: 'id',
                type: 'int'
            }
                , 'loginname'
                , 'truename'
                , 'plainpassword'
                , 'yearMoney'
                , 'mouthInventCount'
                , 'sumActicityMoney'
                , 'recommandNum'
                , 'secondRecommandNum'
            ],
            columns: [{
                header: '推荐人用户名',
                dataIndex: 'loginname',
                width: 80,
                anchor: "100%"
            }, {
                header: '推荐人邀请码',
                width: 80,
                dataIndex: 'plainpassword',
                anchor: "100%"

            }, {
                header: '推荐人姓名',
                align: 'center',
                dataIndex: 'truename',
                width: 50,
                anchor: "100%"

            }, {
                header: '推荐总人数',
                dataIndex: 'recommandNum',
                width: 50,
                anchor: "100%"

            }, {
                header: '7月年期限标的投资总额',
                width: 80,
                dataIndex: 'yearMoney',
                anchor: "100%"

            }, {
                header: '7月投资总额(3月标*1+6月标*1.4+1年标*2)',
                dataIndex: 'sumActicityMoney',
                anchor: "100%"

            }, {
                header: '7月邀请人数',
                width: 50,
                dataIndex: 'mouthInventCount',
                anchor: "100%"

            }]
        });

        //this.gridPanel.addListener('rowdblclick',this.rowClick);

    },// end of the initComponents()


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
    //GridPanel行点击处理事件
    /*rowClick:function(grid,rowindex, e) {
        grid.getSelectionModel().each(function(rec) {
            new BpCustMemberForm({id:rec.data.id}).show();
        });
    },*/

    //行的Action
    onRowAction: function (grid, record, action, row, col) {
        switch (action) {
            case 'btn-del' :
                this.removeRs.call(this, record.data.id);
                break;
            case 'btn-edit' :
                this.editRs.call(this, record);
                break;
            case 'btn-forbidden' :
                this.forbiddenRs.call(this, record.data.id);
                break;
            default :
                break;
        }
    }
});

