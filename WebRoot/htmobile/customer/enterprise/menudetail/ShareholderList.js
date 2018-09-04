

Ext.define('htmobile.customer.enterprise.menudetail.ShareholderList', {
    extend: 'mobile.List',
    
    name: 'ShareholderList',

    constructor: function (config) {
		this.enterpriseId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >股东(投资人)类别</span>",
    	                                                      "<span class='tablehead' >股东(投资人)名称</span>",
    	                                                      "<span class='tablehead' >出资额(元)</span>")});
    	Ext.apply(config,{
    		modeltype:"ShareholderList",
    		flex:1,
    		title:"<span style='font-size:16px;'>股东(投资人)信息</span>",
    		items:[panel],
    		fields:[
									{
										name : 'id'
									},
									{
										name : 'shareholdertype'
									}/*,
									{
										name : 'shareholdercode'
									}*/,
									{
										name : 'capital'
									},
									{
										name : 'capitaltype'
									},
									{
										name : 'share'
									},
									{
										name : 'shareholder'
									},
									{
										name : 'remarks'
									},{
									
									    name : 'createTime'
									},
									{
										name : 'shareholdertypeName'
									},
									{
										name : 'capitaltypeName'
									}

							],
    		url:__ctxPath+ '/creditFlow/common/getShareequity.do?enterpriseId='+ this.enterpriseId,
    		pullRefresh:true,
    		root:'result',
    		totalProperty: 'totalCounts',
		    itemTpl: "<span  class='tablelist'>{shareholdertypeName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{shareholder}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{capital}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {

    	  var data=record.data;
    	  var label = new Array("股东(投资人)类别","股东(投资人)名称","出资额","出资方式","持股比例",
    	 "备注"); 
    	  var value = new Array(data.shareholdertypeName, data.shareholder,data.capital,data.capitaltype,data.share,
    	  data.remarks);  
          getListDetail(label,value);
		    

}
});
