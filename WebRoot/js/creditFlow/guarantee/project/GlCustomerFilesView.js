/**
 * @author lisl
 * @class GlCustomerFilesView
 * @description 项目文件
 * @extends Ext.Window
 */
GlCustomerFilesView = Ext.extend(Ext.Window,{
	  isHidden : true,
      constructor:function(_cfg){
          Ext.applyIf(this,_cfg);
          if (this.processName == 'zmNormalFlow') {
			  this.isHidden = false;
		  }
          this.initUIComponents();
          GlCustomerFilesView.superclass.constructor.call(this,{
        	   title : '项目文件',
        	   border : false,
               width : 800,
			   height : 500,
               modal : true,
               iconCls:'',
               maximizable:true,
               layout : 'fit',
               items:[]
          
          });
      },
      initUIComponents : function() {
    	  var jsArr = [
    		  __ctxPath + '/js/creditFlow/guarantee/contract/LetterAndBookView.js',// 担保意向书、对外担保承诺函
    		  __ctxPath + '/publicmodel/uploads/js/uploads.js'
    	  ];
    	  $ImportSimpleJs(jsArr, this.constructPanel,this);
	  },//初始化组件
	   constructPanel : function() {
	   	  this.letterAndBookView = new LetterAndBookView({
		    	projectId : this.projectId,
		    	businessType : 'Guarantee',
		    	LBTemplate : 'simulateEnterpriseBook',
		    	isHidden : this.isHidden_simulateEnterpriseBook,
		    	isRecordHidden : false,
		    	isGdEdit : this.isGdEdit_seb
		    });
	      this.letterAndBookView1 = new LetterAndBookView({
		    	projectId : this.projectId,
		    	businessType : 'Guarantee',
		    	LBTemplate : 'assureCommitmentLetter',
		    	isHidden : this.isHidden_assureCommitmentLetter,
		    	isRecordHidden : false,
		    	isGdEdit : this.isGdEdit_acl
		    });
		    this.letterAndBookView2 = new LetterAndBookView({
		    	projectId : this.projectId,
		    	businessType : 'Guarantee',
		    	LBTemplate : 'partnerMeetingResolution',
		    	isHiddenPanel : this.isHidden,
		    	isHidden : this.isHidden_partnerMeetingResolution,
		    	isRecordHidden : false,
		    	isGdEdit : this.isGdEdit_pmr
		    });
		    this.uploads = new uploads({
				projectId : this.projectId,
				isgdHidden : false,
				isHidden : this.isHidden_gl_borrow_guarantee,
		    	isgdEdit : this.isGdEdit_gbg,
				isJKDB : true,//标识是否客户借款合同及担保合同，true为uploads方法中上传的文件类型为setname，否则为titleName
				tableName :'gl_borrow_guarantee',
				typeisfile :'typeisglborrowguarantee',
				businessType:'Guarantee',
				uploadsSize : 15
			})
	      this.uploads1 = new uploads({
				projectId : this.projectId,
				isgdHidden : false,
				isHidden : this.isHidden_gl_db_guarantee,
		    	isgdEdit : this.isGdEdit_gdg,
				isJKDB : true,//标识是否客户借款合同及担保合同，true为uploads方法中上传的文件类型为setname，否则为titleName
				tableName :'gl_db_guarantee',
				typeisfile :'typeisgldbguarantee',
				businessType:'Guarantee',
				uploadsSize : 15
			})
			this.uploads2 = new uploads({
		    	projectId : this.projectId,
		    	businessType : 'Guarantee',
		    	isgdHidden : false,
		    	isHidden : this.isHidden_sdbzrjchsmj,
		    	isgdEdit : this.isGdEdit_sdbzrjchsmj,
		    	tableName :'typeisdbzrjchsmj'
		    });
		    this.uploads3 = new LetterAndBookView({
				projectId : this.projectId,
				businessType : 'Guarantee',
				LBTemplate : 'trialResolutions',
				isHiddenPanel : this.isHidden,
		    	isHidden : this.isHiddenSbhFileEdit,
		    	isRecordHidden : false,
		    	isGdEdit : this.isGdSbhFile
			});//审保会决议文档
		   /* this.uploads3 = new uploads({
		    	projectId : this.projectId,
		    	businessType :'Guarantee',
		    	isNotOnlyFile : false,
		    	isHiddenColumn : false,
		    	isDisabledButton : false,
		    	isgdHidden : false,
		    	setname :'审保会决议文档',
		    	titleName :'审保会决议文档',
		    	tableName :'typeisdbsbhjywd',
		    	uploadsSize :1,
		    	isHidden : this.isHiddenSbhFileEdit,
		    	isgdEdit : this.isGdSbhFile
		    });*/
		    this.uploadsTZD = new uploads({
		    	projectId : this.projectId,
		    	businessType :'Guarantee',
		    	isNotOnlyFile : true,
		    	isHiddenColumn : false,
		    	isDisabledButton : false,
		    	isgdHidden : false,
		    	setname :'放款通知单扫描件',
		    	titleName :'放款通知单扫描件',
		    	tableName :'gl_db_guarantee',
		    	typeisfile :'tongzhidan',
		    	isHidden : this.isHiddenSbhFileEdit,
		    	isgdEdit : this.isGdSbhFile
		    });
		  this.outPanel = new Ext.Panel({
							modal : true,
							border : false,
							frame : true,
							height : 470,
							autoScroll :true,
							layout:'anchor',
		  					anchor : '100%',
							items : [
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【拟担保意向书】:</font></B>'},
										this.letterAndBookView,
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【对外担保承诺函】:</font></B>'},
										this.letterAndBookView1,
										{xtype:'panel',border:false,hidden : this.isHidden, bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【股东会决议】:</font></B>'},
										this.letterAndBookView2,
										{xtype:'panel',border:false,hidden : this.isHidden, bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【审保会决议文档】:</font></B>'},
										this.uploads3,
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【客户银行借款合同】:</font></B>'},
										this.uploads,
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【银行担保合同】:</font></B>'},
										this.uploads1,
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【还款凭证】:</font></B>'},
										this.uploads2,
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【放款通知单扫描件】:</font></B>'},
										this.uploadsTZD
									]
						});
				this.add(this.outPanel);
				this.doLayout();
	   }
});