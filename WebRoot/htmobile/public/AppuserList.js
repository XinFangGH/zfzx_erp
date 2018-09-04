
Ext.define('htmobile.public.AppuserList', {
    extend: 'mobile.List',
    
    name: 'AppuserList',

    constructor: function (config) {
		
    	config = config || {};
    	this.callback=config.callback;
    	Ext.apply(config,{
    		modeltype:"AppuserList",
    		title:config.title,
    		fields:[{
								name : 'userId'
							}, {
								name : 'username'
							}, {
								name : 'fullname'
							}],
    		url : __ctxPath + '/system/alllitAppUser.do',
    		root:'result',
		    itemTpl: "<span >{fullname}</span>" ,
		    totalProperty: 'totalCounts',
		    searchCol:'Q_fullname_S_LK',
		    searchTip:'请输入姓名',
		    params:{
		       Q_fullname_S_LK:""
		    },
		    pullRefresh: true,
		    listeners: {
		    	scope:this,
    			 itemsingletap:function( obj, index, target, record, e, eOpts ){
    			 	mobileNavi.pop();
    			 		this.callback(record.data);
    			 	
    			 }
    		},
		    listPaging: true/*,
		    onItemDisclosure:function(record,element,index,e){
		        this.itemsingletap(record);
		    }*/
		    /*listeners: {
    			 itemsingletap:this.itemsingletap
    		}*/
    	});

    	this.callParent([config]);

    }

});
