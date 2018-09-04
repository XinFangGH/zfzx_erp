

Ext.define('htmobile.approve.XiangmuConfig', {
    extend: 'Ext.form.Panel',
    name: 'XiangmuConfig',
    constructor: function (config) {
    	config = config || {};
    	var personData={};
    	Ext.apply(config,{
		    fullscreen: true,
		    title:"项目申请",
		    scrollable:{
		    	direction: 'vertical'
		    },
            items:[{
		           xtype:"button",
		           text:"个人房屋抵押申请",
		           handler:this.Xiangmuloll11
		           //html: "<div class='x-unsized x-button-normal x-button buttonCls' style='border-radius: 0; text-align: center; height:50px;'onclick='javascript:lol();'>个人项目申请</div>"
		          },{
		           xtype:"button",
		           text:"个人车辆抵押申请",
		           handler:this.Xiangmulpll11
		           //html: "<div class='x-unsized x-button-normal x-button buttonCls' style='border-radius: 0; text-align: center; height:50px;'onclick='javascript:lol();'>个人项目申请</div>"
		          },{
		           xtype:"button",
		           text:"个人车辆质押申请",
		           handler:this.Xiangmulqll11
		           //html: "<div class='x-unsized x-button-normal x-button buttonCls' style='border-radius: 0; text-align: center; height:50px;'onclick='javascript:lol();'>个人项目申请</div>"
		          },{
		           xtype:"button",
		           text:"个人特殊业务申请",
		           handler:this.Xiangmulrll11
		           //html: "<div class='x-unsized x-button-normal x-button buttonCls' style='border-radius: 0; text-align: center; height:50px;'onclick='javascript:lol();'>个人项目申请</div>"
		          }
//		          , {
//		           xtype:"button",
//		           text:"企业项目申请",
//		           handler:this.Xiangmulpll11
//		          // html: "<div class='x-unsized x-button-normal x-button buttonCls' style='border-radius: 0; text-align: center; height:50px;'onclick='javascript:lpl();'>企业项目申请</div>"
//		          }
		          
		          
		         ]

    	});
	 
    	this.callParent([config]);
    },
    Xiangmuloll11 :function() {
		  mobileNavi.push(Ext.create('htmobile.approve.person.ApplyPersonloan',{
		  operationType:"SmallLoan_PersonalCreditLoanBusiness",
		  history:"personHouse",
		  only:true
		  }));
  	            
  	},
    Xiangmulpll11 :function() {

		  mobileNavi.push(Ext.create('htmobile.approve.person.ApplyPersonloan',{
		  operationType:"SmallLoan_PersonalCreditLoanBusiness",
		  history:"personVehicleMortgage",
		  only:false
		  }));
  	            
  	},
    Xiangmulqll11 :function() {

		  mobileNavi.push(Ext.create('htmobile.approve.person.ApplyPersonloan',{
		  operationType:"SmallLoan_PersonalCreditLoanBusiness",
		  history:"personVehiclePledge",
		  only:false
		  }));
  	            
  	},
    Xiangmulrll11 :function() {

		  mobileNavi.push(Ext.create('htmobile.approve.person.ApplyPersonloan',{
		  operationType:"SmallLoan_PersonalCreditLoanBusiness",
		  history:"personSpecial",
		  only:false
		  }));
  	            
  	}
    

});
