MailDetail=Ext.extend(Ext.Panel,{
	constructor:function(_cfg){
	    Ext.applyIf(this,_cfg);
	    this.initUI();
	    MailDetail.superclass.constructor.call(this,{
	    	id : 'MailDetail',
	    	title:'我的邮件',
	    	iconCls:'menu-mail',
			border : false,
			tbar : this.toolbar,
			autoLoad : {
				url : __ctxPath + '/communicate/getMail.do?',
				params : {
					mailId : this.mailId,
					boxId : this.boxId
				},
				method : 'Post'
			}
	    });
    },
    initUI:function(){
    	var mailId=this.mailId;
    	var boxId=this.boxId;
    	this.toolbar = new Ext.Toolbar({
    		height : 30,
    		defaultType : 'button',
    		bodyStyle : 'text-align:left',
    		items : [{
    					iconCls : 'btn-mail_reply',
    					text : '回复',
    					handler : function() {
    						var tab = Ext.getCmp('centerTabPanel');
    						var mailForm = Ext.getCmp('MailForm');
    						if (mailForm == null) {
    							mailForm = new MailForm(mailId, boxId, 'reply');
    							tab.add(mailForm);
    							tab.activate(mailForm);
    						} else {
    							tab.remove(mailForm);
    							mailForm = new MailForm(mailId, boxId, 'reply');
    							tab.add(mailForm);
    							tab.activate(mailForm);
    						}
    					}
    				}, {
    					iconCls : 'btn-mail_resend',
    					text : '转发',
    					handler : function() {
    						var tab = Ext.getCmp('centerTabPanel');
    						var mailForm = Ext.getCmp('MailForm');
    						if (mailForm == null) {
    							mailForm = new MailForm(mailId, boxId, 'forward');
    							tab.add(mailForm);
    							tab.activate(mailForm);
    						} else {
    							tab.remove(mailForm);
    							mailForm = new MailForm(mailId, boxId, 'forward');
    							tab.add(mailForm);
    							tab.activate(mailForm);
    						}
    					}
    				}]
    	});
    	
    }
});