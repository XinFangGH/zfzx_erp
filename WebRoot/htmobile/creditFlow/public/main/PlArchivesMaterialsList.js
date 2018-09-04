
//creditorList.js
Ext.define('htmobile.creditFlow.public.main.PlArchivesMaterialsList', {
    extend: 'mobile.List',
    
    name: 'PlArchivesMaterialsList',

    constructor: function (_cfg) {
		if(typeof(_cfg.projectId)!="undefined")
		{
		      this.projId=_cfg.projectId;
		}
		if(typeof(_cfg.businessType)!="undefined"){
		      
			   this.businessType=_cfg.businessType;
		}
    	var panel=Ext.create('tableHeader',{style:"margin-left:10px;",header:new Array("<span class='tableheadtwo' >文件名称(已上传)</span>"
    	                                                    )});
    	Ext.apply(_cfg,{
    		modeltype:"PlArchivesMaterialsList",
    		flex:1,
    		title:"<div style='font-size:15px;'>归档材料清单</div>",
    		items:[panel],
    		fields:[
								'proMaterialsId',
								'projId'
								,'materialsId'
							    ,'materialsName'
								,'isReceive'
								,'isShow'
								,'datumNums'
								,'isPigeonhole'
								,'remark'
								,'archiveConfirmRemark'
								,'xxnums'
								,'pigeonholeTime'
								,'recieveTime'
								,'materialsType'
							],
    	       url : __ctxPath + "/creditFlow/archives/listPlArchivesMaterials.do",
	    		root:'result',
	    	    totalProperty: 'totalCounts',
	    	    params : {
						projectId : this.projId,
						show : true,
						businessType:this.businessType
			},
		    itemTpl: new Ext.XTemplate(
		    		"<span  class='tablelistone' style='width:70%;'>{materialsName}</span>&nbsp;&nbsp;" +
 		        "<span   class='tablelist' style='width:20%;color:#a7573b;text-decoration:underline' onclick='javascript:downReportFileJS({proMaterialsId})'>下载</span>" +
 		        "<span  class='tablelist'  style='width:95%;'>线上份数{datumNums}&nbsp;&nbsp;线下份数{xxnums}</span>&nbsp;&nbsp;" +
		    	"<span class='tableDetail'  onclick='javascript:itemsingletap({" +
		    	"materialsName:\"{materialsName}\",xxnums:\"{xxnums}\",materialsType:\"{materialsType}\",datumNums:\"{datumNums}\"," +
		    	"isPigeonhole:\"{isPigeonhole}\",pigeonholeTime:\"{pigeonholeTime}\"" +
		    	",archiveConfirmRemark:\"{archiveConfirmRemark}\",isReceive:\"{isReceive}\",recieveTime:\"{recieveTime}\",remark:\"{remark}\"});'>></span>"
		    		,{
		    		filepathFormat: function(filepath) {
		    			 	var reg = /\\/g;
                            var  filepath=filepath.replace(reg,"/");
                            return filepath;
						}  
		    		})
    	});
    	itemsingletap = function(data) {
    	  var label = new Array("归档材料名称","线下份数","归档材料是否必备","线上份数",
    	  Ext.isEmpty(data.isPigeonhole)?null:"是否提交", Ext.isEmpty(data.isPigeonhole)?null:"提交时间", Ext.isEmpty(data.isPigeonhole)?null:"提交备注", Ext.isEmpty(data.isReceive)?null:"是否收到", Ext.isEmpty(data.isReceive)?null:"收到时间","备注"
    	); 
    	  var value = new Array(data.materialsName,data.xxnums,data.materialsType=="2"?"否":"是",data.datumNums,
    	  data.isPigeonhole=="false"?"否":"是",data.pigeonholeTime,data.archiveConfirmRemark,data.isReceive=="false"?"否":"是",data.recieveTime,data.remark
    	 );  
          getListDetail(label,value);
		    

}
     downReportFileJS=function(proMaterialsId){
       var	mark='sl_procredit_materials.'+proMaterialsId;
       var	typeisfile=null;
        var	title='归档材料清单';
        mobileNavi
        mobileNavi.push(
			            Ext.create('htmobile.creditFlow.public.file.DownLoadCsFile',{
				         mark:mark,
				         typeisfile:typeisfile,
						 title:title
			        	}));
      }
    	this.callParent([_cfg]);
    	

    }
	
});
