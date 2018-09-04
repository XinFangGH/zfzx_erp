// vim: ts=4:sw=4:nu:fdc=2:nospell
/**
  * Ext.ux.IconCombo Extension Class for Ext 2.x Library
  *
  * @author  Ing. Jozef Sakalos
  * @version $Id: Ext.ux.IconCombo.js 617 2007-12-20 11:29:56Z jozo $
  *
  * @class Ext.ux.IconCombo
  * @extends Ext.form.ComboBox
  */
Ext.ux.IconCombob = Ext.extend(Ext.form.ComboBox, {
    initComponent:function() {
    	Ext.ux.IconCombob.superclass.initComponent.call(this);
    	// call parent initComponent
        Ext.apply(this, {
            tpl:  '<tpl for=".">'
                + '<div class="x-combo-list-item ux-icon-combo-item '
                + '{' + this.valueField + '}">{'+this.displayField+'}</div></tpl>'
        });
    }, // end of function initComponent
 
    onRender:function(ct, position) {
        // call parent onRender
        Ext.ux.IconCombob.superclass.onRender.call(this, ct, position);
        // adjust styles
        this.wrap.applyStyles({position:'relative'});
//        this.el.addClass('ux-icon-combo-input');
         this.el.setStyle('padding-left','25px');
        // add div for icon
        this.icon = Ext.DomHelper.append(this.el.up('div.x-form-field-wrap'), {
            tag: 'div', style:'position:absolute'
        });
    }, // end of function onRender
 
    setIconCls:function() {
        var rec = this.store.query(this.valueField, this.getValue()).itemAt(0);
        if(rec) {
            this.icon.className = 'ux-icon-combo-icon ' + rec.get(this.valueField);
        }
    }, // end of function setIconCls
// 
    setValue: function(value) {
        Ext.ux.IconCombob.superclass.setValue.call(this,value);
        this.setIconCls();
    } // end of function setValue
});
 
// register xtype
Ext.reg('iconcomb', Ext.ux.IconCombob);
 
// end of file