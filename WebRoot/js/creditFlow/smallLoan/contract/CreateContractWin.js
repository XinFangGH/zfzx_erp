/**
 * @HT_version3.0
 * @class CreateContractWin
 * @extends Ext.Window
 * 针对不同业务,新增借款合同添加js
 */
CreateContractWin = Ext.extend(Ext.Window,{
	constructor : function(_cfg) {
		if (_cfg.parentGridPanel) {
			this.parentGridPanel = _cfg.parentGridPanel;
		}
		if (_cfg.projId) {
			this.projId = _cfg.projId;
		}
		if (_cfg.bidPlanId) {
			this.bidPlanId = _cfg.bidPlanId;
		}
		if (_cfg.businessType) {
			this.businessType = _cfg.businessType;
		}
		if (_cfg.htType) {
			this.htType = _cfg.htType;
		}
		CreateContractWin.superclass.constructor.call(this,{
			id:'createContractWin',
			layout : 'fit',
			width : 400,
			height :150,
			modal : true,
			autoScroll : true,
			title : '选择合同模板',
			layout : 'fit',
			width : 400,
			height :150,
			modal : true,
			autoScroll : true,
			title : '选择合同模板',
			items : [{
				layout : 'column',
				items : [{
					columnWidth : 1,
					layout : 'form',
					border :false,
					labelWidth : 60,
					labelAlign : 'right',
					bodyStyle : 'margin-top:10px',
					items : [{
						xtype : "combo",
						anchor : "95%",
						hiddenName : "documenttempletId",
						displayField : 'itemName',
						editable:false,
						allowBlank:false,
						valueField : 'itemId',
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath+ '/contract/listByMarkDocumentTemplet.do?mark='+this.htType,
							fields : ['itemId', 'itemName']
						}),
						fieldLabel : "合同名称",
						blankText : "合同名称不能为空，请正确填写!"
					}]
				}]
			}],
			buttonAlign : 'center',
			buttons : [{
				text : '保存',
				iconCls : 'btn-save',
				scope:this,
				handler :function(v){
					var template=this.getOriginalContainer().getCmpByName('documenttempletId');
					var args={
						templateId:template.getValue(),
						projId :this.projId ,
						bidPlanId:this.bidPlanId,
						businessType:this.businessType,
						htType:this.htType,
						htmcdName:template.lastSelectionText
					}
					if(template){
						var pbar = Ext.MessageBox.wait('合同生成中，可能需要较长时间，请耐心等待...','请等待',{
							interval:200,
							increment:15
						});
						var gridPanel=this.parentGridPanel;
						Ext.Ajax.request({
							url : __ctxPath+'/contract/saveContractContractHelper.do',
							method : 'POST',
							timeout:1800000,
							scope : this,
							success : function(response, request) {
								var obj = Ext.util.JSON.decode(response.responseText);
								if(obj.success == true){
									pbar.getDialog().close();
									if (gridPanel) {
										gridPanel.getStore().reload();
									}
									Ext.getCmp('createContractWin').close()
									Ext.ux.Toast.msg('状态', '合同生成成功！');
								}else{
									pbar.getDialog().close();
									Ext.ux.Toast.msg('状态', '合同生成失败，可能未上传合同模板，请重试！');
								}
							},
							failure : function(response) {
								Ext.ux.Toast.msg('状态', '合同生成成功！');
								pbar.getDialog().close();
							},
							params : args
						})
					}else{
						Ext.ux.Toast.msg('状态','请先选择合同模板！');
					}
				}
			}]
		});
	}
});