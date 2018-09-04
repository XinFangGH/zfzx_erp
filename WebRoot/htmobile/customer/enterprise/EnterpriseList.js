Ext.define('htmobile.customer.enterprise.EnterpriseList', {
    extend: 'mobile.List',
    name: 'EnterpriseList',
    constructor: function (config) {
    	var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
    	Ext.apply(this,config);
		var search = Ext.create('Ext.Container', {
    		docked:'top',
    		items:[{
    			html:
    				'<div class="g-body" style="background-color:white;">'+
				    '<header class="search-title dflex">'+
				       ' <input id="search" type="search" class="search-input flex" placeholder="请输入搜索内容" />'+
				        '<button id="findbtn" type="button" title="搜索" class="w-button-s w-button w-button-org w-button-search">搜索</button>'+
				    '</header>'+
					'</div>'}]
    	});
    	config = config || {};
    	
    	Ext.apply(config,{
    		style:'background-color:white;',
    		title:this.title,
    		modeltype:"EnterpriseList",
    		items:[bottomBar,search],
    		itemsTpl:[search].join(""),
    		title:config.title,
    		fields : [{
						name : 'id'
					}, {
						name : 'enterprisename'
					}, {
						name : 'shortname'
					}, {
						name : 'ownership'
					}, {
						name : 'registermoney'
					}, {
						name : 'organizecode'
					}, {
						name : 'tradetypev'
					}, {
						name : 'ownershipv'
					}, {
						name : 'telephone'
					}, {
						name : 'legalperson'
					},{
						name:'linkman'					
					}, {
						name : 'postcoding'
					}, {
						name : 'cciaa'
					}, {
						name : 'managecityName'
					}, {
						name : 'area'
					}, {
						name : 'opendate'
					}, {
						name : 'hangyetypevalue'
					}, {
						name : 'hangyetypevalue'
					}, {
						name : 'orgName'
					}, {
						name : 'orgUserId'
					}, {
						name : 'orgUserName'
					}, {
						name : 'taxnum'
					},{name:'area',convert:function(v,r){
						return v===""?"暂无数据":v;
					}},'legalpersonName'],
    	    url : __ctxPath + '/creditFlow/customer/enterprise/entListEnterprise.do?isGrantedSeeAllEnterprises='+isGranted('_seeAll_qykh'),
    		root:'topics',
    		itemTpl:'<div class="enterprise-line">' +
    					'<div class="enterprise-line-first">{enterprisename}</div>' +
    					'<div class="enterprise-line-second">' +
    						'<span class="ent-name">{legalpersonName}</span>' +
    						'<span class="ent-call">{telephone}</span>' +
    						'<span class="ent-ass">{registermoney}万  注册资本金</span>' +
    					'</div>' +
    					'<div class="enterprise-line-third">' +
    						'<span>{area}</span>' +
    					'</div>' +
    				'</div>',
		    grouped: true,
		    groupedFiled:'enterprisename',
		    indexBar:true,
		    totalProperty: 'totalProperty',
		    pullRefresh: true,
		    listPaging: false,
		     listeners: {
		    		scope:this,
    			 	itemsingletap:this.itemsingletap
    		}
    	});
    	this.callParent([config]);
    	//条件查询
    	_list =this;
    	$("#findbtn").on('click', function() {
					var enterprisename = $("#search")[0].value;
					_list.getStore().setParams({
								enterprisename : enterprisename
							});
					_list.getStore().loadPage(1);
				});
    },
	itemsingletap:function(index, target, record, e, eOpts ){
		   mobileNavi.push(
			            Ext.create('htmobile.customer.enterprise.EnterpriseDetail',{
				            title:'企业客户详情',
					        data:e.raw
			        	})
		    	);
    }
});
