/**
 * @author 
 * @createtime 
 * @class DepartmentWin
 * @extends Ext.Window
 * @description 组织结构中的部门信息管理
 * @company 智维软件
 */
DepartmentWin = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				DepartmentWin.superclass.constructor.call(this, {
							id : 'DepartmentWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 360,
							width : 500,
							maximizable : true,
							title : '部门详细信息',
							buttonAlign : 'center',
							buttons : [{
										text : '保存',
										iconCls : 'btn-save',
										scope : this,
										handler : this.save
									}, {
										text : '重置',
										iconCls : 'btn-reset',
										scope : this,
										handler : this.reset
									}, {
										text : '取消',
										iconCls : 'btn-cancel',
										scope : this,
										handler : this.close
									}]
						});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll : true,
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
										name : 'organization.orgId',
										xtype : 'hidden',
										value : this.orgId == null ? '' : this.orgId
									}, {
										xtype:'hidden',
										name:'organization.demId',
										value:1//表示为行政维度
									}, {
										fieldLabel : '部门名称',
										name : 'organization.orgName',
										allowBlank : false,
										maxLength : 128
									}, {
										fieldLabel : '部门描述',
										xtype:'textarea',
										name : 'organization.orgDesc',
										maxLength : 500
									},{
										fieldLabel : '上级组织',
										name : 'organization.orgSupId',
										value:this.orgSupId?this.orgSupId:0,
										xtype : 'numberfield'
									}, {
										xtype:'hidden',
										name:'organization.orgType',
										value:'2'//公司
									}
									]
						});
				//加载表单对应的数据	
				if (this.orgId != null && this.orgId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/system/getOrganization.do?orgId=' + this.orgId,
								root : 'data',
								preName : 'organization'
							});
				}

			},//end of the initcomponents

			/**
			 * 重置
			 * @param {} formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 保存记录
			 */
			save : function() {
				$postForm({
							formPanel : this.formPanel,
							scope : this,
							url : __ctxPath + '/system/saveOrganization.do',
							callback : function(fp, action) {
								
								var scope=this.scope?this.scope:this;
								
								if(this.callback){
									this.callback.call(scope);
								}
								
								var gridPanel = Ext.getCmp('OrganizationGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								this.close();
							}
						});
			}//end of save

		});