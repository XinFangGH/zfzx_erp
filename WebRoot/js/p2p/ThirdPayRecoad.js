//PersonAccountDealInfoListQuery.js
/** 投资人收支查询---投资人账户明细查询 */
ThirdPayRecoad = Ext.extend(Ext.Panel, {
    titlePrefix : "",
    seniorHidden : false,
    Confirmhidden : false,
    constructor : function(_cfg) {
        Ext.applyIf(this, _cfg);
        // 初始化组件
        this.initUIComponents();
        // 调用父类构造
        ThirdPayRecoad.superclass.constructor.call(this, {
            id : 'ThirdPayRecoad'+this.Type,
            title :"三方请求查询",
            region : 'center',
            layout : 'border',
            iconCls:"menu-finance",
            items : [this.searchPanel, this.gridPanel]
        });
    },// end of constructor
    // 初始化组件
    initUIComponents : function() {

        var isShow = false;
        var rightValue =  isGranted('_investmentAccountDealInfoView_see_All');
        var isShop = isGranted('_investmentAccountDealInfoView_see_shop');
        if (RoleType == "control") {
            isShow = true;
        }

        var anchor = '100%';
        var typevalue=this.Type;
        this.searchPanel = new Ext.FormPanel({
            layout : 'column',
            region : 'north',
            border : false,
            height : 70,
            anchor : '100%',
            layoutConfig : {
                align : 'middle'
            },
            bodyStyle : 'padding-top:20px',
            items : [{
                columnWidth : .3,
                layout : 'form',
                labelWidth : 80,
                labelAlign : 'right',
                border : false,
                items : [{
                    fieldLabel : '投资人姓名',
                    allowBlank : false,
                    xtype:'textfield',
                    name : 'bpName'
                }]
            },{
                columnWidth : .2,
                layout : 'form',
                labelWidth : 80,
                labelAlign : 'right',
                border : false,
                items : [{

                    fieldLabel : '交易时间',
                    name : 'startDate',
                    anchor : "100%",
                    format : 'Y-m-d',
                    xtype : 'datefield'
                }]
            },{
                columnWidth : .3,
                layout : 'form',
                labelWidth : 80,
                labelAlign : 'right',
                border : false,
                items : [{
                    xtype : "combo",
                    anchor : "100%",
                    hiddenName : "transferType",
                    displayField : 'itemName',
                    valueField : 'itemValue',
                    triggerAction : 'all',
                    mode : 'remote',
                    store : new Ext.data.ArrayStore({
                        autoLoad : true,
                        url : __ctxPath
                        + '/creditFlow/creditAssignment/accountSetting/thirdListObSystemaccountSetting.do',
                        fields : ['itemValue', 'itemName']
                    }),
                    fieldLabel : "交易类型"

                }]
            },{
                columnWidth : .07,
                layout : 'form',
                border : false,
                items : [{
                    text : '查询',
                    xtype : 'button',
                    scope : this,
                    style : 'margin-left:20px',
                    anchor : "100%",
                    iconCls : 'btn-search',
                    handler : this.search
                }]
            },{
                columnWidth : .07,
                layout : 'form',
                border : false,
                items : [{
                    text : '重置',
                    style : 'margin-left:20px',
                    xtype : 'button',
                    scope : this,
                    anchor : "100%",
                    iconCls : 'btn-reset',
                    handler : this.reset
                }]
            }]
        });

        this.gridPanel = new HT.GridPanel({
            // name : 'confirmRechargeGrid',
            region : 'center',
            tbar : this.topbar,
            root : 'result',
            notmask : true,
            // 不适用RowActions
            rowActions : false,
            isautoLoad:false,
            url : __ctxPath+ "/creditFlow/creditAssignment/bank/ThirdPayRecoadObAccountDealInfo.do",
            fields : [{
                name : 'id',
                type : 'int'
            },'userId', 'loginName', 'truename', 'interfaceType','interfaceName' ,'requestTime', 'recordNumber', 'code', 'codeMsg','dataPackage'],

            columns : [{
                header: '用户id',
                dataIndex: 'userId',
                align: 'center',
                hidden:true

            },{
                header : '账户名',
                dataIndex : 'loginName',
                align:'center'

            },{
                header : '真实姓名',
                align:'center',
                dataIndex : 'truename'

            },{
                header : '交易类型编码',
                align:'center',
                dataIndex : 'interfaceType'

            },{
                header : '交易类型名称',
                align:'center',
                dataIndex : 'interfaceName'
            }, {
                header : '交易时间',
                align:'center',
                dataIndex : 'requestTime'

            }, {
                header : '订单号',
                align:'center',
                dataIndex : 'recordNumber'

            }, {
                header : '状态码',
                align:'center',
                dataIndex : 'code'
            }, {
                header : '状态说明',
                align:'center',
                dataIndex : 'codeMsg'
            }, {
                header : '数据包',
                align:'center',
                dataIndex : 'dataPackage'
            }]
        });
        /*this.gridPanel.addListener('afterrender', function() {
                    this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
                                msg : '正在加载数据中······,请稍候······',
                                store : this.gridPanel.store,
                                removeMask : true
                            });
                    this.loadMask1.show(); // 显示
                }, this);*/
    },// end of the initComponents()
    // 重置查询表单
    reset : function() {
        this.searchPanel.getForm().reset();
    },
    // 按条件搜索
    search : function() {
        $search({
            searchPanel : this.searchPanel,
            gridPanel : this.gridPanel
        });
    }
});