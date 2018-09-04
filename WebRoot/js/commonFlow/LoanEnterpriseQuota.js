/**
 * @author tzw
 * @description 借款企业额度管理
 * @extends Ext.Panel
 */
LoanEnterpriseQuota = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
        LoanEnterpriseQuota.superclass.constructor.call(this, {
			id : 'LoanEnterpriseQuota'+this.singleType,
			title : '借款企业额度管理',
			region : 'center',
			layout : 'border',
			iconCls : 'btn-tree-team23',
			items : [
				this.searchPanel,
				this.gridPanel
			]
		});
	},
	initUIComponents : function() {
        this.searchPanel=new HT.SearchPanel({
            layout : 'column',
            autoScroll : true,
            region : 'north',

            border : false,
            height : 50,
            anchor : '80%',
            layoutConfig: {
                align:'middle'
            },
            bodyStyle : 'padding:10px 10px 10px 10px',
            items:[{
                columnWidth :.15,
                layout : 'form',
                labelWidth : 60,
                labelAlign : 'right',
                border : false,
                items : [{
                    fieldLabel:'企业名称',
                    name : 'enterprisename',
                    flex:1,
                    anchor : "100%",
                    xtype : 'textfield'
                }]
            },{
                columnWidth :.2,
                layout : 'form',
                labelWidth : 70,
                labelAlign : 'right',
                border : false,
                items : [{
                    fieldLabel:'证件号码',
                    name : 'organizecode',
                    flex:1,
                    anchor : "100%",
                    xtype : 'textfield'
                }]
            },{
                columnWidth :.07,
                layout : 'form',
                border : false,
                labelWidth :40,
                items :[{
                    text : '查询',
                    xtype : 'button',
                    scope : this,
                    style :'margin-left:30px',
                    anchor : "100%",
                    iconCls : 'btn-search',
                    handler : this.search
                }]
            },{
                columnWidth :.07,
                layout : 'form',
                border : false,
                labelWidth :40,
                items :[{
                    text : '重置',
                    style :'margin-left:30px',
                    xtype : 'button',
                    scope : this,
                    //width : 40,
                    anchor : "100%",
                    iconCls : 'btn-reset',
                    handler : this.reset
                }]
            }]

        });// end of searchPanel
		this.gridPanel=new HT.GridPanel({
			name : 'LoanEnterpriseQuotaGrid',
			region : 'center',
            url : __ctxPath + '/creditFlow/customer/enterprise/loanQuotaEnterprise.do',
            fields : [{
               			 name : 'id',
                		type : 'int'
           		 },
                 'enterprisename',
                 'organizecode',
                 'tenderingMoney',
                 'repayingMoney',
				 'totalMoney',
				 'linkman',
				 'linkManSurplusMoney',
                 'surplusMoney'
         	 ],
			columns : [{
					header : "企业名称",
					width : 110,
					sortable : true,
					dataIndex : 'enterprisename'
				}, {
					header : "证件号码",
					width : 110,
					align:'center',
					sortable : true,
               		 dataIndex : 'organizecode'
				}, {
					header : "总借款金额(万元)",
					width : 110,
					align:'center',
					sortable : true,
					dataIndex : 'totalMoney'
				},{
					header : "还款中金额(万元)",
					width : 110,
					align:'center',
					sortable : true,
					dataIndex : 'repayingMoney'
				},{
					header : "招标中金额(万元)",
					width : 110,
					align:'center',
					sortable : true,
					dataIndex : 'tenderingMoney'
				},{
                  header : "法人",
                  width : 110,
                  align:'center',
                  sortable : true,
                  dataIndex : 'linkman'
              },{
                  header : "法人可用额度",
                  width : 110,
                  align:'center',
                  sortable : true,
                  dataIndex : 'linkManSurplusMoney'
              }, {
					header : "企业可用额度",
					width : 110,
					align:'center',
					sortable : true,
					dataIndex : 'surplusMoney'
				}
			]
		});
	},
    reset : function(){
        this.searchPanel.getForm().reset();
    },
    //按条件搜索
    search : function() {
        $search({
            searchPanel:this.searchPanel,
            gridPanel:this.gridPanel
        });
    }
});

