SharedPhoneBookWin=Ext.extend(Ext.Window,{
	showPanel:null,
	buttons:null,
	constructor:function(_cfg){
	   Ext.applyIf(this,_cfg);
	   this.initUI();
	   SharedPhoneBookWin.superclass.constructor.call(this,{
		   id : 'SharedPhoneBookWin',
			title : '联系人信息',
			iconCls:"menu-phonebook-shared",
			width : 500,
			height :540,
			modal : true,
			autoScroll:true,
			layout : 'anchor',
			plain : true,
			border:false,
			bodyStyle : 'padding:5px;',
			buttonAlign : 'center',
			items : this.showPanel,
			buttons : this.buttons
	   });
     },
     initUI:function(){
    	 this.showPanel=new Ext.Panel({
    	     id:'SharedPhoneBookPanel',
    	     modal : true,
    	     autoScroll:true,
    	     autoLoad:{url:__ctxPath+'/communicate/detailPhoneBook.do?phoneId='+this.phoneId}	     
    	});
    	 this.buttons=[{
				text : '关闭',
				iconCls:'close',
				handler : this.cancel.createCallback(this)
			}];
     },
     cancel:function(self){
    	 self.close();
     }
});