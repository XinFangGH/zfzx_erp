
Ext.define('htmobile.public.AreaSelectList', {
    extend: 'mobile.List',
    
    name: 'AppuserList',

    constructor: function (config) {
		
    	config = config || {};
    	this.callback=config.callback;
    	this.node=config.node;
    	this.count=config.count;
    	this.textall=config.textall;
    	Ext.apply(config,{
    		modeltype:"AreaSelectList",
    		title:config.title,
    		fields:[{
								name : 'id'
							}, {
								name : 'leaf'
							}, {
								name : 'remarks'
							}, {
								name : 'text'
							}],
    		url : __ctxPath + '/creditFlow/multiLevelDic/getDictionaryTreeWindowAreaDic.do',
    		root:'result',
		    itemTpl: "<span >{remarks}</span>" ,
		    totalProperty: 'totalCounts',
		 //   searchCol:'nameorcardnumber',
		//    searchTip:'请输入姓名',
		    params:{
		       lable:"area",
		       node:this.node,
		       isMobile:"1"
		    },
		 //   pullRefresh: true,
		    listeners: {
		    	scope:this,
    			 itemsingletap:function( obj, index, target, record, e, eOpts ){
    			 	if(record.data.leaf==false){
    			 		mobileNavi.push(mobileNavi.push(Ext.create('htmobile.public.AreaSelectList',{
    			 			            count:(this.count+1),
    			 			            textall:(this.textall+"_"+record.data.remarks),
									   	node:record.data.id,
									    callback:this.callback})));
    			 	}else{
    			 		mobileNavi.pop(this.count);
    			 	    this.textall=this.textall+"_"+record.data.remarks;
    			 	    this.textall=this.textall.substring(1,this.textall.length);
    			 	    record.data.remarks=this.textall;
    			 	    this.callback(record.data);
    			 	  
    			 	}
//    			 /	mobileNavi.pop();
    			 		
    			 	
    			 }
    		}/*,
		    listPaging: true*/
    	});

    	this.callParent([config]);

    }

});
