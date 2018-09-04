/**
 * @author
 * @createtime
 * @class LeaseFlEarlyrepaymentRecordEditInfoView
 * @extends Ext.Window
 * @description 提前还款详情
 * @company 智维软件
 */
LeaseFlEarlyrepaymentRecordEditInfoView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg.record) {
			this.record = _cfg.record;
		}
		if (_cfg.flag) {
			this.flag = _cfg.flag;
		}
		if (_cfg.projectStatus) {
			this.projectStatus = _cfg.projectStatus;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		LeaseFlEarlyrepaymentRecordEditInfoView.superclass.constructor.call(this, {
			id: 'LeaseFlEarlyrepaymentRecordEditInfoView_'+this.flag+'_'+this.record.data.slEarlyRepaymentId,
			title : this.flag=='edit'?'提前还款编辑——'+this.record.data.dateMode:'提前还款查看——'+this.record.data.dateMode,
			autoHeight : true,
			items : [this.outPanel]
		});
	},// end of constructor
	
	// 初始化组件
	initUIComponents : function() {
		this.LeaseFlEarlyrepaymentRecordNavigation=new LeaseFlEarlyrepaymentRecordNavigation({record:this.record,flag:this.flag,projectStatus:this.projectStatus});
		this.LeaseFlEarlyrepaymentRecordInfo=""
		if(this.flag=='edit'){
			this.LeaseFlEarlyrepaymentRecordInfo=new LeaseFlEarlyrepaymentRecordEditInfo({record:this.record,oppositeType:this.oppositeType,projectStatus:this.projectStatus});
		}else{
			this.LeaseFlEarlyrepaymentRecordInfo=new LeaseFlEarlyrepaymentRecordSeeInfo({record:this.record,oppositeType:this.oppositeType});
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
				items : [this.LeaseFlEarlyrepaymentRecordNavigation],
				listeners : {
					scope : this,
					"collapse" : function(panel) {
						this.LeaseFlEarlyrepaymentRecordInfo.setHeight(document.body.clientHeight - 225 + 100);//项目信息部分面板最初的高度加上收缩起来的面板的高度
						this.doLayout();
					},
					"expand" : function(panel) { 
						this.LeaseFlEarlyrepaymentRecordInfo.setHeight(document.body.clientHeight - 200);//项目信息部分面板当前的高度减去收缩起来的面板的高度
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
				items : [this.LeaseFlEarlyrepaymentRecordInfo]
			}]
		});
		
	}
});
