/**
 * 网审信息
 */
Ext.define('htmobile.creditFlow.public.main.NetCheckInfoList', {
    extend: 'mobile.List',
    name: 'NetCheckInfoList',
    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
    	Ext.apply(_cfg,{
    		style:'background-color:white;',
    		modeltype:"NetCheckInfoList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:"网审信息",
    		items:[{
    		xtype:'panel',
    		docked:'top',
    		items:[{
    			html:`	
    					<div class="list-column">
    					<span style='width:50%'>查询对象</span>
    					<span style='width:50%'>查询内容</span>
    					</div>
    				`
    		}]
    	}],
    		fields : [{
				name : 'id',
				type : 'int'
			}, 'projectId', 'serachObj', 'serachInfo','isException','remark', 'checkUserId','checkUserName'],
    	        url :__ctxPath+ "/bpPersonNetCheckInfo/listBpPersonNetCheckInfo.do?Q_projectId_L_EQ="+this.projectId,
	    		root:'result',
	    	    totalProperty: 'totalCounts',
		        itemTpl: `
 		        	 	<div class="list-column-content">
	    					<span style='width:50%'>{serachObj}</span>
	    					<span style='width:50%'>{serachInfo}</span>
    					</div>
 		        		`,
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});
    	this.callParent([_cfg]);

    },
	itemsingletap : function(obj, index, target, record) {
	
	}
});
