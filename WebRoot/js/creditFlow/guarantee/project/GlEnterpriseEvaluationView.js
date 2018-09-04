/**
 * @author lisl
 * @class GlEnterpriseEvaluationView
 * @description 企业资信评估
 * @extends Ext.Window
 */
GlEnterpriseEvaluationView = Ext.extend(Ext.Window,{
      constructor:function(_cfg){
          Ext.applyIf(this,_cfg);
          this.initUIComponents();
          GlEnterpriseEvaluationView.superclass.constructor.call(this,{
        	   title : '企业资信评估',
               width : 667,
			   height : 425,
               modal : true,
               iconCls:'',
               autoScroll :true,
               maximizable:true,
               layout : 'fit',
               items:[]
          
          });
      },
      initUIComponents : function() {
    	  var jsArr = [
    		  __ctxPath + '/js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluation.js' //企业评估报告
    		  ];
    	  $ImportSimpleJs(jsArr, this.constructPanel,this);
	  },//初始化组件
	   constructPanel : function() {
		  this.enterpriseEvaluation = new EnterpriseEvaluationGuarantee({projectId : this.projectId,isHidden : this.isHidden});
		  this.add(this.enterpriseEvaluation);
		  this.doLayout();
	   }
});