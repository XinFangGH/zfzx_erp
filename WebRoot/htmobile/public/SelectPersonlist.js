
Ext.define('htmobile.public.SelectPersonlist', {
    extend: 'mobile.List',
    
    name: 'SelectPersonlist',

    constructor: function (config) {
    	var search = Ext.create('Ext.Container', {
    		docked:'top',
    			
    		items:[{
    			html:
    				'<div class="g-body" style="background-color:#f0f1f5;">'+
				    '<header class="search-title dflex">'+
				       ' <input id="search" type="search" class="search-input flex" placeholder="请输入搜索内容" />'+
				        '<button id="findbtn" type="button" title="搜索" class="w-button-s w-button w-button-org w-button-search">搜索</button>'+
				    '</header>'+
					'</div>'}]
    	});
		
    	config = config || {};
    	this.callback=config.callback;
    	Ext.apply(config,{
    		modeltype:"SelectPersonlist",
    		title:config.title,
    		items:[search],
    		itemsTpl:[search].join(""),
    		fields:[{
								name : 'id'
							}, {
								name : 'name'
							}, {
								name : 'sexvalue'
							}, {
								name : 'cellphone'
							}, {
								name : 'cardtypevalue'
							}, {
								name : 'cardnumber'
							}, {
								name : 'cardtype'
							}],
    		url : __ctxPath + '/creditFlow/customer/person/perQueryListPerson.do?isAll='
							+ isGranted('_detail_sygrkh'),
    		root:'topics',
		     itemTpl: "<span style='font-size:14px;color:#a7573b;float:left;margin-left:12px;width:50px'  >{name}</span>&nbsp;&nbsp;&nbsp;&nbsp;" +
		    		"<span style='font-size:14px;color:#a7573b;float:left;margin-left:12px;width:50px' >{cardnumber}</span>&nbsp;&nbsp;&nbsp;&nbsp;",
		    totalProperty: 'totalCounts',
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
		    listPaging: true
    	});

    	this.callParent([config]);
    	
    	//条件查询
    	_list =this;
    	$("#findbtn").on('click', function() {
					var name = $("#search")[0].value;
					_list.getStore().setParams({
								name : name
							});
					_list.getStore().loadPage(1);
				});
    }

});
