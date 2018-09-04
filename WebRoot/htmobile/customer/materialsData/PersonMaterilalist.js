
Ext.define('htmobile.customer.materialsData.PersonMaterilalist', {
    extend: 'mobile.List',
    name: 'PersonMaterilalist',
    constructor: function (config) {
		var search = Ext.create('Ext.Container', {
    		docked:'top',
    		items:[{
    			html:
    				'<div class="g-body" style="background-color:#f0f1f5;">'+
				    '<header class="search-title dflex">'+
				       ' <input id="ziliao" name="ziliao" type="search" class="search-input flex" placeholder="请输入搜索内容" />'+
				        '<button id="find2" onclick="find2();" type="button" title="搜索" class="w-button-s w-button w-button-org w-button-search">搜索</button>'+
				    '</header>'+
					'</div>'}]
    	});
    	find2=function(){
    		param = document.getElementById("ziliao").value; 
    		mobileNavi.pop(1);
    		mobileNavi.push(Ext.create('htmobile.customer.materialsData.PersonMaterilalist', {}));
    	};
    	config = config || {};
    	Ext.apply(config,{
    		allowDeselect:false,
    		refreshHeightOnUpdate:false,
    		title:"资料库",
    		items:[search],
    		itemsTpl:[search].join(""),
    		modeltype:'PersonMaterilalist',
    		fields:[{
				name : 'runId',
				type : 'int'
			}, 'projectId','appUserName','createDate','orgName', 'subject', 'creator', 'userId', 'projectName',
					'projectNumber', 'defId', 'runStatus', 'projectMoney','telphone','cardNumber', 'projectStatus', 'isOtherFlow',
					'payProjectMoney', 'OppositeType', 'oppositeTypeValue',
					'customerName', 'projectStatus', 'operationType',
					'operationTypeValue', 'taskId', 'activityName',
					'oppositeID', 'businessType', 'startDate', 'endDate'],
    		url : __ctxPath + '/project/getprojectByCustomerName1SlSmallloanProject.do?marker=3&projectStatus=2&isGrantedShowAllProjects='+isGranted('isGrantedShowAllProjects'),
		    itemTpl:  new Ext.XTemplate("<span style='font-size:14px;color:#412f1f;'>" +
					"客户名字：{customerName}<br/>" +
					"手机号码：{telphone}<br/>" +
					"身份证号码：{cardNumber}<br/>" +
					"客户经理：{appUserName}<br/>" +
					"该客户最新一笔借贷项目：<br/>" +
					"{projectName}<br/>" +
					"项目状态：" +
					" <tpl if='isOtherFlow==1'> 展期办理</tpl>" +
					" <tpl if='isOtherFlow==2'> 提前还款</tpl>" +
					" <tpl if='isOtherFlow==3'> 利率变更</tpl>" +
					" <tpl if='isOtherFlow==4'> 已展期</tpl>" +
					" <tpl if='isOtherFlow==5'> 已违约</tpl>" +
					" <tpl if='isOtherFlow==6'> 已结项</tpl>" +
					"</span>"
			 ),
		   	root:'result',
	    	totalProperty: 'totalCounts',
//	    	searchCol:'customerName',
//		    searchTip:'请输入项姓名',
	    	grouped: false,
		    autoLoad:true,
		    pullRefresh: true,
		    listPaging: true,
		    params:{
		     customerName:param  //搜索的名字
		    }
    	});
    	this.callParent([config]);
    }

});
