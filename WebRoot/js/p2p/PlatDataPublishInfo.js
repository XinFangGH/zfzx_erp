/**
 * @author
 * @class PlatDataPublishForm
 * @extends Ext.Panel
 * @description [PlatDataPublishForm]管理
 * @company 互融软件
 * @createtime:
 */


PlatDataPublishInfo = Ext.extend(Ext.Panel, {
        // 构造函数
        constructor: function (_cfg) {
            Ext.applyIf(this, _cfg);
            // 初始化组件
            this.initUIComponents();
            // 调用父类构造
            PlatDataPublishInfo.superclass.constructor.call(this, {
                id: 'PlatDataPublishInfo' + this.typeId,
                title: "平台数据详情",
                region: 'center',
                layout: 'fit',
                modal: true,
                //height :800,
                //width : 800,
                height: 1800,
                width: 1200,
                maximizable: true,
                iconCls: "menu-finance",
                tbar: this.ttbar,
                items: [this.searchPanel, this.formPanel]
            });
        },// end of constructor
        // 初始化组件
        initUIComponents: function () {
            //查询条件组件
            var labelsize = 100;
            this.searchPanel = new HT.SearchPanel({
                layout: 'column',
                style: 'padding-left:5px;padding-right:5px;padding-top:5px;',
                region: 'north',
                height: 40,
                anchor: '96%',
                keys: [{
                    key: Ext.EventObject.ENTER,
                    fn: this.search,
                    scope: this
                }, {
                    key: Ext.EventObject.ESC,
                    fn: this.reset,
                    scope: this
                }],
                layoutConfig: {
                    align: 'middle',
                    padding: '5px'

                },
                //            bodyStyle : 'padding:10px 10px 10px 10px',
                items: [{
                    columnWidth: 0.2,
                    layout: 'form',
                    border: false,
                    labelWidth: labelsize,
                    labelAlign: 'right',
                    items: [{
                        fieldLabel: '查询时间:从',
                        name: 'intentDatel',
                        id:'startDate',
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
                    labelWidth: labelsize - 55,
                    labelAlign: 'right',
                    items: [{
                        fieldLabel: '截止到',
                        id:'endDate',
                        name: 'intentDateg',
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
                    }  , {
                        columnWidth: .1,
                        xtype: 'container',
                        layout: 'form',
                        defaults: {
                            xtype: 'button'
                        },
                        style: 'padding-left:10px;',
                        items: [{
                            text: '查询年龄分布',
                            scope: this,
                            iconCls: 'btn-search',
                            handler:function () {
                                Ext.Ajax.request({
                                    url : __ctxPath+'/p2p/searchAgeBpCustMember.do?startDate='+Ext.get('startDate').getValue()+'&endDate='+Ext.get('endDate').getValue(),
                                    method : "post",
                                    scope:this,
                                    success : function(totalCounts) {
                                        var array = new Array();
                                        array    = Ext.util.JSON.decode(totalCounts.responseText).result;

                                        Ext.MessageBox.alert("年龄分布:","<h1>18-20比例:<span style='color: red'>"+array[0].toFixed(4)*100+"%</span></h1>"
                                        +"<h1>18-20人数:<span style='color: red'>"+array[1]+"人</span></h1></br>"
                                        +"<h1>20-30比例:<span style='color: red'>"+array[2].toFixed(4)*100.+"%</span></h1>"
                                        +"<h1>20-30人数:<span style='color: red'>"+array[3]+"人</span></h1></br>"
                                        +"<h1>30-40比例:<span style='color: red'>"+array[4].toFixed(4)*100+"%</span></h1>"
                                        +"<h1>30-40人数:<span style='color: red'>"+array[5]+"人</span></h1></br>"
                                        +"<h1>40-50比例:<span style='color: red'>"+array[6].toFixed(4)*100+"%</span></h1>"
                                        +"<h1>40-50人数:<span style='color: red'>"+array[7]+"人</span></h1></br>"
                                        +"<h1>50-60比例:<span style='color: red'>"+array[8].toFixed(4)*100+"%</span></h1>"
                                        +"<h1>50-60人数:<span style='color: red'>"+array[9]+"人</span></h1></br>"
                                        +"<h1>60以上比例:<span style='color: red'>"+array[10].toFixed(4)*100+"%</span></h1>"
                                        +"<h1>60以上人数:<span style='color: red'>"+array[11]+"人</span></h1>"
                                        );

                                    }
                                })

                            }
                        }]
                    } , {
                        columnWidth: .2,
                        xtype: 'container',
                        layout: 'form',
                        defaults: {
                            xtype: 'button'
                        },
                        style: 'padding-left:10px;',
                        items: [{
                            text: '查询地区性别比例',
                            scope: this,
                            iconCls: 'btn-search',
                            handler: this.addressSex
                        }]
                    }

                ]

            });//end查询条件
            var leftlabel = 150;
            var rightlabel = 160;
            this.formPanel = new Ext.FormPanel({
                region: 'center',
                layout: 'form',
                bodyStyle: 'padding:10px',
                autoScroll: true,
                frame: true,
                labelAlign: 'right',
                defaults: {
                    //anchor : '60%',
                    anchor: '100%',
                    columnWidth: 1,
                    labelWidth: 60
                },
                items: [{
                    layout: "form", // 从上往下的布局
                    xtype: 'fieldset',
                    title: '平台披露数据详情',
                    items: [{
                        layout: "column",
                        border: false,
                        scope: this,
                        defaults: {
                            anchor: '100%',
                            columnWidth: 1,
                            isFormField: true,
                            labelWidth: leftlabel
                        },
                        items: [{
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '累计交易总额',
                                name: 'platDataPublish.sumMoney',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',
                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '累计交易笔数',
                                name: 'platDataPublish.sumCount',
                                maxLength: 100,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%'
                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>笔</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '借贷余额',
                                name: 'platDataPublish.balanceLoanMoney',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '借贷余额笔数',
                                name: 'platDataPublish.balanceLoanCount',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%'
                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>笔</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '为客户赚取收益',
                                name: 'platDataPublish.sumProfit',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%'
                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        },  {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '利息余额',
                                name: 'platDataPublish.interestBalance',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%'
                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '存量不合规余额',
                                name: 'overdueMoney',
                                maxLength: 100,
                                value:'0',
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '存量不合规利息余额',
                                name: 'compensatoryMoney',
                                maxLength: 100,
                                value:'0',
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '累计借款人数量',
                                name: 'platDataPublish.sumIncomePeople',
                                maxLength: 100,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%'
                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>人</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '累计出借人数量',
                                name: 'platDataPublish.sumPayPeople',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%'
                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>人</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '当前借款人数量',
                                name: 'platDataPublish.incomePeople',
                                maxLength: 100,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%'
                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>人</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '当前出借人数量',
                                name: 'platDataPublish.payPeople',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%'
                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>人</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            //columnWidth :.5, // 该列有整行中所占百分比
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '前十大借款人待还金额占比',
                                name: 'platDataPublish.tenIncomeMoneyProporion',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%'
                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '最大单一借款人待还金额占比',
                                name: 'platDataPublish.maxIncomeMoneyProporion',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '关联关系借款余额',
                                name: 'platDataPublish.',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',


                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '关联关系借款余额笔数',
                                name: 'platDataPublish.toEarnCount',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%'
                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>笔</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .5, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                fieldLabel: ' 收费标准',
                                name: 'platDataPublish.manageInformation',
                                xtype: 'textfield',
                                value: '提现手续费单笔1元',
                                maxLength: 100,
                                readOnly: true,
                                anchor: '100%'
                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '人均累计借款金额',
                                name: 'platDataPublish.avgSumIncomePeople',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '人均累计出借金额',
                                name: 'platDataPublish.avgSumPayPeople',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '最大单户出借余额占比',
                                name: 'platDataPublish.maxPayPeopleProporion',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '最大十户出借余额占比',
                                name: 'platDataPublish.tenPayPeopleProporion',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '人均借款额度',
                                name: 'platDataPublish.avgIncomePeople',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '自然人平均借款额度',
                                name: 'platDataPublish.avgIncomePeople',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '法人及其他组织平均借款额度',
                                name: 'platDataPublish.',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '人均借款期限',
                                name: 'platDataPublish.avgIncomeDay',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>天</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '人均借款成本',
                                name: 'platDataPublish.avgIncomeCost',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '人均投资期限',
                                name: 'platDataPublish.avgPayDay',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>天</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '人均投资回报',
                                name: 'platDataPublish.avgInterestMoney',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '平均满标用时',
                                name: 'platDataPublish.avgFullTime',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 30,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>小时</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '平均借款利率',
                                name: 'platDataPublish.avgRate',
                                maxLength: 100,

                                style: {imeMode: 'disabled'},


                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .5, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '机构规模分布情况',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',
                                value: '借款余额未超过一亿元'
                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '逾期金额',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '逾期笔数',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>笔</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '金额逾期率',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '金额分级逾期率(90天)',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '金额分级逾期率(91-180天)',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '金额分级逾期率(181天以上)',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '项目逾期率',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '项目分级逾期率(90天)',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '项目分级逾期率(91-180天)',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '项目分级逾期率(181天以上)',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '逾期90天(不含)以上金额',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '金额分级逾期率(181天以上)',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '逾期90天(不含)以上笔数',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>笔</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '逾期90天(不含)以上逾期率',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>%</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '累计代偿金额',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>元</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }, {
                            columnWidth: .4, // 该列有整行中所占百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: rightlabel,
                            labelAlign: 'right',
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                fieldLabel: '累计代偿笔数',
                                name: 'haveEarnedMoney',
                                maxLength: 100,
                                value:0,
                                style: {imeMode: 'disabled'},
                                readOnly: true,
                                anchor: '100%',

                            }]
                        }, {
                            columnWidth: .1, // 该列在整行中所占的百分比
                            layout: "form", // 从上往下的布局
                            labelWidth: 20,
                            //	style : 'padding-left:5px;padding-top:8px;',
                            items: [{
                                fieldLabel: "<span style='margin-left:1px'>笔</span> ",
                                labelSeparator: '',
                                anchor: "100%"

                            }]
                        }]
                    }]
                }]
            });

         this.formPanel.loadData({
                url: __ctxPath + '/p2p/showPlatDataPublish.do',
                id:'form',
                root: 'data',
                preName: 'platDataPublish',
                scope: this,
                failure: function () {
                    Ext.ux.Toast.msg('操作提示', '对不起，数据加载失败！');
                }
            });

        },
    reset: function () {
        this.searchPanel.getForm().reset();
    },

        search: function () {
            this.formPanel.loadData({
                url: __ctxPath + '/p2p/showPlatDataPublish.do?startDate='+Ext.get('startDate').getValue()+'&endDate='+Ext.get('endDate').getValue(),
                id:'form',
                root: 'data',
                preName: 'platDataPublish',
                scope: this,
                success:function () {
                    Ext.ux.Toast.msg('操作提示', '数据查询成功');
                },
                failure: function () {
                    Ext.ux.Toast.msg('操作提示', '对不起，数据加载失败！');
                }
            });
        },

    addressSex:function () {
          AddressAndSex();
    },
    addressSex:function () {
        AddressAndSex();
    }
    }
);

