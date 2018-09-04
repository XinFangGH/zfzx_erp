PersonalPlanTypeView=Ext.extend(Ext.Panel,{
    searchFormPanel:null,
    gridPanel:null,
	constructor:function(_cfg){
	     Ext.applyIf(this,_cfg);
	     this.initUI();
	     PersonalPlanTypeView.superclass.constructor.call(this,{
	         id:'PersonalPlanTypeView',
	         region:'center',
	         layout:'border',
	         title:'个人计划类型列表',
	         items:[this.searchFormPanel,this.gridPanel]
	     });
	},
	initUI:function(){
//	     this.searchFormPanel=new 
	}
	
});