/**
 * @author lisl
 * @class GlCustomerFilesView
 * @description 项目文件
 * @extends Ext.Window
 */
GlArchiveFilesView = Ext.extend(Ext.Window,{
	  isHidden : true,
      constructor:function(_cfg){
          Ext.applyIf(this,_cfg);
          if (this.flowType == 'zmNormalFlow') {
			  this.isHidden = false;
		  }
          this.initUIComponents();
          GlArchiveFilesView.superclass.constructor.call(this,{
        	   title : '归档文件',
        	   border : false,
               width : 800,
			   height : this.isHidden ? 320 : 370,
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
    		  __ctxPath + '/publicmodel/uploads/js/uploads.js'
    	  ];
    	  $ImportSimpleJs(jsArr, this.constructPanel,this);
	  },//初始化组件
	   constructPanel : function() {
	   	 
		    this.uploads1 = new uploads({
		    	projectId : this.projectId,
		    	businessType : 'Guarantee',
		    	isDisabledButton : this.isHidden_actualtocharge,
		    	isDisabledOnlineButton : this.isHidden_actualtocharge,
		    	isNotOnlyFile : this.isHidden_actualtocharge,
		    	isgdHidden : false,
		    	isgdEdit : this.isGdEdit_actualtocharg,
		    	tableName :'gl_actual_to_charge',
				typeisfile :'typeisglactualtocharge2',
		    	uploadsSize :15
		    });
		    this.uploads2 = new uploads({
		    	projectId : this.projectId,
		    	businessType : 'Guarantee',
		    	isDisabledButton : this.isHidden_back_gl_bank_guaranteemoney,
		    	isDisabledOnlineButton : this.isHidden_back_gl_bank_guaranteemoney,
		    	isNotOnlyFile : this.isHidden_back_gl_bank_guaranteemoney,
		    	isgdHidden : false,
		    	isgdEdit : this.isGdEdit_back_gl_bank_guaranteemoney,
		    	tableName :'back_gl_bank_guaranteemoney',
				typeisfile :'typeisbackglbankguaranteemoney',
		    	uploadsSize :15
		    });
		  this.outPanel = new Ext.Panel({
							modal : true,
							border : false,
							frame : true,
							layout:'anchor',
		  					anchor : '100%',
							items : [
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【退还客户保证金凭证】:</font></B>'},
										this.uploads1,
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【银行解冻保证金凭证】:</font></B>'},
										this.uploads2,
									]
						});
				this.add(this.outPanel);
				this.doLayout();
	   }
});