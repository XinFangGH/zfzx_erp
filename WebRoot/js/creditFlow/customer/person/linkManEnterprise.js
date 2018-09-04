function linkManEnterprise(personName) {

    var jStore_linkManEnterprise = new Ext.data.JsonStore({
        url : __ctxPath
        + '/creditFlow/customer/person/queryEnterpriseMoneyPerson.do?name='+personName,
        totalProperty : 'totalProperty',
        root : 'topics',
        fields : [{
            name : 'id'
        }, {
            name : 'enterprisename'
        }, {
            name : 'organizecode'
        }, {
            name : 'linkman'
        }, {
            name : 'tenderingMoney'
        }, {
            name : 'repayingMoney'
        }, {
            name : 'surplusMoney'
        }, {
            name : 'totalMoney'
        }],
        // 服务器端排序 by chencc
        // sortInfo : {field: "enterprisename", direction: "DESC"}
        remoteSort : true
    });


    // function selectEnterprise(funName) {
    var anchor = '100%';
    var pageSize = 15;
    var listWindowWidth = 650;
    var listWindowHeight = 465;
    var detailWindowWidth = 580;
    var detailWindowHeight = 370;
    var defaultLabelWidth = 110;// 默认标签的宽度
    var defaultTextfieldWidth = 135;// 默认文本输入域宽度
    var enterpriseJsonObj;
    jStore_linkManEnterprise.addListener('load', function() {
        gPanel_enterpriseSelect.getSelectionModel().selectFirstRow();
    }, this);
    jStore_linkManEnterprise.addListener('loadexception', function(proxy,
                                                                     options, response, err) {
        Ext.ux.Toast.msg('提示', '数据加载失败，请保证网络畅通！');
    }, this);
    //var sm = new Ext.grid.CheckboxSelectionModel();
    var cModel_enterprise = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer({
            header : '序号',
            width : 35
        }), {
            id : 'enterpriseName',
            header : "企业名称",
            width : 150,
            sortable : true,
            dataIndex : 'enterprisename'
        }, {
            header : "企业证件号码",
            width : 150,
            sortable : true,
            dataIndex : 'organizecode'
        }, {
            header : "法人",
            width : 105,
            sortable : true,
            dataIndex : 'linkman',

        }, {
            header : "招标中金额",
            width : 90,
            sortable : true,
            dataIndex : 'tenderingMoney'
        }, {
            header : "还款中金额",
            width : 90,
            sortable : true,
            dataIndex : 'repayingMoney'
        }, {
            header : "可用金额",
            width : 90,
            sortable : true,
            dataIndex : 'surplusMoney'
        }, {
            header : "总借款金额",
            width : 90,
            sortable : true,
            dataIndex : 'totalMoney'
        }]);
    var pagingBar_Enterprise = new Ext.PagingToolbar({
        pageSize : pageSize,
        store : jStore_linkManEnterprise,
        autoWidth : true,
        hideBorders : true,
        displayInfo : true,
        displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
        emptyMsg : "没有符合条件的记录······"
    });
    var myMask_EnterpriseSelect = new Ext.LoadMask(Ext.getBody(), {
        msg : "正在加载数据中······,请稍候······"
    });

    var gPanel_enterpriseSelect = new Ext.grid.GridPanel({
        id : 'gPanel_enterpriseSelect',
        store : jStore_linkManEnterprise,
        colModel : cModel_enterprise,
        autoExpandColumn : 'enterpriseName',
        //selModel : new Ext.grid.RowSelectionModel(),
        stripeRows : true,
        loadMask : true,
        bbar : pagingBar_Enterprise,
        tbar : [{
            text : '查看',
            iconCls : 'btn-detail',
            handler : function(btn, e) {
                var selected = gPanel_enterpriseSelect.getSelectionModel().getSelected();
                var len = selected.length;
                if (len > 1) {
                    Ext.ux.Toast.msg('状态', '只能选择一条记录');
                } else if (0 == len) {
                    Ext.ux.Toast.msg('状态', '请选择一条记录');
                } else {
                    enterpriseId = selected.get('id');
                    Ext.Ajax.request({
                        url : __ctxPath+ '/creditFlow/customer/enterprise/loadInfoEnterprise.do',
                        method : 'POST',
                        success : function(response, request) {
                            obj = Ext.util.JSON.decode(response.responseText);
                            var enterpriseData = obj.data.enterprise;
                            var personData = obj.data.person;
                            if (obj.success == false || obj.success == 'false') {
                                Ext.ux.Toast.msg('错误提示', '查询出错');
                                return;
                            }
                            var window_see = new Ext.Window({
                                id : 'win_seeEnterprise',
                                title : '查看<' + enterpriseData.shortname + '>信息',
                                layout : 'fit',
                                iconCls : 'lookIcon',
                                width : (screen.width - 180) * 0.7+ 160,
                                constrainHeader : true,
                                collapsible : true,
                                autoScroll : true,
                                height : 460,
                                closable : true,
                                resizable : false,
                                modal : true,
                                plain : true,
                                border : false,
                                items : [new enterpriseObj({enterpriseId:enterpriseData.id,enterprise:enterpriseData,person:personData,isReadOnly:true})]
                            }).show();
                        },
                        failure : function(response, options) {
                            var responseMsg = Ext.util.JSON.decode(response.responseText);
                            if (response.status == -1) {
                                Ext.ux.Toast.msg('错误原因', '请求超时！');
                                return;
                            }
                            Ext.ux.Toast.msg('错误原因', responseMsg.data? responseMsg.data	: '服务器处理出错');
                        },
                        params : {
                            id : enterpriseId
                        }
                    });
                }
            }
        }, '-', new Ext.form.Label({
            text : '企业名称：'
        }), new Ext.form.TextField({
            id : 'enterprisenameRefer',
            width : 110
        }), {
            text : '查找',
            iconCls : 'btn-search'
        }],
        listeners : {
            'rowdblclick' : function(grid, rowIndex, e) {
                var selected = grid.getStore().getAt(rowIndex);
                callbackFun(selected, funName);
                window_EnterpriseForSelect.destroy();
                myMask_EnterpriseSelect.hide();
            }
        }
    });
    Ext.getCmp('enterprisenameRefer').on('blur', function() {
        var value = Ext.get('enterprisenameRefer').dom.value;
        jStore_linkManEnterprise.baseParams.enterprisename = value;
        jStore_linkManEnterprise.load({
            params : {
                start : 0,
                limit : 15
            }
        });
    });
    var window_EnterpriseForSelect = new Ext.Window({
        title : '法人旗下企业列表',
        border : false,
        width : (screen.width - 180) * 0.7,
        height : listWindowHeight - 30,
        collapsible : true,
        modal : true,
        constrainHeader : true,
        items : [gPanel_enterpriseSelect],
        layout : 'fit',
        buttonAlign : 'center'
    });
    window_EnterpriseForSelect.show();
    jStore_linkManEnterprise.load({
        params : {
            start : 0,
            limit : pageSize
        }
    });


    var callbackFun = function(selected, funName) {
        enterpriseJsonObj = {
            id : selected.get('id'),
            surplusMoney : selected.get('surplusMoney'),
            shortname : selected.get('shortname'),
            hangyetype : selected.get('hangyetype'),
            ownership : selected.get('ownership'),
            registermoney : selected.get('registermoney'),
            enterprisename : selected.get('enterprisename'),
            controlpersonid : selected.get('controlpersonid'),
            legalpersonid : selected.get('legalpersonid'),
            linkmampersonid : selected.get('linkmampersonid'),
            area : selected.get('area'),
            legalperson : selected.get('legalperson'),
            linkperson : selected.get('linkperson'),
            controlperson : selected.get('controlperson'),
            linkpersonjob : selected.get('linkpersonjob'),
            linkpersonmobile : selected.get('linkpersonmobile'),
            linkpersontel : selected.get('linkpersontel'),
            hangyetypevalue : selected.get('hangyeName'),
            organizecode : selected.get('organizecode'),
            cciaa : selected.get('cciaa'),
            telephone : selected.get('telephone'),
            postcoding : selected.get('postcoding'),
            hangyetype : selected.get('hangyeType'),
            address : selected.get('address'),
            registerstartdate : selected.get('registerstartdate'),
            documentType:selected.get('documentType'),
            taxnum:selected.get('taxnum'),
            isCheckCard : selected.get('isCheckCard')
        }
        funName(enterpriseJsonObj);
    }


}