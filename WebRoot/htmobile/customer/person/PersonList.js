/**
 * 手机待办
 * by cjj
 * 
 * var data = {
 *    "tasks" : [
 *    	{
            "taskId": "1",
            "taskName": "task1"
        },
        {
            "taskId": "2",
            "taskName": "task2"
        },
        {
            "taskId": "3",
            "taskName": "task3"
        }
 *    ] 
 * }
 * 
 */

Ext.define('htmobile.customer.person.PersonList', {
    extend: 'mobile.List',
    name: 'PersonList',
    constructor: function (config) {
    	var bottomBar= Ext.create('htmobile.public.bottomBarIndex',{
        });
    	Ext.apply(this,config);
    	config = config || {};
    	var search = Ext.create('Ext.Container', {
    		docked:'top',
    		items:[{
    			html:
    				'<div class="g-body" style="background-color:white">'+
				    '<header class="search-title dflex">'+
				       ' <input id="search" type="search" class="search-input flex" placeholder="请输入搜索内容" />'+
				        '<button id="findbtn" type="button" title="搜索" class="w-button-s w-button w-button-org w-button-search">搜索</button>'+
				    '</header>'+
					'</div>'}]
    		});
    	Ext.apply(config,{
	    		style:'background-color:white;',
	    		modeltype:"PersonList",
	    		title:this.title,
	    		items:[bottomBar,search],
	    		itemsTpl:[search].join(""),
	    		fields : [{
							name : 'id'
						}, {
							name : 'name'
						}, {
							name : 'sexvalue'
						}, {
							name : 'jobvalue'
						}, {
							name : 'cardtypevalue'
						}, {
							name : 'cardnumber',
							convert:function(v,r){
								if(v.length==18){
									return v.substr(0,6)+"****"+v.substr(14,18);
								}else{
									return v;
								}
							}
						}, {
							name : 'cellphone'
						}, {
							name : 'birthday'
						}, {
							name : 'nationalityvalue'
						}, {
							name : 'peopletypevalue'
						}, {
							name : 'dgreevalue'
						}, {
							name : 'techpersonnel'
						}, {
							name : 'age'
						}, {
							name : 'telphone'
						}, {
							name : 'englishname'
						}, {
							name : 'orgName'
						}, {
							name : 'orgUserId'
						}, {
							name : 'orgUserName'
						}, {
							name : 'customerLevel'
						}, {
							name : 'belongedName'
						}, {
							name : 'nationality'
						}, {
							name : 'marry'
						}, {
							name : 'marryvalue'
						}, {
							name : 'unitphone'
						}, {
							name : 'postcode'
						}, {
							name : 'hukou'
						}, {
							name : 'postaddress'
						}, {
							name : 'englishname'
						}, {
							name : 'selfemail'
						}, {
							name : 'isheadoffamily'
						}, {
							name : 'familyaddress'
						}, {
							name : 'familypostcode'
						}, {
							name : 'communityname'
						}, {
							name : 'personCount'
						}, {
							name : 'housearea'
						}, {
							name : 'homeincome'
						}, {
							name : 'disposableCapital'
						}, {
							name : 'recordAndRemarks'
						}, {
							name : 'grossasset'
						}, {
							name : 'homeasset'
						}, {
							name : 'grossdebt'
						}, {
							name : 'yeargrossexpend'
						}, {
							name : 'wagebank'
						}, {
							name : 'wageperson'
						}, {
							name : 'wageaccount'
						}, {
							name : 'matebank'
						}, {
							name : 'mateperson'
						}, {
							name : 'mateaccount'
						}, {
							name : 'personPhotoUrl'
						}, {
							name : 'personSFZZUrl'
						}, {
							name : 'personSFZFUrl'
						},{
							name:'postaddress',
							convert:function(v,r){
								return v.length>=10?v.substr(0,10):v;
							}
						}],
	    		url : __ctxPath + '/creditFlow/customer/person/perQueryListPerson.do?isAll='
								+ isGranted('_detail_sygrkh'),
	    		root:'topics',
			    itemTpl:'<div class="person-line">'+
				    		'<div class="person-line-first">' +
				    			'<span class="p-name">{name}</span>' +
				    			'<span class="p-sex">{sexvalue}</span>' +
				    			'<span class="p-marry">{marryvalue}</span>' +
				    			'<span class="p-add">{postaddress}</span>' +
				    		'</div>' +
			    			'<div class="person-line-second">' +
			    				'<span class="p-call">{cellphone}</span>' +
				    			'<span class="p-card">{cardnumber}</span>' +
				    		'</div>' +
			    		'</div>',
			    grouped: true,
			    groupedFiled:'name',
			    indexBar:true,
			    totalProperty: 'totalCounts',
			    pullRefresh: true,
			    listPaging: true,
			    listeners: {
			    		scope:this,
	    			 	itemsingletap:this.itemsingletap
	    		}
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
    },itemsingletap:function(index, target, record, e, eOpts ){
    	    mobileNavi.push(
			            Ext.create('htmobile.customer.person.PersonDetail',{
			            	title:'个人客户详情',
				        	result:e.raw
			        	})
		    	);
    }
   

});
