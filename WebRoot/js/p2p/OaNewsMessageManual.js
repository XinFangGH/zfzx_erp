OaNewsMessageManual = Ext.extend(Ext.Window, {
    // 构造函数
    constructor : function(_cfg) {
        Ext.applyIf(this, _cfg);
        // 必须先初始化组件
        this.initUIComponents();
        OaNewsMessageManual.superclass.constructor.call(this, {
            id : 'OaNewsMessageManual',
            layout : 'fit',
            items : this.formPanel,
            modal : true,
            height : 400,
            width : 500,
            maximizable : true,
            title : '发送短信',
            buttonAlign : 'center',
            buttons : [{
                text : '发送',
                iconCls : 'btn-save',
                scope : this,
                handler : this.send
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
            // id : 'transferAccount',
            defaults : {
                anchor : '96%,96%'
            },
            defaultType : 'textfield',
            items : [ {
                fieldLabel : '用户手机号',
                name : 'telphone',
                allowBlank : false,
                maxLength : 50,
                regex: /^[1][3,4,5,6,7,8,9][0-9]{9}$/,
                regexText:"手机号码输入错误,请检查",

            }],
            html:"<br><br><h1>发送内容：</h1><br><br><h5>【展信资本】尊敬的<input name='name'  style='text-align: center; color: red; width: 100px;border: 0px;border-bottom: 1px  solid #ccc'>(先生/女士)您好!您于<input name='year' style='text-align: center;color: red; width: 50px;border: 0px;border-bottom: 1px  solid #ccc'>年<input name='mouth'style='text-align: center;color: red; width: 50px;border: 0px;border-bottom: 1px  solid #ccc'>月<br><br><input name='day' style='text-align: center;color: red; width: 50px;border: 0px;border-bottom: 1px  solid #ccc'>日在我公司选择的<input name='product' style='text-align: center;color: red; width: 100px;border: 0px;border-bottom: 1px  solid #ccc'>产品出借成功,出借金额为<input name='money' style='text-align: center;color: red; width: 80px;border: 0px;border-bottom: 1px  solid #ccc'><br><br>元,合同编号:<input name='id' style='text-align: center;color: red; width: 200px;border: 0px;border-bottom: 1px  solid #ccc'> .感谢您对展信资本的支持与信任!</h5>"
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
    send : function() {
        $postForm({
            formPanel : this.formPanel,
            scope : this,
            url : __ctxPath + '/p2p/sendMessageManualOaNewsMessage.do',
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
        });
    }// end of save

});