/**
 * @author lisl
 * @class GlEnterpriseEvaluationView
 * @description 企业资信评估
 * @extends Ext.Window
 */
EnterpriseEvaluationWin = Ext.extend(Ext.Window,{
	  /*customerId:null,
	  customerType:null,
	  isReadonly:false,*/
      constructor:function(_cfg){
      	  if(typeof(_cfg.customerId)!="undefined"){
        	 this.customerId=_cfg.customerId
          };
          if(typeof(_cfg.customerType)!="undefined"){
        	 this.customerType=_cfg.customerType
          };
          if(_cfg.isReadonly){
              this.isReadonly=_cfg.isReadonly;
          };
          Ext.applyIf(this,_cfg);
          this.initUIComponents();
          EnterpriseEvaluationWin.superclass.constructor.call(this,{
        	   title : this.customerType+'资信评估',
               width:800,
			   height : 425,
               modal : true,
               iconCls:'',
               autoScroll :true,
               maximizable:true,
               layout : 'fit',
               items:[this.enterpriseEvaluation]
          
          });
      },
      initUIComponents : function() {
      	this.enterpriseEvaluation = new EnterpriseEvaluationGua({customerId :this.customerId,customerType:this.customerType,isHidden:this.isReadonly});

	  }
});