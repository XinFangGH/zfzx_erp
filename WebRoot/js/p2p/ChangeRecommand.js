var ids = '';
ChangeRecommand = Ext.extend(Ext.Window, {

    // 构造函数
    constructor : function(_cfg) {
        Ext.applyIf(this, _cfg);
        ids = _cfg.ids;

        // 必须先初始化组件
        this.initUIComponents();
        ChangeRecommand.superclass.constructor.call(this, {
            id : 'ChangeRecommand',
            layout : 'fit',
            items : this.formPanel,
            modal : true,
            height : 100,
            width : 300,
            maximizable : true,
            title : '更换邀请码',
            buttonAlign : 'center',
            buttons : [{
                text : '确定',
                iconCls : 'btn-save',
                scope : this,
                handler : this.save
            },{
                text : '取消',
                iconCls : 'btn-cancel',
                scope : this,
                handler : this.cancel
            }]
        });
    },// end of the constructor
    // 初始化组件
    initUIComponents : function() {
        this.formPanel = new Ext.FormPanel({
            layout : 'form',
            bodyStyle : 'padding:10px',
            border : false,
            autoScroll : true,
            width:50,
            // id : 'transferAccount',
            defaults : {
                anchor : '96%,96%'
            },
            defaultType : 'textfield',
            items : [ {
                fieldLabel : '邀请码',
                name : 'recommandPerson',
                allowBlank : false,
            }]
        });
        // 加载表单对应的数据
    },// end of the initcomponents

    /**
     *
     * 重置
     *
     * @param {}
     *            formPanel
     */
    reset : function() {
        this.formPanel.getForm().reset();
    },
    /**
     * 取消
     *
     * @param {}
     *            window
     */
    cancel : function() {
        this.close();
    },
    /**
     * 发送按钮
     */
    save : function() {
        $postForm({
            formPanel : this.formPanel,
            scope : this,
            url : __ctxPath + '/p2p/findByPlainpasswordBpCustMember.do?',
            callback : function(fp, action) {
                var obj=Ext.util.JSON.decode(action.response.responseText);
                obj.id='xxx';
                if (obj.success) {
                    $postForm({
                        formPanel : this.formPanel,
                        scope : this,
                        url : __ctxPath + '/p2p/changeRecommandBpCustMember.do?id='+ids,
                        callback : function(fp, action) {
                            var obj=Ext.util.JSON.decode(action.response.responseText);
                            obj.id='xxx';
                            if (obj.success) {
                                Ext.ux.Toast.msg('操作信息', obj.msg);
                                Ext.MessageBox.hide();//解除锁屏
                                this.close();
                            }else {
                                Ext.ux.Toast.msg('操作信息', obj.msg);
                                Ext.MessageBox.hide();//解除锁屏
                            }
                        }
                    })
                }else {
                    Ext.ux.Toast.msg('操作信息', obj.msg);
                    Ext.MessageBox.hide();//解除锁屏
                }
            }
        })



      ;
    }// end of save

});