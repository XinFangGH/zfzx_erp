/**
 * @author lisl
 * @class ProjectAssuretenet
 * @description 贷前准入原则
 * @extends Ext.Window
 */
ProjectAssuretenet = Ext.extend(Ext.Window,{
      constructor:function(_cfg){
          Ext.applyIf(this,_cfg);
          this.initUIComponents();
          ProjectAssuretenet.superclass.constructor.call(this,{
               title : '准入原则',
               border : false,
               width : 576,
			   height : 365,
               modal : true,
               iconCls:'',
               autoScroll :true,
               maximizable:true,
               layout : 'fit',
               items:[],
               scope : this,
               listeners : {
               		beforeclose : function(win) {
               			this.slProcreditAssuretenetedForm.gridPanel.stopEditing();
               		}
               }
          });
      },
      initUIComponents : function() {
      	  var jsArr = [
    		  __ctxPath + '/js/creditFlow/assuretenet/SlProcreditAssuretenetedForm.js'// 贷款准入原则
    		  ];
    	  $ImportSimpleJs(jsArr, this.constructPanel,this);		
	  },//初始化组件
	  constructPanel : function() {
		  this.slProcreditAssuretenetedForm = new SlProcreditAssuretenetedForm({projectId : this.projectId, businessType : this.businessType, isEditRiskmanageropinion : this.isEditRiskmanageropinion,isEditBbusinessmanageropinion : this.isEditBbusinessmanageropinion,isAutoHeight : false});
		  this.add(this.slProcreditAssuretenetedForm);
		  this.doLayout();
	  }
});