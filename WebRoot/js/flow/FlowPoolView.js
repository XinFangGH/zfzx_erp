/**
 * 
 * @class
 * @extends Ext.Panel
 */
var FlowPoolView=Ext.extend(Ext.Panel,{
	constructor:function(config){
		Ext.applyIf(this,config);
		
		FlowPoolView.superclass.constructor.call(this,{
			id:'FlowP',
			title:'流程分配池',
			items:[
				
			]
		});
	}
});
