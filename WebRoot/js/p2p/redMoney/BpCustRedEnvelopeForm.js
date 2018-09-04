/**
 * @author
 * @createtime
 * @class BpCustRedEnvelopeForm
 * @extends Ext.Window
 * @description BpCustRedEnvelope表单
 * @company 智维软件
 */
RedenvelopeForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		RedenvelopeForm.superclass.constructor.call(this, {
					id : 'RedenvelopeFormWin',
					layout : 'fit',
					items :[ this.formPanel,this.grid_sharteequity],
					modal : true,
					height : 500,
					width : 700,
					maximizable : true,
					title : '[BpCustRedEnvelope]详细信息',
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
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					// id : 'BpCustRedEnvelopeForm',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
								name : 'bpCustRedEnvelope.redId',
								xtype : 'hidden',
								value : this.redId == null ? '' : this.redId
							}, {
								fieldLabel : '名称',
								name : 'bpCustRedEnvelope.name',
								maxLength : 50
							}, {
								fieldLabel : '简介',
								name : 'bpCustRedEnvelope.remarks',
								maxLength : 255
							}]
				});
		// 加载表单对应的数据
		if (this.redId != null && this.redId != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath
								+ '/p2p.redMoney/getBpCustRedEnvelope.do?redId='
								+ this.redId,
						root : 'data',
						preName : 'bpCustRedEnvelope'
					});
		}
		
		
			this.grid_sharteequity = new HT.EditorGridPanel(
			{
				border : false,
				region:'center',
				showPaging:false,
				tbar : this.sharteequitybar,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				viewConfig : {
					forceFit : true
				},
				sm : new Ext.grid.CheckboxSelectionModel({}),
				store : new Ext.data.Store(
						{
							proxy : new Ext.data.HttpProxy(
									{
									 url:__ctxPath+ '/creditFlow/common/getShareequity.do?enterpriseId='+ 1,
										method : "POST"
									}),
							reader : new Ext.data.JsonReader({
								fields : Ext.data.Record.create( [
									{
										name : 'id'
									},
									{
										name : 'shareholdertype'
									}/*,
									{
										name : 'shareholdercode'
									}*/,
									{
										name : 'capital'
									},
									{
										name : 'capitaltype'
									},
									{
										name : 'share'
									},
									{
										name : 'shareholder'
									},
									{
										name : 'remarks'
									},
									{
										name : 'personid'
									},{
									
									    name : 'createTime'
									},
									{
										name : 'personName'
									},
									{
										name : 'shareholdertypeName'
									},
									{
										name : 'capitaltypeName'
									}

							]),
							root : 'result'
						})
			}),
				columns : [
					{
						header : '股东(投资人)名称',
						dataIndex : 'personName',
						sortable : true,
						width : 200
					},{
						header : '出资额',
						dataIndex : 'capital',						
						sortable : true,
						align : 'right',
						editor : {
							xtype : 'numberfield',
							readOnly:this.isHidden,
							allowBlank:false,
							style: {imeMode:'disabled'}
							
						},
						renderer : function(value,metaData, record,rowIndex, colIndex,store) {
						
							return Ext.util.Format.number(value,'0,000.00')+"元"	
						}

					},
					{
						header : '出资方式',
						dataIndex : 'capitaltype',
						sortable : true,
						align:'center',
						width : 100,
						editor : new DicKeyCombo(
								{
									allowBlank : false,
									maxLength : 128,
									editable : true,
									nodeKey : 'czfs',//原来为capitaltype。两个key下的子项一样。使用czfs。
									lazyInit : true,
									lazyRender : true,
									readOnly:this.isHidden
									//width : 200
								}),
						renderer : function(value,metaData, record,rowIndex, colIndex,store) {
						
							var objcom = this.editor;
							var objStore = objcom.getStore();
							var idx = objStore.find("itemId", value);
							if (idx != "-1") {
								return objStore.getAt(idx).data.itemName;
							} else {
								return record.get("capitaltypeName")
							}
						}
					}, {
						header : '备注',
						dataIndex : 'remarks',
						sortable : true,
						align : "center",
						editor : {
							xtype : 'textfield',
							readOnly:this.isHidden
						},
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					},{
						header : '时间',
						dataIndex : 'createTime',
						hidden:true
					} ]

              });

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath + '/p2p.redMoney/saveBpCustRedEnvelope.do',
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('BpCustRedEnvelopeGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save

});