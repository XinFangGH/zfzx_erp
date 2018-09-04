/**
 * 注册用户地区分布与性别比例
 * @constructor
 */
function AddressAndSex () {
    var anchor = '100%';
    var pageSize = 15;
    var listWindowWidth = 550;
    var listWindowHeight = 465;
    var detailWindowWidth = 480;
    var detailWindowHeight = 370;
    var defaultLabelWidth = 100;// 默认标签的宽度

    var myMask_EnterpriseSelect = new Ext.LoadMask(Ext.getBody(), {
        msg : "正在加载数据中······,请稍候······"
    });

    gPanel_AddressSex = new HT.GridPanel({
        name : 'gPanel_enterpriseSelect',
        region : 'center',
        notmask : true,
        //bbar : pagingBar_Enterprise,
        // 不适用RowActions
        rowActions : false,
        url : __ctxPath + "/p2p/AddressAndSexBpCustMember.do?startDate="+Ext.getCmp("startDate").value+"&endDate="+Ext.getCmp("endDate").value,
        fields : ['relationAddress','sex','totalMoney'],

        columns : [ {
            header : '类别',
            width : 80,
            dataIndex : 'relationAddress'

        }, {
            header : '总数',
            width : 100,
            dataIndex : 'sex'
        }, {
            header : '比例',
            width : 110,
            dataIndex : 'totalMoney'
        }]
    });
    searchPanel = new Ext.form.FormPanel({
        layout : 'hbox',
        region : 'north',
        border : false,
        height : 50,
        anchor : '100%',
        layoutConfig : {
            align : 'middle'
        },
        items : [{
            layout : 'form',
            border : false,
            items : [{
                text : '导出到Excel表',
                xtype : 'button',
                scope : this,
                style : 'margin-left:20px',
                anchor : "100%",
                iconCls : 'btn-search',
                handler: function () {
                    window.open(__ctxPath + "/p2p/AddressAndSexBpCustMember.do?startDate="+Ext.getCmp("startDate").value+"&endDate="+Ext.getCmp("endDate").value+'&type=111');
                }
            }]
        }]
    });


    var window_AddressAndSex = new Ext.Window({
        title : '地区与年龄分布',
        border : false,
        width : (screen.width - 180) * 0.8,
        height : listWindowHeight - 30,
        collapsible : true,
        modal : true,
        constrainHeader : true,
        items : [searchPanel,gPanel_AddressSex],
        layout : 'border',
        buttonAlign : 'center'
    });




    window_AddressAndSex.show();


}