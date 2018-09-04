/**
 * @author
 * @createtime
 * @class FlAlterAccrualRecordEditInfoView
 * @extends Ext.Window
 * @description 融资租赁--利率变更详情
 * @company 智维软件
 */
FlAlterAccrualRecordEditInfoView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg.record) {
			this.record = _cfg.record;
		}
		if (_cfg.oppositeType) {
			this.oppositeType = _cfg.oppositeType;
		}
		if (_cfg.flag) {
			this.flag = _cfg.flag;
		}
		if (_cfg.projectStatus) {
			this.projectStatus = _cfg.projectStatus;
		}
		if (_cfg.bmStatus) {
			this.bmStatus = _cfg.bmStatus;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		FlAlterAccrualRecordEditInfoView.superclass.constructor.call(this, {
			id: 'FlAlterAccrualRecordEditInfoView_'+this.flag+'_'+this.record.data.slAlteraccrualRecordId,
			title : this.flag=='edit'?'利率变更编辑——'+this.record.data.dateMode:'利率变更查看——'+this.record.data.dateMode,
			autoHeight : true,
			items : [this.outPanel]
		});
	},// end of constructor
	
	// 初始化组件
	initUIComponents : function() {
		this.FlAlterAccrualRecordNavigation=new FlAlterAccrualRecordNavigation({record:this.record,flag:this.flag,projectStatus:this.projectStatus,bmStatus:this.bmStatus});
		this.FlAlterAccrualRecordInfo=""
		if(this.flag=='edit'){
			this.FlAlterAccrualRecordInfo=new FlAlterAccrualRecordEditInfo({record:this.record,oppositeType:this.oppositeType,projectStatus:this.projectStatus,bmStatus:this.bmStatus});
		}else{
			this.FlAlterAccrualRecordInfo=new FlAlterAccrualRecordSeeInfo({record:this.record,oppositeType:this.oppositeType});
		}
		this.outPanel = new Ext.Panel({
			modal : true,
			buttonAlign : 'center',
			layout : 'form',
			frame : true,
			autoHeight :true,
			defaults : {
				anchor : '100%',
				columnWidth : 1,
				collapsible : false
			},
			labelAlign : "right",
			items : [{
				xtype : 'panel',
				height : 100,
				title : '<font class="x-myZW-fieldset-title">项目总览(</font><font color="red">注：带图标按钮为弹窗式操作按钮，其余均为项目信息部分快速定位操作按钮</font><font class="x-myZW-fieldset-title">)</font>',
				collapsible : true,
				items : [this.FlAlterAccrualRecordNavigation],
				listeners : {
					scope : this,
					"collapse" : function(panel) {
						this.FlAlterAccrualRecordInfo.setHeight(document.body.clientHeight - 225 + 100);//项目信息部分面板最初的高度加上收缩起来的面板的高度
						this.doLayout();
					},
					"expand" : function(panel) { 
						this.FlAlterAccrualRecordInfo.setHeight(document.body.clientHeight - 200);//项目信息部分面板当前的高度减去收缩起来的面板的高度
						this.doLayout();
					}
				}
			}, {
				xtype : 'panel',
				layout : 'form',
				layoutConfig : {
					padding : '5',
					pack : 'center',
					align : 'middle'
				},
				items : [this.FlAlterAccrualRecordInfo]
			}]
		});
		
	}
});
