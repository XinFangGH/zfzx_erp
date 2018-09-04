/**
 * @author YHZ 
 * @createtime 2010-12-13PM
 * @class RelativeJobForm
 * @extends Ext.Window
 * @description RelativeJob表单
 * @company 智维软件
 */
RelativeJobForm = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				RelativeJobForm.superclass.constructor.call(this, {
					id : 'RelativeJobFormWin',
					layout : 'fit',
					iconCls : 'menu-relativeJob',
					items : this.formPanel,
					modal : true,
					height : 120,
					width : 350,
					maximizable : true,
					title : '新增/编辑相对岗位信息',
					buttonAlign : 'center',
					buttons : [
						{
							text : '保存',
							iconCls : 'btn-save',
							scope : this,
							handler : this.save
						} , {
							text : '取消',
							iconCls : 'btn-cancel',
							scope : this,
							handler : this.cancel
						}
			         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
					id : 'RelativeJobForm',
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll:true,
					defaults : {
						anchor : '96%,96%'
					},
					keys : {
						key : Ext.EventObject.ENTER,
						fn : this.save,
						scope : this
					},
					defaultType : 'textfield',
				  reader: new Ext.data.JsonReader( 
						{root:'data'},[
			        	 	{name:'reJobId',mapping:'reJobId'}
			        		,{name:'parent',mapping:'parent'}
			        		,{name:'jobName',mapping:'jobName'}
			        	]),
					items : [{
						name : 'relativeJob.reJobId',
						id : 'reJobId',
						xtype : 'hidden',
						value : this.reJobId == null ? '' : this.reJobId
					} , {
						name : 'relativeJob.parent',
						id : 'parent',
						xtype : 'hidden',
						value : this.nodeId == null ? '2' : this.nodeId
					}, {
						fieldLabel : '岗位名称',	
						name : 'relativeJob.jobName',
						id : 'jobName',
						allowBlank : false,
						blankText : '请输入岗位名称',
						maxLength: 128
					}]
				});
			
			},//end of the initcomponents

			/**
			 * 取消
			 * @param {} window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
				$postForm({
					formPanel:this.formPanel,
					scope:this,
					url:__ctxPath + '/system/saveRelativeJob.do',
					callback:function(fp,action){
						Ext.getCmp('RelativeJobFormWin').close();            				
        				Ext.getCmp('RelativeUserViewTreePanel').root.reload();
					}
				});
			}//end of save
		});