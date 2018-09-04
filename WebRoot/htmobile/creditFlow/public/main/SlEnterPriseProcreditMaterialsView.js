
//creditorList.js
Ext.define('htmobile.creditFlow.public.main.SlEnterPriseProcreditMaterialsView', {
    extend: 'mobile.List',
    name: 'SlEnterPriseProcreditMaterialsView',
    constructor: function (_cfg) {
     this.readOnly=_cfg.readOnly;
	 if(typeof(_cfg.projectId)!="undefined")
		{
		      this.projId=_cfg.projectId;
		}
		if(typeof(_cfg.businessType)!="undefined")
		{
		      this.businessType=_cfg.businessType;
		}
    	Ext.apply(_cfg,{
    		title:'贷款材料清单',
    		modeltype:"SlEnterPriseProcreditMaterialsView",
    		flex:1,
    		style:'background-color:white;',
    		items:[{
    			xtype:'panel',
    			docked:'top',
    			items:[{
    				html:`<div class="panel-title-m">材料名称</div>`
    			}]
    		}],
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
		    itemTpl:  new Ext.XTemplate("<span  class='tablelisttwo'>{materialsName}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
		    		"<span    onclick='javascript:downReportFileJS({proMaterialsId})' style='color:#a7573b;text-decoration:underline;font-size:12px;'>(上传)</span>" +
		    		"<span class='tableDetail'  onclick='javascript:itemsingletap({" +
		    		"materialsName:\"{materialsName}\",datumNums:\"{datumNums}\",datumNumsOfLine:\"{datumNumsOfLine}\",materialsName:\"{materialsName}\",isReceive:\"{isReceive}\",isArchiveConfirm:\"{isArchiveConfirm}\"" +
		    		",isPigeonhole:\"{isPigeonhole}\",confirmTime:\"{confirmTime}\",remark:\"{remark}\"});'>></span>")
 	       /* listeners : {
				itemsingletap : this.itemsingletap
			}*/
    	});
    	this.callParent([_cfg]);
    	
    	
    	
    	var readOnly=this.readOnly;
      downReportFileJS=function(proMaterialsId){
       var	mark='sl_procredit_materials.';
       var	typeisfile=null;
        var	title='材料列表';
       mobileNavi.push(
         Ext.create('htmobile.creditFlow.public.file.FileList',
         	{ 
         	  mark:mark,
         	 readOnly: readOnly,
	         typeisfile:typeisfile,
	         tableid:proMaterialsId,
			 title:title}
         	));
 /*       mobileNavi.push(
			            Ext.create('htmobile.creditFlow.public.file.DownLoadCsFile',{
				         mark:mark,
				         typeisfile:typeisfile,
						 title:title
			        	}));*/
      }
      
      itemsingletap= function(data) {
    	  var label = new Array("材料名称","上传材料份数","线下材料份数",Ext.isEmpty(data.isReceive)?null:"是否收到"
    	  ,Ext.isEmpty(data.isArchiveConfirm)?null:"是否归档",Ext.isEmpty(data.isPigeonhole)?null:"是否提交","提交时间","备注"
    	); 
    	  var value = new Array(data.materialsName,data.datumNums,data.datumNumsOfLine,data.isReceive=="false"?"否":"是"
    	  ,data.isArchiveConfirm=="false"?"否":"是",data.isPigeonhole=="false"?"否":"是",data.confirmTime,data.remark
    	 );  
          getListDetail(label,value);
    }
     
		    

}
	,
	itemsingletap : function(obj, index, target, record) {n 
		  var data=record.data
    	  var label = new Array("材料名称","上传材料份数","线下材料份数",Ext.isEmpty(data.isReceive)?null:"是否收到"
    	  ,Ext.isEmpty(data.isArchiveConfirm)?null:"是否归档",Ext.isEmpty(data.isPigeonhole)?null:"是否提交","提交时间","备注"
    	); 
    	  var value = new Array(data.materialsName,data.datumNums,data.datumNumsOfLine,data.isReceive=="false"?"否":"是"
    	  ,data.isArchiveConfirm=="false"?"否":"是",data.isPigeonhole=="false"?"否":"是",data.confirmTime,data.remark
    	 );  
          getListDetail(label,value);
    
     
    
    
    
    
    }
});
