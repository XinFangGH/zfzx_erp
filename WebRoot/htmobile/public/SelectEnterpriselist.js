
Ext.define('htmobile.public.SelectEnterpriselist', {
    extend: 'mobile.List',
    
    name: 'SelectEnterpriselist',

    constructor: function (config) {
		
    	config = config || {};
    	this.callback=config.callback;
    	Ext.apply(config,{
    		modeltype:"SelectEnterpriselist",
    		title:config.title,
    		fields:[{
				name : 'id'
			}, {
				name : 'enterprisename'
			}, {
				name : 'shortname'
			}, {
				name : 'legalpersonid'
			}, {
				name : 'registermoney'
			}, {
				name : 'organizecode'
			}, {
				name : 'cciaa'
			}, {
				name : 'legalperson'
			}, {
				name : 'telephone'
			}, {
				name : 'area'
			}, {
				name : 'cellphone'
			}],
    	  url : __ctxPath + '/creditFlow/customer/enterprise/entListEnterprise.do?isGrantedSeeAllEnterprises='+isGranted('_seeAll_qykh'),
    		root:'topics',
		    itemTpl: "<span style='font-size:14px;color:#a7573b;float:left;margin-left:12px;width:100%'  >{enterprisename}</span>&nbsp;&nbsp;&nbsp;&nbsp;" ,
		    totalProperty: 'totalProperty',
		    searchCol:'enterprisename',
		    searchTip:'请输入公司名称',
		    params:{
		       nameorcardnumber:""
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
