/*
 * Simplified Chinese translation
 * By DavidHu
 * 09 April 2007
 */

Ext.UpdateManager.defaults.indicatorText = '<div class="loading-indicator">加载中...</div>';

if(Ext.View){
   Ext.View.prototype.emptyText = "";
}

if(Ext.grid.GridPanel){
   Ext.grid.GridPanel.prototype.ddText = "{0} 选择行";
}

if(Ext.TabPanelItem){
   Ext.TabPanelItem.prototype.closeText = "关闭";
}

if(Ext.form.Field){
   Ext.form.Field.prototype.invalidText = "输入值非法";
}

Date.monthNames = [
   "一月",
   "二月",
   "三月",
   "四月",
   "五月",
   "六月",
   "七月",
   "八月",
   "九月",
   "十月",
   "十一月",
   "十二月"
];

Date.dayNames = [
   "日",
   "一",
   "二",
   "三",
   "四",
   "五",
   "六"
];

if(Ext.MessageBox){
   Ext.MessageBox.buttonText = {
      ok     : "确定",
      cancel : "取消",
      yes    : "是",
      no     : "否"
   };
}

if(Ext.util.Format){
   Ext.util.Format.date = function(v, format){
      if(!v) return "";
      if(!(v instanceof Date)) v = new Date(Date.parse(v));
      return v.dateFormat(format || "y年m月d日");
   };
}

if(Ext.DatePicker){
   Ext.apply(Ext.DatePicker.prototype, {
      todayText         : "今天",
      minText           : "日期在最小日期之前",
      maxText           : "日期在最大日期之后",
      disabledDaysText  : "",
      disabledDatesText : "",
      monthNames        : Date.monthNames,
      dayNames          : Date.dayNames,
      nextText          : '下月 (Control+Right)',
      prevText          : '上月 (Control+Left)',
      monthYearText     : '选择一个月 (Control+Up/Down 来改变年)',
      todayTip          : "{0} (空格键选择)",
      format            : "y年m月d日",
      okText            : "确定",
      cancelText        : "取消"
   });
}

if(Ext.PagingToolbar){
   Ext.apply(Ext.PagingToolbar.prototype, {
      beforePageText : "第",
      afterPageText  : "页，共 {0} 页",
      firstText      : "第一页",
      prevText       : "前一页",
      nextText       : "下一页",
      lastText       : "最后页",
      refreshText    : "刷新",
      displayMsg     : "显示 {0} - {1}，共 {2} 条",
      emptyMsg       : '没有数据需要显示'
   });
}

if(Ext.form.TextField){
   Ext.apply(Ext.form.TextField.prototype, {
      minLengthText : "该输入项的最小长度是 {0}",
      maxLengthText : "该输入项的最大长度是 {0}",
      blankText     : "该输入项为必输项",
      regexText     : "",
      emptyText     : null
   });
}

if(Ext.form.NumberField){
   Ext.apply(Ext.form.NumberField.prototype, {
      minText : "该输入项的最小值是 {0}",
      maxText : "该输入项的最大值是 {0}",
      nanText : "{0} 不是有效数值"
   });
}

if(Ext.form.DateField){
   Ext.apply(Ext.form.DateField.prototype, {
      disabledDaysText  : "禁用",
      disabledDatesText : "禁用",
      minText           : "该输入项的日期必须在 {0} 之后",
      maxText           : "该输入项的日期必须在 {0} 之前",
      invalidText       : "{0} 是无效的日期 - 必须符合格式： {1}",
      format            : "y年m月d日"
   });
}

if(Ext.form.ComboBox){
   Ext.apply(Ext.form.ComboBox.prototype, {
      loadingText       : "加载...",
      valueNotFoundText : undefined
   });
}

if(Ext.form.VTypes){
   Ext.apply(Ext.form.VTypes, {
      emailText    : '该输入项必须是电子邮件地址，格式如： "user@domain.com"',
      urlText      : '该输入项必须是URL地址，格式如： "http:/'+'/www.domain.com"',
      alphaText    : '该输入项只能包含字符和_',
      alphanumText : '该输入项只能包含字符,数字和_'
   });
}

if(Ext.grid.GridView){
   Ext.apply(Ext.grid.GridView.prototype, {
      sortAscText  : "正序",
      sortDescText : "逆序",
      lockText     : "锁列",
      unlockText   : "解锁列",
      columnsText  : "列"
   });
}

if(Ext.grid.PropertyColumnModel){
   Ext.apply(Ext.grid.PropertyColumnModel.prototype, {
      nameText   : "名称",
      valueText  : "值",
      dateFormat : "y年m月d日"
   });
}

if(Ext.layout.BorderLayout && Ext.layout.BorderLayout.SplitRegion){
   Ext.apply(Ext.layout.BorderLayout.SplitRegion.prototype, {
      splitTip            : "拖动来改变尺寸.",
      collapsibleSplitTip : "拖动来改变尺寸. 双击隐藏."
   });
}
/*!
 * Ext JS Library 3.2.1
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
/*
 * Simplified Chinese translation
 * By DavidHu
 * 09 April 2007
 * 
 * update by andy_ghg
 * 2009-10-22 15:00:57
 */

Ext.UpdateManager.defaults.indicatorText = '<div class="loading-indicator">加载中...</div>';

if(Ext.DataView){
   Ext.DataView.prototype.emptyText = "";
}

if(Ext.grid.GridPanel){
   Ext.grid.GridPanel.prototype.ddText = "选择了 {0} 行";
}

if(Ext.TabPanelItem){
   Ext.TabPanelItem.prototype.closeText = "关闭此标签";
}

if(Ext.form.Field){
   Ext.form.Field.prototype.invalidText = "输入值非法";
}

if (Ext.LoadMask) {
    Ext.LoadMask.prototype.msg = "正在加载数据中······,请稍候······";
}

Date.monthNames = [
   "一月",
   "二月",
   "三月",
   "四月",
   "五月",
   "六月",
   "七月",
   "八月",
   "九月",
   "十月",
   "十一月",
   "十二月"
];

Date.dayNames = [
   "日",
   "一",
   "二",
   "三",
   "四",
   "五",
   "六"
];

Date.formatCodes.a = "(this.getHours() < 12 ? '上午' : '下午')";
Date.formatCodes.A = "(this.getHours() < 12 ? '上午' : '下午')";

if(Ext.MessageBox){
   Ext.MessageBox.buttonText = {
      ok     : "确定",
      cancel : "取消",
      yes    : "是",
      no     : "否"
   };
}

if(Ext.util.Format){
   Ext.util.Format.date = function(v, format){
      if(!v) return "";
      if(!(v instanceof Date)) v = new Date(Date.parse(v));
      return v.dateFormat(format || "y年m月d日");
   };
}

if(Ext.DatePicker){
   Ext.apply(Ext.DatePicker.prototype, {
      todayText         : "今天",
      minText           : "日期必须大于最小允许日期",//update
      maxText           : "日期必须小于最大允许日期",//update
      disabledDaysText  : "",
      disabledDatesText : "",
      monthNames        : Date.monthNames,
      dayNames          : Date.dayNames,
      nextText          : '下个月 (Ctrl+Right)',
      prevText          : '上个月 (Ctrl+Left)',
      monthYearText     : '选择一个月 (Control+Up/Down 来改变年份)',//update
      todayTip          : "{0} (空格键选择)",
      format            : "y年m月d日",
      okText            : "确定",
      cancelText        : "取消"
   });
}

if(Ext.PagingToolbar){
   Ext.apply(Ext.PagingToolbar.prototype, {
      beforePageText : "第",//update
      afterPageText  : "页,共 {0} 页",//update
      firstText      : "第一页",
      prevText       : "上一页",//update
      nextText       : "下一页",
      lastText       : "最后页",
      refreshText    : "刷新",
      displayMsg     : "当前第{0} - {1}条，共 {2} 条记录",//update
      emptyMsg       : '没有符合条件的记录······'
   });
}

if(Ext.form.TextField){
   Ext.apply(Ext.form.TextField.prototype, {
      minLengthText : "该输入项的最小长度是 {0} 个字符",
      maxLengthText : "该输入项的最大长度是 {0} 个字符",
      blankText     : "该输入项为必输项",
      regexText     : "",
      emptyText     : null
   });
}

if(Ext.form.NumberField){
   Ext.apply(Ext.form.NumberField.prototype, {
      minText : "该输入项的最小值是 {0}",
      maxText : "该输入项的最大值是 {0}",
      nanText : "{0} 不是有效数值"
   });
}

if(Ext.form.DateField){
   Ext.apply(Ext.form.DateField.prototype, {
      disabledDaysText  : "禁用",
      disabledDatesText : "禁用",
      minText           : "该输入项的日期必须在 {0} 之后",
      maxText           : "该输入项的日期必须在 {0} 之前",
      invalidText       : "{0} 是无效的日期 - 必须符合格式： {1}",
      format            : "y年m月d日"
   });
}

if(Ext.form.ComboBox){
   Ext.apply(Ext.form.ComboBox.prototype, {
      loadingText       : "加载中...",
      valueNotFoundText : undefined
   });
}

if(Ext.form.VTypes){
   Ext.apply(Ext.form.VTypes, {
      emailText    : '该输入项必须是电子邮件地址，格式如： "user@example.com"',
      urlText      : '该输入项必须是URL地址，格式如： "http:/'+'/www.example.com"',
      alphaText    : '该输入项只能包含半角字母和_',//update
      alphanumText : '该输入项只能包含半角字母,数字和_'//update
   });
}
//add HTMLEditor's tips by andy_ghg
if(Ext.form.HtmlEditor){
  Ext.apply(Ext.form.HtmlEditor.prototype, {
    createLinkText : '添加超级链接:',
    buttonTips : {
      bold : {
        title: '粗体 (Ctrl+B)',
        text: '将选中的文字设置为粗体',
        cls: 'x-html-editor-tip'
      },
      italic : {
        title: '斜体 (Ctrl+I)',
        text: '将选中的文字设置为斜体',
        cls: 'x-html-editor-tip'
      },
      underline : {
        title: '下划线 (Ctrl+U)',
        text: '给所选文字加下划线',
        cls: 'x-html-editor-tip'
      },
      increasefontsize : {
        title: '增大字体',
        text: '增大字号',
        cls: 'x-html-editor-tip'
      },
      decreasefontsize : {
        title: '缩小字体',
        text: '减小字号',
        cls: 'x-html-editor-tip'
      },
      backcolor : {
        title: '以不同颜色突出显示文本',
        text: '使文字看上去像是用荧光笔做了标记一样',
        cls: 'x-html-editor-tip'
      },
      forecolor : {
        title: '字体颜色',
        text: '更改字体颜色',
        cls: 'x-html-editor-tip'
      },
      justifyleft : {
        title: '左对齐',
        text: '将文字左对齐',
        cls: 'x-html-editor-tip'
      },
      justifycenter : {
        title: '居中',
        text: '将文字居中对齐',
        cls: 'x-html-editor-tip'
      },
      justifyright : {
        title: '右对齐',
        text: '将文字右对齐',
        cls: 'x-html-editor-tip'
      },
      insertunorderedlist : {
        title: '项目符号',
        text: '开始创建项目符号列表',
        cls: 'x-html-editor-tip'
      },
      insertorderedlist : {
        title: '编号',
        text: '开始创建编号列表',
        cls: 'x-html-editor-tip'
      },
      createlink : {
        title: '转成超级链接',
        text: '将所选文本转换成超级链接',
        cls: 'x-html-editor-tip'
      },
      sourceedit : {
        title: '代码视图',
        text: '以代码的形式展现文本',
        cls: 'x-html-editor-tip'
      }
    }
  });
}


if(Ext.grid.GridView){
   Ext.apply(Ext.grid.GridView.prototype, {
      sortAscText  : "正序",//update
      sortDescText : "倒序",//update
      lockText     : "锁定列",//update
      unlockText   : "解除锁定",//update
      columnsText  : "列"
   });
}

if(Ext.grid.PropertyColumnModel){
   Ext.apply(Ext.grid.PropertyColumnModel.prototype, {
      nameText   : "名称",
      valueText  : "值",
      dateFormat : "y年m月d日"
   });
}

if(Ext.layout.BorderLayout && Ext.layout.BorderLayout.SplitRegion){
   Ext.apply(Ext.layout.BorderLayout.SplitRegion.prototype, {
      splitTip            : "拖动来改变尺寸.",
      collapsibleSplitTip : "拖动来改变尺寸. 双击隐藏."
   });
}
var fullAnchor = '100%' ;
var scrollAnchor = '97%' ;
//new Ext.Window({
//	
//}); 
if(Ext.Window){
	Ext.apply(Ext.Window.prototype,{
		constrain : true,
		constrainHeader : true,
		onEsc : Ext.emptyFn
	}) ;
}
if(Ext.form.Field){
	Ext.apply(Ext.form.Field.prototype, {
		onRender : Ext.form.Field.prototype.onRender.createSequence(function(){
			this.readOnlyClass = 'readOnlyClass' ;
	        if(this.readOnly==true){
	        	this.el.addClass([this.readOnlyClass]) ;
	        }
		})
   });
}
//自动为allowBlank的输入框,加上前面的红色*号...,避免了所有
if(Ext.layout.FormLayout){
	Ext.apply(Ext.layout.FormLayout.prototype,{
		getTemplateArgs: function(field) {
	        var noLabelSep = !field.fieldLabel || field.hideLabel;
	        var label = field.fieldLabel || '' ;
	        label = field.allowBlank===false ? (label.indexOf('*')!=-1 ? label : '<font color="red">*</font>'+label) : label ;
	        return {
	            id            : field.id,
	            label         : label ,
//	            label         : field.allowBlank===false?'<font color="red">*</font>'+ field.fieldLabel : field.fieldLabel ,
	            itemCls       : (field.itemCls || this.container.itemCls || '') + (field.hideLabel ? ' x-hide-label' : ''),
	            clearCls      : field.clearCls || 'x-form-clear-left',
	            labelStyle    : this.getLabelStyle(field.labelStyle),
	            elementStyle  : this.elementStyle || '',
	            labelSeparator: noLabelSep ? '' : (Ext.isDefined(field.labelSeparator) ? field.labelSeparator : this.labelSeparator)
	        };
	    }
	}) ;
}


////解决    已经选中了好几行数据，在我点击其他行数据后（注：不是点击复选框），这时选中的行却只有刚点击的那一行了。
//Ext.override(Ext.grid.CheckboxSelectionModel, { 
//handleMouseDown : function(g, rowIndex, e){   
//    if(e.button !== 0 || this.isLocked()){   
//        return;   
//    }   
//    var view = this.grid.getView();   
//    if(e.shiftKey && !this.singleSelect && this.last !== false){ 
//    	//alert("shiftKey="+e.shiftKey+"  singleSelect="+this.singleSelect+"  last="+this.last+" rowIndex=" +rowIndex)
//        var last = this.last;   
//        this.selectRange(last, rowIndex, e.ctrlKey);   
//        this.last = last; // reset the last   
//        view.focusRow(rowIndex);   
//    }else{  
//    	//alert("rowIndex="+rowIndex+"  this.isSelected(rowIndex)="+this.isSelected(rowIndex))
//        var isSelected = this.isSelected(rowIndex);   
//	 	if(isSelected){   
//	       // this.deselectRow(rowIndex);   //这段作用如果这行选中 再点击一次（注：不是点击复选框）取消选中 ,  跟双击事件  选中参照有冲突
//		}else if(!isSelected || this.getCount() > 1){   
//	        this.selectRow(rowIndex, true);   
//	        view.focusRow(rowIndex);   
//        }   
//    }   
//} 
//});
/**
 * 为tabPanel动态设置tabTip
 * @param {} msg
 * @author lisl
 */
Ext.Panel.prototype.setTabTip = function(msg) {
	var tabPanel = this.findParentByType(Ext.TabPanel);
	if (tabPanel) {
		var tabEl = Ext.fly(tabPanel.getTabEl(this).id);
		if (tabEl) {
			var tsTxt = tabEl.child('span.x-tab-strip-text', true);
			if (tsTxt)
			tsTxt.qtip = msg.toString();
		}
	}
}
//验证为通过验证字段 给出提示
Ext.override(Ext.form.BasicForm, {
			isValid : function() {
				var valid = true;
				var msg = "";
				var i = 0;
				this.items.each(function(f) {
					
					if(f.allowBlank==false){
						if (!f.validate()) {
							i++;
	
							if (i % 2 == 0) {
								if(f.emptyMsg!=null){
									msg += ", <font color=red>" + f.emptyMsg
										+ "</font> 不能为空  "
								}
								else{
									msg += ", <font color=red>" + f.fieldLabel
										+ "</font> 不能为空  "
								}
								
								msg += '<br/>'
							} else {
									if(f.emptyMsg!=null){
									msg += "  <font color=red>" + f.emptyMsg
										+ "</font> 不能为空  "
									}
									else{
										msg += "  <font color=red>" + f.fieldLabel
											+ "</font> 不能为空  "
									}
							}
							valid = false;
						}
					}
				});
				if (msg != "") {
	//					Ext.Msg.alert('提示', msg);
					Ext.MessageBox.show({
						title : '提示信息',
						msg : msg,
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.ERROR
					});
				}
				return valid;
			}
		});