/**
 * @author lisl
 * @class SlDueDiligenceReportView
 * @description 尽职调查综合报告
 * @extends Ext.Window
 */
ProjectDueDiligenceReportView = Ext.extend(Ext.Window,{
      constructor:function(_cfg){
          Ext.applyIf(this,_cfg);
          this.initUIComponents();
          ProjectDueDiligenceReportView.superclass.constructor.call(this,{
        	   title : '尽职调查综合报告',
               width : 733,
			   height : 430,
			   border : false,
               modal : true,
               iconCls:'',
               autoScroll :true,
               maximizable:true,
               layout : 'fit',
               items:[],
               scope : this,
               listeners : {
               		beforeclose : function(win) {
               			this.slReportView.grid_ReportPanel.stopEditing();
               		}
               }
          });
      },
      initUIComponents : function() {
      	  var jsArr = [
    		  __ctxPath + '/js/creditFlow/report/SlReportView.js'// 尽职调查综合报告
    		  ];
    	  $ImportSimpleJs(jsArr, this.constructPanel,this);		
	  },//初始化组件
	  constructPanel : function() {
		  this.slReportView = new SlReportView({projectId : this.projectId, businessType : this.businessType,isHiddenAffrim_report : false,isHidden_report : this.isHidden_report,isgdEdit : this.isgdEdit});
		  this.add(this.slReportView);
		  this.doLayout();
	  }
});