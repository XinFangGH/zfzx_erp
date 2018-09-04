/**
 * @author
 * @createtime
 * @class OperateContractWindow
 * @extends Ext.Window
 * @description OperateContractWindow
 * @company
 */
BpOperateKxcsContractWindow = Ext.extend(Ext.Window, {
    isHidden: false,
    isWDLoan: false,
    // 构造函数
    constructor: function (_cfg) {
        if (typeof(_cfg.isHidden) != "undefined") {
            this.isHidden = _cfg.isHidden;
        }
        if (typeof(_cfg.isWDLoan) != "undefined") {
            this.isWDLoan = _cfg.isWDLoan;
        }
        Ext.applyIf(this, _cfg);
        // 必须先初始化组件
        this.initUIComponents();
        BpOperateKxcsContractWindow.superclass.constructor.call(this, {
            id: 'BpOperateKxcsContractWindow',
            layout: 'fit',
            width: 400,
            height: 150,
            modal: true,
            autoScroll: true,
            title: '选择合同模板',
            buttonAlign: 'center',
            items: [this.panelContract],
            buttonAlign: 'center',
            buttons: [{
                iconCls: 'btn-save',
                text: this.isWDLoan ? '保存' : '合同生成',
                scope: this,
                handler: this.createContract
            }, {
                iconCls: 'btn-save',
                text: '合同下载',
                scope: this,
                hidden: this.isHidden,
                handler: this.downloadContract
            }]
        });
    },// end of the constructor
    // 初始化组件
    initUIComponents: function () {

        this.panelContract = new Ext.form.FormPanel({
            layout: 'column',
            items: [{
                columnWidth: 1,
                layout: 'form',
                border: false,
                labelWidth: 60,
                labelAlign: 'right',
                bodyStyle: 'margin-top:10px',
                items: [{
                    xtype: "combo",
                    anchor: "95%",
                    hiddenName: "documenttempletId",
                    displayField: 'itemName',
                    editable: false,
                    allowBlank: false,
                    valueField: 'itemId',
                    triggerAction: 'all',
                    store: new Ext.data.SimpleStore({
                        autoLoad: true,
                        url: __ctxPath + '/contract/listByMarkDocumentTemplet.do?mark=' + this.htType,
                        fields: ['itemId', 'itemName']
                    }),
                    fieldLabel: "合同名称",
                    blankText: "合同名称不能为空，请正确填写!"
                }]
            }]
        });
    },
    //生成合同

    createContract: function () {
        var documenttempletId = this.getCmpByName('documenttempletId').getValue();

        var url = __ctxPath + '/credit/customer/enterprise/creatContractEnterprise.do' +
            '?proId='+this.projectId+'&bdirProId='+this.bdirProId+'&documenttempletId='+documenttempletId;

        window.open(url);

    }
})

