/**
 * 归档材料清单
 */
Ext.define('htmobile.creditFlow.public.main.ArchiveMaterialList', {
    extend: 'mobile.List',
    name: 'ArchiveMaterialList',
    constructor: function (_cfg) {
    	Ext.applyIf(this, _cfg);
    	Ext.apply(_cfg,{
    		style:'background-color:white;',
    		modeltype:"ArchiveMaterialList",
    		flex:1,
    		width:"100%",
		    height:"100%",
    		title:"归档材料清单",
    		items:[{
    		xtype:'panel',
    		docked:'top',
    		items:[{html:`<div class="list-column">
    						<span>归档材料名称</span>
    						<span>线下份数</span>
    						<span>是否必备</span>
    					</div>`
    			}]
    		}],
    		fields : ['proMaterialsId', 'projId', 'materialsId',
					'materialsName', 'isReceive', 'isShow', 'datumNums',
					'isPigeonhole', 'remark', 'archiveConfirmRemark', 'xxnums',
					'pigeonholeTime', 'recieveTime', {name:'materialsType',convert:function(v){
						return v===1?'是':'否';
					}}],
	        url : __ctxPath + "/creditFlow/archives/listPlArchivesMaterials.do",
	        params:{
	        	projectId : this.projectId,
				show : true,
				businessType : this.businessType
	        },
    		root:'result',
    	    totalProperty: 'totalCounts',
	        itemTpl: `<div class="list-column-content">
    					<span>{materialsName}</span>
    					<span>{xxnums}</span>
    					<span>{materialsType}</span>
					</div>`,
	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});
    	this.callParent([_cfg]);
    },itemsingletap : function(obj, index, target, record) {
	
	}
});
