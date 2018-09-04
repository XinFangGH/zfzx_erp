

Ext.define('htmobile.customer.person.menudetail.FamilyEconomyCarList', {
    extend: 'mobile.List',
    
    name: 'FamilyEconomyCarList',

    constructor: function (config) {
		this.personId=config.data.id;
    	config = config || {};
    	var panel=Ext.create('tableHeader',{header:new Array("<span class='tablehead' >所有人</span>",
    	                                                      "<span class='tablehead' >品牌</span>",
    	                                                      "<span class='tablehead' >车型</span>")});
    	Ext.apply(config,{
    		modeltype:"FamilyEconomyCarList",
    		flex:1,
    		title:"车辆信息",
    		items:[panel],
    		fields:[
							{
								name : 'id'
							},
							{
								name : 'personId'
							},{
								name : 'propertyOwner'
							},
							{
								name : 'carSystemType'
							},
							{
								name : 'carType'
							},
							{
								name : 'carLicenseNumber'
							},
							{
								name : 'isMortgage'
							},
							{
								name : 'newCarValue'
							},{
								name : 'loanMoney'
							},{
								name : 'carFactValue'
							},{
								name : 'yearOfCarUse'
							},{
								name : 'finalCertificationPrice'
							},{
								name : 'loanMoney'
							}],
    		url : __ctxPath+ '/creditFlow/customer/person/listCsPersonCar.do?personId='+ this.personId,
    		root:'result',
    	    totalProperty: 'totalCounts',
		    itemTpl: "<span  class='tablelist'>{propertyOwner}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{carSystemType}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelist' >{carType}</span>" +
		    		"<span class='tableDetail'>></span>",
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([config]);

    },
	itemsingletap : function(obj, index, target, record) {
		    	
    	  var data=record.data;
    	  var label = new Array("所有人","品牌","车型","车牌号","是否按揭",
    	 "新车价格·万元","贷款余额·万元","使用年限","市场评估价值·万元"); 
    	  var value = new Array(data.propertyOwner, data.carSystemType,data.carType,data.carLicenseNumber,data.isMortgage,
    	  data.newCarValue,data.loanMoney,data.yearOfCarUse,data.finalCertificationPrice);  
          getListDetail(label,value);
		    

}
});
