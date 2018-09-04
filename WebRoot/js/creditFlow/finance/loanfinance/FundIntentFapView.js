/**
 *放款收息表，通用
 * 
 * BpFundIntent、SlFundIntent两个类均继承FundIntent.
 * 可以使用FundIntent对象
 **/
 
 FundIntentFapView = Ext.extend(Ex.Panel,{
 	constructor:function(_cfg){
 		Ext.applyIf(this,_cfg);
 		this.initUIComponents();
 		FundIntentFapView.superclass.constructor.call(this,{
 			//region : 'center',
			layout : 'anchor',
			anchor : '100%',
			items:[]
 		});
 	},
 	initUIComponents:function(){
 		//统计插件
 		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
 		
 		this.gridPanel = new Ext.grid.EditorGridPanel({
 			authHeight:true,
 			width:'100%',
 			sm:this.sm,
 			cm:this.cm,
 			store:this.store
 		});
 	}
 });