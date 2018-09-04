/**
 * @author lisl
 * @class GlCertificateView
 * @description 款项凭证
 * @extends Ext.Window
 */
GlCertificateView = Ext.extend(Ext.Window, {
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				GlCertificateView.superclass.constructor.call(this, {
							title : '款项凭证',
							width : 655,
							height : 368,
							modal : true,
							iconCls : '',
							autoScroll : true,
							border : false,
							maximizable : true,
							layout : 'fit',
							items : []

						});
			},
			initUIComponents : function() {
				var jsArr = [__ctxPath + '/publicmodel/uploads/js/uploads.js'];
				$ImportSimpleJs(jsArr, this.constructPanel, this);
			},// 初始化组件
			constructPanel : function() {
				this.uploads1 = new uploads({
							projectId : this.projectId,
							isDisabledButton : this.isDisabledButton,
							isDisabledOnlineButton : this.isDisabledOnlineButton,
							setname : '银行冻结保证金凭证',
							titleName : '银行冻结保证金凭证',
							tableName : 'gl_Bank_guaranteemoney',
							typeisfile : 'typeisglbankguaranteemoney1',
							businessType : 'Guarantee',
							uploadsSize : 15

						});
				this.uploads2 = new uploads({
							projectId : this.projectId,
							isDisabledButton : this.isDisabledButton,
							isHiddenOnlineButton : this.isHiddenOnlineButton,
							isDisabledOnlineButton : this.isDisabledOnlineButton,
							setname : '收取客户保证金凭证',
							titleName : '收取客户保证金凭证',
							tableName : 'gl_customer_guaranteemoney',
							typeisfile : 'typeisglbankguaranteemoney2',
							businessType : 'Guarantee',
							uploadsSize : 15

						});
				this.uploads3 = new uploads({
							projectId : this.projectId,
							isDisabledButton : this.isDisabledButton,
							isHiddenOnlineButton : this.isHiddenOnlineButton,
							isDisabledOnlineButton : this.isDisabledOnlineButton,
							setname : '收取保费凭证',
							titleName : '收取保费凭证',
							tableName : 'gl_baofei_guaranteemoney',
							typeisfile : 'typeisglbankguaranteemoney3',
							businessType : 'Guarantee',
							uploadsSize : 15

						});
				this.outPanel = new Ext.Panel({
							modal : true,
							border : false,
							frame : true,
							layout:'anchor',
		  					anchor : '100%',
							items : [
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【收取保费凭证】:</font></B>'},
										this.uploads3,
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【银行冻结保证金凭证】:</font></B>'},
										this.uploads1,
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【收取客户保证金凭证】:</font></B>'},
										this.uploads2
									]
						});
				this.add(this.outPanel);
				this.doLayout();
			}
		});