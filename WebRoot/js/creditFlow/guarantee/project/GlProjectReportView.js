/**
 * @author lisl
 * @class GlProjectReportView
 * @description 项目报告
 * @extends Ext.Window
 */
GlProjectReportView = Ext.extend(Ext.Window, {
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				GlProjectReportView.superclass.constructor.call(this, {
							title : '项目报告',
							border : false,
							width : 787,
							height : this.isHidden ? 153 : 307,
							modal : true,
							iconCls : '',
							autoScroll : true,
							maximizable : true,
							layout : 'fit',
							items : []

						});
			},
			initUIComponents : function() {
				var jsArr = [
						__ctxPath + '/js/creditFlow/report/SlReportView.js',// 调查报告
						__ctxPath + '/js/creditFlow/report/SlRiskReportView.js'// 风险综合分析报告
				];
				$ImportSimpleJs(jsArr, this.constructPanel, this);
			},//初始化组件
			constructPanel : function() {
				this.slReportView = new SlReportView({projectId : this.projectId, businessType : 'Guarantee',isHiddenAffrim_report : false,isHidden_report : this.isHidden_report,isgdEdit : this.isgdEdit});
			    this.slRiskReportView = new SlRiskReportView({projectId : this.projectId,businessType : 'Guarantee',isHidden_riskReport : this.isHidden_riskReport,isgdEdit_riskReport : this.isgdEdit_riskReport,isHiddenAffrim_riskReport : false});
				this.outPanel = new Ext.Panel({
							modal : true,
							border : false,
							frame : true,
							layout:'anchor',
		  					anchor : '100%',
							items : [
										{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【担保调查报告】:</font></B>'},
										this.slReportView,
										{xtype:'panel',border:false,hidden : this.isHidden,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【风险分析报告】:</font></B>'},
										this.slRiskReportView
									]
						});
				this.add(this.outPanel);
				this.doLayout();
			}
		});