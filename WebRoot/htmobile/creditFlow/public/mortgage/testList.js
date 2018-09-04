
//creditorList.js
Ext.define('htmobile.creditFlow.public.mortgage.DZYMortgageViewList', {
    extend: 'Ext.List',
    id:'DZYMortgageViewList',
    name: 'DZYMortgageViewList',
    constructor: function (_cfg) {
    	Ext.apply(_cfg,{
    		  fullscreen: true,
		    itemTpl: '{title}',
		    data: [
		        { title: 'Item 1' },
		        { title: 'Item 2' },
		        { title: 'Item 3' },
		        { title: 'Item 4' }
		    ],
        plugins:[ {
            type:"listopt",
            items:[ {
                action:"Edit",
                cls:"write",
                color:"blue",
                text:"编辑"
            }, {
                action:"Remove",
                cls:"trash",
                color:"red",
                text:"删除"
            } ]
        } ]
    		
    		
    	});
    	this.callParent([_cfg]);
}	
    		
 
});
