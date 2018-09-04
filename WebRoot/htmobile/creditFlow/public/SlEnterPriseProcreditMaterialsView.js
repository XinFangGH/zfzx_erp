
//creditorList.js
Ext.define('htmobile.creditFlow.public.SlEnterPriseProcreditMaterialsView', {
    extend: 'mobile.List',
    
    name: 'SlEnterPriseProcreditMaterialsView',

    constructor: function (_cfg) {
	 if(typeof(_cfg.projectId)!="undefined")
		{
		      this.projId=_cfg.projectId;
		}
		if(typeof(_cfg.businessType)!="undefined")
		{
		      this.businessType=_cfg.businessType;
		}
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tableheadtwo' >材料名称</span>")
    	                                                   });
    	Ext.apply(_cfg,{
    		modeltype:"CreditorList",
    		flex:1,
    		title:this.titleText,
    		items:[panel],
    		fields:[
             {name: 'materialsType'},
             {name: 'materialsName'},
             {name: 'datumNums'},
             {name: 'datumNumsOfLine'},
             {name: 'remark'},
             {name: 'proMaterialsId'},
             {name: 'isReceive'},
             {name: 'ruleExplain'},
             {name: 'isArchiveConfirm'},
             {name: 'confirmTime'},
             {name: 'isPigeonhole'},
              {name: 'parentName'}
              ],
    	       url:__ctxPath + '/materials/listEnterpriseSlProcreditMaterials.do',
	    		root:'result',
	    	    totalProperty: 'totalProperty',
		        grouped: true,
		        groupedFiled:'parentName',
		        isGroupedAll:true,
	    	    params : {
					 projId : this.projId,
					 businessType:this.businessType,
					 show : true
			},
		    itemTpl:  new Ext.XTemplate("<span  class='tablelisttwo'>{materialsName}</span>&nbsp;&nbsp;" +
		    		"<span   class='tablelisttwo' onclick='javascript:downReportFileJS({proMaterialsId})'>{datumNums}份&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp<span style='color:#a7573b;text-decoration:underline;'>下载</span></span>" +
		    		"<span class='tableDetail' >></span>"
		    		),
		    		
 	        listeners : {
				itemsingletap : this.itemsingletap
			}
    	});

    	this.callParent([_cfg]);
    	
    	
    	
    	
      downReportFileJS=function(proMaterialsId){
       var	mark='sl_procredit_materials.'+proMaterialsId;
       var	typeisfile=null;
        var	title='上传材料';
        mobileNavi
        mobileNavi.push(
			            Ext.create('htmobile.creditFlow.public.DownLoadCsFile',{
				         mark:mark,
				         typeisfile:typeisfile,
						 title:title
			        	}));
      }
      
      
     
		    

}
	,
	itemsingletap : function(obj, index, target, record) {
		  var data=record.data
    	  var label = new Array("材料名称","上传材料份数","线下材料份数","是否收到"
    	  ,"是否归档","是否提交","提交时间","备注"
    	); 
    	  var value = new Array(data.materialsName,data.datumNums,data.datumNumsOfLine,data.isReceive
    	  ,data.isArchiveConfirm,data.isPigeonhole,data.confirmTime,data.remark
    	 );  
          getListDetail(label,value);
    }
});
