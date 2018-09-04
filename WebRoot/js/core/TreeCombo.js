// vim: ts=4:sw=4:nu:fdc=4:nospell
/*global Ext */
/**
 * @class Ext.ux.form.CheckTreeCombo
 * @extends Ext.form.TriggerField
 *
 * <p>
 * This is a combo that shows {@link Ext.ux.tree.CheckTreePanel CheckTreePanel} in the drop down list.
 * Text typed manually in textfield serves as the search phrase for CheckTreePanel filtering.
 * </p>
 *
 * @author    Ing. Jozef Sakáloš
 * @copyright (c) 2008, by Ing. Jozef Sakáloš
 * @version   1.0
 * @date      13. January 2009
 * @revision  $Id: Ext.ux.form.CheckTreeCombo.js 589 2009-02-21 23:30:18Z jozo $
 *
 * @license Ext.ux.form.CheckTreeCombo.js is licensed under the terms of the Open Source
 * LGPL 3.0 license. Commercial use is permitted to the extent that the 
 * code/component(s) do NOT become part of another Open Source or Commercially
 * licensed development library or toolkit without explicit permission.
 * 
 * <p>License details: <a href="http://www.gnu.org/licenses/lgpl.html"
 * target="_blank">http://www.gnu.org/licenses/lgpl.html</a></p>
 *
 * @donate
 * <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_blank">
 * <input type="hidden" name="cmd" value="_s-xclick">
 * <input type="hidden" name="hosted_button_id" value="3430419">
 * <input type="image" src="https://www.paypal.com/en_US/i/btn/x-click-butcc-donate.gif" 
 * border="0" name="submit" alt="PayPal - The safer, easier way to pay online.">
 * <img alt="" border="0" src="https://www.paypal.com/en_US/i/scr/pixel.gif" width="1" height="1">
 * </form>
 */

Ext.ns('Ext.ux.form');

/**
 * Creates new CheckTreeCombo
 * @constructor
 * @param {Object} config The configuration object
 */
Ext.ux.form.CheckTreeCombo = Ext.extend(Ext.form.TriggerField, {

    // {{{
    // config options
    /**
     * @cfg {Number} filterChars Minimum characters typed in the text field for tree filtering to start
     * (defaults to 3) <b>Currently not implemented.</b>
     */
     filterChars:3

    /**
     * @cfg {String} getValueMethod Method to call to get value. 
     * Valid options are:
     * <ul class="list">
     * <li><b>"getCheckedIds"</b> - which returns array of ids of checked nodes</li>
     * <li><b>"getText"</b> - which returns (comma) separated string of texts of checked nodes</li></ul>
     * (defaults to 'getCheckedIds')
     */
    ,getValueMethod:'getCheckedIds'

    /**
     * @cfg {Number} handleHeight The height in pixels of the dropdown list resize handle if resizable:true
     * (defaults to 8)
     */
    ,handleHeight:6

    /**
     * @cfg {String} listAlign A valid anchor position value. See {@link Ext.Element#alignTo} for details on supported
     * anchor positions (defaults to 'tl-bl?')
     */
    ,listAlign:'tl-bl?'

    /**
     * @cfg {String} listClass CSS class to apply to the dropdown list element (defaults to '')
     */
    ,listClass:''

    /**
     * @cfg {String} listAlign A valid anchor position value. See {@link Ext.Element#alignTo} for details on supported
     * anchor positions (defaults to 'tl-bl')
     */
    ,listHeight:200

    /**
     * @cfg {Object} loaderBaseParams Base params applied to tree loader if present (defaults to undefined)
     */

    /**
     * @cfg {Number} minListWidth The minimum width of the dropdown list in pixels (defaults to 70, will be ignored if
     * listWidth has a higher value)
     */
    ,minListWidth:70

    /**
     * @cfg {Boolean} resizable True to add a resize handle to the bottom of the dropdown list (defaults to false)
     */
    ,resizable:false

    /**
     * @cfg {Boolean} selectOnFocus True to automatically select any existing field text when the field receives
     * input focus (defaults to true)
     */
    ,selectOnFocus:true

    /**
     * @cfg {Boolean/String} shadow True or "sides" for the default effect, "frame" for 4-way shadow, and "drop" for bottom-right
     */
    ,shadow:'sides'

    /**
     * @cfg {Object} treeConfig Config object for the underlying {@link Ext.ux.tree.CheckTreePanel CheckTreePanel}.
     * Defaults to
     * <pre>
     * {
     * &nbsp;   rootVisible:false
     * &nbsp;  ,border:false
     * &nbsp;  ,autoScroll:true
     * &nbsp;  ,deepestOnly:true
     * &nbsp;  ,loaded:false
     * &nbsp;  ,root:{
     * &nbsp;       nodeType:'async'
     * &nbsp;      ,expanded:true
     * &nbsp;      ,uiProvider:false
     * &nbsp;  }
     * &nbsp;  ,loader:{
     * &nbsp;       url:'request.php'
     * &nbsp;      ,preloadChildren:true
     * &nbsp;  }
     * }
     * </pre>
     */
    ,treeConfig: {
         rootVisible:false
        ,border:false
        ,autoScroll:true
        ,deepestOnly:true
        ,loaded:false
        ,root:{
             nodeType:'async'
            ,expanded:true
            ,uiProvider:false
        }
        ,loader:{
             url:'request.php'
            ,preloadChildren:true
        }
    }

    ,value:[]
    // }}}
    // {{{
    /**
     * Clears filter that may have been applied to the underlying {@link Ext.ux.tree.CheckTreePanel CheckTreePanel}
     * by typing in the combo
     */
    ,clearFilter:function() {
        if(this.tree && this.tree.filter) {
            this.tree.filter.clear();
        }
    } // eo function clearFilter
    // }}}
    // {{{
    /**
     * Collapses (hides) the dropdown panel with tree
     */
    ,collapse:function() {
        if(!this.isExpanded()) {
            return;
        }
        this.list.hide();
        Ext.getDoc().un('mousedown', this.collapseIf, this);
        this.fireEvent('collapse', this);
    }
    // }}}
    // {{{
    ,collapseIf:function(e) {
        if(!e.within(this.wrap) && !e.within(this.list)) {
            this.collapse();
        }
    } // eo function collapseIf
    // }}}
    // {{{
    /**
     * Expands (shows) the dropdown panel with tree
     */
    ,expand:function() {
        if(this.isExpanded()) {
            return;
        }
        this.list.alignTo(this.wrap, this.listAlign);
        this.list.show();
        Ext.getDoc().on('mousedown', this.collapseIf, this);
        this.fireEvent('expand', this);
    }
    // }}}
    // {{{
    ,getCheckedIds:function() {
        if(this.tree) {
            return this.tree.getValue();
        }
        else {
            return this.value;
        }
    } // eo function getCheckedIds
    // }}}
    // {{{
    /** 
     * Returns {@link Ext.grid.GridEditor GridEditor} that is suitable as grid column editor.
     * @param {Object} col Column config object that contains <code>editor</code> property.
     * This property is used as GridEditor config object
     * @return {Ext.grid.GridEditor}
     */
    ,getEditor:function(col) {
        var config = col.editor;

        // todo: add here some defaults if necessary or delete if not
        Ext.apply(config, {
        });

        var ed = new Ext.grid.GridEditor(new Ext.ux.form.CheckTreeCombo(config), {
            updateEl:false
        });
        ed.on({
            startedit:{fn:function(el, value) {
                // collapse tree
                if(this.field.tree) {
                    this.field.tree.filter.clear();
                    Ext.each(this.field.tree.getRootNode().childNodes, function(n) {
                        n.collapse(true, false);
                    });
                }
                this.field.setValue(this.record.get(this.field.idName));
                this.field.expand();
            }}
            ,complete:{fn:function(editor, value, startValue) {
                this.record.set(this.field.idName, this.field.getCheckedIds());
            }}
        });
        return ed;
    } // eo function getEditor
    // }}}
    // {{{
    /**
     * Returns value of the combo
     * @return {Array/String} Returns array of checked node ids or delimited string of checked node texts
     * depending on getValueMethod config option
     */
    ,getValue:function() {
        return this[this.getValueMethod]();
    } // eo function getValue
    // }}}
    // {{{
    ,getText:function() {
        if(this.tree) {
            return this.tree.getText();
        }
        else {
            return this.value;
        }
    } // eo function getText
    // }}}
    // {{{
    ,initComponent:function() {

        // create config
        if(this.loaderBaseParams) {
            Ext.apply(this.treeConfig.loader, {
                baseParams:this.loaderBaseParams
            });
        }
        var config = {
            tree:new Ext.ux.tree.CheckTreePanel(this.treeConfig)
        };
        config.tree.loader.on({
            load:{scope:this, fn:function(loader, node) {
                this.tree.loaded = true;
                this.setValue(this.value);
            }}
        });
        config.tree.on({checkchange:{scope:this, delay:20, fn:this.onCheckChange}});

        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));

        // call parent
        Ext.ux.form.CheckTreeCombo.superclass.initComponent.apply(this, arguments);

        this.addEvents(
            /**
             * @event expand
             * Fires when the dropdown list is expanded
             * @param {Ext.ux.form.CheckTreeCombo} combo This checktree combo box
             */
             'expand'
            /**
             * @event collapse
             * Fires when the dropdown list is collapsed
             * @param {Ext.ux.form.CheckTreeCombo} combo This checktree combo box
             */
            ,'collapse'
        );
         
    } // eo function initComponent
    // }}}
    // {{{
    ,initList:function() {

        if(this.list) {
            return;
        }

        // create list layer
        var cls = 'x-combo-list';
        this.list = new Ext.Layer({
             shadow:this.shadow
            ,cls:[cls, this.listClass].join(' ')
            ,constrain:false
        });

        // set list width
        var lw = this.listWidth || Math.max(this.wrap.getWidth(), this.minListWidth);
        this.innerList = this.list.createChild({cls:cls + '-inner'});
        this.setListSize(lw, this.listHeight);

        // create resizer
        if(this.resizable) {
            this.resizer = new Ext.Resizable(this.list, {
                 pinned:true
                ,handles:'se'
            });
            this.resizer.on('resize', function(r, w, h) {
                this.setListSize(w, h);
            }, this);
        } 
    } // eo function initList
     // }}}
    // {{{
    /**
     * Returns true if dropdown list is expanded (visible) false otherwise
     * @return {Boolean}
     */
    ,isExpanded:function() {
        return this.list && this.list.isVisible();
    }
    // }}}
    // {{{
    ,onCheckChange:function(node, checked) {
        this.el.focus();
        this.el.dom.select();
        return;
    } // eo function onCheckChange
    // }}}
    // {{{
    ,onDestroy:function() {
        if(this.tree) {
            this.tree.destroy();
        }
        if(this.list) {
            // todo: uninstall event handlers
            this.list.destroy();
        }
    } // eo function onDestroy
    // }}}
    // {{{
    ,onKeyUp:function(e, el) {
        var val = this.getRawValue();
        if(e.ESC === e.getKey() || !val) {
            this.clearFilter();
            this.setRawValue();
        }
        else {
            if(this.tree.filter) {
                this.clearFilter();
                this.expand();
                var re = new RegExp('.*' + val + '.*', 'i');
                this.tree.filter.filter(re, 'text');
            }
        }
    } // eo function onKeyUp
    // }}}
    // {{{
    ,onRender:function() {
 
        // call parent
        Ext.ux.form.CheckTreeCombo.superclass.onRender.apply(this, arguments);
 
        // after parent code, e.g. install event handlers on rendered components
        (function() {
             this.initList();
            if(!this.tree.rendered) {
                this.tree.render(this.innerList);
                this.tree.setHeight(this.innerList.getHeight() - this.list.getFrameWidth('tb'));
            }
        }.defer(1, this));

        this.el.on('keyup', this.onKeyUp, this, {buffer:180});
 
    } // eo function onRender
    // }}}
    // {{{
    ,onTriggerClick:function() {
        if(this.disabled) {
            return;
        }
        if(this.isExpanded()) {
            this.collapse();
            this.el.focus();
        }
        else {
            this.expand();
        }
    } // eo function onTriggerClick
    // }}}
    // {{{
    ,setListSize:function(w, h) {
        var hs = this.resizable ? this.handleHeight: 0;
        this.list.setSize(w, h);
        this.innerList.setSize(w - hs - this.list.getFrameWidth('lr'), h - hs);
        if(this.tree) {
            this.tree.setHeight(h - this.list.getFrameWidth('tb') - hs);
        }
    } // eo function setListSize
    // }}}
    // {{{
    /**
     * Sets the value of the combo and checks the nodes of CheckTreePanel
     * @param {Mixed} value Variable length arguments with mixture of arrays and/or separator
     * delimeted strings.
     */
    ,setValue:function() {
        if(this.tree.loaded) {
            this.value = this.tree.setValue.apply(this.tree, arguments);
        }
        else {
            this.value = this.tree.convertValue.apply(this.tree, arguments);
        }
        this.setRawValue();
        return this.value;
    } // eo function setValue
    // }}}
    // {{{
    /**
     * Sets the raw value which is displayed text and also sets tooltip with this text
     * @param {String} text The raw value to set
     */
    ,setRawValue:function() {
        if(this.el) {
            var text = this.getText();
            Ext.ux.form.CheckTreeCombo.superclass.setRawValue.call(this, text);
            this.el.set({qtip:text});
        }
    } // eo function setRawValue
    // }}}
    // {{{
    ,validateBlur:function() {
        return !this.list || !this.list.isVisible();
    } // eo function validateBlur
    // }}}

}); // eo extend

// register xtype
