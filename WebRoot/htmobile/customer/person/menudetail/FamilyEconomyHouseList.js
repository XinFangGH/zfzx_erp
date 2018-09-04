

Ext.define('htmobile.customer.person.menudetail.FamilyEconomyHouseList', {
    extend: 'mobile.List',
    
    name: 'FamilyEconomyHouseList',

    constructor: function (config) {
		this.personId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >产权人</span>",
    	                                                      "<span class='tablehead' >房屋类型</span>",
    	                                                      "<span class='tablehead' >房产建筑面积(m)</span>")});
    	Ext.apply(config,{
    		modeltype:"FamilyEconomyHouseList",
    		flex:1,
    		title:"房产信息",
    		items:[panel],
    		fields:[{
								name : 'id'
							},
							{
								name : 'personId',
								value:this.personId
							},{
								name : 'propertyOwner'
							},
							{
								name : 'floorSpace'
							},
							{
								name : 'address'
							},
							{
								name : 'isMortgage'
							},
							{
								name : 'hoseValue'
							},{
								name : 'loanMoney'
							},{
								name : 'houseFactValue'
							},{
								name : 'purchaseTime'
							}
							,{
								name : 'marketValue'
							},{
								name : 'houseType'
							},{
								name : 'purchasePrice'
							},{
								name : 'propertyScale'
							},{
								name : 'loanPeriod'
							},{
								name : 'monthlyPayments'
							},{
								name : 'isDoubleBalloon'
							},{
								name : 'isSchoolDistrict'
							},{
								name : 'isOverstory'
							},{
								name : 'isFitment'
							}
              ],
    		url : __ctxPath+ '/quickenLoan/listCsPersonHouse.do?personId='+ this.personId,
    		root:'result',
    	    totalProperty: 'totalCounts',
		    itemTpl: "<span  class='tablelist'>{propertyOwner}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{houseType}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{floorSpace}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
    	  var data=record.data;
    	  var label = new Array("产权人","房屋类型","房产建筑面积(m)","地址","房产总值.万元",
    	 "贷款余额.万元","贷款年限","月供.万元"); 
    	  var value = new Array(data.propertyOwner, data.houseType,data.floorSpace,data.address,data.hoseValue,
    	  data.loanMoney,data.loanPeriod,data.monthlyPayments);  
          getListDetail(label,value);
		    

}
});
